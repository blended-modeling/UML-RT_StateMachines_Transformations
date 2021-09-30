/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRTUMLRTFactoryImpl extends EFactoryImpl implements UMLRTUMLRTFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static UMLRTUMLRTFactory init() {
		try {
			UMLRTUMLRTFactory theUMLRTFactory = (UMLRTUMLRTFactory) EPackage.Registry.INSTANCE.getEFactory(UMLRTUMLRTPackage.eNS_URI);
			if (theUMLRTFactory != null) {
				return theUMLRTFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UMLRTUMLRTFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTUMLRTFactoryImpl() {
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
		case UMLRTUMLRTPackage.PACKAGE:
			return (EObject) createPackage();
		case UMLRTUMLRTPackage.CAPSULE:
			return (EObject) createCapsule();
		case UMLRTUMLRTPackage.PORT:
			return (EObject) createPort();
		case UMLRTUMLRTPackage.PROTOCOL:
			return (EObject) createProtocol();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE:
			return (EObject) createProtocolMessage();
		case UMLRTUMLRTPackage.CAPSULE_PART:
			return (EObject) createCapsulePart();
		case UMLRTUMLRTPackage.CONNECTOR:
			return (EObject) createConnector();
		case UMLRTUMLRTPackage.STATE_MACHINE:
			return (EObject) createStateMachine();
		case UMLRTUMLRTPackage.STATE:
			return (EObject) createState();
		case UMLRTUMLRTPackage.TRANSITION:
			return (EObject) createTransition();
		case UMLRTUMLRTPackage.TRIGGER:
			return (EObject) createTrigger();
		case UMLRTUMLRTPackage.GUARD:
			return (EObject) createGuard();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR:
			return (EObject) createOpaqueBehavior();
		case UMLRTUMLRTPackage.CONNECTION_POINT:
			return (EObject) createConnectionPoint();
		case UMLRTUMLRTPackage.PSEUDOSTATE:
			return (EObject) createPseudostate();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
		case UMLRTUMLRTPackage.UMLRT_INHERITANCE_KIND:
			return createUMLRTInheritanceKindFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.UMLRT_PORT_KIND:
			return createUMLRTPortKindFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.UMLRT_CAPSULE_PART_KIND:
			return createUMLRTCapsulePartKindFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.UMLRT_CONNECTION_POINT_KIND:
			return createUMLRTConnectionPointKindFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.UMLRT_PSEUDOSTATE_KIND:
			return createUMLRTPseudostateKindFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.MODEL:
			return createModelFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.STREAM:
			return createStreamFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.LIST:
			return createListFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.MAP:
			return createMapFromString(eDataType, initialValue);
		case UMLRTUMLRTPackage.ILLEGAL_STATE_EXCEPTION:
			return createIllegalStateExceptionFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
		case UMLRTUMLRTPackage.UMLRT_INHERITANCE_KIND:
			return convertUMLRTInheritanceKindToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.UMLRT_PORT_KIND:
			return convertUMLRTPortKindToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.UMLRT_CAPSULE_PART_KIND:
			return convertUMLRTCapsulePartKindToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.UMLRT_CONNECTION_POINT_KIND:
			return convertUMLRTConnectionPointKindToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.UMLRT_PSEUDOSTATE_KIND:
			return convertUMLRTPseudostateKindToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.MODEL:
			return convertModelToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.STREAM:
			return convertStreamToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.LIST:
			return convertListToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.MAP:
			return convertMapToString(eDataType, instanceValue);
		case UMLRTUMLRTPackage.ILLEGAL_STATE_EXCEPTION:
			return convertIllegalStateExceptionToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPackage createPackage() {
		UMLRTPackageImpl package_ = new UMLRTPackageImpl();
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsule createCapsule() {
		UMLRTCapsuleImpl capsule = new UMLRTCapsuleImpl();
		return capsule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocol createProtocol() {
		UMLRTProtocolImpl protocol = new UMLRTProtocolImpl();
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPort createPort() {
		UMLRTPortImpl port = new UMLRTPortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsulePart createCapsulePart() {
		UMLRTCapsulePartImpl capsulePart = new UMLRTCapsulePartImpl();
		return capsulePart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector createConnector() {
		UMLRTConnectorImpl connector = new UMLRTConnectorImpl();
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTStateMachine createStateMachine() {
		UMLRTStateMachineImpl stateMachine = new UMLRTStateMachineImpl();
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTState createState() {
		UMLRTStateImpl state = new UMLRTStateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTransition createTransition() {
		UMLRTTransitionImpl transition = new UMLRTTransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTrigger createTrigger() {
		UMLRTTriggerImpl trigger = new UMLRTTriggerImpl();
		return trigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTGuard createGuard() {
		UMLRTGuardImpl guard = new UMLRTGuardImpl();
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTOpaqueBehavior createOpaqueBehavior() {
		UMLRTOpaqueBehaviorImpl opaqueBehavior = new UMLRTOpaqueBehaviorImpl();
		return opaqueBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnectionPoint createConnectionPoint() {
		UMLRTConnectionPointImpl connectionPoint = new UMLRTConnectionPointImpl();
		return connectionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPseudostate createPseudostate() {
		UMLRTPseudostateImpl pseudostate = new UMLRTPseudostateImpl();
		return pseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage createProtocolMessage() {
		UMLRTProtocolMessageImpl protocolMessage = new UMLRTProtocolMessageImpl();
		return protocolMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTInheritanceKind createUMLRTInheritanceKindFromString(EDataType eDataType, String initialValue) {
		UMLRTInheritanceKind result = UMLRTInheritanceKind.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertUMLRTInheritanceKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTPortKind createUMLRTPortKindFromString(EDataType eDataType, String initialValue) {
		UMLRTPortKind result = UMLRTPortKind.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertUMLRTPortKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTCapsulePartKind createUMLRTCapsulePartKindFromString(EDataType eDataType, String initialValue) {
		UMLRTCapsulePartKind result = UMLRTCapsulePartKind.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertUMLRTCapsulePartKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTConnectionPointKind createUMLRTConnectionPointKindFromString(EDataType eDataType, String initialValue) {
		UMLRTConnectionPointKind result = UMLRTConnectionPointKind.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertUMLRTConnectionPointKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTPseudostateKind createUMLRTPseudostateKindFromString(EDataType eDataType, String initialValue) {
		UMLRTPseudostateKind result = UMLRTPseudostateKind.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertUMLRTPseudostateKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTModel createModelFromString(EDataType eDataType, String initialValue) {
		return (UMLRTModel) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertModelToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public List<?> createListFromString(EDataType eDataType, String initialValue) {
		return (List<?>) super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Stream<?> createStreamFromString(EDataType eDataType, String initialValue) {
		return (Stream<?>) super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertStreamToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Map<?, ?> createMapFromString(EDataType eDataType, String initialValue) {
		return (Map<?, ?>) super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertMapToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IllegalStateException createIllegalStateExceptionFromString(EDataType eDataType, String initialValue) {
		return (IllegalStateException) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertIllegalStateExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTUMLRTPackage getUMLRTPackage() {
		return (UMLRTUMLRTPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UMLRTUMLRTPackage getPackage() {
		return UMLRTUMLRTPackage.eINSTANCE;
	}

} // UMLRTUMLRTFactoryImpl
