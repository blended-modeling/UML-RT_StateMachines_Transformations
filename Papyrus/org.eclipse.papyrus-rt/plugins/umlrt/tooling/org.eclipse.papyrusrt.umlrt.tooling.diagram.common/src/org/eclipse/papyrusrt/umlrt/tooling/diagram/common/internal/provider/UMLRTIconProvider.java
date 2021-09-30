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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.provider;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.icon.GetIconOperation;
import org.eclipse.gmf.runtime.common.ui.services.icon.IIconProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;

/**
 * A GMF icon-provider that delegates to the Papyrus {@link LabelProviderService}
 * for elements of UML-RT models.
 */
public class UMLRTIconProvider extends AbstractProvider implements IIconProvider {

	/**
	 * Initializes me.
	 */
	public UMLRTIconProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		if (operation instanceof GetIconOperation) {
			GetIconOperation getIcon = (GetIconOperation) operation;
			EObject element = PlatformHelper.getAdapter(getIcon.getHint(), EObject.class);

			// If it's an element in a UML-RT model, then we can use our label provider
			// (assuming we have one)
			result = ((element instanceof Element)
					&& UMLRTProfileUtils.isUMLRTProfileApplied((Element) element))
					&& (getLabelService(element) != null);
		}

		return result;
	}

	protected LabelProviderService getLabelService(EObject element) {
		LabelProviderService result = null;

		try {
			result = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, element);
		} catch (ServiceException e) {
			// This is normal in the case that the context is not a Papyrus editor
		}

		return result;
	}

	@Override
	public Image getIcon(IAdaptable hint, int flags) {
		Image result = null;

		EObject element = PlatformHelper.getAdapter(hint, EObject.class);
		if (element != null) {
			LabelProviderService service = getLabelService(element);
			if (service != null) {
				result = service.getLabelProvider().getImage(element);
			}
		}

		return result;
	}

}
