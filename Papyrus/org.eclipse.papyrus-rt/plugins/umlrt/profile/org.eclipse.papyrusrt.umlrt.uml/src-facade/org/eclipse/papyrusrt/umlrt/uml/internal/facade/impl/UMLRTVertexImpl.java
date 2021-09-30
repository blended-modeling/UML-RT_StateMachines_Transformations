/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getRedefinedVertex <em>Redefined Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getState <em>State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getIncomings <em>Incoming</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getOutgoings <em>Outgoing</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTVertexImpl#getStateMachine <em>State Machine</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UMLRTVertexImpl extends UMLRTNamedElementImpl implements UMLRTVertex {
	static final FacadeType<Vertex, EObject, UMLRTVertexImpl> TYPE = new FacadeType.Union<>(
			UMLRTVertexImpl.class,
			UMLPackage.Literals.VERTEX,
			// The initialization of UMLRTStateImpl references this UMLRTVertexImpl.TYPE,
			// which is a dependency cycle that we resolve by deferring the reference to
			// UMLRTStateImpl.TYPE
			() -> Arrays.asList(UMLRTStateImpl.TYPE, UMLRTPseudostateImpl.TYPE, UMLRTConnectionPointImpl.TYPE));

	protected static class VertexAdapter<F extends UMLRTVertexImpl> extends NamedElementAdapter<F> {

		VertexAdapter(F facade) {
			super(facade);
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTVertexImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a state machine vertex.
	 *
	 * @param vertex
	 *            a vertex in a state machine in the UML model
	 *
	 * @return its façade, or {@code null} if the vertex is not a valid UML-RT state machine vertex
	 */
	public static UMLRTVertexImpl getInstance(Vertex vertex) {
		UMLRTVertexImpl result = null;

		if (vertex instanceof State) {
			result = UMLRTStateImpl.getInstance((State) vertex);
		} else if (vertex instanceof Pseudostate) {
			Pseudostate pseudo = (Pseudostate) vertex;

			result = UMLRTPseudostateImpl.getInstance(pseudo);
			if (result == null) {
				result = UMLRTConnectionPointImpl.getInstance(pseudo);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.VERTEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		UMLRTState state = getState();
		if (state != null) {
			return state;
		}
		UMLRTStateMachine stateMachine = getStateMachine();
		if (stateMachine != null) {
			return stateMachine;
		}
		return super.getRedefinitionContext();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTVertexImpl> createFacadeAdapter() {
		return new VertexAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		if (isSetRedefinedVertex()) {
			return getRedefinedVertex();
		}
		return super.getRedefinedElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTState getState() {
		Element owner = toUML().getOwner();
		if (owner instanceof Region) {
			owner = owner.getOwner();
		}
		return (owner instanceof State)
				? UMLRTState.getInstance((State) owner)
				: null;
	}

	protected static class OutgoingList
			extends DelegatingEcoreEList<UMLRTTransition> {

		private static final long serialVersionUID = 1L;

		protected final EStructuralFeature eStructuralFeature;

		protected final EList<UMLRTTransition> delegate;

		protected OutgoingList(InternalEObject owner,
				EStructuralFeature eStructuralFeature,
				EList<UMLRTTransition> delegate) {
			super(owner);

			this.eStructuralFeature = eStructuralFeature;
			this.delegate = delegate;
		}

		@Override
		public EStructuralFeature getEStructuralFeature() {
			return eStructuralFeature;
		}

		@Override
		public int getFeatureID() {
			return owner.eDerivedStructuralFeatureID(
					eStructuralFeature.getFeatureID(), UMLRTVertex.class);
		}

		@Override
		protected EList<UMLRTTransition> delegateList() {
			return delegate;
		}

		@Override
		protected void delegateAdd(int index, UMLRTTransition transition) {
			int delegateIndex = delegate.indexOf(transition);

			if (delegateIndex != -1) {

				if (index != delegateIndex) {
					delegate.move(index, transition);
				}
			} else if (index < delegate.size()) {
				delegate.add(index, transition);
			} else {
				delegate.add(transition);
			}
		}

		@Override
		protected void didAdd(int index, UMLRTTransition newTransition) {
			super.didAdd(index, newTransition);

			newTransition.setSource((UMLRTVertex) owner);
		}

		@Override
		protected void didRemove(int index, UMLRTTransition oldTransition) {
			super.didRemove(index, oldTransition);

			oldTransition.setSource(null);
		}

		@Override
		protected void didSet(int index, UMLRTTransition newTransition,
				UMLRTTransition oldTransition) {
			super.didSet(index, newTransition, oldTransition);

			newTransition.setSource((UMLRTVertex) owner);
			oldTransition.setSource(null);
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTTransition> getOutgoings() {
		EList<Transition> uml = toUML().getOutgoings();
		EList<UMLRTTransition> outgoings = uml.stream()
				.map(UMLRTTransition::getInstance)
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(UniqueEList.FastCompare::new));

		return new OutgoingList(this, UMLRTUMLRTPackage.Literals.VERTEX__OUTGOING, outgoings);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getOutgoing(String name) {
		return getOutgoing(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getOutgoing(String name, boolean ignoreCase) {
		outgoingLoop: for (UMLRTTransition outgoing : getOutgoings()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(outgoing.getName()) : name.equals(outgoing.getName()))) {
				continue outgoingLoop;
			}
			return outgoing;
		}
		return null;
	}

	protected static class IncomingList
			extends DelegatingEcoreEList<UMLRTTransition> {

		private static final long serialVersionUID = 1L;

		protected final EStructuralFeature eStructuralFeature;

		protected final EList<UMLRTTransition> delegate;

		protected IncomingList(InternalEObject owner,
				EStructuralFeature eStructuralFeature,
				EList<UMLRTTransition> delegate) {
			super(owner);

			this.eStructuralFeature = eStructuralFeature;
			this.delegate = delegate;
		}

		@Override
		public EStructuralFeature getEStructuralFeature() {
			return eStructuralFeature;
		}

		@Override
		public int getFeatureID() {
			return owner.eDerivedStructuralFeatureID(
					eStructuralFeature.getFeatureID(), UMLRTVertex.class);
		}

		@Override
		protected EList<UMLRTTransition> delegateList() {
			return delegate;
		}

		@Override
		protected void delegateAdd(int index, UMLRTTransition transition) {
			int delegateIndex = delegate.indexOf(transition);

			if (delegateIndex != -1) {

				if (index != delegateIndex) {
					delegate.move(index, transition);
				}
			} else if (index < delegate.size()) {
				delegate.add(index, transition);
			} else {
				delegate.add(transition);
			}
		}

		@Override
		protected void didAdd(int index, UMLRTTransition newTransition) {
			super.didAdd(index, newTransition);

			newTransition.setTarget((UMLRTVertex) owner);
		}

		@Override
		protected void didRemove(int index, UMLRTTransition oldTransition) {
			super.didRemove(index, oldTransition);

			oldTransition.setTarget(null);
		}

		@Override
		protected void didSet(int index, UMLRTTransition newTransition,
				UMLRTTransition oldTransition) {
			super.didSet(index, newTransition, oldTransition);

			newTransition.setTarget((UMLRTVertex) owner);
			oldTransition.setTarget(null);
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTTransition> getIncomings() {
		EList<Transition> uml = toUML().getIncomings();
		EList<UMLRTTransition> incomings = uml.stream()
				.map(UMLRTTransition::getInstance)
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(UniqueEList.FastCompare::new));

		return new IncomingList(this, UMLRTUMLRTPackage.Literals.VERTEX__INCOMING, incomings);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getIncoming(String name) {
		return getIncoming(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition getIncoming(String name, boolean ignoreCase) {
		incomingLoop: for (UMLRTTransition incoming : getIncomings()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(incoming.getName()) : name.equals(incoming.getName()))) {
				continue incomingLoop;
			}
			return incoming;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getRedefinedVertex() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine getStateMachine() {
		Element owner = toUML().getOwner();
		if (owner instanceof Region) {
			owner = owner.getOwner();
		}
		return (owner instanceof StateMachine)
				? UMLRTStateMachine.getInstance((StateMachine) owner)
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Vertex toUML() {
		return (Vertex) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine containingStateMachine() {
		UMLRTStateMachine result = getStateMachine();

		if (result == null) {
			UMLRTState state = getState();
			if (state != null) {
				result = state.containingStateMachine();
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTTransition createTransitionTo(UMLRTVertex target) {
		Region myRegion = getRegion(toUML());

		Region region = commonRegion(myRegion, getRegion(target.toUML()));
		if (region == null) {
			// No common region? Use mine
			region = myRegion;
		}

		Transition result = region.createTransition(null);
		result.setSource(toUML());
		result.setTarget(target.toUML());

		return UMLRTTransition.getInstance(result);
	}

	static Region commonRegion(Region r1, Region r2) {
		Region result;

		if ((r1 == r2) || isAncestor(r2, r1)) {
			result = r1;
		} else if (isAncestor(r1, r2)) {
			result = r2;
		} else {
			r1 = containingRegion(r1);
			r2 = containingRegion(r2);

			result = ((r1 != null) && (r2 != null))
					? commonRegion(r1, r2)
					: null;
		}

		return result;
	}

	static boolean isAncestor(Region region, Region possibleAncestor) {
		boolean result = region == possibleAncestor;

		if (!result) {
			for (region = containingRegion(region); !result && (region != null); region = containingRegion(region)) {
				result = region == possibleAncestor;
			}
		}

		return result;
	}

	static Region containingRegion(Region region) {
		return (region.getState() == null) ? null : region.getState().getContainer();
	}

	static Region getRegion(Vertex vertex) {
		Region result = null;
		Pseudostate pseudo = (vertex instanceof Pseudostate) ? (Pseudostate) vertex : null;

		if ((pseudo != null) && (pseudo.getState() != null)) {
			// A connection point's preferred region for transitions is
			// the composite state's region
			result = UMLRTStateImpl.getCompositeRegion(pseudo.getState());
		}

		for (Element owner = vertex.getOwner(); (result == null) && (owner != null); owner = owner.getOwner()) {
			if (owner instanceof Region) {
				result = (Region) owner;
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTUMLRTPackage.VERTEX__REDEFINED_VERTEX:
			return getRedefinedVertex();
		case UMLRTUMLRTPackage.VERTEX__STATE:
			return getState();
		case UMLRTUMLRTPackage.VERTEX__INCOMING:
			return getIncomings();
		case UMLRTUMLRTPackage.VERTEX__OUTGOING:
			return getOutgoings();
		case UMLRTUMLRTPackage.VERTEX__STATE_MACHINE:
			return getStateMachine();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.VERTEX__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.VERTEX__REDEFINED_VERTEX:
			return isSetRedefinedVertex();
		case UMLRTUMLRTPackage.VERTEX__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.VERTEX__STATE:
			return getState() != null;
		case UMLRTUMLRTPackage.VERTEX__INCOMING:
			return !getIncomings().isEmpty();
		case UMLRTUMLRTPackage.VERTEX__OUTGOING:
			return !getOutgoings().isEmpty();
		case UMLRTUMLRTPackage.VERTEX__STATE_MACHINE:
			return getStateMachine() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinitionContext() {
		return super.isSetRedefinitionContext()
				|| eIsSet(UMLRTUMLRTPackage.VERTEX__STATE)
				|| eIsSet(UMLRTUMLRTPackage.VERTEX__STATE_MACHINE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetRedefinedVertex() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinedElement() {
		return super.isSetRedefinedElement()
				|| isSetRedefinedVertex();
	}

} // UMLRTVertexImpl
