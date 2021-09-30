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

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the {@link MultiplicityElementAdapter} class.
 */
@RunWith(Parameterized.class)
public class MultiplicityElementAdapterTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	private MultiplicityElement multiplicityElement;

	private MultiplicityElementAdapter fixture;

	@CleanUp
	private List<Object> multNotified = new ArrayList<>();

	@CleanUp
	private List<Object> lowerNotified = new ArrayList<>();

	@CleanUp
	private List<Object> upperNotified = new ArrayList<>();

	@CleanUp
	private List<Object> typeNotified = new ArrayList<>();

	public MultiplicityElementAdapterTest(EClass multiplicityElementMetaclass, String __) {
		super();

		this.multiplicityElement = (MultiplicityElement) EcoreUtil.create(multiplicityElementMetaclass);
	}

	@Test
	public void createLowerValue() {
		multiplicityElement.createLowerValue(null, null, UMLPackage.Literals.LITERAL_INTEGER);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void createUpperValue() {
		multiplicityElement.createUpperValue(null, null, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void setLower() {
		multiplicityElement.setLower(2);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE));
		assertThat(lowerNotified, hasItem(UMLPackage.Literals.LITERAL_INTEGER__VALUE));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void setUpper() {
		multiplicityElement.setUpper(2);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, hasItem(UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void setLowerOpaqueExpressionBody() {
		OpaqueExpression expr = (OpaqueExpression) multiplicityElement.createLowerValue(null, null,
				UMLPackage.Literals.OPAQUE_EXPRESSION);

		reset();

		expr.getBodies().add("MAX_REPLICATION");

		assertThat(multNotified, hasItem(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY));
		assertThat(lowerNotified, hasItem(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void setUpperOpaqueExpressionBody() {
		OpaqueExpression expr = (OpaqueExpression) multiplicityElement.createUpperValue(null, null,
				UMLPackage.Literals.OPAQUE_EXPRESSION);

		reset();

		expr.getBodies().add("MAX_REPLICATION");

		assertThat(multNotified, hasItem(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, hasItem(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY));
		assertThat(typeNotified, not(hasItem(anything())));
	}

	@Test
	public void removeLowerValue() {
		ValueSpecification lowerValue = multiplicityElement.createLowerValue(null, null, UMLPackage.Literals.LITERAL_INTEGER);
		reset();

		multiplicityElement.setLowerValue(null);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));

		assertThat(lowerValue.eAdapters(), not(hasItem(fixture)));
	}

	@Test
	public void removeUpperValue() {
		ValueSpecification upperValue = multiplicityElement.createUpperValue(null, null, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL);
		reset();

		multiplicityElement.setUpperValue(null);

		assertThat(multNotified, hasItem(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, not(hasItem(anything())));

		assertThat(upperValue.eAdapters(), not(hasItem(fixture)));
	}

	@Test
	public void setType() {
		assumeThat("Test not applicable to non-TypedElement", multiplicityElement, instanceOf(TypedElement.class));
		TypedElement typed = (TypedElement) multiplicityElement;

		Type type = UMLFactory.eINSTANCE.createClass();
		typed.setType(type);

		assertThat(multNotified, hasItem(UMLPackage.Literals.TYPED_ELEMENT__TYPE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, hasItem(UMLPackage.Literals.TYPED_ELEMENT__TYPE));
	}

	@Test
	public void changeTypeName() {
		assumeThat("Test not applicable to non-TypedElement", multiplicityElement, instanceOf(TypedElement.class));
		TypedElement typed = (TypedElement) multiplicityElement;

		Type type = UMLFactory.eINSTANCE.createClass();
		typed.setType(type);

		reset();

		type.setName("Foo");

		assertThat(multNotified, hasItem(UMLPackage.Literals.NAMED_ELEMENT__NAME));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, hasItem(UMLPackage.Literals.NAMED_ELEMENT__NAME));
	}

	@Test
	public void removeType() {
		assumeThat("Test not applicable to non-TypedElement", multiplicityElement, instanceOf(TypedElement.class));
		TypedElement typed = (TypedElement) multiplicityElement;

		Type type = UMLFactory.eINSTANCE.createClass();
		typed.setType(type);

		reset();

		typed.setType(null);

		assertThat(multNotified, hasItem(UMLPackage.Literals.TYPED_ELEMENT__TYPE));
		assertThat(lowerNotified, not(hasItem(anything())));
		assertThat(upperNotified, not(hasItem(anything())));
		assertThat(typeNotified, hasItem(UMLPackage.Literals.TYPED_ELEMENT__TYPE));

		assertThat(type.eAdapters(), not(hasItem(fixture)));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ UMLPackage.Literals.CONNECTOR_END, "basic" },
				{ UMLPackage.Literals.PROPERTY, "property" },
		});
	}

	@Before
	public void createFixture() {
		if (multiplicityElement instanceof Property) {
			fixture = new MultiplicityElementAdapter.ForProperty() {
				@Override
				protected void handleTypeChanged(Notification notification) {
					typeNotified.add(notification.getFeature());
					super.handleTypeChanged(notification);
				}

				@Override
				protected void handleMultiplicityChanged(Notification notification) {
					multNotified.add(notification.getFeature());
				}

				@Override
				protected void handleLowerValueChanged(ValueSpecification lowerValue, EAttribute feature, Notification notification) {
					lowerNotified.add(feature);
					super.handleLowerValueChanged(lowerValue, feature, notification);
				}

				@Override
				protected void handleUpperValueChanged(ValueSpecification upperValue, EAttribute feature, Notification notification) {
					upperNotified.add(feature);
					super.handleUpperValueChanged(upperValue, feature, notification);
				}
			};
		} else {
			fixture = new MultiplicityElementAdapter() {
				@Override
				protected void handleMultiplicityChanged(Notification notification) {
					multNotified.add(notification.getFeature());
				}

				@Override
				protected void handleLowerValueChanged(ValueSpecification lowerValue, EAttribute feature, Notification notification) {
					lowerNotified.add(feature);
					super.handleLowerValueChanged(lowerValue, feature, notification);
				}

				@Override
				protected void handleUpperValueChanged(ValueSpecification upperValue, EAttribute feature, Notification notification) {
					upperNotified.add(feature);
					super.handleUpperValueChanged(upperValue, feature, notification);
				}
			};
		}

		fixture.adapt(multiplicityElement);
	}

	@After
	public void assertUnadapt() {
		reset();

		fixture.unadapt(multiplicityElement);

		Consumer<EObject> assertion = obj -> assertThat(obj.eAdapters(), not(hasItem(fixture)));

		assertion.accept(multiplicityElement);
		multiplicityElement.eCrossReferences().forEach(assertion);
		multiplicityElement.eContents().forEach(assertion);
	}

	void reset() {
		multNotified.clear();
		lowerNotified.clear();
		upperNotified.clear();
		typeNotified.clear();
	}
}
