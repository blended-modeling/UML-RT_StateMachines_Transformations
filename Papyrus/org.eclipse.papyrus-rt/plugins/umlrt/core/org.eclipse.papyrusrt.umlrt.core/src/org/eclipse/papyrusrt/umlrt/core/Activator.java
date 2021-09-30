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
 *   Christian W. Damus - bug 510524
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrusrt.umlrt.core.internal.TransactionalUpdateHelper;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.InternalUpdateHelper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Activator for this bundle
 * 
 */
public class Activator extends Plugin {

	public final static String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.core"; //$NON-NLS-1$

	public static LogHelper log;

	private ServiceRegistration<InternalUpdateHelper.Factory> updateHelperFactoryReg;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		log = new LogHelper(this);

		InternalUpdateHelper.Factory upateHelperFactory = TransactionalUpdateHelper::create;
		updateHelperFactoryReg = bundleContext.registerService(InternalUpdateHelper.Factory.class, upateHelperFactory, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (updateHelperFactoryReg != null) {
			updateHelperFactoryReg.unregister();
			updateHelperFactoryReg = null;
		}

		log = null;
		super.stop(bundleContext);
	}

}
