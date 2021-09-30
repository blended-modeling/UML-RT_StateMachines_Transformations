/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.junit.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.IRequestCacheEntries;
import org.junit.Assert;

/**
 * Utility method to create elements in model.
 */
public final class CreationUtils {

	/**
	 * Constructor.
	 */
	private CreationUtils() {
		// empty
	}

	public static Command getCreateChildCommand(EObject owner, IHintedType hintedType, boolean canCreate, TransactionalEditingDomain transactionalEditingDomain) throws Exception {
		return getCreationCommand(owner, hintedType, canCreate, transactionalEditingDomain, CreateElementRequest::new);
	}

	static Command getCreationCommand(EObject owner, IHintedType hintedType, boolean canCreate, TransactionalEditingDomain domain, ICreationRequestFunction requestFunction) throws Exception {
		GetEditContextRequest editContextRequest = new GetEditContextRequest(domain, requestFunction.apply(domain, owner, hintedType), owner);

		editContextRequest.setParameter(IRequestCacheEntries.Cache_Maps, new HashMap<>());
		editContextRequest.setEditContext(owner);
		editContextRequest.setClientContext(TypeContext.getContext(owner));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(owner);
		if (provider == null) {
			Assert.fail("Impossible to get the editing domain provider");
		}

		EObject target = owner;
		Object result = null;
		final ICommand getEditContextCommand = provider.getEditCommand(editContextRequest);
		if (getEditContextCommand != null) {
			IStatus status = null;
			try {
				// this command could run in an unprotected transaction, it is not supposed to modify the model
				InternalTransactionalEditingDomain internalDomain = (InternalTransactionalEditingDomain) domain;
				Map<String, Object> options = new HashMap<>();
				options.put(Transaction.OPTION_NO_NOTIFICATIONS, true);
				options.put(Transaction.OPTION_NO_VALIDATION, true);
				options.put(Transaction.OPTION_NO_TRIGGERS, true);
				Transaction transaction = internalDomain.startTransaction(false, options);
				try {
					status = getEditContextCommand.execute(null, null);
				} finally {
					transaction.commit();
				}
			} catch (InterruptedException e) {
				Assert.fail(e.getMessage());
			} catch (ExecutionException e) {
				Assert.fail(e.getMessage());
			} catch (RollbackException e) {
				Assert.fail(e.getMessage());
			}
			if (!(status == null || !status.isOK())) {
				result = getEditContextCommand.getCommandResult().getReturnValue();
			}
			if (result instanceof EObject) {
				target = (EObject) result;
			}
		}

		provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return UnexecutableCommand.INSTANCE;
		}

		ICommand createGMFCommand = provider.getEditCommand(requestFunction.apply(domain, target, hintedType));
		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if ((createGMFCommand != null) && createGMFCommand.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", createGMFCommand);
			assertTrue("Command should be executable", createGMFCommand.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(createGMFCommand);
			return emfCommand;
		}
		return null;
	}

	/**
	 * For the case where the {@code owner} is also the source end of the relationship.
	 * 
	 * @throws Exception
	 * 
	 * @see #getCreateRelationshipCommand(EObject, IHintedType, EObject, EObject, boolean, TransactionalEditingDomain)
	 */
	public static Command getCreateRelationshipCommand(EObject owner, IHintedType hintedType, EObject target, boolean canCreate, TransactionalEditingDomain transactionalEditingDomain) throws Exception {
		return getCreateRelationshipCommand(owner, hintedType, owner, target, canCreate, transactionalEditingDomain);
	}

	public static Command getCreateRelationshipCommand(EObject owner, IHintedType hintedType, EObject source, EObject target, boolean canCreate, TransactionalEditingDomain transactionalEditingDomain) throws Exception {
		return getCreationCommand(owner, hintedType, canCreate, transactionalEditingDomain,
				(d, o, t) -> new CreateRelationshipRequest(d, o, source, target, t));
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private static interface ICreationRequestFunction {

		IEditCommandRequest apply(TransactionalEditingDomain domain, EObject owner, IHintedType hintedType);
	}
}
