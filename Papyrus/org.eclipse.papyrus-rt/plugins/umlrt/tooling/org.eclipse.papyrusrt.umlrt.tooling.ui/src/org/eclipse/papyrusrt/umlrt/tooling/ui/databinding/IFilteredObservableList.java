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

import java.util.function.Predicate;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.papyrus.infra.tools.databinding.IDelegatingObservable;

/**
 * Mix-in interface for {@link IObservableList}s that support filters
 * to exclude elements.
 */
public interface IFilteredObservableList<E> {
	/**
	 * Add a {@code filter} to match objects that should be included in my filtered view
	 * of my wrapped list, if I do not already have this filter.
	 * 
	 * @param filter
	 *            a filter matching elements to include
	 * @return whether my filters were modified
	 */
	boolean addFilter(Predicate<? super E> filter);

	/**
	 * Removes a filter that was previously {@linkplain #addFilter(Predicate) added}
	 * to me.
	 * 
	 * @param filter
	 *            a filter to remove
	 * @return whether my filters were modified
	 */
	boolean removeFilter(Predicate<? super E> filter);

	/**
	 * Obtains the filtered list that is or that underlies a given {@code list}.
	 * 
	 * @param list
	 *            a list, which may itself be filtered or which may wrap a filtered list
	 * 
	 * @return the filtered list, or {@code null} if the {@code list} does not
	 *         support filtering
	 */
	static <E> IFilteredObservableList<E> getFilteredList(IObservableList<E> list) {
		IFilteredObservableList<E> result = null;

		if (list instanceof IFilteredObservableList<?>) {
			IFilteredObservableList<E> filtered = (IFilteredObservableList<E>) list;
			result = filtered;
		} else if (list instanceof IDelegatingObservable) {
			@SuppressWarnings("unchecked")
			IObservableList<E> delegate = (IObservableList<E>) ((IDelegatingObservable) list).getDelegate();
			result = getFilteredList(delegate);
		}

		return result;
	}

	/**
	 * Wraps an observable list with filtering support and also observing, if the
	 * {@link list} supports that.
	 * 
	 * @param list
	 *            a list to be wrapped for filtering
	 * 
	 * @return the {@code list}, if it already is a filtering list, otherwise
	 *         a filtering decorator for it
	 */
	static <E> IObservableList<E> wrap(IObservableList<E> list) {
		return (list instanceof IFilteredObservableList<?>)
				? list
				: (list instanceof IObserving)
						? new FilteredObservableList.Observing<E>(list)
						: new FilteredObservableList<>(list);
	}
}
