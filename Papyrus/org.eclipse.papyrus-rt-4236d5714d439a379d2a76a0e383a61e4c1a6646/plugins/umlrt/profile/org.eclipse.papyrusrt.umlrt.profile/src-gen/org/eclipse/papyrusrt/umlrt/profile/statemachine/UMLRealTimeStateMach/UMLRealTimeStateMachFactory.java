/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.UMLRealTimeStateMachPackage
 * @generated
 */
public interface UMLRealTimeStateMachFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UMLRealTimeStateMachFactory eINSTANCE = org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRealTimeStateMach.impl.UMLRealTimeStateMachFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>RT State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT State Machine</em>'.
	 * @generated
	 */
	RTStateMachine createRTStateMachine();

	/**
	 * Returns a new object of class '<em>RT Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT Region</em>'.
	 * @generated
	 */
	RTRegion createRTRegion();

	/**
	 * Returns a new object of class '<em>RT State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT State</em>'.
	 * @generated
	 */
	RTState createRTState();

	/**
	 * Returns a new object of class '<em>RT Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT Pseudostate</em>'.
	 * @generated
	 */
	RTPseudostate createRTPseudostate();

	/**
	 * Returns a new object of class '<em>RT Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT Trigger</em>'.
	 * @generated
	 */
	RTTrigger createRTTrigger();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLRealTimeStateMachPackage getUMLRealTimeStateMachPackage();

} //UMLRealTimeStateMachFactory
