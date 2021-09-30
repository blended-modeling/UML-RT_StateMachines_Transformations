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

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * Abstract language editor for code snippet view. This class observes
 * {@link Expression} which is language and body pair.
 * </p>
 * 
 * @author ysroh
 *
 */
public abstract class AbstractLanguageEditor extends AbstractObservableValue<Expression> implements ILanguageEditor {

	/**
	 * Expression for language and body pair.
	 */
	protected Expression expression;

	/**
	 * Main composite.
	 */
	private Composite composite;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public AbstractLanguageEditor() {
		super(Realm.getDefault());
	}

	@Override
	public Composite createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout());
		createContents(composite);

		return composite;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (composite != null) {
			composite.dispose();
			composite = null;
		}
	}

	@Override
	protected void doSetValue(Expression value) {
		this.expression = value;
		SafeRunnable runnable = new SafeRunnable() {

			@Override
			public void run() throws Exception {
				refresh();
			}
		};
		SafeRunnable.run(runnable);
	}

	@Override
	public boolean isStale() {
		return false;
	}

	@Override
	public Object getValueType() {
		return Expression.class;
	}

	@Override
	protected Expression doGetValue() {
		return expression;
	}

	/**
	 * Create contents.
	 * 
	 * @param parent
	 *            container
	 */
	protected abstract void createContents(Composite parent);

	/**
	 * Refresh content.
	 */
	protected abstract void refresh();

}
