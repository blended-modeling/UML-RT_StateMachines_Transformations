/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * {@link org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor} for CapsuleParts.
 */
public class CapsuleStructureDiagramHyperlinkContributor implements HyperlinkContributor {

	/**
	 * Constructor.
	 */
	public CapsuleStructureDiagramHyperlinkContributor() {
		// empty constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<>();

		EObject fromEObject = EMFHelper.getEObject(fromElement);
		if (fromEObject instanceof Property && CapsulePartUtils.isCapsulePart((Property) fromEObject)) {
			Property capsulePart = (Property) fromEObject;
			// get Capsule and to its diagram
			Type type = capsulePart.getType();
			if (type instanceof org.eclipse.uml2.uml.Class) {
				// find the associated Capsule Structure Diagram
				Diagram capsuleDiagram = UMLRTCapsuleStructureDiagramUtils.getCapsuleStructureDiagram((Class) type);
				if (capsuleDiagram != null) {
					HyperLinkObject hyperLink = new HyperLinkSpecificObject(capsuleDiagram);
					// navigable by default
					hyperLink.setIsDefault(true);
					hyperlinks.add(hyperLink);
				}
			}
		}

		return hyperlinks;
	}
}
