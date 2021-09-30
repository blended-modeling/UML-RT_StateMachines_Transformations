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

package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * An adapter for multiplicity elements that maintains itself on the bounds
 * values and provides call-backs for them.
 */
public class MultiplicityElementAdapter extends AdapterImpl {

	public MultiplicityElementAdapter() {
		super();
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// I only actually track the multiplicity element
		if ((newTarget == null) || getTargetType().isInstance(newTarget)) {
			super.setTarget(newTarget);

			if (newTarget instanceof MultiplicityElement) {
				handleNewTarget();
			}
		}
	}

	void handleNewTarget() {
		MultiplicityElement newTarget = getTarget();

		if (newTarget.getLowerValue() != null) {
			adapt(newTarget.getLowerValue());
		}
		if (newTarget.getUpperValue() != null) {
			adapt(newTarget.getUpperValue());
		}
	}

	Class<? extends MultiplicityElement> getTargetType() {
		return MultiplicityElement.class;
	}

	@Override
	public void unsetTarget(Notifier oldTarget) {
		if (oldTarget == getTarget()) {
			handleOldTarget();
		}

		super.unsetTarget(oldTarget);
	}

	void handleOldTarget() {
		MultiplicityElement oldTarget = getTarget();

		if (oldTarget.getLowerValue() != null) {
			unadapt(oldTarget.getLowerValue());
		}
		if (oldTarget.getUpperValue() != null) {
			unadapt(oldTarget.getUpperValue());
		}
	}

	@Override
	public MultiplicityElement getTarget() {
		return (MultiplicityElement) super.getTarget();
	}

	public void adapt(Notifier notifier) {
		if (!notifier.eAdapters().contains(this)) {
			notifier.eAdapters().add(this);
		}
	}

	public void unadapt(Notifier notifier) {
		notifier.eAdapters().remove(this);
	}

	@Override
	public void notifyChanged(Notification notification) {
		// We are only interested in changes to scalar features, and they
		// only support SET, UNSET, and RESOLVE notifications
		switch (notification.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			// Except for OpaqueExpression bodies, which are lists
			if (notification.getFeature() != UMLPackage.Literals.OPAQUE_EXPRESSION__BODY) {
				break;
			}
			// Fall through
		case Notification.SET:
		case Notification.UNSET:
			if (notification.isTouch()) {
				break;
			}
			// Fall through
		case Notification.RESOLVE:
			MultiplicityElement target = getTarget();
			Object notifier = notification.getNotifier();
			Object feature = notification.getFeature();

			if (notifier == target) {
				switch (notification.getFeatureID(MultiplicityElement.class)) {
				case UMLPackage.MULTIPLICITY_ELEMENT__LOWER_VALUE:
				case UMLPackage.MULTIPLICITY_ELEMENT__UPPER_VALUE:
					ValueSpecification oldValue = (ValueSpecification) notification.getOldValue();
					ValueSpecification newValue = (ValueSpecification) notification.getNewValue();

					adaptChange(oldValue, newValue);

					if (feature == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE) {
						handleLowerValueReplaced(oldValue, newValue, notification);
					} else {
						handleUpperValueReplaced(oldValue, newValue, notification);
					}
					break;
				default:
					handleOtherTargetNotification(notification);
					break;
				}
			} else if (!handleDetailNotification(notifier, feature, notification)) {
				// We shouldn't be adapting this object
				unadapt((Notifier) notifier);
			}

			break;
		}

	}

	final void adaptChange(Notifier oldNotifier, Notifier newNotifier) {
		if (oldNotifier != null) {
			// stop listening to this one
			unadapt(oldNotifier);
		}
		if (newNotifier != null) {
			// start listening to this one
			adapt(newNotifier);
		}
	}

	void handleOtherTargetNotification(Notification notification) {
		// Pass
	}

	boolean handleDetailNotification(Object notifier, Object feature, Notification notification) {
		boolean result = false;

		if ((notifier instanceof ValueSpecification)
				&& (getTarget() != null)
				&& (((ValueSpecification) notifier).eContainer() == getTarget())) {

			result = true;

			if (feature instanceof EAttribute) {
				ValueSpecification value = (ValueSpecification) notifier;
				if (value == getTarget().getLowerValue()) {
					handleLowerValueChanged(value, (EAttribute) feature, notification);
				} else if (value == getTarget().getUpperValue()) {
					handleUpperValueChanged(value, (EAttribute) feature, notification);
				}
			}
		}

		return result;
	}

	/**
	 * Handles replacement of the lower value specification. By default, just calls
	 * {@link #handleMultiplicityChanged(Notification)}.
	 * 
	 * @param oldLowerValue
	 *            the previous lower value specification (could be {@code null})
	 * @param newLowerValue
	 *            the new lower value specification (could be {@code null})
	 * @param notification
	 *            the notification describing the change
	 * 
	 * @see #handleMultiplicityChanged(Notification)
	 */
	protected void handleLowerValueReplaced(ValueSpecification oldLowerValue, ValueSpecification newLowerValue, Notification notification) {
		handleMultiplicityChanged(notification);
	}

