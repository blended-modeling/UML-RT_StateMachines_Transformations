/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl#getBase_Class <em>Base Class</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl#getPrivateDeclarations <em>Private Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl#getProtectedDeclarations <em>Protected Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ClassPropertiesImpl#getPublicDeclarations <em>Public Declarations</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ClassPropertiesImpl extends CppFilePropertiesImpl implements ClassProperties {
	/**
	 * The cached value of the '{@link #getBase_Class() <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Class()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.uml2.uml.Class base_Class;

	/**
	 * The default value of the '{@link #getPrivateDeclarations() <em>Private Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIVATE_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrivateDeclarations() <em>Private Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String privateDeclarations = PRIVATE_DECLARATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getProtectedDeclarations() <em>Protected Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtectedDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PROTECTED_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtectedDeclarations() <em>Protected Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtectedDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String protectedDeclarations = PROTECTED_DECLARATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPublicDeclarations() <em>Public Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PUBLIC_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPublicDeclarations() <em>Public Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String publicDeclarations = PUBLIC_DECLARATIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.CLASS_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class getBase_Class() {
		if (base_Class != null && base_Class.eIsProxy()) {
			InternalEObject oldBase_Class = (InternalEObject)base_Class;
			base_Class = (org.eclipse.uml2.uml.Class)eResolveProxy(oldBase_Class);
			if (base_Class != oldBase_Class) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
			}
		}
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class basicGetBase_Class() {
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Class(org.eclipse.uml2.uml.Class newBase_Class) {
		org.eclipse.uml2.uml.Class oldBase_Class = base_Class;
		base_Class = newBase_Class;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrivateDeclarations() {
		return privateDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateDeclarations(String newPrivateDeclarations) {
		String oldPrivateDeclarations = privateDeclarations;
		privateDeclarations = newPrivateDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS, oldPrivateDeclarations, privateDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProtectedDeclarations() {
		return protectedDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtectedDeclarations(String newProtectedDeclarations) {
		String oldProtectedDeclarations = protectedDeclarations;
		protectedDeclarations = newProtectedDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS, oldProtectedDeclarations, protectedDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPublicDeclarations() {
		return publicDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublicDeclarations(String newPublicDeclarations) {
		String oldPublicDeclarations = publicDeclarations;
		publicDeclarations = newPublicDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS, oldPublicDeclarations, publicDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS:
				if (resolve) return getBase_Class();
				return basicGetBase_Class();
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				return getPrivateDeclarations();
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				return getProtectedDeclarations();
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				return getPublicDeclarations();
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
			case RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				setPrivateDeclarations((String)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				setProtectedDeclarations((String)newValue);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				setPublicDeclarations((String)newValue);
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
			case RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)null);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				setPrivateDeclarations(PRIVATE_DECLARATIONS_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				setProtectedDeclarations(PROTECTED_DECLARATIONS_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				setPublicDeclarations(PUBLIC_DECLARATIONS_EDEFAULT);
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
			case RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS:
				return base_Class != null;
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				return PRIVATE_DECLARATIONS_EDEFAULT == null ? privateDeclarations != null : !PRIVATE_DECLARATIONS_EDEFAULT.equals(privateDeclarations);
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				return PROTECTED_DECLARATIONS_EDEFAULT == null ? protectedDeclarations != null : !PROTECTED_DECLARATIONS_EDEFAULT.equals(protectedDeclarations);
			case RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				return PUBLIC_DECLARATIONS_EDEFAULT == null ? publicDeclarations != null : !PUBLIC_DECLARATIONS_EDEFAULT.equals(publicDeclarations);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (privateDeclarations: ");
		result.append(privateDeclarations);
		result.append(", protectedDeclarations: ");
		result.append(protectedDeclarations);
		result.append(", publicDeclarations: ");
		result.append(publicDeclarations);
		result.append(')');
		return result.toString();
	}

} //ClassPropertiesImpl
