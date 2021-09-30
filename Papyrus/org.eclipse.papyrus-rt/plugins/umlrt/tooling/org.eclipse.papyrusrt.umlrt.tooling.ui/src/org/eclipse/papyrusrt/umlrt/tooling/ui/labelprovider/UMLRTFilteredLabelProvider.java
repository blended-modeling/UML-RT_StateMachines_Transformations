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

package org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.CallEventUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.InterfaceRealizationUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UsageUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider.LabelUtils;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Usage;

/**
 * Label provider used by the label provider service.
 */
public class UMLRTFilteredLabelProvider extends UMLRTLabelProvider {

	/**
	 * Constructor.
	 *
	 */
	public UMLRTFilteredLabelProvider() {
		super();
	}

	/**
	 * Handle structured selections, as in the Tabbed Properties View.
	 * 
	 * @param selection
	 *            selection possibly containing a model element
	 * 
	 * @return whether this label provider handles the element in the {@code selection}
	 */
	@Override
	public boolean accept(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return false;
		}

		for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			if (!accept(iterator.next())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean accept(Object element) {
		if (element instanceof IStructuredSelection) {
			return accept((IStructuredSelection) element);
		}

		EObject semanticObject = EMFHelper.getEObject(element);

		// element should be an UML element at least. UML-RT profile should be present.
		if (!(semanticObject instanceof Element)) {
			return false;
		}

		for (IElementType type : UMLRTElementTypesEnumerator.getAllRTTypes()) {
			if (type instanceof ISpecializationType) {
				IElementMatcher matcher = ((ISpecializationType) type).getMatcher();
				if (matcher != null) {
					if (matcher.matches(semanticObject)) {
						return true;
					}
				} else {
					Activator.log.debug("no matcher for this element type: " + type);
				}

			}
		}

		if (semanticObject instanceof CallEvent) {
			if (CallEventUtils.isProtocolMessageCallEvent((CallEvent) semanticObject)) {
				return true;
			}
		}

		if (semanticObject instanceof Interface) {
			if (MessageSetUtils.isRTMessageSet((Interface) semanticObject)) {
				return true;
			}
		}

		if (semanticObject instanceof InterfaceRealization) {
			if (InterfaceRealizationUtils.isInterfaceRealizationFromProtocol((InterfaceRealization) semanticObject)) {
				return true;
			}
		}

		if (semanticObject instanceof Usage) {
			if (UsageUtils.isUsageFromProtocol((Usage) semanticObject)) {
				return true;
			}
		}

		if (semanticObject instanceof AnyReceiveEvent) {
			return true;
		}

		if (semanticObject instanceof Trigger) {
			Event event = ((Trigger) semanticObject).getEvent();
			return (event instanceof AnyReceiveEvent ||
					(event instanceof CallEvent && CallEventUtils.isProtocolMessageCallEvent((CallEvent) event)));
		}

		if (semanticObject instanceof Transition) {
			return true;
		}

		if (semanticObject instanceof Constraint) {
			// Is it a transition or a trigger guard?
			return accept(((Constraint) semanticObject).getContext());
		}

		if (semanticObject instanceof OpaqueBehavior) {
			// Is it a transition effect or state entry/exit?
			return accept(((OpaqueBehavior) semanticObject).getOwner());
		}

		return false;
	}

	@Override
	public Image doGetImage(EObject element) {
		if (!(element instanceof Element)) {
			Activator.log.debug("Trying to display an UMLRT image for a non UML-RT element");
			return null;
		}

		// depending on the element type that matches, return a different icon
		String matchingTypeMatcher = getMatchingType(element);

		if (matchingTypeMatcher == null) {
			if ((element instanceof Constraint) || (element instanceof OpaqueBehavior)) {
				// Look for a façade object because these have custom iconogaphy
				FacadeObject facade = UMLRTFactory.create(element);
				if (facade != null) {
					return baseEMFLabelProvider.getImage(facade);
				}
			}

			// check for UML related elements, like CallEvents
			if (element instanceof Element) {
				return baseEMFLabelProvider.getImage(element);
			}

			// Give up
			return null;
		}

		return super.doGetImage(element);
	}

	@Override
	public String getText(EObject element) {
		// specific for RT Message label
		ISpecializationType inMessageType = (ISpecializationType) UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN;
		if (inMessageType != null && inMessageType.getMatcher() != null && inMessageType.getMatcher().matches(element)) {
			return "in " + super.getText(element);
		}
		ISpecializationType outMessageType = (ISpecializationType) UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_OUT;
		if (outMessageType != null && outMessageType.getMatcher() != null && outMessageType.getMatcher().matches(element)) {
			return "out " + super.getText(element);
		}
		ISpecializationType inOutMessageType = (ISpecializationType) UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_INOUT;
		if (inOutMessageType != null && inOutMessageType.getMatcher() != null && inOutMessageType.getMatcher().matches(element)) {
			return "inout " + super.getText(element);
		}

		/* @noname */
		// for unnamed elements (protocol container, callevents for ProtocolMessages), deduce the name from attached element
		if (element instanceof CallEvent) {
			if (CallEventUtils.isProtocolMessageCallEvent((CallEvent) element)) {
				return LabelUtils.getCallEventForProtocolMessageLabel((CallEvent) element, labelProvider);
			}
		}

		if (element instanceof AnyReceiveEvent) {
			return labelProvider.getText(element);
		}

		if (element instanceof Package) {
			if (ProtocolContainerUtils.isProtocolContainer(element)) {
				return LabelUtils.getProtocolContainerLabel((Package) element, labelProvider);
			}
		}

		if (element instanceof Interface) {
			if (MessageSetUtils.isRTMessageSet((Interface) element)) {
				return LabelUtils.getMessageSetLabel((Interface) element, labelProvider);
			}
		}

		if (element instanceof InterfaceRealization) {
			if (InterfaceRealizationUtils.isInterfaceRealizationFromProtocol((InterfaceRealization) element)) {
				return LabelUtils.getInterfaceRealizationLabel((InterfaceRealization) element, labelProvider);
			}
		}

		if (element instanceof Usage) {
			if (UsageUtils.isUsageFromProtocol((Usage) element)) {
				return LabelUtils.getUsageLabel((Usage) element, labelProvider);
			}
		}

		if (element instanceof Transition) {
			return LabelUtils.getTransitionLabel((Transition) element, labelProvider);
		}

		if (element instanceof Trigger) {
			return LabelUtils.getEventLabel(((Trigger) element).getEvent(), labelProvider);
		}

		return super.getText(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getToolTipText(Object element, ETypedElement eTypedElement) {
		if (element instanceof EObject && TransitionUtils.isInternalTransition((EObject) element)) {
			return LabelUtils.getInternalTransitionTooltip((Transition) element, labelProvider);
		}
		return super.getToolTipText(element, eTypedElement);
	}

}
