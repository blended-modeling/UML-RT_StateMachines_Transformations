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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Internal protocol for state machine regions.
 */
public interface InternalUMLRTRegion extends InternalUMLRTRedefinitionContext<InternalUMLRTRegion>, Region {

	@Override
	default Stream<? extends InternalUMLRTRegion> rtDescendants() {
		return CacheAdapter.getInstance().getNonNavigableInverseReferences(this).stream()
				.filter(setting -> setting.getEStructuralFeature() == UMLPackage.Literals.REGION__EXTENDED_REGION)
				.map(EStructuralFeature.Setting::getEObject)
				.filter(InternalUMLRTRegion.class::isInstance)
				.filter(g -> g.eClass() == eClass())
				.map(InternalUMLRTRegion.class::cast);
	}
}
