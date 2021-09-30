/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 474481, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Edit Helper Advice for {@link RTPort}
 */
public class RTPortEditHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		boolean result = false;

		if (request instanceof CreateRelationshipRequest) {
			IElementType type = ((CreateRelationshipRequest) request).getElementType();
			if (type != null && ElementTypeUtils.isTypeCompatible(type, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_CONNECTOR_ID))) {
				result = checkSourceAndTarget(((CreateRelationshipRequest) request));
			} else {
				result = super.approveRequest(request);
			}
		} else if (request instanceof CreateElementRequest) {
			IElementType type = ((CreateElementRequest) request).getElementType();
			if (type != null && ElementTypeUtils.isTypeCompatible(type, UMLElementTypes.PROPERTY)) {
				result = false;
			}
		} else {
			result = super.approveRequest(request);
		}

		return result;
	}


	/**
	 * Check if a relationShip can be created
	 * 
	 * @param createRelationshipRequest
	 *            Request to create a relationship from or to a port
	 * @return true if it is possible to create such a relationShip
	 */
	protected boolean checkSourceAndTarget(CreateRelationshipRequest createRelationshipRequest) {
		EObject source = createRelationshipRequest.getSource();
		EObject target = createRelationshipRequest.getTarget();

		if (source != null) {
			if (ElementTypeUtils.matches(source, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
				return false; // cannot connect a connector to a SAP
			} else if (ElementTypeUtils.matches(source, IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID)) {
				return false;
			}
		}

		if (target != null) {
			if (ElementTypeUtils.matches(target, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
				return false; // cannot connect a connector to a SAP
			} else if (ElementTypeUtils.matches(target, IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final Port port = (Port) request.getElementToConfigure();

		// This port is new if it is being configured
		NewElementUtil.elementCreated(port);

		CompositeCommand compositeCommand = new CompositeCommand("Before Configure RTPort");

		// If the newly create Port has no multiplicity set it to the default Value
		// if (null == port.getLowerValue()) {
		// // Set the new value based on the kind set
		// LiteralInteger newLowerValue = UMLFactory.eINSTANCE.createLiteralInteger();
		// newLowerValue.setValue(1);
		//
		// SetRequest setLowerBounds = new SetRequest(port, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), newLowerValue);
		// IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(port);
		// compositeCommand.add(commandProvider.getEditCommand(setLowerBounds));
		//
		// }
		//
		// if (null == port.getUpperValue()) {
		// LiteralUnlimitedNatural newUpperValue = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();
		// newUpperValue.setValue(1);
		//
		// SetRequest setUpperBounds = new SetRequest(port, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), newUpperValue);
		// IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(port);
		// compositeCommand.add(commandProvider.getEditCommand(setUpperBounds));
		// }




		compositeCommand.add(new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				port.setIsOrdered(true);

				return CommandResult.newOKCommandResult(port);
			}
		});

		return compositeCommand.isEmpty() ? super.getBeforeConfigureCommand(request) : compositeCommand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest)
	 * 
	 */
	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		return super.getBeforeCreateRelationshipCommand(request);
	}


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 * 
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		CompositeCommand compositeCommand = new CompositeCommand("Set RTPort");

		EStructuralFeature feature = request.getFeature();
		Port portToEdit = (Port) request.getElementToEdit();
		RTPort rtPortToEdit = UMLUtil.getStereotypeApplication(portToEdit, RTPort.class);

		if (UMLPackage.eINSTANCE.getPort_IsService().equals(feature)) {

			ICommand visibilityCommand = getVisibilityCommand(request, portToEdit);
			if (null != visibilityCommand && visibilityCommand.canExecute()) {
				compositeCommand.add(visibilityCommand);
			}

			// isPublish = (!isWired && isService)
			ICommand publishedCommand = getPublishCommand(request, rtPortToEdit);
			if (null != publishedCommand && publishedCommand.canExecute()) {
				compositeCommand.add(publishedCommand);
			}

		}


		ICommand setKindCommand = getSetKindCommand(request);
		if (null != setKindCommand) {
			compositeCommand.add(setKindCommand);
		}

		return compositeCommand.isEmpty() ? super.getAfterSetCommand(request) : compositeCommand;
	}

	/**
	 * Get the visibility Command
	 * 
	 * @param request
	 * @param portToEdit
	 * @return The Command to set the visibility
	 */
	protected ICommand getVisibilityCommand(SetRequest request, Port portToEdit) {
		// The visibility of the port depends on the isService Value
		boolean isService = (Boolean) request.getValue();

		// set visibility of the port
		EAttribute visibilityFeature = UMLPackage.eINSTANCE.getNamedElement_Visibility();
		VisibilityKind visibility;

		if (isService) {
			// isService => public
			visibility = VisibilityKind.PUBLIC_LITERAL;
		} else {
			// !isService => protected
			visibility = VisibilityKind.PROTECTED_LITERAL;
		}

		IElementEditService serviceEdit = ElementEditServiceUtils.getCommandProvider(portToEdit);
		SetRequest setVisibilityRequest = new SetRequest(portToEdit, visibilityFeature, visibility);
		ICommand visibilityCommand = serviceEdit.getEditCommand(setVisibilityRequest);

		return visibilityCommand;
	}

	/**
	 * Get the publish Command
	 * 
	 * @param request
	 *            the Request
	 * @param rtPortToEdit
	 *            The RT Port to edit
	 * @return the Command to set the Publish property of a port
	 */
	protected ICommand getPublishCommand(SetRequest request, RTPort rtPortToEdit) {
		// isPublish = (!isWired && isService)
		boolean isWired = rtPortToEdit.isWired();
		boolean isService = (Boolean) request.getValue();
		boolean isPublish = !isWired && isService;
		IElementEditService serviceEdit = ElementEditServiceUtils.getCommandProvider(rtPortToEdit);
		EStructuralFeature featurePublish = UMLRealTimePackage.eINSTANCE.getRTPort_IsPublish();
		SetRequest requestPublish = new SetRequest(rtPortToEdit, featurePublish, isPublish);
		ICommand gmfCommand = serviceEdit.getEditCommand(requestPublish);
		return gmfCommand;
	}

	/**
	 * Get the Command to set the Kind of a Port.
	 * 
	 * @param request
	 *            the Request
	 * @return the Command to set the Kind property of a port
	 */
	protected ICommand getSetKindCommand(SetRequest request) {
		ICommand cmd = null;
		final EObject object = request.getElementToEdit();
		final Object kindParameter = request.getParameter(RTPortUtils.RTPORT_KIND_REQUEST_PARAMETER);
		if ((object instanceof Port) && (kindParameter instanceof UMLRTPortKind)) {
			final Port rtPort = (Port) object;
			final UMLRTPortKind newKind = (UMLRTPortKind) kindParameter;
			cmd = RTPortUtils.getChangePortKindCommand(rtPort, newKind);
		}

		return cmd;
	}

}
