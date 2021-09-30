/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Feature;

/**
 * A query that obtains the structural features (RT-specific or not) of a capsule.
 */
abstract class GetRTStructuralFeaturesQuery<T extends Feature> implements IJavaQuery2<Class, List<T>> {
	private final java.lang.Class<T> type;

	protected GetRTStructuralFeaturesQuery(java.lang.Class<T> type) {
		super();

		this.type = type;
	}

	@Override
	public List<T> evaluate(final Class context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		List<T> result = Collections.emptyList();

		ParameterValue param = parameterValues.get(0);
		boolean rtSpecific = (Boolean) param.getValue();
		param = parameterValues.get(1);
		boolean inherited = (Boolean) param.getValue();
		param = parameterValues.get(2);
		boolean includeVirtual = (Boolean) param.getValue();

		UMLRTCapsule capsule = UMLRTCapsule.getInstance(context);
		if (capsule != null) {
			List<? extends UMLRTNamedElement> rtFeatures = getFeatures(capsule);

			if (!rtSpecific) {
				// Just the plain ports. They will never be inherited
				List<T> features = new ArrayList<>(getFeatures(context));
				rtFeatures.forEach(rt -> features.remove(rt.toUML()));
				result = features;
			} else {
				// The RT ports
				result = rtFeatures.stream().map(UMLRTNamedElement::toUML)
						.map(type::cast)
						.collect(toList());

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
		}

		return result;
	}

	protected abstract List<? extends UMLRTNamedElement> getFeatures(UMLRTCapsule capsule);

	protected abstract List<T> getFeatures(Class capsule);
}
