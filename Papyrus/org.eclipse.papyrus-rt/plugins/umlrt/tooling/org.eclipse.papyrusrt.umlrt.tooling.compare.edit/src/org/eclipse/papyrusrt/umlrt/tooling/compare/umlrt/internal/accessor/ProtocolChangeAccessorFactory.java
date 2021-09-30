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

import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.getProtocolRenameAttributeChange;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.legacy.ITypedElement;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;

import com.google.common.base.Optional;

/**
 * Accessor factory for {@link ProtocolChange}s
 * 
 * @author Stefan Dirix
 *
 */
public class ProtocolChangeAccessorFactory extends UMLRTDiffAccessorFactory {

	@Override
	public boolean isFactoryFor(Object target) {
		return ProtocolChange.class.isInstance(target);
	}

	@Override
	public ITypedElement createAncestor(AdapterFactory adapterFactory, Object target) {
		return createProtocolChangeAccessor(adapterFactory, ProtocolChange.class.cast(target), MergeViewerSide.ANCESTOR);
	}

	@Override
	public ITypedElement createLeft(AdapterFactory adapterFactory, Object target) {
		return createProtocolChangeAccessor(adapterFactory, ProtocolChange.class.cast(target), MergeViewerSide.LEFT);
	}

	@Override
	public ITypedElement createRight(AdapterFactory adapterFactory, Object target) {
		return createProtocolChangeAccessor(adapterFactory, ProtocolChange.class.cast(target), MergeViewerSide.RIGHT);
	}

	private ITypedElement createProtocolChangeAccessor(AdapterFactory adapterFactory, ProtocolChange protocolChange,
			MergeViewerSide side) {
		// Forward to the normal accessor in case of rename
		final Optional<Diff> protocolRename = getProtocolRenameAttributeChange(protocolChange);
		if (protocolRename.isPresent()) {
			final Diff target = protocolRename.get();
			return createHighestRankingAccessorFactory(adapterFactory, target, side);
		}
		return new ProtocolChangeAccessor(adapterFactory, protocolChange, side);
	}
}
