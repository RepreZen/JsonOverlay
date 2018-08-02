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

public class StringOverlay extends ScalarOverlay<String> {

    private StringOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
        super(json, parent, refReg);
    }

    private StringOverlay(String value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
        super(value, parent, refReg);
    }

    @Override
    protected String _fromJson(JsonNode json) {
        return json.isTextual() ? json.textValue() : null;
    }

    @Override
    protected JsonNode _toJsonInternal(SerializationOptions options) {
        return value != null ? _jsonScalar(value) : _jsonMissing();
    }

    public static OverlayFactory<String> factory = new OverlayFactory<String>() {

		@Override
		protected Class<StringOverlay> getOverlayClass() {
			return StringOverlay.class;
		}

		@Override
		public StringOverlay _create(String value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new StringOverlay(value, parent, refReg);
		}

		@Override
		public StringOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new StringOverlay(json, parent, refReg);
		}

	};
}
