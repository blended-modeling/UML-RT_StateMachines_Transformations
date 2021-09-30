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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties.IFilteredListProperty;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties.PapyrusRTListProperty;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.provider.UMLRTEditPlugin;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A property for a {@link UMLRTNamedElement}'s inheritable list reference, supporting
 * filtering.
 */
class FacadeListProperty<S extends UMLRTNamedElement, T extends UMLRTNamedElement> extends PapyrusRTListProperty<S, T> implements IFilteredListProperty<S, T>, IFacadeProperty {

	FacadeListProperty(Class<T> elementType, EStructuralFeature facadeFeature, EStructuralFeature umlFeature) {
		super(elementType, facadeFeature, umlFeature);
	}

	@Override
	public String getPropertyName() {
		return UMLRTEditPlugin.INSTANCE.getString(String.format("_UI_%s_%s_feature",
				sourceFeature.getEContainingClass().getName(), sourceFeature.getName()));
	}

	@Override
	protected Stream<T> stream(S source) {
		Stream<T> result = eList(source).stream();

		// Also excluded elements of the reference type
		Stream<T> excluded = source.getExcludedElements().stream()
				.filter(elementType::isInstance).map(elementType::cast);

		return Stream.concat(result, excluded);
	}

	@Override
	protected Element unwrapSource(S source) {
		return source.toUML();
	}

	@Override
	protected Element unwrap(T element) {
		return element.toUML();
	}

	@Override
	protected Object wrap(Element element) {
		return UMLRTFactory.create((NamedElement) element);
	}
}
