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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import static org.eclipse.papyrusrt.umlrt.uml.internal.operations.RedefinableElementRTOperations.resolveInheritedReference;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.ArrayDelegatingEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * An adapter on a redefining element that forwards notifications
 * from the element that it redefines, for those features that
 * are redefinable and not redefined.
 */
public class NotificationForwarder extends AdapterImpl {

	private final InternalUMLRTElement owner;
	private final EReference redefinedElementReference;
	private final Collection<? extends EStructuralFeature> inheritedFeatures;

	private static final Class<?> BASIC_CHANGE_RECORDER;

	static {
		Class<?> basicChangeRecorder = null;

		try {
			basicChangeRecorder = CommonPlugin.loadClass("org.eclipse.emf.ecore.change", "org.eclipse.emf.ecore.change.util.BasicChangeRecorder");
		} catch (Exception __) {
			// Okay, no such class? Then we don't have to worry about change recorders
		}

		BASIC_CHANGE_RECORDER = basicChangeRecorder;
	}

	/**
	 * Initializes me.
	 * 
	 * @param owner
	 *            the redefining element
	 * @param redefinedElementReference
	 *            the reference feature of the {@code owner}
	 *            that has the redefined element at the first position
	 * @param inheritedFeatures
	 *            the inherited features for which notifications should be forwarded
	 */
	public NotificationForwarder(InternalUMLRTElement owner, EReference redefinedElementReference,
			Collection<? extends EStructuralFeature> inheritedFeatures) {

		this(owner, redefinedElementReference, inheritedFeatures, true);
	}

	NotificationForwarder(InternalUMLRTElement owner, EReference redefinedElementReference,
			Collection<? extends EStructuralFeature> inheritedFeatures, boolean init) {
		super();

		this.owner = owner;
		this.redefinedElementReference = redefinedElementReference;
		this.inheritedFeatures = inheritedFeatures;

		if (init) {
			initRedefinedElement();
		}
	}

	void initRedefinedElement() {
		EObject redefined = getRedefinedElement(owner);

		if (redefined != target) {
			if (target != null) {
				target.eAdapters().remove(this);
			}
			if (redefined != null) {
				redefined.eAdapters().add(this);
			}
		}
	}

	final InternalUMLRTElement owner() {
		return owner;
	}

	<T extends EObject & InternalUMLRTElement> T getRedefinedElement(T element) {
		T result = null;

		if (redefinedElementReference == null) {
			// Something like a connector end that isn't (UML-ishly) redefinable
			result = element.rtGetRedefinedElement();
		} else {
			@SuppressWarnings("unchecked")
			EList<T> redefinedElements = (EList<T>) element.eGet(redefinedElementReference);

			if (!redefinedElements.isEmpty()) {
				result = redefinedElements.get(0);
			}
		}

		return result;
	}

	final EReference redefinedElementReference() {
		return redefinedElementReference;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// This isn't the one we're interested in
		if (newTarget != owner) {
			super.setTarget(newTarget);
		}
	}

	@Override
	public void unsetTarget(Notifier oldTarget) {
		if ((oldTarget == owner) && (getTarget() != null)) {
			// I'm decomissioned. Stop listening to the redefined element
			getTarget().eAdapters().remove(this);
		}

		super.unsetTarget(oldTarget);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == NotificationForwarder.class;
	}

	@Override
	public void notifyChanged(Notification msg) {
		if (msg.isTouch()) {
			return;
		}

		handleNotification(msg);
	}

	protected void handleNotification(Notification msg) {
		if (msg.getNotifier() == owner) {
			if (msg.getFeature() == redefinedElementReference) {
				initRedefinedElement();
			}
		} else if (shouldForward()) {
			if (inheritedFeatures.contains(msg.getFeature())) {
				EStructuralFeature feature = (EStructuralFeature) msg.getFeature();

				if (!owner.rtIsSet(feature)) {
					forward(msg);
				}
			}
		}
	}

	public static void forward(InternalEObject forwarder, Notification msg) {
		if (forwarder.eNotificationRequired()) {
			EList<Adapter> adapters = forwarder.eAdapters();
			if (!adapters.isEmpty()) {
				Notification forwarded = wrap(forwarder, msg);

				Adapter[] array = (adapters instanceof ArrayDelegatingEList<?>)
						? (Adapter[]) ((ArrayDelegatingEList<Adapter>) adapters).data()
						: adapters.toArray(new Adapter[adapters.size()]);
				for (int i = 0; i < array.length; i++) {
					// Don't record forwards, except for extension features
					if (!isChangeRecorder(array[i]) || (msg.getNotifier() instanceof ExtElement)) {
						array[i].notifyChanged(forwarded);
					}
				}
			}
		}
	}

	private static boolean isChangeRecorder(Object o) {
		return (BASIC_CHANGE_RECORDER != null) && BASIC_CHANGE_RECORDER.isInstance(o);
	}

	public static Notification wrap(InternalEObject forwarder, Notification msg) {
		Notification result;

		Function<? super EObject, ? extends EObject> inheritanceResolver = getInheritanceResolver(forwarder, msg);
		if (inheritanceResolver == null) {
			result = new NotificationWrapper(forwarder, msg) {
				@Override
				public int getFeatureID(Class<?> expectedClass) {
					if (notification.getNotifier() == null) {
						// Happens for extension features when there is no extension
						EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
						return ((InternalEObject) notifier).eDerivedStructuralFeatureID(
								feature.getFeatureID(), feature.getContainerClass());
					}
					return super.getFeatureID(expectedClass);
				}
			};
		} else {
			result = new NotificationWrapper(forwarder, msg) {
				// Memoize the resolutions now because this notification may be accessed
				// at a later time (e.g., after an EMF Transaction completes)
				private final Object oldValue = resolveInheritedReference(super.getOldValue(), inheritanceResolver);
				private final Object newValue = resolveInheritedReference(super.getNewValue(), inheritanceResolver);

				@Override
				public Object getNewValue() {
					return newValue;
				}

				@Override
				public Object getOldValue() {
					return oldValue;
				}
			};
		}

		return result;
	}

