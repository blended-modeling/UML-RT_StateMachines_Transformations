/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage;

import org.eclipse.papyrusrt.xtumlrt.common.CompositeState;
import org.eclipse.papyrusrt.xtumlrt.common.State;

import org.eclipse.papyrusrt.xtumlrt.common.impl.ActionCodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Check History</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl#getCompositeState <em>Composite State</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl#getSubState <em>Sub State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CheckHistoryImpl extends ActionCodeImpl implements CheckHistory
{
    /**
     * The cached value of the '{@link #getCompositeState() <em>Composite State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCompositeState()
     * @generated
     * @ordered
     */
    protected CompositeState compositeState;

    /**
     * The cached value of the '{@link #getSubState() <em>Sub State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubState()
     * @generated
     * @ordered
     */
    protected State subState;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CheckHistoryImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return SmflatmodelPackage.Literals.CHECK_HISTORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompositeState getCompositeState()
    {
        if (compositeState != null && compositeState.eIsProxy())
        {
            InternalEObject oldCompositeState = (InternalEObject)compositeState;
            compositeState = (CompositeState)eResolveProxy(oldCompositeState);
            if (compositeState != oldCompositeState)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE, oldCompositeState, compositeState));
            }
        }
        return compositeState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompositeState basicGetCompositeState()
    {
        return compositeState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCompositeState(CompositeState newCompositeState)
    {
        CompositeState oldCompositeState = compositeState;
        compositeState = newCompositeState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE, oldCompositeState, compositeState));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public State getSubState()
    {
        if (subState != null && subState.eIsProxy())
        {
            InternalEObject oldSubState = (InternalEObject)subState;
            subState = (State)eResolveProxy(oldSubState);
            if (subState != oldSubState)
            {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, SmflatmodelPackage.CHECK_HISTORY__SUB_STATE, oldSubState, subState));
            }
        }
        return subState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public State basicGetSubState()
    {
        return subState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSubState(State newSubState)
    {
        State oldSubState = subState;
        subState = newSubState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SmflatmodelPackage.CHECK_HISTORY__SUB_STATE, oldSubState, subState));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType)
    {
        switch (featureID)
        {
            case SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE:
                if (resolve) return getCompositeState();
                return basicGetCompositeState();
            case SmflatmodelPackage.CHECK_HISTORY__SUB_STATE:
                if (resolve) return getSubState();
                return basicGetSubState();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue)
    {
        switch (featureID)
        {
            case SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE:
                setCompositeState((CompositeState)newValue);
                return;
            case SmflatmodelPackage.CHECK_HISTORY__SUB_STATE:
                setSubState((State)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID)
    {
        switch (featureID)
        {
            case SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE:
                setCompositeState((CompositeState)null);
                return;
            case SmflatmodelPackage.CHECK_HISTORY__SUB_STATE:
                setSubState((State)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID)
    {
        switch (featureID)
        {
            case SmflatmodelPackage.CHECK_HISTORY__COMPOSITE_STATE:
                return compositeState != null;
            case SmflatmodelPackage.CHECK_HISTORY__SUB_STATE:
                return subState != null;
        }
        return super.eIsSet(featureID);
    }

} //CheckHistoryImpl
