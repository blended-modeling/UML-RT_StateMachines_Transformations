/*******************************************************************************
 * Copyright (c) 2016, 2017 Zeligsoft (2009) Limited, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *   Christian W. Damus - bug 514322
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.cell;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.manager.cell.UMLFeatureCellManager;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Trigger port cell manager.
 * 
 * @author ysroh
 *
 */
public class TriggerPortCellManager extends UMLFeatureCellManager {

	@Override
	public boolean handles(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		boolean result = super.handles(columnElement, rowElement, tableManager);

		if (result) {
			final Object row = AxisUtils.getRepresentedElement(rowElement);
			Object column = AxisUtils.getRepresentedElement(columnElement);
			if (!(row instanceof Trigger) || !UMLPackage.Literals.TRIGGER__PORT.equals(column)) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean isCellEditable(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return false;
	}

}
