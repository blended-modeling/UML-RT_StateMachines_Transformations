package org.eclipse.papyrusrt.codegen.config;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/** The {@link BundleContext}. */
	private static BundleContext context;
	
	/** The shared instance. */
	private static Activator INSTANCE;
	
	public Activator() {
		INSTANCE = this;
	}
	
	public static Activator getDefault() {
		return INSTANCE;
	}

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) {
		Activator.context = bundleContext;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
}
