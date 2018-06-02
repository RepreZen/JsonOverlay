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

public final class PrimitiveOverlay extends ScalarOverlay<Object> {

	private PrimitiveOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	private PrimitiveOverlay(Object value, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
	}

	@Override
	protected Object _fromJson(JsonNode json) {
		if (json.isTextual()) {
			return json.textValue();
		} else if (json.isNumber()) {
			return json.numberValue();
		} else if (json.isBoolean()) {
			return json.booleanValue();
		} else {
			return null;
		}
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		if (value == null) {
			return _jsonMissing();
		} else if (value instanceof String) {
			return _jsonScalar((String) value);
		} else if (value instanceof BigDecimal) {
			return _jsonScalar((BigDecimal) value);
		} else if (value instanceof BigInteger) {
			return _jsonScalar((BigInteger) value);
		} else if (value instanceof Byte) {
			return _jsonScalar((Byte) value);
		} else if (value instanceof Double) {
			return _jsonScalar((Double) value);
		} else if (value instanceof Float) {
			return _jsonScalar((Float) value);
		} else if (value instanceof Integer) {
			return _jsonScalar((Integer) value);
		} else if (value instanceof Long) {
			return _jsonScalar((Long) value);
		} else if (value instanceof Short) {
			return _jsonScalar((Short) value);
		} else {
			return null;
		}
	}

	public static OverlayFactory<Object> factory = new OverlayFactory<Object>() {

		@Override
		protected Class<PrimitiveOverlay> getOverlayClass() {
			return PrimitiveOverlay.class;
		}

		@Override
		public PrimitiveOverlay _create(Object value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new PrimitiveOverlay(value, parent, refMgr);
		}

		@Override
		public PrimitiveOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new PrimitiveOverlay(json, parent, refMgr);
		}

	};
}
