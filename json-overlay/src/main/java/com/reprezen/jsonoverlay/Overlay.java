/*********************************************************************
*  Copyright (c) 2017 ModelSolv, Inc. and others.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     ModelSolv, Inc. 
 *     - initial API and implementation and/or initial documentation
**********************************************************************/
package com.reprezen.jsonoverlay;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.PositionInfo.PositionEndpoint;

public class Overlay<V> {

	private JsonOverlay<V> overlay;

	public Overlay(JsonOverlay<V> overlay) {
		this.overlay = overlay;
	}

	public static <V> Overlay<V> of(JsonOverlay<V> overlay) {
		return new Overlay<V>(overlay);
	}

	public Overlay(IJsonOverlay<?> overlay) {
		@SuppressWarnings("unchecked")
		JsonOverlay<V> castOverlay = (JsonOverlay<V>) overlay;
		this.overlay = castOverlay;
	}

	public static <V> Overlay<V> of(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay);
	}

	public static <V> Overlay<Map<String, V>> of(MapOverlay<V> overlay) {
		return new Overlay<Map<String, V>>(overlay);
	}

	public static <V> Overlay<Map<String, V>> of(Map<String, V> map) {
		MapOverlay<V> overlay = getSidebandOverlay(map);
		return overlay != null ? new Overlay<Map<String, V>>(overlay) : null;
	}

	public static <V> Overlay<List<V>> of(ListOverlay<V> overlay) {
		return new Overlay<List<V>>(overlay);
	}

	public static <V> Overlay<List<V>> of(List<V> list) {
		ListOverlay<V> overlay = getSidebandOverlay(list);
		return overlay != null ? new Overlay<List<V>>(overlay) : null;
	}

	public Overlay(MapOverlay<V> map, String key) {
		this.overlay = map._getOverlay(key);
	}

	public static <V> Overlay<V> of(MapOverlay<V> map, String key) {
		return new Overlay<V>(map, key);
	}

	public static <V> Overlay<V> of(Map<String, V> map, String key) {
		Overlay<Map<String, V>> mapOverlay = Overlay.of(map);
		return mapOverlay != null ? Overlay.of((MapOverlay<V>) mapOverlay.getOverlay(), key) : null;
	}

	public Overlay(ListOverlay<V> list, int index) {
		this.overlay = list._getOverlay(index);
	}

	public static <V> Overlay<V> of(ListOverlay<V> list, int index) {
		return new Overlay<V>(list, index);
	}

	public static <V> Overlay<V> of(List<V> list, int index) {
		Overlay<List<V>> listOverlay = Overlay.of(list);
		return listOverlay != null ? Overlay.of((ListOverlay<V>) listOverlay.getOverlay(), index) : null;
	}

	public Overlay(PropertiesOverlay<?> props, String fieldName) {
		@SuppressWarnings("unchecked")
		JsonOverlay<V> overlay = (JsonOverlay<V>) props._getOverlay(fieldName);
		this.overlay = overlay;
	}

	public static <X> Overlay<X> of(PropertiesOverlay<?> props, String fieldName, Class<X> type) {
		return new Overlay<X>(props, fieldName);
	}

	public static <X> Overlay<X> of(IJsonOverlay<?> props, String fieldName, Class<X> type) {
		if (props instanceof PropertiesOverlay<?>) {
			@SuppressWarnings("unchecked")
			PropertiesOverlay<X> castProps = (PropertiesOverlay<X>) props;
			return Overlay.of(castProps, fieldName, type);
		} else {
			return null;
		}
	}

	public final V get() {
		return overlay._get();
	}

	public static <V> V get(JsonOverlay<V> overlay) {
		return overlay._get();
	}

	public final JsonOverlay<V> getOverlay() {
		return overlay;
	}

	public static <X> ListOverlay<X> getListOverlay(Overlay<List<X>> overlay) {
		if (overlay.getOverlay() instanceof ListOverlay) {
			ListOverlay<X> castOverlay = getSidebandOverlay(overlay.get());
			return castOverlay;
		} else {
			return null;
		}
	}

	public static <X> MapOverlay<X> getMapOverlay(Overlay<Map<String, X>> overlay) {
		if (overlay.getOverlay() instanceof MapOverlay) {
			MapOverlay<X> castOverlay = getSidebandOverlay(overlay.get());
			return castOverlay;
		} else {
			return null;
		}
	}

	public static PropertiesOverlay<?> getPropertiesOverlay(Overlay<PropertiesOverlay<?>> overlay) {
		if (overlay.getOverlay() instanceof PropertiesOverlay) {
			return (PropertiesOverlay<?>) overlay.getOverlay();
		} else {
			return null;
		}
	}

	public JsonOverlay<?> find(JsonPointer path) {
		return overlay._find(path);
	}

	public static JsonOverlay<?> find(JsonOverlay<?> overlay, JsonPointer path) {
		return overlay._find(path);
	}

	public JsonOverlay<?> find(String path) {
		return overlay._find(path);
	}

	public static <V, OV extends JsonOverlay<V>> JsonOverlay<?> find(OV overlay, String path) {
		return overlay._find(path);
	}

	public JsonNode toJson() {
		return overlay._toJson();
	}

	public static <V> JsonNode toJson(JsonOverlay<V> overlay) {
		return overlay._toJson();
	}

	public JsonNode toJson(SerializationOptions options) {
		return overlay._toJson(options);
	}

	public static <V> JsonNode toJson(JsonOverlay<V> overlay, SerializationOptions options) {
		return overlay._toJson(options);
	}

	public JsonNode toJson(SerializationOptions.Option... options) {
		return overlay._toJson(options);
	}

	public static <V> JsonNode toJson(JsonOverlay<V> overlay, SerializationOptions.Option... options) {
		return overlay._toJson(options);
	}

	public JsonNode getParsedJson() {
		return overlay._getParsedJson();
	}

	public static JsonNode getParsedJson(JsonOverlay<?> overlay) {
		return overlay._getParsedJson();
	}

	public boolean isPresent() {
		return overlay._isPresent();
	}

	public static <V> boolean isPresent(JsonOverlay<V> overlay) {
		return overlay._isPresent();
	}

	public boolean isElaborated() {
		return overlay._isElaborated();
	}

	public static <V> boolean isElaborated(JsonOverlay<V> overlay) {
		return overlay._isElaborated();
	}

	public JsonOverlay<?> getParent() {
		return overlay._getParent();
	}

	public static <V> JsonOverlay<?> getParent(JsonOverlay<V> overlay) {
		return overlay._getParent();
	}

	public PropertiesOverlay<?> getParentPropertiesOverlay() {
		JsonOverlay<?> parent = getParent();
		while (parent != null) {
			if (parent instanceof PropertiesOverlay<?>) {
				return (PropertiesOverlay<?>) parent;
			} else {
				parent = getParent(parent);
			}
		}
		return null;
	}

	public static <V> PropertiesOverlay<?> getParentPropertiesOverlay(JsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getParentPropertiesOverlay();
	}

	public String getPathInParent() {
		return overlay._getPathInParent();
	}

	public static String getPathInParent(JsonOverlay<?> overlay) {
		return overlay._getPathInParent();
	}

	public JsonOverlay<?> getRoot() {
		return overlay._getRoot();
	}

	public static JsonOverlay<?> getRoot(JsonOverlay<?> overlay) {
		return overlay._getRoot();
	}

	public <Model> Model getModel() {
		@SuppressWarnings("unchecked")
		Model model = (Model) overlay._getModel();
		return model;
	}

	public static <Model, V> Model getModel(JsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getModel();
	}

	public String getPathFromRoot() {
		return overlay._getPathFromRoot();
	}

	public static String getPathFromFromRoot(JsonOverlay<?> overlay) {
		return overlay._getPathFromRoot();
	}

	public String getJsonReference() {
		return overlay._getJsonReference();
	}

	public static String getJsonReference(JsonOverlay<?> overlay) {
		return overlay._getJsonReference();
	}

	public String getJsonReference(boolean forRef) {
		return overlay._getJsonReference(forRef);
	}

	public static String getJsonReference(JsonOverlay<?> overlay, boolean forRef) {
		return overlay._getJsonReference(forRef);
	}

	public Optional<PositionInfo> getPositionInfo() {
		return overlay._getPositionInfo();
	}

	public static Optional<PositionInfo> getPositionInfo(JsonOverlay<?> overlay) {
		return overlay._getPositionInfo();
	}

	public Optional<PositionEndpoint> getStartPosition() {
		return overlay._getPositionInfo().map(info -> info.getStart());
	}

	public static Optional<PositionEndpoint> getStartPosition(JsonOverlay<?> overlay) {
		return overlay._getPositionInfo().map(info -> info.getStart());
	}

	public List<String> getPropertyNames() {
		if (overlay instanceof PropertiesOverlay) {
			return ((PropertiesOverlay<?>) overlay)._getPropertyNames();
		} else {
			return null;
		}
	}

	public static <V> List<String> getPropertyNames(JsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getPropertyNames();
	}

	public boolean isReference(String key) {
		return getReference(key) != null;
	}

	public static <V> boolean isReference(JsonOverlay<V> overlay, String key) {
		return new Overlay<V>(overlay).isReference(key);
	}

	public boolean isReference(int index) {
		return getReference(index) != null;
	}

	public static <V> boolean isReference(JsonOverlay<V> overlay, int index) {
		return new Overlay<V>(overlay).getReference(index) != null;
	}

	public Overlay<V> getReferenceOverlay() {
		RefOverlay<V> refOverlay = overlay._getRefOverlay();
		return refOverlay != null ? new Overlay<V>(refOverlay.getOverlay()) : null;
	}

	public static <V> Overlay<V> getReferenceOverlay(JsonOverlay<V> overlay) {
		return Overlay.of(overlay).getReferenceOverlay();
	}

	public Reference getReference(String key) {
		if (overlay instanceof PropertiesOverlay) {
			return getPropertyReference(key);
		} else if (overlay instanceof MapOverlay) {
			return getMapReference(key);
		} else {
			return null;
		}
	}

	public static <V> Reference getReference(JsonOverlay<V> overlay, String key) {
		return new Overlay<V>(overlay).getReference(key);
	}

	public Reference getReference(int index) {
		if (overlay instanceof ListOverlay) {
			return getListReference(index);
		} else {
			return null;
		}
	}

	public static <V> Reference getReference(JsonOverlay<V> overlay, int index) {
		return new Overlay<V>(overlay).getReference(index);
	}

	private Reference getPropertyReference(String name) {
		PropertiesOverlay<V> propsOverlay = (PropertiesOverlay<V>) overlay;
		return getReference(propsOverlay._getOverlay(name));
	}

	private Reference getMapReference(String key) {
		@SuppressWarnings("unchecked")
		MapOverlay<V> mapOverlay = (MapOverlay<V>) overlay;
		return getReference(mapOverlay._getOverlay(key));
	}

	private Reference getListReference(int index) {
		@SuppressWarnings("unchecked")
		ListOverlay<V> listOverlay = (ListOverlay<V>) overlay;
		return getReference(listOverlay._getOverlay(index));
	}

	private Reference getReference(JsonOverlay<?> overlay) {
		return overlay != null ? overlay._getReference() : null;
	}

	private static <X> ListOverlay<X> getSidebandOverlay(List<X> list) {
		try {
			@SuppressWarnings("unchecked")
			ListOverlay<X> castOverlay = (ListOverlay<X>) list.getClass().getMethod("getOverlay").invoke(list);
			return castOverlay;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			return null;
		}
	}

	private static <X> MapOverlay<X> getSidebandOverlay(Map<String, X> map) {
		try {
			@SuppressWarnings("unchecked")
			MapOverlay<X> castOverlay = (MapOverlay<X>) map.getClass().getMethod("getOverlay").invoke(map);
			return castOverlay;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return overlay.toString();
	}
}
