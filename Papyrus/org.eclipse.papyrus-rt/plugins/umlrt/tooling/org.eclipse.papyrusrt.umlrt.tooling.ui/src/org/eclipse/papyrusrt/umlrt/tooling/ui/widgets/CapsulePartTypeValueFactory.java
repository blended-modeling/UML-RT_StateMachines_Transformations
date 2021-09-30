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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * 
 */
public class CapsulePartTypeValueFactory extends AbstractElementTypeBasedValueFactory {

	public CapsulePartTypeValueFactory(EReference eReference) {
		super(eReference);
	}

	protected IElementType chooseElementType(Control widget) {
		return ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.CAPSULE_ID);
	}

	@Override
	protected EObject getVirtualElementToCreate() {
		// use a trick here to be able to create a capsule only in a package, and not in a classifier, as a standard Class could do.
		return UMLFactory.eINSTANCE.createPackage();
	}


}
