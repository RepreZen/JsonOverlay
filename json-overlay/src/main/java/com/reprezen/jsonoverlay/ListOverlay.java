/*******************************************************************************
 *  Copyright (c) 2017 ModelSolv, Inc. and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *     ModelSolv, Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.reprezen.jsonoverlay;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.reprezen.jsonoverlay.SerializationOptions.Option;

public class ListOverlay<V> extends JsonOverlay<Collection<V>> {

	private OverlayFactory<V> itemFactory;
	private List<AbstractJsonOverlay<V>> overlays = Lists.newLinkedList();

	public ListOverlay(Collection<V> value, JsonOverlay<?> parent, OverlayFactory<V> itemFactory,
			ReferenceRegistry refReg) {
		super(value, parent, refReg);
		this.itemFactory = itemFactory;
	}

	public ListOverlay(JsonNode json, JsonOverlay<?> parent, OverlayFactory<V> itemFactory, ReferenceRegistry refReg) {
		super(json, parent, refReg);
		this.itemFactory = itemFactory;
	}

	@Override
	protected void elaborate() {
		if (json != null) {
			fillWithJson();
		} else {
			fillWithValues();
		}
	}

	private void fillWithValues() {
		overlays.clear();
		if (value != null) {
			for (V item : value) {
				overlays.add(new ChildOverlay<V>(null, item, this, itemFactory, refReg));
			}
		}
	}

	private void fillWithJson() {
		value.clear();
		overlays.clear();
		if (!json.isMissingNode()) {
			for (JsonNode itemJson : iterable(json.elements())) {
				ChildOverlay<V> overlay = new ChildOverlay<>(null, itemJson, this, itemFactory, refReg);
				overlays.add(overlay);
				value.add(overlay._get(false));
			}
		}
	}

	@Override
	Collection<V> _get() {
		return wrap(value);
	}

	@Override
	public Collection<V> _get(boolean elaborate) {
		return _get();
	}

	private Collection<V> wrap(Collection<V> list) {
		if (list instanceof WrappedCollection) {
			return list;
		} else {
			return new WrappedCollection<>(list, this);
		}
	}

	/* package */ AbstractJsonOverlay<V> _get(int index) {
		return overlays.get(index);
	}

	@Override
	protected Collection<V> fromJson(JsonNode json) {
		return Lists.newArrayList();
	}

	@Override
	public AbstractJsonOverlay<?> _findInternal(JsonPointer path) {
		int index = path.getMatchingIndex();
		return overlays.size() > index ? overlays.get(index)._find(path.tail()) : null;
	}

	@Override
	public JsonNode _toJsonInternal(SerializationOptions options) {
		ArrayNode array = jsonArray();
		for (AbstractJsonOverlay<V> overlay : overlays) {
			array.add(overlay._toJson(options.plus(Option.KEEP_ONE_EMPTY)));
		}
		return array.size() > 0 || options.isKeepThisEmpty() ? array : jsonMissing();
	}

	public V get(int index) {
		AbstractJsonOverlay<V> overlay = overlays.get(index);
		return overlay != null ? overlay._get() : null;
	}

	public AbstractJsonOverlay<V> getOverlay(int index) {
		return overlays.get(index);
	}

	public void set(int index, V value) {
		overlays.set(index, itemFactory.create(value, this, refReg, null));
	}

	public void add(V value) {
		overlays.add(itemFactory.create(value, this, refReg, null));
	}

	public void insert(int index, V value) {
		overlays.add(index, itemFactory.create(value, this, refReg, null));
	}

	public void remove(int index) {
		overlays.remove(index);
	}

	public int size() {
		return overlays.size();
	}

	public boolean isReference(int index) {
		ChildOverlay<V> childOverlay = (ChildOverlay<V>) overlays.get(index);
		return childOverlay.isReference();
	}

	public Reference getReference(int index) {
		ChildOverlay<V> childOverlay = (ChildOverlay<V>) overlays.get(index);
		return childOverlay.getReference();
	}

	public static <V> OverlayFactory<Collection<V>> getFactory(OverlayFactory<V> itemFactory) {
		return new ListOverlayFactory<V>(itemFactory);
	}

	private static class ListOverlayFactory<V> extends OverlayFactory<Collection<V>> {

		private OverlayFactory<V> itemFactory;

		public ListOverlayFactory(OverlayFactory<V> itemFactory) {
			this.itemFactory = itemFactory;
		}

		@Override
		protected Class<? extends JsonOverlay<Collection<V>>> getOverlayClass() {
			Class<?> overlayClass = ListOverlay.class;
			@SuppressWarnings("unchecked")
			Class<? extends JsonOverlay<Collection<V>>> castClass = (Class<? extends JsonOverlay<Collection<V>>>) overlayClass;
			return castClass;
		}

		@Override
		public ListOverlay<V> _create(Collection<V> value, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ListOverlay<V>(value, parent, itemFactory, refReg);
		}

		@Override
		public ListOverlay<V> _create(JsonNode json, JsonOverlay<?> parent, ReferenceRegistry refReg) {
			return new ListOverlay<V>(json, parent, itemFactory, refReg);
		}
	}

	/* package */ static class WrappedCollection<V> implements Collection<V> {

		private Collection<V> collection;
		private ListOverlay<V> overlay;

		public WrappedCollection(Collection<V> collection, ListOverlay<V> overlay) {
			this.collection = collection;
			this.overlay = overlay;
		}

		public ListOverlay<V> getOverlay() {
			return overlay;
		}

		public boolean add(V arg0) {
			return collection.add(arg0);
		}

		public boolean addAll(Collection<? extends V> arg0) {
			return collection.addAll(arg0);
		}

		public void clear() {
			collection.clear();
		}

		public boolean contains(Object arg0) {
			return collection.contains(arg0);
		}

		public boolean containsAll(Collection<?> arg0) {
			return collection.containsAll(arg0);
		}

		public void forEach(Consumer<? super V> arg0) {
			collection.forEach(arg0);
		}

		public boolean isEmpty() {
			return collection.isEmpty();
		}

		public Iterator<V> iterator() {
			return collection.iterator();
		}

		public Stream<V> parallelStream() {
			return collection.parallelStream();
		}

		public boolean remove(Object arg0) {
			return collection.remove(arg0);
		}

		public boolean removeAll(Collection<?> arg0) {
			return collection.removeAll(arg0);
		}

		public boolean removeIf(Predicate<? super V> arg0) {
			return collection.removeIf(arg0);
		}

		public boolean retainAll(Collection<?> arg0) {
			return collection.retainAll(arg0);
		}

		public int size() {
			return collection.size();
		}

		public Spliterator<V> spliterator() {
			return collection.spliterator();
		}

		public Stream<V> stream() {
			return collection.stream();
		}

		public Object[] toArray() {
			return collection.toArray();
		}

		public <T> T[] toArray(T[] arg0) {
			return collection.toArray(arg0);
		}

	}
}
