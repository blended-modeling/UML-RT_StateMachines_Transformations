/*******************************************************************************
 * Copyright (c) 2014 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.protocoleditor.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.creation.IAtomicOperationExecutor;
import org.eclipse.papyrus.infra.widgets.providers.TreeCollectionContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.papyrus.views.properties.creation.EcorePropertyEditorFactory;
import org.eclipse.papyrusrt.protocoleditor.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

/**
 * Editor for multi-valued reference property
 * 
 * @author ysroh
 *
 */
public class UMLRTMultiReferenceEditor extends Composite implements
		ISelectionChangedListener, IChangeListener {

	protected Composite buttonSection;

	protected TreeViewer treeViewer;

	protected EcorePropertyEditorFactory factory;

	protected EObject context;

	protected IAtomicOperationExecutor executor;

	protected TransactionalEditingDomain editingDomain;

	protected Button up;

	protected Button down;

	protected Button add;

	protected Button remove;

	protected Button edit;

	IObservableList observable;

	/**
	 * Constructor
	 * 
	 * @param parent
	 * @param style
	 * @param label
	 * @param context
	 * @param reference
	 * @param ownerReferenceFeature
	 */
	public UMLRTMultiReferenceEditor(Composite parent, int style, String label,
			EObject context, IObservableList observable,
			EcorePropertyEditorFactory propertyFactory) {
		super(parent, style);
		this.context = context;
		this.observable = observable;
		observable.addChangeListener(this);
		this.editingDomain = TransactionUtil.getEditingDomain(context);
		executor = (IAtomicOperationExecutor) Platform.getAdapterManager()
				.getAdapter(context, IAtomicOperationExecutor.class);

		this.factory = propertyFactory;

		setLayout(new GridLayout(label == null ? 1 : 2, false));
		if (label != null) {
			Label labelWidget = new Label(this, SWT.NONE);
			labelWidget.setText(label);
		}

		buttonSection = new Composite(this, SWT.NONE);
		buttonSection.setLayout(new FillLayout());
		buttonSection.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
				false));

		Tree tree = new Tree(this, style | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		GridData treeData = new GridData(SWT.FILL, SWT.NONE, true, true);
		treeData.horizontalSpan = 2;
		treeData.heightHint = 80;
		tree.setLayoutData(treeData);

		treeViewer = new TreeViewer(tree);
		treeViewer.addSelectionChangedListener(this);
		treeViewer.setContentProvider(TreeCollectionContentProvider.instance);
		// treeViewer.setContentProvider(new MultiValueTreeContentProvider());
		treeViewer.setLabelProvider(new UMLLabelProvider());
		treeViewer.setInput(observable);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(final DoubleClickEvent e) {
				final EObject selectedElement = getSelectedElement();
				if (selectedElement != null) {
					EMFCommandOperation command = new EMFCommandOperation(
							editingDomain, new RecordingCommand(editingDomain,
									"Edit Element") {

								@Override
								protected void doExecute() {
									if (factory.canEdit()) {
										factory.edit(treeViewer.getControl(),
												selectedElement);
									}
								}
							});
					try {
						OperationHistoryFactory.getOperationHistory().execute(
								command, null, null);
					} catch (ExecutionException e1) {
						Activator.log.error("Failed to add new element", e1);
					}
					treeViewer.refresh(selectedElement, true);
				}
			}
		});

		createControlButtons();
	}

	@Override
	public void dispose() {
		observable.removeChangeListener(this);
		super.dispose();
	}

	/**
	 * Get selected element
	 * 
	 * @return
	 */
	private EObject getSelectedElement() {
		ISelection selection = treeViewer.getSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return (EObject) ((IStructuredSelection) selection).getFirstElement();
	}

	/**
	 * Create control buttons
	 */
	protected void createControlButtons() {
		up = createButton(org.eclipse.papyrus.infra.widgets.Activator
				.getDefault().getImage("/icons/Up_12x12.gif"), "Move up");
		up.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object selectedElement = getSelectedElement();
				if (selectedElement == null) {
					return;
				}
				EMFCommandOperation command = new EMFCommandOperation(
						editingDomain, new RecordingCommand(editingDomain,
								"Move Element") {

							@Override
							protected void doExecute() {
								int index = observable.indexOf(selectedElement);
								if (index == 0) {
									return;
								}
								observable.move(index - 1, index);
							}
						});
				try {
					OperationHistoryFactory.getOperationHistory().execute(
							command, null, null);
				} catch (ExecutionException e1) {
					Activator.log.error("Failed to move element", e1);
				}
				treeViewer.refresh();
			}
		});
		down = createButton(org.eclipse.papyrus.infra.widgets.Activator
				.getDefault().getImage("/icons/Down_12x12.gif"), "Move down");
		down.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object selectedElement = getSelectedElement();
				if (selectedElement == null) {
					return;
				}
				EMFCommandOperation command = new EMFCommandOperation(
						editingDomain, new RecordingCommand(editingDomain,
								"Move Element") {

							@Override
							protected void doExecute() {
								int index = observable.indexOf(selectedElement);
								if (index + 1 == observable.size()) {
									return;
								}
								observable.move(index + 1, index);
							}
						});
				try {
					OperationHistoryFactory.getOperationHistory().execute(
							command, null, null);
				} catch (ExecutionException e1) {
					Activator.log.error("Failed to move element", e1);
				}
				treeViewer.refresh();
			}
		});
		add = createButton(org.eclipse.papyrus.infra.widgets.Activator
				.getDefault().getImage("/icons/Add_12x12.gif"), "Add element");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EMFCommandOperation command = new EMFCommandOperation(
						editingDomain, new RecordingCommand(editingDomain,
								"Add Element") {

							@Override
							protected void doExecute() {
								if (factory.canCreateObject()) {
									final Object result = factory.createObject(
											(Control) e.getSource(), context);
									if (result != null) {
										observable.add(result);
									}
								}
							}
						});
				try {
					OperationHistoryFactory.getOperationHistory().execute(
							command, null, null);
				} catch (ExecutionException e1) {
					Activator.log.error("Failed to add new element", e1);
				}
				treeViewer.refresh();
			}
		});
		remove = createButton(org.eclipse.papyrus.infra.widgets.Activator
				.getDefault().getImage("/icons/Delete_12x12.gif"),
				"Remove element");
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object selectedElement = getSelectedElement();
				if (selectedElement == null) {
					return;
				}
				treeViewer.setSelection(null);
				treeViewer.remove(selectedElement);
				EMFCommandOperation command = new EMFCommandOperation(
						editingDomain, new RecordingCommand(editingDomain,
								"Delete Element") {

							@Override
							protected void doExecute() {
								observable.remove(selectedElement);
							}
						});
				try {
					OperationHistoryFactory.getOperationHistory().execute(
							command, null, null);
				} catch (ExecutionException e1) {
					Activator.log.error("Failed to add new element", e1);
				}
			}
		});
		edit = createButton(org.eclipse.papyrus.infra.widgets.Activator
				.getDefault().getImage("/icons/Edit_12x12.gif"), "Edit element");
		edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Object selectedElement = getSelectedElement();
				if (selectedElement == null) {
					return;
				}
				EMFCommandOperation command = new EMFCommandOperation(
						editingDomain, new RecordingCommand(editingDomain,
								"Edit Element") {

							@Override
							protected void doExecute() {
								if (factory.canEdit()) {
									factory.edit((Control) e.getSource(),
											selectedElement);
								}
							}
						});
				try {
					OperationHistoryFactory.getOperationHistory().execute(
							command, null, null);
				} catch (ExecutionException e1) {
					Activator.log.error("Failed to add new element", e1);
				}
				treeViewer.refresh(selectedElement, true);
			}
		});
	}

	protected Button createButton(Image image, String toolTipText) {
		Button button = new Button(buttonSection, SWT.PUSH);
		button.setImage(image);
		button.setToolTipText(toolTipText);
		return button;
	}

	/**
	 * Queries the tree viewer
	 * 
	 * @return
	 */
	public TreeViewer getViewer() {
		return treeViewer;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		// handle selection change
	}

	@Override
	public void handleChange(ChangeEvent arg0) {
		if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
			treeViewer.refresh();
		}
	}

	public class MultiReferenceEditorContentProvider implements
			ITreeContentProvider {

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

		}

		@Override
		public Object[] getChildren(Object arg0) {
			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IObservableList) {
				return ((IObservableList) inputElement).toArray();
			}

			return new Object[] {};
		}

		@Override
		public Object getParent(Object arg0) {
			return null;
		}

		@Override
		public boolean hasChildren(Object arg0) {
			return false;
		}
	}
}
