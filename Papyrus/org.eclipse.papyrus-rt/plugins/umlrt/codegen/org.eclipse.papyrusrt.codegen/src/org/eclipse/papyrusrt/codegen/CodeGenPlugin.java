/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrusrt.xtumlrt.util.DetailedException;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Core Activator for the Papyrus-RT code generator plugins.
 */
public class CodeGenPlugin extends Plugin {

	/** Plug-in ID. */
	public static final String ID = "org.eclipse.papyrusrt.codegen";

	/** EObject locator extension point id. */
	public static final String EOBJECTLOCATOR_EP_ID = "eobjectlocator"; //$NON-NLS-1$

	/** Reference to the plug-in instance. */
	private static CodeGenPlugin instance;

	/** Boolean flag stating whether the generator is running in stand-alone mode or not. */
	private static boolean standalone = false;

	/** Boolean flag stating whether the generator is running in textual mode or not. */
	private static boolean textual = false;

	/** Boolean flag stating whether to print a stack trace with errors or not. */
	private static boolean printStackTrace;

	/** Common logger for the generator. */
	private static final Logger LOGGER = Logger.getLogger("CodeGenPlugin");


	/**
	 * Default Constructor.
	 */
	public CodeGenPlugin() {
		instance = this;
		LOGGER.setUseParentHandlers(false);
	}

	public static CodeGenPlugin getDefault() {
		return instance;
	}

	/**
	 * Log a debug message.
	 * 
	 * @param message
	 *            - The message
	 * @return an {@link IStatus}
	 */
	public static IStatus debug(String message) {
		IStatus status = new Status(IStatus.INFO, ID, "[DEBUG]" + message);
		if (standalone) {
			getLogger().finest(message);
		}
		return status;
	}

	/**
	 * Log a debug message where the message is to be evaluated lazily rather than at the point of invocation.
	 * 
	 * This method is intended to be used for debugging with large or complex
	 * messages, so the argument is an unevaluated function which will be
	 * evaluated only if the logging level is set to FINEST.
	 * 
	 * @param thunk
	 *            : {@code Function0<String>}
	 *            a function which when applied will return the actual message.
	 * @return IStatus
	 */
	public static IStatus debug(Function0<CharSequence> thunk) {
		IStatus status = null;
		if (!standalone || getLogger().getLevel().intValue() > Level.FINEST.intValue()) {
			// System.out.println("[DEBUG]\n" + thunk.apply().toString() + "\n");
			status = new Status(IStatus.INFO, ID, "[DEBUG] " + thunk.toString());
		} else {
			String message = thunk.apply().toString();
			status = new Status(IStatus.INFO, ID, "[DEBUG]\n" + message);
			if (standalone) {
				getLogger().finest(message);
			}
		}
		return status;
	}

	/**
	 * Log an information message.
	 * 
	 * @param message
	 *            - A message
	 * @return An {@link IStatus}
	 */
	public static IStatus info(String message) {
		IStatus status = new Status(IStatus.INFO, ID, message);
		if (standalone) {
			getLogger().info(message);
		} else {
			getDefault().getLog().log(status);
		}
		return status;
	}

	/**
	 * Log a warning message.
	 * 
	 * @param message
	 *            - A message
	 * @return An {@link IStatus}
	 */
	public static IStatus warning(String message) {
		IStatus status = new Status(IStatus.WARNING, ID, message);
		if (standalone) {
			getLogger().warning(message);
		} else {
			getDefault().getLog().log(status);
		}
		return status;
	}

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            - A message
	 * @return An {@link IStatus}
	 */
	public static IStatus error(String message) {
		return error(message, null);
	}

	/**
	 * Log an error message.
	 * 
	 * @param e
	 *            - A {@code Throwable}
	 * @return An {@link IStatus}
	 */
	public static IStatus error(Throwable e) {
		return error("Error", e);
	}

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            - A {@code String} message
	 * @param e
	 *            - A {@code Throwable} cause
	 * @return An {@link IStatus}
	 */
	public static IStatus error(String message, Throwable e) {
		IStatus status = null;
		if (e instanceof DetailedException) {
			status = new Status(IStatus.ERROR, ID, e.toString());
		} else {
			status = new Status(IStatus.ERROR, ID, message, e);
		}
		if (standalone) {
			getLogger().severe(status.toString());
			if (printStackTrace && e != null) {
				e.printStackTrace();
			}
		} else {
			getDefault().getLog().log(status);
		}
		return status;
	}

	/**
	 * Set the generator to stand-alone mode.
	 * 
	 * @param printTrace
	 *            - A {@code boolean}, whether to print stack traces on errors or not.
	 */
	public static void setStandalone(boolean printTrace) {
		standalone = true;
		printStackTrace = printTrace;
	}

	public static boolean isStandalone() {
		return standalone;
	}

	/**
	 * Set the generator to textual mode.
	 * 
	 * @param printTrace
	 *            - A {@code boolean}, whether to print stack traces on errors or not.
	 */
	public static void setTextual(boolean printTrace) {
		textual = true;
		printStackTrace = printTrace;
	}

	public static boolean isTextual() {
		return textual;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
