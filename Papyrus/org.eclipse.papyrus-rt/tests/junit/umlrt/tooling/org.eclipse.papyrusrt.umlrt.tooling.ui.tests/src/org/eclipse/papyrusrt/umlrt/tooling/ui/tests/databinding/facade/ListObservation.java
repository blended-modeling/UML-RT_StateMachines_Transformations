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

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.databinding.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * Observation of changes in an observable list that tracks and optionally
 * asserts the actual changes.
 */
class ListObservation implements IListChangeListener<UMLRTNamedElement> {
	private final List<UMLRTNamedElement> added = new ArrayList<>();
	private final List<UMLRTNamedElement> removed = new ArrayList<>();

	private final Constraint constraint;

	private ListObservation(Constraint constraint) {
		super();

		this.constraint = (constraint == null) ? Constraint.NONE : constraint;
	}

	static ListObservation create() {
		return new ListObservation(Constraint.NONE);
	}

	static ListObservation createAdditionsOnly() {
		return new ListObservation(Constraint.ADDITIONS_ONLY);
	}

	static ListObservation createRemovalsOnly() {
		return new ListObservation(Constraint.REMOVALS_ONLY);
	}

	@Override
	public void handleListChange(ListChangeEvent<? extends UMLRTNamedElement> event) {
		Stream.of(event.diff.getDifferences()).forEach(diff -> {
			constraint.verify(diff);

			if (diff.isAddition()) {
				added.add(diff.getElement());
			} else {
				removed.add(diff.getElement());
			}
		});
	}

	public List<UMLRTNamedElement> getAdded() {
		return Collections.unmodifiableList(added);
	}

	public List<UMLRTNamedElement> getRemoved() {
		return Collections.unmodifiableList(removed);
	}

	public void reset() {
		added.clear();
		removed.clear();
	}

	//
	// Nested types
	//

	private enum Constraint {
		NONE, ADDITIONS_ONLY, REMOVALS_ONLY;

		void verify(ListDiffEntry<?> diff) {
			switch (this) {
			case ADDITIONS_ONLY:
				assertThat("Should not get a removal", diff.isAddition(), is(true));
				break;
			case REMOVALS_ONLY:
				assertThat("Should not get an addition", diff.isAddition(), is(false));
				break;
			default:
				// Pass
				break;
			}
		}
	}
}
