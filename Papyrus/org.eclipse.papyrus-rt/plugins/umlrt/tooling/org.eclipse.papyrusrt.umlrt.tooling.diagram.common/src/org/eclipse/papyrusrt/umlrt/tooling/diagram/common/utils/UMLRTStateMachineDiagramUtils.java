/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493866, 493869
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils;

import static org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils.isRTStateMachine;
import static org.eclipse.papyrusrt.umlrt.core.utils.StateUtils.isRTState;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils.getDiagram;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrusrt.umlrt.core.internal.commands.CreateHeadlessStateMachineDiagramCommand;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.base.Strings;

/**
 * Utility method for diagrams
 */
public class UMLRTStateMachineDiagramUtils {

	private static final String STATE_NAME_SEPARATOR = ".."; //$NON-NLS-1$

	private static final UMLSwitch<String> separatorSwitch = new UMLSwitch<String>() {
		@Override
		public String caseNamedElement(NamedElement object) {
			return NamedElement.SEPARATOR;
		}

		@Override
		public String caseState(State object) {
			return STATE_NAME_SEPARATOR;
		}

		@Override
		public String defaultCase(EObject object) {
			return NamedElement.SEPARATOR;
		}
	};

	private static final UMLSwitch<BehavioredClassifier> contextSwitch = new UMLSwitch<BehavioredClassifier>() {
		@Override
		public BehavioredClassifier caseBehavior(Behavior object) {
			return object.getContext();
		}

		@Override
		public BehavioredClassifier caseVertex(Vertex object) {
			return Optional.of(object)
					.map(Vertex::containingStateMachine)
					.map(this::doSwitch)
					.orElse(null);
		}

		@Override
		public BehavioredClassifier defaultCase(EObject object) {
			return null;
		}
	};

	/**
	 * Returns <code>true</code> if the diagram is a state machine diagram
	 * 
	 * @param diagram
	 *            the diagram to check
	 * @return <code>true</code> if the diagram is a state machine diagram
	 */
	public static boolean isRTStateMachineDiagram(Diagram diagram) {
		if (diagram == null) {
			return false;
		}
		if (PackageEditPart.MODEL_ID.equals(diagram.getType())) {
			EObject businessElement = diagram.getElement();

			return ((businessElement instanceof StateMachine) && isRTStateMachine((StateMachine) businessElement))
					|| ((businessElement instanceof State) && isRTState((State) businessElement));
		}
		return false;
	}

	/**
	 * Returns the name to be displayed for a capsule state machine diagram, e.g. the name of the associated capsule
	 * 
	 * @param diagram
	 *            the diagram to display
	 * @return the name of the associated capsule or the name of the diagram if no capsule can be found
	 */
	public static String getDisplayedDiagramName(Diagram diagram) {
		EObject businessElement = diagram.getElement();

		// check if the diagram has an explicit name
		if (!Strings.isNullOrEmpty(diagram.getName())) {
			return diagram.getName();
		}

		StringBuilder result = new StringBuilder();

		Optional<String> context = Optional.ofNullable(contextSwitch.doSwitch(businessElement))
				.map(NamedElement::getName)
				.filter(name -> !Strings.isNullOrEmpty(name));

		if (context.isPresent()) {
			result.append(context.get());

			// We only have named-element metaclasses in the table
			NamedElement element = (NamedElement) businessElement;
			if (!Strings.isNullOrEmpty(element.getName())) {
				if (result.length() > 0) {
					result.append(separatorSwitch.doSwitch(element));
				}

				result.append(element.getName());
			}

			return (result.length() > 0) ? result.toString() : "<error>";
		}

		return diagram.getName();
	}

	/**
	 * Creates a new RT state machine diagram, if possible, on the given {@code context}
	 * element.
	 * 
	 * @param context
	 *            the owner and context of the diagram
	 * @param name
	 *            the diagram name, or {@code null} if none
	 * 
	 * @return the diagram, or {@code null} if it could not be created
	 */
	public static Diagram createStateMachineDiagram(EObject context, String name) {
		Diagram result = null;

		ServicesRegistry registry;
		try {
			registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(context);
			ModelSet modelSet = registry.getService(ModelSet.class);

			// returns the prototype for the creation of the diagram
			ViewPrototype prototype = getPrototype(modelSet, context);

			if (prototype != null) {
				result = new CreateHeadlessStateMachineDiagramCommand().createDiagram(modelSet, context, context, prototype, name, false);
			}
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		return result;
	}

	/**
	 * Computes the view prototype for the state machine diagram to be created.
	 * 
	 * @param modelSet
	 *            the model set in which the diagram will be created
	 * @param context
	 *            the context for the new diagram
	 * @return the {@link ViewPrototype} found or <code>null</code> if none was found.
	 */
	private static ViewPrototype getPrototype(ModelSet modelSet, EObject context) {
		// if context is the state machine, there is no reference. But there is one for the state (its "parent" state machine or state)
		if (context instanceof State) {
			// retrieve "reference"
			Region container = ((State) context).getContainer();
			if (container != null) {
				Element owner = container.getOwner();
				// retrieve associated diagram
				List<Diagram> diagrams = DiagramUtils.getAssociatedDiagrams(owner, modelSet);
				// there should be one => the view prototype should have same kind
				if (diagrams != null && !diagrams.isEmpty()) {
					Diagram diagram = diagrams.get(0);
					ViewPrototype prototype = DiagramUtils.getPrototype(diagram);
					if (prototype != null) {
						return prototype;
					}
				}
			}
		}

		// by default, return basic UML-RT state machine kind
		Collection<ViewPrototype> prototypes = PolicyChecker.getFor(context).getPrototypesFor(context);
		for (ViewPrototype prototype : prototypes) {
			RepresentationKind kind = prototype.getRepresentationKind();
			if ((kind != null) && StateMachineUtils.UMLRT_STATE_MACHINE_DIAGRAM.equals(kind.getName())) {
				return prototype;
			}
		}
		return null;
	}

	/**
	 * Obtains the State Machine Diagram for a state machine.
	 * 
	 * @param stateMachine
	 *            a state machine
	 * 
	 * @return its State Machine Diagram, or {@code null} if none is currently known (perhaps
	 *         because it is in a resource that is not yet loaded)
	 */
	public static Diagram getStateMachineDiagram(StateMachine stateMachine) {
		return getDiagram(stateMachine, UMLRTStateMachineDiagramUtils::isRTStateMachineDiagram);
	}

	/**
	 * Obtains the (nested) State Machine Diagram for a state.
	 * 
	 * @param state
	 *            a state
	 * 
	 * @return its State Machine Diagram, or {@code null} if none is currently known (perhaps
	 *         because it is in a resource that is not yet loaded)
	 */
	public static Diagram getStateMachineDiagram(State state) {
		return getDiagram(state, UMLRTStateMachineDiagramUtils::isRTStateMachineDiagram);
	}

}
