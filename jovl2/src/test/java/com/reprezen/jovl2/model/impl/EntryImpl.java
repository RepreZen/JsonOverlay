package com.reprezen.jovl2.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.JsonOverlay;
import com.reprezen.jovl2.OverlayFactory;
import com.reprezen.jovl2.PropertiesOverlay;
import com.reprezen.jovl2.ReferenceManager;
import com.reprezen.jovl2.StringOverlay;
import com.reprezen.jovl2.model.intf.Entry;
import com.reprezen.jovl2.model.intf.TestModel;

public class EntryImpl extends PropertiesOverlay<Entry> implements Entry {

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public EntryImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public EntryImpl(Entry entry, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(entry, parent, factory, refMgr);
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

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	private static Class<? extends Entry> getSubtypeOf(Entry entry) {
		return Entry.class;
	}

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	private static Class<? extends Entry> getSubtypeOf(JsonNode json) {
		return Entry.class;
	}

	@Override
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}
}
