/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA, Obeo, Christian W. Damus, and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Kenn Hussey (CEA) - initial API and implementation
 *   Kenn Hussey (CEA) - 389542, 399544, 425846, 418466, 429352
 *   Mikael Barbero (Obeo) - 414572
 *   Christian W. Damus (CEA) - 414572, 401682
 *   Christian W. Damus - bugs 467545, 513078 adapt from UMLResourcesUtil class
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.util;

import java.net.URL;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.papyrusrt.umlrt.profile.Activator;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.profile.util.UMLRTResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLFactoryImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLRealTimeFactoryImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.UMLRTUMLResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * Static utilities for the configuration of resource sets to work with UML-RT
 * models in a stand-alone (Eclipse-free) context.
 */
public class UMLRTResourcesUtil {

	/**
	 * Not instantiable by clients.
	 */
	private UMLRTResourcesUtil() {
		super();
	}

	public static ResourceSet init(ResourceSet resourceSet) {
		// Basic UML set-up
		UMLResourcesUtil.initLocalRegistries(resourceSet);

		// And overlay UML-RT specifics
		initLocalRegistries(resourceSet);

		// And ensure that stereotype application creation goes through the
		// resource-set-registered factory
		initLocalStereotypeApplicationHelper(resourceSet);

		return resourceSet;
	}

	/**
	 * Adds registrations to EMF's and UML2's global registries that enable
	 * working with UML-RT models. To apply the same registrations only locally on
	 * a resource set (e.g., for isolation from other EMF-based code in the same
	 * class loader), use the {@link #initLocalRegistries(ResourceSet)} method,
	 * instead.
	 * 
	 * @see #initLocalRegistries(ResourceSet)
	 */
	public static void initGlobalRegistries() {
		UMLResourcesUtil.initGlobalRegistries();

		initPackageRegistry(EPackage.Registry.INSTANCE);
		initEPackageNsURIToProfileLocationMap(UMLPlugin.getEPackageNsURIToProfileLocationMap());
		initURIConverterURIMap(URIConverter.URI_MAP);
		initResourceFactoryRegistry(Resource.Factory.Registry.INSTANCE);
	}

	public static void initLocalRegistries(ResourceSet resourceSet) {
		initPackageRegistry(resourceSet.getPackageRegistry());

		// there is no local registry for this
		initEPackageNsURIToProfileLocationMap(UMLPlugin.getEPackageNsURIToProfileLocationMap());

		initURIConverterURIMap(resourceSet.getURIConverter().getURIMap());

		// there are no UML-RT-specific content handlers

		initResourceFactoryRegistry(resourceSet.getResourceFactoryRegistry());
	}

	public static void initLocalStereotypeApplicationHelper(ResourceSet resourceSet) {
		StereotypeApplicationHelper wrapper = new UMLRTStereotypeApplicationHelper(
				StereotypeApplicationHelper.getInstance(resourceSet));

		StereotypeApplicationHelper.setInstance(resourceSet, wrapper);
	}

	/**
	 * Adds packages required for working with UML-RT models to the specified
	 * registry.
	 * 
	 * @param packageRegistry
	 *            a package registry, perhaps local to a resource set or perhaps
	 *            the global registry
	 * 
	 * @return the same {@code packageRegistry}, augmented
	 */
	public static EPackage.Registry initPackageRegistry(EPackage.Registry packageRegistry) {
		packageRegistry.put(UMLRealTimePackage.eNS_URI, UMLRealTimePackage.eINSTANCE);
		packageRegistry.put(UMLRTStateMachinesPackage.eNS_URI, UMLRTStateMachinesPackage.eINSTANCE);
		packageRegistry.put(ExtUMLExtPackage.eNS_URI, ExtUMLExtPackage.eINSTANCE);

		installUMLRTFactory(packageRegistry);

		return packageRegistry;
	}

