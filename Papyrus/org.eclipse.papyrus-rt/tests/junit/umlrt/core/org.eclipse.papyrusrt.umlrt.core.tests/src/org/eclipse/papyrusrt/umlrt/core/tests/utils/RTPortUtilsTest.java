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
package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test class for {@link RTPortUtils}
 */
@PluginResource("resource/Utils.di")
public class RTPortUtilsTest extends AbstractRTPortTest {

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_ExternalKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.EXTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_externalBehavior));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_InternalKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.INTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_internalBehavior));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_RelayKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.RELAY, RTPortUtils.getPortKind(capsule_relay));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_SAPKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.SAP, RTPortUtils.getPortKind(capsule_sap));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_SPPKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.SPP, RTPortUtils.getPortKind(capsule_spp));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_legacySAPKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.SAP, RTPortUtils.getPortKind(capsule_legacySap));
	}

	/**
	 * Test {@link RTPortUtils#getKind(Port)}
	 */
	@Test
	public void testRTPortUtils_legacySPPKind() {
		assertEquals("error in RTPortUtils#getKind(Port)", UMLRTPortKind.SPP, RTPortUtils.getPortKind(capsule_legacySpp));
	}

	// ------------------------------------------------------------------------
	// RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)
	// ------------------------------------------------------------------------

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSAPtoSPP() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_sap));
		testRTPortUtils_getChangeKindCommand(capsule_sap, UMLRTPortKind.SPP);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(capsule_sap));
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSAPtoSAP() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_sap));
		testRTPortUtils_getChangeKindCommand(capsule_sap, UMLRTPortKind.SAP);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(capsule_sap));
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSAPtoExternal() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_sap));
		testRTPortUtils_getChangeKindCommand(capsule_sap, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSAPtoInternal() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_sap));
		testRTPortUtils_getChangeKindCommand(capsule_sap, UMLRTPortKind.INTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSAPtoRelay() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_sap));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_sap));
		testRTPortUtils_getChangeKindCommand(capsule_sap, UMLRTPortKind.RELAY);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSPPtoSPP() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_spp));
		testRTPortUtils_getChangeKindCommand(capsule_spp, UMLRTPortKind.SPP);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(capsule_spp));
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSPPtoSAP() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_spp));
		testRTPortUtils_getChangeKindCommand(capsule_spp, UMLRTPortKind.SAP);
		assertNotEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(capsule_spp));
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSPPtoExternal() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_spp));
		testRTPortUtils_getChangeKindCommand(capsule_spp, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSPPtoInternal() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_spp));
		testRTPortUtils_getChangeKindCommand(capsule_spp, UMLRTPortKind.INTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromSPPtoRelay() throws Exception {
		assertNotEquals("registration error in input model", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(capsule_spp));
		assertNotEquals("registration override error in input model", "", RTPortUtils.getRegistrationOverride(capsule_spp));
		testRTPortUtils_getChangeKindCommand(capsule_spp, UMLRTPortKind.RELAY);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromRelaytoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_relay, UMLRTPortKind.SPP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromRelaytoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_relay, UMLRTPortKind.SAP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromRelaytoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_relay, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromRelaytoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_relay, UMLRTPortKind.INTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromRelaytoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_relay, UMLRTPortKind.RELAY);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromExternaltoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_externalBehavior, UMLRTPortKind.SPP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromExternaltoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_externalBehavior, UMLRTPortKind.SAP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromExternaltoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_externalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromExternaltoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_externalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromExternaltoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_externalBehavior, UMLRTPortKind.RELAY);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromInternaltoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_internalBehavior, UMLRTPortKind.SPP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromInternaltoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_internalBehavior, UMLRTPortKind.SAP);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromInternaltoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_internalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromInternaltoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_internalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeFromInternaltoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommand(capsule_internalBehavior, UMLRTPortKind.RELAY);
	}


	// ------------------------------------------------------------------------
	// RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind, boolean)
	// ------------------------------------------------------------------------

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind, boolean)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSAPtoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_sap, UMLRTPortKind.SPP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommandWithCheck(Port, UMLRTPortKind, boolean)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSAPtoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_sap, UMLRTPortKind.SAP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommandWithCheck(Port, UMLRTPortKind, boolean)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSAPtoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_sap, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSAPtoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_sap, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSAPtoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_sap, UMLRTPortKind.RELAY, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSPPtoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_spp, UMLRTPortKind.SPP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSPPtoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_spp, UMLRTPortKind.SAP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSPPtoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_spp, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSPPtoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_spp, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromSPPtoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_spp, UMLRTPortKind.RELAY, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelaytoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_relay, UMLRTPortKind.SPP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelaytoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_relay, UMLRTPortKind.SAP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelaytoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_relay, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelaytoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_relay, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelaytoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_relay, UMLRTPortKind.RELAY, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternaltoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_externalBehavior, UMLRTPortKind.SPP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternaltoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_externalBehavior, UMLRTPortKind.SAP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternaltoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_externalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternaltoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_externalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternaltoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_externalBehavior, UMLRTPortKind.RELAY, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternaltoSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_internalBehavior, UMLRTPortKind.SPP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternaltoSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_internalBehavior, UMLRTPortKind.SAP, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternaltoExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_internalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternaltoInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_internalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	/**
	 * Test {@link RTPortUtils#getChangeKindCommand(Port, UMLRTPortKind)}
	 */
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternaltoRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(capsule_internalBehavior, UMLRTPortKind.RELAY, true);
	}


	// check the specific ones with connection (internal, external and relay)
	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedInsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_relay, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedInsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_relay, UMLRTPortKind.EXTERNAL_BEHAVIOR, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedInsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_relay, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedInsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_relay, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedInsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_relay, UMLRTPortKind.SPP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedOutsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_relay, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedOutsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_relay, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedOutsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_relay, UMLRTPortKind.INTERNAL_BEHAVIOR, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedOutsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_relay, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromRelayConnectedOutsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_relay, UMLRTPortKind.SPP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedInsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_internalBehavior, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedInsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_internalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedInsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_internalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedInsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_internalBehavior, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedInsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_internalBehavior, UMLRTPortKind.SPP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedOutsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_internalBehavior, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedOutsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_internalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedOutsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_internalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedOutsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_internalBehavior, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromInternalConnectedOutsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_internalBehavior, UMLRTPortKind.SPP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedInsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_externalBehavior, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedInsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_externalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedInsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_externalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedInsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_externalBehavior, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedInsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule_externalBehavior, UMLRTPortKind.SPP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedOutsideToRelay() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_externalBehavior, UMLRTPortKind.RELAY, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedOutsideToExternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_externalBehavior, UMLRTPortKind.EXTERNAL_BEHAVIOR, true);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedOutsideToInternal() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_externalBehavior, UMLRTPortKind.INTERNAL_BEHAVIOR, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedOutsideToSAP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_externalBehavior, UMLRTPortKind.SAP, false);
	}

	@Test
	public void testRTPortUtils_getChangeKindWithCheckFromExternalConnectedOutsideToSPP() throws Exception {
		testRTPortUtils_getChangeKindCommandWithCheck(connectedCapsule2_externalBehavior, UMLRTPortKind.SPP, false);
	}

	// ------------------------------------------------------------------------
	// RTPortUtils#isLegacySpp(Port)
	// ------------------------------------------------------------------------
	@Test
	public void testRTPortUtils_isLegacySpp_LegacySppTrue() throws Exception {
		Assert.assertTrue(RTPortUtils.isLegacySpp(capsule_legacySpp));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_SppFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_spp));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_SapFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_sap));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_LegacySapFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_legacySap));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_ExternalFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_InternalFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isLegacySpp_RelayFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySpp(capsule_relay));
	}

	// ------------------------------------------------------------------------
	// RTPortUtils#isLegacySap(Port)
	// ------------------------------------------------------------------------
	@Test
	public void testRTPortUtils_isLegacySap_LegacySppFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_legacySpp));
	}

	@Test
	public void testRTPortUtils_isLegacySap_SppFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_spp));
	}

	@Test
	public void testRTPortUtils_isLegacySap_SapFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_sap));
	}

	@Test
	public void testRTPortUtils_isLegacySap_LegacySapTrue() throws Exception {
		Assert.assertTrue(RTPortUtils.isLegacySap(capsule_legacySap));
	}

	@Test
	public void testRTPortUtils_isLegacySap_ExternalFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isLegacySap_InternalFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isLegacySap_RelayFalse() throws Exception {
		Assert.assertFalse(RTPortUtils.isLegacySap(capsule_relay));
	}

	// ------------------------------------------------------------------------
	// RTPortUtils#isConnected()
	// ------------------------------------------------------------------------
	@Test
	public void testRTPortUtils_isConnected_ExternalBehaviorConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnected_InternalBehaviorConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnected_RelayConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule_relay));
	}

	@Test
	public void testRTPortUtils_isConnected_ExternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnected(connectedCapsule_externalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnected_InternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnected(connectedCapsule_internalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnected_RelayNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnected(connectedCapsule_relayNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnected_ExternalBehavior2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule2_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnected_InternalBehavior2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule2_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnected_Relay2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnected(connectedCapsule2_relay));
	}


	// ------------------------------------------------------------------------
	// RTPortUtils#isConnectedInside()
	// ------------------------------------------------------------------------
	@Test
	public void testRTPortUtils_isConnectedInside_ExternalBehaviorConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedInside(connectedCapsule_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_InternalBehaviorConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedInside(connectedCapsule_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_RelayConnected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedInside(connectedCapsule_relay));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_ExternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule_externalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_InternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule_internalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_RelayNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule_relayNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_ExternalBehavior2Connected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule2_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_InternalBehavior2Connected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule2_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedInside_Relay2Connected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedInside(connectedCapsule2_relay));
	}

	// ------------------------------------------------------------------------
	// RTPortUtils#isConnectedOutside()
	// ------------------------------------------------------------------------
	@Test
	public void testRTPortUtils_isConnectedOutside_ExternalBehaviorConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_InternalBehaviorConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_RelayConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_relay));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_ExternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_externalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_InternalBehaviorNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_internalBehaviorNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_RelayNotConnected() throws Exception {
		Assert.assertFalse(RTPortUtils.isConnectedOutside(connectedCapsule_relayNotConnected));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_ExternalBehavior2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedOutside(connectedCapsule2_externalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_InternalBehavior2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedOutside(connectedCapsule2_internalBehavior));
	}

	@Test
	public void testRTPortUtils_isConnectedOutside_Relay2Connected() throws Exception {
		Assert.assertTrue(RTPortUtils.isConnectedOutside(connectedCapsule2_relay));
	}


	// ------------------------------------------------------------------------
	// test methods
	// ------------------------------------------------------------------------

	protected void testRTPortUtils_getChangeKindCommand(Port portToModify, UMLRTPortKind newKind) {
		UMLRTPortKind oldKind = RTPortUtils.getPortKind(portToModify);

		Command command = GMFtoEMFCommandWrapper.wrap(RTPortUtils.getChangePortKindCommand(portToModify, newKind));
		if (oldKind == newKind && !command.canExecute()) {
			return;
		}
		if (!command.canExecute()) {
			fail("Change command is not executable");
		}
		command.execute();
		checkPort(portToModify, newKind);

		if (!command.canUndo()) {
			fail("Change command is not undoable");
		}
		command.undo();
		checkPort(portToModify, oldKind);

		command.redo();
		checkPort(portToModify, newKind);

		command.undo();
		// check not dirty
	}

	protected void testRTPortUtils_getChangeKindCommandWithCheck(Port portToModify, UMLRTPortKind newKind, boolean isExecutable) {
		UMLRTPortKind oldKind = RTPortUtils.getPortKind(portToModify);

		Command command = GMFtoEMFCommandWrapper.wrap(RTPortUtils.getChangePortKindCommand(portToModify, newKind, true));
		if (!isExecutable) { // not executable => do not run do/undo
			if (command.canExecute()) {
				fail("Command should not be executable");
			}
			return;
		}
		if (oldKind == newKind && !command.canExecute()) {
			return;
		}
		if (!command.canExecute()) {
			fail("Change command is not executable where it should");
		}

		command.execute();
		checkPort(portToModify, newKind);

		if (!command.canUndo()) {
			fail("Change command is not undoable");
		}
		command.undo();
		checkPort(portToModify, oldKind);

		command.redo();
		checkPort(portToModify, newKind);

		command.undo();
		// check not dirty
	}


	protected void checkPort(Port port, UMLRTPortKind kind) {
		switch (kind) {
		case EXTERNAL_BEHAVIOR:
			assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			assertEquals("Visibility: error in RTPortUtils#getChangeKindCommand(Port)", VisibilityKind.PUBLIC_LITERAL, port.getVisibility());
			break;
		case INTERNAL_BEHAVIOR:
			assertFalse("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			assertEquals("Visibility: error in RTPortUtils#getChangeKindCommand(Port)", VisibilityKind.PROTECTED_LITERAL, port.getVisibility());
			break;
		case RELAY:
			assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertFalse("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			assertEquals("Visibility: error in RTPortUtils#getChangeKindCommand(Port)", VisibilityKind.PUBLIC_LITERAL, port.getVisibility());
			break;
		case SPP:
			// assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port)); // legacy
			assertFalse("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertTrue("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Visibility: error in RTPortUtils#getChangeKindCommand(Port)", VisibilityKind.PUBLIC_LITERAL, port.getVisibility());
			break;
		case SAP:
			// assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));// legacy
			assertFalse("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Visibility: error in RTPortUtils#getChangeKindCommand(Port)", VisibilityKind.PROTECTED_LITERAL, port.getVisibility());
			break;

		default:
			break;
		}
	}

}
