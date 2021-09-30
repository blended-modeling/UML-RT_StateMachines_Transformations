/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetReference;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.uml2.uml.Package;

public class DisplayMessageSetsOnlyQuery implements IJavaQuery2<Package, Boolean> {
	public Boolean evaluate(final Package context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
					throws DerivedTypedElementException {

		// display only in / out and inout features. They should not be collapsed also
		ParameterValue parameterValue = parameterValues.getParameterValueByName("eStructuralFeature");
		EStructuralFeature eStructuralFeature = (EStructuralFeature) parameterValue.getValue();
		// the eStructure is a containmentReference or Facet Reference?
		if (eStructuralFeature instanceof FacetReference) {
			// check this is in / out or inout
			String name = ((FacetReference)eStructuralFeature).getName();
			if("in".equals(name) || "out".equals(name) || "inout".equals(name) ) {
				return true;
			}
		}
		return false;
	}
}
