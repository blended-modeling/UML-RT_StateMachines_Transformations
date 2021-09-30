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

import static java.lang.Math.abs;

import org.eclipse.draw2d.geometry.Point;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Convenient matchers for GEF geometries.
 */
public class GeometryMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private GeometryMatchers() {
		super();
	}

	/**
	 * Obtains a matcher that tests whether a point is within a tolerance of an
	 * {@code expected} location.
	 * 
	 * @param expected
	 *            the expected location
	 * @param plusOrMinus
	 *            the tolerance for error
	 * 
	 * @return the matcher
	 */
	public static Matcher<Point> near(Point expected, int plusOrMinus) {
		return near(expected.x(), expected.y(), plusOrMinus);
	}

	/**
	 * Obtains a matcher that tests whether a point is within a tolerance of an
	 * expected location.
	 * 
	 * @param x
	 *            the x coördinate of the expected location
	 * @param y
	 *            the y coördinate of the expected location
	 * @param plusOrMinus
	 *            the tolerance for error
	 * 
	 * @return the matcher
	 */
	public static Matcher<Point> near(int x, int y, int plusOrMinus) {
		return new BaseMatcher<Point>() {
			@Override
			public void describeTo(Description description) {
				// Use %s to avoid commas and other fancy formatting
				description.appendText(String.format("(%s, %s) ± %s", x, y, plusOrMinus));
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof Point) {
					Point point = (Point) item;
					result = (abs(point.x() - x) <= plusOrMinus)
							&& (abs(point.y() - y) <= plusOrMinus);
				}

				return result;
			}
		};
	}
}
