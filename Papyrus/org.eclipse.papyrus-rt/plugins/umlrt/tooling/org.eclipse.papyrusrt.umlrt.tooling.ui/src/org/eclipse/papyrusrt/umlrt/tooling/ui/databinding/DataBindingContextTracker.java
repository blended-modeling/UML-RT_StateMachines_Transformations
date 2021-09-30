/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

/**
 * Utility API for tracking the creation of data-binding contexts so that,
 * for example, their bindings or validation state may be observed.
 */
public class DataBindingContextTracker {
	private static final ThreadLocal<TrackingContext> trackingContext = new ThreadLocal<>();

	/**
	 * Not instantiable by clients.
	 */
	private DataBindingContextTracker() {
		super();
	}

	/**
	 * Registers a new data-binding context to let it be discovered.
	 * It is up to each individual tracker to determine when it is no
	 * longer of interest (perhaps using weak references).
	 * 
	 * @param dataBindingContext
	 *            a new data-binding context
	 */
	public static void addingDataBindingContext(DataBindingContext dataBindingContext) {
		TrackingContext ctx = trackingContext.get();
		if (ctx != null) {
			ctx.add(dataBindingContext);
		}
	}

	/**
	 * Tracks the creation of data-binding contexts during the execution of an {@code action}.
	 * Note that nested calls to this method on the same thread will result in a nested
	 * tracking context; a nesting {@code action}'s tracker will not receive new contexts,
	 * but only the nested tracker. This should match most applications' expectations,
	 * as data bindings created in the nested context would probably not be valid after
	 * it has completed anyways.
	 * 
	 * @param action
	 *            an action to run
	 * 
	 * @see #getDataBindingContexts()
	 */
	public static void trackWhile(Runnable action) {
		TrackingContext restore = trackingContext.get();

		try {
			TrackingContext ctx = new TrackingContext();
			trackingContext.set(ctx);

			action.run();
		} finally {
			if (restore == null) {
				trackingContext.remove();
			} else {
				trackingContext.set(restore);
			}
		}
	}

	/**
	 * Obtains an unmodifiable, but observable for changes, view of the current
	 * tracker's tracked data-binding contexts.
	 * 
	 * @return the current data-binding contexts
	 * 
	 * @see #trackWhile(Runnable)
	 */
	public static IObservableList<DataBindingContext> getDataBindingContexts() {
		IObservableList<DataBindingContext> result;
		TrackingContext ctx = trackingContext.get();

		if (ctx == null) {
			result = Observables.emptyObservableList(DataBindingContext.class);
		} else {
			result = ctx.getContexts();
		}

		return result;
	}

	//
	// Nested types
	//

	private static class TrackingContext {
		private final IObservableList<DataBindingContext> contexts = WritableList.withElementType(DataBindingContext.class);
		private final IObservableList<DataBindingContext> contextsView = Observables.unmodifiableObservableList(contexts);

		TrackingContext() {
			super();
		}

		void add(DataBindingContext context) {
			contexts.add(context);
		}

		IObservableList<DataBindingContext> getContexts() {
			return contextsView;
		}
	}
}
