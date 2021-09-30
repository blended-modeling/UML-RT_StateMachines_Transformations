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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.PapyrusObservableList;

/**
 * A specialized observable list that supports filtering for the UI, such as to prevent
 * the presentation of an element that is in the process of being created within a dialog.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PapyrusRTObservableList extends PapyrusObservableList implements IFilteredObservableList {

	private final List<Predicate> filters = new ArrayList<>(3);

	public PapyrusRTObservableList(List<?> wrappedList, EditingDomain domain, EObject source, EStructuralFeature feature) {
		super(wrappedList, domain, source, feature, GMFtoEMFCommandWrapper::wrap);
	}

	@Override
	public boolean addFilter(Predicate filter) {
		boolean result = !filters.contains(filter) && filters.add(filter);

		if (result) {
			refreshCacheList();
		}

		return result;
	}

	@Override
	public boolean removeFilter(Predicate filter) {
		boolean result = filters.remove(filter);

		if (result) {
			refreshCacheList();
		}

		return result;
	}

	/**
	 * Refresh the cached list by copying and filtering the real list.
	 */
	@Override
	protected void refreshCacheList() {
		if (filters.isEmpty()) {
			super.refreshCacheList();
		} else if (!isDisposed()) {
			// This observable can be disposed, but the commands might still be
			// in the command stack. Then undo() or redo() will call this method,
			// which should be a no-op because the observable is disposed. The
			// command should probably not call refresh directly; we should have
			// listeners on the concrete list, but it may not be observable

			wrappedList.clear();
			Stream<?> toAdd = concreteList.stream();
			for (Predicate next : filters) {
				toAdd = toAdd.filter(next);
			}

			toAdd.forEach(wrappedList::add);

			fireListChange(null);
		}
	}

}
