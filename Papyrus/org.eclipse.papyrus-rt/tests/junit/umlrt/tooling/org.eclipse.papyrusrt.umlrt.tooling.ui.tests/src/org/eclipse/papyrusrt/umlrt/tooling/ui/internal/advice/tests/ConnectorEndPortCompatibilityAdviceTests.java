/*****************************************************************************
 * Copyright (c) 20167 Zeligsoft (2009) Limited and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.utils.ConnectorUtils;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test class for connector port compatibility advice.
 */
@PluginResource("/resource/advice/ConnectorTestModel.di")
@ActiveDiagram("Top")
public class ConnectorEndPortCompatibilityAdviceTests extends AbstractPapyrusTest {
	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	@ClassRule
	public static final PapyrusEditorFixture editorFixture = new PapyrusRTEditorFixture();

	@CleanUp
	private static IMultiDiagramEditor openPapyrusEditor;

	private static TransactionalEditingDomain transactionalEditingDomain;

	private static Class topCapsule;

	private static Class capsule1;

	private static Property capsule1Part;

	private static Port topCapsuleRelay1;

	private static Port topCapsuleRelay2;

	private static Port topCapsuleInternal;

	private static Port capsule1Relay;

	private static IElementType rtConnectorType;

	private static View topCapsuleView;

	private static View topCapsuleRelay1View;

	private static View topCapsuleRelay2View;

	private static View topCapsuleInternalView;

	private static View capsule1PartView;

