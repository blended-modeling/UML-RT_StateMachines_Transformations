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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A fixture for assistance with the editing of capsuleparts.
 */
public class CapsulePartEditingFixture {
	private final PapyrusEditorFixture editor;

	private String capsuleName;
	private String partName;

	/**
	 * Initializes me with the editor fixture on which I operate.
	 */
	public CapsulePartEditingFixture(PapyrusEditorFixture editor) {
		this(editor, null);
	}

	/**
	 * Initializes me with the editor fixture on which I operate and the
	 * capsule on which to create ports, by default.
	 */
	public CapsulePartEditingFixture(PapyrusEditorFixture editor, String defaultCapsuleName) {
		this(editor, defaultCapsuleName, null);
	}

	/**
	 * Initializes me with the editor fixture on which I operate and the
	 * part in a capsule on which to create ports, by default.
	 */
	public CapsulePartEditingFixture(PapyrusEditorFixture editor, String defaultCapsuleName, String defaultPartName) {
		super();

		this.editor = editor;
		this.capsuleName = defaultCapsuleName;
		this.partName = defaultPartName;
	}

	public Property createCapsulePartWithTool(Point mouse, String elementTypeID) {
		return (partName == null)
				? createCapsulePartWithTool(mouse, elementTypeID, capsuleName)
				: createCapsulePartWithTool(mouse, elementTypeID, capsuleName, partName);
	}

	public Property createCapsulePartWithTool(Point mouse, String elementTypeID, String capsuleName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		Command command = getCreateCapsulePartCommand(capsule, mouse, elementTypeID);
		assertThat("No executable command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No capsule part created", capsule.getOwnedAttributes(), hasItem(anything()));
		return capsule.getOwnedAttributes().get(capsule.getOwnedAttributes().size() - 1);
	}

