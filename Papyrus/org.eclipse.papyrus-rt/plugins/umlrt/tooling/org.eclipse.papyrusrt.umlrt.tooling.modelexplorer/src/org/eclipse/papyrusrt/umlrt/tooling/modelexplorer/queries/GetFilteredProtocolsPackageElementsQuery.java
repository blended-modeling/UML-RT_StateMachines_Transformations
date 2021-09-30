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

import java.util.List;

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.PackageableElement;

/**
 * query operation to return the list of packaged elements, but replace protocol containers with protocols
 */
public class GetFilteredProtocolsPackageElementsQuery implements IJavaQuery2<Element, List<PackageableElement>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PackageableElement> evaluate(Element source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		return ProtocolContainerUtils.getFilteredPackagedElements(source);
	}

}
