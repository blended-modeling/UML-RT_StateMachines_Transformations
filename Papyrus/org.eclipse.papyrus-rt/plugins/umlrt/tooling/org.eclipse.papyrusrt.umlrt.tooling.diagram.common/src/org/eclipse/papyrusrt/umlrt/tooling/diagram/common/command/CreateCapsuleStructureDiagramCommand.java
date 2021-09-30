/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.command;

import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;

/**
 * UML-RT capsule structure diagram creation command.
 * This command is called when action is invoked from the UI context menu.
 * 
 * @author Young-Soo Roh
 */
public class CreateCapsuleStructureDiagramCommand extends CreateCompositeDiagramCommand {

	/**
	 * UML-RT Capsule Diagram type.
	 */
	public static final String DIAGRAM_TYPE = "UMLRTCapsuleStructure"; //$NON-NLS-1$

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
