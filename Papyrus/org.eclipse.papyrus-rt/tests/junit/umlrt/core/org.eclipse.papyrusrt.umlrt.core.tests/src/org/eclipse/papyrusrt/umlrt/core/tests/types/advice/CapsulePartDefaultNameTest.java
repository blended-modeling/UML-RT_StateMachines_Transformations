/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test suite for default naming of capsule-parts in capsules.
 */
@PluginResource("resource/party.di")
public class CapsulePartDefaultNameTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();
	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Named("model::Party")
	private Class partyCapsule;

	@Named("model::Guest")
	private Class guestCapsule;

	@Named("model::Meeting")
	private Class meetingCapsule;

	/**
	 * Initializes me.
	 */
	public CapsulePartDefaultNameTest() {
		super();
	}

	/**
	 * Verify the default name of the only capsule-part of its type.
	 */
	@Test
	public void singleCapsulePart() throws Exception {
		Property part = createCapsulePart(partyCapsule, guestCapsule);
		assertThat(part.getName(), is("guest"));

		// In a different capsule
		part = createCapsulePart(meetingCapsule, guestCapsule);
		assertThat(part.getName(), is("guest"));
	}

	/**
	 * Verify the default names of multiple capsule-parts of the same type.
	 */
	@Test
	public void multipleCapsuleParts() throws Exception {
		Property part = createCapsulePart(partyCapsule, guestCapsule);
		assertThat(part.getName(), is("guest"));

		// In the same capsule
		part = createCapsulePart(partyCapsule, guestCapsule);
		assertThat(part.getName(), is("guest2"));

		// Again
		part = createCapsulePart(partyCapsule, guestCapsule);
		assertThat(part.getName(), is("guest3"));
	}

	//
	// Test framework
	//

	Property createCapsulePart(Class capsule, Class partType) {
		IElementType typeToCreate = UMLRTElementTypesEnumerator.CAPSULE_PART;

		CompositeTransactionalCommand command = new CompositeTransactionalCommand(modelSet.getEditingDomain(), "Create Capsule Part");
		command.add(ElementEditServiceUtils.getCreateChildCommandWithContext(capsule, (IHintedType) typeToCreate));

		ICommand setType = new AbstractTransactionalCommand(modelSet.getEditingDomain(), "Set Type", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				// The new part is the last one added
				Property newPart = capsule.getOwnedAttributes().get(capsule.getOwnedAttributes().size() - 1);

				SetRequest request = new SetRequest(getEditingDomain(), newPart, UMLPackage.Literals.TYPED_ELEMENT__TYPE, partType);
				ICommand delegate = ElementEditServiceUtils.getCommandProvider(newPart).getEditCommand(request);
				delegate.execute(monitor, info);

				return CommandResult.newOKCommandResult(newPart);
			}
		};
		command.add(setType);
		modelSet.execute(command);

		Property result = (Property) setType.getCommandResult().getReturnValue();
		assertThat("Capsule Part creation failed", result, notNullValue());
		return result;
	}
}
