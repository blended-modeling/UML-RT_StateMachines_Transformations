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
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.tryFind;
import static org.eclipse.emf.compare.DifferenceKind.ADD;
import static org.eclipse.emf.compare.DifferenceKind.DELETE;
import static org.eclipse.emf.compare.DifferenceSource.LEFT;
import static org.eclipse.emf.compare.DifferenceSource.RIGHT;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.diagram.internal.CompareDiagramPostProcessor;
import org.eclipse.emf.compare.diagram.internal.extensions.CoordinatesChange;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class UMLRTDiagramChangeTest extends AbstractUMLRTTest {

	private static final String CAPSULE1 = "Capsule1";
	private static final String CAPSULE2 = "Capsule2";
	private static final UMLRTDiagramChangeTestData input = new UMLRTDiagramChangeTestData();
	private static final String CAPSULE2_PART = "capsule2";
	private static final String PORT_CAPSULE1 = "protocol1";
	private static final String PORT_CAPSULE2 = "protocol1";

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


	/* ********************** DIFF TESTS ***********************/

	@Test
	public void testAddCapsule() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getEmptyDiagram();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange addCapsule = getCapsuleChange(diagramChanges, CAPSULE1);
		assertNotNull(addCapsule);
		assertEquals(ADD, addCapsule.getKind());
		assertEquals(LEFT, addCapsule.getSource());
	}

	@Test
	public void testDeleteCapsule() throws IOException {
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule = getCapsuleChange(diagramChanges, CAPSULE1);
		assertNotNull(deleteCapsule);
		assertEquals(DELETE, deleteCapsule.getKind());
		assertEquals(LEFT, deleteCapsule.getSource());
	}

	/** Tests a coordinate change of a Capsule inside the diagram. */
	@Test
	public void testMoveCapsule() throws IOException {
		final Resource right = input.getAddCapsule();
		final Resource left = input.getMoveCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// no diagram changes
		assertEquals(0, size(diagramChanges));
	}

	@Test
	public void testResizeCapsule() throws IOException {
		final Resource right = input.getAddCapsule();
		final Resource left = input.getResizeCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// no diagram changes
		assertEquals(0, size(diagramChanges));
	}


	@Test
	public void testAddCapsulePart() throws IOException {
		final Resource left = input.getAddCapsulePart();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// addition of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange addCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		assertNotNull(addCapsule2);
		assertEquals(ADD, addCapsule2.getKind());
		assertEquals(LEFT, addCapsule2.getSource());

		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(addCapsulePart);
		assertEquals(ADD, addCapsulePart.getKind());
		assertEquals(LEFT, addCapsulePart.getSource());
		assertTrue(((Shape) addCapsulePart.getView()).getElement() instanceof Property);
		Property property = (Property) ((Shape) addCapsulePart.getView()).getElement();
		assertEquals(CAPSULE1, property.getClass_().getName());
	}

	@Test
	public void testDeleteCapsulePart() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// deletion of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		assertNotNull(deleteCapsule2);
		assertEquals(DELETE, deleteCapsule2.getKind());
		assertEquals(LEFT, deleteCapsule2.getSource());

		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);
		assertEquals(DELETE, deleteCapsulePart.getKind());
		assertEquals(LEFT, deleteCapsulePart.getSource());
		assertTrue(((Shape) deleteCapsulePart.getView()).getElement() instanceof Property);
		Property property = (Property) ((Shape) deleteCapsulePart.getView()).getElement();
		assertEquals(CAPSULE1, property.getClass_().getName());
	}

	/** Tests a coordinate change of a CapsulePart inside the diagram. */
	@Test
	public void testMoveCapsulePart() throws IOException {
		final Resource right = input.getAddCapsulePart();
		final Resource left = input.getMoveCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// no diagram changes
		assertEquals(0, size(diagramChanges));
	}

	@Test
	public void testResizeCapsulePart() throws IOException {
		final Resource right = input.getAddCapsulePart();
		final Resource left = input.getResizeCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// no diagram changes
		assertEquals(0, size(diagramChanges));
	}

	@Test
	public void testAddPortToCapsule() throws IOException {
		final Resource right = input.getAddCapsule();
		final Resource left = input.getAddPortToCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);
		assertNotNull(addPort);
		assertEquals(ADD, addPort.getKind());
		assertEquals(LEFT, addPort.getSource());
	}

	@Test
	public void testDeletePortFromCapsule() throws IOException {
		final Resource right = input.getAddPortToCapsule();
		final Resource left = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);
		assertNotNull(deletePort);
		assertEquals(DELETE, deletePort.getKind());
		assertEquals(LEFT, deletePort.getSource());
	}

	@Test
	public void testMovePortInCapsule() throws IOException {
		final Resource right = input.getAddPortToCapsule();
		final Resource left = input.getMovePortInCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(0, size(diagramChanges));
	}

	@Test
	public void testAddPortToCapsulePart() throws IOException {
		final Resource right = input.getAddCapsulePart();
		final Resource left = input.getAddPortToCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);
		assertNotNull(addPort);
		assertEquals(ADD, addPort.getKind());
		assertEquals(LEFT, addPort.getSource());
	}

	@Test
	public void testDeletePortFromCapsulePart() throws IOException {
		final Resource right = input.getAddPortToCapsulePart();
		final Resource left = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);
		assertNotNull(deletePort);
		assertEquals(DELETE, deletePort.getKind());
		assertEquals(LEFT, deletePort.getSource());
	}

	@Test
	public void testMovePortInCapsulePart() throws IOException {
		final Resource right = input.getAddPortToCapsulePart();
		final Resource left = input.getMovePortCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(0, size(diagramChanges));
	}


	/* ********************** MERGE TESTS ***********************/

	@Test
	public void testMergeAddCapsuleLtR() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getEmptyDiagram();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange addCapsule = getCapsuleChange(diagramChanges, CAPSULE1);

		mergeLeftToRight(addCapsule);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddCapsuleRtL() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getEmptyDiagram();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange addCapsule = getCapsuleChange(diagramChanges, CAPSULE1);

		mergeRightToLeft(addCapsule);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeleteCapsuleLtR() throws IOException {
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule = getCapsuleChange(diagramChanges, CAPSULE1);

		mergeLeftToRight(deleteCapsule);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeleteCapsuleRtL() throws IOException {
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule = getCapsuleChange(diagramChanges, CAPSULE1);

		mergeRightToLeft(deleteCapsule);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddCapsulePartMergeCapsulePartLtR() throws IOException {
		final Resource left = input.getAddCapsulePart();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// addition of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);

		mergeLeftToRight(addCapsulePart);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule2
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		assertNotNull(addCapsule2);

		mergeLeftToRight(addCapsule2);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());

	}

	@Test
	public void testMergeAddCapsulePartMergeCapsulePartRtL() throws IOException {
		final Resource left = input.getAddCapsulePart();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// addition of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);

		mergeRightToLeft(addCapsulePart);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule2
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		assertNotNull(addCapsule2);

		mergeRightToLeft(addCapsule2);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddCapsulePartMergeCapsule2LtR() throws IOException {
		final Resource left = input.getAddCapsulePart();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// addition of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange addCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);

		mergeLeftToRight(addCapsule2);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule part
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(addCapsulePart);

		mergeLeftToRight(addCapsulePart);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddCapsulePartMergeCapsule2RtL() throws IOException {
		final Resource left = input.getAddCapsulePart();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// addition of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange addCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);

		mergeRightToLeft(addCapsule2);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule part
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(addCapsulePart);

		mergeRightToLeft(addCapsulePart);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeleteCapsulePartLtR() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// deletion of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);

		mergeLeftToRight(deleteCapsule2);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule part
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);

		mergeLeftToRight(deleteCapsulePart);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeleteCapsulePartRtL() throws IOException {
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		// deletion of Capsule2 and of the CapsulePart in Capsule1
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);

		mergeRightToLeft(deleteCapsule2);

		// verify merge
		comparison = compare(left, right);
		diagramChanges = getUMLRTDiagramChanges(comparison);
		// we should be left with the capsule part
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);

		mergeRightToLeft(deleteCapsulePart);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}


	@Test
	public void testMergeAddPortToCapsuleLtR() throws IOException {
		final Resource right = input.getAddCapsule();
		final Resource left = input.getAddPortToCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);

		mergeLeftToRight(addPort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddPortToCapsuleRtL() throws IOException {
		final Resource right = input.getAddCapsule();
		final Resource left = input.getAddPortToCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);

		mergeRightToLeft(addPort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeletePortFromCapsuleLtR() throws IOException {
		final Resource right = input.getAddPortToCapsule();
		final Resource left = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);

		mergeLeftToRight(deletePort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeletePortFromCapsuleRtL() throws IOException {
		final Resource right = input.getAddPortToCapsule();
		final Resource left = input.getAddCapsule();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);

		mergeRightToLeft(deletePort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddPortToCapsulePartLtR() throws IOException {
		final Resource right = input.getAddCapsulePart();
		final Resource left = input.getAddPortToCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);

		mergeLeftToRight(addPort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeAddPortToCapsulePartRtL() throws IOException {
		final Resource right = input.getAddCapsulePart();
		final Resource left = input.getAddPortToCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange addPort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);

		mergeRightToLeft(addPort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeletePortFromCapsulePartLtR() throws IOException {
		final Resource right = input.getAddPortToCapsulePart();
		final Resource left = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);

		mergeLeftToRight(deletePort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeDeletePortFromCapsulePartRtL() throws IOException {
		final Resource right = input.getAddPortToCapsulePart();
		final Resource left = input.getAddCapsulePart();

		Comparison comparison = compare(left, right);

		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));
		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);

		mergeRightToLeft(deletePort);
		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}


	/* ********************** CONFLICT TESTS ***********************/

	@Test
	public void testConflictDeleteCapsuleAddCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsule();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddCapsulePart();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(3, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(1, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(2, rightChanges.size());

		UMLRTDiagramChange deleteCapsule = getCapsuleChange(leftChanges, CAPSULE1);
		assertNotNull(deleteCapsule);

		UMLRTDiagramChange addCapsule = getCapsuleChange(rightChanges, CAPSULE2);
		assertNotNull(addCapsule);
		UMLRTDiagramChange addCapsulePart = getCapsulePartChange(rightChanges, CAPSULE2_PART);
		assertNotNull(addCapsulePart);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsule, ConflictKind.REAL);
		assertNotNull(conflict1);

		final Conflict conflict2 = getConflictOrConflictOfRefining(addCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict2);

		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(addCapsulePart, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(deleteCapsule, conflict2));
	}

	@Test
	public void testConflictDeleteCapsuleWithCapsulePartMoveCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsulePart();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getMoveCapsulePart();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(3, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(3, leftChanges.size());

		UMLRTDiagramChange deleteCapsule1 = getCapsuleChange(diagramChanges, CAPSULE1);
		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);
		assertNotNull(deleteCapsule1);
		assertNotNull(deleteCapsule2);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict1);
		final Conflict conflict2 = getConflictOrConflictOfRefining(deleteCapsule1, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deleteCapsule1, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(deleteCapsulePart, conflict2));
	}

	@Test
	public void testConflictDeleteCapsuleAddPort() throws IOException {
		final Resource origin = input.getAddCapsule();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddPortToCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(2, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(1, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(1, rightChanges.size());

		UMLRTDiagramChange deleteCapsule = leftChanges.get(0);
		UMLRTDiagramChange addPort = rightChanges.get(0);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsule, ConflictKind.REAL);
		assertNotNull(conflict1);
		final Conflict conflict2 = getConflictOrConflictOfRefining(addPort, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(addPort, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(deleteCapsule, conflict2));
	}

	@Test
	public void testConflictDeleteCapsuleMovePort() throws IOException {
		final Resource origin = input.getAddPortToCapsule();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getMovePortInCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(2, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule = getCapsuleChange(diagramChanges, CAPSULE1);
		assertNotNull(deleteCapsule);
		assertEquals(LEFT, deleteCapsule.getSource());
		UMLRTDiagramChange portCapsule = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);
		assertNotNull(portCapsule);
		assertEquals(LEFT, portCapsule.getSource());

		final Conflict conflict1 = getConflictOrConflictOfRefining(portCapsule, ConflictKind.REAL);
		assertNotNull(conflict1);

		ArrayList<CoordinatesChange> coordChanges = Lists.newArrayList(filter(comparison.getDifferences(), CoordinatesChange.class));
		assertEquals(1, coordChanges.size());
		CoordinatesChange movePort = coordChanges.get(0);
		assertEquals(RIGHT, movePort.getSource());
		final Conflict conflict2 = getConflictOrConflictOfRefining(movePort, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(movePort, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(portCapsule, conflict2));
	}

	@Test
	public void testConflictDeleteCapsuleWithCapsulePartAddPortToCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsulePart();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddPortToCapsulePart();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(4, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule1 = getCapsuleChange(diagramChanges, CAPSULE1);
		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);
		assertEquals(LEFT, deleteCapsule1.getSource());
		assertNotNull(deleteCapsule1);
		assertEquals(LEFT, deleteCapsule2.getSource());
		assertNotNull(deleteCapsule2);
		assertEquals(LEFT, deleteCapsulePart.getSource());

		UMLRTDiagramChange addPortToCapsulePart = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);
		assertNotNull(addPortToCapsulePart);
		assertEquals(RIGHT, addPortToCapsulePart.getSource());

		final Conflict conflict1 = getConflictOrConflictOfRefining(addPortToCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict1);
		final Conflict conflict2 = getConflictOrConflictOfRefining(deleteCapsule1, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(addPortToCapsulePart, conflict2)
				|| containsDiffOrAnyOfItsRefiningDiffs(deleteCapsule1, conflict1));
	}

	@Test
	public void testConflictDeleteCapsulePartAddPortToCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsulePart();
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddPortToCapsulePart();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(3, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePart);
		assertEquals(LEFT, deleteCapsule2.getSource());
		assertNotNull(deleteCapsule2);
		assertEquals(LEFT, deleteCapsulePart.getSource());

		UMLRTDiagramChange addPortToCapsulePart = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);
		assertNotNull(addPortToCapsulePart);
		assertEquals(RIGHT, addPortToCapsulePart.getSource());

		final Conflict conflict1 = getConflictOrConflictOfRefining(addPortToCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict1);
		final Conflict conflict2 = getConflictOrConflictOfRefining(deleteCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(addPortToCapsulePart, conflict2)
				|| containsDiffOrAnyOfItsRefiningDiffs(deleteCapsulePart, conflict1));
	}

	@Test
	public void testConflictDeleteCapsulePartMovePortInCapsulePart() throws IOException {
		final Resource origin = input.getAddPortToCapsulePart();
		final Resource left = input.getAddCapsule();
		final Resource right = input.getMovePortCapsulePart();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(3, size(diagramChanges));

		UMLRTDiagramChange deleteCapsule2 = getCapsuleChange(diagramChanges, CAPSULE2);
		UMLRTDiagramChange deleteCapsulePart = getCapsulePartChange(diagramChanges, CAPSULE2_PART);
		UMLRTDiagramChange deletePortFromCapsulePart = getPortChange(diagramChanges, PORT_CAPSULE2, CAPSULE2);
		assertNotNull(deleteCapsulePart);
		assertEquals(LEFT, deleteCapsule2.getSource());
		assertNotNull(deleteCapsule2);
		assertEquals(LEFT, deleteCapsulePart.getSource());
		assertNotNull(deletePortFromCapsulePart);
		assertEquals(LEFT, deletePortFromCapsulePart.getSource());

		final Conflict conflict1 = getConflictOrConflictOfRefining(deletePortFromCapsulePart, ConflictKind.REAL);
		assertNotNull(conflict1);
		ArrayList<CoordinatesChange> coordChanges = Lists.newArrayList(filter(comparison.getDifferences(), CoordinatesChange.class));
		assertEquals(1, coordChanges.size());
		CoordinatesChange movePort = coordChanges.get(0);
		assertEquals(RIGHT, movePort.getSource());
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(movePort, conflict1));
		final Conflict conflict2 = getConflictOrConflictOfRefining(movePort, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(movePort, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(deletePortFromCapsulePart, conflict2));
	}

	@Test
	public void testConflictDeletePortMovePort() throws IOException {
		final Resource origin = input.getAddPortToCapsule();
		final Resource left = input.getAddCapsule();
		final Resource right = input.getMovePortInCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(1, size(diagramChanges));

		UMLRTDiagramChange deletePort = getPortChange(diagramChanges, PORT_CAPSULE1, CAPSULE1);
		assertNotNull(deletePort);
		assertEquals(LEFT, deletePort.getSource());

		final Conflict conflict1 = getConflictOrConflictOfRefining(deletePort, ConflictKind.REAL);
		assertNotNull(conflict1);
		ArrayList<CoordinatesChange> coordChanges = Lists.newArrayList(filter(comparison.getDifferences(), CoordinatesChange.class));
		assertEquals(1, coordChanges.size());
		CoordinatesChange movePort = coordChanges.get(0);
		assertEquals(RIGHT, movePort.getSource());
		final Conflict conflict2 = getConflictOrConflictOfRefining(movePort, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(movePort, conflict1)
				|| containsDiffOrAnyOfItsRefiningDiffs(deletePort, conflict2));
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(movePort, conflict1));
	}

	@Test
	public void testPseudoConflictDeleteCapsuleDeleteCapsule() throws IOException {
		final Resource origin = input.getAddCapsule();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getEmptyDiagram();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(2, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(1, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(1, rightChanges.size());

		UMLRTDiagramChange deleteCapsuleLeft = leftChanges.get(0);
		UMLRTDiagramChange deleteCapsuleRight = rightChanges.get(0);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsuleLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict1);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deleteCapsuleRight, conflict1));
	}

	@Test
	public void testPseudoConflictDeleteCapsulePartDeleteCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsulePart();
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(4, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(2, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(2, rightChanges.size());

		UMLRTDiagramChange deleteCapsule2Left = getCapsuleChange(leftChanges, CAPSULE2);
		assertNotNull(deleteCapsule2Left);
		UMLRTDiagramChange deleteCapsulePartLeft = getCapsulePartChange(leftChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePartLeft);
		UMLRTDiagramChange deleteCapsule2Right = getCapsuleChange(rightChanges, CAPSULE2);
		assertNotNull(deleteCapsule2Right);
		UMLRTDiagramChange deleteCapsulePartRight = getCapsulePartChange(rightChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePartRight);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsule2Left, ConflictKind.PSEUDO);
		assertNotNull(conflict1);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deleteCapsule2Right, conflict1));
		final Conflict conflict2 = getConflictOrConflictOfRefining(deleteCapsulePartLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deleteCapsulePartRight, conflict2));
	}

	@Test
	public void testPseudoConflictDeletePortDeletePort() throws IOException {
		final Resource origin = input.getAddPortToCapsule();
		final Resource left = input.getAddCapsule();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(2, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(1, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(1, rightChanges.size());

		UMLRTDiagramChange deletePortLeft = leftChanges.get(0);
		UMLRTDiagramChange deletePortRight = rightChanges.get(0);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deletePortLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict1);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deletePortRight, conflict1));
	}

	@Test
	public void testPseudoConflictDeleteCapsuleWithCapsulePartDeleteCapsulePart() throws IOException {
		final Resource origin = input.getAddCapsulePart();
		final Resource left = input.getEmptyDiagram();
		final Resource right = input.getAddCapsule();

		Comparison comparison = compare(left, right, origin);
		final List<UMLRTDiagramChange> diagramChanges = getUMLRTDiagramChanges(comparison);
		assertEquals(5, size(diagramChanges));

		ArrayList<UMLRTDiagramChange> leftChanges = Lists.newArrayList(filter(diagramChanges, fromSide(LEFT)));
		assertEquals(3, leftChanges.size());
		ArrayList<UMLRTDiagramChange> rightChanges = Lists.newArrayList(filter(diagramChanges, fromSide(RIGHT)));
		assertEquals(2, rightChanges.size());

		UMLRTDiagramChange deleteCapsule1Left = getCapsuleChange(leftChanges, CAPSULE1);
		assertNotNull(deleteCapsule1Left);
		UMLRTDiagramChange deleteCapsule2Left = getCapsuleChange(leftChanges, CAPSULE2);
		assertNotNull(deleteCapsule2Left);
		UMLRTDiagramChange deleteCapsulePartLeft = getCapsulePartChange(leftChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePartLeft);

		UMLRTDiagramChange deleteCapsule2Right = getCapsuleChange(rightChanges, CAPSULE2);
		assertNotNull(deleteCapsule2Right);
		UMLRTDiagramChange deleteCapsulePartRight = getCapsulePartChange(rightChanges, CAPSULE2_PART);
		assertNotNull(deleteCapsulePartRight);

		final Conflict conflict1 = getConflictOrConflictOfRefining(deleteCapsulePartRight, ConflictKind.PSEUDO);
		assertNotNull(conflict1);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(deleteCapsulePartLeft, conflict1));
	}

	/* ********************** HELPER PREDICATES and METHODS ***********************/

	private static final Predicate<UMLRTDiagramChange> CAPSULEPART_CHANGE_WITH_NAME_PREDICATE(
			final String name) {
		return new Predicate<UMLRTDiagramChange>() {
			@Override
			public boolean apply(UMLRTDiagramChange change) {
				if (!(change.getView() instanceof Shape)) {
					return false;
				}
				if (!(((Shape) change.getView()).getElement() instanceof Property)) {
					return false;
				}
				Property property = (Property) ((Shape) change.getView()).getElement();
				return name != null && name.equals(property.getName());
			}
		};
	}

	private static final Predicate<UMLRTDiagramChange> CAPSULE_CHANGE_WITH_NAME_PREDICATE(
			final String name) {
		return new Predicate<UMLRTDiagramChange>() {
			@Override
			public boolean apply(UMLRTDiagramChange change) {
				if (!(change.getView() instanceof Shape)) {
					return false;
				}
				if (!(((Shape) change.getView()).getElement() instanceof Class)) {
					return false;
				}
				Class class_ = (Class) ((Shape) change.getView()).getElement();
				return name != null && name.equals(class_.getName());
			}
		};
	}

	private static final Predicate<UMLRTDiagramChange> PORT_CHANGE_WITH_NAME_PREDICATE(
			final String name, final String capsuleName) {
		return new Predicate<UMLRTDiagramChange>() {
			@Override
			public boolean apply(UMLRTDiagramChange change) {
				if (!(change.getView() instanceof Shape)) {
					return false;
				}
				if (!(((Shape) change.getView()).getElement() instanceof Port)) {
					return false;
				}
				Port port = (Port) ((Shape) change.getView()).getElement();
				Class class_ = port.getClass_();
				return name != null && name.equals(port.getName()) && capsuleName.equals(class_.getName());
			}
		};
	}

	public static UMLRTDiagramChange getCapsuleChange(List<UMLRTDiagramChange> diagramChanges, String name) {
		final Optional<UMLRTDiagramChange> optional = tryFind(diagramChanges,
				CAPSULE_CHANGE_WITH_NAME_PREDICATE(name));
		return optional.orNull();
	}

	public static UMLRTDiagramChange getCapsulePartChange(List<UMLRTDiagramChange> diagramChanges, String name) {
		final Optional<UMLRTDiagramChange> optional = tryFind(diagramChanges,
				CAPSULEPART_CHANGE_WITH_NAME_PREDICATE(name));
		return optional.orNull();
	}


	public static UMLRTDiagramChange getPortChange(List<UMLRTDiagramChange> diagramChanges, String name, String capsuleName) {
		final Optional<UMLRTDiagramChange> optional = tryFind(diagramChanges,
				PORT_CHANGE_WITH_NAME_PREDICATE(name, capsuleName));
		return optional.orNull();
	}

	public static List<UMLRTDiagramChange> getUMLRTDiagramChanges(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getDifferences(), UMLRTDiagramChange.class));
	}

}
