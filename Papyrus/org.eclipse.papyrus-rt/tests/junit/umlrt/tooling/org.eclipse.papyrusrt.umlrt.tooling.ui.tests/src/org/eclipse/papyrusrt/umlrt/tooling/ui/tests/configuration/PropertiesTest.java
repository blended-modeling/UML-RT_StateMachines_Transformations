/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.configuration;

import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.tests.AbstractEMFResourceTest;
import org.eclipse.papyrusrt.umlrt.tooling.properties.Activator;
import org.junit.Test;

/**
 * Test the properties model :
 * - validate the model
 */
public class PropertiesTest extends AbstractEMFResourceTest {

	public static final String PROPERTIES_MENU_PATH = Activator.PLUGIN_ID + "/propertyView/UML-RT/uml-rt.ctx"; // $NON-NLS-0$

	@Override
	public String getFileUri() {
		return PROPERTIES_MENU_PATH;
	}

	/**
	 * Validate the model with the rules defined in the meta-model tooling
	 */
	@Test
	@FailingTest
	public void validatePropertiesModel() {
		doValidateResource();
	}

}
