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

package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLFactoryImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A façade for UML {@link Element}s that represent UML-RT concepts,
 * providing a view of the element more in keeping with the UML-RT semantics.
 */
public abstract class FacadeObjectImpl extends MinimalEObjectImpl.Container implements InternalFacadeObject {

	private static final ReferenceKind FACADE_REFERENCE_KIND_DEFAULT = ReferenceKind.SOFT;

	private static final ReferenceKind FACADE_REFERENCE_KIND;

	static {
		String config = System.getProperty("papyrusrt.facadeReferenceKind", FACADE_REFERENCE_KIND_DEFAULT.name());
		ReferenceKind referenceKind = FACADE_REFERENCE_KIND_DEFAULT;

		try {
			referenceKind = ReferenceKind.valueOf(config);
			if (referenceKind != FACADE_REFERENCE_KIND_DEFAULT) {
				UMLRTUMLPlugin.INSTANCE.log(String.format("Using %s references for facade.", referenceKind));
			}
		} catch (Exception e) {
			UMLRTUMLPlugin.INSTANCE.log("Unrecognized value of papyrusrt.facadeReferenceKind system property: " + config);
			UMLRTUMLPlugin.INSTANCE.log(e);
		}

		FACADE_REFERENCE_KIND = referenceKind;
	}

	private Element element;
	private EObject stereotype;

	protected FacadeObjectImpl() {
		super();
	}

	protected BasicFacadeAdapter<? extends FacadeObjectImpl> createFacadeAdapter() {
		return new BasicFacadeAdapter<>(this);
	}

	@SuppressWarnings("unchecked")
	<F extends FacadeObjectImpl> F init(Element element, EObject stereotype) {
		this.element = element;
		this.stereotype = stereotype;
		return (F) this;
	}

	static <T extends Element, U extends EObject, F extends FacadeObjectImpl> //
	F getFacade(T element, FacadeType<T, U, F> type) {
		BasicFacadeAdapter<? extends FacadeObjectImpl> adapter = null;

		if (element != null) {
			adapter = type.getAdapter(element);
			if (adapter == null) {
				adapter = type.createAdapter(element);
			}
		}

		return (adapter == null) ? null : type.getFacade(adapter);
	}

	@Override
	public Element toUML() {
		return element;
	}

	EObject toRT() {
		return stereotype;
	}

	@Override
	public boolean isExcluded() {
		return false;
	}

	public void destroy() {
		// The destroy() API tears down cross-references, so
		// don't let that auto-reify anything
		Optional.ofNullable(toUML()).ifPresent(
				uml -> UMLRTExtensionUtil.run(uml, uml::destroy));
	}

	<T extends Element, U extends EObject, R extends FacadeObject, F extends R> //
	List<R> getFacades(FacadeReference<T, U, R, F> reference) {
		return reference.getFacades(this);
	}

	<T extends Element, U extends EObject, R extends FacadeObject, F extends R> //
	List<R> getFacades(FacadeReference<T, U, R, F> reference, boolean withExclusions) {
		return reference.getFacades(this, withExclusions);
	}

	<T extends Element, U extends EObject, R extends FacadeObject, F extends R> //
	List<R> getFacades(FacadeReference<T, U, R, F> reference, boolean withExclusions, UnaryOperator<R> andThen) {
		return reference.getFacades(this, withExclusions, andThen);
	}

	<T extends Element, U extends EObject, R extends FacadeObject, F extends R> //
	List<R> getFacades(FacadeReference<T, U, R, F> reference, UnaryOperator<R> andThen) {
		return reference.getFacades(this, andThen);
	}

	<T extends NamedElement, U extends EObject, R extends FacadeObject, F extends R> //
	R create(FacadeReference<T, U, R, F> reference, String name) {
		@SuppressWarnings("unchecked")
		T result = (T) UMLRTUMLFactoryImpl.eINSTANCE.create(reference.getEReferenceType());

		if ((name != null) && (result != null)) {
			result.setName(name);
		}

		reference.getUML(toUML()).add(result);
		reference.getType().applyStereotype(result);

		return reference.getType().getFacade(result);
	}

