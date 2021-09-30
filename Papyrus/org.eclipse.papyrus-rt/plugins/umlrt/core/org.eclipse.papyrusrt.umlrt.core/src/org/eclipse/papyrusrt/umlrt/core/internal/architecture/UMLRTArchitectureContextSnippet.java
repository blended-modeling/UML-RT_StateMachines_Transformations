/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.internal.architecture;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescription;
import org.eclipse.papyrus.infra.core.editor.ModelSetServiceFactory;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IModelSetSnippet;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModelUtils;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrusrt.umlrt.core.architecture.UMLRTArchitectureContextIds;
import org.eclipse.papyrusrt.umlrt.core.internal.language.UMLRTLanguage;

/**
 * Checker on UML-RT models opening (to ensure context is OK).
 */
public class UMLRTArchitectureContextSnippet implements IModelSetSnippet {

	/**
	 * Constructor.
	 */
	public UMLRTArchitectureContextSnippet() {
		// no instances
	}

	@Override
	public void start(ModelSet modelsManager) {
		// check current architecture context, if the UML-RT language is installed.
		ILanguageService service = ServiceUtils.getInstance().getService(ILanguageService.class, ModelSetServiceFactory.getServiceRegistry(modelsManager), null);
		if (service != null) {
			if (service.getLanguages(modelsManager).stream().anyMatch(l -> UMLRTLanguage.UMLRT_LANGUAGE_ID.equals(l.getID()))) {
				// check current Architecture description
				ArchitectureDescriptionUtils utils = new ArchitectureDescriptionUtils(modelsManager);

				// Use directly DiModelUtils instead of ArchitectureDescriptionUtils, because we don't want to get the default context if none is set
				ArchitectureDescription architectureDescription = DiModelUtils.getArchitectureDescription(modelsManager);
				String contextId = architectureDescription == null ? null : architectureDescription.getContextId();
				if (!UMLRTArchitectureContextIds.UMLRT.equals(contextId)) {
					try {
						GMFUnsafe.write(modelsManager.getTransactionalEditingDomain(), utils.switchArchitectureContextId(UMLRTArchitectureContextIds.UMLRT));
						// should also check for viewpoints?
						Collection<String> viewpoints = utils.getArchitectureContext().getDefaultViewpoints().stream().map(ctx -> ctx.getId()).collect(Collectors.toList());
						GMFUnsafe.write(modelsManager.getTransactionalEditingDomain(), utils.switchArchitectureViewpointIds(viewpoints.toArray(new String[viewpoints.size()])));
					} catch (InterruptedException | RollbackException e) {
						Activator.log.error(e);
					}
				}
			}
		}
	}

	@Override
	public void dispose(ModelSet modelsManager) {
		// no action to perform here.

	}
}