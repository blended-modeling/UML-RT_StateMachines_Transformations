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

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.collect.ImmutableSet;

/**
 * Advice for classifiers in general in a UML-RT model.
 */
public class ClassifierEditHelperAdvice extends AbstractEditHelperAdvice {
	/** The UML has more than one nested-classifiers property amongst the classifiers. */
	private static final Set<EReference> NESTED_CLASSIFIER_REFERENCES = ImmutableSet.of(
			UMLPackage.Literals.CLASS__NESTED_CLASSIFIER,
			UMLPackage.Literals.INTERFACE__NESTED_CLASSIFIER);

	/**
	 * Initializes me.
	 */
	public ClassifierEditHelperAdvice() {
		super();
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		boolean result;

		if (request instanceof CreateElementRequest) {
			result = approveCreateRequest((CreateElementRequest) request);
		} else if (request instanceof SetRequest) {
			result = approveSetRequest((SetRequest) request);
		} else {
			result = super.approveRequest(request);
		}

		return result;
	}

	protected boolean approveSetRequest(SetRequest request) {
		boolean result = true;

		if (NESTED_CLASSIFIER_REFERENCES.contains(request.getFeature())
				&& UMLRTProfileUtils.isUMLRTProfileApplied((Classifier) request.getElementToEdit())) {

			// Don't attempt to drop protocols into the nested classifiers
			// of any classifier (cf. bug 497742)

			// It's sure to be some kind of EObject because the feature is an EReference
			@SuppressWarnings("unchecked")
			Collection<? extends EObject> collection = (request.getValue() instanceof Collection<?>)
					? (Collection<? extends EObject>) request.getValue()
					: Collections.singleton((EObject) request.getValue());

			result = !collection.isEmpty() // Happens when the object under the mouse is not a nested classifier
					&& !collection.stream().anyMatch(ProtocolUtils::isProtocol);
		}

		return result;
	}

	protected boolean approveCreateRequest(CreateElementRequest request) {
		boolean result = true;

		if (ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.GENERALIZATION)) {
			// Enforce single generalization
			if (request.getContainer() instanceof Classifier) {
				UMLRTNamedElement element = UMLRTFactory.create((Classifier) request.getContainer());
				if (element instanceof UMLRTClassifier) {
					UMLRTClassifier classifier = (UMLRTClassifier) element;
					result = classifier.toUML().getGeneralizations().isEmpty();
				}
			}
		}

		return result;
	}

}
