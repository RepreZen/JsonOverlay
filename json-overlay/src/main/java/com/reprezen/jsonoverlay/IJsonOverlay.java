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

public interface IJsonOverlay<V> {

    V get();

    V get(boolean elaborate);

    void set(V value);

    IJsonOverlay<?> find(JsonPointer path);

    IJsonOverlay<?> find(String path);

    JsonNode toJson();

    JsonNode toJson(SerializationOptions options);

    JsonNode toJson(SerializationOptions.Option... options);

    boolean isPresent();

    boolean isElaborated();

    IJsonOverlay<?> getParent();

    String getPathInParent();

    IJsonOverlay<?> getRoot();

    String getPathFromRoot();

    URL getJsonReference();
}
