/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: CEA LIST
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.Map;

import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class PackageEditHelperAdvice extends AbstractEditHelperAdvice {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeMoveCommand(final MoveRequest request) {
		ICommand result = null;
		
		CompositeCommand compositeMoveCommand = new CompositeCommand("Composite Move Command");
		
		Map<?, ?> elementsToMove = request.getElementsToMove();
		if (!elementsToMove.isEmpty()) {
			for (Object elementToMove : elementsToMove.keySet()) {
				if (elementToMove instanceof Collaboration) {
					final Element protocol = (Element) elementToMove;
					final Package protocolContainer = protocol.getNearestPackage();
					if (protocolContainer != null) {												
						// create the command for moving the protocol container
						MoveElementsCommand command = ProtocolContainerUtils.createMoveProtocolContainerCommand(request, protocolContainer);
						compositeMoveCommand.add(command);
						// prevent the protocol to be moved independently from the protocol container
						request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, true);
					}
				}
			}
		}
		
		if (compositeMoveCommand.isEmpty()) {
			result = super.getAfterMoveCommand(request);
		} else {
			result = compositeMoveCommand;
		}
		
		return result;
	}
}
