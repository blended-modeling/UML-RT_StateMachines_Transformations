/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.factory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.view.factories.ViewFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * It define the contract in order to be able to create a serialization of a figure in the diagram.
 * it is used to allowing reuse code from GMF generated diagram
 */
public interface IBasicViewFactory extends ViewFactory {

	/**
	 * Create the the view, a element that represents the serialization of a figure inside the GMF framework.
	 * 
	 * @param domainElement
	 *            in our case this a UMLRT element
	 * @param containerView
	 *            the container of the created view
	 * @param semanticHint
	 *            the identifier that will associated to the created view
	 * @param index
	 *            the index where will be created the view ( if you don't know -1)
	 * @param persisted
	 *            true means that the node will be serialized in the file
	 * @param preferencesHint
	 *            a constant that is specific to a diagram
	 * @return the created view
	 */
	View createElementView(EObject domainElement, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint);
}
