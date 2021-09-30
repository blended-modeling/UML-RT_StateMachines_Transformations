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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.properties;

import java.util.Collection;

import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Data-binding properties for {@link Transition}.
 */
public class TransitionProperties {

	/**
	 * Not instantiable by clients.
	 */
	public TransitionProperties() {
		super();
	}

	/**
	 * Obtains a property for binding the triggers of a transition.
	 * 
	 * @return the triggers property
	 */
	public static IFilteredListProperty<Transition, Trigger> triggers() {
		return new UMLRTListProperty<Transition, Trigger>(Trigger.class,
				UMLPackage.Literals.TRANSITION__TRIGGER,
				UMLPackage.Literals.TRANSITION__TRIGGER) {

			@Override
			public INativePropertyListener<Transition> adaptListener(ISimplePropertyListener<Transition, ListDiff<Trigger>> listener) {
				return new NativeListener(listener) {
					@Override
					protected void addToMembers(Transition source) {
						super.addToMembers(source);

						// Owned constraints, too, for trigger guards
						source.getOwnedRules().forEach(this::addAdapter);
					}

					@Override
					protected void removeFromMembers(Transition source) {
						super.removeFromMembers(source);

						// Owned constraints, too, for trigger guards
						source.getOwnedRules().forEach(this::removeAdapter);
					}

					@Override
					protected boolean handleAdditionalFeature(Notification notification) {
						if (notification.getFeature() == UMLPackage.Literals.NAMESPACE__OWNED_RULE) {
							// Follow addition/removal of constraints
							switch (notification.getEventType()) {
							case Notification.ADD:
								addAdapter((Constraint) notification.getNewValue());
								break;
							case Notification.ADD_MANY:
								((Collection<?>) notification.getNewValue()).forEach(e -> addAdapter((Constraint) e));
								break;
							case Notification.SET:
								removeAdapter((Constraint) notification.getOldValue());
								addAdapter((Constraint) notification.getNewValue());
								break;
							case Notification.REMOVE:
								removeAdapter((Constraint) notification.getOldValue());
								break;
							case Notification.REMOVE_MANY:
								((Collection<?>) notification.getOldValue()).forEach(e -> removeAdapter((Constraint) e));
								break;
							}

							return true;
						}

						return super.handleAdditionalFeature(notification);
					}

					@Override
					protected Trigger getAffectedElement(Notification notification) {
						Trigger result = null;

						Object notifier = notification.getNotifier();

						if (notifier instanceof Trigger) {
							result = (Trigger) notification.getNotifier();
						} else if (notifier instanceof Transition) {
							// Handle addition/removal of a guard constraint for a trigger,
							// which trigger then is the affected element
							if (notification.getFeature() == UMLPackage.Literals.NAMESPACE__OWNED_RULE) {
								Constraint guard = null;

								switch (notification.getEventType()) {
								case Notification.ADD:
								case Notification.SET:
									guard = (Constraint) notification.getNewValue();
									break;
								case Notification.REMOVE:
									guard = (Constraint) notification.getOldValue();
									break;
								}

								if (guard != null) {
									result = guard.getConstrainedElements().stream()
											.filter(Trigger.class::isInstance)
											.map(Trigger.class::cast)
											.findFirst().orElse(null);
								}
							}
						} else if (notifier instanceof Constraint) {
							if (notification.getFeature() == UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT) {
								switch (notification.getEventType()) {
								case Notification.ADD:
								case Notification.SET:
									result = TypeUtils.as(notification.getNewValue(), Trigger.class);
									break;
								case Notification.REMOVE:
									result = TypeUtils.as(notification.getOldValue(), Trigger.class);
									break;
								}

								if (result != null) {
									// The trigger actually in our transition
									Trigger inherited = result;
									result = UMLRTExtensionUtil.<Trigger> getUMLRTContents(getSource(), UMLPackage.Literals.TRANSITION__TRIGGER)
											.stream()
											.filter(t -> UMLRTExtensionUtil.redefines(t, inherited))
											.findAny().orElse(result);
								}
							}
						} else {
							result = super.getAffectedElement(notification);
						}

						return result;
					}
				};
			}
		};
	}

}
