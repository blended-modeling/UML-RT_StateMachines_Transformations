/*****************************************************************************
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
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.types.tests;

import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.tests.UMLRTTypesAllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Delegation to the EMF-generated test suite in the {@code src-gen/} folder that
 * is excluded from the Maven test configuration to avoid duplicate execution of
 * tests and execution of abstract tests that aren't expected to be implemented.
 */
@RunWith(Suite.class)
@SuiteClasses({
		UMLRTTypesAllTests.class,
})
public class GeneratedModelTests {
	// Suite specified in the annotations
}
