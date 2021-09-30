/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.junit.utils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DecorationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Utility class for GMF-based diagrams.
 */
public final class DiagramUtils {

	/**
	 * Constructor.
	 */
	private DiagramUtils() {
		// no instanciation.
	}

	/**
	 * Returns a child view identified by its eclass or its type.
	 * 
	 * @param containerView
	 *            container view in which to look for children
	 * @param childEClass
	 *            the EClass of the view, or <code>null</code> if we don't care about
	 * @param type
	 *            the visual ID of the view
	 * @return the view found, or <code>null</code> if none was found.
	 */
	public static View getChildView(View containerView, EClass childEClass, String type) {
		for (Object o : containerView.getChildren()) {
			View view = (View) o;
			if ((childEClass != null && childEClass.isSuperTypeOf(view.eClass())) || childEClass == null) {
				if (type.equals(view.getType())) {
					return view;
				}
			}
		}
		return null;
	}

	public static AbstractDecorator getDecorator(IGraphicalEditPart editPart, String decorationKey) throws Exception {
		assertThat(editPart, notNullValue());

		// get the decoration edit policy and get decorators from this edit policy
		EditPolicy editPolicy = editPart.getEditPolicy(EditPolicyRoles.DECORATION_ROLE);
		assertThat(editPolicy, notNullValue());
		assertThat(editPolicy, instanceOf(DecorationEditPolicy.class));
		DecorationEditPolicy decorationEditPolicy = (DecorationEditPolicy) editPolicy;

		Field decoratorField = DecorationEditPolicy.class.getDeclaredField("decorators");
		decoratorField.setAccessible(true);
		Map<?, ?> decorators = (Map<?, ?>) decoratorField.get(decorationEditPolicy);
		assertThat(decorators, notNullValue());

		Object decorator = decorators.get(decorationKey);
		assertThat(decorator, notNullValue());
		assertThat(decorator, instanceOf(AbstractDecorator.class));
		return (AbstractDecorator) decorator;
	}
}
