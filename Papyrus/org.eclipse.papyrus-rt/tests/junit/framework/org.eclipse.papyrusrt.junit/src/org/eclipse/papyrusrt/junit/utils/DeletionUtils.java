/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 512872
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.junit.utils;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.junit.matchers.CommandMatchers;
import org.junit.Assert;

/**
 * Utility method to create elements in model.
 */
public final class DeletionUtils {

	/**
	 * Constructor.
	 */
	private DeletionUtils() {
		// empty
	}

	public static ICommand getDestroyCommand(EObject elementToDestroy) {
		DestroyElementRequest destroy = new DestroyElementRequest(elementToDestroy, false);
		return requireEditService(elementToDestroy).getEditCommand(destroy);
	}

	public static ICommand requireDestroyCommand(EObject elementToDestroy) {
		ICommand result = getDestroyCommand(elementToDestroy);
		assertThat("No command provided", result, notNullValue());
		assertThat("Command not executable", result, CommandMatchers.isExecutable());
		return result;
	}

	static IElementEditService requireEditService(EObject object) {
		IElementEditService result = ElementEditServiceUtils.getCommandProvider(object);
		if (result == null) {
			Assert.fail("Impossible to get the editing domain provider");
		}
		return result;
	}

	public static Command getDeleteCommand(EObject elementToDelete, boolean canDelete, TransactionalEditingDomain transactionalEditingDomain) throws ServiceException {

		DestroyElementRequest req = new DestroyElementRequest(transactionalEditingDomain, elementToDelete, false);
		req.setClientContext(TypeContext.getContext(elementToDelete));

		ICommand deleteCommand = requireEditService(elementToDelete).getEditCommand(req);
		if (!canDelete) {
			// command should not be executable: either it should be null or it should be not executable
			if (deleteCommand != null && deleteCommand.canExecute()) {
				fail("Deletion command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", deleteCommand);
			assertTrue("Command should be executable", deleteCommand.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(deleteCommand);
			return emfCommand;
		}
		return null;
	}
}
