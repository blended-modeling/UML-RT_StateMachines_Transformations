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

package org.eclipse.papyrusrt.umlrt.tooling.types.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesFactory;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for semi-derived features of the {@link UMLRTSetTypeAdviceConfiguration}.
 */
public class SetTypeAdviceSemiDerivedFeaturesTest {
	private final IHintedType classifier = UMLElementTypes.CLASSIFIER;
	private final IHintedType structuredClassifier = UMLElementTypes.STRUCTURED_CLASSIFIER;

	private UMLRTSetTypeAdviceConfiguration fixture = UMLRTTypesFactory.eINSTANCE.createUMLRTSetTypeAdviceConfiguration();

	/**
	 * Initializes me.
	 */
	public SetTypeAdviceSemiDerivedFeaturesTest() {
		super();
	}

	@Test
	public void implicitElementTypeLabel() {
		fixture.setElementType(classifier.getId());
		assertThat(fixture.getElementTypeLabel(), is(classifier.getDisplayName()));

		// Always recomputed
		fixture.setElementType(structuredClassifier.getId());
		assertThat(fixture.getElementTypeLabel(), is(structuredClassifier.getDisplayName()));
	}

	@Test
	public void explicitElementTypeLabel() {
		String customLabel = "Real-Time Classifier";

		fixture.setElementType(classifier.getId());
		fixture.setElementTypeLabel(customLabel);
		assertThat(fixture.getElementTypeLabel(), is(customLabel));

		// Still the same
		fixture.setElementType(structuredClassifier.getId());
		assertThat(fixture.getElementTypeLabel(), is(customLabel));
	}

	@Test
	public void implementElementTypeLabelNotifies() {
		fixture.setElementType(classifier.getId());
		assertThat(fixture.getElementTypeLabel(), is(classifier.getDisplayName()));

		Notification[] notification = { null };
		fixture.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				switch (msg.getFeatureID(UMLRTSetTypeAdviceConfiguration.class)) {
				case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
					notification[0] = msg;
					break;
				}
			}
		});

		// Change in computed value notified
		fixture.setElementType(structuredClassifier.getId());
		assertThat(notification[0], notNullValue());
		assertThat(notification[0].getOldValue(), is(classifier.getDisplayName()));
		assertThat(notification[0].getNewValue(), is(structuredClassifier.getDisplayName()));
		assertThat(notification[0].wasSet(), is(false));
		assertThat(fixture.isSetElementTypeLabel(), is(false));
	}

	//
	// Test framework
	//

	@BeforeClass
	public static void ensureElementTypesRegistry() {
		// Kick the modelled element-types that we depend on
		ElementTypeSetConfigurationRegistry.getInstance();
	}

}
