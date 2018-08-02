package com.reprezen.jovl2.model;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.reprezen.jovl2.gen.CodeGenerator;

public class GenTestModel {

	public static void main(String[] args)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		CodeGenerator.main(new String[] { //
				"-t", "src/test/java/com/reprezen/jovl2/model/types.yaml", //
				"-p", "com.reprezen.jovl2.model", //
				"-d", "src/test/java/com/reprezen/jovl2/model", //
				"-i", "intf", //
				"-I", "intf", //
				"-c", "impl", //
				"-C", "impl" //
		});
	}
}
