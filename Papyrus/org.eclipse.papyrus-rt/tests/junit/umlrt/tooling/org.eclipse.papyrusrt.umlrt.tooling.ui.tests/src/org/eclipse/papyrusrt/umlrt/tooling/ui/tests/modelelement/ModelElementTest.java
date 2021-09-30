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
 *  Christian W. Damus - bugs 491543, 479628, 497801, 467545, 513173, 510188
 *  Young-Soo Roh - bug 483636
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.modelelement;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.eclipse.papyrusrt.junit.matchers.StatusMatchers.hasSeverity;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UIThread;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.IRealTimeConstants;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.RTStereotypeModelElement;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTExtModelElement;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTFacadeModelElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Unit tests for {@link UMLRTExtModelElement} and {@link UMLRTFacadeModelElement}.
 */
@PluginResource("resource/modelelement/model.di")
public class ModelElementTest extends AbstractPapyrusTest {

	protected static Class capsule;
	protected static Class connectedCapsule;
	protected static Class libraryCapsule;
	protected static Class subcapsule;

	protected static Port capsule_externalBehavior;
	protected static Port capsule_internalBehavior;
	protected static Port capsule_relay;
	protected static Port capsule_externalBehaviorNotConnected;
	protected static Port capsule_internalBehaviorNotConnected;
	protected static Port capsule_relayNotConnected;
	protected static Port capsule_sap;
	protected static Port capsule_system_sap;
	protected static Port capsule_spp;
	protected static Port capsule_legacySap;
	protected static Port capsule_legacySpp;


	protected static Port connectedCapsule_externalBehavior;
	protected static Port connectedCapsule_internalBehavior;
	protected static Port connectedCapsule_relay;
	protected static Port connectedCapsule_sap;
	protected static Port connectedCapsule_spp;
	protected static Property connectedCapsule_capsule;

	protected static Port libraryCapsule_externalBehavior;
	protected static Port libraryCapsule_internalBehavior;
	protected static Port libraryCapsule_relay;
	protected static Port libraryCapsule_sap;
	protected static Port libraryCapsule_spp;
	protected static Port libraryCapsule_legacySap;
	protected static Port libraryCapsule_legacySpp;

	protected static Collaboration protocol1;
	protected static Collaboration protocol2;
	protected static Interface protocol2In;
	protected static Interface protocol2Out;
	protected static Interface protocol2InOut;
	protected static Interface protocol1In;
	protected static Interface protocol1Out;
	protected static Interface protocol1InOut;
	protected static Operation greetIn;
	protected static Operation replyOut;
	protected static Operation exchangeInOut;

	protected static StateMachine statemachine;
	protected static Transition transition1;

	// FeaturePaths
	private static final String KIND_ERROR_MESSAGE = "[kind] disable only if ReadOnly";
	protected static final String UNKNOWN = "TestUnknown";
	private static final String UNKNOWN_ERROR_MESSAGE = "[Unknown feature] is always disable"; // current implementation
	protected static final String IS_CONJUGATED = "isConjugated";
	protected static final String IS_CONJUGATED_ERROR_MESSAGE = "[Conjugated] disable only if ReadOnly";
	protected static final String IS_SERVICE = "isService";
	protected static final String IS_SERVICE_ERROR_MESSAGE = "[Service] disable if connected || ! isBehavior || ReadOnly";

	protected static final String IS_WIRED = "isWired";
	protected static final String IS_WIRED_MESSAGE = "[isWired] disabled is connected || !isBehavior";

	protected static final String IS_BEHAVIOR = "isBehavior";
	protected static final String IS_BEHAVIOR_MESSAGE = "[isBehavior] disabled is !isWired || (isWired && !isService)";

	protected static final String IS_PUBLISH = "isPublish";
	protected static final String IS_PUBLISH_MESSAGE = "[isPublish] always disabled";

	protected static final String IS_NOTIFICATION = "isNotification";
	protected static final String IS_NOTIFICATION_MESSAGE = "[isNotification] disabled if !behavior";

