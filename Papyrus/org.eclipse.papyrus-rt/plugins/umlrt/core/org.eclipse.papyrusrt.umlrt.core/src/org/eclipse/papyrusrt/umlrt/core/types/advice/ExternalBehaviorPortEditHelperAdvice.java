/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
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
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * Edit-helper advice for external behavior ports.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class ExternalBehaviorPortEditHelperAdvice extends AbstractPortEditHelperAdvice {

	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		return super.getBeforeCreateRelationshipCommand(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Port externalBehaviorPort = (Port) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("port", externalBehaviorPort.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// isService: true
				externalBehaviorPort.setIsService(true);

				// isBehavior: true
				externalBehaviorPort.setIsBehavior(true);

				// isWired: true
				RTPort stereotype = UMLUtil.getStereotypeApplication(externalBehaviorPort, RTPort.class);
				stereotype.setIsWired(true);

				// isPublish: false
				stereotype.setIsPublish(false);

				// Visibility: public
				externalBehaviorPort.setVisibility(VisibilityKind.PUBLIC_LITERAL);

				// name
				externalBehaviorPort.setName(name);

				return CommandResult.newOKCommandResult(externalBehaviorPort);
			}

		};
	}
}
