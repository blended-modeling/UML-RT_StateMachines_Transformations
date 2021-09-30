/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510189
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

/**
 * Utility class for operations that are UML-RT protocol messages.
 */
public class MessageUtils {

	/**
	 * Returns the corresponding CallEvent of a given operation if exists. Returns null otherwise.
	 * 
	 * @param operation
	 */
	public static CallEvent getCallEvent(Operation operation) {
		CallEvent result = null;

		Package nearestPackage = operation.getNearestPackage();
		if (nearestPackage != null) {
			for (Element element : nearestPackage.getOwnedElements()) {
				if (element instanceof CallEvent) {
					final CallEvent callEvent = (CallEvent) element;
					if (operation.equals(callEvent.getOperation())) {
						result = callEvent;
						break;
					}
				}
			}
		}

		return result;
	}

	public static MoveElementsCommand createMoveCallEventCommand(final MoveRequest request, final CallEvent callEvent) {
		MoveElementsCommand moveElementsCommand = new MoveElementsCommand(request) {
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				EObject protocolContainer = PackageUtils.getNearestPackage(request.getTargetContainer());
				MoveRequest callEventMoveRequest = new MoveRequest(protocolContainer, callEvent);
				MoveElementsCommand command = new MoveElementsCommand(callEventMoveRequest);
				command.execute(monitor, info);

				return super.doExecuteWithResult(monitor, info);
			}
		};
		return moveElementsCommand;
	}

}
