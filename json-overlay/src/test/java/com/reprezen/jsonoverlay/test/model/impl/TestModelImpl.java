package com.reprezen.jsonoverlay.test.model.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.AbstractJsonOverlay;
import com.reprezen.jsonoverlay.ChildListOverlay;
import com.reprezen.jsonoverlay.ChildMapOverlay;
import com.reprezen.jsonoverlay.ChildOverlay;
import com.reprezen.jsonoverlay.IntegerOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceRegistry;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.test.model.intf.TestModel;
import com.reprezen.jsonoverlay.test.model.intf.TestObj;

public class TestModelImpl extends PropertiesOverlay<TestModel> implements TestModel {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestModelImpl(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestModelImpl(TestModel testModel, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(testModel, parent, refReg);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<String> description;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<String> stringVal;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<Integer> intVal;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildOverlay<TestObj> testObj;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildMapOverlay<TestObj> maps;

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private ChildListOverlay<TestObj> lists;

	// Description
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getDescription() {
		return description._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setDescription(String description) {
		this.description._set(description);
	}

	// StringVal
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getStringVal() {
		return stringVal._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setStringVal(String stringVal) {
		this.stringVal._set(stringVal);
	}

	// IntVal
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getIntVal() {
		return intVal._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setIntVal(Integer intVal) {
		this.intVal._set(intVal);
	}

	// TestObj
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObj getTestObj() {
		return testObj._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObj getTestObj(boolean elaborate) {
		return testObj._get(elaborate);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setTestObj(TestObj testObj) {
		this.testObj._set(testObj);
	}

	// Map
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, TestObj> getMaps() {
		return maps._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasMap(String name) {
		return maps.containsKey(name);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObj getMap(String name) {
		return maps._get(name);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setMaps(Map<String, TestObj> maps) {
		this.maps._set(maps);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setMap(String name, TestObj map) {
		maps._set(name, map);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeMap(String name) {
		maps._remove(name);
	}

	// List
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Collection<TestObj> getLists() {
		return lists._get();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasLists() {
		return lists._isPresent();
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestObj getList(int index) {
		return lists._get(index);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setLists(Collection<TestObj> lists) {
		this.lists._set(lists);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setList(int index, TestObj list) {
		lists._set(index, list);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void addList(TestObj list) {
		lists._add(list);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void insertList(int index, TestObj list) {
		lists._insert(index, list);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeList(int index) {
		lists._remove(index);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void elaborateChildren() {
		super.elaborateChildren();
		description = createChild("description", this, StringOverlay.factory);
		stringVal = createChild("stringVal", this, StringOverlay.factory);
		intVal = createChild("intVal", this, IntegerOverlay.factory);
		testObj = createChild("testObj", this, TestObjImpl.factory);
		maps = createChildMap("map", this, TestObjImpl.factory, null);
		lists = createChildList("list", this, TestObjImpl.factory);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<TestModel> factory = new OverlayFactory<TestModel>() {

		@Override
		protected Class<? extends AbstractJsonOverlay<? super TestModel>> getOverlayClass() {
			return TestModelImpl.class;
		}

		@Override
		public JsonOverlay<TestModel> _create(TestModel testModel, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			AbstractJsonOverlay<?> overlay;
			overlay = new TestModelImpl(testModel, parent, refReg);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<TestModel> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			AbstractJsonOverlay<?> overlay;
			overlay = new TestModelImpl(json, parent, refReg);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
			return castOverlay;
		}
	};

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends TestModel> getSubtypeOf(TestModel testModel) {
		return TestModel.class;
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	private static Class<? extends TestModel> getSubtypeOf(JsonNode json) {
		return TestModel.class;
	}
}
