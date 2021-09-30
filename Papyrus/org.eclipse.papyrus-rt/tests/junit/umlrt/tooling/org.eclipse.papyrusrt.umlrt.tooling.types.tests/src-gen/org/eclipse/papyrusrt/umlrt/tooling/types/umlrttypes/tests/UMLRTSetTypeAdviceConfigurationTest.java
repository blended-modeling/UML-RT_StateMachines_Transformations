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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceFactory;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.properties.contexts.ContextsFactory;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesFactory;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>UMLRT Set Type Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews() <em>New Type Views</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UMLRTSetTypeAdviceConfigurationTest extends TestCase {

	/**
	 * The fixture for this UMLRT Set Type Advice Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTSetTypeAdviceConfiguration fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UMLRTSetTypeAdviceConfigurationTest.class);
	}

	/**
	 * Constructs a new UMLRT Set Type Advice Configuration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public UMLRTSetTypeAdviceConfigurationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this UMLRT Set Type Advice Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(UMLRTSetTypeAdviceConfiguration fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this UMLRT Set Type Advice Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTSetTypeAdviceConfiguration getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(UMLRTTypesFactory.eINSTANCE.createUMLRTSetTypeAdviceConfiguration());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews() <em>New Type Views</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews()
	 * @generated NOT
	 */
	public void testGetNewTypeViews() {
		// Empty case
		assertThat(getFixture().getNewTypeViews(), not(hasItem(anything())));

		ViewToDisplay vtd1 = RuntimeValuesAdviceFactory.eINSTANCE.createViewToDisplay();
		getFixture().getNewTypeViewsToDisplay().add(vtd1);

		// The VTD has no view
		assertThat(getFixture().getNewTypeViews(), not(hasItem(anything())));

		// Add an actual view
		ViewToDisplay vtd2 = RuntimeValuesAdviceFactory.eINSTANCE.createViewToDisplay();
		View v2 = ContextsFactory.eINSTANCE.createView();
		vtd2.setView(v2);
		getFixture().getNewTypeViewsToDisplay().add(vtd2);
		assertThat(getFixture().getNewTypeViews(), is(Arrays.asList(v2)));

		// And fill in the first one
		View v1 = ContextsFactory.eINSTANCE.createView();
		vtd1.setView(v1);
		assertThat(getFixture().getNewTypeViews(), is(Arrays.asList(v1, v2)));
	}

} // UMLRTSetTypeAdviceConfigurationTest
