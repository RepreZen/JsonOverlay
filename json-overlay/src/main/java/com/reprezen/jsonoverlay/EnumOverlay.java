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

import com.fasterxml.jackson.databind.JsonNode;

public abstract class EnumOverlay<V extends Enum<V>> extends ScalarOverlay<V> {

	private Class<V> enumClass = null;

	protected EnumOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	protected EnumOverlay(V value, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
		this.enumClass = getEnumClass();
	}

	@Override
	protected V _fromJson(JsonNode json) {
		if (!json.isTextual()) {
			return null;
		}
		if (enumClass == null) {
			enumClass = getEnumClass();
		}
		try {
			return Enum.valueOf(enumClass, json.asText());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		return value != null ? _jsonScalar(value.name()) : _jsonMissing();
	}

	protected abstract Class<V> getEnumClass();
}
