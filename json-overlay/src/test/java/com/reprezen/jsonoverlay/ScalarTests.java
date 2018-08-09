package com.reprezen.jsonoverlay;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ScalarTests {

	@RunWith(Parameterized.class)
	public static class StringTests extends ScalarTestBase<String> {

		@Parameters
		public static Collection<String> getValues() {
			return Lists.newArrayList("hello", "");
		}

		public StringTests(String value) {
			super(StringOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(String value) {
			return value != null ? jfac.textNode(value) : MissingNode.getInstance();
		}
	}

	@RunWith(Parameterized.class)
	public static class BooleanTests extends ScalarTestBase<Boolean> {

		@Parameters
		public static Collection<Boolean> getValues() {
			return Lists.newArrayList(true, false);
		}

		public BooleanTests(Boolean value) {
			super(BooleanOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(Boolean value) {
			return value != null ? jfac.booleanNode(value) : MissingNode.getInstance();
		}
	}

	@RunWith(Parameterized.class)
	public static class IntegerTests extends ScalarTestBase<Integer> {

		@Parameters
		public static Collection<Integer> getValues() {
			return Lists.newArrayList(0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}

		public IntegerTests(Integer value) {
			super(IntegerOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(Integer value) {
			return value != null ? jfac.numberNode(value) : MissingNode.getInstance();
		}
	}

	@RunWith(Parameterized.class)
	public static class NumberTests extends ScalarTestBase<Number> {

		@Parameters
		public static Collection<Number> getValues() {
			return Lists.newArrayList( //
					BigDecimal.ZERO, BigDecimal.ONE, new BigDecimal("-1"),
					new BigDecimal("4323433423423423423424234234234234.342342313434253432342412342342342232"), //
					BigInteger.ZERO, BigInteger.ONE, new BigInteger("-1"), //
					new BigInteger("312371230981234712398471234912873491283471293847129348712349821374129347823"), //
					0.0d, 1.0d, -1.0d, Double.MAX_VALUE, -Double.MAX_VALUE, Double.MIN_VALUE, -Double.MIN_VALUE, //
					0.0, 1.0, -1.0, Float.MAX_VALUE, -Float.MAX_VALUE, Float.MIN_VALUE, -Float.MIN_VALUE, //
					0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE, //
					0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, //
					Short.valueOf((short) 0), Short.valueOf((short) 1), Short.valueOf((short) -1),
					Short.valueOf(Short.MAX_VALUE), Short.valueOf(Short.MIN_VALUE) //
			);
		}

		public NumberTests(Number value) {
			super(NumberOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(Number value) {
			return numberToJson(value);
		}

		// broken out so can be reused in PrimitiveTests
		public static JsonNode numberToJson(Number value) {
			if (value == null) {
				return MissingNode.getInstance();
			} else if (value instanceof BigDecimal) {
				return jfac.numberNode((BigDecimal) value);
			} else if (value instanceof BigInteger) {
				return jfac.numberNode((BigInteger) value);
			} else if (value instanceof Double) {
				return jfac.numberNode((Double) value);
			} else if (value instanceof Float) {
				return jfac.numberNode((Float) value);
			} else if (value instanceof Integer) {
				return jfac.numberNode((Integer) value);
			} else if (value instanceof Long) {
				return jfac.numberNode((Long) value);
			} else if (value instanceof Short) {
				return jfac.numberNode((Short) value);
			}
			throw new IllegalArgumentException();
		}
	}

	@RunWith(Parameterized.class)
	public static class PrimitiveTests extends ScalarTestBase<Object> {

		@Parameters
		public static Collection<Object> getValues() {
			return Lists.newArrayList( //
					"hello", "", //
					BigDecimal.ZERO, BigDecimal.ONE, new BigDecimal("-1"),
					new BigDecimal("4323433423423423423424234234234234.342342313434253432342412342342342232"), //
					BigInteger.ZERO, BigInteger.ONE, new BigInteger("-1"), //
					new BigInteger("312371230981234712398471234912873491283471293847129348712349821374129347823"), //
					0.0d, 1.0d, -1.0d, Double.MAX_VALUE, -Double.MAX_VALUE, Double.MIN_VALUE, -Double.MIN_VALUE, //
					0.0, 1.0, -1.0, Float.MAX_VALUE, -Float.MAX_VALUE, Float.MIN_VALUE, -Float.MIN_VALUE, //
					0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE, //
					0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, //
					Short.valueOf((short) 0), Short.valueOf((short) 1), Short.valueOf((short) -1),
					Short.valueOf(Short.MAX_VALUE), Short.valueOf(Short.MIN_VALUE) //
			);
		}

		public PrimitiveTests(Object value) {
			super(PrimitiveOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(Object value) {
			if (value == null) {
				return MissingNode.getInstance();
			} else if (value instanceof Number) {
				return NumberTests.numberToJson((Number) value);
			} else if (value instanceof String) {
				return jfac.textNode((String) value);
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class ObjectTests extends ScalarTestBase<Object> {

		@Parameters
		public static Collection<Object> getValues() {
			Map<String, Object> map = Maps.newHashMap();
			map.put("x", 1);
			map.put("y", null);
			map.put("z", Arrays.asList("a", "b", "c"));
			return Lists.newArrayList("foo", 1, 1.0, Arrays.asList(0, 1, 2),
					Arrays.<Object>asList(3, "blah", Arrays.asList(1, 2, 3)), map);
		}

		public ObjectTests(Object value) {
			super(ObjectOverlay.factory);
			this.value = value;
		}

		private static final ObjectMapper mapper = new ObjectMapper();

		@Override
		protected JsonNode toJson(Object value) {
			return value != null ? mapper.convertValue(value, JsonNode.class) : MissingNode.getInstance();
		}

		@Override
		public void testWithWrongJson() {
			// there's no "wrong json" for this overlay, so this test is a no-op
		}
	}

	public static enum XEnum {
		A, B, C
	}

	@RunWith(Parameterized.class)
	public static class EnumTests extends ScalarTestBase<XEnum> {

		@Parameters
		public static Collection<XEnum> getValues() {
			return Arrays.asList(XEnum.values());
		}

		public EnumTests(XEnum value) {
			super(XEnumOverlay.factory);
			this.value = value;
		}

		@Override
		protected JsonNode toJson(XEnum value) {
			return value != null ? jfac.textNode(value.name()) : MissingNode.getInstance();
		}

		public static class XEnumOverlay extends EnumOverlay<XEnum> {

			public XEnumOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
				super(json, parent, factory, refMgr);
			}

			public XEnumOverlay(XEnum value, JsonOverlay<?> parent, ReferenceManager refMgr) {
				super(value, parent, factory, refMgr);
			}

			@Override
			protected Class<XEnum> getEnumClass() {
				return XEnum.class;
			}

			@Override
			protected OverlayFactory<XEnum> _getFactory() {
				return factory;
			}

			public static OverlayFactory<XEnum> factory = new OverlayFactory<XEnum>() {
				@Override
				protected Class<? extends JsonOverlay<? super XEnum>> getOverlayClass() {
					return XEnumOverlay.class;
				}

				@Override
				public JsonOverlay<XEnum> _create(XEnum value, JsonOverlay<?> parent, ReferenceManager refMgr) {
					return new XEnumOverlay(value, parent, refMgr);
				}

				@Override
				public JsonOverlay<XEnum> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
					return new XEnumOverlay(json, parent, refMgr);
				}
			};
		}
	}
}
