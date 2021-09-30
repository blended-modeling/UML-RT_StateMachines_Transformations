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

import org.eclipse.core.runtime.IStatus;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for {@link IStatus}es.
 */
public class StatusMatchers {

	/**
	 * A severity matcher.
	 * 
	 * @param severity
	 *            the severity to match (can be a mask of multiple severities to match one of them)
	 * 
	 * @return the severity matcher
	 */
	public static Matcher<IStatus> hasSeverity(int severity) {
		return new SeverityMatcher(severity);
	}

	//
	// Nested types
	//

	private static final class SeverityMatcher extends BaseMatcher<IStatus> {
		private final int severity;

		SeverityMatcher(int severity) {
			super();

			this.severity = severity;
		}

		@Override
		public int hashCode() {
			return 37 * severity + 11;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof SeverityMatcher)
					&& (((SeverityMatcher) obj).severity == severity);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(String.format("has %s severity",
					(severity == IStatus.OK) ? "OK"
							: (severity == IStatus.WARNING) ? "WARNING"
									: (severity == IStatus.INFO) ? "INFO"
											: (severity == IStatus.ERROR) ? "ERROR"
													: (severity == IStatus.CANCEL) ? "CANCEL"
															: "masked"));

		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;

			if (item instanceof IStatus) {
				IStatus status = (IStatus) item;

				if (severity == IStatus.OK) {
					result = status.isOK();
				} else {
					result = status.matches(severity);
				}
			}

			return result;
		}
	}

}
