package com.reprezen.jovl2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.google.common.collect.Lists;

public class ListOverlay<V> extends JsonOverlay<List<V>> {

	private final OverlayFactory<V> itemFactory;
	private List<JsonOverlay<V>> overlays = Lists.newArrayList();

	private ListOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> itemFactory, ReferenceRegistry refReg) {
		super(json, parent, refReg);
		this.itemFactory = itemFactory;
	}

	private ListOverlay(List<V> value, JsonOverlay<?> parent, OverlayFactory<V> itemFactory, ReferenceRegistry refReg) {
		super(Lists.newArrayList(value), parent, refReg);
		this.itemFactory = itemFactory;
	}

	@Override
	protected List<V> _fromJson(JsonNode json) {
		return new ArrayList<V>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public ListOverlay<V> getOverlay() {
				return ListOverlay.this;
			}
		};
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		ArrayNode array = _jsonArray();
		for (JsonOverlay<V> itemOverlay : overlays) {
			array.add(itemOverlay._toJson());
		}
		return array.size() > 0 ? array : MissingNode.getInstance();
	}

	@Override
	protected void _elaborate() {
		if (json != null) {
			fillWithJson();
		} else {
			fillWithValues();
		}
	}

	private void fillWithJson() {
		value.clear();
		overlays.clear();
		for (Iterator<JsonNode> iter = json.elements(); iter.hasNext();) {
			JsonOverlay<V> itemOverlay = itemFactory.create(iter.next(), this, refReg);
			overlays.add(itemOverlay);
			value.add(itemOverlay._get(false));
		}
	}

	private void fillWithValues() {
		overlays.clear();
		for (V itemValue : value) {
			overlays.add(itemOverlayFor(itemValue));
		}
	}

	private JsonOverlay<V> itemOverlayFor(V itemValue) {
		return itemFactory.create(itemValue, this, refReg);
	}

	public V get(int index) {
		return overlays.get(index)._get();
	}

	/* package */ JsonOverlay<V> _getOverlay(int index) {
		return overlays.get(index);
	}

	public void set(int index, V itemValue) {
		value.set(index, itemValue);
		overlays.set(index, itemOverlayFor(itemValue));
	}

	public void add(V itemValue) {
		value.add(itemValue);
		overlays.add(itemOverlayFor(itemValue));
	}

	public void insert(int index, V itemValue) {
		value.add(index, itemValue);
		overlays.add(index, itemOverlayFor(itemValue));
	}

	public void remove(int index) {
		value.remove(index);
		overlays.remove(index);
	}

	public int size() {
		return overlays.size();
	}

	public static <V> OverlayFactory<List<V>> getFactory(OverlayFactory<V> itemFactory) {
		return new ListOverlayFactory<V>(itemFactory);
	}

	private static class ListOverlayFactory<V> extends OverlayFactory<List<V>> {

		private final OverlayFactory<V> itemFactory;

		public ListOverlayFactory(OverlayFactory<V> itemFactory) {
			this.itemFactory = itemFactory;
		}

		@Override
		protected Class<? extends JsonOverlay<? super List<V>>> getOverlayClass() {
			Class<?> overlayClass = ListOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<List<V>>> castClass = (Class<? extends JsonOverlay<List<V>>>) overlayClass;
			return castClass;
		}

		@Override
		protected JsonOverlay<List<V>> _create(List<V> value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ListOverlay<V>(value, parent, itemFactory, refReg);
		}

		@Override
		protected JsonOverlay<List<V>> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ListOverlay<V>(json, parent, itemFactory, refReg);
		}
	}
}
