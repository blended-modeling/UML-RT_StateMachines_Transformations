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

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomainEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomainListenerImpl;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.tools.util.CoreExecutors;
import org.eclipse.papyrusrt.umlrt.core.Activator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Executor utility that runs tasks after the completion or failure of the current
 * top-level transaction, regardless. This is different from executors obtained
 * via the {@link TransactionHelper#createTransactionExecutor(TransactionalEditingDomain, Executor)}
 * and related APIs, which execute before commit or not at all if the transaction rolls
 * back.
 */
public class PostTransactionExecutor implements Executor {
	private static final LoadingCache<TransactionalEditingDomain, Executor> executors = CacheBuilder
			.newBuilder().weakKeys().weakValues().build(CacheLoader.from(
					domain -> new PostTransactionExecutor(domain, CoreExecutors.getUIExecutorService())));

	private final ConcurrentLinkedQueue<Runnable> pendingTasks = new ConcurrentLinkedQueue<>();
	private final Executor fallback;
	private final AtomicReference<Transaction> activeTransaction;

	private PostTransactionExecutor(TransactionalEditingDomain domain, Executor fallback) {
		super();

		this.fallback = fallback;

		InternalTransaction active = ((InternalTransactionalEditingDomain) domain).getActiveTransaction();
		this.activeTransaction = new AtomicReference<>((active == null) ? null : active.getRoot());

		TransactionalEditingDomain.Lifecycle lifecycle = TransactionUtil.getAdapter(domain, TransactionalEditingDomain.Lifecycle.class);
		lifecycle.addTransactionalEditingDomainListener(new TransactionalEditingDomainListenerImpl() {

			@Override
			public void transactionStarted(TransactionalEditingDomainEvent event) {
				activeTransaction.compareAndSet(null, event.getTransaction());
			}

			@Override
			public void transactionClosed(TransactionalEditingDomainEvent event) {
				if (activeTransaction.compareAndSet(event.getTransaction(), null)) {
					executePending();
				}
			}

			@Override
			public void editingDomainDisposing(TransactionalEditingDomainEvent event) {
				lifecycle.removeTransactionalEditingDomainListener(this);

				// In case there were any
				executePending();
			}
		});
	}

	/**
	 * Obtains the post-transaction executor for the given {@code domain}.
	 * 
	 * @param domain
	 *            an editing domain
	 * @return its post-transaction executor
	 */
	public static Executor getInstance(TransactionalEditingDomain domain) {
		// Initialization of an executor cannot fail under this configuration
		return executors.getUnchecked(domain);
	}

	/**
	 * Obtains the best available post-transaction executor for the given {@code object}.
	 * 
	 * @param object
	 *            an object
	 * @return its domain's executor if the {@code object} is in an editing domain,
	 *         otherwise a UI-thread asynchronous executor as a fallback
	 */
	public static Executor getInstance(EObject object) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(object);
		return (domain == null) ? CoreExecutors.getUIExecutorService() : getInstance(domain);
	}

	@Override
	public void execute(Runnable command) {
		Transaction active = activeTransaction.get();
		if (active == null) {
			// There will be no completion of the transaction, so "fall back"
			fallback.execute(command);
		} else {
			pendingTasks.add(command);
		}
	}

	private void executePending() {
		for (Runnable task = pendingTasks.poll(); task != null; task = pendingTasks.poll()) {
			try {
				task.run();
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in post-commit runnable", e); //$NON-NLS-1$
			}
		}
	}
}
