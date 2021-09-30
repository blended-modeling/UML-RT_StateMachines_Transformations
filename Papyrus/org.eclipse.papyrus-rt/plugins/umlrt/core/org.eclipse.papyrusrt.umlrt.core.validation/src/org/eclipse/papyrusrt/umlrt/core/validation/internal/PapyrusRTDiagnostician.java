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

package org.eclipse.papyrusrt.umlrt.core.validation.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.papyrus.infra.services.validation.IValidationFilter;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.validation.internal.OCLEValidatorAdapter;
import org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTValidator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Papyrus-style diagnostician for UML-RT.
 */
@SuppressWarnings("restriction")
public class PapyrusRTDiagnostician extends UMLDiagnostician {

	/**
	 * Initializes me.
	 */
	public PapyrusRTDiagnostician() {
		super();

		// Replace the UML validator with one that uses our validator
		UMLRTValidator umlrt = new UMLRTValidator();

		// In the OCL adapter
		validatorAdapter = new OCLEValidatorAdapter(umlrt);

		// And in the registry
		eValidatorRegistry = new EValidatorRegistryImpl(EValidator.Registry.INSTANCE);
		eValidatorRegistry.put(UMLPackage.eINSTANCE, umlrt);
	}

	@Override
	protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = doValidateStereotypeApplications(eObject, diagnostics, context);

		if (result || (diagnostics != null)) {
			result = basicValidateContents(eObject, diagnostics, context) && result;
		}

		return result;
	}

	protected boolean basicValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;

		// Avoid the extended contents list because we don't need to (nor can we properly)
		// validate the extension objects
		List<EObject> eContents = EContentsEList.createEContentsEList(eObject);
		if (!eContents.isEmpty()) {
			for (Iterator<EObject> iter = eContents.iterator(); iter.hasNext() && (result || (diagnostics != null));) {
				EObject next = iter.next();
				result = validate(next, diagnostics, context) && result;
			}
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * Applicability filter for the Papyrus-RT-specific diagnostician.
	 */
	public static class Filter implements IValidationFilter {

		/**
		 * Initializes me.
		 */
		public Filter() {
			super();
		}

		@Override
		public boolean isApplicable(EObject element) {
			Element uml = TypeUtils.as(element, Element.class);
			if (uml == null) {
				uml = UMLUtil.getBaseElement(element);
			}

			return (uml != null) && UMLRTProfileUtils.isUMLRTProfileApplied(uml);
		}

	}
}
