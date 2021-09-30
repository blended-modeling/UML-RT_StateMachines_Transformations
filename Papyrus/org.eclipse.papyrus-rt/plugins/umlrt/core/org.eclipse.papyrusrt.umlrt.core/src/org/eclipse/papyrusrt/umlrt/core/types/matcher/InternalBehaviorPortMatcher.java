package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 *
 */
public class InternalBehaviorPortMatcher implements IElementMatcher {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(EObject eObject) {
		if (!RTPortUtils.isRTPort(eObject)) {
			return false;
		}

		Port port = (Port) eObject;
		// check now the properties of the port:
		// 1. isService = false
		// 2. behavior = true
		// 3. isWired = true
		// 4. isPublish = false;
		if (port.isService()) {
			return false;
		}

		if (!port.isBehavior()) {
			return false;
		}

		RTPort stereotype = UMLUtil.getStereotypeApplication(port, RTPort.class);
		if (stereotype == null) {
			Activator.log.error("Impossible to get stereotype RTPort", null);
			return false;
		}
		if (!stereotype.isWired()) {
			return false;
		}
		if (stereotype.isPublish()) {
			return false;
		}
		return true;

	}

}
