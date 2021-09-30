/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;

/**
 * Matcher for transitions that are of kind {@link TransitionKind#INTERNAL}.
 */
public class InternalTransitionMatcher implements IElementMatcher {

	/**
	 * Constructor.
	 */
	public InternalTransitionMatcher() {
		// empty
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(EObject eObject) {
		return eObject instanceof Transition && TransitionKind.INTERNAL_LITERAL == ((Transition) eObject).getKind();
	}
}