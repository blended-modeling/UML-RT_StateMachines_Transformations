/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Onder Gurcan <onder.gurcan@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 493869
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.internal.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramPrototype;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;

/**
 * Specific implementation for the creation of the diagram, to avoid setting a name using a popup
 */
public class CreateHeadlessCapsuleStructureDiagramCommand extends CreateCompositeDiagramCommand {

	@Override
	protected Diagram doCreateDiagram(Resource diagramResource, EObject owner, EObject element, DiagramPrototype prototype, String name) {
		HeadlessDiagramCreationHelper helper = new HeadlessDiagramCreationHelper();

		Diagram result = super.doCreateDiagram(diagramResource, owner, element, prototype, name);

		if (result != null) {
			helper.recordOwnerAndElement(result, owner, element);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doEditDiagramName(ViewPrototype prototype, String name) {
		// overrides dialog creation to edit the name
		return CommandResult.newOKCommandResult(null);
	}

	@Override
	protected void initializeDiagram(EObject diagram) {
		if (diagram instanceof Diagram) {
			Diagram currentDiagram = (Diagram) diagram;
			if (canvasDomainElement != null) {
				// Don't set the element again (we'll do that later)
				// because otherwise it will bake in the capsule's package
				// into the ChangeDescription so that undo will result
				// in the package being the diagram's element and therefore
				// the Model Explorer will show the diagram in the package
				initializeDiagramContent(currentDiagram);
			}
		}
	}

}