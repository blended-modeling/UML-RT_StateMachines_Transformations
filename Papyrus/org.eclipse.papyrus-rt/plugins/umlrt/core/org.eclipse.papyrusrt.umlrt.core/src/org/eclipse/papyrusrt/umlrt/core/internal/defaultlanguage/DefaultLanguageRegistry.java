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
package org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageRegistry;
import org.eclipse.papyrusrt.umlrt.core.internal.debug.IDebugOptions;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Type;

import com.google.common.collect.Lists;

/**
 * Registry that manages the available default languages in UML-RT models
 */
public class DefaultLanguageRegistry implements IDefaultLanguageRegistry {

	private static final String EXT_POINT = "defaultLanguage"; //$NON-NLS-1$

	private static final IDefaultLanguage NULL_DEFAULT_LANGUAGE = new IDefaultLanguage() {

		@Override
		public String getId() {
			return "NULL";
		}

		@Override
		public String getName() {
			return "NullName";
		}

		@Override
		public String getIconURL() {
			return null;
		}

		@Override
		public Set<String> getProfilesToApply() {
			return Collections.emptySet();
		}

		@Override
		public Set<String> getLibrariesToImport() {
			return Collections.emptySet();
		}

		@Override
		public Set<Type> getSpecificPrimitiveTypes(ResourceSet resourceSet) {
			return Collections.emptySet();
		}

		@Override
		public Set<Collaboration> getSystemProtocols(ResourceSet resourceSet) {
			return Collections.emptySet();
		}

		@Override
		public Collaboration getBaseProtocol(ResourceSet resourceSet) {
			return null;
		}

		@Override
		public Set<Class> getSystemClasses(ResourceSet resourceSet) {
			return Collections.emptySet();
		}

	};

	/** singleton instance, accessed through {@link #getInstance()} */
	private static IDefaultLanguageRegistry instance;

	private boolean needPrune;

	private final List<IDefaultLanguage> defaultLanguages = Lists.newArrayListWithExpectedSize(1);

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static synchronized IDefaultLanguageRegistry getInstance() {
		if (instance == null) {
			instance = new DefaultLanguageRegistry();
		}
		return instance;
	}

	/**
	 * Private constructor (singleton pattern)
	 */
	private DefaultLanguageRegistry() {
		new DefaultLanguageRegistryReader().readRegistry();
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public List<? extends IDefaultLanguage> getLanguages() {
		prune();
		return Collections.unmodifiableList(defaultLanguages);
	}

	/**
	 * Prune out any null Default Languages (failed to initialize) and replace
	 * descriptors that have been instantiated by their instances, to avoid
	 * delegation.
	 */
	private void prune() {
		if (needPrune) {
			needPrune = false;
			for (ListIterator<IDefaultLanguage> iter = defaultLanguages.listIterator(); iter.hasNext();) {

				IDefaultLanguage next = iter.next();
				if (next == NULL_DEFAULT_LANGUAGE) {
					iter.remove(); // Don't need this, any more
				} else if (next instanceof DefaultLanguageRegistryReader.DefaultLanguageDescriptor) {
					DefaultLanguageRegistryReader.DefaultLanguageDescriptor desc = (DefaultLanguageRegistryReader.DefaultLanguageDescriptor) next;
					if (desc.instance != null) {
						iter.set(desc.instance);
					}
				}
			}
		}
	}

	private void removeProvider(String className) {
		synchronized (defaultLanguages) {
			for (Iterator<IDefaultLanguage> iter = defaultLanguages.iterator(); iter.hasNext();) {

				IDefaultLanguage next = iter.next();
				if (next instanceof DefaultLanguageRegistryReader.DefaultLanguageDescriptor) {
					DefaultLanguageRegistryReader.DefaultLanguageDescriptor desc = (DefaultLanguageRegistryReader.DefaultLanguageDescriptor) next;
					if (className.equals(desc.getClassName())) {
						iter.remove();
						break;
					}
				} else if (className.equals(next.getClass().getName())) {
					iter.remove();
					break;
				}
			}
		}
	}

	/*
	 * Internal class for parsing
	 */
	private class DefaultLanguageRegistryReader extends RegistryReader {

		private static final String E_DEFAULT_LANGUAGE = "defaultLanguage";
		private static final String A_CLASS = "class"; //$NON-NLS-1$
		private static final String A_ID = "id"; //$NON-NLS-1$
		private static final String A_NAME = "name"; //$NON-NLS-1$
		private static final String A_ICON_URL = "iconURL"; //$NON-NLS-1$


		public DefaultLanguageRegistryReader() {
			super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_POINT);
			Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Create a registry reader for DefaultLanguage");
		}

		private DefaultLanguageDescriptor currentDescriptor;

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			return add ? handleAdd(element) : handleRemove(element);
		}

