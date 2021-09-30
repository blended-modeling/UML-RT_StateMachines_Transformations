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
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;

/**
 * A map that is outwardly (by clients) unmodifiable, but which the façade
 * internally maintains.
 */
public class FacadeEMap<K, V> extends EcoreEMap<K, V> implements InternalFacadeEMap<K, V> {

	private static final long serialVersionUID = 1L;

	public FacadeEMap(FacadeObject owner, EClass entryEClass, Class<?> entryClass, int featureID) {
		super(entryEClass, entryClass, (InternalEObject) owner, featureID);
	}

	@Override
	public V facadePut(K key, V value) {
		// Don't produce touch notifications
		V existing = get(key);
		if (existing == null) {
			if (value == null) {
				return existing;
			}
		} else if (value != null) {
			if (useEqualsForValue() ? existing.equals(value) : (existing == value)) {
				return existing;
			}
		}

		return super.put(key, value);
	}

	@Override
	public V facadeRemoveKey(Object key) {
		V result = null;

		ensureEntryDataExists();

		int hash = hashOf(key);
		int index = indexOf(hash);

		Entry<K, V> entry = entryForKey(index, hash, key);
		if (entry != null) {
			facadeRemove(entry);
			result = entry.getValue();
		}

		return result;
	}

	@Override
	protected void ensureEntryDataExists() {
		UMLRTExtensionUtil.run((FacadeObject) getEObject(), super::ensureEntryDataExists);
	}

	@Override
	public boolean facadeAdd(Map.Entry<K, V> entry) {
		return super.add(entry);
	}

	@Override
	public void facadeAdd(int index, Map.Entry<K, V> entry) {
		super.add(index, entry);
	}

	@Override
	public Map.Entry<K, V> facadeSet(int index, Map.Entry<K, V> entry) {
		return super.set(index, entry);
	}

	@Override
	public boolean facadeRemove(Object object) {
		return super.remove(object);
	}

	@Override
	public void facadeMove(int index, Map.Entry<K, V> entry) {
		super.move(index, entry);
	}

	@Override
	public void set(Object newValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addUnique(Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addUnique(int index, Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAllUnique(int index, Collection<? extends Map.Entry<K, V>> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NotificationChain basicAdd(Map.Entry<K, V> entry, NotificationChain notifications) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map.Entry<K, V> remove(int index) {
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
	public Map.Entry<K, V> setUnique(int index, Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map.Entry<K, V> move(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map.Entry<K, V> set(int index, Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Map.Entry<K, V>> collection) {
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
	public void move(int index, Map.Entry<K, V> entry) {
		throw new UnsupportedOperationException();
	}

}
