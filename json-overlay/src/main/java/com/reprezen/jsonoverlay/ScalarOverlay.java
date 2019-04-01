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

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class ScalarOverlay<V> extends JsonOverlay<V> {

	protected ScalarOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	protected ScalarOverlay(V value, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
	}

	public JsonOverlay<?> _findInternal(JsonPointer path) {
		return null;
	}
}
