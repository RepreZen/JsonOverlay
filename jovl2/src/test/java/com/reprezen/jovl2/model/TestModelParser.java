package com.reprezen.jovl2.model;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.JsonLoader;
import com.reprezen.jovl2.ReferenceRegistry;
import com.reprezen.jovl2.ResolutionBaseRegistry;
import com.reprezen.jovl2.Resolver;
import com.reprezen.jovl2.model.impl.TestModelImpl;
import com.reprezen.jovl2.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		JsonLoader loader = new JsonLoader();
		JsonNode tree = loader.load(url);
		ResolutionBaseRegistry baseReg = new ResolutionBaseRegistry(loader);
		baseReg.register(url.toString(), tree);
		ReferenceRegistry refReg = new ReferenceRegistry();
		new Resolver(refReg, baseReg).preresolve(url);
		return (TestModel) TestModelImpl.factory.create(tree, null, refReg);
	}
}
