/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.CapsuleShapeChangeHandler;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.CapsulePartDiagramChangeHandler;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.DiagramChangesHandlerService;
import org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram.PortDiagramChangeHandler;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.tooling.compare"; //$NON-NLS-1$

	private static Activator plugin;

	public static LogHelper log;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);
		registerDiagramChangeHandlers();
	}

	private void registerDiagramChangeHandlers() {
		DiagramChangesHandlerService service = DiagramChangesHandlerService.getService();
		service.registerDiagramChangesHandler(new CapsuleShapeChangeHandler());
		service.registerDiagramChangesHandler(new CapsulePartDiagramChangeHandler());
		service.registerDiagramChangesHandler(new PortDiagramChangeHandler());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log = null;
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
}
