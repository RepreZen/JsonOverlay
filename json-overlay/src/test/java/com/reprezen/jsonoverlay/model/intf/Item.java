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
package com.reprezen.jsonoverlay.model.intf;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface Item extends IJsonOverlay<Item>, IModelPart<TestModel, Item> {

	// Title
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getTitle();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setTitle(String title);
}
