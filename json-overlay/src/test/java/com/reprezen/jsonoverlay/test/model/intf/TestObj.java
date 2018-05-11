package com.reprezen.jsonoverlay.test.model.intf;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface TestObj extends IJsonOverlay<TestObj>, IModelPart<TestModel, TestObj> {

	// X
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getX();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setX(String x);

	// Y
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getY();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setY(String y);
}
