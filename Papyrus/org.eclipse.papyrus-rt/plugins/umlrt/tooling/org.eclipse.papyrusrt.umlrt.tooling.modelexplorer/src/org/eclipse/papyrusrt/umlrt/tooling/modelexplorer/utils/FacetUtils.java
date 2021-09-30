/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.utils;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Names of facets used in the UML/RT customization
 */
public class FacetUtils {

	static final String INHERITED_IN = "inheritedIn"; //$NON-NLS-1$
	static final String INHERITED_INOUT = "inheritedInOut"; //$NON-NLS-1$
	static final String INHERITED_OUT = "inheritedOut"; //$NON-NLS-1$
	static final String IN = "in"; //$NON-NLS-1$
	static final String INOUT = "inOut"; //$NON-NLS-1$
	static final String OUT = "out"; //$NON-NLS-1$
	static final String COMMENTS = "comments"; //$NON-NLS-1$
	static final String GENERALIZATION = "generalization"; //$NON-NLS-1$

	static final Set<String> MESSAGES_AND_COMMENTS = ImmutableSet.of(
			INHERITED_IN, INHERITED_OUT, INHERITED_INOUT,
			IN, OUT, INOUT,
			COMMENTS);

	/**
	 * Check whether the passed facetName is a one of the message set facets (in, out, or inout)
	 * or comment
	 *
	 * @param facetName
	 * @return true, if one of the facet names above
	 */
	public static boolean isMessageOrComment(String facetName) {
		return MESSAGES_AND_COMMENTS.contains(facetName);
	}

	/**
	 * Check whether a {@code featureName} identifies the generalization reference.
	 *
	 * @param featureName
	 *            a facet feature name
	 * @return whether it is the {@code generalization} reference
	 */
	public static boolean isGeneralization(String featureName) {
		return GENERALIZATION.equals(featureName);
	}
}
