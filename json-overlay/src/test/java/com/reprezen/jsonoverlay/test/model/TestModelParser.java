package com.reprezen.jsonoverlay.test.model;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.JsonLoader;
import com.reprezen.jsonoverlay.Reference;
import com.reprezen.jsonoverlay.ReferenceRegistry;
import com.reprezen.jsonoverlay.ResolutionBase;
import com.reprezen.jsonoverlay.ResolutionBaseRegistry;
import com.reprezen.jsonoverlay.Resolver;
import com.reprezen.jsonoverlay.test.model.impl.TestModelImpl;
import com.reprezen.jsonoverlay.test.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		JsonLoader loader = new JsonLoader();
		JsonNode tree = loader.load(url);
		ResolutionBaseRegistry baseReg = new ResolutionBaseRegistry(loader);
		baseReg.register(url.toString(), tree);
		ReferenceRegistry refReg = new ReferenceRegistry();
		new Resolver(refReg, baseReg).preresolve(url);
		ResolutionBase base = baseReg.get(url.toString());
		Reference topRef = refReg.getRef(url.toString(), base, true);
		return (TestModel) TestModelImpl.factory.create(tree, null,  refReg, topRef);
	}
}
