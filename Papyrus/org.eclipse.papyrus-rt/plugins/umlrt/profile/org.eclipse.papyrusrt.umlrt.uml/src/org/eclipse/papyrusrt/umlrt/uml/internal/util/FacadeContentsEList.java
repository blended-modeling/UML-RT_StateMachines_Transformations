/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentsEList;

/**
 * A contents list for façade features that can include references that
 * optionally do not filter out excluded elements.
 */
public class FacadeContentsEList<E> extends EContentsEList<E> {

	/**
	 * Creates a façade contents list on all containment references of the
	 * given {@code eObject} with the option to keep excluded elements.
	 * 
	 * @param eObject
	 *            the object owning the features to be aggregated
	 * @param withExclusions
	 *            whether to retain excluded elements in the view of each feature
	 */
	public FacadeContentsEList(InternalFacadeObject eObject, boolean withExclusions) {
		super(wrap(eObject, withExclusions));
	}

	/**
	 * Creates a façade contents list on specific features of the
	 * given {@code eObject} with the option to keep excluded elements.
	 * 
	 * @param eObject
	 *            the object owning the features to be aggregated
	 * @param withExclusions
	 *            whether to retain excluded elements in the view of each feature
	 * @param eStructuralFeatures
	 *            the features to aggregate in the contents list
	 */
	public FacadeContentsEList(InternalFacadeObject eObject, boolean withExclusions, List<? extends EStructuralFeature> eStructuralFeatures) {
		super(wrap(eObject, withExclusions), eStructuralFeatures);
	}

	/**
	 * Creates a façade contents list on specific features of the
	 * given {@code eObject} with the option to keep excluded elements.
	 * 
	 * @param eObject
	 *            the object owning the features to be aggregated
	 * @param withExclusions
	 *            whether to retain excluded elements in the view of each feature
	 * @param eStructuralFeatures
	 *            the features to aggregate in the contents list
	 */
	public FacadeContentsEList(InternalFacadeObject eObject, boolean withExclusions, EStructuralFeature[] eStructuralFeatures) {
		super(wrap(eObject, withExclusions), eStructuralFeatures);
	}

	/**
	 * Creates a façade contents list on specific features of the
	 * given {@code eObject} with the option to keep excluded elements.
	 * 
	 * @param eObject
	 *            the object owning the features to be aggregated
	 * @param withExclusions
	 *            whether to retain excluded elements in the view of each feature
	 * @param eStructuralFeatures
	 *            the IDs of features to aggregate in the contents list
	 */
	public FacadeContentsEList(InternalFacadeObject eObject, boolean withExclusions, int[] featureIDs) {
		super(wrap(eObject, withExclusions), features(eObject, featureIDs));
	}

	/**
	 * Wraps an object in a view that accesses features with exclusions via the standard EMF reflection
	 * API.
	 * 
	 * @param eObject
	 *            a façade object
	 * @param withExclusions
	 *            whether to retain exclusions in all accessed features
	 * 
	 * @return the wrapper, which is optimized in the case of not retaining exclusions
	 */
	protected static EObject wrap(EObject eObject, boolean withExclusions) {
		return (withExclusions && (eObject instanceof InternalFacadeObject))
				? (EObject) Proxy.newProxyInstance(FacadeContentsEList.class.getClassLoader(), new Class<?>[] { InternalFacadeObject.class },
						(proxy, method, args) -> {
							switch (method.getName()) {
							case "eGet":
								if ((args.length == 2)
										&& (args[0] instanceof EReference)
										&& (args[1] instanceof Boolean)) {

									return ((InternalFacadeObject) eObject).facadeGetAll((EReference) args[0]);
								}
								//$FALL-THROUGH$
							default:
								return method.invoke(eObject, args);
							}
						})
				: eObject;
	}

	protected static List<EStructuralFeature> features(EObject owner, int[] featureIDs) {
		return IntStream.of(featureIDs)
				.mapToObj(owner.eClass()::getEStructuralFeature)
				.collect(Collectors.toList());
	}

	@Override
	protected boolean useIsSet() {
		// Don't cut off inheritable features like Operation::ownedParameter
		return false;
	}

	@Override
	protected ListIterator<E> newResolvingListIterator() {
		return new ResolvingFeatureIteratorImpl<E>(eObject, eStructuralFeatures) {
			@Override
			protected boolean useIsSet() {
				return FacadeContentsEList.this.useIsSet();
			}
		};
	}

	@Override
	protected ListIterator<E> newNonResolvingListIterator() {
		return new FeatureIteratorImpl<E>(eObject, eStructuralFeatures) {
			@Override
			protected boolean useIsSet() {
				return FacadeContentsEList.this.useIsSet();
			}
		};
	}
}
