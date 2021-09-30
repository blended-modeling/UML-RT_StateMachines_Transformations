/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Utilities for working with UML RealTime Capsules.
 */
public class CapsuleUtils {

	/**
	 * Not instantiable by clients.
	 */
	private CapsuleUtils() {
		super();
	}

	/** View name for the Capsule composite diagram */
	public static final String UML_RT_CAPSULE_DIAGRAM = "UMLRTCapsuleStructure";

	public static boolean isCapsule(Classifier classifier) {
		return ElementTypeUtils.matches(classifier, IUMLRTElementTypes.CAPSULE_ID);
	}

	public static org.eclipse.uml2.uml.Class getSuperCapsule(BehavioredClassifier capsule) {
		org.eclipse.uml2.uml.Class result = null;

		if (isCapsule(capsule)) {
			for (Classifier next : capsule.getGenerals()) {
				if (isCapsule(next)) {
					result = (org.eclipse.uml2.uml.Class) next;
					break;
				}
			}
		}
		return result;
	}

	public static org.eclipse.uml2.uml.Class getContextCapsule(Behavior behavior) {
		org.eclipse.uml2.uml.Class result = null;

		BehavioredClassifier context = behavior.getContext();
		if ((context instanceof org.eclipse.uml2.uml.Class) && isCapsule(context)) {
			result = (org.eclipse.uml2.uml.Class) context;
		}

		return result;
	}

	public static <B extends Behavior> B getSuperBehavior(B capsuleBehavior) {
		B result = null;

		org.eclipse.uml2.uml.Class capsule = getContextCapsule(capsuleBehavior);
		if (capsule != null) {
			for (Behavior next : capsuleBehavior.getRedefinedBehaviors()) {
				if (next.eClass() == capsuleBehavior.eClass()) {
					org.eclipse.uml2.uml.Class superCapsule = getContextCapsule(next);
					if ((superCapsule != null) && capsule.allParents().contains(superCapsule)) {
						@SuppressWarnings("unchecked")
						B nextAsB = (B) next; // Safe cast because the EClass is the same
						result = nextAsB;
						break;
					}
				}
			}
		}

		return result;
	}

	public static <V extends Vertex> V getSuperVertex(V capsuleStateMachineVertex) {
		Vertex result = null;

		if (capsuleStateMachineVertex instanceof State) {
			result = TypeUtils.as(((State) capsuleStateMachineVertex).getRedefinedState(), capsuleStateMachineVertex.getClass());
		} else if (capsuleStateMachineVertex instanceof Vertex) {
			Vertex vertex = capsuleStateMachineVertex;
			Region region = vertex.getContainer();

			if (region != null) {
				Region superRegion = region.getExtendedRegion();
				if (superRegion != null) {
					// Match by name and metaclass
					result = superRegion.getSubvertex(vertex.getName(), false, vertex.eClass(), false);
				}
			}
		}

		@SuppressWarnings("unchecked") // Checked already by reflection
		V _result = (V) result;
		return _result;
	}

	public static Transition getSuperTransition(Transition capsuleStateMachineTransition) {
		return capsuleStateMachineTransition.getRedefinedTransition();
	}

	public static Region getSuperRegion(Region capsuleStateMachineRegion) {
		return capsuleStateMachineRegion.getExtendedRegion();
	}

	/**
	 * Queries whether a model {@code element} <em>redefines</em>, in whatever way is appropriate in the UML-RT language,
	 * an element {@code inherited} by its redefinition context.
	 * 
	 * @param element
	 *            a potentially redefining element
	 * @param inherited
	 *            an element inherted by the other {@code element}'s redefinition context
	 * 
	 * @return whether {@code element} logically <em>redefines</em> the {@code inherited} element
	 */
	public static <E extends EObject> boolean redefines(E element, E inherited) {
		return new UMLRTRedefinitionSwitch(inherited).doSwitch(element);
	}

	//
	// Nested types
	//

	private static class UMLRTRedefinitionSwitch extends UMLSwitch<Boolean> {
		private final EObject inherited;

		UMLRTRedefinitionSwitch(EObject inherited) {
			super();

			this.inherited = inherited;
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return false;
		}

		@Override
		public Boolean caseStateMachine(StateMachine object) {
			return getSuperBehavior(object) == inherited;
		}

		@Override
		public Boolean caseRegion(Region object) {
			return getSuperRegion(object) == inherited;
		}

		@Override
		public Boolean caseTransition(Transition object) {
			return getSuperTransition(object) == inherited;
		}

		@Override
		public Boolean caseVertex(Vertex object) {
			return getSuperVertex(object) == inherited;
		}
	}
}
