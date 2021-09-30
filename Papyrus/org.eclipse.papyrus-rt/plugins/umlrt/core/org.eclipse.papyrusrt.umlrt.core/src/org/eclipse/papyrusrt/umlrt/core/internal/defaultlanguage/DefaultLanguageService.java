/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 476984, 501119, 510323
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.internal.debug.IDebugOptions;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.internal.operations.PackageOperations;

/**
 * Basic implementation of the default language service.
 */
public class DefaultLanguageService extends PlatformObject implements IDefaultLanguageService {

	/** source of the {@link EAnnotation} to store default language. */
	private static final String LANGUAGE_ANNOTATION_SOURCE = "http://www.eclipse.org/papyrus-rt/language/1.0.0";

	/** key of the {@link EAnnotation} value to store default language. */
	private static final String LANGUAGE_ANNOTATION_VALUE = "language";

	/** Service registry. */
	protected ServicesRegistry registry;

	/**
	 * Constructor.
	 */
	public DefaultLanguageService() {
		// Empty constructor.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDefaultLanguage getActiveDefaultLanguage(Element element) {
		String languageKey = getLanguageKey(element);
		for (IDefaultLanguage language : getAvailableDefaultLanguages()) {
			if (languageKey.equals(language.getId())) {
				return language;
			}
		}
		return NoDefautLanguage.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveDefaultLanguage(Element element, IDefaultLanguage language) {
		if (language == null) {
			language = NoDefautLanguage.INSTANCE;
		}
		// uninstall previous language
		uninstallLanguage(element);

		// update the Eannotation
		updateLanguageAnnotation(element, language);

		// install the new one
		installLanguage(element, language);
	}

	/**
	 * Updates the model to store the new language
	 * 
	 * @param element
	 *            the element for which the default language is searched
	 * @param language
	 *            the new default language
	 */
	protected void updateLanguageAnnotation(Element element, IDefaultLanguage language) {
		// store in a EAnnotation
		if (!NoDefautLanguage.INSTANCE.equals(language)) {
			EAnnotation annotation = UML2Util.getEAnnotation(element, LANGUAGE_ANNOTATION_SOURCE, true);
			annotation.getDetails().put(LANGUAGE_ANNOTATION_VALUE, language.getId());
		} else {
			// remove eannotation if exists
			EAnnotation annotation = UML2Util.getEAnnotation(element, LANGUAGE_ANNOTATION_SOURCE, false);
			if (annotation != null) {
				element.getEAnnotations().remove(annotation);
			}
		}
	}

	/**
	 * Returns the key of the current language active for the given element.
	 * 
	 * @param element
	 *            the element to check
	 * @return the key of the current language or "noDefault" if none was found
	 */
	protected String getLanguageKey(Element element) {
		// retrieve the root element, and the look for the eannotation.
		// another way could be to find recursively each parent and find the nearest eannotation to support hybrid language
		Package rootPackage = PackageUtil.getRootPackage(element);
		String key = NoDefautLanguage.INSTANCE.getId();
		if (rootPackage != null) {
			EAnnotation annotation = rootPackage.getEAnnotation(LANGUAGE_ANNOTATION_SOURCE);
			if (annotation != null) {
				String value = annotation.getDetails().get(LANGUAGE_ANNOTATION_VALUE);
				if (value != null) {
					key = value;
				}
			}
		} else {
			Activator.log.error("impossible to find root element for: " + element, null);
		}
		return key;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends IDefaultLanguage> getAvailableDefaultLanguages() {
		return DefaultLanguageRegistry.getInstance().getLanguages();
	}

	/*
	 * Service lifecycle
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.registry = servicesRegistry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startService() throws ServiceException {
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "DefaultLanguageService#startService() ...");

		Package root = getRoot();
		if (root != null) {
			ModelSet modelSet = registry.getService(ModelSet.class);
			IDefaultLanguage language = getDefaultLanguage(root, modelSet);
			initLanguage(root, modelSet, language);
		}

		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "DefaultLanguageService#startService() finished");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disposeService() throws ServiceException {
		registry = null;
	}


	//
	// Adaptable API
	//


	/**
	 * Adaptation to other services in the registry takes precedence over registered adapter factories.
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		T result;

		if (adapter.isInstance(registry)) {
			result = adapter.cast(registry);
		} else {
			result = (registry == null) ? null : ServiceUtils.getInstance().getService(adapter, registry, null);
		}

		return (result != null) ? result : super.getAdapter(adapter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void installLanguage(Element element, IDefaultLanguage language) {
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "DefaultLanguageService#installLanguage(" + EMFCoreUtil.getName(element) + ", " + language + ")\n");
		Package root = getRoot();
		if (root == null) {
			return;
		}
		// retrieve root
		if (language != null) {
			ResourceSet rset = EMFHelper.getResourceSet(root);

			// Basic initialization as on every opening of the model
			initLanguage(root, rset, language);

			// Apply required profiles
			MultiStatus status = null;
			for (String profileURI : language.getProfilesToApply()) {
				// Apply the given profile if it can be found
				try {
					Resource modelResource = loadResource(rset, URI.createURI(profileURI));
					if (modelResource.getContents().get(0) instanceof Profile) {
						PackageUtil.applyProfile(root, (Profile) modelResource.getContents().get(0), true);
					}
				} catch (CoreException e) {
					if (status == null) {
						status = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, "Error while loading profiles from DefaultLanguage: " + language.getName(), null);
					}
					status.add(e.getStatus());
				}
			}

			if (status != null) {
				Activator.log.log(status);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uninstallLanguage(Element element) {
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "DefaultLanguageService#uninstallLanguage(" + EMFCoreUtil.getName(element) + ")");
		Package root = getRoot();
		if (root == null) {
			return;
		}

		// retrieve root
		IDefaultLanguage language = getActiveDefaultLanguage(element);
		if (language != null) {
			for (String profileURI : language.getProfilesToApply()) {
				// unapply the given profile if it can be found
				Resource modelResource = EMFHelper.getResourceSet(root).getResource(URI.createURI(profileURI), true);
				if (modelResource != null && modelResource.getContents().get(0) instanceof Profile) {
					PackageOperations.unapplyProfile(root, (Profile) modelResource.getContents().get(0));
				}
			}
		}
	}

	/**
	 * Returns the root of the model.
	 * 
	 * @return the package root of the model or <code>null</code> if none was found
	 */
	protected Package getRoot() {
		ModelSet modelSet;
		try {
			modelSet = registry.getService(ModelSet.class);
			UmlModel model = UmlUtils.getUmlModel(modelSet);
			if (model != null) {
				// Don't call lookupRoot() to avoid exception when Rootelement is not already set
				// EObject eObject = null;
				// eObject = model.lookupRoot();
				if (!model.getResource().getContents().isEmpty()) {
					EObject eObject = model.getResource().getContents().get(0);
					if (eObject instanceof Package) {
						return (Package) eObject;
					}
				}
			}
			return null;
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}

	}


	/**
	 * Returns the default language installed on the specified package.
	 * 
	 * @param root
	 *            root of the model for which default language is looked for.
	 * @param modelSet
	 *            the model set in which the model is supposed to be.
	 * @return the default language for the model or the {@link NoDefautLanguage} singleton instance if none was found.
	 */
	protected IDefaultLanguage getDefaultLanguage(Package root, ModelSet modelSet) {
		IDefaultLanguageService service = null;
		IDefaultLanguage language = null;
		try {
			// service are not yet activated. Starting them
			ServicesRegistry registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(root);
			registry.startServicesByClassKeys(IDefaultLanguageService.class);

			// get the service, now it should be started
			service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, root);

			if (service != null) {
				language = service.getActiveDefaultLanguage(root);
			} else {
				Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "No service found for this package: " + root);
				language = NoDefautLanguage.INSTANCE;
			}
		} catch (BadStateException e) {
			Activator.log.error(e);
			language = NoDefautLanguage.INSTANCE;
		} catch (ServiceException e) {
			Activator.log.error(e);
			language = NoDefautLanguage.INSTANCE;
		}
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "Language " + language.getName() + " for : " + root);
		return language;
	}



	/**
	 * Loads the model libraries required by the default language installed on the given root Package.
	 * 
	 * @param root
	 *            the root element of the model
	 * @param resourceSet
	 *            the resource set in which the library should be loaded
	 * @param defaultLanguage
	 *            the default language that requires the libraries to be load
	 * @return the status of the load operation
	 */
	protected IStatus loadDefaultLanguageLibrary(Package root, ResourceSet resourceSet, IDefaultLanguage defaultLanguage) {
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "loading default language libraries...");
		IStatus status = null;
		for (String libraryURI : defaultLanguage.getLibrariesToImport()) {
			URI uri = URI.createURI(libraryURI);
			if (uri != null) {
				try {
					loadResource(resourceSet, uri);
				} catch (CoreException e) {
					if (status == null) {
						status = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, "Error while loading libraries from DefaultLanguage: " + defaultLanguage.getName(), null);
					}
					((MultiStatus) status).add(e.getStatus());
				}
			}
		}
		Activator.log.trace(IDebugOptions.PAPYRUS_RT_DEFAULT_LANGUAGE, "loading default language libraries: " + (status == null ? "OK" : status));
		return (status == null) ? Status.OK_STATUS : status;
	}

	/**
	 * Loads a default-language resource.
	 * 
	 * @param rset
	 *            the resource set in which to load it
	 * @param uri
	 *            the URI of the resource to load
	 * 
	 * @return the resource; never {@code null}
	 * 
	 * @throws CoreException
	 *             on any problem in loading the resource, even if it
	 *             was partially loaded (loaded with errors)
	 */
	private Resource loadResource(ResourceSet rset, URI uri) throws CoreException {
		Resource result = null;

		try {
			result = rset.getResource(uri, true);
		} catch (Exception e) {
			// Exception is handled in the resource errors, below.
			// Here, just get the failed resource
			result = rset.getResource(uri, false);
		}

		if ((result == null) || result.getContents().isEmpty() || !result.getErrors().isEmpty()) {
			// Resource diagnostics typically are exceptions
			Throwable exception = (result == null) ? null : result.getErrors().stream()
					.filter(Throwable.class::isInstance).map(Throwable.class::cast)
					.findAny().orElse(null);
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to load default language resource: " + uri, exception));
		}

		return result;
	}

	/**
	 * React to the detection or assignment of the default {@link language} of
	 * the user model. This performs actions that are required on installation
	 * of the language in the first place and also whenever the language service
	 * is initialized (on opening a user model).
	 * 
	 * @param rootPackage
	 *            the root package of the user model
	 * @param resourceSet
	 *            the context resource-set
	 * @param language
	 *            the model's default language
	 */
	private void initLanguage(Package rootPackage, ResourceSet resourceSet, IDefaultLanguage language) {
		// Try to load the associage library model(s)
		IStatus libraryStatus = loadDefaultLanguageLibrary(rootPackage, resourceSet, language);
		if (!libraryStatus.isOK()) {
			Activator.log.error("Error while loading default language libraries:" + libraryStatus, null);
		}
	}
}
