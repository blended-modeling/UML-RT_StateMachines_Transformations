/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.provider.TableSelectionProvider;

/**
 * Table selection provider. Keep reference to table manager to calculate row selection.
 * 
 * @author ysroh
 *
 */
public class RTTableSelectionProvider extends TableSelectionProvider implements IRTTableSelectionProvider {

	/**
	 * the current selection in the table
	 */
	private ISelection currentSelection;

	/**
	 * the list of listener on the selections
	 */
	private final List<ISelectionChangedListener> listeners;

	private INattableModelManager manager;

	/**
	 * Constructor.
	 *
	 * @param manager
	 * @param selectionLayer
	 */
	public RTTableSelectionProvider(INattableModelManager manager, SelectionLayer selectionLayer) {
		super(manager, selectionLayer);
		this.manager = manager;
		this.listeners = new ArrayList<>();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.TableSelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 *
	 * @param selection
	 */
	@Override
	public synchronized void setSelection(ISelection selection) {
		super.setSelection(selection);

		// We need to notify even if the selection is same.
		// Code snippet view need to know if user make a selection on the trigger table
		// even if it is same row that user is selecting.
		if (selection.equals(currentSelection)) {
			final SelectionChangedEvent event = new SelectionChangedEvent(this, this.currentSelection);
			for (final ISelectionChangedListener current : this.listeners) {
				current.selectionChanged(event);
			}
		}
		currentSelection = selection;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.TableSelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 *
	 * @param listener
	 */
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		super.addSelectionChangedListener(listener);
		listeners.add(listener);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.TableSelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 *
	 * @param listener
	 */
	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
		super.removeSelectionChangedListener(listener);
	}

	/**
	 * @see org.eclipse.papyrusrt.umlrt.tooling.properties.providers.IRTTableSelectionProvider#getTableManager()
	 *
	 * @return
	 */
	@Override
	public INattableModelManager getTableManager() {
		return manager;
	}

}
