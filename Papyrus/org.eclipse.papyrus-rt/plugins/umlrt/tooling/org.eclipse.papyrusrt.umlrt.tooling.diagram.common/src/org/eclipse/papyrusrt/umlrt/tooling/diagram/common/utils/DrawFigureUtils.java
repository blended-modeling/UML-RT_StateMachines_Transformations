/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Ansgar Radermacher (CEA)  ansgar.radermacher@cea.fr - improvements of stacking figure rendering
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.csscopy.CSS2ColorHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.w3c.dom.css.RGBColor;

/**
 * Utils for all the Figure Drawing
 * 
 * @author Céline JANSSENS
 *
 */
public class DrawFigureUtils {

	/**
	 * Holder class for common offset data
	 */
	public static class Offsets {

		/**
		 * x and y offsets, could be negative
		 */
		protected int xOffSet, yOffSet;

		/**
		 * positive variants of x and y offsets
		 */
		protected int xDelta, yDelta;

		/**
		 * number of layers
		 */
		protected int nl;
	}

	/**
	 * Paint different layers including main figure to form a kind of stack.
	 * 
	 * @param figure
	 *            The original figure
	 * 
	 * @param graphics
	 *            the current graphics
	 * 
	 */
	public static void paintStack(final IStackedFigure figure, final Graphics graphics) {

		if ((figure instanceof NodeFigure) && (figure instanceof IRoundedRectangleFigure)) {

			Rectangle bounds = ((NodeFigure) figure).getBounds();
			Rectangle clip = bounds.getCopy();
			IRoundedRectangleFigure roundedFigure = (IRoundedRectangleFigure) figure;

			int nl = figure.getLayerNumber();

			// Calculate the offsets
			Offsets o = getOffsets(figure);

			// reduce width and height of the rectangle, calculate coordinates in loop
			Rectangle layer = new Rectangle(
					0, 0, bounds.width - o.xDelta, bounds.height - o.yDelta);

			for (int i = nl; i >= 0; i--) {

				// Translate the layer of the Offset, reduce width and height
				layer.setLocation(bounds.x, bounds.y);
				layer.translate(getTranslation(i, o));

				graphics.pushState();

				// Calculate the Clip
				Rectangle clipRectangle = graphics.getClip(clip).getUnion(layer).expand(1, 1);
				graphics.setClip(clipRectangle.getCopy());

				int rx = 0;
				int ry = 0;
				if (roundedFigure.getCornerDimensions().width > o.xDelta) {
					rx = roundedFigure.getCornerDimensions().width - o.xDelta;
				}
				if (roundedFigure.getCornerDimensions().height > 0) {
					ry = roundedFigure.getCornerDimensions().height - o.yDelta;
				}
				if (i == 0) {
					// 0 == original figure, fill only this one
					graphics.setBackgroundColor(figure.getLayerColor());
					graphics.fillRoundRectangle(layer, rx, ry);
					replicateBackground((NodeFigure) figure, graphics, layer);
				}

				graphics.popState();

				// draw the layer
				graphics.setLineWidth(figure.getLayerLineWidth());
				graphics.drawRoundRectangle(layer, rx, ry);
			}
		}
	}

	/**
	 * Fill the Background of the current graphics
	 * Fill a gradient if the figure has gradient
	 * Fill with a color if not.
	 * 
	 * @param figure
	 *            the original figure (must be a NodeFigure)
	 * @param graphics
	 *            the current graphic of the layer
	 * @param layer
	 *            the bounds of the current layer
	 */
	public static void replicateBackground(final NodeFigure figure, final Graphics graphics, Rectangle layer) {

		if ((figure).isUsingGradient()) {
			// if figure has gradient, fill the layer with the gradient
			replicateGradient(figure, graphics, layer);
		} else {
			// otherwise fill the background of the layer with the same color.
			graphics.setBackgroundColor((figure).getBackgroundColor());
		}

	}




	/**
	 * Fill the Gradient of the graphics based on the figure gradient
	 * 
	 * @param figure
	 *            The based figure to replicate the gradient
	 * @param graphics
	 *            the graphics on which the gradient is set
	 * @param rectangle
	 *            The portion of the graphics filled.
	 */
	public static void replicateGradient(final NodeFigure figure, final Graphics graphics, final Rectangle rectangle) {

		if (figure instanceof NodeFigure) {

			fillGradient(
					getColor(figure.getGradientColor2()),
					getColor(figure.getGradientColor1()),
					graphics,
					rectangle,
					figure.getGradientStyle() == 0);
		}
	}


