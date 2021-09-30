/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;

/**
 * Facet query for the single generalization permitted by UML-RT on a capsule
 * class or protocol collaboration.
 */
public class GetGeneralizationQuery implements IJavaQuery2<Classifier, Generalization> {
	@Override
	public Generalization evaluate(final Classifier context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		Generalization result = null;

		if (!context.getGeneralizations().isEmpty()) {
			result = context.getGeneralizations().get(0);
		}

		return result;
	}
}
