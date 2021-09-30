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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;

/**
 * Partial implementation of an inheritable {@link EcoreEList} for the implementation
 * of inheritable multi-valued reference features. It is required of subclasses to
 * implement only the {@link #createRedefinition(EObject)} method to create a redefinition
 * of an inherited element in the list.
 * 
 * @see #createRedefinition(EObject)
 */
public abstract class InheritableEObjectEList<E extends EObject> extends InheritableEcoreEList<E> implements InheritableEList<E>, Adapter {

	private static final long serialVersionUID = 1L;

	public InheritableEObjectEList(InternalEObject owner, int featureID) {
		super(owner, featureID);
	}

	/**
	 * Implemented by subclasses to create a redefinition of an
	 * {@code inherited} element and set it as redefining that
	 * inherited element, as appropriate to its kind.
	 * 
	 * @param inherited
	 *            an inherited element
	 * @return a new element redefining it to be stored in this list
	 */
	protected abstract E createRedefinition(E inherited);

	@Override
	protected NotificationChain setInherited(List<E> elements, NotificationChain msgs) {
		if (!isEmpty()) {
			clear();
		}
		doAddAllUnique(elements);

		if (hasNavigableInverse() || isContainment()) {
			int inverseID = hasNavigableInverse()
					? getInverseFeatureID()
					: InternalEObject.EOPPOSITE_FEATURE_BASE - getFeatureID();

			for (E next : elements) {
				msgs = ((InternalEObject) next).eInverseAdd(owner,
						inverseID, null, msgs);
			}
		}

		if (isNotificationRequired()) {
			// This is like resolving a bunch of proxies, so let content adapters
			// etc. discover them
			for (int i = 0; i < elements.size(); i++) {
				E next = elements.get(i);
				NotificationImpl notif = new ENotificationImpl(owner,
						Notification.RESOLVE,
						getEStructuralFeature(),
						next, next, i);
				if (msgs == null) {
					msgs = notif;
				} else {
					msgs.add(notif);
				}
			}
		}

		return msgs;
	}

	@Override
	protected E redefineSingle(E inherited) {
		return createRedefinition(inherited);
	}

	@Override
	protected List<E> redefineMany(List<E> inherited) {
		int count = inherited.size();
		List<E> result = new ArrayList<>(count);
		List<E> existing = delegateList();

		int i;
		for (i = 0; i < count; i++) {
			if (i >= existing.size()) {
				break;
			} else {
				// Reuse this one
				E next = existing.get(i);
				result.add(next);

				// If the list is a containment, then also unset it to ensure
				// that it inherits all of its features (re-inherit is
				// recursive because an inherited element cannot have any
				// non-inherited details, otherwise it would be redefined)
				if (isContainment() && (next instanceof InternalUMLRTElement)) {
					// Make it fresh
					((InternalUMLRTElement) next).rtUnsetAll();
				}
			}
		}

		// Fill out the rest with new elements
		for (; i < count; i++) {
			result.add(createRedefinition(inherited.get(i)));
		}

		return result;
	}

	@Override
	protected void redefined(E element) {
		NotificationForwarder.initialize(element);
	}

	@Override
	protected void unredefined(E element) {
		NotificationForwarder.initialize(element);
	}

	//
	// Nested types
	//

	/**
	 * A further specialization of the inheritable {@link EcoreEList} that implements
	 * containment references.
	 */
	public static abstract class Containment<E extends EObject> extends InheritableEObjectEList<E> {
		private static final long serialVersionUID = 1L;

		public Containment(InternalEObject owner, int featureID) {
			super(owner, featureID);
		}

		@Override
		protected boolean hasInverse() {
			return true;
		}

		@Override
		protected boolean hasNavigableInverse() {
			return false;
		}

		@Override
		protected boolean isContainment() {
			return true;
		}
	}

	/**
	 * A further specialization of the inheritable {@link EcoreEList} that implements
	 * inheritance-resolving cross-references.
	 */
	public static abstract class Resolving<E extends EObject> extends InheritableEObjectEList<E> {
		private static final long serialVersionUID = 1L;

		public Resolving(InternalEObject owner, int featureID) {
			super(owner, featureID);
		}

