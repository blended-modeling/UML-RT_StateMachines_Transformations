/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Add #getStereotypeApplication()
 *  Christian W. Damus - bugs 467545, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class for capsule parts
 */
public class CapsulePartUtils {

	/**
	 * Returns <code>true</code> if the specified element is a CapsulePart (property typed by a capsule, and stereotyped by <<CapsulePart>>)
	 * 
	 * @param property
	 *            the property to check
	 * @return <code>true</code> if the specified element is a CapsulePart (property typed by a capsule, and stereotyped by <<CapsulePart>>)
	 */
	public static boolean isCapsulePart(final Property property) {
		return ElementTypeUtils.matches(property, IUMLRTElementTypes.CAPSULE_PART_ID);
	}

	/**
	 * Get the Stereotype Application of the Capsule Part
	 * 
	 * @param property
	 *            The Capsule Part as a Property
	 * @return the Capsule Part stereotype application from the UML RT profile application.
	 */
	public static CapsulePart getStereotypeApplication(final Property property) {
		return UMLUtil.getStereotypeApplication(property, CapsulePart.class);

	}

	/**
	 * Query if capsule-part kind is editable.
	 * 
	 * @param capsulePart
	 *            a capsule part
	 * @param newKind
	 *            kind a kind to which to change the {@code capsulePart}
	 * @return whether it is valid to change the {@code capsulePart} to be the new kind
	 */
	public static boolean isKindEditable(Property capsulePart, UMLRTCapsulePartKind newKind) {
		boolean result = true;

		UMLRTCapsulePart partFacade = (capsulePart == null) ? null : UMLRTCapsulePart.getInstance(capsulePart);
		if (partFacade != null) {
			UMLRTCapsulePartKind currentKind = partFacade.getKind();

			if (newKind == UMLRTCapsulePartKind.NULL) {
				result = false;
			}
			// Can always transition to the current kind
			else if (newKind != currentKind) {
				// A redefinition cannot change plug-in-ness
				if (partFacade.isInherited()) {
					// isNotification is irrelevant to the port kind
					result = (currentKind != UMLRTCapsulePartKind.PLUG_IN)
							&& (newKind != UMLRTCapsulePartKind.PLUG_IN);
				}
				// if a part (inherited or not) generate a cycle, we should not be able to change its kind to fixed
				if (newKind == UMLRTCapsulePartKind.FIXED && completesCycle(capsulePart.getType(), capsulePart)) {
					result = false;
				}
			}
		}


		return result;
	}

	/**
	 * Query if the capsule part to be created will complete a cycle or not
	 * 
	 * @param newType
	 *            the type of the newly created capsule part
	 * @param elementToEdit
	 *            the capsule part to be created
	 * @return <code>true</code> if the capsule part completes a cycle
	 */
	public static boolean completesCycle(Type newType, Property elementToEdit) {
		boolean result = false;
		Set<Type> allTypes = new HashSet<>();
		EObject container = elementToEdit.eContainer();
		allTypes.add(newType);

		// to avoid a cycle the new part should not be typed by its container,
		if (container == newType) {
			result = true;
		}
		// to avoid a cycle the new part Type should not contain (directly or indirectly or inherited) part typed by its container
		else {
			result = getAllInvolvedCapsuleTypes(newType, allTypes).contains(container);
		}

		return result;
	}

	private static Set<Type> getAllInvolvedCapsuleTypes(Type newType, Set<Type> allTypes) {

		if (newType != null && newType instanceof Classifier) {
			for (Property next : ((Classifier) newType).getAllAttributes()) {
				// only fixed part could create a cycle
				if (isCapsulePart(next) && UMLRTCapsulePart.getInstance(next).getKind().equals(UMLRTCapsulePartKind.FIXED)) {
					Type thisType = next.getType();
					if (thisType != null && allTypes.add(thisType)) {
						getAllInvolvedCapsuleTypes(thisType, allTypes);
					}
				}
			}
		}
		return allTypes;

	}

}
