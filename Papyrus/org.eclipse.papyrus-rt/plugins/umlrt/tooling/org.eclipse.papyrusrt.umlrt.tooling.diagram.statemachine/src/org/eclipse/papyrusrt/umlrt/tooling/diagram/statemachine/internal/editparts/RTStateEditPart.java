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
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.isVisibilityForced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.CustomStateShowHideCompartmentEditPolicy;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Custom state edit-part for UML-RT state machine diagrams.
 */
public class RTStateEditPart extends CustomStateEditPart implements IStateMachineInheritableEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTStateEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTSemanticEditPolicy());
		installEditPolicy(ShowHideCompartmentEditPolicy.SHOW_HIDE_COMPARTMENT_POLICY, new CustomStateShowHideCompartmentEditPolicy());
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
	protected void refreshForegroundColor() {
		super.refreshForegroundColor();

		// We present inherited states in a lighter colour
		UMLRTEditPartUtils.updateForegroundColor(this, getPrimaryShape());
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart#refreshBackgroundColor()
	 *
	 */
	@Override
	protected void refreshBackgroundColor() {
		// customize background as being the background color if the state is empty.
		// when no compartment is displayed, then the whole state figure should use the label name color by default
		if (getPrimaryView() != null && !getVisibleChildren(getPrimaryView()).stream().filter(BasicCompartment.class::isInstance).findFirst().isPresent()) {
			String labelColor = NotationUtils.getStringValue(getPrimaryView(), NAME_BACKGROUND_COLOR, null);
			if (labelColor != null && !"-1".equals(labelColor)) {
				// Set color of the Name Label background
				setBackgroundColor(DrawFigureUtils.getColorFromString(labelColor));
			} else {
				super.refreshBackgroundColor();
			}
		} else {
			super.refreshBackgroundColor();
		}

		// We present inherited states in a lighter colour
		UMLRTEditPartUtils.updateBackgroundColor(this, getPrimaryShape());
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 *
	 */
	@Override
	protected void refreshChildren() {
		super.refreshChildren();
	}

	/**
	 * Retrieves the list of views that should be in effect displayed.
	 * 
	 * @param view
	 *            the state view to check
	 * @return the list of views, with the internal transitions added or removed depending on its visible status
	 */
	protected List<View> getVisibleChildren(View view) {
		List<View> children = new ArrayList(view.getVisibleChildren());
		// ensure the internalTransition is part of this views
		if (!children.stream().filter(e -> InternalTransitionsCompartmentEditPart.VISUAL_ID.equals(e.getType())).findFirst().isPresent()) {
			// find it from all the views and add it
			View compartmentView = (View) view.getChildren()
					.stream().map(View.class::cast)
					.filter(e -> InternalTransitionsCompartmentEditPart.VISUAL_ID.equals(((View) e).getType()))
					.findFirst().orElse(null);
			if (compartmentView != null) {
				// check its visibility
				boolean isForced = isVisibilityForced(compartmentView);
				if (isForced) {
					if (compartmentView.isVisible()) {
						children.add(compartmentView);
					}
				} else {
					EObject semantic = resolveSemanticElement();
					if (compartmentView.isVisible() && semantic instanceof State && StateUtils.hasInternalTransitions((State) semantic)) {
						children.add(compartmentView);
					}
				}
			}
		} else {
			// the compartment is there. Should it be displayed? no if not forced and no internal transitions
			View compartmentView = children.stream().filter(e -> InternalTransitionsCompartmentEditPart.VISUAL_ID.equals(e.getType())).findFirst().get();
			boolean isForced = isVisibilityForced(compartmentView);
			if (isForced) {
				if (!compartmentView.isVisible()) {
					// should not happen, as the forced value has no sense with the ForceValueHelper current impl.
					children.remove(compartmentView);
				}
			} else {
				EObject semantic = resolveSemanticElement();
				if (compartmentView.isVisible() && semantic instanceof State && !StateUtils.hasInternalTransitions((State) semantic)) {
					children.remove(compartmentView);
				}
			}
		}
		return children;
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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void removeSemanticListeners() {
		EObject semantic = resolveSemanticElement();
		if (semantic instanceof State) {
			removeStateListener((State) semantic);
		}
		super.removeSemanticListeners();

	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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
			refreshChildren();
			refreshBackgroundColor();
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
			refreshChildren();
			refreshBackgroundColor();
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
					refreshChildren();
					refreshBackgroundColor();
				}
			}
			break;
		default:
			break;
		}
		super.handleNotificationEvent(notification);
	}

	/**
	 * Overridden to install the {@link RTPortPositionLocator} as the constraint
	 * for connection point figures.
	 */
	@Override
	protected boolean addFixedChild(EditPart childEditPart) {
		boolean result;

		if (IRTPseudostateEditPart.isConnectionPoint(childEditPart)) {
			IRTPseudostateEditPart connPt = (IRTPseudostateEditPart) childEditPart;
			if (hasNotationView() && getNotationView().isSetElement()) {
				IBorderItemLocator locator = new RTPortPositionLocator(
						(Element) connPt.resolveSemanticElement(), getMainFigure(),
						PositionConstants.NONE, connPt.getDefaultScaleFactor());

				getBorderedFigure().getBorderItemContainer().add(connPt.getFigure(), locator);
			}

			result = true;
		} else {
			result = super.addFixedChild(childEditPart);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<View> getModelChildren() {
		// return also the internal transitions compartment if not in the "visible" children.
		// used for refresh
		Object model = getModel();
		if (model != null && model instanceof View) {
			return getVisibleChildren((View) model);
		}
		return Collections.EMPTY_LIST;
	}
}
