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

	protected JsonOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.json = null;
		this.value = value;
		this.parent = parent;
		this.refReg = refReg;
	}

	protected JsonOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.json = json;
		this.value = fromJson(json);
		this.parent = parent;
		this.refReg = refReg;
	}

	/* package */ V get() {
		return get(true);
	}

	/* package */ V get(boolean elaborate) {
		if (elaborate) {
			ensureElaborated();
		}
		return value;
	}

	/* package */ void set(V value) {
		this.value = value;
	}

	/* package */ JsonOverlay<?> _getParent() {
		return parent;
	}

	protected abstract V fromJson(JsonNode json);

	protected void _setParent(JsonOverlay<?> parent) {
		this.parent = parent;
	}

	/* package */ JsonNode toJson() {
		return toJsonInternal(SerializationOptions.EMPTY);
	}

	protected abstract JsonNode toJsonInternal(SerializationOptions options);

	protected void elaborate() {
		// most types of overlay don't need to do any elaboration
	}

	protected boolean _isElaborated() {
		return true;
	}

	protected void ensureElaborated() {
		if (!_isElaborated()) {
			elaborate();
		}
	}

	/* package */ String _getPathInParent() {
		return pathInParent;
	}

	/* package */ void _setPathInParent(String pathInParent) {
		this.pathInParent = pathInParent;
	}

	public String toString() {
		return toJson().toString();
	}

	// some utility classes for overlays

	protected static ObjectNode jsonObject() {
		return JsonNodeFactory.instance.objectNode();
	}

	protected static ArrayNode jsonArray() {
		return JsonNodeFactory.instance.arrayNode();
	}

	protected static TextNode jsonScalar(String s) {
		return JsonNodeFactory.instance.textNode(s);
	}

	protected static ValueNode jsonScalar(int n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(long n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(short n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(byte n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(double n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(float n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(BigInteger n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonScalar(BigDecimal n) {
		return JsonNodeFactory.instance.numberNode(n);
	}

	protected static ValueNode jsonBoolean(boolean b) {
		return JsonNodeFactory.instance.booleanNode(b);
	}

	protected static MissingNode jsonMissing() {
		return MissingNode.getInstance();
	}

	protected static NullNode jsonNull() {
		return JsonNodeFactory.instance.nullNode();
	}
}