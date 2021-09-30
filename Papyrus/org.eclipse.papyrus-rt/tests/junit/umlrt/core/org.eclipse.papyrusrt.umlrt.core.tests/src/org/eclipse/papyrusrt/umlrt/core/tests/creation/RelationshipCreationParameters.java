/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import org.eclipse.uml2.uml.Element;

/**
 * Contain parameters for creation of a relationship
 */
public class RelationshipCreationParameters {
	public Element container;
	public Element source;
	public Element target;
	public Element forcedContainer;

	/**
	 * Constructor.
	 *
	 */
	public RelationshipCreationParameters(Element container, Element source, Element target, Element forcedContainer) {
		this.container = container;
		this.source = source;
		this.target = target;
		this.forcedContainer = forcedContainer;
	}
}