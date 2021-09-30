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
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTSClassifierProperties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RTS Classifier Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getCopyFunctionBody <em>Copy Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getDecodeFunctionBody <em>Decode Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getDestroyFunctionBody <em>Destroy Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getEncodeFunctionBody <em>Encode Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getInitFunctionBody <em>Init Function Body</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.RTSClassifierPropertiesImpl#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTSClassifierPropertiesImpl extends MinimalEObjectImpl.Container implements RTSClassifierProperties {
	/**
	 * The default value of the '{@link #getCopyFunctionBody() <em>Copy Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String COPY_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCopyFunctionBody() <em>Copy Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String copyFunctionBody = COPY_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDecodeFunctionBody() <em>Decode Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecodeFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String DECODE_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDecodeFunctionBody() <em>Decode Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecodeFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String decodeFunctionBody = DECODE_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDestroyFunctionBody() <em>Destroy Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestroyFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String DESTROY_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDestroyFunctionBody() <em>Destroy Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestroyFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String destroyFunctionBody = DESTROY_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getEncodeFunctionBody() <em>Encode Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodeFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String ENCODE_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEncodeFunctionBody() <em>Encode Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncodeFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String encodeFunctionBody = ENCODE_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitFunctionBody() <em>Init Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected static final String INIT_FUNCTION_BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitFunctionBody() <em>Init Function Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitFunctionBody()
	 * @generated
	 * @ordered
	 */
	protected String initFunctionBody = INIT_FUNCTION_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected int version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTSClassifierPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.RTS_CLASSIFIER_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCopyFunctionBody() {
		return copyFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyFunctionBody(String newCopyFunctionBody) {
		String oldCopyFunctionBody = copyFunctionBody;
		copyFunctionBody = newCopyFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY, oldCopyFunctionBody, copyFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDecodeFunctionBody() {
		return decodeFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecodeFunctionBody(String newDecodeFunctionBody) {
		String oldDecodeFunctionBody = decodeFunctionBody;
		decodeFunctionBody = newDecodeFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY, oldDecodeFunctionBody, decodeFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDestroyFunctionBody() {
		return destroyFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestroyFunctionBody(String newDestroyFunctionBody) {
		String oldDestroyFunctionBody = destroyFunctionBody;
		destroyFunctionBody = newDestroyFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY, oldDestroyFunctionBody, destroyFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEncodeFunctionBody() {
		return encodeFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncodeFunctionBody(String newEncodeFunctionBody) {
		String oldEncodeFunctionBody = encodeFunctionBody;
		encodeFunctionBody = newEncodeFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY, oldEncodeFunctionBody, encodeFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitFunctionBody() {
		return initFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitFunctionBody(String newInitFunctionBody) {
		String oldInitFunctionBody = initFunctionBody;
		initFunctionBody = newInitFunctionBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY, oldInitFunctionBody, initFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(int newVersion) {
		int oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY:
				return getCopyFunctionBody();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY:
				return getDecodeFunctionBody();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY:
				return getDestroyFunctionBody();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY:
				return getEncodeFunctionBody();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY:
				return getInitFunctionBody();
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__VERSION:
				return getVersion();
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
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY:
				setCopyFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY:
				setDecodeFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY:
				setDestroyFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY:
				setEncodeFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY:
				setInitFunctionBody((String)newValue);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__VERSION:
				setVersion((Integer)newValue);
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
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY:
				setCopyFunctionBody(COPY_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY:
				setDecodeFunctionBody(DECODE_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY:
				setDestroyFunctionBody(DESTROY_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY:
				setEncodeFunctionBody(ENCODE_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY:
				setInitFunctionBody(INIT_FUNCTION_BODY_EDEFAULT);
				return;
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__COPY_FUNCTION_BODY:
				return COPY_FUNCTION_BODY_EDEFAULT == null ? copyFunctionBody != null : !COPY_FUNCTION_BODY_EDEFAULT.equals(copyFunctionBody);
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DECODE_FUNCTION_BODY:
				return DECODE_FUNCTION_BODY_EDEFAULT == null ? decodeFunctionBody != null : !DECODE_FUNCTION_BODY_EDEFAULT.equals(decodeFunctionBody);
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__DESTROY_FUNCTION_BODY:
				return DESTROY_FUNCTION_BODY_EDEFAULT == null ? destroyFunctionBody != null : !DESTROY_FUNCTION_BODY_EDEFAULT.equals(destroyFunctionBody);
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__ENCODE_FUNCTION_BODY:
				return ENCODE_FUNCTION_BODY_EDEFAULT == null ? encodeFunctionBody != null : !ENCODE_FUNCTION_BODY_EDEFAULT.equals(encodeFunctionBody);
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__INIT_FUNCTION_BODY:
				return INIT_FUNCTION_BODY_EDEFAULT == null ? initFunctionBody != null : !INIT_FUNCTION_BODY_EDEFAULT.equals(initFunctionBody);
			case RTCppPropertiesPackage.RTS_CLASSIFIER_PROPERTIES__VERSION:
				return version != VERSION_EDEFAULT;
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
		result.append(" (copyFunctionBody: ");
		result.append(copyFunctionBody);
		result.append(", decodeFunctionBody: ");
		result.append(decodeFunctionBody);
		result.append(", destroyFunctionBody: ");
		result.append(destroyFunctionBody);
		result.append(", encodeFunctionBody: ");
		result.append(encodeFunctionBody);
		result.append(", initFunctionBody: ");
		result.append(initFunctionBody);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //RTSClassifierPropertiesImpl
