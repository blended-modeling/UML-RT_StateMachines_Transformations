/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.uml2.internal.postprocessor.AbstractUMLChangeFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * Specialization of the {@link AbstractUMLChangeFactory} for UML-RT.
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractUMLRTChangeFactory extends AbstractUMLChangeFactory {

	/**
	 * Returns the match of the {@link #getDiscriminant(Diff) discriminant} of
	 * the given <code>change</code>.
	 * 
	 * @see org.eclipse.emf.compare.uml2.internal.postprocessor.AbstractUMLChangeFactory#getParentMatch(org.eclipse.emf.compare.Diff)
	 *
	 * @param change
	 *            The custom change created by this change factory.
	 * @return The parent match that should be used for the custom change
	 *         created by this change factory.
	 */
	@Override
	public Match getParentMatch(Diff change) {
		final Match match = change.getMatch();
		final Comparison comparison = match.getComparison();
		final EObject discriminant = getDiscriminant(change);
		return comparison.getMatch(discriminant);
	}

}
