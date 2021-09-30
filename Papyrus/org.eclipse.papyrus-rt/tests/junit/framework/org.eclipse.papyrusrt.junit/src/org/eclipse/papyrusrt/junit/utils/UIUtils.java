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

package org.eclipse.papyrusrt.junit.utils;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.papyrus.infra.ui.util.UIUtil;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Objects;

/**
 * UI utilities for tests
 */
public final class UIUtils {

	/**
	 * Constructor.
	 */
	private UIUtils() {
		// utility class
	}

	public static Control getDirectEditor(Control viewerControl, String expectedString) {
		boolean found = false;
		TreeIterator<Control> tree = UIUtil.allChildren(viewerControl);
		while (!found && tree.hasNext()) {
			Control next = tree.next();
			String text = null;
			if (next instanceof Text) {
				text = ((Text) next).getText().trim();
			} else if (next instanceof StyledText) {
				text = ((StyledText) next).getText().trim();
			}
			if (Objects.equal(expectedString, text)) {
				return next;
			}
		}
		return null;
	}

	/**
	 * Cancels any direct-edit that may be happening within the context of an
	 * edit-part {@code viewer}.
	 * 
	 * @param viewer
	 *            an edit-part viewer
	 */
	public static void cancelDirectEditor(EditPartViewer viewer) {
		// Taking focus removes it from the cell editor, cancelling the direct edit
		viewer.getControl().setFocus();
	}

}
