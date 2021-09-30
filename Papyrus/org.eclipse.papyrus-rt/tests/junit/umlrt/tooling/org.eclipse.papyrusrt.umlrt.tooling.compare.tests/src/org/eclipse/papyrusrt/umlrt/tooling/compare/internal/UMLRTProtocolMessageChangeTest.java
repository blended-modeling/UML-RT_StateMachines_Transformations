/*****************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *     Philip Langer - switch to batch merger
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.size;
import static org.eclipse.emf.compare.DifferenceKind.ADD;
import static org.eclipse.emf.compare.DifferenceKind.CHANGE;
import static org.eclipse.emf.compare.DifferenceKind.DELETE;
import static org.eclipse.emf.compare.DifferenceSource.LEFT;
import static org.eclipse.emf.compare.DifferenceSource.RIGHT;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.junit.Test;

import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class UMLRTProtocolMessageChangeTest extends AbstractUMLRTTest {
	private static final String PROTOCOL_MESSAGE_1_NAME = "inOut1";
	private static final String PROTOCOL_MESSAGE_2_NAME = "in1";

	private UMLRTProtocolMessageChangeTestData input = new UMLRTProtocolMessageChangeTestData();

	@Override
	protected AbstractUMLRTInputData getInput() {
		return input;
	}

	/**
	 * <b>left:</b> a protocol with one {@link RTMessageKind#IN_OUT} protocol
	 * message <br>
	 * <b>right:</b> an empty protocol (no protocol messages)<br>
	 * 
	 * <p>
	 * The comparison of the two sides should contain one
	 * {@link ProtocolMessageChange} diff of type {@link DifferenceKind#ADD}.
	 */
	@Test
	public void testAddProtocolMessageWithoutParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageLeft();
		final Resource right = input.getAddDeleteProtocolMessageRight();
		Comparison comparison = compare(left, right);

		// verify comparison
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(ADD, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		assertRTMessage(protocolMessageChange, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);

		// verify merge
		mergeLeftToRight(protocolMessageChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	/**
	 * <b>left:</b> an empty protocol (no protocol messages)<br>
	 * <b>right:</b> a protocol with one {@link RTMessageKind#IN_OUT} protocol
	 * message
	 * 
	 * <p>
	 * The comparison of the two sides should contain one
	 * {@link ProtocolMessageChange} diff of type {@link DifferenceKind#DELETE}.
	 */
	@Test
	public void testDeleteProtocolMessageWithoutParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageRight();
		final Resource right = input.getAddDeleteProtocolMessageLeft();
		Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(DELETE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		assertRTMessage(protocolMessageChange, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);

		// verify merge
		mergeLeftToRight(protocolMessageChange);

		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	/**
	 * <b>left:</b> a protocol with one {@link RTMessageKind#IN_OUT} and one
	 * {@link RTMessageKind#IN} protocol messages <br>
	 * <b>right:</b> an empty protocol (no protocol messages)<br>
	 * 
	 * <p>
	 * The comparison of the two sides should contain two
	 * {@link ProtocolMessageChange} diffs of type {@link DifferenceKind#ADD}.
	 */
	@Test
	public void testAddAManyProtocolMessagesWithoutParameter() throws IOException {
		final Resource left = input.getAddDeleteManyProtocolMessagesLeft();
		final Resource right = input.getAddDeleteManyProtocolMessagesRight();
		Comparison comparison = compare(left, right);

		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange protocolMessageChange2 = protocolMessageChanges.get(1);
		assertEquals(ADD, protocolMessageChange1.getKind());
		assertEquals(ADD, protocolMessageChange2.getKind());
		assertEquals(LEFT, protocolMessageChange1.getSource());
		assertEquals(LEFT, protocolMessageChange2.getSource());

		if (((NamedElement) protocolMessageChange1.getDiscriminant()).getName().equals(PROTOCOL_MESSAGE_1_NAME)) {
			assertRTMessage(protocolMessageChange1, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);
			assertRTMessage(protocolMessageChange2, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);
		} else if (((NamedElement) protocolMessageChange1.getDiscriminant()).getName()
				.equals(PROTOCOL_MESSAGE_2_NAME)) {
			assertRTMessage(protocolMessageChange1, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);
			assertRTMessage(protocolMessageChange2, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);
		} else {
			fail("Unexpected discriminant " + protocolMessageChange1.getDiscriminant());
		}

		// verify merge
		mergeLeftToRight(protocolMessageChange1, protocolMessageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());

	}

	/**
	 * <b>left:</b> an empty protocol (no protocol messages)<br>
	 * <b>right:</b> a protocol with one {@link RTMessageKind#IN_OUT} and one
	 * {@link RTMessageKind#IN} protocol messages <br>
	 * 
	 * <p>
	 * The comparison of the two sides should contain two
	 * {@link ProtocolMessageChange} diffs of type {@link DifferenceKind#DELETE}
	 * .
	 */
	@Test
	public void testDeleteManyProtocolMessagesWithoutParameter() throws IOException {
		final Resource left = input.getAddDeleteManyProtocolMessagesRight();
		final Resource right = input.getAddDeleteManyProtocolMessagesLeft();
		Comparison comparison = compare(left, right);

		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange protocolMessageChange2 = protocolMessageChanges.get(1);
		assertEquals(DELETE, protocolMessageChange1.getKind());
		assertEquals(DELETE, protocolMessageChange2.getKind());
		assertEquals(LEFT, protocolMessageChange1.getSource());
		assertEquals(LEFT, protocolMessageChange2.getSource());

		if (((NamedElement) protocolMessageChange1.getDiscriminant()).getName().equals(PROTOCOL_MESSAGE_1_NAME)) {
			assertRTMessage(protocolMessageChange1, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);
			assertRTMessage(protocolMessageChange2, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);
		} else if (((NamedElement) protocolMessageChange1.getDiscriminant()).getName()
				.equals(PROTOCOL_MESSAGE_2_NAME)) {
			assertRTMessage(protocolMessageChange1, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);
			assertRTMessage(protocolMessageChange2, RTMessageKind.IN_OUT, PROTOCOL_MESSAGE_1_NAME);
		} else {
			fail("Unexpected discriminant " + protocolMessageChange1.getDiscriminant());
		}

		// verify merge
		mergeLeftToRight(protocolMessageChange1, protocolMessageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testAddProtocolMessageWithParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageWithParameterLeft();
		final Resource right = input.getAddDeleteProtocolMessageWithParameterRight();
		Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(ADD, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testDeleteProtocolMessageWithParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageWithParameterRight();
		final Resource right = input.getAddDeleteProtocolMessageWithParameterLeft();
		Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(DELETE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, PROTOCOL_MESSAGE_2_NAME);

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testAddProtocolMessageParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageParameterLeft();
		final Resource right = input.getAddDeleteProtocolMessageParameterRight();
		final Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(0, size(protocolMessageChanges));
	}

	@Test
	public void testDeleteProtocolMessageParameter() throws IOException {
		final Resource left = input.getAddDeleteProtocolMessageParameterRight();
		final Resource right = input.getAddDeleteProtocolMessageParameterLeft();
		final Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(0, size(protocolMessageChanges));
	}

	@Test
	public void testRenameProtocolMessageParameterLeft() throws IOException {
		final Resource left = input.getRenameProtocolParameterLeft();
		final Resource right = input.getRenameProtocolParameterRight();
		final Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(0, size(protocolMessageChanges));
	}

	@Test
	public void testRenameProtocolMessageParameterRight() throws IOException {
		final Resource left = input.getRenameProtocolParameterRight();
		final Resource right = input.getRenameProtocolParameterLeft();
		final Comparison comparison = compare(left, right);
		final List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(0, size(protocolMessageChanges));
	}

	@Test
	public void testRenameMessageWithoutParameterLeft() throws IOException {
		final Resource left = input.getRenameProtocolMessageWithoutParameterLeft();
		final Resource right = input.getRenameProtocolMessageWithoutParameterRight();
		Comparison comparison = compare(left, right);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(CHANGE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		// rename from "in" to "in_newName"
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, "in_newName");

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testRenameMessageWithoutParameterRight() throws IOException {
		final Resource left = input.getRenameProtocolMessageWithoutParameterRight();
		final Resource right = input.getRenameProtocolMessageWithoutParameterLeft();
		Comparison comparison = compare(left, right);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(CHANGE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		// rename from "in_newName" to "in"
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, "in");

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testRenameMessageWithParameterLeft() throws IOException {
		final Resource left = input.getRenameProtocolMessageWithParameterLeft();
		final Resource right = input.getRenameProtocolMessageWithParameterRight();
		Comparison comparison = compare(left, right);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(CHANGE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		// rename from "in" to "in_newName"
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, "in_newName");

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testRenameMessageWithParameterRight() throws IOException {
		final Resource left = input.getRenameProtocolMessageWithParameterRight();
		final Resource right = input.getRenameProtocolMessageWithParameterLeft();
		Comparison comparison = compare(left, right);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, size(protocolMessageChanges));

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(CHANGE, protocolMessageChange.getKind());
		assertEquals(LEFT, protocolMessageChange.getSource());
		// rename from "in_newName" to "in"
		assertRTMessage(protocolMessageChange, RTMessageKind.IN, "in");

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictAddProtocolMessageSameInterfaceThreeWay() throws IOException {
		final Resource origin = input.getAddProtocolMessageSameInterfacesOrigin();
		final Resource left = input.getAddProtocolMessageSameInterfacesLeft();
		final Resource right = input.getAddProtocolMessageSameInterfacesRight();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());

		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());
		assertEquals(DifferenceKind.ADD, messageChange1.getKind());
		assertEquals(DifferenceKind.ADD, messageChange2.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictAddProtocolMessageDifferentInterfacesThreeWay() throws IOException {
		final Resource origin = input.getAddProtocolMessageDifferentInterfacesOrigin();
		final Resource left = input.getAddProtocolMessageDifferentInterfacesLeft();
		final Resource right = input.getAddProtocolMessageDifferentInterfacesRight();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());

		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());
		assertEquals(DifferenceKind.ADD, messageChange1.getKind());
		assertEquals(DifferenceKind.ADD, messageChange2.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictAddProtocolMessage1AddProtocolMessage2SameNameThreeWay() throws IOException {
		final Resource origin = input.getModelWithProtocol();
		final Resource left = input.getProtocolWithProtocolMessage();
		final Resource right = input.getProtocolMessageSameName();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());
		assertEquals(DifferenceKind.ADD, messageChange1.getKind());
		assertEquals(DifferenceKind.ADD, messageChange2.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictAddTwoProtocolMessageDeleteProtocolThreeWay() throws IOException {
		final Resource origin = input.getModelWithProtocol();
		final Resource left = input.getProtocolWithTwoProtocolMessages();
		final Resource right = input.getEmptyModel();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceKind.ADD, messageChange1.getKind());
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertEquals(DifferenceKind.ADD, messageChange2.getKind());
		assertEquals(DifferenceSource.LEFT, messageChange2.getSource());

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DifferenceSource.RIGHT, protocolChange.getSource());
		assertEquals(DifferenceKind.DELETE, protocolChange.getKind());

		final Conflict conflict1 = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict1);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(protocolChange, conflict1));

		final Conflict conflict2 = getConflictOrConflictOfRefining(messageChange2, ConflictKind.REAL);
		assertNotNull(conflict2);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(protocolChange, conflict2));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(protocolChange, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictAddProtocolMessageDeleteProtocolThreeWay() throws IOException {
		final Resource origin = input.getModelWithProtocol();
		final Resource left = input.getProtocolWithProtocolMessage();
		final Resource right = input.getEmptyModel();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange = protocolMessageChanges.get(0);
		assertEquals(DifferenceKind.ADD, messageChange.getKind());
		assertEquals(DifferenceSource.LEFT, messageChange.getSource());


		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DifferenceSource.RIGHT, protocolChange.getSource());
		assertEquals(DifferenceKind.DELETE, protocolChange.getKind());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(protocolChange, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(protocolChange, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictAddProtocolMessageRenameProtocolThreeWay() throws IOException {
		final Resource origin = input.getModelWithProtocol();
		final Resource left = input.getProtocolWithProtocolMessage();
		final Resource right = input.getProtocolRename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());

		ProtocolMessageChange protocolMessageChange = protocolMessageChanges.get(0);
		assertEquals(DifferenceKind.ADD, protocolMessageChange.getKind());
		assertEquals(DifferenceSource.LEFT, protocolMessageChange.getSource());
		assertEquals(0, comparison.getConflicts().size());

		assertEquals(1, getProtocolChanges(comparison).size());

		// verify merge
		mergeLeftToRight(protocolMessageChange);
		comparison = compare(left, right);
		assertEquals(0, size(filter(comparison.getDifferences(), fromSide(RIGHT))));
		assertEquals(0, getProtocolMessageChanges(comparison).size());
		assertEquals(1, getProtocolChanges(comparison).size());
	}

	@Test
	public void testConflictAddProtocolMessage1RenameProtocolMessage2SameInterfaceThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessage2();
		final Resource left = input.getProtocolMessages1and2();
		final Resource right = input.getProtocolMessage2Rename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);

		if (messageChange1.getKind() == DifferenceKind.CHANGE) {
			assertEquals(DifferenceSource.RIGHT, messageChange1.getSource());
			assertEquals(DifferenceKind.ADD, messageChange2.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange2.getSource());
		} else {
			assertEquals(DifferenceKind.ADD, messageChange1.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
			assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange2.getSource());
		}
		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictAddProtocolMessageDeleteProtocolMessageSameInterfaceThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessage2();
		final Resource left = input.getProtocolMessages1and2();
		final Resource right = input.getModelWithProtocol();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);

		if (messageChange1.getKind() == DifferenceKind.DELETE) {
			assertEquals(DifferenceSource.RIGHT, messageChange1.getSource());
			assertEquals(DifferenceKind.ADD, messageChange2.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange2.getSource());
		} else {
			assertEquals(DifferenceKind.ADD, messageChange1.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
			assertEquals(DifferenceKind.DELETE, messageChange2.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange2.getSource());
		}
		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange2, messageChange1);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictDeleteProtocolMessage1DeleteProtocolMessage2SameInterfaceThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessages1and2();
		final Resource left = input.getProtocolMessage2();
		final Resource right = input.getProtocolWithProtocolMessage();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);

		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());
		assertEquals(DifferenceKind.DELETE, messageChange2.getKind());
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictDeleteProtocolMessageDeleteProtocolThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getEmptyModel();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DifferenceSource.RIGHT, protocolChange.getSource());
		assertEquals(DifferenceKind.DELETE, protocolChange.getKind());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(protocolChange, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(protocolChange, ConflictKind.REAL));
	}

	@Test
	public void testConflictDeleteProtocolMessageRenameProtocolThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getProtocolRenameWithProtocolMessage();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());

		assertEquals(0, comparison.getConflicts().size());
		assertEquals(1, getProtocolChanges(comparison).size());

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertEquals(0, size(filter(comparison.getDifferences(), fromSide(RIGHT))));
		assertEquals(0, getProtocolMessageChanges(comparison).size());
		assertEquals(1, getProtocolChanges(comparison).size());
	}

	@Test
	public void testConflictDeleteProtocolMessageRenameProtocolMessageThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getProtocolWithProtocolMessageRename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);

		if (messageChange1.getKind() == DifferenceKind.DELETE) {
			assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
			assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange2.getSource());
		} else {
			assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange1.getSource());
			assertEquals(DifferenceKind.DELETE, messageChange2.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange2.getSource());
		}
		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(messageChange2, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictDeleteProtocolMessageAddParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getProtocolMessageWithParameter();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict);

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictDeleteProtocolMessageRenameParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getProtocolMessageWithParameterRename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict);

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictDeleteProtocolMessageDeleteParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getProtocolWithProtocolMessage();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO);
		assertNotNull(conflict);

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL));
	}

	@Test
	public void testConflictDeleteSameProtocolMessageThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getModelWithProtocol();
		final Resource right = input.getModelWithProtocol();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertEquals(DifferenceKind.DELETE, messageChange1.getKind());
		assertEquals(DifferenceKind.DELETE, messageChange2.getKind());
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(messageChange2, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.REAL));

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictRenameProtocolMessageDeleteProtocolThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getProtocolWithProtocolMessageRename();
		final Resource right = input.getEmptyModel();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		if (messageChange1.getKind() == DifferenceKind.CHANGE) {
			assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
			assertEquals(DifferenceKind.DELETE, messageChange2.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange2.getSource());
		} else {
			assertEquals(DifferenceKind.DELETE, messageChange1.getKind());
			assertEquals(DifferenceSource.RIGHT, messageChange1.getSource());
			assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
			assertEquals(DifferenceSource.LEFT, messageChange2.getSource());
		}

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(messageChange2, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictRenameProtocolMessageRenameProtocolThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getProtocolWithProtocolMessageRename();
		final Resource right = input.getProtocolRenameWithProtocolMessage();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());

		assertEquals(0, comparison.getConflicts().size());

		List<ProtocolChange> protocolChanges = getProtocolChanges(comparison);
		assertEquals(1, protocolChanges.size());
		ProtocolChange protocolChange = protocolChanges.get(0);
		assertEquals(DifferenceSource.RIGHT, protocolChange.getSource());
		assertEquals(DifferenceKind.CHANGE, protocolChange.getKind());

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertEquals(0, size(filter(comparison.getDifferences(), fromSide(RIGHT))));
		assertEquals(0, getProtocolMessageChanges(comparison).size());
		assertEquals(1, getProtocolChanges(comparison).size());
	}

	@Test
	public void testConflictRenameProtocolMessage1RenameProtocolMessage2SameInterfaceThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessages1and2();
		final Resource left = input.getProtocolMessages1Renameand2();
		final Resource right = input.getProtocolMessages1and2Rename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());
		assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1, messageChange2);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictRenameProtocolMessageAddParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getProtocolWithProtocolMessageRename();
		final Resource right = input.getProtocolMessageWithParameter();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertNotEquals(0, comparison.getDifferences().size());
		assertEquals(0, getProtocolMessageChanges(comparison).size());
	}

	@Test
	public void testConflictRenameProtocolMessageDeleteParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getProtocolMessageRenameWithParameter();
		final Resource right = input.getProtocolWithProtocolMessage();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertNotEquals(0, comparison.getDifferences().size());
		assertEquals(0, getProtocolMessageChanges(comparison).size());
	}

	@Test
	public void testConflictRenameProtocolMessageRenameParameterThreeWay() throws IOException {
		final Resource origin = input.getProtocolMessageWithParameter();
		final Resource left = input.getProtocolMessageRenameWithParameter();
		final Resource right = input.getProtocolMessageWithParameterRename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(1, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		assertEquals(DifferenceSource.LEFT, messageChange1.getSource());
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());

		assertEquals(0, comparison.getConflicts().size());

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertNotEquals(0, comparison.getDifferences().size());
		assertEquals(0, getProtocolMessageChanges(comparison).size());
	}

	@Test
	public void testConflictRenameProtocolMessageSameNameThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getProtocolWithProtocolMessageRename();
		final Resource right = input.getProtocolWithProtocolMessageRename();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());
		assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(messageChange2, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.REAL));

		// verify merge
		mergeLeftToRight(messageChange1);
		comparison = compare(left, right);
		assertEquals(0, comparison.getDifferences().size());
	}

	@Test
	public void testConflictRenameProtocolMessageDifferentNameThreeWay() throws IOException {
		final Resource origin = input.getProtocolWithProtocolMessage();
		final Resource left = input.getProtocolWithProtocolMessageRename();
		final Resource right = input.getProtocolWithProtocolMessageRename2();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(2, protocolMessageChanges.size());
		ProtocolMessageChange messageChange1 = protocolMessageChanges.get(0);
		ProtocolMessageChange messageChange2 = protocolMessageChanges.get(1);
		assertEquals(DifferenceKind.CHANGE, messageChange1.getKind());
		assertEquals(DifferenceKind.CHANGE, messageChange2.getKind());
		assertNotEquals(messageChange1.getSource(), messageChange2.getSource());

		final Conflict conflict = getConflictOrConflictOfRefining(messageChange1, ConflictKind.REAL);
		assertNotNull(conflict);
		assertTrue(containsDiffOrAnyOfItsRefiningDiffs(messageChange2, conflict));

		assertNull(getConflictOrConflictOfRefining(messageChange1, ConflictKind.PSEUDO));
		assertNull(getConflictOrConflictOfRefining(messageChange2, ConflictKind.PSEUDO));
	}

	@Test
	public void testConflictAddParameterSameProtocolMessageThreeWay() throws IOException {
		final Resource origin = input.getAddParameterSameProtocolMessageOrigin();
		final Resource left = input.getAddParameterSameProtocolMessageLeft();
		final Resource right = input.getAddParameterSameProtocolMessageRight();
		Comparison comparison = compare(left, right, origin);

		List<ProtocolMessageChange> protocolMessageChanges = getProtocolMessageChanges(comparison);
		assertEquals(0, protocolMessageChanges.size());
		assertEquals(0, comparison.getConflicts().size());
	}

	private List<ProtocolMessageChange> getProtocolMessageChanges(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getDifferences(), ProtocolMessageChange.class));
	}

	private List<ProtocolChange> getProtocolChanges(Comparison comparison) {
		return Lists.newArrayList(filter(comparison.getDifferences(), ProtocolChange.class));
	}

	private void assertRTMessage(ProtocolMessageChange protocolMessageChange, RTMessageKind kind, String messageName) {
		final EObject discriminant = protocolMessageChange.getDiscriminant();
		assertTrue(discriminant instanceof Operation);
		Operation operation = (Operation) discriminant;
		assertTrue(RTMessageUtils.isRTMessage(operation, kind));
		assertEquals(messageName, operation.getName());
	}

}
