package com.reprezen.jsonoverlay.model;

import com.reprezen.jsonoverlay.gen.CodeGenerator;

public class GenTestModel {

	public static void main(String[] args) throws Exception {
		CodeGenerator.main(new String[] { //
				"-t", "src/test/java/com/reprezen/jsonoverlay/model/types.yaml", //
				"-p", "com.reprezen.jsonoverlay.model", //
				"-d", "src/test/java/com/reprezen/jsonoverlay/model", //
				"-i", "intf", //
				"-I", "intf", //
				"-c", "impl", //
				"-C", "impl" //
		});
	}
}
