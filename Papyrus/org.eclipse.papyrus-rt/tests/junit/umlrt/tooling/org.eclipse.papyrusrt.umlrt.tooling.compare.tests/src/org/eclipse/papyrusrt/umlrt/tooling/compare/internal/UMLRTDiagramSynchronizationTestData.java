/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Test data for {@link UMLRTDiagramSynchronizationTest}.
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
public class UMLRTDiagramSynchronizationTestData extends AbstractUMLRTInputData {

	private static final String SEP = "/";
	private static final String BASE_PATH = "data" + SEP + "diagram-changes";
	private static final String CAPSULE_STRUCTURE_DIAG = BASE_PATH + SEP + "capsule-structure-diagram" + SEP;

	public ResourceSet getEmptyDiagramModel() throws IOException {
		return loadPapyrusModel(CAPSULE_STRUCTURE_DIAG + "empty-diag/model");
	}

	public ResourceSet getAddCapsuleModel() throws IOException {
		return loadPapyrusModel(CAPSULE_STRUCTURE_DIAG + "add-capsule1/model");
	}

	public ResourceSet getAddPortToCapsuleModel() throws IOException {
		return loadPapyrusModel(CAPSULE_STRUCTURE_DIAG + "add-port-to-capsule/model");
	}

	public ResourceSet getAddCapsulePartModel() throws IOException {
		return loadPapyrusModel(CAPSULE_STRUCTURE_DIAG + "add-capsule-part/model");
	}

}
