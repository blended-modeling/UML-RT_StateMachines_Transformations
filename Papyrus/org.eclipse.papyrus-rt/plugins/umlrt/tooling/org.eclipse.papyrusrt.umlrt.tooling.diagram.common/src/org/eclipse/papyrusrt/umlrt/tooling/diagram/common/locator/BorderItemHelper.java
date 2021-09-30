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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.gmfdiag.common.snap.NodeSnapHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.uml2.uml.Element;

/**
 * A helper for creation edit policies that have to deal with border-items
 * of different sizes, in particular different to the Papyrus default 20-by-20.
 */
public class BorderItemHelper<E extends Element> {

	protected final Class<? extends E> semanticType;

	protected final Supplier<? extends IGraphicalEditPart> hostSupplier;
	protected final Supplier<? extends PortPositionLocator> positionLocatorSupplier;

	protected E element;

	/**
	 * Initializes me.
	 */
	public BorderItemHelper(Class<? extends E> semanticType,
			Supplier<? extends IGraphicalEditPart> hostSupplier,
			Supplier<? extends PortPositionLocator> positionLocatorSupplier) {

		super();

		this.semanticType = semanticType;
		this.hostSupplier = hostSupplier;
		this.positionLocatorSupplier = positionLocatorSupplier;
	}

	/**
	 * @return the element
	 */
	public E getElement() {
		return element;
	}

