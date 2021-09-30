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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * Edit-helper advice for SAPs.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class ServiceAccessPointEditHelperAdvice extends AbstractPortEditHelperAdvice {



	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			// get the context, to check it is really needed to check here
			Object editHelperContext = ((CreateRelationshipRequest) request).getEditHelperContext();
			if (editHelperContext instanceof EObject) {
				if (ElementTypeUtils.matches((EObject) editHelperContext, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
					IElementType type = ((CreateRelationshipRequest) request).getElementType();
					if (type != null && ElementTypeUtils.isTypeCompatible(type, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_CONNECTOR_ID))) {
						return false; // can not connect a SAP with a connector
					}
				}
			}
		}
		return super.approveRequest(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Port serviceAccessPoint = (Port) request.getElementToConfigure();
		// final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("ServiceAccessPoint", serviceAccessPoint.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// isService: false
				serviceAccessPoint.setIsService(false);

				// isBehavior: true
				serviceAccessPoint.setIsBehavior(true);

				// isWired: false
				RTPort stereotype = UMLUtil.getStereotypeApplication(serviceAccessPoint, RTPort.class);
				stereotype.setIsWired(false);

				// isPublish: false
				stereotype.setIsPublish(false);

				// Visibility: protected
				serviceAccessPoint.setVisibility(VisibilityKind.PROTECTED_LITERAL);

				// name
				/* @noname serviceAccessPoint.setName(name); */
				serviceAccessPoint.setName(null);

				return CommandResult.newOKCommandResult(serviceAccessPoint);
			}

		};
	}
}
