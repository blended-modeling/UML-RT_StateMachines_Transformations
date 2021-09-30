/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.migration.rsa.tests.qvt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.ProtocolContainer;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Assert;
import org.junit.Test;

public class ImportProfilesTest extends AbstractTransformationTest {

	// resources/rt/*
	@Test
	public void testRTProfile() throws Exception {
		simpleImport("resources/rt/Blank Package.emx", new String[0]);

		openEditor();

		List<Profile> appliedProfiles = rootPackage.getAppliedProfiles();
		List<Stereotype> appliedStereotypes = rootPackage.getAppliedStereotypes();

		Set<URI> expectedProfiles = new HashSet<>();
		expectedProfiles.add(URI.createURI("pathmap://UML_PROFILES/Standard.profile.uml"));
		expectedProfiles.add(URI.createURI("pathmap://PAPYRUS_DOCUMENTATION/Papyrus.profile.uml"));
		expectedProfiles.add(URI.createURI("pathmap://UML_RT_PROFILE/uml-rt.profile.uml"));
		expectedProfiles.add(URI.createURI("pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"));

		// Assert source profile is removed, new profile is applied
		for (Profile appliedProfile : appliedProfiles) {
			URI profileURI = EcoreUtil.getURI(appliedProfile).trimFragment();
			Assert.assertTrue("Unexpected applied profile: " + profileURI, expectedProfiles.contains(profileURI));
		}

		int expectedSize = expectedProfiles.size();
		Assert.assertEquals("The Package should have exactly " + expectedSize + " applied profiles", expectedSize, appliedProfiles.size());

		// Assert standard stereotypes (Capsule, ...) are transformed
		Assert.assertEquals("The package should have exactly one applied stereotype", 1, appliedStereotypes.size());
		Assert.assertNotNull("The root package should be stereotyped with ProtocolContainer", UMLUtil.getStereotypeApplication(rootPackage, ProtocolContainer.class));

		// Assert Protocol/RTMessageSet is properly created/updated
		Package protocolContainer = (Package) rootPackage.getMember("Protocol1");
		assertIsValidProtocol(protocolContainer);

		// Assert StateMachine extensions are added in Capsules (only)

		// This one should not be stereotyped, since it is not owned by a Capsule
		StateMachine stateMachine1 = (StateMachine) rootPackage.getMember("StateMachine1");
		Assert.assertEquals("The StateMachine in the rootPackage shouldn't be stereotyped", 0, stateMachine1.getAppliedStereotypes().size());
		Iterator<EObject> it = stateMachine1.eAllContents();
		while (it.hasNext()) {
			EObject next = it.next();
			if (next instanceof Element) {
				Element child = (Element) next;
				Assert.assertEquals("The nodes of a StateMachine in the rootPackage shouldn't be stereotyped", 0, child.getAppliedStereotypes().size());
			}
		}


		// The next state machine belongs to a Capsule, and should be stereotyped
		Class capsule1 = (Class) rootPackage.getMember("Capsule1");
		Assert.assertNotNull(UMLUtil.getStereotypeApplication(capsule1, Capsule.class));

		StateMachine rtStateMachine = (StateMachine) capsule1.getMember("State Machine");
		Assert.assertNotNull(UMLUtil.getStereotypeApplication(rtStateMachine, RTStateMachine.class));
		// The state machine should also have been set as the ClassifierBehavior of the Capsule
		// It's not always the case in RSA, but should be enforced in Papyrus
		Assert.assertEquals(rtStateMachine, capsule1.getClassifierBehavior());

		Region region = rtStateMachine.getRegions().get(0);
		Assert.assertNotNull(UMLUtil.getStereotypeApplication(region, RTRegion.class));

		// Initial state is unnamed
		Pseudostate pseudo = (Pseudostate) region.getMember(null, false, UMLPackage.eINSTANCE.getPseudostate());
		Assert.assertNotNull(UMLUtil.getStereotypeApplication(pseudo, RTPseudostate.class));

		State rtState = (State) region.getMember("State1");
		Assert.assertNotNull(UMLUtil.getStereotypeApplication(rtState, RTState.class));
	}


