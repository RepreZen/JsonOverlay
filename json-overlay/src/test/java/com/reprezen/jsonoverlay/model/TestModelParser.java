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
package com.reprezen.jsonoverlay.model;

import java.io.IOException;
import java.net.URL;

import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.model.impl.TestModelImpl;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		ReferenceManager manager = new ReferenceManager(url);
		TestModel model = (TestModel) TestModelImpl.factory.create(manager.loadDoc(), null, manager);
		((TestModelImpl) model)._setCreatingRef(manager.getReference(url.toString()));
		return Overlay.of(model).get();
	}
}
