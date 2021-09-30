/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 479425
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Tests for Protocol edition
 */
public class ProtocolEditionTests extends AbstractEditionElementTest {

	public static final Object OLD_NAME = "Protocol0";

	public static final Object NEW_NAME = "newName";

	@Test
	public void testRenameProtocol() throws Exception {
		runEditionTest(protocol, UMLPackage.eINSTANCE.getNamedElement_Name(), NEW_NAME, true, ProtocolRenameValidation.class);
	}

	@Test
	public void testRenameProtocolContainer() throws Exception {
		runEditionTest(protocolContainer, UMLPackage.eINSTANCE.getNamedElement_Name(), NEW_NAME, true,
				IValidationRule.postEdit((targetContainer, commandResults) -> {
					try {
						new ProtocolRenameValidation().validatePostEdition(protocol, commandResults);
					} catch (Exception e) {
						throw new WrappedException(e);
					}
				}));
	}
}
