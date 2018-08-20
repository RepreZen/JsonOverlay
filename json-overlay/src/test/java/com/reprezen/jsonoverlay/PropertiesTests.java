package com.reprezen.jsonoverlay;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

public class PropertiesTests extends Assert {

	private static JsonNodeFactory jfac = JsonNodeFactory.instance;
	private ReferenceManager refMgr = new ReferenceManager();
	private final Object LIST = new Object();
	private final Object MAP = new Object();
	private final Object ROOT_MAP = new Object();
	private final Object END = new Object();

	@Test
	public void testScalars() {
		Foo foo = createFooWithJson("hello");
		assertEquals("hello", foo.getStringField());
		assertNull(foo.getNumField());
		foo.setStringField("bye");
		assertEquals("bye", foo.getStringField());
		foo.setNumField(10);
		assertEquals(Integer.valueOf(10), foo.getNumField());
		foo.setStringField(null);
		assertNull(foo.getStringField());
		assertTrue(foo == foo._getRoot());
		JsonOverlay<String> stringOverlay = foo._getOverlay("stringField", String.class);
		assertTrue(foo == stringOverlay._getRoot());
		assertNull(Overlay.of(foo).getModel());
	}

	@Test
	public void testList() {
		Foo foo = createFooWithJson("hello", LIST, 1, 2, 3, END, 10);
		assertEquals(Arrays.asList(1, 2, 3), foo.getListField());
		checkPropertyNames(foo, "string", "list", "num");
	}

	@Test
	public void testMap() {
		Foo foo = createFooWithJson("hello", MAP, "a", 1, "b", 1, END, 10);
		assertEquals(Maps.toMap(Arrays.asList("a", "b"), s -> 1), foo.getMapField());
		checkPropertyNames(foo, "string", "map", "num");
	}

	@Test
	public void testRootMap() {
		Foo foo = createFooWithJson("hello", ROOT_MAP, "x-a", 1, "x-b", 1, END, 10);
		assertEquals(Maps.toMap(Arrays.asList("x-a", "x-b"), s -> 1), foo.getRootMap());
	}

	@Test
	public void testMixture() {
		Foo foo = createFooWithJson(10, LIST, 10, 20, 30, END, ROOT_MAP, "x-a", 1, END, "hello", MAP, "a", 1, "b", 1,
				END);
		assertEquals(Integer.valueOf(10), foo.getNumField());
		assertEquals("hello", foo.getStringField());
		assertEquals(Lists.newArrayList(10, 20, 30), foo.getListField());
		assertEquals(Maps.toMap(Arrays.asList("a", "b"), s -> 1), foo.getMapField());
		assertEquals(Maps.toMap(Arrays.asList("x-a"), s -> 1), foo.getRootMap());
		checkPropertyNames(foo, "num", "list", "string", "map", "x-a");
		Foo copy = (Foo) foo._copy();
		assertFalse("Copy operation should create different object", foo == copy);
		assertEquals(foo, copy);
		for (String name : foo._getPropertyNames()) {
			assertFalse("Copy operation should create copy of each property value",
					foo._getOverlay(name) == copy._getOverlay(name));
		}
		// foo2 has same content as foo, but numField comes last instead of
		// first
		Foo foo2 = createFooWithJson(LIST, 10, 20, 30, END, 10, ROOT_MAP, "x-a", 1, END, "hello", MAP, "a", 1, "b", 1,
				END);
		assertEquals(foo, foo2);
		assertFalse("Property order difference not detected", foo.equals(foo2, true));
	}

	@Test
	public void testSerializationOrder() {
		Foo foo = createFooWithJson("hello", 1);
		checkPropertyNames(foo, "string", "num");
		foo = createFooWithJson(1, "hello");
		checkPropertyNames(foo, "num", "string");
		foo = createFooWithJson("hello");
		checkPropertyNames(foo, "string");
		foo.setNumField(10);
		checkPropertyNames(foo, "string", "num");
		foo = createFooWithJson(1);
		checkPropertyNames(foo, "num");
		foo.setStringField("hello");
		checkPropertyNames(foo, "num", "string");
		foo = createFooWithJson();
		checkPropertyNames(foo);
	}

