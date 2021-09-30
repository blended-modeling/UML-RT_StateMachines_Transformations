/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;
import org.eclipse.uml2.uml.Message;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl#getReceivePort <em>Receive Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl#getSendPort <em>Send Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl#getBase_Message <em>Base Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTMessageImpl extends MinimalEObjectImpl.Container implements RTMessage {
	/**
	 * The default value of the '{@link #getReceivePort() <em>Receive Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReceivePort()
	 * @generated
	 * @ordered
	 */
	protected static final String RECEIVE_PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReceivePort() <em>Receive Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReceivePort()
	 * @generated
	 * @ordered
	 */
	protected String receivePort = RECEIVE_PORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSendPort() <em>Send Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSendPort()
	 * @generated
	 * @ordered
	 */
	protected static final String SEND_PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSendPort() <em>Send Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSendPort()
	 * @generated
	 * @ordered
	 */
	protected String sendPort = SEND_PORT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBase_Message() <em>Base Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBase_Message()
	 * @generated
	 * @ordered
	 */
	protected Message base_Message;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RTMessageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRealTimePackage.Literals.RT_MESSAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getReceivePort() {
		return receivePort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setReceivePort(String newReceivePort) {
		String oldReceivePort = receivePort;
		receivePort = newReceivePort;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_MESSAGE__RECEIVE_PORT, oldReceivePort, receivePort));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getSendPort() {
		return sendPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSendPort(String newSendPort) {
		String oldSendPort = sendPort;
		sendPort = newSendPort;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_MESSAGE__SEND_PORT, oldSendPort, sendPort));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Message getBase_Message() {
		if (base_Message != null && base_Message.eIsProxy()) {
			InternalEObject oldBase_Message = (InternalEObject) base_Message;
			base_Message = (Message) eResolveProxy(oldBase_Message);
			if (base_Message != oldBase_Message) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE, oldBase_Message, base_Message));
				}
			}
		}
		return base_Message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Message basicGetBase_Message() {
		return base_Message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBase_Message(Message newBase_Message) {
		Message oldBase_Message = base_Message;
		base_Message = newBase_Message;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE, oldBase_Message, base_Message));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRealTimePackage.RT_MESSAGE__RECEIVE_PORT:
			return getReceivePort();
		case UMLRealTimePackage.RT_MESSAGE__SEND_PORT:
			return getSendPort();
		case UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE:
			if (resolve) {
				return getBase_Message();
			}
			return basicGetBase_Message();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRealTimePackage.RT_MESSAGE__RECEIVE_PORT:
			setReceivePort((String) newValue);
			return;
		case UMLRealTimePackage.RT_MESSAGE__SEND_PORT:
			setSendPort((String) newValue);
			return;
		case UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE:
			setBase_Message((Message) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_MESSAGE__RECEIVE_PORT:
			setReceivePort(RECEIVE_PORT_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_MESSAGE__SEND_PORT:
			setSendPort(SEND_PORT_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE:
			setBase_Message((Message) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_MESSAGE__RECEIVE_PORT:
			return RECEIVE_PORT_EDEFAULT == null ? receivePort != null : !RECEIVE_PORT_EDEFAULT.equals(receivePort);
		case UMLRealTimePackage.RT_MESSAGE__SEND_PORT:
			return SEND_PORT_EDEFAULT == null ? sendPort != null : !SEND_PORT_EDEFAULT.equals(sendPort);
		case UMLRealTimePackage.RT_MESSAGE__BASE_MESSAGE:
			return base_Message != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (receivePort: ");
		result.append(receivePort);
		result.append(", sendPort: ");
		result.append(sendPort);
		result.append(')');
		return result.toString();
	}

} // RTMessageImpl
