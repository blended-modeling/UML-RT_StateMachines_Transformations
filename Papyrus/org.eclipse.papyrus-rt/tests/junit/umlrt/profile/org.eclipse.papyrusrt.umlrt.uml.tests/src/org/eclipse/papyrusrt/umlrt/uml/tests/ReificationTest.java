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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.CrossReferenceCheck;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the automatic reification of implicit members on edit.
 */
@RunWith(Parameterized.class)
@Category({ CapsuleTests.class, FacadeTests.class })
public class ReificationTest {

	@Rule
	public final ModelFixture fixture;

	private EReference extension;
	private Function<Class, EList<NamedElement>> ownedListAccessor;
	private Function<UMLRTCapsule, UMLRTNamedElement> newElementCreator;

	public ReificationTest(String testModelPath, EReference extension,
			Function<Class, EList<NamedElement>> ownedListAccessor,
			Function<UMLRTCapsule, UMLRTNamedElement> newElementCreator) {

		super();

		this.fixture = new ModelFixture(testModelPath);
		this.extension = extension;
		this.ownedListAccessor = ownedListAccessor;
		this.newElementCreator = newElementCreator;
	}

	@Test
	public void autoReifyElement() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitElements = (EList<NamedElement>) capsule.eGet(extension);
		NamedElement newElement = fixture.create(extension.getEReferenceType(), "newElement");
		implicitElements.add(newElement);

		assumeThat(capsule.getOwnedMembers(), hasItem(newElement));

		newElement.setName("myElement");

		assertThat(ownedListAccessor.apply(capsule), hasItem(newElement));
		assertThat(implicitElements, not(hasItem(newElement)));

