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

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isExcluded;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.internal.operations.VertexOperations;

/**
 * Externalized operations for vertices with UML-RT semantics.
 */
public class VertexRTOperations extends VertexOperations {

	/**
	 * Not instantiable by clients.
	 */
	protected VertexRTOperations() {
		super();
	}

	public static EList<Transition> getIncomings(Vertex vertex) {
		EList<Transition> result = new UniqueEList.FastCompare<>();

		StateMachine machine = containingStateMachine(vertex);
		if (machine != null) {
			for (Iterator<Transition> iter = allTransitions(machine); iter.hasNext();) {
				Transition next = iter.next();
				if (!isExcluded(next) && (next.getTarget() == vertex)) {
					result.add(next);
				}
			}
		}

		return new IncomingRTEList((InternalUMLRTElement) vertex, UMLPackage.Literals.VERTEX__INCOMING,
				result);
	}

	public static StateMachine containingStateMachine(Vertex vertex) {
		StateMachine result = vertex.containingStateMachine();

		if ((result == null) && (vertex instanceof Pseudostate)) {
			// containingStateMachine() is undefined for state connection points
			State state = ((Pseudostate) vertex).getState();
			if (state != null) {
				result = state.containingStateMachine();
			}
		}

		return result;
	}

	public static EList<Transition> getOutgoings(Vertex vertex) {
		EList<Transition> result = new UniqueEList.FastCompare<>();

		StateMachine machine = containingStateMachine(vertex);
		if (machine != null) {
			for (Iterator<Transition> iter = allTransitions(machine); iter.hasNext();) {
				Transition next = iter.next();
				if (!isExcluded(next) && (next.getSource() == vertex)) {
					result.add(next);
				}
			}
		}

		return new OutgoingRTEList((InternalUMLRTElement) vertex, UMLPackage.Literals.VERTEX__OUTGOING,
				result);
	}

	protected static Iterator<Transition> allTransitions(StateMachine stateMachine) {
		@SuppressWarnings("serial")
		TreeIterator<EObject> tree = new AbstractTreeIterator<EObject>(stateMachine, false) {
			@Override
			protected Iterator<? extends EObject> getChildren(Object object) {
				Iterator<? extends EObject> result = (object instanceof Region)
						? contents((Region) object, UMLPackage.Literals.REGION__TRANSITION,
								UMLPackage.Literals.REGION__SUBVERTEX) // Handle composite states
						: (object instanceof State)
								? contents((State) object, UMLPackage.Literals.STATE__REGION)
								: (object instanceof StateMachine)
										? contents((StateMachine) object, UMLPackage.Literals.STATE_MACHINE__REGION)
										: Collections.emptyIterator();
				return result;
			}

			private Iterator<? extends EObject> contents(EObject object, EReference reference, EReference... rest) {
				return UMLRTExtensionUtil.<EObject> getUMLRTContents(object, reference, rest).iterator();
			}
		};

		return new Iterator<Transition>() {
			boolean done;
			Transition prepared;

			@Override
			public boolean hasNext() {
				while (prepared == null) {
					if (!tree.hasNext()) {
						done = true;
						break;
					}
					Object next = tree.next();
					if (next instanceof Transition) {
						prepared = (Transition) next;
					} else if (next instanceof Pseudostate) {
						// Nothing in here
						tree.prune();
					} else if ((next instanceof State) && !((State) next).isComposite()) {
						// Nothing in here
						tree.prune();
					}
				}
				return !done;
			}

			@Override
			public Transition next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Transition result = prepared;
				prepared = null;
				return result;
			}
		};
	}

	//
	// Nested types
	//

	protected static class IncomingRTEList extends IncomingEList {

		private static final long serialVersionUID = 1L;

		protected IncomingRTEList(InternalUMLRTElement owner, EStructuralFeature eStructuralFeature, EList<Transition> delegateList) {
			super(owner, eStructuralFeature, delegateList);
		}

	}

	protected static class OutgoingRTEList extends OutgoingEList {

		private static final long serialVersionUID = 1L;

		protected OutgoingRTEList(InternalUMLRTElement owner, EStructuralFeature eStructuralFeature, EList<Transition> delegateList) {
			super(owner, eStructuralFeature, delegateList);
		}

	}
}
