package com.reprezen.jovl2.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.EnumOverlay;
import com.reprezen.jovl2.JsonOverlay;
import com.reprezen.jovl2.OverlayFactory;
import com.reprezen.jovl2.ReferenceRegistry;
import com.reprezen.jovl2.model.intf.Color;

public class ColorImpl extends EnumOverlay<Color> {

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public ColorImpl(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, factory, refReg);
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public ColorImpl(Color color, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(color, parent, factory, refReg);
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	protected Class<Color> getEnumClass() {
		return Color.class;
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public static OverlayFactory<Color> factory = new OverlayFactory<Color>() {

		@Override
		protected Class<? extends JsonOverlay<? super Color>> getOverlayClass() {
			return ColorImpl.class;
		}

		@Override
		public JsonOverlay<Color> _create(Color color, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ColorImpl(color, parent, refReg);
		}

		@Override
		public JsonOverlay<Color> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ColorImpl(json, parent, refReg);
		}
	};
}
