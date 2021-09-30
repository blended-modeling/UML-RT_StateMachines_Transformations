/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterables.find;
import static org.eclipse.emf.compare.DifferenceState.DISCARDED;
import static org.eclipse.emf.compare.DifferenceState.MERGED;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.addedToReference;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.removedFromReference;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTDiagramChangeTest.getCapsuleChange;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTDiagramChangeTest.getCapsulePartChange;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTDiagramChangeTest.getPortChange;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTDiagramChangeTest.getUMLRTDiagramChanges;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.diagram.internal.CompareDiagramPostProcessor;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Tests diagram synchronization while merging model changes.
 * <p>
 * The Papyrus-RT tooling automatically synchronizes the diagram when performing semantic model changes,
 * such as adding a capsule part or port, if the diagram is configured to be &quot;synchronized&quot;.
 * Therefore, merging such semantic model changes should also merge the corresponding diagram changes.
 * </p>
 * <p>
 * This test will therefore ensure that merging semantic model UMLRT changes also leads to merging
 * the corresponding diagram changes.
 * </p>
 * <p>
 * This test re-uses the test models of {@link UMLRTDiagramChangeTest}.
 * </p>
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
@SuppressWarnings("restriction")
public class UMLRTDiagramSynchronizationTest extends AbstractUMLRTTest {

	private static final String MODEL_ROOT = "RootElement";
	private static final String CAPSULE1 = MODEL_ROOT + "." + "Capsule1";
	private static final String PORT = CAPSULE1 + "." + "protocol1";
	private static final String PART = CAPSULE1 + "." + "capsule2";
	private static final String PACKAGED_ELEMENTS_FEATURE = UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT.getName();
	private static final String OWNED_ATTRIBUTE_FEATURE = UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE.getName();

	private static final UMLRTDiagramSynchronizationTestData input = new UMLRTDiagramSynchronizationTestData();

	@Override
	protected AbstractUMLRTInputData getInput() {
		return input;
	}

	@Override
	protected void registerPostProcessors(final IPostProcessor.Descriptor.Registry<String> postProcessorRegistry) {
		super.registerPostProcessors(postProcessorRegistry);
		postProcessorRegistry.put(UMLRTPostProcessor.class.getName(),
				new TestPostProcessor.TestPostProcessorDescriptor(Pattern.compile("http://www.eclipse.org/gmf/runtime/\\d.\\d.\\d/notation"), null,
						new UMLRTPostProcessor(), 25));

		postProcessorRegistry.put(CompareDiagramPostProcessor.class.getName(),
				new TestPostProcessor.TestPostProcessorDescriptor(
						Pattern.compile("http://www.eclipse.org/gmf/runtime/\\d.\\d.\\d/notation"), null,
						new CompareDiagramPostProcessor(), 30));

	}

	/**
	 * Tests whether accepting an addition of a capsule will also merge the addition of its shape.
	 */
	@Test
	public void testMergeAddCapsuleLtR() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getEmptyDiagramModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange capsuleDiagramChange = getCapsuleChange(
				getUMLRTDiagramChanges(comparison), name(CAPSULE1));

		final Diff capsuleAddDiff = find(comparison.getDifferences(),
				addedToReference(MODEL_ROOT, PACKAGED_ELEMENTS_FEATURE, CAPSULE1));

		mergeLeftToRight(capsuleAddDiff);

		assertEquals(MERGED, capsuleDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting an addition of a capsule will also reject the addition of its shape.
	 */
	@Test
	public void testMergeAddCapsuleRtL() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getEmptyDiagramModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange capsuleDiagramChange = getCapsuleChange(
				getUMLRTDiagramChanges(comparison), name(CAPSULE1));

		final Diff capsuleAddDiff = find(comparison.getDifferences(),
				addedToReference(MODEL_ROOT, PACKAGED_ELEMENTS_FEATURE, CAPSULE1));

		mergeRightToLeft(capsuleAddDiff);

		assertEquals(DISCARDED, capsuleDiagramChange.getState());
	}

	/**
	 * Tests whether accepting a deletion of a capsule will also merge the deletion of its shape.
	 */
	@Test
	public void testMergeDeleteCapsuleLtR() throws IOException {
		final ResourceSet left = input.getEmptyDiagramModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange capsuleDiagramChange = getCapsuleChange(
				getUMLRTDiagramChanges(comparison), name(CAPSULE1));

		final Diff capsuleDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(MODEL_ROOT, PACKAGED_ELEMENTS_FEATURE, CAPSULE1));

		mergeLeftToRight(capsuleDeleteDiff);

		assertEquals(MERGED, capsuleDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting a deletion of a capsule will also reject the deletion of its shape.
	 */
	@Test
	public void testMergeDeleteCapsuleRtL() throws IOException {
		final ResourceSet left = input.getEmptyDiagramModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange capsuleDiagramChange = getCapsuleChange(
				getUMLRTDiagramChanges(comparison), name(CAPSULE1));

		final Diff capsuleDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(MODEL_ROOT, PACKAGED_ELEMENTS_FEATURE, CAPSULE1));

		mergeRightToLeft(capsuleDeleteDiff);

		assertEquals(DISCARDED, capsuleDiagramChange.getState());
	}

