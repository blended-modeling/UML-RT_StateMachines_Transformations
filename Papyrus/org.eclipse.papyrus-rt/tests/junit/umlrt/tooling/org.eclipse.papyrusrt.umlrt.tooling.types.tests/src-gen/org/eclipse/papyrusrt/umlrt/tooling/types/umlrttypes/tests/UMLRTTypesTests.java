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
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test suite for the '<em><b>umlrttypes</b></em>'
 * package. <!-- end-user-doc -->
 *
 * @generated
 */
public class UMLRTTypesTests extends TestSuite {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new UMLRTTypesTests("umlrttypes Tests"); //$NON-NLS-1$
		suite.addTestSuite(UMLRTSetTypeAdviceConfigurationTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTTypesTests(String name) {
		super(name);
	}

} // UMLRTTypesTests
