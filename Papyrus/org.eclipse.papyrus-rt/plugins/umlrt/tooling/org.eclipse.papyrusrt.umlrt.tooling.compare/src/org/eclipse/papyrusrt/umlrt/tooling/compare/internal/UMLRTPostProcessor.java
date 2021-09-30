/*****************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Services GmbH 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterators.filter;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.CAPSULE_DIAGRAM_MATCH;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.internal.postprocessor.factories.IChangeFactory;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;

import com.google.common.collect.Collections2;




@SuppressWarnings("restriction")
public class UMLRTPostProcessor implements IPostProcessor {

	private Map<Class<? extends Diff>, IChangeFactory> umlRtExtensionFactoriesMap;

	@Override
	public void postComparison(Comparison comparison, Monitor monitor) {
		applyUMLRTDiffExtensions(comparison);
		addRequirementsBetweenDiagramDiffsAndModelDiffs(comparison);
	}

	private void applyUMLRTDiffExtensions(Comparison comparison) {
		umlRtExtensionFactoriesMap = createExtensionFactories();
		createUMLRTDiffExtensions(comparison);
		fillRequiredDifferences(comparison);
	}

	private Map<Class<? extends Diff>, IChangeFactory> createExtensionFactories() {
		return UMLRTExtensionFactoryRegistry.createExtensionFactories();
	}

	private void createUMLRTDiffExtensions(Comparison comparison) {
		for (Diff diff : comparison.getDifferences()) {
			createUMLRTDiffExtension(diff);
		}
	}

	private void createUMLRTDiffExtension(Diff element) {
		for (IChangeFactory factory : umlRtExtensionFactoriesMap.values()) {
			if (factory.handles(element)) {
				final Diff extension = factory.create(element);
				if (!extension.getRefinedBy().isEmpty()) {
					final Match match = factory.getParentMatch(element);
					if (match != null) {
						match.getDifferences().add(extension);
					}
				}
			}
		}
	}

	private void fillRequiredDifferences(Comparison comparison) {
		for (Diff umlDiff : comparison.getDifferences()) {
			if (umlDiff instanceof UMLDiff) {
				final Class<?> classDiffElement = umlDiff.eClass().getInstanceClass();
				final IChangeFactory diffFactory = umlRtExtensionFactoriesMap.get(classDiffElement);
				if (diffFactory != null) {
					diffFactory.fillRequiredDifferences(comparison, umlDiff);
				}
			}
		}
	}

	/**
	 * Adds {@link Diff#getRequires() requirement relationships} from UMLRT semantic model differences
	 * to their corresponding diagram diffs.
	 * 
	 * <p>
	 * In the Papyrus-RT tooling, the diagrams, such as capsule diagrams, are typically
	 * &quot;synchronized&quot;. Thus, changing the model will also change the diagrams accordingly.
	 * To account for this synchronization, we have to ensure that also UMLRT diffs and their
	 * corresponding diagram changes are merged together, e.g., in interactive merging. Therefore,
	 * we add the respective diagram diffs to the requirements of the corresponding UMLRT diffs.
	 * </p>
	 * <p>
	 * Note that this method must be executed after {@link #createUMLRTDiffExtensions(Comparison)}
	 * and {@link #fillRequiredDifferences(Comparison)} to make sure all the UMLRT diffs have already
	 * been added.
	 * </p>
	 * 
	 * @param comparison
	 *            The comparison in which the requirements shall be added.
	 */
	private void addRequirementsBetweenDiagramDiffsAndModelDiffs(Comparison comparison) {
		// We only have capsule diagram support for now, so we can limit the search
		// In the future we might want to integrate this into the creation of the UMLRTDiagramDiffs
		for (Match capsuleDiagramMatch : Collections2.filter(comparison.getMatches(), CAPSULE_DIAGRAM_MATCH)) {
			final Iterator<UMLRTDiagramChange> diagramChangeIterator = filter(capsuleDiagramMatch.eAllContents(), UMLRTDiagramChange.class);
			while (diagramChangeIterator.hasNext()) {
				final UMLRTDiagramChange umlrtDiagramChange = diagramChangeIterator.next();
				if (umlrtDiagramChange.getSemanticDiff() != null) {
					// Originally we wanted to add a bi-directional requirement between umlrtDiagramChange and
					// umlrtDiagramChange.getSemanticDiff(). However, we run into dependency cycles that eventually
					// break the diagram (see bug 515386).
					// As a workaround we add a bi-directional dependency between umlrtDiagramChange and the diff that
					// refines umlrtDiagramChange.getSemanticDiff(), unless there is no refined diff.
					if (!umlrtDiagramChange.getSemanticDiff().getRefines().isEmpty()) {
						addBidirectionalRequirement(umlrtDiagramChange, umlrtDiagramChange.getSemanticDiff().getRefines().get(0));
					} else {
						addBidirectionalRequirement(umlrtDiagramChange, umlrtDiagramChange.getSemanticDiff());
					}
				}
			}
		}
	}

	private void addBidirectionalRequirement(Diff diff1, Diff diff2) {
		diff1.getRequires().add(diff2);
		diff1.getRequiredBy().add(diff2);
	}

	@Override
	public void postConflicts(Comparison comparison, Monitor monitor) {
		// nothing to do
	}

	@Override
	public void postMatch(Comparison comparison, Monitor monitor) {
		// nothing to do
	}

	@Override
	public void postDiff(Comparison comparison, Monitor monitor) {
		// nothing to do
	}

	@Override
	public void postRequirements(Comparison comparison, Monitor monitor) {
		// nothing to do
	}

	@Override
	public void postEquivalences(Comparison comparison, Monitor monitor) {
		// nothing to do
	}
}
