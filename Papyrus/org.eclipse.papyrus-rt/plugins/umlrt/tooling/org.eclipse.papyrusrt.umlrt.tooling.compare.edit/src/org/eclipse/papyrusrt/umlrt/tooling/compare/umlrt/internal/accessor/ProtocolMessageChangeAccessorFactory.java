/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alexandra Buzila - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.accessor;

import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.getProtocolMessageRenameAttributeChange;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.rcp.ui.contentmergeviewer.accessor.legacy.ITypedElement;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;

import com.google.common.base.Optional;

/**
 * Accessor for {@link ProtocolMessageChange ProtocolMessageChanges}.
 * 
 * @author Alexandra Buzila
 */
public class ProtocolMessageChangeAccessorFactory
		extends
			UMLRTDiffAccessorFactory {

	@Override
	public boolean isFactoryFor(Object target) {
		return ProtocolMessageChange.class.isInstance(target);
	}
	
	@Override
	public ITypedElement createAncestor(AdapterFactory adapterFactory,
			Object target) {
		return createProtocolMessageChangeAccessor(adapterFactory, ProtocolMessageChange.class.cast(target), MergeViewerSide.ANCESTOR);
	}
	
	@Override
	public ITypedElement createLeft(AdapterFactory adapterFactory, Object target) {
		return createProtocolMessageChangeAccessor(adapterFactory, ProtocolMessageChange.class.cast(target), MergeViewerSide.LEFT);
	}
	
	@Override
	public ITypedElement createRight(AdapterFactory adapterFactory,
			Object target) {
		return createProtocolMessageChangeAccessor(adapterFactory, ProtocolMessageChange.class.cast(target), MergeViewerSide.RIGHT);
	}
	
	private ITypedElement createProtocolMessageChangeAccessor(AdapterFactory adapterFactory, ProtocolMessageChange protocolMessageChange,
			MergeViewerSide side) {
		// Forward to the normal accessor in case of rename
		final Optional<Diff> protocolMessageRename = getProtocolMessageRenameAttributeChange(protocolMessageChange);
		if (protocolMessageRename.isPresent()) {
			final Diff target = protocolMessageRename.get();
			return createHighestRankingAccessorFactory(adapterFactory, target, side);
		}
		return new ProtocolMessageChangeAccessor(adapterFactory, protocolMessageChange, side);
	}
}
