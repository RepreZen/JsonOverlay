package com.reprezen.jsonoverlay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.norconex.commons.lang.url.URLNormalizer;

public class ReferenceManager {

	private ReferenceRegistry registry;
	private URL docUrl;
	private JsonNode doc = null;

	public ReferenceManager() {
		this(null);
	}

	public ReferenceManager(URL rootUrl) {
		this.registry = new ReferenceRegistry();
		this.docUrl = rootUrl != null ? normalize(rootUrl, true) : null;
		if (docUrl != null) {
			registry.registerManager(docUrl, this);
		}
	}

	public ReferenceManager(URL rootUrl, JsonNode preloadedDoc) {
		this(rootUrl);
		this.doc = preloadedDoc;
	}

	private ReferenceManager(URL baseUrl, ReferenceRegistry registry) {
		this.docUrl = baseUrl;
		this.registry = registry;
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
		try {
			URL url = new URL(docUrl, refString);
			String fragment = url.getRef();
			ReferenceManager manager = getManagerFor(url);
			return new Reference(refString, fragment, normalize(url, false).toString(), manager);
		} catch (MalformedURLException e) {
			return new Reference(refString, new ResolutionException(null, e), null);
		}
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
			URLNormalizer normalizer = new URLNormalizer(url.toString()) //
					.lowerCaseSchemeHost() //
					.upperCaseEscapeSequence() //
					.decodeUnreservedCharacters() //
					.removeDefaultPort() //
					.encodeNonURICharacters() //
					.removeDotSegments();
			if (noFrag) {
				normalizer = normalizer.removeFragment();
			}
			return normalizer.toURL();
		} else {
			return null;
		}
	}
}
