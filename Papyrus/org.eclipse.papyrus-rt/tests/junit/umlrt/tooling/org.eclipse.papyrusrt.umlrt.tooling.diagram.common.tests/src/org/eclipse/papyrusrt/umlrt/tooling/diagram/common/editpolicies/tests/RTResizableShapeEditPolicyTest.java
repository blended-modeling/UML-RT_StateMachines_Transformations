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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for initial placement of port views on newly created
 * capsule part views.
 */
@PluginResource("/resource/capsule-parts/model.di")
public class RTResizableShapeEditPolicyTest {
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
	@ActiveDiagram("Capsule3")
	public void relativePortLocationsOnPartMaintained() {
		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		Class capsule3 = (Class) editor.getModel().getOwnedType("Capsule3");
		Property capsulePart = capsule3.getOwnedAttribute("capsule1", capsule1);

		EditPart capsulePartPart = editor.requireEditPart(editor.getActiveDiagram(), capsulePart);

		// Get relative port locations
		Port greeter = capsule1.getOwnedPort("greeter", null);
		Port dismisser = capsule1.getOwnedPort("dismisser", null);
		Port receiver = capsule1.getOwnedPort("receiver", null);

		EditPart greeterPart = editor.requireEditPart(capsulePartPart, greeter);
		EditPart dismisserPart = editor.requireEditPart(capsulePartPart, dismisser);
		EditPart receiverPart = editor.requireEditPart(capsulePartPart, receiver);

		Map<EditPart, RelativePortLocation> originalLocations = new HashMap<>();
		originalLocations.put(greeterPart, locate(greeterPart));
		originalLocations.put(dismisserPart, locate(dismisserPart));
		originalLocations.put(receiverPart, locate(receiverPart));

		// Resize the capsule-part
		ChangeBoundsRequest changeBounds = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		changeBounds.setSizeDelta(new Dimension(0, 100)); // More downwards
		changeBounds.setEditParts(capsulePartPart);
		Command resize = capsulePartPart.getCommand(changeBounds);
		assertThat("No expand command provided", resize, notNullValue());
		assertThat("Expand command not executable", resize.canExecute(), is(true));
		editor.execute(resize);

		assertPortLocations(originalLocations);

		changeBounds = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		changeBounds.setSizeDelta(new Dimension(0, -100)); // Get back to initial value
		changeBounds.setEditParts(capsulePartPart);
		resize = capsulePartPart.getCommand(changeBounds);
		assertThat("No expand command provided", resize, notNullValue());
		assertThat("Expand command not executable", resize.canExecute(), is(true));
		editor.execute(resize);

		assertPortLocations(originalLocations); // we could even compare with original x/y position

		// Resize the capsule-part
		changeBounds = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		changeBounds.setSizeDelta(new Dimension(100, 0)); // More rightwards
		changeBounds.setEditParts(capsulePartPart);
		resize = capsulePartPart.getCommand(changeBounds);
		assertThat("No shrink command provided", resize, notNullValue());
		assertThat("Shrink command not executable", resize.canExecute(), is(true));
		editor.execute(resize);

		assertPortLocations(originalLocations);

		changeBounds = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		changeBounds.setSizeDelta(new Dimension(-100, 0)); // Get back to initial value
		changeBounds.setEditParts(capsulePartPart);
		resize = capsulePartPart.getCommand(changeBounds);
		assertThat("No expand command provided", resize, notNullValue());
		assertThat("Expand command not executable", resize.canExecute(), is(true));
		editor.execute(resize);

		assertPortLocations(originalLocations); // we could even compare with original x/y position

		// Resize the capsule-part
		changeBounds = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		changeBounds.setSizeDelta(new Dimension(100, 100)); // More rightwards & downwards at the same time
		changeBounds.setEditParts(capsulePartPart);
		resize = capsulePartPart.getCommand(changeBounds);
		assertThat("No shrink command provided", resize, notNullValue());
		assertThat("Shrink command not executable", resize.canExecute(), is(true));
		editor.execute(resize);

		assertPortLocations(originalLocations);
	}

	//
	// Test framework
	//

	RelativePortLocation locate(EditPart portPart) {
		return RelativePortLocation.of(getBounds(portPart), getBounds(portPart.getParent()));
	}

	Rectangle getBounds(EditPart editPart) {
		Rectangle result = ((IGraphicalEditPart) editPart).getFigure().getBounds();

		// Always relative to the parent figure
		result = result.getTranslated(((IGraphicalEditPart) editPart.getParent()).getFigure()
				.getBounds().getLocation().getNegated());

		return result;
	}

	void assertPortLocations(Map<EditPart, RelativePortLocation> originalLocations) {
		// Get new relative port locations
		Map<EditPart, RelativePortLocation> newLocations = originalLocations.keySet().stream()
				.collect(HashMap::new,
						(map, editPart) -> map.put(editPart, locate(editPart)),
						HashMap::putAll);

		// Verify relative port locations within a tolerance allowing for the
		// difference in scale between the old and new sizes of the capsule-part
		newLocations.forEach((editPart, location) -> {
			assertThat(location.side(),
					is(originalLocations.get(editPart).side()));
			assertThat(location.relativePosition(),
					near(originalLocations.get(editPart).relativePosition(), 5));
		});
	}
}
