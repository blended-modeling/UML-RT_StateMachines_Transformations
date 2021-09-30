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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import javax.inject.Named;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.PropertyReplicationObservableValue;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for the {@link PropertyReplicationObservableValue} class.
 */
@PluginResource("resource/modelelement/model.di")
public class PropertyReplicationObservableValueTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	// Yes, it's heavyweight, but the observable needs the default language service
	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	/** We are testing an observable, so give it a nice realm. */
	@Rule
	public final TestRule dataBindings = new DataBindingsRule();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("model");

	@CleanUp
	IObservableValue<String> fixture;

	@CleanUp
	IObservableValue<String> capsulePartFixture;

	@Named("Capsule::externalBehavior")
	Property property;

	@Named("ConnectedCapsule::capsule")
	Property capsulePart;

	public PropertyReplicationObservableValueTest() {
		super();
	}

	@Test
	public void setIntegerReplication() {
		fixture.setValue("4");

		assertReplication(4);
	}

	@Test
	public void setSymbolicReplication() {
		fixture.setValue("MAX_REPLICATION");

		assertReplication("MAX_REPLICATION");
	}

	@Test
	public void valueSpecificationsUnnamed() {
		fixture.setValue("4");

		assumeThat(property.getLowerValue(), notNullValue());
		assertThat(property.getLowerValue().getName(), nullValue());
		assumeThat(property.getUpperValue(), notNullValue());
		assertThat(property.getUpperValue().getName(), nullValue());

		fixture.setValue("MAX_REPLICATION");

		assumeThat(property.getLowerValue(), notNullValue());
		assertThat(property.getLowerValue().getName(), nullValue());
		assumeThat(property.getUpperValue(), notNullValue());
		assertThat(property.getUpperValue().getName(), nullValue());
	}

	@Test
	public void changeNumericReplication() {
		initReplication(4);

		ValueSpecification lowerValue = property.getLowerValue();
		ValueSpecification upperValue = property.getUpperValue();

		fixture.setValue("7");

		assertReplication(7);

		assertThat("Lower value replaced", property.getLowerValue(), sameInstance(lowerValue));
		assertThat("Upper value replaced", property.getUpperValue(), sameInstance(upperValue));
	}

	@Test
	public void changeNumericReplicationToSymbolic() {
		initReplication(4);

		fixture.setValue("MAX_REPLICATION");

		assertReplication("MAX_REPLICATION");
	}

	@Test
	public void changeSymbolicReplication() {
		initReplication("MAX_REPLICATION");

		ValueSpecification lowerValue = property.getLowerValue();
		ValueSpecification upperValue = property.getUpperValue();

		fixture.setValue("NUM_PORTS");

		assertReplication("NUM_PORTS");

		assertThat("Lower value replaced", property.getLowerValue(), sameInstance(lowerValue));
		assertThat("Upper value replaced", property.getUpperValue(), sameInstance(upperValue));
	}

	@Test
	public void changeSymbolicReplicationToNumeric() {
		initReplication("MAX_REPLICATION");

		fixture.setValue("4");

		assertReplication(4);
	}

	@Test
	public void resetNumericReplication() {
		initReplication(4);

		fixture.setValue("None (1)");

		assertDefaultReplication();

		// initial replication is 1 in the test model
		capsulePartFixture.setValue("None (1)");

		assertThat("Aggregation is not composite", capsulePart.getAggregation(), is(AggregationKind.COMPOSITE_LITERAL));
	}

	@Test
	public void resetSymbolicReplication() {
		initReplication("MAX_REPLICATION");

		fixture.setValue("None (1)");

		assertDefaultReplication();
	}

	//
	// Test framework
	//

	@Before
	@SuppressWarnings("unchecked")
	public void createFixture() {
		fixture = new PropertyReplicationObservableValue(property, modelSet.getEditingDomain());
		capsulePartFixture = new PropertyReplicationObservableValue(capsulePart, modelSet.getEditingDomain());

	}

	void assertDefaultReplication() {
		assertThat("Has lower bound", property.getLowerValue(), nullValue());
		assertThat("Wrong lower bound", property.getLower(), is(1));
		assertThat("Has upper bound", property.getUpperValue(), nullValue());
		assertThat("Wrong upper bound", property.getUpper(), is(1));

		assertThat("Wrong observable value", fixture.getValue(), is("None (1)"));
	}

	void assertReplication(int replication) {
		assertThat("Wrong lower bound", property.getLower(), is(replication));
		assertThat("Wrong upper bound", property.getUpper(), is(replication));

		assertThat("Wrong observable value", fixture.getValue(), is(String.valueOf(replication)));
	}

	void assertReplication(String replication) {
		assertThat("No lower bound", property.getLowerValue(), notNullValue());
		assertThat("Wrong lower bound", property.getLowerValue().stringValue(), is(replication));
		assertThat("No upper bound", property.getUpperValue(), notNullValue());
		assertThat("Wrong upper bound", property.getUpperValue().stringValue(), is(replication));

		assertThat("Wrong observable value", fixture.getValue(), is(String.valueOf(replication)));
	}

	void initReplication(int replication) {
		EditingDomain domain = modelSet.getEditingDomain();

		Command command = SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER, replication);
		command = command.chain(SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER, replication));

		modelSet.execute(command);
	}

	void initReplication(String replication) {
		EditingDomain domain = modelSet.getEditingDomain();
		EFactory factory = modelSet.getResourceSet().getPackageRegistry().getEFactory(UMLPackage.eNS_URI);

		OpaqueExpression lowerValue = (OpaqueExpression) factory.create(UMLPackage.Literals.OPAQUE_EXPRESSION);
		lowerValue.getBodies().add(replication);
		lowerValue.getLanguages().add("C++");
		OpaqueExpression upperValue = (OpaqueExpression) factory.create(UMLPackage.Literals.OPAQUE_EXPRESSION);
		upperValue.getBodies().add(replication);
		upperValue.getLanguages().add("C++");


		Command command = SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, lowerValue);
		command = command.chain(SetCommand.create(domain, property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, upperValue));

		modelSet.execute(command);
	}
}
