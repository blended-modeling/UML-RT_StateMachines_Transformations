/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philip Langer - initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.all;
import static com.google.common.collect.Iterables.contains;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterables.tryFind;
import static org.eclipse.emf.compare.DifferenceKind.ADD;
import static org.eclipse.emf.compare.DifferenceKind.CHANGE;
import static org.eclipse.emf.compare.DifferenceKind.DELETE;
import static org.eclipse.emf.compare.DifferenceKind.MOVE;
import static org.eclipse.emf.compare.DifferenceSource.LEFT;
import static org.eclipse.emf.compare.DifferenceSource.RIGHT;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.ofKind;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.onFeature;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * Integration test for {@link ProtocolChange protocol changes}.
 * 
 * <p>
 * Tests the entire comparison process with the {@link UMLRTPostProcessor} and
 * asserts the detection of {@link ProtocolChange protocol changes} of different
 * kinds, such as the single and multiple additions, deletions, and moves, each
 * in two-way and three-way comparisons on both sides.
 * </p>
 * <p>
 * In the test scenario <em>single-1</em>, the protocol change in question is
 * always performed in the model called <code>right.uml</code>.
 * </p>
 * <p>
 * In the test scenario <em>multiple-1</em>, the protocols <em>Resource</em> and
 * <em>USBProtocol</em> have been moved on the right-hand side (
 * <code>right.uml</code>). On the left-hand side (<code>left.uml</code>), the
 * protocol <em>AppControl</em> has been deleted, and <em>TestProtocol</em> has
 * been added.
 * </p>
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
@SuppressWarnings("restriction")
public class UMLRTProtocolChangeTest extends AbstractUMLRTTest {

	private static Function<EObject, String> COLLABORATION_NAME = new Function<EObject, String>() {
		@Override
		public String apply(EObject input) {
			if (input instanceof Collaboration) {
				return ((Collaboration) input).getName();
			}
			return null;
		}
	};

	private static Function<ProtocolChange, String> PROTOCOL_CHANGE_COLLABORATION_NAME = new Function<ProtocolChange, String>() {
		@Override
		public String apply(ProtocolChange input) {
			if (input != null && input.getDiscriminant() instanceof Collaboration) {
				return COLLABORATION_NAME.apply(input.getDiscriminant());
			}
			return null;
		}
	};

	private static final Predicate<ProtocolChange> PROTOCOL_CHANGE_WITH_COLLABORATION_NAME_PREDICATE(
			final String protocolName) {
		return new Predicate<ProtocolChange>() {
			@Override
			public boolean apply(ProtocolChange input) {
				String name = PROTOCOL_CHANGE_COLLABORATION_NAME.apply(input);
				return name != null && name.equals(protocolName);
			}
		};
	}

	private static final String ADDED_PROTOCOL_NAME_MULTIPLE_1 = "TestProtocol"; //$NON-NLS-1$
	private static final String DELETED_PROTOCOL_NAME_MULTIPLE_1 = "AppControl"; //$NON-NLS-1$
	private static final String PROTOCOL_NAME_SINGLE_1 = "Resource"; //$NON-NLS-1$
	private static final String MOVED_PROTOCOL_MULTIPLE_1_1 = "Resource"; //$NON-NLS-1$
	private static final String MOVED_PROTOCOL_MULTIPLE_1_2 = "USBProtocol"; //$NON-NLS-1$
	private static final String RENAMED_PROTOCOL_NAME_MULTIPLE_1 = "Resource1"; //$NON-NLS-1$
	private static final String CHANGED_PROTOCOL_NAME_MULTIPLE_3_1 = "Resource"; //$NON-NLS-1$
	private static final String RENAMED_PROTOCOL_MULTIPLE_3_1 = "Resource1"; //$NON-NLS-1$
	private static final String CHANGED_PROTOCOL_NAME_MULTIPLE_3_2 = "USBProtocol"; //$NON-NLS-1$
	private static final String RENAMED_PROTOCOL_MULTIPLE_3_2 = "USBProtocol1"; //$NON-NLS-1$

