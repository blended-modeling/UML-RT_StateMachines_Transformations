/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs;

import java.util.Iterator;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification;
import org.eclipse.papyrusrt.umlrt.core.utils.MultipleAdapter;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.utils.CodeSnippetTabUtil;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Code snippet tab for trigger guard.
 * 
 * @author ysroh
 *
 */
public class TriggerGuardTab extends AbstractCodeSnippetTab {

	/**
	 * Trigger guard listener.
	 */
	private TriggerGuardAdapter guardAdapter;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public TriggerGuardTab() {
	}

	@Override
	public String getTitle() {
		return "Trigger Guard";
	}

	@Override
	protected void doSetInput(EObject input) {
		this.input = CodeSnippetTabUtil.getTrigger(input);
	}

	@Override
	protected Image getImage() {
		UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) input);
		if (trigger.getGuard() != null) {
			return getLabelProvider().getImage(trigger.getGuard().toUML());
		}

		return org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator
				.getImage(org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator.IMG_OBJ16_GUARD);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IObservable getFeatureObservable() {
		Transition transition = (Transition) input.eContainer();
		if (meForFeatureObservable == null) {
			meForFeatureObservable = rtModelFactory.createFromSource(transition, null);
		}
		IObservable observable = meForFeatureObservable.getObservable("ownedRule");
		observable.addChangeListener(event -> {
			// add listener to each constraints
			IObservableList<Constraint> list = (IObservableList<Constraint>) event.getObservable();
			Iterator<Constraint> itor = list.iterator();
			while (itor.hasNext()) {
				Constraint c = itor.next();
				c.eAdapters().add(getGuardListener());
			}
		});

		return observable;
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) input);
		UMLRTGuard guard = trigger.getGuard();
		if (guard != null) {
			return getExpressionList(guard.toUML().getSpecification());
		}
		return null;
	}

	@Override
	protected void commit(Expression expr) {
		UMLRTTrigger trigger = UMLRTFactory.createTrigger((Trigger) input);
		UMLRTGuard guard = trigger.getGuard();
		if (guard == null) {
			trigger.createGuard(expr.getLanguage(), expr.getBody());
		} else {
			guard.getBodies().put(expr.getLanguage(), expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) input);
		UMLRTGuard guard = trigger.getGuard();
		if (guard != null) {
			return getSpecificationObservable(guard.toUML());
		}
		return null;
	}

	/**
	 * Get guard listener.
	 * 
	 * @return listener
	 */
	private TriggerGuardAdapter getGuardListener() {
		if (guardAdapter == null) {
			guardAdapter = new TriggerGuardAdapter();
		}
		return guardAdapter;
	}

	@Override
	public void dispose() {
		if (guardAdapter != null) {
			guardAdapter.dispose();
		}
		super.dispose();
	}

	/**
	 * Trigger guard listener.
	 * 
	 * @author ysroh
	 *
	 */
	private class TriggerGuardAdapter extends MultipleAdapter {

		/**
		 * Constructor.
		 *
		 */
		TriggerGuardAdapter() {
			super(1);
		}

		/**
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 *
		 * @param msg
		 */
		@Override
		public void notifyChanged(Notification notification) {
			if (!notification.isTouch()) {
				Object value = notification.getNewValue();

				if (notification instanceof StereotypeExtensionNotification) {
					// "Redo" operation does not refresh the tab without
					// following code.
					// All notifications are sent before applying stereotype.
					// We need to refresh after stereotype is applied so that
					// CodeSnippetUtils.getTriggerGuard returns actual value.
					if (value instanceof RTGuard) {
						// new RTGuard is created
						updateExpressionObservableList();
						refresh();
					}
				}
			}
		}
	}
}
