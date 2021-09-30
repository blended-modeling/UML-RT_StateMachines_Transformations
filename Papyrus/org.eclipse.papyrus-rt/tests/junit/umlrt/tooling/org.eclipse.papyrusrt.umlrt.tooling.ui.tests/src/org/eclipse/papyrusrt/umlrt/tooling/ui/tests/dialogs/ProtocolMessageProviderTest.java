/*****************************************************************************
 * Copyright (c) 2017 CEA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ansgar Radermacher - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.dialogs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.ProtocolMsgContentProvider;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test suite for the protocol message provider used in the
 * combined port and message dialog.
 */
@SuppressWarnings("nls")
@PluginResource("resource/dialogs/TriggerCreationTest.di")
public class ProtocolMessageProviderTest {

	/**
	 * expected protocol-message names
	 */
	protected static final String BASE_RESPONSE = "baseResponse";
	protected static final String BASE_REQUEST = "baseRequest";
	protected static final String SUB_RESPONSE = "subResponse";
	protected static final String SUB_REQUEST = "subRequest";
	protected static final String SUB_SUB_RESPONSE = "subSubResponse";
	protected static final String SUB_SUB_REQUEST = "subSubRequest";

	@Rule
	public final TestRule dataBindings = new DataBindingsRule();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	// init @Named fields
	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	protected TransactionalEditingDomain domain;

	@Named("RootElement::Capsule1")
	protected Class capsule1;

	@Named("RootElement::Capsule1::base")
	protected Port base;

	@Named("RootElement::Capsule1::sub")
	protected Port sub;

	@Named("RootElement::Capsule1::subSub")
	protected Port subSub;

	protected Transition t1;

	protected Trigger trigger;

	protected ProtocolMsgContentProvider provider;

	public ProtocolMessageProviderTest() {
		super();
	}

