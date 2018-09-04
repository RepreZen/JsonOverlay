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
package com.reprezen.jsonoverlay.gen

import com.fasterxml.jackson.core.JsonPointer
import com.fasterxml.jackson.databind.JsonNode
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.google.common.collect.Queues
import com.google.common.collect.Sets
import com.reprezen.jsonoverlay.Builder
import com.reprezen.jsonoverlay.EnumOverlay
import com.reprezen.jsonoverlay.IJsonOverlay
import com.reprezen.jsonoverlay.JsonOverlay
import com.reprezen.jsonoverlay.ListOverlay
import com.reprezen.jsonoverlay.MapOverlay
import com.reprezen.jsonoverlay.OverlayFactory
import com.reprezen.jsonoverlay.ReferenceManager
import com.reprezen.jsonoverlay.gen.SimpleJavaGenerator.Member
import com.reprezen.jsonoverlay.gen.TypeData.Field
import com.reprezen.jsonoverlay.gen.TypeData.Structure
import com.reprezen.jsonoverlay.gen.TypeData.Type
import java.io.File
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.stream.Collectors

class ImplGenerator extends TypeGenerator {

	new(File dir, String intfPackage, String implPackage, String suffix, boolean preserve) {
		super(dir, intfPackage, implPackage, suffix, preserve)
	}

	override String getPackage() {
		return implPackage
	}

	override Collection<String> getImports(Type type) {
		return type.getRequiredImports("impl", "both")
	}

	override boolean needIntfImports() {
		return true
	}

	override Members getOtherMembers(Type type) {
		val members = new Members
		if (type.isEnum) {
			members.add(new Member('''
				@Override
				protected Class<«type.name»> getEnumClass() {
					return «type.name».class;
				}
			'''))
			members.add(type.enumFactoryMember)
		} else {
			members.add(getElaborateJsonMethod(type))
			members.addAll(getFactoryMembers(type))
			if (type.typeData.modelType !== null) {
				members.addMember('''
					@Override
					public Class<?> _getModelType() {
						return «type.typeData.modelType».class;
					}
				''')
			}
		}
		members.add(new Member('''
			@Override
			protected OverlayFactory<?> _getFactory() {
				return factory;
			}
		'''))
		members.addAll(getBuilderMethods(type))
		return members
	}

	override TypeDeclaration<?> getTypeDeclaration(Type type, String suffix) {
		val decl = new ClassOrInterfaceDeclaration()
		decl.setInterface(false)
		decl.setPublic(true)
		decl.setName(type.getName() + suffix)
		if (type.isEnum) {
			requireTypes(EnumOverlay)
			decl.addExtendedType('''EnumOverlay<«type.name»>''')
		} else {
			decl.addExtendedType(getSuperType(type))
			decl.addImplementedType(type.getName())
		}
		return decl
	}

	def private isEnum(Type type) {
		return !type.enumValues.empty
	}

	def private String getSuperType(Type type) {
		var superType = type.getExtensionOf()
		if (superType === null) {
			requireTypes("PropertiesOverlay")
			return '''PropertiesOverlay<«type.name»>'''
		} else {
			return superType + suffix
		}
	}

	override Members getConstructors(Type type) {
		val members = new Members
		requireTypes(JsonNode, JsonOverlay)
		members.addMember('''
			public «type.implType»(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
				super(json, parent, «IF type.extensionOf === null»factory, «ENDIF»refMgr);
			}
		''')
		members.addMember('''
			public «type.implType»(«type.name» «type.lcName», JsonOverlay<?> parent, ReferenceManager refMgr) {
				super(«type.lcName», parent, «IF type.extensionOf === null»factory, «ENDIF»refMgr);
			}
		''')
		return members
	}

