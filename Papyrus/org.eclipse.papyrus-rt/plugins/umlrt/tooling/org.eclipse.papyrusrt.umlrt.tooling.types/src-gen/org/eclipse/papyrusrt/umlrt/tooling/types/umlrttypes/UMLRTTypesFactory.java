/**
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage
 * @generated
 */
public interface UMLRTTypesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	UMLRTTypesFactory eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTTypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>UMLRT New Element Configurator</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>UMLRT New Element Configurator</em>'.
	 * @generated
	 */
	UMLRTNewElementConfigurator createUMLRTNewElementConfigurator();

	/**
	 * Returns a new object of class '<em>UMLRT Set Type Advice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>UMLRT Set Type Advice Configuration</em>'.
	 * @generated
	 */
	UMLRTSetTypeAdviceConfiguration createUMLRTSetTypeAdviceConfiguration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLRTTypesPackage getUMLRTTypesPackage();

} // UMLRTTypesFactory
