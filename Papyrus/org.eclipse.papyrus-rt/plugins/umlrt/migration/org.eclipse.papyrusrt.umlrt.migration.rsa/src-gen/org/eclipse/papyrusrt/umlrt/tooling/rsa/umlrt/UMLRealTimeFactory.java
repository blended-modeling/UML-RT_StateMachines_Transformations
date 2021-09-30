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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage
 * @generated
 */
public interface UMLRealTimeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	UMLRealTimeFactory eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.UMLRealTimeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Capsule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Capsule</em>'.
	 * @generated
	 */
	Capsule createCapsule();

	/**
	 * Returns a new object of class '<em>Capsule Part</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Capsule Part</em>'.
	 * @generated
	 */
	CapsulePart createCapsulePart();

	/**
	 * Returns a new object of class '<em>Coregion</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Coregion</em>'.
	 * @generated
	 */
	Coregion createCoregion();

	/**
	 * Returns a new object of class '<em>In Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>In Event</em>'.
	 * @generated
	 */
	InEvent createInEvent();

	/**
	 * Returns a new object of class '<em>Out Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Out Event</em>'.
	 * @generated
	 */
	OutEvent createOutEvent();

	/**
	 * Returns a new object of class '<em>Protocol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Protocol</em>'.
	 * @generated
	 */
	Protocol createProtocol();

	/**
	 * Returns a new object of class '<em>Protocol Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Protocol Container</em>'.
	 * @generated
	 */
	ProtocolContainer createProtocolContainer();

	/**
	 * Returns a new object of class '<em>RT Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>RT Connector</em>'.
	 * @generated
	 */
	RTConnector createRTConnector();

	/**
	 * Returns a new object of class '<em>RT Historystate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>RT Historystate</em>'.
	 * @generated
	 */
	RTHistorystate createRTHistorystate();

	/**
	 * Returns a new object of class '<em>RT Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>RT Port</em>'.
	 * @generated
	 */
	RTPort createRTPort();

	/**
	 * Returns a new object of class '<em>RT Redefinable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>RT Redefinable Element</em>'.
	 * @generated
	 */
	RTRedefinableElement createRTRedefinableElement();

	/**
	 * Returns a new object of class '<em>Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Trigger</em>'.
	 * @generated
	 */
	Trigger createTrigger();

	/**
	 * Returns a new object of class '<em>RT Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>RT Message</em>'.
	 * @generated
	 */
	RTMessage createRTMessage();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLRealTimePackage getUMLRealTimePackage();

} // UMLRealTimeFactory
