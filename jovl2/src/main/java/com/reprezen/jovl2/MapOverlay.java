package com.reprezen.jovl2;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

public class MapOverlay<V> extends JsonOverlay<Map<String, V>> {

	private final OverlayFactory<V> valueFactory;
	private final Pattern keyPattern;
	private Map<String, JsonOverlay<V>> overlays = Maps.newLinkedHashMap();

	private MapOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> valueFactory, String keyPattern,
			ReferenceRegistry refReg) {
		super(json, parent, refReg);
		this.valueFactory = valueFactory;
		this.keyPattern = keyPattern != null ? Pattern.compile(keyPattern) : null;
	}

	private MapOverlay(Map<String, V> value, JsonOverlay<?> parent, OverlayFactory<V> valueFactory,
			ReferenceRegistry refReg) {
		super(Maps.newLinkedHashMap(value), parent, refReg);
		this.valueFactory = valueFactory;
		this.keyPattern = null;
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
			obj.set(entry.getKey(), entry.getValue()._toJson());
		}
		return obj.size() > 0 ? obj : MissingNode.getInstance();
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
		for (Iterator<Entry<String, JsonNode>> iter = json.fields(); iter.hasNext();) {
			Entry<String, JsonNode> entry = iter.next();
			if (keyPattern == null || keyPattern.matcher(entry.getKey()).matches()) {
				JsonOverlay<V> valOverlay = valueFactory.create(entry.getValue(), this, refReg);
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
		return valueFactory.create(val, this, refReg);
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
		protected Class<? extends JsonOverlay<? super Map<String, V>>> getOverlayClass() {
			Class<?> overlayClass = MapOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<Map<String, V>>> castClass = (Class<? extends JsonOverlay<Map<String, V>>>) overlayClass;
			return castClass;
		}

		@Override
		protected JsonOverlay<Map<String, V>> _create(Map<String, V> value, JsonOverlay<?> parent,
				ReferenceRegistry refReg) {
			return new MapOverlay<V>(value, parent, valueFactory, refReg);
		}

		@Override
		protected JsonOverlay<Map<String, V>> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new MapOverlay<V>(json, parent, valueFactory, keyPattern, refReg);
		}
	}
}