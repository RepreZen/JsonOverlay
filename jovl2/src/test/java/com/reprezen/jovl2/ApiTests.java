package com.reprezen.jovl2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.reprezen.jovl2.model.TestModelParser;
import com.reprezen.jovl2.model.impl.TestModelImpl;
import com.reprezen.jovl2.model.intf.Color;
import com.reprezen.jovl2.model.intf.TestModel;

public class ApiTests extends Assert {

	private TestModel model;

	@Before
	public void setup() throws IOException {
		this.model = TestModelParser.parse(getClass().getResource("/apiTestModel.yaml"));
	}

	@Test
	public void testScalarApi() {
		assertEquals("Model description", model.getDescription());
		model.setDescription("Model Description");
		assertEquals("Model Description", model.getDescription());
		assertEquals(Integer.valueOf(10), model.getWidth());
		assertNull(model.getHeight());
		model.setHeight(20);
		assertEquals(Integer.valueOf(20), model.getHeight());
		assertEquals(Color.GREEN, model.getColor());
		model.setColor(Color.BLUE);
		assertEquals(Color.BLUE, model.getColor());
		model.setColor(null);
		assertNull(model.getColor());
		assertEquals(Arrays.asList("A", "B"), getEntryKeys());
	}

	@Test
	public void testListApi() {
		assertTrue(model.hasIntegers());
		checkIntegers(1, 2, 3, 4, 5);
		checkIntegersPaths();
		assertEquals(Integer.valueOf(1), model.getInteger(0));
		model.removeInteger(1);
		model.addInteger(6);
		model.setInteger(0, 100);
		model.insertInteger(1, 200);
		checkIntegers(100, 200, 3, 4, 5, 6);
		checkIntegersPaths();
		assertEquals("Title for item 1", model.getItem(0).getTitle());
		assertEquals("Title for item 2", model.getItem(1).getTitle());
	}

	@Test
	public void testMapApi() {
		assertTrue(model.hasNamedIntegers());
		assertTrue(model.hasNamedInteger("I"));
		assertFalse(model.hasNamedInteger("X"));
		assertEquals(Integer.valueOf(1), model.getNamedInteger("I"));
		checkNamedIntegerNames("I", "II", "III", "IV", "V");
		checkNamedIntegers(1, 2, 3, 4, 5);
		model.removeNamedInteger("I");
		model.setNamedInteger("X", 10);
		model.setNamedInteger("II", 22);
		checkNamedIntegerNames("II", "III", "IV", "V", "X");
		checkNamedIntegers(22, 3, 4, 5, 10);
		assertEquals("Title for entry A", model.getEntry("A").getTitle());
		assertEquals("Title for entry B", model.getEntry("B").getTitle());
	}

	@Test
	public void testPathInParent() {
		assertEquals("description", Overlay.of((TestModelImpl) model, "description", String.class).getPathInParent());
		assertEquals("0", Overlay.of(model.getItems(), 0).getPathInParent());
		assertEquals("A", Overlay.of(model.getEntries(), "A").getPathInParent());
	}

	@Test
	public void testRoot() {
		assertTrue(model == Overlay.of(model).getRoot());
		assertTrue(model == Overlay.of(model, "description", String.class).getRoot());
		assertTrue(model == Overlay.of(model, "integers", ListOverlay.class).getRoot());
		assertTrue(model == Overlay.of(model, "namedIntegers", MapOverlay.class).getRoot());
		assertTrue(model == Overlay.of(model.getEntries(), "A").getRoot());
		assertTrue(model == Overlay.of(model.getItems(), 0).getRoot());

		assertTrue(model == Overlay.of(model).getModel());
		assertTrue(model == Overlay.of(model, "description", String.class).getModel());
		assertTrue(model == Overlay.of(model, "integers", ListOverlay.class).getModel());
		assertTrue(model == Overlay.of(model, "namedIntegers", MapOverlay.class).getModel());
		assertTrue(model == Overlay.of(model.getEntries(), "A").getModel());
		assertTrue(model == Overlay.of(model.getItems(), 0).getModel());
	}