	def private getBuilderMethods(Type type) {
		val members = new Members
		requireTypes(Builder, OverlayFactory, IJsonOverlay)
		val createType = if (isEnum(type)) '''IJsonOverlay<«type.name»>''' else type.name
		members.addMember('''
			public static <OV extends IJsonOverlay<?>> Builder<«type.name»> builder(OV modelMember) {
				return new Builder<«type.name»>(factory, modelMember);
			}
		''')
		members.addMember('''
			public static <OV extends IJsonOverlay<?>> «createType» create(OV modelMember) {
				return «IF !isEnum(type)»(«type.name») «ENDIF»builder(modelMember).build();
			}
		''')
		return members
	}

	override boolean skipField(Field field) {
		return field.noImpl
	}

	override Members getFieldMethods(Field field) {
		val methods = new Members
		var first = true
		switch (field.getStructure()) {
			case scalar:
				for (Member method : getScalarMethods(field)) {
					methods.addMember(method).override().comment(if(first) field.name)
					first = false
				}
			case collection:
				for (Member method : getCollectionMethods(field)) {
					methods.addMember(method).override().comment(if(first) field.name)
					first = false
				}
			case map:
				for (Member method : getMapMethods(field)) {
					methods.addMember(method).override().comment(if(first) field.name)
					first = false
				}
		}
		return methods
	}

	def private Members getScalarMethods(Field f) {
		val methods = new Members
		methods.addMember('''
			public «f.type» get«f.name»() {
				return _get("«f.propertyName»", «f.type».class);
			}
		''')
		if (f.structure == Structure.scalar && !f.isScalarType) {
			methods.addMember('''
				public «f.type» get«f.name»(boolean elaborate) {
					return _get("«f.propertyName»", elaborate, «f.type».class);
				}
			''')
		}
		if (f.isBoolean) {
			methods.addMember('''
				public boolean is«f.name»() {
					Boolean bool = _get("«f.propertyName»", Boolean.class);
					return bool != null ? bool : «f.boolDefault»;
				}
			''')
		}
		methods.addMember('''
			public void set«f.name»(«f.type» «f.lcName») {
				_setScalar("«f.propertyName»", «f.lcName», «f.type».class);
			}
		''')
		return methods
	}

	def private Members getCollectionMethods(Field f) {
		requireTypes(List, ListOverlay)
		val methods = new Members
		methods.addMember('''
			public List<«f.type»> get«f.plural»() {
				return _getList("«f.propertyName»", «f.type».class);
			}
		''')
		methods.addMember('''
			public List<«f.type»> get«f.plural»(boolean elaborate) {
				return _getList("«f.propertyName»", elaborate, «f.type».class);
			}
		''')
		methods.addMember('''
			public boolean has«f.plural»() {
				return _isPresent("«f.propertyName»");
			}
		''')
		methods.addMember('''
			public «f.type» get«f.name»(int index) {
				return _get("«f.propertyName»", index, «f.type».class);
			}
		''')
		methods.addMember('''
			public void set«f.plural»(List<«f.type»> «f.lcPlural») {
				_setList("«f.propertyName»", «f.lcPlural», «f.type».class);
			}
		''')
		methods.addMember('''
			public void set«f.name»(int index, «f.type» «f.lcName») {
				_set("«f.propertyName»", index, «f.lcName», «f.type».class);
			}
		''')
		methods.addMember('''
			public void add«f.name»(«f.type» «f.lcName») {
				_add("«f.propertyName»", «f.lcName», «f.type».class);
			}
		''')
		methods.addMember('''
			public void insert«f.name»(int index, «f.type» «f.lcName») {
				_insert("«f.propertyName»", index, «f.lcName», «f.type».class);
			}
		''')

		methods.addMember('''
			public void remove«f.name»(int index) {
				_remove("«f.propertyName»", index, «f.type».class);
			}
		''')
		return methods
	}

