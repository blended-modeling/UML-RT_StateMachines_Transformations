/**
 * Copyright (c) 2012, 2017 Obeo and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Stefan Dirix - update priority value for UML merger
 *     Philip Langer - add mergeLeftToRight and mergeRightToLeft
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Iterators.all;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.hasConflict;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.EMFCompare.Builder;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.diagram.internal.merge.CompareDiagramMerger;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.postprocessor.PostProcessorDescriptorRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.uml2.internal.StereotypedElementChange;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.compare.uml2.internal.merge.OpaqueElementBodyChangeMerger;
import org.eclipse.emf.compare.uml2.internal.merge.UMLMerger;
import org.eclipse.emf.compare.uml2.internal.merge.UMLReferenceChangeMerger;
import org.eclipse.emf.compare.uml2.internal.postprocessor.OpaqueElementBodyChangePostProcessor;
import org.eclipse.emf.compare.uml2.internal.postprocessor.UMLPostProcessor;
import org.eclipse.emf.compare.utils.EMFComparePredicates;
import org.eclipse.emf.compare.utils.ReferenceUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.umlrt.profile.util.UMLRTResource;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.merger.UMLRTDiagramChangeMerger;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Abstract base class for UML-RT tests.
 * <p>
 * This abstract test is based on org.eclipse.emf.compare.uml2.tests.AbstractUMLTest
 * and adapted for the use of Papyrus-RT.
 * </p>
 */
@SuppressWarnings({ "nls", "restriction" })
public abstract class AbstractUMLRTTest {

	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	protected static final Monitor MONITOR = new BasicMonitor();

	protected EMFCompare emfCompare;

	private IMerger.Registry mergerRegistry;

	@Before
	public void before() {
		Builder builder = EMFCompare.builder();
		// post-processor and merger registry is not filled in runtime
		// (org.eclipse.emf.compare.rcp not
		// loaded)
		final IPostProcessor.Descriptor.Registry<String> postProcessorRegistry = new PostProcessorDescriptorRegistryImpl<>();
		registerPostProcessors(postProcessorRegistry);
		builder.setPostProcessorRegistry(postProcessorRegistry);
		mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
		final IMerger umlMerger = new UMLMerger();
		final IMerger umlReferenceChangeMerger = new UMLReferenceChangeMerger();
		final IMerger opaqueElementBodyChangeMerger = new OpaqueElementBodyChangeMerger();
		final IMerger diagramMerger = new CompareDiagramMerger();
		final IMerger umlrtDiagramChangeMerger = new UMLRTDiagramChangeMerger();
		umlMerger.setRanking(20);
		umlReferenceChangeMerger.setRanking(25);
		opaqueElementBodyChangeMerger.setRanking(25);
		diagramMerger.setRanking(20);
		umlrtDiagramChangeMerger.setRanking(22);
		mergerRegistry.add(umlMerger);
		mergerRegistry.add(umlReferenceChangeMerger);
		mergerRegistry.add(opaqueElementBodyChangeMerger);
		mergerRegistry.add(diagramMerger);
		mergerRegistry.add(umlrtDiagramChangeMerger);
		emfCompare = builder.build();
	}

	/**
	 * Used to register new post processors.
	 * 
	 * @param postProcessorRegistry
	 */
	protected void registerPostProcessors(final IPostProcessor.Descriptor.Registry<String> postProcessorRegistry) {
		postProcessorRegistry.put(UMLPostProcessor.class.getName(), new TestPostProcessor.TestPostProcessorDescriptor(
				Pattern.compile("http://www.eclipse.org/uml2/\\d\\.0\\.0/UML"), null, new UMLPostProcessor(), 20));
		postProcessorRegistry.put(
				OpaqueElementBodyChangePostProcessor.class.getName(),
				new TestPostProcessor.TestPostProcessorDescriptor(Pattern
						.compile("http://www.eclipse.org/uml2/\\d\\.0\\.0/UML"), null,
						new OpaqueElementBodyChangePostProcessor(), 25));
		// UMLRTPostProcessor
		postProcessorRegistry.put(UMLRTPostProcessor.class.getName(),
				new TestPostProcessor.TestPostProcessorDescriptor(Pattern.compile(UMLRTResource.PROFILE_URI), null,
						new UMLRTPostProcessor(), 25));
	}

