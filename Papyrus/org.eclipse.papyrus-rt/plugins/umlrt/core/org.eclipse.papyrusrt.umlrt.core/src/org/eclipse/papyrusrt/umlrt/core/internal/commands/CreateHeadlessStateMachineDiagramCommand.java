/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493869, 510315, 513166
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.internal.commands;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramPrototype;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.statemachine.CreateStateMachineDiagramCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Specific implementation for the creation of the diagram, to avoid setting a name using a popup
 */
public class CreateHeadlessStateMachineDiagramCommand extends CreateStateMachineDiagramCommand {

	/** Implementation ID for the viewpoint's diagram prototype definition. */
	private static final String IMPLEMENTATION_ID = "UMLRTStateMachine"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public CreateHeadlessStateMachineDiagramCommand() {
		super();
	}

	@Override
	public String getCreatedDiagramType() {
		return IMPLEMENTATION_ID;
	}

	@Override
	protected Diagram doCreateDiagram(Resource diagramResource, EObject owner, EObject element, DiagramPrototype prototype, String name) {
		HeadlessDiagramCreationHelper helper = new HeadlessDiagramCreationHelper();

		Diagram result = super.doCreateDiagram(diagramResource, owner, element, prototype, name);

		if (result != null) {
			helper.recordOwnerAndElement(result, owner, element);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doEditDiagramName(ViewPrototype prototype, String name) {
		// overrides dialog creation to edit the name
		return CommandResult.newOKCommandResult(name);
	}

	/**
	 * <p>
	 * <b>Note:</b> this is copied from the superclass implementation of {@link #initializeDiagram(EObject)}.
	 * </p>
	 * FIXME: In Papyrus 3.0 (Papyrus-RT 1.0), refactor superclass with new extensibility API
	 */
	protected void basicInitializeDiagram(EObject diagram) {
		if (diagram instanceof Diagram) {
			Diagram diag = (Diagram) diagram;

			StateMachine stateMachine = null;
			State state = null;
			if (diag.getElement() instanceof StateMachine) {
				stateMachine = (StateMachine) diag.getElement();
			} else if (diag.getElement() instanceof State) {
				state = (State) diag.getElement();
			}

			if (stateMachine != null) {
				// We require a distinct, real definition for the diagram to referene
				if (UMLRTExtensionUtil.isVirtualElement(stateMachine)) {
					UMLRTExtensionUtil.touch(stateMachine);
				}

				diag.setElement(stateMachine);
				View stateMachineView = ViewService.getInstance().createView(Node.class, new EObjectAdapter(stateMachine), diag, null, ViewUtil.APPEND, true, getPreferenceHint());
				Zone.setX(stateMachineView, defaultX);
				Zone.setY(stateMachineView, defaultY);
				Zone.setWidth(stateMachineView, defaultWidth);
				Zone.setHeight(stateMachineView, defaultHeight);
				View compartmentView = null;
				Iterator<?> it = stateMachineView.getChildren().iterator();
				while (it.hasNext()) {
					Object next = it.next();
					if (next instanceof Node) {
						Node currentNode = (Node) next;
						if (currentNode.getLayoutConstraint() == null) {
							currentNode.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
						}
						if (StateMachineNameEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getVisualID(currentNode.getType()))) {
							Zone.setWidth(currentNode, defaultWidth);
							Zone.setHeight(currentNode, defaultHeader);
						} else if (StateMachineCompartmentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getVisualID(currentNode.getType()))) {
							Zone.setY(currentNode, defaultHeader);
							Zone.setWidth(currentNode, defaultWidth);
							Zone.setHeight(currentNode, defaultHeight - defaultHeader);
							compartmentView = currentNode;
						}
					}
				}
				Region region = null;
				EList<Region> regions = UMLRTExtensionUtil.getUMLRTContents(stateMachine, UMLPackage.Literals.STATE_MACHINE__REGION);
				if (regions.isEmpty()) {
					region = stateMachine.createRegion(null);
					region.setName(NamedElementUtil.getDefaultNameWithIncrement(region, regions));
				} else {
					region = regions.get(0);
				}

				if (UMLRTExtensionUtil.isInherited(region)) {
					// The notation always references the root definition
					region = UMLRTExtensionUtil.getRootDefinition(region);
				}

				IAdaptable regionAdaptable = new SemanticAdapter(region, null);
				String semanticHint = ((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint();
				if (compartmentView != null) {
					Node regionNode = ViewService.getInstance().createNode(regionAdaptable, compartmentView, semanticHint, -1, getPreferenceHint());
					if (regionNode.getLayoutConstraint() == null) {
						regionNode.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
					}
					// add region specifics
					Zone.createRegionDefaultAnnotation(regionNode);
					Zone.setWidth(regionNode, defaultWidth);
					Zone.setHeight(regionNode, defaultHeight - defaultHeader);
				}
			} else if (state != null) {
				// We require a distinct, real definition for the diagram to referene
				if (UMLRTExtensionUtil.isVirtualElement(state)) {
					UMLRTExtensionUtil.touch(state);
				}

				diag.setElement(state);
				View stateMachineView = ViewService.getInstance().createView(Node.class, new EObjectAdapter(state), diag, null, ViewUtil.APPEND, true, getPreferenceHint());
				Zone.setX(stateMachineView, defaultX);
				Zone.setY(stateMachineView, defaultY);
				Zone.setWidth(stateMachineView, defaultWidth);
				Zone.setHeight(stateMachineView, defaultHeight);
				View compartmentView = null;
				Iterator<?> it = stateMachineView.getChildren().iterator();
				while (it.hasNext()) {
					Object next = it.next();
					if (next instanceof Node) {
						Node currentNode = (Node) next;
						if (currentNode.getLayoutConstraint() == null) {
							currentNode.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
						}
						if (StateNameEditPartTN.VISUAL_ID.equals(UMLVisualIDRegistry.getVisualID(currentNode.getType()))) {
							Zone.setWidth(currentNode, defaultWidth);
							Zone.setHeight(currentNode, defaultHeader);
						} else if (StateCompartmentEditPartTN.VISUAL_ID.equals(UMLVisualIDRegistry.getVisualID(currentNode.getType()))) {
							Zone.setY(currentNode, defaultHeader);
							Zone.setWidth(currentNode, defaultWidth);
							Zone.setHeight(currentNode, defaultHeight - defaultHeader);
							compartmentView = currentNode;
						}
					}
				}
				Region region = null;
				EList<Region> regions = UMLRTExtensionUtil.getUMLRTContents(state, UMLPackage.Literals.STATE__REGION);
				if (regions.isEmpty()) {
					region = state.createRegion(null);
					region.setName(NamedElementUtil.getDefaultNameWithIncrement(region, regions));
				} else {
					region = regions.get(0);
				}

				if (UMLRTExtensionUtil.isInherited(region)) {
					// The notation always references the root definition
					region = UMLRTExtensionUtil.getRootDefinition(region);
				}

				IAdaptable regionAdaptable = new SemanticAdapter(region, null);
				String semanticHint = ((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint();
				if (compartmentView != null) {
					Node regionNode = ViewService.getInstance().createNode(regionAdaptable, compartmentView, semanticHint, -1, getPreferenceHint());
					if (regionNode.getLayoutConstraint() == null) {
						regionNode.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
					}
					// add region specifics
					Zone.createRegionDefaultAnnotation(regionNode);
					Zone.setWidth(regionNode, defaultWidth);
					Zone.setHeight(regionNode, defaultHeight - defaultHeader);
				}
			}
		}
	}

	@Override
	protected void initializeDiagram(EObject diagram) {
		basicInitializeDiagram(diagram);

		// enforce name = null
		((Diagram) diagram).setName(null);
	}

}