	def private Members getMapMethods(Field f) {
		requireTypes(Map, MapOverlay)
		val methods = new Members
		methods.addMember('''
			public Map<String, «f.type»> get«f.plural»() {
				return _getMap("«f.propertyName»", «f.type».class);
			}
		''')
		methods.addMember('''
			public Map<String, «f.type»> get«f.plural»(boolean elaborate) {
				return _getMap("«f.propertyName»", elaborate, «f.type».class);
			}
		''')

		methods.addMember('''
			public boolean has«f.plural»() {
				return _isPresent("«f.propertyName»");
			}
		''')
		methods.addMember('''
			public boolean has«f.name»(String «f.keyName») {
				return _getMap("«f.propertyName»", «f.type».class).containsKey(«f.keyName»);
			}
		''')
		methods.addMember('''
			public «f.type» get«f.name»(String «f.keyName») {
				return _get("«f.propertyName»", «f.keyName», «f.type».class);
			}
		''')
		methods.addMember('''
			public void set«f.plural»(Map<String, «f.type»> «f.lcPlural») {
				_setMap("«f.propertyName»", «f.lcPlural», «f.type».class);
			}
		''')
		methods.addMember('''
			public void set«f.name»(String «f.keyName», «f.type» «f.lcName») {
				_set("«f.propertyName»", «f.keyName», «f.lcName», «f.type».class);
			}
		''')
		methods.addMember('''
			public void remove«f.name»(String «f.keyName») {
				_remove("«f.propertyName»", «f.keyName», «f.type».class);
			}
		''')
		return methods
	}

	def private Member getElaborateJsonMethod(Type type) {
		return new Member('''
			protected void _elaborateJson() {
				super._elaborateJson();
				«FOR f : type.fields.values.filter[!it.noImpl]»
					«f.elaborateStatement»
				«ENDFOR»
			}
		''').override
	}

	def private String getElaborateStatement(Field f) {
		requireTypes(f.implType)
		return switch (f.structure) {
			case scalar: '''_createScalar("«f.propertyName»", "«f.parentPath»", «f.implType».factory);'''
			case collection: '''_createList("«f.propertyName»", "«f.parentPath»", «f.implType».factory);'''
			case map: {
				val pat = if (f.keyPattern !== null) '''"«f.keyPattern.replaceAll("\\\\","\\\\\\\\")»"''' else "null"
				'''_createMap("«f.propertyName»", "«f.parentPath»", «f.implType».factory, «pat»);'''
			}
		}
	}

	def private Member getEnumFactoryMember(Type type) {
		requireTypes(OverlayFactory, JsonOverlay, JsonNode, ReferenceManager)
		return new Member('''
			public static OverlayFactory<«type.name»> factory = new OverlayFactory<«type.name»>() {
				@Override
				protected Class<? extends JsonOverlay<? super «type.name»>> getOverlayClass() {
					return «type.implType».class;
				}
				
				@Override
				public JsonOverlay<«type.name»> _create(«type.name» «type.lcName», JsonOverlay<?> parent, ReferenceManager refMgr) {
					return new «type.implType»(«type.lcName», parent, refMgr);
				}
				
				@Override
				public JsonOverlay<«type.name»> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
					return new «type.implType»(json, parent, refMgr);
				}			
			};
		''')
	}

	def private Members getFactoryMembers(Type type) {
		val members = new Members
		members.add(getFactoryMember(type))
		members.addAll(getSubtypeMethods(type))
		return members
	}

	def private Member getFactoryMember(Type type) {
		requireTypes(OverlayFactory, JsonNode, ReferenceManager, JsonOverlay)
		return new Member('''
			public static OverlayFactory<«type.name»> factory = new OverlayFactory<«type.name»>(){
				@Override
				protected Class<? extends JsonOverlay<? super «type.name»>> getOverlayClass() {
					return «type.implType».class;
				}
			
				@Override
				public JsonOverlay<«type.name»> _create(«type.name» «type.lcName», JsonOverlay<?> parent, ReferenceManager refMgr) {
					JsonOverlay<?> overlay;
					«IF type.subTypes.empty»
						overlay = new «type.implType»(«type.lcName», parent, refMgr);
					«ELSE»
						Class<? extends «type.name»> subtype = getSubtypeOf(«type.lcName»);
						«getSubtypeCreate(type, type.lcName)»
					«ENDIF»
					@SuppressWarnings("unchecked")
					JsonOverlay<«type.name»> castOverlay = (JsonOverlay<«type.name»>) overlay;
					return castOverlay;
				}
			
				@Override
				public JsonOverlay<«type.name»> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
					JsonOverlay<?> overlay;
					«IF type.subTypes.empty»
						overlay = new «type.implType»(json, parent, refMgr);
					«ELSE»
						Class<? extends «type.name»> subtype = getSubtypeOf(json);
						«getSubtypeCreate(type, ".json")»
					«ENDIF»
					@SuppressWarnings("unchecked")
					JsonOverlay<«type.name»> castOverlay = (JsonOverlay<«type.name»>) overlay;
					return castOverlay;
				}
				
				«getIsExtendedType(!type.subTypes.empty)»
			};	
		''')
	}

