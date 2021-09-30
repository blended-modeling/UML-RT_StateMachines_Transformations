/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 476984
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.axis;

import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;

/**
 * Axis Manager for the Parameter Table in Papyrus RT
 * 
 * @author Céline JANSSENS
 *
 */
public class OwnedParameterAxisManager extends TransactionalSynchronizedOnFeatureAxisManager {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#isAllowedContents(java.lang.Object)
	 */
	@Override
	public boolean isAllowedContents(final Object object) {
		boolean content = false;
		// Check if the object is A parameter contains in a RT Message
		if ((object instanceof Parameter && ((Parameter) object).eContainer() instanceof Operation) && RTMessageUtils.isRTMessage(((Parameter) object).eContainer())) {
			content = super.isAllowedContents(object);

		}

		return content;
	}


}
