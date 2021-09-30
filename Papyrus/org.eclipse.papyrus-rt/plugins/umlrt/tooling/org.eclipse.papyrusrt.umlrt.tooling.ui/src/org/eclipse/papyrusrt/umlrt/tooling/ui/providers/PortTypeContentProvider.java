/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 514413
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.providers;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Package;

/**
 * a content provider to select the Protocol type of a port: exclude base protocols
 *
 */
public class PortTypeContentProvider extends EncapsulatedContentProvider {

	/**
	 * Constructor
	 * 
	 * @param provider
	 *            The encapsulated Content Provider
	 *
	 */
	public PortTypeContentProvider(final IStaticContentProvider provider) {
		super(provider);
	}


	/**
	 * 
	 *
	 * @param element
	 * @return true if the current element is Valid
	 */
	@Override
	public boolean isValidValue(final Object element) {
		boolean result = super.isValidValue(element);

		if (result) {
			EObject eObject = EMFHelper.getEObject(element);
			result = (ProtocolUtils.isProtocol(eObject) &&
					!SystemElementsUtils.isBaseProtocol((Collaboration) eObject));
		}

		return result;
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements()
	 *
	 * @return List of root Element
	 */
	@Override
	public Object[] getElements() {
		return Stream.of(super.getElements())
				.toArray();
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return List of Root Elements
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		return getElements();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return The list of Children
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		return Stream.of(super.getChildren(parentElement))
				.filter(this::isNavigable)
				.toArray();
	}

	/**
	 * exclude base protocols from the tree content
	 * 
	 * @param object
	 * @return
	 */
	private boolean isNavigable(Object object) {
		EObject element = EMFHelper.getEObject(object);
		return (element != null) &&
				((element instanceof Package)
						|| (element instanceof EReference) // Explorer advanced mode
						|| isValidValue(element));
	}


}
