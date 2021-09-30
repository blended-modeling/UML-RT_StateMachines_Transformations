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

package org.eclipse.papyrusrt.umlrt.uml.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * An ordered collection of two elements. It does not support null values.
 */
public class Pair<E> implements List<E>, RandomAccess, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private E first;
	private E second;

	private transient int modificationCount;

	/**
	 * Initializes me.
	 */
	public Pair(E first, E second) {
		super();

		this.first = Objects.requireNonNull(first, "first"); //$NON-NLS-1$
		this.second = Objects.requireNonNull(second, "first"); //$NON-NLS-1$
	}

	public static <E, T extends E, U extends E> Pair<E> of(T first, U second) {
		return new Pair<>(first, second);
	}

	public E setFirst(E newFirst) {
		E result = first;
		first = Objects.requireNonNull(newFirst, "newFirst"); //$NON-NLS-1$
		modificationCount = modificationCount + 1;
		return result;
	}

	public E setSecond(E newSecond) {
		E result = second;
		second = Objects.requireNonNull(newSecond, "newSecond"); //$NON-NLS-1$
		modificationCount = modificationCount + 1;
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result;

		if (obj == null) {
			result = false;
		} else if (obj == this) {
			result = true;
		} else if (obj instanceof List<?>) {
			List<?> other = (List<?>) obj;
			result = (other.size() == 2) && first.equals(other.get(0)) && second.equals(other.get(1));
		} else {
			result = false;
		}

		return result;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return first.equals(o) || second.equals(o);
	}

	void checkModificationCount(int expected) {
		if (modificationCount != expected) {
			throw new ConcurrentModificationException();
		}
	}

	int getModificationCount() {
		return modificationCount;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr<>(this, modificationCount, this::checkModificationCount);
	}

	@Override
	public Object[] toArray() {
		return new Object[] { first, second };
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length >= 2) {
			a[0] = (T) first;
			a[1] = (T) second;
			if (a.length > 2) {
				a[2] = null;
			}
		} else {
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
			toArray(a);
		}

		return a;
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("add"); //$NON-NLS-1$
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// Can't short-circuit on collections of more than 2 elements
		// because they could have duplicates to the point of having
		// 2 or fewer distinct elements

		for (Object next : c) {
			if (!contains(next)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("addAll"); //$NON-NLS-1$
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("addAll"); //$NON-NLS-1$
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("removeAll"); //$NON-NLS-1$
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll"); //$NON-NLS-1$
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear"); //$NON-NLS-1$
	}

	@Override
	public E get(int index) {
		switch (index) {
		case 0:
			return first;
		case 1:
			return second;
		default:
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
	}

	@Override
	public E set(int index, E element) {
		E result;

		switch (index) {
		case 0:
			result = first;
			first = element;
			break;
		case 1:
			result = second;
			second = element;
			break;
		default:
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}

		return result;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("add"); //$NON-NLS-1$
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
	}

	@Override
	public int indexOf(Object o) {
		return first.equals(o) ? 0 : second.equals(o) ? 1 : -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return second.equals(o) ? 1 : first.equals(o) ? 0 : -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListItr<>(this, index, this::checkModificationCount, this::getModificationCount);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if ((fromIndex < 0) || (fromIndex > 2)) {
			throw new IndexOutOfBoundsException("fromIndex: " + fromIndex);
		}
		if ((toIndex < fromIndex) || (toIndex > 2)) {
			throw new IndexOutOfBoundsException("toIndex: " + toIndex);
		}

		return (fromIndex == toIndex)
				// Cannot add/remove anyways, so may as well be immutable
				? Collections.emptyList()
				: ((toIndex - fromIndex) == 2)
						// That's the whole range
						? this
						// All that's left is a singleton
						: new SingletonSublist<>(this, fromIndex, this::checkModificationCount, this::getModificationCount);
	}

	//
	// Nested types
	//

	/**
	 * A simple iterator for fixed-size lists that does not support removing.
	 */
	private static class Itr<E> implements Iterator<E> {
		private final List<E> list;
		private final int expectedModCount;
		private final IntConsumer checkModificationCount;
		private int cursor;

		Itr(List<E> list, int expectedModCount, IntConsumer checkModificationCount) {
			this.list = list;
			this.expectedModCount = expectedModCount;
			this.checkModificationCount = checkModificationCount;
		}

		@Override
		public boolean hasNext() {
			return cursor < 2;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			checkModificationCount.accept(expectedModCount);

			E result = list.get(cursor);
			cursor = cursor + 1;
			return result;
		}
	}

	/**
	 * A simple list iterator for fixed-size lists that supports setting a position but not adding nor removing.
	 */
	private static class ListItr<E> implements ListIterator<E> {
		private final List<E> list;
		private final IntConsumer checkModificationCount;
		private final IntSupplier getModificationCount;
		private int expectedModCount;
		private int cursor;
		private boolean forward;
		private E lastReturned;

		ListItr(List<E> list, int index, IntConsumer checkModificationCount, IntSupplier getModificationCount) {
			if ((index < 0) || (index > list.size())) {
				throw new IndexOutOfBoundsException(String.valueOf(index));
			}

			this.list = list;
			this.cursor = index;
			this.checkModificationCount = checkModificationCount;
			this.getModificationCount = getModificationCount;

			this.expectedModCount = getModificationCount.getAsInt();
		}

		@Override
		public boolean hasNext() {
			return cursor < list.size();
		}

		@Override
		public boolean hasPrevious() {
			return cursor > 0;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			checkModificationCount.accept(expectedModCount);

			E result = list.get(cursor);
			cursor = cursor + 1;
			lastReturned = result;
			forward = true;
			return result;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			checkModificationCount.accept(expectedModCount);

			cursor = cursor - 1;
			E result = list.get(cursor);
			lastReturned = result;
			forward = false;
			return result;
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void set(E e) {
			if (lastReturned == null) {
				throw new IllegalStateException("no next nor previous returned"); //$NON-NLS-1$
			}

			int size = list.size();
			if (cursor == 0) {
				if (forward) {
					// Cannot have gone forwards to 0
					throw new IllegalStateException("no next nor previous returned"); //$NON-NLS-1$
				} else {
					list.set(cursor, e);
				}
			} else if (cursor < size) {
				if (forward) {
					list.set(cursor - 1, e);
				} else {
					list.set(cursor, e);
				}
			} else if (cursor == size) {
				if (forward) {
					list.set(cursor - 1, e);
				} else {
					// Cannot have gone backwards to 2
					throw new IllegalStateException("no next nor previous returned"); //$NON-NLS-1$
				}
			} else {
				throw new IllegalStateException("invalid cursor: " + cursor); //$NON-NLS-1$
			}

			expectedModCount = getModificationCount.getAsInt();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException("add"); //$NON-NLS-1$
		}
	}

	/**
	 * A simple sublist of a pair, of fixed size one.
	 */
	private static class SingletonSublist<E> implements List<E> {
		private final List<E> list;
		private final IntConsumer checkModificationCount;
		private final IntSupplier getModificationCount;
		private final int index;

		SingletonSublist(List<E> list, int index, IntConsumer checkModificationCount, IntSupplier getModificationCount) {
			this.list = list;
			this.index = index;
			this.checkModificationCount = checkModificationCount;
			this.getModificationCount = getModificationCount;
		}

		@Override
		public int hashCode() {
			return get(0).hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			boolean result;

			if (obj == null) {
				result = false;
			} else if (obj == this) {
				result = true;
			} else if (obj instanceof List<?>) {
				List<?> other = (List<?>) obj;
				result = (other.size() == 1) && get(0).equals(other.get(0));
			} else {
				result = false;
			}

			return result;
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean contains(Object o) {
			return get(0).equals(o);
		}

		@Override
		public Iterator<E> iterator() {
			return new Itr<>(this, getModificationCount.getAsInt(), checkModificationCount);
		}

		@Override
		public Object[] toArray() {
			return new Object[] { get(0) };
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T[] toArray(T[] a) {
			if (a.length >= 1) {
				a[0] = (T) get(0);
				if (a.length > 1) {
					a[1] = null;
				}
			} else {
				a = (T[]) Array.newInstance(a.getClass().getComponentType(), 1);
				a[0] = (T) get(0);
			}

			return a;
		}

		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			E element = get(0);
			for (Object next : c) {
				if (!element.equals(next)) {
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			throw new UnsupportedOperationException("addAll");
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException("removeAll");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException("retainAll");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("clear");
		}

		@Override
		public E get(int index) {
			if (index != 0) {
				throw new IndexOutOfBoundsException(String.valueOf(index));
			}
			return list.get(this.index);
		}

		@Override
		public E set(int index, E element) {
			if (index != 0) {
				throw new IndexOutOfBoundsException(String.valueOf(index));
			}
			return list.set(this.index, element);
		}

		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException("add");
		}

		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public int indexOf(Object o) {
			return get(0).equals(o) ? 0 : -1;
		}

		@Override
		public int lastIndexOf(Object o) {
			return get(0).equals(o) ? 0 : -1;
		}

		@Override
		public ListIterator<E> listIterator() {
			return listIterator(0);
		}

		@Override
		public ListIterator<E> listIterator(int index) {
			return new ListItr<>(this, index, checkModificationCount, getModificationCount);
		}

		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			if ((fromIndex < 0) || (fromIndex > 1)) {
				throw new IndexOutOfBoundsException("fromIndex: " + fromIndex);
			}
			if ((toIndex < fromIndex) || (toIndex > 1)) {
				throw new IndexOutOfBoundsException("toIndex: " + toIndex);
			}

			return (fromIndex == toIndex)
					? Collections.emptyList() // Cannot add/remove anyways, so may as well be immutable
					: this; // That's the whole range
		}
	}
}
