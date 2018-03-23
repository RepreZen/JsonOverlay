/*******************************************************************************
 *  Copyright (c) 2017 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.jsonoverlay;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.reprezen.jsonoverlay.SerializationOptions.Option;

public class MapOverlay<V> extends JsonOverlay<Map<String, V>> {
	private Map<String, AbstractJsonOverlay<V>> overlays = Maps.newLinkedHashMap();
	private OverlayFactory<V> valueFactory;
	private Pattern keyPattern;

	public MapOverlay(Map<String, V> value, JsonOverlay<?> parent, OverlayFactory<V> valueFactory, Pattern keyPattern,
			ReferenceRegistry refReg) {
		super(value, parent, refReg);
		this.valueFactory = valueFactory;
		this.keyPattern = keyPattern;
	}

	public MapOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> valueFactory, Pattern keyPattern,
			ReferenceRegistry refReg) {
		super(json, parent, refReg);
		this.valueFactory = valueFactory;
		this.keyPattern = keyPattern;
	}

	@Override
	protected void elaborate() {
		if (json != null) {
			fillWithJson();
		} else {
			fillWithValues();
		}
	}

	private void fillWithValues() {
		overlays.clear();
		if (value != null) {
			for (Entry<String, V> entry : value.entrySet()) {
				overlays.put(entry.getKey(),
						new ChildOverlay<>(entry.getKey(), entry.getValue(), this, valueFactory, refReg));
			}
		}
	}

	private void fillWithJson() {
		value.clear();
		overlays.clear();
		if (!json.isMissingNode()) {
			for (Entry<String, JsonNode> field : iterable(json.fields())) {
				String key = field.getKey();
				if (keyPattern == null || keyPattern.matcher(key).matches()) {
					ChildOverlay<V> overlay = new ChildOverlay<V>(key, json.get(key), this, valueFactory, refReg);
					overlay.getOverlay().setPathInParent(key);
					overlays.put(key, overlay);
					value.put(key, overlay._get(false));
				}
			}
		}
	}

	@Override
	public Map<String, V> _get() {
		return wrap(value);
	}

	private Map<String, V> wrap(Map<String, V> map) {
		return map instanceof WrappedMap ? map : new WrappedMap<V>(map, this);
	}

	/* package */ AbstractJsonOverlay<V> _get(String key) {
		return overlays.get(key);
	}

	@Override
	public AbstractJsonOverlay<?> _findInternal(JsonPointer path) {
		String key = path.getMatchingProperty();
		return overlays.containsKey(key) ? overlays.get(key)._find(path.tail()) : null;
	}

	@Override
	protected Map<String, V> fromJson(JsonNode json) {
		return Maps.newLinkedHashMap();
	}

	@Override
	public JsonNode _toJsonInternal(SerializationOptions options) {
		ObjectNode obj = jsonObject();
		for (Entry<String, AbstractJsonOverlay<V>> entry : overlays.entrySet()) {
			obj.set(entry.getKey(), entry.getValue()._toJson(options.plus(Option.KEEP_ONE_EMPTY)));
		}
		return obj.size() > 0 || options.isKeepThisEmpty() ? obj : jsonMissing();
	}

	public boolean containsKey(String name) {
		return overlays.containsKey(name);
	}

	public V get(String name) {
		AbstractJsonOverlay<V> overlay = overlays.get(name);
		return overlay != null ? overlay._get() : null;
	}

	protected AbstractJsonOverlay<V> getOverlay(String name) {
		return overlays.get(name);
	}

	public void set(String name, V value) {
		overlays.put(name, valueFactory.create(value, this, refReg, null));
	}

	public void remove(String name) {
		overlays.remove(name);
	}

	public int size() {
		return overlays.size();
	}

	public Pattern getKeyPattern() {
		return keyPattern;
	}

	public Set<String> keySet() {
		return overlays.keySet();
	}

	public boolean isReference(String key) {
		ChildOverlay<V> childOverlay = (ChildOverlay<V>) overlays.get(key);
		return childOverlay.isReference();
	}

	public Reference getReference(String key) {
		ChildOverlay<V> childOverlay = (ChildOverlay<V>) overlays.get(key);
		return childOverlay.getReference();
	}

	public static <V> OverlayFactory<Map<String, V>> getFactory(OverlayFactory<V> valueFactory, String keyPattern) {
		return new MapOverlayFactory<V>(valueFactory, getWholeMatchPattern(keyPattern));
	}

	private static Pattern getWholeMatchPattern(String pat) {
		return pat != null ? Pattern.compile("^" + pat + "$") : null;
	}

	protected static class MapOverlayFactory<V> extends OverlayFactory<Map<String, V>> {

		private OverlayFactory<V> valueFactory;
		private Pattern keyPattern;

		public MapOverlayFactory(OverlayFactory<V> valueFactory, Pattern keyPattern) {
			this.valueFactory = valueFactory;
			this.keyPattern = keyPattern;
		}

		public Pattern getKeyPattern() {
			return keyPattern;
		}

		@Override
		protected Class<? extends JsonOverlay<Map<String, V>>> getOverlayClass() {
			Class<?> overlayClass = MapOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<Map<String, V>>> castClass = (Class<? extends JsonOverlay<Map<String, V>>>) overlayClass;
			return castClass;
		}

		@Override
		public MapOverlay<V> _create(Map<String, V> value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new MapOverlay<V>(value, parent, valueFactory, keyPattern, refReg);
		}

		@Override
		public MapOverlay<V> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new MapOverlay<V>(json, parent, valueFactory, keyPattern, refReg);
		}
	}

	/* package */ static class WrappedMap<V> implements Map<String, V> {
		Map<String, V> map;
		MapOverlay<V> overlay;

		public WrappedMap(Map<String, V> map, MapOverlay<V> overlay) {
			this.map = map;
			this.overlay = overlay;
		}

		public MapOverlay<V> getOverlay() {
			return overlay;
		}

		public void clear() {
			map.clear();
		}

		public V compute(String key, BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
			return map.compute(key, remappingFunction);
		}

		public V computeIfAbsent(String key, Function<? super String, ? extends V> mappingFunction) {
			return map.computeIfAbsent(key, mappingFunction);
		}

		public V computeIfPresent(String key, BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
			return map.computeIfPresent(key, remappingFunction);
		}

		public boolean containsKey(Object key) {
			return map.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return map.containsValue(value);
		}

		public Set<Entry<String, V>> entrySet() {
			return map.entrySet();
		}

		public void forEach(BiConsumer<? super String, ? super V> action) {
			map.forEach(action);
		}

		public V get(Object key) {
			return map.get(key);
		}

		public V getOrDefault(Object key, V defaultValue) {
			return map.getOrDefault(key, defaultValue);
		}

		public boolean isEmpty() {
			return map.isEmpty();
		}

		public Set<String> keySet() {
			return map.keySet();
		}

		public V merge(String key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			return map.merge(key, value, remappingFunction);
		}

		public V put(String key, V value) {
			return map.put(key, value);
		}

		public void putAll(Map<? extends String, ? extends V> m) {
			map.putAll(m);
		}

		public V putIfAbsent(String key, V value) {
			return map.putIfAbsent(key, value);
		}

		public boolean remove(Object key, Object value) {
			return map.remove(key, value);
		}

		public V remove(Object key) {
			return map.remove(key);
		}

		public boolean replace(String key, V oldValue, V newValue) {
			return map.replace(key, oldValue, newValue);
		}

		public V replace(String key, V value) {
			return map.replace(key, value);
		}

		public void replaceAll(BiFunction<? super String, ? super V, ? extends V> function) {
			map.replaceAll(function);
		}

		public int size() {
			return map.size();
		}

		public Collection<V> values() {
			return map.values();
		}
	}
}
