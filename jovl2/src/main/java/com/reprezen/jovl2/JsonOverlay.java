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
import java.util.List;

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
	protected final ReferenceRegistry refReg;
	protected List<Integer> jsonPositions = null;
	private String pathInParent = null;
	private boolean present;

	protected JsonOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.json = null;
		this.value = value;
		this.parent = parent;
		this.refReg = refReg;
		this.present = !json.isMissingNode();
	}

	protected JsonOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.json = json;
		this.value = _fromJson(json);
		this.parent = parent;
		this.refReg = refReg;
		this.present = value != null;
	}

	/* package */ V _get() {
		return _get(true);
	}

	/* package */ V _get(boolean elaborate) {
		if (elaborate) {
			_ensureElaborated();
		}
		return value;
	}

	/* package */ void _set(V value) {
		this.value = value;
		this.present = value != null;
	}

	/* packkage */ boolean _isPresent() {
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
		return _toJsonInternal(SerializationOptions.EMPTY);
	}

	protected abstract JsonNode _toJsonInternal(SerializationOptions options);

	protected void _elaborate() {
		// most types of overlay don't need to do any elaboration
	}

	protected boolean _isElaborated() {
		return true;
	}

	protected void _ensureElaborated() {
		if (!_isElaborated()) {
			_elaborate();
		}
	}

	/* package */ String _getPathInParent() {
		return pathInParent;
	}

	/* package */ void _setPathInParent(String pathInParent) {
		this.pathInParent = pathInParent;
	}

	public String toString() {
		return _toJson().toString();
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