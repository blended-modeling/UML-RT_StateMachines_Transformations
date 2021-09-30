/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.validation.tests.rules;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.uml.service.validation.internal.UMLDiagnostician;
import org.eclipse.papyrusrt.umlrt.core.validation.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Sample test for validation Rule: Connected ports must be compatible
 */
public class ConnectorCompatibilityValidationTest extends AbstractValidationEditorTest {

	public static final String CONSTRAINT_PLUGIN = "org.eclipse.papyrusrt.umlrt.core.validation"; //$NON-NLS-1$

	public static final String CONSTRAINT_ID = "UMLRealTime.RTConnector.Connected.Ports.Compatibility"; //$NON-NLS-1$

	public static final String RESOURCES_PATH = "resources/"; //$NON-NLS-1$

	public static final String MODEL_NAME = "ConnectedPortsCompatibilityRule"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "ConnectorCompatibilityValidationTest"; //$NON-NLS-1$

	public static final String CAPSULE = "Top";

	/** validation diagnostic */
	protected Diagnostic globalDiagnostic;

	/** root model */
	public Model model;

	public Class capsule;

	public Connector externalP1ToExternalP1;
	public Connector externalP1ToExternalP2Con;
	public Connector externalP1ToExternalP2;
	public Connector externalP1ConToExternalP1Con;
	public Connector externalP1ConToExternalP2Con;
	public Connector externalP1ConToExternalP2;
	public Connector externalP2ConToRelayP2;
	public Connector internalP1ConToExternalP2;
	public Connector externalP1ToExternalP1Con;
	public Connector externalP2ConToRelayP2Con;
	public Connector internalP1ConToExternalP1;
	public Connector internalP2ToRelayP2;

	public List<Diagnostic> connectorDiagnostics;

	@Before
	public void initModelForValidationTest() throws Exception {
		initModel(PROJECT_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		// validate the new model

		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		model = (Model) getRootUMLModel();
		capsule = (Class) model.getPackagedElement(CAPSULE);

		externalP1ToExternalP1 = capsule.getOwnedConnector("externalBehaviourPortProtocol1_externalBehaviourPortProtocol1", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1_externalBehaviourPortProtocol1", externalP1ToExternalP1);

		externalP1ToExternalP2Con = capsule.getOwnedConnector("externalBehaviourPortProtocol1_externalBehaviourPortProtocol2Conjugated", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1_externalBehaviourPortProtocol2Conjugated", externalP1ToExternalP2Con);

		externalP1ToExternalP2 = capsule.getOwnedConnector("externalBehaviourPortProtocol1_externalBehaviourPortProtocol2", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1_externalBehaviourPortProtocol2", externalP1ToExternalP2);

		externalP1ConToExternalP1Con = capsule.getOwnedConnector("externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol1Conjugated", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol1Conjugated", externalP1ConToExternalP1Con);

		externalP1ConToExternalP2Con = capsule.getOwnedConnector("externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2Conjugated", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2Conjugated", externalP1ConToExternalP2Con);

		externalP1ConToExternalP2 = capsule.getOwnedConnector("externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2", externalP1ConToExternalP2);

		externalP2ConToRelayP2 = capsule.getOwnedConnector("externalBehaviourPortProtocol2Conjugated_relayPortProtocol2", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol2Conjugated_relayPortProtocol2", externalP2ConToRelayP2);

		internalP1ConToExternalP2 = capsule.getOwnedConnector("internalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2", false, false);
		Assert.assertNotNull("Impossible to find connector " + "internalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol2", internalP1ConToExternalP2);

		externalP1ToExternalP1Con = capsule.getOwnedConnector("externalBehaviourPortProtocol1_externalBehaviourPortProtocol1Conjugated", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol1_externalBehaviourPortProtocol1Conjugated", externalP1ToExternalP1Con);

		externalP2ConToRelayP2Con = capsule.getOwnedConnector("externalBehaviourPortProtocol2Conjugated_relayPortProtocol2Conjugated", false, false);
		Assert.assertNotNull("Impossible to find connector " + "externalBehaviourPortProtocol2Conjugated_relayPortProtocol2Conjugated", externalP2ConToRelayP2Con);

		internalP1ConToExternalP1 = capsule.getOwnedConnector("internalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol1", false, false);
		Assert.assertNotNull("Impossible to find connector " + "internalBehaviourPortProtocol1Conjugated_externalBehaviourPortProtocol1", internalP1ConToExternalP1);

		internalP2ToRelayP2 = capsule.getOwnedConnector("internalBehaviourPortProtocol2_relayPortProtocol2", false, false);
		Assert.assertNotNull("Impossible to find connector " + "internalBehaviourPortProtocol2_relayPortProtocol2", internalP2ToRelayP2);


		final EditingDomain domain = TransactionUtil.getEditingDomain(model);
		final ValidateModelCommand validateModelCommand = new ValidateModelCommand(model, new UMLDiagnostician());
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});

		// check that the constraint exist
		ConstraintRegistry instance = ConstraintRegistry.getInstance();
		IConstraintDescriptor descriptor = instance.getDescriptor(CONSTRAINT_PLUGIN, CONSTRAINT_ID);
		Assert.assertNotNull("Constraint is missing", descriptor);

		globalDiagnostic = validateModelCommand.getDiagnostic();
		connectorDiagnostics = findDiagnosticBySource(globalDiagnostic, CONSTRAINT_PLUGIN + "." + CONSTRAINT_ID);
	}

	@Override
	protected String getSourcePath() {
		return RESOURCES_PATH;
	}

	/**
	 * Test assembly connector with same conjugation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ToExternalP1() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ToExternalP1);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector with incompatible protocol.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ToExternalP2Con() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ToExternalP2Con);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector with incompatible port conjugation and incompatible protocol.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ToExternalP2() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ToExternalP2);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector with same conjugation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ConToExternalP1Con() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ConToExternalP1Con);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector with incompatible port conjugation and incompatible protocol.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ConToExternalP2Con() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ConToExternalP2Con);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector with incompatible protocol.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ConToExternalP2() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ConToExternalP2);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test delegation connector with incompatible conjugation. Conjugation must be same.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP2ConToRelayP2() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP2ConToRelayP2);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector from internal behaviour port. Port must be typed with compatible protocol
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateInternalP1ConToExternalP2() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, internalP1ConToExternalP2);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should trigger an issue for this element", 1, diagnostics.size());

	}

	/**
	 * Test assembly connector. Wired service <-> Wired service conjugated
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP1ToExternalP1Con() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP1ToExternalP1Con);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should not trigger an issue for this element", 0, diagnostics.size());

	}

	/**
	 * Test delegation connector. Wired service conjugated <-> relay port conjugated
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateExternalP2ConToRelayP2Con() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, externalP2ConToRelayP2Con);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should not trigger an issue for this element", 0, diagnostics.size());

	}

	/**
	 * Test assembly connector. Internal behaviour conjugated <-> wired service port
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateInternalP1ConToExternalP1() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, internalP1ConToExternalP1);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should not trigger an issue for this element", 0, diagnostics.size());

	}

	/**
	 * Test delegation connector. Internal behaviour <-> relay port with same conjugation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void validateInternalP2ToRelayP2() throws Exception {
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(connectorDiagnostics, internalP2ToRelayP2);
		Assert.assertEquals("The rule " + CONSTRAINT_ID + " should not trigger an issue for this element", 0, diagnostics.size());

	}

}
