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
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A custom diagnostician that understands the inheritance structure of UML-RT
 * models and the ways in which they subvert the EMF run-time's assumptions
 * about how the model is implemented.
 */
public class UMLRTDiagnostician extends Diagnostician {
	public static final UMLRTDiagnostician INSTANCE = new UMLRTDiagnostician();

	/**
	 * Initializes me with the global validator registry.
	 */
	public UMLRTDiagnostician() {
		this(EValidator.Registry.INSTANCE);
	}

	/**
	 * Initializes me with the registry from which I obtain validators
	 *
	 * @param eValidatorRegistry
	 *            my validator registry
	 */
	public UMLRTDiagnostician(EValidator.Registry eValidatorRegistry) {
		super(augment(eValidatorRegistry));
	}

	private static EValidator.Registry augment(EValidator.Registry registry) {
		EValidator.Registry result = new EValidatorRegistryImpl(registry);
		result.put(UMLPackage.eINSTANCE, new UMLRTValidator());
		return result;
	}

	@Override
	protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

}
