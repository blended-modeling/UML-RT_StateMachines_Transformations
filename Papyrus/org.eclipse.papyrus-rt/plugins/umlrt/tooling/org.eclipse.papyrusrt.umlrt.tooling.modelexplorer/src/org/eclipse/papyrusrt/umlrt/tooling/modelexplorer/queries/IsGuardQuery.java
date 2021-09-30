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
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries;

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;

/**
 * Queries whether an element is a Guard in an RT state machine.
 */
public class IsGuardQuery implements IJavaQuery2<Element, Boolean> {
	@Override
	public Boolean evaluate(final Element context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		boolean result = false;

		if (context instanceof Constraint) {
			Constraint constraint = (Constraint) context;
			if (constraint.getContext() instanceof Transition) {
				// Is it in an RT state machine?
				StateMachine stateMachine = ((Transition) constraint.getContext()).containingStateMachine();
				result = (stateMachine != null) && StateMachineUtils.isRTStateMachine(stateMachine);
			}
		}

		return result;
	}
}
