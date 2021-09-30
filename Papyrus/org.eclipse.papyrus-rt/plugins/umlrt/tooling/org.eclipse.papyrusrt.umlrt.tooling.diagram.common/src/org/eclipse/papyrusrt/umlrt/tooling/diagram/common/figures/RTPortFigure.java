/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net  - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils;
import org.eclipse.swt.graphics.Color;

/**
 * Figure for port RT.
 * 
 * @author Céline JANSSENS
 *
 */
public class RTPortFigure extends PortFigure implements IStackedFigure {

	/**
	 * Horizontal Offset (in Pixel)
	 */
	private double xOffSet;

	/**
	 * vertical Offset (in Pixel)
	 */
	private double yOffSet;

	/**
	 * Number of Layer
	 */
	private int layerNumber;

	/**
	 * Opacity
	 */
	private int opacity;


	/**
	 * Define if the figure should be drawn like a stack
	 */
	private boolean stack;

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
	 * @param graphics
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		if (isStack()) {
			paintStackFigure(graphics);
		} else {
			super.paintFigure(graphics);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerNumber()
	 */
	@Override
	public int getLayerNumber() {

		return layerNumber;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setLayerNumber(int)
	 *
	 * @param numberOfLayer
	 */
	@Override
	public void setLayerNumber(final int numberOfLayer) {
		layerNumber = numberOfLayer;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getXOffSet()
	 */
	@Override
	public double getXOffSet() {

		return xOffSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setXOffSet(double)
	 * 
	 */
	@Override
	public void setXOffSet(final double x) {
		xOffSet = x;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setYOffSet(double)
	 * 
	 */
	@Override
	public void setYOffSet(final double y) {
		yOffSet = y;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getYOffSet()
	 * 
	 */
	@Override
	public double getYOffSet() {

		return yOffSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#isStack()
	 * 
	 */
	@Override
	public boolean isStack() {

		return stack;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#setStack(boolean)
	 * 
	 */
	@Override
	public void setStack(final boolean isStack) {
		stack = isStack;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#paintStackFigure(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paintStackFigure(final Graphics graphics) {
		// paint the stack figure (including original one)
		DrawFigureUtils.paintStack(this, graphics);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerColor()
	 */
	@Override
	public Color getLayerColor() {
		return getBackgroundColor();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerOpacity()
	 */
	@Override
	public int getLayerOpacity() {
		return opacity;
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

	/**
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure#getLayerLineWidth()
	 *
	 * @return
	 */
	@Override
	public int getLayerLineWidth() {
		return 1;
	}


}
