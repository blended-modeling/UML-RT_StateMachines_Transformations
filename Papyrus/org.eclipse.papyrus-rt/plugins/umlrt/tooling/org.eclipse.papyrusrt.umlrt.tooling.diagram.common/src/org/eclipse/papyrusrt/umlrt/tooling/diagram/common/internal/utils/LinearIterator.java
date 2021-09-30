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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils;

import java.util.function.Predicate;

import org.eclipse.draw2d.geometry.Point;

/**
 * An iterator of positions marching along a straight path within the interior of
 * a shape.
 */
public class LinearIterator implements ILocationIterator {
	private final double unitX;
	private final double unitY;
	private final Point origin;
	private double current;
	private Predicate<? super Point> hitDetect;

	private LinearIterator(double angle, Point start) {
		super();

		this.unitX = Math.cos(angle);
		this.unitY = Math.sin(angle);
		this.origin = start.getCopy();

	}

	private LinearIterator(double angle, Point start, Predicate<? super Point> hitDetect) {
		super();

		this.unitX = Math.cos(angle);
		this.unitY = Math.sin(angle);
		this.origin = start.getCopy();
		this.hitDetect = hitDetect;

	}

	/**
	 * Creates a straight path-iterator that starts at the given location on an edit-part
	 * and marches in the direction given by the specified angle, in radians.
	 * Because the Y coordinate in a diagram is the negative of the mathematical
	 * convention, so does this angle measure clockwise instead of anticlockwise.
	 * 
	 * @param startingAt
	 *            the point from which to start marching
	 * @param direction
	 *            the angular direction, in radians, in which to march
	 * 
	 * @return an iterator of positions from the starting point
	 */
	public static LinearIterator from(Point startingAt, double direction) {
		return new LinearIterator(direction, startingAt);
	}


	public static LinearIterator from(Point startingAt, double direction, Predicate<? super Point> hitDetect) {
		return new LinearIterator(direction, startingAt, hitDetect);
	}

	/**
	 * Creates a straight path-iterator that starts at the given location on an edit-part
	 * and marches in a due southeast direction.
	 * 
	 * @param startingAt
	 *            the point from which to start marching
	 * 
	 * @return an iterator of positions from the starting point
	 */
	public static LinearIterator from(Point startingAt) {
		return from(startingAt, Math.PI / 4.0);
	}

	/**
	 * Creates a straight path-iterator that starts at the given location on an edit-part
	 * and use a predicate to calculate the next location
	 * 
	 * @param startingAt
	 *            the point from which to start marching
	 * 
	 * @param hitDetect
	 *            a
	 *            hit detection predicate that determines whether a proposed
	 *            location is already occupied and the iteration should skip
	 *            to the next
	 * 
	 * @return an iterator of positions from the starting point
	 */
	public static LinearIterator from(Point startingAt, Predicate<? super Point> hitDetect) {
		return from(startingAt, Math.PI / 4.0, hitDetect);
	}


	@Override
	public Point next(double distance) {

		// used when create a new child element (when location must be calculated:
		// skip to the next location if the new calculated location is already occuped)
		if (hitDetect != null) {

			Point here = where();
			do {
				current = current + distance;
				here = where();

			} while (hitDetect.test(here));
			return here;


		} else {
			// used for drop (when location is already defined)
			current = current + distance;
			return where();

		}
	}

	@Override
	public Point where() {
		return origin.getTranslated(unitX * current, unitY * current);
	}
}
