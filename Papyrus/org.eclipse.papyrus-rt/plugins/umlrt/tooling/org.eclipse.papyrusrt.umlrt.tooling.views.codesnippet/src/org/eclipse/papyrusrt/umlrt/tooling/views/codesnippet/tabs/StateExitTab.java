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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.State;

/**
 * Code snippet tab for state exit.
 * 
 * @author ysroh
 *
 */
public class StateExitTab extends AbstractCodeSnippetTab {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public StateExitTab() {
	}

	@Override
	public String getTitle() {
		return "Exit";
	}

	@Override
	protected void doSetInput(EObject input) {
		State s = CodeSnippetTabUtil.getState(input);
		if (s != null && s.getExit() == input) {
			// select the ext tab by default when user selected exit opaque
			// behaviour directly.
			defaultSelection = true;
		}
		this.input = s;
	}

	@Override
	protected Image getImage() {
		State state = (State) input;
		Behavior exit = state.getExit();
		if (exit != null) {
			return getLabelProvider().getImage(exit);
		}

		// provide default image.
		return org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator
				.getImage(org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator.IMG_OBJ16_EFFECT);
	}

	@Override
	protected IObservable getFeatureObservable() {
		if (meForFeatureObservable == null) {
			meForFeatureObservable = rtModelFactory.createFromSource(input, null);
		}
		return meForFeatureObservable.getObservable("exit");
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		State state = (State) input;
		return getExpressionList(state.getExit());
	}

	@Override
	protected void commit(Expression expr) {
		UMLRTState state = UMLRTFactory.createState((State) input);
		UMLRTOpaqueBehavior exit = state.getExit();

		if (exit == null) {
			exit = state.createExit(expr.getLanguage(), expr.getBody());
			exit.setName(getTitle());
		} else {
			exit.getBodies().put(expr.getLanguage(), expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		return getSpecificationObservable(((State) input).getExit());
	}
}
