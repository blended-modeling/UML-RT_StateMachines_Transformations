/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.UMLRealTimeStateMachFactory
 * @model kind="package"
 * @generated
 */
public interface UMLRealTimeStateMachPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "UMLRealTimeStateMach"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlrt/statemachine"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "UMLRealTimeStateMach"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UMLRealTimeStateMachPackage eINSTANCE = org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateMachineImpl <em>RT State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateMachineImpl
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTStateMachine()
	 * @generated
	 */
	int RT_STATE_MACHINE = 0;

	/**
	 * The feature id for the '<em><b>Base State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_MACHINE__BASE_STATE_MACHINE = 0;

	/**
	 * The feature id for the '<em><b>Is Passive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_MACHINE__IS_PASSIVE = 1;

	/**
	 * The number of structural features of the '<em>RT State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_MACHINE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>RT State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_MACHINE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTRegionImpl <em>RT Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTRegionImpl
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTRegion()
	 * @generated
	 */
	int RT_REGION = 1;

	/**
	 * The feature id for the '<em><b>Base Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_REGION__BASE_REGION = 0;

	/**
	 * The number of structural features of the '<em>RT Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_REGION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RT Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_REGION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateImpl <em>RT State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateImpl
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTState()
	 * @generated
	 */
	int RT_STATE = 2;

	/**
	 * The feature id for the '<em><b>Base State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE__BASE_STATE = 0;

	/**
	 * The number of structural features of the '<em>RT State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RT State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTPseudostateImpl <em>RT Pseudostate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTPseudostateImpl
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTPseudostate()
	 * @generated
	 */
	int RT_PSEUDOSTATE = 3;

	/**
	 * The feature id for the '<em><b>Base Pseudostate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_PSEUDOSTATE__BASE_PSEUDOSTATE = 0;

	/**
	 * The number of structural features of the '<em>RT Pseudostate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_PSEUDOSTATE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RT Pseudostate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_PSEUDOSTATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTTriggerImpl <em>RT Trigger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTTriggerImpl
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTTrigger()
	 * @generated
	 */
	int RT_TRIGGER = 4;

	/**
	 * The feature id for the '<em><b>Base Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_TRIGGER__BASE_OPERATION = 0;

	/**
	 * The number of structural features of the '<em>RT Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_TRIGGER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RT Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_TRIGGER_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine <em>RT State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine
	 * @generated
	 */
	EClass getRTStateMachine();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine#getBase_StateMachine <em>Base State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base State Machine</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine#getBase_StateMachine()
	 * @see #getRTStateMachine()
	 * @generated
	 */
	EReference getRTStateMachine_Base_StateMachine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine#isPassive <em>Is Passive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Passive</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine#isPassive()
	 * @see #getRTStateMachine()
	 * @generated
	 */
	EAttribute getRTStateMachine_IsPassive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTRegion <em>RT Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT Region</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTRegion
	 * @generated
	 */
	EClass getRTRegion();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTRegion#getBase_Region <em>Base Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Region</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTRegion#getBase_Region()
	 * @see #getRTRegion()
	 * @generated
	 */
	EReference getRTRegion_Base_Region();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTState <em>RT State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTState
	 * @generated
	 */
	EClass getRTState();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTState#getBase_State <em>Base State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base State</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTState#getBase_State()
	 * @see #getRTState()
	 * @generated
	 */
	EReference getRTState_Base_State();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTPseudostate <em>RT Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT Pseudostate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTPseudostate
	 * @generated
	 */
	EClass getRTPseudostate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTPseudostate#getBase_Pseudostate <em>Base Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Pseudostate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTPseudostate#getBase_Pseudostate()
	 * @see #getRTPseudostate()
	 * @generated
	 */
	EReference getRTPseudostate_Base_Pseudostate();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTTrigger <em>RT Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTTrigger
	 * @generated
	 */
	EClass getRTTrigger();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTTrigger#getBase_Operation <em>Base Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Operation</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTTrigger#getBase_Operation()
	 * @see #getRTTrigger()
	 * @generated
	 */
	EReference getRTTrigger_Base_Operation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLRealTimeStateMachFactory getUMLRealTimeStateMachFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateMachineImpl <em>RT State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateMachineImpl
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTStateMachine()
		 * @generated
		 */
		EClass RT_STATE_MACHINE = eINSTANCE.getRTStateMachine();

		/**
		 * The meta object literal for the '<em><b>Base State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_STATE_MACHINE__BASE_STATE_MACHINE = eINSTANCE.getRTStateMachine_Base_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Is Passive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RT_STATE_MACHINE__IS_PASSIVE = eINSTANCE.getRTStateMachine_IsPassive();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTRegionImpl <em>RT Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTRegionImpl
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTRegion()
		 * @generated
		 */
		EClass RT_REGION = eINSTANCE.getRTRegion();

		/**
		 * The meta object literal for the '<em><b>Base Region</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_REGION__BASE_REGION = eINSTANCE.getRTRegion_Base_Region();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateImpl <em>RT State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTStateImpl
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTState()
		 * @generated
		 */
		EClass RT_STATE = eINSTANCE.getRTState();

		/**
		 * The meta object literal for the '<em><b>Base State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_STATE__BASE_STATE = eINSTANCE.getRTState_Base_State();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTPseudostateImpl <em>RT Pseudostate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTPseudostateImpl
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTPseudostate()
		 * @generated
		 */
		EClass RT_PSEUDOSTATE = eINSTANCE.getRTPseudostate();

		/**
		 * The meta object literal for the '<em><b>Base Pseudostate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_PSEUDOSTATE__BASE_PSEUDOSTATE = eINSTANCE.getRTPseudostate_Base_Pseudostate();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTTriggerImpl <em>RT Trigger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.RTTriggerImpl
		 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachPackageImpl#getRTTrigger()
		 * @generated
		 */
		EClass RT_TRIGGER = eINSTANCE.getRTTrigger();

		/**
		 * The meta object literal for the '<em><b>Base Operation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_TRIGGER__BASE_OPERATION = eINSTANCE.getRTTrigger_Base_Operation();

	}

} //UMLRealTimeStateMachPackage
