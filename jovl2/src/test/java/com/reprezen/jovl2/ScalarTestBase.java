package com.reprezen.jovl2;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;

public abstract class ScalarTestBase<V> extends Assert {

	private ReferenceRegistry refReg = new ReferenceRegistry();
	private OverlayFactory<V> factory;
	protected static JsonNodeFactory jfac = JsonNodeFactory.instance;
	V value;

	public ScalarTestBase(OverlayFactory<V> factory) {
		this.factory = factory;
	}

	protected abstract JsonNode toJson(V value);

	@Test
	public void testOvlValueFromJson() {
		JsonNode json = toJson(value);
		testWithJson(json, value);
	}

	@Test
	public void testOvlValueFromValue() {
		JsonOverlay<V> ovl = (JsonOverlay<V>) factory.create(value, null, refReg);
		assertTrue(factory.getOverlayClass().isAssignableFrom(ovl.getClass()));
		assertEquals(value, ovl._get());
		testCopy(ovl);
	}

	@Test
	public void testWithNull() {
		value = null;
		testOvlValueFromJson();
		testOvlValueFromValue();
	}

	@Test
	public void testWithMissingJson() {
		testWithJson(MissingNode.getInstance(), null);
	}

	@Test
	public void testWithWrongJson() {
		testWithJson(jfac.objectNode(), null);
	}

	public void testWithJson(JsonNode json, V val) {
		JsonOverlay<V> ovl = (JsonOverlay<V>) factory.create(json, null, refReg);
		assertTrue(factory.getOverlayClass().isAssignableFrom(ovl.getClass()));
		assertEquals(val, ovl._get());
		testCopy(ovl);
	}
	
	public void testCopy(JsonOverlay<V> ovl) {
		JsonOverlay<V> copy = ovl._copy();
		assertFalse("Copy operation should yield different object", ovl == copy);
		assertEquals(ovl, copy);
		assertEquals(ovl._get(), copy._get());
	}
}
