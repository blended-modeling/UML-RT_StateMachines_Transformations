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
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
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
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test suite default naming of ports on capsules.
 */
@PluginResource("resource/party.di")
public class PortDefaultNameTest extends AbstractPapyrusTest {
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

	@Named("model::Greeter::Greeter")
	private Collaboration greeterProtocol;

	/**
	 * Initializes me.
	 */
	public PortDefaultNameTest() {
		super();
	}

	/**
	 * Verify the default name of the only port of its type.
	 */
	@Test
	public void singlePort() throws Exception {
		Port port = createPort(partyCapsule, greeterProtocol, UMLRTPortKind.EXTERNAL_BEHAVIOR);
		assertThat(port.getName(), is("greeter"));

		// In a different capsule
		port = createPort(guestCapsule, greeterProtocol, UMLRTPortKind.EXTERNAL_BEHAVIOR);
		assertThat(port.getName(), is("greeter"));
	}

	/**
	 * Verify the default names of multiple ports of the same type.
	 */
	@Test
	public void multiplePorts() throws Exception {
		Port port = createPort(partyCapsule, greeterProtocol, UMLRTPortKind.EXTERNAL_BEHAVIOR);
		assertThat(port.getName(), is("greeter"));

		// In the same capsule (doesn't matter the port kind)
		port = createPort(partyCapsule, greeterProtocol, UMLRTPortKind.INTERNAL_BEHAVIOR);
		assertThat(port.getName(), is("greeter2"));

		// Again
		port = createPort(partyCapsule, greeterProtocol, UMLRTPortKind.RELAY);
		assertThat(port.getName(), is("greeter3"));
	}

	//
	// Test framework
	//

	Port createPort(Class capsule, Collaboration protocol, UMLRTPortKind kind) {
		IElementType typeToCreate;
		switch (kind) {
		case EXTERNAL_BEHAVIOR:
			typeToCreate = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.EXTERNAL_BEHAVIOR_PORT_ID);
			break;
		case INTERNAL_BEHAVIOR:
			typeToCreate = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.INTERNAL_BEHAVIOR_PORT_ID);
			break;
		case RELAY:
			typeToCreate = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RELAY_PORT_ID);
			break;
		case SAP:
			typeToCreate = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID);
			break;
		case SPP:
			typeToCreate = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID);
			break;
		default:
			throw new IllegalArgumentException("Invalid port kind: " + kind);
		}

		CompositeTransactionalCommand command = new CompositeTransactionalCommand(modelSet.getEditingDomain(), "Create Port");
		command.add(ElementEditServiceUtils.getCreateChildCommandWithContext(capsule, (IHintedType) typeToCreate));

		ICommand setType = new AbstractTransactionalCommand(modelSet.getEditingDomain(), "Set Type", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				// The new port is the last one added
				Port newPort = capsule.getOwnedPorts().get(capsule.getOwnedPorts().size() - 1);

				SetRequest request = new SetRequest(getEditingDomain(), newPort, UMLPackage.Literals.TYPED_ELEMENT__TYPE, protocol);
				ICommand delegate = ElementEditServiceUtils.getCommandProvider(newPort).getEditCommand(request);
				delegate.execute(monitor, info);

				return CommandResult.newOKCommandResult(newPort);
			}
		};
		command.add(setType);
		modelSet.execute(command);

		Port result = (Port) setType.getCommandResult().getReturnValue();
		assertThat("Port creation failed", result, notNullValue());
		return result;
	}
}