	@Test
	public void testPropNames() {
		assertEquals(Sets.newHashSet("description", "width", "height", "entries", "items", "integers", "namedIntegers",
				"color", "scalars"), Sets.newHashSet(Overlay.of(model).getPropertyNames()));
		assertEquals(Sets.newHashSet("title"), Sets.newHashSet(Overlay.of(model.getEntries(), "A").getPropertyNames()));
		assertEquals(Sets.newHashSet("title"), Sets.newHashSet(Overlay.of(model.getItems(), 0).getPropertyNames()));
	}

	@Test
	public void testFind() {
		checkScalarFind("description", String.class, "/description");
		checkScalarFind("width", Integer.class, "/width");
		checkScalarFind("width", Integer.class, "/width");
		checkScalarFind("color", Color.class, "/color");
		assertTrue(Overlay.of(model.getItems(), 0).getOverlay() == Overlay.of(model).find("/items/0"));
		assertTrue(Overlay.of(model.getItems(), 1).getOverlay() == Overlay.of(model).find("/items/1"));
		assertFalse(Overlay.of(model.getItems(), 1).getOverlay() == Overlay.of(model).find("/items/0"));
		assertTrue(
				Overlay.of(model.getNamedIntegers(), "I").getOverlay() == Overlay.of(model).find("/namedIntegers/I"));
		assertTrue(
				Overlay.of(model.getNamedIntegers(), "II").getOverlay() == Overlay.of(model).find("/namedIntegers/II"));
		assertFalse(
				Overlay.of(model.getNamedIntegers(), "I").getOverlay() == Overlay.of(model).find("/namedIntegers/II"));
	}

	@Test
	public void testPathFromRoot() {
		assertEquals("/description", Overlay.of(model, "description", String.class).getPathFromRoot());
		assertEquals("/width", Overlay.of(model, "width", Integer.class).getPathFromRoot());
		assertEquals("/color", Overlay.of(model, "color", Color.class).getPathFromRoot());
		assertEquals("/items/0", Overlay.of(model.getItems(), 0).getPathFromRoot());
		assertEquals("/items/0/title", Overlay.of(model.getItem(0), "title", String.class).getPathFromRoot());
		assertEquals("/entries", Overlay.of(model.getEntries()).getPathFromRoot());
		assertEquals("/entries/A", Overlay.of(model.getEntries(), "A").getPathFromRoot());
	}

	@Test
	public void testJsonRefs() {
		String url = getClass().getResource("/apiTestModel.yaml").toString();
		assertEquals(url + "#/description", Overlay.of(model, "description", String.class).getJsonReference());
		assertEquals(url + "#/width", Overlay.of(model, "width", Integer.class).getJsonReference());
		assertEquals(url + "#/color", Overlay.of(model, "color", Color.class).getJsonReference());
		assertEquals(url + "#/items/0", Overlay.of(model.getItems(), 0).getJsonReference());
		assertEquals(url + "#/items/0/title", Overlay.of(model.getItem(0), "title", String.class).getJsonReference());
		assertEquals(url + "#/entries", Overlay.of(model.getEntries()).getJsonReference());
		assertEquals(url + "#/entries/A", Overlay.of(model.getEntries(), "A").getJsonReference());
	}

	private List<String> getEntryKeys() {
		return Lists.newArrayList(model.getEntries().keySet());
	}

	private void checkIntegers(Integer... integers) {
		assertEquals(Arrays.asList(integers), model.getIntegers());
	}

	private void checkIntegersPaths() {
		for (int i = 0; i < model.getIntegers().size(); i++) {
			assertEquals(Integer.toString(i), Overlay.of(model.getIntegers(), i).getPathInParent());
		}
	}

	private void checkNamedIntegerNames(String... names) {
		assertEquals(Arrays.asList(names), Lists.newArrayList(model.getNamedIntegers().keySet()));
	}

	private void checkNamedIntegers(Integer... integers) {
		assertEquals(Arrays.asList(integers), Lists.newArrayList(model.getNamedIntegers().values()));
	}

	private <V> void checkScalarFind(String field, Class<V> fieldType, String path) {
		assertTrue(Overlay.of(model, field, fieldType).getOverlay() == Overlay.of(model).find(path));
	}
}
