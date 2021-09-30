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
import static org.hamcrest.CoreMatchers.is;
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
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A fixture for assistance with the editing of ports.
 */
public class PortEditingFixture {
	public static final int PORT_SIZE = 16;

	private final PapyrusEditorFixture editor;

	private String capsuleName;
	private String partName;

	/**
	 * Initializes me with the editor fixture on which I operate.
	 */
	public PortEditingFixture(PapyrusEditorFixture editor) {
		this(editor, null);
	}

	/**
	 * Initializes me with the editor fixture on which I operate and the
	 * capsule on which to create ports, by default.
	 */
	public PortEditingFixture(PapyrusEditorFixture editor, String defaultCapsuleName) {
		this(editor, defaultCapsuleName, null);
	}

	/**
	 * Initializes me with the editor fixture on which I operate and the
	 * part in a capsule on which to create ports, by default.
	 */
	public PortEditingFixture(PapyrusEditorFixture editor, String defaultCapsuleName, String defaultPartName) {
		super();

		this.editor = editor;
		this.capsuleName = defaultCapsuleName;
		this.partName = defaultPartName;
	}

	public Port createPortWithTool(Point mouse, String elementTypeID) {
		return (partName == null)
				? createPortWithTool(mouse, elementTypeID, capsuleName)
				: createPortWithTool(mouse, elementTypeID, capsuleName, partName);
	}

	public Port createPortWithTool(Point mouse, String elementTypeID, String capsuleName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		Command command = getCreatePortCommand(capsule, mouse, elementTypeID);
		assertThat("No executable command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No port created", capsule.getOwnedPorts(), hasItem(anything()));
		return capsule.getOwnedPorts().get(capsule.getOwnedPorts().size() - 1);
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
	private Command getCreatePortCommand(EncapsulatedClassifier capsule, Point mouse, String elementTypeID) {
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

	public void assertCannotCreatePortWithTool(Point mouse, String elementTypeID) {
		if (partName == null) {
			assertCannotCreatePortWithTool(mouse, elementTypeID, capsuleName);
		} else {
			assertCannotCreatePortWithTool(mouse, elementTypeID, capsuleName, partName);
		}
	}

	public void assertCannotCreatePortWithTool(Point mouse, String elementTypeID, String capsuleName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		Command command = getCreatePortCommand(capsule, mouse, elementTypeID);
		assertThat("An executable command was provided", command, not(isExecutable()));
	}

	public void assertCannotCreatePortWithTool(Point mouse, String elementTypeID, String capsuleName, String partName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		Command command = getCreatePortCommand(part, mouse, elementTypeID);
		assertThat("An executable command was provided", command, not(isExecutable()));
	}

	public Location requireLocation(EditPart editPart) {
		return requireConstraint(editPart, Location.class);
	}

	public Size requireSize(EditPart editPart) {
		return requireConstraint(editPart, Size.class);
	}

	public void assertPortSize(EditPart portEditPart) {
		assertPortSize(requireSize(portEditPart));
	}

	public void assertPortSize(Rectangle bounds) {
		assertPortSize(bounds.width(), bounds.height());
	}

	public void assertPortSize(Size size) {
		assertPortSize(size.getWidth(), size.getHeight());
	}

	public void assertPortSize(int width, int height) {
		assertThat(width, is(PORT_SIZE));
		assertThat(height, is(PORT_SIZE));
	}

	private <C extends LayoutConstraint> C requireConstraint(EditPart editPart, Class<C> type) {
		assertThat("Not a graphical edit-part", editPart, instanceOf(IGraphicalEditPart.class));
		View view = ((IGraphicalEditPart) editPart).getNotationView();
		assertThat("View is not a Node", view, instanceOf(Node.class));
		LayoutConstraint constraint = ((Node) view).getLayoutConstraint();
		assertThat("Layout constraint is not a " + type.getSimpleName(), constraint, instanceOf(type));
		return type.cast(constraint);
	}

	public Port createPortWithTool(Point mouse, String elementTypeID, String capsuleName, String partName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		Command command = getCreatePortCommand(part, mouse, elementTypeID);
		assertThat("No executable command obtained from diagram", command, isExecutable());
		editor.execute(command);

		EncapsulatedClassifier partType = (EncapsulatedClassifier) part.getType();
		assertThat("No port created", partType.getOwnedPorts(), hasItem(anything()));
		return partType.getOwnedPorts().get(partType.getOwnedPorts().size() - 1);
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
	private Command getCreatePortCommand(Property part, Point mouse, String elementTypeID) {
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

	public Port dropProtocolOnCapsule(Point mouse, String protocolName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		org.eclipse.uml2.uml.Package container = editor.getModel().getNestedPackage(protocolName);
		Collaboration protocol = (Collaboration) container.getOwnedType(protocolName);

		Command command = getDropProtocolOnCapsuleCommand(capsule, mouse, Collections.singletonList(protocol));
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No port created", capsule.getOwnedPorts(), hasItem(anything()));
		return capsule.getOwnedPorts().get(capsule.getOwnedPorts().size() - 1);
	}

	public Port dropSystemProtocolOnCapsule(Point mouse, Collaboration protocol) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);

		Command command = getDropProtocolOnCapsuleCommand(capsule, mouse, Collections.singletonList(protocol));
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No port created", capsule.getOwnedPorts(), hasItem(anything()));
		return capsule.getOwnedPorts().get(capsule.getOwnedPorts().size() - 1);
	}

	public Command getDropProtocolOnCapsuleCommand(EncapsulatedClassifier capsule, Point mouse, List<? extends Collaboration> protocols) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);


		// DropStrategy drop = new ProtocolToPortOnPartDropStrategy();
		DropObjectsRequest request = new DropObjectsRequest();
		request.setLocation(mouse);
		request.setObjects(protocols);

		EditPart target = getTargetEditPart(underMouse, request);
		return target.getCommand(request);
	}