	/**
	 * Obtains a command that creates a port of the specified type at the given {@code mouse}
	 * location in the {@code capsule} structure diagram.
	 * 
	 * @param capsule
	 *            a capsule
	 * @param mouse
	 *            the mouse location in the diagram (in the diagram's overall coordinate system)
	 * @param elementTypeID
	 *            the type of port to create
	 * 
	 * @return the command, or {@code null} if none was provided by the diagram
	 */
	private Command getCreateCapsulePartCommand(EncapsulatedClassifier capsule, Point mouse, String elementTypeID) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);

		IHintedType typeToCreate = (IHintedType) ElementTypeRegistry.getInstance().getType(elementTypeID);
		CreateElementRequest createElement = new CreateElementRequest(capsule, typeToCreate);
		CreateElementRequestAdapter createAdapter = new CreateElementRequestAdapter(createElement);
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(
				new CreateViewAndElementRequest.ViewAndElementDescriptor(
						createAdapter, Node.class, typeToCreate.getSemanticHint(), editor.getPreferencesHint()));
		request.setType(RequestConstants.REQ_CREATE);
		request.setLocation(mouse);
		EditPart target = getTargetEditPart(underMouse, request);

		return target.getCommand(request);
	}

	public void assertCannotCreateCapsulePartWithTool(Point mouse, String elementTypeID) {
		if (partName == null) {
			assertCannotCreateCapsulePartWithTool(mouse, elementTypeID, capsuleName);
		} else {
			assertCannotCreateCapsulePartWithTool(mouse, elementTypeID, capsuleName, partName);
		}
	}

	public void assertCannotCreateCapsulePartWithTool(Point mouse, String elementTypeID, String capsuleName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		Command command = getCreateCapsulePartCommand(capsule, mouse, elementTypeID);
		assertThat("An executable command was provided", command, not(isExecutable()));
	}

	public void assertCannotCreateCapsulePartWithTool(Point mouse, String elementTypeID, String capsuleName, String partName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		Command command = getCreateCapsulePartCommand(part, mouse, elementTypeID);
		assertThat("An executable command was provided", command, not(isExecutable()));
	}

	public Location requireLocation(EditPart editPart) {
		assertThat("Not a graphical edit-part", editPart, instanceOf(IGraphicalEditPart.class));
		View view = ((IGraphicalEditPart) editPart).getNotationView();
		assertThat("View is not a Node", view, instanceOf(Node.class));
		LayoutConstraint constraint = ((Node) view).getLayoutConstraint();
		assertThat("Layout constraint is not a Location", constraint, instanceOf(Location.class));
		return (Location) constraint;
	}

	public Property createCapsulePartWithTool(Point mouse, String elementTypeID, String capsuleName, String partName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		Command command = getCreateCapsulePartCommand(part, mouse, elementTypeID);
		assertThat("No executable command obtained from diagram", command, isExecutable());
		editor.execute(command);

		EncapsulatedClassifier partType = (EncapsulatedClassifier) part.getType();
		assertThat("No port created", partType.getOwnedAttributes(), hasItem(anything()));
		return partType.getOwnedAttributes().get(partType.getOwnedAttributes().size() - 1);
	}

	/**
	 * Obtains a command that creates a port of the specified type at the given {@code mouse}
	 * location on the given {@code part} in a capsule structure diagram.
	 * 
	 * @param part
	 *            a capsule-part
	 * @param mouse
	 *            the mouse location in the diagram (in the diagram's overall coordinate system)
	 * @param elementTypeID
	 *            the type of port to create
	 * 
	 * @return the command, or {@code null} if none was provided by the diagram
	 */
	private Command getCreateCapsulePartCommand(Property part, Point mouse, String elementTypeID) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);

		IHintedType typeToCreate = (IHintedType) ElementTypeRegistry.getInstance().getType(elementTypeID);
		CreateElementRequest createElement = new CreateElementRequest(part, typeToCreate);
		CreateElementRequestAdapter createAdapter = new CreateElementRequestAdapter(createElement);
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(
				new CreateViewAndElementRequest.ViewAndElementDescriptor(
						createAdapter, Node.class, typeToCreate.getSemanticHint(), editor.getPreferencesHint()));
		request.setType(RequestConstants.REQ_CREATE);
		request.setLocation(mouse);
		EditPart target = getTargetEditPart(underMouse, request);

		return target.getCommand(request);
	}

	public RelativePortLocation getRelativeLocation(EditPart port) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) port;
		IGraphicalEditPart parent = (IGraphicalEditPart) port.getParent();

		Rectangle portBounds = editPart.getFigure().getBounds().getCopy();
		Rectangle parentBounds = parent.getFigure().getBounds();
		portBounds.translate(parentBounds.getLocation().getNegated());

		return RelativePortLocation.of(portBounds, parentBounds);
	}

	public Property dropCapsule(Point mouse, String capsuleTypeName) {
		return dropCapsule(mouse, capsuleName, capsuleTypeName);
	}

	public Property dropCapsule(Point mouse, String capsuleName, String capsuleTypeName) {
		org.eclipse.uml2.uml.Class capsule = (Class) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.CLASS, false);

		org.eclipse.uml2.uml.Class capsuleType = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType(capsuleTypeName);

		Command command = getDropCapsuleCommand(capsule, mouse, capsuleType);
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No capsule Part created", capsule.getOwnedAttributes(), hasItem(anything()));
		return capsule.getOwnedAttributes().get(capsule.getOwnedAttributes().size() - 1);
	}

	/**
	 * Obtains a command that drops a {@code protocol} at the given {@code mouse}
	 * location on the given {@code part} in a capsule structure diagram.
	 * 
	 * @param part
	 *            a capsule-part
	 * @param mouse
	 *            the mouse location in the diagram (in the diagram's overall coordinate system)
	 * @param protocol
	 *            the protocol to drop
	 * 
	 * @return the command, or {@code null} if none was provided by the diagram
	 */
	private Command getDropCapsuleCommand(org.eclipse.uml2.uml.Class capsule, Point mouse, org.eclipse.uml2.uml.Class capsuleType) {
		return getDropCapsulesCommand(capsule, mouse, Collections.singletonList(capsuleType));
	}

	private Command getDropCapsulesCommand(org.eclipse.uml2.uml.Class capsule, Point mouse, List<? extends org.eclipse.uml2.uml.Class> capsuleTypes) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);


		DropObjectsRequest request = new DropObjectsRequest();
		request.setLocation(mouse);
		request.setObjects(capsuleTypes);

		EditPart target = getTargetEditPart(underMouse, request);
		return target.getCommand(request);
	}

	protected EditPart getTargetEditPart(EditPart editPart, Request request) {
		EditPart result = editPart;

		// Drill through text compartments
		while (result instanceof ITextAwareEditPart) {
			result = result.getParent();
		}

		result = result.getTargetEditPart(request);

		return result;
	}

	public List<Property> dropCapsules(Point mouse, List<String> capsuleNames) {
		return dropCapsules(mouse, capsuleName, partName, capsuleNames);
	}

	public List<Property> dropCapsules(Point mouse, String capsuleName, String partName, List<String> capsuleNames) {
		Class capsule = (Class) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.CLASS, false);

		List<Class> capsuleTypes = capsuleNames.stream()
				.map(this::getCapsule)
				.collect(Collectors.toList());

		int initialPartsCount = capsule.getOwnedAttributes().size();

		Command command = getDropCapsulesCommand(capsule, mouse, capsuleTypes);
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No ports created", capsule.getOwnedAttributes().size(), greaterThan(initialPartsCount));
		return new ArrayList<>(capsule.getOwnedAttributes().subList(initialPartsCount, capsule.getOwnedAttributes().size()));
	}

	private Class getCapsule(String name) {
		return (Class) editor.getModel().getOwnedType(
				name, false, UMLPackage.Literals.CLASS, false);
	}

	public void assertCannotDropCapsule(Point mouse, String protocolName) {
		assertCannotDropCapsule(mouse, capsuleName, partName, protocolName);
	}

	public void assertCannotDropCapsule(Point mouse, String capsuleName, String partName, String capsuleTypeName) {
		Class capsule = (Class) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.CLASS, false);

		Class capsuleType = (Class) editor.getModel().getOwnedType(capsuleTypeName);

		Command command = getDropCapsuleCommand(capsule, mouse, capsuleType);
		assertThat("An executable drop command was provided", command, not(isExecutable()));
	}
}
