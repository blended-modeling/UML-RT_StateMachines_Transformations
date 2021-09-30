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
package org.eclipse.papyrusrt.umlrt.cpp.defaultlanguage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.internal.debug.IDebugOptions;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Type;

import com.google.common.collect.Sets;

/**
 * Default language implementation for standard UML-RT C++
 */
public class UMLRTCppDefaultLanguage implements IDefaultLanguage {

	private static final String UMLRT_BASE_PROTOCOL_NAME = "UMLRTBaseCommProtocol"; // $NON-NLS-1$
	private static final String INTERNAL_PACKAGE_NAME = "Internal"; // $NON-NLS-1$
	private static final String CPP_PROPERTIES_PROFILE_PATH = "pathmap://UMLRT_CPP/RTCppProperties.profile.uml"; // $NON-NLS-1$
	private static final String CPP_ICON_URL = "platform:/plugin/org.eclipse.papyrusrt.umlrt.cpp/icons/cpp.gif"; // $NON-NLS-1$
	private static final String CPP = "umlrt-cpp"; // $NON-NLS-1$
	private static final String CPP_PRIMITIVE_TYPES_URI = "pathmap://PapyrusC_Cpp_LIBRARIES/AnsiCLibrary.uml"; // $NON-NLS-1$
	private static final String CPP_MODEL_LIBRARY_URI = "pathmap://UMLRTRTSLIB/UMLRT-RTS.uml"; // $NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return CPP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "C++";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIconURL() {
		return CPP_ICON_URL; // $NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getProfilesToApply() {
		// RtCpp property set should be applied;
		return Collections.singleton(CPP_PROPERTIES_PROFILE_PATH);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getLibrariesToImport() {
		// CPP primitive types should be imported
		return Sets.newHashSet(CPP_PRIMITIVE_TYPES_URI, CPP_MODEL_LIBRARY_URI);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Type> getSpecificPrimitiveTypes(ResourceSet resourceSet) {
		// List of UML-RT types that should be easily accessed.
		Set<Type> set = new HashSet<>();
		URI uri = URI.createURI(CPP_PRIMITIVE_TYPES_URI);
		if (uri != null) {
			Resource resource = resourceSet.getResource(uri, true);
			if (resource == null || resource.getContents().isEmpty()) {
				Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Impossible to get a resource or its content: " + resource);
				return Collections.emptySet();
			}

			if (resource.getContents().size() > 0) {
				for (EObject root : resource.getContents()) {
					if (root instanceof org.eclipse.uml2.uml.Package) {
						for (EObject object : ((org.eclipse.uml2.uml.Package) root).getOwnedTypes()) {
							if (object instanceof Type) {
								set.add((Type) object);
							}
						}
					}
				}
			} else {
				Activator.log.error("Impossible to get content of the Cpp Library", null);
			}
			return set;

		}
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Collaboration> getSystemProtocols(ResourceSet resourceSet) {
		URI uri = URI.createURI(CPP_MODEL_LIBRARY_URI);
		if (uri != null) {
			Resource resource = resourceSet.getResource(uri, true);
			if (resource == null || resource.getContents().isEmpty()) {
				Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Impossible to get a resource or its content: " + resource);
				return Collections.emptySet();
			}

			if (resource.getContents().size() > 0) {
				return SystemElementsUtils.getSystemProtocols(resource);
			} else {
				Activator.log.error("Impossible to get content of the Model Library", null);
			}
		}
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collaboration getBaseProtocol(ResourceSet resourceSet) {
		URI uri = URI.createURI(CPP_MODEL_LIBRARY_URI);
		if (uri != null) {
			Resource resource = resourceSet.getResource(uri, true);
			if (resource == null || resource.getContents().isEmpty()) {
				Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Impossible to get a resource or its content: " + resource);
				return null;
			}

			if (resource.getContents().size() > 0) {

				return SystemElementsUtils.getBaseProtocol(resource);

			} else {
				Activator.log.error("Impossible to get content of the Model Library", null);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Class> getSystemClasses(ResourceSet resourceSet) {
		URI uri = URI.createURI(CPP_MODEL_LIBRARY_URI);
		if (uri != null) {
			Resource resource = resourceSet.getResource(uri, true);
			if (resource == null || resource.getContents().isEmpty()) {
				Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Impossible to get a resource or its content: " + resource);
				return Collections.emptySet();
			}

			if (resource.getContents().size() > 0) {
				return SystemElementsUtils.getSystemClasss(resource);
			} else {
				Activator.log.error("Impossible to get content of the Model Library", null);
			}
		}
		return Collections.emptySet();
	}



}
