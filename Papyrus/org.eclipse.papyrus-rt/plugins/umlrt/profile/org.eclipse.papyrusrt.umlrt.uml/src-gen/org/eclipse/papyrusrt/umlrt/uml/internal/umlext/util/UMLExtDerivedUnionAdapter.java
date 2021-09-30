/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;

/**
 * <!-- begin-user-doc -->
 * An adapter that propagates notifications for derived unions.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage
 * @generated
 */
public class UMLExtDerivedUnionAdapter extends AdapterImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ExtUMLExtPackage modelPackage;

	/**
	 * Creates an instance of the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLExtDerivedUnionAdapter() {
		if (modelPackage == null) {
			modelPackage = ExtUMLExtPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> with the appropriate model class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
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
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyChanged(Notification notification, EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ExtUMLExtPackage.CLASS:
			notifyClassChanged(notification, eClass);
			break;
		case ExtUMLExtPackage.INTERFACE:
			notifyInterfaceChanged(notification, eClass);
			break;
		case ExtUMLExtPackage.STATE_MACHINE:
			notifyStateMachineChanged(notification, eClass);
			break;
		case ExtUMLExtPackage.REGION:
			notifyRegionChanged(notification, eClass);
			break;
		case ExtUMLExtPackage.TRANSITION:
			notifyTransitionChanged(notification, eClass);
			break;
		case ExtUMLExtPackage.STATE:
			notifyStateChanged(notification, eClass);
			break;
		}
	}

	/**
	 * Does nothing; clients may override so that it does something.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @param derivedUnion the derived union affected by the change.
	 * @generated
	 */
	public void notifyChanged(Notification notification, EClass eClass, EStructuralFeature derivedUnion) {
		// Do nothing.
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyClassChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtClass.class)) {
		case ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_ATTRIBUTE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_CONNECTOR:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_PORT:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyInterfaceChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtInterface.class)) {
		case ExtUMLExtPackage.INTERFACE__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.INTERFACE__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.INTERFACE__IMPLICIT_OPERATION:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyStateMachineChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtStateMachine.class)) {
		case ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_REGION:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_CONNECTION_POINT:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyRegionChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtRegion.class)) {
		case ExtUMLExtPackage.REGION__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.REGION__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyTransitionChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtTransition.class)) {
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

	/**
	 * Calls <code>notifyChanged</code> for each affected derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param notification a description of the change.
	 * @param eClass the Ecore class of the notifier.
	 * @generated
	 */
	protected void notifyStateChanged(Notification notification, EClass eClass) {
		switch (notification.getFeatureID(ExtState.class)) {
		case ExtUMLExtPackage.STATE__IMPLICIT_OWNED_MEMBER:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE__IMPLICIT_OWNED_RULE:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			notifyChanged(notification, eClass, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			break;
		}
	}

} //UMLExtDerivedUnionAdapter
