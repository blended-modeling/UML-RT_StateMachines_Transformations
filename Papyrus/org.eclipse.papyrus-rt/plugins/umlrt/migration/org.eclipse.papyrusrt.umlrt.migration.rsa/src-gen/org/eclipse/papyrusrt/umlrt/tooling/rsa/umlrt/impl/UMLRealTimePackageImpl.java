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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.AbstractEvent;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Capsule;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.CapsulePart;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Coregion;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.InEvent;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.OutEvent;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Protocol;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.ProtocolContainer;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTConnector;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTMessage;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTStereotype;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.Trigger;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimeFactory;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRealTimePackageImpl extends EPackageImpl implements UMLRealTimePackage {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtStereotypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass abstractEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass capsuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass capsulePartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass coregionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass inEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass outEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass protocolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass protocolContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtHistorystateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtRedefinableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass triggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rtMessageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum portRegistrationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum historyKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLRealTimePackageImpl() {
		super(eNS_URI, UMLRealTimeFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link UMLRealTimePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLRealTimePackage init() {
		if (isInited) {
			return (UMLRealTimePackage) EPackage.Registry.INSTANCE.getEPackage(UMLRealTimePackage.eNS_URI);
		}

		// Obtain or create and register package
		UMLRealTimePackageImpl theUMLRealTimePackage = (UMLRealTimePackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UMLRealTimePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UMLRealTimePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLRealTimePackage.createPackageContents();

		// Initialize created meta-data
		theUMLRealTimePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLRealTimePackage.freeze();


		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLRealTimePackage.eNS_URI, theUMLRealTimePackage);
		return theUMLRealTimePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTStereotype() {
		return rtStereotypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAbstractEvent() {
		return abstractEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAbstractEvent_Base_CallEvent() {
		return (EReference) abstractEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCapsule() {
		return capsuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsule_Base_Class() {
		return (EReference) capsuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCapsulePart() {
		return capsulePartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCapsulePart_Base_Property() {
		return (EReference) capsulePartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCoregion() {
		return coregionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getInEvent() {
		return inEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getOutEvent() {
		return outEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getProtocol() {
		return protocolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocol_Base_Collaboration() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getProtocolContainer() {
		return protocolContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getProtocolContainer_Base_Package() {
		return (EReference) protocolContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTConnector() {
		return rtConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTConnector_Base_Connector() {
		return (EReference) rtConnectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTHistorystate() {
		return rtHistorystateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTHistorystate_Base_Pseudostate() {
		return (EReference) rtHistorystateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTHistorystate_HistoryKind() {
		return (EAttribute) rtHistorystateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTPort() {
		return rtPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTPort_Base_Port() {
		return (EReference) rtPortEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_IsConjugate() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_IsNotification() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_IsPublish() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_IsWired() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_Registration() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTPort_RegistrationOverride() {
		return (EAttribute) rtPortEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTRedefinableElement() {
		return rtRedefinableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTRedefinableElement_Base_RedefinableElement() {
		return (EReference) rtRedefinableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTRedefinableElement_RootFragment() {
		return (EReference) rtRedefinableElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTrigger() {
		return triggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTrigger_Base_Operation() {
		return (EReference) triggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRTMessage() {
		return rtMessageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTMessage_ReceivePort() {
		return (EAttribute) rtMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRTMessage_SendPort() {
		return (EAttribute) rtMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRTMessage_Base_Message() {
		return (EReference) rtMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getPortRegistrationType() {
		return portRegistrationTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getHistoryKind() {
		return historyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRealTimeFactory getUMLRealTimeFactory() {
		return (UMLRealTimeFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		rtStereotypeEClass = createEClass(RT_STEREOTYPE);

		abstractEventEClass = createEClass(ABSTRACT_EVENT);
		createEReference(abstractEventEClass, ABSTRACT_EVENT__BASE_CALL_EVENT);

		capsuleEClass = createEClass(CAPSULE);
		createEReference(capsuleEClass, CAPSULE__BASE_CLASS);

		capsulePartEClass = createEClass(CAPSULE_PART);
		createEReference(capsulePartEClass, CAPSULE_PART__BASE_PROPERTY);

		coregionEClass = createEClass(COREGION);

		inEventEClass = createEClass(IN_EVENT);

		outEventEClass = createEClass(OUT_EVENT);

		protocolEClass = createEClass(PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__BASE_COLLABORATION);

		protocolContainerEClass = createEClass(PROTOCOL_CONTAINER);
		createEReference(protocolContainerEClass, PROTOCOL_CONTAINER__BASE_PACKAGE);

		rtConnectorEClass = createEClass(RT_CONNECTOR);
		createEReference(rtConnectorEClass, RT_CONNECTOR__BASE_CONNECTOR);

		rtHistorystateEClass = createEClass(RT_HISTORYSTATE);
		createEReference(rtHistorystateEClass, RT_HISTORYSTATE__BASE_PSEUDOSTATE);
		createEAttribute(rtHistorystateEClass, RT_HISTORYSTATE__HISTORY_KIND);

		rtPortEClass = createEClass(RT_PORT);
		createEReference(rtPortEClass, RT_PORT__BASE_PORT);
		createEAttribute(rtPortEClass, RT_PORT__IS_CONJUGATE);
		createEAttribute(rtPortEClass, RT_PORT__IS_NOTIFICATION);
		createEAttribute(rtPortEClass, RT_PORT__IS_PUBLISH);
		createEAttribute(rtPortEClass, RT_PORT__IS_WIRED);
		createEAttribute(rtPortEClass, RT_PORT__REGISTRATION);
		createEAttribute(rtPortEClass, RT_PORT__REGISTRATION_OVERRIDE);

		rtRedefinableElementEClass = createEClass(RT_REDEFINABLE_ELEMENT);
		createEReference(rtRedefinableElementEClass, RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT);
		createEReference(rtRedefinableElementEClass, RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT);

		triggerEClass = createEClass(TRIGGER);
		createEReference(triggerEClass, TRIGGER__BASE_OPERATION);

		rtMessageEClass = createEClass(RT_MESSAGE);
		createEAttribute(rtMessageEClass, RT_MESSAGE__RECEIVE_PORT);
		createEAttribute(rtMessageEClass, RT_MESSAGE__SEND_PORT);
		createEReference(rtMessageEClass, RT_MESSAGE__BASE_MESSAGE);

		// Create enums
		portRegistrationTypeEEnum = createEEnum(PORT_REGISTRATION_TYPE);
		historyKindEEnum = createEEnum(HISTORY_KIND);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UMLPackage theUMLPackage = (UMLPackage) EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		inEventEClass.getESuperTypes().add(this.getAbstractEvent());
		outEventEClass.getESuperTypes().add(this.getAbstractEvent());

		// Initialize classes, features, and operations; add parameters
		initEClass(rtStereotypeEClass, RTStereotype.class, "RTStereotype", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(abstractEventEClass, AbstractEvent.class, "AbstractEvent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractEvent_Base_CallEvent(), theUMLPackage.getCallEvent(), null, "base_CallEvent", null, 1, 1, AbstractEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(capsuleEClass, Capsule.class, "Capsule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCapsule_Base_Class(), theUMLPackage.getClass_(), null, "base_Class", null, 1, 1, Capsule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(capsulePartEClass, CapsulePart.class, "CapsulePart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCapsulePart_Base_Property(), theUMLPackage.getProperty(), null, "base_Property", null, 1, 1, CapsulePart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(coregionEClass, Coregion.class, "Coregion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(inEventEClass, InEvent.class, "InEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(outEventEClass, OutEvent.class, "OutEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(protocolEClass, Protocol.class, "Protocol", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProtocol_Base_Collaboration(), theUMLPackage.getCollaboration(), null, "base_Collaboration", null, 1, 1, Protocol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(protocolContainerEClass, ProtocolContainer.class, "ProtocolContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProtocolContainer_Base_Package(), theUMLPackage.getPackage(), null, "base_Package", null, 1, 1, ProtocolContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(rtConnectorEClass, RTConnector.class, "RTConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTConnector_Base_Connector(), theUMLPackage.getConnector(), null, "base_Connector", null, 1, 1, RTConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(rtHistorystateEClass, RTHistorystate.class, "RTHistorystate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTHistorystate_Base_Pseudostate(), theUMLPackage.getPseudostate(), null, "base_Pseudostate", null, 1, 1, RTHistorystate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTHistorystate_HistoryKind(), this.getHistoryKind(), "historyKind", "Deep", 0, 1, RTHistorystate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rtPortEClass, RTPort.class, "RTPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTPort_Base_Port(), theUMLPackage.getPort(), null, "base_Port", null, 1, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_IsConjugate(), ecorePackage.getEBoolean(), "isConjugate", null, 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_IsNotification(), ecorePackage.getEBoolean(), "isNotification", null, 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_IsPublish(), ecorePackage.getEBoolean(), "isPublish", null, 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_IsWired(), ecorePackage.getEBoolean(), "isWired", "true", 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_Registration(), this.getPortRegistrationType(), "registration", "Automatic", 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTPort_RegistrationOverride(), theEcorePackage.getEString(), "registrationOverride", null, 0, 1, RTPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rtRedefinableElementEClass, RTRedefinableElement.class, "RTRedefinableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRTRedefinableElement_Base_RedefinableElement(), theUMLPackage.getRedefinableElement(), null, "base_RedefinableElement", null, 1, 1, RTRedefinableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRTRedefinableElement_RootFragment(), theUMLPackage.getRedefinableElement(), null, "rootFragment", null, 0, 1, RTRedefinableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(triggerEClass, Trigger.class, "Trigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrigger_Base_Operation(), theUMLPackage.getOperation(), null, "base_Operation", null, 1, 1, Trigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(rtMessageEClass, RTMessage.class, "RTMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRTMessage_ReceivePort(), theEcorePackage.getEString(), "receivePort", null, 0, 1, RTMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRTMessage_SendPort(), theEcorePackage.getEString(), "sendPort", null, 0, 1, RTMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRTMessage_Base_Message(), theUMLPackage.getMessage(), null, "base_Message", null, 1, 1, RTMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(portRegistrationTypeEEnum, PortRegistrationType.class, "PortRegistrationType");
		addEEnumLiteral(portRegistrationTypeEEnum, PortRegistrationType.AUTOMATIC);
		addEEnumLiteral(portRegistrationTypeEEnum, PortRegistrationType.APPLICATION);
		addEEnumLiteral(portRegistrationTypeEEnum, PortRegistrationType.AUTOMATIC_LOCKED);

		initEEnum(historyKindEEnum, HistoryKind.class, "HistoryKind");
		addEEnumLiteral(historyKindEEnum, HistoryKind.DEEP);
		addEEnumLiteral(historyKindEEnum, HistoryKind.SHALLOW);

		// Create resource
		createResource(eNS_URI);
	}
} // UMLRealTimePackageImpl
