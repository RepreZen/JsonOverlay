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

import com.fasterxml.jackson.databind.JsonNode;

public abstract class OverlayFactory<V> {

	private final Class<? extends IJsonOverlay<? super V>> overlayClass = getOverlayClass();

	public JsonOverlay<V> create(V value, JsonOverlay<?> parent, ReferenceManager refMgr) {
		JsonOverlay<V> overlay = _create(value, parent, refMgr);
		overlay._elaborate(true);
		return overlay;
	}

	@SuppressWarnings("unchecked")
	public JsonOverlay<V> create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		JsonOverlay<V> overlay;
		if (Reference.isReferenceNode(json)) {
			Reference reference = refMgr.getReference(json);
			RefOverlay<V> refOverlay = new RefOverlay<V>(reference, parent, this, refMgr);
			overlay = refOverlay.getOverlay();
			if (overlay == null) {
				overlay = _create((V) null, null, refMgr);
			}
			if (overlay != null) {
				overlay = ((OverlayFactory<V>) overlay._getFactory())._create((V) null, null, refMgr);
				overlay._setReference(refOverlay);
			}
		} else {
			JsonOverlay<?> existing = refMgr.getRegistry().getOverlay(json, getSignature());
			if (existing != null) {
				overlay = (JsonOverlay<V>) existing;
				if (parent != null) {
					overlay._setParent(parent);
				}
			} else {
				overlay = _create(json, parent, refMgr);
				overlay._setParent(parent);
				refMgr.getRegistry().register(json, getSignature(), overlay);
				overlay._elaborate(true);
			}
		}
		return overlay;
	}

	public boolean isCompatible(JsonOverlay<?> overlay) {
		return overlayClass.isAssignableFrom(overlay.getClass());
	}

	public String getSignature() {
		return getOverlayClass().getSimpleName();
	}

	protected boolean isExtendedType() {
		return false;
	}

	protected abstract Class<? extends JsonOverlay<? super V>> getOverlayClass();

	protected abstract JsonOverlay<V> _create(V value, JsonOverlay<?> parent, ReferenceManager refMgr);

	protected abstract JsonOverlay<V> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr);
}
