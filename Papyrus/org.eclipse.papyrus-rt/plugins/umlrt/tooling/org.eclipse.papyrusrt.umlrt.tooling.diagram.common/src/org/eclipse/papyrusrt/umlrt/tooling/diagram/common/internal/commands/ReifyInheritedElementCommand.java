/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * A command that reifies a virtual inherited model element.
 */
public class ReifyInheritedElementCommand extends AbstractTransactionalCommand {

	private UMLRTNamedElement element;

	/**
	 * Initializes me with the UML-RT façade element to be reified.
	 *
	 * @param element
	 *            the UML-RT façade element
	 */
	public ReifyInheritedElementCommand(UMLRTNamedElement element) {
		super(TransactionUtil.getEditingDomain(element.toUML()), "Redefine Element", getWorkspaceFiles(element.toUML()));

		this.element = element;
	}

	@Override
	public void dispose() {
		element = null;
		super.dispose();
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (element.isVirtualRedefinition()) {
			element.reify();
		}

		return CommandResult.newOKCommandResult();
	}
}
