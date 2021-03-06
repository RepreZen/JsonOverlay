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
package com.reprezen.jsonoverlay.model.impl;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.Builder;
import com.reprezen.jsonoverlay.EnumOverlay;
import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.model.intf.Color;

public class ColorImpl extends EnumOverlay<Color> {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ColorImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ColorImpl(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(color, parent, factory, refMgr);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected Class<Color> getEnumClass() {
		return Color.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<Color> factory = new OverlayFactory<Color>() {

		@Override
		protected Class<? extends JsonOverlay<? super Color>> getOverlayClass() {
			return ColorImpl.class;
		}

		@Override
		public JsonOverlay<Color> _create(Color color, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ColorImpl(color, parent, refMgr);
		}

		@Override
		public JsonOverlay<Color> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			return new ColorImpl(json, parent, refMgr);
		}
	};

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected OverlayFactory<?> _getFactory() {
		return factory;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> Builder<Color> builder(OV modelMember) {
		return new Builder<Color>(factory, modelMember);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> IJsonOverlay<Color> create(OV modelMember) {
		return builder(modelMember).build();
	}
}
