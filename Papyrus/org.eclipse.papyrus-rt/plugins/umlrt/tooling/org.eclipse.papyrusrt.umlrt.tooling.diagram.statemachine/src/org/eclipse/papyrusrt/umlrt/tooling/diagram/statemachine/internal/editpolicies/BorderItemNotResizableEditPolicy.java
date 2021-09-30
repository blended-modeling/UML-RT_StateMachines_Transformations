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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.Handle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.uml.diagram.common.commands.FixPortLocationCommand;
import org.eclipse.papyrus.uml.diagram.common.commands.ResizeParentFigureCommand;
import org.eclipse.papyrus.uml.diagram.common.commands.UpdatePortLocationCommand;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.BorderItemResizableEditPolicy;

/**
 * Specific edit policy to avoid resize of the elements on border.
 */
public class BorderItemNotResizableEditPolicy extends BorderItemResizableEditPolicy {

	/**
	 * Constructor.
	 */
	public BorderItemNotResizableEditPolicy() {
		// empty
	}

	@Override
	public void activate() {
		// Not resizable (but still show selection handles)
		setResizeDirections(0);

		super.activate();
	}

	/**
	 * Customizes the selection handle drag behaviour to ensure that
	 * the drag always tracks around the shape on which border the
	 * port sits (never trying to drag the handle into that shape's
	 * parent compartment, for example).
	 */
	@Override
	protected void replaceHandleDragEditPartsTracker(Handle handle) {
		if (handle instanceof AbstractHandle) {
			AbstractHandle h = (AbstractHandle) handle;
			h.setDragTracker(new DragEditPartsTrackerEx(getHost()) {
				@Override
				protected boolean updateTargetUnderMouse() {
					// Don't ever try to drag off of the shape
					// whose border the item is on
					return false;
				};
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gef.editpolicies.ResizableEditPolicy#getResizeCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@Override
	protected Command getResizeCommand(final ChangeBoundsRequest request) {

		// Create the complete resize command
		CompoundCommand resizeCommand = new CompoundCommand("Resize command"); //$NON-NLS-1$

		Object model = ((GraphicalEditPart) getHost()).getModel();
		if (model instanceof View) {
			boolean resizable = NotationUtils.getBooleanValue((View) model, NamedStyleProperties.IS_PORT_RESIZABLE, false);
			if (resizable) {

				// Prepare command to move the affixed children as well (and an optional
				// fix command)
				CompoundCommand updatePortLocationsCommand = new CompoundCommand("Update border test location"); //$NON-NLS-1$
				CompoundCommand fixPortLocationsCommand = new CompoundCommand("Fix border items location"); //$NON-NLS-1$

				Command res = getResizePortCommand(request);

				if (null != request.getEditParts()) {

					for (Object object : request.getEditParts()) {
						if (object instanceof RoundedBorderNamedElementEditPart) {
							RoundedBorderNamedElementEditPart editpart = (RoundedBorderNamedElementEditPart) object;
							Shape view = (Shape) editpart.getNotationView();
							TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(view);

							ICommand updatePortSize = new ResizeParentFigureCommand(editingDomain, (DefaultSizeNodeFigure) editpart.getPrimaryShape().getParent(), request);
							resizeCommand.add(new ICommandProxy(updatePortSize));

							ICommand fixPortLocationCommand = new FixPortLocationCommand(editingDomain, editpart, (GraphicalEditPart) getHost());
							if (fixPortLocationCommand.canExecute()) {
								fixPortLocationsCommand.add(new ICommandProxy(fixPortLocationCommand));
							}

							ICommand updatePortLocationCommand = new UpdatePortLocationCommand(editingDomain, request, (GraphicalEditPart) getHost(), editpart, editpart.getBorderItemLocator().getCurrentSideOfParent());
							if (updatePortLocationCommand.canExecute()) {
								updatePortLocationsCommand.add(new ICommandProxy(updatePortLocationCommand));
							}
						}
					}
					if (!fixPortLocationsCommand.isEmpty()) {
						resizeCommand.add(fixPortLocationsCommand);
					}
					if (!updatePortLocationsCommand.isEmpty()) {
						resizeCommand.add(updatePortLocationsCommand);
					}
					resizeCommand.add(res);
				}
			}
		}

		return resizeCommand.isEmpty() ? null : resizeCommand;
	}

	/**
	 * This method makes sure that the minimum dimension of the port figure is 20x20
	 * and setting the port figure size due to the request.
	 * 
	 * @param request
	 *            change size bound request
	 * @return
	 */
	protected Command getResizePortCommand(ChangeBoundsRequest request) {
		ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_RESIZE_CHILDREN);
		req.setCenteredResize(request.isCenteredResize());
		req.setConstrainedMove(request.isConstrainedMove());
		req.setConstrainedResize(request.isConstrainedResize());
		req.setSnapToEnabled(request.isSnapToEnabled());
		req.setEditParts(getHost());

		req.setMoveDelta(request.getMoveDelta());

		req.setLocation(request.getLocation());
		req.setExtendedData(request.getExtendedData());
		req.setResizeDirection(request.getResizeDirection());

		if (getHost().getParent() == null) {
			return null;
		}

		if (request.getEditParts() == null)
			return null;

		for (Object object : request.getEditParts()) {
			if (object instanceof RoundedBorderNamedElementEditPart) {
				RoundedBorderNamedElementEditPart editPart = (RoundedBorderNamedElementEditPart) object;

				int w = editPart.getFigure().getBounds().width + request.getSizeDelta().width;
				int h = editPart.getFigure().getBounds().height + request.getSizeDelta().height;

				// check if size is too small? make sure minimum size is 20x20
				int wdelta = 20 - editPart.getFigure().getBounds().width;
				int hdelta = 20 - editPart.getFigure().getBounds().height;

				Dimension sizeDelta = request.getSizeDelta();
				if (w < 20) {
					sizeDelta.setWidth(wdelta);
				}
				if (h < 20)
					sizeDelta.setHeight(hdelta);
				req.setSizeDelta(sizeDelta);
			}
		}
		Command cm = getHost().getParent().getCommand(req);

		return cm;
	}

}
