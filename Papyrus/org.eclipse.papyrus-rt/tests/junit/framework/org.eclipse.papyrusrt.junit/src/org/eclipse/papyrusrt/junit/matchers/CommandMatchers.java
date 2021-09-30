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

package org.eclipse.papyrusrt.junit.matchers;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.Duck;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for commands.
 */
public class CommandMatchers {

	/**
	 * An executability matcher.
	 * 
	 * @param <C>
	 *            the command type, which should conform to any of
	 *            {@link Command}, {@link org.eclipse.emf.common.command.Command},
	 *            {@link ICommand}, or {@link IUndoableOperation}
	 * @return a matcher that determines whether a command is executable
	 */
	public static <C> Matcher<C> isExecutable() {
		return new ExecutabilityMatcher<>();
	}

	//
	// Nested types
	//

	private static final class ExecutabilityMatcher<C> extends BaseMatcher<C> {

		ExecutabilityMatcher() {
			super();
		}

		@Override
		public int hashCode() {
			return 37 * ExecutabilityMatcher.class.hashCode() + 11;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof ExecutabilityMatcher<?>);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("is executable");
		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;

			// A null command is never executable
			if (item != null) {
				try {
					result = new Duck(item).quack("canExecute", Boolean.class);
				} catch (Exception e) {
					// Must not be a command, then
				}
			}

			return result;
		}
	}

}
