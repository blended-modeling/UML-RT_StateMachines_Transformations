/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Onder Gurcan (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.deletion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IDeleteValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test deletion of UML-RT elements (Deletion / Undo / Redo)
 */
@SuppressWarnings("restriction")
@PluginResource("resource/TestModel.di")
public class DeleteElementTest extends AbstractPapyrusRTCoreTest {

	@Test
	public void testDeleteProtocolInModel() throws Exception {
		runDeletionTest(rootModel, UMLRTElementTypesEnumerator.PROTOCOL, true, DeleteProtocolFromModelValidation.class);
	}

	@Test
	public void testDeleteProtocolMessageInMessageSetIn() throws Exception {
		runDeletionTest(messageSetIn, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE, true, DeleteProtocolMessageFromMessageSetInValidation.class);
	}

	protected void runDeletionTest(Element owner, IHintedType hintedType, boolean canCreate, Class<? extends IDeleteValidationRule> validationClass) throws Exception {
		// create the element
		CreateElementCommand commandCreate = getCreateCommand(owner, hintedType, canCreate);
		commandCreate.execute(null, null);
		Element deletedElement = (Element) commandCreate.getNewElement();
		// the new element should not be null
		Assert.assertNotNull(deletedElement);
		// and it should be in the model
		// Assert.assertTrue(ownerModel.eContents().contains(newElement));

		// delete the created element
		Command commandDelete = getDeleteChildCommand(deletedElement, false);
		if (commandDelete != null && commandDelete.canExecute()) {
			getCommandStack().execute(commandDelete);

			IDeleteValidationRule validationRule = validationClass.newInstance();
			Object[] commandResults = commandDelete.getResult().toArray();
			// the element should not be in the model
			validationRule.validatePostEdition(owner, deletedElement, commandResults);

			// undo the delete command
			getCommandStack().undo();
			// the element should be in the model
			validationRule.validatePostUndo(owner, deletedElement, commandResults);

			// redo the delete command
			getCommandStack().redo();
			// the element should not be in the model
			validationRule.validatePostEdition(owner, deletedElement, commandResults);

			// undo the delete command
			getCommandStack().undo();
			// the element should be in the model
			validationRule.validatePostUndo(owner, deletedElement, commandResults);

		} else {
			fail("Delete Command should be executable");
		}

		commandCreate.undo(null, null);
	}

	/**
	 * Creates the element inside the given owner element, undo and redo the
	 * action
	 *
	 * @param owner
	 *            owner of the new element
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the
	 *            specified owner
	 */
	protected CreateElementCommand getCreateCommand(EObject owner, IHintedType hintedType, boolean canCreate)
			throws Exception {


		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(owner, hintedType));
		assertNotNull("Command should not be null", command);
		assertTrue("Command should be executable", command.canExecute());
		return command;
	}

}
