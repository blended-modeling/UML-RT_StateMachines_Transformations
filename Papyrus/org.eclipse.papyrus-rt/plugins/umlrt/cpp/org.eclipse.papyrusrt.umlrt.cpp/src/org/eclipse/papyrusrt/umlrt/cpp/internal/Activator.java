/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.cpp.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.osgi.framework.BundleContext;

/**
 * {@link Activator} for this plugin
 */
public class Activator extends Plugin {

	/** logger for this plugin */
	public static LogHelper log;

	/** Identifier of this plugin */
	public final static String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.cpp"; // $NON-NLS-1$
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		log = new LogHelper(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		log = null;
		super.stop(bundleContext);
	}

}
