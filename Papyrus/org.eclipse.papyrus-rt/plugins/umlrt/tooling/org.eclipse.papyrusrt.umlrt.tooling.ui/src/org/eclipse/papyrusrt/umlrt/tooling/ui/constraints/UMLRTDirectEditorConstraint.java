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
package org.eclipse.papyrusrt.umlrt.tooling.ui.constraints;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Element;

/**
 * Direct editor constraint for UML-RT context.
 * 
 * @author Young-Soo Roh
 *
 */
public class UMLRTDirectEditorConstraint implements IDirectEditorConstraint {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public UMLRTDirectEditorConstraint() {
	}

	@Override
	public String getLabel() {
		return "Direct editor constraint for UML-RT";
	}

	@Override
	public boolean appliesTo(Object selection) {
		EObject eo = EMFHelper.getEObject(selection);
		if (eo instanceof Element) {
			return UMLRTProfileUtils.isUMLRTProfileApplied((Element) eo);
		}
		return false;
	}

}
