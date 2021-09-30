/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import java.lang.reflect.Proxy;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalReferenceEditPolicy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A customization of the external reference edit-policy that understands
 * inherited elements.
 */
public class RTExternalReferenceEditPolicy extends ExternalReferenceEditPolicy {

	public RTExternalReferenceEditPolicy() {
		super();
	}

	@Override
	protected boolean isExternalRef(View view) {
		return super.isExternalRef(inheritableView(view));
	}

	EObject resolveElement(View view) {
		EObject result = view.getElement();
		if (result instanceof NamedElement) {
			UMLRTNamedElement rt = UMLRTFactory.create((NamedElement) result);
			if (rt != null) {
				EObject context = (view.eContainer() instanceof View)
						? ((View) view.eContainer()).getElement()
						: null;
				if (context instanceof NamedElement) {
					UMLRTNamedElement rtContext = UMLRTFactory.create((NamedElement) context);
					if (rtContext instanceof UMLRTCapsulePart) {
						rtContext = ((UMLRTCapsulePart) rtContext).getType();
					}

					if (rtContext instanceof UMLRTCapsule) {
						UMLRTCapsule capsule = (UMLRTCapsule) rtContext;

						// Find the redefining port or part
						if (rt instanceof UMLRTPort) {
							result = capsule.getPorts().stream()
									.filter(p -> p.redefines(rt))
									.findAny()
									.map(UMLRTNamedElement::toUML)
									.orElse(rt.toUML());
						} else if (rt instanceof UMLRTCapsulePart) {
							result = capsule.getCapsuleParts().stream()
									.filter(p -> p.redefines(rt))
									.findAny()
									.map(UMLRTNamedElement::toUML)
									.orElse(rt.toUML());
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Create a dynamic proxy of a {@code view} that presents the correct resolved
	 * semantic element as its element.
	 * 
	 * @param view
	 *            a view
	 * @return the inheritable view proxy
	 */
	protected View inheritableView(View view) {
		return (view == null)
				? null : (View) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] { View.class },
						(proxy, method, args) -> {
							switch (method.getName()) {
							case "getElement":
								return resolveElement(view);
							case "eContainer":
								EObject eContainer = view.eContainer();
								return (eContainer instanceof View)
										? inheritableView((View) eContainer)
										: eContainer;
							default:
								return method.invoke(view, args);
							}
						});
	}
}
