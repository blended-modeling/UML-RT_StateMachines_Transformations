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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTMultiplicityElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTValueSpecification;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableSingleContainment;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link MultiplicityElement}s
 */
public class MultiplicityElementRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected MultiplicityElementRTOperations() {
		super();
	}

	static LowerValue getInheritableLowerValue(InternalUMLRTMultiplicityElement owner) {
		return (LowerValue) EcoreUtil.getExistingAdapter(owner, LowerValue.class);
	}

	static LowerValue demandInheritableLowerValue(InternalUMLRTMultiplicityElement owner) {
		LowerValue result = getInheritableLowerValue(owner);

		if (result == null) {
			result = new LowerValue(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static ValueSpecification getLowerValue(InternalUMLRTMultiplicityElement owner) {
		return demandInheritableLowerValue(owner).getInheritable();
	}

	public static void setLowerValue(InternalUMLRTMultiplicityElement owner, ValueSpecification newLowerValue) {
		demandInheritableLowerValue(owner).set(newLowerValue);
	}

	public static boolean isSetLowerValue(InternalUMLRTMultiplicityElement owner) {
		LowerValue lowerValue = getInheritableLowerValue(owner);
		return (lowerValue != null) && lowerValue.isSet();
	}

	public static void unsetLowerValue(InternalUMLRTMultiplicityElement owner) {
		LowerValue lowerValue = getInheritableLowerValue(owner);
		if (lowerValue != null) {
			lowerValue.unset();
		}
	}

	static UpperValue getInheritableUpperValue(InternalUMLRTMultiplicityElement owner) {
		return (UpperValue) EcoreUtil.getExistingAdapter(owner, UpperValue.class);
	}

	static UpperValue demandInheritableUpperValue(InternalUMLRTMultiplicityElement owner) {
		UpperValue result = getInheritableUpperValue(owner);

		if (result == null) {
			result = new UpperValue(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static ValueSpecification getUpperValue(InternalUMLRTMultiplicityElement owner) {
		return demandInheritableUpperValue(owner).getInheritable();
	}

	public static void setUpperValue(InternalUMLRTMultiplicityElement owner, ValueSpecification newUpperValue) {
		demandInheritableUpperValue(owner).set(newUpperValue);
	}

	public static boolean isSetUpperValue(InternalUMLRTMultiplicityElement owner) {
		UpperValue upperValue = getInheritableUpperValue(owner);
		return (upperValue != null) && upperValue.isSet();
	}

	public static void unsetUpperValue(InternalUMLRTMultiplicityElement owner) {
		UpperValue upperValue = getInheritableUpperValue(owner);
		if (upperValue != null) {
			upperValue.unset();
		}
	}

	//
	// Nested types
	//

	private static final class LowerValue extends InheritableSingleContainment<ValueSpecification> {
		LowerValue(InternalUMLRTMultiplicityElement owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.MULTIPLICITY_ELEMENT__LOWER_VALUE, MultiplicityElement.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTMultiplicityElement) getTarget()).umlGetLowerValue(resolve);
		}

		@Override
		protected NotificationChain basicSet(ValueSpecification newValue, NotificationChain msgs) {
			return ((InternalUMLRTMultiplicityElement) getTarget()).umlBasicSetLowerValue(newValue, msgs);
		}

		@Override
		protected ValueSpecification createRedefinition(ValueSpecification inherited) {
			InternalUMLRTValueSpecification result = (InternalUMLRTValueSpecification) super.createRedefinition(inherited);
			result.handleRedefinedMultiplicityElement(
					((InternalUMLRTMultiplicityElement) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

	private static final class UpperValue extends InheritableSingleContainment<ValueSpecification> {
		UpperValue(InternalUMLRTMultiplicityElement owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.MULTIPLICITY_ELEMENT__UPPER_VALUE, MultiplicityElement.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTMultiplicityElement) getTarget()).umlGetUpperValue(resolve);
		}

		@Override
		protected NotificationChain basicSet(ValueSpecification newValue, NotificationChain msgs) {
			return ((InternalUMLRTMultiplicityElement) getTarget()).umlBasicSetUpperValue(newValue, msgs);
		}

		@Override
		protected ValueSpecification createRedefinition(ValueSpecification inherited) {
			InternalUMLRTValueSpecification result = (InternalUMLRTValueSpecification) super.createRedefinition(inherited);
			result.handleRedefinedMultiplicityElement(
					((InternalUMLRTMultiplicityElement) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

}
