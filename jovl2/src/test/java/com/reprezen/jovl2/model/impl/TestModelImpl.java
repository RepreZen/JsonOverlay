package com.reprezen.jovl2.model.impl;

import com.reprezen.jovl2.JsonOverlay;
import com.reprezen.jovl2.IntegerOverlay;
import com.reprezen.jovl2.model.impl.EntryImpl;
import com.reprezen.jovl2.ListOverlay;
import com.reprezen.jovl2.ReferenceRegistry;
import java.util.stream.Collectors;
import com.reprezen.jovl2.model.intf.*;
import com.reprezen.jovl2.MapOverlay;
import javax.annotation.Generated;
import com.reprezen.jovl2.OverlayFactory;
import com.fasterxml.jackson.core.JsonPointer;
import java.util.List;
import com.reprezen.jovl2.PropertiesOverlay;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jovl2.model.impl.ItemImpl;
import com.reprezen.jovl2.StringOverlay;

public class TestModelImpl extends PropertiesOverlay<TestModel> implements TestModel {

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public TestModelImpl(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
        super(json, parent, refReg);
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public TestModelImpl(TestModel testModel, JsonOverlay<?> parent, ReferenceRegistry refReg) {
        super(testModel, parent, refReg);
    }

    // Description
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public String getDescription() {
        return (String) get("description", String.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setDescription(String description) {
        set("description", description, String.class);
    }

    // Width
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Integer getWidth() {
        return (Integer) get("width", Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setWidth(Integer width) {
        set("width", width, Integer.class);
    }

    // Height
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Integer getHeight() {
        return (Integer) get("height", Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setHeight(Integer height) {
        set("height", height, Integer.class);
    }

    // Entry
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Map<String, Entry> getEntries() {
        return getMap("entries", Entry.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Map<String, Entry> getEntries(boolean elaborate) {
        return getMap("entries", elaborate, Entry.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasEntries() {
        return _isPresent("entries");
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasEntry(String name) {
        return getMap("entries", Entry.class).containsKey(name);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Entry getEntry(String name) {
        return get("entries", name, Entry.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setEntries(Map<String, Entry> entries) {
        set("entries", entries, Entry.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setEntry(String name, Entry entry) {
        set("entries", name, entry, Entry.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void removeEntry(String name) {
        remove("entries", name, Entry.class);
    }

    // Item
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public List<Item> getItems() {
        return getList("items", Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public List<Item> getItems(boolean elaborate) {
        return getList("items", elaborate, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasItems() {
        return _isPresent("items");
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Item getItem(int index) {
        return get("items", index, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setItems(List<Item> items) {
        set("items", items, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setItem(int index, Item item) {
        set("items", index, item, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void addItem(Item item) {
        add("items", item, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void insertItem(int index, Item item) {
        insert("items", index, item, Item.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void removeItem(int index) {
        remove("items", index, Item.class);
    }

    // Integer
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public List<Integer> getIntegers() {
        return getList("integers", Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public List<Integer> getIntegers(boolean elaborate) {
        return getList("integers", elaborate, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasIntegers() {
        return _isPresent("integers");
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Integer getInteger(int index) {
        return get("integers", index, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setIntegers(List<Integer> integers) {
        set("integers", integers, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setInteger(int index, Integer integer) {
        set("integers", index, integer, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void addInteger(Integer integer) {
        add("integers", integer, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void insertInteger(int index, Integer integer) {
        insert("integers", index, integer, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void removeInteger(int index) {
        remove("integers", index, Integer.class);
    }

    // NamedInteger
    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Map<String, Integer> getNamedIntegers() {
        return getMap("namedIntegers", Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Map<String, Integer> getNamedIntegers(boolean elaborate) {
        return getMap("namedIntegers", elaborate, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasNamedIntegers() {
        return _isPresent("namedIntegers");
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public boolean hasNamedInteger(String name) {
        return getMap("namedIntegers", Integer.class).containsKey(name);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public Integer getNamedInteger(String name) {
        return get("namedIntegers", name, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setNamedIntegers(Map<String, Integer> namedIntegers) {
        set("namedIntegers", namedIntegers, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void setNamedInteger(String name, Integer namedInteger) {
        set("namedIntegers", name, namedInteger, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public void removeNamedInteger(String name) {
        remove("namedIntegers", name, Integer.class);
    }

    @Override
    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    protected void elaborateChildren() {
        createScalar("description", "description", StringOverlay.factory);
        createScalar("width", "width", IntegerOverlay.factory);
        createScalar("height", "height", IntegerOverlay.factory);
        createMap("entries", "entries", EntryImpl.factory, null);
        createList("items", "items", ItemImpl.factory);
        createList("integers", "integers", IntegerOverlay.factory);
        createMap("namedIntegers", "namedIntegers", IntegerOverlay.factory, null);
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    public static OverlayFactory<TestModel> factory = new OverlayFactory<TestModel>() {

        @Override
        protected Class<? extends JsonOverlay<? super TestModel>> getOverlayClass() {
            return TestModelImpl.class;
        }

        @Override
        public JsonOverlay<TestModel> _create(TestModel testModel, JsonOverlay<?> parent, ReferenceRegistry refReg) {
            JsonOverlay<?> overlay;
            overlay = new TestModelImpl(testModel, parent, refReg);
            @SuppressWarnings("unchecked") JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
            return castOverlay;
        }

        @Override
        public JsonOverlay<TestModel> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
            JsonOverlay<?> overlay;
            overlay = new TestModelImpl(json, parent, refReg);
            @SuppressWarnings("unchecked") JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
            return castOverlay;
        }
    };

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    private static Class<? extends TestModel> getSubtypeOf(TestModel testModel) {
        return TestModel.class;
    }

    @Generated("com.reprezen.jovl2.gen.CodeGenerator")
    private static Class<? extends TestModel> getSubtypeOf(JsonNode json) {
        return TestModel.class;
    }
}