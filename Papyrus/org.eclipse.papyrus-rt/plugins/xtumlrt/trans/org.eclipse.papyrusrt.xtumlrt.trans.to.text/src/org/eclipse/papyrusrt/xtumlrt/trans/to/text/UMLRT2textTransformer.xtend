/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse- Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.trans.to.text

import com.google.inject.Inject
import com.google.inject.name.Named
import java.util.Collections
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.ENamedElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtModelTranslator
import org.eclipse.papyrusrt.xtumlrt.trans.preproc.ModelPreprocessor
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.MessageBox
import org.eclipse.xtext.Constants
import org.eclipse.xtext.resource.XtextResourceSet

/**
 * This class is responsible for transformations from UML-RT models into textual syntax.
 * 
 * <p>The input model can be either an xtUML-RT model or a UML2 model with the UML-RT profile. 
 * If it is the latter, the transformation from UML2 to xtUML-RT is invoked first.
 * 
 * @author Ernesto Posse
 */
class UMLRT2textTransformer {
	
	@Inject
	@Named(Constants.FILE_EXTENSIONS)
	String TUMLRT_FILE_EXTENSION
	
	public static val SRC_GEN = "."
	static val DEFAULT_NAME = "unnamed"
	static val DEFAULT_TUMLRT_FILE_EXTENSION = "umlrt"
	
	val modelPreprocessor = new ModelPreprocessor
	val uml2xtumlrtTranslator = new UML2xtumlrtModelTranslator
	protected var Resource inputModelResource
	private var ResourceSet resourceSet
	
	String fileExtension
	
	enum Target { TUMLRT, XML }
	
	def setTarget(Target target)
	{
		switch (target)
		{
			case TUMLRT:
			{
				fileExtension = DEFAULT_TUMLRT_FILE_EXTENSION
				resourceSet = new XtextResourceSet
			}
			case XML:
			{
				fileExtension = "xml"
				resourceSet = new ResourceSetImpl
			}
			
		}
	}
	
	dispatch def boolean save(Model model) {
		var result = false
		if (model === null) {
			XTUMLRTLogger.error("Attempted to save 'null' as a model")
		}
		else {
			modelPreprocessor.preprocess(model)
			if (inputModelResource === null) {
				inputModelResource = model.eResource
			}
			val newURI = model.targetURI
			if (newURI.modelExists) {
				val overwrite = askToOverwrite
				if (!overwrite){
					result = true
				}
			}
			if (!result) {
				val Resource newResource = resourceSet.createResource(newURI);
				if (newResource !== null) {
					newResource.getContents().add(model);
					try {
						newResource.save(Collections.EMPTY_MAP);
						result = true
					} catch (Exception e) {
						XTUMLRTLogger.error("Serialization to " + fileExtension + " failed", e);
					}
				}
				else {
					XTUMLRTLogger.error("Failed to create resource : '" + newURI.toString + "'")
				}
			}
		}
		result
	}
	
	dispatch def boolean save(org.eclipse.uml2.uml.Model model) {
		var result = false
		if (model === null) {
			XTUMLRTLogger.error("Attempted to save 'null' as a model")
		}
		else {
			inputModelResource = model.eResource
			uml2xtumlrtTranslator.resetAll
			val translatedModel = uml2xtumlrtTranslator.translateElement(model) as Model
			result = save(translatedModel)
		}
		result
	}

	private def getFileExtension()
	{
		if (fileExtension == null)
		{
			fileExtension = if (TUMLRT_FILE_EXTENSION != null && !TUMLRT_FILE_EXTENSION.empty) TUMLRT_FILE_EXTENSION else DEFAULT_TUMLRT_FILE_EXTENSION
		}
		fileExtension
	}
	
	private def setFileExtension(String ext)
	{
		fileExtension = ext
	}
	
	private def getTargetURI(EObject eobj) {
//		inputModelResource.URI.trimSegments(1).appendSegment(eobj.modelName).appendFileExtension(getFileExtension)
		inputModelResource.URI.trimFileExtension().appendFileExtension(getFileExtension)
	}
	
	private dispatch def getModelName(Model model) {
		val name = model.name
		if (name !== null && !name.empty) {
			name
		}
		else {
			DEFAULT_NAME
		}
	}
	
	private dispatch def getModelName(org.eclipse.uml2.uml.Model model) {
		val name = model.name
		if (name !== null && !name.empty) {
			name
		}
		else {
			DEFAULT_NAME
		}
	}

	private dispatch def getModelName(ENamedElement element) {
		val name = element.name
		if (name !== null && !name.empty) {
			name
		}
		else {
			DEFAULT_NAME
		}
	}

	private dispatch def getModelName(EObject eobj) {
		DEFAULT_NAME
	}

	private static def boolean modelExists(URI uri) {
//		val path = Paths.get(new java.net.URI(uri.toString))
//		return Files.exists(path)
		// TODO: change this so that it works on standalone
		val file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		val exists = file.exists
		exists
	}

	/**
	 * Ask user confirmation to overwrite existing model.
	 */
	private static def boolean askToOverwrite() {
		val dialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING.bitwiseOr(SWT.OK).bitwiseOr(SWT.CANCEL))
		dialog.setText("Warning");
		dialog.setMessage("Do you want to overwrite the existing model?");
		dialog.open() === SWT.OK
	}

}