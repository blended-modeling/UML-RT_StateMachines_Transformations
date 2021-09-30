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
 *   Young-SOo Roh - bug#516977
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.IndirectPropertyLabelEditPolicy;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RTPortLabelHelper;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RTPropertyLabelHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Custom mask-label edit policy for UML-RT diagrams that delegates
 * display configuration to the RT-specific label helper.
 * 
 * @see RTPropertyLabelHelper
 */
public class RTMaskLabelEditPolicy extends IndirectPropertyLabelEditPolicy {

	private MultiplicityElementAdapter adapter = new MultiplicityElementAdapter.ForProperty() {
		@Override
		protected void handleMultiplicityChanged(Notification notification) {
			RTMaskLabelEditPolicy.this.notifyChanged(notification);
		}
	};

	@Override
	protected Element initSemanticElement() {
		return (Element) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
	}

	@Override
	public void refreshDisplay() {
		EditPart editPartHost = getHost();
		// verifies the edit part before to call the concerned helper
		if (editPartHost.getParent() instanceof RTPortEditPart) {
			// if it is a RTPortEditPart, call PortLabelHelper
			RTPortLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) editPartHost);
		} else {
			// calls the helper for this edit part
			RTPropertyLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) editPartHost);
		}
	}

	@Override
	public void addAdditionalListeners() {
		Property property = getUMLElement();

		// check host semantic element is not null
		if (property == null) {
			Activator.log.error("No property in RTMaskLabelEditPolicy", null);
			return;
		}

		adapter.adapt(property);
	}

	@Override
	protected void removeAdditionalListeners() {
		if (adapter.getTarget() != null) {
			adapter.unadapt(adapter.getTarget());
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		// Handle when a port is defined as conjugated
		if (notification.getFeature() == UMLPackage.Literals.PORT__IS_CONJUGATED) {
			refreshDisplay();
		} else {
			super.notifyChanged(notification);

			Object object = notification.getNotifier();
			Property property = getUMLElement();
			if ((object != null) && (property != null)) {
				// Handle also the case of opaque expression bodies changing
				if (notification.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY) {
					refreshDisplay();
				}
			}
		}
	}
}
