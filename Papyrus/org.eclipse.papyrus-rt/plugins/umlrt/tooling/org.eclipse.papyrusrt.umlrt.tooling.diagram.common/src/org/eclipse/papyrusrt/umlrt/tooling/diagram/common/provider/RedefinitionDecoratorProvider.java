/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.decorator.RedefinitionDecorator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortOnPartEditPart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Provider of redefinition/inheritance decorators in the diagrams.
 */
public class RedefinitionDecoratorProvider extends AbstractProvider implements IDecoratorProvider {

	private static final String DECORATION_KEY = "umlrt.redefinition"; //$NON-NLS-1$

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		IDecoratorTarget target = ((CreateDecoratorsOperation) operation).getDecoratorTarget();
		EditPart editPart = target.getAdapter(EditPart.class);

		// Don't show the decorations on ports on parts, which are just too small
		if ((editPart instanceof IInheritableEditPart)
				&& !(editPart instanceof RTPortOnPartEditPart)
				&& !((IInheritableEditPart) editPart).isDependentChild()) {

			IGraphicalEditPart graphical = (IGraphicalEditPart) editPart;
			EObject semantic = graphical.resolveSemanticElement();

			// Only things that the façade API recognizes can be inherited/redefined
			if (semantic instanceof NamedElement) {
				UMLRTNamedElement element = UMLRTFactory.create((NamedElement) semantic);
				result = element != null;
			}
		}

		return result;
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		final View node = decoratorTarget.getAdapter(View.class);

		if (node != null) {
			// Install the decorator
			decoratorTarget.installDecorator(DECORATION_KEY, new RedefinitionDecorator(decoratorTarget));
		}
	}

}
