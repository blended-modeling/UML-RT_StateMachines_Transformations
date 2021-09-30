/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

/**
 * A query that obtains the RT attributes (or non-RT attributes) of a capsule.
 */
public class GetRTAttributesQuery extends GetRTStructuralFeaturesQuery<Property> {

	public GetRTAttributesQuery() {
		super(Property.class);
	}

	@Override
	protected List<Property> getFeatures(Class capsule) {
		List<Property> result = new ArrayList<>(capsule.getOwnedAttributes());
		result.removeAll(capsule.getOwnedPorts());
		return result;
	}

	@Override
	protected List<? extends UMLRTCapsulePart> getFeatures(UMLRTCapsule capsule) {
		return capsule.getCapsuleParts(true);
	}
}
