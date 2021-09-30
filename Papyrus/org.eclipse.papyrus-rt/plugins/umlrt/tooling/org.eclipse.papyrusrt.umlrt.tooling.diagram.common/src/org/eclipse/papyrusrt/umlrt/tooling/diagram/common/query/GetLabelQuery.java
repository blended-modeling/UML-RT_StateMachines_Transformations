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
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.query;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.uml.tools.providers.DelegatingItemLabelProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.provider.UMLRTDiagramFilteredLabelProvider;

public class GetLabelQuery implements IJavaQuery2<Diagram, String> {
	
	private static final UMLRTDiagramFilteredLabelProvider DIAGRAM_LABEL_PROVIDER = new UMLRTDiagramFilteredLabelProvider();
	private static final IItemLabelProvider labelProvider = new DelegatingItemLabelProvider();
	

	/**
	 * {@inheritDoc}
	 */
	public String evaluate(final Diagram context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {
		if (DIAGRAM_LABEL_PROVIDER.accept(context)) {
			return DIAGRAM_LABEL_PROVIDER.getText(context);
		}
		return labelProvider.getText(context);
	}

}
