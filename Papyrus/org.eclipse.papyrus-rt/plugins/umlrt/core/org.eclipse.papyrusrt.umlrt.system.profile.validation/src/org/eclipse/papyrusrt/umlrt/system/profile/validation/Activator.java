/**
 *  Copyright (c) 2016 CEA LIST and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrusrt.umlrt.system.profile.validation;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	public static LogHelper log;

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

