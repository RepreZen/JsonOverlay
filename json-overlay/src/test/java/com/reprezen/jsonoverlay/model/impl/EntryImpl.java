package com.reprezen.jsonoverlay.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.model.intf.Entry;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class EntryImpl extends PropertiesOverlay<Entry> implements Entry {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public EntryImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public EntryImpl(Entry entry, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(entry, parent, factory, refMgr);
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
	public static OverlayFactory<Entry> factory = new OverlayFactory<Entry>() {

		@Override
		protected Class<? extends JsonOverlay<? super Entry>> getOverlayClass() {
			return EntryImpl.class;
		}

		@Override
		public JsonOverlay<Entry> _create(Entry entry, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new EntryImpl(entry, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Entry> castOverlay = (JsonOverlay<Entry>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<Entry> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new EntryImpl(json, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Entry> castOverlay = (JsonOverlay<Entry>) overlay;
			return castOverlay;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Entry> getSubtypeOf(Entry entry) {
		return Entry.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Entry> getSubtypeOf(JsonNode json) {
		return Entry.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}
}
