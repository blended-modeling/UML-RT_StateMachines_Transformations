/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.google.common.base.Strings;

/**
 * Hamcrest matchers for {@link String}es.
 */
public final class StringMatcher {

	/**
	 * Constructor.
	 */
	private StringMatcher() {
		// empty
	}

	/**
	 * A null-or-empty string matcher.
	 * 
	 * @param string
	 *            the string to test, if <code>null</code> or empty
	 * 
	 * @return the null-or-empty matcher
	 */
	public static Matcher<String> nullOrEmpty() {
		return new NullOrEmptyMatcher();
	}

	//
	// Nested types
	//

	private static final class NullOrEmptyMatcher extends BaseMatcher<String> {


		protected NullOrEmptyMatcher() {
			super();
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("String should be null or empty");

		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;
			if (item == null) {
				result = true;
			}
			if (item instanceof String) {
				result = Strings.isNullOrEmpty((String) item);
			}
			return result;
		}
	}
}
