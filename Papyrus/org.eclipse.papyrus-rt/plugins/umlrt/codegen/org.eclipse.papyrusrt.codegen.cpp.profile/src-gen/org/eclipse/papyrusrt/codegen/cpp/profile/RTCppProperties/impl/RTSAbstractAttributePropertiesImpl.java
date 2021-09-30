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
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAbstractAttributeProperties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Abstract Attribute Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl#isGenerateTypeModifier <em>Generate Type Modifier</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl#getNumElementsFunctionBody <em>Num Elements Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAbstractAttributePropertiesImpl#getTypeDescriptor <em>Type Descriptor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSAbstractAttributePropertiesImpl extends MinimalEObjectImpl.Container implements RTSAbstractAttributeProperties {
	/**
	 * The default value of the '{@link #isGenerateTypeModifier() <em>Generate Type Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateTypeModifier()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_TYPE_MODIFIER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateTypeModifier() <em>Generate Type Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateTypeModifier()
	 * @generated
	 * @ordered
	 */
	protected boolean generateTypeModifier = GENERATE_TYPE_MODIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumElementsFunctionBody() <em>Num Elements Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumElementsFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String NUM_ELEMENTS_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumElementsFunctionBody() <em>Num Elements Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumElementsFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String numElementsFunctionBody = NUM_ELEMENTS_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypeDescriptor() <em>Type Descriptor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDescriptor()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_DESCRIPTOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeDescriptor() <em>Type Descriptor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDescriptor()
	 * @generated
	 * @ordered
	 */
	protected String typeDescriptor = TYPE_DESCRIPTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSAbstractAttributePropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateTypeModifier() {
		return generateTypeModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateTypeModifier(boolean newGenerateTypeModifier) {
		boolean oldGenerateTypeModifier = generateTypeModifier;
		generateTypeModifier = newGenerateTypeModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER, oldGenerateTypeModifier, generateTypeModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNumElementsFunctionBody() {
		return numElementsFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumElementsFunctionBody(String newNumElementsFunctionBody) {
		String oldNumElementsFunctionBody = numElementsFunctionBody;
		numElementsFunctionBody = newNumElementsFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY, oldNumElementsFunctionBody, numElementsFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeDescriptor() {
		return typeDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeDescriptor(String newTypeDescriptor) {
		String oldTypeDescriptor = typeDescriptor;
		typeDescriptor = newTypeDescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR, oldTypeDescriptor, typeDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER:
				return isGenerateTypeModifier();
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY:
				return getNumElementsFunctionBody();
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR:
				return getTypeDescriptor();
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
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER:
				setGenerateTypeModifier((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY:
				setNumElementsFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR:
				setTypeDescriptor((String)newValue);
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
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER:
				setGenerateTypeModifier(GENERATE_TYPE_MODIFIER_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY:
				setNumElementsFunctionBody(NUM_ELEMENTS_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR:
				setTypeDescriptor(TYPE_DESCRIPTOR_EDEFAULT);
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
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__GENERATE_TYPE_MODIFIER:
				return generateTypeModifier != GENERATE_TYPE_MODIFIER_EDEFAULT;
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__NUM_ELEMENTS_FUNCTION_BODY:
				return NUM_ELEMENTS_FUNCTION_BODY_EDEFAULT == null ? numElementsFunctionBody != null : !NUM_ELEMENTS_FUNCTION_BODY_EDEFAULT.equals(numElementsFunctionBody);
			case RTCppPropertiesPackage.RTS_ABSTRACT_ATTRIBUTE_PROPERTIES__TYPE_DESCRIPTOR:
				return TYPE_DESCRIPTOR_EDEFAULT == null ? typeDescriptor != null : !TYPE_DESCRIPTOR_EDEFAULT.equals(typeDescriptor);
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
		result.append(" (generateTypeModifier: ");
		result.append(generateTypeModifier);
		result.append(", numElementsFunctionBody: ");
		result.append(numElementsFunctionBody);
		result.append(", typeDescriptor: ");
		result.append(typeDescriptor);
		result.append(')');
		return result.toString();
	}

} //RTSAbstractAttributePropertiesImpl
