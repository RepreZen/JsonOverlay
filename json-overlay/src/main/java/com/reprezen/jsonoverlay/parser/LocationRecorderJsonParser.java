package com.reprezen.jsonoverlay.parser;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.reprezen.jsonoverlay.PositionInfo;

public final class LocationRecorderJsonParser extends JsonParserDelegate {

	private final LocationProcessor processor = new LocationProcessor();

	public LocationRecorderJsonParser(final JsonParser d) {
		super(d);
	}

	@Override
	public JsonToken nextToken() throws IOException {
		JsonToken token = super.nextToken();
		processor.processTokenLocation(token, getTokenLocation(), getCurrentLocation(), getParsingContext());
		return token;
	}

	public Map<JsonPointer, PositionInfo> getLocations() {
		return processor.getLocations();
	}

}