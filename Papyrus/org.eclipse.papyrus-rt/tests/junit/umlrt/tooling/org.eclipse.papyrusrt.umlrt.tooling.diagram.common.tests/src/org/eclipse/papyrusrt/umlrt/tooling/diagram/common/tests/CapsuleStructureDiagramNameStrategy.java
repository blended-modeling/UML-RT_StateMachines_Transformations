/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;

/**
 * Diagram naming strategy supporting capsule-structure diagrams.
 * 
 * @see PapyrusRTEditorFixture.DiagramNaming
 */
public final class CapsuleStructureDiagramNameStrategy implements DiagramNameStrategy {

	@Override
	public boolean canName(Diagram diagram) {
		return UMLRTCapsuleStructureDiagramUtils.isCapsuleStructureDiagram(diagram);
	}

	@Override
	public String getName(Diagram diagram) {
		return UMLRTCapsuleStructureDiagramUtils.getDisplayedCapsuleStructureDiagramName(diagram);
	}

}
