/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.collaboration;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetReference;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.utils.FacetUtils;
import org.eclipse.uml2.uml.Collaboration;

/**
 * Query that returns always false, except when the facet reference is comment/message in,out or inout.
 */
public class HideCollapseLinksExceptDirectionQuery implements IJavaQuery2<Collaboration, Boolean> {

	/**
	 * Constructor.
	 */
	public HideCollapseLinksExceptDirectionQuery() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean evaluate(final Collaboration context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {
		// display only in / out and inout features. They should not be collapsed also
		ParameterValue parameterValue = parameterValues.getParameterValueByName("eStructuralFeature"); //$NON-NLS-1$
		EStructuralFeature eStructuralFeature = (EStructuralFeature) parameterValue.getValue();
		// the eStructure is a containmentReference or Facet Reference?

		boolean result = false;
		if (eStructuralFeature instanceof FacetReference) {
			String name = ((FacetReference) eStructuralFeature).getName();
			// check facet name
			if (FacetUtils.isMessageOrComment(name) || FacetUtils.isGeneralization(name)) {
				result = true;
			}
		}
		return result;
	}
}
