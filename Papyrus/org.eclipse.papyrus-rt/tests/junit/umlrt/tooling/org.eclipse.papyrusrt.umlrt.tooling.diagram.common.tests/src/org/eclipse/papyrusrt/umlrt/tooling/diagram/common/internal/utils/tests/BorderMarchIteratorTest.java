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

import static org.eclipse.papyrusrt.junit.matchers.GeometryMatchers.near;
import static org.junit.Assert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.BorderMarchIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link BorderMarchIterator} utility.
 */
public class BorderMarchIteratorTest {

	@Rule
	public final AnnotationRule<int[]> startingAt = AnnotationRule.create(StartingAt.class);

	private Rectangle rect = new Rectangle(40, 20, 75, 35);
	private ILocationIterator fixture;

	/**
	 * Tests iteration in a clockwise direction from the top.
	 */
	@Test
	@StartingAt({ 50, 25 }) // Slightly inside the top
	public void iterationFromTop() {
		assertThat("Wrong starting point", fixture.where(), near(50, 20, 1));

		// Going in the clockwise direction
		assertThat(fixture.next(20), near(70, 20, 1));
		assertThat(fixture.next(30), near(100, 20, 1));

		// Going around the corner
		assertThat(fixture.next(20), near(115, 25, 1));
		assertThat(fixture.next(20), near(115, 45, 1));
	}

	/**
	 * Tests iteration in a clockwise direction from the top.
	 */
	@Test
	@StartingAt({ 120, 45 }) // Slightly outside the right
	public void iterationFromRight() {
		assertThat("Wrong starting point", fixture.where(), near(115, 45, 1));

		// Going in the clockwise direction around the corner
		assertThat(fixture.next(20), near(105, 55, 1));
		assertThat(fixture.next(20), near(85, 55, 1));
	}

	/**
	 * Tests iteration in an anticlockwise direction from the left.
	 */
	@Test
	@StartingAt({ 45, 30 }) // Slightly inside the left
	public void iterationFromLeft() {
		assertThat("Wrong starting point", fixture.where(), near(40, 30, 1));

		// Going in the anticlockwise direction
		assertThat(fixture.next(20), near(40, 50, 1));

		// Going around the corner
		assertThat(fixture.next(20), near(55, 55, 1));
		assertThat(fixture.next(20), near(75, 55, 1));
	}

	/**
	 * Tests iteration in an anticlockwise direction from the bottom .
	 */
	@Test
	@StartingAt({ 80, 50 }) // Slightly inside the bottom
	public void iterationFromBottom() {
		assertThat("Wrong starting point", fixture.where(), near(80, 55, 1));

		// Going in the anticlockwise direction
		assertThat(fixture.next(20), near(100, 55, 1));

		// And around the corner
		assertThat(fixture.next(30), near(115, 40, 1));

		// Landing on the next corner and rounding it
		assertThat(fixture.next(20), near(115, 20, 1));

		// Still going anticlockwise
		assertThat(fixture.next(20), near(95, 20, 1));
	}

	//
	// Test framework
	//

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface StartingAt {
		int[] value();
	}

	@Before
	public void createFixture() {
		fixture = BorderMarchIterator.from(
				new Point(startingAt.get()[0], startingAt.get()[1]),
				rect);
	}
}
