/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesFactory;

/**
 * Factory for the UML-RT new element configurator advice.
 */
public class UMLRTNewElementConfiguratorAdviceFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {

	/**
	 * Initializes me.
	 */
	public UMLRTNewElementConfiguratorAdviceFactory() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IEditHelperAdvice getEditHelperAdvice(AbstractAdviceBindingConfiguration adviceConfiguration) {
		IEditHelperAdvice result = NullEditHelperAdvice.getInstance();

		if (adviceConfiguration instanceof UMLRTNewElementConfigurator) {
			result = new UMLRTNewElementConfiguratorAdvice((UMLRTNewElementConfigurator) adviceConfiguration);
		}

		return result;
	}

	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return UMLRTTypesFactory.eINSTANCE.createUMLRTNewElementConfigurator();
	}
}
