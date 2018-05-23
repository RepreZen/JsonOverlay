package com.reprezen.jsonoverlay.test;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Resources;
import com.reprezen.jsonoverlay.JsonLoader;
import com.reprezen.jsonoverlay.parser.JsonRegion;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationTest {

	@Test
	public void testYamlLocation() throws IOException {
		Pair<JsonNode, Map<JsonPointer, JsonRegion>> result = new JsonLoader().loadWithLocations(Resources.toString(
				Resources.getResource("referenceTests/main.yaml"), Charset.forName("utf8")));

		Map<JsonPointer, JsonRegion> locations = result.getRight();

		assertTrue(locations.containsKey(JsonPointer.compile("/map/a")));

		JsonRegion region = locations.get(JsonPointer.compile("/map/a"));
		assertEquals(5, region.getStart().getLineNr());
		assertEquals(5, region.getStart().getColumnNr());
		assertEquals(6, region.getEnd().getLineNr());
		assertEquals(3, region.getEnd().getColumnNr());
	}
}
