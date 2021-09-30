/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 490859, 489939, 496304, 475905, 501081, 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeNameEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortNameEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartNameEditPartCN;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeNameEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTConnectorEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortNameEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortOnPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartNameEditPartCN;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.uml2.uml.NamedElement;


/**
 * The Class RTEditPartProvider provide the editParts for the Real Time specification.
 * This is applied for the Composite Structure Diagram
 */
public class RTEditPartProvider extends AbstractRTEditPartProvider {

	/**
	 * Default constructor.
	 */
	public RTEditPartProvider() {
		super(CompositeStructureDiagramEditPart.MODEL_ID);

		// Nodes
		nodeMap.put(ClassCompositeEditPart.VISUAL_ID, always(RTClassCompositeEditPart.class));
		nodeMap.put(ClassCompositeNameEditPart.VISUAL_ID, always(RTClassCompositeNameEditPart.class));
		nodeMap.put(ClassCompositeCompartmentEditPart.VISUAL_ID, always(RTClassCompositeCompartmentEditPart.class));
		nodeMap.put(PropertyPartEditPartCN.VISUAL_ID, always(RTPropertyPartEditPart.class));
		nodeMap.put(PropertyPartNameEditPartCN.VISUAL_ID, always(RTPropertyPartNameEditPartCN.class));
		nodeMap.put(PortEditPart.VISUAL_ID, anyOf(
				ifContainerView(PropertyPartEditPartCN.VISUAL_ID, RTPortOnPartEditPart.class),
				ifContainerView(ClassCompositeEditPart.VISUAL_ID, RTPortEditPart.class)));
		nodeMap.put(PortNameEditPart.VISUAL_ID, always(RTPortNameEditPart.class));

		// Edges
		edgeMap.put(ConnectorEditPart.VISUAL_ID, always(RTConnectorEditPart.class));
	}

	/**
	 * Is the {@code element} one that we understand in the Capsule Structure Diagram?
	 */
	@Override
	protected boolean accept(EObject element) {
		return (element instanceof NamedElement)
				&& (UMLRTFactory.CapsuleFactory.create((NamedElement) element) != null);
	}

}
