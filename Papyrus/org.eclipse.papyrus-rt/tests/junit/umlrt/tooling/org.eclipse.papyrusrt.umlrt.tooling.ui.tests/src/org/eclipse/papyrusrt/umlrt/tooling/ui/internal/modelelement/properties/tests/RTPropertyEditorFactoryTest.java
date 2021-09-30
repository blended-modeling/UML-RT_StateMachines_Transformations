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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.RTPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the {@link RTPropertyEditorFactory} class.
 */
@PluginResource("/resource/modelelement/model.di")
@RunWith(Parameterized.class)
public class RTPropertyEditorFactoryTest {
	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	private final MyCapsulesPropertyFactory fixture;

	private org.eclipse.uml2.uml.Class newCapsule;

	@Named("model::Capsule")
	private org.eclipse.uml2.uml.Class capsule;

	/**
	 * Initializes me with the reference in which to add a new newCapsule.
	 * 
	 * @param addTo
	 *            the add-to reference of the package
	 * @param name
	 *            the add-to reference name
	 */
	@SuppressWarnings("restriction")
	public RTPropertyEditorFactoryTest(EReference addTo, String name) {
		super();

		newCapsule = UMLRTUMLFactoryImpl.eINSTANCE.createClass();
		newCapsule.setName("Foo");

		fixture = new MyCapsulesPropertyFactory(addTo);
	}

	@Test
	public void addNewCapsule() {

		model.execute(createCapsuleAsIfWithEditionDialog(false));

		// The stereotype applications are still present
		assertThat(CapsuleUtils.isCapsule(newCapsule), is(true));

		// And persisted
		List<EObject> stereotypeApplications = newCapsule.getStereotypeApplications();
		stereotypeApplications.forEach(a -> assertThat("Stereotype application not attached", a.eResource(), is(model.getModelResource())));
	}

	@Test
	public void cancelNewCapsule() {
		model.execute(createCapsuleAsIfWithEditionDialog(true));

		// The stereotype applications are not still present
		List<EObject> stereotypeApplications = newCapsule.getStereotypeApplications();
		stereotypeApplications.forEach(a -> assertThat("Stereotype application still attached", a.eResource(), nullValue()));
	}

	//
	// Test framework
	//

	/**
	 * Configure the tests to run once on creation scenario in an ordinary
	 * containment reference and once in a non-containment reference (so
	 * that we simulate the user picking an alternate container).
	 * 
	 * @return
	 */
	@Parameters(name = "{index}: in {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, "Package::packagedElement" },
				{ UMLPackage.Literals.TYPED_ELEMENT__TYPE, "Property::type" },
		});
	}

	private static class MyCapsulesPropertyFactory extends RTPropertyEditorFactory<org.eclipse.uml2.uml.Class> {
		public MyCapsulesPropertyFactory(EReference addTo) {
			super(addTo);
		}

		EReference getAddTo() {
			return referenceIn;
		}

		protected CreationContext getCreationContext(EObject container, EObject newElement) {
			// Do we need an explicit create-in for our add-to reference?
			if (!isContainment(referenceIn)) {
				CreateIn explicit = new CreateIn() {
					// Pass
				};

				explicit.createInObject = container;
				explicit.createInReference = UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT;
				createIn.put(newElement, explicit);
			}

			return super.getCreationContext(container);
		}
	}

	Command createCapsuleAsIfWithEditionDialog(boolean cancel) {
		org.eclipse.uml2.uml.Package package_ = model.getModel();
		CreationContext ctx = fixture.getCreationContext(package_, newCapsule);

		return new RecordingCommand(model.getEditingDomain()) {

			@Override
			protected void doExecute() {
				ctx.pushCreatedElement(newCapsule);
				assertThat("New Capsule not attached", newCapsule.eResource(), notNullValue());

				// Emulate a creation dialog
				IElementEditService edit = ElementEditServiceUtils.getCommandProvider(newCapsule);
				ICommand configureCapsule = edit.getEditCommand(new ConfigureRequest(newCapsule, UMLRTElementTypesEnumerator.CAPSULE));
				assertThat(configureCapsule, isExecutable());
				try {
					configureCapsule.execute(new NullProgressMonitor(), null);
				} catch (Exception e) {
					e.printStackTrace();
					fail("Failed to execute configure command: " + e.getMessage());
				}

				ctx.popCreatedElement(newCapsule);
				assertThat("New Capsule still attached", newCapsule.eResource(), nullValue());

				if (cancel) {
					// Don't add it
					throw new OperationCanceledException();
				} else {
					// Performs the add to the create-in, if necessary
					fixture.validateObjects(Collections.singleton(newCapsule));

					EReference addTo = fixture.getAddTo();
					if (addTo == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
						package_.getPackagedElements().add(newCapsule);
					} else if (addTo == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
						capsule.createOwnedAttribute("foo", newCapsule);
					} else {
						fail("Unsupported add-to reference in test configuration: " + addTo);
					}
				}
			}
		};
	}
}
