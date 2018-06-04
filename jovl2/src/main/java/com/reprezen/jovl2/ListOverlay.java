package com.reprezen.jovl2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.reprezen.jovl2.SerializationOptions.Option;

public final class ListOverlay<V> extends JsonOverlay<List<V>> {

	private final OverlayFactory<V> itemFactory;
	private List<JsonOverlay<V>> overlays = Lists.newArrayList();

	private ListOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<List<V>> factory,
			ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
		this.itemFactory = ((ListOverlayFactory<V>) factory).getItemFactory();
	}

	private ListOverlay(List<V> value, JsonOverlay<?> parent, OverlayFactory<List<V>> factory,
			ReferenceManager refMgr) {
		super(Lists.newArrayList(value), parent, factory, refMgr);
		this.itemFactory = ((ListOverlayFactory<V>) factory).getItemFactory();
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
		for (JsonOverlay<V> item : overlays) {
			// we can't have missing children, since they'd screw up the position of other
			// entries. So we set an option for each of chilren ensuring that they will
			// return empty containers. This only affects list, map, and properties
			// overlays. Map and properties overlays remove the keep-one option when
			// serializing their children.
			array.add(item._toJson(options.plus(Option.KEEP_ONE_EMPTY)));
		}
		return array.size() > 0 || options.isKeepThisEmpty() ? array : _jsonMissing();
	}

	@Override
	protected void _elaborate(boolean atCreation) {
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
			JsonOverlay<V> itemOverlay = itemFactory.create(iter.next(), this, refMgr);
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
		return itemFactory.create(itemValue, this, refMgr);
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ListOverlay<?>) {
			ListOverlay<?> castObj = (ListOverlay<?>) obj;
			return overlays.equals(castObj.overlays);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
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
		public String getSignature() {
			return String.format("list[%s]", itemFactory.getSignature());
		}

		@Override
		protected Class<? extends JsonOverlay<? super List<V>>> getOverlayClass() {
			Class<?> overlayClass = ListOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<List<V>>> castClass = (Class<? extends JsonOverlay<List<V>>>) overlayClass;
			return castClass;
		}

		@Override
		protected JsonOverlay<List<V>> _create(List<V> value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ListOverlay<V>(value, parent, this, refMgr);
		}

		@Override
		protected JsonOverlay<List<V>> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ListOverlay<V>(json, parent, this, refMgr);
		}

		public OverlayFactory<V> getItemFactory() {
			return itemFactory;
		}
	}
}