	// resources/rt+fragments/*
	@Test
	// Fragmented StateMachines are not found in QVTo, thus are not stereotyped
	public void testFragmentedRTModel() throws Exception {
		String path = "resources/rt+fragments/";
		String modelPath = path + "JavaModel.emx";
		String[] fragments = new String[] {
				path + "ModelFragment_1.efx",
				path + "ModelFragment_2.efx" };

		simpleImport(modelPath, fragments);
		openEditor();
		// assertRSAModelsRemoved(true); //Various standard RSA libraries and profiles are still referenced and are not handled. Expected; don't test.

		// Directly contained element already tested in #testRTProfile(). Only check fragments

		Package fragment1 = (Package) rootPackage.getPackagedElement("Fragment1");
		Class capsule1 = (Class) fragment1.getPackagedElement("Capsule1");
		Capsule capsuleST = UMLUtil.getStereotypeApplication(capsule1, Capsule.class);
		Assert.assertNotNull("Missing Capsule on fragmented class Capsule1", capsuleST);

		Package protocolContainer = (Package) fragment1.getPackagedElement("Protocol1");
		assertIsValidProtocol(protocolContainer);


		// StateMachine stereotypes are not applied (Bug 457430/457433)
		StateMachine stateMachine = (StateMachine) capsule1.getOwnedBehavior("State Machine");
		RTStateMachine stateMachineST = UMLUtil.getStereotypeApplication(stateMachine, RTStateMachine.class);
		Assert.assertNotNull(stateMachineST); // Bug 457433

		Region region1 = stateMachine.getRegions().get(0);
		RTRegion regionST = UMLUtil.getStereotypeApplication(region1, RTRegion.class);
		Assert.assertNotNull(regionST);

		State state1 = (State) region1.getMember("State1");
		RTState stateST = UMLUtil.getStereotypeApplication(state1, RTState.class);
		Assert.assertNotNull(stateST);

		Pseudostate pseudo1 = (Pseudostate) region1.getMember(null, false, UMLPackage.eINSTANCE.getPseudostate());
		RTPseudostate pseudoST = UMLUtil.getStereotypeApplication(pseudo1, RTPseudostate.class);
		Assert.assertNotNull(pseudoST);
	}

	protected void assertIsValidProtocol(Package protocolContainer) {
		String name = protocolContainer.getName();

		Assert.assertNotNull("The package Protocol1 should be stereotyped with ProtocolContainer", UMLUtil.getStereotypeApplication(protocolContainer, ProtocolContainer.class));

		Collaboration protocol = (Collaboration) protocolContainer.getMember(name, false, UMLPackage.eINSTANCE.getCollaboration());
		Assert.assertNotNull("The collaboration Protocol1 should be stereotyped with Protocol", UMLUtil.getStereotypeApplication(protocol, Protocol.class));

		Interface protocolIn = (Interface) protocolContainer.getMember(name, false, UMLPackage.eINSTANCE.getInterface());
		RTMessageSet messageSetIn = UMLUtil.getStereotypeApplication(protocolIn, RTMessageSet.class);
		Assert.assertEquals("The direction of the Protocol1 interface should be 'in'", RTMessageKind.IN, messageSetIn.getRtMsgKind());

		Interface protocolOut = (Interface) protocolContainer.getMember(name + "~", false, UMLPackage.eINSTANCE.getInterface());
		RTMessageSet messageSetOut = UMLUtil.getStereotypeApplication(protocolOut, RTMessageSet.class);
		Assert.assertEquals("The direction of the Protocol1~ interface should be 'out'", RTMessageKind.OUT, messageSetOut.getRtMsgKind());

		Interface protocolInOut = (Interface) protocolContainer.getMember(name + "IO", false, UMLPackage.eINSTANCE.getInterface());
		RTMessageSet messageSetInOut = UMLUtil.getStereotypeApplication(protocolInOut, RTMessageSet.class);
		Assert.assertEquals("The direction of the Protocol1 interface should be 'inOut'", RTMessageKind.IN_OUT, messageSetInOut.getRtMsgKind());
	}
}
