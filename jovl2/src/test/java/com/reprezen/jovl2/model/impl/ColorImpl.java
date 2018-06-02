package com.reprezen.jovl2.model.impl;

import com.reprezen.jovl2.JsonOverlay;
import javax.annotation.Generated;
import com.reprezen.jovl2.OverlayFactory;
import com.reprezen.jovl2.ReferenceManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.EnumOverlay;
import com.reprezen.jovl2.model.intf.*;

public class ColorImpl extends EnumOverlay<Color> {

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public ColorImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
        super(json, parent, factory, refMgr);
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public ColorImpl(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
        super(color, parent, factory, refMgr);
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
        public JsonOverlay<Color> _create(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
            return new ColorImpl(color, parent, refMgr);
        }

        @Override
        public JsonOverlay<Color> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
            return new ColorImpl(json, parent, refMgr);
        }
    };
}
