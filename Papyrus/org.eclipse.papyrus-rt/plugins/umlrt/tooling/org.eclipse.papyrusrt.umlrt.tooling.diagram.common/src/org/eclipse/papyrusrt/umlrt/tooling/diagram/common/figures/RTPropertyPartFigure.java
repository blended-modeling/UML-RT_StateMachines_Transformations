/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PropertyPartFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils;
import org.eclipse.swt.graphics.Color;


/**
 * Figure for the Property Part in Real Time context (used for the CapsulePart for example)
 * 
 * @author Céline JANSSENS
 *
 */
public class RTPropertyPartFigure extends PropertyPartFigure implements IStackedFigure {


	/**
	 * Horizontal offset in pixel
	 */
	private double xOffSet;
	/**
	 * Vertical offset in pixel
	 */
	private double yOffset;
	/**
	 * Number of Layer
	 */
	private int layerNumber;

	/**
	 * Path of the pattern to use
	 */
	private String pathPattern;


	/**
	 * Boolean that test if the figure should be fill with hashed pattern
	 */
	private boolean hashed;

	/**
	 * Stack boolean
	 */
	private boolean stack;

	/**
	 * Layer Opacity
	 */
	private int opacity;

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure#paintBorder(org.eclipse.draw2d.Graphics)
	 *
	 * @param graphics
	 */
	@Override
	protected void paintBorder(Graphics graphics) {
		if (!isStack()) {
			// no border for stacked figure
			super.paintBorder(graphics);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure#paintFigure(org.eclipse.draw2d.Graphics)
	 *
	 */
	@Override
	public void paintFigure(Graphics graphics) {

		if (isStack()) {
			// Paint stack before the figure
			paintStackFigure(graphics);
		} else {
			// paint figure
			super.paintFigure(graphics);
		}

		// paint hash pattern
		paintHashPattern(graphics);
	}

	/**
	 * Paint stacked figure
	 */
	@Override
	public void paintStackFigure(Graphics graphics) {

		graphics.pushState();
		if (isStack()) {
			if (getLineStyle() == Graphics.LINE_CUSTOM) {
				graphics.setLineDash(getCustomDash());
			}
			DrawFigureUtils.paintStack(this, graphics);
		}
		graphics.popState();
	}

	/**
	 * Paint the Hash Figure on the CapsulePart
	 * 
	 * @param graphics
	 *            the graphics of the figure
	 * @deprecated To implemented into Papyrus RoundedCompartment
	 */
	@Deprecated
	public void paintHashPattern(final Graphics graphics) {

		if (hashed) {
			DrawFigureUtils.paintPattern(this, graphics, getLayerOpacity(), getPathPattern());
		}
	}


	/**
	 * getter of hashed
	 * 
	 * @return hashed boolean value
	 */
	public boolean isHashed() {
		return hashed;

	}

	/**
	 * Getter of isStack
	 * 
	 * @return isStack
	 */
	@Override
	public boolean isStack() {
		return stack;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure#paintFigure(org.eclipse.draw2d.Graphics)
	 *
	 */
	@Override
	public void setStack(boolean isStack) {
		this.stack = isStack;
	}


	/**
	 * Setter of hashed
	 * 
	 * @param hashed
	 *            the hashed to set
	 */
	public void setHashed(boolean hashed) {
		this.hashed = hashed;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerNumber()
	 *
	 */
	@Override
	public int getLayerNumber() {

		return layerNumber;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getXOffSet()
	 * 
	 */
	@Override
	public double getXOffSet() {

		return xOffSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getYOffSet()
	 * 
	 */
	@Override
	public double getYOffSet() {

		return yOffset;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setXOffSet(double)
	 * 
	 */
	@Override
	public void setXOffSet(double x) {
		xOffSet = x;

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setYOffSet(double)
	 *
	 */
	@Override
	public void setYOffSet(double y) {
		yOffset = y;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setLayerNumber(int)
	 *
	 */
	@Override
	public void setLayerNumber(int numberOfLayer) {
		layerNumber = numberOfLayer;

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerColor()
	 * 
	 */
	@Override
	public Color getLayerColor() {
		return getBackgroundColor();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerOpacity()
	 * 
	 */
	@Override
	public int getLayerOpacity() {
		return this.opacity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerLineWidth()
	 *
	 */
	@Override
	public int getLayerLineWidth() {
		return 1;
	}

	/**
	 * Get Path pattern
	 * 
	 * @return the pathPattern
	 */
	public String getPathPattern() {
		return pathPattern;
	}

	/**
	 * Set Path Pattern
	 * 
	 * @param pathPattern
	 *            the pathPattern to set
	 */
	public void setPathPattern(String pathPattern) {
		this.pathPattern = pathPattern;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setLayerOpacity(int)
	 */
	@Override
	public void setLayerOpacity(int opacity) {
		this.opacity = opacity;

	}




}
