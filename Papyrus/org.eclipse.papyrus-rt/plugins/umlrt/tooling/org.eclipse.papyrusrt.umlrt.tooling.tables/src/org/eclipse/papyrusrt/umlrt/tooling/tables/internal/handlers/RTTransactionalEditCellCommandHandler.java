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

package org.eclipse.papyrusrt.umlrt.tooling.tables.internal.handlers;

import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.handler.TransactionalEditCellCommandHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * A specialized transactional edit cell command-handler that deals with nested
 * commands, avoiding throwing cancellation exceptions that end up in the error log.
 */
public class RTTransactionalEditCellCommandHandler extends TransactionalEditCellCommandHandler {

	private final TransactionalEditingDomain domain;

	public RTTransactionalEditCellCommandHandler(TransactionalEditingDomain domain) {
		super(domain);

		this.domain = domain;
	}

	public RTTransactionalEditCellCommandHandler(TransactionalEditingDomain domain, String label) {
		super(domain, label);

		this.domain = domain;
	}

	@Override
	protected ExecutionStatusKind editCell(ILayerCell cell, Composite parent, Object initialCanonicalValue, IConfigRegistry configRegistry) {
		ExecutionStatusKind result = super.editCell(cell, parent, initialCanonicalValue, configRegistry);

		if ((result == ExecutionStatusKind.OK_ROLLBACK) && isNestedTransaction()) {
			// Don't roll back because it's a nested transaction, so it won't be
			// on the stack anyways (the root transaction will be on the stack)
			result = ExecutionStatusKind.OK_COMPLETE;
		}

		return result;
	}

	boolean isNestedTransaction() {
		Transaction active = ((InternalTransactionalEditingDomain) domain).getActiveTransaction();
		return (active != null) && (active.getParent() != null);
	}
}
