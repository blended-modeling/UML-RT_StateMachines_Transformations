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
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;

/**
 * Edit-helper advice that injects parameters into the creation request for a
 * {@link CapsulePart}.
 */
public class CapsulePartCreationParametersAdvice extends AbstractEditHelperAdvice {

	public CapsulePartCreationParametersAdvice() {
		super();
	}

	@Override
	public void configureRequest(IEditCommandRequest request) {
		super.configureRequest(request);

		if (request instanceof ConfigureRequest) {
			// Configuring the configure request.  So meta
			configureRequest((ConfigureRequest) request);
		}
	}

	protected void configureRequest(ConfigureRequest configureRequest) {
		// The creation of a CapsulePart is always cancelable. cf. bug 477821
		ElementTypeUtils.setDialogCancellable(configureRequest, true);
	}
}
