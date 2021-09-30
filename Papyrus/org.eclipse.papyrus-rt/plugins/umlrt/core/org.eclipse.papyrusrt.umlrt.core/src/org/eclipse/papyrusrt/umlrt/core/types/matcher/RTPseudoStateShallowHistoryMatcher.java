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

import org.eclipse.uml2.uml.PseudostateKind;

/**
 * Matcher for RT pseudo states (initial)
 */
public class RTPseudoStateShallowHistoryMatcher extends AsbtractRTPseudoStateMatcher {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PseudostateKind getKind() {
		return PseudostateKind.SHALLOW_HISTORY_LITERAL;
	}
}
