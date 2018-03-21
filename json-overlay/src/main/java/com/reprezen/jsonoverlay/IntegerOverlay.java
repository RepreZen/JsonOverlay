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

public class IntegerOverlay extends ScalarOverlay<Integer> {

	private IntegerOverlay(Integer value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(value, parent, refReg);
	}

	private IntegerOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
	}

	@Override
	public Integer fromJson(JsonNode json) {
		return json.isInt() ? json.intValue() : null;
	}

	@Override
	public JsonNode toJson(SerializationOptions options) {
		return value != null ? jsonScalar(value) : jsonMissing();
	}

	public static OverlayFactory<Integer> factory = new OverlayFactory<Integer>() {
		@Override
		protected Class<IntegerOverlay> getOverlayClass() {
			return IntegerOverlay.class;
		}

		@Override
		public IntegerOverlay _create(Integer value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new IntegerOverlay(value, parent, refReg);
		}

		@Override
		public IntegerOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new IntegerOverlay(json, parent, refReg);
		}
	};
}
