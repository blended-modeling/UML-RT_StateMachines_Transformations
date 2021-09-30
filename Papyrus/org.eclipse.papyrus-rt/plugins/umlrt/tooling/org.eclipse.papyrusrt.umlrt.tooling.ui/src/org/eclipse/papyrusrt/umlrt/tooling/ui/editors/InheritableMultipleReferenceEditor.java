/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.editors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.widgets.providers.TreeCollectionContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

/**
 * Customized multiple-reference editor for references that support inheritance.
 */
public class InheritableMultipleReferenceEditor extends AbstractInheritableMultipleReferenceEditor {

	private final int style;

	private IBaseLabelProvider labelProvider;

	/**
	 * The viewer displaying the current values from
	 * the model
	 */
	protected TreeViewer treeViewer;

	/**
	 * The tree associated to the viewer
	 */
	protected Tree tree;

	public InheritableMultipleReferenceEditor(Composite parent, int style) {
		this(parent, style, false, false, null);
	}

	public InheritableMultipleReferenceEditor(Composite parent, int style, String label) {
		this(parent, style, false, false, label);
	}

	public InheritableMultipleReferenceEditor(Composite parent, int style, boolean ordered, boolean unique, String label) {
		super(parent, ordered, unique, label);

		this.style = style;

		createContents();
	}

	@Override
	protected Control createContents(Composite parent) {
		tree = new Tree(parent, style | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

		tree.addSelectionListener(this);

		treeViewer = new TreeViewer(tree);
		treeViewer.setContentProvider(TreeCollectionContentProvider.instance);

		IBaseLabelProvider labels = getLabelProvider();
		if (labels != null) {
			treeViewer.setLabelProvider(labels);
		}

		return tree;
	}

	@Override
	protected ISelectionProvider getSelectionProvider(Control contentControl) {
		return treeViewer;
	}

	@Override
	protected void setInput(IObservableList modelProperty) {
		treeViewer.setInput(modelProperty);
	}

	/**
	 * Sets the label provider for this editor
	 *
	 * @param labelProvider
	 *            The label provider for this editor
	 */
	@Override
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;

		if (treeViewer != null) {
			treeViewer.setLabelProvider(labelProvider);
		}
	}

	@Override
	public IBaseLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * Gets the tree viewer associated to this editor
	 *
	 * @return the tree viewer associated to this editor
	 */
	public TreeViewer getViewer() {
		return treeViewer;
	}

	@Override
	public void dispose() {
		if (null != tree) {
			this.tree.removeSelectionListener(this);
		}
		super.dispose();
	}

	@Override
	public void refreshValue() {
		treeViewer.refresh();
	}

}
