package org.eclipse.papyrusrt.xtumlrt.trans.to.uml;

import javax.inject.Inject;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTModel;
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * XTUMLRT generator.
 * 
 * @author ysroh
 */
public class TextToModelGeneration {

	private IXtumlrt2UMLTranslator generator;

	@Inject
	public void setGenerator(IXtumlrt2UMLTranslator translator) {
		this.generator = translator;
	}

	/**
	 * Invoke generation.
	 * 
	 * @param textUri
	 *            - The {@link URI) of the textual model.
	 * @return An {@link IStatus} with the outcome.
	 */
	public IStatus generate(URI textUri) {

		XtextResourceSet rset = new XtextResourceSet();
		Resource resource = rset.getResource(textUri, true);
		EObject model = resource.getContents().get(0);
		IStatus result = Status.OK_STATUS;
		try {
			if (Status.OK != generator.generate((RTModel) model)) {
				result = Status.CANCEL_STATUS;
			}

		} catch (Exception e) {
			XTUMLRTLogger.error("Text to Model transformation failed", e);
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
		}

		return result;
	}
}
