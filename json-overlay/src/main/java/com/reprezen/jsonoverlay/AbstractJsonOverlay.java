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

	/* package */ abstract V _get();

	/* package */ abstract void _set(V value);

	/* package */ abstract AbstractJsonOverlay<?> _find(JsonPointer path);

	/* package */ abstract AbstractJsonOverlay<?> _find(String path);

	/* package */ abstract JsonNode _toJson();

	/* package */ abstract JsonNode _toJson(SerializationOptions options);

	/* package */ abstract JsonNode _toJson(SerializationOptions.Option... options);

	/* package */ abstract boolean _isPresent();

	/* package */ abstract boolean _isElaborated();

	/* package */ abstract AbstractJsonOverlay<?> _getParent();

	/* package */ abstract String _getPathInParent();

	/* package */ abstract AbstractJsonOverlay<?> _getRoot();

	/* package */ abstract String _getPathFromRoot();

	/* package */ abstract URL _getJsonReference();
}
