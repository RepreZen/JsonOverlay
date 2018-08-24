package com.reprezen.jsonoverlay.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.reprezen.jsonoverlay.PositionInfo;

public class LocationProcessor {

	private final Map<JsonPointer, PositionInfo> locations = new HashMap<>();

	private JsonPointer ptr = JsonPointer.compile("");
	private boolean seenRoot = false;

	LocationProcessor() {
	}

	public Map<JsonPointer, PositionInfo> getLocations() {
		return Collections.unmodifiableMap(locations);
	}

	public void processTokenLocation(JsonToken token, JsonLocation tokenStart, JsonLocation tokenEnd,
			JsonStreamContext context) {

		final JsonStreamContext parent = context.getParent();

		/*
		 * Handle root of document. We create a region that contains the whole document.
		 */
		if (!seenRoot) {
			locations.put(ptr, new PositionInfo(ptr, tokenStart, tokenStart));
			seenRoot = true;
			return;
		}

		/*
		 * We have reached end of document, so we update the root region end location.
		 */
		if (context.inRoot()) {
			PositionInfo region = locations.get(ptr);
			locations.put(ptr, new PositionInfo(region.getPointer(), region.getStart(), tokenEnd));
			return;
		}

		/*
		 * Beginning of a container.
		 */
		if (token == JsonToken.START_ARRAY || token == JsonToken.START_OBJECT) {
			ptr = getPointer(parent, ptr);
			locations.put(ptr, new PositionInfo(ptr, tokenEnd, tokenEnd));
			return;
		}

		/*
		 * If the end of a container, we update the end location of it's region and
		 * update the pointer to it's parent.
		 */
		if (token == JsonToken.END_OBJECT || token == JsonToken.END_ARRAY) {
			PositionInfo region = locations.get(ptr);
			locations.put(ptr, new PositionInfo(region.getPointer(), region.getStart(), tokenEnd));

			ptr = ptr.head();

			return;
		}

		/*
		 * Nothing to do with fields.
		 */
		if (token == JsonToken.FIELD_NAME) {
		}

		/*
		 * Normal case, build a region for the pointer.
		 */
		final JsonPointer entryPointer = getPointer(context, ptr);
		PositionInfo range = new PositionInfo(entryPointer, tokenStart, tokenEnd);
		locations.put(entryPointer, range);
	}

	private JsonPointer getPointer(JsonStreamContext context, JsonPointer ptr) {
		if (context.inArray())
			return ptr.append(JsonPointer.compile("/" + context.getCurrentIndex()));
		else
			return ptr.append(JsonPointer.compile("/" + encode(context.getCurrentName())));
	}

	private String encode(String name) {
		return name.replaceAll("~", "~0").replaceAll("/", "~1");
	}
}
