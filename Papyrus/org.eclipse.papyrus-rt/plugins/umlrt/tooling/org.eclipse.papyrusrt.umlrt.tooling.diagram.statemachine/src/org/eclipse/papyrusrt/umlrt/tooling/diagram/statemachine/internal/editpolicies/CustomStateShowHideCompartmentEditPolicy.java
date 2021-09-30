/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TopGraphicEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideCompartmentRequest;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.SetVisibilityCommand;

/**
 * @author RS211865
 *
 */
public class CustomStateShowHideCompartmentEditPolicy extends ShowHideCompartmentEditPolicy {

	/** A listener on the created compartment to refresh parent */
	private NotificationListener listener = new NotificationListener() {
		@Override
		public void notifyChanged(Notification notification) {
			getHost().refresh();
		}
	};

	/** the list of the listened compartment */
	private List<View> childlistened = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getShowHideCompartmentCommand(ShowHideCompartmentRequest request) {
		if (getHost() instanceof TopGraphicEditPart) {
			List<?> views = getAllNotationViews((TopGraphicEditPart) getHost());
			for (Iterator<?> iter = views.iterator(); iter.hasNext();) {
				View childView = (View) iter.next();
				if (ViewUtil.isPropertySupported(childView, request.getPropertyID())) {
					if (childView.equals(request.getCompartment())) {

						DiagramEventBroker diagramEventBroker = getDiagramEventBroker();

						if (request.getValue() instanceof Boolean && null != diagramEventBroker) {
							if ((Boolean) request.getValue()) {
								// add a listener on the compartment
								diagramEventBroker.addNotificationListener(childView, listener);
								childlistened.add(childView);
							} else {
								// remove a listener on the compartment
								diagramEventBroker.removeNotificationListener(childView, listener);
								childlistened.remove(childView);
							}
							return new ICommandProxy(new SetVisibilityCommand(getEditingDomain(), childView, (Boolean) request.getValue()));
						}
					}
				}
			}
		}
		return super.getShowHideCompartmentCommand(request);
	}

}
