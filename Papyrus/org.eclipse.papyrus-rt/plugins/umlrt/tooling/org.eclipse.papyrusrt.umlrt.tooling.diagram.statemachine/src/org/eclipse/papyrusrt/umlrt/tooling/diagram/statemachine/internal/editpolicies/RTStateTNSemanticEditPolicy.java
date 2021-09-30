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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;

/**
 * Specific edit policy for semantic role for "internal" view for composite state. This will allow for example retargeting of region container for transitions.
 */
public class RTStateTNSemanticEditPolicy extends RTSemanticEditPolicy {

	/**
	 * Constructor.
	 */
	public RTStateTNSemanticEditPolicy() {
		this(new RTStateTNInternal());
	}

	/**
	 * Initializes me with my internal delegate.
	 *
	 * @param delegate
	 *            my delegate
	 */
	protected RTStateTNSemanticEditPolicy(Internal delegate) {
		super(delegate);
	}

	//
	// Nested types
	//
	/**
	 * Overrides the {@link Internal} policy to update target container region for {@link Transition}.
	 */
	protected static class RTStateTNInternal extends Internal {

		/**
		 * Constructor.
		 */
		public RTStateTNInternal() {
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
		 * Returns the region owned by the composite state.
		 * 
		 * @return the region owned by the state host of this edit policy or <code>null</code> if none was found.
		 */
		protected Region getContainerRegion() {
			if (getHost() instanceof IGraphicalEditPart) {
				EObject semantic = EditPartInheritanceUtils.resolveSemanticElement(getHost(), EObject.class);
				if (StateUtils.isCompositeState(semantic)) {
					return ((State) semantic).getRegions().get(0);
				}
			}
			return null;
		}

	}
}
