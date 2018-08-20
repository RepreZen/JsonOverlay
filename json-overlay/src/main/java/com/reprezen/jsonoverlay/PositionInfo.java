package com.reprezen.jsonoverlay;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;

public class PositionInfo {
	private final JsonPointer pointer;
	private final PositionEndpoint start;
	private final PositionEndpoint end;
	private String documentUrl = null;

	/**
	 * Records the position of a JSON value.
	 * 
	 * Depending on the value type, this may be an intermediate value, including
	 * only the position of the beginning of the value. This is the case for the
	 * document as a whole in all cases, and also for any containers (objects or
	 * arrays), whose end positions are detected later in the parse.
	 * 
	 * @param pointer
	 *            JsonPointer uniquely identifying this value in the oveall document
	 * @param start
	 *            position data for start of value
	 * @param end
	 *            position data for end of value if known, else repeat of start data
	 */
	public PositionInfo(JsonPointer pointer, JsonLocation start, JsonLocation end) {
		this(pointer, new PositionEndpoint(start), new PositionEndpoint(end));
	}

	/**
	 * Records the position of a JSON value.
	 * 
	 * This is used to update a provisional instance that was created when the end
	 * of the value had not yet been detected. The start data is supplied in its
	 * processed form, while the end data comes in the raw form provided by the
	 * parser.
	 * 
	 * @param pointer
	 *            JsonPointer uniquely identifying this value in the overall
	 *            document
	 * @param start
	 *            position data for start of value, in PositionEndpoint form
	 * @param end
	 *            position data for end of value, in raw JsonLocation form
	 */
	public PositionInfo(JsonPointer pointer, PositionEndpoint start, JsonLocation end) {
		this(pointer, start, new PositionEndpoint(end));
	}

	public PositionInfo(JsonPointer pointer, PositionEndpoint start, PositionEndpoint end) {
		this.pointer = pointer;
		this.start = start;
		this.end = end;
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

	public String getDocumentUrl() {
		return documentUrl;
	}

	/**
	 * Sets the document URL for this instance.
	 * 
	 * This is accessible within the package, so it can be used by the JsonOverlay
	 * package that obtains instances on demand for associated overlay objects. The
	 * document URL is not available to the JSON/YAML parser that actually create
	 * the insstances, so it must be filled in later.
	 * 
	 * @param documentUrl
	 */
	/* package */void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
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
		return String.format("%s[%s-%s]", documentUrl, start, end);
	}

	public String toString(boolean startOnly) {
		return startOnly ? String.format("%s[%s]", documentUrl, start) : toString();
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