	/**
	 * Adds resource factories required for working with UML-RT models to the
	 * specified registry.
	 * 
	 * @param resourceFactoryRegistry
	 *            a resource-factory registry, perhaps local to a resource set
	 *            or perhaps the global registry
	 * 
	 * @return the same {@code resourceFactoryRegistry}
	 */
	public static Resource.Factory.Registry initResourceFactoryRegistry(Resource.Factory.Registry resourceFactoryRegistry) {
		installUMLRTResourceFactory(resourceFactoryRegistry);
		return resourceFactoryRegistry;
	}

	/**
	 * Adds profile namespace URI mappings required for working with UML-RT models
	 * to the specified map. These include at least mappings for the
	 * UML-RT profiles.
	 * 
	 * @param ePackageNsURIToProfileLocationMap
	 *            a profile location map, perhaps local to a resource set or
	 *            perhaps the global location map
	 * 
	 * @return the same {@code ePackageNsURIToProfileLocationMap}, augmented
	 */
	public static Map<String, URI> initEPackageNsURIToProfileLocationMap(Map<String, URI> ePackageNsURIToProfileLocationMap) {
		ePackageNsURIToProfileLocationMap.put(UMLRealTimePackage.eNS_URI,
				URI.createURI("pathmap://UML_RT_PROFILE/uml-rt.profile.uml#_1h74oEeVEeO0lv5O1DTHOQ")); //$NON-NLS-1$

		ePackageNsURIToProfileLocationMap.put(UMLRTStateMachinesPackage.eNS_URI,
				URI.createURI("pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml#_KLcn0FDtEeOA4ecmvfqvaw")); //$NON-NLS-1$

		ePackageNsURIToProfileLocationMap.put(ExtUMLExtPackage.eNS_URI,
				URI.createURI("pathmap://UML_RT_UML/uml-ext.metamodel.uml#_0")); //$NON-NLS-1$

		return ePackageNsURIToProfileLocationMap;
	}

	/**
	 * Adds resource URI mappings required for working with UML-RT models to the
	 * specified map. These include at least mappings for the UML-RT
	 * model libraries, metamodels, and profiles.
	 * 
	 * @param uriMap
	 *            a URI map, perhaps local to a resource set or perhaps the
	 *            global URI map
	 * 
	 * @return the same {@code uriMap}, augmented
	 */
	public static Map<URI, URI> initURIConverterURIMap(Map<URI, URI> uriMap) {
		URI baseURI = getBaseResourceURI(UMLRTResource.class, UMLRTResource.PROFILE_PATH, "umlProfile"); //$NON-NLS-1$

		mapUMLRTResourceURIs(uriMap, "pathmap://UML_RT_PROFILE/", //$NON-NLS-1$
				baseURI.appendSegment("umlProfile")); //$NON-NLS-1$

		baseURI = getBaseResourceURI(UMLRTResourcesUtil.class, "pathmap://UML_RT_UML/uml-ext.metamodel.uml", "model"); //$NON-NLS-1$//$NON-NLS-2$

		mapUMLRTResourceURIs(uriMap, "pathmap://UML_RT_UML/", //$NON-NLS-1$
				baseURI.appendSegment("model")); //$NON-NLS-1$

		return uriMap;
	}

	private static URI getBaseResourceURI(Class<?> context, String prototypeURI, String folder) {
		// The UML-Ext metamodel is in my bundle
		URI uri = URI.createURI(prototypeURI); // $NON-NLS-1$
		URL resultURL = context.getClassLoader().getResource(
				String.format("%s/%s", folder, uri.lastSegment())); //$NON-NLS-1$

		URI result;

		if (resultURL != null) {
			// remove the /<folder>/<model-name>.uml segments of the resource
			// we found
			result = URI.createURI(resultURL.toExternalForm(), true)
					.trimSegments(2);
		} else {
			// probably, we're not running with JARs, so assume the source
			// project folder layout
			resultURL = context.getResource(context.getSimpleName() + ".class"); //$NON-NLS-1$

			String baseURL = resultURL.toExternalForm();
			baseURL = baseURL.substring(0, baseURL.lastIndexOf("/bin/")); //$NON-NLS-1$
			result = URI.createURI(baseURL, true);
		}

		return result;
	}

