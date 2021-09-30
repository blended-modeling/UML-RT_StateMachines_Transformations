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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Internal protocol for value specifications serving as multiplicity
 * bounds for {@link MultiplicityElement}s.
 */
public interface InternalUMLRTValueSpecification extends InternalUMLRTElement, ValueSpecification {
	/**
	 * Notifies me that my owning multiplicity-element's redefinition
	 * status has changed (I am its {@linkplain MultiplicityElement#getLowerValue() lower}
	 * or {@linkplain MultiplicityElement#getUpperValue() upper bound}).
	 * 
	 * @param mult
	 *            my owner's new redefined element, or {@code null}
	 *            if it is now not a redefinition
	 */
	void handleRedefinedMultiplicityElement(MultiplicityElement mult);

	/**
	 * Notifies me that my owning constraint's redefinition
	 * status has changed (I am its {@linkplain Constraint#getSpecification() specification}).
	 * 
	 * @param mult
	 *            my owner's new redefined element, or {@code null}
	 *            if it is now not a redefinition
	 */
	void handleRedefinedConstraint(Constraint constraint);
}
