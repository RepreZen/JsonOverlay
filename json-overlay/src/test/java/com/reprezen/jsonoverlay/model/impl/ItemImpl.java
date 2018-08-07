package com.reprezen.jsonoverlay.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.model.intf.Item;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class ItemImpl extends PropertiesOverlay<Item> implements Item {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ItemImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ItemImpl(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(item, parent, factory, refMgr);
	}

	// Title
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getTitle() {
		return _get("title", String.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setTitle(String title) {
		_setScalar("title", title, String.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void _elaborateJson() {
		super._elaborateJson();
		_createScalar("title", "title", StringOverlay.factory);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<Item> factory = new OverlayFactory<Item>() {

		@Override
		protected Class<? extends JsonOverlay<? super Item>> getOverlayClass() {
			return ItemImpl.class;
		}

		@Override
		public JsonOverlay<Item> _create(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ItemImpl(item, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<Item> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ItemImpl(json, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
			return castOverlay;
		}

		@Override
		protected boolean isExtendedType() {
			return false;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Item> getSubtypeOf(Item item) {
		return Item.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Item> getSubtypeOf(JsonNode json) {
		return Item.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}
}
