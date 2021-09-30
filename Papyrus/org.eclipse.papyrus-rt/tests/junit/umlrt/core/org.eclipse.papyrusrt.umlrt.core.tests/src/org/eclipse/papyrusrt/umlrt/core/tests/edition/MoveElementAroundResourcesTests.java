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

package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.common.collect.Lists;

/**
 * Tests covering element move scenarios.
 */
@PluginResource("resource/subunits/submodels.di")
public class MoveElementAroundResourcesTests {

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final ModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	@Named("top::sub1")
	private Package subunit;

	@Named("top::Capsule1")
	private Class capsule1;

	@Named("top::Capsule1::greeter")
	private Port capsule1_greeter;

	@Named("top::Capsule2")
	private Class capsule2;

	@Named("top::Capsule2::StateMachine")
	private StateMachine capsule2SM;

	@Named("top::Greeter")
	private Package greeterContainer;

	@Named("top::Greeter::Greeter")
	private Collaboration greeter;

	@Named("top::Greeter::Greeter")
	private Interface greeterIn;

	@Named("top::Greeter::Greeter~")
	private Interface greeterOut;

	@Named("top::Greeter::GreeterIO")
	private Interface greeterIO;

	@Named("top::Protocol2::Protocol2")
	private Collaboration protocol2;

	/**
	 * Initializes me.
	 */
	public MoveElementAroundResourcesTests() {
		super();
	}

	/**
	 * Basic scenario, verifying that stereotype applications of moved objects
	 * are moved along with them.
	 */
	@Test
	public void stereotypeApplicationsMove() {
		// Verify objects contained by the moved object, also
		assumeStereotypeApplications(capsule1, capsule1_greeter);

		move(capsule1, subunit);
		assertThat(capsule1.eResource(), is(subunit.eResource()));

		// Verify objects contained by the moved object, also
		assertStereotypeApplications(capsule1, capsule1_greeter);
	}

	/**
	 * Verifies that externalized stereotype applications of an element that
	 * is moved are not moved along with it.
	 */
	@Test
	public void externalizedStereotypeApplications() {
		List<EObject> stereos = new ArrayList<>();
		stereos.addAll(capsule1.getStereotypeApplications());
		stereos.addAll(capsule1_greeter.getStereotypeApplications());

		// Pseudo-externalize the stereotype applications
		Resource externalizer = model.getResourceSet().createResource(
				model.getModelResourceURI().trimFileExtension().appendFileExtension(".ext.uml"));
		model.execute(new AddCommand(model.getEditingDomain(), externalizer.getContents(), stereos));

		move(capsule1, subunit);
		assertThat(capsule1.eResource(), is(subunit.eResource()));

		// The stereotype applications are not moved because they were externalized
		stereos.stream().forEach(a -> assertThat(a.eResource(), is(externalizer)));
	}

	/**
	 * Verifies that only the stereotype applications of the proper content tree
	 * that was moved are moved along with it.
	 */
	@Test
	public void stereotypeApplicationsOfProperContents() {
		// Verify objects contained by the moved object, also
		assumeStereotypeApplications(capsule2, capsule2SM);

		move(capsule2, subunit);
		assertThat(capsule2.eResource(), is(subunit.eResource()));

		// the State Machine is still its own sub-unit
		assumeThat(capsule2SM.eResource(), not(subunit.eResource()));

		// Verify objects contained by the moved object, also (which aren't
		// moved and, therefore, neither are their stereotypes
		assertStereotypeApplications(capsule2, capsule2SM);
	}

	/**
	 * Verifies that moving a protocol brings along all expected stereotype applications.
	 */
	@Test
	public void stereotypeApplicationsOfProtocol() {
		// Verify objects contained by the moved object, also
		assumeStereotypeApplications(greeterContainer, greeter,
				greeterIn, greeterOut, greeterIO);

		move(greeter, subunit);

		// What is actually moved is the protocol container and everything in it
		assertThat(greeterContainer.eResource(), is(subunit.eResource()));

		// Verify objects contained by the moved object, also (which aren't
		// moved and, therefore, neither are their stereotypes
		assertStereotypeApplications(greeterContainer, greeter,
				greeterIn, greeterOut, greeterIO);
	}

