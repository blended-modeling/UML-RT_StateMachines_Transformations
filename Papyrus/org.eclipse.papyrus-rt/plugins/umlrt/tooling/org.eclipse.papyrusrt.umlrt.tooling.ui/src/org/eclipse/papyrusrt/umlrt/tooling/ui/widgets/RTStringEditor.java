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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.DataBindingContextTracker;
import org.eclipse.swt.widgets.Composite;

/**
 * A string-editor that provides live validation feed-back through a
 * {@linkplain ValidationStatusProvider status provider} attached to its
 * {@linkplain DataBindingContext data-binding context}, which latter is
 * made discoverable via the {@linkplain DataBindingContextTracker tracker}.
 * 
 * @see DataBindingContextTracker
 */
public class RTStringEditor extends AbstractPropertyEditor {

	/**
	 * Initializes me.
	 *
	 * @param parent
	 *            my parent composite
	 * @param style
	 *            my style bits
	 */
	public RTStringEditor(Composite parent, int style) {
		super(new Impl(parent, style));
	}

	private static class Impl extends org.eclipse.papyrus.infra.widgets.editors.StringEditor {

		private boolean updatingStatus;

		Impl(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		protected DataBindingContext getBindingContext() {
			if (dbc == null) {
				dbc = new DataBindingContext();
				DataBindingContextTracker.addingDataBindingContext(dbc);
			}

			return dbc;
		}

		@Override
		public void updateStatus(IStatus status) {
			if (!updatingStatus) {
				updatingStatus = true;

				try {
					if (binding != null) {
						// Propagate through the context
						binding.validateTargetToModel();
					}

					super.updateStatus(status);
				} finally {
					updatingStatus = false;
				}
			}
		}
	}
}
