/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.uml2.uml.Element;

/**
 * Miscellaneous utilities for working with models and model elements.
 */
public class ModelUtils {

	/**
	 * Not instantiable by clients.
	 */
	private ModelUtils() {
		super();
	}

	/**
	 * Obtains a stream over the containers of an {@code object}, not including
	 * the {@code object}, itself.
	 * 
	 * @param object
	 *            an object
	 * @return its containers, in bottom-up order
	 */
	public static Stream<EObject> containersOf(EObject object) {
		return selfAndContainersOf(object.eContainer());
	}

	/**
	 * Obtains a stream over the containers of an {@code object}, including
	 * the {@code object}, itself.
	 * 
	 * @param object
	 *            an object
	 * @return itself and its containers, in bottom-up order
	 */
	public static Stream<EObject> selfAndContainersOf(EObject object) {
		Iterator<EObject> containers = new Iterator<EObject>() {
			EObject next = object;

			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public EObject next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				EObject result = next;
				next = result.eContainer();
				return result;
			}
		};

		return stream(spliteratorUnknownSize(containers, Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	/**
	 * Instantiate an {@link EClass} in the {@code context} of some existing model
	 * element, using the factory registered in its resource set.
	 * 
	 * @param context
	 *            the context from which the resource set is inferred
	 * @param eclass
	 *            the class to instantiate
	 * 
	 * @return the new object. Never {@code null} because as a last resort it class's static
	 *         factory is used
	 * 
	 * @throws IllegalArgumentException
	 *             if the EClass is abstract
	 * 
	 * @see #create(ResourceSet, EClass)
	 */
	public static EObject create(EObject context, EClass eclass) {
		return (context == null) ? EcoreUtil.create(eclass) : create(context.eResource(), eclass);
	}

	/**
	 * Instantiate an {@link EClass} in the {@code context} of some existing resource,
	 * using the factory registered in its resource set.
	 * 
	 * @param resource
	 *            the context from which the resource set is inferred
	 * @param eclass
	 *            the class to instantiate
	 * 
	 * @return the new object. Never {@code null} because as a last resort it class's static
	 *         factory is used
	 * 
	 * @throws IllegalArgumentException
	 *             if the EClass is abstract
	 * 
	 * @see #create(ResourceSet, EClass)
	 */
	public static EObject create(Resource context, EClass eclass) {
		return (context == null) ? EcoreUtil.create(eclass) : create(context.getResourceSet(), eclass);
	}

	/**
	 * Instantiate an {@link EClass} in the {@code context} of some existing model
	 * element, using the factory registered in its resource set.
	 * 
	 * @param context
	 *            the context from which the resource set is inferred
	 * @param eclass
	 *            the class to instantiate
	 * 
	 * @return the new object. Never {@code null} because as a last resort it class's static
	 *         factory is used
	 * 
	 * @throws IllegalArgumentException
	 *             if the EClass is abstract
	 */
	public static EObject create(ResourceSet context, EClass eclass) {
		EObject result;

		if (context == null) {
			result = EcoreUtil.create(eclass);
		} else {
			EFactory factory = context.getPackageRegistry().getEFactory(eclass.getEPackage().getNsURI());
			result = (factory == null) ? EcoreUtil.create(eclass) : factory.create(eclass);
		}

		return result;
	}

	/**
	 * Get default language.
	 * 
	 * @param context
	 *            element
	 * @return Default language or null if not found
	 */
	public static IDefaultLanguage getDefaultLanguage(Element context) {
		IDefaultLanguage language = null;

		try {
			IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, context);
			language = service.getActiveDefaultLanguage(context);
		} catch (ServiceException e) {
			org.eclipse.papyrusrt.umlrt.core.Activator.log.error(e);
		}
		return language;
	}

}
