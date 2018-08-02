package com.reprezen.jsonoverlay.model.intf;

import java.util.List;
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

	// Width
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getWidth();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setWidth(Integer width);

	// Height
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getHeight();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setHeight(Integer height);

	// Entry
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Entry> getEntries();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Entry> getEntries(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasEntries();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasEntry(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Entry getEntry(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setEntries(Map<String, Entry> entries);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setEntry(String name, Entry entry);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeEntry(String name);

	// Item
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	List<Item> getItems();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	List<Item> getItems(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasItems();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Item getItem(int index);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setItems(List<Item> items);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setItem(int index, Item item);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void addItem(Item item);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void insertItem(int index, Item item);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeItem(int index);

	// Integer
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	List<Integer> getIntegers();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	List<Integer> getIntegers(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasIntegers();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getInteger(int index);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setIntegers(List<Integer> integers);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setInteger(int index, Integer integer);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void addInteger(Integer integer);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void insertInteger(int index, Integer integer);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeInteger(int index);

	// NamedInteger
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Integer> getNamedIntegers();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Integer> getNamedIntegers(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasNamedIntegers();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasNamedInteger(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Integer getNamedInteger(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setNamedIntegers(Map<String, Integer> namedIntegers);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setNamedInteger(String name, Integer namedInteger);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeNamedInteger(String name);

	// Color
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Color getColor();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Color getColor(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setColor(Color color);

	// Scalar
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Scalars> getScalars();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Map<String, Scalars> getScalars(boolean elaborate);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasScalars();

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	boolean hasScalar(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	Scalars getScalar(String name);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setScalars(Map<String, Scalars> scalars);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void setScalar(String name, Scalars scalar);

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	void removeScalar(String name);
}
