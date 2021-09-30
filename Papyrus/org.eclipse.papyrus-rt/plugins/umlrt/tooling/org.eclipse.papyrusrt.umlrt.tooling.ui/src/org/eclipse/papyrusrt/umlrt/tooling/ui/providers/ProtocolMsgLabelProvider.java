/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.DelegatingLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.CallEvent;

/**
 * A delegating label provider that displays the icon of the associated
 * operation instead of that of the call event itself.
 */
public class ProtocolMsgLabelProvider extends DelegatingLabelProvider {

	public ProtocolMsgLabelProvider(ILabelProvider originalLabelProvider) {
		super(originalLabelProvider);
	}

	/**
	 * no image in case of any receive event.
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof AnyReceiveEvent) {
			return null;
		}
		return super.getImage(element);
	}

	/**
	 * image of associated operation in case of call event
	 */
	@Override
	protected Image customGetImage(Object element) {
		if (element instanceof CallEvent) {
			CallEvent ce = (CallEvent) element;
			return getImage(ce.getOperation());
		}
		return null;
	}
}

