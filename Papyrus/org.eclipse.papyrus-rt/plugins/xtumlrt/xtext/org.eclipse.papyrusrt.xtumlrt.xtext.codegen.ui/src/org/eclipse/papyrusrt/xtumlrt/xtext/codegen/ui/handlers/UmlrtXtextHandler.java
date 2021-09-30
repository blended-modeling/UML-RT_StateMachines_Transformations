/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.xtext.codegen.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrusrt.codegen.UMLRTCodeGenerator;
import org.eclipse.papyrusrt.codegen.config.CodeGenProvider;
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.xtext.ui.outline.UmlrtOutlineTreeProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class UmlrtXtextHandler extends AbstractHandler {

	/** The code generator. */
	protected static UMLRTCodeGenerator generator = CodeGenProvider.getDefault().get();

	/**
	 * The constructor.
	 */
	public UmlrtXtextHandler() {
	}

	/**
	 * The command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {

			IStructuredSelection selection = (IStructuredSelection) sel;
			if (!selection.isEmpty()) {
				Object selected = selection.getFirstElement();
				EObject eobj = getEObject(selected);
				if (eobj != null) {
					EObject eobjRoot = EcoreUtil.getRootContainer(eobj);

					final IStatus status = generate(eobjRoot);

					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							ErrorDialog.openError(Display.getCurrent().getActiveShell(), "UML-RT Code Generator", null, status);
						}
					});
				}
			}
		}
		return null;
	}

	/**
	 * Invokes the preprocessor and code generator.
	 * 
	 * @param eobjRoot
	 *            - The root model element (an {@link EObject}.)
	 * @return The {@link IStatus} with the result of code-generation.
	 */
	public static IStatus generate(EObject eobjRoot) {
		IStatus status = null;

		CommonElement element = (CommonElement) eobjRoot;
		String topCapsuleName = getTopCapsuleName(element);
		List<EObject> targets = new ArrayList<>();

		if (eobjRoot instanceof Model) {

			targets.add(eobjRoot);

			status = generator.generate(targets, topCapsuleName, false);
		}
		return status;
	}

	/**
	 * Obtain the {@link EObject} for a given {@link EObjectNode} in the outline view.
	 * 
	 * @param obj
	 *            - An {@link Object}/
	 * 
	 * @return If obj is not null and is an instance of an {@link EObjectNode}, the corresponding {@link EObject}.
	 */
	public static EObject getEObject(Object obj) {
		EObject eobj = null;
		if (obj != null && obj instanceof EObjectNode) {
			EObjectNode on = (EObjectNode) obj;
			EObject baseEobj = UmlrtOutlineTreeProvider.getModelElement();
			Resource resource = baseEobj.eResource();
			eobj = on.getEObject(resource);
		}
		return eobj;
	}


	/**
	 * Retrieve top capsule name.
	 * 
	 * @param root
	 *            root element
	 * @return top capsule name
	 */
	public static String getTopCapsuleName(CommonElement root) {
		String retVal = null;

		// TODO: Accept other top capsules

		return retVal != null ? retVal : "Top";
	}
}
