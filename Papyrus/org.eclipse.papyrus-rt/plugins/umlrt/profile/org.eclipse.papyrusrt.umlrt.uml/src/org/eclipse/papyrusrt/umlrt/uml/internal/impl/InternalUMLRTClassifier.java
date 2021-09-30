/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Internal protocol for classifiers.
 */
public interface InternalUMLRTClassifier extends InternalUMLRTRedefinitionContext<InternalUMLRTClassifier>, Classifier {

	@Override
	default InternalUMLRTClassifier rtGetAncestor() {
		InternalUMLRTClassifier result;

		List<Classifier> generals = getGenerals();
		switch (generals.size()) {
		case 0:
			result = null;
			break;
		case 1:
			result = (generals.get(0) instanceof InternalUMLRTClassifier)
					? (InternalUMLRTClassifier) generals.get(0)
					: null;
			break;
		default:
			result = generals.stream()
					.filter(InternalUMLRTClassifier.class::isInstance)
					.filter(g -> g.eClass() == eClass())
					.map(InternalUMLRTClassifier.class::cast)
					.findFirst()
					.orElse(null);
			break;
		}
		return result;
	}

	@Override
	default void rtSetAncestor(InternalUMLRTClassifier supertype) {
		// UML-RT recognizes single inheritance only

		if (supertype == null) {
			new ArrayList<>(getGeneralizations()).forEach(ElementRTOperations::delete);
		} else if (rtGetAncestor() != supertype) {
			if (supertype.eClass() != eClass()) {
				throw new IllegalArgumentException("incompatible metaclass: " + supertype); //$NON-NLS-1$
			}

			List<Generalization> generalizations = getGeneralizations();
			if (generalizations.isEmpty()) {
				createGeneralization(supertype);
			} else {
				generalizations.get(0).setGeneral(supertype);
			}
		}
	}

	@Override
	default Stream<? extends InternalUMLRTClassifier> rtDescendants() {
		return CacheAdapter.getInstance().getNonNavigableInverseReferences(this).stream()
				.filter(setting -> setting.getEStructuralFeature() == UMLPackage.Literals.GENERALIZATION__GENERAL)
				.map(EStructuralFeature.Setting::getEObject)
				.map(Generalization.class::cast)
				.map(Generalization::getSpecific)
				.filter(InternalUMLRTClassifier.class::isInstance)
				.filter(g -> g.eClass() == eClass())
				.map(InternalUMLRTClassifier.class::cast);
	}
}
