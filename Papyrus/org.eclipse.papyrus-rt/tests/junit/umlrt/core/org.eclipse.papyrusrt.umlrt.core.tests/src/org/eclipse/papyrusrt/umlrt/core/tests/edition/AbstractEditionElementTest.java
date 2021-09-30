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
 *  Christian W. Damus - bug 476984
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

/**
 * Test class for edition of UML-RT elements
 */
public abstract class AbstractEditionElementTest extends AbstractPapyrusRTCoreTest {

	protected void runEditionTest(Element target, EStructuralFeature featureToSet, Object newValue, boolean canEdit, Class<? extends IValidationRule> validationClass) throws Exception {
		runEditionTest(target, featureToSet, newValue, canEdit, () -> {
			try {
				return validationClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to instantiate validation class: " + e.getMessage());
				return null; // Unreachable
			}
		});
	}

	protected void runEditionTest(Element target, EStructuralFeature featureToSet, Object newValue, boolean canEdit, Supplier<? extends IValidationRule> validationSupplier) throws Exception {
		Command command = getEditCommand(target, featureToSet, newValue, canEdit);

		// command has been tested when created. Runs the test if it is possible
		if (command != null && canEdit) {
			getCommandStack().execute(command);
			IValidationRule validationRule = validationSupplier.get();
			Object[] commandResults = command.getResult().toArray();
			validationRule.validatePostEdition(target, commandResults);
			getCommandStack().undo();
			validationRule.validatePostUndo(target, commandResults);
			Assert.assertTrue("Editor should not be dirty after undo", !isDirty());
			getCommandStack().redo();
			validationRule.validatePostEdition(target, commandResults);
			getCommandStack().undo();
			validationRule.validatePostUndo(target, commandResults);
			// assert editor is not dirty
			Assert.assertTrue("Editor should not be dirty after undo", !isDirty());
		} else {
			if (canEdit) {
				fail("Command is executable while it should not be");
			}
		}
	}

	protected Command getEditCommand(Element target, EStructuralFeature featureToSet, Object newValue, boolean canEdit) {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(target);
		ICommand command = elementEditService.getEditCommand(new SetRequest(target, featureToSet, newValue));

		if (!canEdit) {
			// command should not be executable: either it should be null or it should be not executable
			if (command != null && command.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", command);
			assertTrue("Command should be executable", command.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = new GMFtoEMFCommandWrapper(command);
			return emfCommand;
		}
		return null;
	}
}
