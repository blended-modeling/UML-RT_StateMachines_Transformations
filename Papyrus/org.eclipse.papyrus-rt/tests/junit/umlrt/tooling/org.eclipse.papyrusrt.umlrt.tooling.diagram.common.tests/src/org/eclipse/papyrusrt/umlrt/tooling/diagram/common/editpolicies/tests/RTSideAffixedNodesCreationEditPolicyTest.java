/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests;

import static org.eclipse.papyrusrt.junit.matchers.NumberFuzzyMatcher.near;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop.CreateCapsulePartAndDisplayCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for initial placement of port views on newly created
 * capsule part views.
 */
@PluginResource("/resource/capsule-parts/model.di")
public class RTSideAffixedNodesCreationEditPolicyTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@Rule
	public final TestRule uiThread = new UIThreadRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	/**
	 * Verify that the new view for a newly created capsule-part initially
	 * has ports located around it in proportion to their placement on the
	 * capsule that defines them in its capsule structure diagram.
	 */
	@Test
	@ActiveDiagram("Capsule2")
	public void relativePortLocationsOnPart() {
		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		Class capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");

		// Simulate drop of Capsule1 from Model Explorer
		IGraphicalEditPart classShape = editor.getActiveDiagram().getChildBySemanticHint("Class_Shape");
		EditPart structure = classShape.getChildBySemanticHint("Class_StructureCompartment");
		ICommand createPart = new CreateCapsulePartAndDisplayCommand(
				capsule2,
				UMLRTElementTypesEnumerator.CAPSULE_PART_ID,
				UMLPackage.Literals.NAMESPACE__OWNED_MEMBER,
				capsule1,
				new Point(100, 100),
				structure,
				false);
		// no need to cancel direct editor anymore
		editor.getEditingDomain().getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(createPart));
		editor.flushDisplayEvents();

		// Get relative port locations
		Port greeter = capsule1.getOwnedPort("greeter", null);
		Port dismisser = capsule1.getOwnedPort("dismisser", null);
		Port receiver = capsule1.getOwnedPort("receiver", null);

		EditPart capsulePartPart = editor.requireEditPart(editor.getActiveDiagram(),
				(EObject) createPart.getCommandResult().getReturnValue());
		EditPart greeterPart = editor.requireEditPart(capsulePartPart, greeter);
		EditPart dismisserPart = editor.requireEditPart(capsulePartPart, dismisser);
		EditPart receiverPart = editor.requireEditPart(capsulePartPart, receiver);

		RelativePortLocation greeterLocation = RelativePortLocation.of(
				getBounds(greeterPart), getBounds(capsulePartPart));
		RelativePortLocation dismisserLocation = RelativePortLocation.of(
				getBounds(dismisserPart), getBounds(capsulePartPart));
		RelativePortLocation receiverLocation = RelativePortLocation.of(
				getBounds(receiverPart), getBounds(capsulePartPart));

		// Verify relative port locations within a tolerance allowing for the
		// difference in scale between the capsule in the capsule structure
		// diagram and the capsule-part typed by that capsule
		assertThat(greeterLocation.side(), is(PositionConstants.EAST));
		assertThat(dismisserLocation.side(), is(PositionConstants.WEST));
		assertThat(receiverLocation.side(), is(PositionConstants.SOUTH));
		assertThat(greeterLocation.relativePosition(), near(25, 5));
		assertThat(dismisserLocation.relativePosition(), near(75, 5));
		assertThat(receiverLocation.relativePosition(), near(33, 5));
	}

	/**
	 * Verify that the new view for a newly created capsule-part has ports that
	 * are about 2/3 the size of the corresponding ports on the capsule type.
	 */
	@Test
	@ActiveDiagram("Capsule2")
	public void portSizesOnPart() {
		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		Class capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");

		// Simulate drop of Capsule1 from Model Explorer
		IGraphicalEditPart classShape = editor.getActiveDiagram().getChildBySemanticHint("Class_Shape");
		EditPart structure = classShape.getChildBySemanticHint("Class_StructureCompartment");
		ICommand createPart = new CreateCapsulePartAndDisplayCommand(
				capsule2,
				UMLRTElementTypesEnumerator.CAPSULE_PART_ID,
				UMLPackage.Literals.NAMESPACE__OWNED_MEMBER,
				capsule1,
				new Point(100, 100),
				structure);
		editor.getEditingDomain().getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(createPart));
		editor.flushDisplayEvents();

		// Cancel the in-line editor because it pushes ports around while it's open
		cancelDirectEditor(editor.getActiveDiagram().getViewer());
		editor.flushDisplayEvents();

		// Get port sizes
		Port greeter = capsule1.getOwnedPort("greeter", null);
		Port dismisser = capsule1.getOwnedPort("dismisser", null);
		Port receiver = capsule1.getOwnedPort("receiver", null);

		EditPart capsulePartPart = editor.requireEditPart(editor.getActiveDiagram(),
				(EObject) createPart.getCommandResult().getReturnValue());
		EditPart greeterPart = editor.requireEditPart(capsulePartPart, greeter);
		EditPart dismisserPart = editor.requireEditPart(capsulePartPart, dismisser);
		EditPart receiverPart = editor.requireEditPart(capsulePartPart, receiver);

		Rectangle greeterBounds = getBounds(greeterPart);
		Rectangle dismisserBounds = getBounds(dismisserPart);
		Rectangle receiverBounds = getBounds(receiverPart);

		// Verify relative port sizes within a tolerance allowing for rounding
		assertThat(greeterBounds.width(), near(11, 1));
		assertThat(greeterBounds.height(), near(11, 1));
		assertThat(dismisserBounds.width(), near(11, 1));
		assertThat(dismisserBounds.height(), near(11, 1));
		assertThat(receiverBounds.width(), near(11, 1));
		assertThat(receiverBounds.height(), near(11, 1));
	}

	//
	// Test framework
	//

	Rectangle getBounds(EditPart editPart) {
		Rectangle result = ((IGraphicalEditPart) editPart).getFigure().getBounds();

		// Always relative to the parent figure
		result = result.getTranslated(((IGraphicalEditPart) editPart.getParent()).getFigure()
				.getBounds().getLocation().getNegated());

		return result;
	}

	/**
	 * Cancels any direct-edit that may be happening within the context of an
	 * edit-part {@code viewer}.
	 * 
	 * @param viewer
	 *            an edit-part viewer
	 */
	void cancelDirectEditor(EditPartViewer viewer) {
		// Taking focus removes it from the cell editor, cancelling the direct edit
		viewer.getControl().setFocus();
	}
}
