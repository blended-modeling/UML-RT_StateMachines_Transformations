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
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.provider.decorator;

import org.eclipse.emf.compare.uml2.internal.provider.decorator.UMLDiffItemProviderDecorator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;

@SuppressWarnings("restriction")
public class UMLRTDiffItemProviderDecorator extends UMLDiffItemProviderDecorator {

	public UMLRTDiffItemProviderDecorator(ComposeableAdapterFactory adapterFactory) {
		super(adapterFactory);
	}
}
