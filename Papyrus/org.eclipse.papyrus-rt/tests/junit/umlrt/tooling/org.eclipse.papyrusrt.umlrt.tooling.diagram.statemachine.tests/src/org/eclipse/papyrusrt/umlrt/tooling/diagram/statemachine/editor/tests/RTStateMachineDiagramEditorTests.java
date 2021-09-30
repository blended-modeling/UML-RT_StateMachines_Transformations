/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editor.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ExtendedConnectionToolEntry;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ExtendedCreationToolEntry;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.common.collect.ImmutableMap;

/**
 * Tests around the RT State Machine diagram editor (palette for example)
 */
@PluginResource("resource/statemachine/model.di")
@ActiveDiagram("Capsule1::StateMachine")
public class RTStateMachineDiagramEditorTests extends AbstractPapyrusTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private static final ImmutableMap<String, Boolean> drawerIdToVisibility = ImmutableMap.of("standardGroup", true, "rtstatemachine.nodes", true, "createNodesGroup", false, "createEdgesGroup", false);

	private static final ImmutableMap<String, List<String>> drawerToTools = ImmutableMap.of("rtstatemachine.nodes", Arrays.asList("rtstatemachine.nodes.rtstate", "rtstatemachine.edges.transition", "rtstatemachine.separator.statetopseudostate",
			"rtstatemachine.nodes.rtpseudostate_choice", "rtstatemachine.nodes.rtpseudostate_deepHistory", "rtstatemachine.nodes.rtpseudostate_entryPoint", "rtstatemachine.nodes.rtpseudostate_exitPoint", "rtstatemachine.nodes.rtpseudostate_initial",
			"rtstatemachine.nodes.rtpseudostate_junction"));

	/**
	 * Verify that when a port is changed to a non-service port, it disappears
	 * from the capsule parts typed by its featuring capsules.
	 */
	@Test
	public void checkRTStateMachineEditorPalette() {
		DiagramEditorWithFlyOutPalette diagramEditor = editor.getActiveDiagramEditor();
		assertThat("Diagram should not be null", diagramEditor, notNullValue());
		PaletteViewer paletteViewer = (PaletteViewer) diagramEditor.getAdapter(PaletteViewer.class);
		assertThat("Palette viewer should not be null", paletteViewer, notNullValue());

		List<?> paletteElements = paletteViewer.getPaletteRoot().getChildren();
		assertThat("There should be 2 drawers", paletteElements.size(), equalTo(2));
		for (Object paletteElement : paletteElements) {
			if (paletteElement instanceof PaletteContainer) {
				PaletteContainer container = (PaletteContainer) paletteElement;

				// visible or not?
				assertThat("Wrong visibility for container: " + container.getLabel() + " - " + container.getId(), container, matchVisibility(drawerIdToVisibility.getOrDefault(container.getId(), false)));

				// check children
				if (drawerToTools.containsKey(container.getId())) {
					checkTools(container, drawerToTools.get(container.getId()));
				}
			}
		}
	}

	/**
	 * Check tools for a given palette container
	 * 
	 * @param container
	 *            the palette container to check
	 */
	protected void checkTools(PaletteContainer container, List<String> toolIds) {
		List<?> tools = container.getChildren();
		for (Object tool : tools) {
			assertThat("Unknown element kind", tool, instanceOf(PaletteEntry.class));
			assertTrue("Unexpected element: " + ((PaletteEntry) tool).getId(), toolIds.contains(((PaletteEntry) tool).getId()));
			assertTrue("Element should be visible", ((PaletteEntry) tool).isVisible());
			if (tool instanceof PaletteSeparator) {
				// nothing
			} else if (tool instanceof ExtendedCreationToolEntry) {
				ExtendedCreationToolEntry creationToolEntry = (ExtendedCreationToolEntry) tool;
				// check element types...
				assertThat("An element type is not valid for node tool: " + creationToolEntry, creationToolEntry.getElementTypes(), not(hasNullItem()));
			} else if (tool instanceof ExtendedConnectionToolEntry) {
				ExtendedConnectionToolEntry connectionToolEntry = (ExtendedConnectionToolEntry) tool;
				assertThat("An element type is not valid for connection tool: " + connectionToolEntry, connectionToolEntry.getElementTypes(), not(hasNullItem()));
			}
		}
	}

	public static Matcher<List<?>> hasNullItem() {
		return new BaseMatcher<List<?>>() {

			@Override
			public boolean matches(Object item) {
				return item instanceof List && ((List) item).contains(null);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("unexpected null value");
			}
		};
	}


	public static Matcher<PaletteContainer> matchVisibility(boolean expected) {
		return new BaseMatcher<PaletteContainer>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(expected ? "visible" : "invisible");
			}

			/**
			 * @see org.hamcrest.BaseMatcher#describeMismatch(java.lang.Object, org.hamcrest.Description)
			 *
			 * @param item
			 * @param description
			 */
			@Override
			public void describeMismatch(Object item, Description description) {
				description.appendText("was ").appendText(((PaletteContainer) item).isVisible() ? "visible" : "invisible");
			}

			@Override
			public boolean matches(Object item) {
				return item instanceof PaletteContainer && expected == ((PaletteContainer) item).isVisible();
			}
		};
	}

}