	/**
	 * Verifies the reordering of protocols in a package as is directed by the
	 * Model Explorer's drop assistant, which uses set requests to set a new
	 * list of packaged elements.
	 * 
	 * @see <a href="http://eclip.se/497742">bug 497742</a>
	 */
	@Test
	public void reorderProtocols() {
		// The Model Explorer doesn't understand the logical structure, so this
		// is the kind of SetRequest that it creates when trying to insert a
		// protocol ahead of another in the logically containing package
		List<PackageableElement> reordering = new ArrayList<>(greeterContainer.getPackagedElements());
		reordering.add(0, protocol2); // Insertion as done by the Model Explorer
		SetRequest request = new SetRequest(greeterContainer, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, reordering);

		ICommand command = ElementEditServiceUtils.getCommandProvider(greeterContainer).getEditCommand(request);
		assertThat("No executable set command", command, isExecutable());

		model.execute(command);

		// The protocol still looks okay
		assertThat(protocol2.eContainer(), not(greeterContainer));
		assertThat(protocol2.eContainer(), instanceOf(Package.class));
		assertThat(((Package) protocol2.eContainer()).getName(), is(protocol2.getName()));
		assertThat(ProtocolUtils.getRTMessages(protocol2, RTMessageKind.OUT, false).size(), is(1));
	}

	/**
	 * Verifies the reordering of an heterogeneous selection of objects,
	 * including a protocol, in a package as is directed by the
	 * Model Explorer's drop assistant, which uses multiple distinct set
	 * requests to set a new list of packaged elements (one request per
	 * group of elements of the same metaclass).
	 * 
	 * @see <a href="http://eclip.se/497742">bug 497742</a>
	 */
	@Test
	public void reorderHeterogeneousSelectionIncludingProtocol() {
		// The Model Explorer doesn't understand the logical structure, so this
		// is the kind of SetRequest that it creates when trying to insert objects
		// ahead of a protocol in the logically containing package

		// Simulate drag and drop of Capsule2 and Protocol2, which Model Explorer
		// processes in separate SetRequests, ahead of the Greeter protocol
		List<PackageableElement> reordering1 = new ArrayList<>(greeterContainer.getPackagedElements());
		reordering1.add(0, capsule2); // That is, ahead of Greeter in its container
		SetRequest request1 = new SetRequest(greeterContainer, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, reordering1);
		ICommand command1 = ElementEditServiceUtils.getCommandProvider(greeterContainer).getEditCommand(request1);
		assertThat("No executable set command for capsule", command1, isExecutable());

		List<PackageableElement> reordering2 = new ArrayList<>(greeterContainer.getPackagedElements());
		reordering2.add(0, protocol2); // That is, ahead of Greeter in its container
		SetRequest request2 = new SetRequest(greeterContainer, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, reordering2);
		ICommand command2 = ElementEditServiceUtils.getCommandProvider(greeterContainer).getEditCommand(request2);
		assertThat("No executable set command for protocol", command2, isExecutable());

		ICommand command = new CompositeTransactionalCommand(model.getEditingDomain(), "Drop Element(s)",
				Arrays.asList(command1, command2));

		model.execute(command);

		// The protocol still looks okay
		assertThat(protocol2.eContainer(), not(greeterContainer));
		assertThat(protocol2.eContainer(), instanceOf(Package.class));
		assertThat(((Package) protocol2.eContainer()).getName(), is(protocol2.getName()));
		assertThat(ProtocolUtils.getRTMessages(protocol2, RTMessageKind.OUT, false).size(), is(1));

		// The dropped elements are in the correct order
		List<PackageableElement> modelContents = model.getModel().getPackagedElements();
		Package protocol2Container = ProtocolUtils.getProtocolContainer(protocol2);
		assertThat(modelContents, hasItem(protocol2Container));
		assertThat(modelContents.indexOf(protocol2Container), lessThan(modelContents.indexOf(greeterContainer)));
		assertThat(modelContents.indexOf(capsule2), lessThan(modelContents.indexOf(protocol2Container)));
	}

