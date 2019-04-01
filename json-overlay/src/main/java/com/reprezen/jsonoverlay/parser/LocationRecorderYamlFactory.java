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
package com.reprezen.jsonoverlay.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

public class LocationRecorderYamlFactory extends YAMLFactory {

	private static final long serialVersionUID = 1L;

	@Override
	protected YAMLParser _createParser(InputStream in, IOContext ctxt) throws IOException {
		return new LocationRecorderYamlParser(ctxt, _getBufferRecycler(), _parserFeatures, _yamlParserFeatures,
				_objectCodec, _createReader(in, null, ctxt));
	}

	@Override
	protected YAMLParser _createParser(Reader r, IOContext ctxt) {
		return new LocationRecorderYamlParser(ctxt, _getBufferRecycler(), _parserFeatures, _yamlParserFeatures,
				_objectCodec, r);
	}
}
