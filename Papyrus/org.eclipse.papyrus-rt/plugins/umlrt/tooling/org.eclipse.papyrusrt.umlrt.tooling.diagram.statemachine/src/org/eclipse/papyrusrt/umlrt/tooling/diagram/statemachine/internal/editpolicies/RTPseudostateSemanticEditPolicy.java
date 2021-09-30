/*****************************************************************************
 * Copyright (c) 2017 EclipseSource GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import java.util.stream.Stream;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;

/**
 * Specific edit policy for RT pseudo states (kind entry and exit) when they are located on
 * the border of a composite state, in the context of itself.
 */
public class RTPseudostateSemanticEditPolicy extends RTSemanticEditPolicy {

	/**
	 * Initializes me.
	 */
	public RTPseudostateSemanticEditPolicy() {
		// Disguise this edit-policy behind a delegator so that the
		// CustomEditPolicyProvider from Papyrus won't come along
		// and replace it
		this(new RTPseudostateInternal());
	}

	/**
	 * Initializes me with my internal delegate.
	 *
	 * @param delegate
	 *            my delegate
	 */
	protected RTPseudostateSemanticEditPolicy(Internal delegate) {
		super(delegate);
	}

	//
	// Nested types
	//
	/**
	 * Overrides the {@link Internal} policy to update target container region for {@link Transition}.
	 */
	protected static class RTPseudostateInternal extends Internal {

		/**
		 * Constructor.
		 */
		public RTPseudostateInternal() {
			super();
		}

		@Override
		protected IEditCommandRequest completeRequest(IEditCommandRequest request) {
			IEditCommandRequest result = super.completeRequest(request);
			if (result instanceof CreateRelationshipRequest) {
				// check if it is a transition => in this case, container region for region should be the inner region of the state
				IElementType type = ((CreateRelationshipRequest) request).getElementType();
				if (ElementTypeUtils.isTypeCompatible(type, UMLElementTypes.TRANSITION)) {
					Region region = getContainerRegion();
					if (region != null) {
						((CreateRelationshipRequest) request).setContainer(region);
					}
				}
			}
			return result;
		}

		/**
		 * Returns the region owned by the composite state, in case the pseudo state is a connection point of the composite state and we are in the "inside" context of this composite state.
		 * 
		 * @return the region owned by the state host of this edit policy or <code>null</code> if none was found.
		 */
		protected Region getContainerRegion() {
			// get state parent edit part for this pseudo state edit policy
			// is the host a state as a top node? If yes, container region is the one owned by the owner state. *
			// If not, return null to not modify the request
			Region region = null;
			// parent edit part
			EditPart editPart = getHost();
			EditPart parent = Stream.iterate(editPart, EditPart::getParent).filter(ep -> RTStateEditPartTN.class.isInstance(ep) || ep.getParent() == null).findFirst().orElse(null);
			if (parent instanceof RTStateEditPartTN && ((RTStateEditPartTN) parent).resolveSemanticElement() instanceof State) {
				region = ((State) ((RTStateEditPartTN) parent).resolveSemanticElement()).getRegions().get(0);
			}
			return region;
		}
	}
}
