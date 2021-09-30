/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UMLRealTimeStateMachFactoryImpl extends EFactoryImpl implements UMLRealTimeStateMachFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UMLRealTimeStateMachFactory init() {
		try {
			UMLRealTimeStateMachFactory theUMLRealTimeStateMachFactory = (UMLRealTimeStateMachFactory)EPackage.Registry.INSTANCE.getEFactory(UMLRealTimeStateMachPackage.eNS_URI);
			if (theUMLRealTimeStateMachFactory != null) {
				return theUMLRealTimeStateMachFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRealTimeStateMachFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRealTimeStateMachFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case UMLRealTimeStateMachPackage.RT_STATE_MACHINE: return createRTStateMachine();
			case UMLRealTimeStateMachPackage.RT_REGION: return createRTRegion();
			case UMLRealTimeStateMachPackage.RT_STATE: return createRTState();
			case UMLRealTimeStateMachPackage.RT_PSEUDOSTATE: return createRTPseudostate();
			case UMLRealTimeStateMachPackage.RT_TRIGGER: return createRTTrigger();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTStateMachine createRTStateMachine() {
		RTStateMachineImpl rtStateMachine = new RTStateMachineImpl();
		return rtStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTRegion createRTRegion() {
		RTRegionImpl rtRegion = new RTRegionImpl();
		return rtRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTState createRTState() {
		RTStateImpl rtState = new RTStateImpl();
		return rtState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTPseudostate createRTPseudostate() {
		RTPseudostateImpl rtPseudostate = new RTPseudostateImpl();
		return rtPseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTTrigger createRTTrigger() {
		RTTriggerImpl rtTrigger = new RTTriggerImpl();
		return rtTrigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRealTimeStateMachPackage getUMLRealTimeStateMachPackage() {
		return (UMLRealTimeStateMachPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRealTimeStateMachPackage getPackage() {
		return UMLRealTimeStateMachPackage.eINSTANCE;
	}

} //UMLRealTimeStateMachFactoryImpl
