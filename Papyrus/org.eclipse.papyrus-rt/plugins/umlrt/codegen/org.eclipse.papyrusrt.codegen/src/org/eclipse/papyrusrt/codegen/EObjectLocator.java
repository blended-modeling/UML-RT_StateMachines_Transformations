/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.CommitResult;

/**
 * EObject locator.
 * 
 * @author ysroh
 *
 */
public final class EObjectLocator {
	/**
	 * Locator instance.
	 */
	private static EObjectLocator locator = new EObjectLocator();
	/**
	 * 
	 */
	private static final String EOBJECTLOCATOR_ELEMENT = "eobjectlocator";

	/**
	 * extension attribute.
	 */
	private static final String ID_ATTR = "id";
	/**
	 * extension attribute.
	 */
	private static final String CLASS_ATTR = "class";

	/**
	 * EObject locator registered through extension point.
	 */
	private final Map<String, IEObjectLocator> locators = new TreeMap<>();

	/**
	 * commands to execute to make changes to open model.
	 */
	private Map<TransactionalEditingDomain, CompoundCommand> commandsToExecute = new HashMap<>();

	/**
	 * 
	 * Constructor.
	 *
	 */
	private EObjectLocator() {
		load();
	}

	public static EObjectLocator getInstance() {
		return locator;
	}

	/**
	 * Query EObject to registered EObject locator.
	 * 
	 * @param label
	 *            label info
	 * @return EObject
	 */
	public EObject getEObject(UserEditableRegion.Label label) {
		EObject result = null;
		URI uri = URI.createURI(label.getUri());
		IEObjectLocator locator = this.locators.get(uri.fileExtension().toLowerCase());
		if (locator != null) {
			result = locator.getEObject(label);
		}
		return result;
	}

	/**
	 * init locators.
	 */
	public void initLocators() {
		for (IEObjectLocator locator : this.locators.values()) {
			locator.initialize();
		}
	}

	/**
	 * init locators.
	 */
	public void cleanUpLocators() {
		for (IEObjectLocator locator : this.locators.values()) {
			locator.cleanUp();
		}
	}

	/**
	 * Query EObject to registered EObject locator.
	 * 
	 * @param label
	 *            label info
	 * @param source
	 *            source code to save
	 * @return EObject
	 */
	public CommitResult saveSource(UserEditableRegion.Label label, String source) {
		CommitResult result = null;
		URI uri = URI.createURI(label.getUri());
		IEObjectLocator locator = this.locators.get(uri.fileExtension().toLowerCase());
		if (locator != null) {
			result = locator.saveSource(label, source);
			if (result.getCommand() != null) {
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(result.getTarget());
				CompoundCommand cmd = commandsToExecute.get(domain);
				if (cmd == null) {
					cmd = new CompoundCommand("Sync Source to Model");
					commandsToExecute.put(domain, cmd);
				}
				cmd.append(result.getCommand());
			}
		}
		return result;
	}

	/**
	 * execute changes.
	 */
	public synchronized void flushChanges() {
		for (Entry<TransactionalEditingDomain, CompoundCommand> entry : commandsToExecute.entrySet()) {
			TransactionalEditingDomain domain = entry.getKey();
			domain.getCommandStack().execute(entry.getValue());
		}
		commandsToExecute.clear();
	}

	/**
	 * clear locators.
	 */
	public void dispose() {
		this.locators.clear();
	}

	/**
	 * Read extension point for locators.
	 */
	public void load() {
		IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(CodeGenPlugin.ID, CodeGenPlugin.EOBJECTLOCATOR_EP_ID)
				.getExtensions();

		for (IExtension extension : extensions) {
			for (IConfigurationElement configElement : extension.getConfigurationElements()) {
				if (EOBJECTLOCATOR_ELEMENT.equals(configElement.getName())) {
					String id = configElement.getAttribute(ID_ATTR);
					if (id == null) {
						CodeGenPlugin.error(
								NLS.bind("Missing attribute: ", ID_ATTR));
						continue;
					}

					Object locatorObj = null;
					try {
						locatorObj = configElement.createExecutableExtension(CLASS_ATTR);
					} catch (CoreException e) {
						CodeGenPlugin.error(e);
					}
					if (locatorObj == null) {
						CodeGenPlugin.error(
								NLS.bind("Missing class: ", CLASS_ATTR));
						continue;
					}

					if (!(locatorObj instanceof IEObjectLocator)) {
						CodeGenPlugin.error(
								NLS.bind("Invalid class: ", CLASS_ATTR),
								null);
						continue;
					}

					this.locators.put(id, (IEObjectLocator) locatorObj);
				}
			}
		}
	}
}
