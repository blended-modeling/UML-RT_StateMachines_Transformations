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

import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Cell editor configuration for the trigger name column.
 */
public class TriggerNameCellEditorConfiguration extends AbstractTriggerTableCellEditorConfiguration {
	/**
	 * The ID of this editor.
	 */
	public static final String ID = "org.eclipse.papyrusrt.tooling.tables.configuration.TriggerNameCellEditor"; //$NON-NLS-1$

	public TriggerNameCellEditorConfiguration() {
		super(ID, "Cell editor configuration for trigger names", UMLPackage.Literals.NAMED_ELEMENT__NAME);
	}

	@Override
	protected Image getImage(UMLRTTrigger trigger) throws ServiceException {
		Image result = null;

		LabelProviderService labels = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, trigger.toUML());
		result = labels.getLabelProvider(trigger).getImage(trigger);

		return result;
	}

	@Override
	protected ICellPainter getCellPainter(Table table, INattableModelManager manager, Object axis) {
		// Don't need the text part
		return getImageCellPainter(table, manager, axis);
	}

}
