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

package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritanceAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;

/**
 * A specialized resource implementation that serves as the extent of
 * UML-RT extension objects for UML.
 */
public class ExtensionResource extends UMLResourceImpl {
	public static final URI EXTENSION_EXTENT_URI = URI.createURI("umlrt://extensions"); //$NON-NLS-1$

	// TODO: This may be prone to leaks
	public static final ExtensionResource SHARED_EXTENT = new ExtensionResource();

	private ReificationAdapter reificationAdapter;

	private InternalUpdateHelper updateHelper = InternalUpdateHelper.DEFAULT;

	public ExtensionResource() {
		this(EXTENSION_EXTENT_URI);
	}

	public ExtensionResource(URI uri) {
		super(uri);

		// I implicitly begin in the loaded state. In particular, I must not
		// appear to become loaded on the first addition of an extension
		isLoaded = true;

		// Preemptively add the CacheAdapter so that there will be an ECrossReferenceAdapter
		// for Papyrus's EMFHelper.getUsages(...) API to find, shortcutting the creation of
		// a new one. And also because UML elements will be attached eventually anyways
		CacheAdapter.getInstance().adapt(this);

		// And install the inheritance adapter
		InheritanceAdapter.getInstance(this);
	}

	/**
	 * Obtains the extension resource that either contains the given
	 * {@code context} object or that is the extension resource for the
	 * {@link ResourceSet} containing the {@code context} element.
	 * 
	 * @param context
	 *            a model element
	 * 
	 * @return its extension resource, or {@code null} if the {@code context}
	 *         is not in a resource set that has one
	 */
	public static ExtensionResource getExtensionExtent(EObject context) {
		ExtensionResource result = null;

		Resource resource = context.eResource();
		if (resource instanceof ExtensionResource) {
			result = (ExtensionResource) resource;
		} else if (resource != null) {
			ResourceSet rset = resource.getResourceSet();
			if (rset != null) {
				resource = rset.getResource(EXTENSION_EXTENT_URI, false);
				if (resource instanceof ExtensionResource) {
					result = (ExtensionResource) resource;
				}
			}
		}

		return result;
	}

	/**
	 * Obtains the extent in which the UML extension of an {@code object} is
	 * kept. If the {@code object} is in a resource set that does not yet
	 * have an extension extent, it will be created. If it is not in a
	 * resource set, then the shard extent is returned.
	 * 
	 * @param object
	 *            a model element or resource
	 * @return its extension extent, which is never {@code null}
	 */
	public static ExtensionResource demandExtensionExtent(Object object) {
		ExtensionResource result = null;
		ResourceSet rset = null;

		if (object instanceof ResourceSet) {
			rset = (ResourceSet) object;
		} else if (object instanceof Resource) {
			if (object instanceof ExtensionResource) {
				// Easy!
				result = (ExtensionResource) object;
			} else {
				rset = ((Resource) object).getResourceSet();
			}
		} else if (object instanceof EObject) {
			Resource resource = ((EObject) object).eResource();
			if (resource != null) {
				rset = resource.getResourceSet();
			}
		}

		if ((result == null) && (rset != null)) {
			result = (ExtensionResource) rset.getResource(EXTENSION_EXTENT_URI, false);
			if (result == null) {
				result = new ExtensionResource();
				rset.getResources().add(result);
			}
		}

		if (result == null) {
			result = ExtensionResource.SHARED_EXTENT;
		}

		return result;
	}

	/**
	 * Obtains my reification adapter.
	 * 
	 * @return my reificationAdapter
	 */
	public ReificationAdapter getReificationAdapter() {
		if (reificationAdapter == null) {
			ResourceSet rset = getResourceSet();
			reificationAdapter = (rset != null)
					? ReificationAdapter.getInstance(rset)
					: new ReificationAdapter();
		}

		return reificationAdapter;
	}

	@Override
	protected boolean isAttachedDetachedHelperRequired() {
		// We use the helper to ensure attachment of the reification adapter
		return true;
	}

	@Override
	protected void attachedHelper(EObject eObject) {
		super.attachedHelper(eObject);

		// Ensure that it has the reification adapter attached
		getReificationAdapter().addAdapter(eObject);
	}

	@Override
	public NotificationChain basicSetResourceSet(ResourceSet resourceSet, NotificationChain notifications) {
		NotificationChain result = super.basicSetResourceSet(resourceSet, notifications);

		// Recompute this, next time
		reificationAdapter = null;

		// And get the appropriate update helper
		updateHelper = UMLRTUMLPlugin.getInternalUpdateHelperFactory().create(this);
		if (updateHelper == null) {
			updateHelper = InternalUpdateHelper.DEFAULT;
		}

		return result;
	}

	public void run(Object context, Runnable update) {
		updateHelper.run(context, update);
	}

}
