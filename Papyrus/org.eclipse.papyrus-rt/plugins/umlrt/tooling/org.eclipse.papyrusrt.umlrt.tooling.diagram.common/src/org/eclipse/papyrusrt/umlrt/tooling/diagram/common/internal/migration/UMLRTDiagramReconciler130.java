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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.migration;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler_1_3_0;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle;
import org.eclipse.papyrusrt.umlrt.core.architecture.UMLRTArchitectureContextIds;

/**
 * Migration facility for UMLRT Diagrams to architecture framework rather than viewpoints.
 */
public class UMLRTDiagramReconciler130 extends DiagramReconciler_1_3_0 {

	private static final String CAPSULE_STRUCTURE_DIAGRAM_OLD_TYPE = "CompositeStructure";
	private static final String STATE_MACHINE_DIAGRAM_OLD_TYPE = "PapyrusUMLStateMachineDiagram";
	private static final String CAPSULE_STRUCTURE_DIAGRAM_OLD_NAME = "UML-RT Capsule Structure Diagram";
	private static final String STATE_MACHINE_DIAGRAM_OLD_NAME = "UML-RT State Machine Diagram";

	/**
	 * Constructor.
	 */
	public UMLRTDiagramReconciler130() {
		super();
	}

	@Override
	protected PapyrusDiagram getDiagramKind(Diagram diagram, PapyrusViewStyle oldStyle) {
		if (oldStyle != null) {
			org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusDiagram oldDiagramKind = (org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusDiagram) oldStyle.getConfiguration();
			if (oldDiagramKind != null) {
				String name = oldDiagramKind.getName();
				if (name == null) { // configuration could not be resolved
					// check by URI
					name = getNameByURI(oldDiagramKind);
				}
				return getDiagramKind(name, diagram);
			}
		}
		if (CAPSULE_STRUCTURE_DIAGRAM_OLD_TYPE.equals(diagram.getType())) {
			return getDiagramKind(CAPSULE_STRUCTURE_DIAGRAM_OLD_NAME, diagram);
		} else if (STATE_MACHINE_DIAGRAM_OLD_TYPE.equals(diagram.getType())) {
			return getDiagramKind(STATE_MACHINE_DIAGRAM_OLD_NAME, diagram);
		}
		return null;
	}

	/**
	 * @return
	 */
	protected String getNameByURI(org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusDiagram oldDiagramKind) {
		if (oldDiagramKind instanceof MinimalEObjectImpl) {
			URI uri = ((MinimalEObjectImpl) oldDiagramKind).eProxyURI();
			String uri2String = uri.toString();
			if ("platform:/plugin/org.eclipse.papyrusrt.umlrt.tooling.diagram.common/configuration/UMLRT.configuration#_Z79eQHcZEeSnWeKqQOfW2A".equals(uri2String)) {
				return CAPSULE_STRUCTURE_DIAGRAM_OLD_NAME;
			} else if ("platform:/plugin/org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine/configuration/RTStateMachine.configuration#_Z79eQHcZEeSnWeKqQOfW2A".equals(uri2String)) {
				return STATE_MACHINE_DIAGRAM_OLD_NAME;
			}
		}
		return null;
	}

	/**
	 * Gets the diagram kind that matches given name and that supports the given diagram.
	 * 
	 * @param name
	 *            name of the old diagram configuration
	 * @param diagram
	 *            the diagram to migrate
	 * @return the {@link PapyrusDiagram} to set for the new diagram
	 */
	protected PapyrusDiagram getDiagramKind(String name, Diagram diagram) {
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		MergedArchitectureDescriptionLanguage context = (MergedArchitectureDescriptionLanguage) manager.getArchitectureContextById(UMLRTArchitectureContextIds.UMLRT);
		for (RepresentationKind pKind : context.getRepresentationKinds()) {
			if (pKind.getName().equals(name)) {
				PapyrusDiagram dKind = (PapyrusDiagram) pKind;
				return dKind;
			}
		}
		return null;
	}
}
