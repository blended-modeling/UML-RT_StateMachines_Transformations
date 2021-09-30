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
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.StateMachine;

/**
 * Queries whether an element is defined in an UML-RT state machine.
 */
public class IsInRTStateMachineQuery implements IJavaQuery2<Element, Boolean> {
	@Override
	public Boolean evaluate(final Element context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		boolean result = false;

		for (Element next = context; next != null; next = next.getOwner()) {
			if (next instanceof StateMachine) {
				result = StateMachineUtils.isRTStateMachine((StateMachine) next);
				break;
			} else if (next instanceof Classifier) {
				// Nested classifiers are not supported in UML-RT state machines
				break;
			}
		}

		return result;
	}
}
