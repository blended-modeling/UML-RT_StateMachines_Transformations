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

import static java.lang.Math.max;
import static java.lang.Math.min;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * An iterator of positions marching around the border of a shape, in either
 * clockwise or anti-clockwise direction.
 */
public class BorderMarchIterator implements ILocationIterator {
	private static final int CLOCKWISE = +1;
	private static final int ANTICLOCKWISE = -1;

	private final Rectangle bounds;
	private final double firstBase;
	private final double secondBase;
	private final double thirdBase;
	private final double perimeter;
	private final double direction;
	private double current;

	private BorderMarchIterator(Rectangle bounds, int direction, Point start) {
		super();

		this.bounds = bounds;
		this.firstBase = bounds.width();
		this.secondBase = firstBase + bounds.height();
		this.thirdBase = secondBase + bounds.width();
		this.perimeter = thirdBase + bounds.height();
		this.direction = direction;
		this.current = where(start);
	}

	/**
	 * Creates a border march that starts at the given location on an edit-part
	 * and marches clockwise if the start is on the top or right side,
	 * anti-clockwise if the start is on the left or bottom side.
	 * 
	 * @param startingAt
	 *            the point from which to start marching
	 * @param onBorder
	 *            the border around which to march
	 * 
	 * @return an iterator of positions around the edit-part from the starting point
	 */
	public static ILocationIterator from(Point startingAt, Rectangle onBorder) {
		Point startRelativeToBorder = startingAt.getTranslated(onBorder.getLocation().getNegated());
		boolean clockwise;
		switch (RelativePortLocation.getSide(startRelativeToBorder, onBorder)) {
		case PositionConstants.NORTH:
		case PositionConstants.EAST:
			clockwise = true;
			break;
		default:
			clockwise = false;
			break;
		}

		return from(startingAt, onBorder, clockwise);
	}

	static ILocationIterator from(Point startingAt, Rectangle onBorder, boolean clockwise) {
		Rectangle bounds = onBorder.getCopy();
		int direction = clockwise ? CLOCKWISE : ANTICLOCKWISE;

		return new BorderMarchIterator(bounds, direction, startingAt);
	}

	@Override
	public Point next(double distance) {
		current = (current + (direction * distance)) % perimeter;
		return where();
	}

	@Override
	public Point where() {
		Point result;

		if (current <= firstBase) {
			// On the top edge
			result = bounds.getTopRight().getTranslated(current - firstBase, 0.0);
		} else if (current <= secondBase) {
			result = bounds.getTopRight().getTranslated(0.0, current - firstBase);
		} else if (current <= thirdBase) {
			result = bounds.getBottomLeft().getTranslated(thirdBase - current, 0.0);
		} else {
			result = bounds.getBottomLeft().getTranslated(0.0, thirdBase - current);
		}

		return result;
	}

	/**
	 * Where on the perimeter, starting from the top-left and going around
	 * clockwise, is a {@code location}?
	 * 
	 * @param location
	 *            a location on the border
	 * 
	 * @return its perimeter distance from the origin
	 */
	private double where(Point location) {
		double result;

		Point relativeToBorder = location.getTranslated(bounds.getLocation().getNegated());
		switch (RelativePortLocation.getSide(relativeToBorder, bounds)) {
		case PositionConstants.NORTH:
			result = max(bounds.x(), min(location.x(), bounds.right())) - bounds.x();
			break;
		case PositionConstants.EAST:
			result = firstBase
					+ max(bounds.y(), min(location.y(), bounds.bottom())) - bounds.y();
			break;
		case PositionConstants.SOUTH:
			result = thirdBase
					- (max(bounds.x(), min(location.x(), bounds.right())) - bounds.x());
			break;
		case PositionConstants.WEST:
			result = perimeter
					- (max(bounds.y(), min(location.y(), bounds.bottom())) - bounds.y());
			break;
		default:
			throw new IllegalArgumentException("cannot compute location of " + location); //$NON-NLS-1$
		}

		return result;
	}
}
