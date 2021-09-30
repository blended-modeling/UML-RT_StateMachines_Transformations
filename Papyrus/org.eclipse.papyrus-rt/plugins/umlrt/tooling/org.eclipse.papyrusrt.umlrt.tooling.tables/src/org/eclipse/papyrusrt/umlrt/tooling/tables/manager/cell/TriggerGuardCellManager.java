/*******************************************************************************
 * Copyright (c) 2016, 2017 Zeligsoft (2009) Limited, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 514322
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.cell;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Trigger guard cell manager.
 * 
 * @author ysroh
 *
 */
public class TriggerGuardCellManager extends AbstractCellManager {

	@Override
	public boolean handles(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		final Object row = AxisUtils.getRepresentedElement(rowElement);
		final Object column = AxisUtils.getRepresentedElement(columnElement);
		if (row instanceof Trigger && UMLPackage.Literals.TRANSITION__GUARD.equals(column)) {
			return true;
		}
		return false;
	}

	@Override
	protected Object doGetValue(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		Trigger trigger = (Trigger) AxisUtils.getRepresentedElement(rowElement);
		return getTriggerGuard(trigger);
	}

	/**
	 * Get or create trigger guard.
	 * 
	 * @param rowElement
	 *            row element
	 * @param createOnDemand
	 *            create if true
	 * @return trigger guard
	 */
	private String getTriggerGuard(Trigger trigger) {
		UMLRTTrigger rtTrigger = UMLRTTrigger.getInstance(trigger);
		UMLRTGuard guard = rtTrigger.getGuard();
		if (guard != null) {
			IDefaultLanguage language = ModelUtils.getDefaultLanguage(trigger);
			if (language != null && guard.getBodies().containsKey(language.getName())) {
				return guard.getBodies().get(language.getName());
			}
		}
		return "";
	}

	@Override
	public void setValue(TransactionalEditingDomain domain, Object columnElement, Object rowElement, Object newValue, INattableModelManager tableManager) {
		// Trigger guard cell editor not supported
	}
}
