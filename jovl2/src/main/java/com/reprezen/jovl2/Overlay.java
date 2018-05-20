package com.reprezen.jovl2;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Overlay<V> {

	private JsonOverlay<V> overlay;

	public Overlay(JsonOverlay<V> overlay) {
		this.overlay = overlay;
	}

	public static <V> Overlay<V> of(JsonOverlay<V> overlay) {
		return new Overlay<V>(overlay);
	}

	public static <X> Overlay<Map<String, X>> of(MapOverlay<X> overlay) {
		return new Overlay<Map<String, X>>((JsonOverlay<Map<String, X>>) overlay);
	}

	public static <V> Overlay<Map<String, V>> of(Map<String, V> map) {
		@SuppressWarnings("unchecked")
		MapOverlay<V> overlay = (MapOverlay<V>) getSidebandOverlay(map);
		return overlay != null ? new Overlay<Map<String, V>>(overlay) : null;
	}

	public static <V> Overlay<List<V>> of(ListOverlay<V> overlay) {
		return new Overlay<List<V>>((JsonOverlay<List<V>>) overlay);
	}

	public static <V> Overlay<List<V>> of(List<V> list) {
		@SuppressWarnings("unchecked")
		ListOverlay<V> overlay = (ListOverlay<V>) getSidebandOverlay(list);
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

	public final V get() {
		return overlay._get();
	}

	public final JsonOverlay<V> getOverlay() {
		return overlay;
	}

	public final MapOverlay<?> getMapOverlay() {
		if (overlay instanceof MapOverlay) {
			MapOverlay<?> castOverlay = (MapOverlay<?>) overlay;
			return castOverlay;
		} else {
			return null;
		}
	}

	public final ListOverlay<V> getListOverlay() {
		if (overlay instanceof ListOverlay) {
			@SuppressWarnings("unchecked")
			ListOverlay<V> castOverlay = (ListOverlay<V>) overlay;
			return castOverlay;
		} else {
			return null;
		}
	}

	public static <V> V get(JsonOverlay<V> overlay) {
		return ((JsonOverlay<V>) overlay)._get();
	}

	// public JsonOverlay<?> find(JsonPointer path) {
	// return overlay._find(path);
	// }

	// public static <V> JsonOverlay<?> find(JsonOverlay<V> overlay, JsonPointer
	// path) {
	// return ((JsonOverlay<V>) overlay)._find(path);
	// }

	// public JsonOverlay<?> find(String path) {
	// return overlay._find(path);
	// }

	// public static <V> JsonOverlay<?> find(JsonOverlay<V> overlay, String path) {
	// return ((JsonOverlay<V>) overlay)._find(path);
	// }

	public JsonNode toJson() {
		return overlay._toJson();
	}

	public static <V> JsonNode toJson(JsonOverlay<V> overlay) {
		return ((JsonOverlay<V>) overlay)._toJson();
	}

	// public JsonNode toJson(SerializationOptions options) {
	// return overlay.toJson(options);
	// }

	// public static <V> JsonNode toJson(JsonOverlay<V> overlay,
	// SerializationOptions options) {
	// return ((JsonOverlay<V>) overlay).toJson(options);
	// }

	// public JsonNode toJson(SerializationOptions.Option... options) {
	// return overlay.toJson(options);
	// }

	// public static <V> JsonNode toJson(JsonOverlay<V> overlay,
	// SerializationOptions.Option... options) {
	// return ((JsonOverlay<V>) overlay)._toJson(options);
	// }

	public boolean isPresent() {
		return overlay._isPresent();
	}

	public static <V> boolean isPresent(JsonOverlay<V> overlay) {
		return ((JsonOverlay<V>) overlay)._isPresent();
	}

	public boolean isElaborated() {
		return overlay._isElaborated();
	}

	public static <V> boolean isElaborated(JsonOverlay<V> overlay) {
		return overlay._isElaborated();
	}

	public JsonOverlay<?> getParent() {
		return (JsonOverlay<?>) overlay._getParent();
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

	public static <V> String getPathInParent(JsonOverlay<V> overlay) {
		return overlay._getPathInParent();
	}

	// public JsonOverlay<?> getRoot() {
	// return overlay._getRoot();
	// }

	// public static <V> JsonOverlay<?> getRoot(JsonOverlay<V> overlay) {
	// return overlay)._getRoot();
	// }

	// public <Model> Model getModel() {
	// if (overlay instanceof IModelPart<?, ?>) {
	// @SuppressWarnings("unchecked")
	// Model root = (Model) overlay._getRoot();
	// return root;
	// } else {
	// return null;
	// }
	// }

	// public static <Model, V> Model getModel(JsonOverlay<V> overlay) {
	// return new Overlay<V>(overlay).getModel();
	// }

	// public String getPathFromRoot() {
	// return overlay._getPathFromRoot();
	// }

	// public static <V> String getPathFromFromRoot(JsonOverlay<V> overlay) {
	// return ((JsonOverlay<V>) overlay)._getPathFromRoot();
	// }

	// public URL getJsonReference() {
	// return overlay._getJsonReference();
	// }

	// public static <V> URL getJsonReference(JsonOverlay<V> overlay) {
	// return ((JsonOverlay<V>) overlay)._getJsonReference();
	// }

	// public List<String> getPropertyNames() {
	// if (overlay instanceof PropertiesOverlay) {
	// return ((PropertiesOverlay<?>) overlay)._getPropertyNames();
	// } else {
	// return null;
	// }
	// }

	// public static <V> List<String> getPropertyNames(JsonOverlay<V> overlay) {
	// return new Overlay<V>(overlay).getPropertyNames();
	// }

	// public boolean isReference(String key) {
	// return getReference(key) != null;
	// }

	// public static <V> boolean isReference(JsonOverlay<V> overlay, String key) {
	// return new Overlay<V>(overlay).isReference(key);
	// }

	// public boolean isReference(int index) {
	// return getReference(index) != null;
	// }

	// public static <V> boolean isReference(JsonOverlay<V> overlay, int index) {
	// return new Overlay<V>(overlay).getReference(index) != null;
	// }

	// public Reference getReference(String key) {
	// if (overlay instanceof PropertiesOverlay) {
	// return getPropertyReference(key);
	// } else if (overlay instanceof MapOverlay) {
	// return getMapReference(key);
	// } else {
	// return null;
	// }
	// }

	// public static <V> Reference getReference(JsonOverlay<V> overlay, String key)
	// {
	// return new Overlay<V>(overlay).getReference(key);
	// }

	// public Reference getReference(int index) {
	// if (overlay instanceof ListOverlay) {
	// return getListReference(index);
	// } else {
	// return null;
	// }
	// }

	// public static <V> Reference getReference(JsonOverlay<V> overlay, int index) {
	// return new Overlay<V>(overlay).getReference(index);
	// }

	// private Reference getPropertyReference(String name) {
	// PropertiesOverlay<V> propsOverlay = (PropertiesOverlay<V>) overlay;
	// return propsOverlay._getReference(name);
	// }

	// private Reference getMapReference(String key) {
	// @SuppressWarnings("unchecked")
	// MapOverlay<V> mapOverlay = (MapOverlay<V>) overlay;
	// return mapOverlay.getReference(key);
	// }

	// private Reference getListReference(int index) {
	// @SuppressWarnings("unchecked")
	// ListOverlay<V> listOverlay = (ListOverlay<V>) overlay;
	// return listOverlay.getReference(index);
	// }

	private static JsonOverlay<?> getSidebandOverlay(Object o) {
		try {
			return (JsonOverlay<?>) o.getClass().getMethod("getOverlay").invoke(o);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			return null;
		}
	}

}
