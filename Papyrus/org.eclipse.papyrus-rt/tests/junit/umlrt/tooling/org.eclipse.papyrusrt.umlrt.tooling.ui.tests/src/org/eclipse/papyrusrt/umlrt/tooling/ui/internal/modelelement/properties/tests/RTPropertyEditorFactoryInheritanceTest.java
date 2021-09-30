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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isVirtualElement;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collections;

import javax.inject.Named;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties.RTPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLFactoryImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for the {@link RTPropertyEditorFactory} operating in an inherited context.
 */
@SuppressWarnings("restriction")
@PluginResource("/resource/modelelement/model.di")
public class RTPropertyEditorFactoryInheritanceTest {
	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	private final MyTriggersPropertyFactory fixture = new MyTriggersPropertyFactory();

	private Trigger newTrigger;

	@Named("model::Capsule2::StateMachine")
	private StateMachine stateMachine;

	private Transition initialTransition;

	/**
	 * Initializes me with the reference in which to add a new newCapsule.
	 * 
	 * @param addTo
	 *            the add-to reference of the package
	 * @param name
	 *            the add-to reference name
	 */
	public RTPropertyEditorFactoryInheritanceTest() {
		super();

		newTrigger = UMLRTUMLFactoryImpl.eINSTANCE.createTrigger();
	}

	@Test
	public void addNewTrigger() {
		model.execute(createTriggerAsIfWithEditionDialog(false));

		assertThat("Transition not reified", isVirtualElement(initialTransition), is(false));
		assertThat("Trigger not added", initialTransition.getTriggers(), hasItem(newTrigger));
	}

	@Test
	public void cancelNewTrigger() {
		model.execute(createTriggerAsIfWithEditionDialog(true));

		assertThat("Transition reified", isVirtualElement(initialTransition), is(true));
		assertThat("Trigger added", initialTransition.getTriggers(), not(hasItem(newTrigger)));

		// Also, the transition is still contained properly in the extensions
		EObject container = ((InternalEObject) initialTransition).eInternalContainer();
		assertThat(container, instanceOf(ExtRegion.class));
		assertThat(getUMLRTContents(initialTransition.getContainer(), UMLPackage.Literals.REGION__TRANSITION),
				hasItem(initialTransition));
	}

	//
	// Test framework
	//

	@Before
	public void getInheritedElements() {
		initialTransition = UMLRTStateMachine.getInstance(stateMachine).getTransition("Initial").toUML();
	}

	private static class MyTriggersPropertyFactory extends RTPropertyEditorFactory<Trigger> {
		public MyTriggersPropertyFactory() {
			super(UMLPackage.Literals.TRANSITION__TRIGGER);
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

	Command createTriggerAsIfWithEditionDialog(boolean cancel) {
		CreationContext ctx = fixture.getCreationContext(initialTransition, newTrigger);

		return new RecordingCommand(model.getEditingDomain()) {

			@Override
			protected void doExecute() {
				ctx.pushCreatedElement(newTrigger);
				assertThat("New Trigger not attached", newTrigger.eResource(), notNullValue());

				// Emulate a creation dialog
				IElementEditService edit = ElementEditServiceUtils.getCommandProvider(newTrigger);
				ICommand configureCapsule = edit.getEditCommand(new ConfigureRequest(newTrigger, UMLElementTypes.TRIGGER));
				assertThat(configureCapsule, isExecutable());
				try {
					configureCapsule.execute(new NullProgressMonitor(), null);
				} catch (Exception e) {
					e.printStackTrace();
					fail("Failed to execute configure command: " + e.getMessage());
				}

				ctx.popCreatedElement(newTrigger);
				assertThat("New Trigger still attached", newTrigger.eResource(), nullValue());

				if (cancel) {
					// Don't add it
					throw new OperationCanceledException();
				} else {
					// Performs the add to the create-in, if necessary
					fixture.validateObjects(Collections.singleton(newTrigger));

					EReference addTo = fixture.getAddTo();
					if (addTo == UMLPackage.Literals.TRANSITION__TRIGGER) {
						initialTransition.getTriggers().add(newTrigger);
					} else {
						fail("Unsupported add-to reference in test configuration: " + addTo);
					}
				}
			}
		};
	}
}
