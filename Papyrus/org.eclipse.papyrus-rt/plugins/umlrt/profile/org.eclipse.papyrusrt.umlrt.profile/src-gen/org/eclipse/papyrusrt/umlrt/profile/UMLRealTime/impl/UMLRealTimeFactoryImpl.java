/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UMLRealTimeFactoryImpl extends EFactoryImpl implements UMLRealTimeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UMLRealTimeFactory init() {
		try {
			UMLRealTimeFactory theUMLRealTimeFactory = (UMLRealTimeFactory)EPackage.Registry.INSTANCE.getEFactory(UMLRealTimePackage.eNS_URI);
			if (theUMLRealTimeFactory != null) {
				return theUMLRealTimeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRealTimeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRealTimeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case UMLRealTimePackage.CAPSULE: return createCapsule();
			case UMLRealTimePackage.CAPSULE_PART: return createCapsulePart();
			case UMLRealTimePackage.PROTOCOL: return createProtocol();
			case UMLRealTimePackage.RT_PORT: return createRTPort();
			case UMLRealTimePackage.RT_CONNECTOR: return createRTConnector();
			case UMLRealTimePackage.PROTOCOL_CONTAINER: return createProtocolContainer();
			case UMLRealTimePackage.RT_REDEFINED_ELEMENT: return createRTRedefinedElement();
			case UMLRealTimePackage.RT_MESSAGE_SET: return createRTMessageSet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case UMLRealTimePackage.PORT_REGISTRATION_TYPE:
				return createPortRegistrationTypeFromString(eDataType, initialValue);
			case UMLRealTimePackage.RT_MESSAGE_KIND:
				return createRTMessageKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case UMLRealTimePackage.PORT_REGISTRATION_TYPE:
				return convertPortRegistrationTypeToString(eDataType, instanceValue);
			case UMLRealTimePackage.RT_MESSAGE_KIND:
				return convertRTMessageKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Capsule createCapsule() {
		CapsuleImpl capsule = new CapsuleImpl();
		return capsule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapsulePart createCapsulePart() {
		CapsulePartImpl capsulePart = new CapsulePartImpl();
		return capsulePart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Protocol createProtocol() {
		ProtocolImpl protocol = new ProtocolImpl();
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTPort createRTPort() {
		RTPortImpl rtPort = new RTPortImpl();
		return rtPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTConnector createRTConnector() {
		RTConnectorImpl rtConnector = new RTConnectorImpl();
		return rtConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolContainer createProtocolContainer() {
		ProtocolContainerImpl protocolContainer = new ProtocolContainerImpl();
		return protocolContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTRedefinedElement createRTRedefinedElement() {
		RTRedefinedElementImpl rtRedefinedElement = new RTRedefinedElementImpl();
		return rtRedefinedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTMessageSet createRTMessageSet() {
		RTMessageSetImpl rtMessageSet = new RTMessageSetImpl();
		return rtMessageSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortRegistrationType createPortRegistrationTypeFromString(EDataType eDataType, String initialValue) {
		PortRegistrationType result = PortRegistrationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPortRegistrationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTMessageKind createRTMessageKindFromString(EDataType eDataType, String initialValue) {
		RTMessageKind result = RTMessageKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTMessageKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRealTimePackage getUMLRealTimePackage() {
		return (UMLRealTimePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRealTimePackage getPackage() {
		return UMLRealTimePackage.eINSTANCE;
	}

} //UMLRealTimeFactoryImpl