	@Test
	public void testPropertyNames() {
		Foo foo = createFooWithJson();
		assertEquals(Sets.newHashSet("stringField", "numField", "listField", "mapField", "rootMap"),
				Sets.newHashSet(Overlay.of(foo).getPropertyNames()));
	}

	private void checkPropertyNames(Foo foo, String... expected) {
		assertArrayEquals(expected, Iterators.toArray(foo._toJson().fieldNames(), String.class));
	}

	private Foo createFooWithJson(Object... values) {
		Deque<Object> queue = Queues.newArrayDeque(Arrays.asList(values));
		ObjectNode json = jfac.objectNode();
		while (!queue.isEmpty()) {
			Object value = queue.removeFirst();
			if (value instanceof String) {
				json.put("string", (String) value);
			} else if (value instanceof Integer) {
				json.put("num", (Integer) value);
			} else if (value == LIST) {
				json.set("list", gatherList(queue));
			} else if (value == MAP) {
				json.set("map", gatherMap(queue));
			} else if (value == ROOT_MAP) {
				json.setAll(gatherMap(queue));
			}
		}
		return (Foo) Foo.factory.create(json, null, refMgr);
	}

	private ArrayNode gatherList(Deque<Object> queue) {
		ArrayNode array = jfac.arrayNode();
		while (!queue.isEmpty()) {
			Object value = queue.removeFirst();
			if (value == END) {
				break;
			}
			array.add((Integer) value);
		}
		return array;
	}

	private ObjectNode gatherMap(Deque<Object> queue) {
		ObjectNode map = jfac.objectNode();
		while (!queue.isEmpty()) {
			Object key = queue.removeFirst();
			if (key == END) {
				break;
			}
			Integer value = (Integer) queue.removeFirst();
			map.put((String) key, value);
		}
		return map;
	}

	public static class Foo extends PropertiesOverlay<Foo> {

		private Foo(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			super(json, parent, factory, refMgr);
		}

		private Foo(Foo value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			super(value, parent, factory, refMgr);
		}

		@Override
		protected void _elaborateJson() {
			_createScalar("stringField", "string", StringOverlay.factory);
			_createScalar("numField", "num", IntegerOverlay.factory);
			_createList("listField", "list", IntegerOverlay.factory);
			_createMap("mapField", "map", IntegerOverlay.factory, null);
			_createMap("rootMap", "", IntegerOverlay.factory, "x-.*");
		}

		public String getStringField() {
			return _get("stringField", String.class);
		}

		public void setStringField(String value) {
			_setScalar("stringField", value, String.class);
		}

		public Integer getNumField() {
			return _get("numField", Integer.class);
		}

		public void setNumField(Integer value) {
			_setScalar("numField", value, Integer.class);
		}

		public List<Integer> getListField() {
			return _getList("listField", Integer.class);
		}

		public Map<String, Integer> getMapField() {
			return _getMap("mapField", Integer.class);
		}

		public Map<String, Integer> getRootMap() {
			return _getMap("rootMap", Integer.class);
		}

		public static OverlayFactory<Foo> factory = new OverlayFactory<Foo>() {

			@Override
			protected Class<? extends JsonOverlay<? super Foo>> getOverlayClass() {
				return Foo.class;
			}

			@Override
			protected JsonOverlay<Foo> _create(Foo value, JsonOverlay<?> parent, ReferenceManager refMgr) {
				return new Foo(value, parent, refMgr);
			}

			@Override
			protected JsonOverlay<Foo> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
				return new Foo(json, parent, refMgr);
			}

		};

		@Override
		protected OverlayFactory<Foo> _getFactory() {
			return factory;
		}
	}
}
