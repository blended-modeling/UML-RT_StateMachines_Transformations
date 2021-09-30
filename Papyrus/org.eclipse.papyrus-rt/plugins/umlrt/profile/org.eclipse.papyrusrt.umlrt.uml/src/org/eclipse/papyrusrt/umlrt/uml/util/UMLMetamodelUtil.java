/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.util;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Utilities for analysis of the UML metamodel.
 */
public class UMLMetamodelUtil {

	private static final String ANNOTATION_SUBSETS = "subsets"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private UMLMetamodelUtil() {
		super();
	}

	/**
	 * Queries whether a {@code subset} is a subset, transitively, of a
	 * purported {@code superset}.
	 * 
	 * @param subset
	 *            a reference that may be a subset of the {@code superset}
	 * @param superset
	 *            a reference that may be a superset of the {@code subset}
	 * 
	 * @return whether the {@code subset} is a subset of the {@code superset}, transitively
	 */
	public static boolean isSubsetOf(EReference subset, EReference superset) {
		boolean result = false;

		// null is not a subset of anything
		EAnnotation supersets = (subset == null) ? null : subset.getEAnnotation(ANNOTATION_SUBSETS);
		if (supersets != null) {
			result = supersets.getReferences().contains(superset);
			if (!result) {
				// Look for transitive subset, which is at least plausible
				// considering that we do have some superset
				for (Iterator<EObject> iter = supersets.getReferences().iterator(); !result && iter.hasNext();) {
					EObject next = iter.next();
					if (next instanceof EReference) {
						result = isSubsetOf((EReference) next, superset);
					}
				}
			}
		}

		return result;
	}

}
