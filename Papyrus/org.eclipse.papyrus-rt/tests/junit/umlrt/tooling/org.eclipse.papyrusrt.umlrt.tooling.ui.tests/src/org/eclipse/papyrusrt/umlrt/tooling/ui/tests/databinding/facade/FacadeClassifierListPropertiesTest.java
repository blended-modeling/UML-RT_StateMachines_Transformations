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

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.databinding.facade;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.IFilteredObservableList;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.CapsuleProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.ProtocolProperties;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.base.Strings;

/**
 * Test suite for the observable list properties provided by the
 * {@link CapsuleProperties} and {@link ProtocolProperties} factories.
 */
@PluginResource("resource/modelelement/model.di")
@RunWith(Parameterized.class)
public class FacadeClassifierListPropertiesTest {
	@Rule
	public final TestRule dataBindings = new DataBindingsRule();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	private final PropertyKind propertyKind;

	@CleanUp
	private UMLRTClassifier subtype;
	private IObservableList<UMLRTNamedElement> elements;

	public FacadeClassifierListPropertiesTest(PropertyKind propertyKind) {
		super();

		this.propertyKind = propertyKind;
	}

	/**
	 * Basic test for getting elements from the list.
	 */
	@Test
	public void getElements() {
		assertThat("No elements", elements.isEmpty(), is(false));

		elements.forEach(e -> assertThat("Wrong kind of element", e, instanceOf(propertyKind.getElementType())));
		elements.forEach(e -> assertThat("Non-inherited element", e.isVirtualRedefinition(), is(true)));
	}

	@Test
	public void excludeElement() {
		assumeThat("No elements", elements.isEmpty(), is(false));

		UMLRTNamedElement element = any(elements);

		elements.remove(element);

		// Not actually removed, but excluded
		assertThat("Element actually removed", elements, hasItem(element));
		assertThat("Element not excluded", element.isExcluded(), is(true));
	}

	@Test
	public void addElement() {
		// Create an element in a scratch capsule, to be added to our test capsule from there
		UMLRTNamedElement newElement = createScratchElement("newThing");

		elements.add(newElement);

		assertThat("Element not added", elements, hasItem(newElement));

		// It was moved in the model
		UMLRTClassifier owner = getContextClassifier(newElement);
		assertThat(owner, is(subtype));
		assertThat("New element is a redefinition?!", newElement.isInherited(), is(false));
	}

	@Test
	public void destroyElement() {
		// Create an element in a scratch capsule, to be added to our test capsule from there
		UMLRTNamedElement newElement = createScratchElement("newThing");

		elements.add(newElement);

		assumeThat("Element not added", elements, hasItem(newElement));

		elements.remove(newElement);

		assertThat("Element not detached", newElement.toUML().eResource(), nullValue());
		assertThat("Element not destroyed", newElement.toUML().eContainer(), nullValue());
	}

	@Test
	public void filterList() {
		assertThat("Not a filtered list", elements, instanceOf(IFilteredObservableList.class));

		@SuppressWarnings("unchecked")
		IFilteredObservableList<UMLRTNamedElement> filtered = (IFilteredObservableList<UMLRTNamedElement>) elements;

		assumeThat("No elements", elements.isEmpty(), is(false));

		UMLRTNamedElement element = any(elements);

		Predicate<UMLRTNamedElement> isExcluded = UMLRTNamedElement::isExcluded;
		Predicate<UMLRTNamedElement> notExcluded = isExcluded.negate();

		filtered.addFilter(notExcluded);

		assertThat("Filter suppressed inherited element", elements, hasItem(element));

		modelSet.execute(ExclusionCommand.getExclusionCommand(element.toUML(), true));
		assertThat("Filter did not suppress excluded element", elements, not(hasItem(element)));

		filtered.removeFilter(notExcluded);

		assertThat("List still filtered", elements, hasItem(element));
	}

