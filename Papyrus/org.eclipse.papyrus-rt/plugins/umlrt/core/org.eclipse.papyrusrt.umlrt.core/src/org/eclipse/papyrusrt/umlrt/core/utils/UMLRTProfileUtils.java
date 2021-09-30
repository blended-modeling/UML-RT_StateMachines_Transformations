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
 *  Christian W. Damus - bugs 493869, 513066, 513166, 513793, 512955
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTSupersetOf;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrusrt.umlrt.profile.util.UMLRTResource;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Util methods for UML-RT profile
 */
public class UMLRTProfileUtils extends UMLUtil {

	public static boolean isUMLRTProfileApplied(Element element) {
		Package package_ = element.getNearestPackage();
		if (package_ == null) {
			return false;
		}

		for (Profile profile : package_.getAllAppliedProfiles()) {
			if (UMLRTResource.PROFILE_URI.equals(profile.getURI())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Looks up an applicable stereotype by its Ecore definition, which usually
	 * is obtains from the profile's statically generated package metadata.
	 * 
	 * @param context
	 *            an element to which the stereotype would be applied
	 * @param ecoreDefinition
	 *            the stereotype's Ecore definition
	 * 
	 * @return the stereotype's UML definition, or {@code null} if it is not applicable
	 *         to the {@code context} element
	 */
	public static Stereotype getApplicableStereotype(Element context, EClass ecoreDefinition) {
		Stereotype result = (Stereotype) getNamedElement(ecoreDefinition, context);

		if ((result != null) && !context.isStereotypeApplicable(result)) {
			result = null;
		}

		return result;
	}

	/**
	 * Applies the given {@code stereotype} to an {@code element}, if it is
	 * applicable and is not already applied.
	 * 
	 * @param element
	 *            a model element
	 * @return the element with the stereotype applied
	 */
	public static <E extends Element> E ensureStereotype(E element, EClass stereotype) {
		Stereotype rt = getApplicableStereotype(element, stereotype);
		if ((rt != null) && !element.isStereotypeApplied(rt)) {
			element.applyStereotype(rt);
		}

		return element;
	}

	/**
	 * Queries whether it is permissible to exclude an {@code element} in the model.
	 * Reasons not may include the element being neither inherited nor redefined, the
	 * element being of a kind that cannot be excluded from inheritance, or anything
	 * else.
	 * 
	 * @param element
	 *            an element in the model
	 * @return whether it may be excluded
	 */
	public static boolean canExclude(Element element) {
		boolean result;

		UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of(element);
		switch (inheritance) {
		case INHERITED:
		case REDEFINED:
			// Exclusion of pseudostates, guard constraints, and
			// classifier behaviours is not supported
			result = !(element instanceof Pseudostate)
					&& !(element instanceof Constraint)
					&& !getUMLRTSupersetOf(UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR).contains(
							element.eContainmentFeature());
			break;
		default:
			// Cannot exclude local definitions or elements already excluded
			result = false;
			break;
		}

		return result;
	}

	/**
	 * Queries whether it is permissible to re-inherit an {@code element} in the model.
	 * Reasons not may include the element being neither inherited nor redefined, the
	 * element being of a kind that must be a redefinition, or anything else.
	 * 
	 * @param element
	 *            an element in the model
	 * @return whether it may be re-inherited
	 */
	public static boolean canReinherit(Element element) {
		boolean result;

		// Triggers can have an implied redefined state
		UMLRTInheritanceKind inheritance = (element instanceof Trigger)
				? Optional.ofNullable(UMLRTTrigger.getInstance((Trigger) element))
						.map(UMLRTTrigger::getInheritanceKind)
						.orElse(UMLRTInheritanceKind.NONE)
				: UMLRTInheritanceKind.of(element);
		switch (inheritance) {
		case REDEFINED:
		case EXCLUDED:
			// Reinheritance of classifier behaviours is not supported because
			// they are currently required to be redefinitions
			result = (element.eContainmentFeature() != UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR);
			break;
		default:
			// Cannot exclude local definitions or elements already excluded
			result = false;
			break;
		}

		return result;
	}

	/**
	 * Queries whether an {@code element} supports redefinition according to
	 * UML-RT semantics. This is obviously true only for elements that are
	 * {@linkplain UMLRTExtensionUtil#isInherited(Element) inherited} (and thus
	 * not locally defined or already redefined locally) but may furthermore
	 * be constrained according to arbitrary rules. For example, inherited
	 * {@code Trigger}s are not intrinsically redefinable; only their guard
	 * constraints are (in some sense).
	 * 
	 * @param element
	 *            an element
	 * @return whether it may be re-defined in the inheriting context
	 */
	public static boolean canRedefine(Element element) {
		boolean result;

		// Do not consider the implied redefinition state of triggers
		// that have re-defined guards; we are interested here only
		// in the trigger, itself
		UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of(element);
		switch (inheritance) {
		case INHERITED:
			result = !(element instanceof Pseudostate)
					&& !(element instanceof Trigger);
			break;
		default:
			// Cannot redefine local definitions or elements already redefined
			result = false;
			break;
		}

		return result;
	}
}
