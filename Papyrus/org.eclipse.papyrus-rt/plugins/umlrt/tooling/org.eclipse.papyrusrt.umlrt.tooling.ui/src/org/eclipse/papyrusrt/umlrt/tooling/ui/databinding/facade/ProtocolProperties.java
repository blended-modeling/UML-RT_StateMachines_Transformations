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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties.IFilteredListProperty;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Data-binding properties for {@link UMLRTProtocol}.
 */
public class ProtocolProperties {

	/**
	 * Not instantiable by clients.
	 */
	public ProtocolProperties() {
		super();
	}

	/**
	 * Obtains a property for binding the messages of a protocol in a particular direction.
	 * 
	 * @param directionKind
	 *            the direction kind
	 * 
	 * @return the messages property
	 */
	public static IFilteredListProperty<UMLRTProtocol, UMLRTProtocolMessage> messages(RTMessageKind directionKind) {
		EStructuralFeature feature;
		switch (directionKind) {
		case IN:
			feature = UMLRTUMLRTPackage.Literals.PROTOCOL__IN_MESSAGE;
			break;
		case OUT:
			feature = UMLRTUMLRTPackage.Literals.PROTOCOL__OUT_MESSAGE;
			break;
		case IN_OUT:
			feature = UMLRTUMLRTPackage.Literals.PROTOCOL__IN_OUT_MESSAGE;
			break;
		default:
			throw new IllegalArgumentException("directionKind: " + directionKind); //$NON-NLS-1$
		}

		return new MessagesProperty(directionKind, feature);
	}

	/**
	 * Obtains a property for binding the supertype of a protocol.
	 * 
	 * @return the supertype property
	 */
	public static IValueProperty<UMLRTProtocol, UMLRTProtocol> supertype() {
		return new UMLRTClassifierGeneralProperty<>(UMLRTProtocol.class, UMLRTProtocol::getSuperProtocol);
	}

	//
	// Nested types
	//

	private static final class MessagesProperty extends FacadeListProperty<UMLRTProtocol, UMLRTProtocolMessage> {
		private final RTMessageKind directionKind;

		MessagesProperty(RTMessageKind directionKind, EStructuralFeature feature) {
			super(UMLRTProtocolMessage.class, feature, UMLPackage.Literals.INTERFACE__OWNED_OPERATION);

			this.directionKind = directionKind;
		}

		@Override
		protected EObject umlOwner(Element uml) {
			// We need to add to a related element of the source's element
			return ProtocolUtils.getMessageSet((Collaboration) uml, directionKind);
		}
	}
}
