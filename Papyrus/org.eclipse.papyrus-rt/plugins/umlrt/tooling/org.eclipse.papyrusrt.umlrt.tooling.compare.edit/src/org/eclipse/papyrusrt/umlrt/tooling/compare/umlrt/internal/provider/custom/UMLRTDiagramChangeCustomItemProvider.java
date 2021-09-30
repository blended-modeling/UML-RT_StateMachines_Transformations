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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.custom;

import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff;
import org.eclipse.emf.compare.diagram.internal.extensions.provider.spec.DiagramChangeItemProviderSpec;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

/**
 * Custom item provider for {@link UMLRTDiagramChange UMLRTDiagramChanges}.
 * 
 * @author Alexandra Buzila
 */
@SuppressWarnings("restriction")
public class UMLRTDiagramChangeCustomItemProvider extends DiagramChangeItemProviderSpec implements IItemLabelProvider {

	/**
	 * Constructor.
	 *
	 * @param delegate
	 *            The origin item provider adapter.
	 */
	public UMLRTDiagramChangeCustomItemProvider(ItemProviderAdapter delegate) {
		super(delegate);
	}

	@Override
	protected String getReferenceText(DiagramDiff diagramDiff) {
		if (diagramDiff.getPrimeRefining() instanceof ReferenceChange) {
			ReferenceChange primeRefining = (ReferenceChange) diagramDiff.getPrimeRefining();
			return primeRefining.getReference().getName();
		}
		return super.getReferenceText(diagramDiff);
	}
}
