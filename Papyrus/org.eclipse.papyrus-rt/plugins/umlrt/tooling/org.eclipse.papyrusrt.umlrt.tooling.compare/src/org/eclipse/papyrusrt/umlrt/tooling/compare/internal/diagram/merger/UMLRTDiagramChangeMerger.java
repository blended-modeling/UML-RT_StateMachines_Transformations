/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Philip Langer - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.merger;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.filter;
import static org.eclipse.emf.compare.DifferenceState.UNRESOLVED;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.hasState;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.AUTO_DIAGRAM_CHANGE;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.diagram.internal.merge.CompareDiagramMerger;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

/**
 * Custom instance of {@link CompareDiagramMerger} for {@link UMLRTDiagramChange} differences.
 * <p>
 * In addition to the behavior of its super class, this merger only merges the remaining
 * automatic diagram configuration diffs that are not required by any other UMLRTDiagramChange
 * anymore.
 * </p>
 * 
 * @author Philip Langer
 */
@SuppressWarnings("restriction")
public class UMLRTDiagramChangeMerger extends CompareDiagramMerger {

	@Override
	public boolean isMergerFor(Diff target) {
		return target instanceof UMLRTDiagramChange;
	}


	/**
	 * {@inheritDoc}
	 * <p>
	 * This method also adds the required automatic diagram changes, if the given <code>diff</code> shall be rejected and
	 * is the last {@link UMLRTDiagramChange} that requires them. This way, we clean up the automatically created diagram
	 * changes.
	 * </p>
	 */
	@Override
	public Set<Diff> getDirectMergeDependencies(Diff diff, boolean mergeRightToLeft) {
		final Set<Diff> dependencies = super.getDirectMergeDependencies(diff, mergeRightToLeft);
		dependencies.addAll(addRequiredAutoDiagramChanges(diff, mergeRightToLeft));
		return dependencies;
	}


	/**
	 * Returns the merge dependencies
	 * 
	 * @param dependencies
	 * @param change
	 */
	@SuppressWarnings("unchecked")
	private Set<Diff> addRequiredAutoDiagramChanges(Diff diff, boolean mergeRightToLeft) {
		final Set<Diff> requiredAutoDiagramChanges = new LinkedHashSet<>();
		if (diff instanceof UMLRTDiagramChange && !isAccepting(diff, mergeRightToLeft)) {
			final UMLRTDiagramChange change = (UMLRTDiagramChange) diff;
			for (Diff autoDiagramChange : filter(change.getRequires(), AUTO_DIAGRAM_CHANGE)) {
				if (!any(autoDiagramChange.getRequiredBy(), and(
						hasState(UNRESOLVED), not(equalTo(change)), instanceOf(UMLRTDiagramChange.class)))) {
					requiredAutoDiagramChanges.add(autoDiagramChange);
				}
			}
		}
		return requiredAutoDiagramChanges;
	}

}
