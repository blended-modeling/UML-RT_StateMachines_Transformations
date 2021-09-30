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

package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Utilities for manipulation and inspection of properties in UML-RT models,
 * both capsule-parts and ports.
 * 
 * @see CapsulePartUtils
 * @see RTPortUtils
 */
public class RTPropertyUtils {

	/**
	 * Not instantiable by clients.
	 */
	private RTPropertyUtils() {
		super();
	}

	/**
	 * Queries whether a {@code property}'s multiplicity indicates explicit
	 * or implicit replication.
	 * 
	 * @param property
	 *            a property
	 * @return whether the {@code property} is replicated, or assumed to be
	 *         replicated (as when symbolic expressions are involved in
	 *         the multiplicity bounds)
	 */
	public static boolean isReplicated(Property property) {
		return !isSingular(property.getUpperValue());
	}

	/**
	 * Queries whether a {@code property}'s multiplicity indicates explicit
	 * or implicit optionality.
	 * 
	 * @param property
	 *            a property
	 * @return whether the {@code property} is optional, or assumed to be
	 *         optional
	 */
	public static boolean isOptional(Property property) {
		return isZero(property.getLowerValue());
	}

	/**
	 * Queries whether a multiplicity bound is implicitly (as when {@code null}) or
	 * explicitly (as when a literal {@code 1}) singular.
	 * 
	 * @param multiplicityBound
	 *            a multiplicity bound (may be {@code null})
	 * 
	 * @return whether it is singular
	 */
	private static boolean isSingular(ValueSpecification multiplicityBound) {
		boolean result = true;

		// A null is an absent bound, which implicitly is 1
		if (multiplicityBound != null) {
			// We can only actually understand literal 1 values
			result = multiplicityBound.eClass().getEPackage() == UMLPackage.eINSTANCE;
			if (result) {
				switch (multiplicityBound.eClass().getClassifierID()) {
				case UMLPackage.LITERAL_INTEGER:
					result = ((LiteralInteger) multiplicityBound).getValue() == 1;
					break;
				case UMLPackage.LITERAL_UNLIMITED_NATURAL:
					result = ((LiteralUnlimitedNatural) multiplicityBound).getValue() == 1;
					break;
				case UMLPackage.LITERAL_REAL:
					result = ((LiteralReal) multiplicityBound).getValue() == 1.0;
					break;
				case UMLPackage.LITERAL_STRING:
					// The LiteralString implementation of integerValue() would throw
					// if there is any whitespace or decimal point, so we should be
					// equally restrictive, here
					result = "1".equals(((LiteralString) multiplicityBound).getValue()); //$NON-NLS-1$
					break;
				case UMLPackage.OPAQUE_EXPRESSION:
					OpaqueExpression opaqueExpr = (OpaqueExpression) multiplicityBound;
					result = opaqueExpr.isIntegral() && (opaqueExpr.value() == 1);
					break;
				default:
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Queries whether a multiplicity bound is zero.
	 * 
	 * @param multiplicityBound
	 *            a multiplicity bound (may be {@code null})
	 * 
	 * @return whether it is zero (a {@code null} bound is singular, not zero)
	 */
	private static boolean isZero(ValueSpecification multiplicityBound) {
		boolean result = false;

		// A null is an absent bound, which implicitly is 1, so not zero
		if (multiplicityBound != null) {
			// We can only actually understand literal 1 values
			result = multiplicityBound.eClass().getEPackage() == UMLPackage.eINSTANCE;
			if (result) {
				switch (multiplicityBound.eClass().getClassifierID()) {
				case UMLPackage.LITERAL_INTEGER:
					result = ((LiteralInteger) multiplicityBound).getValue() == 0;
					break;
				case UMLPackage.LITERAL_UNLIMITED_NATURAL:
					result = ((LiteralUnlimitedNatural) multiplicityBound).getValue() == 0;
					break;
				case UMLPackage.LITERAL_REAL:
					result = ((LiteralReal) multiplicityBound).getValue() == 0.0;
					break;
				case UMLPackage.LITERAL_STRING:
					// The LiteralString implementation of integerValue() would throw
					// if there is any whitespace or decimal point, so we should be
					// equally restrictive, here
					result = "0".equals(((LiteralString) multiplicityBound).getValue()); //$NON-NLS-1$
					break;
				case UMLPackage.OPAQUE_EXPRESSION:
					OpaqueExpression opaqueExpr = (OpaqueExpression) multiplicityBound;
					result = opaqueExpr.isIntegral() && (opaqueExpr.value() == 0);
					break;
				default:
					result = false;
					break;
				}
			}
		}

		return result;
	}
}
