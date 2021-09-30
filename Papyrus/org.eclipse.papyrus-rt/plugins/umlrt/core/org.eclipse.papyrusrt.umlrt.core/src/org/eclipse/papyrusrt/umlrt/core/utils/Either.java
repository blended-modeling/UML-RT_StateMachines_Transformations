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

package org.eclipse.papyrusrt.umlrt.core.utils;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An analogue of {@link Optional} that encapsulates a value that may have either
 * of two different types.
 */
public final class Either<T, U> {

	private static final Either<?, ?> EMPTY = new Either<>();
	private final T t;
	private final U u;

	private Either() {
		super();

		this.t = null;
		this.u = null;
	}

	private Either(T t, U u) {
		super();

		this.t = t;
		this.u = u;
	}

	/**
	 * The "neither".
	 * 
	 * @return the empty value
	 */
	@SuppressWarnings("unchecked")
	public static <T, U> Either<T, U> empty() {
		return (Either<T, U>) EMPTY;
	}

	/**
	 * Obtains a value of the first or primary type.
	 * 
	 * @param t
	 *            the value
	 * @return the value as the first or primary type
	 * 
	 * @throws NullPointerException
	 *             if {@code t} is {@code null}
	 */
	public static <T, U> Either<T, U> of(T t) {
		return new Either<>(requireNonNull(t), null);
	}

	/**
	 * Obtains a value of the second or alternate type.
	 * 
	 * @param u
	 *            the value
	 * @return the value as the second or alternate type
	 * 
	 * @throws NullPointerException
	 *             if {@code u} is {@code null}
	 */
	public static <T, U> Either<T, U> ofAlternate(U u) {
		return new Either<>(null, requireNonNull(u));
	}

	/**
	 * Obtains a value of either type.
	 * 
	 * @param t
	 *            the possible value of the first or primary type
	 * @param u
	 *            the possible value of the second or alternate type
	 * @return the value, or {@link #empty() empty} if both
	 *         possible values are {@code null}
	 * 
	 * @throws IllegalArgumentException
	 *             if both {@code t} and {@code u}
	 *             are non-{@code null}, because then which should the result have?
	 */
	public static <T, U> Either<T, U> or(T t, U u) {
		if ((t != null) && (u != null)) {
			throw new IllegalArgumentException("both values cannot be null"); //$NON-NLS-1$
		}

		return (t != null) ? of(t) : (u != null) ? ofAlternate(u) : empty();
	}

	/**
	 * Obtains a value by casting an {@code object} to two alternative types.
	 * 
	 * @param object
	 *            an object to cast
	 * @param type
	 *            the first or primary possible type
	 * @param alternateType
	 *            the second or alternate possible type
	 * @return the value as either type, or {@link #empty() empty} if the
	 *         {@code object} is {@code null} or an instance of neither type
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code object} is an instance of
	 *             both types, because then which type should the result have?
	 */
	public static <T, U> Either<T, U> cast(Object object, Class<T> type, Class<U> alternateType) {
		if (requireNonNull(type).isInstance(object) && requireNonNull(alternateType).isInstance(object)) {
			throw new IllegalArgumentException("object is an instance of both types"); //$NON-NLS-1$
		}

		return (type.isInstance(object))
				? of(type.cast(object))
				: (alternateType.isInstance(object))
						? ofAlternate(alternateType.cast(object))
						: empty();
	}

	/**
	 * Obtains a value of either type. This operation short-ciruites:
	 * the alternate supplier is not invoked if it is not needed.
	 * 
	 * @param t
	 *            the possible value of the first or primary type
	 * @param u
	 *            a supplier of a value of the second or alternate type
	 * @return the value, or {@link #empty() empty} if both
	 *         possible values are {@code null}
	 */
	public static <T, U> Either<T, U> or(T t, Supplier<? extends U> u) {
		if (t != null) {
			return of(t);
		} else {
			U u_ = requireNonNull(u).get();
			return (u_ != null) ? ofAlternate(u_) : empty();
		}
	}

