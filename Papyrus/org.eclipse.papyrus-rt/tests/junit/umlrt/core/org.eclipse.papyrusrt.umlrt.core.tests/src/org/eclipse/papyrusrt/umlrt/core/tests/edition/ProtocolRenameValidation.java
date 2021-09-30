/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.junit.Assert;

/**
 * Validation rules for Protocol renaming
 */
public class ProtocolRenameValidation implements IValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element targetContainer, Object[] commandResults) throws Exception {
		Assert.assertTrue(ProtocolUtils.isProtocol(targetContainer));
		Collaboration protocol = (Collaboration) targetContainer;
		org.eclipse.uml2.uml.Package protocolContainer = ProtocolUtils.getProtocolContainer(protocol);
		Assert.assertNotNull(protocolContainer);
		Interface messageSetIn = ProtocolUtils.getMessageSetIn(protocol);
		Assert.assertNotNull(messageSetIn);
		Interface messageSetOut = ProtocolUtils.getMessageSetOut(protocol);
		Assert.assertNotNull(messageSetOut);
		Interface messageSetInOut = ProtocolUtils.getMessageSetInOut(protocol);
		Assert.assertNotNull(messageSetInOut);

		Assert.assertEquals("Wrong name for Protocol after Protocol renaming", ProtocolEditionTests.NEW_NAME, protocol.getName());
		Assert.assertEquals("Wrong name for Protocol container after Protocol renaming", ProtocolEditionTests.NEW_NAME, protocolContainer.getName());
		Assert.assertEquals("Wrong name for MessageSet IN after Protocol renaming", ProtocolEditionTests.NEW_NAME, messageSetIn.getName());
		Assert.assertEquals("Wrong name for MessageSet OUT after Protocol renaming", ProtocolEditionTests.NEW_NAME + "~", messageSetOut.getName());
		Assert.assertEquals("Wrong name for MessageSet INOUT after Protocol renaming", ProtocolEditionTests.NEW_NAME + "IO", messageSetInOut.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element targetContainer, Object[] commandResults) throws Exception {
		// check names for Protocol, protocolContainer, message sets and links
		Assert.assertTrue(ProtocolUtils.isProtocol(targetContainer));
		Collaboration protocol = (Collaboration) targetContainer;
		org.eclipse.uml2.uml.Package protocolContainer = ProtocolUtils.getProtocolContainer(protocol);
		Assert.assertNotNull(protocolContainer);
		Interface messageSetIn = ProtocolUtils.getMessageSetIn(protocol);
		Assert.assertNotNull(messageSetIn);
		Interface messageSetOut = ProtocolUtils.getMessageSetOut(protocol);
		Assert.assertNotNull(messageSetOut);
		Interface messageSetInOut = ProtocolUtils.getMessageSetInOut(protocol);
		Assert.assertNotNull(messageSetInOut);

		Assert.assertEquals("Wrong name for Protocol after Protocol renaming Undo", ProtocolEditionTests.OLD_NAME, protocol.getName());
		Assert.assertEquals("Wrong name for Protocol container after Protocol renaming Undo", ProtocolEditionTests.OLD_NAME, protocolContainer.getName());
		Assert.assertEquals("Wrong name for MessageSet IN after Protocol renaming Undo", ProtocolEditionTests.OLD_NAME, messageSetIn.getName());
		Assert.assertEquals("Wrong name for MessageSet OUT after Protocol renaming Undo", ProtocolEditionTests.OLD_NAME + "~", messageSetOut.getName());
		Assert.assertEquals("Wrong name for MessageSet INOUT after Protocol renaming Undo", ProtocolEditionTests.OLD_NAME + "IO", messageSetInOut.getName());
	}

}
