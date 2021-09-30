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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Interface;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for working with messages in conjugate {@link UMLRTProtocol}s.
 */
@TestModel("inheritance/ports.uml")
@RunWith(Parameterized.class)
@Category({ ProtocolTests.class, FacadeTests.class })
public class ProtocolMessageConjugationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	private final RTMessageKind kind;
	private final RTMessageKind conjKind;

	public ProtocolMessageConjugationTest(RTMessageKind kind) {
		super();

		this.kind = kind;
		this.conjKind = (kind == RTMessageKind.IN)
				? RTMessageKind.OUT
				: (kind == RTMessageKind.OUT)
						? RTMessageKind.IN
						: kind;
	}

	@Test
	public void conjugation() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		assumeThat(protocol1.isConjugate(), is(false));

		UMLRTProtocol protocol1_ = protocol1.getConjugate();
		assumeThat(protocol1_.isConjugate(), is(true));

		UMLRTProtocolMessage msg = protocol1.createMessage(kind, "newmsg");
		UMLRTProtocolMessage msg_ = protocol1_.getMessage("newmsg");
		assertThat(msg_.getKind(), is(conjKind));
		assertThat("Message conjugate not cached", protocol1_.getMessage("newmsg"), sameInstance(msg_));

		msg_.setKind(kind);
		assertThat(msg.getKind(), is(conjKind));
		String expectedInterface = interfaceName("Protocol1", conjKind);
		assertThat("Message not moved in UML", msg.toUML().getInterface(), named(expectedInterface));
	}

	@Test
	public void getMessagesByKind() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		assumeThat(protocol1.isConjugate(), is(false));

		UMLRTProtocol protocol1_ = protocol1.getConjugate();
		assumeThat(protocol1_.isConjugate(), is(true));

		// Create a message using UML API for test independence
		Interface interface_ = fixture.getElement("Protocol1::" + interfaceName("Protocol1", conjKind), Interface.class);
		interface_.createOwnedOperation("newmsg", null, null);

		// Get messages from the conjugate
		List<UMLRTProtocolMessage> messages = protocol1_.getMessages(kind);
		assertThat(messages, hasItem(anything()));
		UMLRTProtocolMessage msg = messages.stream()
				.filter(m -> m.getName().equals("newmsg"))
				.findFirst().get(); // Asserts that it is there
		assertThat(msg.getKind(), is(kind));
	}

	@Test
	public void getMessageByKindAndName() {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		assumeThat(protocol1.isConjugate(), is(false));

		UMLRTProtocol protocol1_ = protocol1.getConjugate();
		assumeThat(protocol1_.isConjugate(), is(true));

		// Create a message using UML API for test independence
		Interface interface_ = fixture.getElement("Protocol1::" + interfaceName("Protocol1", conjKind), Interface.class);
		interface_.createOwnedOperation("newmsg", null, null);

		// Get message from the conjugate
		UMLRTProtocolMessage message = protocol1_.getMessage(kind, "newmsg");
		assertThat(message, notNullValue());
		assertThat(message.getKind(), is(kind));
	}

	@Test
	public void createMessage() {
		assertNewConjugatedMessage(protocol -> protocol.createMessage(kind, "foo"));
	}

	void assertNewConjugatedMessage(Function<UMLRTProtocol, UMLRTProtocolMessage> factory) {
		UMLRTProtocol protocol1 = getProtocol("Protocol1");
		assumeThat(protocol1.isConjugate(), is(false));

		UMLRTProtocol protocol1_ = protocol1.getConjugate();
		assumeThat(protocol1_.isConjugate(), is(true));

		UMLRTProtocolMessage newMessage = factory.apply(protocol1_);
		assertThat(newMessage.getKind(), is(kind));

		String expectedInterface = interfaceName("Protocol1", conjKind);
		assertThat(newMessage.toUML().getInterface(), named(expectedInterface));
	}

	@Test
	public void conjugationCreateMessageWithData() {
		assertNewConjugatedMessage(protocol -> protocol.createMessage(kind, "foo", fixture.getElement("YesNo")));
	}

	@Test
	public void conjugationCreateMessageWithParameters() {
		assertNewConjugatedMessage(protocol -> protocol.createMessage(kind, "foo",
				Arrays.asList("yesno"), Arrays.asList(fixture.getElement("YesNo"))));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ RTMessageKind.IN },
				{ RTMessageKind.OUT },
				{ RTMessageKind.IN_OUT },
		});
	}

	String interfaceName(String protocolName, RTMessageKind kind) {
		String result;

		switch (conjKind) {
		case IN:
			// It's actually an IN message in the model
			result = protocolName;
			break;
		case OUT:
			// It's actually an OUT message in the model
			result = protocolName + "~";
			break;
		case IN_OUT:
			// Idempotent under conjugation
			result = protocolName + "IO";
			break;
		default:
			fail("Invalid message kind: " + kind);
			throw new Error(); // Unreachable
		}

		return result;
	}

	UMLRTProtocol getProtocol(String name) {
		return UMLRTPackage.getInstance(fixture.getModel()).getProtocol(name);
	}
}