	/**
	 * Handles replacement of the upper value specification. By default, just calls
	 * {@link #handleMultiplicityChanged(Notification)}.
	 * 
	 * @param oldUpperValue
	 *            the previous upper value specification (could be {@code null})
	 * @param newUpperValue
	 *            the new upper value specification (could be {@code null})
	 * @param notification
	 *            the notification describing the change
	 * 
	 * @see #handleMultiplicityChanged(Notification)
	 */
	protected void handleUpperValueReplaced(ValueSpecification oldUpperValue, ValueSpecification newUpperValue, Notification notification) {
		handleMultiplicityChanged(notification);
	}

	/**
	 * May be overridden by subclasses to handle the simple and common case of just needing
	 * to react to any change at all in the multiplicity.
	 * 
	 * @param notification
	 *            the notification describing the change
	 */
	protected void handleMultiplicityChanged(Notification notification) {
		// Pass
	}

	/**
	 * Handles a change in the lower value specification. By default, just calls
	 * {@link #handleMultiplicityChanged(Notification)}.
	 * 
	 * @param lowerValue
	 *            the lower value specification that changed (not {@code null})
	 * @param feature
	 *            the attribute of the lower value that changed (not {@code null})
	 * @param notification
	 *            the notification describing the change
	 * 
	 * @see #handleMultiplicityChanged(Notification)
	 */
	protected void handleLowerValueChanged(ValueSpecification lowerValue, EAttribute feature, Notification notification) {
		handleMultiplicityChanged(notification);
	}

	/**
	 * Handles a change in the upper value specification. By default, just calls
	 * {@link #handleMultiplicityChanged(Notification)}.
	 * 
	 * @param upperValue
	 *            the upper value specification that changed (not {@code null})
	 * @param feature
	 *            the attribute of the upper value that changed (not {@code null})
	 * @param notification
	 *            the notification describing the change
	 * 
	 * @see #handleMultiplicityChanged(Notification)
	 */
	protected void handleUpperValueChanged(ValueSpecification upperValue, EAttribute feature, Notification notification) {
		handleMultiplicityChanged(notification);
	}

	//
	// Nested types
	//

	/**
	 * A specialization of the {@link MultiplicityElementAdapter} for {@link Property}
	 * instances that provides hooks for changes in the property's {@link TypedElement#getType() type}.
	 */
	public static class ForProperty extends MultiplicityElementAdapter {
		public ForProperty() {
			super();
		}

		@Override
		Class<? extends Property> getTargetType() {
			return Property.class;
		}

		@Override
		public Property getTarget() {
			return (Property) super.getTarget();
		}

		@Override
		void handleNewTarget() {
			super.handleNewTarget();

			Property newTarget = getTarget();
			if (newTarget.getType() != null) {
				adapt(newTarget.getType());
			}
		}

		@Override
		void handleOldTarget() {
			Property oldTarget = getTarget();
			if (oldTarget.getType() != null) {
				unadapt(oldTarget.getType());
			}

			super.handleOldTarget();
		}

		@Override
		void handleOtherTargetNotification(Notification notification) {
			switch (notification.getFeatureID(Property.class)) {
			case UMLPackage.PROPERTY__TYPE:
				Type oldType = (Type) notification.getOldValue();
				Type newType = (Type) notification.getNewValue();

				adaptChange(oldType, newType);

				handleTypeReplaced(oldType, newType, notification);
				break;
			}
		}

		@Override
		boolean handleDetailNotification(Object notifier, Object feature, Notification notification) {
			boolean result = false;

			if ((notifier instanceof Type)
					&& (feature instanceof EStructuralFeature)
					&& (getTarget().getType() == notifier)) {

				result = true;

				handleTypeChanged((Type) notifier, (EStructuralFeature) feature, notification);
			}

			return result || super.handleDetailNotification(notifier, feature, notification);
		}

		/**
		 * Handles replacement of the property's type. By default, just calls
		 * {@link #handleTypeChanged(Notification)}.
		 * 
		 * @param oldType
		 *            the previous type (could be {@code null})
		 * @param newType
		 *            the new type (could be {@code null})
		 * @param notification
		 *            the notification describing the change
		 * 
		 * @see #handleTypeChanged(Notification)
		 */
		protected void handleTypeReplaced(Type oldType, Type newType, Notification notification) {
			handleTypeChanged(notification);
		}

		/**
		 * May be overridden by subclasses to handle the simple and common case of just needing
		 * to react to any change at all in the property's type. By default, forwards the
		 * {@code notification} to the {@link #handleMultiplicityChanged(Notification)} method.
		 * 
		 * @param notification
		 *            the notification describing the change
		 */
		protected void handleTypeChanged(Notification notification) {
			handleMultiplicityChanged(notification);
		}

		/**
		 * Handles a change in the property's type. By default, just calls
		 * {@link #handleTypeChanged(Notification)}.
		 * 
		 * @param type
		 *            the type that changed (not {@code null})
		 * @param feature
		 *            the feature of the type that changed (not {@code null})
		 * @param notification
		 *            the notification describing the change
		 * 
		 * @see #handleTypeChanged(Notification)
		 */
		protected void handleTypeChanged(Type type, EStructuralFeature feature, Notification notification) {
			handleTypeChanged(notification);
		}
	}
}
