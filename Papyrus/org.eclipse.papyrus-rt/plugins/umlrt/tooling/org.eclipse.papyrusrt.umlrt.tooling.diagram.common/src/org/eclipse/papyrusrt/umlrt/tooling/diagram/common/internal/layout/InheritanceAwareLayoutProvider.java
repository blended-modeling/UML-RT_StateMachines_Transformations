/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutNodeProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;

/**
 * A provider of layout for edit-parts that inherit their
 * layout from a parent diagram. Its purpose is to ensure
 * that no automatic layout is performed on an inherited
 * layout.
 */
public class InheritanceAwareLayoutProvider extends AbstractLayoutNodeProvider {

	/**
	 * Initializes me.
	 */
	public InheritanceAwareLayoutProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		if (operation instanceof ILayoutNodeOperation) {
			ILayoutNodeOperation layout = (ILayoutNodeOperation) operation;
			if (LayoutType.DEFAULT.equals(layout.getLayoutHint().getAdapter(String.class))) {
				IGraphicalEditPart container = layout.getLayoutHint().getAdapter(IGraphicalEditPart.class);

				if (container != null) {
					@SuppressWarnings("unchecked")
					Map<?, ? extends EditPart> registry = container.getViewer().getEditPartRegistry();

					// We provide the layout if all nodes inherit their layout, because
					// then our layout algorithm is a no-op
					@SuppressWarnings("unchecked")
					List<ILayoutNode> nodes = layout.getLayoutNodes();
					result = nodes.stream()
							.map(ILayoutNode::getNode)
							.map(registry::get)
							.allMatch(this::isLayoutInherited);
				}
			}
		}

		return result;
	}

	@Override
	public Runnable layoutLayoutNodes(@SuppressWarnings("rawtypes") List layoutNodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
		// My purpose is to prevent layout because the layout is inherited
		return InheritanceAwareLayoutProvider::pass;
	}

	/**
	 * Queries whether an edit-part's layout is inherited.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return whether its layout is inherited
	 */
	protected boolean isLayoutInherited(EditPart editPart) {
		return (editPart instanceof IInheritableEditPart)
				&& ((IInheritableEditPart) editPart).isInherited();
	}

	static void pass() {
		// Pass
	}
}
