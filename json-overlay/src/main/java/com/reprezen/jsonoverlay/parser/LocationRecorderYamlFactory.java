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
