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

package org.eclipse.papyrusrt.umlrt.tooling.tables.configurations;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Cell editor configuration for the trigger guard column.
 */
public class TriggerGuardCellEditorConfiguration extends AbstractTriggerTableCellEditorConfiguration {
	/**
	 * The ID of this editor.
	 */
	public static final String ID = "org.eclipse.papyrusrt.tooling.tables.configuration.TriggerGuardCellEditor"; //$NON-NLS-1$

	public TriggerGuardCellEditorConfiguration() {
		super(ID, "Cell editor configuration for trigger guard conditions", UMLPackage.Literals.TRANSITION__GUARD);
	}

	@Override
	protected Image getImage(UMLRTTrigger trigger) throws ServiceException {
		Image result = null;

		UMLRTGuard guard = (trigger == null) ? null : trigger.getGuard();
		if (guard != null) {
			LabelProviderService labels = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, guard.toUML());
			result = labels.getLabelProvider(guard.toUML()).getImage(guard.toUML());
		}

		return result;
	}

}
