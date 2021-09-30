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

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cpp File Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl#getHeaderPreface <em>Header Preface</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl#getHeaderEnding <em>Header Ending</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl#getImplementationPreface <em>Implementation Preface</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CppFilePropertiesImpl#getImplementationEnding <em>Implementation Ending</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CppFilePropertiesImpl extends MinimalEObjectImpl.Container implements CppFileProperties {
	/**
	 * The default value of the '{@link #getHeaderPreface() <em>Header Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderPreface()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_PREFACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderPreface() <em>Header Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderPreface()
	 * @generated
	 * @ordered
	 */
	protected String headerPreface = HEADER_PREFACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaderEnding() <em>Header Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderEnding()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_ENDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderEnding() <em>Header Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderEnding()
	 * @generated
	 * @ordered
	 */
	protected String headerEnding = HEADER_ENDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationPreface() <em>Implementation Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPreface()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_PREFACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationPreface() <em>Implementation Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPreface()
	 * @generated
	 * @ordered
	 */
	protected String implementationPreface = IMPLEMENTATION_PREFACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationEnding() <em>Implementation Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationEnding()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_ENDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationEnding() <em>Implementation Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationEnding()
	 * @generated
	 * @ordered
	 */
	protected String implementationEnding = IMPLEMENTATION_ENDING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CppFilePropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.CPP_FILE_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderPreface() {
		return headerPreface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderPreface(String newHeaderPreface) {
		String oldHeaderPreface = headerPreface;
		headerPreface = newHeaderPreface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE, oldHeaderPreface, headerPreface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderEnding() {
		return headerEnding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderEnding(String newHeaderEnding) {
		String oldHeaderEnding = headerEnding;
		headerEnding = newHeaderEnding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING, oldHeaderEnding, headerEnding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationPreface() {
		return implementationPreface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationPreface(String newImplementationPreface) {
		String oldImplementationPreface = implementationPreface;
		implementationPreface = newImplementationPreface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE, oldImplementationPreface, implementationPreface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationEnding() {
		return implementationEnding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationEnding(String newImplementationEnding) {
		String oldImplementationEnding = implementationEnding;
		implementationEnding = newImplementationEnding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING, oldImplementationEnding, implementationEnding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE:
				return getHeaderPreface();
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING:
				return getHeaderEnding();
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE:
				return getImplementationPreface();
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING:
				return getImplementationEnding();
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
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE:
				setHeaderPreface((String)newValue);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING:
				setHeaderEnding((String)newValue);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE:
				setImplementationPreface((String)newValue);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING:
				setImplementationEnding((String)newValue);
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
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE:
				setHeaderPreface(HEADER_PREFACE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING:
				setHeaderEnding(HEADER_ENDING_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE:
				setImplementationPreface(IMPLEMENTATION_PREFACE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING:
				setImplementationEnding(IMPLEMENTATION_ENDING_EDEFAULT);
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
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE:
				return HEADER_PREFACE_EDEFAULT == null ? headerPreface != null : !HEADER_PREFACE_EDEFAULT.equals(headerPreface);
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING:
				return HEADER_ENDING_EDEFAULT == null ? headerEnding != null : !HEADER_ENDING_EDEFAULT.equals(headerEnding);
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE:
				return IMPLEMENTATION_PREFACE_EDEFAULT == null ? implementationPreface != null : !IMPLEMENTATION_PREFACE_EDEFAULT.equals(implementationPreface);
			case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING:
				return IMPLEMENTATION_ENDING_EDEFAULT == null ? implementationEnding != null : !IMPLEMENTATION_ENDING_EDEFAULT.equals(implementationEnding);
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
		result.append(" (headerPreface: ");
		result.append(headerPreface);
		result.append(", headerEnding: ");
		result.append(headerEnding);
		result.append(", implementationPreface: ");
		result.append(implementationPreface);
		result.append(", implementationEnding: ");
		result.append(implementationEnding);
		result.append(')');
		return result.toString();
	}

} //CppFilePropertiesImpl