	/**
	 * Gets a set-bounds command for the given view {@code descriptor}.
	 * 
	 * @param request
	 *            a view creation request (for delegation)
	 * @param descriptor
	 *            the descriptor of the view to be created
	 * @param sizeFunction
	 *            a function that computes an optional size for the
	 *            semantic element. If the element is not of the expected
	 *            semantic type or the function computes no size, then
	 *            {@code delegate} to the fall-back
	 * @param typeSizeFunction
	 *            a function that computes an optional size for the
	 *            element type. If the element type is not of the expected
	 *            semantic type or the function computes no size, then
	 *            {@code delegate} to the fall-back. sizeFunction delegates
	 *            to this function as a first fall-back, then to the delegate.
	 * @param delegate
	 *            a fall-back algorithm for calculation of the set-bounds command.
	 *            Often this will be {@code super::getSetBoundsCommand} in the caller's context
	 * 
	 * @return the set-bounds command
	 */
	public ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor,
			Function<? super E, Optional<Dimension>> sizeFunction,
			Function<IElementType, Optional<Dimension>> typeSizeFunction,
			BiFunction<? super CreateViewRequest, ? super ViewDescriptor, ? extends ICommand> delegate) {

		// Capture the element for which we're creating a view
		IAdaptable adaptable = descriptor.getElementAdapter();
		element = (adaptable != null)
				? TypeUtils.as(adaptable.getAdapter(EObject.class), semanticType)
				: null;

		IElementType type = (adaptable != null) ? adaptable.getAdapter(IElementType.class) : null;

		return Optional.ofNullable(element)
				.flatMap(sizeFunction::apply)
				.map(d -> basicGetSetBoundsCommand(request, descriptor, d))
				.orElse(
						Optional.ofNullable(type)
								.flatMap(typeSizeFunction::apply)
								.map(d -> basicGetSetBoundsCommand(request, descriptor, d))
								.orElseGet(() -> delegate.apply(request, descriptor)));
	}

	/**
	 * Gets a set-bounds command for the given view {@code descriptor}.
	 * 
	 * @param request
	 *            a view creation request (for delegation)
	 * @param descriptor
	 *            the descriptor of the view to be created
	 * @param sizeFunction
	 *            a function that computes an optional size for the
	 *            semantic element. If the element is not of the expected
	 *            semantic type or the function computes no size, then
	 *            {@code delegate} to the fall-back
	 * @param delegate
	 *            a fall-back algorithm for calculation of the set-bounds command.
	 *            Often this will be {@code super::getSetBoundsCommand} in the caller's context
	 * 
	 * @return the set-bounds command
	 */
	public ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor,
			Function<? super E, Optional<Dimension>> sizeFunction,
			BiFunction<? super CreateViewRequest, ? super ViewDescriptor, ? extends ICommand> delegate) {

		// Capture the element for which we're creating a view
		IAdaptable adaptable = descriptor.getElementAdapter();
		element = (adaptable != null)
				? TypeUtils.as(adaptable.getAdapter(EObject.class), semanticType)
				: null;

		return Optional.ofNullable(element)
				.flatMap(sizeFunction::apply)
				.map(d -> basicGetSetBoundsCommand(request, descriptor, d))
				.orElseGet(() -> delegate.apply(request, descriptor));
	}

	/**
	 * Gets a set-bounds command for the given view {@code descriptor}.
	 * 
	 * @param request
	 *            a view creation request (for delegation)
	 * @param descriptor
	 *            the descriptor of the view to be created
	 * @param size
	 *            the preferred size of the border item view
	 * 
	 * @return the set-bounds command
	 */
	public ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor, Dimension size) {
		return basicGetSetBoundsCommand(request, descriptor, size);
	}

	/**
	 * The {@link SideAffixedNodesCreationEditPolicy} class's {@code getSetBoundsCommand(...)}
	 * method hard-codes the border item size as <tt>20x20</tt>, which cannot be overridden,
	 * so we copy the entire implementation here and parameterize it.
	 */
	protected ICommand basicGetSetBoundsCommand(CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor, Dimension size) {
		ICommand setBoundsCommand = null;
		IGraphicalEditPart host = hostSupplier.get();
		IFigure hostFigure = host.getFigure();
		TransactionalEditingDomain editingDomain = host.getEditingDomain();
		// Retrieve parent location
		Point parentLoc = hostFigure.getBounds().getLocation().getCopy();

		Point requestedLocation = computeLocation(request, descriptor, size);
		hostFigure.translateToRelative(requestedLocation);
		// Create proposed creation bounds and use the locator to find the expected position
		PortPositionLocator locator = positionLocatorSupplier.get();
		if (locator == null) {
			return null;
		}
		final Rectangle preferredBounds = locator.getPreferredLocation(new Rectangle(requestedLocation, size));
		Rectangle retainedBounds = preferredBounds.getCopy();
		// find the current side of the wanted position
		final Rectangle parentBounds = hostFigure.getBounds().getCopy();
		// break all!!! getHostFigure().translateToAbsolute(parentBounds);
		locator.setConstraint(preferredBounds.getCopy().translate(parentBounds.getLocation().getNegated()));
		int currentSide = locator.getCurrentSideOfParent();
		if (request.isSnapToEnabled() && currentSide != PositionConstants.NORTH_EAST && currentSide != PositionConstants.NORTH_WEST && currentSide != PositionConstants.SOUTH_EAST && currentSide != PositionConstants.SOUTH_WEST) { // request for snap port at the
																																																										// // creation
			// we find the best location with snap
			Point wantedPoint = preferredBounds.getLocation();
			hostFigure.translateToAbsolute(wantedPoint);
			Rectangle portBounds = new Rectangle(wantedPoint, size);
			NodeSnapHelper helper = new NodeSnapHelper(host.getAdapter(SnapToHelper.class), portBounds, false, false, true);
			final ChangeBoundsRequest tmpRequest = new ChangeBoundsRequest("move"); //$NON-NLS-1$
			tmpRequest.setEditParts(Collections.emptyList());
			tmpRequest.setSnapToEnabled(true);
			tmpRequest.setLocation(portBounds.getLocation());
			helper.snapPoint(tmpRequest);
			preferredBounds.translate(tmpRequest.getMoveDelta());
			switch (currentSide) {
			case PositionConstants.NORTH:
			case PositionConstants.SOUTH:
				preferredBounds.y = retainedBounds.y;
				break;
			case PositionConstants.EAST:
			case PositionConstants.WEST:
				preferredBounds.x = retainedBounds.x;
				break;
			default:
				break;
			}
		}
		// Convert the calculated preferred bounds as relative to parent location
		Rectangle creationBounds = preferredBounds.getTranslated(parentLoc.getNegated());
		setBoundsCommand = new SetBoundsCommand(editingDomain, DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, creationBounds);
		return setBoundsCommand;
	}

	/**
	 * @param request
	 * @param size
	 * @param descriptor
	 * @return
	 */
	protected Point computeLocation(CreateViewRequest request, ViewDescriptor descriptor, Dimension size) {
		Map<?, ?> params = request.getExtendedData();
		Point realLocation = (Point) params.get(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION);
		// return initial location pointed by mouse, or the request location itself if no mouse event
		return (realLocation != null) ? realLocation.getCopy() : request.getLocation().getCopy();
	}

	/**
	 * Gets a port-position locator for the element currently being added.
	 * 
	 * @param scaleFactorFunction
	 *            a function that computes an optional scale factor for the
	 *            semantic element. If the element is not of the expected semantic type or the
	 *            function computes no scale factor, then {@code delegate} to the fall-back
	 * @param delegate
	 *            a fall-back algorithm for calculation of the port-position locator.
	 *            Often this will be {@code super::getPositionLocator} in the caller's context
	 * 
	 * @return the set-bounds command
	 */
	public PortPositionLocator getPositionLocator(Function<? super E, OptionalDouble> scaleFactorFunction,
			Supplier<? extends PortPositionLocator> delegate) {

		OptionalDouble scaleFactor = Optional.ofNullable(element)
				.map(scaleFactorFunction::apply)
				.orElse(OptionalDouble.empty());

		return (scaleFactor.isPresent())
				? new RTPortPositionLocator(
						element, hostSupplier.get().getFigure(),
						PositionConstants.NONE,
						scaleFactor.getAsDouble())
				: delegate.get();
	}

}
