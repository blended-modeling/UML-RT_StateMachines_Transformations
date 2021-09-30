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

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.uml.modelexplorer.queries.GetVisibleUMLReferencesQuery;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Query that returns all usual UML references, except also Package:PackageElement.
 */
public class GetVisiblePackageUMLRTReferencesQuery extends GetVisibleUMLReferencesQuery {

	/** excluded reference set. */
	private static final Set<EReference> EXCLUDED_REFERENCES = getExcludedReferences();

	/**
	 * Constructor.
	 */
	public GetVisiblePackageUMLRTReferencesQuery() {
		// empty
	}

	/**
	 * Returns the list of Excluded references from this query.
	 * 
	 * @return the list of Excluded references from this query.
	 */
	private static Set<EReference> getExcludedReferences() {
		final Set<EReference> result = new HashSet<>(1);
		result.add(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EReference> evaluate(final EObject source, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		List<EReference> result = new ArrayList<>(super.evaluate(source, parameterValues, facetManager));
		result.removeAll(EXCLUDED_REFERENCES);
		return result;
	}
}
