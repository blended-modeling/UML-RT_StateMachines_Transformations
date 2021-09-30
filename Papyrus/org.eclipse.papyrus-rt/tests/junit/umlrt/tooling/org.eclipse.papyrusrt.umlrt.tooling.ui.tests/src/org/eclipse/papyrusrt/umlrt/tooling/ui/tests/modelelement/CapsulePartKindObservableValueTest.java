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

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.modelelement;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Named;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.CapsulePartKindObservableValue;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for the {@link CapsulePartKindObservableValue} class.
 */
@PluginResource("resource/modelelement/model.di")
public class CapsulePartKindObservableValueTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new UMLRTModelSetFixture();

	/** We are testing an observable, so give it a nice realm. */
	@Rule
	public final TestRule dataBindings = new DataBindingsRule();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("model");

	@CleanUp
	IObservableValue<UMLRTCapsulePartKind> fixture;

	@Named("ConnectedCapsule::capsule")
	Property property;

	public CapsulePartKindObservableValueTest() {
		super();
	}

	@Test
	public void defaultBoundsIsFixed() {
		// The model has explicit 1 values
		Command unset = SetCommand.create(modelSet.getEditingDomain(), property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, SetCommand.UNSET_VALUE);
		unset = unset.chain(SetCommand.create(modelSet.getEditingDomain(), property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, SetCommand.UNSET_VALUE));

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.FIXED));
	}

	@Test
	public void fixed() {
		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.FIXED));
	}

	@Test
	public void optional() {
		initReplication(0, 1);
		initAggregation(AggregationKind.COMPOSITE_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.OPTIONAL));
	}

	@Test
	public void plugin() {
		initReplication(0, 1);
		initAggregation(AggregationKind.SHARED_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.PLUG_IN));
	}

	@Test
	public void fixedMultiple() {
		initReplication(2, 2);
		initAggregation(AggregationKind.COMPOSITE_LITERAL);
		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.FIXED));
	}

	@Test
	public void optionalMultiple() {
		initReplication(0, 2);
		initAggregation(AggregationKind.COMPOSITE_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.OPTIONAL));
	}

	@Test
	public void pluginMultiple() {
		initReplication(0, 3);
		initAggregation(AggregationKind.SHARED_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.PLUG_IN));
	}

	@Test
	public void optionalUnbounded() {
		initReplication(2, LiteralUnlimitedNatural.UNLIMITED);
		initAggregation(AggregationKind.COMPOSITE_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.OPTIONAL));
	}

	@Test
	public void pluginUnbounded() {
		initReplication(3, LiteralUnlimitedNatural.UNLIMITED);
		initAggregation(AggregationKind.SHARED_LITERAL);

		assertThat("Wrong value", fixture.getValue(), is(UMLRTCapsulePartKind.PLUG_IN));
	}

	@Test
	public void setOptional() {
		fixture.setValue(UMLRTCapsulePartKind.OPTIONAL);

		assertReplication(0, 1);
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	@Test
	public void setPlugin() {
		fixture.setValue(UMLRTCapsulePartKind.PLUG_IN);

		assertReplication(0, 1);
		assertAggregation(AggregationKind.SHARED_LITERAL);
	}

	@Test
	public void setFixed() {
		initAggregation(AggregationKind.SHARED_LITERAL);
		initReplication(0, 1);

		fixture.setValue(UMLRTCapsulePartKind.FIXED);

		assertReplication(1, 1);
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	@Test
	public void setOptionalMultiple() {
		initReplication(3, 3);
		fixture.setValue(UMLRTCapsulePartKind.OPTIONAL);

		assertReplication(0, 3);
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	@Test
	public void setPluginMultiple() {
		initReplication(2, 2);
		fixture.setValue(UMLRTCapsulePartKind.PLUG_IN);

		assertReplication(0, 2);
		assertAggregation(AggregationKind.SHARED_LITERAL);
	}

	@Test
	public void setFixedMultiple() {
		initAggregation(AggregationKind.SHARED_LITERAL);
		initReplication(0, 3);

		fixture.setValue(UMLRTCapsulePartKind.FIXED);

		assertReplication(3, 3);
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	@Test
	public void setOptionalSymbolic() {
		initReplication("MAX", "MAX");
		fixture.setValue(UMLRTCapsulePartKind.OPTIONAL);

		assertReplication(0, "MAX");
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	@Test
	public void setPluginSymbolic() {
		initReplication("MAX", "MAX");
		fixture.setValue(UMLRTCapsulePartKind.PLUG_IN);

		assertReplication(0, "MAX");
		assertAggregation(AggregationKind.SHARED_LITERAL);
	}

	@Test
	public void setFixedSymbolic() {
		initAggregation(AggregationKind.SHARED_LITERAL);
		initReplication(0, "MAX");

		fixture.setValue(UMLRTCapsulePartKind.FIXED);

		assertReplication("MAX", "MAX");
		assertAggregation(AggregationKind.COMPOSITE_LITERAL);
	}

	//
	// Test framework
	//

	@Before
	@SuppressWarnings("unchecked")
	public void createFixture() {
		fixture = new CapsulePartKindObservableValue(
				UMLUtil.getStereotypeApplication(property, CapsulePart.class),
				modelSet.getEditingDomain());
	}

	void assertAggregation(AggregationKind aggregation) {
		assertThat("Wrong aggregation", property.getAggregation(), is(aggregation));
	}

	@SuppressWarnings("restriction")
	void assertReplication(int lower, int upper) {
		assertThat("Wrong kind of lower bound", property.getLowerValue(),
				either(nullValue()).or(instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class)));
		assertThat("Wrong lower bound", property.getLower(), is(lower));
		assertThat("Wrong kind of upper bound", property.getUpperValue(),
				either(nullValue()).or(instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class)));
		assertThat("Wrong upper bound", property.getUpper(), is(upper));
	}

	@SuppressWarnings("restriction")
	void assertReplication(String lower, String upper) {
		assertThat("No lower bound", property.getLowerValue(), notNullValue());
		assertThat("Wrong kind of lower bound", property.getLowerValue(), instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class));
		assertThat("Wrong lower bound", property.getLowerValue().stringValue(), is(lower));
		assertThat("No upper bound", property.getUpperValue(), notNullValue());
		assertThat("Wrong kind of upper bound", property.getUpperValue(), instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class));
		assertThat("Wrong upper bound", property.getUpperValue().stringValue(), is(upper));
	}

	@SuppressWarnings("restriction")
	void assertReplication(int lower, String upper) {
		assertThat("No lower bound", property.getLowerValue(), notNullValue());
		assertThat("Wrong kind of lower bound", property.getLowerValue(), instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class));
		assertThat("Wrong lower bound", property.getLowerValue().integerValue(), is(lower));
		assertThat("No upper bound", property.getUpperValue(), notNullValue());
		assertThat("Wrong kind of upper bound", property.getUpperValue(), instanceOf(org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement.class));
		assertThat("Wrong upper bound", property.getUpperValue().stringValue(), is(upper));
	}

	void initAggregation(AggregationKind aggregation) {
		EditingDomain domain = modelSet.getEditingDomain();

		Command command = SetCommand.create(domain, property, UMLPackage.Literals.PROPERTY__AGGREGATION, aggregation);

		modelSet.execute(command);
	}

	void initReplication(int lower, int upper) {
		EditingDomain domain = modelSet.getEditingDomain();

		Command command = SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER, lower);
		command = command.chain(SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER, upper));

		modelSet.execute(command);
	}

	void initReplication(String lower, String upper) {
		EditingDomain domain = modelSet.getEditingDomain();

		OpaqueExpression lowerValue = (OpaqueExpression) ModelUtils.create(property, UMLPackage.Literals.OPAQUE_EXPRESSION);
		lowerValue.getBodies().add(lower);
		lowerValue.getLanguages().add("C++");
		OpaqueExpression upperValue = (OpaqueExpression) ModelUtils.create(property, UMLPackage.Literals.OPAQUE_EXPRESSION);
		upperValue.getBodies().add(upper);
		upperValue.getLanguages().add("C++");

		Command command = SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, lowerValue);
		command = command.chain(SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, upperValue));

		modelSet.execute(command);
	}

	void initReplication(int lower, String upper) {
		EditingDomain domain = modelSet.getEditingDomain();

		OpaqueExpression upperValue = (OpaqueExpression) ModelUtils.create(property, UMLPackage.Literals.OPAQUE_EXPRESSION);
		upperValue.getBodies().add(upper);
		upperValue.getLanguages().add("C++");

		Command command = SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER, lower);
		command = command.chain(SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, upperValue));

		modelSet.execute(command);
	}
}