	/**
	 * Verifies the reordering of a protocol relative to a non-protocol in a package
	 * as is directed by the Model Explorer's drop assistant, which uses set requests
	 * to set a new list of packaged elements. This is different to {@link #reorderProtocols()}
	 * in that the protocol is not being dropped next to another protocol (from which the
	 * Model Explorer infers the latter's protocol-container as the drop target) but rather
	 * it is being dropped next to some other kind of element, which does not have the protocol
	 * structure, and so presents a more normal scenario except that it is still a protocol
	 * being dragged but it is its container package that needs to be dropped.
	 * 
	 * @see <a href="http://eclip.se/497742">bug 497742</a>
	 */
	@Test
	public void reorderProtocolRelativeToNonProtocol() {
		// The Model Explorer doesn't understand the logical structure, so this
		// is the kind of SetRequest that it creates when trying to insert a
		// protocol ahead of some element in some package
		List<PackageableElement> reordering = new ArrayList<>(model.getModel().getPackagedElements());
		reordering.add(0, protocol2); // Insertion ahead of the 'sub1' package
		SetRequest request = new SetRequest(model.getModel(), UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, reordering);

		ICommand command = ElementEditServiceUtils.getCommandProvider(model.getModel()).getEditCommand(request);
		assertThat("No executable set command", command, isExecutable());

		model.execute(command);

		// The protocol still looks okay
		assertThat(protocol2.eContainer(), not(model.getModel()));
		assertThat(protocol2.eContainer(), instanceOf(Package.class));
		assertThat(((Package) protocol2.eContainer()).getName(), is(protocol2.getName()));
		assertThat(ProtocolUtils.getRTMessages(protocol2, RTMessageKind.OUT, false).size(), is(1));

		// And the protocol-container was dropped in the expected place
		assertThat(model.getModel().getPackagedElements().indexOf(protocol2.getOwner()), is(0));
	}

	//
	// Test framework
	//

	/**
	 * Verify that the stereotype applications extending an {@code element}
	 * are persisted in the same resource as it.
	 * 
	 * @param element
	 *            an element in the model
	 * @param more
	 *            additional elements to check
	 */
	void assertStereotypeApplications(Element element, Element... more) {
		Lists.asList(element, more).stream().forEach(e -> {
			List<EObject> stereos = e.getStereotypeApplications();
			assertThat(stereos, hasItem(anything())); // Not a useful test, otherwise

			Resource resource = e.eResource();
			stereos.stream().forEach(a -> assertThat(a.eResource(), is(resource)));
		});
	}

	/**
	 * Verify as an initial condition that the stereotype applications extending
	 * an {@code element} are persisted in the same resource as it.
	 * 
	 * @param element
	 *            an element in the model
	 * @param more
	 *            additional elements to check
	 */
	void assumeStereotypeApplications(Element element, Element... more) {
		Lists.asList(element, more).stream().forEach(e -> {
			List<EObject> stereos = e.getStereotypeApplications();
			assumeThat(stereos, hasItem(anything())); // Not a useful test, otherwise

			Resource resource = e.eResource();
			stereos.stream().forEach(a -> assertThat(a.eResource(), is(resource)));
		});
	}

	/**
	 * Moves an {@code object} from its current container in a new container,
	 * using the GMF edit-helpers.
	 * 
	 * @param object
	 *            an object to move
	 * @param newContainer
	 *            its new container
	 */
	void move(EObject object, EObject newContainer) {
		EReference containment = object.eContainmentFeature();
		MoveRequest req = new MoveRequest(newContainer, containment, object);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(newContainer);
		assumeThat(edit, notNullValue());
		model.execute(edit.getEditCommand(req));
	}
}
