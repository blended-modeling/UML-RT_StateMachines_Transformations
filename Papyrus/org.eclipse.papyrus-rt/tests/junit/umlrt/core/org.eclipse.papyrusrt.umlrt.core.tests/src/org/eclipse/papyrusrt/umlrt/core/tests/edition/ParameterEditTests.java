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
package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule.postEdit;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Tests for editing parameters of operations, both plain UML class operations
 * (mostly for regression purposes) and also UML-RT protocol messages.
 */
public class ParameterEditTests extends AbstractEditionElementTest {

	@Test
	public void testRenameParameterInPlainClassOperation() throws Exception {
		// test setting the name of a parameter in an ordinary operation of a simple UML class

		runEditionTest(umlParameter, UMLPackage.Literals.NAMED_ELEMENT__NAME, "now", true, postEdit((element, results) -> {
			assertThat(element, instanceOf(Parameter.class));
			Parameter parameter = (Parameter) element;
			assertThat(parameter.getName(), is("now"));
		}));
	}

	@Test
	public void testSetTypeOfParameterInPlainClassOperation() throws Exception {
		// test setting the name of a parameter in an ordinary operation of a simple UML class

		runEditionTest(umlParameter, UMLPackage.Literals.TYPED_ELEMENT__TYPE, capsule, true, postEdit((element, results) -> {
			assertThat(element, instanceOf(Parameter.class));
			Parameter parameter = (Parameter) element;
			assertThat(parameter.getType(), is(capsule));
		}));
	}

}
