package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;

public class JsonRegion {
	private final JsonPointer pointer;
	private final JsonLocation start;
	private final JsonLocation end;

	public JsonRegion(JsonPointer pointer, JsonLocation start, JsonLocation end) {
		this.pointer = pointer;
		this.start = start;
		this.end = end;
	}

	public JsonPointer getPointer() {
		return pointer;
	}

	public JsonLocation getStart() {
		return start;
	}

	public JsonLocation getEnd() {
		return end;
	}
}
