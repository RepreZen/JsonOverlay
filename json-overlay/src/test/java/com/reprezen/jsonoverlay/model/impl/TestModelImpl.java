package com.reprezen.jsonoverlay.model.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.IntegerOverlay;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.OverlayFactory;
import com.reprezen.jsonoverlay.PropertiesOverlay;
import com.reprezen.jsonoverlay.ReferenceManager;
import com.reprezen.jsonoverlay.StringOverlay;
import com.reprezen.jsonoverlay.model.intf.Color;
import com.reprezen.jsonoverlay.model.intf.Entry;
import com.reprezen.jsonoverlay.model.intf.Item;
import com.reprezen.jsonoverlay.model.intf.Scalars;
import com.reprezen.jsonoverlay.model.intf.TestModel;

public class TestModelImpl extends PropertiesOverlay<TestModel> implements TestModel {

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestModelImpl(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(json, parent, factory, refMgr);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public TestModelImpl(TestModel testModel, JsonOverlay<?> parent, ReferenceManager refMgr) {
		super(testModel, parent, factory, refMgr);
	}

	// Description
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public String getDescription() {
		return _get("description", String.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setDescription(String description) {
		_setScalar("description", description, String.class);
	}

	// Width
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getWidth() {
		return _get("width", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setWidth(Integer width) {
		_setScalar("width", width, Integer.class);
	}

	// Height
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getHeight() {
		return _get("height", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setHeight(Integer height) {
		_setScalar("height", height, Integer.class);
	}

	// Entry
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Entry> getEntries() {
		return _getMap("entries", Entry.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Entry> getEntries(boolean elaborate) {
		return _getMap("entries", elaborate, Entry.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasEntries() {
		return _isPresent("entries");
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasEntry(String name) {
		return _getMap("entries", Entry.class).containsKey(name);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Entry getEntry(String name) {
		return _get("entries", name, Entry.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setEntries(Map<String, Entry> entries) {
		_setMap("entries", entries, Entry.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setEntry(String name, Entry entry) {
		_set("entries", name, entry, Entry.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeEntry(String name) {
		_remove("entries", name, Entry.class);
	}

	// Item
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public List<Item> getItems() {
		return _getList("items", Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public List<Item> getItems(boolean elaborate) {
		return _getList("items", elaborate, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasItems() {
		return _isPresent("items");
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Item getItem(int index) {
		return _get("items", index, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setItems(List<Item> items) {
		_setList("items", items, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setItem(int index, Item item) {
		_set("items", index, item, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void addItem(Item item) {
		_add("items", item, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void insertItem(int index, Item item) {
		_insert("items", index, item, Item.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeItem(int index) {
		_remove("items", index, Item.class);
	}

	// Integer
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public List<Integer> getIntegers() {
		return _getList("integers", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public List<Integer> getIntegers(boolean elaborate) {
		return _getList("integers", elaborate, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasIntegers() {
		return _isPresent("integers");
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getInteger(int index) {
		return _get("integers", index, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setIntegers(List<Integer> integers) {
		_setList("integers", integers, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setInteger(int index, Integer integer) {
		_set("integers", index, integer, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void addInteger(Integer integer) {
		_add("integers", integer, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void insertInteger(int index, Integer integer) {
		_insert("integers", index, integer, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeInteger(int index) {
		_remove("integers", index, Integer.class);
	}

	// NamedInteger
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Integer> getNamedIntegers() {
		return _getMap("namedIntegers", Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Integer> getNamedIntegers(boolean elaborate) {
		return _getMap("namedIntegers", elaborate, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasNamedIntegers() {
		return _isPresent("namedIntegers");
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasNamedInteger(String name) {
		return _getMap("namedIntegers", Integer.class).containsKey(name);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Integer getNamedInteger(String name) {
		return _get("namedIntegers", name, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setNamedIntegers(Map<String, Integer> namedIntegers) {
		_setMap("namedIntegers", namedIntegers, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setNamedInteger(String name, Integer namedInteger) {
		_set("namedIntegers", name, namedInteger, Integer.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeNamedInteger(String name) {
		_remove("namedIntegers", name, Integer.class);
	}

	// Color
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Color getColor() {
		return _get("color", Color.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Color getColor(boolean elaborate) {
		return _get("color", elaborate, Color.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setColor(Color color) {
		_setScalar("color", color, Color.class);
	}

	// Scalar
	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Scalars> getScalars() {
		return _getMap("scalars", Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Map<String, Scalars> getScalars(boolean elaborate) {
		return _getMap("scalars", elaborate, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasScalars() {
		return _isPresent("scalars");
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public boolean hasScalar(String name) {
		return _getMap("scalars", Scalars.class).containsKey(name);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Scalars getScalar(String name) {
		return _get("scalars", name, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setScalars(Map<String, Scalars> scalars) {
		_setMap("scalars", scalars, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void setScalar(String name, Scalars scalar) {
		_set("scalars", name, scalar, Scalars.class);
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public void removeScalar(String name) {
		_remove("scalars", name, Scalars.class);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_description = "description";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_width = "width";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_height = "height";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_entries = "entries";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_items = "items";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_integers = "integers";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_namedIntegers = "namedIntegers";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_color = "color";

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static final String F_scalars = "scalars";

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected void _elaborateJson() {
		super._elaborateJson();
		_createScalar("description", "description", StringOverlay.factory);
		_createScalar("width", "width", IntegerOverlay.factory);
		_createScalar("height", "height", IntegerOverlay.factory);
		_createMap("entries", "entries", EntryImpl.factory, null);
		_createList("items", "items", ItemImpl.factory);
		_createList("integers", "integers", IntegerOverlay.factory);
		_createMap("namedIntegers", "namedIntegers", IntegerOverlay.factory, null);
		_createScalar("color", "color", ColorImpl.factory);
		_createMap("scalars", "scalars", ScalarsImpl.factory, null);
	}

	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public static OverlayFactory<TestModel> factory = new OverlayFactory<TestModel>() {

		@Override
		protected Class<? extends JsonOverlay<? super TestModel>> getOverlayClass() {
			return TestModelImpl.class;
		}

		@Override
		public JsonOverlay<TestModel> _create(TestModel testModel, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new TestModelImpl(testModel, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
			return castOverlay;
		}

		@Override
		public JsonOverlay<TestModel> _create(JsonNode json, JsonOverlay<?> parent, ReferenceManager refMgr) {
			JsonOverlay<?> overlay;
			overlay = new TestModelImpl(json, parent, refMgr);
			@SuppressWarnings("unchecked")
			JsonOverlay<TestModel> castOverlay = (JsonOverlay<TestModel>) overlay;
			return castOverlay;
		}

		@Override
		protected boolean isExtendedType() {
			return false;
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

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	public Class<?> _getModelType() {
		return TestModel.class;
	}

	@Override
	@Generated("com.reprezen.jsonoverlay.gen.CodeGenerator")
	protected OverlayFactory<?> _getFactory() {
		return factory;
	}
}
