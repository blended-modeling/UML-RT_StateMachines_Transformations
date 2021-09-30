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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.decorator;

import org.eclipse.emf.compare.diagram.internal.extensions.provider.spec.ForwardingDiagramDiffItemProvider;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemProviderDecorator;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

/**
 * Specialized ItemProviderDecorator for {@link UMLRTDiagramChange} diffs.
 * 
 * @author Alexandra Buzila
 */
@SuppressWarnings("restriction")
public class UMLRTDiagramChangeItemProviderDecorator extends ForwardingDiagramDiffItemProvider implements IItemProviderDecorator {

	/**
	 * This keeps track of the item provider being decorated.
	 */
	protected IChangeNotifier decoratedItemProvider;

	/**
	 * Constructor.
	 *
	 * @param delegate
	 *            Default item provider adapter.
	 */
	public UMLRTDiagramChangeItemProviderDecorator(ItemProviderAdapter delegate) {
		super(delegate);
	}

	@Override
	public IChangeNotifier getDecoratedItemProvider() {
		return decoratedItemProvider;
	}

	@Override
	public void setDecoratedItemProvider(IChangeNotifier decoratedItemProvider) {
		this.decoratedItemProvider = decoratedItemProvider;
	}
}
