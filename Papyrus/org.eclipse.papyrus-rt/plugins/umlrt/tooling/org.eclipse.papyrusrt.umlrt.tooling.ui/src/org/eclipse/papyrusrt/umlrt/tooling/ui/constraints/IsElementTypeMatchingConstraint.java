/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.constraints;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IMetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;

/**
 * constraint that checks that the element matches a specific element type
 */
public class IsElementTypeMatchingConstraint extends AbstractConstraint {

	private String elementTypeID;

	@Override
	public void setDescriptor(SimpleConstraint descriptor) {
		elementTypeID = getValue("elementTypeID"); //$NON-NLS-1$
	}

	@Override
	public boolean match(Object selection) {
		EObject objectSemantic = UMLUtil.resolveUMLElement(selection);
		if (objectSemantic != null) {
			IElementType type = ElementTypeRegistry.getInstance().getType(elementTypeID);
			if (type != null) {
				if (type instanceof ISpecializationType) {
					IElementMatcher matcher = ((ISpecializationType) type).getMatcher();
					if (matcher != null) {
						return matcher.matches(objectSemantic);
					}
				} else if (type instanceof IMetamodelType) {
					EClass metamodelClass = ((IMetamodelType) type).getEClass();
					if (metamodelClass != null) {
						return metamodelClass.isSuperTypeOf(objectSemantic.eClass());
					}
				}
			}

		}
		return false;
	}

	/**
	 * A class constraint overrides its superclass constraints
	 * e.g. : instanceOf(Class) overrides instanceOf(Classifier)
	 */
	@Override
	public boolean overrides(Constraint constraint) {
		if (!(constraint instanceof IsElementTypeMatchingConstraint)) {
			return false;
		}

		IsElementTypeMatchingConstraint elementTypeConstraint = (IsElementTypeMatchingConstraint) constraint;
		boolean result = (!getElementTypeID().equals(elementTypeConstraint.getElementTypeID())) /* && UMLUtil.isSubClass(umlClassName, umlConstraint.umlClassName); check super element types */ ;

		return result || super.overrides(constraint);
	}

	@Override
	public String toString() {
		return String.format("IsElementTypeMatchingConstraint %s (%s)", getElementTypeID(), getDisplayUnit().getElementMultiplicity() == 1 ? "Single" : "Multiple");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	protected boolean equivalent(Constraint constraint) {
		if (this == constraint) {
			return true;
		}
		if (constraint == null) {
			return false;
		}
		if (!(constraint instanceof IsElementTypeMatchingConstraint)) {
			return false;
		}
		IsElementTypeMatchingConstraint other = (IsElementTypeMatchingConstraint) constraint;
		if (getElementTypeID() == null) {
			if (other.getElementTypeID() != null) {
				return false;
			}
		} else if (!getElementTypeID().equals(other.getElementTypeID())) {
			return false;
		}
		return true;
	}

	/**
	 * @return the identifier of the element type
	 */
	public String getElementTypeID() {
		return elementTypeID;
	}
}
