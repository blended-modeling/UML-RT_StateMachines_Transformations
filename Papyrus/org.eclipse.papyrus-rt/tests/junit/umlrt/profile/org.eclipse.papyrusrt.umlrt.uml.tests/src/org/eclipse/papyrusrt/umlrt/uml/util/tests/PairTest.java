/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.util.tests;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.papyrusrt.umlrt.uml.util.Pair;
import org.junit.Test;

/**
 * Test suite for the {@link Pair} API.
 */
public class PairTest {

	@Test(expected = NullPointerException.class)
	public void constructor() {
		new Pair<>("Hello", null);
	}

	@Test(expected = NullPointerException.class)
	public void setFirst() {
		Pair<String> pair = new Pair<>("Hello", "world");

		pair.setFirst("Good-bye");
		assertThat(pair, is(Arrays.asList("Good-bye", "world")));

		pair.setFirst(null);
	}

	@Test(expected = NullPointerException.class)
	public void setSecond() {
		Pair<String> pair = new Pair<>("Hello", "world");

		pair.setSecond("Earthlings");
		assertThat(pair, is(Arrays.asList("Hello", "Earthlings")));

		pair.setSecond(null);
	}

	@Test
	public void size() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.size(), is(2));
	}

	@Test
	public void isEmpty() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.isEmpty(), is(false));
	}

	@Test
	public void contains() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.contains("Hello"), is(true));
		assertThat(pair.contains("world"), is(true));
		assertThat(pair.contains("Earthlings"), is(false));
		assertThat(pair.contains(null), is(false));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void concurrentModification() {
		Pair<String> pair = Pair.of("Hello", "world");

		Iterator<String> iter = pair.iterator();
		iter.next();
		pair.setFirst("Good-bye");
		iter.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void iterator() {
		Pair<String> pair = Pair.of("Hello", "world");

		Iterator<String> iter = pair.iterator();
		assertThat(iter.hasNext(), is(true));
		assertThat(iter.next(), is("Hello"));
		assertThat(iter.hasNext(), is(true));
		assertThat(iter.next(), is("world"));
		assertThat(iter.hasNext(), is(false));

		iter.next();
	}

	@Test
	public void toArray() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.toArray(), is(new Object[] { "Hello", "world" }));
	}

	@Test
	public void toArrayOfT() {
		Pair<String> pair = Pair.of("Hello", "world");

		String[] tooShort = new String[0];
		String[] array = pair.toArray(tooShort);
		assertThat(array, not(sameInstance(tooShort)));
		assertThat(array.length, is(2));
		assertThat(Arrays.asList(array), is(Arrays.asList("Hello", "world")));

		String[] moreThanLongEnough = new String[] { "a", "b", "c", "d" };
		array = pair.toArray(moreThanLongEnough);
		assertThat(array, sameInstance(moreThanLongEnough));
		assertThat(Arrays.asList(array), is(Arrays.asList("Hello", "world", null, "d")));

		String[] justLongEnough = new String[] { "a", "b" };
		array = pair.toArray(justLongEnough);
		assertThat(array, sameInstance(justLongEnough));
		assertThat(Arrays.asList(array), is(Arrays.asList("Hello", "world")));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void add() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.add("!");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void remove() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.remove("world");
	}

	@Test
	public void containsAll() {
		Pair<String> pair = Pair.of("Hello", "world");

		assertThat(pair.containsAll(singleton("Hello")), is(true));
		assertThat(pair.containsAll(emptySet()), is(true));

		assertThat(pair.containsAll(Arrays.asList("Hello", "world", "!")), is(false));
		assertThat(pair.containsAll(Arrays.asList("Hello", "world", "Hello")), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void addAll() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.addAll(singleton("!"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void addAllAtIndex() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.addAll(1, singleton("there"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeAll() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.removeAll(Arrays.asList("Hello", "world"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void retainAll() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.retainAll(Arrays.asList("Hello", "people"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void clear() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.clear();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void get() {
		Pair<String> pair = Pair.of("Hello", "world");

		assertThat(pair.get(0), is("Hello"));
		assertThat(pair.get(1), is("world"));

		pair.get(2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void set() {
		Pair<String> pair = Pair.of("Hello", "world");

		pair.set(0, "Good-bye");
		pair.set(1, "Earthlings");

		assertThat(pair, is(Pair.of("Good-bye", "Earthlings")));

		pair.set(2, "!");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void addAtIndex() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.add(0, "Well");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeAtIndex() {
		Pair<String> pair = Pair.of("Hello", "world");
		pair.remove(1);
	}

	@Test
	public void indexOf() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.indexOf("Hello"), is(0));
		assertThat(pair.indexOf("world"), is(1));
		assertThat(pair.indexOf("Earthlings"), is(-1));
		assertThat(pair.indexOf(null), is(-1));

		pair = Pair.of("hello", "hello");
		assertThat(pair.indexOf("hello"), is(0));
	}

	@Test
	public void lastIndexOf() {
		Pair<String> pair = Pair.of("Hello", "world");
		assertThat(pair.lastIndexOf("Hello"), is(0));
		assertThat(pair.lastIndexOf("world"), is(1));
		assertThat(pair.lastIndexOf("Earthlings"), is(-1));
		assertThat(pair.lastIndexOf(null), is(-1));

		pair = Pair.of("hello", "hello");
		assertThat(pair.lastIndexOf("hello"), is(1));
	}

	@Test(expected = NoSuchElementException.class)
	public void listIterator() {
		Pair<String> pair = Pair.of("Hello", "world");
		ListIterator<String> iter = pair.listIterator();

		assertThat(iter.hasNext(), is(true));
		assertThat(iter.hasPrevious(), is(false));

		assertThat(iter.previousIndex(), is(-1));
		assertThat(iter.nextIndex(), is(0));
		assertThat(iter.next(), is("Hello"));

		assertThat(iter.hasNext(), is(true));
		assertThat(iter.hasPrevious(), is(true));
		assertThat(iter.previousIndex(), is(0));
		assertThat(iter.nextIndex(), is(1));

		assertThat(iter.previous(), is("Hello"));
		assertThat(iter.previousIndex(), is(-1));
		assertThat(iter.nextIndex(), is(0));
		assertThat(iter.hasNext(), is(true));
		assertThat(iter.hasPrevious(), is(false));

		assertThat(iter.next(), is("Hello"));
		assertThat(iter.next(), is("world"));

		assertThat(iter.hasNext(), is(false));
		assertThat(iter.hasPrevious(), is(true));
		assertThat(iter.previousIndex(), is(1));
		assertThat(iter.nextIndex(), is(2));

		assertThat(iter.previous(), is("world"));

		iter.set("Earthlings");
		assertThat(pair, is(Pair.of("Hello", "Earthlings")));

		assertThat(iter.hasNext(), is(true));
		assertThat(iter.hasPrevious(), is(true));
		assertThat(iter.previousIndex(), is(0));
		assertThat(iter.nextIndex(), is(1));
		assertThat(iter.next(), is("Earthlings"));

		iter.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void listIteratorAtIndex() {
		Pair<String> pair = Pair.of("Hello", "world");
		ListIterator<String> iter = pair.listIterator(2);

		assertThat(iter.hasPrevious(), is(true));
		assertThat(iter.previousIndex(), is(1));
		assertThat(iter.next(), is(2));
		assertThat(iter.previous(), is("world"));
		assertThat(iter.hasPrevious(), is(true));
		assertThat(iter.previousIndex(), is(0));
		assertThat(iter.next(), is(1));
		assertThat(iter.previous(), is("Hello"));

		assertThat(iter.hasPrevious(), is(false));
		assertThat(iter.previousIndex(), is(-1));
		assertThat(iter.next(), is(0));
		iter.previous();
	}

	@Test
	public void subList() {
		Pair<String> pair = Pair.of("Hello", "world");

		assertThat(pair.subList(0, 0), is(Collections.emptyList()));
		assertThat(pair.subList(1, 1), is(Collections.emptyList()));
		assertThat(pair.subList(2, 2), is(Collections.emptyList()));

		assertThat(pair.subList(0, 2), is(pair));
		assertThat(pair.subList(0, 1), is(Collections.singletonList("Hello")));
		assertThat(pair.subList(1, 2), is(Collections.singletonList("world")));

		assertThat(pair.subList(0, 1).size(), is(1));
		assertThat(pair.subList(0, 1).isEmpty(), is(false));

		pair.subList(1, 2).set(0, "Earthlings");
		assertThat(pair, is(Pair.of("Hello", "Earthlings")));
	}

	@Test
	public void hashCode_() {
		Pair<String> pair = Pair.of("Hello", "world");
		Pair<String> pair2 = Pair.of("Greetings", "Earthlings");
		Pair<String> pair3 = Pair.of("Hello", "world");

		assertThat(pair.hashCode(), is(pair3.hashCode()));
		assertThat(pair.hashCode(), not(pair2.hashCode()));
	}

	@Test
	public void equals_() {
		Pair<String> pair = Pair.of("Hello", "world");

		assertThat(pair.equals(pair), is(true));
		assertThat(pair.equals(Pair.of("Hello", "world")), is(true));
		assertThat(pair.equals(Pair.of("Greetings", "Earthlings")), is(false));
		assertThat(pair.equals(Arrays.asList("Hello", "world")), is(true));
		assertThat(pair.equals("Hello"), is(false));
		assertThat(pair.equals(null), is(false));
	}

}
