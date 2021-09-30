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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.Page;

/**
 * <p>
 * Empty code snippet view for non important parts.
 * </p>
 * 
 * @author ysroh
 *
 */
public class EmptyCodeSnippetPage extends Page implements ICodeSnippetPage {

	/**
	 * Main page composite.
	 */
	private Composite pageControl;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public EmptyCodeSnippetPage() {
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	}

	@Override
	public void createControl(Composite parent) {

		pageControl = new Composite(parent, SWT.NONE);
		pageControl.setLayout(new FillLayout());
		pageControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite tabComposite = new Composite(pageControl, SWT.NONE);
		tabComposite.setLayoutData(new GridData());
		tabComposite.setLayout(new GridLayout());
		CLabel label = new CLabel(tabComposite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		label.setText("Code snippet view is not available for the current selection.");

		getSite().setSelectionProvider(this);
	}

	@Override
	public Control getControl() {
		return pageControl;
	}

	@Override
	public void setFocus() {
		pageControl.setFocus();
	}

	@Override
	public void dispose() {
		getSite().setSelectionProvider(null);
		super.dispose();
	}

	@Override
	public ISelection getSelection() {
		// return empty selection
		return new StructuredSelection();
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// code snippet view is not available for this part
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		// code snippet view is not available for this part
	}

	@Override
	public void setSelection(ISelection selection) {
		// code snippet view is not available for this part
	}
}
