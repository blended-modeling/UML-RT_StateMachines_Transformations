/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.*;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage
 * @generated
 */
public class SmflatmodelSwitch<T> extends Switch<T>
{
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SmflatmodelPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SmflatmodelSwitch()
    {
        if (modelPackage == null)
        {
            modelPackage = SmflatmodelPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage)
    {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject)
    {
        switch (classifierID)
        {
            case SmflatmodelPackage.SAVE_HISTORY:
            {
                SaveHistory saveHistory = (SaveHistory)theEObject;
                T result = caseSaveHistory(saveHistory);
                if (result == null) result = caseActionCode(saveHistory);
                if (result == null) result = caseNamedElement(saveHistory);
                if (result == null) result = caseCommonElement(saveHistory);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SmflatmodelPackage.CHECK_HISTORY:
            {
                CheckHistory checkHistory = (CheckHistory)theEObject;
                T result = caseCheckHistory(checkHistory);
                if (result == null) result = caseActionCode(checkHistory);
                if (result == null) result = caseNamedElement(checkHistory);
                if (result == null) result = caseCommonElement(checkHistory);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SmflatmodelPackage.ENTRY_ACTION:
            {
                EntryAction entryAction = (EntryAction)theEObject;
                T result = caseEntryAction(entryAction);
                if (result == null) result = caseActionCode(entryAction);
                if (result == null) result = caseNamedElement(entryAction);
                if (result == null) result = caseCommonElement(entryAction);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SmflatmodelPackage.EXIT_ACTION:
            {
                ExitAction exitAction = (ExitAction)theEObject;
                T result = caseExitAction(exitAction);
                if (result == null) result = caseActionCode(exitAction);
                if (result == null) result = caseNamedElement(exitAction);
                if (result == null) result = caseCommonElement(exitAction);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SmflatmodelPackage.TRANSITION_ACTION:
            {
                TransitionAction transitionAction = (TransitionAction)theEObject;
                T result = caseTransitionAction(transitionAction);
                if (result == null) result = caseActionCode(transitionAction);
                if (result == null) result = caseNamedElement(transitionAction);
                if (result == null) result = caseCommonElement(transitionAction);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Save History</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Save History</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSaveHistory(SaveHistory object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Check History</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Check History</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCheckHistory(CheckHistory object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entry Action</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entry Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntryAction(EntryAction object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Exit Action</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Exit Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExitAction(ExitAction object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transition Action</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transition Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransitionAction(TransitionAction object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCommonElement(CommonElement object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Action Code</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Action Code</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActionCode(ActionCode object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object)
    {
        return null;
    }

} //SmflatmodelSwitch
