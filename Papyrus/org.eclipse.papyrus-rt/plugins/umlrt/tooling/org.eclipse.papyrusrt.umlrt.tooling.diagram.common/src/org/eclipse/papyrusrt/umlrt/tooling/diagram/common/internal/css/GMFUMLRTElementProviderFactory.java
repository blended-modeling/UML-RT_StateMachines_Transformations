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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.css;

import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.IPapyrusElementProvider;
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementProviderFactory;

/**
 * A specialized CSS DOM element provider factory that creates providers that
 * know understand the inheritance semantics of UML-RT diagrams.
 */
public class GMFUMLRTElementProviderFactory extends GMFUMLElementProviderFactory {

	public GMFUMLRTElementProviderFactory() {
		super();
	}

	@Override
	public IPapyrusElementProvider createProvider(CSSDiagram diagram) {
		return new GMFUMLRTElementProvider();
	}

}