	protected <E> EList<E> elist(EStructuralFeature feature, List<E> list) {
		return new EcoreEList.UnmodifiableEList.FastCompare<>(this, feature, list.size(), list.toArray());
	}

	@Override
	public Object facadeGetAll(EReference reference) {
		int featureID = eDerivedStructuralFeatureID(reference);
		return (featureID >= 0)
				? facadeGetAll(featureID)
				: eGet(featureID, true, true); // Re-use EMF dynamic/open access
	}

	protected abstract Object facadeGetAll(int referenceID);

	@Override
	public String toString() {
		String pattern = isExcluded() ? "%s[X]()" : "%s()"; //$NON-NLS-1$//$NON-NLS-2$
		return String.format(pattern, eClass().getName());
	}

	//
	// Nested types
	//

	enum ReferenceKind {
		HARD() {
			@Override
			<T> Supplier<T> supplierOf(T referent) {
				return () -> referent;
			}
		},
		SOFT() {
			@Override
			<T> Supplier<T> supplierOf(T referent) {
				return new SoftReference<>(referent)::get;
			}
		};

		abstract <T> Supplier<T> supplierOf(T referent);
	}

	/**
	 * An adapter that attaches the façade object to its underlying UML
	 * element (providing canonicalization of the mapping) and allows it to
	 * react to changes in the UML (for example, to invalidate cached information
	 * or to send change events).
	 */
	protected static class BasicFacadeAdapter<F extends FacadeObject> extends AdapterImpl implements FacadeAdapter, Supplier<F> {
		private final Supplier<F> facade;

		BasicFacadeAdapter(F facade) {
			super();

			this.facade = FACADE_REFERENCE_KIND.supplierOf(facade);
		}

		@Override
		public final F get() {
			// Check our status
			F result = facade.get();
			if ((result == null) && (getTarget() != null)) {
				// Our facade has been flushed
				removeAdapter(getTarget());
			}

			return result;
		}

		@Override
		public final boolean isAdapterForType(Object type) {
			return (type == FacadeAdapter.class)
					|| ((type instanceof Class<?>) && ((Class<?>) type).isInstance(get()));
		}

		protected boolean addAdapter(Notifier notifier) {
			EList<Adapter> eAdapters = notifier.eAdapters();
			return !eAdapters.contains(this) && eAdapters.add(this);
		}

		protected boolean removeAdapter(Notifier notifier) {
			return notifier.eAdapters().remove(this);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			// Only track the first target, which is the primary UML element
			if ((target == null) || (newTarget == null)) {
				super.setTarget(newTarget);
			}

			if (newTarget != null) {
				// Ensure that we get stereotype application changes
				UMLRTModel.getInstance(((EObject) newTarget).eResource());
			}
		}

		@Override
		public final void notifyChanged(Notification msg) {
			// Check our status
			F facade = get();
			if (facade == null) {
				// Our facade has been flushed
				removeAdapter((Notifier) msg.getNotifier());
				return;
			}

			if (msg.isTouch()) {
				return;
			}

			// We maintain a hard reference to the façade on the stack, here
			// to ensure that get() calls during the handling of the notification
			// will not get a null

			handleNotification(msg);
		}

		protected void handleNotification(Notification msg) {
			// Pass
		}

		@Override
		public void tickle(NamedElement element) {
			// Pass
		}

		@Override
		public void excluded(Element element) {
			// Pass
		}

		@Override
		public void reinherited(Element element) {
			// Pass
		}
	}

	protected static class Reactor<F extends FacadeObjectImpl> extends BasicFacadeAdapter<F> {

		private List<Notifier> additionalTargets;

		protected Reactor(F facade) {
			super(facade);
		}

