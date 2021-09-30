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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.rcp.ui.mergeviewer.IMergeViewer.MergeViewerSide;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;

/**
 * Accessor for {@link ProtocolMessageChange ProtocolMessageChanges}.
 * 
 * @author Alexandra Buzila
 */
public class ProtocolMessageChangeAccessor extends UMLRTDiffAccessor {

	/** Default constructor. */
	public ProtocolMessageChangeAccessor(AdapterFactory adapterFactory,
			UMLRTDiff diff, MergeViewerSide side) {
		super(adapterFactory, diff, side);
	}

}
