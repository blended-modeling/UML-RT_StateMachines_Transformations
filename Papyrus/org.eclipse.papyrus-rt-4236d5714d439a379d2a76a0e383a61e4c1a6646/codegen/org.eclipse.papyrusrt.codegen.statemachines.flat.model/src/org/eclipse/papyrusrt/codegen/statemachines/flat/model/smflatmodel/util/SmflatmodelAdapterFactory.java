/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.*;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage
 * @generated
 */
public class SmflatmodelAdapterFactory extends AdapterFactoryImpl
{
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SmflatmodelPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SmflatmodelAdapterFactory()
    {
        if (modelPackage == null)
        {
            modelPackage = SmflatmodelPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object)
    {
        if (object == modelPackage)
        {
            return true;
        }
        if (object instanceof EObject)
        {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SmflatmodelSwitch<Adapter> modelSwitch =
        new SmflatmodelSwitch<Adapter>()
        {
            @Override
            public Adapter caseSaveHistory(SaveHistory object)
            {
                return createSaveHistoryAdapter();
            }
            @Override
            public Adapter caseCheckHistory(CheckHistory object)
            {
                return createCheckHistoryAdapter();
            }
            @Override
            public Adapter caseEntryAction(EntryAction object)
            {
                return createEntryActionAdapter();
            }
            @Override
            public Adapter caseExitAction(ExitAction object)
            {
                return createExitActionAdapter();
            }
            @Override
            public Adapter caseTransitionAction(TransitionAction object)
            {
                return createTransitionActionAdapter();
            }
            @Override
            public Adapter caseCommonElement(CommonElement object)
            {
                return createCommonElementAdapter();
            }
            @Override
            public Adapter caseNamedElement(NamedElement object)
            {
                return createNamedElementAdapter();
            }
            @Override
            public Adapter caseActionCode(ActionCode object)
            {
                return createActionCodeAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object)
            {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target)
    {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory <em>Save History</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory
     * @generated
     */
    public Adapter createSaveHistoryAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory <em>Check History</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory
     * @generated
     */
    public Adapter createCheckHistoryAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction <em>Entry Action</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction
     * @generated
     */
    public Adapter createEntryActionAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction <em>Exit Action</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction
     * @generated
     */
    public Adapter createExitActionAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction <em>Transition Action</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction
     * @generated
     */
    public Adapter createTransitionActionAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.xtumlrt.common.CommonElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.xtumlrt.common.CommonElement
     * @generated
     */
    public Adapter createCommonElementAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.xtumlrt.common.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.xtumlrt.common.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.xtumlrt.common.ActionCode <em>Action Code</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.papyrusrt.xtumlrt.common.ActionCode
     * @generated
     */
    public Adapter createActionCodeAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter()
    {
        return null;
    }

} //SmflatmodelAdapterFactory
