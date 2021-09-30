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
 *  Christian W. Damus - bug 517466
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.constraints;

import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Element;

/**
 * constraint that checks that the element is the root Package of the UML-RT model (e.g. has no container).
 */
public class IsRootPackageConstraint extends AbstractConstraint {

	/**
	 * Constructor.
	 */
	public IsRootPackageConstraint() {
		// Empty constructor
	}

	@Override
	public boolean match(Object selection) {
		Element element = UMLUtil.resolveUMLElement(selection);
		if (element instanceof org.eclipse.uml2.uml.Package) {
			org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) element;
			return (package_.getOwner() == null) && UMLRTProfileUtils.isUMLRTProfileApplied(package_);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("IsRootPackageConstraint %s", getDisplayUnit().getElementMultiplicity() == 1 ? "Single" : "Multiple");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean equivalent(Constraint constraint) {
		if (this == constraint) {
			return true;
		}
		if (constraint == null) {
			return false;
		}
		if (!(constraint instanceof IsRootPackageConstraint)) {
			return false;
		}
		return true;
	}

}
