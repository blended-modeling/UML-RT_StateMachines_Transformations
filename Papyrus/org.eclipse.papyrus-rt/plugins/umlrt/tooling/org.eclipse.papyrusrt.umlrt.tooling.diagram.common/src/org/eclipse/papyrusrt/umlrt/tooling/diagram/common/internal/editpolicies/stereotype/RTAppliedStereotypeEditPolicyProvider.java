/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.stereotype;

import static org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY;
import static org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies.AppliedStereotypeCommentEditPolicy.APPLIED_STEREOTYPE_COMMENT;
import static org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies.AppliedStereotypeCompartmentEditPolicy.STEREOTYPE_COMPARTMENT_POLICY;

import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTDiagramKind;

import com.google.common.collect.ImmutableMap;

/**
 * An edit-policy provider that serves to replace stereotype-application edit-policies
 * by our own that account for inheritance of views from other diagrams.
 */
public class RTAppliedStereotypeEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	private final Map<String, Class<? extends EditPolicy>> editPolicies = ImmutableMap.of(
			APPLIED_STEREOTYPE_COMMENT, RTAppliedStereotypeCommentEditPolicy.class,
			STEREOTYPE_LABEL_POLICY, RTAppliedStereotypeLabelEditPolicy.class,
			STEREOTYPE_COMPARTMENT_POLICY, RTAppliedStereotypeCompartmentEditPolicy.class);

	@Override
	public boolean provides(IOperation operation) {
		return isUMLRTDiagram(((CreateEditPoliciesOperation) operation).getEditPart());
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		// Replace edit-policies with our variants
		editPolicies.forEach((role, epClass) -> {
			EditPolicy existing = editPart.getEditPolicy(role);

			// Does an edit-policy exist that is substitutable by ours?
			if ((existing != null) && (existing.getClass().isAssignableFrom(epClass))) {
				try {
					editPart.installEditPolicy(role, epClass.newInstance());
				} catch (Exception e) {
					Activator.log.error("Failed to override edit policy: " + role, e); //$NON-NLS-1$
				}
			}
		});
	}

	Diagram getDiagramView(EditPart editPart) {
		DiagramEditPart diagramEP = DiagramEditPartsUtil.getDiagramEditPart(editPart);
		return (diagramEP == null) ? null : diagramEP.getDiagramView();
	}

	private boolean isUMLRTDiagram(EditPart editPart) {
		return UMLRTDiagramKind.getDiagramKind(editPart).isUMLRTDiagram();
	}
}
