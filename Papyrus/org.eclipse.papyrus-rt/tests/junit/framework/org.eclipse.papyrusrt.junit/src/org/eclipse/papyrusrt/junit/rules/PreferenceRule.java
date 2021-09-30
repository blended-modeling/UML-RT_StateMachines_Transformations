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

package org.eclipse.papyrusrt.junit.rules;

import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Defaults;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;

/**
 * A test rule that ensures a preference setting required by the test(s) in
 * its scope, restoring the previous value on completion.
 */
public class PreferenceRule<T> extends TestWatcher {

	private static final Map<Class<?>, PrefsAccessor<?>> ACCESSORS;
	private static final Map<Class<?>, PrefsMutator<?>> MUTATORS;

	private final String qualifier;
	private final String key;
	private final T value;
	private Optional<T> prior;

	static {
		ACCESSORS = ImmutableMap.<Class<?>, PrefsAccessor<?>> builder()
				.put(String.class, (PrefsAccessor<String>) Preferences::get)
				.put(Boolean.class, (PrefsAccessor<Boolean>) Preferences::getBoolean)
				.put(Integer.class, (PrefsAccessor<Integer>) Preferences::getInt)
				.put(Long.class, (PrefsAccessor<Long>) Preferences::getLong)
				.put(Float.class, (PrefsAccessor<Float>) Preferences::getFloat)
				.put(Double.class, (PrefsAccessor<Double>) Preferences::getDouble)
				.put(byte[].class, (PrefsAccessor<byte[]>) Preferences::getByteArray)
				.build();

		MUTATORS = ImmutableMap.<Class<?>, PrefsMutator<?>> builder()
				.put(String.class, (PrefsMutator<String>) Preferences::put)
				.put(Boolean.class, (PrefsMutator<Boolean>) Preferences::putBoolean)
				.put(Integer.class, (PrefsMutator<Integer>) Preferences::putInt)
				.put(Long.class, (PrefsMutator<Long>) Preferences::putLong)
				.put(Float.class, (PrefsMutator<Float>) Preferences::putFloat)
				.put(Double.class, (PrefsMutator<Double>) Preferences::putDouble)
				.put(byte[].class, (PrefsMutator<byte[]>) Preferences::putByteArray)
				.build();
	}

	private PreferenceRule(String qualifier, String key, T value) {
		super();

		if (!ACCESSORS.containsKey(value.getClass())) {
			throw new IllegalArgumentException("No support for preferences of type " + value.getClass());
		}

		this.qualifier = qualifier;
		this.key = key;
		this.value = value;
	}

	/**
	 * Obtains a rule for the given preference setting.
	 * 
	 * @param qualifier
	 *            the preference qualifier (usually bundle ID)
	 * @param key
	 *            the preference key
	 * @param value
	 *            the value to set (must not be {@code null})
	 * 
	 * @return the preference rule
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code value} is of a type not supported
	 *             by the preferences framework
	 */
	public static <T> PreferenceRule<T> getInstance(String qualifier, String key, T value) {
		if (value == null) {
			throw new NullPointerException("value");
		}
		return new PreferenceRule<>(qualifier, key, value);
	}

	/**
	 * Obtains a preference rule for the Papyrus "snap to grid for new diagrams" preference.
	 * 
	 * @param value
	 *            whether new diagrams should snap to grid
	 * 
	 * @return the preference rule
	 */
	public static PreferenceRule<Boolean> getSnapToGrid(boolean value) {
		return getInstance("org.eclipse.papyrus.infra.gmfdiag.preferences",
				"PAPYRUS_EDITOR.rulergrid.snaptogrid",
				value);
	}

	@Override
	protected void starting(Description description) {
		prior = Optional.ofNullable(get(InstanceScope.INSTANCE));
		set(InstanceScope.INSTANCE, value);
	}

	@Override
	protected void finished(Description description) {
		prior.map(value -> set(InstanceScope.INSTANCE, value))
				.orElseGet(() -> remove(InstanceScope.INSTANCE));
	}

	@SuppressWarnings("unchecked")
	private T get(IScopeContext scope) {
		Preferences node = scope.getNode(qualifier);

		// Need to test via a non-primitive type to detect absence of the key (null value)
		if (node.get(key, null) == null) {
			return null;
		}

		Class<T> type = (Class<T>) value.getClass();
		PrefsAccessor<T> accessor = (PrefsAccessor<T>) ACCESSORS.get(type);
		return accessor.get(node, key,
				type.cast(Defaults.defaultValue(Primitives.unwrap(value.getClass()))));
	}

	private T set(IScopeContext scope, T value) {
		@SuppressWarnings("unchecked")
		PrefsMutator<T> mutator = (PrefsMutator<T>) MUTATORS.get(value.getClass());
		mutator.set(scope.getNode(qualifier), key, value);
		return value;
	}

	private T remove(IScopeContext scope) {
		scope.getNode(qualifier).remove(key);
		return null;
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private static interface PrefsAccessor<V> {
		V get(Preferences node, String key, V defaultValue);
	}

	@FunctionalInterface
	private static interface PrefsMutator<V> {
		void set(Preferences node, String key, V value);
	}
}
