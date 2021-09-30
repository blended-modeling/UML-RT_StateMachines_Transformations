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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.icon.IconService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.swt.graphics.Image;

/**
 * Mix-in interface for UML-RT icon image provision in name edit-parts.
 */
public interface IRTNameEditPart extends IGraphicalEditPart {
	/**
	 * Obtains the best available image for my element from the Icon Service,
	 * unless I am {@linkplain #isShowElementIcon() configured to suppress}
	 * the element icon.
	 * 
	 * @return my icon-service icon
	 */
	default Image getRTLabelIcon() {
		Image result = null;

		EObject element = resolveSemanticElement();
		if (element != null) {
			if (isShowElementIcon()) {
				result = IconService.getInstance().getIcon(new EObjectAdapter(element));
			}
		}

		return result;
	}

	/**
	 * Queries whether either my view or my primary view is
	 * configured to show the element icon.
	 * 
	 * @return whether to show the element icon
	 * 
	 * @see AppearanceHelper#showElementIcon(org.eclipse.emf.ecore.EModelElement)
	 */
	default boolean isShowElementIcon() {
		View view = getNotationView();

		boolean result = AppearanceHelper.showElementIcon(view);
		if (!result) {
			View primary = getPrimaryView();
			if ((primary != null) && (primary != view)) {
				result = AppearanceHelper.showElementIcon(primary);
			}
		}

		return result;
	}

}
