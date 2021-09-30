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
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.LinearIterator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link LinearIterator} utility.
 */
public class LinearIteratorTest {

	@Rule
	public final AnnotationRule<Boolean> customDirection = AnnotationRule.createExists(Direction.class);

	@Rule
	public final AnnotationRule<Double> direction = AnnotationRule.create(Direction.class);

	private Point start = new Point(40, 40);
	private ILocationIterator fixture;

	/**
	 * Tests iteration the default direction.
	 */
	@Test
	public void iterationInDefaultDirection() {
		// The default direction is due southeast
		assertThat(fixture.next(20), near(54, 54, 1));
		assertThat(fixture.next(30), near(75, 75, 1));

		// Stepping backwards
		assertThat(fixture.next(-50), near(40, 40, 1));
	}

	/**
	 * Tests iteration in a non-default direction.
	 */
	@Test
	@Direction(-Math.PI / 6.0)
	public void iterationUpAndRight30Degrees() {
		assertThat(fixture.next(20), near(57, 30, 1));
		assertThat(fixture.next(30), near(83, 15, 1));

		// Stepping backwards
		assertThat(fixture.next(-50), near(40, 40, 1));
	}

	//
	// Test framework
	//

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Direction {
		double value();
	}

	@Before
	public void createFixture() {
		Double direction = this.direction.get();
		if (!customDirection.get()) {
			fixture = LinearIterator.from(start);
		} else {
			fixture = LinearIterator.from(start, direction.doubleValue());
		}
	}
}
