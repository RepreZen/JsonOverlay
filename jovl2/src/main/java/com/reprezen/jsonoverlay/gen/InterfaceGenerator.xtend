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

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.EnumConstantDeclaration
import com.github.javaparser.ast.body.EnumDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.reprezen.jsonoverlay.gen.TypeData.Field
import com.reprezen.jsonoverlay.gen.TypeData.Structure
import com.reprezen.jsonoverlay.gen.TypeData.Type
import java.io.File
import java.util.List
import java.util.Map

class InterfaceGenerator extends TypeGenerator {

	new(File dir, String intfPackage, String implPackage, String suffix, boolean preserve) {
		super(dir, intfPackage, implPackage, suffix, preserve)
	}

	override getPackage() {
		return intfPackage
	}

	override TypeDeclaration<?> getTypeDeclaration(Type type, String suffix) {
		val decl = if(type.enumValues.empty) new ClassOrInterfaceDeclaration else new EnumDeclaration
		decl.name = type.name
		decl.public = true
		switch (decl) {
			ClassOrInterfaceDeclaration: {
				decl.interface = true
				if (type.superType !== null) {
					decl.addExtendedType(type.superType)
					requireTypes(type.superType)
				}
				if (type.typeData.modelType !== null) {
					requireTypes("IModelPart")
					decl.addExtendedType('''IModelPart<«type.typeData.modelType», «type.name»>''')
				}
				type.extendInterfaces.forEach[requireTypes(it); decl.addExtendedType(it)]
			}
			EnumDeclaration: {
				for (enumValue : type.enumValues) {
					decl.addEntry(new EnumConstantDeclaration().setName(enumValue))
				}
			}
		}
		return decl
	}

	def private getSuperType(Type type) {
		type.extensionOf ?: '''IJsonOverlay<«type.name»>'''
	}

	override getImports(Type type) {
		type.getRequiredImports("intf", "both")
	}

	override getFieldMethods(Field field) {
		val methods = new Members
		requireTypes(field.type)
		var first = true
		switch (field.structure) {
			case scalar: {
				for (method : field.scalarMethods) {
					methods.addMember(method).comment(if(first) field.name)
					first = false
				}
			}
			case collection: {
				for (method : field.collectionMethods) {
					methods.addMember(method).comment(if(first) field.name)
					first = false

				}
			}
			case map: {
				for (method : field.mapMethods) {
					methods.addMember(method).comment(if(first) field.name)
					first = false
				}
			}
		}
		return methods
	}

	def private getScalarMethods(Field f) {
		val methods = new Members
		methods.addMember('''«f.type» get«f.name»();''')
		if (f.structure === Structure.scalar && !f.isScalarType) {
			methods.addMember('''«f.type» get«f.name»(boolean elaborate);''')
		}
		if (f.type == "Boolean") {
			methods.addMember('''boolean is«f.name»();''')
		}
		methods.addMember('''void set«f.name»(«f.type» «f.lcName»);''')
		return methods
	}

	def private getCollectionMethods(Field f) {
		val methods = new Members
		requireTypes(List)
		methods.addMember('''List<«f.type»> get«f.plural»();''')
		methods.addMember('''List<«f.type»> get«f.plural»(boolean elaborate);''')
		methods.addMember('''boolean has«f.plural»();''')
		methods.addMember('''«f.type» get«f.name»(int index);''')
		methods.addMember('''void set«f.plural»(List<«f.type»> «f.lcPlural»);''')
		methods.addMember('''void set«f.name»(int index, «f.type» «f.lcName»);''')
		methods.addMember('''void add«f.name»(«f.type» «f.lcName»);''')
		methods.addMember('''void insert«f.name»(int index, «f.type» «f.lcName»);''')
		methods.addMember('''void remove«f.name»(int index);''')
		return methods
	}

	def private getMapMethods(Field f) {
		requireTypes(Map)
		val methods = new Members
		methods.addMember('''Map<String, «f.type»> get«f.plural»();''')
		methods.addMember('''Map<String, «f.type»> get«f.plural»(boolean elaborate);''')
		methods.addMember('''boolean has«f.plural»();''')
		methods.addMember('''boolean has«f.name»(String «f.keyName»);''')
		methods.addMember('''«f.type» get«f.name»(String «f.keyName»);''')
		methods.addMember('''void set«f.plural»(Map<String, «f.type»> «f.lcPlural»);''')
		methods.addMember('''void set«f.name»(String «f.keyName», «f.type» «f.lcName»);''')
		methods.addMember('''void remove«f.name»(String «f.keyName»);''')
		return methods
	}
}
