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
package org.eclipse.papyrusrt.umlrt.uml.util;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;

/**
 * <!-- begin-user-doc -->
 * An adapter that propagates notifications for derived unions.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage
 * @generated
 */
public class UMLRTDerivedUnionAdapter extends AdapterImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static UMLRTUMLRTPackage modelPackage;

	/**
	 * Creates an instance of the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTDerivedUnionAdapter() {
		if (modelPackage == null) {
			modelPackage = UMLRTUMLRTPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> with the appropriate model class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();
		if (notifier instanceof EObject) {
			EClass eClass = ((EObject) notifier).eClass();
			if (eClass.eContainer() == modelPackage) {
				notifyChanged(notification, eClass);
			}
		}
	}

	/**
	 * Calls <code>notifyXXXChanged</code> for the corresponding class of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyChanged(Notification notification, EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UMLRTUMLRTPackage.PACKAGE:
			notifyPackageChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.CAPSULE:
			notifyCapsuleChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.PORT:
			notifyPortChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.PROTOCOL:
			notifyProtocolChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE:
			notifyProtocolMessageChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.CAPSULE_PART:
			notifyCapsulePartChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.CONNECTOR:
			notifyConnectorChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.STATE_MACHINE:
			notifyStateMachineChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.STATE:
			notifyStateChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.TRANSITION:
			notifyTransitionChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.TRIGGER:
			notifyTriggerChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.GUARD:
			notifyGuardChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR:
			notifyOpaqueBehaviorChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.CONNECTION_POINT:
			notifyConnectionPointChanged(notification, eClass);
			break;
		case UMLRTUMLRTPackage.PSEUDOSTATE:
			notifyPseudostateChanged(notification, eClass);
			break;
		}
	}

	/**
	 * Does nothing; clients may override so that it does something.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @param derivedUnion
	 *            the derived union affected by the change.
	 * @generated
	 */
	public void notifyChanged(Notification notification, EClass eClass, EStructuralFeature derivedUnion) {
		// Do nothing.
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyPackageChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTPackage.class)) {
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyCapsuleChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTCapsule.class)) {
		case UMLRTUMLRTPackage.CAPSULE__SUPERCLASS:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.CLASSIFIER__GENERAL);
			break;
		case UMLRTUMLRTPackage.CAPSULE__SUBCLASS:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC);
			break;
		case UMLRTUMLRTPackage.CAPSULE__PORT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CAPSULE__CONNECTOR:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CAPSULE__STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyPortChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTPort.class)) {
		case UMLRTUMLRTPackage.PORT__REDEFINED_PORT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PORT__INSIDE_CONNECTOR:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.PORT__CONNECTOR);
			break;
		case UMLRTUMLRTPackage.PORT__OUTSIDE_CONNECTOR:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.PORT__CONNECTOR);
			break;
		case UMLRTUMLRTPackage.PORT__CAPSULE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyProtocolChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTProtocol.class)) {
		case UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.CLASSIFIER__GENERAL);
			break;
		case UMLRTUMLRTPackage.PROTOCOL__SUB_PROTOCOL:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC);
			break;
		case UMLRTUMLRTPackage.PROTOCOL__MESSAGE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyProtocolMessageChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTProtocolMessage.class)) {
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINED_MESSAGE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PROTOCOL:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyConnectorChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTConnector.class)) {
		case UMLRTUMLRTPackage.CONNECTOR__REDEFINED_CONNECTOR:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CONNECTOR__CAPSULE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyStateMachineChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTStateMachine.class)) {
		case UMLRTUMLRTPackage.STATE_MACHINE__VERTEX:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE_MACHINE__TRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE_MACHINE__REDEFINED_STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE_MACHINE__CAPSULE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyStateChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTState.class)) {
		case UMLRTUMLRTPackage.STATE__STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.STATE__REDEFINED_VERTEX:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.STATE__SUBTRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__ENTRY:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__EXIT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__CONNECTION_POINT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__ENTRY_POINT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__EXIT_POINT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__REDEFINED_STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.VERTEX__REDEFINED_VERTEX);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.STATE__SUBVERTEX:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyTransitionChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTTransition.class)) {
		case UMLRTUMLRTPackage.TRANSITION__STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.TRANSITION__TRIGGER:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.TRANSITION__GUARD:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.TRANSITION__REDEFINED_TRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.TRANSITION__EFFECT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			break;
		case UMLRTUMLRTPackage.TRANSITION__STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyTriggerChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTTrigger.class)) {
		case UMLRTUMLRTPackage.TRIGGER__REDEFINED_TRIGGER:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.TRIGGER__TRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyGuardChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTGuard.class)) {
		case UMLRTUMLRTPackage.GUARD__TRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.GUARD__REDEFINED_GUARD:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.GUARD__TRIGGER:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyOpaqueBehaviorChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTOpaqueBehavior.class)) {
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyConnectionPointChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTConnectionPoint.class)) {
		case UMLRTUMLRTPackage.CONNECTION_POINT__STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_VERTEX:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CONNECTION_POINT__STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_CONNECTION_POINT:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.VERTEX__REDEFINED_VERTEX);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyPseudostateChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTPseudostate.class)) {
		case UMLRTUMLRTPackage.PSEUDOSTATE__STATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_VERTEX:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.PSEUDOSTATE__STATE_MACHINE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		case UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_PSEUDOSTATE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.VERTEX__REDEFINED_VERTEX);
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param notification
	 *            a description of the change.
	 * @param eClass
	 *            the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyCapsulePartChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(UMLRTCapsulePart.class)) {
		case UMLRTUMLRTPackage.CAPSULE_PART__REDEFINED_PART:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINED_ELEMENT);
			break;
		case UMLRTUMLRTPackage.CAPSULE_PART__CAPSULE:
			notifyChanged(notification, eClass, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINITION_CONTEXT);
			break;
		}
	}

} // UMLRTDerivedUnionAdapter
