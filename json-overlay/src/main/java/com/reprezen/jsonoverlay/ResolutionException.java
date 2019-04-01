/*********************************************************************
*  Copyright (c) 2017 ModelSolv, Inc. and others.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     ModelSolv, Inc. 
 *     - initial API and implementation and/or initial documentation
**********************************************************************/
package com.reprezen.jsonoverlay;

public class ResolutionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Reference ref;

	public ResolutionException(Reference ref) {
		this(null, ref, null);
	}

	public ResolutionException(String message, Reference ref, Throwable cause) {
		super(message, cause);
		this.ref = ref;
	}

	public ResolutionException(String message, Reference ref) {
		this(message, ref, null);
	}

	public ResolutionException(Reference ref, Throwable cause) {
		this(null, ref, cause);
	}

	public Reference getReference() {
		return ref;
	}

	public static class ReferenceCycleException extends ResolutionException {

		private static final long serialVersionUID = 1L;

		private Reference detectedAt;

		public ReferenceCycleException(Reference ref, Reference detectedAt) {
			super("This reference participates in a reference cycle", ref);
			this.detectedAt = detectedAt;
		}

		public Reference getDetectedAt() {
			return detectedAt;
		}

	}
}
