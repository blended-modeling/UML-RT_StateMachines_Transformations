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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.Node;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.BorderMarchIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy for the dropping of protocols onto the border of a capsule part,
 * to create ports on the capsule-part's capsule type.
 */
public class ProtocolToPortOnPartDropStrategy extends TransactionalDropStrategy {
	private static final String EXTERNAL_PORT_SHAPE_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.capsulestructurediagram.BasicPort_Shape"; //$NON-NLS-1$

	@Override
	public String getLabel() {
		return "Create Port";
	}

	@Override
	public String getDescription() {
		return "Drop protocol to create an external behavior port";
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".protocolToExternalBehaviorPortOnPartDrop";//$NON-NLS-1$ ;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		Command result = null;

		if (request instanceof DropObjectsRequest) {
			DropObjectsRequest drop = getDropObjectsRequest(request);

			if (drop != null) {
				// Unpack the request
				List<Classifier> protocols = getDroppedProtocols(drop);
				EObject targetElement = getTargetSemanticElement(targetEditPart);
				Point location = drop.getLocation();

				if (canDrop(protocols, targetEditPart, targetElement, location)) {
					// Wouldn't have canDrop if this weren't a property
					Property capsulePart = (Property) targetElement;

					if (protocols.size() == 1) {
						// Simple case of creating a single port
						result = getCreatePortCommand(protocols.get(0), capsulePart, targetEditPart, location);
					} else {
						// Dropping multiple protocols to create a port for each
						IFigure figure = ((IGraphicalEditPart) targetEditPart).getFigure();
						Rectangle bounds = figure.getBounds().getCopy();
						figure.translateToAbsolute(bounds);

						CompoundCommand compound = new CompoundCommand();
						ILocationIterator marcher = BorderMarchIterator.from(location, bounds);
						int stride = IRTPortEditPart.getDefaultSize(true).width * 5 / 3;
						for (Classifier protocol : protocols) {
							// Create this port
							Command createPort = getCreatePortCommand(protocol, capsulePart, targetEditPart, location);
							if (createPort != null) {
								compound.add(createPort);

								// March along the border with a little space in between
								// to position the next port
								location = marcher.next(stride);
							}

							result = compound;
						}
					}
				}
			}
		}

		return result;
	}

	protected Command getCreatePortCommand(Classifier protocol, Property capsulePart, EditPart partEditPart, Point location) {
		CompoundCommand result = null;

		CreateElementRequest createElement = new CreateElementRequest(capsulePart, ElementTypeRegistry.getInstance().getType(EXTERNAL_PORT_SHAPE_ID));
		CreateElementRequestAdapter createAdapter = new CreateElementRequestAdapter(createElement);
		ViewAndElementDescriptor descriptor = new ViewAndElementDescriptor(createAdapter, Node.class, PortEditPart.VISUAL_ID, ((GraphicalEditPart) partEditPart).getDiagramPreferencesHint());

		CreateViewAndElementRequest request = new CreateViewAndElementRequest(descriptor);
		request.setLocation(location.getCopy());

		Command createCommand = partEditPart.getCommand(request);
		if ((createCommand != null) && createCommand.canExecute()) {
			result = new CompoundCommand();
			result.add(createCommand);

			// And set the port's type
			ICommand setType = new AbstractTransactionalCommand(((IGraphicalEditPart) partEditPart).getEditingDomain(), "Set Type", null) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					CommandResult result;

					// The created port
					Port port = (Port) createAdapter.getAdapter(Port.class);
					if (port == null) {
						result = CommandResult.newErrorCommandResult("Port not created to set its type"); //$NON-NLS-1$
					} else {
						IElementEditService edit = ElementEditServiceUtils.getCommandProvider(port);
						if (edit == null) {
							result = CommandResult.newErrorCommandResult("No edit provider for " + port); //$NON-NLS-1$
						} else {
							ICommand delegate = edit.getEditCommand(new SetRequest(getEditingDomain(),
									port, UMLPackage.Literals.TYPED_ELEMENT__TYPE, protocol));
							if (delegate == null) {
								result = CommandResult.newErrorCommandResult("No command provided to set type of " + port); //$NON-NLS-1$
							} else {
								delegate.execute(monitor, info);
								result = CommandResult.newOKCommandResult(port);
							}
						}
					}

					return result;
				}
			};

			result.add(new ICommandProxy(setType));
		}

		return result;
	}

	private List<Classifier> getDroppedProtocols(DropObjectsRequest dropRequest) {
		return getSourceEObjects(dropRequest).stream()
				.filter(Classifier.class::isInstance)
				.map(Classifier.class::cast)
				.filter(ProtocolUtils::isProtocol)
				.collect(Collectors.toList());
	}

	protected boolean canDrop(List<Classifier> protocols, EditPart ontoEditPart, EObject ontoElement, Point location) {
		return !protocols.isEmpty()
				&& (ontoElement instanceof Property)
				&& CapsulePartUtils.isCapsulePart((Property) ontoElement)
				&& !isInInterior(ontoEditPart, location);
	}

	private boolean isInInterior(EditPart editPart, Point location) {
		IFigure figure = ((IGraphicalEditPart) editPart).getFigure();
		Rectangle bounds = figure.getBounds();
		location = location.getCopy();
		figure.getParent().translateToRelative(location);

		Rectangle interior = bounds.getShrinked(10, 10);
		return interior.contains(location);
	}
}
