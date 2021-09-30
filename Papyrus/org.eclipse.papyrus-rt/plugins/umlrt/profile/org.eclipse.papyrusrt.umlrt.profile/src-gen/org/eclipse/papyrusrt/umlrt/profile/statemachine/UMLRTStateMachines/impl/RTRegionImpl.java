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

import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;

import org.eclipse.uml2.uml.Region;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Region</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.impl.RTRegionImpl#getBase_Region <em>Base Region</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTRegionImpl extends MinimalEObjectImpl.Container implements RTRegion {
	/**
	 * The cached value of the '{@link #getBase_Region() <em>Base Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Region()
	 * @generated
	 * @ordered
	 */
	protected Region base_Region;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTRegionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTStateMachinesPackage.Literals.RT_REGION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Region getBase_Region() {
		if (base_Region != null && base_Region.eIsProxy()) {
			InternalEObject oldBase_Region = (InternalEObject)base_Region;
			base_Region = (Region)eResolveProxy(oldBase_Region);
			if (base_Region != oldBase_Region) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRTStateMachinesPackage.RT_REGION__BASE_REGION, oldBase_Region, base_Region));
			}
		}
		return base_Region;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Region basicGetBase_Region() {
		return base_Region;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Region(Region newBase_Region) {
		Region oldBase_Region = base_Region;
		base_Region = newBase_Region;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTStateMachinesPackage.RT_REGION__BASE_REGION, oldBase_Region, base_Region));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UMLRTStateMachinesPackage.RT_REGION__BASE_REGION:
				if (resolve) return getBase_Region();
				return basicGetBase_Region();
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
			case UMLRTStateMachinesPackage.RT_REGION__BASE_REGION:
				setBase_Region((Region)newValue);
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
			case UMLRTStateMachinesPackage.RT_REGION__BASE_REGION:
				setBase_Region((Region)null);
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
			case UMLRTStateMachinesPackage.RT_REGION__BASE_REGION:
				return base_Region != null;
		}
		return super.eIsSet(featureID);
	}

} //RTRegionImpl
