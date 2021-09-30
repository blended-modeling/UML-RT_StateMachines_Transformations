/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import static org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils.isOptional;
import static org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils.isReplicated;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.helper.PropertyLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTNameEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Property;

/**
 * Custom property label-helper for conventional UML-RT presentation,
 * including especially
 * <ul>
 * <li>exclusion of the {@code [1]} multiplicity for non-replicated ports and parts</li>
 * <li>custom icon images that don't fit the Papyrus label framework</li>
 * </ul>
 */
public class RTPropertyLabelHelper extends PropertyLabelHelper {
	private static final RTPropertyLabelHelper INSTANCE = new RTPropertyLabelHelper();

	public static RTPropertyLabelHelper getInstance() {
		return INSTANCE;
	}

	@Override
	public Property getUMLElement(GraphicalEditPart editPart) {
		return TypeUtils.as(((IGraphicalEditPart) editPart).resolveSemanticElement(), Property.class);
	}

	@Override
	public Image getImage(GraphicalEditPart editPart) {
		return (editPart instanceof IRTNameEditPart)
				? ((IRTNameEditPart) editPart).getRTLabelIcon()
				: super.getImage(editPart);
	}

	@Override
	protected String parseString(GraphicalEditPart editPart, Collection<String> displayValue) {
		String result;

		Property property = getUMLElement(editPart);

		if (property == null) {
			result = super.parseString(editPart, displayValue);
		} else {
			// Do not show multiplicity for a multiplicity [1] part
			if (!isReplicated(property) && !isOptional(property)) {
				displayValue = new ArrayList<>(displayValue);
				displayValue.remove(ICustomAppearance.DISP_MULTIPLICITY);
			}

			result = super.parseString(editPart, displayValue);
		}

		return result;
	}
}