		/**
		 * Resolves the definition of an {@code element} from the persisted reference to the
		 * correct dynamic local definition in the inheriting context.
		 * 
		 * @param element
		 *            an element from the persistent storage of the reference
		 * @return the resolved run-time definition of the {@code element}, which may just
		 *         be the same again in many cases (such as when the owner is in the
		 *         same root context)
		 */
		protected abstract E resolveInheritance(E element);

		/**
		 * Unresolves a reference to a potentially inherited or redefined {@code element}
		 * for persistence. This usually should be the root definition of the {@code element}.
		 * 
		 * @param element
		 *            an element to unresolve
		 * @return the unresolved, persistable, reference
		 */
		protected abstract E unresolveInheritance(E element);

		@Override
		protected E createRedefinition(E inherited) {
			// This isn't a containment list: we resolve to the (re-)definintion
			// that is effective in our inheriting context
			return resolveInheritance(inherited);
		}

		//
		// Inheritance-resolving access
		//

		@Override
		public void addUnique(E object) {
			super.addUnique(unresolveInheritance(object));
		}

		@Override
		public void addUnique(int index, E object) {
			super.addUnique(index, unresolveInheritance(object));
		}

		@Override
		public boolean addAllUnique(Collection<? extends E> collection) {
			return super.addAllUnique(unresolveInheritance(collection));
		}

		protected final <T> Collection<T> unresolveInheritance(Collection<T> collection) {
			return collection.stream().map(this::unresolveInheritance).collect(Collectors.toList());
		}

		@Override
		public boolean addAllUnique(int index, Collection<? extends E> collection) {
			return super.addAllUnique(index, unresolveInheritance(collection));
		}

		@Override
		public boolean addAllUnique(Object[] objects, int start, int end) {
			return super.addAllUnique(unresolveInheritance(objects, start, end));
		}

		@SuppressWarnings("unchecked")
		protected final Collection<E> unresolveInheritance(Object[] objects, int start, int end) {
			Collection<E> result = new ArrayList<>(end - start);
			for (int i = start; i < end; i++) {
				result.add(unresolveInheritance((E) objects[i]));
			}
			return result;
		}

		@Override
		public boolean addAllUnique(int index, Object[] objects, int start, int end) {
			return super.addAllUnique(index, unresolveInheritance(objects, start, end));
		}

		@Override
		public E setUnique(int index, E object) {
			return super.setUnique(index, unresolveInheritance(object));
		}

		@Override
		public E get(int index) {
			return resolveInheritance(super.get(index));
		}

		@Override
		public int indexOf(Object object) {
			return super.indexOf(unresolveInheritance(object));
		}

		@SuppressWarnings("unchecked")
		protected final <T> T unresolveInheritance(T object) {
			return isInstance(object) ? (T) unresolveInheritance((E) object) : object;
		}

		@Override
		public boolean contains(Object object) {
			return super.contains(unresolveInheritance(object));
		}

		@Override
		public boolean containsAll(Collection<?> collection) {
			return super.containsAll(unresolveInheritance(collection));
		}

		@Override
		protected boolean useEquals() {
			return false;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this) {
				return true;
			}

			if (!(object instanceof List<?>)) {
				return false;
			}

			List<?> list = (List<?>) object;
			int size = size();
			if (list.size() != size) {
				return false;
			}

			Iterator<?> other = list.iterator();
			for (int i = 0; i < size; i++) {
				if (get(i) != other.next()) {
					return false;
				}
			}

			return true;
		}

		@Override
		public int hashCode() {
			return Objects.hash(toArray());
		}

		@Override
		public Object[] toArray() {
			int size = size();
			Object[] result = new Object[size];
			for (int i = 0; i < size; i++) {
				result[i] = get(i);
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T[] toArray(T[] array) {
			int size = size();
			T[] result = (array.length >= size) ? array : (T[]) Array.newInstance(array.getClass().getComponentType(), size);
			for (int i = 0; i < size; i++) {
				result[i] = (T) get(i);
			}

			if (result.length > size) {
				// Null terminator
				result[size] = null;
			}

			return result;
		}

		@Override
		public String toString() {
			return stream().map(String::valueOf).collect(Collectors.joining(", ", "[", "]"));//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		}
	}
}
