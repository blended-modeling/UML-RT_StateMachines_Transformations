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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.uml.textedit.property.xtext.ui.contributions.PropertyXtextDirectEditorConfiguration;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.uml2.uml.Property;

/**
 * Specific editor for UML-RT Port. By default, this is a simple direct editor.
 */
public class CapsulePartDirectEditorConfiguration extends PropertyXtextDirectEditorConfiguration {


	@Override
	public String getTextToEdit(final Object objectToEdit) {


		if (CapsulePartUtils.isCapsulePart((Property) objectToEdit)) {
			return ((Property) objectToEdit).getName();
		} else {
			return super.getTextToEdit(objectToEdit);
		}
	}

	@Override
	public IParser createParser(final EObject semanticObject) {
		if (CapsulePartUtils.isCapsulePart((Property) semanticObject)) {
			return new UMLRTElementDirectEditorParser(getTextToEdit(objectToEdit));
		} else {
			return super.createParser(semanticObject);
		}

	}




}
