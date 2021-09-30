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

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.uml.modelexplorer.queries.GetComplexName;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider.UMLRTFilteredLabelProvider;
import org.eclipse.uml2.uml.NamedElement;

public class GetLabelQuery extends GetComplexName {
	
	private static final UMLRTFilteredLabelProvider UML_LABEL_PROVIDER = new UMLRTFilteredLabelProvider();
	
	/**
	 * {@inheritDoc}
	 */
	public String evaluate(final NamedElement context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {
		if (UML_LABEL_PROVIDER.accept(context)) {
			return UML_LABEL_PROVIDER.getText(context);
		}
		return super.evaluate(context, parameterValues, facetManager);
		
	}
}
