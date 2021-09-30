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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;

/**
 * Custom edit part for list items supporting inheritance.
 */
public class RTListItemEditPart extends LabelInListCompartmentEditPart implements ISubordinateInheritableEditPart {

	/**
	 * Initializes me with my view.
	 *
	 * @param view
	 *            my label-in-list-compartment view
	 */
	public RTListItemEditPart(View view) {
		super(view);
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	protected Image getLabelIcon() {
		Image result = null;
		EObject parserElement = getParserElement();

		if (parserElement != null) {
			// The notation view references the root definition
			EObject semantic = parserElement;
			if (semantic instanceof Element) {
				semantic = UMLRTExtensionUtil.getRootDefinition((Element) semantic);
			}

			for (View view : DiagramEditPartsUtil.findViews(semantic, getViewer())) {
				if (AppearanceHelper.showElementIcon(view)) {
					try {
						return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, parserElement).getLabelProvider().getImage(parserElement);
					} catch (ServiceException e) {
						Activator.log.error(e);
					}
				}
			}
		}

		return result;
	}

}
