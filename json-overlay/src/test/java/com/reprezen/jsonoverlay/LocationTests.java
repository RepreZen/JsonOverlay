/*********************************************************************
*  Copyright (c) 2017 ModelSolv, Inc. and others.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     ModelSolv, Inc. 
 *     - initial API and implementation and/or initial documentation
**********************************************************************/
package com.reprezen.jsonoverlay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Resources;
import com.reprezen.jsonoverlay.model.TestModelParser;
import com.reprezen.jsonoverlay.model.intf.Scalars;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class LocationTests {

	@Test
	public void testYamlLocation() throws IOException {
		Pair<JsonNode, Map<JsonPointer, PositionInfo>> result = new JsonLoader().loadWithLocations(
				Resources.toString(Resources.getResource("locationsTest.yaml"), Charset.forName("utf8")));

		Map<JsonPointer, PositionInfo> locations = result.getRight();

		assertTrue(locations.containsKey(JsonPointer.compile("/map/a")));

		PositionInfo region = locations.get(JsonPointer.compile("/map/a"));
		assertEquals(5, region.getStart().getLine());
		assertEquals(5, region.getStart().getColumn());
		assertEquals(6, region.getEnd().getLine());
		assertEquals(3, region.getEnd().getColumn());
	}

	@Test
	public void testPositions() throws IOException {
		TestModel model = TestModelParser.parse(Resources.getResource("refTest.yaml"));
		checkPositions(model, 1, 1, 53, 35);
		{
			Overlay<StringOverlay> desc = Overlay.of(model, "description", StringOverlay.class);
			checkPositions(desc, 1, 14, 1, 28);
		}
		{
			Overlay<Map<String, Scalars>> scalars = Overlay.of(model.getScalars());
			checkPositions(scalars, 3, 3, 53, 35);
		}
		{
			Overlay<IntegerOverlay> s2intRef = Overlay.of(model.getScalar("s2"), "intValue", IntegerOverlay.class);
			checkPositions(s2intRef, 15, 7, 16, 5);
		}
		{
			Overlay<IntegerOverlay> s2Int = Overlay.of(model.getScalar("s2"), "intValue", IntegerOverlay.class)
					.getReferenceOverlay();
			checkPositions(s2Int, 5, 15, 5, 17);
		}
		{
			Overlay<Scalars> ext1Ref = Overlay.of(model.getScalars(), "ext1");
			checkPositions(ext1Ref, 49, 5, 50, 3);
		}
		{
			Overlay<Scalars> ext1Obj = Overlay.of(model.getScalars(), "ext1").getReferenceOverlay();
			checkPositions(ext1Obj, 2, 3, 9, 1);
		}

	}

	private <V, T extends IJsonOverlay<V>> void checkPositions(T overlay, int startLine, int startCol, int endLine,
			int endCol) {
		checkPositions(Overlay.of(overlay), startLine, startCol, endLine, endCol);
	}

	private void checkPositions(Overlay<?> overlay, int startLine, int startCol, int endLine, int endCol) {
		Optional<PositionInfo> pos = overlay.getPositionInfo();
		assertTrue(pos.isPresent());
		assertEquals(startLine, pos.get().getLine());
		assertEquals(startCol, pos.get().getColumn());
		assertEquals(endLine, pos.get().getEnd().getLine());
		assertEquals(endCol, pos.get().getEnd().getColumn());
	}

}
