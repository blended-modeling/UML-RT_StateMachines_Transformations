/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.decorator;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortOnPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.DecorationEditPolicyEx.TransitionDecoratorTarget;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.Element;

/**
 * A decoration indicating that an element redefines an inherited element.
 */
public class RedefinitionDecorator extends AbstractUMLRTDecorator<IDecoratorTarget> {

	private static final ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
			org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID,
			"$nl$/icons/full/ovr16/redefinition_ovr.png"); //$NON-NLS-1$

	private static final Direction DECORATION_POSITION = Direction.NORTH_EAST;

	private static final int DECORATION_OFFSET = 1;

	// Preferred method for transitions: pixels from target (room for arrow head)
	private static final double CONNECTION_FROM_TARGET = 20.0;

	// Backup method for non-transitions: percentage from source
	private static final int CONNECTION_PERCENT = 90;

	public RedefinitionDecorator(IDecoratorTarget decoratorTarget) {
		super(decoratorTarget);
	}

	@Override
	protected void addDecorations(IGraphicalEditPart editPart, EObject semanticElement) {
		// Don't show the decorations on ports on parts, which are just too small
		if (!(editPart instanceof RTPortOnPartEditPart) && !(editPart instanceof LabelEditPart)) {
			IGraphicalEditPart graphical = editPart;
			EObject semantic = graphical.resolveSemanticElement();

			if (semantic instanceof Element) {
				UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of((Element) semantic);
				if (inheritance == UMLRTInheritanceKind.REDEFINED) {
					// Create the icon figure
					Image icon = ExtendedImageRegistry.INSTANCE.getImage(imageDescriptor);
					IFigure iconFigure = new ImageFigure(icon);
					Dimension iconSize = new Rectangle(icon.getBounds()).getSize();

					// Set the size of the decorator figure
					final IMapMode mm = MapModeUtil.getMapMode(graphical.getFigure());
					iconFigure.setSize(mm.DPtoLP(iconSize.width()), mm.DPtoLP(iconSize.height()));

					IDecoration decoration;
					if (editPart instanceof ConnectionEditPart) {
						IDecoratorTarget decoratorTarget = getDecoratorTarget();
						if (decoratorTarget instanceof TransitionDecoratorTarget) {
							TransitionDecoratorTarget tdt = (TransitionDecoratorTarget) decoratorTarget;
							decoration = tdt.addFixedConnectionTargetDecoration(iconFigure, CONNECTION_FROM_TARGET, false);
						} else {
							decoration = decoratorTarget.addConnectionDecoration(iconFigure, CONNECTION_PERCENT, false);
						}
					} else {
						decoration = getDecoratorTarget().addShapeDecoration(iconFigure, DECORATION_POSITION, -DECORATION_OFFSET, false);
					}

					if (decoration instanceof Figure) {
						((Figure) decoration).setToolTip(new Label("Redefinition of an inherited element"));
					}

					addDecoration(decoration);
				}
			}
		}
	}
}
