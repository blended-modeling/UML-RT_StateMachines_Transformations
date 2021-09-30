/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

import org.eclipse.papyrus.infra.core.language.Language;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrusrt.umlrt.core.Activator;

/**
 * Implementation of the UML-RT language extension.
 */
public class UMLRTLanguage extends Language {

	public UMLRTLanguage() {
		super("org.eclipse.papyrusrt.umlrt.core.language", new Version(1, 1, 0), "UML Real-Time"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void install(ModelSet modelSet) {
		Activator.log.error("UML-RT Language installation is not implemented, yet.", null); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uninstall(ModelSet modelSet) {
		Activator.log.error("UML-RT Language uninstallation is not implemented, yet.", null); //$NON-NLS-1$
	}
}
