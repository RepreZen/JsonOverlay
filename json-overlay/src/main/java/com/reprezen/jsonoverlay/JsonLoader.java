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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.reprezen.jsonoverlay.parser.PositionInfo;
import com.reprezen.jsonoverlay.parser.LocationRecorderJsonFactory;
import com.reprezen.jsonoverlay.parser.LocationRecorderJsonParser;
import com.reprezen.jsonoverlay.parser.LocationRecorderYamlFactory;
import com.reprezen.jsonoverlay.parser.LocationRecorderYamlParser;

public class JsonLoader {

	private static LocationRecorderJsonFactory jsonFactory = new LocationRecorderJsonFactory();
	private static LocationRecorderYamlFactory yamlFactory = new LocationRecorderYamlFactory();

	private static ObjectMapper jsonMapper = new ObjectMapper(jsonFactory);
	private static ObjectMapper yamlMapper = new ObjectMapper(yamlFactory);

	static {
		jsonMapper.setNodeFactory(MinSharingJsonNodeFactory.instance);
		yamlMapper.setNodeFactory(MinSharingJsonNodeFactory.instance);
	}

	private Map<String, JsonNode> cache = Maps.newHashMap();
	private Map<String, Map<JsonPointer, PositionInfo>> positions = Maps.newHashMap();

	public JsonLoader() {
	}

	public JsonNode load(URL url) throws IOException {
		String urlString = url.toString();
		if (cache.containsKey(urlString)) {
			return cache.get(urlString);
		}
		try (InputStream in = url.openStream()) {
			try (Scanner scanner = new Scanner(in, "UTF-8")) {
				String json = scanner.useDelimiter("\\Z").next();
				return loadString(url, json);
			}
		}
	}

	public JsonNode loadString(URL url, String json) throws IOException, JsonProcessingException {
		Pair<JsonNode, Map<JsonPointer, PositionInfo>> result = loadWithLocations(json);
		if (url != null) {
			cache.put(url.toString(), result.getLeft());
			positions.put(url.toString(), result.getRight());
		}
		return result.getLeft();
	}

	public Optional<PositionInfo> getPositionInfo(String url, JsonPointer pointer) {
		if (positions.containsKey(url)) {
			return Optional.ofNullable(positions.get(url).get(pointer));
		} else {
			return Optional.empty();
		}
	}

	public Pair<JsonNode, Map<JsonPointer, PositionInfo>> loadWithLocations(String json) throws IOException {
		JsonNode tree;
		Map<JsonPointer, PositionInfo> regions;

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
