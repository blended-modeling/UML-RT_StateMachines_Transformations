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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.widgets.ReferenceDialog;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A content-provider for façades in the Papyrus {@link ReferenceDialog}.
 */
public class FacadeContentProvider extends UMLContentProvider {

	private final Predicate<UMLRTNamedElement> validator;

	public FacadeContentProvider(UMLRTNamedElement source, EStructuralFeature feature) {
		this(source, feature, null);
	}

	public FacadeContentProvider(UMLRTNamedElement source, EStructuralFeature feature, Predicate<UMLRTNamedElement> validator) {
		super(source.toUML(), feature);

		this.validator = (validator == null) ? __ -> true : validator;
	}

	@Override
	public Object getAdaptedValue(Object selection) {
		EObject element = EMFHelper.getEObject(selection);
		return (element instanceof NamedElement)
				? UMLRTFactory.create((NamedElement) element)
				: element;
	}

	@Override
	protected IStructuredContentProvider getSemanticProvider(EObject source, EStructuralFeature feature, Stereotype stereotype) {
		return getSemanticProvider(root);
	}

	@Override
	public boolean isValidValue(Object element) {
		element = getAdaptedValue(element);
		return (element instanceof UMLRTNamedElement) && validator.test((UMLRTNamedElement) element);
	}

}
