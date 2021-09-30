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

package org.eclipse.papyrusrt.umlrt.tooling.ui.util;

import java.util.Comparator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * A comparator that sorts elements by inheritance kind in the preferred order for
 * presentation in the UI. This is more specific than the ordering provided by
 * the {@link UMLRTInheritanceKind} API because it accounts for special cases such
 * as the implicit redefinition of a trigger (which is not actually redefinable)
 * by redefinition/overriding of its guard constraint.
 */
public final class InheritanceUIComparator implements Comparator<Object> {
	private final UMLSwitch<UMLRTInheritanceKind> inheritanceKind = new UMLSwitch<UMLRTInheritanceKind>() {
		@Override
		public UMLRTInheritanceKind caseElement(Element element) {
			return UMLRTInheritanceKind.of(element);
		}

		@Override
		public UMLRTInheritanceKind caseTrigger(Trigger trigger) {
			// Triggers may be implicitly inherited, as defined by the façade API
			return facadeCase(trigger);
		}

		protected UMLRTInheritanceKind facadeCase(NamedElement namedElement) {
			UMLRTNamedElement facade = UMLRTFactory.create(namedElement);
			return (facade == null) ? null : facade.getInheritanceKind();
		}

		@Override
		public UMLRTInheritanceKind defaultCase(EObject object) {
			return UMLRTInheritanceKind.NONE;
		}
	};

	private final Comparator<Element> forElements = Comparator.comparing(this::getInheritanceKind)
			.thenComparing(UMLRTInheritanceKind.byInheritanceDepth());

	@Override
	public int compare(Object o1, Object o2) {
		int result = 0;

		EObject e1 = EMFHelper.getEObject(o1);
		EObject e2 = EMFHelper.getEObject(o2);

		// Only UML elements are comparable
		if ((e1 instanceof Element) && (e2 instanceof Element)) {
			result = forElements.compare((Element) e1, (Element) e2);
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj.getClass() == InheritanceUIComparator.class);
	}

	private UMLRTInheritanceKind getInheritanceKind(Element element) {
		return inheritanceKind.doSwitch(element);
	}
}
