package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class LocationProcessor {

	private final Map<JsonPointer, JsonRegion> locations = Maps.newHashMap();

	private JsonPointer ptr = JsonPointer.compile("");
	private boolean seenRoot = false;

	LocationProcessor() {
	}

	public Map<JsonPointer, JsonRegion> getLocations() {
		return ImmutableMap.copyOf(locations);
	}

	public void processTokenLocation(JsonToken token, JsonLocation location, JsonStreamContext context) {

		/*
		 * Handle root of document. We create a region that contains the whole document.
		 */
		if (!seenRoot) {
			locations.put(ptr, new JsonRegion(ptr, location, location));
			seenRoot = true;
			return;
		}

		/*
		 * We have reached end of document, so we update the root region end location.
		 */
		if (context.inRoot()) {
			JsonRegion region = locations.get(ptr);
			locations.put(ptr, new JsonRegion(region.getPointer(), region.getStart(), location));
			return;
		}

		/*
		 * If the end of a container, we update the end location of it's region and update the
		 * pointer to it's parent.
		 */
		if (token == JsonToken.END_OBJECT || token == JsonToken.END_ARRAY) {
			JsonRegion region = locations.get(ptr);
			locations.put(ptr, new JsonRegion(region.getPointer(), region.getStart(), location));

			ptr = ptr.head();

			return;
		}

		/*
		 * Nothing to do with fields.
		 */
		if (token == JsonToken.FIELD_NAME) {
			return;
		}

		final JsonStreamContext parent = context.getParent();

		/*
		 * Beginning of a container.
		 */
		if (token == JsonToken.START_ARRAY || token == JsonToken.START_OBJECT) {
			ptr = getPointer(parent, ptr);
			locations.put(ptr, new JsonRegion(ptr, location, location));
			return;
		}

		/*
		 * Normal case, build a region for the pointer.
		 */
		final JsonPointer entryPointer = getPointer(context, ptr);

		JsonRegion range = new JsonRegion(entryPointer, location, location);
		locations.put(entryPointer, range);
	}

	private JsonPointer getPointer(JsonStreamContext context, JsonPointer ptr) {
		if (context.inArray())
			return ptr.append(JsonPointer.compile("/" + context.getCurrentIndex()));
		else
			return ptr.append(JsonPointer.compile("/" + context.getCurrentName()));
	}
}
