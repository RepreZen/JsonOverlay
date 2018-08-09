package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class LocationRecorderYamlParser extends YAMLParser {

	private final LocationProcessor processor = new LocationProcessor();

	public LocationRecorderYamlParser(IOContext ctxt, BufferRecycler br, int parserFeatures, int formatFeatures, ObjectCodec codec, Reader reader) {
		super(ctxt, br, parserFeatures, formatFeatures, codec, reader);
	}

	public Map<JsonPointer, JsonRegion> getLocations() {
		return processor.getLocations();
	}

	@Override
	public JsonToken nextToken() throws IOException {
		JsonToken token = super.nextToken();
		processor.processTokenLocation(token, getCurrentLocation(), getParsingContext());

		return token;
	}
}
