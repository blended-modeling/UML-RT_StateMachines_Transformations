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

package org.eclipse.papyrusrt.umlrt.core.internal.language;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.AbstractReadOnlyHandler;
import org.eclipse.papyrus.infra.core.resource.ReadOnlyAxis;

import com.google.common.base.Optional;

/**
 * Read-only support for the special extension resource that must always be
 * treated as writable.
 */
public class UMLExtensionReadOnlyHandler extends AbstractReadOnlyHandler {
	private static final URI EXTENSIONS_URI = URI.createURI("umlrt://extensions"); //$NON-NLS-1$

	public UMLExtensionReadOnlyHandler(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	public Optional<Boolean> anyReadOnly(Set<ReadOnlyAxis> axes, URI[] uris) {
		// I must handle the permission axis in addition to discretion because
		// otherwise I could not overrule the EMFReadOnlyHandler

		int knownWritable = 0;
		for (int i = 0; i < uris.length; i++) {
			if (EXTENSIONS_URI.equals(uris[i])) {
				knownWritable++;
			}
		}

		return (knownWritable == uris.length) ? Optional.of(false) : Optional.absent();
	}

	@Override
	public Optional<Boolean> makeWritable(Set<ReadOnlyAxis> axes, URI[] uris) {
		// I never report read-only for a resource
		return Optional.absent();
	}

}
