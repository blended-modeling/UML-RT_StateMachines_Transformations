/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import javax.inject.Named;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test suite default naming of Protocol Message.
 */
@PluginResource("resource/party.di")
public class ProtocolMessageNameTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Named("model::Greeter::Greeter")
	private Collaboration greeterProtocol;

	/**
	 * Initializes me.
	 */
	public ProtocolMessageNameTest() {
		super();
	}

	/**
	 * Verify the default name of the created Protocol Message
	 */
	@Test
	public void ProtoclMessageDefaultName() throws Exception {
		IElementType inProtocolMessage = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN;
		IElementType outProtocolMessage = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_OUT;
		IElementType inOutProtocolMessage = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_INOUT;

		// first Protocol Messages creation to verify the IN Out and InOut prefixes
		Operation inProtocolMessage1 = CreateProtoclMessage(inProtocolMessage);
		Operation outProtocolMessage1 = CreateProtoclMessage(outProtocolMessage);
		Operation inOutProtocolMessage1 = CreateProtoclMessage(inOutProtocolMessage);

		assertThat(inProtocolMessage1.getName(), is("InProtocolMessage1"));
		assertThat(outProtocolMessage1.getName(), is("OutProtocolMessage1"));
		assertThat(inOutProtocolMessage1.getName(), is("InOutProtocolMessage1"));

		// second Protocol Messages creation to verify the 1,2,.. suffixes
		Operation inProtocolMessage2 = CreateProtoclMessage(inProtocolMessage);
		Operation outProtocolMessage2 = CreateProtoclMessage(outProtocolMessage);
		Operation inOutProtocolMessage2 = CreateProtoclMessage(inOutProtocolMessage);

		assertThat(inProtocolMessage2.getName(), is("InProtocolMessage2"));
		assertThat(outProtocolMessage2.getName(), is("OutProtocolMessage2"));
		assertThat(inOutProtocolMessage2.getName(), is("InOutProtocolMessage2"));
	}

	/**
	 * Create the Protocol Message
	 */
	public Operation CreateProtoclMessage(IElementType typeToCreate) {

		ICommand command = ElementEditServiceUtils.getCreateChildCommandWithContext(greeterProtocol, (IHintedType) typeToCreate);
		assumeThat("Cannot create Protocol Message", command, isExecutable());
		modelSet.execute(command);
		Operation protocolMessage = (Operation) command.getCommandResult().getReturnValue();
		return protocolMessage;


	}

}
