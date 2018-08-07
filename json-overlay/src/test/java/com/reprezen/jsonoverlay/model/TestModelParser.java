package com.reprezen.jsonoverlay.model;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.JsonLoader;
import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.model.impl.TestModelImpl;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		JsonNode json = new JsonLoader().load(url);
		ReferenceManager manager = new ReferenceManager(url, json);
		TestModel model = (TestModel) TestModelImpl.factory.create(json, null, manager);
		((TestModelImpl) model)._setCreatingRef(manager.getReference(url.toString()));
		return Overlay.of(model).get();
	}
}
