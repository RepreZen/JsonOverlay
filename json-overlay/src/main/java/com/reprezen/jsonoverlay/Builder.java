package com.reprezen.jsonoverlay;

public class Builder<V> {
	private OverlayFactory<?> factory;
	private JsonOverlay<?> modelMember;

	public <OV extends IJsonOverlay<?>> Builder(OverlayFactory<?> factory, OV modelMember) {
		this.factory = factory;
		this.modelMember = (JsonOverlay<?>) modelMember;
	}

	public JsonOverlay<V> build() {
		ReferenceManager refMgr = modelMember != null ? modelMember.refMgr : new ReferenceManager();
		@SuppressWarnings("unchecked")
		JsonOverlay<V> castResult = (JsonOverlay<V>) factory.create(JsonOverlay._jsonObject(), null, refMgr);
		return castResult;
	}
}
