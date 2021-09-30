/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.RTTrigger;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.UMLRealTimeStateMachFactory;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.UMLRealTimeStateMachPackage;
import org.eclipse.uml2.types.TypesPackage;

import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UMLRealTimeStateMachPackageImpl extends EPackageImpl implements UMLRealTimeStateMachPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtStateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtRegionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtPseudostateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rtTriggerEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.UMLRealTimeStateMachPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLRealTimeStateMachPackageImpl() {
		super(eNS_URI, UMLRealTimeStateMachFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link UMLRealTimeStateMachPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLRealTimeStateMachPackage init() {
		if (isInited) return (UMLRealTimeStateMachPackage)EPackage.Registry.INSTANCE.getEPackage(UMLRealTimeStateMachPackage.eNS_URI);

		// Obtain or create and register package
		UMLRealTimeStateMachPackageImpl theUMLRealTimeStateMachPackage = (UMLRealTimeStateMachPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UMLRealTimeStateMachPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UMLRealTimeStateMachPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLRealTimeStateMachPackage.createPackageContents();

		// Initialize created meta-data
		theUMLRealTimeStateMachPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLRealTimeStateMachPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLRealTimeStateMachPackage.eNS_URI, theUMLRealTimeStateMachPackage);
		return theUMLRealTimeStateMachPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTStateMachine() {
		return rtStateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTStateMachine_Base_StateMachine() {
		return (EReference)rtStateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRTStateMachine_IsPassive() {
		return (EAttribute)rtStateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTRegion() {
		return rtRegionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTRegion_Base_Region() {
		return (EReference)rtRegionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTState() {
		return rtStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTState_Base_State() {
		return (EReference)rtStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTPseudostate() {
		return rtPseudostateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTPseudostate_Base_Pseudostate() {
		return (EReference)rtPseudostateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRTTrigger() {
		return rtTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRTTrigger_Base_Operation() {
		return (EReference)rtTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRealTimeStateMachFactory getUMLRealTimeStateMachFactory() {
		return (UMLRealTimeStateMachFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		rtStateMachineEClass = createEClass(RT_STATE_MACHINE);
		createEReference(rtStateMachineEClass, RT_STATE_MACHINE__BASE_STATE_MACHINE);
		createEAttribute(rtStateMachineEClass, RT_STATE_MACHINE__IS_PASSIVE);

		rtRegionEClass = createEClass(RT_REGION);
		createEReference(rtRegionEClass, RT_REGION__BASE_REGION);

		rtStateEClass = createEClass(RT_STATE);
		createEReference(rtStateEClass, RT_STATE__BASE_STATE);

		rtPseudostateEClass = createEClass(RT_PSEUDOSTATE);
		createEReference(rtPseudostateEClass, RT_PSEUDOSTATE__BASE_PSEUDOSTATE);

		rtTriggerEClass = createEClass(RT_TRIGGER);
		createEReference(rtTriggerEClass, RT_TRIGGER__BASE_OPERATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(rtStateMachineEClass, RTStateMachine.class, "RTStateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRTStateMachine_Base_StateMachine(), theUMLPackage.getStateMachine(), null, "base_StateMachine", null, 1, 1, RTStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getRTStateMachine_IsPassive(), theTypesPackage.getBoolean(), "isPassive", "false", 1, 1, RTStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(rtRegionEClass, RTRegion.class, "RTRegion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRTRegion_Base_Region(), theUMLPackage.getRegion(), null, "base_Region", null, 1, 1, RTRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(rtStateEClass, RTState.class, "RTState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRTState_Base_State(), theUMLPackage.getState(), null, "base_State", null, 1, 1, RTState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(rtPseudostateEClass, RTPseudostate.class, "RTPseudostate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRTPseudostate_Base_Pseudostate(), theUMLPackage.getPseudostate(), null, "base_Pseudostate", null, 1, 1, RTPseudostate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(rtTriggerEClass, RTTrigger.class, "RTTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRTTrigger_Base_Operation(), theUMLPackage.getOperation(), null, "base_Operation", null, 1, 1, RTTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //UMLRealTimeStateMachPackageImpl
