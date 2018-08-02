package com.reprezen.jovl2;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class PropertiesOverlay<V> extends JsonOverlay<V> {

	// retrieve property values from this map by property name
	private Map<String, JsonOverlay<?>> children = Maps.newHashMap();
	// this queue sets ordering for serialization, so it matches parsed JSON
	private List<PropertyLocator> childOrder = Lists.newArrayList();
	private boolean elaborated = false;
	private boolean deferElaboration = false;

	protected PropertiesOverlay(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(json, parent, refReg);
		this.deferElaboration = json.isMissingNode();
	}

	protected PropertiesOverlay(V value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
		super(value, parent, refReg);
		elaborated = true;
	}

	protected <T> T get(String name, Class<T> cls) {
		return get(name, true, cls);
	}
	
	/* package */ <T> JsonOverlay<?> _getOverlay(String name) {
		return children.get(name);
	}

	protected boolean _isPresent(String name) {
		JsonOverlay<?> overlay = children.get(name);
		return overlay != null && !overlay.json.isMissingNode();
	}
	
	protected <T> T get(String name, boolean elaborate, Class<T> cls) {
		if (elaborate) {
			ensureElaborated();
		}
		@SuppressWarnings("unchecked")
		JsonOverlay<T> overlay = (JsonOverlay<T>) children.get(name);
		return overlay.get();
	}

	protected <T> JsonOverlay<T> getOverlay(String name, Class<T> cls) {
		@SuppressWarnings("unchecked")
		JsonOverlay<T> overlay = (JsonOverlay<T>) children.get(name);
		return overlay;
	}

	protected <T> void set(String name, T val, Class<T> cls) {
		@SuppressWarnings("unchecked")
		JsonOverlay<T> overlay = (JsonOverlay<T>) children.get(name);
		overlay.set(val);
	}

	protected <T> List<T> getList(String name, Class<T> cls) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) get(name, List.class);
		return list;
	}

	protected <T> List<T> getList(String name, boolean elaborate, Class<T> cls) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) get(name, elaborate, List.class);
		return list;
	}

	protected <T> T get(String name, int index, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		return overlay.get(index);
	}

	protected <T> void set(String name, List<T> listVal, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		overlay.set(listVal);
	}

	protected <T> void set(String name, int index, T val, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		overlay.set(index, val);
	}

	protected <T> void insert(String name, int index, T val, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		overlay.insert(index, val);
	}

	protected <T> void add(String name, T val, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		overlay.add(val);
	}

	protected <T> void remove(String name, int index, Class<T> cls) {
		@SuppressWarnings("unchecked")
		ListOverlay<T> overlay = (ListOverlay<T>) children.get(name);
		overlay.remove(index);
	}

	protected <T> Map<String, T> getMap(String name, Class<T> cls) {
		@SuppressWarnings("unchecked")
		Map<String, T> map = (Map<String, T>) get(name, Map.class);
		return map;
	}

	protected <T> Map<String, T> getMap(String name, boolean elaborate, Class<T> cls) {
		@SuppressWarnings("unchecked")
		Map<String, T> map = (Map<String, T>) get(name, elaborate, Map.class);
		return map;
	}

	protected <T> T get(String name, String key, Class<T> cls) {
		@SuppressWarnings("unchecked")
		MapOverlay<T> overlay = (MapOverlay<T>) children.get(name);
		return overlay.get(key);
	}

	protected <T> void set(String name, Map<String, T> mapVal, Class<T> cls) {
		@SuppressWarnings("unchecked")
		MapOverlay<T> overlay = (MapOverlay<T>) children.get(name);
		overlay.set(mapVal);
	}

	protected <T> void set(String name, String key, T val, Class<T> cls) {
		@SuppressWarnings("unchecked")
		MapOverlay<T> overlay = (MapOverlay<T>) children.get(name);
		overlay.set(key, val);
	}

	protected <T> void remove(String name, String key, Class<T> cls) {
		@SuppressWarnings("unchecked")
		MapOverlay<T> overlay = (MapOverlay<T>) children.get(name);
		overlay.remove(key);
	}

	protected void maybeElaborateAtCreation() {
		if (!deferElaboration) {
			ensureElaborated();
		}
	}

	@Override
	protected void elaborate() {
		elaborateChildren();
		Collections.sort(childOrder);
		elaborated = true;
	}

	protected abstract void elaborateChildren();

	@Override
	protected boolean _isElaborated() {
		return elaborated;
	}

	protected <X> JsonOverlay<X> createScalar(String name, String path, OverlayFactory<X> factory) {
		return addChild(name, path, factory);
	}

	protected <X> ListOverlay<X> createList(String name, String path, OverlayFactory<X> itemFactory) {
		return (ListOverlay<X>) addChild(name, path, ListOverlay.getFactory(itemFactory));
	}

	protected <X> MapOverlay<X> createMap(String name, String path, OverlayFactory<X> valueFactory, String keyPattern) {
		return (MapOverlay<X>) addChild(name, path, MapOverlay.getFactory(valueFactory, keyPattern));
	}

	private <X> JsonOverlay<X> addChild(String name, String path, OverlayFactory<X> factory) {
		JsonPointer pointer = JsonPointer.compile(path.isEmpty() ? "" : "/" + path);
		JsonNode childJson = json.at(pointer);
		JsonOverlay<X> child = factory.create(childJson, this, refReg);
		child._setPathInParent(path);
		PropertyLocator locator = new PropertyLocator(name, path, json);
		childOrder.add(locator);
		children.put(name, child);
		return child;
	}

	@Override
	public V fromJson(JsonNode json) {
		// parsing of the json node is expected to be done in the constructor of the
		// subclass, so nothing is done here. But we do establish this object as its own
		// value.
		@SuppressWarnings("unchecked")
		V result = (V) this;
		return result;
	}

	@Override
	protected JsonNode toJsonInternal(SerializationOptions options) {
		JsonNode obj = jsonMissing();
		for (PropertyLocator child : childOrder) {
			JsonNode childJson = children.get(child.getName()).toJson();
			if (!childJson.isMissingNode()) {
				obj = injectChild(obj, childJson, child.getPointer());
			}
		}
		obj = fixJson(obj);
		return obj.size() > 0 || options.isKeepThisEmpty() ? obj : jsonMissing();
	}

	private JsonNode injectChild(JsonNode node, JsonNode child, JsonPointer pointer) {
		if (pointer.matches()) {
			// inject into current node, which means:
			// * If current is missing, return child
			// * If current and child are both objects, merge child into current
			// * Otherwise error
			if (node.isMissingNode()) {
				return child;
			} else if (node.isObject() && child.isObject()) {
				((ObjectNode) node).setAll((ObjectNode) child);
				return node;
			} else {
				throw new IllegalArgumentException();
			}
		} else if (node.isObject() || node.isMissingNode()) {
			String name = pointer.getMatchingProperty();
			JsonNode childNode = injectChild(node.path(name), child, pointer.tail());
			if (!childNode.isMissingNode()) {
				node = node.isObject() ? node : jsonObject();
				((ObjectNode) node).set(name, childNode);
			}
			return node;
		} else {
			// can't add a property name to a non-object
			throw new IllegalArgumentException();
		}
	}

	protected JsonNode fixJson(JsonNode json) {
		return json;
	}

	protected static class PropertyLocator implements Comparable<PropertyLocator> {
		private final String name;
		private final JsonPointer pointer;
		private final List<Integer> vector;

		public PropertyLocator(String name, String path, JsonNode json) {
			this.name = name;
			this.pointer = JsonPointer.compile(path.isEmpty() ? "" : "/" + path);
			this.vector = computeVector(pointer, json);
		}

		public String getName() {
			return name;
		}

		public JsonPointer getPointer() {
			return pointer;
		}

		private List<Integer> computeVector(JsonPointer pointer, JsonNode json) {
			// the "position vector" computed by this method is key to keeping our children
			// sorted by the order in which they appeared in a parsed JSON object. The
			// vector list contains the index, in each level of object nesting, of the
			// property on the path to each child's value. The ordering of these vectors
			// thus represents the order in which the properties appeared in the parsed
			// JSON, with missing properties arbitrarily ordered at the end. Root-level maps
			// are also ordered at the end. They are either partial, in which case their
			// actual members may be scattered among other properties and we don't try to
			// maintain that ordering, or they represent the entire root object, in which
			// case the ordering is irrelevant.
			JsonNode currentJson = json;
			List<Integer> result = Lists.newArrayList();
			// we only consider object nodes and continue until our pointer is fully
			// consumed
			while (currentJson instanceof ObjectNode && !pointer.matches()) {
				String key = pointer.getMatchingProperty();
				boolean found = false;
				int i = 0;
				for (Iterator<String> iter = currentJson.fieldNames(); iter.hasNext(); i += 1) {
					if (key.equals(iter.next())) {
						found = true;
						result.add(i);
						currentJson = currentJson.get(key);
						pointer = pointer.tail();
						break;
					}
				}
				if (!found) {
					// no match at current level, so child is not present - exclude from ordering
					return null;
				}
			}
			// empty vector means the path was empty and matched the root json object. This
			// occurs only with maps, which are excluded from ordering.
			return result.isEmpty() ? null : result;
		}

		@Override
		public int compareTo(PropertyLocator other) {
			if (vector == null) {
				return other.vector == null ? name.compareTo(other.name) : 1;
			} else if (other.vector == null) {
				return -1;
			} else {
				int cmp = 0;
				// first component where paths differ determines relative ordering
				for (int i = 0; cmp == 0 && i < vector.size() && i < other.vector.size(); i++) {
					cmp = vector.get(i) - other.vector.get(i);
				}
				return cmp;
			}

		}

		@Override
		public String toString() {
			return String.format("Loc[%s]=%s", name, vector);
		}

	}
}
