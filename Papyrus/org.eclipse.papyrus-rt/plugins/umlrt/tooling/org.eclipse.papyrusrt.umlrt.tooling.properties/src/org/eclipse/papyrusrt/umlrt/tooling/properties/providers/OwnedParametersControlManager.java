/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.properties.providers;

import org.eclipse.papyrusrt.umlrt.tooling.properties.widget.RTNatTableMultiReferencePropertyEditor.IControlManager;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Operation;

/**
 * A provider of enablement rules for the control buttons on the
 * protocol message parameters table.
 */
public class OwnedParametersControlManager implements IControlManager {

	public OwnedParametersControlManager() {
		super();
	}

	/**
	 * The number and order of parameters may not be changed in an inherited or redefined
	 * protocol message.
	 * 
	 * @param parent
	 *            the parent object
	 * @return {@code true} if the {@code parent} is a root definition of a protocol message;
	 *         {@code false}, otherwise
	 */
	protected boolean canEditParameters(Object parent) {
		return (parent instanceof Operation) && !UMLRTExtensionUtil.isInherited((Operation) parent);
	}

	/**
	 * @see #canEditParameters(Object)
	 */
	@Override
	public boolean canAddElement(Object editedParent) {
		return canEditParameters(editedParent);
	}

	/**
	 * @see #canEditParameters(Object)
	 */
	@Override
	public boolean canMoveElement(Object editedParent, Object object) {
		return canEditParameters(editedParent);
	}

	/**
	 * @see #canEditParameters(Object)
	 */
	@Override
	public boolean canRemoveElement(Object editedParent, Object object) {
		return canEditParameters(editedParent);
	}
}
