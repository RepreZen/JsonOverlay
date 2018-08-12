package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;

public class PositionInfo {
	private final JsonPointer pointer;
	private final PositionEndpoint start;
	private final PositionEndpoint end;

	public PositionInfo(JsonPointer pointer, PositionEndpoint start, PositionEndpoint end) {
		this.pointer = pointer;
		this.start = start;
		this.end = end;
	}

	public PositionInfo(JsonPointer pointer, JsonLocation start, JsonLocation end) {
		this(pointer, new PositionEndpoint(start), new PositionEndpoint(end));
	}

	public PositionInfo(JsonPointer pointer, PositionEndpoint start, JsonLocation end) {
		this(pointer, start, new PositionEndpoint(end));
	}

	public JsonPointer getPointer() {
		return pointer;
	}

	public PositionEndpoint getStart() {
		return start;
	}

	public PositionEndpoint getEnd() {
		return end;
	}

	// convenience methods when only start endpoint is of interest
	public int getLine() {
		return start.getLine();
	}

	public int getColumn() {
		return start.getColumn();
	}

	@Override
	public String toString() {
		return String.format("[%s-%s]", start, end);
	}

	public String toString(boolean startOnly) {
		return startOnly ? String.format("[%s]", start) : toString();
	}

	public static class PositionEndpoint {
		private final long charOffset;
		private final int line;
		private final int column;

		public PositionEndpoint(long charOffset, int line, int column) {
			this.charOffset = charOffset;
			this.line = line;
			this.column = column;
		}

		public PositionEndpoint(JsonLocation location) {
			this(location.getCharOffset(), location.getLineNr(), location.getColumnNr());
		}

		public long getCharOffset() {
			return charOffset;
		}

		public int getLine() {
			return line;
		}

		public int getColumn() {
			return column;
		}

		@Override
		public String toString() {
			return String.format("%d:%d", line, column);
		}
	}
}
