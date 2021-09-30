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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import static org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder.UML_EXTENSION_FEATURE_BASE;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;

/**
 * Implementation of a derived union that includes extension features.
 */
public class DerivedUnionEObjectEListExt<E> extends DerivedUnionEObjectEList<E> {

	public DerivedUnionEObjectEListExt(Class<?> dataClass, InternalEObject owner, int featureID, int[] sourceFeatureIDs) {
		super(dataClass, owner, featureID, sourceFeatureIDs);
	}

	public static int[] extendSubsets(int[] baseSubsets, int... extendedSubset) {
		int base = baseSubsets.length;
		int ext = extendedSubset.length;

		int[] result = new int[base + ext];
		System.arraycopy(baseSubsets, 0, result, 0, base);

		for (int i = 0; i < ext; i++) {
			result[base + i] = UML_EXTENSION_FEATURE_BASE - extendedSubset[i];
		}

		return result;
	}

	@Override
	public EStructuralFeature getEStructuralFeature(int featureID) {
		return (featureID <= UML_EXTENSION_FEATURE_BASE)
				? ExtensionHolder.getExtensionFeature(owner.eClass(), featureID)
				: super.getEStructuralFeature(featureID);
	}
}