	public Port dropProtocol(Point mouse, String protocolName) {
		return dropProtocol(mouse, capsuleName, partName, protocolName);
	}

	public Port dropProtocol(Point mouse, String capsuleName, String partName, String protocolName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		org.eclipse.uml2.uml.Package container = editor.getModel().getNestedPackage(protocolName);
		Collaboration protocol = (Collaboration) container.getOwnedType(protocolName);

		Command command = getDropProtocolCommand(part, mouse, protocol);
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		EncapsulatedClassifier partType = (EncapsulatedClassifier) part.getType();
		assertThat("No port created", partType.getOwnedPorts(), hasItem(anything()));
		return partType.getOwnedPorts().get(partType.getOwnedPorts().size() - 1);
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
	private Command getDropProtocolCommand(Property part, Point mouse, Collaboration protocol) {
		return getDropProtocolsCommand(part, mouse, Collections.singletonList(protocol));
	}

	private Command getDropProtocolsCommand(Property part, Point mouse, List<? extends Collaboration> protocols) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);


		// DropStrategy drop = new ProtocolToPortOnPartDropStrategy();
		DropObjectsRequest request = new DropObjectsRequest();
		request.setLocation(mouse);
		request.setObjects(protocols);

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

	public List<Port> dropProtocols(Point mouse, List<String> protocolNames) {
		return dropProtocols(mouse, capsuleName, partName, protocolNames);
	}

	public List<Port> dropProtocols(Point mouse, String capsuleName, String partName, List<String> protocolNames) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		List<Collaboration> protocols = protocolNames.stream()
				.map(this::getProtocol)
				.collect(Collectors.toList());

		EncapsulatedClassifier partType = (EncapsulatedClassifier) part.getType();
		int initialPortCount = partType.getOwnedPorts().size();

		Command command = getDropProtocolsCommand(part, mouse, protocols);
		assertThat("No executable drop command obtained from diagram", command, isExecutable());
		editor.execute(command);

		assertThat("No ports created", partType.getOwnedPorts().size(), greaterThan(initialPortCount));
		return new ArrayList<>(partType.getOwnedPorts().subList(initialPortCount, partType.getOwnedPorts().size()));
	}

	private Collaboration getProtocol(String name) {
		return (Collaboration) editor.getModel().getNestedPackage(name).getOwnedType(
				name, false, UMLPackage.Literals.COLLABORATION, false);
	}

	public void assertCannotDropProtocol(Point mouse, String protocolName) {
		assertCannotDropProtocol(mouse, capsuleName, partName, protocolName);
	}

	public void assertCannotDropProtocol(Point mouse, String capsuleName, String partName, String protocolName) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		org.eclipse.uml2.uml.Package container = editor.getModel().getNestedPackage(protocolName);
		Collaboration protocol = (Collaboration) container.getOwnedType(protocolName);

		Command command = getDropProtocolCommand(part, mouse, protocol);
		assertThat("An executable drop command was provided", command, not(isExecutable()));
	}

	public void assertCannotDropSystemProtocolOnBorder(Point mouse, Collaboration protocol) {
		EncapsulatedClassifier capsule = (EncapsulatedClassifier) editor.getModel().getOwnedType(
				capsuleName, false, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER, false);
		Property part = capsule.getOwnedAttribute(partName, null);

		Command command = getDropProtocolCommand(part, mouse, protocol);
		assertThat("An executable drop command was provided", command, not(isExecutable()));
	}

	public PortFigure requirePortFigure(EditPart editPart) {
		assertThat(editPart, instanceOf(PortEditPart.class));
		return ((PortEditPart) editPart).getPrimaryShape();
	}

}
