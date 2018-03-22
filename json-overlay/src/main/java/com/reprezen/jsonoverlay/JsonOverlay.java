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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

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

public abstract class JsonOverlay<V> extends AbstractJsonOverlay<V> {

	protected final static ObjectMapper mapper = new ObjectMapper();

	protected V value = null;
	protected JsonOverlay<?> parent;
	protected JsonNode json = null;
	protected ReferenceRegistry refReg;
	private String pathInParent = null;
	private Reference reference = null;

	public JsonOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.value = value;
		this.parent = parent;
		this.refReg = refReg;
	}

	public JsonOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		this.json = json;
		this.value = fromJson(json);
		this.parent = parent;
		this.refReg = refReg;
	}

	protected void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	/* protected */boolean _isPresent() {
		return value != null && !json.isMissingNode();
	}

	@Override
	/* package */boolean _isElaborated() {
		// most overlays are complete when constructed
		return true;
	}

	@Override
	/* package */ AbstractJsonOverlay<?> _find(JsonPointer path) {
		return path.matches() ? this : _findInternal(path);
	}

	abstract protected AbstractJsonOverlay<?> _findInternal(JsonPointer path);

	@Override
	/* package */ AbstractJsonOverlay<?> _find(String path) {
		return _find(JsonPointer.compile(path));
	}

	/* package */ V _get() {
		return value;
	}

	/* package */void _set(V value) {
		_set(value, true);
	}

	protected void _set(V value, boolean invalidate) {
		this.value = value;
	}

	/* package */ JsonOverlay<?> _getParent() {
		return parent;
	}

	protected void setParent(JsonOverlay<?> parent) {
		this.parent = parent;
	}

	protected void setPathInParent(String pathInParent) {
		this.pathInParent = pathInParent;
	}

	/* package */String _getPathInParent() {
		return pathInParent;
	}

	/* package */String _getPathFromRoot() {
		return parent != null ? (parent._getParent() != null ? parent._getPathFromRoot() : "") + "/" + pathInParent
				: "/";
	}

	@Override
	/* package */URL _getJsonReference() {
		URL result = null;
		try {
			if (reference != null) {
				result = new URL(reference.getCanonicalRefString());
			} else {
				URL parentUrl = parent != null ? parent._getJsonReference() : null;
				if (parentUrl != null) {
					JsonPointer ptr = JsonPointer.compile(parentUrl.getRef());
					ptr = ptr.append(JsonPointer.compile("/" + pathInParent));
					result = new URL(parentUrl, "#" + ptr.toString());
				}
			}
		} catch (IllegalArgumentException | MalformedURLException e) {
		}
		return result;
	}

	/* package */JsonOverlay<?> _getRoot() {
		return parent != null ? parent._getRoot() : this;
	}

	protected abstract V fromJson(JsonNode json);

	protected void elaborate() {
	}

	private static final SerializationOptions emptyOptions = new SerializationOptions();

	@Override
	/* package */JsonNode _toJson() {
		return _toJsonInternal(emptyOptions);
	}

	@Override
	/* package */JsonNode _toJson(SerializationOptions.Option... options) {
		return _toJsonInternal(new SerializationOptions(options));
	}

	@Override
	/* package */JsonNode _toJson(SerializationOptions options) {
		return _toJsonInternal(options);
	}

	/* package */abstract JsonNode _toJsonInternal(SerializationOptions options);

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

	protected static final <T> Iterable<T> iterable(final Iterator<T> iterator) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return iterator;
			}
		};
	}

	@Override
	public String toString() {
		return _toJson().toString();
	}

}