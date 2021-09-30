package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;

/**
 *
 */
public class RelayPortMatcher implements IElementMatcher {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(EObject eObject) {
		return RTPortUtils.isRTPort(eObject);
	}

}
