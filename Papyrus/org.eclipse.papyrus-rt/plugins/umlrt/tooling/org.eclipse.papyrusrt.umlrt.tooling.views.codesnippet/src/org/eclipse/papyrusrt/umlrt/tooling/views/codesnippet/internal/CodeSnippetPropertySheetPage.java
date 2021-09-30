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
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Property sheet page for Code Snippet View.
 * 
 * @author ysroh
 *
 */
public class CodeSnippetPropertySheetPage extends TabbedPropertySheetPage implements ISelectionChangedListener {

	/**
	 * Part.
	 **/
	private CodeSnippetView part;

	/**
	 * Dispose listeners.
	 */
	private Set<DisposeListener> disposeListeners = new HashSet<>();

	/**
	 * 
	 * Constructor.
	 *
	 * @param contributor
	 *            contributor
	 */
	public CodeSnippetPropertySheetPage(CodeSnippetView contributor) {
		super(contributor);
		this.part = contributor;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// we are not relying on the workbench selection.
	}

	/**
	 * Add dispose listener.
	 * 
	 * @param listener
	 *            listener.
	 */
	public void addDisposeListener(DisposeListener listener) {
		disposeListeners.add(listener);
	}

	@Override
	public void dispose() {
		for (DisposeListener l : disposeListeners) {
			// notify dispose
			Event e = new Event();
			e.widget = getControl();
			SafeRunnable runnable = new SafeRunnable() {

				@Override
				public void run() throws Exception {
					l.widgetDisposed(new DisposeEvent(e));
				}
			};

			SafeRunnable.run(runnable);
		}
		disposeListeners.clear();
		part = null;
		super.dispose();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (getControl() != null && getWidgetFactory() != null) {
			super.selectionChanged(part, event.getSelection());
		}
	}
}
