/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510782, 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Basic tests for Protocol Messages.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(ProtocolTests.class)
public class ProtocolMessageTest {
	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ProtocolMessageTest() {
		super();
	}

	@Test
	public void unsetOwnedParameterList() {
		Class capsule = fixture.getElement("RootCapsule");
		Operation op = capsule.createOwnedOperation("op", null, null);
		Parameter p1 = op.createOwnedParameter("p1", null);
		assertThat(op.getOwnedParameters().size(), equalTo(1));
		assertThat(op.getOwnedParameters(), hasItem(p1));

		// unset the owned parameter list
		op.eUnset(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER);
		assertThat(op.getOwnedParameters().size(), equalTo(0));
	}

}
