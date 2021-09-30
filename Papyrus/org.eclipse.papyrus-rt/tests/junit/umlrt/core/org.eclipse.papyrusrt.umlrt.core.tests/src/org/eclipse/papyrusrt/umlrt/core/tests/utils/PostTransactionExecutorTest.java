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

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomainEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomainListener;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.utils.PostTransactionExecutor;
import org.eclipse.swt.widgets.Display;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Test suite for the {@link PostTransactionExecutor} API.
 */
@PluginResource("resource/TestModel.di")
public class PostTransactionExecutorTest {
	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	@ClassRule
	public static final ModelSetFixture modelSet = new ModelSetFixture();

	@Rule
	public final TestRule notUI = new NotUIThreadRule();

	@CleanUp
	private static Executor fixture;

	/**
	 * Initializes me.
	 */
	public PostTransactionExecutorTest() {
		super();
	}

	/**
	 * Verify the execution of runnables after the close of a read-only transaction.
	 */
	@Test
	public void postReadTransaction() throws Exception {
		// We expect read notification(s) to be followed by the post-commit execution
		Canary canary = new Canary();
		canary.expectModelNotifications();
		canary.expectExecution();
		canary.close();

		// Do something in a read-only transaction
		modelSet.getEditingDomain().runExclusive(() -> {
			fixture.execute(canary);

			// This generates read-compatible notifications for a post-commit listener
			modelSet.getResourceSet().createResource(URI.createURI("http://localhost/bogus.xmi"));
		});

		canary.assertTimeline();
	}

	/**
	 * Verify the execution of runnables after the close of a read/write transaction.
	 */
	@Test
	public void postWriteTransaction() throws Exception {
		// We expect write notification(s) to be followed by the post-commit execution
		Canary canary = new Canary();
		canary.expectModelNotifications();
		canary.expectExecution();
		canary.close();

		// Do something in a read/write transaction
		modelSet.execute(new RecordingCommand(modelSet.getEditingDomain(), "Do It") {

			@Override
			protected void doExecute() {
				fixture.execute(canary);

				// This generates read-compatible notifications for a post-commit listener
				modelSet.getModel().setName("Foo");
			}
		});

		canary.assertTimeline();
	}

	/**
	 * Verify the execution of runnables after the failure of a read/write transaction.
	 */
	@Test
	public void postTransactionRollback() throws Exception {
		// We expect rollback to be followed by the post-rollback execution
		Canary canary = new Canary();
		canary.expectRollback();
		canary.expectExecution();
		canary.close();

		// Do something in a read/write transaction
		modelSet.execute(new RecordingCommand(modelSet.getEditingDomain(), "Do It") {

			@Override
			protected void doExecute() {
				fixture.execute(canary);

				((InternalTransactionalEditingDomain) modelSet.getEditingDomain())
						.getActiveTransaction().abort(
								new Status(IStatus.ERROR,
										"org.eclipse.papyrusrt.umlrt.core.test",
										"Trigger roll-back"));
			}
		});

		canary.assertTimeline();
	}

	/**
	 * Verify the execution of runnables not in the context of a transaction.
	 */
	@Test
	public void noTransaction() throws Exception {
		// We expect rollback to be followed by the post-rollback execution
		Canary canary = new Canary();
		canary.expectUIThread();
		canary.expectExecution();
		canary.close();

		// Do something not in a transaction
		fixture.execute(canary);

		canary.assertTimeline();
	}

	//
	// Test framework
	//

	@BeforeClass
	public static void createFixture() {
		fixture = PostTransactionExecutor.getInstance(modelSet.getEditingDomain());
	}

	/**
	 * A test fixture that observes and asserts a specific timeline sequence of events.
	 */
	static class Canary extends ResourceSetListenerImpl implements Runnable, TransactionalEditingDomainListener {
		private final Queue<TimelineEventKind> expectedTimeline = new LinkedList<>();
		private final List<String> timelineErrors = new ArrayList<>(2);

		private final TransactionalEditingDomain domain;
		private final TransactionalEditingDomain.Lifecycle domainLifecycle;

		private final Lock lock = new ReentrantLock();
		private final Condition executedCond = lock.newCondition();
		private volatile boolean executed;

		Canary() {
			super();

			domain = modelSet.getEditingDomain();
			domain.addResourceSetListener(this);

			domainLifecycle = TransactionUtil.getAdapter(domain, TransactionalEditingDomain.Lifecycle.class);
			domainLifecycle.addTransactionalEditingDomainListener(this);
		}

		//
		// Canary protocol
		//

		void expectModelNotifications() {
			expectedTimeline.add(TimelineEventKind.MODEL_NOTIFICATIONS);
		}

		void expectRollback() {
			expectedTimeline.add(TimelineEventKind.ROLLBACK);
		}

		void expectUIThread() {
			expectedTimeline.add(TimelineEventKind.UI_THREAD);
		}

		void expectExecution() {
			expectedTimeline.add(TimelineEventKind.EXECUTION);
		}

		void close() {
			expectedTimeline.add(TimelineEventKind.DONE);
		}

