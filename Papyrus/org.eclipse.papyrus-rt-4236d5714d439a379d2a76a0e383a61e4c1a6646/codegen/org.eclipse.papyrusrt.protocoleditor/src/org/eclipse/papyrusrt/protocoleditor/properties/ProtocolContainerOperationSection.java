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
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.umlrt.UMLRealTime.RTMessageKind;
import org.eclipse.papyrus.views.properties.creation.EcorePropertyEditorFactory;
import org.eclipse.papyrus.views.properties.widgets.layout.PropertiesLayout;
import org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil;
import org.eclipse.papyrusrt.protocoleditor.Activator;
import org.eclipse.papyrusrt.protocoleditor.UMLRTModelElementSelectionService;
import org.eclipse.papyrusrt.protocoleditor.internal.UMLRTProtocolUtil;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * MessageSet interface section for protocol editor. Allow drag drop operations
 * between interfaces.
 * 
 * @author ysroh
 *
 */
public class ProtocolContainerOperationSection extends Composite {

	private Composite listArea;

	public ProtocolContainerOperationSection(final Composite parent, int style) {
		super(parent, style);
		EObject selectedElement = UMLRTModelElementSelectionService
				.getSelectedEObject();
		listArea = createListArea(parent, style, selectedElement);
		final UMLRTModelElementSelectionService.IModelExplorerSelectionChangeListener listener = new UMLRTModelElementSelectionService.IModelExplorerSelectionChangeListener() {

			@Override
			public void selectionChanged(ISelection selection) {
				if (selection.isEmpty()) {
					return;
				}
				if (((IStructuredSelection) selection).getFirstElement() instanceof IAdaptable) {
					EObject selectedElement = (EObject) ((IAdaptable) ((IStructuredSelection) selection)
							.getFirstElement()).getAdapter(EObject.class);
					if (selectedElement instanceof Element
							&& UMLRealTimeProfileUtil
									.isProtocolContainer((Element) selectedElement)) {
						if (listArea != null && !listArea.isDisposed()) {
							listArea.dispose();
						}
						if (getParent() != null && !getParent().isDisposed()) {
							listArea = createListArea(getParent(), getParent()
									.getStyle(), selectedElement);
							getParent().layout(true);
						}
					}
				}
			}
		};
		UMLRTModelElementSelectionService.addListener(listener);
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				UMLRTModelElementSelectionService.removeListener(listener);
			}
		});

	}

	private Composite createListArea(final Composite parent, int style,
			EObject selectedElement) {
		Composite composite = new Composite(parent, style);
		composite.setLayout(new PropertiesLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (selectedElement == null) {
			return composite;
		}

		Package protocolContainer = (Package) selectedElement;
		final PackageableElement inMessageSet = UMLRTProtocolUtil
				.getMessageSet(protocolContainer, RTMessageKind.IN);
		final PackageableElement outMessageSet = UMLRTProtocolUtil
				.getMessageSet(protocolContainer, RTMessageKind.OUT);
		final PackageableElement inOutMessageSet = UMLRTProtocolUtil
				.getMessageSet(protocolContainer, RTMessageKind.IN_OUT);

		EcorePropertyEditorFactory factory = new EcorePropertyEditorFactory(
				UMLPackage.Literals.INTERFACE__OWNED_OPERATION);

		if (inMessageSet != null) {
			IObservableList observable = (IObservableList) new UMLModelElement(
					inMessageSet).getObservable("ownedOperation");
			UMLRTMultiReferenceEditor editor = new UMLRTMultiReferenceEditor(
					composite, style, "In", inMessageSet, observable, factory);
			TreeViewer inMessageTreeViewer = editor.getViewer();
			addDnDSupport(inMessageTreeViewer);
		}

		if (outMessageSet != null) {
			IObservableList observable = (IObservableList) new UMLModelElement(
					outMessageSet).getObservable("ownedOperation");
			UMLRTMultiReferenceEditor editor = new UMLRTMultiReferenceEditor(
					composite, style, "Out", outMessageSet, observable, factory);
			TreeViewer outMessageTreeViewer = editor.getViewer();
			addDnDSupport(outMessageTreeViewer);
		}

		if (inOutMessageSet != null) {
			IObservableList observable = (IObservableList) new UMLModelElement(
					inOutMessageSet).getObservable("ownedOperation");
			UMLRTMultiReferenceEditor editor = new UMLRTMultiReferenceEditor(
					composite, style, "InOut", inOutMessageSet, observable,
					factory);
			TreeViewer inOutMessageTreeViewer = editor.getViewer();
			addDnDSupport(inOutMessageTreeViewer);
		}

		return composite;
	}

	/**
	 * Add drag and drop support for viewer
	 * 
	 * @param viewer
	 */
	private void addDnDSupport(final TreeViewer viewer) {
		viewer.addDragSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DragSourceAdapter() {

					public void dragSetData(DragSourceEvent event) {
						final LocalSelectionTransfer transfer = LocalSelectionTransfer
								.getTransfer();
						if (transfer.isSupportedType(event.dataType)) {
							transfer.setSelection(viewer.getSelection());
							transfer.setSelectionSetTime(event.time & 0xFFFF);
						}
					}
				});
		viewer.addDropSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new ViewerDropAdapter(viewer) {

					@Override
					public boolean performDrop(Object data) {
						final LocalSelectionTransfer transfer = LocalSelectionTransfer
								.getTransfer();
						final IStructuredSelection selection = (IStructuredSelection) transfer
								.getSelection();
						if (selection.isEmpty()) {
							return false;
						}
						if (!(selection.getFirstElement() instanceof Operation)) {
							return false;
						}
						final EObject element = (EObject) selection
								.getFirstElement();
						if (!(viewer.getInput() instanceof IObservableList)) {
							return false;
						}
						final IObservableList observable = (IObservableList) viewer
								.getInput();
						TransactionalEditingDomain editingDomain = TransactionUtil
								.getEditingDomain(element);
						EMFCommandOperation command = new EMFCommandOperation(
								editingDomain, new RecordingCommand(
										editingDomain, "Move Element") {

									@Override
									protected void doExecute() {
										observable.add(element);
									}
								});
						try {
							OperationHistoryFactory.getOperationHistory()
									.execute(command, null, null);
						} catch (ExecutionException e1) {
							Activator.log.error("Failed to move element", e1);
						}
						return true;
					}

					@Override
					public boolean validateDrop(Object target, int operation,
							TransferData transferType) {
						final LocalSelectionTransfer transfer = LocalSelectionTransfer
								.getTransfer();
						final int location = getCurrentLocation();
						final boolean valid = (location == LOCATION_NONE || location == LOCATION_ON)
								&& transfer.isSupportedType(transferType);
						return valid;
					}
				});
	}
}
