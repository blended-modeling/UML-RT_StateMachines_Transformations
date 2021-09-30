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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Basic test cases for the UML extension feature containing implicit members.
 */
@NoFacade
@Category(CapsuleTests.class)
public class BasicImplicitMembersTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicImplicitMembersTest() {
		super();
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void addImplicitPort() {
		Class capsule = fixture.getElement("RootCapsule");

		// Capture notifications of new ports added
		List<Notifier> notifiers = new ArrayList<>();
		List<EStructuralFeature> features = new ArrayList<>();
		capsule.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				switch (msg.getEventType()) {
				case Notification.ADD:
					if (msg.getNewValue() instanceof Port) {
						notifiers.add((Notifier) msg.getNotifier());
						features.add((EStructuralFeature) msg.getFeature());
					}
					break;
				}
			}
		});

		@SuppressWarnings("unchecked")
		EList<Port> implicitPorts = (EList<Port>) capsule.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		Port newPort = fixture.create(UMLPackage.Literals.PORT, "newPort");
		implicitPorts.add(newPort);

		assertThat(capsule.getOwnedPorts(), not(hasItem(newPort)));
		assertThat(capsule.getOwnedMembers(), hasItem(newPort));
		assertThat(capsule.getOwnedElements(), hasItem(newPort));
		assertThat(newPort.getNamespace(), is(capsule));
		assertThat(newPort.getOwner(), is(capsule));

		assertThat(capsule.eContents(), hasItem(newPort));
		assertThat(newPort.eContainer(), is(capsule));
		assertThat(((InternalEObject) newPort).eInternalContainer(), not(capsule));

		// Because implicitPort is a derived subset of this
		assertThat(newPort.eContainmentFeature(), is(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE));

		// Subset of implicit members
		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitMembers = (EList<NamedElement>) capsule.eGet(ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
		assertThat(implicitMembers, hasItem(newPort));

		// And we got the expected notifications
		assertThat(notifiers.size(), is(2));
		assertThat(notifiers, everyItem(is(capsule)));
		assertThat(features.get(0), is(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE));
		assertThat(features.get(1), is(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT));

		// Finally, the implicit element is in a resource but not the model resource
		assertThat(((InternalEObject) newPort).eInternalResource(), notNullValue());
		assertThat(((InternalEObject) newPort).eInternalResource(), not(fixture.getResource()));
	}

	/**
	 * Verify that {@link Collection#contains(Object)} is {@code true} for elements
	 * contained in extensions.
	 */
	@Test
	@TestModel("inheritance/ports.uml")
	public void implicitContainmentListContains() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Port> implicitPorts = (EList<Port>) capsule.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		Port newPort = fixture.create(UMLPackage.Literals.PORT, "newPort");
		implicitPorts.add(newPort);

		assertThat("Wrong contains(Object) for < 4 elements",
				((InternalUMLRTElement) capsule).rtExtension(ExtClass.class).getImplicitAttributes().contains(newPort), is(true));
		assertThat("Wrong contains(Object) for < 4 elements",
				capsule.getOwnedMembers().contains(newPort), is(true));

		// The contains(Object) method is implemented completely differently for lists
		// of more than four (!) elements
		IntStream.range(1, 5).forEach(i -> implicitPorts.add(fixture.create(UMLPackage.Literals.PORT, "newPort" + i)));

		assertThat("Wrong contains(Object) for > 4 elements",
				((InternalUMLRTElement) capsule).rtExtension(ExtClass.class).getImplicitAttributes().contains(newPort), is(true));
		assertThat("Wrong contains(Object) for > 4 elements",
				capsule.getOwnedMembers().contains(newPort), is(true));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void removeImplicitPort() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Port> implicitPorts = (EList<Port>) capsule.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		Port newPort = fixture.create(UMLPackage.Literals.PORT, "newPort");
		implicitPorts.add(newPort);

		assumeThat(capsule.getOwnedMembers(), hasItem(newPort));

		newPort.destroy();

		assertThat(capsule.getOwnedPorts(), not(hasItem(newPort)));
		assertThat(capsule.eContents(), not(hasItem(newPort)));
		assertThat(implicitPorts, not(hasItem(newPort)));
		assertThat(newPort.eContainer(), nullValue());
		assertThat(newPort.eContainmentFeature(), nullValue());
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void reifyImplicitPort() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Port> implicitPorts = (EList<Port>) capsule.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		Port newPort = fixture.create(UMLPackage.Literals.PORT, "newPort");
		implicitPorts.add(newPort);

		assumeThat(capsule.getOwnedMembers(), hasItem(newPort));

		capsule.getOwnedPorts().add(newPort);

		assertThat(capsule.getOwnedPorts(), hasItem(newPort));
		assertThat(capsule.eContents(), hasItem(newPort));
		assertThat(implicitPorts, not(hasItem(newPort)));
		assertThat(newPort.eContainer(), is(capsule));
		assertThat(newPort.eContainmentFeature(), is(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE));
		assertThat(((InternalEObject) newPort).eInternalContainer(), is(capsule));
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void addImplicitCapsulePart() {
		Class capsule = fixture.getElement("RootCapsule");

		// Capture notifications of new parts added
		List<Notifier> notifiers = new ArrayList<>();
		List<EStructuralFeature> features = new ArrayList<>();
		capsule.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				switch (msg.getEventType()) {
				case Notification.ADD:
					if (msg.getNewValue() instanceof Property) {
						notifiers.add((Notifier) msg.getNotifier());
						features.add((EStructuralFeature) msg.getFeature());
					}
					break;
				}
			}
		});

		@SuppressWarnings("unchecked")
		EList<Property> implicitParts = (EList<Property>) capsule.eGet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		Property newPart = fixture.create(UMLPackage.Literals.PROPERTY, "newPart");
		implicitParts.add(newPart);

		assertThat(capsule.getOwnedAttributes(), not(hasItem(newPart)));
		assertThat(capsule.getOwnedMembers(), hasItem(newPart));
		assertThat(capsule.getOwnedElements(), hasItem(newPart));
		assertThat(newPart.getNamespace(), is(capsule));
		assertThat(newPart.getOwner(), is(capsule));

		assertThat(capsule.eContents(), hasItem(newPart));
		assertThat(newPart.eContainer(), is(capsule));
		assertThat(((InternalEObject) newPart).eInternalContainer(), not(capsule));
		assertThat(newPart.eContainmentFeature(), is(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE));

		// Subset of implicit members
		@SuppressWarnings("unchecked")
		EList<RedefinableElement> implicitMembers = (EList<RedefinableElement>) capsule.eGet(ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
		assertThat(implicitMembers, hasItem(newPart));

		// And we got the expected notifications
		assertThat(notifiers.size(), is(1));
		assertThat(notifiers, everyItem(is(capsule)));
		assertThat(features.get(0), is(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE));

		// Finally, the implicit element is in a resource but not the model resource
		assertThat(((InternalEObject) newPart).eInternalResource(), notNullValue());
		assertThat(((InternalEObject) newPart).eInternalResource(), not(fixture.getResource()));
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void removeImplicitCapsulePart() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Property> implicitParts = (EList<Property>) capsule.eGet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		Property newPart = fixture.create(UMLPackage.Literals.PROPERTY, "newPart");
		implicitParts.add(newPart);

		assumeThat(capsule.getOwnedMembers(), hasItem(newPart));

		newPart.destroy();

		assertThat(capsule.getOwnedAttributes(), not(hasItem(newPart)));
		assertThat(capsule.eContents(), not(hasItem(newPart)));
		assertThat(implicitParts, not(hasItem(newPart)));
		assertThat(newPart.eContainer(), nullValue());
		assertThat(newPart.eContainmentFeature(), nullValue());
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void reifyImplicitCapsulePart() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Property> implicitParts = (EList<Property>) capsule.eGet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		Property newPart = fixture.create(UMLPackage.Literals.PROPERTY, "newPart");
		implicitParts.add(newPart);

		assumeThat(capsule.getOwnedMembers(), hasItem(newPart));

		capsule.getOwnedAttributes().add(newPart);

		assertThat(capsule.getOwnedAttributes(), hasItem(newPart));
		assertThat(capsule.eContents(), hasItem(newPart));
		assertThat(implicitParts, not(hasItem(newPart)));
		assertThat(newPart.eContainer(), is(capsule));
		assertThat(newPart.eContainmentFeature(), is(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE));
		assertThat(((InternalEObject) newPart).eInternalContainer(), is(capsule));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void capsuleClassSanity() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<Port> implicitPorts = (EList<Port>) capsule.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		Port newPort = fixture.create(UMLPackage.Literals.PORT, "newPort");
		implicitPorts.add(newPort);

		List<PackageableElement> imported = null;
		try {
			// Inherited operation
			Port port = capsule.getOwnedPort("newPort", null);
			assertThat(port, is(newPort));

			imported = capsule.getImportedMembers();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Computing imported members failed: " + e.getMessage());
		}

		assertThat(imported, not(hasItem(anything())));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	@NoFacade(false)
	public void protocolMessageInterfaceSanity() {
		UMLRTProtocol protocol2 = UMLRTProtocol.getInstance(fixture.getElement("Protocol2::Protocol2", Collaboration.class));
		UMLRTProtocol protocol1 = protocol2.getPackage().getProtocol("Protocol1");
		protocol2.setSuperProtocol(protocol1);

		Interface interface_ = fixture.getElement("Protocol2::Protocol2", Interface.class);

		List<PackageableElement> imported = null;
		try {
			// Inherited operation
			Operation greet = interface_.getOwnedOperation("greet", null, null);
			assertThat(greet, notNullValue());

			imported = interface_.getImportedMembers();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Computing imported members failed: " + e.getMessage());
		}

		assertThat(imported, not(hasItem(anything())));
	}

}
