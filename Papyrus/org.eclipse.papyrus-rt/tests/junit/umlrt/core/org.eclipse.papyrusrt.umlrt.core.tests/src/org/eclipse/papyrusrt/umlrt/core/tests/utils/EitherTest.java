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

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.junit.Test;

/**
 * Test suite for the {@link Either} type.
 */
public class EitherTest {

	private static final Object NULL = null;
	private static final Integer INT_NULL = null;
	private static final Double DOUBLE_NULL = null;
	private static final Number NUM_NULL = null;
	private static final String STRING_NULL = null;

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#empty()}.
	 */
	@Test
	public void testEmpty() {
		assertThat("More than one empty instance", Either.empty(), sameInstance(Either.empty()));

		assertThat("New empty instance created", Either.or(NULL, NULL), sameInstance(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#of(java.lang.Object)}.
	 */
	@Test
	public void testOfT() {
		assertThat(Either.of("foo").toString(), is("Either[foo][]"));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ofAlternate(java.lang.Object)}.
	 */
	@Test
	public void testOfAlternate() {
		assertThat(Either.ofAlternate("foo").toString(), is("Either[][foo]"));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#or(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testOr() {
		assertThat(Either.or("foo", INT_NULL), is(Either.of("foo")));
		assertThat(Either.or(STRING_NULL, 42), is(Either.ofAlternate(42)));
		assertThat(Either.or(STRING_NULL, INT_NULL), is(Either.empty()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOr_throw() {
		Either.or("foo", 42);
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#cast(java.lang.Object, java.lang.Class, java.lang.Class)}.
	 */
	@Test
	public void testCast() {
		assertThat(Either.cast("foo", String.class, Integer.class), is(Either.of("foo")));
		assertThat(Either.cast(42, String.class, Integer.class), is(Either.ofAlternate(42)));
		assertThat(Either.cast(Either.class, String.class, Integer.class), is(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#or(java.lang.Object, java.util.function.Supplier)}.
	 */
	@Test
	public void testOrTSupplierOfQextendsU() {
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.or("foo", () -> NULL.hashCode()), is(Either.of("foo")));
		assertThat(Either.or(STRING_NULL, () -> 42), is(Either.ofAlternate(42)));
		assertThat(Either.or(STRING_NULL, () -> NULL), is(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#of(java.util.Optional, java.util.Optional)}.
	 */
	@Test
	public void testOfOptionalOfTOptionalOfU() {
		assertThat(Either.of(Optional.of("foo"), Optional.empty()), is(Either.of("foo")));
		assertThat(Either.of(Optional.empty(), Optional.of(42)), is(Either.ofAlternate(42)));
		assertThat(Either.of(Optional.empty(), Optional.empty()), is(Either.empty()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfOptionalOfTOptionalOfU_throw() {
		Either.of(Optional.of("foo"), Optional.of(42));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#or(java.util.Optional, java.util.function.Supplier)}.
	 */
	@Test
	public void testOrOptionalOfTSupplierOfOptionalOfU() {
		assertThat(Either.or(Optional.of("foo"), () -> Optional.of(42)), is(Either.of("foo")));
		assertThat(Either.or(Optional.empty(), () -> Optional.of(42)), is(Either.ofAlternate(42)));
		assertThat(Either.or(Optional.empty(), () -> Optional.empty()), is(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#isPresent()}.
	 */
	@Test
	public void testIsPresent() {
		assertTrue("Not present", Either.of("foo").isPresent());
		assertFalse("Present", Either.empty().isPresent());
		assertFalse("Present", Either.ofAlternate(42).isPresent());
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#isAlternate()}.
	 */
	@Test
	public void testIsAlternate() {
		assertTrue("Not alternate", Either.ofAlternate(42).isAlternate());
		assertFalse("Alternate", Either.empty().isAlternate());
		assertFalse("Alternate", Either.of("foo").isAlternate());
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#isEither()}.
	 */
	@Test
	public void testIsEither() {
		assertTrue("Not either", Either.of("foo").isEither());
		assertTrue("Not either", Either.ofAlternate(42).isEither());
		assertFalse("Either", Either.empty().isEither());
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#get()}.
	 */
	@Test
	public void testGet() {
		assertThat(Either.of("foo").get(), is("foo"));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGet_throw() {
		Either.ofAlternate(42).get();
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#getAlternate()}.
	 */
	@Test
	public void testGetAlternate() {
		assertThat(Either.ofAlternate(42).getAlternate(), is(42));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetAlternative_throw() {
		Either.of("foo").getAlternate();
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#getEither()}.
	 */
	@Test
	public void testGetEither() {
		assertThat(Either.of("foo").getEither(), is("foo"));
		assertThat(Either.ofAlternate(42).getEither(), is(42));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetEither_throw() {
		Either.empty().getEither();
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElse(java.lang.Object)}.
	 */
	@Test
	public void testOrElseObject() {
		assertThat(Either.of("foo").orElse("bar"), is("foo"));
		assertThat(Either.ofAlternate(42).orElse("bar"), is(42));
		assertThat(Either.empty().orElse("bar"), is("bar"));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElseGet(java.util.function.Supplier)}.
	 */
	@Test
	public void testOrElseGetSupplierOfQ() {
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.of("foo").orElseGet(() -> NULL.toString()), is("foo"));
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.ofAlternate(42).orElseGet(() -> NULL.toString()), is(42));
		assertThat(Either.empty().orElseGet(() -> "bar"), is("bar"));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElseThrow(java.util.function.Supplier)}.
	 */
	@Test(expected = IOException.class)
	public void testOrElseThrowSupplierOfQextendsX() throws IOException {
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.of("foo").orElseThrow(NullPointerException::new), is("foo"));
		assertThat(Either.ofAlternate(42).orElseThrow(IOException::new), is(42));
		Either.empty().orElseThrow(IOException::new);
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#getEither(java.lang.Class)}.
	 */
	@Test
	public void testGetEitherClassOfS() {
		assertThat(Either.or(42, DOUBLE_NULL).getEither(Number.class), is(42));
		assertThat(Either.or(INT_NULL, Math.E).getEither(Number.class), is(Math.E));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElse(java.lang.Class, java.lang.Object)}.
	 */
	@Test
	public void testOrElseClassOfSS() {
		assertThat(Either.or(42, DOUBLE_NULL).orElse(Number.class, Math.E), is(42));
		assertThat(Either.or(INT_NULL, Math.PI).orElse(Number.class, Math.E), is(Math.PI));
		assertThat(Either.or(INT_NULL, DOUBLE_NULL).orElse(Number.class, Math.E), is(Math.E));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElseGet(java.lang.Class, java.util.function.Supplier)}.
	 */
	@Test
	public void testOrElseGetClassOfSSupplierOfQextendsS() {
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.or(42, DOUBLE_NULL).orElseGet(Number.class, () -> NUM_NULL.hashCode()), is(42));
		// Asserts that the supplier is not invoked if it is not needed
		assertThat(Either.or(INT_NULL, Math.PI).orElseGet(Number.class, () -> NUM_NULL.hashCode()), is(Math.PI));
		assertThat(Either.or(INT_NULL, DOUBLE_NULL).orElseGet(Number.class, () -> Math.E), is(Math.E));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#orElseThrow(java.lang.Class, java.util.function.Supplier)}.
	 */
	@Test(expected = IOException.class)
	public void testOrElseThrowClassOfSSupplierOfQextendsX() throws IOException {
		assertThat(Either.or(42, DOUBLE_NULL).orElseThrow(Number.class, NullPointerException::new), is(42));
		assertThat(Either.or(INT_NULL, Math.PI).orElseThrow(Number.class, NullPointerException::new), is(Math.PI));
		Either.or(INT_NULL, DOUBLE_NULL).orElseThrow(Number.class, IOException::new);
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ifPresent(java.util.function.Consumer)}.
	 */
	@Test
	public void testIfPresentConsumerOfQsuperT() {
		Object[] slot = { null };
		Consumer<Object> fillSlot = t -> slot[0] = t;

		Either.ofAlternate(42).ifPresent(fillSlot);
		assertThat(slot[0], nullValue());
		Either.of("foo").ifPresent(fillSlot);
		assertThat(slot[0], is("foo"));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ifAlternate(java.util.function.Consumer)}.
	 */
	@Test
	public void testIfAlternate() {
		Object[] slot = { null };
		Consumer<Object> fillSlot = t -> slot[0] = t;

		Either.of("foo").ifAlternate(fillSlot);
		assertThat(slot[0], nullValue());
		Either.ofAlternate(42).ifAlternate(fillSlot);
		assertThat(slot[0], is(42));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ifAbsent(java.lang.Runnable)}.
	 */
	@Test
	public void testIfAbsent() {
		boolean[] canary = { true };
		Runnable die = () -> canary[0] = false;

		Either.of("foo").ifAbsent(die);
		assertThat(canary[0], is(true));
		Either.ofAlternate(42).ifAbsent(die);
		assertThat(canary[0], is(true));
		Either.empty().ifAbsent(die);
		assertThat(canary[0], is(false));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ifPresent(java.util.function.Consumer, java.util.function.Consumer)}.
	 */
	@Test
	public void testIfPresentConsumerOfQsuperTConsumerOfQsuperU() {
		Object[] slot = { null };
		Consumer<Object> fillString = t -> slot[0] = t;
		Consumer<Object> fillInteger = u -> slot[0] = u;

		Either.empty().ifPresent(fillString, fillInteger);
		assertThat(slot[0], nullValue());
		Either.of("foo").ifPresent(fillString, fillInteger);
		assertThat(slot[0], is("foo"));
		Either.ofAlternate(42).ifPresent(fillString, fillInteger);
		assertThat(slot[0], is(42));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#ifEither(java.lang.Class, java.util.function.Consumer)}.
	 */
	@Test
	public void testIfEither() {
		Number[] slot = { null };
		Consumer<Number> fillSlot = t -> slot[0] = t;

		Either.empty().ifEither(Number.class, fillSlot);
		assertThat(slot[0], nullValue());
		Either.or(42, DOUBLE_NULL).ifEither(Number.class, fillSlot);
		assertThat(slot[0], is(42));
		Either.or(INT_NULL, Math.E).ifEither(Number.class, fillSlot);
		assertThat(slot[0], is(Math.E));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#accept(java.util.function.Consumer, java.util.function.Consumer, java.lang.Runnable)}.
	 */
	@Test
	public void testAccept() {
		String[] string = { null };
		Consumer<String> fillString = t -> string[0] = t;
		int[] integer = { 0 };
		Consumer<Integer> fillInteger = u -> integer[0] = u;
		boolean[] canary = { true };
		Runnable die = () -> canary[0] = false;

		Either.<String, Integer> empty().accept(fillString, fillInteger, die);
		assertThat(string[0], nullValue());
		assertThat(integer[0], is(0));
		assertThat(canary[0], is(false));

		canary[0] = true;
		Either.or("foo", INT_NULL).accept(fillString, fillInteger, die);
		assertThat(string[0], is("foo"));
		assertThat(integer[0], is(0));
		assertThat(canary[0], is(true));

		string[0] = null;
		Either.or(STRING_NULL, 42).accept(fillString, fillInteger, die);
		assertThat(string[0], nullValue());
		assertThat(integer[0], is(42));
		assertThat(canary[0], is(true));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#map(java.util.function.Function)}.
	 */
	@Test
	public void testMapFunctionOfQsuperTF() {
		assertThat(Either.<String, Integer> empty().map(String::length), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").map(String::length), is(Either.of(3)));
		assertThat(Either.<String, Integer> ofAlternate(42).map(String::length), is(Either.ofAlternate(42)));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#mapAlternate(java.util.function.Function)}.
	 */
	@Test
	public void testMapAlternate() {
		assertThat(Either.<String, Integer> empty().mapAlternate(Integer::toHexString), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").mapAlternate(Integer::toHexString), is(Either.of("foo")));
		assertThat(Either.<String, Integer> ofAlternate(42).mapAlternate(Integer::toHexString), is(Either.ofAlternate("2a")));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#mapEither(java.lang.Class, java.util.function.Function)}.
	 */
	@Test
	public void testMapEither() {
		assertThat(Either.<Integer, Double> empty().mapEither(Number.class, Number::doubleValue), is(Optional.empty()));
		assertThat(Either.<Integer, Double> of(42).mapEither(Number.class, Number::doubleValue), is(Optional.of(42.0)));
		assertThat(Either.<Integer, Double> ofAlternate(Math.E).mapEither(Number.class, Number::doubleValue), is(Optional.of(Math.E)));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#flatMapEither(java.lang.Class, java.util.function.Function)}.
	 */
	@Test
	public void testFlatMapEither() {
		assertThat(Either.<Integer, Double> empty().flatMapEither(Number.class, v -> Optional.of(v.doubleValue())), is(Optional.empty()));
		assertThat(Either.<Integer, Double> of(42).flatMapEither(Number.class, v -> Optional.of(v.doubleValue())), is(Optional.of(42.0)));
		assertThat(Either.<Integer, Double> ofAlternate(Math.E).flatMapEither(Number.class, v -> Optional.of(v.doubleValue())), is(Optional.of(Math.E)));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#flatMap(java.util.function.Function, java.util.function.Function)}.
	 */
	@Test
	public void testFlatMapOptionalOptional() {
		Function<String, Optional<Integer>> sFunc = s -> Optional.of(s.length());
		Function<Integer, Optional<String>> iFunc = i -> Optional.of(i.toString());

		assertThat(Either.<String, Integer> empty().flatMap(sFunc, iFunc), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").flatMap(sFunc, iFunc), is(Either.of(3)));
		assertThat(Either.<String, Integer> ofAlternate(3).flatMap(sFunc, iFunc), is(Either.ofAlternate("3")));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#map(java.util.function.Function, java.util.function.Function)}.
	 */
	@Test
	public void testMapFunctionOfQsuperTFFunctionOfQsuperUG() {
		assertThat(Either.<String, Integer> empty().map(String::length, Integer::toHexString), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").map(String::length, Integer::toHexString), is(Either.of(3)));
		assertThat(Either.<String, Integer> ofAlternate(42).map(String::length, Integer::toHexString), is(Either.ofAlternate("2a")));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#map(java.util.function.BiFunction)}.
	 */
	@Test
	public void testMapBiFunctionOfQsuperTQsuperUR() {
		BiFunction<String, Integer, Double> asDouble = (s, i) -> (s != null) ? (double) s.length() : i.doubleValue();
		assertThat(Either.<String, Integer> empty().map(asDouble), is(Optional.empty()));
		assertThat(Either.<String, Integer> of("foo").map(asDouble), is(Optional.of(3.0)));
		assertThat(Either.<String, Integer> ofAlternate(42).map(asDouble), is(Optional.of(42.0)));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#flatMap(java.util.function.BiFunction)}.
	 */
	@Test
	public void testFlatMap() {
		BiFunction<String, Integer, Optional<Double>> asDouble = (s, i) -> (s != null) ? Optional.of((double) s.length()) : Optional.of(i.doubleValue());
		assertThat(Either.<String, Integer> empty().flatMap(asDouble), is(Optional.empty()));
		assertThat(Either.<String, Integer> of("foo").flatMap(asDouble), is(Optional.of(3.0)));
		assertThat(Either.<String, Integer> ofAlternate(42).flatMap(asDouble), is(Optional.of(42.0)));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#filter(java.util.function.Predicate)}.
	 */
	@Test
	public void testFilterPredicateOfQsuperT() {
		Predicate<String> isFoo = "foo"::equals;
		assertThat(Either.<String, Integer> empty().filter(isFoo), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").filter(isFoo), is(Either.of("foo")));
		assertThat(Either.<String, Integer> ofAlternate(42).filter(isFoo), is(Either.empty()));
		assertThat(Either.<String, Integer> of("bar").filter(isFoo), is(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#filterAlternate(java.util.function.Predicate)}.
	 */
	@Test
	public void testFilterAlternate() {
		Predicate<Integer> is42 = new Integer(42)::equals;
		assertThat(Either.<String, Integer> empty().filterAlternate(is42), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").filterAlternate(is42), is(Either.empty()));
		assertThat(Either.<String, Integer> ofAlternate(42).filterAlternate(is42), is(Either.ofAlternate(42)));
		assertThat(Either.<String, Integer> ofAlternate(0).filterAlternate(is42), is(Either.empty()));
	}

	/**
	 * Test method for {@link org.eclipse.papyrusrt.umlrt.core.utils.Either#filter(java.util.function.BiPredicate)}.
	 */
	@Test
	public void testFilterBiPredicateOfQsuperTQsuperU() {
		BiPredicate<String, Integer> isFooOr42 = (s, i) -> "foo".equals(s) || ((Integer) 42).equals(i);
		assertThat(Either.<String, Integer> empty().filter(isFooOr42), is(Either.empty()));
		assertThat(Either.<String, Integer> of("foo").filter(isFooOr42), is(Either.of("foo")));
		assertThat(Either.<String, Integer> ofAlternate(42).filter(isFooOr42), is(Either.ofAlternate(42)));
		assertThat(Either.<String, Integer> of("bar").filter(isFooOr42), is(Either.empty()));
		assertThat(Either.<String, Integer> ofAlternate(0).filter(isFooOr42), is(Either.empty()));
	}

}
