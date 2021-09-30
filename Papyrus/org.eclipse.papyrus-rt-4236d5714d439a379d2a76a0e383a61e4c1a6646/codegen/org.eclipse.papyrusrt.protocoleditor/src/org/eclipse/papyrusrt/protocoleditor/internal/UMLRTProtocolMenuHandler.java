/*******************************************************************************
 * Copyright (c) 2014 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.protocoleditor.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Package;

public class UMLRTProtocolMenuHandler extends AbstractHandler {

	private TransactionalEditingDomain editingDomain;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (!(sel instanceof IStructuredSelection))
			return null;

		Package selectedPackage = null;
		if (((IStructuredSelection) sel).getFirstElement() instanceof IAdaptable) {
			EObject eObject = (EObject) ((IAdaptable) ((IStructuredSelection) sel)
					.getFirstElement()).getAdapter(EObject.class);
			if (eObject instanceof Package) {
				selectedPackage = (Package) eObject;
			}
		}
		if (selectedPackage == null) {
			return null;
		}

		editingDomain = TransactionUtil.getEditingDomain(selectedPackage);
		if (editingDomain == null) {
			throw new IllegalStateException("Editing domain not found.");
		}
		final Package container = selectedPackage;

		IInputValidator validator = new IInputValidator() {

			@Override
			public String isValid(String name) {
				Package pkg = container.getNestedPackage(name);
				if (pkg != null) {
					return "Invalid name. Name already exist.";
				}
				return null;
			}
		};
		InputDialog nameDialog = new InputDialog(Display.getCurrent()
				.getActiveShell(), "Create Protocol", "Enter protocol name",
				"protocol", validator);
		int result = nameDialog.open();
		if (result == Window.OK) {
			final String name = nameDialog.getValue();
			Command command = new RecordingCommand(editingDomain) {

				@Override
				protected void doExecute() {
					createProtocolContainer(container, name);
				}
			};
			editingDomain.getCommandStack().execute(command);
		}
		return null;
	}

	/**
	 * Create protocol container.
	 * 
	 * @param container
	 * @param name
	 * @return
	 */
	private Package createProtocolContainer(final Package container,
			final String name) {

		Package pContainer = container.createNestedPackage(name);
		UMLRTProtocolUtil.applyStereoType(pContainer,
				"UMLRealTime::ProtocolContainer");

		return pContainer;
	}
}
