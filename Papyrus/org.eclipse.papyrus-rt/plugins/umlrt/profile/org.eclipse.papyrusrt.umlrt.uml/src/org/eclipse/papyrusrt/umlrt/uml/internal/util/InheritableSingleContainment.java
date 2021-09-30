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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.RedefinableElementRTOperations;
import org.eclipse.uml2.uml.RedefinableElement;

/**
 * Partial implementation of an inheritable slot for the implementation
 * of inheritable single-valued reference features.
 */
public abstract class InheritableSingleContainment<E extends EObject> extends AdapterImpl implements InheritableSetting<E>, Adapter {

	private static final int SET_EFLAG = 0x1 << 0;
	private static final int HANDLING_INHERITANCE_EFLAG = 0x1 << 1;
	private static final int FEATURE_EOFFSET = 2;

	private E inherited;
	private int eFlags;

	public InheritableSingleContainment(int featureID) {
		super();

		eFlags = featureID << FEATURE_EOFFSET;
	}

	@Override
	public InternalUMLRTElement getEObject() {
		return (InternalUMLRTElement) getTarget();
	}

	public int getFeatureID() {
		return eFlags >> FEATURE_EOFFSET;
	}

	@Override
	public EStructuralFeature getEStructuralFeature() {
		return getEObject().eClass().getEStructuralFeature(getFeatureID());
	}

	@Override
	public E getInheritable() {
		E result = get();
		E inheritedResult = getEObject().inheritFeature(getEStructuralFeature());

		if (inheritedResult != result) {
			// Inherit this into our slot. If it's null, that means we are the root definition
			inherit(inheritedResult);

			result = get();
		}

		return result;
	}

	protected E get() {
		@SuppressWarnings("unchecked")
		E result = (E) get(false);

		return result;
	}

	@Override
	public boolean isSet() {
		return (eFlags & SET_EFLAG) != 0;
	}

	@Override
	public void unset() {
		boolean wasSet = isSet();
		E oldValue = getInheritable();

		InternalEObject owner = getEObject();

		// Need to record unset state before trying to derive inherited value
		eFlags = eFlags & ~SET_EFLAG;

		// And ignore any possible cached value
		E result = RedefinableElementRTOperations.inheritFeature(getEObject(),
				getEStructuralFeature());

		if (inherited == null) {
			// We are a root definition. Force the removal of the current
			// value by pretending that it was inherited
			inherited = oldValue;
		}

		// Inherit this. If it's null, that means we are the root definition.
		basicInherit(result, true);

		// And then unset the contained object, unless we are in undo/redo,
		// in which case this should be taken care of for us
		E newValue = get();
		if (!ReificationAdapter.isUndoRedoInProgress(owner) && (newValue instanceof InternalUMLRTElement)) {
			((InternalUMLRTElement) newValue).rtUnsetAll();
		}

		if (owner.eNotificationRequired()) {
			owner.eNotify(new RTNotificationImpl(owner, Notification.UNSET,
					getEStructuralFeature(), oldValue, newValue, wasSet));
		}
	}

	protected final void handlingInheritance(Runnable action) {
		final int restore = eFlags | ~HANDLING_INHERITANCE_EFLAG;
		eFlags = eFlags | HANDLING_INHERITANCE_EFLAG;
		try {
			EObject owner = getEObject();
			if (owner instanceof InternalUMLRTElement) {
				// Whatever we do here should not reify our owner
				((InternalUMLRTElement) owner).run(action);
			} else {
				// Just run it
				action.run();
			}
		} finally {
			eFlags = eFlags & restore;
		}
	}

	@Override
	public void inherit(E newInherited) {
		basicInherit(newInherited, false);
	}

	protected void basicInherit(E newInherited, boolean isUnset) {
		if (inherited != newInherited) {
			handlingInheritance(() -> {
				InternalUMLRTElement owner = getEObject();
				E oldInherited = inherited;

				// Stop listening to the old inherited element
				if (inherited != null) {
					unadapt(inherited);
				}

				// Can we reuse the old instance?
				final boolean canReuse = isUnset && (oldInherited != newInherited) && (oldInherited instanceof InternalUMLRTElement);

				NotificationChain msgs = null;

				E oldValue = get();
				if ((oldValue != null) && !canReuse) {
					NotificationForwarder.initialize(oldValue);

					InternalEObject internalValue = (InternalEObject) oldValue;
					InternalEObject oldOwner = internalValue.eInternalContainer();
					int oldFeatureID = internalValue.eContainerFeatureID();

					msgs = internalValue.eInverseRemove(oldOwner, oldFeatureID, null, msgs);
				}

				// Assign and listen to the new inherited element
				inherited = newInherited;
				if (inherited == null) {
					msgs = basicSet(null, msgs);
				} else {
					adapt(inherited);

					E redefinition;
					if (canReuse) {
						// Clean it and reuse it, unless we are in undo/redo,
						// in which case it will be cleaned for us
						if (!ReificationAdapter.isUndoRedoInProgress(owner)) {
							((InternalUMLRTElement) oldInherited).rtUnsetAll();
						}
						redefinition = oldInherited;
					} else {
						// Create our new element from the inherited element
						redefinition = createRedefinition(inherited);
					}

					if (redefinition != oldInherited) {
						// We didn't reuse the previous instance, so this one is added
						EReference containment = (EReference) getEStructuralFeature();
						if (containment.getEOpposite() != null) {
							((InternalEObject) redefinition).eInverseAdd(owner,
									containment.getEOpposite().getFeatureID(),
									containment.getEOpposite().getEReferenceType().getInstanceClass(),
									null);
						} else {
							((InternalEObject) redefinition).eInverseAdd(owner,
									InternalEObject.EOPPOSITE_FEATURE_BASE - getFeatureID(),
									null, null);
						}
					}

					// We will make our own notification
					basicSet(redefinition, null);

					NotificationForwarder.initialize(redefinition);

					if (owner.eNotificationRequired()) {
						NotificationImpl msg;

						if (oldValue == null) {
							// This is like resolving a proxy, so let content adapters
							// etc. discover it
							msg = new ENotificationImpl(owner,
									Notification.RESOLVE,
									getEStructuralFeature(),
									redefinition, redefinition);
						} else {
							// The inherited value was replaced by an edit
							msg = new ENotificationImpl(owner,
									Notification.SET,
									getEStructuralFeature(),
									oldValue, redefinition);
						}

						if (msgs != null) {
							msgs.add(msg);
						} else {
							msgs = msg;
						}
					}

					if (msgs != null) {
						msgs.dispatch();
					}
				}
			});
		}
	}