	@After
	public void cleanup() {
		for (ResourceSet set : getInput().getSets()) {
			for (Resource res : set.getResources()) {
				res.unload();
			}
			set.getResources().clear();
		}
		getInput().getSets().clear();

	}

	protected EMFCompare getCompare() {
		return emfCompare;
	}

	protected Comparison compare(Notifier left, Notifier right) {
		return compare(left, right, null);
	}

	protected Comparison compare(Notifier left, Notifier right, Notifier origin) {
		IComparisonScope scope = new DefaultComparisonScope(left, right, origin);
		return getCompare().compare(scope);
	}

	protected IMerger.Registry getMergerRegistry() {
		return mergerRegistry;
	}

	protected enum TestKind {
		ADD, DELETE;
	}

	protected static int count(List<Diff> differences, Predicate<Object> p) {
		int count = 0;
		final Iterator<Diff> result = Iterators.filter(differences.iterator(), p);
		while (result.hasNext()) {
			count++;
			result.next();
		}
		return count;
	}

	public static Predicate<? super Diff> onRealFeature(final EStructuralFeature feature) {
		return new Predicate<Diff>() {
			@Override
			public boolean apply(Diff input) {
				final EStructuralFeature affectedFeature;
				if (input instanceof AttributeChange) {
					affectedFeature = ((AttributeChange) input).getAttribute();
				} else if (input instanceof ReferenceChange) {
					affectedFeature = ((ReferenceChange) input).getReference();
				} else {
					return false;
				}
				return feature == affectedFeature;
			}
		};
	}

	public static Predicate<? super Diff> isChangeAdd() {
		return new Predicate<Diff>() {
			@Override
			public boolean apply(Diff input) {
				if (input instanceof ReferenceChange) {
					return ReferenceUtil
							.getAsList(input.getMatch().getLeft(), ((ReferenceChange) input).getReference()).contains(
									((ReferenceChange) input).getValue());
				} else if (input instanceof AttributeChange) {
					return ReferenceUtil
							.getAsList(input.getMatch().getLeft(), ((AttributeChange) input).getAttribute()).contains(
									((AttributeChange) input).getValue());
				}
				return false;
			}
		};
	}

	protected static Predicate<Diff> discriminantInstanceOf(final EClass clazz) {
		return new Predicate<Diff>() {
			@Override
			public boolean apply(Diff input) {
				return input instanceof UMLDiff && clazz.isInstance(((UMLDiff) input).getDiscriminant());
			}

		};
	}

	protected abstract AbstractUMLRTInputData getInput();

	protected void testMergeLeftToRight(Notifier left, Notifier right, Notifier origin) {
		testMergeLeftToRight(left, right, origin, false);
	}

	protected void testMergeRightToLeft(Notifier left, Notifier right, Notifier origin) {
		testMergeRightToLeft(left, right, origin, false);
	}

	protected void testMergeLeftToRight(Notifier left, Notifier right, Notifier origin, boolean pseudoAllowed) {
		final IComparisonScope scope = new DefaultComparisonScope(left, right, origin);
		final Comparison comparisonBefore = getCompare().compare(scope);
		EList<Diff> differencesBefore = comparisonBefore.getDifferences();
		final IBatchMerger merger = new BatchMerger(mergerRegistry);
		merger.copyAllLeftToRight(differencesBefore, new BasicMonitor());
		final Comparison comparisonAfter = getCompare().compare(scope);
		EList<Diff> differencesAfter = comparisonAfter.getDifferences();
		final boolean diffs;
		if (pseudoAllowed) {
			diffs = all(differencesAfter.iterator(), hasConflict(ConflictKind.PSEUDO));
		} else {
			diffs = differencesAfter.isEmpty();
		}
		assertTrue("Comparison#getDifferences() must be empty after copyAllLeftToRight", diffs);
	}

