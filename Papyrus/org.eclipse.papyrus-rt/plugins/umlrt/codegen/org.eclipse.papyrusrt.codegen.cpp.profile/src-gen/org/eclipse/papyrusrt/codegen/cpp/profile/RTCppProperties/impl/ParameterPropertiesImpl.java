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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ParameterProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

import org.eclipse.uml2.uml.Parameter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl#getBase_Parameter <em>Base Parameter</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl#isPointsToType <em>Points To Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl#isPointsToConst <em>Points To Const</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ParameterPropertiesImpl#isPointsToVolatile <em>Points To Volatile</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterPropertiesImpl extends MinimalEObjectImpl.Container implements ParameterProperties {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = "";

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
	 * The cached value of the '{@link #getBase_Parameter() <em>Base Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Parameter()
	 * @generated
	 * @ordered
	 */
	protected Parameter base_Parameter;

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
	 * The default value of the '{@link #isPointsToConst() <em>Points To Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToConst()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POINTS_TO_CONST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPointsToConst() <em>Points To Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToConst()
	 * @generated
	 * @ordered
	 */
	protected boolean pointsToConst = POINTS_TO_CONST_EDEFAULT;

	/**
	 * The default value of the '{@link #isPointsToVolatile() <em>Points To Volatile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToVolatile()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POINTS_TO_VOLATILE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPointsToVolatile() <em>Points To Volatile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPointsToVolatile()
	 * @generated
	 * @ordered
	 */
	protected boolean pointsToVolatile = POINTS_TO_VOLATILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.PARAMETER_PROPERTIES;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PARAMETER_PROPERTIES__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getBase_Parameter() {
		if (base_Parameter != null && base_Parameter.eIsProxy()) {
			InternalEObject oldBase_Parameter = (InternalEObject)base_Parameter;
			base_Parameter = (Parameter)eResolveProxy(oldBase_Parameter);
			if (base_Parameter != oldBase_Parameter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER, oldBase_Parameter, base_Parameter));
			}
		}
		return base_Parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter basicGetBase_Parameter() {
		return base_Parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Parameter(Parameter newBase_Parameter) {
		Parameter oldBase_Parameter = base_Parameter;
		base_Parameter = newBase_Parameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER, oldBase_Parameter, base_Parameter));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_TYPE, oldPointsToType, pointsToType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPointsToConst() {
		return pointsToConst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointsToConst(boolean newPointsToConst) {
		boolean oldPointsToConst = pointsToConst;
		pointsToConst = newPointsToConst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_CONST, oldPointsToConst, pointsToConst));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPointsToVolatile() {
		return pointsToVolatile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointsToVolatile(boolean newPointsToVolatile) {
		boolean oldPointsToVolatile = pointsToVolatile;
		pointsToVolatile = newPointsToVolatile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_VOLATILE, oldPointsToVolatile, pointsToVolatile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__TYPE:
				return getType();
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER:
				if (resolve) return getBase_Parameter();
				return basicGetBase_Parameter();
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_TYPE:
				return isPointsToType();
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_CONST:
				return isPointsToConst();
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_VOLATILE:
				return isPointsToVolatile();
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
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__TYPE:
				setType((String)newValue);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER:
				setBase_Parameter((Parameter)newValue);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_TYPE:
				setPointsToType((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_CONST:
				setPointsToConst((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_VOLATILE:
				setPointsToVolatile((Boolean)newValue);
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
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER:
				setBase_Parameter((Parameter)null);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_TYPE:
				setPointsToType(POINTS_TO_TYPE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_CONST:
				setPointsToConst(POINTS_TO_CONST_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_VOLATILE:
				setPointsToVolatile(POINTS_TO_VOLATILE_EDEFAULT);
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
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__BASE_PARAMETER:
				return base_Parameter != null;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_TYPE:
				return pointsToType != POINTS_TO_TYPE_EDEFAULT;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_CONST:
				return pointsToConst != POINTS_TO_CONST_EDEFAULT;
			case RTCppPropertiesPackage.PARAMETER_PROPERTIES__POINTS_TO_VOLATILE:
				return pointsToVolatile != POINTS_TO_VOLATILE_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", pointsToType: ");
		result.append(pointsToType);
		result.append(", pointsToConst: ");
		result.append(pointsToConst);
		result.append(", pointsToVolatile: ");
		result.append(pointsToVolatile);
		result.append(')');
		return result.toString();
	}

} //ParameterPropertiesImpl
