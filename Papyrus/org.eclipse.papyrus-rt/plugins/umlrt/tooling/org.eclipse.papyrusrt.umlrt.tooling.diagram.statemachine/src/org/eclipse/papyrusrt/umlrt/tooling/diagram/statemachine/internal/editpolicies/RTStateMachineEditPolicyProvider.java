/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA List, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493866, 493869, 494310, 518265
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils.isRTStateMachineDiagram;

import java.util.function.Supplier;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.CustomizableDropEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.CustomStateMachineDiagramDragDropEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.RTShapeCompartmentPopupBarEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTRegionCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * <p>
 * Specific Edit policy provider for some elements on the RT state machine structure diagram to override view creation and more.
 * </p>
 */
public class RTStateMachineEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	// Ordering is important because some edit-policies look for ones installed previously
	// navigation policies must be provided using provider, as they are already provided by a lower priority provider. They can't move to local edit part definition
	// Graphical node role are also overridden by @{RTLinksLFEditPolicyProvider}, so they should be declared here.
	private static final ListMultimap<Class<? extends EditPart>, EditPolicyBinding> editPolicies = ImmutableListMultimap.<Class<? extends EditPart>, EditPolicyBinding> builder()
			.put(RTStateEditPart.class, bind(EditPolicyRoles.CREATION_ROLE, RTStateConnectionPointsCreationEditPolicy::new))
			.put(RTStateEditPart.class, bind(EditPolicy.GRAPHICAL_NODE_ROLE, RTStateGraphicalNodeEditPolicy::new))
			.put(RTStateEditPartTN.class, bind(EditPolicy.GRAPHICAL_NODE_ROLE, RTStateGraphicalNodeEditPolicy::new))
			.put(RTStateEditPartTN.class, bind(EditPolicyRoles.CREATION_ROLE, RTStateConnectionPointsCreationEditPolicy::new))
			.put(RTStateEditPart.class, bind(NavigationEditPolicy.NAVIGATION_POLICY, RTCustomStateNavigationEditPolicy::new))
			.put(RTStateEditPartTN.class, bind(NavigationEditPolicy.NAVIGATION_POLICY, RTCustomStateNavigationEditPolicy::new))
			.put(RTStateEditPartTN.class, bind(EditPolicy.LAYOUT_ROLE, RTStateLayoutEditPolicy::new))
			.put(RTStateEditPart.class, bind(EditPolicy.LAYOUT_ROLE, RTStateLayoutEditPolicy::new))
			.put(RTRegionCompartmentEditPart.class, bind(EditPolicyRoles.POPUPBAR_ROLE, RTShapeCompartmentPopupBarEditPolicy::new))
			.build();

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;

		try {
			ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(epOperation.getEditPart());
			if (registry == null) {
				return false;
			}

			EditPart editPart = epOperation.getEditPart();

			// Is this in an RT state machine diagram?
			if (isRTStateMachineDiagram(getDiagram(editPart))) {
				// Do I recognize this edit-part?
				result = editPolicies.containsKey(editPart.getClass());
			}
		} catch (ServiceException e) {
			// Obviously not an RT state machine diagram
		}

		return result;
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		// Install our edit-policies for this edit-part
		editPolicies.get(editPart.getClass()).forEach(b -> b.apply(editPart));
	}

	private static EditPolicyBinding bind(String role, Supplier<? extends EditPolicy> editPolicy) {
		return new EditPolicyBinding(role, editPolicy);
	}

	private static Diagram getDiagram(EditPart editPart) {
		return TypeUtils.as(editPart.getRoot().getContents().getModel(), Diagram.class);
	}

	//
	// Nested types
	//

	private static final class EditPolicyBinding {
		final String role;
		final Supplier<? extends EditPolicy> editPolicySupplier;

		EditPolicyBinding(String role, Supplier<? extends EditPolicy> editPolicySupplier) {
			super();

			this.role = role;
			this.editPolicySupplier = editPolicySupplier;
		}

		void apply(EditPart editPart) {
			EditPolicy editPolicy = editPolicySupplier.get();

			if (EditPolicyRoles.CREATION_ROLE.equals(role)) {
				// First, the ceation edit-policy needs to be installed
				// so that it knows its host
				editPart.installEditPolicy(role, editPolicy);

				// Handle the drop/creation coordination
				// install first the edit policy to set the host and activate it, then create the customizable drop edit policy
				DragDropEditPolicy dragDropEditPolicy = new CustomStateMachineDiagramDragDropEditPolicy();
				editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, dragDropEditPolicy);

				CustomizableDropEditPolicy customEditPolicy = new CustomizableDropEditPolicy(
						dragDropEditPolicy,
						editPolicy);
				editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, null);
				editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, customEditPolicy);
			} else if (EditPolicyRoles.DRAG_DROP_ROLE.equals(role)) {
				// Handle the drop/creation coordination
				EditPolicy creation = editPart.getEditPolicy(EditPolicyRoles.CREATION_ROLE);
				if (creation instanceof CustomizableDropEditPolicy) {
					// Unwrap
					creation = ((CustomizableDropEditPolicy) creation).getDefaultCreationPolicy();
				}
				CustomizableDropEditPolicy customEditPolicy = new CustomizableDropEditPolicy(
						editPolicy, creation);
				editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, null);
				editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, customEditPolicy);
			} else {
				// Simple case: just install it
				editPart.installEditPolicy(role, editPolicy);
			}
		}
	}
}
