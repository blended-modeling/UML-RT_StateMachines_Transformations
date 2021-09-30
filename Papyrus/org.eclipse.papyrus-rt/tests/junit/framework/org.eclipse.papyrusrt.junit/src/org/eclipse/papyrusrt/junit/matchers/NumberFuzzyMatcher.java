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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * A fuzzy matcher for number equality.
 */
public class NumberFuzzyMatcher<N extends Number> extends BaseMatcher<N> {
	private final N expected;
	private final N plusOrMinus;
	private final Class<N> numberType;

	/**
	 * Not instantiable by clients.
	 */
	@SuppressWarnings("unchecked")
	private NumberFuzzyMatcher(N expected, N plusOrMinus) {
		super();

		this.expected = expected;
		this.plusOrMinus = plusOrMinus;
		this.numberType = (Class<N>) expected.getClass();
	}

	/**
	 * Obtains a matcher that tests whether a number is within a tolerance of an
	 * {@code expected} value.
	 * 
	 * @param expected
	 *            the expected value
	 * @param plusOrMinus
	 *            the tolerance for error
	 * 
	 * @return the matcher
	 */
	public static <N extends Number> NumberFuzzyMatcher<N> near(N expected, N plusOrMinus) {
		return new NumberFuzzyMatcher<>(expected, plusOrMinus);
	}

	@Override
	public void describeTo(Description description) {
		description.appendValue(expected).appendText(" ± ").appendValue(plusOrMinus);
	}

	@Override
	public boolean matches(Object item) {
		boolean result = false;

		if (numberType.isInstance(item)) {
			N number = numberType.cast(item);

			double actual = number.doubleValue();
			double expected = this.expected.doubleValue();
			double plusOrMinus = Math.abs(this.plusOrMinus.doubleValue());
			result = (actual >= (expected - plusOrMinus))
					&& (actual <= (expected + plusOrMinus));
		}

		return result;
	}
}
