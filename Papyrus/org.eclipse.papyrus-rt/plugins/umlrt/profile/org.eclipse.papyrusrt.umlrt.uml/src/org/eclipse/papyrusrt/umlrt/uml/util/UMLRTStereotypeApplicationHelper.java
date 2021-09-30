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

package org.eclipse.papyrusrt.umlrt.uml.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * A wrapper for a {@link ResourceSet}'s stereotype-application helper
 * that ensures creation of the {@link UMLRealTimePackage} factory implementation
 * for the <em>UMLRealTime</em> profile that supports incremental redefinition
 * according to UML-RT semantics.
 */
public class UMLRTStereotypeApplicationHelper extends StereotypeApplicationHelper {

	private final StereotypeApplicationHelper delegate;

	/**
	 * Initializes me with the helper that I wrap.
	 * 
	 * @param delegate
	 *            the wrapped helper
	 */
	public UMLRTStereotypeApplicationHelper(StereotypeApplicationHelper delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public boolean addToContainmentList(Element element, EObject stereotypeApplication) {
		// Don't allow the stereotype applications for virtual elements to be stored
		// anywhere else than the extension extent (otherwise, it may end up in
		// a decorator model resource or worse)
		return UMLRTExtensionUtil.isVirtualElement(element)
				? super.addToContainmentList(element, stereotypeApplication)
				: delegate.addToContainmentList(element, stereotypeApplication);
	}

	@Override
	public boolean removeFromContainmentList(Element element, EObject stereotypeApplication) {
		return UMLRTExtensionUtil.isVirtualElement(element)
				? super.removeFromContainmentList(element, stereotypeApplication)
				: delegate.removeFromContainmentList(element, stereotypeApplication);
	}

	@Override
	protected EList<EObject> getContainmentList(Element element, EClass definition) {
		Resource eResource = ((InternalEObject) element).eInternalResource();

		return (eResource != null)
				? eResource.getContents()
				: null;
	}

	@Override
	public EObject applyStereotype(Element element, EClass definition) {
		EObject result;

		// Use the factory registered locally in the resource set, if possible
		EFactory factory = getEFactory(element, definition);
		result = factory.create(definition);

		CacheAdapter.getInstance().adapt(result);

		addToContainmentList(element, result);
		UMLUtil.setBaseElement(result, element);

		return result;
	}

	/**
	 * Obtains the best factory for instantiation of the given stereotype {@code definition}.
	 * For all but class from the {@link UMLRealTimePackage}, this will be the pakage's
	 * static factory. For <em>UMLRealTime</em> stereotypes, it is the factory registered
	 * in the resource set's package registry.
	 * 
	 * @param element
	 *            an element in the context of some resource set
	 * @param definition
	 *            the stereotype definition to instantiate
	 * 
	 * @return the best factory for the job
	 */
	protected EFactory getEFactory(Element element, EClass definition) {
		EFactory result;

		// Get the registered factory, if possible
		EPackage ePackage = definition.getEPackage();
		Resource resource = element.eResource();
		ResourceSet rset = (resource == null) ? null : resource.getResourceSet();
		result = (rset == null) ? null : rset.getPackageRegistry().getEFactory(ePackage.getNsURI());
		if (result == null) {
			result = ePackage.getEFactoryInstance();
		}

		return result;
	}
}
