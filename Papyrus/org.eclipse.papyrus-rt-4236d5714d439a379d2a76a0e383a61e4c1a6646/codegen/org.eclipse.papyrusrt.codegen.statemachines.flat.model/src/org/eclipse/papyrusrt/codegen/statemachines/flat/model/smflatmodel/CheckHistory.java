/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel;

import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.CompositeState;
import org.eclipse.papyrusrt.xtumlrt.common.State;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Check History</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getCompositeState <em>Composite State</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getSubState <em>Sub State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage#getCheckHistory()
 * @model
 * @generated
 */
public interface CheckHistory extends ActionCode
{
    /**
     * Returns the value of the '<em><b>Composite State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Composite State</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Composite State</em>' reference.
     * @see #setCompositeState(CompositeState)
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage#getCheckHistory_CompositeState()
     * @model required="true"
     * @generated
     */
    CompositeState getCompositeState();

    /**
     * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getCompositeState <em>Composite State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Composite State</em>' reference.
     * @see #getCompositeState()
     * @generated
     */
    void setCompositeState(CompositeState value);

    /**
     * Returns the value of the '<em><b>Sub State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sub State</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sub State</em>' reference.
     * @see #setSubState(State)
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage#getCheckHistory_SubState()
     * @model required="true"
     * @generated
     */
    State getSubState();

    /**
     * Sets the value of the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getSubState <em>Sub State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sub State</em>' reference.
     * @see #getSubState()
     * @generated
     */
    void setSubState(State value);

} // CheckHistory