	@Override
	public void set(Object newValue) {
		InternalUMLRTElement owner = getEObject();

		boolean wasSet = isSet();
		E oldValue = get();

		NotificationChain msgs = null;

		disinherit();

		EReference reference = (EReference) getEStructuralFeature();
		int oppositeID = (reference.getEOpposite() != null)
				? reference.getEOpposite().getFeatureID()
				: InternalEObject.EOPPOSITE_FEATURE_BASE - getFeatureID();

		if (oldValue != null) {
			NotificationForwarder.initialize(oldValue);
			if (reference.isContainment()) {
				msgs = ((InternalEObject) oldValue).eInverseRemove(owner,
						oppositeID, null, msgs);
			}
		}
		if (newValue != null) {
			if (reference.isContainment()) {
				msgs = ((InternalEObject) newValue).eInverseAdd(owner,
						oppositeID, null, msgs);
			}
			NotificationForwarder.initialize(newValue);

			// It must nonetheless redefine the inherited value, if any
			InternalUMLRTElement redefined = owner.rtGetRedefinedElement();
			if (redefined != null) {
				Object inheritedValue = redefined.eGet(reference);

				// Are they both UML-RT-redefinable elements of compatible types?
				if ((inheritedValue instanceof RedefinableElement)
						&& (inheritedValue instanceof InternalUMLRTElement)
						&& (newValue instanceof InternalUMLRTElement)
						&& ((InternalUMLRTElement) inheritedValue).eClass().isInstance(newValue)) {

					((InternalUMLRTElement) newValue).rtRedefine((InternalUMLRTElement) inheritedValue);
				}
			}
		}

		@SuppressWarnings("unchecked")
		E newValue_ = (E) newValue;
		msgs = basicSet(newValue_, msgs);

		if (owner.eNotificationRequired()) {
			NotificationImpl setMsg = new RTNotificationImpl(owner, Notification.SET,
					getEStructuralFeature(), oldValue, newValue, wasSet);
			if (msgs == null) {
				msgs = setMsg;
			} else {
				msgs.add(setMsg);
			}
		}

		if (msgs != null) {
			msgs.dispatch();
		}
	}

	protected abstract NotificationChain basicSet(E newValue, NotificationChain msgs);

	protected void disinherit() {
		if ((eFlags & (SET_EFLAG | HANDLING_INHERITANCE_EFLAG)) == 0) {
			// not set and not handling inheritance
			basicInherit(null, false);
			eFlags = eFlags | SET_EFLAG;
		}
	}

	/**
	 * Creates a redefinition of an {@code inherited} element. Clients may
	 * extend to set it as redefining that inherited element, as appropriate
	 * to its kind.
	 * 
	 * @param inherited
	 *            an inherited element
	 * @return a new element redefining it to be stored in this list
	 */
	@SuppressWarnings("unchecked")
	protected E createRedefinition(E inherited) {
		return (E) getEObject().create(inherited.eClass());
	}

	//
	// Inheritance adapter API
	//

	private void adapt(E inherited) {
		EObject owner = inherited.eContainer();
		if ((owner != null) && !owner.eAdapters().contains(this)) {
			owner.eAdapters().add(this);
		}
	}

	private void unadapt(E inherited) {
		// There is a special case in which we pretend that our own local definition is
		// our inherited value, because we are unsetting it
		EObject owner = inherited.eContainer();
		if ((owner != null) && (owner != getEObject())) {
			owner.eAdapters().remove(this);
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.isTouch()
				|| (notification.getNotifier() == getTarget())
				|| (notification.getFeature() != getEStructuralFeature())) {
			return;
		}

		switch (notification.getEventType()) {
		case Notification.SET:
		case Notification.UNSET:
			// Create the redefinition of the new element
			@SuppressWarnings("unchecked")
			E added = (E) notification.getNewValue();
			inherit(added);

			break;
		}
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// Only track the first object we're attached to
		if ((getTarget() == null) || (newTarget == null)) {
			super.setTarget(newTarget);
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == getClass();
	}
}
