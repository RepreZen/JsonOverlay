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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class ListTests extends Assert {

	private List<Integer> data = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	private OverlayFactory<List<Integer>> factory = ListOverlay.getFactory(IntegerOverlay.factory);

	private final ReferenceManager refMgr = new ReferenceManager(null);

	private final JsonNodeFactory jfac = JsonNodeFactory.instance;

	@Test
	public void testListFromValues() {
		doChecks((ListOverlay<Integer>) factory.create(data, null, refMgr));
	}

	@Test
	public void testListFromJson() {
		ArrayNode json = jfac.arrayNode();
		for (int i : data) {
			json.add(i);
		}
		doChecks((ListOverlay<Integer>) factory.create(json, null, refMgr));
	}

	private void doChecks(ListOverlay<Integer> overlay) {
		// initial content 0..9
		assertEquals(10, overlay.size());
		for (int i = 0; i < 10; i++) {
			checkValueAt(overlay, i, i);
		}
		overlay.remove(0);
		overlay.remove(4);
		overlay.remove(7);
		try {
			overlay.remove(7);
			fail("Removal past end of list did not throw");
		} catch (IndexOutOfBoundsException e) {
		}
		// now should be 1..4,6..8
		assertEquals(7, overlay.size());
		checkValueAt(overlay, 0, 1);
		checkValueAt(overlay, 4, 6);
		checkValueAt(overlay, 6, 8);

		overlay.set(0, 0);
		// now 0,2..4,6..8
		checkValueAt(overlay, 0, 0);
		overlay.insert(1, 1);
		// now 0..4,6..8
		checkValueAt(overlay, 1, 1);
		overlay.insert(5, 5);
		// now 0..8
		overlay.add(9);
		// now 0..9
		assertEquals(10, overlay.size());
		for (int i = 0; i < 10; i++) {
			checkValueAt(overlay, i, i);
		}
		ListOverlay<Integer> copy = (ListOverlay<Integer>) overlay._copy();
		assertFalse("Copy operation should create different object", overlay == copy);
		assertEquals(overlay, copy);
		for (int i = 0; i < overlay.size(); i++) {
			assertFalse("Copy operation should create copies of list overlay items",
					overlay._getOverlay(i) == copy._getOverlay(i));
		}
		copy = (ListOverlay<Integer>) overlay.factory.create(overlay._toJson(), null, refMgr);
		assertEquals(overlay._get(), copy._get());
		assertTrue(overlay == overlay._getRoot());
		JsonOverlay<Integer> itemOverlay = overlay._getOverlay(0);
		assertTrue(overlay == itemOverlay._getRoot());
		assertNull(Overlay.of(overlay).getModel());
	}

	private void checkValueAt(ListOverlay<Integer> overlay, int index, int value) {
		assertEquals(Integer.valueOf(value), overlay.get(index));
	}
}
