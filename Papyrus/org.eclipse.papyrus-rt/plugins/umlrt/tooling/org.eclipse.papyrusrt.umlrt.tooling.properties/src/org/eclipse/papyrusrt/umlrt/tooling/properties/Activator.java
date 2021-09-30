/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.properties;



import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.widgets.util.ImageDescriptorManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.tooling.properties"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	public static LogHelper log;

	/**
	 * The logger for this plugin
	 */
	public ImageDescriptorManager imageDescriptorManager;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// register the login helper
		log = new LogHelper(plugin);

		imageDescriptorManager = new ImageDescriptorManager();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;

		imageDescriptorManager.reset();
		imageDescriptorManager = null;
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

	/**
	 * Returns the image at the given path from this plugin
	 *
	 * @param path
	 *            the path of the image to be displayed
	 * @return The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(String path) {
		return getImage(PLUGIN_ID, path);
	}

	/**
	 * Returns the image from the given image descriptor
	 *
	 * @param pluginId
	 *            The plugin in which the image is located
	 * @param path
	 *            The path to the image from the plugin
	 * @return
	 * 		The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(String pluginId, String path) {
		final ImageRegistry registry = getImageRegistry();
		String key = pluginId + "/" + path; //$NON-NLS-1$
		Image image = registry.get(key);
		if (image == null) {
			registry.put(key, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
			image = registry.get(key);
		}
		return image;
	}

	/**
	 * Returns the image from the given image location
	 *
	 * @param pluginId
	 *            The plugin in which the image is located
	 * @param path
	 *            The path to the image from the plugin
	 * @return
	 * 		The Image Descriptor at the given location, or null if it
	 *         couldn't be found
	 */
	public ImageDescriptor getImageDescriptor(String pluginId, String path) {
		final ImageRegistry registry = getImageRegistry();
		String key = pluginId + "/" + path; //$NON-NLS-1$
		ImageDescriptor descriptor = registry.getDescriptor(key);
		if (descriptor == null) {
			registry.put(key, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
			descriptor = registry.getDescriptor(key);
		}
		return descriptor;
	}

	/**
	 * Get the Image from the descriptor
	 * 
	 * @param descriptor
	 *            The descriptor
	 * @return the Image
	 */
	public Image getImage(final ImageDescriptor descriptor) {
		Image image = null;
		final ImageRegistry registry = getImageRegistry();
		if (null != imageDescriptorManager && null != registry) {

			String key = imageDescriptorManager.getKey(descriptor);
			image = registry.get(key);
			if (null == image) {
				registry.put(key, descriptor);
				image = registry.get(key);
			}
		}
		return image;
	}


}
