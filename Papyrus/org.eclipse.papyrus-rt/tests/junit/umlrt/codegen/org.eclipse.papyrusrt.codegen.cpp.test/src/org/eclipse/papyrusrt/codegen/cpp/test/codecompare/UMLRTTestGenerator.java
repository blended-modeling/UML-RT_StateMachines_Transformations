/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.test.codecompare;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.AbstractUMLRT2CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.XTUMLRT2CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.build.ProjectGenerator;
import org.eclipse.papyrusrt.codegen.utils.ProjectUtils;

@SuppressWarnings("nls")
public class UMLRTTestGenerator extends AbstractUMLRT2CppCodeGenerator {

	public static UMLRTTestGenerator INSTANCE = new UMLRTTestGenerator();

	private UMLRTTestGenerator() {
		super();
		setStandalone(false);
	}

	@Override
	public IStatus generate(List<EObject> elements, String top, boolean uml) {

		IStatus status = super.generate(elements, top, uml);

		Set<IProject> projects = new HashSet<>();
		for (EObject e : elements) {
			IProject p = getProject(e);
			if (p != null) {
				projects.add(p);
			}
		}

		// Refresh generated projects
		for (IProject p : projects) {
			try {
				p.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				// ignore
			}
		}

		return status;
	}

	@Override
	protected File getModelFolder(EObject eobj) {
		if (eobj == null) {
			return null;
		}

		org.eclipse.emf.common.util.URI eobjURI = eobj.eResource().getURI();
		org.eclipse.emf.common.util.URI containerURI = eobjURI.trimSegments(1);
		String containerFileString = containerURI.toFileString();
		File file = new File(containerFileString);
		return file;
	}

	public File getOutputFolder(EObject eobj) {
		return getOutputFolder(eobj, null);
	}

	@Override
	protected File getOutputFolder(EObject eobj, XTUMLRT2CppCodeGenerator codeGen) {
		IProject project = getProject(eobj, codeGen);

		// Bug 109: Refresh the folder before checking if it exists.
		IFolder folder = project.getFolder("src");
		try {
			folder.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			CodeGenPlugin.error("could not refresh output folder", e);
		}

		if (!folder.exists())
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				CodeGenPlugin.error("could not create output folder", e);
			}

		return folder.getRawLocation().toFile();
	}

	public IProject getProject(EObject eobj) {
		return getProject(eobj, null);
	}

	public IProject getProject(EObject eobj, XTUMLRT2CppCodeGenerator codeGen) {
		String projectName = ProjectUtils.getProjectName(eobj);
		projectName = projectName + "_CDTProject";

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (!project.exists()) {
			resetResource(eobj, codeGen);
		}
		return ProjectGenerator.getOrCreateCPPProject(projectName, null);
	}
}