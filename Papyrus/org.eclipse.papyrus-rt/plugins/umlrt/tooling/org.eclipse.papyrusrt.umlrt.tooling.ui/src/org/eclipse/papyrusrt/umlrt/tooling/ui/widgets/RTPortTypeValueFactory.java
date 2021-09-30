/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.UMLFactory;

/**
 */
public class RTPortTypeValueFactory extends AbstractElementTypeBasedValueFactory {

	public RTPortTypeValueFactory(EReference eReference) {
		super(eReference);
	}

	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		return objectsToValidate;
	}

	protected IElementType chooseElementType(Control widget) {
		return ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_ID);
	}

	@Override
	protected EObject getVirtualElementToCreate() {
		return UMLFactory.eINSTANCE.createPackage();
	}
}
