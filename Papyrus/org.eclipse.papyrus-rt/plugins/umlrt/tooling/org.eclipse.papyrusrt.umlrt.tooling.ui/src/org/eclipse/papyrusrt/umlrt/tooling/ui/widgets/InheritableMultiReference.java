/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import org.eclipse.papyrusrt.umlrt.tooling.ui.editors.InheritableMultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Customized multi-reference property for references that support inheritance.
 */
public class InheritableMultiReference extends AbstractInheritableMultiReference<InheritableMultipleReferenceEditor> {

	public InheritableMultiReference(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected InheritableMultipleReferenceEditor doCreateMultipleReferenceEditor(Composite parent, int style) {
		return new InheritableMultipleReferenceEditor(parent, style);
	}

}
