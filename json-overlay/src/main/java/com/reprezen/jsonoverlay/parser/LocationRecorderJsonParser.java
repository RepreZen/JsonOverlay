package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;

import java.io.IOException;
import java.util.Map;

public final class LocationRecorderJsonParser extends JsonParserDelegate {

	private final LocationProcessor processor = new LocationProcessor();

	public LocationRecorderJsonParser(final JsonParser d) {
		super(d);
	}

	@Override
	public JsonToken nextToken() throws IOException {
		JsonToken token = super.nextToken();
		processor.processTokenLocation(token, getCurrentLocation(), getParsingContext());

		return token;
	}

	public Map<JsonPointer, JsonRegion> getLocations() {
		return processor.getLocations();
	}

}