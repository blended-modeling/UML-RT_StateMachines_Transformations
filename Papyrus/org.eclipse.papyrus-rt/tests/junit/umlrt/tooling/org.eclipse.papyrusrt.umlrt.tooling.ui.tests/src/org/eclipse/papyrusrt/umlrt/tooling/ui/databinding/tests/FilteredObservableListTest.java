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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.Predicate;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.FilteredObservableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression test cases for the {@link FilteredObservableList} class.
 */
@RunWith(Parameterized.class)
public class FilteredObservableListTest {

	@Rule
	public final TestRule bindingsRule = new DataBindingsRule();

	private final boolean isFiltered;

	private FilteredObservableList<String> fixture;

	public FilteredObservableListTest(boolean isFiltered, String __) {
		super();

		this.isFiltered = isFiltered;
	}

	@Test
	public void size() {
		assertThat(fixture.size(), is(select(6, 5)));
	}

	@Test
	public void contains() {
		assertThat(fixture.contains(""), is(select(true, false)));
	}

	@Test
	public void indexOf() {
		assertThat(fixture.indexOf("c"), is(select(3, 2)));
	}

	@Test
	public void lastIndexOf() {
		assertThat(fixture.lastIndexOf("b"), is(select(4, 3)));
	}

	@Test
	public void get() {
		assertThat(fixture.get(select(3, 2)), is("c"));
	}

	@Test
	public void remove() {
		assertThat(fixture.remove((int) select(3, 2)), is("c"));
		assertThat(fixture, is(select(
				Arrays.asList("a", "b", "", "b", "d"),
				Arrays.asList("a", "b", "b", "d"))));
	}

	@Test
	public void set() {
		assertThat(fixture.set(select(3, 2), "e"), is("c"));
		assertThat(fixture, is(select(
				Arrays.asList("a", "b", "", "e", "b", "d"),
				Arrays.asList("a", "b", "e", "b", "d"))));
	}

	@Test
	public void add() {
		fixture.add("e");
		assertThat(fixture, is(select(
				Arrays.asList("a", "b", "", "c", "b", "d", "e"),
				Arrays.asList("a", "b", "c", "b", "d", "e"))));
	}

	@Test
	public void insert() {
		fixture.add(select(4, 3), "e");
		assertThat(fixture, is(select(
				Arrays.asList("a", "b", "", "c", "e", "b", "d"),
				Arrays.asList("a", "b", "c", "e", "b", "d"))));
	}

	@Test
	public void move() {
		assertThat(fixture.move(select(3, 2), select(4, 3)), is("c"));
		assertThat(fixture, is(select(
				Arrays.asList("a", "b", "", "b", "c", "d"),
				Arrays.asList("a", "b", "b", "c", "d"))));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ false, "raw" },
				{ true, "filtered" },
		});
	}

	@Before
	public void createFixture() {
		fixture = new FilteredObservableList<>(new WritableList<>());

		fixture.add("a");
		fixture.add("b");
		fixture.add("");
		fixture.add("c");
		fixture.add("b");
		fixture.add("d");

		if (isFiltered) {
			filter();
		}
	}

	protected void filter() {
		Predicate<String> isEmpty = String::isEmpty;
		fixture.addFilter(isEmpty.negate());
	}

	<T> T select(T raw, T filtered) {
		return isFiltered ? filtered : raw;
	}
}
