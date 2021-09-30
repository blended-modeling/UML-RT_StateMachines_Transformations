package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;

/**
 * Utility class on {@link Operation} that are RTMessage
 */
public class RTPortUtils {


	/**
	 * @param eObject
	 * @return
	 */
	public static boolean isRTPort(EObject eObject) {
		if (eObject instanceof Port) {
			// get Owner of the operation, and check if this is a messageSET
			Port port = (Port) eObject;
			return true;//UMLUtil.getStereotype(stereotypeApplication);
		}
		return false;
	}

}
