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

import java.net.URL;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reprezen.jsonoverlay.SerializationOptions.Option;

public class ChildOverlay<V> extends AbstractJsonOverlay<V> {

	private JsonPath path;
	protected JsonOverlay<V> overlay;
	private JsonOverlay<?> parent;
	private Reference reference = null;
	protected OverlayFactory<V> factory;

	public ChildOverlay(String path, V value, JsonOverlay<?> parent, OverlayFactory<V> factory,
			ReferenceRegistry refReg) {
		this.path = new JsonPath(path);
		this.parent = parent;
		this.factory = factory;
		this.overlay = factory.create(value, parent, refReg, null);
	}

	public ChildOverlay(String path, JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> factory,
			ReferenceRegistry refReg) {
		this.path = new JsonPath(path);
		this.parent = parent;
		this.factory = factory;
		if (isReferenceNode(json)) {
			this.reference = refReg.getRef(json);
			JsonNode resolved = reference.resolve();
			if (reference.isValid()) {
				if (refReg.hasOverlay(resolved)) {
					AbstractJsonOverlay<?> overlay = refReg.getOverlay(resolved);
					if (factory.isCompatible(overlay)) {
						@SuppressWarnings("unchecked")
						JsonOverlay<V> castOverlay = (JsonOverlay<V>) overlay;
						this.overlay = castOverlay;
					} else {
						// TODO don't make this a parse killer - it's really a model error
						throw new IllegalStateException("Referenced object is not compatible with referencing site");
					}
				} else {
					// note - since this is a reference, we don't set parent. If there's a way to
					// navigate to the object directly, that will determine its parent
					this.overlay = (JsonOverlay<V>) factory.create(resolved, null, refReg, reference);
					refReg.setOverlay(resolved, overlay);
				}
			} else {
				this.overlay = null;
			}
		} else {
			this.overlay = (JsonOverlay<V>) factory.create(json, parent, isPartial(), refReg);
		}
	}

	protected boolean isPartial() {
		return false;
	}

	protected boolean matchesPath(JsonPointer path) {
		return this.path.matchesPath(path);
	}

	protected JsonPointer tailPath(JsonPointer path) {
		return this.path.tailPath(path);
	}

	private boolean isReferenceNode(JsonNode node) {
		return node.isObject() && node.has("$ref");
	}

	public boolean _isPresent() {
		return overlay._isPresent();
	}

	public V _get() {
		return overlay._get();
	}

	public V _get(boolean complete) {
		if (overlay instanceof PropertiesOverlay) {
			return ((PropertiesOverlay<V>) overlay)._get(complete);
		} else {
			return overlay._get();
		}
	}

	public AbstractJsonOverlay<?> _find(JsonPointer path) {
		return overlay._find(path);
	}

	public AbstractJsonOverlay<?> _find(String path) {
		return overlay._find(path);
	}

	public void _set(V value) {
		overlay._set(value);
	}

	public AbstractJsonOverlay<?> _getParent() {
		// Note: here we return the creator of the childnode, which for a reference is
		// the holder of the reference. This may not be the same as the parent of the
		// referenced object, which is available via getOverlay().getParent().
		return parent;
	}

	public String _getPathInParent() {
		return overlay._getPathInParent();
	}

	public AbstractJsonOverlay<?> _getRoot() {
		return parent != null ? parent._getParent() : overlay._getRoot();
	}

	private static final SerializationOptions emptyOptions = new SerializationOptions();

	public JsonNode _toJson() {
		return _toJson(emptyOptions);
	}

	public JsonNode _toJson(Option... options) {
		return _toJson(new SerializationOptions(options));
	}

	public JsonNode _toJson(SerializationOptions options) {
		if (isReference() && (!options.isFollowRefs() || getReference().isInvalid())) {
			ObjectNode obj = JsonOverlay.jsonObject();
			obj.put("$ref", reference.getRefString());
			return obj;
		} else {
			return overlay != null ? overlay._toJsonInternal(options) : JsonOverlay.jsonNull();
		}
	}

	@Override
	public boolean _isElaborated() {
		return overlay._isElaborated();
	}

	public JsonPath getPath() {
		return path;
	}

	public boolean isReference() {
		return reference != null;
	}

	public Reference getReference() {
		return reference;
	}

	public JsonOverlay<V> getOverlay() {
		return overlay;
	}

	public String _getPathFromRoot() {
		return overlay._getPathFromRoot();
	}

	public URL _getJsonReference() {
		return overlay._getJsonReference();
	}

	@Override
	public String toString() {
		String refString = reference != null ? String.format("<%s>", reference.getRefString()) : "";
		return String.format("Child@%s%s: %s", path, refString, overlay);
	}
}
