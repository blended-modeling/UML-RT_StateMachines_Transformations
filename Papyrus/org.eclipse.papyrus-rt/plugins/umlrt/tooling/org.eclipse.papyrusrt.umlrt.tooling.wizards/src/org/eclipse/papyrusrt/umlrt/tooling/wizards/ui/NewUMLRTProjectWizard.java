/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.wizards.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.NewPapyrusProjectWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

//Unused yet. The Papyrus wizard is not really useful when the language/profile doesn't provide specific diagrams
public class NewUMLRTProjectWizard extends NewPapyrusProjectWizard {

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle("New UML RealTime Project");
	}

	@Override
	protected WizardNewProjectCreationPage createNewProjectCreationPage() {
		WizardNewProjectCreationPage newProjectPage = super.createNewProjectCreationPage();
		newProjectPage.setTitle("Papyrus UML RealTime Project");
		newProjectPage.setDescription("Create a new Papyrus UML RealTime Project");
		return newProjectPage;
	}

	@Override
	protected void saveDiagramCategorySettings() {
		// do nothing
		// here UML RT is the only available category
	}

	@Override
	public boolean isPapyrusRootWizard() {
		return false;
	}
}
