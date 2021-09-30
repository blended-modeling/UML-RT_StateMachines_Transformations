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
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSAttributeProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSDescriptorProperties;

import org.eclipse.uml2.uml.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Attribute Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl#isGenerateDescriptor <em>Generate Descriptor</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSAttributePropertiesImpl#getBase_Property <em>Base Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSAttributePropertiesImpl extends RTSAbstractAttributePropertiesImpl implements RTSAttributeProperties {
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
	 * The cached value of the '{@link #getBase_Property() <em>Base Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Property()
	 * @generated
	 * @ordered
	 */
	protected Property base_Property;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSAttributePropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_ATTRIBUTE_PROPERTIES;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR, oldGenerateDescriptor, generateDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getBase_Property() {
		if (base_Property != null && base_Property.eIsProxy()) {
			InternalEObject oldBase_Property = (InternalEObject)base_Property;
			base_Property = (Property)eResolveProxy(oldBase_Property);
			if (base_Property != oldBase_Property) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY, oldBase_Property, base_Property));
			}
		}
		return base_Property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetBase_Property() {
		return base_Property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Property(Property newBase_Property) {
		Property oldBase_Property = base_Property;
		base_Property = newBase_Property;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY, oldBase_Property, base_Property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR:
				return isGenerateDescriptor();
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
				if (resolve) return getBase_Property();
				return basicGetBase_Property();
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
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
				setBase_Property((Property)newValue);
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
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR:
				setGenerateDescriptor(GENERATE_DESCRIPTOR_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
				setBase_Property((Property)null);
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
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR:
				return generateDescriptor != GENERATE_DESCRIPTOR_EDEFAULT;
			case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
				return base_Property != null;
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
				case RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR;
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
				case RTCppPropertiesPackage.RTS_DESCRIPTOR_PROPERTIES__GENERATE_DESCRIPTOR: return RTCppPropertiesPackage.RTS_ATTRIBUTE_PROPERTIES__GENERATE_DESCRIPTOR;
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

} //RTSAttributePropertiesImpl