	private static Function<? super EObject, ? extends EObject> getInheritanceResolver(InternalEObject forwarder, Notification msg) {
		Function<? super EObject, ? extends EObject> result = null;

		if ((forwarder instanceof InternalUMLRTElement) && (msg.getFeature() instanceof EReference)) {
			EReference reference = (EReference) msg.getFeature();
			if (!reference.isContainment() && !reference.isContainer()) {
				result = ((InternalUMLRTElement) forwarder).rtGetInheritanceResolver(reference);
			}
		}

		return result;
	}

	void forward(Notification msg) {
		forward(owner, msg);
	}

	final boolean shouldForward() {
		return owner.eNotificationRequired();
	}

	static boolean isForwarded(Notification notification) {
		return (notification instanceof NotificationWrapper)
				&& (notification.getClass().getEnclosingClass() != null)
				&& NotificationForwarder.class.isAssignableFrom(notification.getClass().getEnclosingClass());
	}

	static NotificationForwarder getInstance(InternalUMLRTElement element) {
		NotificationForwarder result = null;

		for (Adapter next : element.eAdapters()) {
			if (next.isAdapterForType(NotificationForwarder.class)) {
				NotificationForwarder forwarder = (NotificationForwarder) next;
				if (forwarder.owner() == element) {
					result = forwarder;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Removes the notification forwarder owned by a given {@code element}
	 * from it, if there is any such.
	 * 
	 * @param element
	 *            a model element
	 * 
	 * @return whether any notification forwarder actually was removed
	 */
	public static boolean unadapt(InternalUMLRTElement element) {
		return element.eAdapters().removeIf(a -> a.isAdapterForType(NotificationForwarder.class)
				&& ((NotificationForwarder) a).owner() == element);
	}

	/**
	 * Ensures that a notification forwarder is attached to a given {@code element}
	 * without any redundancy.
	 * 
	 * @param element
	 *            a model element
	 * @param forwarderSupplier
	 *            in case an owned forwarder is not already attached,
	 *            a supplier that will provide one to add
	 * 
	 * @return whether any notification forwarder actually was added
	 */
	public static boolean adapt(InternalUMLRTElement element, Supplier<? extends NotificationForwarder> forwarderSupplier) {
		boolean result = getInstance(element) == null;

		if (result) {
			element.eAdapters().add(forwarderSupplier.get());
		}

		return result;
	}

	public static void initialize(Object object) {
		if (object instanceof InternalUMLRTElement) {
			InternalUMLRTElement element = (InternalUMLRTElement) object;
			NotificationForwarder forwarder = getInstance(element);
			if (forwarder != null) {
				forwarder.initRedefinedElement();
			}
		}
	}

	//
	// Nested types
	//

	/**
	 * A specialized notification forwarder for stereotype applications that forwards
	 * notifications from the corresponding stereotype of the redefined base element.
	 */
	public static class FromStereotype extends NotificationForwarder {
		private final EReference baseElementReference;

		public FromStereotype(InternalUMLRTElement owner,
				EReference baseElementReference,
				EReference redefinedElementReference,
				Collection<? extends EStructuralFeature> inheritedFeatures) {

			super(owner, redefinedElementReference, inheritedFeatures, false);

			this.baseElementReference = baseElementReference;

			initRedefinedElement();
		}

		@Override
		<T extends EObject & InternalUMLRTElement> T getRedefinedElement(T element) {
			T result = null;

			if (element instanceof Element) {
				result = super.getRedefinedElement(element);
			} else {
				// It's a stereotype application
				Element baseElement = (Element) element.eGet(baseElementReference);
				if (baseElement instanceof InternalUMLRTElement) {
					// Get the redefined of the base element
					baseElement = (Element) getRedefinedElement((InternalUMLRTElement) baseElement);
					if (baseElement != null) {
						// And get the stereotype application
						@SuppressWarnings("unchecked")
						Class<? extends EObject> stereotype = (Class<? extends EObject>) owner().eClass().getInstanceClass();

						@SuppressWarnings("unchecked")
						T redefined = (T) UMLUtil.getStereotypeApplication(baseElement, stereotype);
						result = redefined;
					}
				}
			}

			return result;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			// Our main target is the base element's redefined element's stereotype
			if (!(newTarget instanceof Element)) {
				super.setTarget(newTarget);
			}
		}

		@Override
		protected void handleNotification(Notification msg) {
			if (msg.getFeature() == baseElementReference) {
				switch (msg.getEventType()) {
				case Notification.SET:
				case Notification.UNSET:
					// Need to track redefinition changes in my base element
					if (msg.getOldValue() != null) {
						((EObject) msg.getOldValue()).eAdapters().remove(this);
					}
					if (msg.getNewValue() != null) {
						((EObject) msg.getNewValue()).eAdapters().add(this);
					}

					// Look at the base element's redefinition
					initRedefinedElement();
					break;
				}
			} else if (msg.getFeature() == redefinedElementReference()) {
				// Base element's redefinition has changed
				initRedefinedElement();
			} else {
				super.handleNotification(msg);
			}
		}
	}
}
