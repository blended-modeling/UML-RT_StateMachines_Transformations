/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog;
import org.eclipse.swt.widgets.Composite;

/**
 * Specific implementation for RTPorts. RTPort can only be typed by a Protocol
 */
public class RTPortTypeDialog extends ReferenceDialog {

	public RTPortTypeDialog(Composite parent, int style) {
		super(parent, style);
	}

}
