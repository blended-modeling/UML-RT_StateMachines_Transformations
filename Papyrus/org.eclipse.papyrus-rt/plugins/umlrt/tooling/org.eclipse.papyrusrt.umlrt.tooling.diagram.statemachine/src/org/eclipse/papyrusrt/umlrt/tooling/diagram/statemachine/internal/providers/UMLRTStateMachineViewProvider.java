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
 *   Christian W. Damus - bug 512994
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.providers;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLViewProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.InternalTransitionsCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;

/**
 * Specific view provider that extends the creation of states as shape.
 * <br/>
 * It will create from the begining the compartment for internal transitions, rather than waiting the defered creation commande with unsafe write
 */
public class UMLRTStateMachineViewProvider extends UMLViewProvider {

	/**
	 * Constructor.
	 */
	public UMLRTStateMachineViewProvider() {
		super();
	}

	@Override
	public Node createState_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = super.createState_Shape(domainElement, containerView, index, persisted, preferencesHint);
		// complete with creation of the internal transition compartment
		createInternalTransitionCompartment(node);
		return node;
	}

	@Override
	public Node createState_Shape_TN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = super.createState_Shape_TN(domainElement, containerView, index, persisted, preferencesHint);
		// complete with creation of the internal transition compartment
		createInternalTransitionCompartment(node);
		return node;
	}

	@Override
	public Node createBehavior_EntryBehaviorLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		// We only ever visualize the root definition. The StateBehaviorsListener (ugh)
		// from Papyrus doesn't understand this
		domainElement = normalizeInheritance(domainElement);

		return createCanonical(domainElement, containerView, EntryStateBehaviorEditPart.VISUAL_ID, Node.class,
				toVisualize -> super.createBehavior_EntryBehaviorLabel(toVisualize, containerView, index, persisted, preferencesHint));
	}

	/**
	 * Normalize the domain element to be visualized according to inheritance semantics.
	 * In practice, this means getting the root definition in the case that it is a
	 * redefinition or an inherited element. Some clients that request view creation
	 * don't understand inheritance and just request the local (re)definition.
	 * 
	 * @param domainElement
	 *            a domain element requested for visualization
	 * 
	 * @return the actual element that should be visualized
	 */
	protected EObject normalizeInheritance(EObject domainElement) {
		EObject result = domainElement;

		if (result instanceof Element) {
			result = UMLRTExtensionUtil.getRootDefinition((Element) domainElement);
		}

		return result;
	}

	/**
	 * Resolves a canonical view in the case that it already exists, to avoid creating
	 * a duplicate that will just have to be deleted (possibly in an unprotected transaction,
	 * risking undo) by the <em>Canonical Edit Policy</em>.
	 * 
	 * @param domainElement
	 *            an element to be visualized
	 * @param containerView
	 *            the parent view in which to visualize it
	 * @param visualID
	 *            the visual ID indicating the kind of visualization
	 * @param viewType
	 *            the expected type of view
	 * @param createNew
	 *            a function that creates the view anew if it does not already exist
	 * 
	 * @return the existing view, or a new view if none exists
	 */
	protected <V extends View> V createCanonical(EObject domainElement, View containerView,
			String visualID, Class<V> viewType,
			Function<? super EObject, ? extends V> createNew) {

		// Look for an existing view
		@SuppressWarnings("unchecked")
		List<? extends View> children = containerView.getChildren();
		return children.stream()
				.filter(v -> visualID.equals(v.getType()))
				.filter(v -> v.getElement() == domainElement)
				.filter(viewType::isInstance).map(viewType::cast)
				.findFirst()
				.orElseGet(() -> createNew.apply(domainElement));
	}

	@Override
	public Node createBehavior_ExitBehaviorLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		// We only ever visualize the root definition. The StateBehaviorsListener (ugh)
		// from Papyrus doesn't understand this
		domainElement = UMLRTExtensionUtil.getRootDefinition((OpaqueBehavior) domainElement);

		return createCanonical(domainElement, containerView, ExitStateBehaviorEditPart.VISUAL_ID, Node.class,
				toVisualize -> super.createBehavior_ExitBehaviorLabel(toVisualize, containerView, index, persisted, preferencesHint));
	}

	/**
	 * @param node
	 *            the state view on which to create the compartment for internal transitions
	 */
	protected void createInternalTransitionCompartment(Node node) {
		Node rv = NotationFactory.eINSTANCE.createCompartment();
		rv.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		rv.setType(InternalTransitionsCompartmentEditPart.VISUAL_ID);
		ViewUtil.insertChildView(node, rv, ViewUtil.APPEND, true);
	}
}
