/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import static com.google.common.collect.Iterables.filter;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.getChangedValue;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.markAsAutoDiagramChanges;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diagram.internal.factories.AbstractDiagramChangeFactory;
import org.eclipse.emf.compare.internal.utils.ComparisonUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

/**
 * Factory for {@link UMLRTDiagramChange} extensions.
 * 
 * @author Alexandra Buzila
 */
@SuppressWarnings("restriction")
public abstract class AbstractUMLRTDiagramChangeFactory extends AbstractDiagramChangeFactory {

	/**
	 * Returns the set of diagram Shapes affected by the given difference.
	 * 
	 * @param diff
	 *            the difference
	 * @return the set of shapes
	 */
	public Set<EObject> getAffectedShapes(Diff diff) {
		final DiagramChangesHandler handler = DiagramChangesHandlerService.getService().getHandlerForDiff(diff);
		if (handler != null) {
			return handler.getShapes(diff);
		}
		return Collections.emptySet();
	}

	@Override
	public Diff create(Diff input) {
		final Diff ret = super.create(input);
		setRequiredAutoCreatedDiagramChanges(ret, input);
		return ret;
	}

	@Override
	public void setRefiningChanges(Diff extension, DifferenceKind extensionKind, Diff refiningDiff) {
		final Comparison comparison = ComparisonUtil.getComparison(refiningDiff);
		final Set<EObject> affectedShapes = getAffectedShapes(refiningDiff);

		final Builder<Diff> refiningChanges = ImmutableSet.builder();
		refiningChanges.add(refiningDiff);

		for (EObject shape : affectedShapes) {
			Match match = comparison.getMatch(shape);
			refiningChanges.addAll(filter(match.getAllDifferences(), fromSide(extension.getSource())));
		}

		refiningChanges.addAll(Collections2.filter(comparison.getDifferences(), isChangeOfAffectedShapes(affectedShapes)));

		extension.getRefinedBy().addAll(refiningChanges.build());
	}

	/**
	 * Mark the differences created automatically by the given change and set them as required by the extension diff.
	 * 
	 * @param extension
	 *            The extension to set.
	 * 
	 * @param diff
	 *            The refining difference.
	 */
	public void setRequiredAutoCreatedDiagramChanges(Diff extension, Diff diff) {
		final DiagramChangesHandler handler = DiagramChangesHandlerService.getService().getHandlerForDiff(diff);
		if (handler == null) {
			return;
		}

		final Set<EObject> shapesOfImpliedDiffs = handler.getAutomaticallyCreatedShapes(diff);
		if (shapesOfImpliedDiffs == null || shapesOfImpliedDiffs.isEmpty()) {
			return;
		}

		final Builder<Diff> impliedDiffs = ImmutableSet.builder();
		final Comparison comparison = diff.getMatch().getComparison();
		for (EObject shape : shapesOfImpliedDiffs) {
			Match match = comparison.getMatch(shape);
			impliedDiffs.addAll(filter(match.getAllDifferences(), fromSide(extension.getSource())));
		}

		impliedDiffs.addAll(Collections2.filter(comparison.getDifferences(), isChangeOfAffectedShapes(shapesOfImpliedDiffs)));

		final ImmutableSet<Diff> autoCreatedDiagramChanges = impliedDiffs.build();
		markAsAutoDiagramChanges(autoCreatedDiagramChanges);
		extension.getRequires().addAll(autoCreatedDiagramChanges);
	}

	/**
	 * Predicate used to determine whether a difference affects any EObject from the given set.
	 * 
	 * @param affectedShapes
	 *            the set of {@link EObject}s to check against
	 * 
	 * @return the predicate
	 */
	public static Predicate<? super Diff> isChangeOfAffectedShapes(Set<EObject> affectedShapes) {
		return new Predicate<Diff>() {
			@Override
			public boolean apply(Diff input) {
				return affectedShapes.contains(getChangedValue(input));
			}
		};
	}

}
