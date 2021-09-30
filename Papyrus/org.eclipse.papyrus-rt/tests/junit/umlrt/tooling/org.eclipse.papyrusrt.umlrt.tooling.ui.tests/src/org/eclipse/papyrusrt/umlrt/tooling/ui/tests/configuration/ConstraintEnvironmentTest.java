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

import org.eclipse.papyrus.junit.utils.tests.AbstractEMFResourceTest;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.junit.Test;

/**
 * Test the constraint model :
 * - validate the model
 */
public class ConstraintEnvironmentTest extends AbstractEMFResourceTest {

	public static final String CONSTRAINT_PATH = Activator.PLUGIN_ID + "/environment/Environment.xmi"; // $NON-NLS-0$

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFileUri() {
		return CONSTRAINT_PATH;
	}

	@Test
	public void validateResource() {
		doValidateResource();
	}

}
