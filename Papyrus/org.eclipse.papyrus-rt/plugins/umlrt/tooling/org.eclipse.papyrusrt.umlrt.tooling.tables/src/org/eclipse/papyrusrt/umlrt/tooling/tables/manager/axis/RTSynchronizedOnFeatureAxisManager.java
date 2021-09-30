/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.tables.manager.axis;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isExcluded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.emf.nattable.manager.axis.AbstractSynchronizedOnEStructuralFeatureAxisManager;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * A specialization of the synchronized-on-feature axis manager that accounts for
 * inheritance by accessing the extended contents-lists of {@link EReference}s in
 * the model elements.
 */
public class RTSynchronizedOnFeatureAxisManager extends AbstractSynchronizedOnEStructuralFeatureAxisManager {

	private boolean showExclusions;

	public RTSynchronizedOnFeatureAxisManager() {
		super();
	}

	public void setShowExclusions(boolean showExclusions) {
		if (showExclusions == this.showExclusions) {
			return;
		}

		this.showExclusions = showExclusions;

		List<Object> exclusions = getFeaturesValue().stream()
				.filter(Element.class::isInstance)
				.map(Element.class::cast)
				.filter(UMLRTExtensionUtil::isExcluded)
				.collect(Collectors.toList());

		if (!exclusions.isEmpty()) {
			if (showExclusions) {
				updateManagedList(exclusions, Collections.emptyList());
			} else {
				updateManagedList(Collections.emptyList(), exclusions);
			}
		}
	}

	public boolean isShowExclusions() {
		return showExclusions;
	}

	@Override
	protected List<Object> getFeaturesValue() {
		// Get the base UML features with any extensions that they may have
		return UMLRTExtensionUtil.getUMLRTContents(getTableContext(), getListenFeatures());
	}

	@Override
	public boolean isAllowedContents(Object object) {
		// Filter out excluded elements when configured thus
		return (showExclusions || !((object instanceof Element) && isExcluded((Element) object)))
				&& super.isAllowedContents(object);
	}

	public void handleExclusion(Object possiblyExcluded) {
		if (!showExclusions && (possiblyExcluded instanceof Element)) {
			// Is it, in fact, just excluded?
			Element element = (Element) possiblyExcluded;
			if ((element.eResource() != null) && UMLRTExtensionUtil.isExcluded(element)) {
				updateManagedList(Collections.emptyList(), Collections.singletonList(element));
			}
		}
	}
}
