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
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Class Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl#isGenerateDescriptor <em>Generate Descriptor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl#getTypeDescriptorHint <em>Type Descriptor Hint</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassPropertiesImpl#getBase_Class <em>Base Class</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSClassPropertiesImpl extends RTSClassifierPropertiesImpl implements RTSClassProperties {
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
	 * The default value of the '{@link #getTypeDescriptorHint() <em>Type Descriptor Hint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDescriptorHint()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_DESCRIPTOR_HINT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeDescriptorHint() <em>Type Descriptor Hint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDescriptorHint()
	 * @generated
	 * @ordered
	 */
	protected String typeDescriptorHint = TYPE_DESCRIPTOR_HINT_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSClassPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_CLASS_PROPERTIES;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR, oldGenerateDescriptor, generateDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeDescriptorHint() {
		return typeDescriptorHint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeDescriptorHint(String newTypeDescriptorHint) {
		String oldTypeDescriptorHint = typeDescriptorHint;
		typeDescriptorHint = newTypeDescriptorHint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT, oldTypeDescriptorHint, typeDescriptorHint));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR:
				return isGenerateDescriptor();
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT:
				return getTypeDescriptorHint();
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS:
				if (resolve) return getBase_Class();
				return basicGetBase_Class();
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
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT:
				setTypeDescriptorHint((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)newValue);
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
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor(GENERATE_DESCRIPTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT:
				setTypeDescriptorHint(TYPE_DESCRIPTOR_HINT_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)null);
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
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR:
				return generateDescriptor != GENERATE_DESCRIPTOR_EDEFAULT;
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__TYPE_DESCRIPTOR_HINT:
				return TYPE_DESCRIPTOR_HINT_EDEFAULT == null ? typeDescriptorHint != null : !TYPE_DESCRIPTOR_HINT_EDEFAULT.equals(typeDescriptorHint);
			case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__BASE_CLASS:
				return base_Class != null;
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
				case RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR;
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
				case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_CLASS_PROPERTIES__GENERATE_DESCRIPTOR;
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
		result.append(", typeDescriptorHint: ");
		result.append(typeDescriptorHint);
		result.append(')');
		return result.toString();
	}

} //RTSClassPropertiesImpl
