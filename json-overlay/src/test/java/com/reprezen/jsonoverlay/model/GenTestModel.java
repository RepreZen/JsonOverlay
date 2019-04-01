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

import com.reprezen.jsonoverlay.gen.CodeGenerator;

public class GenTestModel {

	public static void main(String[] args) throws Exception {
		CodeGenerator.main(new String[] { //
				"-t", "src/test/java/com/reprezen/jsonoverlay/model/types.yaml", //
				"-p", "com.reprezen.jsonoverlay.model", //
				"-d", "src/test/java/com/reprezen/jsonoverlay/model", //
				"-i", "intf", //
				"-I", "intf", //
				"-c", "impl", //
				"-C", "impl" //
		});
	}
}
