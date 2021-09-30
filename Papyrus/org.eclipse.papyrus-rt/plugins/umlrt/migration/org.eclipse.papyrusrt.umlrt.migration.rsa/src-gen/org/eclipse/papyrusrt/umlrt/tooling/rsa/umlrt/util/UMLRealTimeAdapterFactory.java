/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage
 * @generated
 */
public class UMLRealTimeAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static UMLRealTimePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRealTimeAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = UMLRealTimePackage.eINSTANCE;
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
	protected UMLRealTimeSwitch<Adapter> modelSwitch = new UMLRealTimeSwitch<Adapter>() {
		@Override
		public Adapter caseRTStereotype(RTStereotype object) {
			return createRTStereotypeAdapter();
		}

		@Override
		public Adapter caseAbstractEvent(AbstractEvent object) {
			return createAbstractEventAdapter();
		}

		@Override
		public Adapter caseCapsule(Capsule object) {
			return createCapsuleAdapter();
		}

		@Override
		public Adapter caseCapsulePart(CapsulePart object) {
			return createCapsulePartAdapter();
		}

		@Override
		public Adapter caseCoregion(Coregion object) {
			return createCoregionAdapter();
		}

		@Override
		public Adapter caseInEvent(InEvent object) {
			return createInEventAdapter();
		}

		@Override
		public Adapter caseOutEvent(OutEvent object) {
			return createOutEventAdapter();
		}

		@Override
		public Adapter caseProtocol(Protocol object) {
			return createProtocolAdapter();
		}

		@Override
		public Adapter caseProtocolContainer(ProtocolContainer object) {
			return createProtocolContainerAdapter();
		}

		@Override
		public Adapter caseRTConnector(RTConnector object) {
			return createRTConnectorAdapter();
		}

		@Override
		public Adapter caseRTHistorystate(RTHistorystate object) {
			return createRTHistorystateAdapter();
		}

		@Override
		public Adapter caseRTPort(RTPort object) {
			return createRTPortAdapter();
		}

		@Override
		public Adapter caseRTRedefinableElement(RTRedefinableElement object) {
			return createRTRedefinableElementAdapter();
		}

		@Override
		public Adapter caseTrigger(Trigger object) {
			return createTriggerAdapter();
		}

		@Override
		public Adapter caseRTMessage(RTMessage object) {
			return createRTMessageAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype <em>RT Stereotype</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype
	 * @generated
	 */
	public Adapter createRTStereotypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent <em>Abstract Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent
	 * @generated
	 */
	public Adapter createAbstractEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule
	 * @generated
	 */
	public Adapter createCapsuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart <em>Capsule Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart
	 * @generated
	 */
	public Adapter createCapsulePartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Coregion <em>Coregion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Coregion
	 * @generated
	 */
	public Adapter createCoregionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.InEvent <em>In Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.InEvent
	 * @generated
	 */
	public Adapter createInEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.OutEvent <em>Out Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.OutEvent
	 * @generated
	 */
	public Adapter createOutEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol
	 * @generated
	 */
	public Adapter createProtocolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer <em>Protocol Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer
	 * @generated
	 */
	public Adapter createProtocolContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector <em>RT Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector
	 * @generated
	 */
	public Adapter createRTConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate <em>RT Historystate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate
	 * @generated
	 */
	public Adapter createRTHistorystateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort <em>RT Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort
	 * @generated
	 */
	public Adapter createRTPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement <em>RT Redefinable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement
	 * @generated
	 */
	public Adapter createRTRedefinableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger
	 * @generated
	 */
	public Adapter createTriggerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage <em>RT Message</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage
	 * @generated
	 */
	public Adapter createRTMessageAdapter() {
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

} // UMLRealTimeAdapterFactory