	private static void mapUMLRTResourceURIs(Map<URI, URI> uriMap, String uri, URI location) {
		URI prefix = URI.createURI(uri);

		// ensure trailing separator (make it a "URI prefix")
		if (!prefix.hasTrailingPathSeparator()) {
			prefix = prefix.appendSegment(""); //$NON-NLS-1$
		}

		// same with the location
		if (!location.hasTrailingPathSeparator()) {
			location = location.appendSegment(""); //$NON-NLS-1$
		}

		uriMap.put(prefix, location);

		// and platform URIs, too
		String folder = location.segment(location.segmentCount() - 2);
		String platformURI = String.format("%s/%s/", Activator.PLUGIN_ID, folder);
		uriMap.put(URI.createPlatformPluginURI(platformURI, true), location);
		uriMap.put(URI.createPlatformResourceURI(platformURI, true), location);
	}

	/**
	 * Installs the {@link UMLFactory} and {@link UMLResource.Factory} for
	 * UML-RT semantics in the context of a resource set.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * @return the same {@code resourceSet}, augmented
	 * 
	 * @see #installUMLRTFactory(org.eclipse.emf.ecore.EPackage.Registry)
	 * @see #installUMLRTResourceFactory(org.eclipse.emf.ecore.resource.Resource.Factory.Registry)
	 */
	public static ResourceSet installUMLRTFactory(ResourceSet resourceSet) {
		installUMLRTFactory(resourceSet.getPackageRegistry());
		installUMLRTResourceFactory(resourceSet.getResourceFactoryRegistry());
		return resourceSet;
	}

	/**
	 * Installs the {@link UMLFactory} for UML-RT semantics into a package
	 * registry, which may be specific to a resource set or (please, only
	 * in a stand-alone application) the global registry.
	 * 
	 * @param packageRegistry
	 *            a package registry
	 */
	public static void installUMLRTFactory(EPackage.Registry packageRegistry) {
		// Our shim for the UML package with its custom factory
		UMLPackage umlPackage = UMLRTUMLFactoryImpl.eINSTANCE.getUMLPackage();
		installUMLFactory(packageRegistry, umlPackage);

		// And same for the UMLRealTime profile package
		UMLRealTimePackage umlRealTimePackage = UMLRTUMLRealTimeFactoryImpl.eINSTANCE.getUMLRealTimePackage();
		packageRegistry.put(UMLRealTimePackage.eNS_URI, umlRealTimePackage);
	}

	private static void installUMLFactory(EPackage.Registry packageRegistry, UMLPackage umlPackage) {
		packageRegistry.put(UMLPackage.eNS_URI, umlPackage);

		// And any other namespace URIs under which it was registered
		for (Map.Entry<String, Object> next : packageRegistry.entrySet()) {
			if (next.getValue() instanceof UMLPackage) {
				next.setValue(umlPackage);
			}
		}
	}

	/**
	 * Installs the {@link UMLResource.Factory} for UML-RT semantics into a resource
	 * factory registry, which may be specific to a resource set or (please, only
	 * in a stand-alone application) the global registry.
	 * 
	 * @param resourceFactoryRegistry
	 *            a resource factory registry
	 */
	public static void installUMLRTResourceFactory(Resource.Factory.Registry resourceFactoryRegistry) {
		installUMLResourceFactory(resourceFactoryRegistry, UMLRTUMLResourceFactoryImpl.INSTANCE);
	}

	private static void installUMLResourceFactory(Resource.Factory.Registry resourceFactoryRegistry, UMLResource.Factory umlResourceFactory) {
		Map<String, Object> extensionToFactoryMap = resourceFactoryRegistry.getExtensionToFactoryMap();
		extensionToFactoryMap.put(UMLResource.FILE_EXTENSION, umlResourceFactory);

		Map<String, Object> contentTypeToFactoryMap = resourceFactoryRegistry.getContentTypeToFactoryMap();
		contentTypeToFactoryMap.put(UMLResource.UML_CONTENT_TYPE_IDENTIFIER, umlResourceFactory);
	}

