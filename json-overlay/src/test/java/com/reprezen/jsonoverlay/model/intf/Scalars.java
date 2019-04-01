/*********************************************************************
*  Copyright (c) 2017 ModelSolv, Inc. and others.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     ModelSolv, Inc. 
 *     - initial API and implementation and/or initial documentation
**********************************************************************/
package com.reprezen.jsonoverlay.model.intf;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface Scalars extends IJsonOverlay<Scalars>, IModelPart<TestModel, Scalars> {

	// StringValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getStringValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setStringValue(String stringValue);

	// IntValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getIntValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setIntValue(Integer intValue);

	// NumberValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Number getNumberValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setNumberValue(Number numberValue);

	// BoolValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Boolean getBoolValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean isBoolValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setBoolValue(Boolean boolValue);

	// ObjValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Object getObjValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setObjValue(Object objValue);

	// PrimValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Object getPrimValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setPrimValue(Object primValue);

	// ColorValue
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Color getColorValue();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Color getColorValue(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setColorValue(Color colorValue);

	// EmbeddedScalars
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Scalars getEmbeddedScalars();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Scalars getEmbeddedScalars(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setEmbeddedScalars(Scalars embeddedScalars);
}
