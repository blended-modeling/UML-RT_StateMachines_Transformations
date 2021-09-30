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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Descriptor Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSDescriptorPropertiesImpl#isGenerateDescriptor <em>Generate Descriptor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSDescriptorPropertiesImpl extends MinimalEObjectImpl.Container implements RTSDescriptorProperties {
	/**
	 * The default value of the '{@link #isGenerateDescriptor() <em>Generate Descriptor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDescriptor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DESCRIPTOR_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateDescriptor() <em>Generate Descriptor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDescriptor()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDescriptor = GENERATE_DESCRIPTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSDescriptorPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_DESCRIPTOR_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDescriptor() {
		return generateDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDescriptor(boolean newGenerateDescriptor) {
		boolean oldGenerateDescriptor = generateDescriptor;
		generateDescriptor = newGenerateDescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR, oldGenerateDescriptor, generateDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR:
				return isGenerateDescriptor();
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
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor((Boolean)newValue);
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
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor(GENERATE_DESCRIPTOR_EDEFAULT);
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
			case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR:
				return generateDescriptor != GENERATE_DESCRIPTOR_EDEFAULT;
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
		result.append(" (generateDescriptor: ");
		result.append(generateDescriptor);
		result.append(')');
		return result.toString();
	}

} //RTSDescriptorPropertiesImpl
