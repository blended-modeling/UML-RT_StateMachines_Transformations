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
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.utils.CodeSnippetTabUtil;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Transition;

/**
 * Code snippet tab for transition guard.
 * 
 * @author ysroh
 *
 */
public class TransitionGuardTab extends AbstractCodeSnippetTab {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public TransitionGuardTab() {
	}

	@Override
	public String getTitle() {
		return "Guard";
	}

	@Override
	protected void doSetInput(EObject input) {
		Transition t = CodeSnippetTabUtil.getTransition(input);
		if (t != null && t.getGuard() == input) {
			// select the guard tab by default when user selected guard
			// constraint directly.
			defaultSelection = true;
		}
		this.input = t;
	}

	@Override
	protected Image getImage() {

		Transition transition = (Transition) input;
		Constraint guard = transition.getGuard();
		if (guard != null) {
			return getLabelProvider().getImage(guard);
		}
		return org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator
				.getImage(org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator.IMG_OBJ16_GUARD);
	}

	@Override
	protected IObservable getFeatureObservable() {
		if (meForFeatureObservable == null) {
			meForFeatureObservable = rtModelFactory.createFromSource(input, null);
		}

		return meForFeatureObservable.getObservable("guard");
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		Transition transition = (Transition) input;
		Constraint guard = transition.getGuard();
		if (guard != null) {
			return getExpressionList(guard.getSpecification());
		}

		return null;
	}

	@Override
	protected void commit(Expression expr) {
		UMLRTTransition transition = UMLRTFactory.createTransition((Transition) input);
		UMLRTGuard guard = transition.getGuard();
		if (guard == null) {
			transition.createGuard(expr.getLanguage(), expr.getBody());
		} else {
			guard.getBodies().put(expr.getLanguage(), expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		return getSpecificationObservable(((Transition) input).getGuard());
	}
}