	@Test
	public void observeAddition() {
		ListObservation obs = ListObservation.createAdditionsOnly();

		elements.addListChangeListener(obs);
		UMLRTNamedElement newElement;

		try {
			// Let it be inherited, for extra fun!
			newElement = modelSet.execute(() -> propertyKind.createNewElement(subtype, "newThing"));
		} finally {
			elements.removeListChangeListener(obs);
		}

		assertThat("Addition not notified", obs.getAdded(), hasItem(newElement));
	}

	@Test
	public void observeRemoval() {
		UMLRTNamedElement newElement = modelSet.execute(() -> propertyKind.createNewElement(subtype, "newThing"));

		ListObservation obs = ListObservation.createRemovalsOnly();

		elements.addListChangeListener(obs);

		try {
			modelSet.execute(newElement::destroy);
		} finally {
			elements.removeListChangeListener(obs);
		}

		assertThat("Removal not notified", obs.getRemoved(), hasItem(newElement));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Stream.of(PropertyKind.values())
				.map(v -> new Object[] { v })
				.collect(collectingAndThen(toList(), Collections::unmodifiableList));
	}

	@Before
	public void configureResourceSet() {
		// We need the injected stereotype notifications to trigger updates to observed lists
		StereotypeElementListener listener = new StereotypeElementListener(modelSet.getEditingDomain());
		modelSet.getEditingDomain().addResourceSetListener(listener);
	}

	@Before
	public void createSubtypeAndObservableList() {
		UMLRTPackage root = UMLRTPackage.getInstance(modelSet.getModel());

		subtype = modelSet.execute(() -> propertyKind.createSubtypeFixture(root));

		elements = housekeeper.cleanUpLater(propertyKind.getListProperty().observe(subtype));
	}

	UMLRTNamedElement createScratchElement(String name) {
		UMLRTPackage root = UMLRTPackage.getInstance(modelSet.getModel());

		return modelSet.execute(() -> propertyKind.createScratchElement(root, name));
	}

	UMLRTClassifier getContextClassifier(UMLRTNamedElement element) {
		return (element instanceof UMLRTReplicatedElement)
				? ((UMLRTReplicatedElement) element).getCapsule()
				: (element instanceof UMLRTConnector)
						? ((UMLRTConnector) element).getCapsule()
						: (element instanceof UMLRTProtocolMessage)
								? ((UMLRTProtocolMessage) element).getProtocol()
								: (element instanceof UMLRTClassifier)
										? (UMLRTClassifier) element
										: null;
	}

	static String initialUpper(String s) {
		String result = s;

		if (!Strings.isNullOrEmpty(s)) {
			StringBuilder buf = new StringBuilder(s);
			buf.setCharAt(0, Character.toUpperCase(buf.charAt(0)));
			result = buf.toString();
		}

		return result;
	}

	static <T> T any(Collection<T> collection) {
		List<T> list = new ArrayList<>(collection);
		// Randomize the order
		Collections.shuffle(list);
		return list.get(0);
	}

	static UMLRTPort anyPort(UMLRTCapsule capsule) {
		return any(capsule.getPorts());
	}

	enum PropertyKind {
		PORTS(UMLRTPort.class, CapsuleProperties::ports), //
		CAPSULE_PARTS(UMLRTCapsulePart.class, CapsuleProperties::capsuleParts), //
		CONNECTORS(UMLRTConnector.class, CapsuleProperties::connectors), //
		IN_MESSAGES(UMLRTProtocolMessage.class, () -> ProtocolProperties.messages(RTMessageKind.IN)), //
		OUT_MESSAGES(UMLRTProtocolMessage.class, () -> ProtocolProperties.messages(RTMessageKind.OUT)), //
		INOUT_MESSAGES(UMLRTProtocolMessage.class, () -> ProtocolProperties.messages(RTMessageKind.IN_OUT)); //

		private final Class<? extends UMLRTNamedElement> elementType;
		private final Supplier<IListProperty<? extends UMLRTClassifier, ? extends UMLRTNamedElement>> listPropertySupplier;

