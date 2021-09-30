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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Comment;

public class GetOwnedComments implements IJavaQuery2<EObject, List<Comment>> {
	public List<Comment> evaluate(final EObject context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
					throws DerivedTypedElementException {
		if (context instanceof Collaboration) {
			return ((Collaboration) context).getOwnedComments();
		}
		return null;
	}
}
