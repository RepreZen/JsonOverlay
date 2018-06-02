package com.reprezen.jovl2.model.impl;

import com.reprezen.jovl2.JsonOverlay;
import javax.annotation.Generated;
import com.reprezen.jovl2.OverlayFactory;
import com.fasterxml.jackson.core.JsonPointer;
import com.reprezen.jovl2.PropertiesOverlay;
import com.reprezen.jovl2.ReferenceManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.StringOverlay;
import java.util.stream.Collectors;
import com.reprezen.jovl2.model.intf.*;

public class ItemImpl extends PropertiesOverlay<Item> implements Item {

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public ItemImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
        super(json, parent, factory, refMgr);
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public ItemImpl(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
        super(item, parent, factory, refMgr);
    }

    // Title
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public String getTitle() {
        return (String) _get("title", String.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setTitle(String title) {
        _set("title", title, String.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    protected void _elaborateJson() {
        _createScalar("title", "title", StringOverlay.factory);
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public static OverlayFactory<Item> factory = new OverlayFactory<Item>() {

        @Override
        protected Class<? extends JsonOverlay<? super Item>> getOverlayClass() {
            return ItemImpl.class;
        }

        @Override
        public JsonOverlay<Item> _create(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
            JsonOverlay<?> overlay;
            overlay = new ItemImpl(item, parent, refMgr);
            @SuppressWarnings("unchecked") JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
            return castOverlay;
        }

        @Override
        public JsonOverlay<Item> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
            JsonOverlay<?> overlay;
            overlay = new ItemImpl(json, parent, refMgr);
            @SuppressWarnings("unchecked") JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
            return castOverlay;
        }
    };

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    private static Class<? extends Item> getSubtypeOf(Item item) {
        return Item.class;
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    private static Class<? extends Item> getSubtypeOf(JsonNode json) {
        return Item.class;
    }
}
