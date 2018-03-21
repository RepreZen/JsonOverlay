/*******************************************************************************
 *  Copyright (c) 2017 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.jsonoverlay;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class ScalarOverlay<V> extends JsonOverlay<V> {

	public ScalarOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
	}

	public ScalarOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(value, parent, refReg);
	}

	public JsonOverlay<?> _findInternal(JsonPointer path) {
		return null;
	}

	@Override
	public V _get(boolean elaborate) {
		return value;
	}
}
