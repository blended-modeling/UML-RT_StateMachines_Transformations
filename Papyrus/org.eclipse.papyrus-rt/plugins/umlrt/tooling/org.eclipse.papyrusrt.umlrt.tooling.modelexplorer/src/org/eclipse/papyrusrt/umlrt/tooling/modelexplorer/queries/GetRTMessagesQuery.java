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

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;

public class GetRTMessagesQuery implements IJavaQuery2<EObject, List<Operation>> {
	@Override
	public List<Operation> evaluate(final EObject context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		List<Operation> result = Collections.emptyList();

		ParameterValue param = parameterValues.get(0);
		String directionString = (String) param.getValue();
		RTMessageKind kind = RTMessageKind.get(directionString);
		param = parameterValues.get(1);
		boolean inherited = (Boolean) param.getValue();
		param = parameterValues.get(2);
		boolean includeVirtual = (Boolean) param.getValue();

		if (context instanceof Collaboration) {
			result = ProtocolUtils.getRTMessages((Collaboration) context, kind, inherited);
			Predicate<Element> filter = UMLRTExtensionUtil::isInherited;

			if (inherited) {
				// We want only the inherited ones
				filter = filter.negate();

				if (!includeVirtual) {
					// But not purely inherited (virtual) elements
					filter = filter.or(UMLRTExtensionUtil::isVirtualElement);
				}
			}

			result.removeIf(filter);
		}

		return result;
	}
}
