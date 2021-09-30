/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stefan Dirix - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.decorator;

import org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.DecoratorAdapterFactory;
import org.eclipse.emf.edit.provider.IItemProviderDecorator;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.UMLRTCompareItemProviderAdapterFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.UMLRTDiagramChangeItemProvider;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.util.UMLRTCompareSwitch;

/**
 * Adapter Factory for {@link IItemProviderDecorator}s of {@link UMLRTDiagramChange} changes.
 */
@SuppressWarnings("restriction")
public class UMLRTDiffItemProviderDecoratorAdapterFactory extends DecoratorAdapterFactory {

	/** ItemProviderAdapter for the diagram changes. */
	private ItemProviderAdapter diagramChangeItemProvider;


	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 */
	private final UMLRTCompareSwitch<IItemProviderDecorator> modelSwitch = new UMLRTCompareSwitch<IItemProviderDecorator>() {
		@Override
		public IItemProviderDecorator caseProtocolChange(ProtocolChange object) {
			return createProtocolChangeItemProviderDecorator();
		}

		@Override
		public IItemProviderDecorator caseProtocolMessageChange(ProtocolMessageChange object) {
			return createProtocolMessageChangeItemProviderDecorator();
		};

		@Override
		public IItemProviderDecorator caseUMLRTDiff(UMLRTDiff object) {
			return createUMLRTDiffItemProviderDecorator();
		};

		@Override
		public IItemProviderDecorator caseDiagramDiff(DiagramDiff object) {
			return createUMLRTDiagramChangeItemProviderDecorator();
		};
	};

	public UMLRTDiffItemProviderDecoratorAdapterFactory() {
		super(new UMLRTCompareItemProviderAdapterFactory());
	}

	@Override
	protected IItemProviderDecorator createItemProviderDecorator(Object target, Object Type) {
		return modelSwitch.doSwitch((EObject) target);
	}

	protected IItemProviderDecorator createProtocolChangeItemProviderDecorator() {
		return new ProtocolChangeItemProviderDecorator(this);
	}

	protected IItemProviderDecorator createProtocolMessageChangeItemProviderDecorator() {
		return new ProtocolMessageChangeItemProviderDecorator(this);
	}

	protected IItemProviderDecorator createUMLRTDiffItemProviderDecorator() {
		return new UMLRTDiffItemProviderDecorator(this);
	}

	protected IItemProviderDecorator createUMLRTDiagramChangeItemProviderDecorator() {
		return new UMLRTDiagramChangeItemProviderDecorator(getDiagramChangeAdapter());
	}

	protected ItemProviderAdapter getDiagramChangeAdapter() {
		if (diagramChangeItemProvider == null) {
			diagramChangeItemProvider = new UMLRTDiagramChangeItemProvider(this);
		}
		return diagramChangeItemProvider;
	}
}
