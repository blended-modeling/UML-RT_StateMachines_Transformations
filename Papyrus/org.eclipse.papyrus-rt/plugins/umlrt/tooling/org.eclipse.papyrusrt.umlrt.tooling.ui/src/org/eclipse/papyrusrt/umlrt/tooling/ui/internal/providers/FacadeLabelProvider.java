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
 *   Christian W. Damus - bugs 475905, 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers;

import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFFilteredLabelProvider;
import org.eclipse.papyrus.uml.tools.providers.DelegatingItemLabelProvider;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.MultiplicityElement;

/**
 * UML-RT Façade label provider for the <em>Label Provider Service</em>.
 */
public class FacadeLabelProvider extends EMFFilteredLabelProvider {

	private final CopyOnWriteArrayList<ILabelProviderListener> listeners = new CopyOnWriteArrayList<>();
	private final IItemLabelProvider delegate;
	private final INotifyChangedListener changeForwarder = this::forwardChange;

	public FacadeLabelProvider() {
		super();

		delegate = new DelegatingItemLabelProvider();

		if (delegate instanceof IChangeNotifier) {
			((IChangeNotifier) delegate).addListener(changeForwarder);
		}
	}

	@Override
	public void dispose() {
		if (delegate instanceof IChangeNotifier) {
			((IChangeNotifier) delegate).removeListener(changeForwarder);
		}

		super.dispose();
	}

	@Override
	public boolean accept(Object element) {
		if (element instanceof IStructuredSelection) {
			return accept((IStructuredSelection) element);
		}

		EObject eObject = EMFHelper.getEObject(element);

		if (eObject == null) {
			return false;
		}

		return eObject.eClass().getEPackage() == UMLRTUMLRTPackage.eINSTANCE;
	}

	@Override
	protected String getText(EObject element) {
		if (element instanceof UMLRTProtocolMessage) {
			// Special case for direction prefix
			UMLRTProtocolMessage message = (UMLRTProtocolMessage) element;

			switch (message.getKind()) {
			case IN:
				return "in " + getText(message.toUML());
			case OUT:
				return "out " + getText(message.toUML());
			case IN_OUT:
				return "inout " + getText(message.toUML());
			default:
				return getText(message.toUML());
			}
		} else if (element instanceof FacadeObject) {
			// Delegate to UML
			element = ((FacadeObject) element).toUML();
		}

		if (element instanceof MultiplicityElement) {
			// Ensure that the bounds values (if any) have the item-provider attached
			// so that they will fire viewer notifications when their values change.
			MultiplicityElement mult = (MultiplicityElement) element;
			if (mult.getLowerValue() != null) {
				getText(mult.getLowerValue());
			}
			if (mult.getUpperValue() != null) {
				getText(mult.getUpperValue());
			}
		}

		return delegate.getText(element);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// The superclass's handling of listeners is useless to us
		listeners.addIfAbsent(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// The superclass's handling of listeners is useless to us
		listeners.remove(listener);
	}

	private void forwardChange(Notification notification) {
		if (notification instanceof ViewerNotification) {
			ViewerNotification vnot = (ViewerNotification) notification;
			if (vnot.isLabelUpdate() && !listeners.isEmpty()) {
				LabelProviderChangedEvent event = new LabelProviderChangedEvent(this, vnot.getElement());
				listeners.forEach(l -> {
					try {
						l.labelProviderChanged(event);
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in label provider listener", e); //$NON-NLS-1$
					}
				});
			}
		}
	}

}
