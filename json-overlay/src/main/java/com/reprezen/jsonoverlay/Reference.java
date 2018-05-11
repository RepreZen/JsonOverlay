/*******************************************************************************
 *  Copyright (c) 2017 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.jsonoverlay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.reprezen.jsonoverlay.ResolutionException.ReferenceCycleException;

public class Reference {

	private String refString;
	private String canonRefString;
	private ResolutionBase base;
	private String fragment;
	private Boolean isValid = null;
	private JsonNode json = null;
	private ResolutionException error;
	private String key;

	private final ResolutionBaseRegistry resolutionBaseRegistry;
	private ReferenceRegistry refReg;

	/* package */ Reference(String refString, String canonicalRefString, ResolutionBase base,
			ReferenceRegistry refReg) {
		this.refString = refString;
		this.canonRefString = canonicalRefString;
		this.resolutionBaseRegistry = base.getResolutionBaseRegistry();
		this.refReg = refReg;
		int pos = refString.indexOf('#');
		String relUrl = pos < 0 ? refString : refString.substring(0, pos);
		if (relUrl.isEmpty()) {
			this.base = base;
		} else {
			// note re false: if creating a ref with resolution requested, the base will be
			// resolved as a side-effect during ref resolution, so no need to do it now.
			this.base = resolutionBaseRegistry.of(base.comprehend(relUrl), false);
		}
		if (pos >= 0) {
			this.fragment = refString.substring(pos);
		}
		if (fragment != null) {
			try {
				// certain chars *should* be URL encoded in fragments, and if they are we'll
				// decode them before applying
				// as a pointer. But if not, we don't complain (all our URL/URI parsing is done
				// with fragments removed).
				this.fragment = URLDecoder.decode(fragment, Charsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {
			}
		}
		this.key = canonRefString;
	}

	/* package */ Reference(String refString, ResolutionBase base, ResolutionException e, ReferenceRegistry refReg) {
		this.refString = refString;
		this.canonRefString = null;
		resolutionBaseRegistry = base.getResolutionBaseRegistry();
		this.refReg = refReg;
		this.fragment = null;
		this.base = base;
		this.isValid = false;
		this.error = e;
		this.key = UUID.randomUUID().toString();
	}

	public String getRefString() {
		return refString;
	}

	public String getCanonicalRefString() {
		return canonRefString;
	}

	public ResolutionBase getBase() {
		return base;
	}

	public String getFragment() {
		return fragment;
	}

	public JsonNode getJson() {
		if (isValid()) {
			return json;
		} else {
			return MissingNode.getInstance();
		}
	}

	public boolean isValid() {
		return isValid != null && isValid;
	}

	public boolean isInvalid() {
		return isValid != null && !isValid;
	}

	public boolean isResolved() {
		return isValid != null;
	}

	public ResolutionException getError() {
		return error;
	}

	public String getErrorReason() {
		return error != null ? error.getLocalizedMessage() : null;
	}

	public String getKey() {
		return key;
	}

	public JsonNode resolve() {
		return resolve(true);
	}

	public JsonNode resolve(boolean follow) {
		return resolveInternal(follow, Sets.<Reference>newHashSet());
	}

	private JsonNode resolveInternal(boolean follow, Set<Reference> seen) {
		if (seen.contains(this)) {
			resolutionCycle(this);
		} else if (!isResolved()) {
			seen.add(this);
			try {
				JsonNode root = base.resolve();
				if (fragment == null) {
					this.json = root;
				} else {
					try {
						this.json = root.at(fragment.substring(1));
						if (json.isMissingNode()) {
							throw new ResolutionException(
									"JSON pointer does not address a value in the containing structure");
						}
					} catch (IllegalArgumentException e) {
						throw new ResolutionException("Failed to resolve JSON pointer", e);
					}
				}
			} catch (ResolutionException e) {
				badResolution("Unresolvable reference", e);
			}
			while (follow && isReferenceNode(json)) {
				Reference ref = refReg.getRef(json);
				this.json = ref.resolveInternal(follow, seen);
				if (!ref.isValid()) {
					if (inCycle(ref.getError(), seen)) {
						resolutionCycle(ref.getError());
					} else {
						badResolution("Invalid indirect reference", ref.getError());
					}
					return json;
				}
			}
			if (!isReferenceNode(json) && isValid == null) {
				isValid = true;
			}
			seen.remove(this);
		}
		return json;
	}

	private void badResolution(String message, ResolutionException e) {
		this.isValid = false;
		this.error = new ResolutionException(message, e);
		this.json = MissingNode.getInstance();
	}

	private boolean inCycle(ResolutionException e, Set<Reference> seen) {
		if (e instanceof ReferenceCycleException) {
			Reference cycleAnchor = ((ReferenceCycleException) e).getDetectedAt();
			return seen.contains(cycleAnchor);
		} else {
			return false;
		}
	}

	private void resolutionCycle(ResolutionException e) {
		resolutionCycle(((ReferenceCycleException) e).getDetectedAt());
	}

	private void resolutionCycle(Reference cycleAnchor) {
		this.isValid = false;
		this.error = new ReferenceCycleException(cycleAnchor);
		this.json = MissingNode.getInstance();
	}

	public static boolean isReferenceNode(JsonNode node) {
		return node.isObject() && node.has("$ref");
	}

	@Override
	public String toString() {
		return String.format("Reference[$ref=%s; canonical=%s; valid=%s, badReason=%s]", getRefString(),
				getCanonicalRefString(), isValid(), getErrorReason());
	}
}
