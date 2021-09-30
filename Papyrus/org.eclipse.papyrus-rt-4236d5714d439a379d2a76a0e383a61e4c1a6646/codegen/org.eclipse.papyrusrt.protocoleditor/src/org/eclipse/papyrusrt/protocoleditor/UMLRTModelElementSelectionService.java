/*******************************************************************************
 * Copyright (c) 2014 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.protocoleditor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Change listener server for model element change in papyrus model explorer.
 * 
 * @author ysroh
 *
 */
public class UMLRTModelElementSelectionService implements IStartup,
		ISelectionListener {

	private static ISelection selection;

	private static Set<IModelExplorerSelectionChangeListener> listeners = new HashSet<IModelExplorerSelectionChangeListener>();

	@Override
	public void earlyStartup() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(new IWindowListener() {

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				addSelectionListener(window);
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				removeSelectionListener(window);
			}

			@Override
			public void windowActivated(IWorkbenchWindow arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(IWorkbenchWindow arg0) {
				// TODO Auto-generated method stub

			}
		});

		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
					addSelectionListener(window);
				}
			}
		});
	}

	private void addSelectionListener(IWorkbenchWindow window) {
		if (window != null) {
			window.getSelectionService().addSelectionListener(
					"org.eclipse.papyrus.views.modelexplorer.modelexplorer",
					this);
		}
	}

	private void removeSelectionListener(IWorkbenchWindow window) {
		if (window != null) {
			window.getSelectionService().removeSelectionListener(
					"org.eclipse.papyrus.views.modelexplorer.modelexplorer",
					this);
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		UMLRTModelElementSelectionService.selection = selection;
		fireSelectionChangedEvent(selection);

	}

	private synchronized void fireSelectionChangedEvent(ISelection selection) {
		for (IModelExplorerSelectionChangeListener l : listeners) {
			l.selectionChanged(selection);
		}
	}

	public static void addListener(
			IModelExplorerSelectionChangeListener listener) {
		listeners.add(listener);
	}

	public static void removeListener(
			IModelExplorerSelectionChangeListener listener) {
		listeners.remove(listener);
	}

	public static EObject getSelectedEObject() {
		if (!selection.isEmpty()
				&& ((IStructuredSelection) selection).getFirstElement() instanceof IAdaptable) {
			return (EObject) ((IAdaptable) ((IStructuredSelection) selection)
					.getFirstElement()).getAdapter(EObject.class);
		}
		return null;
	}

	/**
	 * Change listener interface
	 * 
	 * @author ysroh
	 *
	 */
	public interface IModelExplorerSelectionChangeListener {

		public void selectionChanged(ISelection selection);

	}
}