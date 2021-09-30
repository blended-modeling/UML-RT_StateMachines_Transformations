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

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.util.UMLValidator;

/**
 * A custom validator that understands the inheritance structure of UML-RT
 * models and the ways in which they subvert the EMF run-time's assumptions
 * about how the model is implemented. This validator supersedes the standard
 * {@link UMLValidator} in the {@link UMLRTDiagnostician}.
 */
public class UMLRTValidator extends UMLValidator {

	/**
	 * Initializes me.
	 */
	public UMLRTValidator() {
		super();
	}

	/**
	 * Account for inheritable features that can have a non-zero cardinality despite
	 * being unset.
	 */
	@Override
	protected boolean validate_MultiplicityConforms(EObject eObject, EStructuralFeature eStructuralFeature, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result;

		if (!(eObject instanceof InternalUMLRTElement) || eStructuralFeature.isMany() || (getLowerBound(eObject, eStructuralFeature) == 0)) {
			result = super.validate_MultiplicityConforms(eObject, eStructuralFeature, diagnostics, context);
		} else {
			InternalUMLRTElement rt = (InternalUMLRTElement) eObject;
			if (rt.rtInheritedFeatures().contains(eStructuralFeature)) {
				// Unset state implies inherited, so don't consider it. And have to resolve
				// to compute the inheritance
				result = eObject.eGet(eStructuralFeature) != null;
				if (!result && (diagnostics != null)) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR,
							EObjectValidator.DIAGNOSTIC_SOURCE,
							EObjectValidator.EOBJECT__EVERY_MULTIPCITY_CONFORMS,
							"_UI_RequiredFeatureMustBeSet_diagnostic", //$NON-NLS-1$
							new Object[] {
									getFeatureLabel(eStructuralFeature, context),
									getObjectLabel(eObject, context) },
							new Object[] { eObject, eStructuralFeature }, context));
				}
			} else {
				result = super.validate_MultiplicityConforms(eObject, eStructuralFeature, diagnostics, context);
			}
		}

		return result;
	}
}
