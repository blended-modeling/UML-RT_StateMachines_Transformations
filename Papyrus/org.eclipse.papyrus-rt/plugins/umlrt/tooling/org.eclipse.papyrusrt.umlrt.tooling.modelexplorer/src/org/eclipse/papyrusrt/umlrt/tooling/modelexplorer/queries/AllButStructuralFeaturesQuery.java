/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Query that returns all references of an object apart from its structural features
 * (attributes including ports, connectors).
 */
public class AllButStructuralFeaturesQuery implements IJavaQuery2<EObject, List<EReference>> {

	/**
	 * Constructor.
	 */
	public AllButStructuralFeaturesQuery() {
		super();
	}

	@Override
	public List<EReference> evaluate(final EObject source, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		List<EReference> result = new ArrayList<>(source.eClass().getEAllContainments());
		result.remove(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
		result.remove(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
		result.remove(UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT);
		return result;
	}
}
