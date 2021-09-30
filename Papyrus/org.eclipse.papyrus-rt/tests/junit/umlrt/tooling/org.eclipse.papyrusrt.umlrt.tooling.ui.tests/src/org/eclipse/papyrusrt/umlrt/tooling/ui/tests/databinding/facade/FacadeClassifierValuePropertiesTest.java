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

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.databinding.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.DataBindingsRule;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.CapsuleProperties;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade.ProtocolProperties;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test suite for the observable value properties provided by the
 * {@link CapsuleProperties} and {@link ProtocolProperties} factories.
 */
@PluginResource("resource/modelelement/model.di")
public class FacadeClassifierValuePropertiesTest {
	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final TestRule dataBindings = new DataBindingsRule();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	public FacadeClassifierValuePropertiesTest() {
		super();
	}

	@Test
	public void getSuperclass() {
		UMLRTCapsule connected = UMLRTPackage.getInstance(modelSet.getModel()).getCapsule("ConnectedCapsule");
		UMLRTCapsule subcapsule = connected.getPackage().getCapsule("Subcapsule");

		IObservableValue<UMLRTCapsule> superclass = CapsuleProperties.superclass().observe(connected);
		assertThat("Found superclass", superclass.getValue(), nullValue());

		superclass = CapsuleProperties.superclass().observe(subcapsule);
		assertThat("Wrong superclass", superclass.getValue(), is(connected));
	}

	@Test
	public void setSuperclassNull() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(modelSet.getModel()).getCapsule("Capsule");
		UMLRTCapsule subcapsule = capsule.getPackage().getCapsule("Subcapsule");

		assumeThat("No initial superclass", subcapsule.getSuperclass(), notNullValue());

		IObservableValue<UMLRTCapsule> superclass = CapsuleProperties.superclass().observe(subcapsule);
		superclass.setValue(null);

		assertThat("Found superclass", superclass.getValue(), nullValue());
	}

	@Test
	public void setSuperclass() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(modelSet.getModel()).getCapsule("Capsule");
		UMLRTCapsule subcapsule = capsule.getPackage().getCapsule("Subcapsule");

		IObservableValue<UMLRTCapsule> superclass = CapsuleProperties.superclass().observe(subcapsule);
		superclass.setValue(capsule);

		assertThat("Wrong superclass", subcapsule.getSuperclass(), is(capsule));
	}

	@Test
	public void getSuperProtocol() {
		UMLRTProtocol protocol1 = UMLRTPackage.getInstance(modelSet.getModel()).getProtocol("Protocol1");
		UMLRTProtocol protocol2 = protocol1.getPackage().getProtocol("Protocol2");

		IObservableValue<UMLRTProtocol> supertype = ProtocolProperties.supertype().observe(protocol1);
		assertThat("Found supertype", supertype.getValue(), nullValue());

		supertype = ProtocolProperties.supertype().observe(protocol2);
		assertThat("Wrong supertype", supertype.getValue(), is(protocol1));
	}

	@Test
	public void setSuperProtoolNull() {
		UMLRTProtocol protocol2 = UMLRTPackage.getInstance(modelSet.getModel()).getProtocol("Protocol2");

		assumeThat("No initial supertype", protocol2.getSuperProtocol(), notNullValue());

		IObservableValue<UMLRTProtocol> supertype = ProtocolProperties.supertype().observe(protocol2);
		supertype.setValue(null);

		assertThat("Found supertype", supertype.getValue(), nullValue());
	}

	@Test
	public void setSuperProtocol() {
		UMLRTProtocol protocol2 = UMLRTPackage.getInstance(modelSet.getModel()).getProtocol("Protocol2");
		UMLRTProtocol newProtocol = modelSet.execute(() -> protocol2.getPackage().createProtocol("NewProtocol"));

		IObservableValue<UMLRTProtocol> supertype = ProtocolProperties.supertype().observe(protocol2);
		supertype.setValue(newProtocol);

		assertThat("Wrong supertype", protocol2.getSuperProtocol(), is(newProtocol));
	}
}
