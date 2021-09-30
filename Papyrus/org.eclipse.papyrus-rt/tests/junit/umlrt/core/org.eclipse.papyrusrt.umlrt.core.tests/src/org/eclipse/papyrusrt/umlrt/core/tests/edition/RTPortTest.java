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
 *  Christian W. Damus - bug 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.AbstractRTPortTest;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test class for {@link RTPort} edition
 */
@PluginResource("resource/Utils.di")
public class RTPortTest extends AbstractRTPortTest {

	@Test
	public void testChangeFromLegacySppToSPP() throws Exception {
		// check service
		Port portToTest = capsule_legacySpp;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.SPP; // legacy, but SPP
		UMLRTPortKind newKind = UMLRTPortKind.SPP; // "real" SPP
		Object newvalue = true;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@FailingTest("This transition is not allowed")
	@Test
	public void testChangeFromLegacySPPToExternalBehavior() throws Exception {
		// check wired
		Port portToTest = capsule_legacySpp;
		RTPort rtPort = RTPortUtils.getStereotypeApplication(portToTest);
		EStructuralFeature featureToSet = UMLRealTimePackage.eINSTANCE.getRTPort_IsWired();
		UMLRTPortKind oldKind = UMLRTPortKind.SPP; // legacy, but SPP
		UMLRTPortKind newKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		Object newvalue = true;

		testChangeFromKindToKind(rtPort, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromSPPToExternalBehavior() throws Exception {
		// check wired
		Port portToTest = capsule_spp;
		RTPort rtPort = RTPortUtils.getStereotypeApplication(portToTest);
		EStructuralFeature featureToSet = UMLRealTimePackage.eINSTANCE.getRTPort_IsWired();
		UMLRTPortKind oldKind = UMLRTPortKind.SPP;
		UMLRTPortKind newKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		Object newvalue = true;
		Assert.assertEquals(oldKind, RTPortUtils.getPortKind(portToTest));
		testChangeFromKindToKind(rtPort, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromLegacySPPToSAP() throws Exception {
		// check service
		Port portToTest = capsule_legacySpp;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.SPP; // legacy, but SPP
		UMLRTPortKind newKind = UMLRTPortKind.SAP;
		Object newvalue = false;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromSPPToSAP() throws Exception {
		// uncheck service
		Port portToTest = capsule_spp;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.SPP;
		UMLRTPortKind newKind = UMLRTPortKind.SAP;
		Object newvalue = false;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(portToTest));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(portToTest));
	}

	@Test
	public void testChangeFromSAPToSPP() throws Exception {
		// check service
		Port portToTest = capsule_sap;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.SAP;
		UMLRTPortKind newKind = UMLRTPortKind.SPP;
		Object newvalue = true;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(portToTest));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(portToTest));
	}

	@Test
	public void testChangeFromLegacySAPToSAP() throws Exception {
		// uncheck service
		Port portToTest = capsule_legacySap;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.SAP;
		UMLRTPortKind newKind = UMLRTPortKind.SAP;
		Object newvalue = false;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromSAPToInternalBehavior() throws Exception {
		// check wired
		Port portToTest = capsule_sap;
		RTPort rtPort = RTPortUtils.getStereotypeApplication(portToTest);
		EStructuralFeature featureToSet = UMLRealTimePackage.eINSTANCE.getRTPort_IsWired();
		UMLRTPortKind oldKind = UMLRTPortKind.SAP;
		UMLRTPortKind newKind = UMLRTPortKind.INTERNAL_BEHAVIOR;
		Object newvalue = true;

		testChangeFromKindToKind(rtPort, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromInternalBehaviorToSAP() throws Exception {
		// uncheck wired
		Port portToTest = capsule_internalBehavior;
		RTPort rtPort = RTPortUtils.getStereotypeApplication(portToTest);
		EStructuralFeature featureToSet = UMLRealTimePackage.eINSTANCE.getRTPort_IsWired();
		UMLRTPortKind oldKind = UMLRTPortKind.INTERNAL_BEHAVIOR;
		UMLRTPortKind newKind = UMLRTPortKind.SAP;
		Object newvalue = false;

		testChangeFromKindToKind(rtPort, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromInternalBehaviorToExternalBehavior() throws Exception {
		// check service
		Port portToTest = capsule_internalBehavior;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.INTERNAL_BEHAVIOR;
		UMLRTPortKind newKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		Object newvalue = true;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromExternalBehaviorToInternalBehavior() throws Exception {
		// uncheck service
		Port portToTest = capsule_externalBehavior;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsService();
		UMLRTPortKind oldKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		UMLRTPortKind newKind = UMLRTPortKind.INTERNAL_BEHAVIOR;
		Object newvalue = false;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromExternalBehaviorToSPP() throws Exception {
		// uncheck wired
		Port portToTest = capsule_externalBehavior;
		RTPort rtPort = RTPortUtils.getStereotypeApplication(portToTest);
		EStructuralFeature featureToSet = UMLRealTimePackage.eINSTANCE.getRTPort_IsWired();
		UMLRTPortKind oldKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		UMLRTPortKind newKind = UMLRTPortKind.SPP;
		Object newvalue = false;

		testChangeFromKindToKind(rtPort, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromExternalBehaviorToRelay() throws Exception {
		// uncheck behavior
		Port portToTest = capsule_externalBehavior;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsBehavior();
		UMLRTPortKind oldKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		UMLRTPortKind newKind = UMLRTPortKind.RELAY;
		Object newvalue = false;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	@Test
	public void testChangeFromRelayToExternalBehavior() throws Exception {
		// check behavior
		Port portToTest = capsule_relay;
		EStructuralFeature featureToSet = UMLPackage.eINSTANCE.getPort_IsBehavior();
		UMLRTPortKind oldKind = UMLRTPortKind.RELAY;
		UMLRTPortKind newKind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
		Object newvalue = true;

		testChangeFromKindToKind(portToTest, featureToSet, newvalue, oldKind, newKind);
	}

	protected void testChangeFromKindToKind(EObject objectToSet, EStructuralFeature featureToSet, Object newvalue, UMLRTPortKind oldKind, UMLRTPortKind newKind) throws ExecutionException {
		ICommand command = getSetCommand(objectToSet, featureToSet, newvalue);
		if (command.canExecute()) {
			IProgressMonitor monitor = new NullProgressMonitor();
			IStatus status = command.execute(new NullProgressMonitor(), null);
			if (status.isOK()) {
				checkPort(objectToSet, newKind);
			}
			if (command.canUndo()) {
				command.undo(monitor, null);
			} else {
				fail("Command should be undoable");
			}
			checkPort(objectToSet, oldKind);
			if (command.canRedo()) {
				command.redo(monitor, null);
			} else {
				fail("Command should be redoable");
			}
			checkPort(objectToSet, newKind);
			if (command.canUndo()) {
				command.undo(monitor, null);
			} else {
				fail("Command should be undoable");
			}
			checkPort(objectToSet, oldKind);
		} else {
			fail("Command should be executable");
		}

	}

	protected ICommand getSetCommand(EObject objectToTest, EStructuralFeature featureToModify, Object newValue) {
		SetRequest setRequest = new SetRequest(modelSet.getEditingDomain(), objectToTest, featureToModify, newValue);
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(objectToTest);

		if (provider != null) {
			return provider.getEditCommand(setRequest);
		}

		fail("impossible to find command provider");
		return null;
	}

}
