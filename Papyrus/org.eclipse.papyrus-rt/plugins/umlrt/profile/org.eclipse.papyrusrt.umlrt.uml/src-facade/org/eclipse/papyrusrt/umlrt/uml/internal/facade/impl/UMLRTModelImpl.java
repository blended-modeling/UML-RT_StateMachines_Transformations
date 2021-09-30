/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Implementation of the model façade.
 */
public class UMLRTModelImpl implements UMLRTModel {

	private final Resource resource;

	protected UMLRTModelImpl(Resource resource) {
		super();

		this.resource = resource;

		initExtensionExtent();
	}

	/**
	 * Obtains the canonical instance of the model façade for a resource.
	 * 
	 * @param resource
	 *            an UML resource containing an UML-RT model
	 * 
	 * @return its model façade, or {@code null} if the resource is not a valid UML-RT resource
	 */
	public static UMLRTModel getInstance(Resource resource) {
		FacadeAdapter adapter = null;

		if (resource instanceof UMLResource) {
			adapter = (FacadeAdapter) EcoreUtil.getExistingAdapter(resource, UMLRTModel.class);
			if (adapter == null) {
				adapter = new UMLRTModelImpl(resource).new FacadeAdapter();
				resource.eAdapters().add(adapter);
			}
		}

		return (adapter == null) ? null : adapter.getModel();
	}

	private void initExtensionExtent() {
		// Ensure that extensions are moved into the the resource set's extension extent
		Resource extensionResource = ExtensionResource.demandExtensionExtent(resource);
		if (extensionResource != ExtensionResource.SHARED_EXTENT) {
			EList<EObject> extensionExtent = extensionResource.getContents();
			// We will be removing objects from this extent
			for (EObject next : new ArrayList<>(ExtensionResource.SHARED_EXTENT.getContents())) {
				if (next instanceof ExtElement) {
					ExtElement extension = (ExtElement) next;
					if (extension.getExtendedElement().eResource() == resource) {
						// And add it to its proper extent
						extensionExtent.add(extension);
					}
				}
			}
		}
	}

	/**
	 * Obtains the UML representation of the model.
	 *
	 * @return the UML representation
	 */
	@Override
	public Resource toUML() {
		return resource;
	}

	/**
	 * Obtains the URI of the resource that I represent.
	 *
	 * @return my URI
	 */
	@Override
	public URI getURI() {
		return resource.getURI();
	}

	/**
	 * Obtains the root package of the model (or sub-model, as the case may be).
	 *
	 * @return the root package
	 */
	@Override
	public UMLRTPackage getRoot() {
		Package result = (Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
		return UMLRTPackage.getInstance(result);
	}

	@Override
	public String toString() {
		return String.format("UMLRTModel(uri=%s)", getURI()); //$NON-NLS-1$
	}

	//
	// Nested types
	//

	private class FacadeAdapter extends AdapterImpl {
		FacadeAdapter() {
			super();
		}

		UMLRTModel getModel() {
			return UMLRTModelImpl.this;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == UMLRTModel.class;
		}
	}

}
