
/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Remi Schnekenburger (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands;

import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.statemachine.CreateStateMachineDiagramCommand;

/**
 * UML-RT state machine diagram creation command.
 * This command is called when action is invoked from the UI context menu.
 */
public class CreateRTStateMachineDiagramCommand extends CreateStateMachineDiagramCommand {

	/**
	 * UML-RT Capsule Diagram type.
	 */
	public static final String DIAGRAM_TYPE = "UMLRTStateMachine"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.AbstractPapyrusGmfCreateDiagramCommandHandler#getCreatedDiagramType()
	 *
	 * @return
	 */
	@Override
	public String getCreatedDiagramType() {
		return DIAGRAM_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doEditDiagramName(ViewPrototype prototype, String name) {
		// overrides dialog creation to edit the name
		return CommandResult.newOKCommandResult(null);
	}

}