	/**
	 * Configure trigger and ports.
	 * 
	 * @param ports
	 * @param conjugated
	 */
	protected void configureTriggerAndPorts(final List<Port> ports, boolean conjugated) {
		AbstractTransactionalCommand cmd = new AbstractTransactionalCommand(domain, "configure port", Collections.EMPTY_LIST) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				trigger = t1.createTrigger("testTrigger");
				trigger.getPorts().addAll(ports);
				ports.forEach(port -> port.setIsConjugated(conjugated));
				return CommandResult.newOKCommandResult();
			}
		};
		IOperationHistory history = OperationHistoryFactory.getOperationHistory();
		try {
			history.execute(cmd, new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			fail("Can't execute command: " + e.getMessage());
		}
		provider = new ProtocolMsgContentProvider(trigger);
	}

	/**
	 * Test for events in case of base port.
	 */
	@Test
	public void checkEventsForBase() {
		configureTriggerAndPorts(Arrays.asList(base), false);

		Object[] rootEvents = getProvidedElements(base);
		assertThat("Single any event as root", rootEvents.length == 1);
		assertThat("Root element must be an any-event", rootEvents[0] instanceof AnyReceiveEvent);

		Object[] children = getProvidedChildren(null, base);
		// the base protocol is not loaded during the test
		// => rtBound and rtUnbound are not part of the returned events
		assertThat(String.format("Should contain 1 in-request (have %d)", children.length), children.length == 1);

		Arrays.asList(children).forEach(child -> assertThat("All childs must be call events", child instanceof CallEvent));
		assertThat("Check name of third event", ((CallEvent) children[0]).getOperation().getName().equals(BASE_RESPONSE));
	}

	/**
	 * Test for events in case of subSub port.
	 */
	@Test
	public void checkEventsForSubSub() {
		configureTriggerAndPorts(Arrays.asList(subSub), false);

		Object[] children = getProvidedChildren(null, subSub);
		assertThat(String.format("Should contain three in-requests (have %d)", children.length), children.length == 3);
		Arrays.asList(children).forEach(child -> assertThat("All childs must be events", child instanceof CallEvent));

		// check names (and order) of events
		String msgName = ((CallEvent) children[0]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, BASE_RESPONSE), msgName.equals(BASE_RESPONSE));
		msgName = ((CallEvent) children[1]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, SUB_RESPONSE), msgName.equals(SUB_RESPONSE));
		msgName = ((CallEvent) children[2]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, SUB_SUB_RESPONSE), msgName.equals(SUB_SUB_RESPONSE));
	}

	/**
	 * Test for events in case of selecting two ports (sub and subSub).
	 */
	@Test
	public void checkEventsForMultiSel() {
		configureTriggerAndPorts(Arrays.asList(sub, subSub), false);

		Object[] children = getProvidedChildren(null, subSub);
		assertThat(String.format("Should contain two in-requests (have %d)", children.length), children.length == 2);
		Arrays.asList(children).forEach(child -> assertThat("All childs must be call events", child instanceof CallEvent));

		// check names (and order) of events
		// should contain the base and sub events, since these are common for the two ports
		String msgName = ((CallEvent) children[0]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, BASE_RESPONSE), msgName.equals(BASE_RESPONSE));
		msgName = ((CallEvent) children[1]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, SUB_RESPONSE), msgName.equals(SUB_RESPONSE));
	}

	/**
	 * Test for events in case of subSub port when conjugated.
	 */
	@Test
	public void checkEventsForSubSubConj() {
		configureTriggerAndPorts(Arrays.asList(subSub), true);

		Object[] children = getProvidedChildren(null, subSub);
		assertThat(String.format("Should contain three out-requests (have %d)", children.length), children.length == 3);
		Arrays.asList(children).forEach(child -> assertThat("All childs must be call events", child instanceof CallEvent));

		// check names (and also order of events)
		String msgName = ((CallEvent) children[0]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, BASE_REQUEST), msgName.equals(BASE_REQUEST));
		msgName = ((CallEvent) children[1]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, SUB_REQUEST), msgName.equals(SUB_REQUEST));
		msgName = ((CallEvent) children[2]).getOperation().getName();
		assertThat(String.format("1st event is %s, should be %s", msgName, SUB_SUB_REQUEST), msgName.equals(SUB_SUB_REQUEST));
	}

	@Before
	public void getModelReferences() {

		Behavior behavior = capsule1.getClassifierBehavior();
		assertThat("Capsule must have a state machine", behavior instanceof StateMachine);
		StateMachine sm = (StateMachine) behavior;

		assertThat("SM must have at least one region", sm.getRegions().size() >= 1);
		Region mainRegion = sm.getRegions().get(0);

		// choose first transition for trigger tests
		assertThat("SM must have at least one transition", mainRegion.getTransitions().size() >= 1);
		t1 = mainRegion.getTransitions().get(0);

		domain = TransactionUtil.getEditingDomain(t1);
	}

	//
	// Test framework
	//

	/**
	 * Gets the children provided by our provider under test with messages of the
	 * base protocol (if any) in the context of the given {@code port} excluded.
	 * 
	 * @param parent
	 *            the parent for which to get children
	 * @param port
	 *            the context in which to resolve a base protocol
	 * 
	 * @return the children provided by the provider under test, without any base protocol messages
	 */
	Object[] getProvidedChildren(Object parent, Port port) {
		// In case the test environment includes and loads the base protocol,
		// filter out its contributions
		return filterBaseEvents(provider.getChildren(parent), port);
	}

	/**
	 * Gets the top elements provided by our provider under test with messages of the
	 * base protocol (if any) in the context of the given {@code port} excluded.
	 * 
	 * @param port
	 *            the context in which to resolve a base protocol
	 * 
	 * @return the elements provided by the provider under test, without any base protocol messages
	 */
	Object[] getProvidedElements(Port port) {
		// In case the test environment includes and loads the base protocol,
		// filter out its contributions
		return filterBaseEvents(provider.getElements(), port);
	}

	private Object[] filterBaseEvents(Object[] messages, Element context) {
		Object[] result = messages;

		Collaboration base = ProtocolUtils.getBaseProtocol(context);
		if (base != null) {
			Set<CallEvent> baseEvents = UMLRTProtocol.getInstance(base)
					.getMessages().stream()
					.map(UMLRTProtocolMessage::toReceiveEvent)
					.collect(Collectors.toSet());
			Predicate<Object> isBase = baseEvents::contains;
			result = Stream.of(result)
					.filter(isBase.negate())
					.toArray(Object[]::new);
		}

		return result;
	}
}
