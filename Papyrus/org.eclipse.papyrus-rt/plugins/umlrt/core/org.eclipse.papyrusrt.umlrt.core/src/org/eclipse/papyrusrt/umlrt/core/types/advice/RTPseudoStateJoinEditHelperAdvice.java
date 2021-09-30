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
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.uml2.uml.PseudostateKind;

/**
 * Edit Helper Advice for PseudoState with kind {@link PseudostateKind#JOIN_LITERAL}
 */
public class RTPseudoStateJoinEditHelperAdvice extends AbstractRTPseudoStateEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	protected PseudostateKind getKind() {
		return PseudostateKind.JOIN_LITERAL;
	}

}
