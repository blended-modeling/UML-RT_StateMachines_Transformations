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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.tests;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.eclipse.papyrusrt.junit.matchers.GeometryMatchers.near;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.PortLayoutIterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * Test cases for the {@link PortLayoutIterator} utility.
 */
@RunWith(ScenarioRunner.class)
public class PortLayoutIteratorTest {
	static final int STEP_SIZE = 20;

	@Rule
	public final TestName name = new TestName();

	private Rectangle rect = new Rectangle(20, 20, 70, 50);

	private ILocationIterator fixture;

	private Collection<Point> obstacles;


	/**
	 * A complex scenario asserting the entire iteration pattern.
	 */
	@Scenario({ "downLeft", "downRight", "upLeft", "upRight", "bottom", "top", "whatever" })
	public void iterationPattern() {
		// Walk down the left side from the middle
		Point[] steps = step(3);

		if (verificationPoint()) {
			assertThat(steps[0], near(20, 45, 1));
			assertThat(steps[1], near(20, 65, 1));

			// We didn't go around the corner
			assertThat(steps[2], not(adjacent(20, 70)));
		}

		// This is the middle of the right side
		Point first = steps[2];
		// Continue down the right side
		steps = step(2);

		if (verificationPoint()) {
			assertThat(first, near(90, 45, 1));
			assertThat(steps[0], near(90, 65, 1));

			// We didn't go around the corner
			assertThat(steps[1], not(adjacent(90, 70)));
		}

		// This is the above the middle of the left side
		first = steps[1];
		// Continue up the left side
		steps = step(1);

		if (verificationPoint()) {
			assertThat(first, near(20, 25, 1));

			// We didn't go around the corner
			assertThat(steps[0], not(adjacent(20, 20)));
		}

		// This is the above the middle of the right side
		first = steps[0];
		// Continue up the right side
		steps = step(1);

		if (verificationPoint()) {
			assertThat(first, near(90, 25, 1));

			// We didn't go around the corner
			assertThat(steps[0], not(adjacent(90, 20)));
		}

		// This is near the left of the bottom side
		first = steps[0];
		// Continue along the bottom
		steps = step(3);

		if (verificationPoint()) {
			assertThat(first, near(40, 70, 1));
			assertThat(steps[0], near(60, 70, 1));
			assertThat(steps[1], near(80, 70, 1));

			// We didn't go around the corner
			assertThat(steps[2], not(adjacent(90, 70)));
		}

		// This is near the left of the top side
		first = steps[2];
		// Continue along the top
		steps = step(7);

		if (verificationPoint()) {
			assertThat(first, near(40, 20, 1));
			assertThat(steps[0], near(60, 20, 1));
			assertThat(steps[1], near(80, 20, 1));
		}

		// The pattern has now run its course so we just keep going
		// on whatever path (which happens to be around clockwise)
		if (verificationPoint()) {
			assertThat(steps[2], near(90, 30, 1));
			assertThat(steps[3], near(90, 50, 1));
			assertThat(steps[4], near(90, 70, 1));
			assertThat(steps[5], near(70, 70, 1));
			assertThat(steps[6], near(50, 70, 1));
		}
	}

	@Test
	public void obstacles() {
		obstacles = Arrays.asList(
				new Point(20, 45), // The first in the pattern
				new Point(90, 65),
				new Point(20, 25),
				new Point(40, 70));

		Point[] steps = step(4);

		// The first iteration landed on an obstacle
		assertThat(steps[0], near(20, 65, 1));
		assertThat(steps[1], near(90, 45, 1));
		// Another obstacle here
		// Another here another immediately
		assertThat(steps[2], near(90, 25, 1));
		// And one more obstacle here
		assertThat(steps[3], near(60, 70, 1));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = PortLayoutIterator.on(rect, this::shouldSkip);
	}

	boolean shouldSkip(Point location) {
		return (obstacles != null) && obstacles.contains(location);
	}

	Point[] step(int times) {
		return IntStream.range(0, times)
				.mapToObj(__ -> fixture.next(STEP_SIZE))
				.toArray(Point[]::new);
	}

	static Matcher<Point> adjacent(int x, int y) {
		return new BaseMatcher<Point>() {
			@Override
			public void describeTo(Description description) {
				// Use %s to avoid commas and other formatting niceties
				description.appendText(String.format("adjacent to (%s, %s)", x, y));
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof Point) {
					Point point = (Point) item;
					result = (point.x() == x) || (point.y() == y);
				}

				return result;
			}
		};
	}
}
