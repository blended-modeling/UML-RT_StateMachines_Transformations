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
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

/**
 * Factory for {@link UMLRTDiagramChange UMLRTDiagramChanges}.
 * 
 * @author Alexandra Buzila
 */
public class UMLRTDiagramChangeFactory extends AbstractUMLRTDiagramChangeFactory {

	@Override
	public boolean handles(Diff input) {
		return DiagramChangesHandlerService.getService().getHandlerForDiff(input) != null;
	}

	@Override
	public Class<? extends Diff> getExtensionKind() {
		return UMLRTDiagramChange.class;
	}

	@Override
	public Diff createExtension() {
		return UMLRTCompareFactory.eINSTANCE.createUMLRTDiagramChange();
	}

	@Override
	protected DifferenceKind getRelatedExtensionKind(Diff input) {
		return input.getKind();
	}
}
