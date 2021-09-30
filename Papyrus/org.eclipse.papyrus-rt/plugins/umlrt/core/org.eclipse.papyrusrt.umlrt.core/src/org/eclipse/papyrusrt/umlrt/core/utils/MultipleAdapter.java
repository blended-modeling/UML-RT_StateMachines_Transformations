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

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * An adapter that may be attached to multiple objects, tracks them,
 * and can remove itself from all of them at once.
 */
public class MultipleAdapter extends AdapterImpl {
	private final List<Notifier> targets;

	/**
	 * Initializes me with the expectation of tracking one notifier
	 * (which actually makes room for more than one).
	 */
	public MultipleAdapter() {
		this(1); // Be conservative
	}

	/**
	 * Initializes me with the expectation of track a given number of notifiers.
	 *
	 * @param expectedTargetCount
	 *            the expected number of notifiers to be tracked
	 */
	public MultipleAdapter(int expectedTargetCount) {
		super();

		this.targets = Lists.newArrayListWithExpectedSize(expectedTargetCount);
	}

	/**
	 * Removes me from all of the {@link #getNotifiers() notifiers} to which
	 * I am currently attached.
	 */
	public void dispose() {
		if (!targets.isEmpty()) {
			Notifier[] iterationSafeCopy = Iterables.toArray(targets, Notifier.class);
			for (int i = 0; i < iterationSafeCopy.length; i++) {
				iterationSafeCopy[i].eAdapters().remove(this);
			}
		}
	}

	/**
	 * Obtains an unmodifiable view of the notifiers to which I am
	 * currently attached.
	 * 
	 * @return my targets
	 * 
	 * @see #getTarget()
	 */
	public List<Notifier> getTargets() {
		return Collections.unmodifiableList(targets);
	}

	@Override
	public void setTarget(Notifier newTarget) {
		targets.add(newTarget);
	}

	@Override
	public void unsetTarget(Notifier oldTarget) {
		targets.remove(oldTarget);
	}

	/**
	 * Queries some notifier to which I am attached.
	 * 
	 * @return any of the {@link #getTargets() targets} to which
	 *         I am currently attached
	 * 
	 * @see #getTargets()
	 */
	@Override
	public Notifier getTarget() {
		return targets.isEmpty() ? null : targets.get(0);
	}
}
