/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;

/**
 * Generic matcher for RT pseudo states, with RT PseudoState Stereotype applied
 */
public abstract class AsbtractRTPseudoStateMatcher implements IElementMatcher {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(EObject eObject) {
		if(eObject instanceof Pseudostate) {
			if (ElementTypeUtils.matches(eObject, IUMLRTElementTypes.RT_PSEUDO_STATE_ID)) {
				// check kind of the element type
				if (getKind().equals(((Pseudostate) eObject).getKind())) {
					return true;
				}
			}
		}
		return false;
	}

	protected abstract PseudostateKind getKind();

}
