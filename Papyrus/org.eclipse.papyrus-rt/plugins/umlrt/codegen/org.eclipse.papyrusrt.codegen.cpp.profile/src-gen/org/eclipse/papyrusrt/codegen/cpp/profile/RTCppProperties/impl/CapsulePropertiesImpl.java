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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CapsuleProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capsule Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl#isGenerateHeader <em>Generate Header</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.CapsulePropertiesImpl#isGenerateImplementation <em>Generate Implementation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CapsulePropertiesImpl extends ClassPropertiesImpl implements CapsuleProperties {
	/**
	 * The default value of the '{@link #isGenerateHeader() <em>Generate Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_HEADER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateHeader() <em>Generate Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean generateHeader = GENERATE_HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateImplementation() <em>Generate Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_IMPLEMENTATION_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateImplementation() <em>Generate Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateImplementation()
	 * @generated
	 * @ordered
	 */
	protected boolean generateImplementation = GENERATE_IMPLEMENTATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CapsulePropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.CAPSULE_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateHeader() {
		return generateHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateHeader(boolean newGenerateHeader) {
		boolean oldGenerateHeader = generateHeader;
		generateHeader = newGenerateHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER, oldGenerateHeader, generateHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateImplementation() {
		return generateImplementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateImplementation(boolean newGenerateImplementation) {
		boolean oldGenerateImplementation = generateImplementation;
		generateImplementation = newGenerateImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION, oldGenerateImplementation, generateImplementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER:
				return isGenerateHeader();
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION:
				return isGenerateImplementation();
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
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER:
				setGenerateHeader((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION:
				setGenerateImplementation((Boolean)newValue);
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
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER:
				setGenerateHeader(GENERATE_HEADER_EDEFAULT);
				return;
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION:
				setGenerateImplementation(GENERATE_IMPLEMENTATION_EDEFAULT);
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
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER:
				return generateHeader != GENERATE_HEADER_EDEFAULT;
			case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION:
				return generateImplementation != GENERATE_IMPLEMENTATION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == FileGenerationProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER: return RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_HEADER;
				case RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION: return RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == FileGenerationProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_HEADER: return RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_HEADER;
				case RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION: return RTCppPropertiesPackage.CAPSULE_PROPERTIES__GENERATE_IMPLEMENTATION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (generateHeader: ");
		result.append(generateHeader);
		result.append(", generateImplementation: ");
		result.append(generateImplementation);
		result.append(')');
		return result.toString();
	}

} //CapsulePropertiesImpl