	protected static final String REGISTRATION_KIND = "registration";
	protected static final String REGISTRATION_KIND_MESSAGE = "[Registration kind] disabled if isWired";

	protected static final String REGISTRATION_OVERRIDE = "registrationOverride";
	protected static final String REGISTRATION_OVERRIDE_MESSAGE = "[Registration override] disabled if isWired";

	protected static final String TRANSITION_KIND = "registrationOverride";
	protected static final String TRANSITION_KIND_MESSAGE = "Kind must be enabled";

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@ClassRule
	public final static ModelSetFixture modelSetFixture = new UMLRTModelSetFixture();

	// Some of the observables tested herein are exercised and, therefore, must
	// be accessed on the UI thread (which provides their default realm)
	@Rule
	public final TestRule uiThread = new UIThreadRule(true);

	@Rule
	public final AnnotationRule<ModelElementKind> modelElementKind = AnnotationRule.create(ModelElementFactory.class, ModelElementKind.UML);

	@BeforeClass
	public static void initModelElements() throws Exception {
		// capsules
		capsule = (Class) modelSetFixture.getModel().getMember("Capsule");
		Assert.assertNotNull(capsule);
		connectedCapsule = (Class) modelSetFixture.getModel().getMember("ConnectedCapsule");
		Assert.assertNotNull(connectedCapsule);
		libraryCapsule = (Class) modelSetFixture.getModel().getMember("LibraryCapsule");
		Assert.assertNotNull(libraryCapsule);
		subcapsule = (Class) modelSetFixture.getModel().getMember("Subcapsule");
		Assert.assertNotNull(subcapsule);

		// ports
		capsule_externalBehavior = capsule.getOwnedPort("externalBehavior", null);
		Assert.assertNotNull(capsule_externalBehavior);
		capsule_internalBehavior = capsule.getOwnedPort("internalBehavior", null);
		Assert.assertNotNull(capsule_internalBehavior);
		capsule_relay = capsule.getOwnedPort("relay", null);
		Assert.assertNotNull(capsule_relay);
		capsule_externalBehaviorNotConnected = capsule.getOwnedPort("externalBehaviorNotConnected", null);
		Assert.assertNotNull(capsule_externalBehaviorNotConnected);
		capsule_internalBehaviorNotConnected = capsule.getOwnedPort("internalBehaviorNotConnected", null);
		Assert.assertNotNull(capsule_internalBehaviorNotConnected);
		capsule_relayNotConnected = capsule.getOwnedPort("relayNotConnected", null);
		Assert.assertNotNull(capsule_relayNotConnected);
		capsule_system_sap = capsule.getOwnedPort("frame", null);
		Assert.assertNotNull(capsule_system_sap);
		capsule_sap = capsule.getOwnedPort("sap", null);
		Assert.assertNotNull(capsule_sap);
		capsule_spp = capsule.getOwnedPort("spp", null);
		Assert.assertNotNull(capsule_spp);
		Assert.assertNotNull(capsule_relay);
		capsule_legacySap = capsule.getOwnedPort("legacySap", null);
		Assert.assertNotNull(capsule_legacySap);
		capsule_legacySpp = capsule.getOwnedPort("legacySpp", null);
		Assert.assertNotNull(capsule_legacySpp);

		connectedCapsule_externalBehavior = connectedCapsule.getOwnedPort("externalBehavior", null);
		Assert.assertNotNull(connectedCapsule_externalBehavior);
		connectedCapsule_internalBehavior = connectedCapsule.getOwnedPort("internalBehavior", null);
		Assert.assertNotNull(connectedCapsule_internalBehavior);
		connectedCapsule_relay = connectedCapsule.getOwnedPort("relay", null);
		Assert.assertNotNull(connectedCapsule_relay);
		connectedCapsule_sap = connectedCapsule.getOwnedPort("sap", null);
		Assert.assertNotNull(connectedCapsule_sap);
		connectedCapsule_spp = connectedCapsule.getOwnedPort("spp", null);
		Assert.assertNotNull(connectedCapsule_spp);

		libraryCapsule_externalBehavior = libraryCapsule.getOwnedPort("externalBehavior", null);
		Assert.assertNotNull(libraryCapsule_externalBehavior);
		libraryCapsule_internalBehavior = libraryCapsule.getOwnedPort("internalBehavior", null);
		Assert.assertNotNull(libraryCapsule_internalBehavior);
		libraryCapsule_relay = libraryCapsule.getOwnedPort("relay", null);
		Assert.assertNotNull(libraryCapsule_relay);
		libraryCapsule_sap = libraryCapsule.getOwnedPort("sap", null);
		Assert.assertNotNull(libraryCapsule_sap);
		libraryCapsule_spp = libraryCapsule.getOwnedPort("spp", null);
		Assert.assertNotNull(libraryCapsule_spp);
		libraryCapsule_legacySap = libraryCapsule.getOwnedPort("legacySap", null);
		Assert.assertNotNull(libraryCapsule_legacySap);
		libraryCapsule_legacySpp = libraryCapsule.getOwnedPort("legacySpp", null);
		Assert.assertNotNull(libraryCapsule_legacySpp);

		// capsule-parts
		connectedCapsule_capsule = connectedCapsule.getOwnedAttribute("capsule", null);
		Assert.assertNotNull(connectedCapsule_capsule);

		// Protocols

		protocol1 = (Collaboration) modelSetFixture.getModel().getNestedPackage("Protocol1")
				.getOwnedType("Protocol1", false, UMLPackage.Literals.COLLABORATION, false);
		assertThat("Protocol1 not found in test model", protocol1, notNullValue());
		protocol2 = (Collaboration) modelSetFixture.getModel().getNestedPackage("Protocol2")
				.getOwnedType("Protocol2", false, UMLPackage.Literals.COLLABORATION, false);
		assertThat("Protocol2 not found in test model", protocol2, notNullValue());
		protocol1In = ProtocolUtils.getMessageSetIn(protocol1);
		assertThat("Protocol1 IN message-set not found in test model", protocol1In, notNullValue());
		greetIn = protocol1In.getOperation("greet", null, null);
		assertThat("Protocol1 IN message 'greet' not found in test model", greetIn, notNullValue());
		protocol1Out = ProtocolUtils.getMessageSetOut(protocol1);
		assertThat("Protocol1 OUT message-set not found in test model", protocol1Out, notNullValue());
		replyOut = protocol1Out.getOperation("reply", null, null);
		assertThat("Protocol1 OUT message 'reply' not found in test model", replyOut, notNullValue());
		protocol1InOut = ProtocolUtils.getMessageSetInOut(protocol1);
		assertThat("Protocol1 INOUT message-set not found in test model", protocol1InOut, notNullValue());
		exchangeInOut = protocol1InOut.getOperation("exchange", null, null);
		assertThat("Protocol1 INOUT message 'exchange' not found in test model", exchangeInOut, notNullValue());
		protocol2In = ProtocolUtils.getMessageSetIn(protocol2);
		assertThat("Protocol2 IN message-set not found in test model", protocol2In, notNullValue());
		protocol2Out = ProtocolUtils.getMessageSetOut(protocol2);
		assertThat("Protocol2 OUT message-set not found in test model", protocol2Out, notNullValue());
		protocol2InOut = ProtocolUtils.getMessageSetInOut(protocol2);
		assertThat("Protocol2 INOUT message-set not found in test model", protocol2InOut, notNullValue());

		statemachine = (StateMachine) capsule.getOwnedBehaviors().get(0);
		Assert.assertNotNull(statemachine);
		State state1 = (State) statemachine.getRegion("Region").getSubvertex("State1");
		Assert.assertNotNull(state1);
		transition1 = state1.getRegion("Region1").getTransition("local_transition");
		Assert.assertNotNull(transition1);
	}

