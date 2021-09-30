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

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.papyrus.infra.widgets.databinding.TextObservableValue;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Text language editor.
 * 
 * @author ysroh
 *
 */
public class TextLanguageEditor extends AbstractLanguageEditor {

	/**
	 * Text input widget.
	 */
	protected Text text;

	/**
	 * Text observable.
	 */
	protected IObservable textObservable;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public TextLanguageEditor() {
	}

	@Override
	public void dispose() {
		textObservable.dispose();
		super.dispose();
	}

	@Override
	protected void createContents(Composite parent) {
		text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text.setFont(JFaceResources.getTextFont());
		textObservable = new TextObservableValue(text, null, SWT.FocusOut);
		textObservable.addChangeListener(new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {

				if (text.getText().equals(expression.getBody())) {
					// ignore no change
					return;
				}
				Expression oldEx = new Expression();
				oldEx.setLanguage(expression.getLanguage());
				oldEx.setBody(expression.getBody());

				// update the body value
				expression.setBody(text.getText());

				ValueDiff<Expression> diff = Diffs.createValueDiff(oldEx, expression);

				fireValueChange(diff);
			}
		});

	}

	@Override
	protected void refresh() {
		if (!text.isDisposed()) {
			text.setText(expression.getBody() == null ? "" : expression.getBody());
		}
	}
}
