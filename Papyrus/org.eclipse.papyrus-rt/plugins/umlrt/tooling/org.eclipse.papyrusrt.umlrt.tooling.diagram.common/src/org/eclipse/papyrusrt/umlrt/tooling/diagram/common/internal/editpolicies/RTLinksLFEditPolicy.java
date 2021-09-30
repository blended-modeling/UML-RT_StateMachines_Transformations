/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SemanticCreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.DefaultLinksLFEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;

/**
 * An inheritance-aware Link LF edit-policy.
 */
public class RTLinksLFEditPolicy extends DefaultLinksLFEditPolicy {

	public RTLinksLFEditPolicy() {
		super();
	}

	@Override
	protected Command getConnectionAndRelationshipCreateCommand(
			CreateConnectionViewAndElementRequest request) {
		// get the element descriptor
		CreateElementRequestAdapter requestAdapter = request
				.getConnectionViewAndElementDescriptor().getCreateElementRequestAdapter();
		// get the semantic request
		CreateRelationshipRequest createElementRequest = (CreateRelationshipRequest) requestAdapter
				.getAdapter(CreateRelationshipRequest.class);
		// complete the semantic request by filling in the source
		View sourceView = (View) getHost().getModel();
		createElementRequest.setSource(EditPartInheritanceUtils.resolveSemanticElement(sourceView));
		// get the create element request based on the elementdescriptor's
		// request
		Command createElementCommand = getHost().getCommand(
				new EditCommandRequestWrapper(
						(CreateRelationshipRequest) requestAdapter
								.getAdapter(CreateRelationshipRequest.class),
						request.getExtendedData()));
		// if element cannot be created, ignore
		if (createElementCommand == null || !createElementCommand.canExecute()) {
			// Even if the command is not executable, status information may be set.
			return createElementCommand;
		}

		return getConnectionCreateCommand(request);
	}

	@Override
	protected Command getConnectionAndRelationshipCompleteCommand(
			CreateConnectionViewAndElementRequest request) {

		//
		// From DefaultLinksLFEditPolicy
		//

		// Add parameter (source and target view to the CreateRelationshipRequest
		CreateElementRequestAdapter requestAdapter = request.getConnectionViewAndElementDescriptor().getCreateElementRequestAdapter();
		CreateRelationshipRequest createElementRequest = (CreateRelationshipRequest) requestAdapter.getAdapter(CreateRelationshipRequest.class);

		View sourceView = (View) request.getSourceEditPart().getModel();
		createElementRequest.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW, sourceView);

		View targetView = (View) request.getTargetEditPart().getModel();
		createElementRequest.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW, targetView);
		createElementRequest.setParameter(RequestParameterConstants.EDGE_TARGET_POINT, request.getLocation());

		createElementRequest.setPrompt(!request.isUISupressed());

		//
		// From GraphicalNodeEditPolicy
		//

		INodeEditPart targetEP = getConnectionCompleteEditPart(request);

		// resolve the source
		EObject source = EditPartInheritanceUtils.resolveSemanticElement(sourceView);
		if (source == null) {
			source = sourceView;
		}
		createElementRequest.setSource(source);

		// resolve the target
		EObject target = EditPartInheritanceUtils.resolveSemanticElement(targetView);
		if (target == null) {
			target = targetView;
		}
		createElementRequest.setTarget(target);

		// get the create element request based on the elementdescriptor's
		// request
		Command createElementCommand = targetEP
				.getCommand(new EditCommandRequestWrapper(
						(CreateRelationshipRequest) requestAdapter
								.getAdapter(CreateRelationshipRequest.class),
						request.getExtendedData()));

		// create the create semantic element wrapper command
		if (null == createElementCommand)
			return null;

		SemanticCreateCommand semanticCommand = new SemanticCreateCommand(
				requestAdapter, createElementCommand);
		// get the view command
		Command viewCommand = getConnectionCompleteCommand(request);
		if (null == viewCommand)
			return null;
		// form the compound command and return
		CompositeCommand cc = new CompositeCommand(semanticCommand.getLabel());
		cc.compose(semanticCommand);
		cc.compose(new CommandProxy(viewCommand));
		return new ICommandProxy(cc);
	}

}
