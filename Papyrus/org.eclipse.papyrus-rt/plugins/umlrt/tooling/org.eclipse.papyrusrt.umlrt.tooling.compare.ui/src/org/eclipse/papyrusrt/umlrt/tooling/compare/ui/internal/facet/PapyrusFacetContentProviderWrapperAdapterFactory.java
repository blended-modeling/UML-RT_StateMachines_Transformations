/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stefan Dirix - initial API and implementation
 *******************************************************************************/

/** 
 * XXX: Copied from the org.eclipse.emf.compare.diagram.ide.ui.papyrus bundle 
 *  of EMFCompare because it is not (yet) available in the target platform
 */

package org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.facet;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.Disposable;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * Couples the Papyrus Facet mechanism with the AdapterFactory approach of EMFCompare.
 * 
 * @author Stefan Dirix <sdirix@eclipsesource.com>
 */
public class PapyrusFacetContentProviderWrapperAdapterFactory extends UMLItemProviderAdapterFactory {

	/**
	 * Collects and disposes associated adapters.
	 */
	private Disposable disposable = new Disposable();

	/**
	 * Constructor.
	 */
	public PapyrusFacetContentProviderWrapperAdapterFactory() {
		super();
		// Only support content types
		supportedTypes.clear();
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
	}

	@Override
	public Adapter createAdapter(Notifier target) {
		ResourceSet resourceSet = getResourceSet(target);
		if (resourceSet != null) {
			return new PapyrusFacetContentProviderWrapper(this, resourceSet);
		}
		return super.createAdapter(target);
	}

	/**
	 * Determines the {@link ResourceSet} of the given {@code target}.
	 * 
	 * @param target
	 *            The {@Notifier} for which a {@link ResourceSet} is to be determined.
	 * @return The {@link ResourceSet} for the given {@code target} if there is one, {@code null} otherwise.
	 */
	private ResourceSet getResourceSet(Notifier target) {
		if (EObject.class.isInstance(target)) {
			EObject object = EObject.class.cast(target);
			return object.eResource().getResourceSet();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see NotationItemProviderAdapterFactory#dispose()
	 */
	@Override
	public void dispose() {
		disposable.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see NotationItemProviderAdapterFactory#associate(Adapter adapter, Notifier target)
	 */
	@Override
	protected void associate(Adapter adapter, Notifier target) {
		super.associate(adapter, target);
		if (adapter != null) {
			disposable.add(adapter);
		}
	}
}
