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

package org.eclipse.papyrusrt.umlrt.uml.edit;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.provider.UMLRTEditPlugin;

/**
 * Root item provider adapter for UML-RT façade model elements.
 */
public class UMLRTItemProviderAdapter extends ItemProviderAdapter {

	private static final EnumMap<UMLRTInheritanceKind, String> INHERITANCE_OVERLAYS = new EnumMap<>(UMLRTInheritanceKind.class);

	static {
		INHERITANCE_OVERLAYS.put(UMLRTInheritanceKind.INHERITED, "full/ovr16/inherited_ovr.png"); //$NON-NLS-1$
		INHERITANCE_OVERLAYS.put(UMLRTInheritanceKind.REDEFINED, "full/ovr16/redefinition_ovr.png"); //$NON-NLS-1$
		INHERITANCE_OVERLAYS.put(UMLRTInheritanceKind.EXCLUDED, "full/ovr16/excluded_ovr.png"); //$NON-NLS-1$
	}

	/**
	 * Initializes me with the adapter factory that owns me.
	 *
	 * @param adapterFactory
	 *            my adapter factory
	 */
	public UMLRTItemProviderAdapter(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * Add an overlay for the inheritance state of the element.
	 */
	@Override
	protected Object overlayImage(Object object, Object image) {
		String imageKey = (object instanceof UMLRTNamedElement)
				? INHERITANCE_OVERLAYS.get(((UMLRTNamedElement) object).getInheritanceKind())
				: null;

		if (imageKey != null) {
			List<Object> images = new ArrayList<>(2);
			images.add(image);
			images.add(UMLRTEditPlugin.INSTANCE.getImage(imageKey));
			image = new ComposedImage(images);
		}

		return image;
	}

}
