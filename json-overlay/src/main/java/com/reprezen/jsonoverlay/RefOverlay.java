package com.reprezen.jsonoverlay;

import com.fasterxml.jackson.databind.JsonNode;

public class RefOverlay<V> {

	private Reference reference;
	private JsonOverlay<?> parent;
	private JsonOverlay<V> target = null;
	private OverlayFactory<V> factory;
	private ReferenceManager refMgr;

	public RefOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		this.reference = refMgr.getReference(json);
		this.parent = parent; // parent of reference, not parent of referent
		this.factory = factory;
		this.refMgr = refMgr;
	}

	public RefOverlay(Reference reference, JsonOverlay<?> parent, OverlayFactory<V> factory, ReferenceManager refMgr) {
		this.reference = reference;
		this.parent = parent;
		this.factory = factory;
		this.refMgr = refMgr;
	}

	/* package */JsonOverlay<V> getOverlay() {
		if (!reference.isResolved()) {
			reference.resolve();
		}
		if (target == null && reference.isValid()) {
			ReferenceRegistry registry = refMgr.getRegistry();
			{
				@SuppressWarnings("unchecked")
				JsonOverlay<V> castTarget = (JsonOverlay<V>) registry.getOverlay(reference.getNormalizedRef(),
						factory.getSignature());
				this.target = castTarget;
			}
			if (target == null) {
				@SuppressWarnings("unchecked")
				JsonOverlay<V> castTarget = (JsonOverlay<V>) registry.getOverlay(reference.getJson(),
						factory.getSignature());
				this.target = castTarget;
			}
			if (target == null) {
				target = factory.create(reference.getJson(), null, refMgr);
				target._setCreatingRef(reference);
				refMgr.getRegistry().register(reference.getNormalizedRef(), factory.getSignature(), target);
			}
		}
		return target;
	}

	public V _get(boolean elaborate) {
		getOverlay();
		return target != null ? target._get(elaborate) : null;
	}

	public Reference _getReference() {
		return reference;
	}

	public JsonOverlay<?> _getParent() {
		return parent;
	}
}