	private UMLRTProtocolChangeTestData input = new UMLRTProtocolChangeTestData();

	@Override
	protected UMLRTProtocolChangeTestData getInput() {
		return input;
	}

	@Test
	public void testSingleProtocolAddInTwoWay() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Comparison comparison = compare(left, right);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(ADD, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolDeleteInTwoWay() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Comparison comparison = compare(right, left);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(DELETE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolAddInThreeWayLeft() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(left, right, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(ADD, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolAddInThreeWayRight() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(right, left, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(ADD, protocolChange.getKind());
		assertEquals(RIGHT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolDeleteInThreeWayLeft() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Resource origin = left;
		final Comparison comparison = compare(right, left, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(DELETE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolDeleteInThreeWayRight() throws IOException {
		final Resource left = input.getAddDeleteSingle1Left();
		final Resource right = input.getAddDeleteSingle1Right();
		final Resource origin = left;
		final Comparison comparison = compare(left, right, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(DELETE, protocolChange.getKind());
		assertEquals(RIGHT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolMoveInTwoWay() throws IOException {
		final Resource left = input.getMoveSingle1Left();
		final Resource right = input.getMoveSingle1Right();
		final Comparison comparison = compare(left, right);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(MOVE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolMoveInThreeWayLeft() throws IOException {
		final Resource left = input.getMoveSingle1Left();
		final Resource right = input.getMoveSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(left, right, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(MOVE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolMoveInThreeWayRight() throws IOException {
		final Resource left = input.getMoveSingle1Left();
		final Resource right = input.getMoveSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(right, left, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(MOVE, protocolChange.getKind());
		assertEquals(RIGHT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolRenameInTwoWay() throws IOException {
		final Resource left = input.getRenameSingle1Left();
		final Resource right = input.getRenameSingle1Right();
		final Comparison comparison = compare(left, right);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(CHANGE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolRenameInThreeWayLeft() throws IOException {
		final Resource left = input.getRenameSingle1Left();
		final Resource right = input.getRenameSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(left, right, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(CHANGE, protocolChange.getKind());
		assertEquals(LEFT, protocolChange.getSource());
	}

	@Test
	public void testSingleProtocolRenameInThreeWayRight() throws IOException {
		final Resource left = input.getRenameSingle1Left();
		final Resource right = input.getRenameSingle1Right();
		final Resource origin = right;
		final Comparison comparison = compare(right, left, origin);
		final ProtocolChange protocolChange = getAndAssertSingle1ProtocolChange(comparison);
		assertEquals(CHANGE, protocolChange.getKind());
		assertEquals(RIGHT, protocolChange.getSource());
	}

	@Test
	public void testMultipleProtocolChangesInThreeWay() throws IOException {
		final Resource left = input.getMultiple1Left();
		final Resource right = input.getMultiple1Right();
		final Resource origin = input.getMultiple1Origin();
		final Comparison comparison = compare(left, right, origin);
		final List<Diff> differences = comparison.getDifferences();

		// we expect five protocol changes
		final Iterable<ProtocolChange> protocolChanges = filter(differences, ProtocolChange.class);
		assertEquals(5, size(protocolChanges));

		// three on the left, and two on the right
		final Iterable<ProtocolChange> leftProtocolChanges = filter(protocolChanges, fromSide(LEFT));
		final Iterable<ProtocolChange> rightProtocolChanges = filter(protocolChanges, fromSide(RIGHT));
		assertEquals(3, size(leftProtocolChanges));
		assertEquals(2, size(rightProtocolChanges));

		// on the left side, we expect one deletion and one addition
		// DEL: AppControl, ADD: TestProtocol, Rename: Resource
		final Iterable<ProtocolChange> leftDeleteProtocolChanges = filter(leftProtocolChanges, ofKind(DELETE));
		final Iterable<ProtocolChange> leftAddProtocolChanges = filter(leftProtocolChanges, ofKind(ADD));
		final Iterable<ProtocolChange> leftRenameProtocolChanges = filter(leftProtocolChanges, ofKind(CHANGE));
		assertEquals(1, size(leftDeleteProtocolChanges));
		assertEquals(1, size(leftAddProtocolChanges));
		assertEquals(1, size(leftRenameProtocolChanges));
		final ProtocolChange leftDeleteProtocolChange = get(leftDeleteProtocolChanges, 0);
		assertEquals(DELETED_PROTOCOL_NAME_MULTIPLE_1,
				PROTOCOL_CHANGE_COLLABORATION_NAME.apply(leftDeleteProtocolChange));
		final ProtocolChange leftAddProtocolChange = get(leftAddProtocolChanges, 0);
		assertEquals(ADDED_PROTOCOL_NAME_MULTIPLE_1, PROTOCOL_CHANGE_COLLABORATION_NAME.apply(leftAddProtocolChange));
		final ProtocolChange leftRenameProtocolChange = get(leftRenameProtocolChanges, 0);
		assertEquals(RENAMED_PROTOCOL_NAME_MULTIPLE_1,
				PROTOCOL_CHANGE_COLLABORATION_NAME.apply(leftRenameProtocolChange));

		// on the right side, we expect two moves
		// Resource and USBProtocol
		assertEquals(2, size(filter(rightProtocolChanges, ofKind(MOVE))));
		final Iterable<String> movedProtocolNames = transform(rightProtocolChanges, PROTOCOL_CHANGE_COLLABORATION_NAME);
		assertTrue(contains(movedProtocolNames, MOVED_PROTOCOL_MULTIPLE_1_1));
		assertTrue(contains(movedProtocolNames, MOVED_PROTOCOL_MULTIPLE_1_2));
	}

	@Test
	public void testProtocolRenameAndMoveOnResourceProtocolTwoWay() throws IOException {
		final Resource left = input.getMultiple3Origin();
		final Resource right = input.getMultiple3RenameAndMoveOfResource();
		final Comparison comparison = compare(left, right);
		final List<Diff> differences = comparison.getDifferences();
		assertRenameAndMoveProtocolChange(differences, CHANGED_PROTOCOL_NAME_MULTIPLE_3_1);
	}

	@Test
	public void testProtocolRenameAndMoveOnUsbProtocolTwoWay() throws IOException {
		final Resource left = input.getMultiple3Origin();
		final Resource right = input.getMultiple3RenameAndMoveOfUsbProtocol();
		final Comparison comparison = compare(left, right);
		final List<Diff> differences = comparison.getDifferences();
		assertRenameAndMoveProtocolChange(differences, CHANGED_PROTOCOL_NAME_MULTIPLE_3_2);
	}

	@Test
	public void testProtocolRenameAndMoveOnSameProtocolThreeWay() throws IOException {
		final Resource origin = input.getMultiple3Origin();
		final Resource left = input.getMultiple3RenameAndMoveOfResource();
		final Resource right = input.getMultiple3RenameAndMoveOfUsbProtocol();
		final Comparison comparison = compare(left, right, origin);
		final List<Diff> differences = comparison.getDifferences();

		final Iterable<Diff> leftDifferences = filter(differences, fromSide(LEFT));
		assertRenameAndMoveProtocolChange(leftDifferences, RENAMED_PROTOCOL_MULTIPLE_3_1);

		final Iterable<Diff> rightDifferences = filter(differences, fromSide(RIGHT));
		assertRenameAndMoveProtocolChange(rightDifferences, RENAMED_PROTOCOL_MULTIPLE_3_2);
	}

	private void assertRenameAndMoveProtocolChange(Iterable<Diff> differences, String expectedProtocolName) {
		// we expect two protocol changes
		final Iterable<ProtocolChange> protocolChanges = filter(differences, ProtocolChange.class);
		assertEquals(2, size(protocolChanges));

		// one is the move of protocol
		final Iterable<ProtocolChange> protocolMoves = filter(protocolChanges, ofKind(MOVE));
		assertEquals(1, size(protocolMoves));
		final ProtocolChange protocolMove = get(protocolMoves, 0);
		final String nameOfMovedProtocol = PROTOCOL_CHANGE_COLLABORATION_NAME.apply(protocolMove);
		assertEquals(expectedProtocolName, nameOfMovedProtocol);

		// protocol move is refined by one reference change
		final EList<Diff> refiningDiffsOfProtocolMove = protocolMove.getRefinedBy();
		assertEquals(1, refiningDiffsOfProtocolMove.size());
		assertTrue(all(refiningDiffsOfProtocolMove, ofKind(MOVE)));
		assertTrue(all(refiningDiffsOfProtocolMove, instanceOf(ReferenceChange.class)));
		assertTrue(all(refiningDiffsOfProtocolMove, onFeature(UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT)));

		// one is the rename of protocol
		final Iterable<ProtocolChange> protocolRenames = filter(protocolChanges, ofKind(CHANGE));
		assertEquals(1, size(protocolRenames));
		final ProtocolChange protocolRename = get(protocolRenames, 0);
		final String nameOfRenamedProtocol = PROTOCOL_CHANGE_COLLABORATION_NAME.apply(protocolRename);
		assertEquals(expectedProtocolName, nameOfRenamedProtocol);

		// protocol rename is refined by five attribute changes
		final EList<Diff> refiningDiffsOfProtocolRename = protocolRename.getRefinedBy();
		assertEquals(5, refiningDiffsOfProtocolRename.size());
		assertTrue(all(refiningDiffsOfProtocolRename, ofKind(CHANGE)));
		assertTrue(all(refiningDiffsOfProtocolRename, instanceOf(AttributeChange.class)));
		assertTrue(all(refiningDiffsOfProtocolRename, onFeature(UMLPackage.Literals.NAMED_ELEMENT__NAME)));
	}

	/* ******************* MERGE TESTS ******************* */

	@Test
	public void testMergeSingleProtocolAddInTwoWayRightToLeft() throws IOException {
		final Resource left = input.getAddDeleteSingle2Left();
		final Resource right = input.getAddDeleteSingle2Right();
		Comparison comparison = compare(left, right);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(ADD, protocolChange.getKind());

		mergeRightToLeft(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolAddInTwoWayLeftToRight() throws IOException {
		final Resource left = input.getAddDeleteSingle2Left();
		final Resource right = input.getAddDeleteSingle2Right();
		Comparison comparison = compare(left, right);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(ADD, protocolChange.getKind());

		mergeLeftToRight(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolDeleteInTwoWayLeftToRight() throws IOException {
		final Resource left = input.getAddDeleteSingle2Left();
		final Resource right = input.getAddDeleteSingle2Right();
		Comparison comparison = compare(right, left);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DELETE, protocolChange.getKind());

		mergeLeftToRight(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolDeleteInTwoWayRightToLeft() throws IOException {
		final Resource left = input.getAddDeleteSingle2Left();
		final Resource right = input.getAddDeleteSingle2Right();
		Comparison comparison = compare(right, left);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DELETE, protocolChange.getKind());

		mergeRightToLeft(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolMoveInTwoWayLeftToRight() throws IOException {
		final Resource left = input.getMoveSingle1Left();
		final Resource right = input.getMoveSingle1Right();
		Comparison comparison = compare(left, right);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(MOVE, protocolChange.getKind());

		mergeLeftToRight(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolMoveInTwoWayRightToLeft() throws IOException {
		final Resource left = input.getMoveSingle1Left();
		final Resource right = input.getMoveSingle1Right();
		Comparison comparison = compare(right, left);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(MOVE, protocolChange.getKind());

		mergeRightToLeft(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolRenameInTwoWayLeftToRight() throws IOException {
		final Resource left = input.getRenameSingle1Left();
		final Resource right = input.getRenameSingle1Right();
		Comparison comparison = compare(left, right);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(CHANGE, protocolChange.getKind());

		mergeLeftToRight(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeSingleProtocolRenameInTwoWayRightToLeft() throws IOException {
		final Resource left = input.getRenameSingle1Left();
		final Resource right = input.getRenameSingle1Right();
		Comparison comparison = compare(left, right);

		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(CHANGE, protocolChange.getKind());

		mergeRightToLeft(protocolChange);

		// verify merge
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeMultipleProtocolAddInTwoWayLeftToRight() throws IOException {
		final Resource left = input.getMultiple2AddRename();
		final Resource right = input.getMultiple2Origin();
		Comparison comparison = compare(left, right);

		// we expect three protocol changes
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(3, protocolChanges.size());

		ProtocolChange protocol1 = getChangeOfProtocol(protocolChanges, "Protocol1");
		assertNotNull(protocol1);
		ProtocolChange protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		ProtocolChange testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol_newName");
		assertNotNull(testProtocol);

		// accept protocol1 change
		mergeLeftToRight(protocol1);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());
		// verify that the other two changes were not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol_newName");
		assertNotNull(testProtocol);

		// accept TestProtocol rename change
		mergeLeftToRight(testProtocol);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		// verify that the other change was not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);

		// accept the last change
		mergeLeftToRight(protocol2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeMultipleProtocolAddInTwoWayRightToLeft() throws IOException {
		final Resource left = input.getMultiple2AddRename();
		final Resource right = input.getMultiple2Origin();
		Comparison comparison = compare(left, right);

		// we expect three protocol changes
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(3, protocolChanges.size());

		ProtocolChange protocol1 = getChangeOfProtocol(protocolChanges, "Protocol1");
		assertNotNull(protocol1);
		ProtocolChange protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		ProtocolChange testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol_newName");
		assertNotNull(testProtocol);

		// accept protocol1 change
		mergeRightToLeft(protocol1);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());
		// verify that the other two changes were not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol_newName");
		assertNotNull(testProtocol);

		// accept TestProtocol rename change
		mergeRightToLeft(testProtocol);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		// verify that the other change was not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);

		// accept the last change
		mergeRightToLeft(protocol2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeMultipleProtocolDeleteInTwoWayRightToLeft() throws IOException {
		final Resource right = input.getMultiple2AddRename();
		final Resource left = input.getMultiple2Origin();
		Comparison comparison = compare(left, right);

		// we expect three protocol changes
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(3, protocolChanges.size());

		ProtocolChange protocol1 = getChangeOfProtocol(protocolChanges, "Protocol1");
		assertNotNull(protocol1);
		ProtocolChange protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		ProtocolChange testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol");
		assertNotNull(testProtocol);

		// accept protocol1 change
		mergeRightToLeft(protocol1);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());
		// verify that the other two changes were not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol");
		assertNotNull(testProtocol);

		// accept TestProtocol rename change
		mergeRightToLeft(testProtocol);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		// verify that the other change was not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);

		// accept the last change
		mergeRightToLeft(protocol2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testMergeMultipleProtocolDeleteInTwoWayLeftToRight() throws IOException {
		final Resource right = input.getMultiple2AddRename();
		final Resource left = input.getMultiple2Origin();
		Comparison comparison = compare(left, right);

		// we expect three protocol changes
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(3, protocolChanges.size());

		ProtocolChange protocol1 = getChangeOfProtocol(protocolChanges, "Protocol1");
		assertNotNull(protocol1);
		ProtocolChange protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		ProtocolChange testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol");
		assertNotNull(testProtocol);

		// accept protocol1 change
		mergeLeftToRight(protocol1);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());
		// verify that the other two changes were not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		testProtocol = getChangeOfProtocol(protocolChanges, "TestProtocol");
		assertNotNull(testProtocol);

		// accept TestProtocol rename change
		mergeLeftToRight(testProtocol);

		// verify merge
		comparison = compare(left, right);
		protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		// verify that the other change was not merged
		protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);

		// accept the last change
		mergeLeftToRight(protocol2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	/* ******************* CONFLICT TESTS ******************* */

	@Test
	public void testProtocolConflictChangeVsDelete() throws IOException {
		final Resource right = input.getMultiple2Delete();
		final Resource left = input.getMultiple2AddRename();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(4, protocolChanges.size());

		ProtocolChange protocol1 = getChangeOfProtocol(protocolChanges, "Protocol1");
		assertNotNull(protocol1);
		assertEquals(ADD, protocol1.getKind());
		assertEquals(LEFT, protocol1.getSource());

		ProtocolChange protocol2 = getChangeOfProtocol(protocolChanges, "Protocol2");
		assertNotNull(protocol2);
		assertEquals(ADD, protocol2.getKind());
		assertEquals(LEFT, protocol2.getSource());

		ProtocolChange testProtocolRename = getChangeOfProtocol(protocolChanges, "TestProtocol_newName");
		assertNotNull(testProtocolRename);
		assertEquals(CHANGE, testProtocolRename.getKind());
		assertEquals(LEFT, testProtocolRename.getSource());

		ProtocolChange testProtocolDelete = getChangeOfProtocol(protocolChanges, "TestProtocol");
		assertNotNull(testProtocolDelete);
		assertEquals(DELETE, testProtocolDelete.getKind());
		assertEquals(RIGHT, testProtocolDelete.getSource());


		final Conflict conflict = getConflictOrConflictOfRefining(testProtocolRename, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(testProtocolDelete, conflict));

		assertNull(getConflictOrConflictOfRefining(testProtocolRename, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(testProtocolDelete, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(protocol1, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(protocol1, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(protocol2, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(protocol2, ConflictKind.REAL));
	}

	@Test
	public void testProtocolConflictDeleteVsDelete() throws IOException {
		final Resource right = input.getMultiple2Delete();
		final Resource left = input.getMultiple2Delete();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());

		ProtocolChange testProtocolDeleteLeft = getChangeOfProtocol(filter(protocolChanges, fromSide(LEFT)),
				"TestProtocol");
		assertNotNull(testProtocolDeleteLeft);
		assertEquals(DELETE, testProtocolDeleteLeft.getKind());
		assertEquals(LEFT, testProtocolDeleteLeft.getSource());

		ProtocolChange testProtocolDeleteRight = getChangeOfProtocol(filter(protocolChanges, fromSide(RIGHT)),
				"TestProtocol");
		assertNotNull(testProtocolDeleteRight);
		assertEquals(DELETE, testProtocolDeleteRight.getKind());
		assertEquals(RIGHT, testProtocolDeleteRight.getSource());


		final Conflict conflict = getConflictOrConflictOfRefining(testProtocolDeleteLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(testProtocolDeleteRight, conflict));

		assertNull(getConflictOrConflictOfRefining(testProtocolDeleteLeft, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(testProtocolDeleteRight, ConflictKind.REAL));
	}

	@Test
	public void testProtocolConflictMoveVsMove() throws IOException {
		final Resource right = input.getMultiple2Move();
		final Resource left = input.getMultiple2Move();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());

		ProtocolChange testProtocolMoveLeft = getChangeOfProtocol(filter(protocolChanges, fromSide(LEFT)),
				"TestProtocol");
		assertNotNull(testProtocolMoveLeft);
		assertEquals(MOVE, testProtocolMoveLeft.getKind());
		assertEquals(LEFT, testProtocolMoveLeft.getSource());

		ProtocolChange testProtocolMoveRight = getChangeOfProtocol(filter(protocolChanges, fromSide(RIGHT)),
				"TestProtocol");
		assertNotNull(testProtocolMoveRight);
		assertEquals(MOVE, testProtocolMoveRight.getKind());
		assertEquals(RIGHT, testProtocolMoveRight.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(testProtocolMoveLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(testProtocolMoveRight, conflict));

		assertNull(getConflictOrConflictOfRefining(testProtocolMoveLeft, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(testProtocolMoveRight, ConflictKind.REAL));

	}

	@Test
	public void testProtocolConflictRenameVsRename() throws IOException {
		final Resource right = input.getMultiple2Rename();
		final Resource left = input.getMultiple2Rename();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());

		ProtocolChange testProtocolRenameLeft = getChangeOfProtocol(filter(protocolChanges, fromSide(LEFT)),
				"TestProtocol");
		assertNotNull(testProtocolRenameLeft);
		assertEquals(CHANGE, testProtocolRenameLeft.getKind());
		assertEquals(LEFT, testProtocolRenameLeft.getSource());

		ProtocolChange testProtocolRenameRight = getChangeOfProtocol(filter(protocolChanges, fromSide(RIGHT)),
				"TestProtocol");
		assertNotNull(testProtocolRenameRight);
		assertEquals(CHANGE, testProtocolRenameRight.getKind());
		assertEquals(RIGHT, testProtocolRenameRight.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(testProtocolRenameLeft, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(testProtocolRenameRight, conflict));

		assertNull(getConflictOrConflictOfRefining(testProtocolRenameLeft, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(testProtocolRenameRight, ConflictKind.REAL));

	}

	@Test
	public void testProtocolConflictRenameVsMove() throws IOException {
		final Resource right = input.getMultiple2Rename();
		final Resource left = input.getMultiple2Move();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());

		ProtocolChange testProtocolMove = getChangeOfProtocol(filter(protocolChanges, fromSide(LEFT)), "TestProtocol");
		assertNotNull(testProtocolMove);
		assertEquals(MOVE, testProtocolMove.getKind());
		assertEquals(LEFT, testProtocolMove.getSource());

		ProtocolChange testProtocolRename = getChangeOfProtocol(filter(protocolChanges, fromSide(RIGHT)),
				"TestProtocol_newName");
		assertNotNull(testProtocolRename);
		assertEquals(CHANGE, testProtocolRename.getKind());
		assertEquals(RIGHT, testProtocolRename.getSource());

		assertEquals(0, comparison.getConflicts().size());
		assertNull(testProtocolMove.getConflict());
		assertNull(testProtocolRename.getConflict());
	}

	@Test
	public void testProtocolConflictDeleteVsMove() throws IOException {
		final Resource left = input.getMultiple2Delete();
		final Resource right = input.getMultiple2Move();
		final Resource origin = input.getMultiple2Origin();

		Comparison comparison = compare(left, right, origin);

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(2, protocolChanges.size());

		ProtocolChange testProtocolDelete = getChangeOfProtocol(filter(protocolChanges, fromSide(LEFT)), "TestProtocol");
		assertNotNull(testProtocolDelete);
		assertEquals(DELETE, testProtocolDelete.getKind());
		assertEquals(LEFT, testProtocolDelete.getSource());

		ProtocolChange testProtocolMove = getChangeOfProtocol(filter(protocolChanges, fromSide(RIGHT)), "TestProtocol");
		assertNotNull(testProtocolMove);
		assertEquals(MOVE, testProtocolMove.getKind());
		assertEquals(RIGHT, testProtocolMove.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(testProtocolDelete, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(testProtocolMove, conflict));
	}

	private ProtocolChange getAndAssertSingle1ProtocolChange(Comparison comparison) {
		// we expect one protocol change
		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, size(protocolChanges));
		final ProtocolChange protocolChange = protocolChanges.get(0);

		// assert properties of protocol change
		assertProtocolWithName(protocolChange.getDiscriminant(), PROTOCOL_NAME_SINGLE_1);
		return protocolChange;
	}

	private List<ProtocolChange> getProtocolChanges(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getDifferences(), ProtocolChange.class));
	}

	private void assertProtocolWithName(EObject protocol, String expectedProtocolName) {
		assertTrue(protocol instanceof Collaboration);
		assertEquals(expectedProtocolName, COLLABORATION_NAME.apply(protocol));
	}

	private ProtocolChange getChangeOfProtocol(Iterable<ProtocolChange> protocolChanges, String protocolName) {
		final Optional<ProtocolChange> optional = tryFind(protocolChanges,
				PROTOCOL_CHANGE_WITH_COLLABORATION_NAME_PREDICATE(protocolName));
		return optional.orNull();
	}
}
