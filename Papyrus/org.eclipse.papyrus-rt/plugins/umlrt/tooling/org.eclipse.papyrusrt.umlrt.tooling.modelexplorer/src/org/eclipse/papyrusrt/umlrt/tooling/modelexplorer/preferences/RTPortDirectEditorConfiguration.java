/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.preferences;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.extensionpoints.editors.configuration.AbstractBasicDirectEditorConfiguration;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Specific editor for UML-RT Port. By default, this is a simple direct editor.
 */
public class RTPortDirectEditorConfiguration extends AbstractBasicDirectEditorConfiguration {


	@Override
	public String getTextToEdit(final Object objectToEdit) {
		if (objectToEdit instanceof NamedElement) {
			return ((NamedElement) objectToEdit).getName();
		}

		return super.getTextToEdit(objectToEdit);
	}

	@Override
	public IParser createDirectEditorParser() {
		return new UMLRTElementDirectEditorParser(getTextToEdit(objectToEdit));
	}



}
