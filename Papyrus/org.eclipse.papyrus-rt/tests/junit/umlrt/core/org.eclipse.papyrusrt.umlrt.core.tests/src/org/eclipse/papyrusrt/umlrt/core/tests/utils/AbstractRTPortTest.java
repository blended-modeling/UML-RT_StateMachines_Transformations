/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * Abstract class for {@link RTPort}.
 */
@PluginResource("resource/Utils.di")
public abstract class AbstractRTPortTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();
	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();
	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();
	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Named("Utils")
	protected Model root;
	@Named("Utils::Capsule")
	protected Class capsule;
	@Named("Utils::Capsule::externalBehavior")
	protected Port capsule_externalBehavior;
	@Named("Utils::Capsule::internalBehavior")
	protected Port capsule_internalBehavior;
	@Named("Utils::Capsule::relay")
	protected Port capsule_relay;
	@Named("Utils::Capsule::sap")
	protected Port capsule_sap;
	@Named("Utils::Capsule::spp")
	protected Port capsule_spp;
	@Named("Utils::Capsule::legacySap")
	protected Port capsule_legacySap;
	@Named("Utils::Capsule::legacySpp")
	protected Port capsule_legacySpp;
	@Named("Utils::ConnectedCapsule")
	protected Class connectedCapsule;
	@Named("Utils::ConnectedCapsule2")
	protected Class connectedCapsule2;
	@Named("Utils::ConnectedCapsule::externalBehavior")
	protected Port connectedCapsule_externalBehavior;
	@Named("Utils::ConnectedCapsule::internalBehavior")
	protected Port connectedCapsule_internalBehavior;
	@Named("Utils::ConnectedCapsule::relay")
	protected Port connectedCapsule_relay;
	@Named("Utils::ConnectedCapsule::externalBehaviorNotConnected")
	protected Port connectedCapsule_externalBehaviorNotConnected;
	@Named("Utils::ConnectedCapsule::internalBehaviorNotConnected")
	protected Port connectedCapsule_internalBehaviorNotConnected;
	@Named("Utils::ConnectedCapsule::relayNotConnected")
	protected Port connectedCapsule_relayNotConnected;
	@Named("Utils::ConnectedCapsule2::externalBehavior")
	protected Port connectedCapsule2_externalBehavior;
	@Named("Utils::ConnectedCapsule2::internalBehavior")
	protected Port connectedCapsule2_internalBehavior;
	@Named("Utils::ConnectedCapsule2::relay")
	protected Port connectedCapsule2_relay;
	@Named("Utils::ConnectedCapsule::externalBehaviorConnector")
	protected Connector connectedCapsule_externalBehaviorConnector;
	@Named("Utils::ConnectedCapsule::internalBehaviorConnector")
	protected Connector connectedCapsule_internalBehaviorConnector;
	@Named("Utils::ConnectedCapsule::relayConnector")
	protected Connector connectedCapsule_relayConnector;

	@Before
	public void initModelElements() {

		// capsules
		Assert.assertNotNull(capsule);

		// ports
		Assert.assertNotNull(capsule_externalBehavior);
		Assert.assertNotNull(capsule_internalBehavior);
		Assert.assertNotNull(capsule_relay);
		Assert.assertNotNull(capsule_sap);
		Assert.assertNotNull(capsule_spp);
		Assert.assertNotNull(capsule_relay);
		Assert.assertNotNull(capsule_legacySap);
		Assert.assertNotNull(capsule_legacySpp);

		// connections tests - classes
		Assert.assertNotNull(connectedCapsule);
		Assert.assertNotNull(connectedCapsule2);

		// connections tests - ports
		Assert.assertNotNull(connectedCapsule_externalBehavior);
		Assert.assertNotNull(connectedCapsule_internalBehavior);
		Assert.assertNotNull(connectedCapsule_relay);
		// not connected
		Assert.assertNotNull(connectedCapsule_externalBehaviorNotConnected);
		Assert.assertNotNull(connectedCapsule_internalBehaviorNotConnected);
		Assert.assertNotNull(connectedCapsule_relayNotConnected);

		// port on part
		Assert.assertNotNull(connectedCapsule2_externalBehavior);
		Assert.assertNotNull(connectedCapsule2_internalBehavior);
		Assert.assertNotNull(connectedCapsule2_relay);

		// connector
		Assert.assertNotNull(connectedCapsule_externalBehaviorConnector);
		Assert.assertNotNull(connectedCapsule_internalBehaviorConnector);
		Assert.assertNotNull(connectedCapsule_relayConnector);
	}

	protected void checkPort(EObject objectToSet, UMLRTPortKind kind) {
		Port port = null;
		if (objectToSet instanceof Port) {
			port = (Port) objectToSet;
		} else if (objectToSet instanceof RTPort) {
			port = ((RTPort) objectToSet).getBase_Port();
		} else {
			fail("impossible to get the port from EObject: " + objectToSet);
		}
		// See Bug 479352
		Assert.assertFalse("RTPort Multiplicities should be left unset ", port.eIsSet(UMLPackage.eINSTANCE.getMultiplicityElement().getEStructuralFeature("lowerValue")));
		Assert.assertFalse("RTPort Multiplicities should be left unset ", port.eIsSet(UMLPackage.eINSTANCE.getMultiplicityElement().getEStructuralFeature("upperValue")));

		switch (kind) {
		case EXTERNAL_BEHAVIOR:
			assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			break;
		case INTERNAL_BEHAVIOR:
			assertFalse("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			break;
		case RELAY:
			assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));
			assertTrue("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertFalse("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			assertEquals("Registration: error in RTPortUtils#getChangeKindCommand(Port)", PortRegistrationType.AUTOMATIC, RTPortUtils.getRegistration(port));
			assertEquals("RegistrationOverride: error in RTPortUtils#getChangeKindCommand(Port)", "", RTPortUtils.getRegistrationOverride(port));
			break;
		case SPP:
			// assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port)); // legacy
			assertFalse("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertTrue("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			break;
		case SAP:
			// assertTrue("Service: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isService(port));// legacy
			assertFalse("Wired: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isWired(port));
			assertTrue("Behavior: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isBehavior(port));
			assertFalse("Publish: error in RTPortUtils#getChangeKindCommand(Port)", RTPortUtils.isPublish(port));
			break;

		default:
			break;
		}
	}
}
