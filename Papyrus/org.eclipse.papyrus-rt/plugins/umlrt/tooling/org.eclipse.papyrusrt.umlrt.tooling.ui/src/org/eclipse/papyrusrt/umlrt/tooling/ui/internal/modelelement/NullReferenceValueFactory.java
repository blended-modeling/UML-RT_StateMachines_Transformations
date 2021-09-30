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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement;

import java.util.Collection;

import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.swt.widgets.Control;

/**
 * A reference value factory that doesn't create or edit anything.
 */
public final class NullReferenceValueFactory implements ReferenceValueFactory {

	public static final NullReferenceValueFactory INSTANCE = new NullReferenceValueFactory();

	private NullReferenceValueFactory() {
		super();
	}

	@Override
	public boolean canCreateObject() {
		return false;
	}

	@Override
	public Object createObject(Control widget, Object context) {
		throw new UnsupportedOperationException("createObject"); //$NON-NLS-1$
	}

	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		return objectsToValidate;
	}

	@Override
	public boolean canEdit() {
		return false;
	}

	@Override
	public Object edit(Control widget, Object object) {
		throw new UnsupportedOperationException("edit"); //$NON-NLS-1$
	}

}
