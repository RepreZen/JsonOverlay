package com.reprezen.jovl2;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

public class MapTests extends Assert {

	private OverlayFactory<Map<String, Integer>> factory = MapOverlay.getFactory(IntegerOverlay.factory, null);

	private final ReferenceManager refMgr = new ReferenceManager();

	private final JsonNodeFactory jfac = JsonNodeFactory.instance;
	private final char a = 'A';

	@Test
	public void testMapFromValues() {
		Map<String, Integer> map = Maps.newLinkedHashMap();
		for (int i = 0; i < 10; i++) {
			map.put(Character.toString((char) (a + i)), i);
		}
		doChecks((MapOverlay<Integer>) factory.create(map, null, refMgr));
	}

	@Test
	public void testMapFromJson() {
		ObjectNode obj = jfac.objectNode();
		for (int i = 0; i < 10; i++) {
			obj.set(Character.toString((char) (a + i)), jfac.numberNode(i));
		}
		doChecks((MapOverlay<Integer>) factory.create(obj, null, refMgr));
	}

	private void doChecks(MapOverlay<Integer> overlay) {
		// initial content: A=>0, B=>1, ..., C=>10
		assertEquals(10, overlay.size());
		checkKeys(overlay, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
		overlay.remove("A");
		overlay.remove("E");
		overlay.remove("J");
		// now B=>1, .. D=>4, F=>6, ..., I=>9
		assertEquals(7, overlay.size());
		checkKeys(overlay, "B", "C", "D", "F", "G", "H", "I");
		overlay.set("A", 0);
		overlay.set("E", 4);
		overlay.set("J", 9);
		// now complete again, but A, E, and J are final keys
		assertEquals(10, overlay.size());
		checkKeys(overlay, "B", "C", "D", "F", "G", "H", "I", "A", "E", "J");
		MapOverlay<Integer> copy = (MapOverlay<Integer>) overlay._copy();
		assertFalse("Copy operation should yield different object", overlay == copy);
		assertEquals(overlay, copy);
		for (String key : overlay.value.keySet()) {
			assertFalse("Copy operation should create copy of each map entry",
					overlay._getOverlay(key) == copy._getOverlay(key));
		}
		copy.remove("B");
		copy.set("B", 1);
		assertEquals(overlay, copy);
		assertFalse("Key order difference not detected", overlay.equals(copy, true));
	}

	private void checkKeys(MapOverlay<Integer> overlay, String... keys) {
		int i = 0;
		for (String key : overlay._get().keySet()) {
			assertEquals(keys[i++], key);
			assertEquals(Integer.valueOf(key.charAt(0) - a), overlay._get().get(key));
		}
	}
}
