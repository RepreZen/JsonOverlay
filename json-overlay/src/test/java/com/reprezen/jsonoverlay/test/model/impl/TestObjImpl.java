package com.reprezen.jsonoverlay.test.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.AbstractJsonOverlay;
import com.reprezen.jsonoverlay.ChildOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceRegistry;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.test.model.intf.TestObj;

public class TestObjImpl extends PropertiesOverlay<TestObj> implements TestObj {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObjImpl(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObjImpl(TestObj testObj, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(testObj, parent, refReg);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<String> x;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<String> y;

	// X
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getX() {
		return x._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setX(String x) {
		this.x._set(x);
	}

	// Y
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getY() {
		return y._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setY(String y) {
		this.y._set(y);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void elaborateChildren() {
		super.elaborateChildren();
		x = createChild("x", this, StringOverlay.factory);
		y = createChild("y", this, StringOverlay.factory);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<TestObj> factory = new OverlayFactory<TestObj>() {

		@Override
		protected Class<? extends AbstractJsonOverlay<? super TestObj>> getOverlayClass() {
			return TestObjImpl.class;
		}

		@Override
		public JsonOverlay<TestObj> _create(TestObj testObj, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			AbstractJsonOverlay<?> overlay;
			overlay = new TestObjImpl(testObj, parent, refReg);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestObj> castOverlay = (JsonOverlay<TestObj>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<TestObj> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			AbstractJsonOverlay<?> overlay;
			overlay = new TestObjImpl(json, parent, refReg);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestObj> castOverlay = (JsonOverlay<TestObj>) overlay;
			return castOverlay;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends TestObj> getSubtypeOf(TestObj testObj) {
		return TestObj.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends TestObj> getSubtypeOf(JsonNode json) {
		return TestObj.class;
	}
}
