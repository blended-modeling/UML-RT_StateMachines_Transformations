/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;

import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Pseudostate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.impl.RTPseudostateImpl#getBase_Pseudostate <em>Base Pseudostate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTPseudostateImpl extends MinimalEObjectImpl.Container implements RTPseudostate {
	/**
	 * The cached value of the '{@link #getBase_Pseudostate() <em>Base Pseudostate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Pseudostate()
	 * @generated
	 * @ordered
	 */
	protected Pseudostate base_Pseudostate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTPseudostateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTStateMachinesPackage.Literals.RT_PSEUDOSTATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pseudostate getBase_Pseudostate() {
		if (base_Pseudostate != null && base_Pseudostate.eIsProxy()) {
			InternalEObject oldBase_Pseudostate = (InternalEObject)base_Pseudostate;
			base_Pseudostate = (Pseudostate)eResolveProxy(oldBase_Pseudostate);
			if (base_Pseudostate != oldBase_Pseudostate) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE, oldBase_Pseudostate, base_Pseudostate));
			}
		}
		return base_Pseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pseudostate basicGetBase_Pseudostate() {
		return base_Pseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Pseudostate(Pseudostate newBase_Pseudostate) {
		Pseudostate oldBase_Pseudostate = base_Pseudostate;
		base_Pseudostate = newBase_Pseudostate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE, oldBase_Pseudostate, base_Pseudostate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE:
				if (resolve) return getBase_Pseudostate();
				return basicGetBase_Pseudostate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE:
				setBase_Pseudostate((Pseudostate)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE:
				setBase_Pseudostate((Pseudostate)null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case UMLRTStateMachinesPackage.RT_PSEUDOSTATE__BASE_PSEUDOSTATE:
				return base_Pseudostate != null;
		}
		return super.eIsSet(featureID);
	}

} //RTPseudostateImpl
