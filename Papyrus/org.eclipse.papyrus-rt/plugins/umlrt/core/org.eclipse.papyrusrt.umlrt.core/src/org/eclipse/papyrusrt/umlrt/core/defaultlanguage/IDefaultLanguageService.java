/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.defaultlanguage;

import java.util.List;

import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.uml2.uml.Element;

/**
 * Interface to be implemented by the DefaultLanguage service.
 * <p>
 * This service is in charge of retrieving the current active default language on an element, and also the list of all available default language.
 * </p>
 */
public interface IDefaultLanguageService extends IService {

	/**
	 * Returns the current active default language for the given {@link Element} in the model.
	 * 
	 * @param element
	 *            the element for which the default language is searched
	 * @return the current active default language for the given {@link Element} in the model or the {@link NoDefautLanguage} singleton instance if none can be found
	 */
	IDefaultLanguage getActiveDefaultLanguage(Element element);

	/**
	 * Sets the current active default language for a given {@link Element} in the model.
	 * 
	 * @param element
	 *            the element for which the default language is searched
	 * @param value
	 *            the new default language
	 */
	void setActiveDefaultLanguage(Element element, IDefaultLanguage value);

	/**
	 * Returns all available default languages in the platform.
	 * 
	 * @return all available default languages in the platform or an empty list if none is available
	 */
	List<? extends IDefaultLanguage> getAvailableDefaultLanguages();

	/**
	 * Install the specified language on the given model element.
	 * 
	 * @param element
	 *            the element from which model is retrieved to apply default langauge
	 * @param language
	 *            the language to apply
	 */
	void installLanguage(Element element, IDefaultLanguage language);

	/**
	 * Uninstall the current default language on the given model element.
	 * 
	 * @param element
	 *            the element from which model is retrieved to apply default langauge
	 */
	void uninstallLanguage(Element element);



}
