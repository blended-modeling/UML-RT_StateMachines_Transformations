/*******************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *     Philip Langer - switch to batch merger
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static org.eclipse.emf.compare.DifferenceKind.ADD;
import static org.eclipse.emf.compare.DifferenceKind.CHANGE;
import static org.eclipse.emf.compare.DifferenceKind.DELETE;
import static org.eclipse.emf.compare.DifferenceSource.LEFT;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange;
import org.eclipse.uml2.uml.Parameter;
import org.junit.Test;

import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class UMLRTProtocolMessageParameterChangeTest extends AbstractUMLRTTest {

	private static final String NEW_NAME_SUFFIX = "_newName";
	private static final String NEW_NAME_SUFFIX2 = "_newName2";
	private static final String PARAMETER1 = "parameter1";

	/** The input data for the tests. */
	private UMLRTProtocolMessageParameterChangeTestData input = new UMLRTProtocolMessageParameterChangeTestData();

	@Override
	protected AbstractUMLRTInputData getInput() {
		return input;
	}

	@Test
	public void testAddProtocolMessageParameter() throws IOException {
		final Resource left = input.getProtocolMessageWithParameter();
		final Resource right = input.getEmptyProtocolMessage();
		Comparison comparison = compare(left, right);

		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(1, size(protocolMessageParamChanges));

		ProtocolMessageParameterChange protocolMessageParamChange = protocolMessageParamChanges.get(0);
		assertEquals(LEFT, protocolMessageParamChange.getSource());
		assertEquals(ADD, protocolMessageParamChange.getKind());
		assertRTMessageParameter(protocolMessageParamChange, PARAMETER1);

		// verify merge
		mergeLeftToRight(protocolMessageParamChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testDeleteProtocolMessageParameter() throws IOException {
		final Resource right = input.getProtocolMessageWithParameter();
		final Resource left = input.getEmptyProtocolMessage();
		Comparison comparison = compare(left, right);

		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(1, size(protocolMessageParamChanges));

		ProtocolMessageParameterChange protocolMessageParamChange = protocolMessageParamChanges.get(0);
		assertEquals(LEFT, protocolMessageParamChange.getSource());
		assertEquals(DELETE, protocolMessageParamChange.getKind());
		assertRTMessageParameter(protocolMessageParamChange, PARAMETER1);

		// verify merge
		mergeLeftToRight(protocolMessageParamChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testRenameProtocolMessageParameter() throws IOException {
		final Resource right = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		Comparison comparison = compare(left, right);

		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(1, size(protocolMessageParamChanges));

		ProtocolMessageParameterChange protocolMessageParamChange = protocolMessageParamChanges.get(0);
		assertEquals(LEFT, protocolMessageParamChange.getSource());
		assertEquals(CHANGE, protocolMessageParamChange.getKind());
		assertRTMessageParameter(protocolMessageParamChange, PARAMETER1 + NEW_NAME_SUFFIX);

		// verify merge
		mergeLeftToRight(protocolMessageParamChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testTypeChangeProtocolMessageParameter() throws IOException {
		final Resource right = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		Comparison comparison = compare(left, right);

		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(1, size(protocolMessageParamChanges));

		ProtocolMessageParameterChange protocolMessageParamChange = protocolMessageParamChanges.get(0);
		assertEquals(LEFT, protocolMessageParamChange.getSource());
		assertEquals(CHANGE, protocolMessageParamChange.getKind());
		assertRTMessageParameter(protocolMessageParamChange, PARAMETER1);

		// verify merge
		mergeLeftToRight(protocolMessageParamChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictDeleteRenameParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		final Resource right = input.getEmptyProtocolMessage();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, rightChanges.size());
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DifferenceKind.DELETE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1);

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.LEFT);
		assertEquals(1, leftChanges.size());
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(DifferenceKind.CHANGE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1 + NEW_NAME_SUFFIX);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(rightChange, conflict));
	}

	@Test
	public void testConflictRenameDeleteParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getEmptyProtocolMessage();
		final Resource right = input.getParameterRename();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.LEFT);
		assertEquals(1, leftChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(DifferenceKind.DELETE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, rightChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DifferenceKind.CHANGE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1 + NEW_NAME_SUFFIX);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(rightChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(leftChange, conflict));
	}

	@Test
	public void testConflictDeleteTypeChangeParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getEmptyProtocolMessage();
		final Resource right = input.getParameterTypeChange();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.LEFT);
		assertEquals(1, leftChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(DifferenceKind.DELETE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, rightChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DifferenceKind.CHANGE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(rightChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(leftChange, conflict));
	}

	@Test
	public void testConflictTypeChangeDeleteParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		final Resource right = input.getEmptyProtocolMessage();

		Comparison comparison = compare(left, right, origin);

		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, rightChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertRTMessageParameter(rightChange, PARAMETER1);
		assertEquals(DifferenceKind.DELETE, rightChange.getKind());

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.LEFT);
		assertEquals(1, leftChanges.size());
		assertTrue(protocolMessageParamChanges.containsAll(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(DifferenceKind.CHANGE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(rightChange, conflict));
	}

	@Test
	public void testConflictRenameTypeChangeParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		final Resource right = input.getParameterTypeChange();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		if (change1.getSource() == DifferenceSource.LEFT) {
			assertRTMessageParameter(change1, PARAMETER1 + NEW_NAME_SUFFIX);
			assertRTMessageParameter(change2, PARAMETER1);
		} else {
			assertRTMessageParameter(change1, PARAMETER1);
			assertRTMessageParameter(change2, PARAMETER1 + NEW_NAME_SUFFIX);
		}

		List<Conflict> conflicts = comparison.getConflicts();
		assertEquals(0, conflicts.size());
	}

	@Test
	public void testConflictTypeChangeRenameParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		final Resource right = input.getParameterRename();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		if (change1.getSource() == DifferenceSource.RIGHT) {
			assertRTMessageParameter(change1, PARAMETER1 + NEW_NAME_SUFFIX);
			assertRTMessageParameter(change2, PARAMETER1);
		} else {
			assertRTMessageParameter(change1, PARAMETER1);
			assertRTMessageParameter(change2, PARAMETER1 + NEW_NAME_SUFFIX);
		}

		List<Conflict> conflicts = comparison.getConflicts();
		assertEquals(0, conflicts.size());
	}

	@Test
	public void testPseudoConflictRenameRenameParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		final Resource right = input.getParameterRename();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		assertRTMessageParameter(change1, PARAMETER1);
		assertRTMessageParameter(change2, PARAMETER1);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(change1, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(change2, conflict));
	}

	@Test
	public void testConflictRenameRenameParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		final Resource right = input.getParameterRename2();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		if (change1.getSource() == DifferenceSource.LEFT) {
			assertRTMessageParameter(change1, PARAMETER1 + NEW_NAME_SUFFIX);
			assertRTMessageParameter(change2, PARAMETER1 + NEW_NAME_SUFFIX2);
		} else {
			assertRTMessageParameter(change1, PARAMETER1 + NEW_NAME_SUFFIX2);
			assertRTMessageParameter(change2, PARAMETER1 + NEW_NAME_SUFFIX);
		}

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(change1, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(change2, conflict));
	}

	@Test
	public void testPseudoConflictTypeChangeTypeChangeParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		final Resource right = input.getParameterTypeChange();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		assertRTMessageParameter(change1, PARAMETER1);
		assertRTMessageParameter(change2, PARAMETER1);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(change1, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(change2, conflict));
	}

	@Test
	public void testConflictTypeChangeTypeChangeParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		final Resource right = input.getParameterTypeChange2();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));
		ProtocolMessageParameterChange change1 = protocolMessageParamChanges.get(0);
		ProtocolMessageParameterChange change2 = protocolMessageParamChanges.get(1);
		assertEquals(CHANGE, change1.getKind());
		assertEquals(CHANGE, change2.getKind());
		assertNotEquals(change1.getSource(), change2.getSource());
		assertRTMessageParameter(change1, PARAMETER1);
		assertRTMessageParameter(change2, PARAMETER1);

		assertEquals(1, comparison.getConflicts().size());
		final Conflict conflict = getConflictOrConflictOfRefining(change1, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(change2, conflict));
	}

	@Test
	public void testPseudoConflictDeleteDeleteParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getEmptyProtocolMessage();
		final Resource right = input.getEmptyProtocolMessage();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, LEFT);
		assertEquals(1, size(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(DELETE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, size(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DELETE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1);

		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(rightChange, conflict));
	}

	@Test
	public void testConflictDeleteProtocolMessageAddParameter() throws IOException {
		final Resource origin = input.getEmptyProtocolMessage();
		final Resource left = input.getProtocolMessageWithParameter();
		final Resource right = input.getDeleteProtocolMessage();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(1, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, LEFT);
		assertEquals(1, size(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(ADD, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(0, size(rightChanges));

		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.REAL);
		assertNotNull(conflict);
	}

	@Test
	public void testConflictDeleteProtocolMessageRenameParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterRename();
		final Resource right = input.getDeleteProtocolMessage();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, LEFT);
		assertEquals(1, size(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(CHANGE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1 + NEW_NAME_SUFFIX);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, size(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DELETE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1);

		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(rightChange, conflict));
	}

	@Test
	public void testConflictDeleteProtocolMessageTypeChangeParameter() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getParameterTypeChange();
		final Resource right = input.getDeleteProtocolMessage();

		Comparison comparison = compare(left, right, origin);
		// verify comparison
		final List<ProtocolMessageParameterChange> protocolMessageParamChanges = getProtocolMessageParameterChanges(comparison);
		assertEquals(2, size(protocolMessageParamChanges));

		List<ProtocolMessageParameterChange> leftChanges = getProtocolMessageParameterChangesFromSide(comparison, LEFT);
		assertEquals(1, size(leftChanges));
		ProtocolMessageParameterChange leftChange = leftChanges.get(0);
		assertEquals(CHANGE, leftChange.getKind());
		assertRTMessageParameter(leftChange, PARAMETER1);

		List<ProtocolMessageParameterChange> rightChanges = getProtocolMessageParameterChangesFromSide(comparison, DifferenceSource.RIGHT);
		assertEquals(1, size(rightChanges));
		ProtocolMessageParameterChange rightChange = rightChanges.get(0);
		assertEquals(DELETE, rightChange.getKind());
		assertRTMessageParameter(rightChange, PARAMETER1);

		final Conflict conflict = getConflictOrConflictOfRefining(leftChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(rightChange, conflict));
	}

	private void assertRTMessageParameter(ProtocolMessageParameterChange protocolMessageParamChange, String paramName) {
		final EObject discriminant = protocolMessageParamChange.getDiscriminant();
		assertTrue(discriminant instanceof Parameter);
		assertTrue(RTMessageUtils.isRTMessageParameter(discriminant));
		assertEquals(paramName, ((Parameter) discriminant).getName());
	}

	private List<ProtocolMessageParameterChange> getProtocolMessageParameterChanges(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getDifferences(), ProtocolMessageParameterChange.class));
	}

	private List<ProtocolMessageParameterChange> getProtocolMessageParameterChangesFromSide(Comparison comparison, DifferenceSource side) {
		return Lists.newArrayList(filter(getProtocolMessageParameterChanges(comparison), fromSide(side)));
	}

}
