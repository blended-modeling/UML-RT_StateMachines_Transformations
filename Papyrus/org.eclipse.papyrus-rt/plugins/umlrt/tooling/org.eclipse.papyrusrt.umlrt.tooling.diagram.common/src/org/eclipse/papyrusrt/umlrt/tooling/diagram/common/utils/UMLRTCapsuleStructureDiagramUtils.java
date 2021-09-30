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
 *  Christian W. Damus - bugs 493869, 513067
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;

/**
 * Utility method for capsule structure diagrams
 */
public class UMLRTCapsuleStructureDiagramUtils {

	/**
	 * Returns <code>true</code> if the diagram is a composite capsule structure diagram
	 * 
	 * @param diagram
	 *            the diagram to check
	 * @return <code>true</code> if the diagram is a composite capsule structure diagram
	 */
	public static boolean isCapsuleStructureDiagram(Diagram diagram) {
		if (diagram == null) {
			return false;
		}
		if (CompositeStructureDiagramEditPart.MODEL_ID.equals(diagram.getType())) {
			EObject businessElement = diagram.getElement();
			if (businessElement instanceof Classifier && CapsuleUtils.isCapsule((Classifier) businessElement)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the name to be displayed for a capsule structure diagram, e.g. the name of the associated capsule
	 * 
	 * @param diagram
	 *            the diagram to display
	 * @return the name of the associated capsule or the name of the diagram if no capsule can be found
	 */
	public static String getDisplayedCapsuleStructureDiagramName(Diagram diagram) {
		// check if the diagram has a setted name
		if (diagram.getName() != null && !"".equals(diagram.getName())) {
			return diagram.getName();
		}

		EObject businessElement = diagram.getElement();
		if (businessElement instanceof Classifier && CapsuleUtils.isCapsule((Classifier) businessElement)) {
			return ((Classifier) businessElement).getName();
		}
		return diagram.getName();
	}

	/**
	 * Obtains the Capsule Structure Diagram for a class that may or may not be a capsule.
	 * 
	 * @param capsule
	 *            a possible capsule
	 * 
	 * @return its Capsule Structure Diagram, or {@code null} if none is currently known (perhaps
	 *         because it is in a resource that is not yet loaded)
	 */
	public static Diagram getCapsuleStructureDiagram(Class capsule) {
		return getDiagram(capsule, UMLRTCapsuleStructureDiagramUtils::isCapsuleStructureDiagram);
	}

	/**
	 * Gets the diagram on the given {@code context} matching a particular
	 * {@code filter} condition.
	 * 
	 * @param context
	 *            a diagram context (its {@link View#getElement() element})
	 * @param filter
	 *            the diagram selection criterion, or {@code null} to get any diagram
	 * 
	 * @return the matching diagram, or {@code null} if none
	 */
	public static Diagram getDiagram(EObject context, Predicate<? super Diagram> filter) {
		if (filter == null) {
			filter = __ -> true;
		}

		return EMFHelper.getUsages(context).stream()
				.filter(s -> s.getEObject() instanceof Diagram)
				.filter(s -> s.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
				.map(s -> s.getEObject())
				// Not in an undone command's change description
				.filter(o -> o.eResource() != null)
				.map(Diagram.class::cast)
				.filter(filter)
				.findAny().orElse(null);
	}

}
