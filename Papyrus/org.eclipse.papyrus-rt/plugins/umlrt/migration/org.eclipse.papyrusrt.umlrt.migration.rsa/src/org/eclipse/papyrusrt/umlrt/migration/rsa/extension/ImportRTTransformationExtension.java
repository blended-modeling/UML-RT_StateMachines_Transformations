/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 495572, 462323
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.migration.rsa.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.papyrus.interoperability.rsa.concurrent.ExecutorsPool;
import org.eclipse.papyrus.interoperability.rsa.internal.extension.TransformationExtension;
import org.eclipse.papyrus.interoperability.rsa.transformation.ImportTransformation;
import org.eclipse.papyrusrt.umlrt.migration.rsa.Activator;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * Extension to the Payrus ImportTransformation, that handles RSA-RTE models and produces Papyrus-RT models
 *
 * @author Camille Letavernier
 *
 */
public class ImportRTTransformationExtension implements TransformationExtension {

	protected ResourceSet resourceSet;

	protected ExecutorsPool executorsPool;

	protected ImportTransformation importTransformation;

	@Override
	public void setResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	@Override
	public void setTransformation(ImportTransformation importTransformation) {
		this.importTransformation = importTransformation;
	}

	@Override
	public void setExecutorsPool(ExecutorsPool pool) {
		this.executorsPool = pool;
	}

	@Override
	public IStatus executeBefore(ExecutionContext context, IProgressMonitor monitor) {
		// Import stereotypes (semantics)
		monitor.subTask("Importing RT Stereotypes...");

		importRTProfile(context, monitor);

		monitor.worked(1);

		return Status.OK_STATUS;
	}

	@Override
	public IStatus executeAfter(ExecutionContext context, IProgressMonitor monitor) {
		monitor.subTask("Import RT Diagrams...");

		importRTDiagrams(context, monitor);

		monitor.worked(1);

		return Status.OK_STATUS;
	}

	protected IStatus importRTProfile(ExecutionContext context, IProgressMonitor monitor) {
		monitor.subTask("Importing RT Profile... ");

		URI transformationURI = getRTTransformationURI("RSARTToPapyrusRT"); //$NON-NLS-1$

		List<ModelExtent> extents = new LinkedList<>();

		// Reuse the same inout UML model as the main transformation
		extents.add(importTransformation.getInOutUMLModel());

		// Use our own set of required profiles/definitions (RT and RT-StateMachine)
		extents.add(initializeExtent(getInRTProfiles()));
		// Don't add the CacheAdapter to registered EPackages
		extents.add(getInRTProfileDefinitions());

		return importTransformation.runTransformation(transformationURI, extents, monitor);
	}

	protected IStatus importRTDiagrams(ExecutionContext context, IProgressMonitor monitor) {
		monitor.subTask("Importing RT Profile... ");

		URI transformationURI = getRTTransformationURI("PapyrusRTDiagrams"); //$NON-NLS-1$

		List<ModelExtent> extents = new ArrayList<>(3);

		// Reuse the same inout UML model as the main transformation
		extents.add(importTransformation.getInOutUMLModel());

		// And the diagrams
		extents.add(initializeExtent(importTransformation.getInoutNotationModel()));

		return importTransformation.runTransformation(transformationURI, extents, monitor);
	}

	protected ModelExtent initializeExtent(ModelExtent extent) {
		// Ward off concurrent modification exceptions in inverse cross-referencer
		// caused by re-entrant discovery of this extent and adding the CacheAdapter
		// to it during calculation of stereotype applications
		extent.getContents().forEach(CacheAdapter.getInstance()::adapt);

		return extent;
	}

	// The definition of the Papyrus-RT profiles (Generated EPackages)
	protected ModelExtent getInRTProfileDefinitions() {
		return new BasicModelExtent(Arrays.asList(new EPackage[] {
				org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage.eINSTANCE, // Fully qualified name to avoid conflicts
				UMLRTStateMachinesPackage.eINSTANCE
		}));
	}

	protected ModelExtent getInRTProfiles() {
		List<EObject> allContents = new LinkedList<>();

		URI umlrtProfileURI = URI.createURI("pathmap://UML_RT_PROFILE/uml-rt.profile.uml"); //$NON-NLS-1$
		Resource umlrtProfile = resourceSet.getResource(umlrtProfileURI, true);
		allContents.addAll(umlrtProfile.getContents());

		URI umlrtSMProfileURI = URI.createURI("pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"); //$NON-NLS-1$
		Resource umlrtSMProfile = resourceSet.getResource(umlrtSMProfileURI, true);
		allContents.addAll(umlrtSMProfile.getContents());

		return new BasicModelExtent(allContents);
	}

	protected TransformationExecutor getTransformation(URI transformationURI, IProgressMonitor monitor) throws DiagnosticException {
		return executorsPool.getExecutor(transformationURI);
	}

	// MemoryLeak: Don't rely on BasicDiagnostic.toIStatus
	// The source Diagnostic contains references to the QVTo ModelExtents, referencing the Model elements (used in #extractPapyrusProfiles())
	// When using the standard conversion, these references are not discarded
	protected static IStatus createStatusFromDiagnostic(Diagnostic diagnostic) {
		return new Status(diagnostic.getSeverity(),
				diagnostic.getSource(),
				diagnostic.getMessage(),
				diagnostic.getException());
	}

	protected URI getRTTransformationURI(String name) {
		return URI.createPlatformPluginURI(String.format("%s/transform/%s.qvto", Activator.PLUGIN_ID, name), true); //$NON-NLS-1$
	}

	@Override
	public int getNumberOfSteps() {
		return 2; // Semantics and Diagrams
	}

	@Override
	public Set<EPackage> getAdditionalSourceEPackages() {
		return Collections.singleton(org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage.eINSTANCE);
	}

}
