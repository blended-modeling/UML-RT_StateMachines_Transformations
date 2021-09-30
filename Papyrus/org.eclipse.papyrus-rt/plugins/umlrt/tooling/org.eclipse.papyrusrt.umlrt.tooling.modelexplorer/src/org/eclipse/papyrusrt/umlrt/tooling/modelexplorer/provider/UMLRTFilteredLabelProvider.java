/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 475905
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider;

/**
 * Label provider used by the label provider service.
 * 
 * @deprecated use {@link org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider.UMLRTFilteredLabelProvider} instead.
 */
@Deprecated
public class UMLRTFilteredLabelProvider extends org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider.UMLRTFilteredLabelProvider {

	/**
	 * Constructor.
	 */
	public UMLRTFilteredLabelProvider() {
		super();
	}
}
