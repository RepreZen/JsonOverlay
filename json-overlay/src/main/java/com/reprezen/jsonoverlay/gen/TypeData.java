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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeData {

	private Collection<Type> types;
	private Map<String, String> imports = new HashMap<>();
	private List<String> defaultExtendInterfaces = null;
	private Map<String, Type> typeMap = null;
	private String modelType = null;
	private String discriminator = null;

	// Container for "decls" section that is solely used to define reusable
	// anchors
	@JsonProperty
	private Object decls;

	public void init() {
		typeMap = types.stream().collect(Collectors.toMap(Type::getName, t -> t));
		types.stream().forEach(t -> t.init(this));
	}

	public String getModelType() {
		return modelType;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public Collection<Type> getTypes() {
		return types;
	}

	public Map<String, Type> getTypeMap() {
		return typeMap;
	}

	public Type getType(String typeName) {
		return typeMap.get(typeName);
	}

	public Map<String, String> getImports() {
		return imports;
	}

	public List<String> getDefaultExtendInterfaces() {
		return defaultExtendInterfaces;
	}

	public static class Type {

		private String name;
		private Map<String, Field> fields = new LinkedHashMap<>();
		private List<String> extendInterfaces = new ArrayList<>();
		private Map<String, Collection<String>> imports = new HashMap<>();
		private boolean noGen = false;
		private String extensionOf;
		private Map<String, String> renames = new HashMap<>();
		@JsonProperty("abstract")
		private boolean abstractType = false;
		private String discriminator = null;
		private String discriminatorValue = null;
		private List<String> enumValues = new ArrayList<>();

		private TypeData typeData;

		public void init(TypeData typeData) {
			this.typeData = typeData;
			for (Entry<String, Field> field : fields.entrySet()) {
				field.getValue().init(field.getKey(), this);
			}
		}

		public TypeData getTypeData() {
			return typeData;
		}

		public Collection<String> getRequiredImports(String... moduleTypes) {
			Set<String> results = new LinkedHashSet<>();
			Collection<String> interfaces = extendInterfaces != null ? extendInterfaces
					: typeData.defaultExtendInterfaces;
			if (interfaces != null) {
				results.addAll(interfaces);
			}
			for (String moduleType : moduleTypes) {
				if (imports.get(moduleType) != null) {
					results.addAll(imports.get(moduleType));
				}
			}
			return results;
		}

		public String getIntfExtendsDecl() {
			List<String> interfaces = extendInterfaces != null ? extendInterfaces : typeData.defaultExtendInterfaces;
			return interfaces != null ? " extends " + interfaces.stream().collect(Collectors.joining(",")) : "";
		}

		public String getName() {
			return name;
		}

		public String getLcName() {
			String lcName = lcFirst(name);
			return lcName;
		}

		public Map<String, Field> getFields() {
			return fields;
		}

		public List<String> getExtendInterfaces() {
			return extendInterfaces;
		}

		public Map<String, Collection<String>> getImports() {
			return imports;
		}

		public boolean isNoGen() {
			return noGen;
		}

		public String getExtensionOf() {
			return extensionOf;
		}

		public Map<String, String> getRenames() {
			return renames;
		}

		public boolean isAbstract() {
			return abstractType;
		}

		public String getDiscriminator() {
			return discriminator != null ? discriminator : typeData.getDiscriminator();
		}

		public String getDiscriminatorValue() {
			return discriminatorValue != null ? discriminatorValue : name;
		}

		public List<String> getEnumValues() {
			return enumValues;
		}

		public String getImplType() {
			return isNoGen() ? name : getImplType(name);
		}

		public static String getImplType(String typeName) {
			switch (typeName) {
			case "String":
			case "Integer":
			case "Number":
			case "Boolean":
			case "Primitive":
			case "Object":
				return typeName + "Overlay";
			default:
				return typeName + "Impl";
			}
		}

		String lcFirst(String s) {
			return s.substring(0, 1).toLowerCase() + s.substring(1);
		}
	}

	public static class Field {
		private String name;
		private String plural;
		private Structure structure = Structure.scalar;
		private String type;
		private String keyName = "name";
		private String keyPattern;
		private boolean noImpl;
		private String id;
		private boolean boolDefault = false;
		private String parentPath;

		private Type container;

		public void init(String id, Type container) {
			this.id = id;
			this.container = container;
			if (this.name == null) {
				String[] parts = id.split("/");
				String lastPart = parts[parts.length - 1];
				String defaultName = lastPart.substring(0, 1).toUpperCase() + lastPart.substring(1);
				if (this.structure == Structure.scalar) {
					this.name = defaultName;
				} else {
					this.name = defaultName.endsWith("s") ? defaultName.substring(0, defaultName.length() - 1)
							: defaultName;
				}
			}
			if (this.type == null) {
				this.type = getTypeData().getType(name) != null ? name : "String";
			}
		}

		public String getId() {
			return id;
		}

		public Type getContainer() {
			return container;
		}

		public TypeData getTypeData() {
			return container.getTypeData();
		}

		public String getName() {
			return name;
		}

		public String getLcName() {
			String lcName = lcFirst(name);
			switch (lcName) {
			case "default":
			case "enum":
				lcName = lcName + "Value";
			}
			return lcName;
		}

		public String getPlural() {
			return plural != null ? plural : name + "s";
		}

		public String getLcPlural() {
			return lcFirst(getPlural());
		}

		public Structure getStructure() {
			return structure;
		}

		public String getType() {
			return type.equals("Primitive") ? "Object" : type;
		}

		String lcFirst(String s) {
			return s.substring(0, 1).toLowerCase() + s.substring(1);
		}

		public String getKeyName() {
			return keyName;
		}

		public String getKeyPattern() {
			return keyPattern;
		}

		public boolean isNoImpl() {
			return noImpl;
		}

		public boolean isBoolean() {
			return getType().equals("Boolean");
		}

		public boolean getBoolDefault() {
			return boolDefault;
		}

		public String getParentPath() {
			return parentPath != null ? parentPath : id;
		}

		public String getImplType() {
			Type objectType = getContainer().getTypeData().getTypeMap().get(getType());
			return Type.getImplType(objectType != null ? objectType.getName() : type);
		}

		public boolean isScalarType() {
			switch (getType()) {
			case "String":
			case "Integer":
			case "Number":
			case "Boolean":
			case "Primitive":
			case "Object":
				return true;
			default:
				return false;
			}
		}

		public String getPropertyName() {
			return structure == Structure.scalar ? getLcName() : getLcPlural();
		}

		public String getOverlayType() {
			return getType() + (isScalarType() ? "Overlay" : "");
		}
	}

	public static class Method {
		private String name;
		private String type;
		private List<String> argDecls;
		private List<String> code;

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public List<String> getArgDecls() {
			return argDecls;
		}

		public List<String> getCode() {
			return code;
		}
	}

	public enum Structure {
		scalar, collection, map
	}
}
