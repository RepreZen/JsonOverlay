package com.reprezen.jsonoverlay.parser;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.reprezen.jsonoverlay.PositionInfo;

public class LocationProcessor {

	private final Map<JsonPointer, PositionInfo> locations = new HashMap<>();

	private Deque<Context> contextStack = new ArrayDeque<Context>();
	private Context context; // current context, never on the stack

	LocationProcessor() {
		context = new RootContext();
	}

	public Map<JsonPointer, PositionInfo> getLocations() {
		return Collections.unmodifiableMap(locations);
	}

	public void processTokenLocation(JsonToken token, String name, JsonLocation tokenStart, JsonLocation tokenEnd) {
		switch (token) {
		case FIELD_NAME:
			context.setNextProp(name);
			break;

		case START_OBJECT:
			context.startValue();
			contextStack.push(context);
			context = new ObjectContext(context, tokenStart);
			break;

		case END_OBJECT:
			recordInfo(context, tokenEnd);
			if (!(context instanceof ObjectContext)) {
				throw new IllegalStateException();
			}
			context = contextStack.pop();
			break;

		case START_ARRAY:
			context.startValue();
			contextStack.push(context);
			context = new ArrayContext(context, tokenStart);
			break;

		case END_ARRAY:
			recordInfo(context, tokenEnd);
			if (!(context instanceof ArrayContext)) {
				throw new IllegalStateException();
			}
			context = contextStack.pop();
			break;

		case NOT_AVAILABLE:
		case VALUE_EMBEDDED_OBJECT:
			throw new IllegalStateException();

		default: // all the other cases represent scalar values
			context.startValue();
			recordInfo(context.getChildPointer(), tokenStart, tokenEnd);
			break;
		}
	}

	private void recordInfo(Context context, JsonLocation end) {
		recordInfo(context.getPointer(), context.getStart(), end);
	}

	private void recordInfo(JsonPointer ptr, JsonLocation start, JsonLocation end) {
		PositionInfo pos = new PositionInfo(ptr, start, end);
		locations.put(ptr, pos);
	}

	private static abstract class Context {
		protected JsonPointer ptr;
		protected JsonLocation start;
		protected JsonLocation end;

		public Context(JsonPointer ptr, JsonLocation start, JsonLocation end) {
			super();
			this.ptr = ptr;
			this.start = start;
			this.end = end;
		}

		public JsonPointer getPointer() {
			return ptr;
		}

		public JsonLocation getStart() {
			return start;
		}

		public JsonLocation getEnd() {
			return end;
		}

		public abstract JsonPointer getChildPointer();

		public abstract void startValue();

		public abstract void setNextProp(String name);
	}

	private static class RootContext extends Context {
		public RootContext() {
			super(JsonPointer.compile(""), null, null);
		}

		@Override
		public JsonPointer getChildPointer() {
			return ptr;
		}

		@Override
		public void startValue() {
		}

		@Override
		public void setNextProp(String name) {
			throw new IllegalStateException();
		}
	}

	private static class ObjectContext extends Context {
		private String currentProp = null;
		private String nextProp = null;

		public ObjectContext(Context parent, JsonLocation start) {
			super(parent.getChildPointer(), start, null);
		}

		@Override
		public JsonPointer getChildPointer() {
			if (currentProp != null) {
				JsonPointer child = JsonPointer.compile("/" + encode(currentProp));
				return ptr.append(child);
			} else {
				throw new IllegalStateException();
			}
		}

		private String encode(String s) {
			return s.replaceAll("~", "~0").replaceAll("/", "~1");
		}

		@Override
		public void startValue() {
			if (nextProp != null) {
				this.currentProp = nextProp;
				nextProp = null;
			} else {
				throw new IllegalStateException();
			}
		}

		@Override
		public void setNextProp(String name) {
			if (nextProp == null) {
				this.nextProp = name;
			} else {
				throw new IllegalStateException();
			}
		}
	}

	private static class ArrayContext extends Context {
		private int currentIndex = -1;

		public ArrayContext(Context parent, JsonLocation start) {
			super(parent.getChildPointer(), start, null);
		}

		@Override
		public JsonPointer getChildPointer() {
			if (currentIndex >= 0) {
				return ptr.append(JsonPointer.compile("/" + currentIndex));
			} else {
				throw new IllegalStateException();
			}
		}

		@Override
		public void startValue() {
			this.currentIndex += 1;
		}

		@Override
		public void setNextProp(String name) {
			throw new IllegalStateException();
		}
	}
}