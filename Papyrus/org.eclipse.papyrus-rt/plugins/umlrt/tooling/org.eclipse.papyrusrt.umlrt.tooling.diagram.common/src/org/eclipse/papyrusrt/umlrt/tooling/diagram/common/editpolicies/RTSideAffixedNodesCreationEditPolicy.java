/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 482599, 490859, 492368, 489939, 494310, 496464, 496304, 510315, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.scheduleDirectEdit;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.BorderItemHelper;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This EditPolicy is installed on the RTClassCompositeEditPart to manage the location of RTPort that could be internal instead of on the border
 * 
 * @author Céline JANSSENS
 *
 */
public class RTSideAffixedNodesCreationEditPolicy extends SideAffixedNodesCreationEditPolicy {

	private final BorderItemHelper<Port> portHelper = new BorderItemHelper<>(Port.class,
			this::getGraphicalHost, this::getPositionLocator);

	/**
	 * Extends the inherited method with special handling for newly created port views, to
	 * add a deferred update of their initial location on the border of the part to match
	 * their location on the border of the capsule that defines them in its structure
	 * diagram. This calculation is deferred until the actual bounds of the part figure
	 * is known, because this is required for placement of ports on the bottom and right
	 * edges of the part shape.
	 */
	@Override
	protected ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor) {
		ICommand result;

		if (request instanceof CreateViewAndElementRequest) {
			// Creating from the palette
			UMLRTPortKind portKind = getPortKind((CreateViewAndElementRequest) request);
			if (portKind == null) {
				// Not our kind of port. Give up
				result = super.getSetBoundsCommand(request, descriptor);
			} else {
				switch (portKind) {
				case INTERNAL_BEHAVIOR:
				case SAP:
					// Don't pop these to the exterior
					result = getBasicSetBoundsCommand(request, descriptor);
					break;
				default:
					// Pop these to the exterior
					result = portHelper.getSetBoundsCommand(request, descriptor,
							IRTPortEditPart.getDefaultSize(getHostCapsulePart().isPresent()));
					break;
				}
			}
		} else {
			result = portHelper.getSetBoundsCommand(request, descriptor,
					// If it's an RTPort on a CapsulePart, then make the
					// smaller size
					port -> getHostCapsulePart()
							.filter(__ -> RTPortUtils.isRTPort(port))
							.map(__ -> IRTPortEditPart.getDefaultSize(true)),
					(r, d) -> portHelper.getSetBoundsCommand(r, d, IRTPortEditPart.getDefaultSize(getHostCapsulePart().isPresent())));
		}

		return result;
	}

	/**
	 * Obtains a set-bounds command that doesn't snap the port location to the nearest side of the
	 * class shape. This is appropriate only for internal ports.
	 * 
	 * @param request
	 *            the creation request
	 * @param descriptor
	 *            the view descriptor
	 * 
	 * @return the basic where-the-user-asked-for-it set-bounds command
	 */
	private ICommand getBasicSetBoundsCommand(CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();

		Point location = getLocation(request);

		// Compute relative creation location
		getHostFigure().translateToRelative(location);

		// And relative to the class shape for the notation-model coordinates
		Rectangle bounds = new Rectangle(location, IRTPortEditPart.getDefaultSize(getHostCapsulePart().isPresent()));
		final Rectangle parentBounds = getHostFigure().getBounds().getCopy();
		bounds.translate(parentBounds.getLocation().getNegated());

		return new SetBoundsCommand(editingDomain, DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, bounds);
	}

	/**
	 * Gets the best-effort location from a creation request, which accounts for
	 * special information communicated by Papyrus's palette tools.
	 * 
	 * @param request
	 *            a creation request
	 * 
	 * @return its mouse location as a safely mutable copy
	 */
	private Point getLocation(CreateRequest request) {
		Point result;

		Map<?, ?> params = request.getExtendedData();
		Point realLocation = (Point) params.get(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION);
		if (realLocation != null) {
			result = realLocation.getCopy();
		} else {
			result = request.getLocation().getCopy();
		}

		return result;
	}

	/**
	 * Obtains the capsule-part that is the semantic element visualized by my host,
	 * if indeed I visualize a capsule-part.
	 * 
	 * @return the possible capsule-part that my host edit-part presents
	 */
	protected Optional<Property> getHostCapsulePart() {
		return Optional.ofNullable(getHost().getModel())
				.filter(View.class::isInstance)
				.map(View.class::cast)
				.map(View::getElement)
				.filter(Property.class::isInstance)
				.map(Property.class::cast)
				.filter(CapsulePartUtils::isCapsulePart);
	}

	/**
	 * @param request
	 * @return
	 */
	protected Point getParamLocation(CreateViewRequest request) {
		Map<?, ?> params = request.getExtendedData();
		Point realLocation = (Point) params.get(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION);
		return realLocation;
	}

	/**
	 * In case the element is an RTPort, set the locator accordingly.
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy#getPositionLocator()
	 */
	@Override
	protected PortPositionLocator getPositionLocator() {
		return portHelper.getPositionLocator(
				this::getScaleFactor,
				() -> new RTPortPositionLocator(null, getHostFigure(), PositionConstants.NONE,
						IRTPortEditPart.getDefaultScaleFactor(getHostCapsulePart().isPresent())));
	}

	private OptionalDouble getScaleFactor(Port port) {
		return RTPortUtils.isRTPort(port)
				? OptionalDouble.of(IRTPortEditPart.getDefaultScaleFactor(getHostCapsulePart().isPresent()))
				: OptionalDouble.empty();
	}

	/**
	 * @return
	 */
	@Override
	protected IFigure getHostFigure() {
		return ((GraphicalEditPart) getHost()).getFigure();
	}

	/**
	 * Extends the superclass implementation to support creation of internal behaviour
	 * ports within the interior of the structure compartment.
	 */
	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		Command result = null;

		if (getHostCapsulePart().isPresent()) {
			// Trying to create a port on a part
			result = getCreatePortOnPartAndViewCommand(request);
		} else {
			// Trying to create a port on or in a capsule
			UMLRTPortKind portKind = getPortKind(request);
			if (portKind == null) {
				result = EditPartInheritanceUtils.getCreateElementAndViewCommand((IGraphicalEditPart) getHost(),
						request, this::getCreateCommand);
			} else {
				switch (portKind) {
				case EXTERNAL_BEHAVIOR:
				case SPP:
				case RELAY:
					// These are created on the exterior only
					result = !isInInterior(request)
							? super.getCreateElementAndViewCommand(request)
							: UnexecutableCommand.INSTANCE;
					break;
				case INTERNAL_BEHAVIOR:
				case SAP:
					// These are created in the interior only
					result = isInInterior(request)
							? super.getCreateElementAndViewCommand(request)
							: UnexecutableCommand.INSTANCE;
					break;
				default:
					// Invalid port kind
					result = UnexecutableCommand.INSTANCE;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Queries whether the location of a {@code request} is within the interior
	 * of my bounds where I present internal ports.
	 * 
	 * @param request
	 *            a creation request
	 * 
	 * @return whether the location is within my interior
	 */
	boolean isInInterior(CreateRequest request) {
		Rectangle bounds = getHostFigure().getBounds();
		Point location = getLocation(request);
		getHostFigure().getParent().translateToRelative(location);

		Rectangle interior = bounds.getShrinked(10, 10);
		return interior.contains(location);
	}

	/**
	 * Queries the kind of <tt>RTPort</tt> that a {@code request} is creating, if any.
	 * 
	 * @param request
	 *            a creation request
	 * @return the port kind, or {@code null} if it is not creating an <tt>RTPort</tt>
	 */
	private UMLRTPortKind getPortKind(CreateViewAndElementRequest request) {
		IElementType typeToCreate = request.getViewAndElementDescriptor().getElementAdapter().getAdapter(IElementType.class);
		return RTPortUtils.getPortKind(typeToCreate);
	}

	protected Command getCreatePortOnPartAndViewCommand(CreateViewAndElementRequest request) {
		Command result;

		// Unpack the request details
		CreateElementRequestAdapter createElement = request.getViewAndElementDescriptor().getCreateElementRequestAdapter();
		IHintedType portType = (IHintedType) createElement.getAdapter(IElementType.class);
		UMLRTPortKind portKind = RTPortUtils.getPortKind(portType);

		if (portKind == null) {
			// Not a port at all
			result = super.getCreateElementAndViewCommand(request);
		} else {
			switch (portKind) {
			case EXTERNAL_BEHAVIOR:
			case RELAY:
			case SPP:
				// It only makes sense to create ports of a kind that will show up
				// on a part of that capsule type. And only on the border
				result = !isInInterior(request)
						? getCreatePortOnPartAndViewCommand(request, portType)
						: UnexecutableCommand.INSTANCE;
				break;
			default:
				// Not an external port
				result = super.getCreateElementAndViewCommand(request);
				break;
			}
		}

		return result;
	}

	private Command getCreatePortOnPartAndViewCommand(CreateViewAndElementRequest request, IHintedType portType) {
		Command result = UnexecutableCommand.INSTANCE;

		// Find the diagram in which to create the port. Without that, there's nothing to do
		Optional<EncapsulatedClassifier> capsule = getPartCapsuleType();
		Optional<View> capsuleView = findCapsuleView(capsule);
		if (capsuleView.isPresent()) {
			// If we got this far, we know that we have a graphical host, so the get is safe
			TransactionalEditingDomain domain = getGraphicalHost().getEditingDomain();
			CompositeTransactionalCommand composite = new CompositeTransactionalCommand(domain, "Create Port");
			ICommand createPort = null;

			CreateElementRequest semanticRequest = new CreateElementRequest(
					capsule.get(),
					portType,
					UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule.get());
			if (edit != null) {
				// Create the port
				createPort = edit.getEditCommand(semanticRequest);
			}

			if ((createPort != null) && createPort.canExecute()) {
				// Wrap the port creation command to set the new port back into
				// the view-and-element request
				ICommand _createPort = createPort;
				composite.add(new AbstractCommand(createPort.getLabel()) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
						_createPort.execute(progressMonitor, info);
						Port createdPort = TypeUtils.as(_createPort.getCommandResult().getReturnValue(), Port.class);
						if (createdPort != null) {
							request.getViewAndElementDescriptor().getCreateElementRequestAdapter().setNewElement(createdPort);

							// And trigger in-line edit on this port when it shows up on the capsule-part
							scheduleDirectEdit(getHost(), createdPort);
						}
						return _createPort.getCommandResult();
					}

					@Override
					protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
						_createPort.undo(progressMonitor, info);
						return _createPort.getCommandResult();
					}

					@Override
					protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
						_createPort.redo(progressMonitor, info);
						return _createPort.getCommandResult();
					}
				});

				// And create the view
				CreateElementRequestAdapter semanticAdapter = new CreateElementRequestAdapter(semanticRequest);
				ViewDescriptor descriptor = new ViewDescriptor(
						semanticAdapter,
						Node.class, PortEditPart.VISUAL_ID, true,
						request.getViewAndElementDescriptor().getPreferencesHint());
				ICommand createView = new CreateViewCommand(domain, descriptor, capsuleView.get());
				composite.add(createView);

				if (createView.canExecute()) {
					// And set bounds according to the relative location in our capsule-part
					ICommand setBounds = new SetBoundsCommand(domain, "Port Location",
							descriptor,
							getRelativeBounds(getLocation(request), capsuleView.get()));
					composite.add(setBounds);
				}
			}

			result = new ICommandProxy(composite.reduce());
		}

		return result;
	}

	/**
	 * Obtains my graphical edit-part host.
	 * 
	 * @return my graphical edit-part host
	 */
	protected IGraphicalEditPart getGraphicalHost() {
		return (IGraphicalEditPart) getHost();
	}

	/**
	 * Obtains the capsule that is the type of my host edit-part's semantic
	 * property element, if any.
	 * 
	 * @return maybe my host property's capsule type
	 */
	protected Optional<EncapsulatedClassifier> getPartCapsuleType() {
		return Optional.ofNullable(getGraphicalHost())
				.map(IGraphicalEditPart::resolveSemanticElement)
				.filter(Property.class::isInstance)
				.map(Property.class::cast)
				.map(TypedElement::getType)
				.filter(EncapsulatedClassifier.class::isInstance)
				.map(EncapsulatedClassifier.class::cast);
	}

	/**
	 * Finds the class-shape view of the given {@code capsule} in its capsule
	 * structure diagram, if any.
	 * 
	 * @param capsule
	 *            a capsule
	 * @return maybe the class-shape (frame) view of its capsule structure diagram
	 */
	protected Optional<View> findCapsuleView(Optional<EncapsulatedClassifier> capsule) {
		return capsule
				.filter(Class.class::isInstance)
				.map(Class.class::cast)
				.map(UMLRTCapsuleStructureDiagramUtils::getCapsuleStructureDiagram)
				.map(d -> ViewUtil.getChildBySemanticHint(d, ClassCompositeEditPart.VISUAL_ID));
	}

	/**
	 * Gets the bounds of a port on the border of the given capsule view relative
	 * to the specified {@code mouse} location on my capsule-part.
	 * 
	 * @param mouse
	 *            a mouse location on the border of my capsule-part
	 * @param capsuleView
	 *            the view of the capsule type of my capsule-part in its
	 *            structure diagram
	 * 
	 * @return the bounds of a port on the capsule view corresponding to a port-on-part
	 *         at the given {@code mouse} location on my part
	 */
	protected Rectangle getRelativeBounds(Point mouse, View capsuleView) {
		Rectangle result = new Rectangle(mouse.getCopy(),
				// We're locating a port on the capsule
				IRTPortEditPart.getDefaultSize(false));

		// If we got this far, we have a graphical host
		IFigure partFigure = getHostFigure();
		partFigure.translateToRelative(result);

		// And relative to the class shape for the notation-model coordinates
		Rectangle partBounds = partFigure.getBounds().getCopy();
		result.translate(partBounds.getLocation().getNegated());

		// We know that the capsuleView is a node because we found it by its type
		Bounds capsuleBounds = TypeUtils.as(((Node) capsuleView).getLayoutConstraint(), Bounds.class);
		if (capsuleBounds != null) {
			// A port on a part is smaller!
			result.setSize(IRTPortEditPart.getDefaultSize(true));
			RelativePortLocation relative = RelativePortLocation.of(result, partBounds);

			// But, the port on the capsule is still larger
			result.setSize(IRTPortEditPart.getDefaultSize(false));
			result.setLocation(relative.applyTo(
					new Rectangle(capsuleBounds.getX(), capsuleBounds.getY(), capsuleBounds.getWidth(), capsuleBounds.getHeight()),
					result.getSize()));
			// And account for positioning of the centre
			result.translate(result.width() / 2, result.height() / 2);
		}

		return result;
	}
}