		private boolean handleAdd(IConfigurationElement element) {
			boolean result = true;

			if (E_DEFAULT_LANGUAGE.equals(element.getName())) {
				currentDescriptor = new DefaultLanguageDescriptor(element, A_CLASS);
				defaultLanguages.add(currentDescriptor);
			}

			return result;
		}

		private boolean handleRemove(IConfigurationElement element) {
			boolean result = true;

			if (E_DEFAULT_LANGUAGE.equals(element.getName())) {
				String className = getClassName(element, A_CLASS);
				if (className != null) {
					removeProvider(className);
				}
			}

			return result;
		}

		private String getClassName(IConfigurationElement provider, String attributeName) {
			String result = provider.getAttribute(attributeName);
			if (result == null) {
				IConfigurationElement[] classes = provider.getChildren(attributeName);
				if (classes.length > 0) { // Assume a single occurrence
					result = classes[0].getAttribute(A_CLASS);
				}
			}
			return result;
		}

		/**
		 * Descriptor class for {@link IDefaultLanguage} provided via extension point. To avoid loading of unused plugins, the real instanciation of each IDefaultlanguage is postponed until it is really needed.
		 */
		public class DefaultLanguageDescriptor extends PluginClassDescriptor implements IDefaultLanguage {

			/** Real instance of the IdefaultLanguage. lazy creation, to avoid unnecessary plugin start */
			private IDefaultLanguage instance;

			public DefaultLanguageDescriptor(IConfigurationElement element, String attributeName) {
				super(element, attributeName);
			}

			private String getClassName() {
				return DefaultLanguageRegistryReader.this.getClassName(element, attributeName);
			}

			private IDefaultLanguage getInstance() {
				if (instance == null) {
					try {
						String className = getClassName();
						if (className == null) {
							// No implementation has been provided, which is an issue
							instance = NULL_DEFAULT_LANGUAGE;
						} else {
							instance = (IDefaultLanguage) createInstance();
						}
					} catch (Exception e) {
						Activator.log.error("Failed to instantiate default language extension.", e);
						instance = NULL_DEFAULT_LANGUAGE;
					}

					needPrune = true;
				}

				return instance;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getId() {
				return element.getAttribute(A_ID);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getName() {
				return element.getAttribute(A_NAME);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getIconURL() {
				return element.getAttribute(A_ICON_URL);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Set<String> getProfilesToApply() {
				return getInstance().getProfilesToApply();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Set<String> getLibrariesToImport() {
				return getInstance().getLibrariesToImport();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Set<Type> getSpecificPrimitiveTypes(ResourceSet resourceSet) {
				return getInstance().getSpecificPrimitiveTypes(resourceSet);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Set<Collaboration> getSystemProtocols(ResourceSet resourceSet) {
				return getInstance().getSystemProtocols(resourceSet);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Collaboration getBaseProtocol(ResourceSet resourceSet) {
				return getInstance().getBaseProtocol(resourceSet);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Set<Class> getSystemClasses(ResourceSet resourceSet) {
				return getInstance().getSystemClasses(resourceSet);
			}
		}
	}
}
