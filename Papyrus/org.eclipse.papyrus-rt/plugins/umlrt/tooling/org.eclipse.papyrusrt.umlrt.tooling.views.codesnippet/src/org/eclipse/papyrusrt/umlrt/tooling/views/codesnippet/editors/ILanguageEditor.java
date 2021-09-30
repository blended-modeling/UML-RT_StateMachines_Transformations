/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.editors;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.swt.widgets.Composite;

/**
 * Interface for language editor.
 * 
 * @author ysroh
 *
 */
public interface ILanguageEditor extends IObservableValue<Expression> {

	/**
	 * Create editor composite.
	 * 
	 * @param parent
	 *            container
	 * @return Composite.
	 */
	Composite createControl(Composite parent);

	/**
	 * dispose editor.
	 */
	@Override
	void dispose();

}
