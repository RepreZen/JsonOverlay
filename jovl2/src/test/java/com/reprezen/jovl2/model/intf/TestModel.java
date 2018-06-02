package com.reprezen.jovl2.model.intf;

import com.reprezen.jovl2.model.intf.Item;
import javax.annotation.Generated;
import java.util.List;
import com.reprezen.jovl2.IModelPart;
import com.reprezen.jovl2.IJsonOverlay;
import com.reprezen.jovl2.model.intf.Scalars;
import java.util.Map;
import com.reprezen.jovl2.model.intf.Color;
import com.reprezen.jovl2.model.intf.Entry;

public interface TestModel extends IJsonOverlay<TestModel>, IModelPart<TestModel, TestModel> {

    // Description
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    String getDescription();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setDescription(String description);

    // Width
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Integer getWidth();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setWidth(Integer width);

    // Height
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Integer getHeight();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setHeight(Integer height);

    // Entry
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Entry> getEntries();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Entry> getEntries(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasEntries();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasEntry(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Entry getEntry(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setEntries(Map<String, Entry> entries);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setEntry(String name, Entry entry);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void removeEntry(String name);

    // Item
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    List<Item> getItems();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    List<Item> getItems(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasItems();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Item getItem(int index);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setItems(List<Item> items);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setItem(int index, Item item);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void addItem(Item item);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void insertItem(int index, Item item);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void removeItem(int index);

    // Integer
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    List<Integer> getIntegers();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    List<Integer> getIntegers(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasIntegers();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Integer getInteger(int index);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setIntegers(List<Integer> integers);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setInteger(int index, Integer integer);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void addInteger(Integer integer);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void insertInteger(int index, Integer integer);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void removeInteger(int index);

    // NamedInteger
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Integer> getNamedIntegers();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Integer> getNamedIntegers(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasNamedIntegers();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasNamedInteger(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Integer getNamedInteger(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setNamedIntegers(Map<String, Integer> namedIntegers);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setNamedInteger(String name, Integer namedInteger);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void removeNamedInteger(String name);

    // Color
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Color getColor();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Color getColor(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setColor(Color color);

    // Scalar
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Scalars> getScalars();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Map<String, Scalars> getScalars(boolean elaborate);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasScalars();

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    boolean hasScalar(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    Scalars getScalar(String name);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setScalars(Map<String, Scalars> scalars);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void setScalar(String name, Scalars scalar);

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    void removeScalar(String name);
}
