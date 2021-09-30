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

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties.IFilteredListProperty;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Data-binding properties for {@link UMLRTCapsule}.
 */
public class CapsuleProperties {

	/**
	 * Not instantiable by clients.
	 */
	public CapsuleProperties() {
		super();
	}

	/**
	 * Obtains a property for binding the ports of a capsule.
	 * 
	 * @return the ports property
	 */
	public static IFilteredListProperty<UMLRTCapsule, UMLRTPort> ports() {
		return new FacadeListProperty<>(UMLRTPort.class,
				UMLRTUMLRTPackage.Literals.CAPSULE__PORT,
				UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT);
	}

	/**
	 * Obtains a property for binding the capsule-parts of a capsule.
	 * 
	 * @return the capsule-parts property
	 */
	public static IFilteredListProperty<UMLRTCapsule, UMLRTCapsulePart> capsuleParts() {
		return new FacadeListProperty<>(UMLRTCapsulePart.class,
				UMLRTUMLRTPackage.Literals.CAPSULE__CAPSULE_PART,
				UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
	}

	/**
	 * Obtains a property for binding the connectors of a capsule.
	 * 
	 * @return the connectors property
	 */
	public static IFilteredListProperty<UMLRTCapsule, UMLRTConnector> connectors() {
		return new FacadeListProperty<>(UMLRTConnector.class,
				UMLRTUMLRTPackage.Literals.CAPSULE__CONNECTOR,
				UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
	}

	/**
	 * Obtains a property for binding the superclass of a capsule.
	 * 
	 * @return the superclass property
	 */
	public static IValueProperty<UMLRTCapsule, UMLRTCapsule> superclass() {
		return new UMLRTClassifierGeneralProperty<>(UMLRTCapsule.class, UMLRTCapsule::getSuperclass);
	}
}
