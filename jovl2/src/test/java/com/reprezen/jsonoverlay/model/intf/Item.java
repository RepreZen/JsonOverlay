package com.reprezen.jsonoverlay.model.intf;

import com.reprezen.jsonoverlay.IModelPart;
import javax.annotation.Generated;
import com.reprezen.jsonoverlay.IJsonOverlay;

public interface Item extends IJsonOverlay<Item>, IModelPart<TestModel, Item> {

    // Title
    @Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
    String getTitle();

    @Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
    void setTitle(String title);
}