	/**
	 * Restores the {@link UMLFactory} and {@link UMLResource.Factory} for
	 * standard UML semantics in the context of a resource set.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * @return the same {@code resourceSet}, augmented
	 * 
	 * @see #uninstallUMLRTFactory(org.eclipse.emf.ecore.EPackage.Registry)
	 * @see #uninstallUMLRTResourceFactory(org.eclipse.emf.ecore.resource.Resource.Factory.Registry)
	 */
	public static ResourceSet uninstallUMLRTFactory(ResourceSet resourceSet) {
		uninstallUMLRTFactory(resourceSet.getPackageRegistry());
		uninstallUMLRTResourceFactory(resourceSet.getResourceFactoryRegistry());
		return resourceSet;
	}

	/**
	 * Restores the {@link UMLFactory} for standard UML semantics in a package
	 * registry, which may be specific to a resource set or may be the
	 * global registry.
	 * 
	 * @param packageRegistry
	 *            a package registry
	 */
	public static void uninstallUMLRTFactory(EPackage.Registry packageRegistry) {
		packageRegistry.put(UMLRealTimePackage.eNS_URI, UMLRealTimePackage.eINSTANCE);
		installUMLFactory(packageRegistry, UMLPackage.eINSTANCE);
	}

	/**
	 * Restores the {@link UMLResource.Factory} for standard UML semantics into
	 * a resource factory registry, which may be specific to a resource set or
	 * may be the global registry.
	 * 
	 * @param resourceFactoryRegistry
	 *            a resource factory registry
	 */
	public static void uninstallUMLRTResourceFactory(Resource.Factory.Registry resourceFactoryRegistry) {
		installUMLResourceFactory(resourceFactoryRegistry, UMLResource.Factory.INSTANCE);
	}

	/**
	 * Some adapters in the UML-RT Façade API react to changes in the model to maintain
	 * a correct (for UML-RT) semantic structure in the UML model, but those adaptations
	 * are best avoided when changes result from undo/redo of recorded commands. This
	 * API provides the façade API with the means to recognize changes that are triggered
	 * by undo or redo (such as in a transactional editing domain).
	 * 
	 * @param resourceSet
	 *            a resource set to configure for UML-RT editing
	 * @param undoRedoRecognizer
	 *            a predicate that matches notifications triggered by undo/redo
	 * 
	 * @deprecated Use the {@link #setUndoRedoQuery(ResourceSet, BooleanSupplier)} API, instead
	 *             to handle scenarios where changes are occurring (for example, by change
	 *             application) that have not yet produced notifications and it is necessary to
	 *             know <em>a priori</em> whether they are in the context of undo/redo
	 */
	@Deprecated
	public static void setUndoRedoRecognizer(ResourceSet resourceSet, Predicate<? super Notification> undoRedoRecognizer) {
		ReificationAdapter.getInstance(resourceSet).setUndoRedoRecognizer(undoRedoRecognizer);
	}

	/**
	 * Some adapters in the UML-RT Façade API react to changes in the model to maintain
	 * a correct (for UML-RT) semantic structure in the UML model, but those adaptations
	 * are best avoided when changes result from undo/redo of recorded commands. This
	 * API provides the façade API with the means to query whether undo/redo is currently
	 * in progress, which should already account for all consequences of the changes
	 * that are being observed.
	 * 
	 * @param resourceSet
	 *            a resource set to configure for UML-RT editing
	 * @param isUndoRedo
	 *            indicates whether undo or redo is currently in progress in the context of
	 *            the resource set. In the case of a Transactional Editing Domain, this
	 *            must also cover the case of transaction roll-back
	 */
	public static void setUndoRedoQuery(ResourceSet resourceSet, BooleanSupplier isUndoRedo) {
		ReificationAdapter.getInstance(resourceSet).setUndoRedoQuery(isUndoRedo);
	}
}
