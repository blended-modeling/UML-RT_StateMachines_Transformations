/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 513067
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.isVisibilityForced;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editparts.ListCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.ReuseCreationEditPolicy;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Specific edit part for Internal Transition compartment inside the state edit part itself.
 */
public class InternalTransitionsCompartmentEditPart extends ListCompartmentEditPart {

	/** Visual id of this compartment. */
	public static final String VISUAL_ID = "InternalTransitions"; // //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param view
	 *            The IResizableCompartmentView compartment view
	 */
	public InternalTransitionsCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new ReuseCreationEditPolicy());
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(getNotationView());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		Object notifier = notification.getNotifier();
		Object newValue = notification.getNewValue();
		Object oldValue = notification.getOldValue();

		switch (notification.getEventType()) {
		case Notification.ADD:
			if (notifier instanceof State) {
				if (UMLPackage.Literals.STATE__REGION == notification.getFeature()) {
					if (newValue instanceof Region) {
						addRegionListener((Region) newValue);
					}
				}
			} else if (notifier instanceof Region) {
				if (UMLPackage.Literals.REGION__TRANSITION == notification.getFeature()) {
					if (newValue instanceof Transition) {
						addTransitionListener((Transition) newValue);
					}
				}
			}
			refreshVisibility();
			break;
		case Notification.REMOVE:
			if (notifier instanceof State) {
				if (UMLPackage.Literals.STATE__REGION == notification.getFeature()) {
					if (oldValue instanceof Region) {
						removeRegionListener((Region) oldValue);
					}
				}
			} else if (notifier instanceof Region) {
				if (UMLPackage.Literals.REGION__TRANSITION == notification.getFeature()) {
					if (oldValue instanceof Transition) {
						removeTransitionListener(((Transition) oldValue));
					}
				}
			}
			refreshVisibility();
			break;
		case Notification.ADD_MANY:
			if (notifier instanceof State) {
				if (UMLPackage.Literals.STATE__REGION == notification.getFeature()) {
					((Collection<?>) newValue).forEach(
							r -> addRegionListener((Region) r));
				}
			} else if (notifier instanceof Region) {
				if (UMLPackage.Literals.REGION__TRANSITION == notification.getFeature()) {
					((Collection<?>) newValue).forEach(
							t -> addTransitionListener((Transition) t));
				}
			}
			break;
		case Notification.REMOVE_MANY:
			if (notifier instanceof State) {
				if (UMLPackage.Literals.STATE__REGION == notification.getFeature()) {
					if (UMLPackage.Literals.STATE__REGION == notification.getFeature()) {
						((Collection<?>) oldValue).forEach(
								r -> removeRegionListener((Region) r));
					}
				}
			} else if (notifier instanceof Region) {
				if (UMLPackage.Literals.REGION__TRANSITION == notification.getFeature()) {
					((Collection<?>) oldValue).forEach(
							t -> removeTransitionListener((Transition) t));
				}
			}
			break;
		case Notification.SET:
			if (notifier instanceof Transition) {
				if (UMLPackage.Literals.TRANSITION__KIND == notification.getFeature()) {
					refreshVisibility();
				}
			}
			break;
		default:
			break;
		}
		super.handleNotificationEvent(notification);

	}

	/**
	 * Update the visibility of the compartment, if there is one or more internal transition.
	 */
	@Override
	protected void refreshVisibility() {
		// if visibility is hard coded to be visible or not, do not continue
		Object model = null;
		EditPart ep = this;
		while (!(model instanceof View) && ep != null) {
			model = ep.getModel();
			ep = ep.getParent();
		}
		if (!(model instanceof View)) {
			return;
		}
		View view = (View) model;
		boolean isForced = isVisibilityForced(view);
		if (isForced) {
			setVisibility(view.isVisible());
		} else {
			// let the fact that internal transitions exist or not to toggle visibility
			EObject semantic = resolveSemanticElement();
			if (semantic instanceof State) {
				setVisibility(view.isVisible() && StateUtils.hasInternalTransitions((State) semantic));
			}
		}
	}

	/**
	 * Remove this edit part as a listener of the specified transition.
	 * 
	 * @param transition
	 *            the transition to stop listen
	 */
	protected void removeTransitionListener(Transition transition) {
		DiagramEventBroker.getInstance(this.getEditingDomain()).removeNotificationListener(transition, UMLPackage.Literals.TRANSITION__KIND, this);
	}

	/**
	 * Remove this edit part as a listener of the specified region.
	 * 
	 * @param region
	 *            the region to stop listen
	 */
	protected void removeRegionListener(Region region) {
		region.getTransitions().forEach(t -> removeTransitionListener(t));
		DiagramEventBroker.getInstance(this.getEditingDomain()).removeNotificationListener(region, UMLPackage.Literals.REGION__TRANSITION, this);
	}

	/**
	 * Adds this edit part as a listener to the specified transition.
	 * 
	 * @param transition
	 *            the transition to listen
	 */
	protected void addTransitionListener(Transition transition) {
		DiagramEventBroker.getInstance(this.getEditingDomain()).addNotificationListener(transition, UMLPackage.Literals.TRANSITION__KIND, this);
	}

	/**
	 * Adds this edit part as a listener to the region.
	 * 
	 * @param region
	 *            the region to listen
	 */
	protected void addRegionListener(Region region) {
		DiagramEventBroker.getInstance(this.getEditingDomain()).addNotificationListener(region, UMLPackage.Literals.REGION__TRANSITION, this);
		region.getTransitions().forEach(t -> addTransitionListener(t));
	}

	/**
	 * Adds this edit part as a listener to the state.
	 * 
	 * @param state
	 *            the state to listen
	 */
	protected void addStateListener(State state) {
		DiagramEventBroker.getInstance(this.getEditingDomain()).addNotificationListener(state, UMLPackage.Literals.STATE__REGION, this);
		state.getRegions().forEach(r -> addRegionListener(r));
	}

	/**
	 * Remove this edit part as a listener of the specified state.
	 * 
	 * @param state
	 *            the state to stop listen
	 */
	protected void removeStateListener(State state) {
		state.getRegions().forEach(r -> removeRegionListener(r));
		DiagramEventBroker.getInstance(this.getEditingDomain()).removeNotificationListener(state, UMLPackage.Literals.STATE__REGION, this);
	}

	@Override
	protected void removeSemanticListeners() {
		EObject semantic = resolveSemanticElement();
		if (semantic instanceof State) {
			removeStateListener((State) semantic);
		}
		super.removeSemanticListeners();

	}

	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();

		// add listeners to state for region addition. If the first one is added => add a listener to them for internal transition addition / removal)
		// state is already added by super
		EObject semantic = resolveSemanticElement();
		if (semantic instanceof State) {
			addStateListener((State) semantic);
		}
	}


}
