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
package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;


/**
 * Listener that forces refresh of the model element. Workaround for bug 477033
 * 
 */
@Deprecated
public class ChangeListenerUtils {

	public static void fireDataSourceChanged(DataSource dataSource) throws RuntimeException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (dataSource != null) {
			// force change
			Method dataSourceChanged = DataSource.class.getDeclaredMethod("fireDataSourceChanged"); //$NON-NLS-1$
			dataSourceChanged.setAccessible(true);
			dataSourceChanged.invoke(dataSource);
		}
	}

}
