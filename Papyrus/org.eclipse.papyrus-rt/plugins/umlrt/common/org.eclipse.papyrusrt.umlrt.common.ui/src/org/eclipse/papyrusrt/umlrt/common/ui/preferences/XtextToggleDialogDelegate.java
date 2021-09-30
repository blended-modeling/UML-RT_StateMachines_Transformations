/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.common.ui.preferences;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.eclipse.xtext.ui.util.DontAskAgainDialogs;

import com.google.inject.Inject;

/**
 * Xtext-based implementation of the {@link IToggleDialogDelegate} protocol
 * that clears an Xtext editor's remembered dialog toggle states. To be used
 * with an {@link AbstractGuiceAwareExecutableExtensionFactory} in the usual
 * Xtext way.
 */
public class XtextToggleDialogDelegate implements IToggleDialogDelegate {
	@Inject
	private DontAskAgainDialogs dialogs;

	/**
	 * Initializes me.
	 */
	public XtextToggleDialogDelegate() {
		super();
	}

	@Override
	public void clearUserDecisions() {
		dialogs.forgetAllUserDecisions();
	}
}
