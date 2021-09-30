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
 * Code snippet tab for state entry.
 * 
 * @author ysroh
 *
 */
public class StateEntryTab extends AbstractCodeSnippetTab {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public StateEntryTab() {
	}

	@Override
	public String getTitle() {
		return "Entry";
	}

	@Override
	protected void doSetInput(EObject input) {
		State s = CodeSnippetTabUtil.getState(input);
		if (s != null && s.getEntry() == input) {
			// select the entry tab by default when user selected entry opaque
			// behaviour directly.
			defaultSelection = true;
		}
		this.input = s;
	}

	@Override
	protected Image getImage() {
		State state = (State) input;
		Behavior entry = state.getEntry();
		if (entry != null) {
			return getLabelProvider().getImage(entry);
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
		return meForFeatureObservable.getObservable("entry");
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		State state = (State) input;
		return getExpressionList(state.getEntry());
	}

	@Override
	protected void commit(Expression expr) {
		UMLRTState state = UMLRTFactory.createState((State) input);
		UMLRTOpaqueBehavior entry = state.getEntry();
		if (entry == null) {
			entry = state.createEntry(expr.getLanguage(), expr.getBody());
			entry.setName(getTitle());
		} else {
			entry.getBodies().put(expr.getLanguage(), expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		return getSpecificationObservable(((State) input).getEntry());
	}
}
