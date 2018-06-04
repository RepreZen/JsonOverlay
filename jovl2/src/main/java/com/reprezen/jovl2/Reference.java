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
package com.reprezen.jovl2;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Sets;
import com.reprezen.jovl2.ResolutionException.ReferenceCycleException;

public class Reference {

	private String refString;
	private String normalizedRef;
	private JsonPointer pointer = null;
	private ReferenceManager manager;
	private JsonNode json = null;
	private Boolean valid = null;
	private ResolutionException invalidReason = null;

	public Reference(String refString, String fragment, String normalizedRef, ReferenceManager manager) {
		this.refString = refString;
		this.normalizedRef = normalizedRef;
		this.manager = manager;
		try {
			this.pointer = fragment != null ? JsonPointer.compile(fragment) : null;
		} catch (IllegalArgumentException e) {
			this.valid = false;
			this.invalidReason = new ResolutionException("Invalid JSON pointer in JSON reference", this, e);
		}
	}

	public Reference(String refString, ResolutionException invalidReason, ReferenceManager manager) {
		this.refString = refString;
		this.invalidReason = invalidReason;
		this.manager = manager;
	}

	public static boolean isReferenceNode(JsonNode node) {
		return node.isObject() && node.has("$ref") && node.get("$ref").isTextual();
	}

	public String getRefString() {
		return refString;
	}

	public boolean isValid() {
		return isResolved() && valid;
	}

	public boolean isInvalid() {
		return isResolved() && !valid;
	}

	public ResolutionException getInvalidReason() {
		return invalidReason;
	}

	public boolean isResolved() {
		return valid != null;
	}

	public JsonNode getJson() {
		if (!isResolved()) {
			resolve();
		}
		return json;
	}

	public String getNormalizedRef() {
		return normalizedRef;
	}

	public ReferenceManager getManager() {
		return manager;
	}

	public boolean resolve() {
		Set<String> visited = Sets.newHashSet();
		Reference current = this;
		while (!isResolved()) {
			String normalized = current.getNormalizedRef();
			if (visited.contains(normalized)) {
				return failResolve(null, new ReferenceCycleException(this, current));
			} else {
				visited.add(normalized);
			}
			JsonNode currentJson = null;
			try {
				currentJson = current.getManager().loadDoc();
			} catch (IOException e) {
				return failResolve("Failed to load referenced documnet", e);
			}
			currentJson = current.pointer != null ? currentJson.at(current.pointer) : currentJson;
			if (isReferenceNode(currentJson)) {
				current = manager.getReference(currentJson);
				if (current.isInvalid()) {
					return failResolve("Invalid reference in reference chain", current.getInvalidReason());
				}
			} else {
				this.json = currentJson;
				if (json.isMissingNode()) {
					failResolve("Referenced node not present in JSON document");
				}
				this.valid = true;
			}
		}
		return isValid();
	}

	private boolean failResolve(String msg) {
		return failResolve(msg, null);
	}

	private boolean failResolve(String msg, Exception e) {
		this.valid = false;
		if (e instanceof ResolutionException && msg == null) {
			this.invalidReason = (ResolutionException) e;
		} else {
			this.invalidReason = new ResolutionException(msg, this, e);
		}
		return false;
	}
}
