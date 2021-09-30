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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;

/**
 * A property for an {@link Element}'s inheritable list reference, supporting
 * filtering.
 */
class UMLRTListProperty<S extends Element, T extends Element> extends PapyrusRTListProperty<S, T> implements IFilteredListProperty<S, T> {

	UMLRTListProperty(Class<T> elementType, EStructuralFeature facadeFeature, EStructuralFeature umlFeature) {
		super(elementType, facadeFeature, umlFeature);
	}

	@Override
	public String getPropertyName() {
		return UMLEditPlugin.INSTANCE.getString(String.format("_UI_%s_%s_feature",
				sourceFeature.getEContainingClass().getName(), sourceFeature.getName()));
	}

	@Override
	protected Element unwrapSource(S source) {
		return source;
	}

	@Override
	protected Element unwrap(T element) {
		return element;
	}

	@Override
	protected Object wrap(Element element) {
		return element;
	}
}
