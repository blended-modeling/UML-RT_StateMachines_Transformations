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
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.rcp.ui.internal.mergeviewer.item.impl.MergeViewerItem;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.item.IMergeViewerItem;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;

/**
 * Accessor for {@link ProtocolChange}s.
 * 
 * @author Stefan Dirix
 *
 */
@SuppressWarnings("restriction")
public class ProtocolChangeAccessor extends UMLRTDiffAccessor {

	private ProtocolChange protocolChange;

	/**
	 * Creates a specialized accessor for ProtocolChange differences.
	 * 
	 * @param adapterFactory
	 *            The adapter factory used by the accessor.
	 * @param diff
	 *            The diff for which we need an accessor.
	 * @param side
	 *            The side on which this accessor will be used.
	 */
	public ProtocolChangeAccessor(AdapterFactory adapterFactory, ProtocolChange protocolChange, MergeViewerSide side) {
		super(adapterFactory, protocolChange, side);
		this.protocolChange = protocolChange;
	}

	@Override
	public IMergeViewerItem getInitialItem() {
		if (DifferenceKind.MOVE.equals(protocolChange.getKind())) {
			return new MergeViewerItem.Container(protocolChange.getMatch().getComparison(), protocolChange
					.getRefinedBy().get(0), protocolChange.getMatch().getComparison()
							.getMatch(protocolChange.getDiscriminant()),
					getSide(), getRootAdapterFactory());
		}
		return super.getInitialItem();
	}

}
