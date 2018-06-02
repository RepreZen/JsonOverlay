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

public final class BooleanOverlay extends ScalarOverlay<Boolean> {

	private BooleanOverlay(Boolean value, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
	}

	private BooleanOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Override
	protected Boolean _fromJson(JsonNode json) {
		return json.isBoolean() ? json.booleanValue() : null;
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		return value != null ? _jsonBoolean(value) : _jsonMissing();
	}

	public static OverlayFactory<Boolean> factory = new OverlayFactory<Boolean>() {
		@Override
		protected Class<BooleanOverlay> getOverlayClass() {
			return BooleanOverlay.class;
		}

		@Override
		public BooleanOverlay _create(Boolean value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new BooleanOverlay(value, parent, refMgr);
		}

		@Override
		public BooleanOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new BooleanOverlay(json, parent, refMgr);
		}
	};
}