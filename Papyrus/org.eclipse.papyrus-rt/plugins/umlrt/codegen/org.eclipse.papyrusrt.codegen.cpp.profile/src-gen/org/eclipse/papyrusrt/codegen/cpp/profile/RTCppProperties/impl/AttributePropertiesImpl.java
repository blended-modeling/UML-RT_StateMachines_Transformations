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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.AttributeProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.InitializationKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

import org.eclipse.uml2.uml.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#isVolatile <em>Volatile</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#getInitialization <em>Initialization</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#getSize <em>Size</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#isPointsToConstType <em>Points To Const Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#isPointsToVolatileType <em>Points To Volatile Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#isPointsToType <em>Points To Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.AttributePropertiesImpl#getBase_Property <em>Base Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributePropertiesImpl extends MinimalEObjectImpl.Container implements AttributeProperties {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final AttributeKind KIND_EDEFAULT = AttributeKind.MEMBER;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected AttributeKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #isVolatile() <em>Volatile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVolatile()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VOLATILE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVolatile() <em>Volatile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVolatile()
	 * @generated
	 * @ordered
	 */
	protected boolean volatile_ = VOLATILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitialization() <em>Initialization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialization()
	 * @generated
	 * @ordered
	 */
	protected static final InitializationKind INITIALIZATION_EDEFAULT = InitializationKind.ASSIGNMENT;

	/**
	 * The cached value of the '{@link #getInitialization() <em>Initialization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialization()
	 * @generated
	 * @ordered
	 */
	protected InitializationKind initialization = INITIALIZATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final String SIZE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected String size = SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPointsToConstType() <em>Points To Const Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToConstType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POINTS_TO_CONST_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPointsToConstType() <em>Points To Const Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToConstType()
	 * @generated
	 * @ordered
	 */
	protected boolean pointsToConstType = POINTS_TO_CONST_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPointsToVolatileType() <em>Points To Volatile Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToVolatileType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POINTS_TO_VOLATILE_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPointsToVolatileType() <em>Points To Volatile Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToVolatileType()
	 * @generated
	 * @ordered
	 */
	protected boolean pointsToVolatileType = POINTS_TO_VOLATILE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPointsToType() <em>Points To Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POINTS_TO_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPointsToType() <em>Points To Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToType()
	 * @generated
	 * @ordered
	 */
	protected boolean pointsToType = POINTS_TO_TYPE_EDEFAULT;

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
	protected AttributePropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.ATTRIBUTE_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(AttributeKind newKind) {
		AttributeKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVolatile() {
		return volatile_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVolatile(boolean newVolatile) {
		boolean oldVolatile = volatile_;
		volatile_ = newVolatile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__VOLATILE, oldVolatile, volatile_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitializationKind getInitialization() {
		return initialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialization(InitializationKind newInitialization) {
		InitializationKind oldInitialization = initialization;
		initialization = newInitialization == null ? INITIALIZATION_EDEFAULT : newInitialization;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__INITIALIZATION, oldInitialization, initialization));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSize(String newSize) {
		String oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__SIZE, oldSize, size));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPointsToConstType() {
		return pointsToConstType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointsToConstType(boolean newPointsToConstType) {
		boolean oldPointsToConstType = pointsToConstType;
		pointsToConstType = newPointsToConstType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE, oldPointsToConstType, pointsToConstType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPointsToVolatileType() {
		return pointsToVolatileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointsToVolatileType(boolean newPointsToVolatileType) {
		boolean oldPointsToVolatileType = pointsToVolatileType;
		pointsToVolatileType = newPointsToVolatileType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE, oldPointsToVolatileType, pointsToVolatileType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPointsToType() {
		return pointsToType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointsToType(boolean newPointsToType) {
		boolean oldPointsToType = pointsToType;
		pointsToType = newPointsToType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE, oldPointsToType, pointsToType));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY, oldBase_Property, base_Property));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY, oldBase_Property, base_Property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__KIND:
				return getKind();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__VOLATILE:
				return isVolatile();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__INITIALIZATION:
				return getInitialization();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__TYPE:
				return getType();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__SIZE:
				return getSize();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE:
				return isPointsToConstType();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE:
				return isPointsToVolatileType();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE:
				return isPointsToType();
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
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
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__KIND:
				setKind((AttributeKind)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__VOLATILE:
				setVolatile((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__INITIALIZATION:
				setInitialization((InitializationKind)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__TYPE:
				setType((String)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__SIZE:
				setSize((String)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE:
				setPointsToConstType((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE:
				setPointsToVolatileType((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE:
				setPointsToType((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
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
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__VOLATILE:
				setVolatile(VOLATILE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__INITIALIZATION:
				setInitialization(INITIALIZATION_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE:
				setPointsToConstType(POINTS_TO_CONST_TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE:
				setPointsToVolatileType(POINTS_TO_VOLATILE_TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE:
				setPointsToType(POINTS_TO_TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
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
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__KIND:
				return kind != KIND_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__VOLATILE:
				return volatile_ != VOLATILE_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__INITIALIZATION:
				return initialization != INITIALIZATION_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_CONST_TYPE:
				return pointsToConstType != POINTS_TO_CONST_TYPE_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_VOLATILE_TYPE:
				return pointsToVolatileType != POINTS_TO_VOLATILE_TYPE_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__POINTS_TO_TYPE:
				return pointsToType != POINTS_TO_TYPE_EDEFAULT;
			case RTCppPropertiesPackage.ATTRIBUTE_PROPERTIES__BASE_PROPERTY:
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (kind: ");
		result.append(kind);
		result.append(", volatile: ");
		result.append(volatile_);
		result.append(", initialization: ");
		result.append(initialization);
		result.append(", type: ");
		result.append(type);
		result.append(", size: ");
		result.append(size);
		result.append(", pointsToConstType: ");
		result.append(pointsToConstType);
		result.append(", pointsToVolatileType: ");
		result.append(pointsToVolatileType);
		result.append(", pointsToType: ");
		result.append(pointsToType);
		result.append(')');
		return result.toString();
	}

} //AttributePropertiesImpl
