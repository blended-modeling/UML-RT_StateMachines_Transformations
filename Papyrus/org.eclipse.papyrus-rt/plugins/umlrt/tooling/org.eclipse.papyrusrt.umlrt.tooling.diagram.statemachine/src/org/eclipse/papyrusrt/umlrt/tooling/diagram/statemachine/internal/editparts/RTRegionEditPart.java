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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils.isTypeCompatible;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomRegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML-RT implementation of the edit-part for inheritable {@link Region}s in state machines.
 */
public class RTRegionEditPart extends CustomRegionEditPart implements IStateMachineInheritableEditPart {

	private Adapter redefinitionAdapter;

	/**
	 * Initializes me with my notation {@code view}.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTRegionEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTSemanticEditPolicy());
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	public boolean isSemanticInherited() {
		// Regions have no realization in the façade model apart from
		// their containing state or state machine
		EObject semantic = resolveSemanticElement();
		return (semantic instanceof Region) && UMLRTExtensionUtil.isInherited((Region) semantic);
	}

	/**
	 * As a region has no specific realization in the façade model apart from
	 * its containing state or state machine, use that as its UML-RT element.
	 *
	 * @return the façade of my region's containing state or state machine
	 */
	@Override
	public Optional<UMLRTNamedElement> getUMLRTElement() {
		Optional<UMLRTNamedElement> result = Optional.empty();

		EObject semantic = resolveSemanticElement();
		if (semantic instanceof Region) {
			Region region = (Region) semantic;
			result = Optional.ofNullable(Either.or(region.getStateMachine(), region.getState())
					.orElse(NamedElement.class, null))
					.map(UMLRTFactory::create);
		}

		return result;
	}

	@Override
	public UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		// My containing state machine or state is the redefinition context
		// for my diagram because it is the diagram's root element. So, likewise
		// for any ancestor definition that it (recursively) redefines
		Predicate<UMLRTNamedElement> same = element::equals;
		Predicate<UMLRTNamedElement> redefinition = e -> e.redefines(element);

		return getUMLRTElement() // See note on getUMLRTElement() method
				.filter(same.or(redefinition))
				.map(__ -> element)
				.orElseGet(() -> EditPartInheritanceUtils.getStateMachineContext(element).getEither(UMLRTNamedElement.class));
	}

	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();

		Region region = TypeUtils.as(resolveSemanticElement(), Region.class);
		if ((region != null) && (region.getExtendedRegion() == null)) {
			// Listen in case it should become a redefinition
			region.eAdapters().add(getRedefinitionAdapter());
		}
	}

	/**
	 * Obtain an adapter that reacts to changes in the redefinition relationship of
	 * my semantic region element to update my view to reference the (possibly different)
	 * root definition of that region.
	 * 
	 * @return my redefinition adapter
	 */
	private Adapter getRedefinitionAdapter() {
		if (redefinitionAdapter == null) {
			redefinitionAdapter = new AdapterImpl() {
				@Override
				public void notifyChanged(Notification msg) {
					if (!msg.isTouch() && (msg.getFeature() == UMLPackage.Literals.REGION__EXTENDED_REGION)) {
						switch (msg.getEventType()) {
						case Notification.SET:
						case Notification.UNSET:
							Region region = TypeUtils.as(resolveSemanticElement(), Region.class);
							Region root = (region == null) ? region : UMLRTExtensionUtil.getRootDefinition(region);
							if (root != null) {
								View view = getNotationView();
								if ((view != null) && (view.getElement() != root)) {
									view.setElement(root);
								}
							}
							break;
						}
					}
				}
			};
		}

		return redefinitionAdapter;
	}

	@Override
	protected void removeSemanticListeners() {
		if (redefinitionAdapter != null) {
			Notifier target = redefinitionAdapter.getTarget();
			if (target != null) {
				target.eAdapters().remove(redefinitionAdapter);
			}
			redefinitionAdapter = null;
		}
		super.removeSemanticListeners();
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		EditPartInheritanceUtils.handleNotificationEvent(this, notification,
				super::handleNotificationEvent);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		EditPart result;

		if (request instanceof CreateViewAndElementRequest) {
			Optional<IElementType> elementType = Optional.of(request)
					.map(CreateViewAndElementRequest.class::cast)
					.map(CreateViewAndElementRequest::getViewAndElementDescriptor)
					.map(ViewAndElementDescriptor::getElementAdapter)
					.map(a -> a.getAdapter(IElementType.class));

			result = elementType.<EditPart> map(type ->
			// Create connection points and internal transitions in our containing state
			(isTypeCompatible(type, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT)
					|| isTypeCompatible(type, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT)
					|| isTypeCompatible(type, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL))
							? TypeUtils.as(getParent().getParent(), RTStateEditPartTN.class)
							// Create other pseudostates and actual states in our compartment
							: (isTypeCompatible(type, UMLElementTypes.STATE) || isTypeCompatible(type, UMLElementTypes.PSEUDOSTATE))
									? getChildBySemanticHint(RegionCompartmentEditPart.VISUAL_ID)
									: null)
					.orElseGet(() -> super.getTargetEditPart(request));
		} else {
			result = super.getTargetEditPart(request);
		}

		return result;
	}
}
