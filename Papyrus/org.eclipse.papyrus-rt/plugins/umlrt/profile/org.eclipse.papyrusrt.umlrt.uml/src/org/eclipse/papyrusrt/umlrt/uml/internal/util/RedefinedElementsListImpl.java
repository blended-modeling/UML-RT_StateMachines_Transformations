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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.AbstractEList;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.RedefinableElement;

/**
 * A specialized reference list for the elements redefined by a model element,
 * which notifies the owner when its redefinition state changes. This
 * implementation is intended to substitute for uses of the {@link EObjectResolvingEList}
 * in the UML2 implementation classes.
 */
public class RedefinedElementsListImpl<E extends RedefinableElement> extends EObjectResolvingEList<E> implements RedefinedElementsList<E> {

	private static final long serialVersionUID = 1L;

	private final Consumer<? super E> onRedefinition;

	public RedefinedElementsListImpl(Class<?> dataClass, InternalUMLRTElement owner, int featureID) {
		this(dataClass, owner, featureID, null);
	}

	public RedefinedElementsListImpl(Class<?> dataClass, InternalUMLRTElement owner, int featureID,
			Consumer<? super E> onRedefinition) {

		super(dataClass, owner, featureID);

		this.onRedefinition = onRedefinition;
	}

	@Override
	public InternalUMLRTElement getRedefinableOwner() {
		return (InternalUMLRTElement) getEObject();
	}

	@Override
	public void setRedefinedElement(E redefinedElement) {
		if (redefinedElement == null) {
			if (!isEmpty()) {
				clear();
			}
		} else if (isEmpty()) {
			add(redefinedElement);
		} else {
			set(0, redefinedElement);
		}
	}

	@Override
	protected void didAdd(int index, E newObject) {
		super.didAdd(index, newObject);

		InternalUMLRTElement owner = getRedefinableOwner();
		if ((size() == 1) && (NotificationForwarder.getInstance(owner) == null)) {
			// Now we are redefining an element, we need to forward notifications
			Collection<? extends EStructuralFeature> inherited = owner.rtInheritedFeatures();

			NotificationForwarder.adapt(owner,
					() -> new NotificationForwarder(owner,
							(EReference) getEStructuralFeature(), inherited));

			if (onRedefinition != null) {
				onRedefinition.accept(newObject);
			}
		}
	}

	@Override
	protected void didRemove(int index, E oldObject) {
		super.didRemove(index, oldObject);

		if (size() == 0) {
			// Now we are un-redefining an element, we no longer need to
			// forward notifications
			NotificationForwarder.unadapt(getRedefinableOwner());

			if (onRedefinition != null) {
				onRedefinition.accept(null);
			}
		}
	}

	@Override
	public E basicGet(int index) {
		E result = super.basicGet(index);

		if (result instanceof InternalUMLRTElement) {
			result = ((InternalUMLRTElement) result).rtGetNearestRealDefinition();
		}

		return result;
	}

	@Override
	public List<E> basicList() {
		return isEmpty()
				? ECollections.emptyEList()
				: new BasicEList.UnmodifiableEList<E>(size(),
						IntStream.range(0, size()).mapToObj(this::basicGet).toArray());
	}

	@Override
	public Iterator<E> basicIterator() {
		return new BasicIterator();
	}

	@Override
	public ListIterator<E> basicListIterator() {
		return new BasicIterator();
	}

	@Override
	public ListIterator<E> basicListIterator(int index) {
		return new BasicIterator(index);
	}

	//
	// Nested types
	//

	protected class BasicIterator extends AbstractEList<E>.NonResolvingEListIterator<E> {
		public BasicIterator() {
			super();
		}

		public BasicIterator(int index) {
			super(index);
		}

		@Override
		protected E doNext() {
			try {
				E next = basicGet(cursor);
				checkModCount();
				lastCursor = cursor++;
				return next;
			} catch (IndexOutOfBoundsException exception) {
				checkModCount();
				throw new NoSuchElementException();
			}
		}
	}

}