		void assertTimeline() {
			// First, disconnect me
			domainLifecycle.removeTransactionalEditingDomainListener(this);
			domain.removeResourceSetListener(this);

			// Wait for execution on another thread, if appropriate
			try {
				awaitExecuted();
			} catch (InterruptedException e) {
				fail("Test interrupted waiting for canary execution");
			}

			if (!timelineErrors.isEmpty()) {
				fail("Timeline out of sequence:\n    "
						+ timelineErrors.stream().collect(Collectors.joining("\n    ")));
			}

			if (expectedTimeline.peek() != TimelineEventKind.DONE) {
				fail("Test timeline not finished");
			}
		}

		private void encounter(TimelineEventKind timelineEvent) {
			timelineEvent.dequeue(expectedTimeline, timelineErrors);
		}

		private void awaitExecuted() throws InterruptedException {
			// Be generous in case of high build server load
			Date deadline = new Date(System.currentTimeMillis() + 5000L);

			lock.lock();

			try {
				while (!executed) {
					executedCond.awaitUntil(deadline);

					if (deadline.before(new Date(System.currentTimeMillis()))) {
						// Ran out of time. The test will fail
						break;
					}
				}
			} finally {
				lock.unlock();
			}
		}

		//
		// Event protocols
		//

		@Override
		public boolean isPostcommitOnly() {
			return true;
		}

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			encounter(TimelineEventKind.MODEL_NOTIFICATIONS);
		}

		@Override
		public void transactionClosing(TransactionalEditingDomainEvent event) {
			if (event.getTransaction().getStatus().getSeverity() >= IStatus.ERROR) {
				// Rolling back
				encounter(TimelineEventKind.ROLLBACK);
			}
		}

		@Override
		public void run() {
			if (Display.getCurrent() != null) {
				encounter(TimelineEventKind.UI_THREAD);
			}

			encounter(TimelineEventKind.EXECUTION);

			lock.lock();

			try {
				executed = true;
				executedCond.signalAll();
			} finally {
				lock.unlock();
			}
		}

		@Override
		public void transactionStarting(TransactionalEditingDomainEvent event) {
			// Pass
		}

		@Override
		public void transactionInterrupted(TransactionalEditingDomainEvent event) {
			// Pass
		}

		@Override
		public void transactionStarted(TransactionalEditingDomainEvent event) {
			// Pass
		}

		@Override
		public void transactionClosed(TransactionalEditingDomainEvent event) {
			// pass
		}

		@Override
		public void editingDomainDisposing(TransactionalEditingDomainEvent event) {
			// Pass
		}

		//
		// Nested types
		//

		private static enum TimelineEventKind {
			MODEL_NOTIFICATIONS, ROLLBACK, UI_THREAD, EXECUTION, DONE;

			void dequeue(Queue<? extends TimelineEventKind> expectedTimeline, List<? super String> errors) {
				TimelineEventKind expected = expectedTimeline.peek();
				if (expected == this) {
					// Dequeue it
					expectedTimeline.remove();
				} else {
					// Event out of sequence
					errors.add(String.format("Got %s; expected %s", this, expected));
				}
			}
		}

	}

	/**
	 * A test rule that ensures execution of the test not on the UI thread, in the
	 * case that the test framework's thread finds itself becoming the UI thread by
	 * accident of some previous test execution (accidentally) initializing the
	 * SWT display.
	 */
	static final class NotUIThreadRule implements TestRule {
		private static ExecutorService testExecutor;

		@Override
		public Statement apply(Statement base, Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					if (Display.getCurrent() == null) {
						// Great. Just go
						base.evaluate();
						return;
					}

					Callable<Exception> testCallable = () -> {
						Exception result = null;

						try {
							base.evaluate();
						} catch (Error e) {
							throw e;
						} catch (Exception e) {
							result = e;
						} catch (Throwable t) {
							throw new Error("Invalid throwable type: " + t.getClass(), t);
						}

						return result;
					};

					Exception testFailure = null;
					Future<Exception> testExecution = getExecutor().submit(testCallable);

					// None of these tests takes anything like this long
					final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);

					// Because we are the UI thread and a test may synchronize with the UI
					// thread, we need to keep it going
					for (;;) {
						if (testExecution.isDone()) {
							// The test has finished. Nothing more to do
							testFailure = testExecution.get();
							break;
						}
						if (System.currentTimeMillis() >= deadline) {
							// Give up: the test is stalled
							break;
						}

						// Run the event loop
						DisplayUtils.flushEventLoop();

						try {
							testFailure = testExecution.get(100L, TimeUnit.MICROSECONDS);
						} catch (TimeoutException __) {
							// This is deliberate in our busy-wait
						}
					}

					if (testFailure != null) {
						throw testFailure;
					}
				}
			};
		}

		private static synchronized ExecutorService getExecutor() {
			if (testExecutor == null) {
				testExecutor = Executors.newSingleThreadExecutor();
				houseKeeper.cleanUpLater(testExecutor, exec -> exec.shutdown());
			}

			return testExecutor;
		}
	}
}
