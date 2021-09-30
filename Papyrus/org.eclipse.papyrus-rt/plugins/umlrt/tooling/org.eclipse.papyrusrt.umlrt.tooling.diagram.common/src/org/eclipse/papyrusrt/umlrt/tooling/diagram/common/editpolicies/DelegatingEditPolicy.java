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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

/**
 * An edit-policy that forwards all of the API to a {@linkplain #getDelegate() delegate}.
 * This is useful for wrapping edit-policies to intercept and modify interactions
 * (the <em>Decorator</em> pattern).
 */
public class DelegatingEditPolicy extends AbstractEditPolicy {
	private EditPolicy delegate;

	public DelegatingEditPolicy(EditPolicy delegate) {
		super();

		this.delegate = delegate;
	}

	/**
	 * Obtains my delegate.
	 * 
	 * @return the delegate
	 */
	protected final EditPolicy getDelegate() {
		return delegate;
	}

	//
	// Delegation
	//

	@Override
	public void activate() {
		delegate.activate();
	}

	@Override
	public void deactivate() {
		delegate.deactivate();
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		delegate.eraseSourceFeedback(request);
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		delegate.eraseTargetFeedback(request);
	}

	@Override
	public Command getCommand(Request request) {
		return delegate.getCommand(request);
	}

	@Override
	public EditPart getHost() {
		return delegate.getHost();
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		return delegate.getTargetEditPart(request);
	}

	@Override
	public void setHost(EditPart editpart) {
		delegate.setHost(editpart);
	}

	@Override
	public void showSourceFeedback(Request request) {
		delegate.showSourceFeedback(request);
	}

	@Override
	public void showTargetFeedback(Request request) {
		delegate.showTargetFeedback(request);
	}

	@Override
	public boolean understandsRequest(Request request) {
		return delegate.understandsRequest(request);
	}

}
