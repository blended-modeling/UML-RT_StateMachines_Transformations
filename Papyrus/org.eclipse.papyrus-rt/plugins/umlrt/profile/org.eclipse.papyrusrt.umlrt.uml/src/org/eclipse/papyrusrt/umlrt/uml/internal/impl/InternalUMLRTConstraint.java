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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Internal protocol for constraints.
 */
public interface InternalUMLRTConstraint extends InternalUMLRTElement, Constraint {
	/**
	 * Obtains the specification, with or without proxy resolution, according to UML semantics.
	 * 
	 * @param resolve
	 *            whether to resolve proxies
	 * 
	 * @return the UML-ish specification (not inherited)
	 */
	ValueSpecification umlGetSpecification(boolean resolve);

	/**
	 * Basic-sets the specification, according to UML semantics.
	 * 
	 * @param newSpecification
	 *            the new specification
	 * @param msgs
	 *            an incoming notification chain to append, or {@code null} if none, yet
	 * 
	 * @return the outgoing notifications
	 */
	NotificationChain umlBasicSetSpecification(ValueSpecification newSpecification, NotificationChain msgs);

	/**
	 * Notifies me that my owning transition's redefinition
	 * status has changed (I am its {@linkplain Transition#getGuard() guard}).
	 * 
	 * @param mult
	 *            my owner's new redefined element, or {@code null}
	 *            if it is now not a redefinition
	 */
	void handleRedefinedTransition(Transition transition);

	/**
	 * If the constraint that I redefine has the «RTGuard» stereotype applied, ensures
	 * that I do, and likewise if it doesn't, ensures that I don't.
	 */
	default void synchronizeGuardStereotype() {
		InternalUMLRTConstraint redefined = rtGetRedefinedElement();
		if (redefined != null) {
			RTGuard redefinedGuard = UMLUtil.getStereotypeApplication(redefined, RTGuard.class);
			RTGuard myGuard = UMLUtil.getStereotypeApplication(this, RTGuard.class);

			if ((redefinedGuard != null) != (myGuard != null)) {
				if (redefinedGuard == null) {
					unapplyStereotype(UMLUtil.getStereotype(myGuard));
				} else {
					applyStereotype(UMLUtil.getStereotype(redefinedGuard));
				}
			}
		}
	}
}
