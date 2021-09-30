/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.utils;

import static java.util.Spliterators.spliteratorUnknownSize;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.Activator;

/**
 * Various static utilities for working with commands.
 */
public class UMLRTCommandUtils {

	// Not instantiable by clients
	private UMLRTCommandUtils() {
		super();
	}

	/**
	 * An alternative to the {@link ICommand#compose(org.eclipse.core.commands.operations.IUndoableOperation)}
	 * API that compose commands into a single, flat composite, instead of adding nested composites
	 * to the result.
	 * 
	 * @param c1
	 *            a possible composite command
	 * @param c2
	 *            another possibly composite command
	 * 
	 * @return a command that composes {@code c1} with {@code c2} or its components,
	 *         according to whethr {@code c2} is composite
	 */
	public static ICommand flatCompose(ICommand c1, ICommand c2) {
		ICommand result;

		if (c2 instanceof UnexecutableCommand) {
			// That's enough
			result = c2;
		} else if (c1 instanceof UnexecutableCommand) {
			// Or that
			result = c1;
		} else if (c2 instanceof ICompositeCommand) {
			ICompositeCommand cc2 = (ICompositeCommand) c2;
			@SuppressWarnings("unchecked")
			Iterator<ICommand> commands = cc2.iterator();
			result = StreamSupport.stream(
					spliteratorUnknownSize(commands, Spliterator.NONNULL | Spliterator.ORDERED),
					false).reduce(c1, UMLRTCommandUtils::flatCompose);
		} else if (c1 != null) {
			result = ((c2 == null) || (c2 instanceof IdentityCommand))
					? c1
					: (c1 instanceof IdentityCommand)
							? c2
							: c1.compose(c2);
		} else {
			result = c2; // Whether it's null or not
		}

		return result;
	}

	/**
	 * Queries whether changes reported by a {@code notifier} are happening in the
	 * context of an undo or a redo of a command execution, or roll-back of a
	 * transaction, which is a kind of undo.
	 * 
	 * @param notifier
	 *            some EMF run-time object
	 * @return whether it is being changed by undo/redo or roll-back
	 */
	public static boolean isUndoRedoInProgress(Notifier notifier) {
		boolean result = false;

		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(notifier);
		if (domain instanceof InternalTransactionalEditingDomain) {
			InternalTransaction active = ((InternalTransactionalEditingDomain) domain).getActiveTransaction();
			result = (active != null)
					&& (Boolean.TRUE.equals(active.getOptions().get(Transaction.OPTION_IS_UNDO_REDO_TRANSACTION))
							|| active.isRollingBack());
		}

		return result;
	}

	/**
	 * Obtains a command from the element edit service to set a feature of an object.
	 * 
	 * @param request
	 *            an edit request for which a set command is to be provided. Needs
	 *            not be a {@link SetRequest} but should provide the appropriate editing-domain and
	 *            client-context details for construction of new requests
	 * @param owner
	 *            the object to edit
	 * @param feature
	 *            the feature to set
	 * @param value
	 *            the value to set into the feature
	 * 
	 * @return the edit command, or an unexecutable command if the edit cannot be done
	 */
	public static ICommand set(IEditCommandRequest request, EObject owner, EStructuralFeature feature, Object value) {
		SetRequest set = new SetRequest(request.getEditingDomain(), owner, feature, value);
		set.setClientContext(request.getClientContext());

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(owner);
		if (edit == null) {
			Activator.log.error("No edit provider for element: " + owner, null);
			return UnexecutableCommand.INSTANCE;
		}

		return edit.getEditCommand(set);
	}

	/**
	 * Obtains a command from the element edit service to unset a feature of an object.
	 * 
	 * @param request
	 *            an edit request for which a set command is to be provided. Needs
	 *            not be an {@link UnsetRequest} but should provide the appropriate editing-domain and
	 *            client-context details for construction of new requests
	 * @param owner
	 *            the object to edit
	 * @param feature
	 *            the feature to unset
	 * 
	 * @return the edit command, or an unexecutable command if the edit cannot be done
	 */
	public static ICommand unset(IEditCommandRequest request, EObject owner, EStructuralFeature feature) {
		UnsetRequest unset = new UnsetRequest(request.getEditingDomain(), owner, feature);
		unset.setClientContext(request.getClientContext());

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(owner);
		if (edit == null) {
			Activator.log.error("No edit provider for element: " + owner, null);
			return UnexecutableCommand.INSTANCE;
		}

		return edit.getEditCommand(unset);
	}

	/**
	 * Obtains a command from the element edit service to destroy an object.
	 * 
	 * @param request
	 *            an edit request for which a destroy command is to be provided. Needs
	 *            not be a {@link DestroyElementRequest} but should provide the appropriate
	 *            editing-domain and client-context details for construction of new requests
	 * @param owner
	 *            the object to destroy
	 * 
	 * @return the edit command, or an unexecutable command if the edit cannot be done
	 */
	public static ICommand destroy(IEditCommandRequest request, EObject object) {
		DestroyElementRequest destroy = new DestroyElementRequest(request.getEditingDomain(), object, false);
		destroy.setClientContext(request.getClientContext());

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(destroy.getContainer());
		if (edit == null) {
			Activator.log.error("No edit provider for owner of element: " + object, null);
			return UnexecutableCommand.INSTANCE;
		}

		return edit.getEditCommand(destroy);
	}
}
