/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util;

/**
 * This class is used to identify Exceptions and Errors which should be reported
 * as MultiStatus, rather than simple (single-line) Status.
 *
 * @author epp
 * 
 */
@SuppressWarnings("serial")
public class DetailedException extends Exception {

	/**
	 * Default Constructor: no message.
	 */
	public DetailedException() {
		super();
	}

	/**
	 * Constructor with a message.
	 *
	 * @param message
	 *            - A {@cod String}
	 */
	public DetailedException(String message) {
		super(message);
	}

	/**
	 * Constructor with a Throwable.
	 *
	 * @param cause
	 *            - A {@code Throwable}
	 */
	public DetailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with both a String message and a cause.
	 *
	 * @param message
	 *            - A {@code String}
	 * @param cause
	 *            - A {@code Throwable}
	 */
	public DetailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            - A {@code String}
	 * @param cause
	 *            - A {@code Throwable}
	 * @param enableSuppression
	 *            - A {@code boolean}.
	 * @param writableStackTrace
	 *            - A {@code boolean}.
	 */
	public DetailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
