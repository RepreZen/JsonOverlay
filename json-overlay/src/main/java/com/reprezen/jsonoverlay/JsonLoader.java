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

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.reprezen.jsonoverlay.parser.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class JsonLoader {

	private static LocationRecorderJsonFactory jsonFactory = new LocationRecorderJsonFactory();
	private static LocationRecorderYamlFactory yamlFactory = new LocationRecorderYamlFactory();

	private static ObjectMapper jsonMapper = new ObjectMapper(jsonFactory);
	private static ObjectMapper yamlMapper = new ObjectMapper(yamlFactory);
	private Yaml yaml = new Yaml();

	private Map<String, JsonNode> cache = Maps.newHashMap();

	public JsonLoader() {
	}

	public JsonNode load(URL url) throws IOException {
		String urlString = url.toString();
		if (cache.containsKey(urlString)) {
			return cache.get(urlString);
		}
		try (InputStream in = url.openStream()) {
			String json = IOUtils.toString(in, Charsets.UTF_8);
			return loadString(url, json);
		}
	}

	public JsonNode loadString(URL url, String json) throws IOException, JsonProcessingException {
		JsonNode tree;
		if (json.trim().startsWith("{")) {
			tree = jsonMapper.readTree(json);
		} else {
			Object parsedYaml = yaml.load(json); // this handles aliases - YAMLMapper doesn't
			tree = yamlMapper.convertValue(parsedYaml, JsonNode.class);
		}
		if (url != null) {
			cache.put(url.toString(), tree);
		}
		return tree;
	}

	public Pair<JsonNode, Map<JsonPointer, JsonRegion>> loadWithLocations(String json) throws IOException {
		JsonNode tree;
		Map<JsonPointer, JsonRegion> regions;

		if (json.trim().startsWith("{")) {
			LocationRecorderJsonParser parser = (LocationRecorderJsonParser) jsonFactory.createParser(json);
			tree = jsonMapper.readTree(parser);
			regions = parser.getLocations();
		} else {
			LocationRecorderYamlParser parser = (LocationRecorderYamlParser) yamlFactory.createParser(json);
			tree = yamlMapper.readTree(parser);
			regions = parser.getLocations();
		}
		return Pair.of(tree, regions);
	}
}
