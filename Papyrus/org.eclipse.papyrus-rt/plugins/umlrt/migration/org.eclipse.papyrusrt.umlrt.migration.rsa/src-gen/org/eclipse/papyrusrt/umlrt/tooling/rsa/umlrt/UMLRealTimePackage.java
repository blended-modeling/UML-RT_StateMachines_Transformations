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
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimeFactory
 * @model kind="package"
 * @generated
 */
public interface UMLRealTimePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "umlrt";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/profile/umlrt";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "UMLRealTime";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	UMLRealTimePackage eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTStereotypeImpl <em>RT Stereotype</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTStereotypeImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTStereotype()
	 * @generated
	 */
	int RT_STEREOTYPE = 0;

	/**
	 * The number of structural features of the '<em>RT Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_STEREOTYPE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>RT Stereotype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_STEREOTYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.AbstractEventImpl <em>Abstract Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.AbstractEventImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getAbstractEvent()
	 * @generated
	 */
	int ABSTRACT_EVENT = 1;

	/**
	 * The feature id for the '<em><b>Base Call Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EVENT__BASE_CALL_EVENT = 0;

	/**
	 * The number of structural features of the '<em>Abstract Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EVENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EVENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsuleImpl <em>Capsule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsuleImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCapsule()
	 * @generated
	 */
	int CAPSULE = 2;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Capsule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Capsule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsulePartImpl <em>Capsule Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsulePartImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCapsulePart()
	 * @generated
	 */
	int CAPSULE_PART = 3;

	/**
	 * The feature id for the '<em><b>Base Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART__BASE_PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Capsule Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Capsule Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CAPSULE_PART_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CoregionImpl <em>Coregion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CoregionImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCoregion()
	 * @generated
	 */
	int COREGION = 4;

	/**
	 * The number of structural features of the '<em>Coregion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COREGION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Coregion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COREGION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.InEventImpl <em>In Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.InEventImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getInEvent()
	 * @generated
	 */
	int IN_EVENT = 5;

	/**
	 * The feature id for the '<em><b>Base Call Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_EVENT__BASE_CALL_EVENT = ABSTRACT_EVENT__BASE_CALL_EVENT;

	/**
	 * The number of structural features of the '<em>In Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_EVENT_FEATURE_COUNT = ABSTRACT_EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>In Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_EVENT_OPERATION_COUNT = ABSTRACT_EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.OutEventImpl <em>Out Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.OutEventImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getOutEvent()
	 * @generated
	 */
	int OUT_EVENT = 6;

	/**
	 * The feature id for the '<em><b>Base Call Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OUT_EVENT__BASE_CALL_EVENT = ABSTRACT_EVENT__BASE_CALL_EVENT;

	/**
	 * The number of structural features of the '<em>Out Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OUT_EVENT_FEATURE_COUNT = ABSTRACT_EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Out Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OUT_EVENT_OPERATION_COUNT = ABSTRACT_EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolImpl <em>Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getProtocol()
	 * @generated
	 */
	int PROTOCOL = 7;

	/**
	 * The feature id for the '<em><b>Base Collaboration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__BASE_COLLABORATION = 0;

	/**
	 * The number of structural features of the '<em>Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolContainerImpl <em>Protocol Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolContainerImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getProtocolContainer()
	 * @generated
	 */
	int PROTOCOL_CONTAINER = 8;

	/**
	 * The feature id for the '<em><b>Base Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CONTAINER__BASE_PACKAGE = 0;

	/**
	 * The number of structural features of the '<em>Protocol Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Protocol Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTConnectorImpl <em>RT Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTConnectorImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTConnector()
	 * @generated
	 */
	int RT_CONNECTOR = 9;

	/**
	 * The feature id for the '<em><b>Base Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_CONNECTOR__BASE_CONNECTOR = 0;

	/**
	 * The number of structural features of the '<em>RT Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_CONNECTOR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>RT Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_CONNECTOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl <em>RT Historystate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTHistorystate()
	 * @generated
	 */
	int RT_HISTORYSTATE = 10;

	/**
	 * The feature id for the '<em><b>Base Pseudostate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_HISTORYSTATE__BASE_PSEUDOSTATE = 0;

	/**
	 * The feature id for the '<em><b>History Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_HISTORYSTATE__HISTORY_KIND = 1;

	/**
	 * The number of structural features of the '<em>RT Historystate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_HISTORYSTATE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>RT Historystate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_HISTORYSTATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl <em>RT Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTPort()
	 * @generated
	 */
	int RT_PORT = 11;

	/**
	 * The feature id for the '<em><b>Base Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__BASE_PORT = 0;

	/**
	 * The feature id for the '<em><b>Is Conjugate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__IS_CONJUGATE = 1;

	/**
	 * The feature id for the '<em><b>Is Notification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__IS_NOTIFICATION = 2;

	/**
	 * The feature id for the '<em><b>Is Publish</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__IS_PUBLISH = 3;

	/**
	 * The feature id for the '<em><b>Is Wired</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__IS_WIRED = 4;

	/**
	 * The feature id for the '<em><b>Registration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__REGISTRATION = 5;

	/**
	 * The feature id for the '<em><b>Registration Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT__REGISTRATION_OVERRIDE = 6;

	/**
	 * The number of structural features of the '<em>RT Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>RT Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_PORT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl <em>RT Redefinable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTRedefinableElement()
	 * @generated
	 */
	int RT_REDEFINABLE_ELEMENT = 12;

	/**
	 * The feature id for the '<em><b>Base Redefinable Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Root Fragment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT = 1;

	/**
	 * The number of structural features of the '<em>RT Redefinable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_REDEFINABLE_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>RT Redefinable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_REDEFINABLE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.TriggerImpl <em>Trigger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.TriggerImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getTrigger()
	 * @generated
	 */
	int TRIGGER = 13;

	/**
	 * The feature id for the '<em><b>Base Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER__BASE_OPERATION = 0;

	/**
	 * The number of structural features of the '<em>Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRIGGER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl <em>RT Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTMessage()
	 * @generated
	 */
	int RT_MESSAGE = 14;

	/**
	 * The feature id for the '<em><b>Receive Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_MESSAGE__RECEIVE_PORT = 0;

	/**
	 * The feature id for the '<em><b>Send Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_MESSAGE__SEND_PORT = 1;

	/**
	 * The feature id for the '<em><b>Base Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_MESSAGE__BASE_MESSAGE = 2;

	/**
	 * The number of structural features of the '<em>RT Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_MESSAGE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>RT Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RT_MESSAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType <em>Port Registration Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getPortRegistrationType()
	 * @generated
	 */
	int PORT_REGISTRATION_TYPE = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind <em>History Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getHistoryKind()
	 * @generated
	 */
	int HISTORY_KIND = 16;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype <em>RT Stereotype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Stereotype</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype
	 * @generated
	 */
	EClass getRTStereotype();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent <em>Abstract Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Abstract Event</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent
	 * @generated
	 */
	EClass getAbstractEvent();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent#getBase_CallEvent <em>Base Call Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Call Event</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent#getBase_CallEvent()
	 * @see #getAbstractEvent()
	 * @generated
	 */
	EReference getAbstractEvent_Base_CallEvent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule <em>Capsule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Capsule</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule
	 * @generated
	 */
	EClass getCapsule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule#getBase_Class()
	 * @see #getCapsule()
	 * @generated
	 */
	EReference getCapsule_Base_Class();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart <em>Capsule Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Capsule Part</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart
	 * @generated
	 */
	EClass getCapsulePart();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart#getBase_Property <em>Base Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Property</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart#getBase_Property()
	 * @see #getCapsulePart()
	 * @generated
	 */
	EReference getCapsulePart_Base_Property();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Coregion <em>Coregion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Coregion</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Coregion
	 * @generated
	 */
	EClass getCoregion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.InEvent <em>In Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>In Event</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.InEvent
	 * @generated
	 */
	EClass getInEvent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.OutEvent <em>Out Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Out Event</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.OutEvent
	 * @generated
	 */
	EClass getOutEvent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Protocol</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol
	 * @generated
	 */
	EClass getProtocol();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol#getBase_Collaboration <em>Base Collaboration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Collaboration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol#getBase_Collaboration()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Base_Collaboration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer <em>Protocol Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Protocol Container</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer
	 * @generated
	 */
	EClass getProtocolContainer();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer#getBase_Package <em>Base Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Package</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer#getBase_Package()
	 * @see #getProtocolContainer()
	 * @generated
	 */
	EReference getProtocolContainer_Base_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector <em>RT Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector
	 * @generated
	 */
	EClass getRTConnector();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector#getBase_Connector <em>Base Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Connector</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector#getBase_Connector()
	 * @see #getRTConnector()
	 * @generated
	 */
	EReference getRTConnector_Base_Connector();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate <em>RT Historystate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Historystate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate
	 * @generated
	 */
	EClass getRTHistorystate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getBase_Pseudostate <em>Base Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Pseudostate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getBase_Pseudostate()
	 * @see #getRTHistorystate()
	 * @generated
	 */
	EReference getRTHistorystate_Base_Pseudostate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getHistoryKind <em>History Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>History Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate#getHistoryKind()
	 * @see #getRTHistorystate()
	 * @generated
	 */
	EAttribute getRTHistorystate_HistoryKind();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort <em>RT Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort
	 * @generated
	 */
	EClass getRTPort();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getBase_Port <em>Base Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getBase_Port()
	 * @see #getRTPort()
	 * @generated
	 */
	EReference getRTPort_Base_Port();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsConjugate <em>Is Conjugate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Conjugate</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsConjugate()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_IsConjugate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsNotification <em>Is Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Notification</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsNotification()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_IsNotification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsPublish <em>Is Publish</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Publish</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsPublish()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_IsPublish();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsWired <em>Is Wired</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Wired</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#isIsWired()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_IsWired();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistration <em>Registration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Registration</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistration()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_Registration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistrationOverride <em>Registration Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Registration Override</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort#getRegistrationOverride()
	 * @see #getRTPort()
	 * @generated
	 */
	EAttribute getRTPort_RegistrationOverride();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement <em>RT Redefinable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Redefinable Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement
	 * @generated
	 */
	EClass getRTRedefinableElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getBase_RedefinableElement <em>Base Redefinable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Redefinable Element</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getBase_RedefinableElement()
	 * @see #getRTRedefinableElement()
	 * @generated
	 */
	EReference getRTRedefinableElement_Base_RedefinableElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getRootFragment <em>Root Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Root Fragment</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement#getRootFragment()
	 * @see #getRTRedefinableElement()
	 * @generated
	 */
	EReference getRTRedefinableElement_RootFragment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Trigger</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger
	 * @generated
	 */
	EClass getTrigger();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger#getBase_Operation <em>Base Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Operation</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger#getBase_Operation()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_Base_Operation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage <em>RT Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>RT Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage
	 * @generated
	 */
	EClass getRTMessage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getReceivePort <em>Receive Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Receive Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getReceivePort()
	 * @see #getRTMessage()
	 * @generated
	 */
	EAttribute getRTMessage_ReceivePort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getSendPort <em>Send Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Send Port</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getSendPort()
	 * @see #getRTMessage()
	 * @generated
	 */
	EAttribute getRTMessage_SendPort();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getBase_Message <em>Base Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Base Message</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage#getBase_Message()
	 * @see #getRTMessage()
	 * @generated
	 */
	EReference getRTMessage_Base_Message();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType <em>Port Registration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Port Registration Type</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType
	 * @generated
	 */
	EEnum getPortRegistrationType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind <em>History Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>History Kind</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind
	 * @generated
	 */
	EEnum getHistoryKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLRealTimeFactory getUMLRealTimeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTStereotypeImpl <em>RT Stereotype</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTStereotypeImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTStereotype()
		 * @generated
		 */
		EClass RT_STEREOTYPE = eINSTANCE.getRTStereotype();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.AbstractEventImpl <em>Abstract Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.AbstractEventImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getAbstractEvent()
		 * @generated
		 */
		EClass ABSTRACT_EVENT = eINSTANCE.getAbstractEvent();

		/**
		 * The meta object literal for the '<em><b>Base Call Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ABSTRACT_EVENT__BASE_CALL_EVENT = eINSTANCE.getAbstractEvent_Base_CallEvent();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsuleImpl <em>Capsule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsuleImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCapsule()
		 * @generated
		 */
		EClass CAPSULE = eINSTANCE.getCapsule();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE__BASE_CLASS = eINSTANCE.getCapsule_Base_Class();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsulePartImpl <em>Capsule Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CapsulePartImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCapsulePart()
		 * @generated
		 */
		EClass CAPSULE_PART = eINSTANCE.getCapsulePart();

		/**
		 * The meta object literal for the '<em><b>Base Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CAPSULE_PART__BASE_PROPERTY = eINSTANCE.getCapsulePart_Base_Property();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CoregionImpl <em>Coregion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.CoregionImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getCoregion()
		 * @generated
		 */
		EClass COREGION = eINSTANCE.getCoregion();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.InEventImpl <em>In Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.InEventImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getInEvent()
		 * @generated
		 */
		EClass IN_EVENT = eINSTANCE.getInEvent();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.OutEventImpl <em>Out Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.OutEventImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getOutEvent()
		 * @generated
		 */
		EClass OUT_EVENT = eINSTANCE.getOutEvent();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolImpl <em>Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getProtocol()
		 * @generated
		 */
		EClass PROTOCOL = eINSTANCE.getProtocol();

		/**
		 * The meta object literal for the '<em><b>Base Collaboration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL__BASE_COLLABORATION = eINSTANCE.getProtocol_Base_Collaboration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolContainerImpl <em>Protocol Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.ProtocolContainerImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getProtocolContainer()
		 * @generated
		 */
		EClass PROTOCOL_CONTAINER = eINSTANCE.getProtocolContainer();

		/**
		 * The meta object literal for the '<em><b>Base Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference PROTOCOL_CONTAINER__BASE_PACKAGE = eINSTANCE.getProtocolContainer_Base_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTConnectorImpl <em>RT Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTConnectorImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTConnector()
		 * @generated
		 */
		EClass RT_CONNECTOR = eINSTANCE.getRTConnector();

		/**
		 * The meta object literal for the '<em><b>Base Connector</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_CONNECTOR__BASE_CONNECTOR = eINSTANCE.getRTConnector_Base_Connector();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl <em>RT Historystate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTHistorystate()
		 * @generated
		 */
		EClass RT_HISTORYSTATE = eINSTANCE.getRTHistorystate();

		/**
		 * The meta object literal for the '<em><b>Base Pseudostate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_HISTORYSTATE__BASE_PSEUDOSTATE = eINSTANCE.getRTHistorystate_Base_Pseudostate();

		/**
		 * The meta object literal for the '<em><b>History Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_HISTORYSTATE__HISTORY_KIND = eINSTANCE.getRTHistorystate_HistoryKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl <em>RT Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTPort()
		 * @generated
		 */
		EClass RT_PORT = eINSTANCE.getRTPort();

		/**
		 * The meta object literal for the '<em><b>Base Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_PORT__BASE_PORT = eINSTANCE.getRTPort_Base_Port();

		/**
		 * The meta object literal for the '<em><b>Is Conjugate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__IS_CONJUGATE = eINSTANCE.getRTPort_IsConjugate();

		/**
		 * The meta object literal for the '<em><b>Is Notification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__IS_NOTIFICATION = eINSTANCE.getRTPort_IsNotification();

		/**
		 * The meta object literal for the '<em><b>Is Publish</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__IS_PUBLISH = eINSTANCE.getRTPort_IsPublish();

		/**
		 * The meta object literal for the '<em><b>Is Wired</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__IS_WIRED = eINSTANCE.getRTPort_IsWired();

		/**
		 * The meta object literal for the '<em><b>Registration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__REGISTRATION = eINSTANCE.getRTPort_Registration();

		/**
		 * The meta object literal for the '<em><b>Registration Override</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_PORT__REGISTRATION_OVERRIDE = eINSTANCE.getRTPort_RegistrationOverride();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl <em>RT Redefinable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTRedefinableElement()
		 * @generated
		 */
		EClass RT_REDEFINABLE_ELEMENT = eINSTANCE.getRTRedefinableElement();

		/**
		 * The meta object literal for the '<em><b>Base Redefinable Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT = eINSTANCE.getRTRedefinableElement_Base_RedefinableElement();

		/**
		 * The meta object literal for the '<em><b>Root Fragment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT = eINSTANCE.getRTRedefinableElement_RootFragment();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.TriggerImpl <em>Trigger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.TriggerImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getTrigger()
		 * @generated
		 */
		EClass TRIGGER = eINSTANCE.getTrigger();

		/**
		 * The meta object literal for the '<em><b>Base Operation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRIGGER__BASE_OPERATION = eINSTANCE.getTrigger_Base_Operation();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl <em>RT Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTMessageImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getRTMessage()
		 * @generated
		 */
		EClass RT_MESSAGE = eINSTANCE.getRTMessage();

		/**
		 * The meta object literal for the '<em><b>Receive Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_MESSAGE__RECEIVE_PORT = eINSTANCE.getRTMessage_ReceivePort();

		/**
		 * The meta object literal for the '<em><b>Send Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RT_MESSAGE__SEND_PORT = eINSTANCE.getRTMessage_SendPort();

		/**
		 * The meta object literal for the '<em><b>Base Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RT_MESSAGE__BASE_MESSAGE = eINSTANCE.getRTMessage_Base_Message();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType <em>Port Registration Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getPortRegistrationType()
		 * @generated
		 */
		EEnum PORT_REGISTRATION_TYPE = eINSTANCE.getPortRegistrationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind <em>History Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind
		 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimePackageImpl#getHistoryKind()
		 * @generated
		 */
		EEnum HISTORY_KIND = eINSTANCE.getHistoryKind();

	}

} // UMLRealTimePackage
