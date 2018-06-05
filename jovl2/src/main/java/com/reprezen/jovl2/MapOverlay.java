package com.reprezen.jovl2;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.reprezen.jovl2.SerializationOptions.Option;

public final class MapOverlay<V> extends JsonOverlay<Map<String, V>> {

	private final OverlayFactory<V> valueFactory;
	private final Pattern keyPattern;
	private Map<String, JsonOverlay<V>> overlays = Maps.newLinkedHashMap();

	private MapOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<Map<String, V>> factory,
			ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
		MapOverlayFactory<V> mapOverlayFactory = (MapOverlayFactory<V>) factory;
		this.valueFactory = mapOverlayFactory.getValueFactory();
		String keyPattern = mapOverlayFactory.getKeyPattern();
		this.keyPattern = keyPattern != null ? Pattern.compile(keyPattern) : null;
	}

	private MapOverlay(Map<String, V> value, JsonOverlay<?> parent, OverlayFactory<Map<String, V>> factory,
			ReferenceManager refMgr) {
		super(Maps.newLinkedHashMap(value), parent, factory, refMgr);
		MapOverlayFactory<V> mapOverlayFactory = (MapOverlayFactory<V>) factory;
		this.valueFactory = mapOverlayFactory.getValueFactory();
		String keyPattern = mapOverlayFactory.getKeyPattern();
		this.keyPattern = keyPattern != null ? Pattern.compile(keyPattern) : null;
	}

	@Override
	protected JsonOverlay<?> _findInternal(JsonPointer path) {
		String key = path.getMatchingProperty();
		return overlays.containsKey(key) ? overlays.get(key)._find(path.tail()) : null;
	}

	@Override
	protected Map<String, V> _fromJson(JsonNode json) {
		return new LinkedHashMap<String, V>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public MapOverlay<V> getOverlay() {
				return MapOverlay.this;
			}
		};
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		ObjectNode obj = _jsonObject();
		for (Entry<String, JsonOverlay<V>> entry : overlays.entrySet()) {
			obj.set(entry.getKey(), entry.getValue()._toJson(options.minus(Option.KEEP_ONE_EMPTY)));
		}
		return obj.size() > 0 || options.isKeepThisEmpty() ? obj : _jsonMissing();
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
		for (Iterator<Entry<String, JsonNode>> iter = json.fields(); iter.hasNext();) {
			Entry<String, JsonNode> entry = iter.next();
			if (keyPattern == null || keyPattern.matcher(entry.getKey()).matches()) {
				JsonOverlay<V> valOverlay = valueFactory.create(entry.getValue(), this, refMgr);
				overlays.put(entry.getKey(), valOverlay);
				valOverlay._setPathInParent(entry.getKey());
				value.put(entry.getKey(), valOverlay._get(false));
			}
		}
	}

	private void fillWithValues() {
		overlays.clear();
		for (Entry<String, V> entry : value.entrySet()) {
			JsonOverlay<V> valOverlay = valueOverlayFor(entry.getValue());
			overlays.put(entry.getKey(), valOverlay);
			valOverlay._setPathInParent(entry.getKey());
		}
	}

	private JsonOverlay<V> valueOverlayFor(V val) {
		return valueFactory.create(val, this, refMgr);
	}

	public V get(String key) {
		return overlays.get(key)._get();
	}

	/* package */ JsonOverlay<V> _getOverlay(String key) {
		return overlays.get(key);
	}

	public void set(String key, V val) {
		value.put(key, val);
		overlays.put(key, valueOverlayFor(val));
	}

	public void remove(String key) {
		value.remove(key);
		overlays.remove(key);
	}

	public int size() {
		return overlays.size();
	}

	@Override
	public boolean equals(Object obj) {
		return equals(obj, false);
	}

	public boolean equals(Object obj, boolean sameOrder) {
		if (obj instanceof MapOverlay<?>) {
			MapOverlay<?> castObj = (MapOverlay<?>) obj;
			return overlays.equals(castObj.overlays) && (!sameOrder || checkOrder(castObj));
		}
		return false;
	}

	private boolean checkOrder(MapOverlay<?> other) {
		Iterator<String> myKeys = overlays.keySet().iterator();
		Iterator<String> theirKeys = other.overlays.keySet().iterator();
		while (myKeys.hasNext() && theirKeys.hasNext()) {
			if (!myKeys.next().equals(theirKeys.next())) {
				return false;
			}
		}
		return !myKeys.hasNext() && !theirKeys.hasNext();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public static <V> OverlayFactory<Map<String, V>> getFactory(OverlayFactory<V> valueFactory, String keyPattern) {
		return new MapOverlayFactory<V>(valueFactory, keyPattern);
	}

	private static class MapOverlayFactory<V> extends OverlayFactory<Map<String, V>> {
		private final OverlayFactory<V> valueFactory;
		private final String keyPattern;

		public MapOverlayFactory(OverlayFactory<V> valueFactory, String keyPattern) {
			this.valueFactory = valueFactory;
			this.keyPattern = keyPattern;
		}

		@Override
		public String getSignature() {
			return String.format("map[%s|%s]", valueFactory.getSignature(), keyPattern != null ? keyPattern : "*");
		}

		@Override
		protected Class<? extends JsonOverlay<? super Map<String, V>>> getOverlayClass() {
			Class<?> overlayClass = MapOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<Map<String, V>>> castClass = (Class<? extends JsonOverlay<Map<String, V>>>) overlayClass;
			return castClass;
		}

		@Override
		protected JsonOverlay<Map<String, V>> _create(Map<String, V> value, JsonOverlay<?> parent,
				ReferenceManager refMgr) {
			return new MapOverlay<V>(value, parent, this, refMgr);
		}

		@Override
		protected JsonOverlay<Map<String, V>> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new MapOverlay<V>(json, parent, this, refMgr);
		}

		public String getKeyPattern() {
			return keyPattern;
		}

		public OverlayFactory<V> getValueFactory() {
			return valueFactory;
		}
	}
}