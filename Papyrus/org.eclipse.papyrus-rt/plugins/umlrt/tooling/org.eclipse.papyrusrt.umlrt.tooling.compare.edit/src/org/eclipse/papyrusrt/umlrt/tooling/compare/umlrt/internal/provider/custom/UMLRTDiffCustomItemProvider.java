/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stefan Dirix - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.custom;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.uml2.internal.provider.custom.UMLDiffCustomItemProvider;

@SuppressWarnings("restriction")
public class UMLRTDiffCustomItemProvider extends UMLDiffCustomItemProvider {

	public UMLRTDiffCustomItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

}
