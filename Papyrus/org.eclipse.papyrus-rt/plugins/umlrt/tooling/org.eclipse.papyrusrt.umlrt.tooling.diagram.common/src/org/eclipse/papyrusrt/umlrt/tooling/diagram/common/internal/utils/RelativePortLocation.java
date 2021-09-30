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

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.eclipse.draw2d.PositionConstants.EAST;
import static org.eclipse.draw2d.PositionConstants.NORTH;
import static org.eclipse.draw2d.PositionConstants.SOUTH;
import static org.eclipse.draw2d.PositionConstants.WEST;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.Bounds;

/**
 * A representation of the relative location of a port on the border of a shape.
 */
public final class RelativePortLocation {

	private final int side;
	private final int relativePosition;

	/**
	 * Initializes me.
	 *
	 * @param side
	 *            the side of the parent shape on which the port is located.
	 *            One of {@link PositionConstants#NORTH}, {@link PositionConstants#WEST},
	 *            {@link PositionConstants#SOUTH}, {@link PositionConstants#EAST}
	 * 
	 * @param relativePosition
	 *            the percentage across or down, as appropriate to the
	 *            {@code side}, at which the centre-point of the port is located.
	 *            Between 0 and 100, inclusive
	 */
	private RelativePortLocation(int side, int relativePosition) {
		super();

		this.side = side;
		this.relativePosition = relativePosition;
	}

	/**
	 * Computes the relative position of a port on a shape.
	 * 
	 * @param portBounds
	 *            the port bounds
	 * @param onShape
	 *            the bounds of the shape that it's on
	 * 
	 * @return the relative position
	 */
	public static RelativePortLocation of(Rectangle portBounds, Rectangle onShape) {
		Point portCenter = portBounds.getCenter();

		int side = getSide(portCenter, onShape);
		double relativePosition;

		switch (side) {
		case WEST:
		case EAST:
			relativePosition = min(max(0.0, portCenter.preciseY()), onShape.preciseHeight()) * 100
					/ onShape.preciseHeight();
			break;
		case NORTH:
		case SOUTH:
			relativePosition = min(max(0, portCenter.preciseX()), onShape.preciseWidth()) * 100
					/ onShape.preciseWidth();
			break;
		default:
			throw new IllegalStateException("Could not compute side of relative port location"); //$NON-NLS-1$
		}

		return new RelativePortLocation(side, (int) Math.round(relativePosition));
	}

	/**
	 * Computes the relative position of a port on a shape.
	 * 
	 * @param portBounds
	 *            the port bounds
	 * @param onShape
	 *            the bounds of the shape that it's on
	 * 
	 * @return the relative position
	 */
	public static RelativePortLocation of(Bounds portBounds, Bounds onShape) {
		return of(new Rectangle(portBounds.getX(), portBounds.getY(), portBounds.getWidth(), portBounds.getHeight()),
				new Rectangle(onShape.getX(), onShape.getY(), onShape.getWidth(), onShape.getHeight()));
	}

	/**
	 * Computes the side of a rectangle on which a point lies.
	 * 
	 * @param portCenter
	 *            a point to locate on a rectangle
	 * @param onShape
	 *            the rectangle on which to locate it
	 * 
	 * @return the side of the rectangle (north, south, west, east) on
	 *         which the point lies
	 */
	static int getSide(Point portCenter, Rectangle onShape) {
		int result;

		// Where is the centre of the port?
		int closenessToLeft = abs(portCenter.x());
		int closenessToTop = abs(portCenter.y());
		int closenessToRight = abs(onShape.width() - portCenter.x());
		int closenessToBottom = abs(onShape.height() - portCenter.y());

		if (closenessToLeft < closenessToRight) {
			// It's left-ish, but maybe top or bottom
			if (closenessToBottom < closenessToTop) {
				if (closenessToBottom < closenessToLeft) {
					result = SOUTH;
				} else {
					result = WEST;
				}
			} else {
				if (closenessToTop < closenessToLeft) {
					result = NORTH;
				} else {
					result = WEST;
				}
			}
		} else {
			// It's right-ish, but maybe top or bottom
			if (closenessToBottom < closenessToTop) {
				if (closenessToBottom < closenessToRight) {
					result = SOUTH;
				} else {
					result = EAST;
				}
			} else {
				if (closenessToTop < closenessToRight) {
					result = NORTH;
				} else {
					result = EAST;
				}
			}
		}

		return result;
	}

	/**
	 * Queries the side of the parent shape on which the port is located.
	 * 
	 * @return one of {@link PositionConstants#NORTH}, {@link PositionConstants#WEST},
	 *         {@link PositionConstants#SOUTH}, {@link PositionConstants#EAST}
	 */
	public final int side() {
		return side;
	}

	/**
	 * Queries the percentage across or down, as appropriate to the
	 * {@linkplain #side() side}, at which the centre-point of the
	 * port is located.
	 * 
	 * @return between 0 and 100, inclusive
	 */
	public final int relativePosition() {
		return relativePosition;
	}

	/**
	 * Obtains the point on a shape's {@code bounds} that is the absolute
	 * location that I indicate.
	 * 
	 * @param bounds
	 *            a shape's bounds
	 * @param portSize
	 *            the size of the port to locate (I represent the relative position
	 *            of its centre-point)
	 * 
	 * @return the point on the {@code bounds} that I represent, in absolute terms of
	 *         the port's origin corner
	 */
	public Point applyTo(Rectangle bounds, Dimension portSize) {
		Point result = new Point(-portSize.width() / 2, -portSize.height() / 2);

		switch (side()) {
		case WEST:
			result.translate(
					0,
					// Relative position up and down the left side
					bounds.height() * relativePosition() / 100);
			break;
		case NORTH:
			result.translate(
					// Relative position along the top side
					bounds.width() * relativePosition() / 100,
					0);
			break;
		case EAST:
			result.translate(
					bounds.width(),
					// Relative position up and down the right side
					bounds.height() * relativePosition() / 100);
			break;
		case SOUTH:
			result.translate(
					// Relative position along the bottom side
					bounds.width() * relativePosition() / 100,
					bounds.height());
			break;
		default:
			throw new IllegalStateException("invalid side: " + side());
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + relativePosition;
		result = prime * result + side;
		return result;
	}

	/**
	 * Relative port locations are equal if they are on the same side
	 * at the same relative position.
	 *
	 * @param obj
	 *            another object
	 * @return whether they are equivalent relative port locations
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelativePortLocation other = (RelativePortLocation) obj;
		if (relativePosition != other.relativePosition)
			return false;
		if (side != other.side)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String sideString;
		switch (side) {
		case NORTH:
			sideString = "NORTH"; //$NON-NLS-1$
			break;
		case WEST:
			sideString = "WEST"; //$NON-NLS-1$
			break;
		case SOUTH:
			sideString = "SOUTH"; //$NON-NLS-1$
			break;
		case EAST:
			sideString = "EAST"; //$NON-NLS-1$
			break;
		default:
			sideString = "<invalid>"; //$NON-NLS-1$
		}

		return String.format("Port@(%s, %d%%)", sideString, relativePosition);
	}
}
