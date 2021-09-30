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

package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.axis;

import java.util.Optional;

import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.properties.ui.creation.EditionDialog;
import org.eclipse.papyrusrt.umlrt.core.utils.EMFHacks;

/**
 * A specialization of the synchronized-on-feature axis manager that reacts only to
 * transaction post-commit notifications, not directly as an adapter, unless
 * the table is created in an {@link EditionDialog}, in which case it is all within
 * the context of a top-level transaction and there is no option but the adapter.
 * In the case of usage within the Properties view, the transactional behaviour ensures that
 * rows/columns/whatever are only created or updated if the transaction is not rolled
 * back, such as happens when a command that has already created model content is later
 * cancelled.
 */
public class TransactionalSynchronizedOnFeatureAxisManager extends RTSynchronizedOnFeatureAxisManager {

	private Runnable listenerRemoval;

	@Override
	protected void addContextFeatureValueListener() {
		// Are we in the context of a dialog?
		// (in which case there is already an active transaction)
		if (EMFHacks.isReadWriteTransactionActive(getTableContext())) {
			// The dialog is operating entirely within a top-level transaction,
			// so we need the default behaviour of immediate reaction
			super.addContextFeatureValueListener();
		} else {
			// The superclass does not need the 'featureListener', so no super

			registerFeatureListener();
			registerRefreshListener();
		}
	}

	private void registerFeatureListener() {
		Optional<NotificationFilter> featureFilter = getListenFeatures().stream()
				.map(NotificationFilter::createFeatureFilter)
				.reduce(NotificationFilter::or);

		featureFilter.ifPresent(ff -> {
			addListener(new ResourceSetListenerImpl(
					NotificationFilter.createNotifierFilter(getTableContext())
							.and(ff)) {

				@Override
				public boolean isPostcommitOnly() {
					return true;
				}

				@Override
				public void resourceSetChanged(ResourceSetChangeEvent event) {
					event.getNotifications().forEach(
							TransactionalSynchronizedOnFeatureAxisManager.this::featureValueHasChanged);
				}
			});
		});
	}

	private void registerRefreshListener() {
		Optional<NotificationFilter> refreshFilter = Optional.ofNullable(getRefreshFilter());

		refreshFilter.ifPresent(rf -> {
			addListener(new ResourceSetListenerImpl(rf) {

				@Override
				public boolean isPostcommitOnly() {
					return true;
				}

				@Override
				public void resourceSetChanged(ResourceSetChangeEvent event) {
					refreshTable();
				}
			});
		});
	}

	/**
	 * Adds and registers a transactional {@code listener} for removal.
	 * 
	 * @param listener
	 *            the transactional listener to add
	 */
	protected final void addListener(ResourceSetListener listener) {
		TransactionalEditingDomain contextDomain = getContextEditingDomain();
		contextDomain.addResourceSetListener(listener);

		if (listenerRemoval == null) {
			listenerRemoval = () -> contextDomain.removeResourceSetListener(listener);
		} else {
			// Chain them
			Runnable next = listenerRemoval;
			listenerRemoval = () -> {
				contextDomain.removeResourceSetListener(listener);
				next.run();
			};
		}
	}

	/**
	 * Hook for subclasses to provide a notification filter that triggers refresh of the table.
	 * 
	 * @return the refresh notification filter, or {@code null} if none required
	 */
	protected NotificationFilter getRefreshFilter() {
		return null;
	}

	/**
	 * Fully refresh the contents of the table.
	 */
	protected void refreshTable() {
		getTableManager().updateAxisContents(getRepresentedContentProvider());
	}

	@Override
	protected void removeListeners() {
		if (listenerRemoval != null) {
			listenerRemoval.run();
			listenerRemoval = null;
		}

		// Attempting to remove a null from the adapter list is harmless
		super.removeListeners();
	}
}
