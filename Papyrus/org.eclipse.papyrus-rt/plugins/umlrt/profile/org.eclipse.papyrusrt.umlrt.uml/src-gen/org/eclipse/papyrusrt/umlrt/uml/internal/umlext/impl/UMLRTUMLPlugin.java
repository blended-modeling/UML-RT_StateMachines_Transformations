/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.InternalUpdateHelper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * This is the central singleton for the UML-Ext model plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class UMLRTUMLPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final UMLRTUMLPlugin INSTANCE = new UMLRTUMLPlugin();

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	private static InternalUpdateHelper.Factory internalUpdateHelperFactory = InternalUpdateHelper.Factory.DEFAULT;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLRTUMLPlugin() {
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * Assigns the internal update helper factory to use for creation of the helper
	 * for an {@link ExtensionResource}.
	 *
	 * @param helperFactory
	 *            the helper factory, or {@code null} to use a default
	 */
	public static void setInternalUpdateHelperFactory(InternalUpdateHelper.Factory helperFactory) {
		internalUpdateHelperFactory = (helperFactory == null)
				? InternalUpdateHelper.Factory.DEFAULT
				: helperFactory;
	}

	/**
	 * Obtains the internal update helper factory to use for creation of the helper
	 * for an {@link ExtensionResource}.
	 *
	 * @return the helper factory (never {@code null})
	 */
	public static InternalUpdateHelper.Factory getInternalUpdateHelperFactory() {
		return internalUpdateHelperFactory;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {
		private DelegatingInternalUpdateHelperFactory updateHelperFactory;

		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);

			updateHelperFactory = new DelegatingInternalUpdateHelperFactory(context);

			if (UMLRTUMLPlugin.getInternalUpdateHelperFactory() == InternalUpdateHelper.Factory.DEFAULT) {
				UMLRTUMLPlugin.setInternalUpdateHelperFactory(updateHelperFactory);
			}
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			if (UMLRTUMLPlugin.getInternalUpdateHelperFactory() == updateHelperFactory) {
				UMLRTUMLPlugin.setInternalUpdateHelperFactory(InternalUpdateHelper.Factory.DEFAULT);
			}

			updateHelperFactory.shutdown(context);
			updateHelperFactory = null;

			super.stop(context);
		}

		//
		// Nested types
		//

		private static class DelegatingInternalUpdateHelperFactory implements InternalUpdateHelper.Factory, ServiceTrackerCustomizer<InternalUpdateHelper.Factory, InternalUpdateHelper.Factory> {
			private final BundleContext context;
			private final ServiceTracker<InternalUpdateHelper.Factory, InternalUpdateHelper.Factory> tracker;
			private List<InternalUpdateHelper.Factory> delegates = new ArrayList<>();

			DelegatingInternalUpdateHelperFactory(BundleContext context) {
				this.context = context;
				this.tracker = new ServiceTracker<>(context, InternalUpdateHelper.Factory.class, this);

				tracker.open();

				// Get services that already exist
				Object[] services = tracker.getServices();
				if (services != null) {
					Stream.of(services)
							.filter(InternalUpdateHelper.Factory.class::isInstance).map(InternalUpdateHelper.Factory.class::cast)
							.forEach(delegates::add);
				}
			}

			synchronized void shutdown(BundleContext context) {
				if (context == this.context) {
					// Remove all contributions
					delegates.clear();
					tracker.close();
				}
			}

			@Override
			public synchronized InternalUpdateHelper.Factory addingService(ServiceReference<InternalUpdateHelper.Factory> reference) {
				InternalUpdateHelper.Factory result = context.getService(reference);

				if (result != null) {
					delegates.add(result);
				}

				return result;
			}

			@Override
			public synchronized void removedService(ServiceReference<InternalUpdateHelper.Factory> reference, InternalUpdateHelper.Factory service) {
				context.ungetService(reference);
				delegates.remove(service);
			}

			@Override
			public void modifiedService(ServiceReference<InternalUpdateHelper.Factory> reference, InternalUpdateHelper.Factory service) {
				// Pass
			}

			//
			// Helper factory API
			//

			@Override
			public InternalUpdateHelper create(Resource extensionResource) {
				return delegates.stream()
						.sequential()
						.map(factory -> factory.create(extensionResource))
						.filter(Objects::nonNull)
						.findFirst()
						.orElseGet(() -> InternalUpdateHelper.Factory.DEFAULT.create(extensionResource));
			}
		}

	}

}
