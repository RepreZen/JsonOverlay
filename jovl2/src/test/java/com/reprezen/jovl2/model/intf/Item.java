package com.reprezen.jovl2.model.intf;

import javax.annotation.Generated;
import com.reprezen.jovl2.IModelPart;
import com.reprezen.jovl2.IJsonOverlay;

public interface Item extends IJsonOverlay<Item>, IModelPart<TestModel, Item> {

    // Title
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    String getTitle();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setTitle(String title);
}