		assertThat(UMLRTExtensionUtil.isVirtualElement(newElement), is(false));
	}

	@Test
	public void reinheritElement() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");
		UMLRTNamedElement newElement = newElementCreator.apply(UMLRTCapsule.getInstance(capsule));
		NamedElement inherited = UMLRTCapsule.getInstance(subcapsule).getRedefinitionOf(newElement).toUML();

		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitElements = (EList<NamedElement>) subcapsule.eGet(extension);

		assumeThat(implicitElements, hasItem(inherited));
		assumeThat(ownedListAccessor.apply(subcapsule), not(hasItem(inherited)));
		assumeThat(UMLRTExtensionUtil.isVirtualElement(inherited), is(true));

		inherited.setName("myElement");

		assumeThat(implicitElements, not(hasItem(inherited)));
		assumeThat(ownedListAccessor.apply(subcapsule), hasItem(inherited));
		assumeThat(UMLRTExtensionUtil.isVirtualElement(inherited), is(false));

		((InternalUMLRTElement) inherited).rtReinherit();

		// The element was un-reified
		assertThat(implicitElements, hasItem(inherited));
		assertThat(ownedListAccessor.apply(subcapsule), not(hasItem(inherited)));
		assertThat(UMLRTExtensionUtil.isVirtualElement(inherited), is(true));

		// And the name was unset
		assertThat(inherited.isSetName(), is(false));
		assertThat(inherited.getName(), is(newElement.getName())); // Inherited
	}

	@Test
	public void undoRedoAutoReifyElement() {
		Class capsule = fixture.getElement("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitElements = (EList<NamedElement>) capsule.eGet(extension);
		NamedElement newElement = fixture.create(extension.getEReferenceType(), "newElement");
		implicitElements.add(newElement);

		assumeThat(capsule.getOwnedMembers(), hasItem(newElement));

		ChangeDescription change = fixture.record(() -> newElement.setName("myElement"));

		assumeThat(ownedListAccessor.apply(capsule), hasItem(newElement));
		assumeThat(UMLRTExtensionUtil.isVirtualElement(newElement), is(false));

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change);

		// The element was un-reified
		assertThat(implicitElements, hasItem(newElement));
		assertThat(ownedListAccessor.apply(capsule), not(hasItem(newElement)));
		assertThat(UMLRTExtensionUtil.isVirtualElement(newElement), is(true));

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change); // Actually a redo!

		// The element was re-reified
		assertThat(implicitElements, not(hasItem(newElement)));
		assertThat(ownedListAccessor.apply(capsule), hasItem(newElement));
		assertThat(UMLRTExtensionUtil.isVirtualElement(newElement), is(false));
	}

	@Test
	public void autoReifyElementByMultiplicityBound() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");
		UMLRTNamedElement newElement = newElementCreator.apply(UMLRTCapsule.getInstance(capsule));

		MultiplicityElement mult = getMultiplicityElement(newElement.toUML());
		mult.setLower(2);
		mult.setUpper(2);

		NamedElement inherited = UMLRTCapsule.getInstance(subcapsule).getRedefinitionOf(newElement).toUML();
		mult = getMultiplicityElement(inherited);
		mult.setUpper(7);

		// It was reified
		assertThat(ownedListAccessor.apply(subcapsule), hasItem(inherited));
		assertThat(UMLRTExtensionUtil.isVirtualElement(inherited), is(false));
	}

	@Test
	public void reificationListener() {
		UMLRTPackage package_ = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = package_.getCapsule("RootCapsule");

		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitElements = (EList<NamedElement>) capsule.toUML().eGet(extension);
		UMLRTNamedElement newElement = newElementCreator.apply(capsule);
		implicitElements.add(newElement.toUML()); // Forcibly virtualize it

		Boolean[] callback = { null };
		newElement.addReificationListener((element, reified) -> callback[0] = reified);

		ChangeDescription change = fixture.record(() -> newElement.setName("myElement"));

		assertThat("No reification event", callback[0], is(Boolean.TRUE));
		callback[0] = null;

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change);

		assertThat("No un-reification event", callback[0], is(Boolean.FALSE));
		callback[0] = null;

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change); // Actually a redo!

		assertThat("No reification event", callback[0], is(Boolean.TRUE));
	}

	@Test
	public void reificationListenerByMultiplicityBound() {
		UMLRTPackage package_ = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = package_.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = package_.getCapsule("Subcapsule");

		UMLRTNamedElement newElement = newElementCreator.apply(capsule);
		MultiplicityElement mult = getMultiplicityElement(newElement.toUML());
		mult.setLower(2);
		mult.setUpper(2);

		UMLRTNamedElement inherited = subcapsule.getRedefinitionOf(newElement);

		Boolean[] callback = { null };
		inherited.addReificationListener((element, reified) -> callback[0] = reified);

		ChangeDescription change = fixture.record(() -> {
			MultiplicityElement iMult = getMultiplicityElement(inherited.toUML());
			iMult.setUpper(7);
		});

		assertThat("No reification event", callback[0], is(Boolean.TRUE));
		callback[0] = null;

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change);

		assertThat("No un-reification event", callback[0], is(Boolean.FALSE));
		callback[0] = null;

		// Must be careful not to reify the element by mistake
		change = fixture.undo(change); // Actually a redo!

		assertThat("No reification event", callback[0], is(Boolean.TRUE));
	}

	@Test
	@CrossReferenceCheck(false)
	public void deletedElementIsNotReified() {
		UMLRTPackage package_ = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = package_.getCapsule("RootCapsule");
		UMLRTNamedElement newElement = newElementCreator.apply(capsule);

		@SuppressWarnings("unchecked")
		EList<NamedElement> implicitElements = (EList<NamedElement>) capsule.toUML().eGet(extension);
		implicitElements.add(newElement.toUML()); // Forcibly virtualize it

		Boolean[] callback = { null };
		newElement.addReificationListener((element, reified) -> callback[0] = reified);

		// Don't do newPort.destroy() because that does much else besides detaching the element.
		// This leaves a dangling stereotype that would fail the cross-reference check
		EcoreUtil.remove(newElement.toUML());

		assertThat("Got reification event", callback[0], nullValue());
	}

	//
	// Test framework
	//

	@Parameters(name = "{index}: {0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				// Test cases for port inheritance
				{ "inheritance/ports.uml", ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
						(Function<Class, EList<Port>>) Class::getOwnedPorts,
						(Function<UMLRTCapsule, UMLRTNamedElement>) capsule -> capsule.createPort(capsule.getPackage().createProtocol("NewProtocol")) },
				// Test cases for capsule-part inheritance
				{ "inheritance/parts.uml", ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE,
						(Function<Class, EList<Property>>) Class::getOwnedAttributes,
						(Function<UMLRTCapsule, UMLRTNamedElement>) capsule -> capsule.createCapsulePart(capsule.getPackage().createCapsule("NewCapsule")) },
				// Test cases for capsule-part inheritance
				{ "inheritance/connectors.uml", ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR,
						(Function<Class, EList<Connector>>) Class::getOwnedConnectors,
						(Function<UMLRTCapsule, UMLRTNamedElement>) capsule -> capsule.createConnector("NewConnector", capsule.getPorts().get(0), null, capsule.getCapsuleParts().get(0).getType().getPorts().get(0), capsule.getCapsuleParts().get(0)) },
		});
	}

	MultiplicityElement getMultiplicityElement(NamedElement element) {
		MultiplicityElement result = null;

		if (element instanceof MultiplicityElement) {
			result = (MultiplicityElement) element;
		} else if (element instanceof Connector) {
			result = ((Connector) element).getEnds().get(0);
		}

		assertThat("No multiplicity element", result, notNullValue());
		return result;
	}
}
