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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SmflatmodelFactoryImpl extends EFactoryImpl implements SmflatmodelFactory
{
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SmflatmodelFactory init()
    {
        try
        {
            SmflatmodelFactory theSmflatmodelFactory = (SmflatmodelFactory)EPackage.Registry.INSTANCE.getEFactory(SmflatmodelPackage.eNS_URI);
            if (theSmflatmodelFactory != null)
            {
                return theSmflatmodelFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SmflatmodelFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SmflatmodelFactoryImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass)
    {
        switch (eClass.getClassifierID())
        {
            case SmflatmodelPackage.SAVE_HISTORY: return createSaveHistory();
            case SmflatmodelPackage.CHECK_HISTORY: return createCheckHistory();
            case SmflatmodelPackage.ENTRY_ACTION: return createEntryAction();
            case SmflatmodelPackage.EXIT_ACTION: return createExitAction();
            case SmflatmodelPackage.TRANSITION_ACTION: return createTransitionAction();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SaveHistory createSaveHistory()
    {
        SaveHistoryImpl saveHistory = new SaveHistoryImpl();
        return saveHistory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CheckHistory createCheckHistory()
    {
        CheckHistoryImpl checkHistory = new CheckHistoryImpl();
        return checkHistory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EntryAction createEntryAction()
    {
        EntryActionImpl entryAction = new EntryActionImpl();
        return entryAction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExitAction createExitAction()
    {
        ExitActionImpl exitAction = new ExitActionImpl();
        return exitAction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitionAction createTransitionAction()
    {
        TransitionActionImpl transitionAction = new TransitionActionImpl();
        return transitionAction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SmflatmodelPackage getSmflatmodelPackage()
    {
        return (SmflatmodelPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SmflatmodelPackage getPackage()
    {
        return SmflatmodelPackage.eINSTANCE;
    }

} //SmflatmodelFactoryImpl
