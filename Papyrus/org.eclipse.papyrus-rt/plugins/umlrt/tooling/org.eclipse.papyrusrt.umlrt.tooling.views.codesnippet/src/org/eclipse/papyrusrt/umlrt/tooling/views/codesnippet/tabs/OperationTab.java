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
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.utils.CodeSnippetTabUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Code snippet tab for operation.
 * 
 * @author ysroh
 *
 */
public class OperationTab extends AbstractCodeSnippetTab {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public OperationTab() {
	}

	@Override
	public String getTitle() {
		return "Operation";
	}

	@Override
	protected void doSetInput(EObject input) {
		this.input = CodeSnippetTabUtil.getOperation(input);
	}

	@Override
	protected Image getImage() {
		Operation op = (Operation) input;
		if (!op.getMethods().isEmpty()) {
			return getLabelProvider().getImage(op.getMethods().get(0));
		}
		return null;
	}

	@Override
	protected IObservable getFeatureObservable() {
		if (meForFeatureObservable == null) {
			meForFeatureObservable = rtModelFactory.createFromSource(input, null);
		}
		return meForFeatureObservable.getObservable("method");
	}

	@Override
	protected ExpressionList getExpressionObservableList() {
		Operation op = (Operation) input;
		if (!op.getMethods().isEmpty()) {
			return getExpressionList(op.getMethods().get(0));
		}
		return null;
	}

	@Override
	protected void commit(Expression expr) {
		Operation op = (Operation) input;
		Class container = (Class) input.eContainer();

		if (op.getMethods().isEmpty()) {
			// create new opaque behaviour
			OpaqueBehavior ob = (OpaqueBehavior) ModelUtils.create(container, UMLPackage.Literals.OPAQUE_BEHAVIOR);
			ob.getBodies().add(expr.getBody());
			ob.getLanguages().add(expr.getLanguage());
			container.getOwnedBehaviors().add(ob);
			op.getMethods().add(ob);
		} else {
			OpaqueBehavior ob = (OpaqueBehavior) op.getMethods().get(0);

			int index = ob.getLanguages().indexOf(expr.getLanguage());
			if (index == -1) {
				// add language if not found
				ob.getLanguages().add(expr.getLanguage());
				index = ob.getLanguages().size() - 1;
			}
			// fill missing bodies with empty string so we always have at least
			// same number of bodies for languages.
			int numFillBody = ob.getLanguages().size() - ob.getBodies().size();
			while (numFillBody-- > 0) {
				ob.getBodies().add("");
			}
			ob.getBodies().set(index, expr.getBody());
		}
	}

	@Override
	protected IObservable getSpecificationObservable() {
		Operation op = (Operation) input;
		if (!op.getMethods().isEmpty()) {
			return getSpecificationObservable(op.getMethods().get(0));
		}
		return null;
	}

}
