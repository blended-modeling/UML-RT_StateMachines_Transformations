/*****************************************************************************
 * Copyright (c) 2017 EclipseSource
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier - cletavernier@eclipsesource.com - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IModelSetSnippet;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.infra.gmfdiag.style.StyleFactory;
import org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView;
import org.eclipse.papyrus.infra.viewpoints.style.PapyrusViewStyle;
import org.eclipse.papyrus.infra.viewpoints.style.StylePackage;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.internal.language.UMLRTLanguage;

/**
 * Migrates <= 1.2.x Papyrus-RT diagrams to 1.3.x (New architecture framework/viewpoints)
 * 
 * @author Camille Letavernier
 * 
 *         Bug 516138
 */
@SuppressWarnings("deprecation")
public class DiagramMigrationSnippet implements IModelSetSnippet {

	public static final String LEGACY_STRUCTURE_CONFIGURATION = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common/configuration/UMLRT.configuration";
	public static final String LEGACY_STATEMACHINE_CONFIGURATION = "org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine/configuration/RTStateMachine.configuration";
	public static final String LEGACY_STRUCTURE_FRAGMENT = "_Z79eQHcZEeSnWeKqQOfW2A";
	public static final String LEGACY_STATEMACHINE_FRAGMENT = "_Z79eQHcZEeSnWeKqQOfW2A";
	public static final URI LEGACY_STRUCTURE_URI = URI.createPlatformPluginURI(LEGACY_STRUCTURE_CONFIGURATION, true).appendFragment(LEGACY_STRUCTURE_FRAGMENT);
	public static final URI LEGACY_STATEMACHINE_URI = URI.createPlatformPluginURI(LEGACY_STATEMACHINE_CONFIGURATION, true).appendFragment(LEGACY_STATEMACHINE_FRAGMENT);
	public static final String STRUCTURE_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.UMLRTCapsuleStructure";
	public static final String STATEMACHINE_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.UMLRTStateMachine";


	@Override
	public void start(ModelSet modelsManager) {
		ILanguageService service;
		try {
			service = ServiceUtilsForResourceSet.getInstance().getService(ILanguageService.class, modelsManager);
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return;
		}
		if (service.getLanguages(modelsManager).stream().anyMatch(l -> UMLRTLanguage.UMLRT_LANGUAGE_ID.equals(l.getID()))) {
			// ReconcileHelper can't be used, yet, because the services registry is not started
			List<EObject> allDiagrams = new ArrayList<>();
			NotationUtils.getAllNotations(modelsManager).forEach(allDiagrams::add);
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(modelsManager);
			try {
				GMFUnsafe.write(domain, () -> allDiagrams.stream().filter(e -> e instanceof Diagram).map(e -> (Diagram) e).forEach(this::migrateDiagram));
			} catch (InterruptedException e) {
				Activator.log.error(e);
			} catch (RollbackException e) {
				Activator.log.error(e);
			}
		}
	}

	private void migrateDiagram(Diagram diagram) {
		Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_VIEW_STYLE);
		if (style instanceof PapyrusViewStyle) {
			PapyrusViewStyle legacyViewpointStyle = (PapyrusViewStyle) style;


			// The configuration is a proxy, because the viewpoint doesn't exist anymore
			PapyrusView configuration = legacyViewpointStyle.getConfiguration();
			if (configuration == null) {
				return;
			}

			URI viewpointURI = EcoreUtil.getURI(configuration);

			final String representationID;

			if (LEGACY_STRUCTURE_URI.equals(viewpointURI)) {
				representationID = STRUCTURE_ID;
			} else if (LEGACY_STATEMACHINE_URI.equals(viewpointURI)) {
				representationID = STATEMACHINE_ID;
			} else {
				representationID = null;
			}

			if (representationID != null) {
				PapyrusDiagramStyle newViewpointStyle = StyleFactory.eINSTANCE.createPapyrusDiagramStyle();
				newViewpointStyle.setDiagramKindId(representationID);
				newViewpointStyle.setOwner(legacyViewpointStyle.getOwner());
				diagram.getStyles().remove(legacyViewpointStyle);
				diagram.getStyles().add(newViewpointStyle);
			}
		}
	}

	@Override
	public void dispose(ModelSet modelsManager) {
		// Nothing here
	}

}
