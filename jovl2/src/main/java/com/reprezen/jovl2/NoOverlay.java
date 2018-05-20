package com.reprezen.jovl2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;

public final class NoOverlay<V> extends JsonOverlay<V> {

	private NoOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(MissingNode.getInstance(), parent, refReg);
	}

	private NoOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super((V) null, parent, refReg);
	}

	@Override
	protected V _fromJson(JsonNode json) {
		return null;
	}

	@Override
	protected JsonNode _toJsonInternal(SerializationOptions options) {
		return MissingNode.getInstance();
	}

	public static OverlayFactory<Object> factory = new OverlayFactory<Object>() {

		@Override
		protected Class<? extends JsonOverlay<? super Object>> getOverlayClass() {
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<? super Object>> fac = (Class<? extends JsonOverlay<? super Object>>) NoOverlay.class;
			return fac;
		}

		@Override
		protected JsonOverlay<Object> _create(Object value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new NoOverlay<Object>(value, parent, refReg);
		}

		@Override
		protected JsonOverlay<Object> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new NoOverlay<Object>(json, parent, refReg);
		}

	};
}
