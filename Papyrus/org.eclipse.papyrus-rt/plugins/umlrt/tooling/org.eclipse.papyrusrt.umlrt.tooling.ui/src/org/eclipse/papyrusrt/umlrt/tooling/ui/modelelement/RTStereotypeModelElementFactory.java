/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.StereotypeModelElementFactory;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class RTStereotypeModelElementFactory extends StereotypeModelElementFactory {

	private static final String UML_REAL_TIME = "UMLRealTime"; // $NON-NLS-0$

	@Override
	protected EMFModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {

		Element umlElement = UMLUtil.resolveUMLElement(sourceElement);

		EMFModelElement modelElement = null;
		if (umlElement != null) {
			Stereotype stereotype = UMLUtil.getAppliedSuperstereotype(umlElement, getQualifiedName(context));
			Stereotype actual = (stereotype == null) ? null : UMLUtil.getAppliedSubstereotype(umlElement, stereotype);
			EObject stereotypeApplication = (actual == null) ? null : umlElement.getStereotypeApplication(actual);

			if (null == stereotypeApplication) {
				Activator.log.warn("Stereotype " + getQualifiedName(context) + " is not applied on " + umlElement); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				// check if we are in the Real Time case
				if (umlElement instanceof Element && null != stereotype && UML_REAL_TIME.equals(stereotype.getProfile().getName())) {
					modelElement = new RTStereotypeModelElement(stereotypeApplication, stereotype, EMFHelper.resolveEditingDomain(sourceElement));
				} else {
					modelElement = super.doCreateFromSource(umlElement, context);
				}
			}
		}
		return modelElement;
	}
}