	/**
	 * Obtains a value of either type.
	 * 
	 * @param t
	 *            the possible value of the first or primary type
	 * @param u
	 *            the possible value of the second or alternate type
	 * @return the value, or {@link #empty() empty} if both
	 *         possible values are {@code null}
	 * 
	 * @throws IllegalArgumentException
	 *             if both {@code t} and {@code u}
	 *             are {@link Optional#isPresent() present}, because then which
	 *             should the result have?
	 */
	public static <T, U> Either<T, U> of(Optional<T> t, Optional<U> u) {
		if (requireNonNull(t).isPresent() && requireNonNull(u).isPresent()) {
			throw new IllegalArgumentException("both optionals cannot be present"); //$NON-NLS-1$
		}

		return t.isPresent() ? of(t.get()) : u.isPresent() ? ofAlternate(u.get()) : empty();
	}

	/**
	 * Obtains a value of either type. This operation short-ciruites:
	 * the alternate supplier is not invoked if it is not needed.
	 * 
	 * @param t
	 *            the possible value of the first or primary type
	 * @param u
	 *            a supplier of a value of the second or alternate type
	 * @return the value, or {@link #empty() empty} if both
	 *         possible values are {@code null}
	 */
	public static <T, U> Either<T, U> or(Optional<T> t, Supplier<Optional<U>> u) {
		if (t.isPresent()) {
			return of(t.get());
		} else {
			Optional<U> u_ = requireNonNull(u).get();
			return (u_.isPresent()) ? ofAlternate(u_.get()) : empty();
		}
	}

	/**
	 * Two {@code Either} values are equal if they encapsulate the
	 * same value of the same type.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Either<?, ?>)) {
			return false;
		} else {
			Either<?, ?> other = (Either<?, ?>) obj;
			return Objects.equals(t, other.t) && Objects.equals(u, other.u);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(t, u);
	}

	/**
	 * Queries whether the value has the first or primary type.
	 * 
	 * @return whether the value has the first or primary type
	 */
	public boolean isPresent() {
		return t != null;
	}

	/**
	 * Queries whether the value has the second or alternate type.
	 * 
	 * @return whether the value has the second or alternate type
	 */
	public boolean isAlternate() {
		return u != null;
	}

	/**
	 * Queries whether the value has either type.
	 * 
	 * @return whether I am not the {@link #empty() empty} value
	 */
	public boolean isEither() {
		return (t != null) || (u != null);
	}

	/**
	 * Obtains my value as the first or primary type.
	 * 
	 * @return my value has the first or primary type
	 * 
	 * @throws NoSuchElementException
	 *             if the value is not {@link #isPresent() present}
	 * @see #isPresent()
	 */
	public T get() {
		if (t == null) {
			throw new NoSuchElementException();
		}

		return t;
	}

	/**
	 * Obtains my value as the second or alternate type.
	 * 
	 * @return my value has the second or alternate type
	 * 
	 * @throws NoSuchElementException
	 *             if the value is not {@link #isAlternate() alternate-present}
	 * @see #isAlternate()
	 */
	public U getAlternate() {
		if (u == null) {
			throw new NoSuchElementException();
		}

		return u;
	}

