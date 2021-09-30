/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
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
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.parsers;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.uml.diagram.common.parser.NamedElementLabelParser;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Parser for internal transitions.
 */
public class TransitionLabelParser extends NamedElementLabelParser {

	/**
	 * Constructor.
	 */
	public TransitionLabelParser() {
		super();
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		EObject eObject = EMFHelper.getEObject(element);

		if (eObject instanceof NamedElement) {
			try {
				return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, eObject).getLabelProvider().getText(eObject);
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		}

		return super.getPrintString(element, flags);
	}

	@Override
	public List<EObject> getSemanticElementsBeingParsed(EObject element) {
		List<EObject> semantics = super.getSemanticElementsBeingParsed(element);
		if (!(element instanceof Transition)) {
			return semantics;
		}

		List<Trigger> triggers = UMLRTExtensionUtil.getUMLRTContents(element, UMLPackage.Literals.TRANSITION__TRIGGER);
		if (!triggers.isEmpty()) {
			semantics.addAll(triggers);

			Set<CallEvent> callEvents = triggers.stream()
					.map(t -> t.getEvent())
					.filter(CallEvent.class::isInstance)
					.map(CallEvent.class::cast).collect(Collectors.toSet());

			semantics.addAll(callEvents);
			semantics.addAll(callEvents.stream()
					.map(ce -> ce.getOperation())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));

		}
		return semantics;
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return true;
	}

	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		return super.areSemanticElementsAffected(listener, notification);
	}

}