	private static View capsule1RelayView;

	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() throws Exception {

		// open project
		openPapyrusEditor = editorFixture.getEditor();

		transactionalEditingDomain = editorFixture.getEditingDomain();

		try {
			initExistingElements();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// force load of the element type registry. Will need to load in in UI thread
		// (provided by the UIThread rule) because of some advice in communication diag:
		// see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages
		ElementTypeSetConfigurationRegistry registry = ElementTypeSetConfigurationRegistry.getInstance();
		Assert.assertNotNull("registry should not be null after init", registry);
		Assert.assertNotNull("element type should not be null", UMLRTElementTypesEnumerator.RT_CONNECTOR);

		rtConnectorType = UMLRTElementTypesEnumerator.RT_CONNECTOR;
	}

	protected static Diagram getDiagram() {
		return editorFixture.getActiveDiagram().getDiagramView();
	}

	/**
	 * Init fields corresponding to element in the test model
	 */
	private static void initExistingElements() throws Exception {
		org.eclipse.uml2.uml.Package rootModel = editorFixture.getModel();
		topCapsule = (Class) rootModel.getPackagedElement("Top", false, UMLPackage.eINSTANCE.getClass_(), false);
		capsule1 = (Class) rootModel.getPackagedElement("Capsule1", false, UMLPackage.eINSTANCE.getClass_(), false);

		capsule1Part = topCapsule.getOwnedAttribute("capsule1_part", null);

		topCapsuleRelay1 = topCapsule.getOwnedPort("top_relay1", null);
		topCapsuleRelay2 = topCapsule.getOwnedPort("top_relay2", null);
		topCapsuleInternal = topCapsule.getOwnedPort("top_internal", null);

		capsule1Relay = capsule1.getOwnedPort("capsule1_relay", null);

		topCapsuleView = getView(getDiagram(), topCapsule, "" + "Class_Shape", false);
		capsule1PartView = getView(topCapsuleView, capsule1Part, "" + "Property_Shape", true);

		topCapsuleRelay1View = getView(topCapsuleView, topCapsuleRelay1, "" + "Port_Shape", true);
		topCapsuleRelay2View = getView(topCapsuleView, topCapsuleRelay2, "" + "Port_Shape", true);
		topCapsuleInternalView = getView(topCapsuleView, topCapsuleInternal, "" + "Port_Shape", true);

		capsule1RelayView = getView(capsule1PartView, capsule1Relay, "" + "Port_Shape", true);

	}

	protected static View getView(View containerView, EObject semanticElement, String graphicalHint, boolean recursive) {
		for (Object view : containerView.getChildren()) {
			if (semanticElement.equals(((View) view).getElement())) {
				if (graphicalHint.equals(((View) view).getType())) {
					return (View) view;
				}

			} else if (recursive) {
				View subview = getView((View) view, semanticElement, graphicalHint, recursive);
				if (subview != null) {
					return subview;
				}
			}
		}
		return null;
	}

	// Both connector ends must be port.
	@Test
	public void testCreateConnectorFromExternalBehaviorPortToPart() throws Exception {
		runConnectorCreationTest(topCapsuleRelay1, null, topCapsuleRelay1View, topCapsuleRelay2, null, topCapsuleRelay2View, true);
	}

	// Relay port (wired non-behavior service port) <-> Wired service port on a capsule part. This will be a delegation connector.
	@Test
	public void testCreateConnectorFromRelayToMainCapsuleRelay() throws Exception {
		runConnectorCreationTest(topCapsuleRelay1, null, topCapsuleRelay1View, topCapsuleInternal, null, topCapsuleInternalView, true);
	}

	// Wired service port on capsule part <-> Wired service port on capsule part. This will be an assembly connector.
	@Test
	public void testCreateConnectorFromRelayToCompatiblePartRelay() throws Exception {
		runConnectorCreationTest(topCapsuleRelay1, null, topCapsuleRelay1View, capsule1Relay, capsule1Part, capsule1RelayView, true);
	}


	protected void runConnectorCreationTest(Property source, Property sourcePartWithPort, View sourceView, Property target, Property targetPartWithPort, View targetView, boolean canCreate) {
		Assert.assertTrue("Editor should not be dirty before test", !openPapyrusEditor.isDirty());
		Command command = getCreateConnectorCommand(source, sourcePartWithPort, sourceView, target, targetPartWithPort, targetView, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (canCreate) {
			if (command != null) {
				if (command.canExecute()) {
					transactionalEditingDomain.getCommandStack().execute(command);
					Connector connector = getExecutionResult(command);
					verifyConnector(connector, source, target, sourcePartWithPort, targetPartWithPort);
					transactionalEditingDomain.getCommandStack().undo();
					Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
					transactionalEditingDomain.getCommandStack().redo();
					verifyConnector(connector, source, target, sourcePartWithPort, targetPartWithPort);
					transactionalEditingDomain.getCommandStack().undo();
					// assert editor is not dirty
					Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
				} else {
					fail("Command is supposed to be executable but it is not executable");
				}

			} else {
				fail("Command is supposed to be executable but it is null");
			}
		} else {
			if (command != null) {
				if (command.canExecute()) {
					fail("Command is supposed to be not executable but it is executable");
				}
			}
		}
	}

	protected Connector getExecutionResult(Command command) {
		Collection<?> results = command.getResult();
		if (results.size() > 0) {
			for (Object result : results) {
				if (result instanceof Connector) {
					return (Connector) result;
				}
			}
		}
		return null;
	}

	protected void verifyConnector(Connector connector, Property source, Property target, Property sourcePartWithPort, Property targetPartWithPort) {
		if (connector == null) {
			fail("impossible to find the created connector. Something went wrong during creation of the connector");
		}

		// Check connector ends
		Assert.assertEquals("there should be 2 connector ends", 2, connector.getEnds().size());

		ConnectorEnd sourceEnd = connector.getEnds().get(0);
		Assert.assertEquals("The first connector end should point to source", source, sourceEnd.getRole());
		if (sourcePartWithPort != null) {
			Assert.assertEquals("The first connector end partWithPort should be sourcePartWithPort", sourcePartWithPort, sourceEnd.getPartWithPort());
		} else {
			Assert.assertNull("The first connector end partWithPort should be null", sourceEnd.getPartWithPort());
		}

		ConnectorEnd targetEnd = connector.getEnds().get(1);
		Assert.assertEquals("The second connector end should point to target", target, targetEnd.getRole());
		if (targetPartWithPort != null) {
			Assert.assertEquals("The second connector end partWithPort should be targetPartWithPort", targetPartWithPort, targetEnd.getPartWithPort());
		} else {
			Assert.assertNull("The second connector end partWithPort should be null", targetEnd.getPartWithPort());
		}


	}

	protected Command getCreateConnectorCommand(Property sourcePort, Property sourcePartWithPort, View sourceView, Property targetPort, Property targetPartWithPort, View targetView, boolean canCreate) {
		StructuredClassifier container = new ConnectorUtils().deduceContainer(sourceView, targetView);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(container);
		CreateRelationshipRequest request = new CreateRelationshipRequest(transactionalEditingDomain, container, sourcePort, targetPort, rtConnectorType);
		// set Parameters
		request.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW, sourceView);
		request.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW, targetView);

		ICommand command = elementEditService.getEditCommand(request);
		if (command != null) {
			return GMFtoEMFCommandWrapper.wrap(command);
		}
		return null;
	}

	/**
	 * Creates the element in the givesn owner element, undo and redo the action
	 *
	 * @param owner
	 *            owner of the new element
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 */
	protected Command getCreateChildCommand(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(owner);
		ICommand command = elementEditService.getEditCommand(new CreateElementRequest(owner, hintedType));

		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (command != null && command.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", command);
			assertTrue("Command should be executable", command.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(command);
			return emfCommand;
		}
		return null;
	}

	/**
	 * After a possibly failed test, unwinds the undo stack as much as possible
	 * to try to return to a clean editor.
	 */
	@After
	public void unwindUndoStack() {
		CommandStack stack = transactionalEditingDomain.getCommandStack();
		while (stack.canUndo()) {
			stack.undo();
		}
	}
}
