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
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * An iterator of locations around the frame of a capsule for layout of
 * ports. It proceeds
 * <ul>
 * <li>down the left side, starting from the middle, then</li>
 * <li>down the right side, starting from the middle, then</li>
 * <li>up the left side, starting from the middle, then</li>
 * <li>up the right side, starting from the middle, then</li>
 * <li>across the bottom, starting from the left, then</li>
 * <li>across the top, starting from the left, then</li>
 * <li>wherever</li>
 * </ul>
 */
public class PortLayoutIterator implements ILocationIterator {
	private static final int FORWARDS = +1;
	private static final int BACKWARDS = -1;

	private static final int STAGE_LEFT_DOWN = 0;
	private static final int STAGE_RIGHT_DOWN = STAGE_LEFT_DOWN + 1;
	private static final int STAGE_LEFT_UP = STAGE_RIGHT_DOWN + 1;
	private static final int STAGE_RIGHT_UP = STAGE_LEFT_UP + 1;
	private static final int STAGE_BOTTOM = STAGE_RIGHT_UP + 1;
	private static final int STAGE_TOP = STAGE_BOTTOM + 1;
	private static final int STAGE_WHATEVER = STAGE_TOP + 1;

	// The rectangle around which we're placing locations
	private final Rectangle bounds;
	private final Rectangle interior;

	// Determines whether a proposed location hits something
	// already there and we should skip to the next one
	private final Predicate<? super Point> hitDetect;

	// Which stage of the pattern are we in
	private int stage;

	// An iterator that walks the current stage
	private ILocationIterator delegate;

	// The direction in which to walk the delegate
	private int direction;

	// The current location
	private Point preparedNext;

	/**
	 * Initializes me with the bounds of the capsule frame and a
	 * hit detection predicate that determines whether a proposed
	 * location is already occupied and the iteration should skip
	 * to the next.
	 *
	 * @param bounds
	 *            the capsule frame bounds
	 * @param hitDetect
	 *            the hit-detection predicate
	 */
	private PortLayoutIterator(Rectangle bounds, Predicate<? super Point> hitDetect) {
		super();

		this.bounds = bounds.getCopy();
		this.interior = bounds.getShrinked(1, 1);
		this.hitDetect = hitDetect;

		// The first stage starts right on the middle
		// of the left side (no offset)
		advance(STAGE_LEFT_DOWN, 0.0);
	}

	/**
	 * Creates an iterator on the given border that starts at the middle of the
	 * left side and proceeds as described {@linkplain PortLayoutIterator above}.
	 * 
	 * @param border
	 *            the border around which to iterate locations
	 * @param hitDetect
	 *            a
	 *            hit detection predicate that determines whether a proposed
	 *            location is already occupied and the iteration should skip
	 *            to the next
	 * 
	 * @return an iterator of positions around the edit-part
	 */
	public static ILocationIterator on(Rectangle border, Predicate<? super Point> hitDetect) {
		return new PortLayoutIterator(border, hitDetect);
	}

	@Override
	public Point next(double distance) {
		Point result;

		do {
			Point here = where();

			if (preparedNext != null) {
				result = preparedNext;
				preparedNext = null; // Consume it
			} else {
				result = delegate.next(distance * direction);
			}

			// Did we turn a corner? Note that getPosition doesn't work for points inside
			// the rectangle, and the left and top edges are inside it, so use interior
			if (interior.getPosition(result) != interior.getPosition(here)) {
				// Advance to the next stage (if there is a next stage)
				if (advance(stage + 1, distance)) {
					// Start over with the new stage
					result = where();
					preparedNext = null; // Consume it
				}
			}
			// Avoidance of hit detection is only feasible before we get
			// to the 'whatever' stage. Beyond that point, we must simply
			// accept whatever happens
		} while (!whatever() && hitDetect.test(result));

		return result;
	}

	@Override
	public Point where() {
		return delegate.where();
	}

	private boolean advance(int toStage, double offset) {
		boolean result = toStage <= STAGE_WHATEVER;

		switch (toStage) {
		case STAGE_LEFT_DOWN:
			delegate = BorderMarchIterator.from(bounds.getLeft(), bounds);
			direction = FORWARDS;
			break;
		case STAGE_RIGHT_DOWN:
			delegate = BorderMarchIterator.from(bounds.getRight(), bounds);
			direction = FORWARDS;
			break;
		case STAGE_LEFT_UP:
			delegate = BorderMarchIterator.from(bounds.getLeft(), bounds);
			direction = BACKWARDS;
			// Take a step because we already did the centre point
			delegate.next(offset * direction);
			break;
		case STAGE_RIGHT_UP:
			delegate = BorderMarchIterator.from(bounds.getRight(), bounds);
			direction = BACKWARDS;
			// Take a step because we already did the centre point
			delegate.next(offset * direction);
			break;
		case STAGE_BOTTOM:
			// Force an anticlockwise direction
			delegate = BorderMarchIterator.from(bounds.getBottomLeft(), bounds, false);
			direction = FORWARDS;
			// Take a step so that we don't start right on the corner
			delegate.next(offset * direction);
			break;
		case STAGE_TOP:
			// Force a clockwise direction (otherwise it would go anticlockwise,
			// down the left again)
			delegate = BorderMarchIterator.from(bounds.getTopLeft(), bounds, true);
			// Take a step so that we don't start right on the corner
			delegate.next(offset * direction);
			break;
		case STAGE_WHATEVER:
			// Just keep going around from where the previous stage left off
			break;
		}

		if (toStage < STAGE_WHATEVER) {
			stage = toStage;
			preparedNext = delegate.where();
		}

		return result;
	}

	private boolean whatever() {
		return stage >= STAGE_WHATEVER;
	}
}
