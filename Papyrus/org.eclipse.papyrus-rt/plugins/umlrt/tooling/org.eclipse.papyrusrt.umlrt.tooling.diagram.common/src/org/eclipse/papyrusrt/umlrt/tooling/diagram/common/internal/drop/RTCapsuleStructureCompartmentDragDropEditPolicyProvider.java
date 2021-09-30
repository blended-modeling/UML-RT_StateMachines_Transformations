/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 472885, 496464
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.drop;

import java.util.function.Supplier;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.CustomizableDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.CustomDiagramDragDropEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.RTCustomDiagramDragDropEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.RTResizableShapeEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.RTSideAffixedNodesCreationEditPolicy;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * <p>
 * Specific Edit policy provider for some elements on the capsule structure diagram to override customizable drag and drop edit policies.
 * </p>
 * <p>
 * This is in part used as a workaround for <a href="http://eclip.se/472885">bug 472885: [tooling] Papyrus-RT shall support CapsulePart creation using drag n drop from model explorer</a>.
 * </p>
 * 
 * @see <a href="http://eclip.se/472885">bug 472885</a>
 */
public class RTCapsuleStructureCompartmentDragDropEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	// Ordering is important because some edit-policies look for ones installed previously
	private static final ListMultimap<Class<? extends EditPart>, EditPolicyBinding> editPolicies = ImmutableListMultimap.<Class<? extends EditPart>, EditPolicyBinding> builder()
			.put(RTClassCompositeEditPart.class, bind(EditPolicyRoles.CREATION_ROLE, RTSideAffixedNodesCreationEditPolicy::new))
			.put(RTClassCompositeEditPart.class, bind(EditPolicyRoles.DRAG_DROP_ROLE, RTCustomDiagramDragDropEditPolicy::new))
			.put(RTClassCompositeCompartmentEditPart.class, bind(EditPolicyRoles.DRAG_DROP_ROLE, CustomDiagramDragDropEditPolicy::new))
			.put(RTPropertyPartEditPart.class, bind(EditPolicyRoles.CREATION_ROLE, RTSideAffixedNodesCreationEditPolicy::new))
			.put(RTPropertyPartEditPart.class, bind(EditPolicyRoles.DRAG_DROP_ROLE, RTCustomDiagramDragDropEditPolicy::new))
			.put(RTPropertyPartEditPart.class, bind(EditPolicy.PRIMARY_DRAG_ROLE, RTResizableShapeEditPolicy::new))
			.build();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;

		try {
			ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(epOperation.getEditPart());
			if (registry == null) {
				return false;
			}

			// Do I recognize this edit-part?
			EditPart editPart = epOperation.getEditPart();
			return editPolicies.containsKey(editPart.getClass());
		} catch (ServiceException e) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createEditPolicies(EditPart editPart) {
		// Install our edit-policies for this edit-part. Critically: including
		// the drop policy!
		editPolicies.get(editPart.getClass()).forEach(b -> b.apply(editPart));

		// Unwrap the existing creation policy, if necessary
		EditPolicy creation = editPart.getEditPolicy(EditPolicyRoles.CREATION_ROLE);
		if (creation instanceof CustomizableDropEditPolicy) {
			creation = ((CustomizableDropEditPolicy) creation).getDefaultCreationPolicy();
		}

		// create a new edit policy as it is done in standard diagrams.
		CustomizableDropEditPolicy customEditPolicy = new RTCapsuleStructureDiagramDragDropEditPolicy(
				editPart.getEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE),
				creation);
		editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, null);
		editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, customEditPolicy);

	}

	private static EditPolicyBinding bind(String role, Supplier<? extends EditPolicy> editPolicy) {
		return new EditPolicyBinding(role, editPolicy);
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
			editPart.installEditPolicy(role, editPolicySupplier.get());
		}
	}
}
