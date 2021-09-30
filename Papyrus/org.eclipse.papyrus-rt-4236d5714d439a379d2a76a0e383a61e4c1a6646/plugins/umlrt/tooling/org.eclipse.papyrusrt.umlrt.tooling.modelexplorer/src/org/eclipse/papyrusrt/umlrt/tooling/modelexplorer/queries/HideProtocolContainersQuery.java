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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.uml.modelexplorer.queries.DisplayUMLStructuralFeature;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

public class HideProtocolContainersQuery extends DisplayUMLStructuralFeature implements IJavaQuery2<EObject, Boolean> {

	/**
	 * {@inheritDoc}
	 */
	public Boolean evaluate(final EObject context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		ParameterValue parameterValue = parameterValues.getParameterValueByName("eStructuralFeature");
		EStructuralFeature eStructuralFeature = (EStructuralFeature) parameterValue.getValue();
		// This is an UML element?
		if (parameterValue != null && context instanceof Element) {
			// the eStructure is a containmentReference or Facet Reference?
			if (eStructuralFeature instanceof EReference) {
				if (UMLPackage.eINSTANCE.getPackage_PackagedElement().equals(eStructuralFeature)) {
					return false;
				}
			}
		}
		return super.evaluate(context, parameterValues, facetManager);
	}
}
