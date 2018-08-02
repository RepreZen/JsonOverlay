package com.reprezen.jsonoverlay.model;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.model.impl.TestModelImpl;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		ReferenceManager manager = new ReferenceManager(url);
		ObjectNode rootRef = JsonNodeFactory.instance.objectNode();
		rootRef.put("$ref", url.toString());
		TestModel model = (TestModel) TestModelImpl.factory.create(rootRef, null, manager);
		return Overlay.of(model).get();
	}
}
