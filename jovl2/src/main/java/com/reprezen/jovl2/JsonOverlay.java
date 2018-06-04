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
package com.reprezen.jovl2;

import java.math.BigDecimal;
import java.math.BigInteger;

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

	/* package */ boolean _isPresent() {
		return present;
	}

	/* package */ JsonOverlay<?> _getParent() {
		return parent;
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

	protected static ObjectNode _jsonObject() {
		return JsonNodeFactory.instance.objectNode();
	}

	protected static ArrayNode _jsonArray() {
		return JsonNodeFactory.instance.arrayNode();
	}

	protected static TextNode _jsonScalar(String s) {
		return JsonNodeFactory.instance.textNode(s);
	}

	protected static ValueNode _jsonScalar(int n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(long n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(short n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(byte n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(double n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(float n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(BigInteger n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonScalar(BigDecimal n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode _jsonBoolean(boolean b) {
		return JsonNodeFactory.instance.booleanNode(b);
	}

	protected static MissingNode _jsonMissing() {
		return MissingNode.getInstance();
	}

	protected static NullNode _jsonNull() {
		return JsonNodeFactory.instance.nullNode();
	}
}