package com.reprezen.jovl2.model.intf;

import javax.annotation.Generated;
import com.reprezen.jovl2.IModelPart;
import com.reprezen.jovl2.IJsonOverlay;
import com.reprezen.jovl2.model.intf.Scalars;
import com.reprezen.jovl2.model.intf.Color;

public interface Scalars extends IJsonOverlay<Scalars>, IModelPart<TestModel, Scalars> {

    // StringValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    String getStringValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setStringValue(String stringValue);

    // IntValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Integer getIntValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setIntValue(Integer intValue);

    // NumberValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Number getNumberValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setNumberValue(Number numberValue);

    // BoolValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Boolean getBoolValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean isBoolValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setBoolValue(Boolean boolValue);

    // ObjValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Object getObjValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setObjValue(Object objValue);

    // PrimValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Object getPrimValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setPrimValue(Object primValue);

    // ColorValue
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Color getColorValue();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Color getColorValue(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setColorValue(Color colorValue);

    // EmbeddedScalars
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Scalars getEmbeddedScalars();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Scalars getEmbeddedScalars(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setEmbeddedScalars(Scalars embeddedScalars);
}
