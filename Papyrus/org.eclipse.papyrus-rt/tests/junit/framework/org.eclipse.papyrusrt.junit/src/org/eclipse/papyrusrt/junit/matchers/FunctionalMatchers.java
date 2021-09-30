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

package org.eclipse.papyrusrt.junit.matchers;

import java.util.Objects;
import java.util.function.Function;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Generic Hamcrest matchers that compose standard matchers on details extracted by functions.
 */
public class FunctionalMatchers {

	public static <T, U> Matcher<T> compose(Function<? super T, ? extends U> detailFunction, Matcher<? super U> detailMatcher) {
		return new DetailMatcher<>(detailFunction, detailMatcher);
	}

	//
	// Nested types
	//

	private static final class DetailMatcher<T, U> extends BaseMatcher<T> {
		private final Function<? super T, ? extends U> detailFunction;
		private final Matcher<? super U> detailMatcher;

		DetailMatcher(Function<? super T, ? extends U> detailFunction, Matcher<? super U> detailMatcher) {
			super();

			this.detailFunction = detailFunction;
			this.detailMatcher = detailMatcher;
		}

		@Override
		public int hashCode() {
			return Objects.hash(detailFunction, detailMatcher);
		}

		@Override
		public boolean equals(Object obj) {
			boolean result;

			if (obj == this) {
				result = true;
			} else if (!(obj instanceof DetailMatcher<?, ?>)) {
				result = false;
			} else {
				DetailMatcher<?, ?> other = (DetailMatcher<?, ?>) obj;
				result = Objects.equals(other.detailFunction, this.detailFunction)
						&& Objects.equals(other.detailMatcher, this.detailMatcher);
			}

			return result;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("detail of object ").appendDescriptionOf(detailMatcher);
		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;

			if (item != null) {
				@SuppressWarnings("unchecked")
				T master = (T) item;
				U detail = detailFunction.apply(master);

				result = detailMatcher.matches(detail);
			}

			return result;
		}
	}

}
