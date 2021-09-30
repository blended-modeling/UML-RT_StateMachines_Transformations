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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import static org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder.UML_EXTENSION_FEATURE_BASE;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.uml2.common.util.SubsetSupersetEObjectContainmentWithInverseEList;

/**
 * Implementation of a subset/superset that includes extension features.
 */
public class SubsetSupersetEObjectContainmentWithInverseEListExt<E> extends SubsetSupersetEObjectContainmentWithInverseEList<E> {

	private static final long serialVersionUID = 1L;

	public SubsetSupersetEObjectContainmentWithInverseEListExt(Class<?> dataClass, InternalUMLRTElement owner,
			int featureID, int[] supersetFeatureIDs, int[] subsetFeatureIDs, int inverseFeatureID) {
		super(dataClass, owner, featureID, supersetFeatureIDs, subsetFeatureIDs, inverseFeatureID);
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

	protected EStructuralFeature getEStructuralFeature(int featureID) {
		return (featureID <= UML_EXTENSION_FEATURE_BASE)
				? ExtensionHolder.getExtensionFeature(owner.eClass(), featureID)
				: owner.eClass().getEStructuralFeature(featureID);
	}

	@Override
	protected void supersetAdd(Object object) {
		if (supersetFeatureIDs != null && enforceSubsetConstraints()) {
			for (int i = 0; i < supersetFeatureIDs.length; i++) {
				EStructuralFeature supersetEStructuralFeature = getEStructuralFeature(supersetFeatureIDs[i]);

				if (supersetEStructuralFeature.isMany()) {
					@SuppressWarnings("unchecked")
					EList<Object> supersetEList = (EList<Object>) owner.eGet(supersetEStructuralFeature);

					if (!supersetEList.contains(object)) {
						supersetEList.add(object);
					}
				}
			}
		}
	}

	@Override
	protected void subsetRemove(Object object) {
		if (subsetFeatureIDs != null && enforceSupersetConstraints()) {
			for (int i = 0; i < subsetFeatureIDs.length; i++) {
				EStructuralFeature subsetEStructuralFeature = getEStructuralFeature(subsetFeatureIDs[i]);

				if (subsetEStructuralFeature.isMany()) {
					@SuppressWarnings("unchecked")
					EList<Object> list = ((EList<Object>) owner.eGet(subsetEStructuralFeature));
					list.remove(object);
				} else if (object.equals(owner.eGet(subsetEStructuralFeature))) {
					owner.eSet(subsetEStructuralFeature, null);
				}
			}
		}
	}

	//
	// Nested types
	//

	public static class Resolving<E>
			extends SubsetSupersetEObjectContainmentWithInverseEListExt<E> {

		private static final long serialVersionUID = 1L;

		public Resolving(Class<?> dataClass, InternalUMLRTElement owner,
				int featureID, int[] supersetFeatureIDs, int[] subsetFeatureIDs, int inverseFeatureID) {

			super(dataClass, owner, featureID, supersetFeatureIDs, subsetFeatureIDs, inverseFeatureID);
		}

		@Override
		protected boolean hasProxies() {
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected E resolve(int index, E object) {
			return (E) resolve(index, (EObject) object);
		}
	}

}
