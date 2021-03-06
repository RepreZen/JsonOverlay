/*********************************************************************
*  Copyright (c) 2017 ModelSolv, Inc. and others.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     ModelSolv, Inc. 
 *     - initial API and implementation and/or initial documentation
**********************************************************************/
package com.reprezen.jsonoverlay;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public class ReferenceRegistry {

	private Map<String, ReferenceManager> managers = new HashMap<>();
	private JsonLoader loader;
	private Map<Pair<String, String>, JsonOverlay<?>> overlaysByRef = new HashMap<>();
	// can't use Pair here because we need to index by JsonNode identity, not
	// using
	// its equals impl
	private Map<JsonNode, Map<String, JsonOverlay<?>>> overlaysByJson = new IdentityHashMap<>();

	public ReferenceRegistry() {
		this(null);
	}

	public ReferenceRegistry(JsonLoader loader) {
		this.loader = loader != null ? loader : new JsonLoader();
	}

	public ReferenceManager getManager(URL baseUrl) {
		return managers.get(baseUrl.toString());
	}

	public void registerManager(URL baseUrl, ReferenceManager manager) {
		managers.put(baseUrl.toString(), manager);
	}

	public JsonNode loadDoc(URL url) throws IOException {
		return loader.load(url);
	}

	public JsonOverlay<?> getOverlay(String normalizedRef, String factorySig) {
		return overlaysByRef.get(Pair.of(normalizedRef, factorySig));
	}

	public void register(String normalizedRef, String factorySig, JsonOverlay<?> overlay) {
		overlaysByRef.put(Pair.of(normalizedRef, factorySig), overlay);
	}

	public JsonOverlay<?> getOverlay(JsonNode json, String factorySig) {
		Map<String, JsonOverlay<?>> overlaysBySig = overlaysByJson.get(json);
		return overlaysBySig != null ? overlaysBySig.get(factorySig) : null;
	}

	public void register(JsonNode json, String factorySig, JsonOverlay<?> overlay) {
		// can't share boolean or nulls because they don't have a public
		// constructor,
		// and factory uses shared instances
		if (!json.isMissingNode() && !json.isBoolean() && !json.isNull()) {
			if (!overlaysByJson.containsKey(json)) {
				overlaysByJson.put(json, new HashMap<>());
			}
			overlaysByJson.get(json).put(factorySig, overlay);
		}
	}

	public Optional<PositionInfo> getPositionInfo(String docUrl, JsonPointer pointer) {
		return loader.getPositionInfo(docUrl, pointer);
	}
}