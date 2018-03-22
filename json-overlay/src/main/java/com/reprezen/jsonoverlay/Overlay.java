package com.reprezen.jsonoverlay;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.ListOverlay.WrappedCollection;
import com.reprezen.jsonoverlay.MapOverlay.WrappedMap;

public class Overlay<V> {

	private AbstractJsonOverlay<V> overlay;

	public Overlay(IJsonOverlay<V> overlay) {
		this.overlay = (AbstractJsonOverlay<V>) overlay;
	}

	public static <V> Overlay<V> of(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay);
	}

	public static <X> Overlay<Map<String, X>> of(MapOverlay<X> overlay) {
		return new Overlay<Map<String, X>>((IJsonOverlay<Map<String, X>>) overlay);
	}

	public static <V> Overlay<Map<String, V>> of(Map<String, V> map) {
		if (map instanceof WrappedMap) {
			MapOverlay<V> mapOverlay = ((WrappedMap<V>) map).getOverlay();
			return Overlay.of(mapOverlay);
		} else {
			return null;
		}
	}

	public static <X> Overlay<Collection<X>> of(ListOverlay<X> overlay) {
		return new Overlay<Collection<X>>((IJsonOverlay<Collection<X>>) overlay);
	}

	public static <V> Overlay<Collection<V>> of(Collection<V> list) {
		if (list instanceof WrappedCollection) {
			ListOverlay<V> listOverlay = ((WrappedCollection<V>) list).getOverlay();
			return Overlay.of(listOverlay);
		} else {
			return null;
		}
	}

	public Overlay(MapOverlay<V> map, String key) {
		this.overlay = map._get(key);
	}

	public static <V> Overlay<V> of(MapOverlay<V> map, String key) {
		return new Overlay<V>(map, key);
	}

	public Overlay(ListOverlay<V> list, int index) {
		this.overlay = list._get(index);
	}

	public static <V> Overlay<V> of(ListOverlay<V> list, int index) {
		return new Overlay<V>(list, index);
	}

	public Overlay(PropertiesOverlay<?> props, String fieldName, Class<? extends V> type) {
		AbstractJsonOverlay<?> overlay = props._get(fieldName);
		Object value = overlay != null ? overlay._get() : null;
		if (value == null || type.isAssignableFrom(value.getClass())) {
			@SuppressWarnings("unchecked")
			IJsonOverlay<V> castOverlay1 = (IJsonOverlay<V>) overlay;
			if (castOverlay1 instanceof ChildOverlay) {
				@SuppressWarnings("unchecked")
				AbstractJsonOverlay<V> castOverlay2 = (AbstractJsonOverlay<V>) ((ChildOverlay<?>) castOverlay1)
						.getOverlay();
				this.overlay = castOverlay2;
			} else {
				this.overlay = (AbstractJsonOverlay<V>) castOverlay1;
			}
		} else {
			this.overlay = null;
		}
	}

	public static <V> Overlay<V> of(Object props, String fieldName, Class<? extends V> type) {
		if (props instanceof PropertiesOverlay) {
			PropertiesOverlay<?> castProps = (PropertiesOverlay<?>) props;
			return new Overlay<V>(castProps, fieldName, type);
		} else {
			return null;
		}

	}

	public final V get() {
		return overlay._get();
	}

	public final IJsonOverlay<V> getOverlay() {
		return overlay;
	}

	public final MapOverlay<V> getMapOverlay() {
		if (overlay instanceof MapOverlay) {
			@SuppressWarnings("unchecked")
			MapOverlay<V> castOverlay = (MapOverlay<V>) overlay;
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

	public static <V> V get(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._get();
	}

	public AbstractJsonOverlay<?> find(JsonPointer path) {
		return overlay._find(path);
	}

	public static <V> AbstractJsonOverlay<?> find(IJsonOverlay<V> overlay, JsonPointer path) {
		return ((AbstractJsonOverlay<V>) overlay)._find(path);
	}

	public AbstractJsonOverlay<?> find(String path) {
		return overlay._find(path);
	}

	public static <V> AbstractJsonOverlay<?> find(IJsonOverlay<V> overlay, String path) {
		return ((AbstractJsonOverlay<V>) overlay)._find(path);
	}

	public JsonNode toJson() {
		return overlay._toJson();
	}

	public static <V> JsonNode toJson(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._toJson();
	}

	public JsonNode toJson(SerializationOptions options) {
		return overlay._toJson(options);
	}

	public static <V> JsonNode toJson(IJsonOverlay<V> overlay, SerializationOptions options) {
		return ((AbstractJsonOverlay<V>) overlay)._toJson(options);
	}

	public JsonNode toJson(SerializationOptions.Option... options) {
		return overlay._toJson(options);
	}

	public static <V> JsonNode toJson(IJsonOverlay<V> overlay, SerializationOptions.Option... options) {
		return ((AbstractJsonOverlay<V>) overlay)._toJson(options);
	}

	public boolean isPresent() {
		return overlay._isPresent();
	}

	public static <V> boolean isPresent(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._isPresent();
	}

	public boolean isElaborated() {
		return overlay._isElaborated();
	}

	public static <V> boolean isElaborated(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._isElaborated();
	}

	public JsonOverlay<?> getParent() {
		return (JsonOverlay<?>) overlay._getParent();
	}

	public static <V> IJsonOverlay<?> getParent(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._getParent();
	}

	public PropertiesOverlay<?> getParentPropertiesOverlay() {
		IJsonOverlay<?> parent = getParent();
		while (parent != null) {
			if (parent instanceof PropertiesOverlay<?>) {
				return (PropertiesOverlay<?>) parent;
			} else {
				parent = getParent(parent);
			}
		}
		return null;
	}

	public static <V> PropertiesOverlay<?> getParentPropertiesOverlay(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getParentPropertiesOverlay();
	}

	public String getPathInParent() {
		return overlay._getPathInParent();
	}

	public static <V> String getPathInParent(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._getPathInParent();
	}

	public AbstractJsonOverlay<?> getRoot() {
		return overlay._getRoot();
	}

	public static <V> AbstractJsonOverlay<?> getRoot(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._getRoot();
	}

	public <Model> Model getModel() {
		if (overlay instanceof IModelPart<?, ?>) {
			@SuppressWarnings("unchecked")
			Model root = (Model) overlay._getRoot();
			return root;
		} else {
			return null;
		}
	}

	public static <Model, V> Model getModel(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getModel();
	}

	public String getPathFromRoot() {
		return overlay._getPathFromRoot();
	}

	public static <V> String getPathFromFromRoot(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._getPathFromRoot();
	}

	public URL getJsonReference() {
		return overlay._getJsonReference();
	}

	public static <V> URL getJsonReference(IJsonOverlay<V> overlay) {
		return ((AbstractJsonOverlay<V>) overlay)._getJsonReference();
	}

	public List<String> getPropertyNames() {
		if (overlay instanceof PropertiesOverlay) {
			return ((PropertiesOverlay<?>) overlay).getPropertyNames();
		} else {
			return null;
		}
	}

	public static <V> List<String> getPropertyNames(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay).getPropertyNames();
	}

	public boolean isReference(String key) {
		return getReference(key) != null;
	}

	public static <V> boolean isReference(IJsonOverlay<V> overlay, String key) {
		return new Overlay<V>(overlay).isReference(key);
	}

	public boolean isReference(int index) {
		return getReference(index) != null;
	}

	public static <V> boolean isReference(IJsonOverlay<V> overlay, int index) {
		return new Overlay<V>(overlay).getReference(index) != null;
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

	public static <V> Reference getReference(IJsonOverlay<V> overlay, String key) {
		return new Overlay<V>(overlay).getReference(key);
	}

	public Reference getReference(int index) {
		if (overlay instanceof ListOverlay) {
			return getListReference(index);
		} else {
			return null;
		}
	}

	public static <V> Reference getReference(IJsonOverlay<V> overlay, int index) {
		return new Overlay<V>(overlay).getReference(index);
	}

	private Reference getPropertyReference(String name) {
		PropertiesOverlay<V> propsOverlay = (PropertiesOverlay<V>) overlay;
		return propsOverlay.getReference(name);
	}

	private Reference getMapReference(String key) {
		@SuppressWarnings("unchecked")
		MapOverlay<V> mapOverlay = (MapOverlay<V>) overlay;
		return mapOverlay.getReference(key);
	}

	private Reference getListReference(int index) {
		@SuppressWarnings("unchecked")
		ListOverlay<V> listOverlay = (ListOverlay<V>) overlay;
		return listOverlay.getReference(index);
	}
}
