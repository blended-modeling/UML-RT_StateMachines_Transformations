package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.IXtumlrt2UMLTranslator;
import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt.Xtumlrt2UMLTranslatorInjectorModule;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.xtumlrt.trans.to.uml.ui"; //$NON-NLS-1$

	/** The shared instance. */
	private static Activator plugin;

	/** A {@link Map} from file extensions (as {@link String}s) to injector configurators (as {@link AbstractModule}s). */
	private final Map<String, AbstractModule> translatorInjectorModules = new HashMap<>();


	/**
	 * Constructor.
	 */
	public Activator() {
		// Register the default injector for umlrt.
		registerInjectorModule("umlrt", new Xtumlrt2UMLTranslatorInjectorModule());
	}

	/**
	 * Adds a new extension and an associated injector to configure the translator.
	 * 
	 * The injector is an {@link AbstractModule} that should at least bind the {@link IXtumlrt2UMLTranslator}
	 * interface to an implementation. Then that implementation is used as the translator for files with the
	 * given extension.
	 * 
	 * @param extension
	 *            - A {@link String}.
	 * @param module
	 *            - An {@link AbstractModule}.
	 */
	public void registerInjectorModule(String extension, AbstractModule module) {
		translatorInjectorModules.put(extension, module);
	}

	/**
	 * @param extension
	 *            - A {@link String}.
	 * @return The {@link AbstractModule} associated to the extension or {@code null} if there isn't any.
	 */
	public AbstractModule getInjectorModule(String extension) {
		return translatorInjectorModules.get(extension);
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
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
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

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
