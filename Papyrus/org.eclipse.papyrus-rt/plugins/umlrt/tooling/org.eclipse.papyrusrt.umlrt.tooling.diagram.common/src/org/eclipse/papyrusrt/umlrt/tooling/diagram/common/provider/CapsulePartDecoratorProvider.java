/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.decorator.CapsulePartDecorator;
import org.eclipse.uml2.uml.Property;

/**
 * Provider of the Capsule Part Decoration.
 * 
 * @author Celine JANSSENS
 *
 */
public class CapsulePartDecoratorProvider extends AbstractProvider implements IDecoratorProvider {

	private static final String GMF_DECORATION_KEY = "CapsulePartDecoration"; //$NON-NLS-1$

	@Override
	public boolean provides(IOperation operation) {
		boolean provide = false;

		EObject referenceElement = ((CreateDecoratorsOperation) operation).getDecoratorTarget().getAdapter(EObject.class);

		// Test Element should be a CapsulePart
		if ((referenceElement instanceof Property) && (CapsulePartUtils.isCapsulePart((Property) referenceElement))) {

			provide = true;

		} else {
			provide = false;
		}

		return provide;
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		final View node = decoratorTarget.getAdapter(View.class);
		if (node != null) {
			// Install the decorator
			decoratorTarget.installDecorator(GMF_DECORATION_KEY, new CapsulePartDecorator(decoratorTarget));
		}

	}

}
