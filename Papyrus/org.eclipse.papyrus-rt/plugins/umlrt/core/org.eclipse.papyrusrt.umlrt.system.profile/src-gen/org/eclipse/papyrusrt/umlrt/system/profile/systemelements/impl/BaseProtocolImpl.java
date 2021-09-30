/**
 * Copyright (c) 2016 CEA LIST and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol;
import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemElementsPackage;

import org.eclipse.uml2.uml.Collaboration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Protocol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.BaseProtocolImpl#getBase_Collaboration <em>Base Collaboration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BaseProtocolImpl extends MinimalEObjectImpl.Container implements BaseProtocol {
	/**
	 * The cached value of the '{@link #getBase_Collaboration() <em>Base Collaboration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Collaboration()
	 * @generated
	 * @ordered
	 */
	protected Collaboration base_Collaboration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseProtocolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SystemElementsPackage.Literals.BASE_PROTOCOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collaboration getBase_Collaboration() {
		if (base_Collaboration != null && base_Collaboration.eIsProxy()) {
			InternalEObject oldBase_Collaboration = (InternalEObject)base_Collaboration;
			base_Collaboration = (Collaboration)eResolveProxy(oldBase_Collaboration);
			if (base_Collaboration != oldBase_Collaboration) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION, oldBase_Collaboration, base_Collaboration));
			}
		}
		return base_Collaboration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collaboration basicGetBase_Collaboration() {
		return base_Collaboration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Collaboration(Collaboration newBase_Collaboration) {
		Collaboration oldBase_Collaboration = base_Collaboration;
		base_Collaboration = newBase_Collaboration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION, oldBase_Collaboration, base_Collaboration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION:
				if (resolve) return getBase_Collaboration();
				return basicGetBase_Collaboration();
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
			case SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION:
				setBase_Collaboration((Collaboration)newValue);
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
			case SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION:
				setBase_Collaboration((Collaboration)null);
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
			case SystemElementsPackage.BASE_PROTOCOL__BASE_COLLABORATION:
				return base_Collaboration != null;
		}
		return super.eIsSet(featureID);
	}

} //BaseProtocolImpl