	def private Members getSubtypeMethods(Type type) {
		val members = new Members
		val subTypes = type.subTypes
		if (!subTypes.isEmpty() && !type.isAbstract()) {
			subTypes.add(type)
		}
		members.add(getValueSubtypeSelector(type, subTypes))
		members.add(getJsonSubtypeSelector(type, subTypes))
		return members
	}

	def private getIsExtendedType(boolean isExtended) {
		'''
			@Override
			protected boolean isExtendedType() {
				return «if (isExtended) "true" else "false"»;
			}
		'''
	}

	def private Member getValueSubtypeSelector(Type t, Collection<Type> subTypes) {
		val switchExpr = '''«t.lcName».getClass().getSimpleName()'''
		return new Member('''
			private static Class<? extends «t.name»> getSubtypeOf(«t.name» «t.lcName») {
				«t.getSubtypeSwitch(subTypes, switchExpr,[it.name])»
			}
		''')
	}

	def private Member getJsonSubtypeSelector(Type t, Collection<Type> subTypes) {
		requireTypes(JsonPointer, Collectors)
		val switchExpr = '''json.at(JsonPointer.compile("/«t.discriminator»")).asText()'''
		return new Member('''
			private static Class<? extends «t.name»> getSubtypeOf(JsonNode json) {
				«t.getSubtypeSwitch(subTypes, switchExpr, [it.discriminatorValue])»
			}
		''')
	}

	def private String getSubtypeSwitch(Type t, Collection<Type> subTypes, String switchExpr, (Type)=>String discFn) {
		return '''
			«IF subTypes.empty»
				return «t.name».class;
			«ELSE»
				switch(«switchExpr») {
					«FOR sub: subTypes»
						case "«discFn.apply(sub)»":
							return «sub.name».class;
					«ENDFOR»
					default:
						return null;
				}
			«ENDIF»
		'''
	}

	def private String getSubtypeCreate(Type t, String arg0) {
		val subtypes = t.subTypes
		if (subtypes.empty) {
			return '''
				overlay = new «t.implType»(«t.castArg0(arg0)», parent, refMgr);
			'''
		} else {
			return '''
				switch (subtype != null ? subtype.getSimpleName() : "") {
					«FOR sub : subtypes»
						case "«sub.name»":
							overlay = «sub.implType».factory.create(«sub.castArg0(arg0)», parent, refMgr);
							break;
					«ENDFOR»
					default:
						overlay = new «t.implType»(«t.castArg0(arg0)», parent, refMgr);
				}
			'''
		}
	}

	def private castArg0(Type type, String arg0) {
		return if(arg0 == ".json") "json" else '''(«type.name») «arg0»'''
	}

	def private Collection<Type> getSubTypes(Type type) {
		val subTypes = Sets.<Type>newHashSet()
		val todo = Queues.<Type>newArrayDeque()
		todo.add(type)
		while (!todo.isEmpty()) {
			val nextType = todo.remove()
			if (!subTypes.contains(nextType)) {
				subTypes.add(nextType)
				val directSubtypes = type.typeData.types.filter[it.extensionOf == nextType.name]
				todo.addAll(directSubtypes)
			}
		}
		subTypes.remove(type)
		return subTypes
	}
}
