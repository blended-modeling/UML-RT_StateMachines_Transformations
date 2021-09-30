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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for resize handles on ports.
 */
@PluginResource("/resource/capsule-parts/model.di")
public class RTPortResizableEditPolicyTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@Rule
	public final TestRule uiThread = new UIThreadRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	/**
	 * Verify that a port on a part is not resizable (it has only selection
	 * handles and the move handle).
	 */
	@Test
	@ActiveDiagram("Capsule3")
	public void portOnPartHasNoResizeHandles() {
		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		Class capsule3 = (Class) editor.getModel().getOwnedType("Capsule3");
		Property capsulePart = capsule3.getOwnedAttribute("capsule1", capsule1);

		EditPart capsulePartPart = editor.requireEditPart(editor.getActiveDiagram(), capsulePart);

		// Get a port edit-part
		Port greeter = capsule1.getOwnedPort("greeter", null);
		EditPart greeterPart = editor.requireEditPart(capsulePartPart, greeter);

		// Select it
		editor.select(greeterPart);

		// Get its handles
		List<AbstractHandle> handles = getHandles(greeterPart);

		// Should only have the move handle and the
		// handles at the corners
		assertThat("Port has resize handles", handles.size(), is(5));
	}

	/**
	 * Verify that a port on a capsule is not resizable (it has only selection
	 * handles and the move handle).
	 */
	@Test
	@ActiveDiagram("Capsule1")
	public void portOnCapsuleHasNoResizeHandles() {
		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		EditPart capsulePart = editor.requireEditPart(editor.getActiveDiagram(), capsule1);

		// Get a port edit-part
		Port greeter = capsule1.getOwnedPort("greeter", null);
		EditPart greeterPart = editor.requireEditPart(capsulePart, greeter);

		// Select it
		editor.select(greeterPart);

		// Get its handles
		List<AbstractHandle> handles = getHandles(greeterPart);

		// Should only have the move handle and the
		// handles at the corners
		assertThat("Port has resize handles", handles.size(), is(5));
	}

	//
	// Test framework
	//

	List<AbstractHandle> getHandles(EditPart editPart) {
		IFigure handleLayer = LayerManager.Helper.find(editPart).getLayer(LayerConstants.HANDLE_LAYER);
		return ((List<?>) handleLayer.getChildren()).stream()
				.filter(AbstractHandle.class::isInstance)
				.map(AbstractHandle.class::cast)
				.filter(h -> isOwnedBy(h, editPart))
				.collect(Collectors.toList());
	}

	private boolean isOwnedBy(AbstractHandle handle, EditPart editPart) {
		boolean result = false;

		try {
			Method method = AbstractHandle.class.getDeclaredMethod("getOwner");
			method.setAccessible(true);
			result = method.invoke(handle) == editPart;
		} catch (Exception e) {
			// Must not be the owner, then
		}

		return result;
	}
}
