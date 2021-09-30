/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

/**
 * 
 * Content Provider For Parameter Type
 * It filters the basic content to keep only the rootModel and add the list of default language primitives
 * Required when adding a new parameter to a Protocol Message
 * 
 * @author Céline JANSSENS
 *
 */
public class ParameterTypeContentProvider extends EncapsulatedContentProvider {

	/**
	 * Undefined type
	 */
	protected static final String UNDEFINED = "*";//$NON-NLS-1$

	/**
	 * Main Root Element
	 */
	private EObject root;

	/**
	 * Constructor
	 * 
	 * @param provider
	 *            The encapsulated Content Provider
	 *
	 */
	public ParameterTypeContentProvider(final IStaticContentProvider provider) {
		super(provider);

	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return true if the current element is Valid
	 */
	@Override
	public boolean isValidValue(final Object element) {
		boolean valid = super.isValidValue(element);

		if (!valid) {
			valid = UNDEFINED.equals(element) || element instanceof PrimitiveType;
		}

		return valid;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements()
	 *
	 * @return List of root Element
	 */
	@Override
	public Object[] getElements() {


		Object[] initialElements = super.getElements();

		List<Object> filteredElements = new ArrayList<>();

		// Add the Undefined choice
		filteredElements.add(UNDEFINED);

		// Add Primitive of the default Language
		filteredElements.addAll(getPrimitiveFromDefaultLanguage());

		// Add the Root Model elements
		for (int i = 0; i < initialElements.length; i++) {

			EObject element = EMFHelper.getEObject(initialElements[i]);
			if (null != element) {
				try {

					ModelSet modelSet = ServiceUtilsForEObject.getInstance().getModelSet(element);
					Resource resource = ((UmlModel) modelSet.getModel(UmlModel.MODEL_ID)).getResource();
					if (resource.getContents().contains(element)) {
						root = element;
						filteredElements.add(initialElements[i]);

					}
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		}

		return filteredElements.toArray();

	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return List of Root Elements
	 */
	@Override
	public Object[] getElements(final Object inputElement) {

		return getElements();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return The list of Children
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {

		Object[] initialElements = super.getChildren(parentElement);
		List<Object> filteredElements = new ArrayList<>();

		// Add Root Element
		for (int i = 0; i < initialElements.length; i++) {

			EObject element = EMFHelper.getEObject(initialElements[i]);
			if (null != element) {
				if (element instanceof Classifier) {
					// The type of a parameter cannot be a Capsule or a Protocol
					if (!CapsuleUtils.isCapsule((Classifier) element) && !(ProtocolUtils.isProtocol(element))) {
						filteredElements.add(initialElements[i]);
					}
				}
				if (element instanceof Package) {
					filteredElements.add(initialElements[i]);
				}

			}

		}

		return filteredElements.toArray();
	}




	/**
	 * Retrieve the Primitive from the Default Language
	 * 
	 * @return the list of the primitive Language
	 */
	public Collection<Type> getPrimitiveFromDefaultLanguage() {
		Set<Type> specificPrimitiveTypes = new HashSet<>();
		try {
			if (root instanceof Element && root.eResource() != null && root.eResource().getResourceSet() != null) {
				// Retrieve the default language of the project (by default it is C++
				IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, root);
				if (service != null) {
					IDefaultLanguage language = service.getActiveDefaultLanguage((Element) root);
					// Retrieve the list of the specific primitives of the default language
					specificPrimitiveTypes = language.getSpecificPrimitiveTypes(root.eResource().getResourceSet());
				}
			}
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return specificPrimitiveTypes;
	}


}


