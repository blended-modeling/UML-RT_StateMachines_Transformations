/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.junit.matchers;

import org.eclipse.papyrus.junit.utils.Duck;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Specific matcher for figure.
 */
public class FigureMatchers {

	/**
	 * Constructor.
	 */
	private FigureMatchers() {
		// empty
	}

	public static <C> Matcher<C> isShowing() {
		return new VisibilityMatcher<>();
	}

	private static final class VisibilityMatcher<C> extends BaseMatcher<C> {

		private VisibilityMatcher() {
			super();
		}

		@Override
		public int hashCode() {
			return 37 * VisibilityMatcher.class.hashCode() + 11;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof VisibilityMatcher<?>);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("is showing");
		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;

			// A null view is never showing...
			if (item != null) {
				try {
					result = new Duck(item).quack("isShowing", Boolean.class);
				} catch (Exception e) {
				}
			}

			return result;
		}
	}
}
