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

import java.lang.ref.SoftReference;
import java.util.function.Supplier;

/**
 * A lazily-computed wrapper for a {@link SoftReference} cache that refreshes
 * the reference whenever it is discovered to have been cleared.
 */
public final class CachedReference<T> implements Supplier<T> {

	private final Supplier<T> supplier;
	private Supplier<T> reference;

	/**
	 * Initializes me with my lazy computation. Obviously, this entire
	 * caching mechanism will not be useful if the {@code supplier}, itself,
	 * retains the reference value.
	 *
	 * @param supplier
	 *            the lazy computation that generates the cached reference
	 */
	public CachedReference(Supplier<T> supplier) {
		super();

		this.supplier = supplier;
		this.reference = this::compute;
	}

	private T compute() {
		T result = supplier.get();
		reference = new SoftReference<>(result)::get;
		return result;
	}

	@Override
	public T get() {
		T result = reference.get();
		if (result == null) {
			result = compute();
		}
		return result;
	}

	/**
	 * Queries whether a cached value exists. This may be
	 * used as a guard before accessing the value, which
	 * could lazily create it. But it is not a guarantee:
	 * the value should be checked that it is not {@code null}
	 * even when a previous call to this method returned
	 * {@code true}.
	 * 
	 * @return whether a cached reference exists at this instant
	 */
	public boolean exists() {
		return (reference != null) && (reference.get() != null);
	}
}
