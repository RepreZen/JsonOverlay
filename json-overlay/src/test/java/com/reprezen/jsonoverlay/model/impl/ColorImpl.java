package com.reprezen.jsonoverlay.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.EnumOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.model.intf.Color;

public class ColorImpl extends EnumOverlay<Color> {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ColorImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ColorImpl(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(color, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected Class<Color> getEnumClass() {
		return Color.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<Color> factory = new OverlayFactory<Color>() {

		@Override
		protected Class<? extends JsonOverlay<? super Color>> getOverlayClass() {
			return ColorImpl.class;
		}

		@Override
		public JsonOverlay<Color> _create(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ColorImpl(color, parent, refMgr);
		}

		@Override
		public JsonOverlay<Color> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ColorImpl(json, parent, refMgr);
		}
	};

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected OverlayFactory<?> _getFactory() {
		return factory;
	}
}
