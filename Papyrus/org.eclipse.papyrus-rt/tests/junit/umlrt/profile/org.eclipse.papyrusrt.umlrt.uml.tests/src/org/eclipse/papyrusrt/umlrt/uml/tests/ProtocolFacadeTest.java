/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the protocol façade class {@link UMLRTProtocol}.
 */
@TestModel("inheritance/ports.uml")
@Category({ ProtocolTests.class, FacadeTests.class })
public class ProtocolFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ProtocolFacadeTest() {
		super();
	}

	@Test
	public void messages() {
		UMLRTProtocol protocol = getProtocol("Protocol1");
		List<UMLRTProtocolMessage> messages = protocol.getMessages();
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.get(0).getName(), is("greet"));
		assertThat(messages.get(0).getKind(), is(RTMessageKind.IN));
		assertThat(messages.get(0).toReceiveEvent(), notNullValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/ports.uml")
	public void facadeGetInMessages() {
		UMLRTProtocol super_ = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol2");
		protocol.setSuperProtocol(super_);

		List<UMLRTProtocolMessage> messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__IN_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("greet"));

		messages.get(0).exclude();

		assumeThat(protocol.getInMessages().size(), is(0));

		messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__IN_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("greet"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/ports.uml")
	public void facadeGetOutMessages() {
		UMLRTProtocol super_ = fixture.getRoot().getProtocol("Protocol1");
		super_.createMessage(RTMessageKind.OUT, "testOutMessage");
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol2");
		protocol.setSuperProtocol(super_);

		List<UMLRTProtocolMessage> messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__OUT_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("testOutMessage"));

		messages.get(0).exclude();

		assumeThat(protocol.getOutMessages().size(), is(0));

		messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__OUT_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("testOutMessage"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/ports.uml")
	public void facadeGetInOutMessages() {
		UMLRTProtocol super_ = fixture.getRoot().getProtocol("Protocol1");
		super_.createMessage(RTMessageKind.IN_OUT, "testInOutMessage");
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol2");
		protocol.setSuperProtocol(super_);

		List<UMLRTProtocolMessage> messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__IN_OUT_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("testInOutMessage"));

		messages.get(0).exclude();

		assumeThat(protocol.getInOutMessages().size(), is(0));

		messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__IN_OUT_MESSAGE, true);
		assertThat(messages.size(), is(1));
		assertThat(messages.get(0).getName(), is("testInOutMessage"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/ports.uml")
	public void facadeGetMessages() {
		UMLRTProtocol super_ = fixture.getRoot().getProtocol("Protocol1");
		super_.createMessage(RTMessageKind.OUT, "testOutMessage");
		super_.createMessage(RTMessageKind.IN_OUT, "testInOutMessage");
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol2");
		protocol.setSuperProtocol(super_);

		List<UMLRTProtocolMessage> messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE, true);
		assertThat(messages.size(), is(3));
		assertThat(messages.get(0).getName(), is("greet"));
		assertThat(messages.get(1).getName(), is("testOutMessage"));
		assertThat(messages.get(2).getName(), is("testInOutMessage"));

		messages.get(1).exclude();

		assumeThat(protocol.getMessages().size(), is(2));
		assumeThat(protocol.getMessages().get(0).getName(), is("greet"));
		assumeThat(protocol.getMessages().get(1).getName(), is("testInOutMessage"));

		messages = (List<UMLRTProtocolMessage>) ((InternalFacadeObject) protocol).facadeGet(UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE, true);
		assertThat(messages.size(), is(3));
		assertThat(messages.get(0).getName(), is("greet"));
		assertThat(messages.get(1).getName(), is("testOutMessage"));
		assertThat(messages.get(2).getName(), is("testInOutMessage"));
	}

	@Test
	public void getMessage() {
		UMLRTProtocol protocol = getProtocol("Protocol1");

		UMLRTProtocolMessage message = protocol.getMessage("greet");
		assertThat(message, notNullValue());

		message = protocol.getMessage("foo");
		assertThat(message, nullValue());
	}

	@Test
	public void getMessageByKind() {
		UMLRTProtocol protocol = getProtocol("Protocol1");

		UMLRTProtocolMessage message = protocol.getMessage(RTMessageKind.IN, "greet");
		assertThat(message, notNullValue());

		message = protocol.getMessage(RTMessageKind.OUT, "greet");
		assertThat(message, nullValue());
	}

	@Test
	public void createMessage() {
		UMLRTProtocol protocol = getProtocol("Protocol1");

		UMLRTProtocolMessage message = protocol.createMessage(RTMessageKind.OUT, "status",
				(PrimitiveType) fixture.getModel().getMember("Integer"));

		assertThat(message, notNullValue());
		assertThat(message.getKind(), is(RTMessageKind.OUT));
		assertThat(message.toUML().getInterface().getName(), is("Protocol1~"));
		assertThat(message.toUML().getOwnedParameters().size(), is(1));
		assertThat(message.toUML().getOwnedParameters().get(0).getName(), is("data"));
		assertThat(message.toUML().getOwnedParameters().get(0).getType().getName(), is("Integer"));
		assertThat(message.toReceiveEvent(), notNullValue());
	}

	@Test
	public void setMessageKind() {
		UMLRTProtocol protocol = getProtocol("Protocol1");

		UMLRTProtocolMessage message = protocol.getMessage(RTMessageKind.IN, "greet");
		assumeThat(message, notNullValue());

		message.setKind(RTMessageKind.OUT);

		assertThat(message.getKind(), is(RTMessageKind.OUT));
		assertThat(message.toUML().getInterface().getName(), is("Protocol1~"));
		assertThat(message.toUML().getOwnedParameters().size(), is(1));
		assertThat(message.toUML().getOwnedParameters().get(0).getName(), is("data"));
		assertThat(message.toUML().getOwnedParameters().get(0).getType().getName(), is("String"));
	}

	@Test
	public void inheritance() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assertThat(protocol2.getSuperProtocol(), nullValue());

		protocol2.setSuperProtocol(protocol1);
		assertThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited messages
		List<UMLRTProtocolMessage> messages = protocol2.getMessages();
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.get(0).getName(), is("greet"));
		assertThat(messages.get(0).toUML().isSetName(), is(false));
		assertThat(protocol2.getMessage("greet").toUML(), redefines(protocol1.getMessage("greet").toUML()));

		// Message sets
		Interface in = fixture.getElement("Protocol2::Protocol2", Interface.class);
		Interface out = fixture.getElement("Protocol2::Protocol2~", Interface.class);
		Interface inout = fixture.getElement("Protocol2::Protocol2IO", Interface.class);
		assertThat(in.getGenerals(), hasItem(named("Protocol1")));
		assertThat(out.getGenerals(), hasItem(named("Protocol1~")));
		assertThat(inout.getGenerals(), hasItem(named("Protocol1IO")));

		// Clear the super
		protocol2.setSuperProtocol(null);
		assertThat(protocol2.getSuperProtocol(), nullValue());
		assertThat(in.getGenerals().isEmpty(), is(true));
		assertThat(out.getGenerals().isEmpty(), is(true));
		assertThat(inout.getGenerals().isEmpty(), is(true));
		assertThat(protocol2.getMessages().isEmpty(), is(true));
	}

	@Test
	public void inheritanceViaUML() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assertThat(protocol2.getSuperProtocol(), nullValue());

		// Via the UML API
		protocol2.toUML().createGeneralization(protocol1.toUML());
		assertThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited messages
		List<UMLRTProtocolMessage> messages = protocol2.getMessages();
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.get(0).getName(), is("greet"));
		assertThat(messages.get(0).toUML().isSetName(), is(false));
		assertThat(protocol2.getMessage("greet").toUML(), redefines(protocol1.getMessage("greet").toUML()));

		// Message sets
		Interface in = fixture.getElement("Protocol2::Protocol2", Interface.class);
		Interface out = fixture.getElement("Protocol2::Protocol2~", Interface.class);
		Interface inout = fixture.getElement("Protocol2::Protocol2IO", Interface.class);
		assertThat(in.getGenerals(), hasItem(named("Protocol1")));
		assertThat(out.getGenerals(), hasItem(named("Protocol1~")));
		assertThat(inout.getGenerals(), hasItem(named("Protocol1IO")));

		// Clear the super
		protocol2.setSuperProtocol(null);
		assertThat(protocol2.getSuperProtocol(), nullValue());
		assertThat(in.getGenerals().isEmpty(), is(true));
		assertThat(out.getGenerals().isEmpty(), is(true));
		assertThat(inout.getGenerals().isEmpty(), is(true));
		assertThat(protocol2.getMessages().isEmpty(), is(true));
	}

	@Test
	public void newMessageInherited() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		UMLRTProtocolMessage newMessage = protocol1.createMessage(RTMessageKind.OUT, "reply");

		UMLRTProtocolMessage inherited = protocol2.getMessage("reply");
		assertThat(inherited, notNullValue());
		assertThat(inherited.getKind(), is(RTMessageKind.OUT));
		assertThat(inherited.toUML(), redefines(newMessage.toUML()));
		assertThat(inherited.getInheritedElement(), is(newMessage));
	}

	@Test
	public void messageParameters() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		UMLRTProtocolMessage newMessage = protocol1.createMessage(RTMessageKind.OUT, "reply");

		UMLRTProtocolMessage inherited = protocol2.getMessage("reply");
		assumeThat(inherited, notNullValue());

		Type stringType = (PrimitiveType) fixture.getModel().getMember("String");
		newMessage.createParameter("text", stringType);

		Parameter text = inherited.getParameter("text");
		assertThat(text, notNullValue());
		assertThat(UMLRTExtensionUtil.isVirtualElement(text), is(true)); // It's inherited

		assertThat(inherited.getParameter(stringType), is(text));
		assertThat(inherited.getParameters(), hasItem(text));
	}

	@Test
	public void conjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		assertThat(protocol1.isConjugate(), is(false));

		UMLRTProtocol protocol1_ = protocol1.getConjugate();
		assertThat(protocol1_.isConjugate(), is(true));
		assertThat(protocol1_.getConjugate(), is(protocol1));
		assertThat("Conjugate not cached", protocol1.getConjugate(), sameInstance(protocol1_));
		assertThat(protocol1_.getName(), is("Protocol1~"));

		protocol1_.setName("MyProtocol~");
		assertThat(protocol1.getName(), is("MyProtocol"));
	}

	@Test
	public void messageParameterInheritance() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assertThat(protocol2.getSuperProtocol(), nullValue());

		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited message parameters
		List<UMLRTProtocolMessage> messages = protocol2.getMessages();
		assumeThat(messages.isEmpty(), is(false));
		assumeThat(messages.get(0).getName(), is("greet"));

		EList<Parameter> parameters = messages.get(0).toUML().getOwnedParameters();
		assertThat(parameters, hasItem(named("data")));
		Parameter data = parameters.get(0);
		assertThat(data.isSetName(), is(false));
		assertThat(data.getName(), is("data"));
	}

	@Test
	public void messageReification() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assertThat(protocol2.getSuperProtocol(), nullValue());

		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited message
		List<UMLRTProtocolMessage> messages = protocol2.getMessages();
		assumeThat(messages.isEmpty(), is(false));
		assumeThat(messages.get(0).getName(), is("greet"));

		Operation operation = messages.get(0).toUML();

		assumeThat(UMLRTExtensionUtil.isVirtualElement(operation), is(true));

		// This reifies the operation
		ChangeDescription change = fixture.record(() -> operation.setName("sayHello"));

		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(false));

		// Must be careful not to reify the operation by mistake
		change = fixture.undo(change);

		// The operation was un-reified
		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(true));

		// Must be careful not to reify the operation by mistake
		change = fixture.undo(change); // Actually a redo!

		// The operation was re-reified
		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(false));
	}

	@Test
	public void messageReificationByParameter() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assertThat(protocol2.getSuperProtocol(), nullValue());

		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited message parameters
		List<UMLRTProtocolMessage> messages = protocol2.getMessages();
		assumeThat(messages.isEmpty(), is(false));
		assumeThat(messages.get(0).getName(), is("greet"));

		Operation operation = messages.get(0).toUML();
		Parameter parameter = operation.getOwnedParameter("data", null);

		assumeThat(UMLRTExtensionUtil.isVirtualElement(operation), is(true));
		assumeThat(UMLRTExtensionUtil.isVirtualElement(parameter), is(true));

		// This reifies both parameter (actually, all parameters) and operation
		ChangeDescription change = fixture.record(() -> parameter.setName("foo"));

		assertThat(UMLRTExtensionUtil.isVirtualElement(parameter), is(false));
		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(false));

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change);

		// The elements were un-reified
		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(true));
		assertThat(UMLRTExtensionUtil.isVirtualElement(parameter), is(true));

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change); // Actually a redo!

		// The elements were re-reified
		assertThat(UMLRTExtensionUtil.isVirtualElement(operation), is(false));
		assertThat(UMLRTExtensionUtil.isVirtualElement(parameter), is(false));
	}

	@Test
	public void superprotocolWithConjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1").getConjugate();
		UMLRTProtocol protocol2 = getProtocol("Protocol2").getConjugate();
		UMLRTProtocol protocol3 = createProtocol("Protocol3").getConjugate();

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getSuperProtocol(), nullValue());
		assertThat(protocol2.getSuperProtocol(), is(protocol1));
		assertThat(protocol3.getSuperProtocol(), is(protocol2));
	}

	@Test
	public void subprotocols() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");
		UMLRTProtocol protocol3 = createProtocol("Protocol3");

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getSubProtocols(), is(Arrays.asList(protocol2)));
		assertThat(protocol2.getSubProtocols(), is(Arrays.asList(protocol3)));
	}

	@Test
	public void subprotocolsWithConjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1").getConjugate();
		UMLRTProtocol protocol2 = getProtocol("Protocol2").getConjugate();
		UMLRTProtocol protocol3 = createProtocol("Protocol3").getConjugate();

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getSubProtocols(), is(Arrays.asList(protocol2)));
		assertThat(protocol2.getSubProtocols(), is(Arrays.asList(protocol3)));
	}

	@Test
	public void hierarchy() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");
		UMLRTProtocol protocol3 = createProtocol("Protocol3");

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getHierarchy().collect(toList()),
				is(Arrays.asList(protocol1, protocol2, protocol3)));
		assertThat(protocol2.getHierarchy().collect(toList()),
				is(Arrays.asList(protocol2, protocol3)));
	}

	@Test
	public void hierarchyWithConjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1").getConjugate();
		UMLRTProtocol protocol2 = getProtocol("Protocol2").getConjugate();
		UMLRTProtocol protocol3 = createProtocol("Protocol3").getConjugate();

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getHierarchy().collect(toList()),
				is(Arrays.asList(protocol1, protocol2, protocol3)));
		assertThat(protocol2.getHierarchy().collect(toList()),
				is(Arrays.asList(protocol2, protocol3)));
	}

	@Test
	public void ancestry() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");
		UMLRTProtocol protocol3 = createProtocol("Protocol3");

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getAncestry(),
				is(Collections.singletonList(protocol1)));
		assertThat(protocol2.getAncestry(),
				is(Arrays.asList(protocol2, protocol1)));
		assertThat(protocol3.getAncestry(),
				is(Arrays.asList(protocol3, protocol2, protocol1)));
	}

	@Test
	public void ancestryWithConjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1").getConjugate();
		UMLRTProtocol protocol2 = getProtocol("Protocol2").getConjugate();
		UMLRTProtocol protocol3 = createProtocol("Protocol3").getConjugate();

		protocol2.toUML().createGeneralization(protocol1.toUML());
		protocol3.toUML().createGeneralization(protocol2.toUML());

		assertThat(protocol1.getAncestry(),
				is(Collections.singletonList(protocol1)));
		assertThat(protocol2.getAncestry(),
				is(Arrays.asList(protocol2, protocol1)));
		assertThat(protocol3.getAncestry(),
				is(Arrays.asList(protocol3, protocol2, protocol1)));
	}

	@Test
	public void anyReceiveEvent() {
		UMLRTProtocol newProtocol = createProtocol("NewProtocol");

		AnyReceiveEvent wildcard = newProtocol.getAnyReceiveEvent();
		assertThat("No any-receive-event", wildcard, notNullValue());
		assertThat(wildcard.getName(), is("*"));
		assertThat(wildcard.getOwner(), is(newProtocol.toUML().getNearestPackage()));
	}

	/**
	 * Check that attempts to access the façade of a deleted protocol, as in
	 * the Properties View UI, do not cause exceptions.
	 */
	@Test
	public void deletedProtocolMessageSets_bug512666() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		protocol2.setSuperProtocol(protocol1);

		// Simulate destruction in the UI, which completely disaggregates the tree
		fixture.destroy(protocol2.toUML().getNearestPackage());

		// Trying to access the message-sets should not throw
		assertThat(protocol2.getExcludedElements(), not(hasItem(anything())));
	}

	@Test
	public void messagesAddedToConjugate() {
		UMLRTProtocol protocol2 = getProtocol("Protocol2");
		UMLRTProtocol protocol2$ = protocol2.getConjugate();

		assertThat(protocol2$.getInMessages(), not(hasItem(anything())));
		assertThat(protocol2$.getOutMessages(), not(hasItem(anything())));
		assertThat(protocol2$.getInOutMessages(), not(hasItem(anything())));

		protocol2.createMessage(RTMessageKind.IN, "anInMessage");
		protocol2.createMessage(RTMessageKind.OUT, "anOutMessage");
		protocol2.createMessage(RTMessageKind.IN_OUT, "anInOutMessage");

		assertThat(protocol2$.getInMessages(), hasItem(FacadeMatchers.named("anOutMessage")));
		assertThat(protocol2$.getOutMessages(), hasItem(FacadeMatchers.named("anInMessage")));
		assertThat(protocol2$.getInOutMessages(), hasItem(FacadeMatchers.named("anInOutMessage")));

	}

	@Test
	public void undoDestroyInheritingProtocol() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		UMLRTProtocol protocol2 = getProtocol("Protocol2");

		assumeThat(protocol2.getSuperProtocol(), nullValue());
		protocol2.setSuperProtocol(protocol1);
		assumeThat(protocol2.getSuperProtocol(), is(protocol1));

		// Inherited message
		List<UMLRTProtocolMessage> messages = protocol2.getInMessages();
		assumeThat(messages, hasItem(FacadeMatchers.named("greet")));
		messages.forEach(m -> assumeThat("Message not inherited", m, FacadeMatchers.isInherited()));

		// Run this a few times in succession
		fixture.repeat(3, () -> {
			ChangeDescription change = fixture.record(() -> {
				// GMF-style destroy
				Collaboration collab = protocol2.toUML();
				org.eclipse.uml2.uml.Package container = collab.getPackage();
				fixture.destroy(container);
			});

			assumeThat("Protocol not destroyed", protocol2.toUML().eResource(), nullValue());
			assumeThat("Message not removed", messages.size(), is(0));

			fixture.undo(change);

			assertThat("Message not restored", messages, hasItem(FacadeMatchers.named("greet")));
			assertThat("Duplicate messages", messages.size(), is(1));

			UMLRTProtocolMessage greet = protocol2.getInMessage("greet");
			assertThat("Message not properly inherited", greet, FacadeMatchers.isInherited());
			assertThat("Message not propertly contained",
					((InternalEObject) greet.toUML()).eInternalResource(), instanceOf(ExtensionResource.class));
		});

	}

	@Test
	public void messageAllRedefinitions() {
		UMLRTProtocol rootProtocol = getProtocol("Protocol1");
		UMLRTProtocol subprotocol = rootProtocol.getPackage().createProtocol("NewSubProtocol");
		UMLRTProtocol subsubprotocol = rootProtocol.getPackage().createProtocol("NewSubsubProtocol");
		subprotocol.setSuperProtocol(rootProtocol);
		subsubprotocol.setSuperProtocol(subprotocol);

		UMLRTProtocolMessage rootProtocolMessage = rootProtocol.getMessage("greet");
		UMLRTProtocolMessage subProtocolMessage = subprotocol.getMessage("greet");
		UMLRTProtocolMessage subsubProtocolMessage = subsubprotocol.getMessage("greet");

		List<UMLRTProtocolMessage> expected = Arrays.asList(rootProtocolMessage, subProtocolMessage, subsubProtocolMessage);
		List<? extends UMLRTProtocolMessage> actual = rootProtocolMessage.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	//
	// Test framework
	//

	UMLRTProtocol getProtocol(String name) {
		return UMLRTPackage.getInstance(fixture.getModel()).getProtocol(name);
	}

	UMLRTProtocol createProtocol(String name) {
		return UMLRTPackage.getInstance(fixture.getModel()).createProtocol(name);
	}
}
