/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.modelelement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Test;

public class ChangeListenerUtilsTest extends AbstractPapyrusTest {

	// check method exists
	@Test
	public void testFireDataSourceChangedMethodExists() {
		Method dataSourceChanged = null;
		try {
			dataSourceChanged = DataSource.class.getDeclaredMethod("fireDataSourceChanged");// $NON-NLS-1$
			assertNotNull(dataSourceChanged);
			dataSourceChanged.setAccessible(true);
			assertTrue(dataSourceChanged.isAccessible());
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getMessage());
		}

	}

}
