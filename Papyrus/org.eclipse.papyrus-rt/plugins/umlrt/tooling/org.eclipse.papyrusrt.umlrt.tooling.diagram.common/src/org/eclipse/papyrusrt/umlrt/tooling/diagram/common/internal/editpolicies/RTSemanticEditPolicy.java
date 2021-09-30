/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.DelegatingEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;

/**
 * A specialized Semantic Edit Policy that understands the inheritance in diagrams
 * and resolves elements correctly when completing requests, especially the
 * {@link DestroyElementRequest}.
 */
public class RTSemanticEditPolicy extends DelegatingEditPolicy {

	/**
	 * Initializes me.
	 */
	public RTSemanticEditPolicy() {
		// Disguise this edit-policy behind a delegator so that the
		// CustomEditPolicyProvider from Papyrus won't come along
		// and replace it
		this(new Internal());
	}

	/**
	 * Initializes me with my internal delegate.
	 *
	 * @param delegate
	 *            my delegate
	 */
	protected RTSemanticEditPolicy(Internal delegate) {
		super(delegate);
	}

	//
	// Nested types
	//

	protected static class Internal extends DefaultSemanticEditPolicy {

		protected Internal() {
			super();
		}

		@Override
		protected IEditCommandRequest completeRequest(IEditCommandRequest request) {
			IEditCommandRequest result = request;

			if (result instanceof DestroyRequest) {
				DestroyRequest destroyRequest = (DestroyRequest) result;

				if (getHostElement() != null) {
					// Destroy element request

					if (destroyRequest instanceof DestroyElementRequest) {
						((DestroyElementRequest) destroyRequest)
								.setElementToDestroy(getHostElement());
						((DestroyElementRequest) destroyRequest).getParameters().clear();
					} else {
						result = new DestroyElementRequest(request
								.getEditingDomain(), getHostElement(), destroyRequest
										.isConfirmationRequired());
						result.addParameters(request.getParameters());
					}


				} else if (getHost() instanceof ConnectionEditPart) {
					// Destroy reference request
					ConnectionEditPart connection = (ConnectionEditPart) getHost();
					EObject container = resolveSemanticElement(connection.getSource());
					EObject referenceObject = resolveSemanticElement(connection.getTarget());

					if (destroyRequest instanceof DestroyReferenceRequest) {
						DestroyReferenceRequest destroyReferenceRequest = (DestroyReferenceRequest) result;

						destroyReferenceRequest.setContainer(container);
						destroyReferenceRequest.setReferencedObject(referenceObject);

					} else {
						result = new DestroyReferenceRequest(((IGraphicalEditPart) getHost()).getEditingDomain(),
								container, null, referenceObject,
								destroyRequest.isConfirmationRequired());

						result.addParameters(request.getParameters());
					}
				}
			} else {
				result = super.completeRequest(request);
			}

			return result;
		}

		protected static EObject resolveSemanticElement(EditPart editPart) {
			return (editPart instanceof IGraphicalEditPart)
					? ((IGraphicalEditPart) editPart).resolveSemanticElement()
					: EditPartInheritanceUtils.resolveSemanticElement((View) editPart.getModel());
		}

		protected EObject getHostElement() {
			return resolveSemanticElement(getHost());
		}

		@Override
		protected Command getReorientRelationshipSourceCommand(ReconnectRequest request) {
			EObject connectionSemElement = resolveSemanticElement(request.getConnectionEditPart());
			EObject targetSemElement = resolveSemanticElement(request.getTarget());
			EObject oldSemElement = resolveSemanticElement(request.getConnectionEditPart().getSource());

			TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
					.getEditingDomain();
			ReorientRelationshipRequest semRequest = new ReorientRelationshipRequest(
					editingDomain, connectionSemElement, targetSemElement,
					oldSemElement, ReorientRelationshipRequest.REORIENT_SOURCE);

			semRequest.addParameters(request.getExtendedData());

			return getSemanticCommand(semRequest);
		}

		@Override
		protected Command getReorientRelationshipTargetCommand(ReconnectRequest request) {
			EObject connectionSemElement = resolveSemanticElement(request.getConnectionEditPart());
			EObject targetSemElement = resolveSemanticElement(request.getTarget());
			EObject oldSemElement = resolveSemanticElement(request.getConnectionEditPart().getTarget());

			// check if we need to redirect the semantic request because of a tree
			// gesture.
			String connectionHint = ViewUtil.getSemanticElementClassId((View) request.getConnectionEditPart().getModel());
			if (((View) request.getTarget().getModel()).getElement() != null) {
				String targetHint = ViewUtil.getSemanticElementClassId((View) request.getTarget().getModel());

				if ((request.getConnectionEditPart() instanceof ITreeBranchEditPart)
						&& (request.getTarget() instanceof ITreeBranchEditPart)
						&& connectionHint.equals(targetHint)) {

					ITreeBranchEditPart targetBranch = (ITreeBranchEditPart) request.getTarget();
					targetSemElement = resolveSemanticElement(targetBranch.getTarget());
				}
			}

			TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
			ReorientRelationshipRequest semRequest = new ReorientRelationshipRequest(
					editingDomain, connectionSemElement, targetSemElement,
					oldSemElement, ReorientRelationshipRequest.REORIENT_TARGET);

			semRequest.addParameters(request.getExtendedData());

			return getSemanticCommand(semRequest);
		}
	}
}
