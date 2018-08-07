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
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;

public final class NumberOverlay extends ScalarOverlay<Number> {

	private NumberOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	private NumberOverlay(Number value, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
	}

	@Override
	protected Number _fromJson(JsonNode json) {
		if (json.isBigDecimal()) {
			return json.decimalValue();
		} else if (json.isBigInteger()) {
			return json.bigIntegerValue();
		}
		// no methods for Byte, even though numberNode(Byte) is provided.
		// experimentations shows that bytes show up as ints. Oh well..
		else if (json.isDouble()) {
			return json.doubleValue();
		} else if (json.isFloat()) {
			return json.floatValue();
		} else if (json.isInt()) {
			return json.intValue();
		} else if (json.isLong()) {
			return json.longValue();
		} else if (json.isShort()) {
			return json.shortValue();
		} else {
			return null;
		}
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		if (value == null) {
			return _jsonMissing();
		}
		NumberType type = NumberType.of(value);
		if (type == null) {
			throw new IllegalArgumentException(
					"Numeric class " + value.getClass().getName() + " is not representable as a JsonNode");
		} else {
			switch (type) {
			case BigDecimal:
				return _jsonScalar((BigDecimal) value);
			case BigInteger:
				return _jsonScalar((BigInteger) value);
			case Byte:
				return _jsonScalar((Byte) value);
			case Double:
				return _jsonScalar((Double) value);
			case Float:
				return _jsonScalar((Float) value);
			case Integer:
				return _jsonScalar((Integer) value);
			case Long:
				return _jsonScalar((Long) value);
			case Short:
				return _jsonScalar((Short) value);
			default:
				return _jsonMissing();
			}
		}
	}

	@Override
	protected OverlayFactory<Number> _getFactory() {
		return factory;
	}

	public static OverlayFactory<Number> factory = new OverlayFactory<Number>() {
		@Override
		protected Class<NumberOverlay> getOverlayClass() {
			return NumberOverlay.class;
		}

		@Override
		public NumberOverlay _create(Number value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new NumberOverlay(value, parent, refMgr);
		}

		@Override
		public NumberOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new NumberOverlay(json, parent, refMgr);
		}
	};

	private enum NumberType {
		BigDecimal(BigDecimal.class), //
		BigInteger(BigInteger.class), //
		Byte(Byte.class), //
		Double(Double.class), //
		Float(Float.class), //
		Integer(Integer.class), //
		Long(Long.class), //
		Short(Short.class);

		NumberType(Class<? extends Number> cls) {
			this.cls = cls;
		}

		private Class<? extends Number> cls;
		private static Map<Class<? extends Number>, NumberType> typeMap = null;

		private static <T extends Number> NumberType of(T obj) {
			if (typeMap == null) {
				buildTypeMap();
			}
			return typeMap.get(obj.getClass());
		}

		private static void buildTypeMap() {
			typeMap = Maps.newHashMap();
			for (NumberType type : NumberType.values()) {
				typeMap.put(type.cls, type);
			}
		}
	}
}