	/**
	 * Get the Color from the integer color
	 * 
	 * @param integerColor
	 */
	public static Color getColor(int integerColor) {
		return ColorRegistry.getInstance().getColor(integerColor);

	}

	/**
	 * Fill the gradient of a Graphics
	 * 
	 * @param gradiantColor1
	 *            First Color of the gradient
	 * @param gradientColor2
	 *            Second Color of the gradient
	 * @param graphics
	 *            The graphics to draw the gradient
	 * @param rectangle
	 *            the rectangle area in which the gradient is filled
	 * @param style
	 *            true if vertical gradient
	 */
	public static void fillGradient(final Color gradiantColor1, final Color gradientColor2, final Graphics graphics, final Rectangle rectangle, final boolean style) {
		// Set the first Color of the gradient
		graphics.setForegroundColor(gradiantColor1);
		// Set the second Color of the gradient
		graphics.setBackgroundColor(gradientColor2);
		// Fill the gradient
		graphics.fillGradient(rectangle.getCopy(), style);
	}


	/**
	 * Paint a Pattern on top of a Figure
	 * TODO: Take the rounded corner into account.
	 * To be called AFTER painting the figure.
	 * 
	 * @param figure
	 *            the figure on which the pattern is applied
	 * @param graphics
	 *            The current graphics being drawn
	 * @param alpha
	 *            the transparency to apply to the pattern
	 * @param patternPath
	 *            the path in the UI bundle of the pattern to draw
	 */
	public static void paintPattern(final IRoundedRectangleFigure figure, final Graphics graphics, final int alpha, final String patternPath) {

		// Create the Image of the pattern
		Image hashImage = Activator.getPluginIconImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, patternPath);


		// Get the dimension of the pattern
		int imageWidth = hashImage.getBounds().width;
		int imageHeight = hashImage.getBounds().height;

		Offsets o = getOffsets(figure);

		// Defines how many times the pattern fill the figure horizontally and vertically
		int horizontalTimes = ((figure.getBounds().width() - o.xDelta) / imageWidth) + 1;
		int verticalTimes = ((figure.getBounds().height() - o.yDelta) / imageHeight) + 1;

		// Define the origin point to start the pattern filling
		Point origin = new Point(figure.getBounds().getLocation());
		Rectangle clipRectangle = figure.getBounds().getCopy();

