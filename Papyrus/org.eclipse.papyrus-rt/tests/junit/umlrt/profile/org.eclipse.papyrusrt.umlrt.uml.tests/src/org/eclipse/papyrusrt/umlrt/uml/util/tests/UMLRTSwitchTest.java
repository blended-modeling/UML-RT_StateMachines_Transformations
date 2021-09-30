/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.util.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test suite for the {@link UMLRTSwitch} class.
 */
@TestModel("inheritance/connectors.uml")
public class UMLRTSwitchTest {

	@ClassRule
	public static final ModelFixture model = new ModelFixture();

	public UMLRTSwitchTest() {
		super();
	}

	@Test
	public void caseCapsule() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseCapsule(UMLRTCapsule object) {
				return "ok";
			}

			@Override
			public String caseClassifier(UMLRTClassifier object) {
				fail("Should not call caseClassifier");
				return super.caseClassifier(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(capsule), is("ok"));
	}

	@Test
	public void casePort() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTPort port = capsule.getPort("protocol1");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String casePort(UMLRTPort object) {
				return "ok";
			}

			@Override
			public String caseReplicatedElement(UMLRTReplicatedElement object) {
				fail("Should not call caseReplicatedElement");
				return super.caseReplicatedElement(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(port), is("ok"));
	}

	@Test
	public void caseCapsulePart() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseCapsulePart(UMLRTCapsulePart object) {
				return "ok";
			}

			@Override
			public String caseReplicatedElement(UMLRTReplicatedElement object) {
				fail("Should not call caseReplicatedElement");
				return super.caseReplicatedElement(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(part), is("ok"));
	}

	@Test
	public void caseConnector() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTConnector connector = capsule.getConnector("connector1");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseConnector(UMLRTConnector object) {
				return "ok";
			}

			@Override
			public String caseNamedElement(UMLRTNamedElement object) {
				fail("Should not call caseNamedElement");
				return super.caseNamedElement(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(connector), is("ok"));
	}

	@Test
	public void caseProtocol() {
		UMLRTProtocol protocol = model.getRoot().getProtocol("Protocol1");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseProtocol(UMLRTProtocol object) {
				return "ok";
			}

			@Override
			public String caseClassifier(UMLRTClassifier object) {
				fail("Should not call caseClassifier");
				return super.caseClassifier(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(protocol), is("ok"));
	}

	@Test
	public void caseProtocolMessage() {
		UMLRTProtocol protocol = model.getRoot().getProtocol("Protocol1");
		UMLRTProtocolMessage msg = protocol.getMessage("greet");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseProtocolMessage(UMLRTProtocolMessage object) {
				return "ok";
			}

			@Override
			public String caseNamedElement(UMLRTNamedElement object) {
				fail("Should not call caseNamedElement");
				return super.caseNamedElement(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(msg), is("ok"));
	}

	@Test
	public void casePackage() {
		UMLRTPackage nested = model.getRoot().getNestedPackage("nested");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String casePackage(UMLRTPackage object) {
				return "ok";
			}

			@Override
			public String caseNamedElement(UMLRTNamedElement object) {
				fail("Should not call caseNamedElement");
				return super.caseNamedElement(object);
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(nested), is("ok"));
	}

	@Test
	public void caseModel() {
		UMLRTModel model = UMLRTSwitchTest.model.getRoot().getModel();

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseModel(UMLRTModel object) {
				return "ok";
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(model), is("ok"));
	}

	@Test
	public void caseClassifier() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTProtocol protocol = model.getRoot().getProtocol("Protocol1");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseClassifier(UMLRTClassifier object) {
				return "ok";
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(capsule), is("ok"));
		assertThat(fixture.doSwitch(protocol), is("ok"));
	}

	@Test
	public void caseReplicatedElement() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTPort port = capsule.getPort("protocol1");
		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseReplicatedElement(UMLRTReplicatedElement object) {
				return "ok";
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(port), is("ok"));
		assertThat(fixture.doSwitch(part), is("ok"));
	}

	@Test
	public void caseNamedElement() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");
		UMLRTPort port = capsule.getPort("protocol1");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String caseNamedElement(UMLRTNamedElement object) {
				return "ok";
			}

			@Override
			public String defaultCase(Object object) {
				fail("Should not call defaultCase");
				return super.defaultCase(object);
			}
		};

		assertThat(fixture.doSwitch(capsule), is("ok"));
		assertThat(fixture.doSwitch(port), is("ok"));
	}

	@Test
	public void defaultCase() {
		Object garbage = new Object();

		UMLRTSwitch<String> fixture = new UMLRTSwitch<String>() {
			@Override
			public String defaultCase(Object object) {
				return "ok";
			}
		};

		assertThat(fixture.doSwitch(garbage), is("ok"));

		assertThat(fixture.doSwitch((Object) null), is("ok")); // As in EMF
	}

	@Test
	public void emptySwitch() {
		UMLRTCapsule capsule = model.getRoot().getCapsule("RootCapsule");

		UMLRTSwitch<String> fixture = new UMLRTSwitch<>();

		assertThat(fixture.doSwitch(capsule), nullValue());
	}
}
