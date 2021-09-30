/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A query that obtains the single child element of an UML-RT element via the Façade API.
 */
public class GetUMLRTSingleElementQuery implements IJavaQuery2<NamedElement, NamedElement> {
	public GetUMLRTSingleElementQuery() {
		super();
	}

	@Override
	public NamedElement evaluate(final NamedElement context,
			final IParameterValueList2 parameterValues,
			final IFacetManager facetManager)
			throws DerivedTypedElementException {

		NamedElement result = null;

		ParameterValue param = parameterValues.get(0);
		boolean inherited = (Boolean) param.getValue();
		param = parameterValues.get(1);
		boolean includeVirtual = (Boolean) param.getValue();
		param = parameterValues.get(2);
		EReference reference = (EReference) param.getValue();

		// We never show children of excluded elements (bug 513178)
		UMLRTNamedElement namespace = UMLRTFactory.create(context);
		if ((namespace != null) && !namespace.isExcluded()) {
			Optional<UMLRTNamedElement> element = getChild(namespace, reference);

			Predicate<UMLRTNamedElement> filter = UMLRTNamedElement::isInherited;

			if (inherited) {
				// We want only the inherited one
				filter = filter.negate();

				if (!includeVirtual) {
					// But not if it's purely inherited (virtual)
					filter = filter.or(UMLRTNamedElement::isVirtualRedefinition);
				}
			}

			result = element.filter(filter.negate())
					.map(UMLRTNamedElement::toUML)
					.map(NamedElement.class::cast)
					.orElse(null);
		}

		return result;
	}

	protected Optional<UMLRTNamedElement> getChild(UMLRTNamedElement element, EReference reference) {
		Optional<UMLRTNamedElement> result;
		Object value = ((InternalFacadeObject) element).facadeGet(reference, true);
		if (value instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<? extends UMLRTNamedElement> list = (List<? extends UMLRTNamedElement>) value;
			result = list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
		} else if (value instanceof UMLRTNamedElement) {
			result = Optional.of((UMLRTNamedElement) value);
		} else {
			result = Optional.empty();
		}

		return result;
	}
}
