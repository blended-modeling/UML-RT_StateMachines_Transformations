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

package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElementFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A factory creating model-elements that use the UML-RT Façade API
 * to access and present properties.
 */
public class UMLRTFacadeModelElementFactory extends UMLModelElementFactory {

	@Override
	protected UMLModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		EObject source = EMFHelper.getEObject(sourceElement);
		UMLRTFacadeModelElement result = null;
		if (source instanceof NamedElement) {
			UMLRTNamedElement element = UMLRTFactory.create((NamedElement) source);

			if (element != null) {
				result = new UMLRTFacadeModelElement(element);
			}
		} else {
			Activator.log.warn("Unable to resolve the selected element to an UMLRTNamedElement"); //$NON-NLS-1$
		}

		return result;
	}

	@Override
	protected void updateModelElement(UMLModelElement modelElement, Object newSourceElement) {
		super.updateModelElement(modelElement, newSourceElement);

		EObject eObject = EMFHelper.getEObject(newSourceElement);
		UMLRTNamedElement element = null;
		if (eObject instanceof NamedElement) {
			element = UMLRTFactory.create((NamedElement) eObject);
		}
		if (element == null) {
			throw new IllegalArgumentException("Cannot resolve UMLRTNamedElement selection: " + newSourceElement);
		}

		updateFacadeModelElement((UMLRTFacadeModelElement) modelElement, element);
	}

	public static void updateFacadeModelElement(UMLRTFacadeModelElement modelElement, UMLRTNamedElement newElement) {
		modelElement.element = newElement;
	}
}
