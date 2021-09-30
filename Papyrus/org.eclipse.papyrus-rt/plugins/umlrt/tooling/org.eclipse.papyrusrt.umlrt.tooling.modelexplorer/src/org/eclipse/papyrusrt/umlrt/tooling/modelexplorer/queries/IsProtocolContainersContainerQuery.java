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
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.uml2.uml.Package;

/**
 * Query that returns <code>true</code> if the specified Package context contains a protocolContainer Package.
 */
public class IsProtocolContainersContainerQuery implements IJavaQuery2<Package, Boolean> {

	/**
	 * Constructor.
	 */
	public IsProtocolContainersContainerQuery() {
		// empty constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean evaluate(final Package context, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		return ProtocolContainerUtils.containsProtocolContainer(context);
	}
}




