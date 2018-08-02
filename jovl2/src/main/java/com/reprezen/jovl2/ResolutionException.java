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
package com.reprezen.jovl2;

public class ResolutionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResolutionException() {
		super();
	}

	public ResolutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResolutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResolutionException(String message) {
		super(message);
	}

	public ResolutionException(Throwable cause) {
		super(cause);
	}

	public static class ReferenceCycleException extends ResolutionException {

		private static final long serialVersionUID = 1L;

		private Reference detectedAt;

		public ReferenceCycleException(Reference detectedAt) {
			super("This reference participates in a reference cycle");
			this.detectedAt = detectedAt;
		}

		public Reference getDetectedAt() {
			return detectedAt;
		}

	}
}
