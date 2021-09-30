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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.IFilteredObservableList;

/**
 * A list property that supports filtering. The lists that it provides implement
 * the {@link IFilteredObservableList} protocol.
 */
public interface IFilteredListProperty<S, E> extends IListProperty<S, E> {
	/**
	 * @return an observable list observing this property on the given {@code source},
	 *         with support for filtering and observing
	 * 
	 * @see IFilteredObservableList
	 */
	@Override
	public IObservableList<E> observe(S source);

	/**
	 * @return an observable list observing this property on the given {@code source},
	 *         with support for filtering and observing
	 * 
	 * @see IFilteredObservableList
	 */
	@Override
	public IObservableList<E> observe(Realm realm, S source);

}
