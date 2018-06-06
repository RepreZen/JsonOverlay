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

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;

public abstract class JsonOverlay<V> implements IJsonOverlay<V> {

	protected final static ObjectMapper mapper = new ObjectMapper();

	protected V value = null;
	protected JsonOverlay<?> parent;
	protected JsonNode json;
	protected final ReferenceManager refMgr;
	protected final OverlayFactory<V> factory;
	private String pathInParent = null;
	private boolean present;
	private RefOverlay<V> reference = null;
	private Reference creatingRef = null;

	protected JsonOverlay(V value, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		this.json = null;
		this.value = value;
		this.parent = parent;
		this.factory = factory;
		this.refMgr = refMgr;
		this.present = value != null;
	}

	protected JsonOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		this.json = json;
		if (Reference.isReferenceNode(json)) {
			this.reference = new RefOverlay<V>(json, parent, factory, refMgr);
		} else {
			this.value = _fromJson(json);
		}
		this.parent = parent;
		this.factory = factory;
		this.refMgr = refMgr;
		this.present = !json.isMissingNode();
	}

	/* package */ V _get() {
		return _get(true);
	}

	/* package */ V _get(boolean elaborate) {
		if (reference != null) {
			return reference._get();
		} else {
			if (elaborate) {
				_ensureElaborated();
			}
			return _getValue();
		}
	}

	protected V _getValue() {
		return value;
	}

	/* package */ void _set(V value) {
		this.value = value;
		this.present = value != null;
	}

	/* package */ JsonOverlay<V> _copy() {
		return factory.create(_get(), null, refMgr);
	}

	/* package */ boolean _isReference() {
		return reference != null;
	}

	/* package */ RefOverlay<V> _getReference() {
		return reference;
	}

	/* package */ void _setReference(RefOverlay<V> reference) {
		this.reference = reference;
	}

	/* package */ Reference _getCreatingRef() {
		return creatingRef;
	}

	/* pakcage */ void _setCreatingRef(Reference creatingRef) {
		this.creatingRef = creatingRef;
	}

	/* package */ boolean _isPresent() {
		return present;
	}

	/* package */ JsonOverlay<?> _getParent() {
		return parent;
	}

	/* package */ JsonOverlay<?> _getRoot() {
		if (reference != null && reference._getReference().isValid()) {
			return reference.getOverlay()._getRoot();
		} else {
			JsonOverlay<?> result = this;
			while (result._getParent() != null) {
				result = result._getParent();
			}
			return result;
		}
	}

	/* package */ JsonOverlay<?> _getModel() {
		if (reference != null && reference._getReference().isValid()) {
			return reference.getOverlay()._getModel();
		} else {
			JsonOverlay<?> modelPart = this._getModelType() != null ? this : null;
			JsonOverlay<?> result = this;
			while (result._getParent() != null) {
				result = result._getParent();
				modelPart = modelPart == null && result._getModelType() != null ? result : null;
			}
			return modelPart != null && modelPart._getModelType().isAssignableFrom(result.getClass()) ? result : null;
		}
	}

	protected Class<?> _getModelType() {
		return null;
	}

	/* package */ JsonOverlay<?> _find(JsonPointer path) {
		return path.matches() ? thisOrRefTarget() : _findInternal(path);
	}

	/* package */ JsonOverlay<?> _find(String path) {
		return _find(JsonPointer.compile(path));
	}

	abstract protected JsonOverlay<?> _findInternal(JsonPointer path);

	/* package */String _getPathFromRoot() {
		return parent != null ? (parent._getParent() != null ? parent._getPathFromRoot() : "") + "/" + pathInParent
				: "/";
	}

	/* package */String _getJsonReference() {
		return _getJsonReference(false);
	}

	/* package */String _getJsonReference(boolean forRef) {
		if (_isReference() && reference._getReference().isValid() && !forRef) {
			return reference.getOverlay()._getJsonReference(false);
		}
		if (creatingRef != null && creatingRef.isValid()) {
			return creatingRef.getNormalizedRef();
		} else if (parent != null) {
			String ref = parent._getJsonReference();
			return ref + (ref.contains("#") ? "" : "#") + "/" + pathInParent;
		} else {
			return "#";
		}
	}

	protected abstract V _fromJson(JsonNode json);

	protected void _setParent(JsonOverlay<?> parent) {
		this.parent = parent;
	}

	/* package */ JsonNode _toJson() {
		return _toJson(SerializationOptions.EMPTY);
	}

	/* package */ JsonNode _toJson(SerializationOptions options) {
		if (_isReference() && (options.isFollowRefs() || reference._getReference().isInvalid())) {
			ObjectNode obj = _jsonObject();
			obj.put("$ref", reference._getReference().getRefString());
			return obj;
		} else {
			return _toJsonInternal(options);
		}
	}

	/* package */ JsonNode _toJson(SerializationOptions.Option... options) {
		return _toJson(new SerializationOptions(options));
	}

	protected abstract JsonNode _toJsonInternal(SerializationOptions options);

	private JsonOverlay<V> thisOrRefTarget() {
		if (reference == null || reference._getReference().isInvalid()) {
			return this;
		} else {
			return reference.getOverlay();
		}
	}

	protected void _elaborate(boolean atCreation) {
		// most types of overlay don't need to do any elaboration
	}

	protected boolean _isElaborated() {
		return true;
	}

	protected void _ensureElaborated() {
		if (!_isElaborated() && reference == null) {
			_elaborate(false);
		}
	}

	/* package */ String _getPathInParent() {
		return pathInParent;
	}

	/* package */ void _setPathInParent(String pathInParent) {
		this.pathInParent = pathInParent;
	}

	@Override
	public String toString() {
		return _toJson().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JsonOverlay) {
			JsonOverlay<?> castObj = (JsonOverlay<?>) obj;
			return value != null ? value.equals(castObj.value) : castObj.value == null;
		} else {
			return false; // obj is null or not an overlay object
		}
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	// some utility classes for overlays

	private static JsonNodeFactory fac = MinSharingJsonNodeFactory.instance;

	protected static ObjectNode _jsonObject() {
		return fac.objectNode();
	}

	protected static ArrayNode _jsonArray() {
		return fac.arrayNode();
	}

	protected static TextNode _jsonScalar(String s) {
		return fac.textNode(s);
	}

	protected static ValueNode _jsonScalar(int n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(long n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(short n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(byte n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(double n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(float n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(BigInteger n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonScalar(BigDecimal n) {
		return fac.numberNode(n);
	}

	protected static ValueNode _jsonBoolean(boolean b) {
		return fac.booleanNode(b);
	}

	protected static MissingNode _jsonMissing() {
		return MissingNode.getInstance();
	}

	protected static NullNode _jsonNull() {
		return fac.nullNode();
	}
}