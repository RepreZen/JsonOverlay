package com.reprezen.jsonoverlay.test.model.intf;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Generated;

import com.reprezen.jsonoverlay.IJsonOverlay;
import com.reprezen.jsonoverlay.IModelPart;

public interface TestModel extends IJsonOverlay<TestModel>, IModelPart<TestModel, TestModel> {

	// Description
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getDescription();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setDescription(String description);

	// StringVal
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	String getStringVal();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setStringVal(String stringVal);

	// IntVal
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getIntVal();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setIntVal(Integer intVal);

	// TestObj
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	TestObj getTestObj();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	TestObj getTestObj(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setTestObj(TestObj testObj);

	// Map
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, TestObj> getMaps();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasMap(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	TestObj getMap(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setMaps(Map<String, TestObj> maps);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setMap(String name, TestObj map);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeMap(String name);

	// List
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Collection<TestObj> getLists();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasLists();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	TestObj getList(int index);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setLists(Collection<TestObj> lists);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setList(int index, TestObj list);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void addList(TestObj list);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void insertList(int index, TestObj list);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeList(int index);
}
