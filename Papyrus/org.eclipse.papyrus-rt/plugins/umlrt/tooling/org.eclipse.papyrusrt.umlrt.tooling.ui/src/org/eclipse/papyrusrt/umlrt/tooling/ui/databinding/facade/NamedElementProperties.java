/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTSupersetOf;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Data-binding properties for {@link UMLRTNamedElement}.
 */
public class NamedElementProperties {

	/**
	 * Not instantiable by clients.
	 */
	public NamedElementProperties() {
		super();
	}

	/**
	 * Obtains a property for binding the name of an element.
	 * 
	 * @return the name property
	 */
	public static IValueProperty<UMLRTNamedElement, String> name() {
		return new FacadeValueProperty<>(String.class,
				getUMLRTSupersetOf(UMLPackage.Literals.NAMED_ELEMENT__NAME),
				UMLRTNamedElement::getName);
	}

}
