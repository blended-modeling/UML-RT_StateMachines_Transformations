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
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils;
import org.eclipse.swt.graphics.Color;

/**
 * Interface for Stacked Figure
 * Figure who needs to draw several layers of the main figure should implement this Interface.
 * 
 * Then call the paintStackFigure before painting the Figure.
 * 
 * @see {@link DrawFigureUtils}
 * 
 * @author Céline JANSSENS
 *
 */
public interface IStackedFigure {

	/**
	 * CSS and NamedStyle property Name
	 */
	public static final String STACK_PROPERTY_NAME = "isMultiple";// $NON-NLS-1$

	/**
	 * Main entry point for painting a stack figure
	 * 
	 * @param graphics
	 *            The current graphics
	 */
	public void paintStackFigure(final Graphics graphics);

	/**
	 * Define the number of layers in addition of the main figure
	 * Example return 2 if you need 2 layers and the original Figure on top.
	 * 
	 * @return the number of Layer required
	 */
	public int getLayerNumber();

	/**
	 * Define the number of layer in addition of the main figure
	 * Example return 2 if you need 2 layers and the original Figure on top.
	 * 
	 * @param numberOfLayer
	 *            the number of Layer required
	 */
	public void setLayerNumber(final int numberOfLayer);

	/**
	 * Get the Horizontal offset between each layer
	 * 
	 * @return the Horizontal offset between each layer
	 */
	public double getXOffSet();

	/**
	 * Get the Vertical offset between each layer
	 * 
	 * @return the Vertical offset between each layer
	 */
	public double getYOffSet();


	/**
	 * Set the Horizontal offset between each layer
	 * 
	 * @param x
	 *            the Horizontal offset between each layer
	 */
	public void setXOffSet(final double x);

	/**
	 * Set the Vertical offset between each layer
	 * 
	 * @param y
	 *            the Vertical offset between each layer
	 */
	public void setYOffSet(final double y);

	/**
	 * Define if the figure that implements this Interface should be stacked
	 * 
	 * @return true if the figure should be stacked.
	 */
	public boolean isStack();

	/**
	 * Set if the figure that implements this Interface should be stacked
	 * 
	 * @param isStack
	 *            True if the figure should be true.
	 */
	public void setStack(final boolean isStack);

	/**
	 * Retrieve the layer Color
	 * 
	 * @return the Color of the Layer
	 */
	public Color getLayerColor();

	/**
	 * Get the Layer opacity
	 * 
	 * @return The percentage of Opacity 0 is completely transparent , 100 is fully opaque
	 */
	public int getLayerOpacity();

	/**
	 * Set the Layer opacity
	 * 
	 * @param The
	 *            percentage of Opacity 0 is completely transparent , 100 is fully opaque
	 */
	public void setLayerOpacity(final int opacity);

	/**
	 * Get the Layer line width
	 * 
	 * @return the layer line width (in pixel)
	 */
	public int getLayerLineWidth();
}
