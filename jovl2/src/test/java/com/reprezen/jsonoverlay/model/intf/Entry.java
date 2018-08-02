package com.reprezen.jsonoverlay.model.intf;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface Entry extends IJsonOverlay<Entry>, IModelPart<TestModel, Entry> {

	// Title
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getTitle();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setTitle(String title);
}
