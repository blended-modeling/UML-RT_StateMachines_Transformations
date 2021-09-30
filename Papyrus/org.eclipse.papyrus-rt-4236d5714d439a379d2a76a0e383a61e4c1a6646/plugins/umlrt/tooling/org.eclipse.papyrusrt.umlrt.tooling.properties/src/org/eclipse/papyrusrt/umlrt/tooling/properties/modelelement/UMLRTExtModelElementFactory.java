package org.eclipse.papyrusrt.umlrt.tooling.properties.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElementFactory;
import org.eclipse.papyrus.views.properties.contexts.DataContextElement;
import org.eclipse.papyrusrt.umlrt.tooling.properties.Activator;
import org.eclipse.uml2.uml.Collaboration;

public class UMLRTExtModelElementFactory extends UMLModelElementFactory {

	@Override
	protected UMLModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		EObject source = EMFHelper.getEObject(sourceElement);
		if (source == null) {
			Activator.log.warn("Unable to resolve the selected element to an EObject"); //$NON-NLS-1$
			return null;
		}
		if (source instanceof Collaboration) {
			return new UMLRTExtModelElement(source);
		}
		else {
			return super.doCreateFromSource(sourceElement, context);
		}
	}

}
