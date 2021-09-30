/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.editors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;

/**
 * Language editor registry for code snippet view.
 * 
 * @author ysroh
 *
 */
@SuppressWarnings("restriction")
public final class LanguageEditorRegistry {

	/**
	 * instance.
	 */
	private static LanguageEditorRegistry instance;

	/**
	 * editors.
	 */
	private Map<String, Class<? extends ILanguageEditor>> editorMap = new HashMap<>();

	/**
	 * 
	 * Constructor.
	 *
	 */
	private LanguageEditorRegistry() {
		editorMap.put(NoDefautLanguage.INSTANCE.getName(), NoLanguageEditor.class);
		editorMap.put("C++", TextLanguageEditor.class);
	}

	/**
	 * Get instance.
	 * 
	 * @return instance
	 */
	public static LanguageEditorRegistry getInstance() {
		if (instance == null) {
			instance = new LanguageEditorRegistry();
		}
		return instance;
	}

	/**
	 * Get editor instance for given language.
	 * 
	 * @param language
	 *            language
	 * @return editor instance
	 */
	public ILanguageEditor getNewLanguageEditorInstance(String language) {
		Class<? extends ILanguageEditor> clazz = editorMap.get(language);
		if (clazz == null) {
			clazz = editorMap.get(NoDefautLanguage.INSTANCE.getName());
		}
		if (clazz != null) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
			}
		}

		return null;
	}
}