	/**
	 * Obtains my value as either type.
	 * 
	 * @return my value as either type
	 * 
	 * @throws NoSuchElementException
	 *             if the value is not {@link #isEither() either-present}
	 * @see #isEither()
	 */
	public Object getEither() {
		if (t != null) {
			return t;
		} else if (u != null) {
			return u;
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Obtains my value as either type, or the given default if not present.
	 * 
	 * @param other
	 *            a default value to return if I have neither value
	 * @return my value as either type, or else the {@code other}
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public Object orElse(Object other) {
		return isEither() ? getEither() : other;
	}

	/**
	 * Obtains my value as either type, or the given default if not present.
	 * This operation short-circuits: the supplier is not invoked if it is
	 * not needed.
	 * 
	 * @param other
	 *            supplier of a default value to return if I have neither value
	 * @return my value as either type, or else the {@code other}'s value
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public Object orElseGet(Supplier<?> other) {
		return isEither() ? getEither() : requireNonNull(other).get();
	}

	/**
	 * Obtains my value as either type, or else throws the given exception
	 * 
	 * @param exception
	 *            a supplier of an exception to throw if I have neither value
	 * @return my value as either type
	 * 
	 * @throws the
	 *             supplied exception if I am {@link #empty() empty}
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public <X extends Throwable> Object orElseThrow(Supplier<? extends X> exception) throws X {
		if (isEither()) {
			return getEither();
		}

		throw requireNonNull(exception).get();
	}

	/**
	 * Obtains my value as an instance of the given common supertype
	 * of either of my types.
	 * 
	 * @param commonSupertype
	 *            a common ancestor of both of my types
	 * @return my value as the common supertype
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public <S> S getEither(Class<S> commonSupertype) {
		return commonSupertype.cast(getEither());
	}

	/**
	 * Obtains my value as an instance of the given common supertype
	 * of either of my types, or the given default if not present.
	 * 
	 * @param commonSupertype
	 *            a common ancestor of both of my types
	 * @param other
	 *            a default value to return if I have neither value
	 * @return my value as the common supertype, or else the {@code other}
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public <S> S orElse(Class<S> commonSupertype, S other) {
		return isEither() ? getEither(commonSupertype) : other;
	}

	/**
	 * Obtains my value as an instance of the given common supertype
	 * of either of my types, or the given default if not present.
	 * This operation short-circuits: the supplier is not invoked
	 * if it is not needed.
	 * 
	 * @param commonSupertype
	 *            a common ancestor of both of my types
	 * @param other
	 *            supplier of a default value to return if I have neither value
	 * @return my value as the common supertype, or else the {@code other}
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public <S> S orElseGet(Class<S> commonSupertype, Supplier<? extends S> other) {
		return isEither() ? getEither(commonSupertype) : requireNonNull(other).get();
	}

	/**
	 * Obtains my value as an instance of the given common supertype
	 * of either of my types, with the option to throw an exception
	 * if I have no value.
	 * 
	 * @param commonSupertype
	 *            a common ancestor of both of my types
	 * @param exception
	 *            supplier of an exception to throw if I have neither value
	 * @return my value as the common supertype
	 * 
	 * @throws the
	 *             supplied exception if I am {@link #empty() empty}
	 * 
	 * @see #isEither()
	 * @see #getEither()
	 */
	public <S, X extends Throwable> S orElseThrow(Class<S> commonSupertype, Supplier<? extends X> exception) throws X {
		if (isEither()) {
			return getEither(commonSupertype);
		}

		throw requireNonNull(exception).get();
	}

	/**
	 * Injects my value into some procedure if it is of my first or primary type.
	 * 
	 * @param consumer
	 *            a consumer of my first or primary type
	 * @return myself, for the convenience of call chaining
	 */
	public Either<T, U> ifPresent(Consumer<? super T> consumer) {
		if (t != null) {
			consumer.accept(t);
		}

		return this;
	}

	/**
	 * Injects my value into some procedure if it is of my second or alternate type.
	 * 
	 * @param consumer
	 *            a consumer of my second or alternate type
	 * @return myself, for the convenience of call chaining
	 */
	public Either<T, U> ifAlternate(Consumer<? super U> consumer) {
		if (u != null) {
			consumer.accept(u);
		}

		return this;
	}

	/**
	 * Runs an action if I have no value
	 * 
	 * @param action
	 *            an action to run in the event that I am {@link #empty() empty}
	 * @return myself, for the convenience of call chaining
	 */
	public Either<T, U> ifAbsent(Runnable action) {
		if ((t == null) && (u == null)) {
			action.run();
		}

		return this;
	}

	/**
	 * Injects my value into some procedure if I have a value of either type.
	 * 
	 * @param consumer
	 *            a consumer of my first or primary type
	 * @param alternate
	 *            a consumer of my second or alternate type
	 * 
	 * @return myself, for the convenience of call chaining
	 */
	public Either<T, U> ifPresent(Consumer<? super T> consumer, Consumer<? super U> alternate) {
		if (t != null) {
			consumer.accept(t);
		} else if (u != null) {
			alternate.accept(u);
		}

		return this;
	}

	/**
	 * Injects my value into some procedure if it is of either of my types.
	 * 
	 * @param commonSupertype
	 *            an ancestor of both of my types
	 * @param consumer
	 *            a consumer of the common supertype
	 * 
	 * @return my value as the common supertype, for the convenience of call chaining.
	 *         This may, of course, be {@link Optional#empty() empty}
	 */
	public <S> Optional<S> ifEither(Class<S> commonSupertype, Consumer<? super S> consumer) {
		if (isEither()) {
			S either = getEither(commonSupertype);
			consumer.accept(either);
			return Optional.of(either);
		}

		return Optional.empty();
	}

	/**
	 * Injects my value into some procedure if it is of my first or primary type,
	 * or else runs an action if I have no value.
	 * 
	 * @param consumer
	 *            a consumer of my first or primary type
	 * @param alternate
	 *            a consumer of my second or alternate type
	 * @param absent
	 *            an action to run in the case that my value is absent
	 * 
	 * @return myself, for the convenience of call chaining
	 */
	public Either<T, U> accept(Consumer<? super T> consumer, Consumer<? super U> alternate, Runnable absent) {
		if (t != null) {
			consumer.accept(t);
		} else if (u != null) {
			alternate.accept(u);
		} else {
			absent.run();
		}

		return this;
	}

	/**
	 * Transforms my value under the given function if it is of my first or primary type.
	 * 
	 * @param mapper
	 *            a function on my first or primary type
	 * @return the result of applying the function to my value
	 */
	@SuppressWarnings("unchecked")
	public <F> Either<F, U> map(Function<? super T, F> mapper) {
		return (t != null)
				? of(mapper.apply(t))
				// If I am the alternate, then the primary type doesn't matter
				: (Either<F, U>) this;
	}

	/**
	 * Transforms my value under the given function if it is of my second or alternate type.
	 * 
	 * @param mapper
	 *            a function on my second or alternate type
	 * @return the result of applying the function to my value
	 */
	@SuppressWarnings("unchecked")
	public <G> Either<T, G> mapAlternate(Function<? super U, G> mapper) {
		return (u != null)
				? ofAlternate(mapper.apply(u))
				// If I am the primary, then the alternate type doesn't matter
				: (Either<T, G>) this;
	}

	/**
	 * Transforms my value under the given function.
	 * 
	 * @param commonSupertype
	 *            an ancestor of both of my types
	 * @param mapper
	 *            a function on the common supertype
	 * @return the result of applying the function to my value, or
	 *         {@link Optional#empty() empty} if I am {@link #empty() empty}
	 */
	public <S, R> Optional<R> mapEither(Class<S> commonSupertype, Function<? super S, ? extends R> mapping) {
		if (isEither()) {
			S either = getEither(commonSupertype);
			return Optional.ofNullable(mapping.apply(either));
		}

		return Optional.empty();
	}

	/**
	 * Transforms my value under the given function, flattening its optional result.
	 * 
	 * @param commonSupertype
	 *            an ancestor of both of my types
	 * @param mapper
	 *            a function on the common supertype that computes an optional value
	 * @return the result of applying the function to my value, or
	 *         {@link Optional#empty() empty} if I am {@link #empty() empty}
	 */
	public <S, R> Optional<R> flatMapEither(Class<S> commonSupertype, Function<? super S, Optional<R>> mapping) {
		if (isEither()) {
			S either = getEither(commonSupertype);
			return requireNonNull(mapping.apply(either));
		}

		return Optional.empty();
	}

	/**
	 * Transforms my value under one of the given functions, according to its type.
	 * 
	 * @param ifPresent
	 *            a function on my first or primary type
	 * @param ifAlternate
	 *            a function on my second or alternate type
	 *
	 * @return the result of applying the function to my value
	 */
	public <F, G> Either<F, G> map(Function<? super T, F> ifPresent, Function<? super U, G> ifAlternate) {
		return (t != null)
				? of(ifPresent.apply(t))
				: (u != null)
						? ofAlternate(ifAlternate.apply(u))
						: empty();
	}

	/**
	 * Transforms my value under one of the given functions, according to which value I have,
	 * flattening their optional results.
	 * 
	 * @param ifPresent
	 *            a function on my first or primary type
	 * @param ifAlternate
	 *            a function on my second or alternate type
	 *
	 * @return the result of applying the function to my value and flattening its result
	 */
	public <F, G> Either<F, G> flatMap(Function<? super T, Optional<F>> ifPresent, Function<? super U, Optional<G>> ifAlternate) {
		if (t != null) {
			Optional<F> result = ifPresent.apply(t);
			return result.map(f -> Either.<F, G> of(f)).orElse(Either.empty());
		} else if (u != null) {
			Optional<G> result = ifAlternate.apply(u);
			return result.map(g -> Either.<F, G> ofAlternate(g)).orElse(Either.empty());
		} else {
			return Either.empty();
		}
	}

	/**
	 * Transforms my value under the given binary function.
	 * 
	 * @param mapper
	 *            a function accepting inputs of both of my types. It must be prepared
	 *            to accept a {@code null} value in exactly one of its inputs
	 *
	 * @return the result of applying the function to my value,
	 *         or {@link Optional#empty() empty} if I am {@link #empty() empty}
	 */
	public <R> Optional<R> map(BiFunction<? super T, ? super U, R> mapper) {
		return ((t != null) || (u != null))
				? Optional.ofNullable(mapper.apply(t, u))
				: Optional.empty();
	}

	/**
	 * Transforms my value under the given binary function, flattening its optional
	 * result.
	 * 
	 * @param mapper
	 *            a function accepting inputs of both of my types, returning an
	 *            optional result. It must be prepared to accept a {@code null} value in exactly
	 *            one of its inputs
	 *
	 * @return the result of applying the function to my value,
	 *         or {@link Optional#empty() empty} if I am {@link #empty() empty}
	 */
	public <R> Optional<R> flatMap(BiFunction<? super T, ? super U, Optional<R>> mapper) {
		return ((t != null) || (u != null))
				? requireNonNull(mapper.apply(t, u))
				: Optional.empty();
	}

	/**
	 * Filters my value according to the given {@code filter} predicate
	 * of my first or primary type.
	 * 
	 * @param filter
	 *            a predicate on my first or primary type
	 *
	 * @return myself if my value is of my first or primary type and
	 *         the {@code filter} matches it, or else {@link #empty() empty}
	 * 
	 * @see #isPresent()
	 */
	public Either<T, U> filter(Predicate<? super T> filter) {
		return ((t != null) && filter.test(t))
				? this
				: empty();
	}

	/**
	 * Filters my value according to the given {@code filter} predicate
	 * of my second or alternate type.
	 * 
	 * @param filter
	 *            a predicate on my second or alternate type
	 *
	 * @return myself if my value is of my second or alternate type and
	 *         the {@code filter} matches it, or else {@link #empty() empty}
	 * 
	 * @see #isAlternate()
	 */
	public Either<T, U> filterAlternate(Predicate<? super U> filter) {
		return ((u != null) && filter.test(u))
				? this
				: empty();
	}

	/**
	 * Filters my value according to the given {@code filter} predicate
	 * of both of my types.
	 * 
	 * @param filter
	 *            a predicate on both of my types. It must be
	 *            prepared to accept a {@code null} value in exactly one of its inputs
	 *
	 * @return myself if I have a value and the {@code filter} matches it,
	 *         or else {@link #empty() empty}
	 * 
	 * @see #isEither()
	 */
	public Either<T, U> filter(BiPredicate<? super T, ? super U> filter) {
		return (((t != null) || (u != null)) && filter.test(t, u))
				? this
				: empty();
	}

	@Override
	public String toString() {
		return ((t != null) || (u != null))
				? String.format("Either[%s][%s]", (t == null) ? "" : t, (u == null) ? "" : u) //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
				: "Either.empty"; //$NON-NLS-1$
	}
}
