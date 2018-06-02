package com.reprezen.jovl2.model;

import java.io.IOException;
import java.net.URL;

import com.reprezen.jovl2.ReferenceManager;
import com.reprezen.jovl2.model.impl.TestModelImpl;
import com.reprezen.jovl2.model.intf.TestModel;

public class TestModelParser {

	public static TestModel parse(URL url) throws IOException {
		ReferenceManager manager = new ReferenceManager(url);
		return (TestModel) TestModelImpl.factory.create(manager.loadDoc(), null, manager);
	}
}
