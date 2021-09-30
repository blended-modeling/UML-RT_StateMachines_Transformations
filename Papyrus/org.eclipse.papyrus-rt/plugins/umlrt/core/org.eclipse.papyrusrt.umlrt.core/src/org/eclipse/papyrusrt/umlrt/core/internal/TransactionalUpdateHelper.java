/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.internal;

import java.util.Collections;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.InternalUpdateHelper;

/**
 * An extension update helper for {@link TransactionalEditingDomain} contexts.
 */
public class TransactionalUpdateHelper implements InternalUpdateHelper {

	private final InternalTransactionalEditingDomain domain;

	TransactionalUpdateHelper(InternalTransactionalEditingDomain domain) {
		super();

		this.domain = domain;
	}

	public static TransactionalUpdateHelper create(Resource extensionResource) {
		TransactionalUpdateHelper result = null;

		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(extensionResource);
		if (domain instanceof InternalTransactionalEditingDomain) {
			result = new TransactionalUpdateHelper((InternalTransactionalEditingDomain) domain);
		}

		return result;
	}

	@Override
	public void run(Object context, Runnable update) {
		Transaction active = domain.getActiveTransaction();
		if ((active != null) && !active.isReadOnly()) {
			// Just go ahead
			update.run();
		} else {
			// Need a (nested) unprotected write
			try {
				Transaction unprotected = domain.startTransaction(false, Collections.singletonMap(Transaction.OPTION_UNPROTECTED, true));
				try {
					update.run();
				} catch (RuntimeException e) {
					active.rollback();
					Activator.log.error("Uncaught exception in internal update helper. Update rolled back.", e); //$NON-NLS-1$
					throw e;
				} finally {
					if (unprotected.isActive()) {
						try {
							unprotected.commit();
						} catch (RollbackException e) {
							Activator.log.error("Update rolled back by commit validation.", e); //$NON-NLS-1$
						}
					}
				}
			} catch (InterruptedException e) {
				Activator.log.error("Interrupted waiting for transaction.  Attempting update.", e); //$NON-NLS-1$
				update.run();
			}
		}
	}

}
