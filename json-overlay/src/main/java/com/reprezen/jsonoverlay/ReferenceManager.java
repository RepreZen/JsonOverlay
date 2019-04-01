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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public class ReferenceManager {

	private ReferenceRegistry registry;
	private URL docUrl;
	private JsonNode doc = null;
	private Map<JsonPointer, Optional<PositionInfo>> positions = new HashMap<>();

	public ReferenceManager() {
		this((URL) null, (JsonLoader) null);
	}

	public ReferenceManager(URL rootUrl) {
		this(rootUrl, (JsonLoader) null);
	}

	public ReferenceManager(URL rootUrl, JsonLoader loader) {
		this.registry = new ReferenceRegistry(loader);
		this.docUrl = rootUrl != null ? normalize(rootUrl, true) : null;
		if (docUrl != null) {
			registry.registerManager(docUrl, this);
		}
	}

	public ReferenceManager(URL rootUrl, JsonNode preloadedDoc, JsonLoader loader) {
		this(rootUrl, loader);
		this.doc = preloadedDoc;
	}

	private ReferenceManager(URL baseUrl, ReferenceRegistry registry) {
		this.docUrl = baseUrl;
		this.registry = registry;
	}

	public Reference getDocReference() {
		return getReference(docUrl.toString());
	}

	public ReferenceManager getManagerFor(URL url) {
		URL normalized = normalize(url, true);
		ReferenceManager manager = registry.getManager(normalized);
		if (manager == null) {
			manager = new ReferenceManager(normalized, registry);
			registry.registerManager(normalized, manager);
		}
		return manager;
	}

	public Reference getReference(JsonNode refNode) {
		String refString = refNode.get("$ref").asText();
		return getReference(refString);
	}

	public Reference getReference(String refString) {
		try {
			URL url = new URL(docUrl, refString);
			String fragment = url.getRef();
			ReferenceManager manager = getManagerFor(url);
			return new Reference(refString, fragment, normalize(url, false).toString(), manager);
		} catch (MalformedURLException e) {
			return new Reference(refString, new ResolutionException(null, e), null);
		}
	}

	public Optional<PositionInfo> getPositionInfo(JsonPointer pointer) {
		if (!positions.containsKey(pointer)) {
			positions.put(pointer, registry.getPositionInfo(docUrl.toString(), pointer));
		}
		return positions.get(pointer);
	}

	public JsonNode loadDoc() throws IOException {
		if (doc == null) {
			doc = registry.loadDoc(docUrl);
		}
		return doc;
	}

	public ReferenceRegistry getRegistry() {
		return registry;
	}

	private static URL normalize(URL url, boolean noFrag) {
		if (url != null) {
			String urlString = url.toString();
			int fragPos = urlString.indexOf("#");
			if (noFrag && fragPos >= 0) {
				urlString = urlString.substring(0, fragPos);
			}
			try {
				return new URI(urlString).normalize().toURL();
			} catch (MalformedURLException | URISyntaxException e) {
				// oh well, we tried...
				return url;
			}
		} else {
			return null;
		}
	}
}
