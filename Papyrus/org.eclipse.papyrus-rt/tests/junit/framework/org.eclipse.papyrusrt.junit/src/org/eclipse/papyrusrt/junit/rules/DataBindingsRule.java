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

package org.eclipse.papyrusrt.junit.rules;

import org.eclipse.core.databinding.observable.Realm;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A rule that sets a default realm for the duration of a test for the convenience of
 * testing data-bindings APIs such as observables without requiring the UI thread.
 */
public class DataBindingsRule implements TestRule {

	public DataBindingsRule() {
		super();
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Throwable[] thrown = { null };

				new TestRealm().installWhile(() -> {
					try {
						base.evaluate();
					} catch (Throwable t) {
						thrown[0] = t;
					}
				});

				if (thrown[0] != null) {
					throw thrown[0];
				}
			}
		};
	}

	//
	// Nested types
	//

	/**
	 * A realm useful for testing data-bindings without requiring the UI thread.
	 */
	public static class TestRealm extends Realm {

		private final Thread owner = Thread.currentThread();

		public TestRealm() {
			super();
		}

		@Override
		public boolean isCurrent() {
			return Thread.currentThread() == owner;
		}

		public void installWhile(Runnable action) {
			Realm restore = setDefault(this);

			try {
				action.run();
			} finally {
				setDefault(restore);
			}
		}
	}

}
