package com.reprezen.jovl2.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.BooleanOverlay;
import com.reprezen.jovl2.IntegerOverlay;
import com.reprezen.jovl2.JsonOverlay;
import com.reprezen.jovl2.NumberOverlay;
import com.reprezen.jovl2.ObjectOverlay;
import com.reprezen.jovl2.OverlayFactory;
import com.reprezen.jovl2.PrimitiveOverlay;
import com.reprezen.jovl2.PropertiesOverlay;
import com.reprezen.jovl2.ReferenceManager;
import com.reprezen.jovl2.StringOverlay;
import com.reprezen.jovl2.model.intf.Color;
import com.reprezen.jovl2.model.intf.Scalars;
import com.reprezen.jovl2.model.intf.TestModel;

public class ScalarsImpl extends PropertiesOverlay<Scalars> implements Scalars {

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public ScalarsImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public ScalarsImpl(Scalars scalars, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(scalars, parent, factory, refMgr);
	}

	// StringValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public String getStringValue() {
		return (String) _get("stringValue", String.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setStringValue(String stringValue) {
		_set("stringValue", stringValue, String.class);
	}

	// IntValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Integer getIntValue() {
		return (Integer) _get("intValue", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setIntValue(Integer intValue) {
		_set("intValue", intValue, Integer.class);
	}

	// NumberValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Number getNumberValue() {
		return (Number) _get("numberValue", Number.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setNumberValue(Number numberValue) {
		_set("numberValue", numberValue, Number.class);
	}

	// BoolValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Boolean getBoolValue() {
		return (Boolean) _get("boolValue", Boolean.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public boolean isBoolValue() {
		Boolean bool = _get("boolValue", Boolean.class);
		return bool != null ? bool : false;
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setBoolValue(Boolean boolValue) {
		_set("boolValue", boolValue, Boolean.class);
	}

	// ObjValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Object getObjValue() {
		return (Object) _get("objValue", Object.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setObjValue(Object objValue) {
		_set("objValue", objValue, Object.class);
	}

	// PrimValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Object getPrimValue() {
		return (Object) _get("primValue", Object.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setPrimValue(Object primValue) {
		_set("primValue", primValue, Object.class);
	}

	// ColorValue
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Color getColorValue() {
		return (Color) _get("colorValue", Color.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Color getColorValue(boolean elaborate) {
		return (Color) _get("colorValue", elaborate, Color.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setColorValue(Color colorValue) {
		_set("colorValue", colorValue, Color.class);
	}

	// EmbeddedScalars
	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Scalars getEmbeddedScalars() {
		return (Scalars) _get("embeddedScalars", Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Scalars getEmbeddedScalars(boolean elaborate) {
		return (Scalars) _get("embeddedScalars", elaborate, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public void setEmbeddedScalars(Scalars embeddedScalars) {
		_set("embeddedScalars", embeddedScalars, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	protected void _elaborateJson() {
		_createScalar("stringValue", "stringValue", StringOverlay.factory);
		_createScalar("intValue", "intValue", IntegerOverlay.factory);
		_createScalar("numberValue", "numberValue", NumberOverlay.factory);
		_createScalar("boolValue", "boolValue", BooleanOverlay.factory);
		_createScalar("objValue", "objValue", ObjectOverlay.factory);
		_createScalar("primValue", "primValue", PrimitiveOverlay.factory);
		_createScalar("colorValue", "colorValue", ColorImpl.factory);
		_createScalar("embeddedScalars", "embeddedScalars", ScalarsImpl.factory);
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
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
	};

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	private static Class<? extends Scalars> getSubtypeOf(Scalars scalars) {
		return Scalars.class;
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	private static Class<? extends Scalars> getSubtypeOf(JsonNode json) {
		return Scalars.class;
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}
}
