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
import org.eclipse.papyrus.emf.facet.custom.metamodel.custompt.IImage;
import org.eclipse.papyrus.emf.facet.custom.ui.ImageUtils;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetReference;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider.UMLRTFilteredLabelProvider;
import org.eclipse.uml2.uml.Package;

/**
 * Returns the icon of a protocol for a Protocol Container.
 */
public class GetProtocolImageQuery implements IJavaQuery2<Package, IImage> {

	UMLRTFilteredLabelProvider labelProvider = new UMLRTFilteredLabelProvider();

	/**
	 * {@inheritDoc}
	 */
	public IImage evaluate(final Package context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
					throws DerivedTypedElementException {
		ParameterValue parameterValue = parameterValues.getParameterValueByName("eObject");

		if (parameterValue != null) {
			EStructuralFeature eStructuralFeature = (EStructuralFeature) parameterValue.getValue();
			// the eStructure is a containmentReference or Facet Reference?
			if (eStructuralFeature instanceof FacetReference) {
				// check this is in / out or inout
				String name = ((FacetReference) eStructuralFeature).getName();
				if ("in".equals(name)) {
					return ImageUtils.wrap(labelProvider.getImage(ProtocolContainerUtils.getMessageSetIn(context)));
				}
				if ("out".equals(name)) {
					return ImageUtils.wrap(labelProvider.getImage(ProtocolContainerUtils.getMessageSetOut(context)));
				}
				if ("inout".equals(name)) {
					return ImageUtils.wrap(labelProvider.getImage(ProtocolContainerUtils.getMessageSetInOut(context)));
				}
			}
		}


		// by default, return the protocol container image
		return ImageUtils.wrap(labelProvider.getImage(context));
	}
}
