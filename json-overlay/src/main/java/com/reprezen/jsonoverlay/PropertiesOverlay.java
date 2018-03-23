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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reprezen.jsonoverlay.SerializationOptions.Option;

public abstract class PropertiesOverlay<V> extends JsonOverlay<V> {

	private List<ChildOverlay<?>> children = Lists.newArrayList();
	private static Map<Class<?>, List<String>> propertyNames = Maps.newHashMap();
	private boolean elaborated = false;
	private boolean deferElaboration = false;

	protected PropertiesOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
		deferElaboration = json.isMissingNode();
		gatherPropertyNames();
	}

	public PropertiesOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(value, parent, refReg);
		elaborated = true;
		gatherPropertyNames();
	}

	private void gatherPropertyNames() {
		if (!propertyNames.containsKey(getClass())) {
			List<String> names = Lists.newArrayList();
			for (Field field : getClass().getDeclaredFields()) {
				if (IJsonOverlay.class.isAssignableFrom(field.getType())) {
					names.add(field.getName());
				}
			}
			propertyNames.put(getClass(), names);
		}
	}

	/* package */ List<String> getPropertyNames() {
		return propertyNames.get(getClass());
	}

	protected void maybeElaborateChildrenAtCreation() {
		if (!deferElaboration) {
			ensureElaborated();
		}
	}

	protected void ensureElaborated() {
		if (!elaborated) {
			elaborateChildren();
			elaborated = true;
		}
	}

	protected void elaborateChildren() {
	}

	@Override
	public boolean _isElaborated() {
		return elaborated;
	}

	@Override
	public AbstractJsonOverlay<?> _findInternal(JsonPointer path) {
		for (ChildOverlay<?> child : children) {
			if (child.matchesPath(path)) {
				AbstractJsonOverlay<?> found = child._find(child.tailPath(path));
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	@Override
	public V fromJson(JsonNode json) {
		// parsing of the json node is expected to be done in the constructor of the
		// subclass, so nothing is done here. But we do establish this object as its own
		// value.
		@SuppressWarnings("unchecked")
		V result = (V) this;
		return result;
	}

	@Override
	public JsonNode _toJsonInternal(SerializationOptions options) {
		JsonNode obj = jsonMissing();
		for (ChildOverlay<?> child : children) {
			JsonNode childJson = child._toJson(options.minus(Option.KEEP_ONE_EMPTY));
			if (!childJson.isMissingNode()) {
				obj = child.getPath().setInPath(obj, childJson);
			}
		}
		JsonNode result = fixJson(obj);
		return result.size() > 0 || options.isKeepThisEmpty() ? result : jsonMissing();
	}

	protected JsonNode fixJson(JsonNode json) {
		return json;
	}


	
	@Override
	V _get() {
		ensureElaborated();
		return super._get();
	}

	public V _get(boolean elaborate) {
		return elaborate ? _get() : super._get();
	}

	/* package */ AbstractJsonOverlay<?> _get(String fieldName) {
		Optional<Field> field = Stream.of(this.getClass().getDeclaredFields())
				.filter(f -> f.getName().equals(fieldName)).findFirst();
		if (field.isPresent()) {
			try {
				field.get().setAccessible(true);
				return (AbstractJsonOverlay<?>) field.get().get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}
		return null;
	}

	@Override
	public void _set(V value) {
		super._set(value);
		elaborateChildren();
	}

	protected <T> ChildOverlay<T> createChild(boolean create, String path, JsonOverlay<?> parent,
			OverlayFactory<T> factory) {
		return create ? createChild(path, parent, factory) : null;
	}

	protected <T> ChildOverlay<T> createChild(String path, JsonOverlay<?> parent, OverlayFactory<T> factory) {
		ChildOverlay<T> child = new ChildOverlay<>(path, json.at("/" + path), parent, factory, refReg);
		if (child.getOverlay() != null) {
			child.getOverlay().setPathInParent(path);
		}
		children.add(child);
		return child;
	}

	protected <T> ChildMapOverlay<T> createChildMap(boolean create, String path, JsonOverlay<?> parent,
			OverlayFactory<T> factory, String keyPattern) {
		return create ? createChildMap(path, parent, factory, keyPattern) : null;
	}

	protected <T> ChildMapOverlay<T> createChildMap(String path, JsonOverlay<?> parent, OverlayFactory<T> factory,
			String keyPattern) {
		ChildMapOverlay<T> child = new ChildMapOverlay<T>(path, json.at(pathPointer(path)), parent,
				MapOverlay.getFactory(factory, keyPattern), refReg);
		child.getOverlay().setPathInParent(path);
		children.add(child);
		return child;
	}

	protected <T> ChildListOverlay<T> createChildList(boolean create, String path, JsonOverlay<?> parent,
			OverlayFactory<T> factory) {
		return create ? createChildList(path, parent, factory) : null;
	}

	protected <T> ChildListOverlay<T> createChildList(String path, JsonOverlay<?> parent, OverlayFactory<T> factory) {
		ChildListOverlay<T> child = new ChildListOverlay<T>(path, json.at(pathPointer(path)), parent,
				ListOverlay.getFactory(factory), refReg);
		child.getOverlay().setPathInParent(path);
		children.add(child);
		return child;
	}

	private JsonPointer pathPointer(String path) {
		return (path == null || path.isEmpty()) ? JsonPointer.compile("") : JsonPointer.compile("/" + path);
	}

	@Override
	protected void elaborate() {
		maybeElaborateChildrenAtCreation();
	}

	/* package */ boolean isReference(String name) {
		ChildOverlay<?> childOverlay = (ChildOverlay<?>) _get(name);
		return childOverlay.isReference();
	}

	/* package */ Reference getReference(String name) {
		ChildOverlay<?> childOverlay = (ChildOverlay<?>) _get(name);
		return childOverlay.getReference();
	}

}
