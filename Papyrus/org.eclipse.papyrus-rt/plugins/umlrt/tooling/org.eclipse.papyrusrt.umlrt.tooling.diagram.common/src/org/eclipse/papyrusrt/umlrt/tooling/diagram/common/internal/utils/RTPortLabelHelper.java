/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.helper.PortLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils;
import org.eclipse.uml2.uml.Port;

/**
 * UMLRT Port Label Helper.
 * 
 * @author Young-Soo
 *
 */
public class RTPortLabelHelper extends PortLabelHelper {

	/**
	 * Instance.
	 */
	private static final RTPortLabelHelper INSTANCE = new RTPortLabelHelper();

	/**
	 * Constructor.
	 *
	 */
	protected RTPortLabelHelper() {
		super();
	}

	public static RTPortLabelHelper getInstance() {
		return INSTANCE;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.helper.PortLabelHelper#parseString(org.eclipse.gef.GraphicalEditPart, java.util.Collection)
	 *
	 * @param editPart
	 * @param maskValues
	 * @return
	 */
	@Override
	protected String parseString(GraphicalEditPart editPart, Collection<String> maskValues) {
		String result;

		Port port = getUMLElement(editPart);

		if (port == null) {
			result = super.parseString(editPart, maskValues);
		} else {
			Collection<String> newMaskValues = new ArrayList<>(maskValues);
			// Do not show multiplicity for a multiplicity [1] part
			if (!RTPropertyUtils.isReplicated(port) && !RTPropertyUtils.isOptional(port)) {
				newMaskValues.remove(ICustomAppearance.DISP_MULTIPLICITY);
			}
			result = super.parseString(editPart, newMaskValues);
		}
		return result;
	}
}
