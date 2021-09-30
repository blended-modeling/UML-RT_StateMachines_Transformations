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

package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;

/**
 * A list that is outwardly (by clients) unmodifiable, but which the façade
 * internally maintains.
 */
public class FacadeObjectEList<E> extends EObjectEList<E> implements InternalFacadeEList<E> {

	private static final long serialVersionUID = 1L;

	public FacadeObjectEList(FacadeObject owner, Class<?> dataClass, int featureID) {
		super(dataClass, (InternalEObject) owner, featureID);
	}

	public FacadeObjectEList(FacadeObject owner, Class<?> dataClass, int featureID,
			Collection<? extends E> initialContents) {

		this(owner, dataClass, featureID);

		doAddAllUnique(initialContents);
	}

	@Override
	public boolean facadeAdd(E object) {
		if (isUnique() && contains(object)) {
			return false;
		} else {
			super.addUnique(object);
			return true;
		}
	}

	@Override
	public void facadeAdd(int index, E object) {
		super.addUnique(index, object);
	}

	@Override
	public E facadeSet(int index, E object) {
		int size = size();
		if (index >= size) {
			throw new BasicIndexOutOfBoundsException(index, size);
		}

		if (isUnique()) {
			int currentIndex = indexOf(object);
			if ((currentIndex >= 0) && (currentIndex != index)) {
				throw new IllegalArgumentException("The 'no duplicates' constraint is violated"); //$NON-NLS-1$
			}
		}

		return super.setUnique(index, object);
	}

	@Override
	public boolean facadeRemove(Object object) {
		int index = indexOf(object);
		if (index >= 0) {
			super.remove(index);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void facadeMove(int index, E object) {
		super.move(index, indexOf(object));
	}

	@Override
	public void set(Object newValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addUnique(E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addUnique(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAllUnique(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAllUnique(int index, Object[] objects, int start, int end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NotificationChain basicAdd(E object, NotificationChain notifications) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NotificationChain basicRemove(Object object, NotificationChain notifications) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E setUnique(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NotificationChain basicSet(int index, E object, NotificationChain notifications) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E move(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void move(int index, E object) {
		throw new UnsupportedOperationException();
	}

}
