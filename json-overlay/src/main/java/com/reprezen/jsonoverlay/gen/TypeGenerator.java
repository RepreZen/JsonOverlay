/*******************************************************************************
 *  Copyright (c) 2017 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.jsonoverlay.gen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.reprezen.jsonoverlay.BooleanOverlay;
import com.reprezen.jsonoverlay.EnumOverlay;
import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;
import com.reprezen.jsonoverlay.IntegerOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.ListOverlay;
import com.reprezen.jsonoverlay.MapOverlay;
import com.reprezen.jsonoverlay.NumberOverlay;
import com.reprezen.jsonoverlay.ObjectOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.Primitive;
import com.reprezen.jsonoverlay.PrimitiveOverlay;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.gen.SimpleJavaGenerator.Member;
import com.reprezen.jsonoverlay.gen.TypeData.Field;
import com.reprezen.jsonoverlay.gen.TypeData.Type;

public abstract class TypeGenerator {

	private File dir;
	protected String intfPackage;
	protected String implPackage;
	protected String suffix;
	private boolean preserve;
	private Set<String> requiredTypes = Sets.newHashSet();

	public TypeGenerator(File dir, String intfPackage, String implPackage, String suffix, boolean preserve) {
		this.dir = dir;
		this.intfPackage = intfPackage;
		this.implPackage = implPackage;
		this.suffix = suffix;
		this.preserve = preserve;
	}

	protected abstract TypeDeclaration<?> getTypeDeclaration(Type type, String suffix);

	public void generate(Type type) throws IOException {
		String filename = String.format("%s%s.java", type.getName(), suffix);
		File javaFile = new File(dir, filename);
		System.out.println("Generating " + javaFile.getCanonicalFile());
		CompilationUnit existing = preserve && javaFile.exists() ? tryParse(javaFile) : null;
		TypeDeclaration<?> declaration = getTypeDeclaration(type, suffix);
		SimpleJavaGenerator gen = new SimpleJavaGenerator(getPackage(), declaration);
		if (existing != null) {
			copyFileComment(gen, existing);
			addManualMembers(gen, existing);
		}
		requireTypes(getImports(type));
		if (needIntfImports()) {
			gen.addImport(intfPackage + ".*");
		}
		addGeneratedMembers(type, gen);
		requireTypes(Generated.class);
		resolveImports(type, gen);
		FileUtils.write(javaFile, gen.format(), Charsets.UTF_8);
	}

	protected abstract String getPackage();

	protected abstract Collection<String> getImports(Type type);

	protected boolean needIntfImports() {
		return false;
	}

	protected void requireTypes(Class<?>... types) {
		requireTypes(Collections2.transform(Arrays.asList(types), new Function<Class<?>, String>() {
			@Override
			public String apply(Class<?> type) {
				return type.getSimpleName();
			}
		}).toArray(new String[types.length]));
	}

	protected void requireTypes(Type... types) {
		requireTypes(Collections2.transform(Arrays.asList(types), new Function<Type, String>() {
			@Override
			public String apply(Type type) {
				return type.getName();
			}
		}));
	}

	protected void requireTypes(String... types) {
		requireTypes(Arrays.asList(types));
	}

	protected void requireTypes(Collection<String> types) {
		requiredTypes.addAll(Collections2.transform(types, new Function<String, String>() {
			@Override
			public String apply(String type) {
				return type.contains("<") ? type.substring(0, type.indexOf("<")) : type;
			}
		}));
	}

	private void resolveImports(Type type, SimpleJavaGenerator gen) {
		Map<String, String> importMap = type.getTypeData().getImports();
		Map<String, Type> typeMap = type.getTypeData().getTypeMap();
		for (String requiredType : requiredTypes) {
			gen.addImport(resolveImport(requiredType, typeMap, importMap));
		}
	}

	private static Set<String> autoTypes = getAutoTypes();

	private static Set<String> getAutoTypes() {
		Set<String> results = Sets.newHashSet();
		ArrayList<Class<?>> autos = Lists.<Class<?>>newArrayList(//
				String.class, //
				Integer.class, //
				Number.class, //
				Boolean.class, //
				Primitive.class, //
				Object.class);
		for (Class<?> cls : autos) {
			results.add(cls.getSimpleName());
		}
		return results;
	}

	private static Map<String, String> knownTypes = getKnownTypes();

	private static Map<String, String> getKnownTypes() {
		Map<String, String> results = Maps.newHashMap();
		ArrayList<Class<?>> overlays = Lists.<Class<?>>newArrayList( //
				Generated.class, //
				List.class, //
				Map.class, //
				Optional.class, //
				Collectors.class, //
				JsonNode.class, //
				ObjectNode.class, //
				JsonNodeFactory.class, //
				JsonPointer.class, //
				IJsonOverlay.class, //
				JsonOverlay.class, //
				IModelPart.class, //
				PropertiesOverlay.class, //
				OverlayFactory.class, //
				ReferenceManager.class, //
				Inject.class, //
				StringOverlay.class, //
				IntegerOverlay.class, //
				NumberOverlay.class, //
				BooleanOverlay.class, //
				EnumOverlay.class, //
				PrimitiveOverlay.class, //
				ObjectOverlay.class, //
				ListOverlay.class, //
				MapOverlay.class); //
		for (Class<?> cls : overlays) {
			results.put(cls.getSimpleName(), cls.getName().replaceAll("\\$", "."));
		}
		return results;
	}

	private String resolveImport(String type, Map<String, Type> typeMap, Map<String, String> importMap) {
		if (importMap.containsKey(type)) {
			String imp = importMap.get(type);
			if (imp.equals("_intf")) {
				return intfPackage + "." + type;
			} else if (imp.equals("_impl")) {
				return implPackage + "." + type;
			} else {
				return imp;
			}
		} else if (typeMap.containsKey(type)) {
			// interface type
			return intfPackage + "." + type;
		} else if (!suffix.isEmpty() && type.endsWith(suffix)
				&& typeMap.containsKey(type.substring(0, type.length() - suffix.length()))) {
			// impl type
			return implPackage + "." + type;
		} else if (autoTypes.contains(type)) {
			return null;
		} else if (knownTypes.containsKey(type)) {
			return knownTypes.get(type);
		} else {
			throw new RuntimeException("Unable to resolve import for type: " + type);
		}
	}

	protected void addGeneratedMembers(Type type, SimpleJavaGenerator gen) {
		Members members = new Members();
		members.addAll(getConstructors(type));
		for (Field field : type.getFields().values()) {
			if (!skipField(field)) {
				members.addAll(getFieldMembers(field));
			}
		}
		for (Field field : type.getFields().values()) {
			if (!skipField(field)) {
				members.addAll(getFieldMethods(field));
			}
		}
		members.addAll(getOtherMembers(type));
		for (Member member : members) {
			maybeRename(member, type.getRenames());
		}
		gen.addGeneratedMembers(members);
	}

	private void maybeRename(Member member, Map<String, String> renames) {
		String name = member.getName();
		if (name != null && renames.containsKey(name)) {
			member.rename(name, renames.get(name));
		}
	}

	protected boolean skipField(Field field) {
		return false;
	}

	private CompilationUnit tryParse(File file) {
		try {
			return JavaParser.parse(file);
		} catch (IOException e) {
			System.err.println("ABORTING AFTER PARTIAL GENERATION!");
			System.err.printf(
					"Parsing of file %s failed; so generation cannot continue without destroying manual code.\n", file);
			System.err.println("Please restore generated code artifacts to a known good state before regenerating");
			System.err.println("Parse Error:");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	private void copyFileComment(SimpleJavaGenerator gen, CompilationUnit existing) {
		Optional<Comment> fileComment = existing.getComment();
		if (fileComment.isPresent()) {
			gen.setFileComment(fileComment.get().toString());
		}
	}

	private void addManualMembers(SimpleJavaGenerator gen, CompilationUnit existing) {
		for (TypeDeclaration<?> type : existing.getTypes()) {
			for (BodyDeclaration<?> member : type.getMembers()) {
				if (member instanceof MethodDeclaration || member instanceof FieldDeclaration
						|| member instanceof ConstructorDeclaration) {
					if (!isGenerated(member)) {
						gen.addMember(new Member(member));
					}
				}
			}
		}
	}

	private boolean isGenerated(BodyDeclaration<?> node) {
		for (AnnotationExpr annotation : node.getAnnotations()) {
			if (annotation.getName().toString().equals("Generated")) {
				return true;
			}
		}
		return false;
	}

	protected Members getConstructors(Type type) {
		return new Members();
	}

	protected Members getFieldMembers(Field field) {
		return new Members();
	}

	protected Members getFieldMethods(Field field) {
		return new Members();
	}

	protected Members getOtherMembers(Type type) {
		return new Members();
	}

	protected Member addMember(BodyDeclaration<?> declaration, Collection<String> code) {
		return addMember(declaration, null);
	}

	protected final Member addMember(BodyDeclaration<?> declaration) {
		return addMember(declaration, null);
	}

	protected static class Members extends ArrayList<Member> {

		private static final long serialVersionUID = 1L;

		public Member addMember(String code) {
			return addMember(new Member(code));
		}

		public Member addMember(Member member) {
			add(member);
			return member;
		}

	}
}
