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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Transition;

/**
 * Code snippet tab for transition effect.
 * 
 * @author ysroh
 *
 */
public class TransitionEffectTab extends AbstractCodeSnippetTab {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public TransitionEffectTab() {
	}

	@Override
	public String getTitle() {
		return "Effect";
	}

	@Override
	protected void doSetInput(EObject input) {
		Transition t = CodeSnippetTabUtil.getTransition(input);
		if (t != null && t.getEffect() == input) {
			// select the effect tab by default when user selected effect opaque
			// behaviour directly.
			defaultSelection = true;
		}
		this.input = t;
	}

	@Override
	protected Image getImage() {
		UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.NONE;

		Transition transition = (Transition) input;
		Behavior effect = transition.getEffect();
		if (effect != null) {
			return getLabelProvider().getImage(effect);
		}
		return org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator.getImage(
				org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator.getEffectSmallKey(inheritance));
	}

	@Override
	protected IObservable getFeatureObservable() {
		if (meForFeatureObservable == null) {
			meForFeatureObservable = rtModelFactory.createFromSource(input, null);
		}
		return meForFeatureObservable.getObservable("effect");
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		Transition transition = (Transition) input;
		return getExpressionList(transition.getEffect());
	}

	@Override
	protected void commit(Expression expr) {
		UMLRTTransition transition = UMLRTFactory.createTransition((Transition) input);
		UMLRTOpaqueBehavior effect = transition.getEffect();
		if (effect == null) {
			effect = transition.createEffect(expr.getLanguage(), expr.getBody());
			effect.setName(getTitle());
		} else {
			effect.getBodies().put(expr.getLanguage(), expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		return getSpecificationObservable(((Transition) input).getEffect());
	}
}
