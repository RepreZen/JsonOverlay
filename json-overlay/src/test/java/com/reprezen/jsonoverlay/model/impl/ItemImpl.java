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
import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.model.intf.Item;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class ItemImpl extends PropertiesOverlay<Item> implements Item {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ItemImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public ItemImpl(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(item, parent, factory, refMgr);
	}

	// Title
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getTitle() {
		return _get("title", String.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setTitle(String title) {
		_setScalar("title", title, String.class);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_title = "title";

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void _elaborateJson() {
		super._elaborateJson();
		_createScalar("title", "title", StringOverlay.factory);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<Item> factory = new OverlayFactory<Item>() {

		@Override
		protected Class<? extends JsonOverlay<? super Item>> getOverlayClass() {
			return ItemImpl.class;
		}

		@Override
		public JsonOverlay<Item> _create(Item item, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ItemImpl(item, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<Item> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new ItemImpl(json, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<Item> castOverlay = (JsonOverlay<Item>) overlay;
			return castOverlay;
		}

		@Override
		protected boolean isExtendedType() {
			return false;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Item> getSubtypeOf(Item item) {
		return Item.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends Item> getSubtypeOf(JsonNode json) {
		return Item.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected OverlayFactory<?> _getFactory() {
		return factory;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> Builder<Item> builder(OV modelMember) {
		return new Builder<Item>(factory, modelMember);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static <OV extends IJsonOverlay<?>> Item create(OV modelMember) {
		return (Item) builder(modelMember).build();
	}
}
