package com.reprezen.jovl2;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.google.common.collect.Lists;

@RunWith(Parameterized.class)
public class NoOverlayTests extends Assert {

	private Object value;

	public NoOverlayTests(Object value) {
		this.value = value;
	}

	@Parameters
	public static Collection<Object> getValues() {
		return Arrays.asList(null, "hello", 1, Lists.newArrayList(1, 2, 3), new File("/a/b/c"));
	}

	private OverlayFactory<Object> factory = NoOverlay.factory;
	private ReferenceRegistry refReg = new ReferenceRegistry();

	@Test
	public void testAlwasyNullFromValue() {
		NoOverlay<?> overlay = (NoOverlay<?>) factory.create(value, null, refReg);
		assertNull(overlay.get());
	}

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAlwaysNullFromJson() {
		JsonNode json = value != null ? mapper.convertValue(value, JsonNode.class) : NullNode.instance;
		NoOverlay<?> overlay = (NoOverlay<?>) factory.create(json,  null,  refReg);
		assertNull(overlay.get());
	}

}