/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

public class UMLRTDiagramChangeTestData extends AbstractUMLRTInputData {

	private static final String SEP = "/";
	private static final String BASE_PATH = "data" + SEP + "diagram-changes";
	private static final String CAPSULE_STRUCTURE_DIAG = BASE_PATH + SEP + "capsule-structure-diagram" + SEP;

	public Resource getEmptyDiagram() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "empty-diag/model.notation");
	}

	public Resource getAddCapsule() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "add-capsule1/model.notation");
	}

	public Resource getMoveCapsule() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "move-capsule1/model.notation");
	}

	public Resource getResizeCapsule() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "resize-capsule1/model.notation");
	}

	public Resource getAddCapsulePart() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "add-capsule-part/model.notation");
	}

	public Resource getMoveCapsulePart() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "move-capsule-part/model.notation");
	}

	public Resource getResizeCapsulePart() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "resize-capsule-part/model.notation");
	}

	public Resource getAddPortToCapsulePart() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "add-port-to-capsule-part/model.notation");
	}

	public Resource getMovePortCapsulePart() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "move-port-capsule-part/model.notation");
	}

	public Resource getAddPortToCapsule() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "add-port-to-capsule/model.notation");
	}

	public Resource getMovePortInCapsule() throws IOException {
		return loadFromClassLoader(CAPSULE_STRUCTURE_DIAG + "move-port-capsule/model.notation");
	}
}
