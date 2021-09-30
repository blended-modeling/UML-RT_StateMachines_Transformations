/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.rules.TestRule;

/**
 * Abstract class for transition creation tests
 */
public abstract class AbstractCreationTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	protected static final int TOLERANCE = 6;

	/**
	 * @param target
	 * @param transition
	 * @return
	 */
	protected Pseudostate findEntryPoint(State owner, Transition transition) {
		return owner.getOwnedElements().stream()
				.filter(Pseudostate.class::isInstance)
				.map(Pseudostate.class::cast)
				.filter(ps -> ps.getKind().equals(PseudostateKind.ENTRY_POINT_LITERAL))
				.filter(ps -> ps.getIncomings().contains(transition)).findAny().orElse(null);
	}

	/**
	 * 
	 */
	protected Pseudostate findExitPoint(State owner, Transition outgoingTransition) {
		return owner.getOwnedElements().stream()
				.filter(Pseudostate.class::isInstance)
				.map(Pseudostate.class::cast)
				.filter(ps -> ps.getKind().equals(PseudostateKind.EXIT_POINT_LITERAL))
				.filter(ps -> ps.getOutgoings().contains(outgoingTransition)).findAny().orElse(null);
	}


	/**
	 * @param sourceEditPart
	 * @param targetEditPart
	 * @param elementTypes
	 */
	@SuppressWarnings("unchecked")
	protected void runCreationNodeTest(NodeCreationParameters parameters, Supplier<? extends ValidationRule> validationSupplier) throws Exception {
		ValidationRule rule = validationSupplier.get();

		EStructuralFeature containement = PackageUtil.findFeature(parameters.getTargetElement().eClass(), parameters.typeToCreate.getEClass());
		assertThat(containement, notNullValue());
		List<Element> values = new ArrayList<>(TypeUtils.as(parameters.getTargetElement().eGet(containement), List.class));

		org.eclipse.gef.commands.Command gefCommand = getNodeCreationCommand(parameters.targetEditPart, parameters.getTargetElement(), parameters.typeToCreate, parameters.targetLocation);
		assertThat(gefCommand, isExecutable());

		// do
		getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(gefCommand));
		waitForUIEvents();

		assertThat(editor.getEditor().isDirty(), is(true));
		List<Element> afterDo = getDiffs((List<Element>) parameters.getTargetElement().eGet(containement), values);
		assertThat(afterDo.size(), equalTo(1));
		Element newElement = afterDo.get(0);
		rule.validatePostEdition(newElement);

		// undo
		assertThat(getCommandStack().canUndo(), is(true));
		getCommandStack().undo();
		waitForUIEvents();
		assertThat(editor.getEditor().isDirty(), is(false));
		// check transition has been deleted
		List<Element> afterUndo = getDiffs((List<Element>) parameters.getTargetElement().eGet(containement), values);
		assertThat(afterUndo.size(), equalTo(0));

		// redo
		assertThat(getCommandStack().canRedo(), is(true));
		getCommandStack().redo();
		waitForUIEvents();
		assertThat(editor.getEditor().isDirty(), is(true));
		List<Element> afterRedo = getDiffs((List<Element>) parameters.getTargetElement().eGet(containement), values);
		assertThat(afterRedo.size(), equalTo(1));
		newElement = afterRedo.get(0);
		// check source, target, kind, owner again
		rule.validatePostEdition(newElement);
	}

	protected void runNotCreationConnexionTest(ConnectionCreationParameters parameters, List<IHintedType> elementTypes) throws Exception {
		Command command = getConnectionCreationCommand(parameters.sourceEditPart, parameters.targetEditPart, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE));
		assertThat(command, not(isExecutable()));
	}

	/**
	 * @param sourceEditPart
	 * @param targetEditPart
	 * @param elementTypes
	 */
	protected void runCreationConnexionTest(ConnectionCreationParameters parameters, List<IHintedType> elementTypes, Supplier<? extends ValidationRule> validationSupplier) throws Exception {
		ValidationRule rule = validationSupplier.get();
		Region region = (Region) parameters.container;
		List<Transition> existing = new ArrayList<>(region.getTransitions());
		List<Pseudostate> initialSource = getConnectionPseudostates(parameters.sourceEditPart.resolveSemanticElement());
		List<Pseudostate> initialTarget = getConnectionPseudostates(parameters.targetEditPart.resolveSemanticElement());


		Command command = getConnectionCreationCommand(parameters.sourceEditPart, parameters.targetEditPart, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), parameters.sourceLocation, parameters.targetLocation);
		assertThat(command, isExecutable());

		// do
		getCommandStack().execute(command);
		waitForUIEvents();
		assertThat(editor.getEditor().isDirty(), is(true));
		List<Transition> afterDo = getDiffs(region.getTransitions(), existing);
		assertThat(afterDo.size(), equalTo(1));
		Transition newTransition = afterDo.get(0);
		rule.validatePostEdition(newTransition);

		// undo
		assertThat(getCommandStack().canUndo(), is(true));
		getCommandStack().undo();
		waitForUIEvents();
		assertThat(editor.getEditor().isDirty(), is(false));
		// check transition has been deleted
		assertThat(getDiffs(region.getTransitions(), existing), isEmpty());
		assertThat(getConnectionPseudostates(parameters.sourceEditPart.resolveSemanticElement()), equalTo(initialSource));
		assertThat(getConnectionPseudostates(parameters.targetEditPart.resolveSemanticElement()), equalTo(initialTarget));

		// redo
		assertThat(getCommandStack().canRedo(), is(true));
		getCommandStack().redo();
		waitForUIEvents();
		assertThat(editor.getEditor().isDirty(), is(true));
		List<Transition> afterRedo = getDiffs(region.getTransitions(), existing);
		assertThat(afterRedo.size(), equalTo(1));
		newTransition = afterRedo.get(0);
		// check source, target, kind, owner again
		rule.validatePostEdition(newTransition);
	}

	/**
	 * @param semantic
	 * @return
	 */
	protected List<Pseudostate> getConnectionPseudostates(EObject semantic) {
		List<Pseudostate> list;
		if (semantic instanceof State) {
			list = new ArrayList<>(getUMLRTContents(semantic, UMLPackage.Literals.STATE__CONNECTION_POINT));
		} else if (semantic instanceof Pseudostate) {
			list = new ArrayList<>(getUMLRTContents(((Pseudostate) semantic).getState(), UMLPackage.Literals.STATE__CONNECTION_POINT));
		} else {
			list = Collections.emptyList();
		}
		return list;
	}

	/**
	 * @param simple12
	 * @param simple22
	 * @param transitionEdge
	 * @return
	 */
	protected Command getConnectionCreationCommand(IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart, List<IElementType> types) {
		CreateConnectionRequest connectionRequest = (CreateConnectionRequest) createTargetRequest(types);

		connectionRequest.setTargetEditPart(sourceEditPart);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		connectionRequest.setLocation(new Point(0, 0));

		// only if the connection is supported will we get a non null
		// command from the sourceEditPart
		org.eclipse.gef.commands.Command sourceCommand = sourceEditPart.getCommand(connectionRequest);
		assertThat(sourceCommand, isExecutable());

		connectionRequest.setSourceEditPart(sourceEditPart);
		connectionRequest.setTargetEditPart(targetEditPart);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(new Point(0, 0));

		org.eclipse.gef.commands.Command gefCommand = targetEditPart.getCommand(connectionRequest);
		if (gefCommand != null) {
			return GEFtoEMFCommandWrapper.wrap(gefCommand);
		}
		return null;
	}

	/**
	 * @param simple12
	 * @param simple22
	 * @param transitionEdge
	 * @return
	 */
	protected Command getConnectionCreationCommand(IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart, List<IElementType> types, Point startLocation, Point targetLocation) {
		CreateConnectionRequest connectionRequest = (CreateConnectionRequest) createTargetRequest(types);

		connectionRequest.setTargetEditPart(sourceEditPart);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		connectionRequest.setLocation(new Point(startLocation));

		// only if the connection is supported will we get a non null
		// command from the sourceEditPart
		org.eclipse.gef.commands.Command sourceCommand = sourceEditPart.getCommand(connectionRequest);
		assertThat(sourceCommand, isExecutable());

		connectionRequest.setSourceEditPart(sourceEditPart);
		connectionRequest.setTargetEditPart(targetEditPart);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(new Point(targetLocation));

		org.eclipse.gef.commands.Command gefCommand = targetEditPart.getCommand(connectionRequest);
		if (gefCommand != null) {
			return GEFtoEMFCommandWrapper.wrap(gefCommand);
		}
		return null;
	}

	protected Request createTargetRequest(List<IElementType> types) {
		return new CreateUnspecifiedTypeConnectionRequest(types,
				false, editor.getPreferencesHint());
	}

	/**
	 * @param <T>
	 * @param capsule1_stateMachine_region2
	 * @param existing
	 * @return
	 */
	protected static <T> List<T> getDiffs(List<T> current, List<T> initial) {
		return current.stream().filter(t -> !initial.contains(t)).collect(Collectors.toList());
	}

	/**
	 * @return
	 */
	protected CommandStack getCommandStack() {
		return editor.getEditingDomain().getCommandStack();
	}

	protected IUndoableOperation getUndoPosition() {
		IWorkspaceCommandStack stack = (IWorkspaceCommandStack) editor.getEditingDomain().getCommandStack();
		IOperationHistory history = stack.getOperationHistory();
		IUndoContext ctx = stack.getDefaultUndoContext();
		return history.getUndoOperation(ctx);
	}

	protected static Point getBottomLocation(Rectangle frame) {
		return frame.getBottom().getTranslated(0, -6);
	}

	protected static Point getRightLocation(Rectangle frame) {
		return frame.getRight().getTranslated(-2, 0);
	}

	protected static Point getLeftLocation(Rectangle frame) {
		return frame.getLeft().getTranslated(2, 0);
	}

	protected Point getTopLeftLocation(Rectangle frame) {
		return frame.getTopLeft().getTranslated(2, 2);
	}

	protected Point getTopRightLocation(Rectangle frame) {
		return frame.getTopRight().getTranslated(-2, 2);
	}

	protected Point getBottomLeftLocation(Rectangle frame) {
		return frame.getBottomLeft().getTranslated(2, -2);
	}

	protected Point getBottomRightLocation(Rectangle frame) {
		return frame.getBottomRight().getTranslated(-2, -2);
	}

	protected static Point getTopLocation(Rectangle frame) {
		return frame.getTop().getTranslated(0, 2);
	}

}
