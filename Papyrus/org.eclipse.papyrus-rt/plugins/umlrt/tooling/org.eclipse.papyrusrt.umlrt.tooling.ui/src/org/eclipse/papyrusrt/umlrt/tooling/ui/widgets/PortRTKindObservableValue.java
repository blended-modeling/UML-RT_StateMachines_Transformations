/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 489380, 489434, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.PapyrusObservableValue;
import org.eclipse.papyrusrt.umlrt.core.utils.MultipleAdapter;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * The Class PortRTKindObservableValue.
 */
public class PortRTKindObservableValue extends PapyrusObservableValue implements IObserving {

	/** The port. */
	private Port port;

	/** The stereotype application. */
	private RTPort rtPort;

	private PortAdapter portAdapter;

	/**
	 * Constructor.
	 *
	 * @param RTPortApplication
	 *            the RT port application
	 * @param domain
	 *            the domain
	 */
	public PortRTKindObservableValue(final EObject rtPortApplication, final TransactionalEditingDomain domain) {
		super(UMLUtil.getBaseElement(rtPortApplication), UMLUtil.getBaseElement(rtPortApplication).eContainingFeature(), domain, GMFtoEMFCommandWrapper::wrap);

		if (rtPortApplication instanceof RTPort) {
			rtPort = (RTPort) rtPortApplication;
			port = rtPort.getBase_Port();

			rtPort.eAdapters().add(getListener());
			port.eAdapters().add(getListener());
		}
	}

	@Override
	public synchronized void dispose() {
		try {
			if (portAdapter != null) {
				portAdapter.dispose();
				portAdapter = null;
			}
		} finally {
			super.dispose();
		}
	}

	/**
	 * Retrieve Listener to put on the Stereotype Application.
	 *
	 * @return the listener
	 */
	protected AdapterImpl getListener() {
		if (portAdapter == null) {
			portAdapter = new PortAdapter();
		}
		return portAdapter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValueType() {
		return UMLRTPortKind.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getObserved() {
		return port;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object doGetValue() {
		return RTPortUtils.getPortKind(port);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Command getCommand(Object value) {
		Command command = null;
		if (value instanceof UMLRTPortKind) {
			return GMFtoEMFCommandWrapper.wrap(RTPortUtils.getChangePortKindCommand(port, (UMLRTPortKind) value));
		}
		return command;
	}

	//
	// Nested types
	//

	private class PortAdapter extends MultipleAdapter {
		PortAdapter() {
			super(2);
		}

		@Override
		public void notifyChanged(Notification notification) {
			if (!notification.isTouch()) {
				Object notifier = notification.getNotifier();
				int type = notification.getEventType();
				Object feature = notification.getFeature();

				// Case of StereotypeApplication that is changed (unless it has been unapplied)
				if ((notifier == rtPort) && (type == Notification.SET) && (rtPort.getBase_Port() == port)) {
					EList<EStructuralFeature> realTimeFeatureList = rtPort.eClass().getEStructuralFeatures();
					if (realTimeFeatureList.contains(feature)) {
						fireDiff(notification);
					}
					// Case if it is the UML Port that is modified
				} else if ((notifier == port) && (type == Notification.SET)) {
					EList<EStructuralFeature> umlFeatureList = port.eClass().getEStructuralFeatures();
					if (umlFeatureList.contains(feature)) {
						fireDiff(notification);
					}
				}
			}
		}

		private void fireDiff(Notification notification) {
			final ValueDiff diff = Diffs.createValueDiff(notification.getOldValue(), notification.getNewValue());
			getRealm().exec(new Runnable() {
				@Override
				public void run() {
					fireValueChange(diff);
				}
			});
		}

	}
}
