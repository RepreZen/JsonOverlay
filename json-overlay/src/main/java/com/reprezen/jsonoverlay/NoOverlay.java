package com.reprezen.jsonoverlay;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;

public class NoOverlay<V> extends JsonOverlay<V> {

	public NoOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(MissingNode.getInstance(), parent, refReg);
	}

	public NoOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super((V) null, parent, refReg);
	}

	@Override
	protected AbstractJsonOverlay<?> _findInternal(JsonPointer path) {
		return null;
	}

	@Override
	protected V fromJson(JsonNode json) {
		return null;
	}

	@Override
	JsonNode _toJsonInternal(SerializationOptions options) {
		return MissingNode.getInstance();
	}
	
	
}
