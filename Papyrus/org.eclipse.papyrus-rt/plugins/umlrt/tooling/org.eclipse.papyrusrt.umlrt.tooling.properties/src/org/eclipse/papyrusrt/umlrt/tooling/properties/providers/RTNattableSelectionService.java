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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * Selection service for tables used in the properties view.
 * This service is used by {@link org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.CodeSnippetView}.
 * Provides selected row elements from the table selection.
 * </p>
 * 
 * @author ysroh
 *
 */
public class RTNattableSelectionService implements ISelectionChangedListener {

	private static RTNattableSelectionService instance;

	private Set<ISelectionListener> listeners = new HashSet<>();

	private Set<IRTTableSelectionProvider> providers = new HashSet<>();

	private ISelection currentSelection;

	/**
	 * Constructor.
	 *
	 */
	private RTNattableSelectionService() {
	}

	public static RTNattableSelectionService getInstance() {
		if (instance == null) {
			instance = new RTNattableSelectionService();
		}
		return instance;
	}

	public void addSelectionProvider(IRTTableSelectionProvider provider) {
		providers.add(provider);
		provider.addSelectionChangedListener(this);
	}

	public void removeSelectionProvider(IRTTableSelectionProvider provider) {
		provider.removeSelectionChangedListener(this);
		providers.remove(provider);
	}

	public void addSelectionListener(ISelectionListener listener) {
		listeners.add(listener);
	}

	public void removeSelectionListener(ISelectionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 *
	 * @param event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IRTTableSelectionProvider provider = (IRTTableSelectionProvider) event.getSelectionProvider();
		INattableModelManager manager = provider.getTableManager();
		if (manager == null) {
			return;
		}
		// List of the selected elements
		List elements = new ArrayList<>(1);

		// Get the selected cells coordinates
		TableStructuredSelection selection = (TableStructuredSelection) event.getSelection();
		TableSelectionWrapper wrapper = (TableSelectionWrapper) selection.getAdapter(TableSelectionWrapper.class);
		Collection<PositionCoordinate> selectedCells = wrapper.getSelectedCells();

		// The row position of the cell
		int rowPosition = 0;

		for (PositionCoordinate coordinate : selectedCells) {

			// get the real index of the row
			rowPosition = coordinate.getRowPosition();
			final RowHideShowLayer rowHideShowLayer = manager.getBodyLayerStack().getRowHideShowLayer();
			int realRowIndex = rowHideShowLayer.getRowIndexByPosition(rowPosition);

			// get the associated element
			final Object rowElement = AxisUtils.getRepresentedElement(manager.getRowElement(realRowIndex));

			// if it is a element not already selected add it into the list of elements
			if ((!elements.contains(rowElement))) {
				elements.add(rowElement);
			}
		}

		currentSelection = new StructuredSelection(elements);

		IWorkbenchPart part = null;
		try {
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		} catch (Exception e) {
		}

		if (part != null && "org.eclipse.ui.views.PropertySheet".equals(part.getSite().getId())) {
			// only care about selection from the table in the properties sheet.
			for (ISelectionListener l : listeners) {
				l.selectionChanged(part, currentSelection);
			}
		}
	}
}
