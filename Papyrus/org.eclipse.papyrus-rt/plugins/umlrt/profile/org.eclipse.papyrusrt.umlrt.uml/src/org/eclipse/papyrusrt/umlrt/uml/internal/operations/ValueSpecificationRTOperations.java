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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTConstraint;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTMultiplicityElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTValueSpecification;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link ValueSpecification}s
 */
public class ValueSpecificationRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected ValueSpecificationRTOperations() {
		super();
	}

	public static void rtReify(InternalUMLRTValueSpecification valueSpecification) {
		Element owner = valueSpecification.getOwner();
		if ((owner instanceof MultiplicityElement) || (owner instanceof Constraint)) {
			EReference containment = valueSpecification.eContainmentFeature();
			// This will implicitly reify the owner, too, which is good
			owner.eSet(containment, valueSpecification);
			valueSpecification.rtAdjustStereotypes();
		}
	}

	public static void rtVirtualize(InternalUMLRTValueSpecification valueSpecification) {
		Element owner = valueSpecification.getOwner();
		if ((owner instanceof MultiplicityElement) || (owner instanceof Constraint)) {
			EReference containment = valueSpecification.eContainmentFeature();
			owner.eUnset(containment);
			valueSpecification.rtAdjustStereotypes();
		}
	}

	@SuppressWarnings("unchecked")
	public static <R extends InternalUMLRTElement> R rtGetRedefinedElement(InternalUMLRTValueSpecification valueSpecification) {
		R result = null;

		Element owner = valueSpecification.getOwner();
		if (owner instanceof InternalUMLRTMultiplicityElement) {
			InternalUMLRTMultiplicityElement mult = (InternalUMLRTMultiplicityElement) valueSpecification.getOwner();
			InternalUMLRTMultiplicityElement redefined = mult.rtGetRedefinedElement();

			if (redefined != null) {
				EReference containment = valueSpecification.eContainmentFeature();
				if (containment == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE) {
					result = (R) redefined.getLowerValue();
				} else if (containment == UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE) {
					result = (R) redefined.getUpperValue();
				}
			}
		} else if (owner instanceof InternalUMLRTConstraint) {
			InternalUMLRTConstraint constraint = (InternalUMLRTConstraint) valueSpecification.getOwner();
			InternalUMLRTConstraint redefined = constraint.rtGetRedefinedElement();

			if (redefined != null) {
				EReference containment = valueSpecification.eContainmentFeature();
				if (containment == UMLPackage.Literals.CONSTRAINT__SPECIFICATION) {
					result = (R) redefined.getSpecification();
				}
			}
		}

		return result;
	}

	public static void umlSetRedefinedElement(InternalUMLRTValueSpecification valueSpecification, InternalUMLRTElement redefined) {
		if (!(redefined instanceof ValueSpecification)) {
			throw new IllegalArgumentException("not a value specification: " + redefined); //$NON-NLS-1$
		}

		// Redefinition is via the containing multiplicity element or constraint
		Element owner = valueSpecification.getOwner();
		if (owner instanceof InternalUMLRTMultiplicityElement) {
			InternalUMLRTMultiplicityElement mult = (InternalUMLRTMultiplicityElement) valueSpecification.getOwner();
			InternalUMLRTMultiplicityElement redefinedMult = (InternalUMLRTMultiplicityElement) redefined.rtOwner();

			if (redefinedMult != null) {
				mult.umlSetRedefinedElement(redefinedMult);
			}
		} else if (owner instanceof InternalUMLRTConstraint) {
			InternalUMLRTConstraint constraint = (InternalUMLRTConstraint) valueSpecification.getOwner();
			InternalUMLRTConstraint redefinedConstraint = (InternalUMLRTConstraint) redefined.rtOwner();

			if (redefinedConstraint != null) {
				constraint.umlSetRedefinedElement(redefinedConstraint);
			}
		}
	}

}
