package com.reprezen.jsonoverlay;

import java.net.URL;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public class Overlay<V> {

	private AbstractJsonOverlay<V> overlay;

	public Overlay(IJsonOverlay<V> overlay) {
		this.overlay = (AbstractJsonOverlay<V>) overlay;
	}

	public static <V> Overlay<V> of(IJsonOverlay<V> overlay) {
		return new Overlay<V>(overlay);
	}

	public final V get() {
		return overlay._get();
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
}
