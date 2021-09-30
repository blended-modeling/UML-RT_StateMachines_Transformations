/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST- Initial API and implementation
 *  Christian W. Damus - bug 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Edit-helper advice for internal behavior ports.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class InternalBehaviorPortEditHelperAdvice extends AbstractPortEditHelperAdvice {

	/**
	 * Gets the before create relationship command.
	 *
	 * @param request
	 *            the request
	 * @return the before create relationship command
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest)
	 */
	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		return super.getBeforeCreateRelationshipCommand(request);
	}

	/**
	 * Gets the after configure command.
	 *
	 * @param request
	 *            the request
	 * @return the after configure command
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Port internalBehaviorPort = (Port) request.getElementToConfigure();
		// final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("InternalBehaviorPort", internalBehaviorPort.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// isService: false
				internalBehaviorPort.setIsService(false);

				// isBehavior: true
				internalBehaviorPort.setIsBehavior(true);

				// isWired: true
				RTPort stereotype = UMLUtil.getStereotypeApplication(internalBehaviorPort, RTPort.class);
				stereotype.setIsWired(true);

				// isPublish: false
				stereotype.setIsPublish(false);

				// Visibility: protected
				internalBehaviorPort.setVisibility(VisibilityKind.PROTECTED_LITERAL);

				// name
				/* @noname internalBehaviorPort.setName(name); */
				internalBehaviorPort.setName(null);

				return CommandResult.newOKCommandResult(internalBehaviorPort);
			}

		};
	}
}