		/**
		 * Adapt an additional {@code notifier} that should be tracked explicitly
		 * to avoid unintentionally re-loading objects or otherwise processing
		 * inheritance when unadapting later, especially during resource unload.
		 * 
		 * @param notifier
		 *            an additional notifier to adapt
		 * @see #unadaptAdditional(Notifier)
		 */
		protected final void adaptAdditional(Notifier notifier) {
			if (addAdapter(notifier) && (notifier != getTarget())) {
				if (additionalTargets == null) {
					additionalTargets = new ArrayList<>(4);
				}
				additionalTargets.add(notifier);
			}
		}

		/**
		 * Unadapts and forgets an additional {@code notifier}.
		 * 
		 * @param notifier
		 *            an additional notifier to unadapt
		 * 
		 * @see #adaptAdditional(Notifier)
		 * @see #unadaptAdditional()
		 */
		protected final void unadaptAdditional(Notifier notifier) {
			removeAdapter(notifier);

			if (additionalTargets != null) {
				if (additionalTargets.remove(notifier) && additionalTargets.isEmpty()) {
					additionalTargets = null;
				}
			}
		}

		/**
		 * Unadapts and forgets all additional notifiers.
		 * 
		 * @see #unadaptAdditional(Notifier)
		 */
		protected final void unadaptAdditional() {
			if (additionalTargets != null) {
				additionalTargets.forEach(this::removeAdapter);
				additionalTargets = null;
			}
		}

		@Override
		protected void handleNotification(Notification msg) {
			Object feature = msg.getFeature();

			if (feature instanceof EReference) {
				handleReference(msg);
			} else if (feature instanceof EAttribute) {
				handleAttribute(msg);
			}
		}

		protected void handleReference(Notification msg) {
			switch (msg.getEventType()) {
			case Notification.ADD:
				handleObjectAdded(msg, msg.getPosition(), (EObject) msg.getNewValue());
				break;
			case Notification.ADD_MANY:
				int addedAt = msg.getPosition();
				for (Object next : (List<?>) msg.getNewValue()) {
					handleObjectAdded(msg, addedAt, (EObject) next);
					if (addedAt != Notification.NO_INDEX) {
						// If it's just -1 for appended, then we don't need indices
						addedAt = addedAt + 1;
					}
				}
				break;
			case Notification.REMOVE:
				handleObjectRemoved(msg, msg.getPosition(), (EObject) msg.getOldValue());
				break;
			case Notification.REMOVE_MANY:
				if (msg.getNewValue() instanceof int[]) {
					// It was a removeAll(...)
					int[] removedAt = (int[]) msg.getNewValue();
					List<?> removed = (List<?>) msg.getOldValue();
					for (int i = 0; i < removedAt.length; i++) {
						handleObjectRemoved(msg, removedAt[i], (EObject) removed.get(i));
					}
				} else {
					// It was a clear()
					List<?> removed = (List<?>) msg.getOldValue();
					for (int i = 0; i < removed.size(); i++) {
						handleObjectRemoved(msg, i, (EObject) removed.get(i));
					}
				}
				break;
			case Notification.SET:
			case Notification.UNSET:
			case Notification.RESOLVE:
				handleObjectReplaced(msg, msg.getPosition(),
						(EObject) msg.getOldValue(), (EObject) msg.getNewValue());
				break;
			case Notification.MOVE:
				handleObjectMoved(msg, (int) msg.getOldValue(), msg.getPosition(), (EObject) msg.getNewValue());
				break;
			}
		}

		protected final boolean isUndoRedoNotification(Notification msg) {
			// If the reification adapter is unreachable, assume that we are
			// in the midst of undo/redo with an object that is detached
			ReificationAdapter adapter = ReificationAdapter.getInstance(getTarget());
			return (adapter == null) || adapter.isUndoRedoNotification(msg);
		}

		/**
		 * Discover the contents of a new object by processing a fake add-many notification
		 * on a {@code containment} reference.
		 * 
		 * @param newObject
		 *            the new object whose contents are to be discovered
		 * @param containment
		 *            the reference (with optional extension) in which to discover
		 *            new contents
		 */
		protected void discover(EObject newObject, EReference containment) {
			handleReference(new ENotificationImpl((InternalEObject) newObject,
					Notification.ADD_MANY, containment,
					null, UMLRTExtensionUtil.getUMLRTContents(newObject, containment)));
		}

