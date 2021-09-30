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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRealTimeFactoryImpl extends EFactoryImpl implements UMLRealTimeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static UMLRealTimeFactory init() {
		try {
			UMLRealTimeFactory theUMLRealTimeFactory = (UMLRealTimeFactory) EPackage.Registry.INSTANCE.getEFactory(UMLRealTimePackage.eNS_URI);
			if (theUMLRealTimeFactory != null) {
				return theUMLRealTimeFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRealTimeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRealTimeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UMLRealTimePackage.CAPSULE:
			return createCapsule();
		case UMLRealTimePackage.CAPSULE_PART:
			return createCapsulePart();
		case UMLRealTimePackage.COREGION:
			return createCoregion();
		case UMLRealTimePackage.IN_EVENT:
			return createInEvent();
		case UMLRealTimePackage.OUT_EVENT:
			return createOutEvent();
		case UMLRealTimePackage.PROTOCOL:
			return createProtocol();
		case UMLRealTimePackage.PROTOCOL_CONTAINER:
			return createProtocolContainer();
		case UMLRealTimePackage.RT_CONNECTOR:
			return createRTConnector();
		case UMLRealTimePackage.RT_HISTORYSTATE:
			return createRTHistorystate();
		case UMLRealTimePackage.RT_PORT:
			return createRTPort();
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT:
			return createRTRedefinableElement();
		case UMLRealTimePackage.TRIGGER:
			return createTrigger();
		case UMLRealTimePackage.RT_MESSAGE:
			return createRTMessage();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case UMLRealTimePackage.PORT_REGISTRATION_TYPE:
			return createPortRegistrationTypeFromString(eDataType, initialValue);
		case UMLRealTimePackage.HISTORY_KIND:
			return createHistoryKindFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case UMLRealTimePackage.PORT_REGISTRATION_TYPE:
			return convertPortRegistrationTypeToString(eDataType, instanceValue);
		case UMLRealTimePackage.HISTORY_KIND:
			return convertHistoryKindToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Capsule createCapsule() {
		CapsuleImpl capsule = new CapsuleImpl();
		return capsule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CapsulePart createCapsulePart() {
		CapsulePartImpl capsulePart = new CapsulePartImpl();
		return capsulePart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Coregion createCoregion() {
		CoregionImpl coregion = new CoregionImpl();
		return coregion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InEvent createInEvent() {
		InEventImpl inEvent = new InEventImpl();
		return inEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public OutEvent createOutEvent() {
		OutEventImpl outEvent = new OutEventImpl();
		return outEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Protocol createProtocol() {
		ProtocolImpl protocol = new ProtocolImpl();
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ProtocolContainer createProtocolContainer() {
		ProtocolContainerImpl protocolContainer = new ProtocolContainerImpl();
		return protocolContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RTConnector createRTConnector() {
		RTConnectorImpl rtConnector = new RTConnectorImpl();
		return rtConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RTHistorystate createRTHistorystate() {
		RTHistorystateImpl rtHistorystate = new RTHistorystateImpl();
		return rtHistorystate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RTPort createRTPort() {
		RTPortImpl rtPort = new RTPortImpl();
		return rtPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RTRedefinableElement createRTRedefinableElement() {
		RTRedefinableElementImpl rtRedefinableElement = new RTRedefinableElementImpl();
		return rtRedefinableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Trigger createTrigger() {
		TriggerImpl trigger = new TriggerImpl();
		return trigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RTMessage createRTMessage() {
		RTMessageImpl rtMessage = new RTMessageImpl();
		return rtMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public PortRegistrationType createPortRegistrationTypeFromString(EDataType eDataType, String initialValue) {
		PortRegistrationType result = PortRegistrationType.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertPortRegistrationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public HistoryKind createHistoryKindFromString(EDataType eDataType, String initialValue) {
		HistoryKind result = HistoryKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertHistoryKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRealTimePackage getUMLRealTimePackage() {
		return (UMLRealTimePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRealTimePackage getPackage() {
		return UMLRealTimePackage.eINSTANCE;
	}

} // UMLRealTimeFactoryImpl