	protected void testMergeRightToLeft(Notifier left, Notifier right, Notifier origin, boolean pseudoAllowed) {
		final IComparisonScope scope = new DefaultComparisonScope(left, right, origin);
		final Comparison comparisonBefore = getCompare().compare(scope);
		EList<Diff> differencesBefore = comparisonBefore.getDifferences();
		final IBatchMerger merger = new BatchMerger(mergerRegistry);
		merger.copyAllRightToLeft(differencesBefore, new BasicMonitor());
		final Comparison comparisonAfter = getCompare().compare(scope);
		EList<Diff> differencesAfter = comparisonAfter.getDifferences();
		final boolean diffs;
		if (pseudoAllowed) {
			diffs = all(differencesAfter.iterator(), hasConflict(ConflictKind.PSEUDO));
		} else {
			diffs = differencesAfter.isEmpty();
		}
		assertTrue("Comparison#getDifferences() must be empty after copyAllRightToLeft", diffs);
	}

	protected void testIntersections(Comparison comparison) {
		for (Diff diff : comparison.getDifferences()) {
			int realRefinesSize = Iterables.size(Iterables.filter(diff.getRefines(),
					not(instanceOf(StereotypedElementChange.class))));
			assertFalse("Wrong number of refines (without StereotypedElementChange) on" + diff, realRefinesSize > 1);
			int stereotypedElementChangeRefines = Iterables.size(Iterables.filter(diff.getRefines(),
					instanceOf(StereotypedElementChange.class)));
			assertFalse("Wrong number of refines (of type StereotypedElementChange) on " + diff,
					stereotypedElementChangeRefines > 1);

		}
	}

	protected static Conflict getConflictOrConflictOfRefining(Diff input, ConflictKind kind) {
		Conflict conflict = null;
		if (input.getConflict() != null) {
			conflict = input.getConflict();
		} else {
			Optional<Diff> conflictingRefiningDiff = tryFind(input.getRefinedBy(), EMFComparePredicates.hasConflict(kind));
			if (conflictingRefiningDiff.isPresent()) {
				conflict = conflictingRefiningDiff.get().getConflict();
			}
		}
		return conflict;
	}

	protected static boolean containsDiffOrAnyOfItsRefiningDiffs(Diff diff, Conflict conflict) {
		return conflict.getDifferences().contains(diff)
				|| containsAny(conflict.getDifferences(), diff.getRefinedBy());
	}

	protected static boolean containsAny(EList<Diff> diffs, EList<Diff> toBeContained) {
		for (Diff currentDiff : toBeContained) {
			if (Iterables.contains(diffs, currentDiff)) {
				return true;
			}
		}
		return false;
	}

	protected static final Predicate<Conflict> REFINED_CONFLICTS_PREDICATE = new Predicate<Conflict>() {
		@Override
		public boolean apply(Conflict input) {
			return Iterables.any(input.getDifferences(), REFINED_DIFFS_PREDICATE);
		}
	};

	protected static final Predicate<Diff> REFINED_DIFFS_PREDICATE = new Predicate<Diff>() {
		@Override
		public boolean apply(Diff input) {
			return !Iterables.any(input.getRefines(), instanceOf(UMLDiff.class));
		}
	};

	protected List<Conflict> getVisibleConflicts(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getConflicts(), REFINED_CONFLICTS_PREDICATE));
	}

	protected void mergeRightToLeft(Diff... diff) {
		new BatchMerger(getMergerRegistry()).copyAllRightToLeft(Arrays.asList(diff), MONITOR);
	}

	protected void mergeLeftToRight(Diff... diff) {
		new BatchMerger(getMergerRegistry()).copyAllLeftToRight(Arrays.asList(diff), MONITOR);
	}
}
