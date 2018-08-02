package com.reprezen.jovl2;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

public class MapTests extends Assert {

	private OverlayFactory<Map<String, Integer>> factory = MapOverlay.getFactory(IntegerOverlay.factory, null);

	private final ReferenceRegistry refReg = new ReferenceRegistry();

	private final JsonNodeFactory jfac = JsonNodeFactory.instance;
	private final char a = 'A';

	@Test
	public void testMapFromValues() {
		Map<String, Integer> map = Maps.newLinkedHashMap();
		for (int i = 0; i < 10; i++) {
			map.put(Character.toString((char) (a + i)), i);
		}
		doChecks((MapOverlay<Integer>) factory.create(map, null, refReg));
	}

	@Test
	public void testMapFromJson() {
		ObjectNode obj = jfac.objectNode();
		for (int i = 0; i < 10; i++) {
			obj.set(Character.toString((char) (a + i)), jfac.numberNode(i));
		}
		doChecks((MapOverlay<Integer>) factory.create(obj, null, refReg));
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
	}

	private void checkKeys(MapOverlay<Integer> overlay, String... keys) {
		int i = 0;
		for (String key : overlay.get().keySet()) {
			assertEquals(keys[i++], key);
			assertEquals(Integer.valueOf(key.charAt(0) - a), overlay.get().get(key));
		}
	}
}
