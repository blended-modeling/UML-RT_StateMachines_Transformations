/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction;
import org.eclipse.papyrusrt.xtumlrt.common.CommonPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SmflatmodelPackageImpl extends EPackageImpl implements SmflatmodelPackage
{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass saveHistoryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass checkHistoryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass entryActionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass exitActionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass transitionActionEClass = null;

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
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private SmflatmodelPackageImpl()
    {
        super(eNS_URI, SmflatmodelFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link SmflatmodelPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static SmflatmodelPackage init()
    {
        if (isInited) return (SmflatmodelPackage)EPackage.Registry.INSTANCE.getEPackage(SmflatmodelPackage.eNS_URI);

        // Obtain or create and register package
        SmflatmodelPackageImpl theSmflatmodelPackage = (SmflatmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SmflatmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SmflatmodelPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        CommonPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theSmflatmodelPackage.createPackageContents();

        // Initialize created meta-data
        theSmflatmodelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theSmflatmodelPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(SmflatmodelPackage.eNS_URI, theSmflatmodelPackage);
        return theSmflatmodelPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSaveHistory()
    {
        return saveHistoryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSaveHistory_CompositeState()
    {
        return (EReference)saveHistoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSaveHistory_SubState()
    {
        return (EReference)saveHistoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCheckHistory()
    {
        return checkHistoryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCheckHistory_CompositeState()
    {
        return (EReference)checkHistoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCheckHistory_SubState()
    {
        return (EReference)checkHistoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEntryAction()
    {
        return entryActionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExitAction()
    {
        return exitActionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTransitionAction()
    {
        return transitionActionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SmflatmodelFactory getSmflatmodelFactory()
    {
        return (SmflatmodelFactory)getEFactoryInstance();
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
    public void createPackageContents()
    {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        saveHistoryEClass = createEClass(SAVE_HISTORY);
        createEReference(saveHistoryEClass, SAVE_HISTORY__COMPOSITE_STATE);
        createEReference(saveHistoryEClass, SAVE_HISTORY__SUB_STATE);

        checkHistoryEClass = createEClass(CHECK_HISTORY);
        createEReference(checkHistoryEClass, CHECK_HISTORY__COMPOSITE_STATE);
        createEReference(checkHistoryEClass, CHECK_HISTORY__SUB_STATE);

        entryActionEClass = createEClass(ENTRY_ACTION);

        exitActionEClass = createEClass(EXIT_ACTION);

        transitionActionEClass = createEClass(TRANSITION_ACTION);
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
    public void initializePackageContents()
    {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        CommonPackage theCommonPackage = (CommonPackage)EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        saveHistoryEClass.getESuperTypes().add(theCommonPackage.getActionCode());
        checkHistoryEClass.getESuperTypes().add(theCommonPackage.getActionCode());
        entryActionEClass.getESuperTypes().add(theCommonPackage.getActionCode());
        exitActionEClass.getESuperTypes().add(theCommonPackage.getActionCode());
        transitionActionEClass.getESuperTypes().add(theCommonPackage.getActionCode());

        // Initialize classes, features, and operations; add parameters
        initEClass(saveHistoryEClass, SaveHistory.class, "SaveHistory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSaveHistory_CompositeState(), theCommonPackage.getCompositeState(), null, "compositeState", null, 1, 1, SaveHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSaveHistory_SubState(), theCommonPackage.getState(), null, "subState", null, 1, 1, SaveHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(checkHistoryEClass, CheckHistory.class, "CheckHistory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCheckHistory_CompositeState(), theCommonPackage.getCompositeState(), null, "compositeState", null, 1, 1, CheckHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCheckHistory_SubState(), theCommonPackage.getState(), null, "subState", null, 1, 1, CheckHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(entryActionEClass, EntryAction.class, "EntryAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(exitActionEClass, ExitAction.class, "ExitAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(transitionActionEClass, TransitionAction.class, "TransitionAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //SmflatmodelPackageImpl
