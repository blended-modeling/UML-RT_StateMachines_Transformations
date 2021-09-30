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

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for GMF element-types.
 */
public class ElementTypeMatchers {

	/**
	 * An instance-of matcher.
	 * 
	 * @param type
	 *            the element type to match
	 * @return a matcher that determines whether an object is of the given {@code type}
	 */
	public static Matcher<EObject> isA(IElementType type) {
		return new IsAMatcher(type);
	}

	//
	// Nested types
	//

	private static final class IsAMatcher extends BaseMatcher<EObject> {
		private final IElementType type;

		IsAMatcher(IElementType type) {
			super();

			this.type = type;
		}

		@Override
		public int hashCode() {
			return 37 * type.hashCode() + 11;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof IsAMatcher) &&
					Objects.equals(type, ((IsAMatcher) obj).type);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("is a " + type.getDisplayName());
		}

		@Override
		public boolean matches(Object item) {
			boolean result = false;

			// A non-EObject (incl. null) is an instance of no type
			if (item instanceof EObject) {
				EObject object = (EObject) item;

				if (type instanceof ISpecializationType) {
					IElementMatcher matcher = ((ISpecializationType) type).getMatcher();
					result = (matcher != null) && matcher.matches(object);
				} else {
					result = type.getEClass().isInstance(object);
				}
			}

			return result;
		}
	}

}
