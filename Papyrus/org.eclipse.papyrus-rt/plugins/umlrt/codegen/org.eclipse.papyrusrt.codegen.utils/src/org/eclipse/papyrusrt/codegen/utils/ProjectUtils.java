/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * General utilities for projects.
 * 
 * @author epp
 */
public final class ProjectUtils {

	/**
	 * Default Constructor. Private because this is not intended to be extendes.
	 */
	private ProjectUtils() {
	}

	/**
	 * Get the project name of the project containing the given {@link EObject}.
	 * 
	 * @param eobj
	 *            - An {@link EObject}.
	 * @return The project name or null if it cannot be determined.
	 */
	public static String getProjectName(EObject eobj) {
		String projectName = null;
		URI uri = eobj.eResource().getURI();
		// Default project name is the same as the name of the folder containing the .uml file
		// TODO: this should be refined to look up the folder tree, since the .uml file
		// may be nested inside one or more folders in the project.
		String[] segments = uri.segments();
		if (segments.length > 1) {
			projectName = segments[segments.length - 2];
		} else if (segments.length == 1) {
			if (!segments[0].isEmpty()) {
				projectName = segments[0].replace(".uml", "");
			}
		}
		return projectName;
	}
}
