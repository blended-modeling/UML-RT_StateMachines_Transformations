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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.RedefinableElementRTOperations;

/**
 * Implementation of an inheritable {@link EcoreEList} for the implementation
 * of inheritable multi-valued attributes.
 */
public class InheritableEcoreEList<E> extends DelegatingEcoreEList<E> implements InheritableEList<E>, Adapter {

	private static final long serialVersionUID = 1L;

	private static final int SET_EFLAG = 0x1 << 0;
	private static final int HANDLING_INHERITANCE_EFLAG = 0x1 << 1;
	private static final int FEATURE_EOFFSET = 2;

	private final EList<E> content = new BasicEList<>();

	private EList<E> inherited;
	private int eFlags;

	public InheritableEcoreEList(InternalEObject owner, int featureID) {
		super(owner);

		eFlags = featureID << FEATURE_EOFFSET;
	}

	@Override
	public int getFeatureID() {
		return eFlags >> FEATURE_EOFFSET;
	}

	@Override
	public boolean isSet() {
		return (eFlags & SET_EFLAG) != 0;
	}

	@Override
	public void unset() {
		// Make sure that we appear to be unset before computing inherited value
		final int wasSet = isSet() ? SET_EFLAG : 0;
		final int unset = ~SET_EFLAG;
		eFlags = eFlags & unset;

		List<E> oldElements = new ArrayList<>(delegateList());

		EList<E> result = RedefinableElementRTOperations.inheritFeature(
				(InternalUMLRTElement) getEObject(),
				(EStructuralFeature) getFeature());

		try {
			if ((result == null) || ((wasSet != 0) && !isContainment())) {
				// Restore previous set state before clear to get a sensible notification
				eFlags = eFlags | wasSet;
				if (result == null) {
					clear();
				} else {
					ECollections.setEList(this, result);
				}
				eFlags = eFlags & unset;
			}

			if ((result != null) && (result != this)) {
				// Inherit this. If it's null, that means we are the root definition
				inherit(result, true);
			}
		} finally {
			// Now we are truly unset
			eFlags = eFlags & unset;
		}

		// Adjust stereotypes of the old elements in case they need to switch resources
		oldElements.stream()
				.filter(InternalUMLRTElement.class::isInstance)
				.map(InternalUMLRTElement.class::cast)
				.forEach(InternalUMLRTElement::rtAdjustStereotypes);
	}

	@Override
	public void touch() {
		eFlags = eFlags | SET_EFLAG;

		// Instantly disinherit
		if (inherited != null) {
			unadapt(inherited);
			inherited = null;
		}

		if (owner instanceof InternalUMLRTElement) {
			InternalUMLRTElement rtOwner = (InternalUMLRTElement) owner;
			if (rtOwner.rtIsVirtual()) {
				rtOwner.rtReify();
			}
		}

		stream().filter(InternalUMLRTElement.class::isInstance)
				.map(InternalUMLRTElement.class::cast)
				.forEach(InternalUMLRTElement::rtAdjustStereotypes);
	}

	@Override
	protected List<E> delegateList() {
		return content;
	}

	protected final void handlingInheritance(Runnable action) {
		final int restore = eFlags | ~HANDLING_INHERITANCE_EFLAG;
		eFlags = eFlags | HANDLING_INHERITANCE_EFLAG;
		try {
			if (getNotifier() instanceof InternalUMLRTElement) {
				// Whatever we do here should not reify our owner
				((InternalUMLRTElement) getNotifier()).run(action);
			} else {
				// Just run it
				action.run();
			}
		} finally {
			eFlags = eFlags & restore;
		}
	}

	@Override
	public void inherit(EList<E> newInherited) {
		inherit(newInherited, false);
	}

	protected void inherit(EList<E> newInherited, boolean isUnset) {
		if (canInherit()) {
			NotificationChain msgs = basicInherit(newInherited, isUnset, null);

			if ((msgs != null) && owner.eNotificationRequired()) {
				msgs.dispatch();
			}
		}
	}

	protected NotificationChain basicInherit(EList<E> newInherited, boolean isUnset, NotificationChain msgs) {
		NotificationChain[] result = { msgs };

		if (inherited != newInherited) {
			handlingInheritance(() -> {
				// Stop listening to the old inherited list
				if (inherited != null) {
					unadapt(inherited);
				}

				// Assign and listen to the new inherited list
				inherited = newInherited;
				if (inherited != null) {
					adapt(inherited);
				}

				// Create our new contents from the inherited list
				if ((inherited == null) || inherited.isEmpty()) {
					if (isUnset && !isEmpty()) {
						clear();
					}
				} else {
					List<E> redefinitions = redefineMany(inherited);
					result[0] = setInherited(redefinitions, result[0]);
					redefinitions.forEach(this::redefined);
				}
			});
		}

		return result[0];
	}

	protected NotificationChain setInherited(List<E> elements, NotificationChain msgs) {
		// For multi-valued attributes, it is as though the value was always
		// there but never previously read, so don't notify
		if (!isEmpty()) {
			doClear();
		}
		doAddAllUnique(elements);
		return msgs;
	}

