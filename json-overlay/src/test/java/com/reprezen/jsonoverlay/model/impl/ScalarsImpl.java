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
package com.reprezen.jsonoverlay.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.BooleanOverlay;
import com.reprezen.jsonoverlay.Builder;
import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IntegerOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.NumberOverlay;
import com.reprezen.jsonoverlay.ObjectOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PrimitiveOverlay;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.model.intf.Color;
import com.reprezen.jsonoverlay.model.intf.Scalars;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class ScalarsImpl extends PropertiesOverlay<Scalars> implements Scalars {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ScalarsImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ScalarsImpl(Scalars scalars, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(scalars, parent, factory, refMgr);
	}

	// StringValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getStringValue() {
		return _get("stringValue", String.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setStringValue(String stringValue) {
		_setScalar("stringValue", stringValue, String.class);
	}

	// IntValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getIntValue() {
		return _get("intValue", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setIntValue(Integer intValue) {
		_setScalar("intValue", intValue, Integer.class);
	}

	// NumberValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Number getNumberValue() {
		return _get("numberValue", Number.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setNumberValue(Number numberValue) {
		_setScalar("numberValue", numberValue, Number.class);
	}

	// BoolValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Boolean getBoolValue() {
		return _get("boolValue", Boolean.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean isBoolValue() {
		Boolean bool = _get("boolValue", Boolean.class);
		return bool != null ? bool : false;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setBoolValue(Boolean boolValue) {
		_setScalar("boolValue", boolValue, Boolean.class);
	}

	// ObjValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Object getObjValue() {
		return _get("objValue", Object.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setObjValue(Object objValue) {
		_setScalar("objValue", objValue, Object.class);
	}

	// PrimValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Object getPrimValue() {
		return _get("primValue", Object.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setPrimValue(Object primValue) {
		_setScalar("primValue", primValue, Object.class);
	}

	// ColorValue
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Color getColorValue() {
		return _get("colorValue", Color.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Color getColorValue(boolean elaborate) {
		return _get("colorValue", elaborate, Color.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setColorValue(Color colorValue) {
		_setScalar("colorValue", colorValue, Color.class);
	}

	// EmbeddedScalars
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Scalars getEmbeddedScalars() {
		return _get("embeddedScalars", Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Scalars getEmbeddedScalars(boolean elaborate) {
		return _get("embeddedScalars", elaborate, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setEmbeddedScalars(Scalars embeddedScalars) {
		_setScalar("embeddedScalars", embeddedScalars, Scalars.class);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_stringValue = "stringValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_intValue = "intValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_numberValue = "numberValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_boolValue = "boolValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_objValue = "objValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_primValue = "primValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_colorValue = "colorValue";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_embeddedScalars = "embeddedScalars";

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void _elaborateJson() {
		super._elaborateJson();
		_createScalar("stringValue", "stringValue", StringOverlay.factory);
		_createScalar("intValue", "intValue", IntegerOverlay.factory);
		_createScalar("numberValue", "numberValue", NumberOverlay.factory);
		_createScalar("boolValue", "boolValue", BooleanOverlay.factory);
		_createScalar("objValue", "objValue", ObjectOverlay.factory);
		_createScalar("primValue", "primValue", PrimitiveOverlay.factory);
		_createScalar("colorValue", "colorValue", ColorImpl.factory);
		_createScalar("embeddedScalars", "embeddedScalars", ScalarsImpl.factory);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<Scalars> factory = new OverlayFactory<Scalars>() {

		@Override
		protected Class<? extends JsonOverlay<? super Scalars>> getOverlayClass() {
			return ScalarsImpl.class;
		}

		@Override
		public JsonOverlay<Scalars> _create(Scalars scalars, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ScalarsImpl(scalars, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Scalars> castOverlay = (JsonOverlay<Scalars>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<Scalars> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ScalarsImpl(json, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Scalars> castOverlay = (JsonOverlay<Scalars>) overlay;
			return castOverlay;
		}

		@Override
		protected boolean isExtendedType() {
			return false;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Scalars> getSubtypeOf(Scalars scalars) {
		return Scalars.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Scalars> getSubtypeOf(JsonNode json) {
		return Scalars.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected OverlayFactory<?> _getFactory() {
		return factory;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> Builder<Scalars> builder(OV modelMember) {
		return new Builder<Scalars>(factory, modelMember);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> Scalars create(OV modelMember) {
		return (Scalars) builder(modelMember).build();
	}
}
