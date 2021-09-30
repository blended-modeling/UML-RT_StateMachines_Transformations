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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTConstraint;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTValueSpecification;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableSingleContainment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Constraint}s
 */
public class ConstraintRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected ConstraintRTOperations() {
		super();
	}

	static Specification getInheritableSpecification(InternalUMLRTConstraint owner) {
		return (Specification) EcoreUtil.getExistingAdapter(owner, Specification.class);
	}

	static Specification demandInheritableSpecification(InternalUMLRTConstraint owner) {
		Specification result = getInheritableSpecification(owner);

		if (result == null) {
			result = new Specification(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static ValueSpecification getSpecification(InternalUMLRTConstraint owner) {
		return demandInheritableSpecification(owner).getInheritable();
	}

	public static void setSpecification(InternalUMLRTConstraint owner, ValueSpecification newSpecification) {
		demandInheritableSpecification(owner).set(newSpecification);
	}

	public static boolean isSetSpecification(InternalUMLRTConstraint owner) {
		Specification specification = getInheritableSpecification(owner);
		return (specification != null) && specification.isSet();
	}

	public static void unsetSpecification(InternalUMLRTConstraint owner) {
		Specification specification = getInheritableSpecification(owner);
		if (specification != null) {
			specification.unset();
		}
	}

	public static Trigger getGuarded(InternalUMLRTConstraint constraint) {
		Trigger result = null;

		for (Element next : constraint.getConstrainedElements()) {
			if (next instanceof Trigger) {
				result = (Trigger) next;
				break;
			}
		}

		return result;
	}

	public static Element resolveConstrainedElement(InternalUMLRTConstraint constraint, Element element) {
		Element result = element;

		if ((element instanceof Trigger) && constraint.rtIsRedefinition() && (element instanceof InternalUMLRTElement)) {
			Namespace context = constraint.getContext();
			if (context instanceof Transition) {
				Transition transition = (Transition) context;
				InternalUMLRTElement trigger = (InternalUMLRTElement) element;

				result = transition.getTriggers().stream()
						.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast)
						.filter(e -> e.rtRedefines(trigger))
						.findFirst().map(Element.class::cast)
						.orElse(result);
			}
		}

		return result;
	}

	//
	// Nested types
	//

	private static final class Specification extends InheritableSingleContainment<ValueSpecification> {
		Specification(InternalUMLRTConstraint owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.CONSTRAINT__SPECIFICATION, Constraint.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTConstraint) getTarget()).umlGetSpecification(resolve);
		}

		@Override
		protected NotificationChain basicSet(ValueSpecification newSpecification, NotificationChain msgs) {
			return ((InternalUMLRTConstraint) getTarget()).umlBasicSetSpecification(newSpecification, msgs);
		}

		@Override
		protected ValueSpecification createRedefinition(ValueSpecification inherited) {
			InternalUMLRTValueSpecification result = (InternalUMLRTValueSpecification) super.createRedefinition(inherited);
			result.handleRedefinedConstraint(
					((InternalUMLRTConstraint) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

}
