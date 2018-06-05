package com.reprezen.jovl2.model.intf;

import javax.annotation.Generated;

import com.reprezen.jovl2.IJsonOverlay;
import com.reprezen.jovl2.IModelPart;

public interface Entry extends IJsonOverlay<Entry>, IModelPart<TestModel, Entry> {

	// Title
	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	String getTitle();

	@Generated("com.reprezen.jovl2.gen.CodeGenerator")
	void setTitle(String title);
}
