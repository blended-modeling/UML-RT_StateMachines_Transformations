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
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.canonical;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;

/**
 * A listener on notifications from dependencies to trigger the refresh of
 * canonical children for some view.
 */
class CanonicalRefreshListener<E extends EObject> implements NotificationListener {
	private static final Map<EditPart, Runnable> pendingRefreshes = new ConcurrentHashMap<>();

	private final Reference<View> hostView;
	private final Reference<E> dependency;
	private final int hashCode;

	private final NotificationFilter filter;

	CanonicalRefreshListener(View hostView, E dependency) {
		this(hostView, dependency, null);
	}

	CanonicalRefreshListener(View hostView, E dependency, NotificationFilter filter) {
		super();

		this.hostView = new WeakReference<>(hostView);
		this.dependency = new WeakReference<>(dependency);
		this.filter = (filter == null) ? NotificationFilter.NOT_TOUCH : filter;

		// Capture our immutable hash code (for map safety)
		hashCode = Objects.hash(hostView, dependency);
	}

	@Override
	public void notifyChanged(Notification notification) {
		E dependency = this.dependency.get();
		if ((notification.getNotifier() == dependency) && filter.matches(notification)) {
			View hostView = this.hostView.get();
			if (hostView != null) {
				refreshCanonical(hostView);
			}
		}
	}

	void refreshCanonical(View hostView) {
		for (final EditPart ep : DiagramEditPartsUtil.findEditParts(hostView)) {
			// Don't schedule redundant refreshes
			if (ep.isActive() && !pendingRefreshes.containsKey(ep)) {
				Runnable refresh = new Runnable() {

					@Override
					public void run() {
						// No longer pending
						pendingRefreshes.remove(ep);

						if (ep.isActive()) {
							EditPolicy canonical = ep.getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
							if (canonical instanceof CanonicalEditPolicy) {
								((CanonicalEditPolicy) canonical).refresh();
							}
						}
					}
				};

				pendingRefreshes.put(ep, refresh);
				DiagramHelper.asyncExec(ep, refresh);
			}
		}
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	// To prevent adding the same listener twice to the event broker
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (obj instanceof CanonicalRefreshListener<?>) {
			CanonicalRefreshListener<?> other = (CanonicalRefreshListener<?>) obj;
			result = Objects.equals(other.hostView.get(), hostView.get())
					&& Objects.equals(other.dependency.get(), dependency.get());
		}

		return result;
	}
}
