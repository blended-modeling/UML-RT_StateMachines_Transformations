/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 491543, 479628, 512955
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElementFactory;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

public class UMLRTExtModelElementFactory extends UMLModelElementFactory {

	@Override
	protected UMLModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		EObject source = EMFHelper.getEObject(sourceElement);
		UMLModelElement modelElement = null;
		if (source != null) {
			// should check here for RT specific Elements?
			if ((source instanceof Classifier) && CapsuleUtils.isCapsule((Classifier) source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof Collaboration && ProtocolUtils.isProtocol(source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof Port && RTPortUtils.isRTPort(source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof Property && CapsulePartUtils.isCapsulePart((Property) source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof Interface && MessageSetUtils.isRTMessageSet((Interface) source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if ((source instanceof Operation) && RTMessageUtils.isRTMessage(source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (RTMessageUtils.isRTMessageParameter(source)) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof org.eclipse.uml2.uml.Package) {
				modelElement = new UMLRTExtModelElement(source);
			} else if (source instanceof org.eclipse.uml2.uml.Transition) {
				modelElement = new UMLRTExtModelElement(source);
			} else {
				modelElement = super.doCreateFromSource(sourceElement, context);
			}
		} else {
			Activator.log.warn("Unable to resolve the selected element to an EObject"); //$NON-NLS-1$
		}

		return modelElement;
	}

}
