package com.reprezen.jovl2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.reprezen.jovl2.model.TestModelParser;
import com.reprezen.jovl2.model.impl.TestModelImpl;
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
		assertEquals(Arrays.asList("A", "B"), getEntryKeys());
	}

	@Test
	public void testListApi() {
		assertTrue(model.hasIntegers());
		checkIntegers(1, 2, 3, 4, 5);
		assertEquals(Integer.valueOf(1), model.getInteger(0));
		model.removeInteger(1);
		model.addInteger(6);
		model.setInteger(0, 100);
		model.insertInteger(1, 200);
		checkIntegers(100, 200, 3, 4, 5, 6);
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
		assertNull(Overlay.of(model.getItems(),0).getPathInParent());
		assertEquals("A", Overlay.of(model.getEntries(), "A").getPathInParent());
	}

	private List<String> getEntryKeys() {
		return Lists.newArrayList(model.getEntries().keySet());
	}

	private void checkIntegers(Integer... integers) {
		assertEquals(Arrays.asList(integers), model.getIntegers());
	}

	private void checkNamedIntegerNames(String... names) {
		assertEquals(Arrays.asList(names), Lists.newArrayList(model.getNamedIntegers().keySet()));
	}

	private void checkNamedIntegers(Integer... integers) {
		assertEquals(Arrays.asList(integers), Lists.newArrayList(model.getNamedIntegers().values()));
	}
}
