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
package com.reprezen.jovl2;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class OverlayFactory<V> {

	private final Class<? extends IJsonOverlay<? super V>> overlayClass = getOverlayClass();

	public JsonOverlay<V> create(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		JsonOverlay<V> overlay = _create(value, parent, refReg);
		overlay.elaborate();
		return overlay;
	}

	public JsonOverlay<V> create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		JsonOverlay<V> overlay = _create(json, parent, refReg);
		overlay.elaborate();
		return overlay;
	}

	public boolean isCompatible(JsonOverlay<?> overlay) {
		return overlayClass.isAssignableFrom(overlay.getClass());
	}

	protected abstract Class<? extends JsonOverlay<? super V>> getOverlayClass();

	protected abstract JsonOverlay<V> _create(V value, JsonOverlay<?> parent, ReferenceRegistry refReg);

	protected abstract JsonOverlay<V> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg);
}
