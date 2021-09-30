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
import org.eclipse.uml2.uml.StateMachine;

/**
 * Queries whether a state machine is an UML-RT state machine.
 */
public class IsRTStateMachineQuery implements IJavaQuery2<StateMachine, Boolean> {
	@Override
	public Boolean evaluate(final StateMachine context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		return StateMachineUtils.isRTStateMachine(context);
	}
}
