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
package org.eclipse.papyrusrt.umlrt.uml.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage
 * @generated
 */
public class UMLRTAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static UMLRTUMLRTPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = UMLRTUMLRTPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTSwitch<Adapter> modelSwitch = new UMLRTSwitch<Adapter>() {
		@Override
		public Adapter caseNamedElement(UMLRTNamedElement object) {
			return createNamedElementAdapter();
		}

		@Override
		public Adapter casePackage(UMLRTPackage object) {
			return createPackageAdapter();
		}

		@Override
		public Adapter caseCapsule(UMLRTCapsule object) {
			return createCapsuleAdapter();
		}

		@Override
		public Adapter caseClassifier(UMLRTClassifier object) {
			return createClassifierAdapter();
		}

		@Override
		public Adapter casePort(UMLRTPort object) {
			return createPortAdapter();
		}

		@Override
		public Adapter caseReplicatedElement(UMLRTReplicatedElement object) {
			return createReplicatedElementAdapter();
		}

		@Override
		public Adapter caseProtocol(UMLRTProtocol object) {
			return createProtocolAdapter();
		}

		@Override
		public Adapter caseProtocolMessage(UMLRTProtocolMessage object) {
			return createProtocolMessageAdapter();
		}

		@Override
		public Adapter caseCapsulePart(UMLRTCapsulePart object) {
			return createCapsulePartAdapter();
		}

		@Override
		public Adapter caseConnector(UMLRTConnector object) {
			return createConnectorAdapter();
		}

		@Override
		public Adapter caseStateMachine(UMLRTStateMachine object) {
			return createStateMachineAdapter();
		}

		@Override
		public Adapter caseVertex(UMLRTVertex object) {
			return createVertexAdapter();
		}

		@Override
		public Adapter caseState(UMLRTState object) {
			return createStateAdapter();
		}

		@Override
		public Adapter caseTransition(UMLRTTransition object) {
			return createTransitionAdapter();
		}

		@Override
		public Adapter caseTrigger(UMLRTTrigger object) {
			return createTriggerAdapter();
		}

		@Override
		public Adapter caseGuard(UMLRTGuard object) {
			return createGuardAdapter();
		}

		@Override
		public Adapter caseOpaqueBehavior(UMLRTOpaqueBehavior object) {
			return createOpaqueBehaviorAdapter();
		}

		@Override
		public Adapter caseConnectionPoint(UMLRTConnectionPoint object) {
			return createConnectionPointAdapter();
		}

		@Override
		public Adapter casePseudostate(UMLRTPseudostate object) {
			return createPseudostateAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage
	 * @generated
	 */
	public Adapter createPackageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier
	 * @generated
	 */
	public Adapter createClassifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule
	 * @generated
	 */
	public Adapter createCapsuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement <em>Replicated Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement
	 * @generated
	 */
	public Adapter createReplicatedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPort
	 * @generated
	 */
	public Adapter createPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart <em>Capsule Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart
	 * @generated
	 */
	public Adapter createCapsulePartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector
	 * @generated
	 */
	public Adapter createConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine
	 * @generated
	 */
	public Adapter createStateMachineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex
	 * @generated
	 */
	public Adapter createVertexAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTState
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger
	 * @generated
	 */
	public Adapter createTriggerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard
	 * @generated
	 */
	public Adapter createGuardAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior <em>Opaque Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior
	 * @generated
	 */
	public Adapter createOpaqueBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint <em>Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint
	 * @generated
	 */
	public Adapter createConnectionPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate <em>Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate
	 * @generated
	 */
	public Adapter createPseudostateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol
	 * @generated
	 */
	public Adapter createProtocolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage <em>Protocol Message</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage
	 * @generated
	 */
	public Adapter createProtocolMessageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // UMLRTAdapterFactory
