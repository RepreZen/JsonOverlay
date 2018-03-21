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

import java.net.URL;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractJsonOverlay<V> implements IJsonOverlay<V> {

	abstract V get();

	/* package */ abstract V get(boolean elaborate);

	/* package */ abstract void set(V value);

	/* package */ abstract AbstractJsonOverlay<?> find(JsonPointer path);

	/* package */ abstract AbstractJsonOverlay<?> find(String path);

	/* package */ abstract JsonNode toJson();

	/* package */ abstract JsonNode toJson(SerializationOptions options);

	/* package */ abstract JsonNode toJson(SerializationOptions.Option... options);

	/* package */ abstract boolean isPresent();

	/* package */ abstract boolean isElaborated();

	/* package */ abstract AbstractJsonOverlay<?> getParent();

	/* package */ abstract String getPathInParent();

	/* package */ abstract AbstractJsonOverlay<?> getRoot();

	/* package */ abstract String getPathFromRoot();

	/* package */ abstract URL getJsonReference();
}
