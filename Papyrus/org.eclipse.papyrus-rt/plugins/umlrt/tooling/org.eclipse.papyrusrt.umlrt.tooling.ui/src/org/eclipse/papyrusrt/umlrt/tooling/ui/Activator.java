/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.handlers.WorkbenchSourceProviders;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.tooling.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	// log helper
	public static LogHelper log;

	/**
	 * The constructor
	 */
	public Activator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);

		WorkbenchSourceProviders.asyncInstall();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
