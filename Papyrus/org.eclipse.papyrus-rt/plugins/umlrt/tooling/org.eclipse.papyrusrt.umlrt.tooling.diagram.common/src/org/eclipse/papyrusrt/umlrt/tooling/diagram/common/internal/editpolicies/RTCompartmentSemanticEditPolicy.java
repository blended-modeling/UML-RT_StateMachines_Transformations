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
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCompartmentSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.DelegatingEditPolicy;

/**
 * A specialized Semantic Edit Policy for Compartments that understands the
 * inheritance in diagrams and resolves elements correctly when completing
 * requests, especially the {@link DestroyElementRequest}.
 */
public class RTCompartmentSemanticEditPolicy extends DelegatingEditPolicy {

	/**
	 * Initializes me.
	 */
	public RTCompartmentSemanticEditPolicy() {
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
	protected RTCompartmentSemanticEditPolicy(Internal delegate) {
		super(delegate);
	}

	//
	// Nested types
	//

	protected static class Internal extends DefaultCompartmentSemanticEditPolicy {

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
					: ViewUtil.resolveSemanticElement((View) editPart.getModel());
		}

		protected EObject getHostElement() {
			return resolveSemanticElement(getHost());
		}
	}
}
