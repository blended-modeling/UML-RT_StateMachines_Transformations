/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.decorator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider.UMLRTFilteredLabelProvider;

@SuppressWarnings("restriction")
/** Specialized ItemProviderDecorator for {@link ProtocolMessageChange} diffs. */
public class ProtocolMessageChangeItemProviderDecorator extends UMLRTDiffItemProviderDecorator {

	private UMLRTFilteredLabelProvider umlLabelProvider = new UMLRTFilteredLabelProvider();

	public ProtocolMessageChangeItemProviderDecorator(
			ComposeableAdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		ProtocolMessageChange diff = (ProtocolMessageChange) object;
		EObject discriminant = diff.getDiscriminant();
		return umlLabelProvider.getText(discriminant);
	}

	@Override
	public Object getImage(Object object) {
		ProtocolMessageChange diff = (ProtocolMessageChange) object;
		EObject discriminant = diff.getDiscriminant();
		Object image = umlLabelProvider.getImage(discriminant);
		if (getOverlayProvider() != null && image != null) {
			image = getOverlayProvider().getComposedImage(diff, image);
		}
		return image;
	}

}
