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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;

/**
 * Property tester for UML-RT edit-parts.
 */
public class EditPartPropertyTester extends PropertyTester {

	private static final String PROPERTY_IS_INHERITED = "isInherited";

	public EditPartPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		switch (property) {
		case PROPERTY_IS_INHERITED:
			result = isInherited(TypeUtils.as(receiver, IInheritableEditPart.class)) == asBoolean(expectedValue, true);
			break;
		}

		return result;
	}

	boolean asBoolean(Object value, boolean defaultValue) {
		return (value instanceof Boolean)
				? (Boolean) value
				: (value == null)
						? defaultValue
						: Boolean.valueOf(String.valueOf(value));
	}

	protected boolean isInherited(IInheritableEditPart editPart) {
		// Dependent children are not "inherited" in their own right
		return (editPart != null) && editPart.canInherit() && !editPart.isDependentChild();
	}
}
