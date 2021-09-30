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

package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.cell;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.manager.cell.UMLFeatureCellManager;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Cell manager for the {@code NamedElement::name} property.
 */
public class NamedElementNameCellManager extends UMLFeatureCellManager {

	@Override
	public boolean handles(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		boolean result = super.handles(columnElement, rowElement, tableManager);

		if (result) {
			Object row = AxisUtils.getRepresentedElement(rowElement);
			Object column = AxisUtils.getRepresentedElement(columnElement);

			result = (row instanceof NamedElement) && (column == UMLPackage.Literals.NAMED_ELEMENT__NAME);
		}

		return result;
	}

	@Override
	public boolean isCellEditable(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return !UMLRTExtensionUtil.isInherited((NamedElement) rowElement);
	}

	@Override
	protected Object doGetValue(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		Object result;

		Object row = AxisUtils.getRepresentedElement(rowElement);
		if (row instanceof Trigger) {
			// Don't show the name
			result = null;
		} else {
			result = super.doGetValue(columnElement, rowElement, tableManager);
		}

		return result;
	}
}