	@Test
	public void validateTransition() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(transition1);
		assertFalse(TRANSITION_KIND_MESSAGE, isDeactivated(umlrtExtModelElement, TRANSITION_KIND)); // it is connected outside
	}

	@Test
	public void validateStandardCapsuleExternalBehaviorPort() throws Exception {
		assertEquals(UMLRTPortKind.EXTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_externalBehavior));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_externalBehavior);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));// it is connected outside
		assertFalse(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_externalBehavior), capsule_externalBehavior.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED)); // it is connected outside
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleInternalBehaviorPort() throws Exception {
		assertEquals(UMLRTPortKind.INTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_internalBehavior));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_internalBehavior);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE)); // is is connected outside
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_internalBehavior), capsule_internalBehavior.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleRelayPort() throws Exception {
		assertEquals(UMLRTPortKind.RELAY, RTPortUtils.getPortKind(capsule_relay));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_relay);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertFalse(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_relay), capsule_relay.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));

	}

	@Test
	public void validateStandardCapsuleExternalBehaviorNotConnectedPort() throws Exception {
		assertEquals(UMLRTPortKind.EXTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_externalBehaviorNotConnected));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_externalBehaviorNotConnected);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertFalse(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_externalBehaviorNotConnected), capsule_externalBehaviorNotConnected.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertFalse(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleInternalBehaviorNotConnectedPort() throws Exception {
		assertEquals(UMLRTPortKind.INTERNAL_BEHAVIOR, RTPortUtils.getPortKind(capsule_internalBehaviorNotConnected));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_internalBehaviorNotConnected);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_internalBehaviorNotConnected), capsule_internalBehaviorNotConnected.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertFalse(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleRelayNotConnectedPort() throws Exception {
		assertEquals(UMLRTPortKind.RELAY, RTPortUtils.getPortKind(capsule_relayNotConnected));
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_relayNotConnected);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertFalse(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_relayNotConnected), capsule_relayNotConnected.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));

	}

	@Test
	public void validateStandardCapsuleSAPSystemProtocol() throws Exception {
		assertEquals(UMLRTPortKind.SAP, RTPortUtils.getPortKind(capsule_system_sap));

		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_system_sap);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_system_sap), capsule_system_sap.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleSAP() throws Exception {
		assertEquals(UMLRTPortKind.SAP, RTPortUtils.getPortKind(capsule_sap));
		assertFalse(RTPortUtils.isLegacySpp(capsule_sap));
		assertFalse(RTPortUtils.isLegacySap(capsule_sap));

		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_sap);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_sap), capsule_sap.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertFalse(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertFalse(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertFalse(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleSPP() throws Exception {
		assertEquals(UMLRTPortKind.SPP, RTPortUtils.getPortKind(capsule_spp));
		assertFalse(RTPortUtils.isLegacySpp(capsule_spp));
		assertFalse(RTPortUtils.isLegacySap(capsule_spp));

		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_spp);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_spp), capsule_spp.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertFalse(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertFalse(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertFalse(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleLegacySAP() throws Exception {
		assertEquals(UMLRTPortKind.SAP, RTPortUtils.getPortKind(capsule_legacySap));
		assertFalse(RTPortUtils.isLegacySpp(capsule_legacySap));
		assertTrue(RTPortUtils.isLegacySap(capsule_legacySap));

		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_legacySap);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_legacySap), capsule_legacySap.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertFalse(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertFalse(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateStandardCapsuleLegacySPP() throws Exception {
		assertEquals(UMLRTPortKind.SPP, RTPortUtils.getPortKind(capsule_legacySpp));
		assertTrue(RTPortUtils.isLegacySpp(capsule_legacySpp));
		assertFalse(RTPortUtils.isLegacySap(capsule_legacySpp));

		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(capsule_legacySpp);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertFalse(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(capsule_legacySpp), capsule_legacySpp.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertFalse(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertFalse(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateConnectedCapsuleExternalBehaviorPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(connectedCapsule_externalBehavior);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertFalse(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(connectedCapsule_externalBehavior), connectedCapsule_externalBehavior.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateConnectedCapsuleInternalBehaviorPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(connectedCapsule_internalBehavior);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(connectedCapsule_internalBehavior), connectedCapsule_internalBehavior.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertFalse(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateConnectedCapsuleRelayPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(connectedCapsule_relay);
		assertFalse(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR)); // is is connected inside
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(connectedCapsule_relay), connectedCapsule_relay.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertFalse(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	/*
	 * No Test for ConencttedCapsule SPP & SAP, as it should not be possible to connect them
	 */

	@Test
	public void validateLibraryCapsuleExternalBehaviorPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_externalBehavior);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_externalBehavior), libraryCapsule_externalBehavior.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateLibraryCapsuleInternalBehaviorPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_internalBehavior);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_internalBehavior), libraryCapsule_internalBehavior.getAppliedStereotype("UMLRealTime::RTPort"),
				modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateLibraryCapsuleRelayPort() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_relay);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_relay), libraryCapsule_relay.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));
	}

	@Test
	public void validateLibraryCapsuleSAP() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_sap);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_sap), libraryCapsule_sap.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));// isWired, but read only (library)
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));// isWired, but read only (library)
	}

	@Test
	public void validateLibraryCapsuleSPP() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_spp);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_spp), libraryCapsule_spp.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND)); // isWired, but read aonly (library)
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE)); // isWired, but read aonly (library)
	}

	@Test
	public void validateLibraryCapsuleLegacySAP() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_legacySap);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_legacySap), libraryCapsule_legacySap.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND)); // isWired, but read only (library)
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE)); // isWired, but read only (library)
	}

	@Test
	public void validateLibraryCapsuleLegacySPP() throws Exception {
		// test Property paths on UML Element
		ModelElement umlrtExtModelElement = modelElement(libraryCapsule_legacySpp);
		assertTrue(IS_CONJUGATED_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_CONJUGATED));
		assertTrue(IS_SERVICE_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_SERVICE));
		assertTrue(IS_BEHAVIOR_MESSAGE, isDeactivated(umlrtExtModelElement, IS_BEHAVIOR));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(umlrtExtModelElement, UNKNOWN));
		// test Property Paths for RTStereotypeModelElement

		RTStereotypeModelElement rtStereotypeModelElement = new RTStereotypeModelElement(RTPortUtils.getStereotypeApplication(libraryCapsule_legacySpp), libraryCapsule_legacySpp.getAppliedStereotype("UMLRealTime::RTPort"), modelSetFixture.getEditingDomain());
		assertTrue(KIND_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, IRealTimeConstants.KIND));
		assertTrue(IS_WIRED_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_WIRED));
		assertTrue(IS_NOTIFICATION_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_NOTIFICATION));
		assertTrue(IS_PUBLISH_MESSAGE, isDeactivated(rtStereotypeModelElement, IS_PUBLISH));
		assertTrue(UNKNOWN_ERROR_MESSAGE, isDeactivated(rtStereotypeModelElement, UNKNOWN));
		assertTrue(REGISTRATION_KIND_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_KIND));// isWired, but read only (library)
		assertTrue(REGISTRATION_OVERRIDE_MESSAGE, isDeactivated(rtStereotypeModelElement, REGISTRATION_OVERRIDE));// isWired, but read only (library)
	}

	protected boolean isDeactivated(ModelElement modelElement, String propertyPath) {
		return !modelElement.isEditable(propertyPath);
	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsuleNameProperty() {
		assertNameProperty(capsule);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsuleNameValidation() {
		assertNameValidation(capsule, "ConnectedCapsule");
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsuleNameEditable() {
		assertNameEditable(capsule, true);
		assertNameEditable(subcapsule, true);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void portNameEditable() {
		assertNameEditable(connectedCapsule_externalBehavior, true);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void inheritedPortNameNotEditable() {
		assertNameEditable(subcapsule.getMember(connectedCapsule_externalBehavior.getName()), false);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsulePartNameEditable() {
		assertNameEditable(connectedCapsule_capsule, true);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void inheritedCapsulePartNameNotEditable() {
		assertNameEditable(subcapsule.getMember(connectedCapsule_capsule.getName()), false);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@UIThread
	@Test
	public void protocolNameProperty() {
		assertNameProperty(protocol1);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolNameValidation() {
		assertNameValidation(protocol1, "Protocol2");
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolNameEditable() {
		assertNameEditable(protocol1, true);
		assertNameEditable(protocol2, true);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolMessageNameEditable() {
		assertNameEditable(greetIn, true);
		assertNameEditable(replyOut, true);
		assertNameEditable(exchangeInOut, true);
	}

	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void inheritedProtocolMessageNameNotEditable() {
		assertNameEditable(protocol2In.getMember(greetIn.getName()), false);
		assertNameEditable(protocol2Out.getMember(replyOut.getName()), false);
		assertNameEditable(protocol2InOut.getMember(exchangeInOut.getName()), false);
	}

	@UIThread
	@Test
	public void protocolMessageParameterNameProperty() {
		Parameter data = greetIn.getOwnedParameters().get(0);
		assertNameProperty(data);
	}

	@UIThread
	@Test
	public void protocolMessageParameterTypeProperty() {
		Parameter data = greetIn.getOwnedParameters().get(0);
		ModelElement modelElement = modelElement(data);

		// Check that we are presenting the correct labels
		ILabelProvider labels = modelElement.getLabelProvider("type");
		assertThat(labels.getText(data.getType()), containsString("String"));

		// Check that we can clear the value (set the type to '*')
		IObservableValue<?> value = (IObservableValue<?>) modelElement.getObservable("type");
		assertThat("No observable value provided for 'type'", value, notNullValue());
		assertThat("Observable value not editable for 'type'", modelElement.isEditable("type"), is(true));
		value.setValue(null);
		assertThat(labels.getText(data.getType()), is("*"));

		// And check the value presented in the properties widget
		IPapyrusConverter converter = modelElement.getPapyrusConverter("type");
		assertThat("No converter provided for 'type'", converter, notNullValue());
		assertThat(converter.canonicalToEditValue(data.getType(), 0), is("*"));

	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsulePortsProperty() {
		ModelElement modelElement = modelElement(capsule);
		IObservableList<?> list = (IObservableList<?>) modelElement.getObservable("port");
		UMLRTPort port = require(list, UMLRTPort.class, capsule_relay.getName());
		assertThat(port.toUML(), is(capsule_relay));
	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void capsuleCapsulePartsProperty() {
		ModelElement modelElement = modelElement(connectedCapsule);
		IObservableList<?> list = (IObservableList<?>) modelElement.getObservable("capsulePart");
		UMLRTCapsulePart part = require(list, UMLRTCapsulePart.class, connectedCapsule_capsule.getName());
		assertThat(part.toUML(), is(connectedCapsule_capsule));
	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolInMessagesProperty() {
		ModelElement modelElement = modelElement(protocol1);
		IObservableList<?> list = (IObservableList<?>) modelElement.getObservable("Incoming");
		UMLRTProtocolMessage msg = require(list, UMLRTProtocolMessage.class, greetIn.getName());
		assertThat(msg.toUML(), is(greetIn));
	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolOutMessagesProperty() {
		ModelElement modelElement = modelElement(protocol1);
		IObservableList<?> list = (IObservableList<?>) modelElement.getObservable("Outgoing");
		UMLRTProtocolMessage msg = require(list, UMLRTProtocolMessage.class, replyOut.getName());
		assertThat(msg.toUML(), is(replyOut));
	}

	@UIThread
	@ModelElementFactory(ModelElementKind.FACADE)
	@Test
	public void protocolInOutMessagesProperty() {
		ModelElement modelElement = modelElement(protocol1);
		IObservableList<?> list = (IObservableList<?>) modelElement.getObservable("InOut");
		UMLRTProtocolMessage msg = require(list, UMLRTProtocolMessage.class, exchangeInOut.getName());
		assertThat(msg.toUML(), is(exchangeInOut));
	}

	//
	// Test framework
	//

	void assertNameProperty(NamedElement element) {
		ModelElement modelElement = modelElement(element);
		IObservable obs = modelElement.getObservable("name");
		assertThat("No observable value provided for 'name'", obs, instanceOf(IObservableValue.class));

		@SuppressWarnings("unchecked")
		IObservableValue<String> value = (IObservableValue<String>) obs;
		assertThat("Observable value does not access the name", value.getValue(), is(element.getName()));
		assertThat("'name' property not editable", modelElement.isEditable("name"), is(true));

		final String originalName = element.getName();
		value.setValue("new-name");
		assertThat("Observable did not change the name", element.getName(), is("new-name"));
		modelSetFixture.undo();
		assertThat("Observable did not use a command for edit", element.getName(), is(originalName));
	}

	void assertNameValidation(NamedElement element, String clashingName) {
		ModelElement modelElement = modelElement(element);
		IValidator validator = modelElement.getValidator("name");
		assertThat("No validator provided for 'name'", validator, notNullValue());
		assertThat("Current name rejected", validator.validate(element.getName()), hasSeverity(IStatus.OK));
		assertThat("No warning on name clash", validator.validate(clashingName), hasSeverity(IStatus.WARNING));
		assertThat("No error on blank name", validator.validate("   "), hasSeverity(IStatus.ERROR));
	}

	void assertNameEditable(NamedElement element, boolean expected) {
		ModelElement modelElement = modelElement(element);
		assertThat("Incorrect editability of name", modelElement.isEditable("name"), is(expected));
	}

	ModelElement modelElement(NamedElement element) {
		ModelElement result;

		switch (modelElementKind.get()) {
		case UML:
			result = new UMLRTExtModelElement(element, modelSetFixture.getEditingDomain());
			break;
		case PROFILE:
			@SuppressWarnings("unchecked")
			RTStereotypeModelElement stereotype = Stream.of(UMLRealTimePackage.Literals.CAPSULE, UMLRealTimePackage.Literals.PROTOCOL,
					UMLRealTimePackage.Literals.RT_PORT, UMLRealTimePackage.Literals.CAPSULE_PART,
					UMLRealTimePackage.Literals.RT_CONNECTOR,
					UMLRealTimePackage.Literals.RT_MESSAGE_SET, UMLRealTimePackage.Literals.PROTOCOL_CONTAINER)
					.map(eclass -> UMLUtil.getStereotypeApplication(element, (java.lang.Class<? extends EObject>) eclass.getInstanceClass()))
					.filter(Objects::nonNull)
					.map(appl -> new RTStereotypeModelElement(appl, UMLUtil.getStereotype(appl), modelSetFixture.getEditingDomain()))
					.findAny()
					.orElseThrow(() -> new AssertionError("No UML-RT stereotype applied to " + element));
			result = stereotype;
			break;
		case FACADE:
			result = new UMLRTFacadeModelElement(UMLRTFactory.create(element));
			break;
		default:
			fail("Invalid model element kind: " + modelElementKind.get());
			throw new Error(); // Make the compiler happy
		}

		return result;
	}

	<T extends UMLRTNamedElement> Optional<T> find(Collection<?> collection, java.lang.Class<T> type, String name) {
		return collection.stream()
				.filter(type::isInstance).map(type::cast)
				.filter(e -> name.equals(e.getName()))
				.findAny();
	}

	<T extends UMLRTNamedElement> T require(Collection<?> collection, java.lang.Class<T> type, String name) {
		return find(collection, type, name)
				.orElseThrow(() -> new AssertionError(String.format("%s not found: %s", type.getSimpleName(), name)));
	}

	//
	// Nested types
	//

	/**
	 * Enumeration of the kinds of model elements covered by this test suite.
	 */
	public static enum ModelElementKind {
		/** The normal UML-ish model element. */
		UML,
		/** A model element for the stereotype application from the UML-RT profile, specifically. */
		PROFILE,
		/** The model element for the UML-RT façades. */
		FACADE;
	}

	/**
	 * Annotates tests to indicate the kind of model element to create.
	 * The default is the normal UML-ish model element.
	 */
	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	public static @interface ModelElementFactory {
		ModelElementKind value() default ModelElementKind.UML;
	}

}
