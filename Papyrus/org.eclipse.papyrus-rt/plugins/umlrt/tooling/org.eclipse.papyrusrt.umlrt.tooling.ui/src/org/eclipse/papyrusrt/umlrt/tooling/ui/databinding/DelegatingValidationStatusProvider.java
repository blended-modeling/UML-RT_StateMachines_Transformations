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

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;

/**
 * A validation-status provider that simply encapsulates a writable validation status.
 * This is particularly cool for wrapping an {@link AggregateValidationStatus}.
 */
public class DelegatingValidationStatusProvider extends ValidationStatusProvider {
	private final IObservableValue<IStatus> value;

	/**
	 * Initializes me with the observable status that I encapsulate.
	 */
	public DelegatingValidationStatusProvider(IObservableValue<IStatus> status) {
		super();

		this.value = status;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IObservableValue getValidationStatus() {
		return value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IObservableList getTargets() {
		return Observables.emptyObservableList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IObservableList getModels() {
		return Observables.emptyObservableList();
	}
}
