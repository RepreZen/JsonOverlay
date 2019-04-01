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

import com.fasterxml.jackson.databind.JsonNode;

public final class IntegerOverlay extends ScalarOverlay<Integer> {

	private IntegerOverlay(Integer value, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(value, parent, factory, refMgr);
	}

	private IntegerOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Override
	protected Integer _fromJson(JsonNode json) {
		return json.isInt() ? json.intValue() : null;
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		return value != null ? _jsonScalar(value) : _jsonMissing();
	}

	@Override
	protected OverlayFactory<Integer> _getFactory() {
		return factory;
	}

	public static OverlayFactory<Integer> factory = new OverlayFactory<Integer>() {
		@Override
		protected Class<IntegerOverlay> getOverlayClass() {
			return IntegerOverlay.class;
		}

		@Override
		public IntegerOverlay _create(Integer value, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new IntegerOverlay(value, parent, refMgr);
		}

		@Override
		public IntegerOverlay _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new IntegerOverlay(json, parent, refMgr);
		}
	};

	public static Builder<Integer> builder(JsonOverlay<?> modelMember) {
		return new Builder<Integer>(factory, modelMember);
	}

	public static JsonOverlay<Integer> create(JsonOverlay<?> modelMember) {
		return builder(modelMember).build();
	}

	public static JsonOverlay<Integer> create(int value, JsonOverlay<?> modelMember) {
		JsonOverlay<Integer> result = create(modelMember);
		result._set(value);
		return result;
	}
}
