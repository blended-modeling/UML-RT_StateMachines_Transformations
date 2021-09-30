/**
 * Copyright (c) 2016 CEA LIST and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrusrt.umlrt.system.profile.systemelements;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemElementsPackage
 * @generated
 */
public interface SystemElementsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SystemElementsFactory eINSTANCE = org.eclipse.papyrusrt.umlrt.system.profile.systemelements.impl.SystemElementsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>System Protocol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>System Protocol</em>'.
	 * @generated
	 */
	SystemProtocol createSystemProtocol();

	/**
	 * Returns a new object of class '<em>Base Protocol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base Protocol</em>'.
	 * @generated
	 */
	BaseProtocol createBaseProtocol();

	/**
	 * Returns a new object of class '<em>System Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>System Class</em>'.
	 * @generated
	 */
	SystemClass createSystemClass();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SystemElementsPackage getSystemElementsPackage();

} //SystemElementsFactory
