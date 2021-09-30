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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.accessor;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.rcp.ui.EMFCompareRCPUIPlugin;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.factory.IAccessorFactory;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.legacy.ITypedElement;
import org.eclipse.emf.compare.rcp.ui.internal.contentmergeviewer.accessor.factory.impl.AbstractAccessorFactory;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;

/**
 * Creates {@link UMLRTDiffAccessor}s for {@link UMLRTDiff}s.
 * 
 * @author Stefan Dirix
 */
@SuppressWarnings("restriction")
public class UMLRTDiffAccessorFactory extends AbstractAccessorFactory {

	@Override
	public boolean isFactoryFor(Object target) {
		return UMLRTDiff.class.isInstance(target);
	}

	@Override
	public ITypedElement createLeft(AdapterFactory adapterFactory, Object target) {
		return new UMLRTDiffAccessor(adapterFactory, UMLRTDiff.class.cast(target), MergeViewerSide.LEFT);
	}

	@Override
	public ITypedElement createRight(AdapterFactory adapterFactory, Object target) {
		return new UMLRTDiffAccessor(adapterFactory, UMLRTDiff.class.cast(target), MergeViewerSide.RIGHT);
	}

	@Override
	public ITypedElement createAncestor(AdapterFactory adapterFactory, Object target) {
		return new UMLRTDiffAccessor(adapterFactory, UMLRTDiff.class.cast(target), MergeViewerSide.ANCESTOR);
	}
	
	protected ITypedElement createHighestRankingAccessorFactory(AdapterFactory adapterFactory, Diff diff,
			MergeViewerSide side) {
		final IAccessorFactory factory = getHighestRankingAccessorFactory(diff);
		switch (side) {
		case ANCESTOR:
			return factory.createAncestor(adapterFactory, diff);
		case LEFT:
			return factory.createLeft(adapterFactory, diff);
		case RIGHT:
			return factory.createRight(adapterFactory, diff);
		default:
			throw new IllegalArgumentException("Unkown merge viewer side."); //$NON-NLS-1$
		}
	}

	private IAccessorFactory getHighestRankingAccessorFactory(final Diff target) {
		return EMFCompareRCPUIPlugin.getDefault().getAccessorFactoryRegistry().getHighestRankingFactory(target);
	}

}