	/**
	 * Queries whether I am in a state of being able to process inheritance
	 * from the parent definition, which is to say that either I am unset (in the
	 * EMF sense) and so not a redefinition, or the handling-inheritance flag is off
	 * that would block re-entrance of the inheritance machinery.
	 * 
	 * @return {@code true} if I am open to inheritance inheritance, or
	 *         {@code false} if I refuse inheritance for any reason
	 */
	final boolean canInherit() {
		return (eFlags & (SET_EFLAG | HANDLING_INHERITANCE_EFLAG)) == 0;
	}

	protected void disinherit() {
		if (canInherit()) {
			// not set and not handling inheritance
			NotificationChain msgs = basicInherit(null, false, null);

			touch();

			if ((msgs != null) && owner.eNotificationRequired()) {
				msgs.dispatch();
			}
		}
	}

	protected E redefineSingle(E inherited) {
		return inherited;
	}

	protected List<E> redefineMany(List<E> inherited) {
		return inherited;
	}

	protected void redefined(E element) {
		// Pass
	}

	protected void unredefined(E element) {
		// Pass
	}

	//
	// Auto-disinheriting API
	//

	@Override
	protected E delegateSet(int index, E object) {
		disinherit();
		return super.delegateSet(index, object);
	}

	@Override
	protected void delegateAdd(E object) {
		disinherit();
		super.delegateAdd(object);
	}

	@Override
	protected void delegateAdd(int index, E object) {
		boolean canInherit = canInherit();

		disinherit();

		if (canInherit && ReificationAdapter.isUndoRedoInProgress(owner)) {
			// If this is an undo/redo situation, then clear out our contents
			// because the ChangeDescription will only have recorded the
			// add(s), not any removals, on the assumption that an unset
			// list must have been empty
			delegateBasicList().clear();
		}

		super.delegateAdd(index, object);
	}

	@Override
	protected E delegateRemove(int index) {
		disinherit();
		return super.delegateRemove(index);
	}

	@Override
	protected void delegateClear() {
		disinherit();
		super.delegateClear();
	}

	@Override
	protected E delegateMove(int targetIndex, int sourceIndex) {
		disinherit();
		return super.delegateMove(targetIndex, sourceIndex);
	}

	@Override
	protected NotificationImpl createNotification(int eventType, Object oldObject, Object newObject, int index, boolean wasSet) {
		return new RTNotificationImpl(owner, eventType, getFeatureID(), oldObject, newObject, index, wasSet);
	}

	//
	// Inheritance adapter API
	//

	static EObject getOwner(EList<?> list) {
		return (list instanceof EStructuralFeature.Setting)
				? ((EStructuralFeature.Setting) list).getEObject()
				: null;
	}

	private void adapt(EList<?> inherited) {
		EObject owner = getOwner(inherited);
		if ((owner != null) && !owner.eAdapters().contains(this)) {
			owner.eAdapters().add(this);
		}
	}

	private void unadapt(EList<?> inherited) {
		EObject owner = getOwner(inherited);
		if (owner != null) {
			owner.eAdapters().remove(this);
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.isTouch() || (notification.getFeature() != getFeature())
				|| NotificationForwarder.isForwarded(notification)) {

			return;
		}

		handlingInheritance(() -> {
			switch (notification.getEventType()) {
			case Notification.ADD: {
				int position = notification.getPosition();
				// Create the redefinition of the added element
				@SuppressWarnings("unchecked")
				E added = (E) notification.getNewValue();
				E redefinition = redefineSingle(added);
				add(position, redefinition);
				redefined(redefinition);
				break;
			}
			case Notification.REMOVE: {
				int position = notification.getPosition();
				E redefinition = remove(position);
				unredefined(redefinition);
				break;
			}
			case Notification.ADD_MANY: {
				int position = notification.getPosition();
				// Create the redefinitions of the added elements
				@SuppressWarnings("unchecked")
				List<E> added = (List<E>) notification.getNewValue();
				List<E> redefinitions = redefineMany(added);
				addAll(position, redefinitions);
				redefinitions.forEach(this::redefined);
				break;
			}
			case Notification.REMOVE_MANY: {
				if (notification.getPosition() == Notification.NO_INDEX) {
					// It was a clear()
					clear();
				} else {
					// It was a removeAll(...) or retainAll(...)
					int[] positions = (int[]) notification.getNewValue();
					// get the redefinitions of the removed elements
					List<E> redefinitions = IntStream.of(positions)
							.mapToObj(this::get)
							.collect(Collectors.toList());
					removeAll(redefinitions);
					redefinitions.forEach(this::unredefined);
				}
				break;
			}
			case Notification.SET: {
				int position = notification.getPosition();
				// Create the redefinition of the added element
				@SuppressWarnings("unchecked")
				E added = (E) notification.getNewValue();
				E newRedefinition = redefineSingle(added);
				E oldRedefinition = set(position, newRedefinition);
				unredefined(oldRedefinition);
				redefined(newRedefinition);
				break;
			}
			}
		});
	}

	@Override
	public Notifier getTarget() {
		return getOwner(inherited);
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// Don't need to track the target (we have its EList)
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == this;
	}
}
