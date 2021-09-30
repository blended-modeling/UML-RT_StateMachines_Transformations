/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editor.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Named;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.AbstractPopupBarTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.PopupBarTool;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for modeling assistants in the RT state machine diagram.
 */
@PluginResource("resource/statemachine/model.di")
@ActiveDiagram("Capsule1::StateMachine")
public class ModelingAssistantsTest extends AbstractStateMachineDiagramTest {

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	@Rule
	public final AnnotationRule<String> diagramName = AnnotationRule.create(ActiveDiagram.class);

	@Rule
	public final FixtureElementRule elementsRule = new FixtureElementRule("test");

	@Named("Capsule1::StateMachine::Region::State1")
	State state;

	@Named("Capsule2::StateMachine::Region::State1")
	State compositeState;

	@Named("Capsule2::StateMachine::Region::State1::Region1")
	Region compositeStateRegion;

	@Test
	public void popupEntryPointOnState() {
		assertPopupAssistant(requireEditPart(state), "RTPseudoState_EntryPointShape", true);
	}

	@Test
	public void popupExitPointOnState() {
		assertPopupAssistant(requireEditPart(state), "RTPseudoState_ExitPointShape", true);
	}

	@Test
	public void noPopupUMLEntryPointOnState() {
		assertPopupAssistant(requireEditPart(state), UMLDIElementTypes.PSEUDOSTATE_ENTRY_POINT_SHAPE, false);
	}

	@Test
	public void noPopupUMLExitPointOnState() {
		assertPopupAssistant(requireEditPart(state), UMLDIElementTypes.PSEUDOSTATE_EXIT_POINT_SHAPE, false);
	}

	@Test
	public void popupInternalTransitionOnState() {
		assertPopupAssistant(requireEditPart(state), "InternalTransition_Label", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupEntryPointOnCompositeState() {
		assertPopupAssistant(requireEditPart(compositeState), "RTPseudoState_EntryPointShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupExitPointOnCompositeState() {
		assertPopupAssistant(requireEditPart(compositeState), "RTPseudoState_ExitPointShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupInternalTransitionOnCompositeState() {
		assertPopupAssistant(requireEditPart(compositeState), "InternalTransition_Label", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupEntryPointOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "RTPseudoState_EntryPointShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupExitPointOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "RTPseudoState_ExitPointShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupInternalTransitionOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "InternalTransition_Label", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupStateOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "State_Shape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupChoiceOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "RTPseudoState_ChoiceShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupJunctionOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "RTPseudoState_JunctionShape", true);
	}

	@Test
	@ActiveDiagram("Capsule2::State1")
	public void popupHistoryOnCompositeStateRegion() {
		assertPopupAssistant(requireEditPart(compositeStateRegion), "RTPseudoState_DeepHistoryShape", true);
	}

	//
	// Test framework
	//

	@Before
	public void activateDiagram() {
		editor.activateDiagram(diagramName.get());

		if (!Objects.equals(editor.getActiveDiagramEditor().getPartName(), diagramName.get())) {
			// Try opening it, then
			editor.openDiagram(diagramName.get());
		}
	}

	void assertPopupAssistant(EditPart editPart, String elementType, boolean expected) {
		assertPopupAssistant(editPart,
				ElementTypeRegistry.getInstance().getType("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine." + elementType),
				expected);
	}

	void assertPopupAssistant(EditPart editPart, IElementType elementType, boolean expected) {
		Optional<PopupBarTool> found = ((List<?>) ModelingAssistantService.getInstance().getTypesForPopupBar(editPart))
				.stream().map(IElementType.class::cast)
				.filter(elementType::equals)
				.map(type -> new PopupBarTool(editPart, type))
				.filter(AbstractPopupBarTool::isCommandEnabled)
				.findAny();

		assertThat((expected ? "Element type not found: " : "Unexpected element type: ") + elementType.getDisplayName(),
				found.isPresent(), is(expected));
	}

	@Override
	protected DiagramEditPart getDiagramEditPart() {
		return editor.getActiveDiagramEditor().getDiagramEditPart();
	}
}