		PropertyKind(Class<? extends UMLRTNamedElement> elementType,
				Supplier<IListProperty<? extends UMLRTClassifier, ? extends UMLRTNamedElement>> listPropertySupplier) {

			this.elementType = elementType;
			this.listPropertySupplier = listPropertySupplier;
		}

		public Class<? extends UMLRTNamedElement> getElementType() {
			return elementType;
		}

		@SuppressWarnings("unchecked")
		public IListProperty<UMLRTClassifier, UMLRTNamedElement> getListProperty() {
			// We will be careful in the test only to try to add elements of a compatible kind
			return (IListProperty<UMLRTClassifier, UMLRTNamedElement>) listPropertySupplier.get();
		}

		public UMLRTNamedElement createNewElement(UMLRTClassifier classifier, String name) {
			switch (this) {
			case PORTS:
				UMLRTProtocol newProtocol = classifier.getPackage().createProtocol(initialUpper(name));
				return ((UMLRTCapsule) classifier).createPort(newProtocol);
			case CAPSULE_PARTS:
				UMLRTCapsule newCapsule = classifier.getPackage().createCapsule(initialUpper(name));
				return ((UMLRTCapsule) classifier).createCapsulePart(newCapsule);
			case CONNECTORS:
				// Look for ports in a capsule that is known to have some. Yes, this means that
				// the resulting connector may connect ports of a different capsule, but that
				// doesn't matter for our tests here. Nor does it matter that it may connect
				// the same port to itself, or some other invalid combination
				UMLRTCapsule portOwner = classifier.getPackage().getCapsule("SubcapsuleForTest");
				return ((UMLRTCapsule) classifier).createConnector(name, anyPort(portOwner), null, anyPort(portOwner), null);
			case IN_MESSAGES:
				return ((UMLRTProtocol) classifier).createMessage(RTMessageKind.IN, name);
			case OUT_MESSAGES:
				return ((UMLRTProtocol) classifier).createMessage(RTMessageKind.OUT, name);
			case INOUT_MESSAGES:
				return ((UMLRTProtocol) classifier).createMessage(RTMessageKind.IN_OUT, name);
			default:
				throw new AssertionError("Invalid test property kind: " + this);
			}
		}

		public UMLRTClassifier createSubtypeFixture(UMLRTPackage package_) {
			UMLRTClassifier result;

			switch (this) {
			case PORTS:
			case CAPSULE_PARTS:
			case CONNECTORS:
				UMLRTCapsule capsule = package_.createCapsule("SubcapsuleForTest");
				capsule.setSuperclass(package_.getCapsule("ConnectedCapsule"));
				result = capsule;
				break;
			case IN_MESSAGES:
			case OUT_MESSAGES:
			case INOUT_MESSAGES:
				UMLRTProtocol protocol = package_.createProtocol("SubprotocolForTest");
				protocol.setSuperProtocol(package_.getProtocol("Protocol1"));
				result = protocol;
				break;
			default:
				throw new AssertionError("Invalid test property kind: " + this);
			}

			return result;
		}

		public UMLRTNamedElement createScratchElement(UMLRTPackage package_, String name) {
			UMLRTNamedElement result;

			switch (this) {
			case PORTS:
			case CAPSULE_PARTS:
			case CONNECTORS:
				UMLRTCapsule capsule = package_.createCapsule("ScratchPad");
				result = createNewElement(capsule, name);
				break;
			case IN_MESSAGES:
			case OUT_MESSAGES:
			case INOUT_MESSAGES:
				UMLRTProtocol protocol = package_.createProtocol("ScratchPad");
				result = createNewElement(protocol, name);
				break;
			default:
				throw new AssertionError("Invalid test property kind: " + this);
			}

			return result;
		}

		@Override
		public String toString() {
			switch (this) {
			case PORTS:
				return "port";
			case CAPSULE_PARTS:
				return "capsule-part";
			case CONNECTORS:
				return "connector";
			case IN_MESSAGES:
				return "in msg";
			case OUT_MESSAGES:
				return "out msg";
			case INOUT_MESSAGES:
				return "inout msg";
			default:
				return super.toString();
			}
		}
	}
}
