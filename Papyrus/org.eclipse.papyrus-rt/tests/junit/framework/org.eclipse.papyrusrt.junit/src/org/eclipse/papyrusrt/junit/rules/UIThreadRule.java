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

package org.eclipse.papyrusrt.junit.rules;

import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.swt.widgets.Display;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A JUnit rule that runs its test synchronously on the UI thread.
 * This should be used only when the test manipulates API that
 * require the current thread to be the UI thread.
 */
public class UIThreadRule implements TestRule {

	private final boolean requireAnnotation;

	/**
	 * Initializes me without the requirement for any annotation: all tests
	 * in my scope will run on the UI thread.
	 */
	public UIThreadRule() {
		this(false);
	}

	/**
	 * Initializes me with the requirement that individual tests needin the UI
	 * thread be annotated with {@link UIThread @UIThread}.
	 *
	 * @param requireAnnotation
	 */
	public UIThreadRule(boolean requireAnnotation) {
		super();

		this.requireAnnotation = requireAnnotation;
	}

	@Override
	public Statement apply(final Statement base, Description description) {
		if (!requiresUIThread(description)) {
			return base; // Nothing to do for this test
		} else {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					final Throwable[] caught = { null };

					final Runnable runTest = new Runnable() {

						@Override
						public void run() {
							try {
								base.evaluate();
							} catch (Throwable t) {
								caught[0] = t;
							}
						}
					};

					if (Display.getCurrent() != null) {
						// Just run it
						runTest.run();
					} else {
						// Run it on the UI thread
						Display.getDefault().syncExec(runTest);
					}

					if (caught[0] != null) {
						throw caught[0];
					}
				}
			};
		}
	}

	private boolean requiresUIThread(Description description) {
		return !requireAnnotation
				|| JUnitUtils.getAnnotation(description, UIThread.class) != null;
	}
}
