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

/**
 * A protocol for extensions to contribute a delegate for the
 * "Clear" button in the toggle dialog preference page.
 */
public interface IToggleDialogDelegate {

	/**
	 * Clears all "Remember my decision" toggle states of dialogs
	 * under my control.
	 */
	void clearUserDecisions();
}
