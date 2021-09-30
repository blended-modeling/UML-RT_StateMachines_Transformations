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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import static org.eclipse.gmf.runtime.common.core.util.StringStatics.BLANK;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.commands.RestrictedArrangeCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout.PrivateLayoutService;
import org.eclipse.uml2.uml.Port;

/**
 * A custom container edit-policy that arranges ports not via the
 * {@link LayoutService} but by explicit delegation to UML-RT specific
 * layout providers (in case ELK is present in the installation to
 * break the layout of border items).
 * 
 * @see <a href="http://eclip.se/500743">bug 500743</a>
 */
public class PortContainerEditPolicy extends ContainerEditPolicy {

	/**
	 * Initializes me.
	 */
	public PortContainerEditPolicy() {
		super();
	}

	/**
	 * Obtains a custom arrange command that does not delegate arrangement
	 * to the {@code LayoutService} but instead uses UML-RT specific
	 * layout providers.
	 *
	 * @param request
	 *            the arrange request
	 * @return the arrange command
	 */
	@Override
	protected Command getArrangeCommand(ArrangeRequest request) {

		if (RequestConstants.REQ_ARRANGE_DEFERRED.equals(request.getType())) {
			// Super behaviour is fine for deferral. We will return for
			// the main event
			return super.getArrangeCommand(request);
		}
		if (RequestConstants.REQ_ARRANGE_RADIAL.equals(request.getType())) {
			// We do not do a radial layout
			return super.getArrangeCommand(request);
		}

		String layoutDesc = (request.getLayoutType() != null) ? request.getLayoutType() : LayoutType.DEFAULT;
		if (!layoutDesc.equals(LayoutType.DEFAULT)) {
			// We only customize the default layout
			return super.getArrangeCommand(request);
		}

		boolean offsetFromBoundingBox = false;
		List<IGraphicalEditPart> editparts = new ArrayList<>();

		if ((ActionIds.ACTION_ARRANGE_ALL.equals(request.getType())) ||
				(ActionIds.ACTION_TOOLBAR_ARRANGE_ALL.equals(request.getType()))) {
			editparts = ((IGraphicalEditPart) getHost()).getChildren();
			request.setPartsToArrange(editparts);
		}
		if ((ActionIds.ACTION_ARRANGE_SELECTION.equals(request.getType())) ||
				(ActionIds.ACTION_TOOLBAR_ARRANGE_SELECTION.equals(request.getType()))) {
			editparts = request.getPartsToArrange();
			offsetFromBoundingBox = true;
		}

		// If not all edit-parts to be arranged are ports, go super. In the
		// canonical edit-policy scenario that we care about most, all edit-parts
		// will always be ports
		if (!editparts.stream().allMatch(g -> g.resolveSemanticElement() instanceof Port)) {
			return super.getArrangeCommand(request);
		}

		Command result = null;

		if (!editparts.isEmpty()) {
			List<Object> hints = new ArrayList<>(2);
			hints.add(layoutDesc);
			hints.add(getHost());
			IAdaptable layoutHint = new ObjectAdapter(hints);

			TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();

			ICommand arrange = new RestrictedArrangeCommand(editingDomain, BLANK,
					PrivateLayoutService.INSTANCE,
					null, editparts, layoutHint, offsetFromBoundingBox);

			// Don't worry about snapping ports to the grid

			result = new ICommandProxy(arrange);
		}

		return result;
	}

	//
	// Nested types
	//


}
