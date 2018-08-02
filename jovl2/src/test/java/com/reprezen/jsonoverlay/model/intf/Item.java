package com.reprezen.jsonoverlay.model.intf;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface Item extends IJsonOverlay<Item>, IModelPart<TestModel, Item> {

	// Title
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getTitle();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setTitle(String title);
}
