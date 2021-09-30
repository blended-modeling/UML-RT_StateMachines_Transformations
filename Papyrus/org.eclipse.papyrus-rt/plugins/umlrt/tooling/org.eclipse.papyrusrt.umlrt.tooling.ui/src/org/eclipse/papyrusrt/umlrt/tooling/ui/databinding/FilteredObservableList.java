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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.list.DecoratingObservableList;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListDiff;


/**
 * A filtering decorator for observable lists.
 */
public class FilteredObservableList<E> extends DecoratingObservableList<E> implements IFilteredObservableList<E> {

	private final List<Predicate<? super E>> filters = new ArrayList<>(3);
	private Predicate<? super E> filter;

	public FilteredObservableList(IObservableList<E> wrappedList) {
		super(wrappedList, true);
	}

	@Override
	public boolean addFilter(Predicate<? super E> filter) {
		boolean result = !filters.contains(filter) && filters.add(filter);

		if (result) {
			ArrayList<E> oldView = new ArrayList<>(this);
			this.filter = filters.stream().reduce(Predicate::and).get();
			ArrayList<E> newView = new ArrayList<>(this);

			ListDiff<E> diff = Diffs.computeListDiff(oldView, newView);
			if (!diff.isEmpty()) {
				fireListChange(diff);
			}
		}

		return result;
	}

	@Override
	public boolean removeFilter(Predicate<? super E> filter) {
		boolean result = filters.remove(filter);

		if (result) {
			ArrayList<E> oldView = new ArrayList<>(this);
			filters.remove(filter);
			this.filter = filters.stream().reduce(Predicate::and).orElse(null);
			ArrayList<E> newView = new ArrayList<>(this);
			fireListChange(Diffs.computeListDiff(oldView, newView));
		}

		return result;
	}

	//
	// IObservableList API
	//

	Stream<E> filtered() {
		getterCalled();
		return getWrappedList().stream()
				.sequential()
				.filter(filter);
	}

	@SuppressWarnings("unchecked")
	IObservableList<E> getWrappedList() {
		return (IObservableList<E>) getDecorated();
	}

	@Override
	public int size() {
		if (filter == null) {
			return super.size();
		}

		return (int) filtered().count();
	}

	@Override
	public boolean contains(Object o) {
		if (filter == null) {
			return super.contains(o);
		}

		return (o != null) && filtered().anyMatch(o::equals);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (filter == null) {
			return super.containsAll(c);
		}

		return filtered().filter(c::contains).count() >= c.size();
	}

	@Override
	public int indexOf(Object o) {
		if (filter == null) {
			return super.indexOf(o);
		}

		int result = -1;
		for (ListIterator<E> iter = listIterator(); (result < 0) && iter.hasNext();) {
			if (Objects.equals(iter.next(), o)) {
				result = iter.previousIndex();
			}
		}

		return result;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (filter == null) {
			return super.lastIndexOf(o);
		}

		int result = -1;
		for (ListIterator<E> iter = listIterator(size()); (result < 0) && iter.hasPrevious();) {
			if (Objects.equals(iter.previous(), o)) {
				result = iter.nextIndex();
			}
		}

		return result;
	}

	/**
	 * Computes the offset that must be added to an {@code index} in the logical
	 * view to get the corresponding index in the backing store.
	 * 
	 * @param index
	 *            a logical index
	 * @return the relative offset of the corresponding backing-store index
	 * 
	 * @precondition the list has at least one {@code filter}
	 */
	int offset(int index) {
		int result = 0;

		for (ListIterator<E> iter = getWrappedList().listIterator(); iter.hasNext();) {
			if (iter.nextIndex() > index) {
				break;
			} else if (!filter.test(iter.next())) {
				// Skip this one in the backing store
				result = result + 1;
			}
		}

		return result;
	}

	/**
	 * Translates an {@code index} in the logical view to the
	 * the backing store.
	 * 
	 * @param index
	 *            a logical index
	 * @return the corresponding backing-store index
	 */
	int translate(int index) {
		return (filter == null) ? index : index + offset(index);
	}

