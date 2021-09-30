/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Type;

/**
 * Default language returned when no default language was found.
 */
public final class NoDefautLanguage implements IDefaultLanguage {

	/** Singleton instance of the NoDefault language. */
	public static final NoDefautLanguage INSTANCE = new NoDefautLanguage();

	/**
	 * Constructor.
	 */
	private NoDefautLanguage() {
		// not possible to instantiate, use INSTANCE instead
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return "noDefault"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "No Language";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIconURL() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getProfilesToApply() {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getLibrariesToImport() {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Type> getSpecificPrimitiveTypes(ResourceSet resourceSet) {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Collaboration> getSystemProtocols(ResourceSet resourceSet) {
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collaboration getBaseProtocol(ResourceSet resourceSet) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Class> getSystemClasses(ResourceSet resourceSet) {
		return Collections.emptySet();
	}

}