	/**
	 * Tests whether accepting an addition of a port will also merge the addition of its shape.
	 */
	@Test
	public void testMergeAddPortLtR() throws IOException {
		final ResourceSet left = input.getAddPortToCapsuleModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange portDiagramChange = getPortChange(
				getUMLRTDiagramChanges(comparison), name(PORT), name(CAPSULE1));

		final Diff portAddDiff = find(comparison.getDifferences(),
				addedToReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PORT));

		mergeLeftToRight(portAddDiff);

		assertEquals(MERGED, portDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting an addition of a port will also reject the addition of its shape.
	 */
	@Test
	public void testMergeAddPortRtL() throws IOException {
		final ResourceSet left = input.getAddPortToCapsuleModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange portDiagramChange = getPortChange(
				getUMLRTDiagramChanges(comparison), name(PORT), name(CAPSULE1));

		final Diff portAddDiff = find(comparison.getDifferences(),
				addedToReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PORT));

		mergeRightToLeft(portAddDiff);

		assertEquals(DISCARDED, portDiagramChange.getState());
	}

	/**
	 * Tests whether accepting a deletion of a port will also merge the deletion of its shape.
	 */
	@Test
	public void testMergeDeletePortLtR() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getAddPortToCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange portDiagramChange = getPortChange(
				getUMLRTDiagramChanges(comparison), name(PORT), name(CAPSULE1));

		final Diff portDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PORT));

		mergeLeftToRight(portDeleteDiff);

		assertEquals(MERGED, portDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting a deletion of a port will also reject the deletion of its shape.
	 */
	@Test
	public void testMergeDeletePortRtL() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getAddPortToCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange portDiagramChange = getPortChange(
				getUMLRTDiagramChanges(comparison), name(PORT), name(CAPSULE1));

		final Diff portDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PORT));

		mergeRightToLeft(portDeleteDiff);

		assertEquals(DISCARDED, portDiagramChange.getState());
	}

	/**
	 * Tests whether accepting an addition of a capsule part will also merge the addition of its shape.
	 */
	@Test
	public void testMergeAddCapsulePartLtR() throws IOException {
		final ResourceSet left = input.getAddCapsulePartModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange partDiagramChange = getCapsulePartChange(
				getUMLRTDiagramChanges(comparison), name(PART));

		final Diff partAddDiff = find(comparison.getDifferences(),
				addedToReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PART));

		mergeLeftToRight(partAddDiff);

		assertEquals(MERGED, partDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting an addition of a capsule part will also reject the addition of its shape.
	 */
	@Test
	public void testMergeAddCapsulePartRtL() throws IOException {
		final ResourceSet left = input.getAddCapsulePartModel();
		final ResourceSet right = input.getAddCapsuleModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange partDiagramChange = getCapsulePartChange(
				getUMLRTDiagramChanges(comparison), name(PART));

		final Diff partAddDiff = find(comparison.getDifferences(),
				addedToReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PART));

		mergeRightToLeft(partAddDiff);

		assertEquals(DISCARDED, partDiagramChange.getState());
	}

	/**
	 * Tests whether accepting a deletion of a capsule part will also merge the deletion of its shape.
	 */
	@Test
	public void testMergeDeleteCapsulePartLtR() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getAddCapsulePartModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange partDiagramChange = getCapsulePartChange(
				getUMLRTDiagramChanges(comparison), name(PART));

		final Diff partDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PART));

		mergeLeftToRight(partDeleteDiff);

		assertEquals(MERGED, partDiagramChange.getState());
	}

	/**
	 * Tests whether rejecting an deletion of a capsule part will also reject the deletion of its shape.
	 */
	@Test
	public void testMergeDeleteCapsulePartRtL() throws IOException {
		final ResourceSet left = input.getAddCapsuleModel();
		final ResourceSet right = input.getAddCapsulePartModel();

		final Comparison comparison = compare(left, right);
		final UMLRTDiagramChange partDiagramChange = getCapsulePartChange(
				getUMLRTDiagramChanges(comparison), name(PART));

		final Diff partDeleteDiff = find(comparison.getDifferences(),
				removedFromReference(CAPSULE1, OWNED_ATTRIBUTE_FEATURE, PART));

		mergeRightToLeft(partDeleteDiff);

		assertEquals(DISCARDED, partDiagramChange.getState());
	}

	/**
	 * Returns the element name from a <code>qualifiedName</code>.
	 * 
	 * @param qualifiedName
	 *            The qualified name.
	 * @return The name.
	 */
	private String name(String qualifiedName) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
	}

}
