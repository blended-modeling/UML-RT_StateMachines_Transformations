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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSEnumerationLiteralProperties;

import org.eclipse.uml2.uml.EnumerationLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Enumeration Literal Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl#isGenerateDescriptor <em>Generate Descriptor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSEnumerationLiteralPropertiesImpl#getBase_EnumerationLiteral <em>Base Enumeration Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSEnumerationLiteralPropertiesImpl extends RTSAbstractAttributePropertiesImpl implements RTSEnumerationLiteralProperties {
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
	 * The cached value of the '{@link #getBase_EnumerationLiteral() <em>Base Enumeration Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_EnumerationLiteral()
	 * @generated
	 * @ordered
	 */
	protected EnumerationLiteral base_EnumerationLiteral;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSEnumerationLiteralPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_ENUMERATION_LITERAL_PROPERTIES;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR, oldGenerateDescriptor, generateDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumerationLiteral getBase_EnumerationLiteral() {
		if (base_EnumerationLiteral != null && base_EnumerationLiteral.eIsProxy()) {
			InternalEObject oldBase_EnumerationLiteral = (InternalEObject)base_EnumerationLiteral;
			base_EnumerationLiteral = (EnumerationLiteral)eResolveProxy(oldBase_EnumerationLiteral);
			if (base_EnumerationLiteral != oldBase_EnumerationLiteral) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL, oldBase_EnumerationLiteral, base_EnumerationLiteral));
			}
		}
		return base_EnumerationLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumerationLiteral basicGetBase_EnumerationLiteral() {
		return base_EnumerationLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_EnumerationLiteral(EnumerationLiteral newBase_EnumerationLiteral) {
		EnumerationLiteral oldBase_EnumerationLiteral = base_EnumerationLiteral;
		base_EnumerationLiteral = newBase_EnumerationLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL, oldBase_EnumerationLiteral, base_EnumerationLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR:
				return isGenerateDescriptor();
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL:
				if (resolve) return getBase_EnumerationLiteral();
				return basicGetBase_EnumerationLiteral();
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
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL:
				setBase_EnumerationLiteral((EnumerationLiteral)newValue);
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
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor(GENERATE_DESCRIPTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL:
				setBase_EnumerationLiteral((EnumerationLiteral)null);
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
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR:
				return generateDescriptor != GENERATE_DESCRIPTOR_EDEFAULT;
			case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__BASE_ENUMERATION_LITERAL:
				return base_EnumerationLiteral != null;
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
		if (baseClass == RTSDescriptorProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR;
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
		if (baseClass == RTSDescriptorProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_ENUMERATION_LITERAL_PROPERTIES__GENERATE_DESCRIPTOR;
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
		result.append(" (generateDescriptor: ");
		result.append(generateDescriptor);
		result.append(')');
		return result.toString();
	}

} //RTSEnumerationLiteralPropertiesImpl
