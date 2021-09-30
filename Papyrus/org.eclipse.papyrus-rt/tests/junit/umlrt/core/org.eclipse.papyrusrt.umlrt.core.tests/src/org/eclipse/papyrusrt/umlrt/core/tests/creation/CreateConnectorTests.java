/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 492565, 467545, 510782
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import javax.inject.Named;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.service.types.utils.ConnectorUtils;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for connector creation.
 */
@PluginResource("/resource/ConnectorTestModel.di")
public class CreateConnectorTests extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	private ElementTypeRegistry reg;

	private TransactionalEditingDomain transactionalEditingDomain;

	@Named("Main")
	private Package model;

	@Named("Main::Capsule1")
	private Class mainCapsule;

	@Named("Main::SourceCapsule")
	private Class sourceCapsule;

	@Named("Main::CompatibleCapsule")
	private Class compatibleCapsule;

	@Named("Main::IncompatibleCapsule")
	private Class incompatibleCapsule;

	@Named("Main::Capsule1::sourceCapsule")
	private Property sourceCapsulePart;

	@Named("Main::Capsule1::compatibleCapsule")
	private Property compatibleCapsulePart;

	@Named("Main::Capsule1::incompatibleCapsule")
	private Property incompatibleCapsulePart;

	@Named("Main::Capsule1::spp")
	private Port mainCapsuleSPP;

	@Named("Main::Capsule1::sap")
	private Port mainCapsuleSAP;

	@Named("Main::Capsule1::externalBehaviorPort")
	private Port mainCapsuleExternalBehaviorPort;

	@Named("Main::Capsule1::internalBehaviorPort")
	private Port mainCapsuleInternalBehaviorPort;

	@Named("Main::Capsule1::relay")
	private Port mainCapsuleRelay;

	@Named("Main::SourceCapsule::spp")
	private Port sourceCapsuleSPP;

	@Named("Main::SourceCapsule::externalBehaviorPort")
	private Port sourceCapsuleExternalBehaviorPort;

	@Named("Main::SourceCapsule::sap")
	private Port sourceCapsuleSAP;

	@Named("Main::SourceCapsule::internalBehaviorPort")
	private Port sourceCapsuleInternalBehaviorPort;

	@Named("Main::SourceCapsule::relay")
	private Port sourceCapsuleRelay;

	@Named("Main::CompatibleCapsule::spp")
	private Port compatibleCapsuleSPP;

	@Named("Main::CompatibleCapsule::externalBehaviorPort")
	private Port compatibleCapsuleExternalBehaviorPort;

	@Named("Main::CompatibleCapsule::sap")
	private Port compatibleCapsuleSAP;

	@Named("Main::CompatibleCapsule::relay")
	private Port compatibleCapsuleRelay;

	@Named("Main::CompatibleCapsule::internalBehaviorPort")
	private Port compatibleCapsuleInternalBehaviorPort;

	private IElementType rtConnectorType;

	private Diagram mainCapsuleDiagram;

	private View mainCapsuleView;

	private View sourceCapsulePartView;

	private View compatibleCapsulePartView;


	private View sourceCapsuleSPPView;

	private View sourceCapsuleExternalBehaviorPortView;

	private View sourceCapsuleSAPView;

	private View sourceCapsuleRelayView;

	private View compatibleCapsuleSPPView;

	private View compatibleCapsuleExternalBehaviorPortView;

	private View compatibleCapsuleSAPView;

	private View compatibleCapsuleInternalBehaviorPortView;

	private View compatibleCapsuleRelayView;
	private View mainCapsuleRelayView;

	/**
	 * Constructor.
	 *
	 */
	public CreateConnectorTests() {
		reg = ElementTypeRegistry.getInstance();
		rtConnectorType = reg.getType("org.eclipse.papyrusrt.umlrt.core.RTConnector");
		transactionalEditingDomain = modelSet.getEditingDomain();
	}

	protected static Diagram getDiagram(EObject representedElement) {
		return DiagramUtils.getAssociatedDiagrams(representedElement, null).stream().findFirst().orElseThrow(() -> new RuntimeException("Impossible to find diagram for " + representedElement));
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

	@Before
	public void initViews() {
		mainCapsuleDiagram = getDiagram(mainCapsule);

		mainCapsuleView = getView(mainCapsuleDiagram, mainCapsule, "" + ClassCompositeEditPart.VISUAL_ID, false);

		mainCapsuleRelayView = getView(mainCapsuleView, mainCapsuleRelay, "" + PortEditPart.VISUAL_ID, true);

		sourceCapsulePartView = getView(mainCapsuleView, sourceCapsulePart, "" + PropertyPartEditPartCN.VISUAL_ID, true);
		compatibleCapsulePartView = getView(mainCapsuleView, compatibleCapsulePart, "" + PropertyPartEditPartCN.VISUAL_ID, true);

		sourceCapsuleSPPView = getView(sourceCapsulePartView, sourceCapsuleSPP, "" + PortEditPart.VISUAL_ID, true);
		sourceCapsuleExternalBehaviorPortView = getView(sourceCapsulePartView, sourceCapsuleExternalBehaviorPort, "" + PortEditPart.VISUAL_ID, true);
		sourceCapsuleSAPView = getView(sourceCapsulePartView, sourceCapsuleSAP, "" + PortEditPart.VISUAL_ID, true);
		sourceCapsuleRelayView = getView(sourceCapsulePartView, sourceCapsuleRelay, "" + PortEditPart.VISUAL_ID, true);

		compatibleCapsuleSPPView = getView(compatibleCapsulePartView, compatibleCapsuleSPP, "" + PortEditPart.VISUAL_ID, true);
		compatibleCapsuleExternalBehaviorPortView = getView(compatibleCapsulePartView, compatibleCapsuleExternalBehaviorPort, "" + PortEditPart.VISUAL_ID, true);
		compatibleCapsuleSAPView = getView(compatibleCapsulePartView, compatibleCapsuleSAP, "" + PortEditPart.VISUAL_ID, true);
		compatibleCapsuleInternalBehaviorPortView = getView(compatibleCapsulePartView, compatibleCapsuleInternalBehaviorPort, "" + PortEditPart.VISUAL_ID, true);
		compatibleCapsuleRelayView = getView(compatibleCapsulePartView, compatibleCapsuleRelay, "" + PortEditPart.VISUAL_ID, true);
	}

	// CHECK RT connectors rules
	/*
	 * Relay port (wired non-behavior service port) <-> Wired service port on a capsule part. This will be a delegation connector.
	 * Internal behavior port (wired non-service behavior port) <-> Wired service port on capsule part. This will be a an assembly connector.
	 * Wired service port on capsule part <-> Wired service port on capsule part. This will be an assembly connector.
	 * Relay port (wired non-behavior service port) <-> Internal behavior port (wired non-service behavior port). This will be a delegation connector. The use of this kind of connector is rather uncommon but should probably be supported due to the
	 * limitation that the run-time system cannot allow non-behavior ports (relay ports) to be redefined into behavior port in a subclass capsule. This forces the user to add and internal behavior port instead and have a delegation connector from the relay
	 * port (inherited from the superclass capsule).
	 * 
	 * It should never be allowed to create a connector from/to an unwired port, i.e. SAP or SPP.
	 * 
	 * It should also be checked that the ports are compatible, i.e. are typed by the same protocol with matching conjugation. For delegation connectors the connected ports should always have the same conjugation and for assembly connectors the connected
	 * ports should always have differtent conjugation.
	 */

	// Both connector ends must be port.
	@Test
	public void testCreateConnectorFromExternalBehaviorPortToPart() throws Exception {
		runConnectorCreationTest(sourceCapsuleExternalBehaviorPort, sourceCapsulePart, sourceCapsuleExternalBehaviorPortView, compatibleCapsulePart, null, compatibleCapsuleSPPView, false);
	}

	// Relay port (wired non-behavior service port) <-> Wired service port on a capsule part. This will be a delegation connector.
	@Test
	public void testCreateConnectorFromRelayToMainCapsuleRelay() throws Exception {
		runConnectorCreationTest(sourceCapsuleRelay, sourceCapsulePart, sourceCapsuleRelayView, mainCapsuleRelay, null, mainCapsuleRelayView, true);
	}

	// Wired service port on capsule part <-> Wired service port on capsule part. This will be an assembly connector.
	@Test
	public void testCreateConnectorFromRelayToCompatiblePartRelay() throws Exception {
		runConnectorCreationTest(sourceCapsuleRelay, sourceCapsulePart, sourceCapsuleRelayView, compatibleCapsuleRelay, compatibleCapsulePart, compatibleCapsuleRelayView, true);
	}

	// Internal behavior port (wired non-service behavior port) <-> Wired service port on capsule part. This will be a an assembly connector.
	@Test
	public void testCreateConnectorFromInternalBehaviorPortToCompatibleRelay() throws Exception {
		runConnectorCreationTest(sourceCapsuleInternalBehaviorPort, sourceCapsulePart, sourceCapsuleRelayView, compatibleCapsuleRelay, compatibleCapsulePart, compatibleCapsuleRelayView, true);
	}

	// Relay port (wired non-behavior service port) <-> Internal behavior port (wired non-service behavior port)
	@Test
	@FailingTest("Test passes on its own, so is seeing interference from other tests")
	public void testCreateConnectorFromRelayToCompatibleInternalBehaviorPort() throws Exception {
		runConnectorCreationTest(sourceCapsuleRelay, sourceCapsulePart, sourceCapsuleRelayView, compatibleCapsuleInternalBehaviorPort, compatibleCapsulePart, compatibleCapsuleInternalBehaviorPortView, true);
	}

	// It should never be allowed to create a connector from/to an unwired port, i.e. SAP or SPP.
	@Test
	public void testCreateConnectorFromSAPToCompatibleExternalBehaviorPort() throws Exception {
		runConnectorCreationTest(sourceCapsuleSAP, sourceCapsulePart, sourceCapsuleSAPView, compatibleCapsuleExternalBehaviorPort, compatibleCapsulePart, compatibleCapsuleExternalBehaviorPortView, false);
	}

	@Test
	public void testCreateConnectorFromSAPToCompatibleInternalBehaviorPort() throws Exception {
		runConnectorCreationTest(sourceCapsuleSAP, sourceCapsulePart, sourceCapsuleSAPView, compatibleCapsuleInternalBehaviorPort, compatibleCapsulePart, compatibleCapsuleInternalBehaviorPortView, false);
	}

	@Test
	public void testCreateConnectorFromSAPToCompatibleSPP() throws Exception {
		runConnectorCreationTest(sourceCapsuleSAP, sourceCapsulePart, sourceCapsuleSAPView, compatibleCapsuleSPP, compatibleCapsulePart, compatibleCapsuleSPPView, false);
	}

	@Test
	public void testCreateConnectorFromSAPToCompatibleSAP() throws Exception {
		runConnectorCreationTest(sourceCapsuleSAP, sourceCapsulePart, sourceCapsuleSAPView, compatibleCapsuleSAP, compatibleCapsulePart, compatibleCapsuleSAPView, false);
	}

	@Test
	public void testCreateConnectorFromSAPToCompatibleRelay() throws Exception {
		runConnectorCreationTest(sourceCapsuleSAP, sourceCapsulePart, sourceCapsuleSAPView, compatibleCapsuleRelay, compatibleCapsulePart, compatibleCapsuleRelayView, false);
	}

	@Test
	public void testCreateConnectorFromSPPToCompatibleExternalBehaviorPort() throws Exception {
		runConnectorCreationTest(sourceCapsuleSPP, sourceCapsulePart, sourceCapsuleSPPView, compatibleCapsuleExternalBehaviorPort, compatibleCapsulePart, compatibleCapsuleExternalBehaviorPortView, false);
	}

	@Test
	public void testCreateConnectorFromSPPToCompatibleInternalBehaviorPort() throws Exception {
		runConnectorCreationTest(sourceCapsuleSPP, sourceCapsulePart, sourceCapsuleSPPView, compatibleCapsuleInternalBehaviorPort, compatibleCapsulePart, compatibleCapsuleInternalBehaviorPortView, false);
	}

	@Test
	public void testCreateConnectorFromSPPToCompatibleSPP() throws Exception {
		runConnectorCreationTest(sourceCapsuleSPP, sourceCapsulePart, sourceCapsuleSPPView, compatibleCapsuleSPP, compatibleCapsulePart, compatibleCapsuleSPPView, false);
	}

	@Test
	public void testCreateConnectorFromSPPToCompatibleSAP() throws Exception {
		runConnectorCreationTest(sourceCapsuleSPP, sourceCapsulePart, sourceCapsuleSPPView, compatibleCapsuleSAP, compatibleCapsulePart, compatibleCapsuleSAPView, false);
	}

	@Test
	public void testCreateConnectorFromSPPToCompatibleRelay() throws Exception {
		runConnectorCreationTest(sourceCapsuleSPP, sourceCapsulePart, sourceCapsuleSPPView, compatibleCapsuleRelay, compatibleCapsulePart, compatibleCapsuleRelayView, false);
	}

	@Test
	public void testCreateConnectorFromExternalBehaviorPortToSAP() throws Exception {
		runConnectorCreationTest(sourceCapsuleExternalBehaviorPort, sourceCapsulePart, sourceCapsuleExternalBehaviorPortView, compatibleCapsuleSAP, compatibleCapsulePart, compatibleCapsuleSAPView, false);
	}

	@Test
	public void testCreateConnectorFromExternalBehaviorPortToSPP() throws Exception {
		runConnectorCreationTest(sourceCapsuleExternalBehaviorPort, sourceCapsulePart, sourceCapsuleExternalBehaviorPortView, compatibleCapsuleSPP, compatibleCapsulePart, compatibleCapsuleSPPView, false);
	}

	protected void runConnectorCreationTest(Property source, Property sourcePartWithPort, View sourceView, Property target, Property targetPartWithPort, View targetView, boolean canCreate) throws ServiceException {
		Command command = getCreateConnectorCommand(source, sourcePartWithPort, sourceView, target, targetPartWithPort, targetView, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (canCreate) {
			if (command != null) {
				if (command.canExecute()) {
					getCommandStack().execute(command);
					Connector connector = getExecutionResult(command);
					verifyConnector(connector, source, target, sourcePartWithPort, targetPartWithPort);
					getCommandStack().undo();
					getCommandStack().redo();
					verifyConnector(connector, source, target, sourcePartWithPort, targetPartWithPort);
					getCommandStack().undo();
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

	protected Command getCreateConnectorCommand(Property sourcePort, Property sourcePartWithPort, View sourceView, Property targetPort, Property targetPartWithPort, View targetView, boolean canCreate) throws ServiceException {
		Element container = new ConnectorUtils().deduceContainer(sourceView, targetView);
		if (canCreate) {
			assertThat(container, notNullValue());
		}

		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(sourcePort);
		CreateRelationshipRequest request = new CreateRelationshipRequest(transactionalEditingDomain, container, sourcePort, targetPort, reg.getType("org.eclipse.papyrusrt.umlrt.core.RTConnector"));
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

	protected CommandStack getCommandStack() {
		return modelSet.getEditingDomain().getCommandStack();
	}
}
