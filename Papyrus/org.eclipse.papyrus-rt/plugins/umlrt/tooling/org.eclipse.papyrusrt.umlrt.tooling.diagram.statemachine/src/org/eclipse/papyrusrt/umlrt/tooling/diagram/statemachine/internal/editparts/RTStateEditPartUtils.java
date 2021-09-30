/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import java.util.Collection;

import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;

/**
 * Utility class for {@link StateEditPart} & {@link StateEditPartTN}.
 */
public final class RTStateEditPartUtils {

	/**
	 * Constructor.
	 */
	private RTStateEditPartUtils() {
		// clients should not instantiate.
	}

	public static Compartment getInternalTransitionCompartment(View containerView) {
		return (Compartment) ((Collection<?>) containerView.getChildren()).stream()
				.map(View.class::cast)
				.filter(RTStateEditPartUtils::isInternalTransitionCompartment)
				.findAny().orElse(null);
	}

	/**
	 * @param viewFromEditPart
	 * @return
	 */
	public static boolean isInternalTransitionCompartment(Object view) {
		return view instanceof Compartment &&
				RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_COMPARTMENT.equals(((Compartment) view).getType());
	}

	/**
	 * Queries whether a vertex view is contained in a region compartment.  For example,
	 * the state top node or a connection point view will not be in a region compartment.
	 * 
	 * @param vertexView a view of a vertex
	 * @return whether it is contained in a region compartment
	 */
	public static boolean isInRegionCompartment(View vertexView) {
		View container = ViewUtil.getContainerView(vertexView);
		return (container != null) && RegionCompartmentEditPart.VISUAL_ID.equals(container.getType());
	}
}
