/*******************************************************************************
 * Copyright (c) 2016, 2017 Zeligsoft (2009) Limited, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *   Christian W. Damus - bug 516817
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.axis;

import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Axis Manager for the Trigger Table in Papyrus RT
 * 
 * @author Young-Soo Roh
 *
 */
public class TriggerAxisManager extends TransactionalSynchronizedOnFeatureAxisManager {

	@Override
	public boolean isAllowedContents(final Object object) {
		boolean content = false;
		// Check if the object is A trigger for transition
		if (object instanceof Trigger && UMLRTProfileUtils.isUMLRTProfileApplied((Trigger) object)) {
			content = super.isAllowedContents(object);
		}

		return content;
	}

	/**
	 * Refresh also when the context transition's owned rules change, because these include
	 * guards for the triggers.
	 */
	@Override
	protected NotificationFilter getRefreshFilter() {
		return NotificationFilter.createFeatureFilter(UMLPackage.Literals.NAMESPACE__OWNED_RULE)
				.and(NotificationFilter.createNotifierFilter(getTableContext()));
	}
}