		// Then fill the figure with the pattern and loop horizontally and vertically to fill all the figure with the pattern
		for (int verticalTime = 0; verticalTime < verticalTimes; verticalTime++) {
			for (int horizontalTime = 0; horizontalTime < horizontalTimes; horizontalTime++) {
				graphics.pushState();
				graphics.getClip(clipRectangle);
				clipRectangle.width -= o.xDelta;
				clipRectangle.height -= o.yDelta;
				// main figure corresponds to i = 0 in loop for stacked figure.
				clipRectangle.translate(getTranslation(0, o));
				graphics.setClip(clipRectangle);

				graphics.setAlpha(alpha);
				graphics.drawImage(hashImage, origin);
				graphics.popState();
				// translate the original point accordingly
				origin.translate(imageWidth, 0);

			}
			// Reset the line origin
			origin.setX(figure.getBounds().getLocation().x());
			// Translate the column origin
			origin.translate(0, imageHeight);
		}
	}

	/**
	 * Returns the color from a given string (used in styles).
	 * 
	 * @param stringColor
	 *            the color as String
	 * @return the color found, coming from a registry. It should not be disposed by user.
	 */
	public static Color getColorFromString(String stringColor) {
		Color color = null;
		if (stringColor != null && !"-1".equals(stringColor)) {
			// get the the RGBColor from string
			final RGBColor rgbColor = CSS2ColorHelper.getRGBColor(stringColor);
			if (rgbColor != null) {
				try {
					// extract RGB
					final int red = Integer.parseInt(rgbColor.getRed().toString());
					final int green = Integer.parseInt(rgbColor.getGreen().toString());
					final int blue = Integer.parseInt(rgbColor.getBlue().toString());

					// get the the Color from RGB
					color = ColorRegistry.getInstance().getColor(new RGB(red, green, blue));
				} catch (final NumberFormatException e) {
					Activator.log.error("name label color not well set: " + stringColor, e);
				}
			}

			if (color == null) {
				try {
					color = ColorRegistry.getInstance().getColor(Integer.valueOf(stringColor));
				} catch (final NumberFormatException e) {
					Activator.log.error("Shadow Color not well set", e);
				}
			}
		}

		return color;
	}

	/**
	 * Function returning offsets. Used by stacked figure and pattern
	 * 
	 * @param figure
	 * @return
	 */
	protected static Offsets getOffsets(Object figure) {
		Offsets o = new Offsets();
		if (figure instanceof IStackedFigure) {
			// Calculate the offsets
			o.xOffSet = (int) Math.round(((IStackedFigure) figure).getXOffSet());
			o.yOffSet = (int) Math.round(((IStackedFigure) figure).getYOffSet());
			o.xDelta = Math.abs(o.xOffSet) + 1;
			o.yDelta = Math.abs(o.yOffSet) + 1;
			o.nl = ((IStackedFigure) figure).getLayerNumber();
		} else {
			o.xOffSet = 0;
			o.yOffSet = 0;
			o.xDelta = 0;
			o.yDelta = 0;
			o.nl = 0;
		}
		return o;
	}

	/**
	 * Helper function to return the translation of a stacked figure at a certain index.
	 * Although quite trivial, it's important to ensure the use of consistent coordinates
	 * between a stacked figure and pattern.
	 * 
	 * @param index
	 *            index between 0 and number-of-layers - 1
	 * @param o
	 *            the offset data
	 * @return
	 */
	protected static Point getTranslation(int index, Offsets o) {
		Point translation = new Point(index * o.xOffSet, (o.nl - index) * o.yOffSet);
		return translation;
	}

	/**
	 * Obtains a lighter hue of the given {@code color}. Foreground colors
	 * are lightened to a lesser degree than backgrounds, for visual clarity.
	 * 
	 * @param color
	 *            a color
	 * @param foreground
	 *            whether it is use for a foreground kind of drawing
	 * 
	 * @return the lighter hue
	 */
	public static Color getLighterColor(Color color, boolean foreground) {
		return DiagramColorRegistry.getInstance().getColor(
				getLighterColor(color.getRGB(), foreground));
	}

	/**
	 * Obtains a lighter hue of the given {@code rgb} color. Foreground colors
	 * are lightened to a lesser degree than backgrounds, for visual clarity.
	 * 
	 * @param rgb
	 *            an RGB color specification
	 * @param foreground
	 *            whether it is use for a foreground kind of drawing
	 * 
	 * @return the lighter hue
	 */
	public static RGB getLighterColor(RGB rgb, boolean foreground) {
		if (foreground) {
			return new RGB(
					(rgb.red + (255 - rgb.red) * 3 / 5) & 0xFF,
					(rgb.green + (255 - rgb.green) * 3 / 5) & 0xFF,
					(rgb.blue + (255 - rgb.blue) * 3 / 5) & 0xFF);
		} else {
			return new RGB(
					(rgb.red + (255 - rgb.red) * 3 / 4) & 0xFF,
					(rgb.green + (255 - rgb.green) * 3 / 4) & 0xFF,
					(rgb.blue + (255 - rgb.blue) * 3 / 4) & 0xFF);
		}
	}

	/**
	 * Obtains a lighter hue of the given color. Foreground colors
	 * are lightened to a lesser degree than backgrounds, for visual clarity.
	 * 
	 * @param colorID
	 *            the numeric value of a color
	 * @param foreground
	 *            whether it is use for a foreground kind of drawing
	 * 
	 * @return the lighter hue
	 */
	public static int getLighterColor(int colorID, boolean foreground) {
		return FigureUtilities.RGBToInteger(
				getLighterColor(FigureUtilities.integerToRGB(colorID), foreground));
	}

	/**
	 * Obtains a modified alpha-channel (opacity) value for lighter-impact
	 * rendering of background images or patterns in situations where a
	 * figure uses the {@linkplain #getLighterColor(Color, boolean) lighter background}
	 * colour.
	 * 
	 * @param alpha
	 *            the figure's configured alpha value
	 * @return a suitably lighter alpha value
	 */
	public static int getLighterAlpha(int alpha) {
		return Math.max(2, alpha / 4);
	}
}