		/**
		 * Un-discovers the contents of a deleted object by processing a fake remove-many notification
		 * on a {@code containment} reference.
		 * 
		 * @param oldObject
		 *            the deleted object whose contents are to be undiscovered
		 * @param containment
		 *            the reference (with optional extension) in which to undiscover
		 *            old contents
		 */
		protected void undiscover(EObject oldObject, EReference containment) {
			handleReference(new ENotificationImpl((InternalEObject) oldObject,
					Notification.REMOVE_MANY, containment,
					UMLRTExtensionUtil.getUMLRTContents(oldObject, containment), null));
		}

		protected FacadeObject getFacade(Notification msg, EObject object) {
			return (object == null) ? null : getFacade((EObject) msg.getNotifier(), (EReference) msg.getFeature(), object);
		}

		protected FacadeObject getFacade(EObject umlOwner, EReference umlReference, EObject object) {
			FacadeObject result = (object == null) ? null : UMLRTFactory.create(object);

			if ((result == null) && (object != null) && (object.eResource() == null)) {
				// If the object was destroyed, it may have lost its adapter,
				// so we need to scan
				List<? extends FacadeObject> search = getFacadeList(umlOwner, umlReference, object);
				if (search != null) {
					for (FacadeObject next : search) {
						if (next.toUML() == object) {
							result = next;
							break;
						}
					}
				}
			}

			return result;
		}

		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			return null;
		}

		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			FacadeObject facade = getFacade(msg, object);
			if (facade != null) {
				handleObjectAdded(msg, position, facade);
			}
		}

		protected void handleObjectRemoved(Notification msg, int position, EObject object) {
			FacadeObject facade = getFacade(msg, object);
			if (facade != null) {
				handleObjectRemoved(msg, position, facade);
			}
		}

		protected void handleObjectReplaced(Notification msg, int position, EObject oldObject, EObject newObject) {
			FacadeObject oldFacade = getFacade(msg, oldObject);
			FacadeObject newFacade = getFacade(msg, newObject);

			if (((oldFacade != null) || (oldObject == null))
					&& ((newFacade != null) || (newObject == null))) {
				// Be careful to to misinterpret the case where there was an old/new
				// object that does not correspond to a façade object
				handleObjectReplaced(msg, position, oldFacade, newFacade);
			} else {
				handleObjectRemoved(msg, position, oldObject);
				handleObjectAdded(msg, position, newObject);
			}
		}

		protected void handleObjectMoved(Notification msg, int oldPosition, int newPosition, EObject object) {
			FacadeObject facade = getFacade(msg, object);
			if (facade != null) {
				handleObjectMoved(msg, oldPosition, newPosition, facade);
			}
		}

		protected void handleObjectAdded(Notification msg, int position, FacadeObject object) {
			@SuppressWarnings("unchecked")
			InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(msg, object);

			// The list will be null if validation failed
			if (list != null) {
				list.facadeAdd(object);
			}
		}

		protected InternalFacadeEList<? extends FacadeObject> validateObject(Notification msg, FacadeObject object) {
			return validateObject((EObject) msg.getNotifier(), (EReference) msg.getFeature(), object);
		}

		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			return null;
		}

		protected void handleObjectRemoved(Notification msg, int position, FacadeObject object) {
			@SuppressWarnings("unchecked")
			InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(msg, object);

			// The list will be null if validation failed
			if (list != null) {
				list.facadeRemove(object);
			}
		}

		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (oldObject == null) {
				handleObjectAdded(msg, position, newObject);
			} else if (newObject == null) {
				handleObjectRemoved(msg, position, oldObject);
			} else {
				@SuppressWarnings("unchecked")
				InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(msg, newObject);

				// The list will be null if validation failed
				if (list != null) {
					int index = list.indexOf(oldObject);
					if (index >= 0) {
						list.facadeSet(index, newObject);
					} else {
						handleObjectRemoved(msg, position, oldObject);
						handleObjectAdded(msg, position, newObject);
					}
				} else {
					handleObjectRemoved(msg, position, oldObject);
					handleObjectAdded(msg, position, newObject);
				}
			}
		}

		protected void handleObjectMoved(Notification msg, int oldPosition, int newPosition, FacadeObject object) {
			@SuppressWarnings("unchecked")
			InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(msg, object);

			// The list will be null if validation failed
			if ((list != null) && list.contains(object)) {
				matchMove(msg, oldPosition, newPosition, object, list);
			}
		}

		/**
		 * Moves an {@code object} to the position in my {@code list} that corresponds to
		 * the {@code newPosition} in the UML element's list to which it was moved.
		 * 
		 * @param umlOwner
		 *            the UML model element to which the {@code object} was added
		 * @param umlReference
		 *            the reference of the UML model element in which list the {@code object} was moved
		 * @param oldPosition
		 *            the position in the UML list from which the {@code object} was moved
		 * @param newPosition
		 *            the position in the UML list to which the {@code object} was moved
		 * @param object
		 *            the façade for the UML element that was moved
		 * @param list
		 *            the façade list in which to make the matching move
		 */
		protected <E extends FacadeObject> void matchMove(EObject umlOwner, EReference umlReference, int oldPosition, int newPosition, E object, InternalFacadeEList<E> list) {
			// Find the index of the first object behind the new location of the moved object
			// where that other object is in our list. This is the insertion point in our
			// list of the moved object
			EList<? extends EObject> umlList = getUMLList(umlOwner, umlReference);

			int insertion = list.size() - 1; // If we don't find an insertion point, it is the end
			int offset = (newPosition < oldPosition)
					? 0 // Going up
					: -1; // Going down

			for (int i = newPosition + 1; i < umlList.size(); i++) {
				FacadeObject facade = getFacade(umlOwner, umlReference, umlList.get(i));
				int index = (facade == null) ? -1 : list.indexOf(facade);
				if (index >= 0) {
					insertion = index + offset;
					break;
				}
			}

			if (insertion >= 0) {
				list.facadeMove(insertion, object);
			}
		}

		protected <E extends FacadeObject> void matchMove(Notification msg, int oldPosition, int newPosition, E object, InternalFacadeEList<E> list) {
			matchMove((EObject) msg.getNotifier(), (EReference) msg.getFeature(), oldPosition, newPosition, object, list);
		}

		/**
		 * Adds an {@code object} to the position in my {@code list} that corresponds to
		 * the {@code position} in the UML element's list in which it sits.
		 * 
		 * @param umlOwner
		 *            the UML model element to which the {@code object} was added
		 * @param umlReference
		 *            the reference of the UML model element in which list the {@code object} was added
		 * @param position
		 *            the position in the UML list at which the {@code object} is
		 * @param object
		 *            the façade for the UML element that to add
		 * @param list
		 *            the façade list in which to make the matching add
		 */
		protected <E extends FacadeObject> void matchAdd(EObject umlOwner, EReference umlReference, int position, E object, InternalFacadeEList<E> list) {
			// Find the index of the first object behind the new location of the moved object
			// where that other object is in our list. This is the insertion point in our
			// list of the moved object
			EList<? extends EObject> umlList = getUMLList(umlOwner, umlReference);

			int insertion = list.size(); // If we don't find an insertion point, it is the end

			for (int i = umlList.size() - 1; i >= position; i--) {
				FacadeObject facade = getFacade(umlOwner, umlReference, umlList.get(i));
				int index = (facade == null) ? -1 : list.indexOf(facade);
				if (index >= 0) {
					insertion = index;
					break;
				}
			}

			list.facadeAdd(insertion, object);
		}

		@SuppressWarnings("unchecked")
		protected EList<? extends EObject> getUMLList(EObject umlOwner, EReference umlReference) {
			return (EList<? extends EObject>) umlOwner.eGet(umlReference);
		}

		protected void handleAttribute(Notification msg) {
			switch (msg.getEventType()) {
			case Notification.ADD:
				handleValueAdded(msg, msg.getPosition(), msg.getNewValue());
				break;
			case Notification.ADD_MANY:
				int addedAt = msg.getPosition();
				for (Object next : (List<?>) msg.getNewValue()) {
					handleValueAdded(msg, addedAt, next);
					if (addedAt >= 0) {
						// If it's just -1 for appended, then we don't need indices
						addedAt = addedAt + 1;
					}
				}
				break;
			case Notification.REMOVE:
				handleValueRemoved(msg, msg.getPosition(), msg.getOldValue());
				break;
			case Notification.REMOVE_MANY:
				// Iterate backwards to maintain validity of positions
				if (msg.getNewValue() instanceof int[]) {
					// It was a removeAll(...)
					int[] removedAt = (int[]) msg.getNewValue();
					List<?> removed = (List<?>) msg.getOldValue();
					for (int i = removedAt.length - 1; i >= 0; i++) {
						handleValueRemoved(msg, removedAt[i], removed.get(i));
					}
				} else {
					// It was a clear()
					List<?> removed = (List<?>) msg.getOldValue();
					for (int i = removed.size() - 1; i >= 0; i++) {
						handleValueRemoved(msg, i, removed.get(i));
					}
				}
				break;
			case Notification.SET:
			case Notification.UNSET:
			case Notification.RESOLVE:
				handleValueReplaced(msg, msg.getPosition(),
						msg.getOldValue(), msg.getNewValue());
				break;
			case Notification.MOVE:
				handleValueMoved(msg, (int) msg.getOldValue(), msg.getPosition(), msg.getNewValue());
				break;
			}
		}

		protected void handleValueAdded(Notification msg, int position, Object value) {
			// Pass
		}

		protected void handleValueRemoved(Notification msg, int position, Object value) {
			// Pass
		}

		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			handleValueAdded(msg, position, oldValue);
			handleValueRemoved(msg, position, newValue);
		}

		protected void handleValueMoved(Notification msg, int oldPosition, int newPosition, Object value) {
			// Pass
		}

		@Override
		public void tickle(NamedElement element) {
			EObject owner = element.eContainer();
			EReference reference = element.eContainmentFeature();

			FacadeObject facade = getFacade(owner, reference, element);
			if (facade != null) {
				@SuppressWarnings("unchecked")
				InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(
						owner, reference, facade);

				if (list != null) {
					boolean remove = facade.isExcluded();
					if (!remove && !list.contains(facade)) {
						// Add it in its place
						EList<?> umlList = (EList<?>) owner.eGet(reference);
						matchAdd(owner, reference, umlList.indexOf(element), facade, list);
					} else if (remove && list.contains(facade)) {
						// Remove the facade, which does not belong
						list.facadeRemove(facade);
					}
				}
			}
		}

		@Override
		public void excluded(Element element) {
			InternalEObject internal = (InternalEObject) element;
			EObject container = internal.eInternalContainer();
			EReference containment = internal.eContainmentFeature();

			FacadeObject facade = getFacade(container, containment, element);
			if (facade != null) {
				handleObjectExcluded(container, containment, facade);
			}
		}

		protected void handleObjectExcluded(EObject umlOwner, EReference umlReference, FacadeObject object) {
			@SuppressWarnings("unchecked")
			InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(
					umlOwner, umlReference, object);

			if (list != null) {
				list.facadeRemove(object);
			}
		}

		@Override
		public void reinherited(Element element) {
			InternalEObject internal = (InternalEObject) element;
			EObject container = internal.eInternalContainer();
			EReference containment = internal.eContainmentFeature();

			FacadeObject facade = getFacade(container, containment, element);
			if (facade != null) {
				handleObjectReinherited(container, containment, facade);
			}
		}

		protected void handleObjectReinherited(EObject umlOwner, EReference umlReference, FacadeObject object) {
			@SuppressWarnings("unchecked")
			InternalFacadeEList<FacadeObject> list = (InternalFacadeEList<FacadeObject>) validateObject(
					umlOwner, umlReference, object);

			if ((list != null) && !list.contains(object)) {
				list.facadeAdd(object);
			}
		}
	}

}