	@Override
	public E get(int index) {
		return super.get(translate(index));
	}

	@Override
	public E remove(int index) {
		return super.remove(translate(index));
	}

	@Override
	public E set(int index, E element) {
		return super.set(translate(index), element);
	}

	@Override
	public void add(int index, E o) {
		super.add(translate(index), o);
	}

	@Override
	public E move(int oldIndex, int newIndex) {
		return super.move(translate(oldIndex), translate(newIndex));
	}

	@Override
	public Iterator<E> iterator() {
		if (filter == null) {
			return super.iterator();
		}

		Iterator<E> delegate = super.iterator();
		return new Iterator<E>() {
			private E preparedNext;

			@Override
			public boolean hasNext() {
				while ((preparedNext == null) && delegate.hasNext()) {
					E next = delegate.next();
					if (filter.test(next)) {
						preparedNext = next;
					}
				}

				return preparedNext != null;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				E result = preparedNext;
				preparedNext = null;
				return result;
			}

			@Override
			public void remove() {
				delegate.remove();
			}
		};
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		if (filter == null) {
			return super.listIterator(index);
		}

		ListIterator<E> delegate = super.listIterator(translate(index));
		return new ListIterator<E>() {
			private E preparedNext;
			private E preparedPrev;
			private int cursor = index;

			@Override
			public boolean hasNext() {
				while ((preparedNext == null) && delegate.hasNext()) {
					E next = delegate.next();
					if (filter.test(next)) {
						preparedNext = next;
					}
				}

				return preparedNext != null;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				E result = preparedNext;
				preparedNext = null;
				preparedPrev = null;
				cursor = cursor + 1;
				return result;
			}

			@Override
			public boolean hasPrevious() {
				while ((preparedPrev == null) && delegate.hasPrevious()) {
					E prev = delegate.previous();
					if (filter.test(prev)) {
						preparedPrev = prev;
					}
				}

				return preparedPrev != null;
			}

			@Override
			public E previous() {
				if (!hasPrevious()) {
					throw new NoSuchElementException();
				}

				E result = preparedPrev;
				preparedPrev = null;
				preparedNext = null;
				cursor = cursor - 1;
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
			public void remove() {
				delegate.remove();
			}

			@Override
			public void set(E e) {
				delegate.set(e);
			}

			@Override
			public void add(E e) {
				delegate.add(e);
			}
		};
	}

	@Override
	public int hashCode() {
		if (filter == null) {
			return super.hashCode();
		}

		return filtered().mapToInt(Objects::hashCode)
				.reduce(1, (a, b) -> 31 * a + b);
	}

	@Override
	public boolean equals(Object obj) {
		if (filter == null) {
			return super.equals(obj);
		}

		return (obj == this) ? true : filtered().collect(Collectors.toList()).equals(obj);
	}

	@Override
	public Object[] toArray() {
		if (filter == null) {
			return super.toArray();
		}

		return filtered().toArray(Object[]::new);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (filter == null) {
			return super.toArray(a);
		}

		return filtered().toArray(length -> (T[]) Array.newInstance(a.getClass().getComponentType(), length));
	}

	@Override
	public String toString() {
		if (filter == null) {
			return super.toString();
		}

		return filtered().map(Object::toString)
				.collect(joining(", ", "[", "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	//
	// Nested types
	//

	/**
	 * A specialized filtering wrapper for lists that implement the {@link IObserving}
	 * protocol.
	 */
	public static class Observing<E> extends FilteredObservableList<E> implements IObserving {
		private final IObserving observing;

		public Observing(IObservableList<E> wrappedList) {
			super(wrappedList);

			if (!(wrappedList instanceof IObserving)) {
				throw new IllegalArgumentException("not an observing list"); //$NON-NLS-1$
			}

			this.observing = (IObserving) wrappedList;
		}

		@Override
		public Object getObserved() {
			return observing.getObserved();
		}
	}
}
