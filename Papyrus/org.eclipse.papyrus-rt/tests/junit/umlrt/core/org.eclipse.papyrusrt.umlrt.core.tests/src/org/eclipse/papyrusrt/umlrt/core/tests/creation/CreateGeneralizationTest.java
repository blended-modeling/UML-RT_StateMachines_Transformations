/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.junit.utils.CreationUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for UML-RT classifier generalization scenarios.
 */
@PluginResource("resource/TestModel.di")
public class CreateGeneralizationTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture fixture = new UMLRTModelSetFixture();

	public CreateGeneralizationTest() {
		super();
	}

	@Test
	public void createCapsuleGeneralization() throws Exception {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule0 = model.getCapsule("Capsule0");
		UMLRTCapsule newCapsule = fixture.execute(() -> model.createCapsule("NewCapsule"));

		Command create = CreationUtils.getCreateRelationshipCommand(newCapsule.toUML(),
				UMLElementTypes.GENERALIZATION, capsule0.toUML(),
				true, fixture.getEditingDomain());
		fixture.execute(create);

		assertThat(newCapsule.getSuperclass(), is(capsule0));
	}

	@Test
	public void createSecondCapsuleGeneralization() throws Exception {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule0 = model.getCapsule("Capsule0");
		UMLRTCapsule newCapsule = fixture.execute(() -> model.createCapsule("NewCapsule"));

		fixture.execute(() -> newCapsule.setSuperclass(capsule0));

		CreationUtils.getCreateRelationshipCommand(newCapsule.toUML(),
				UMLElementTypes.GENERALIZATION, capsule0.toUML(),
				false, fixture.getEditingDomain());
	}

	@Test
	public void createProtocolGeneralization() throws Exception {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTProtocol protocol0 = model.getProtocol("Protocol0");
		UMLRTProtocol newProtocol = fixture.execute(() -> model.createProtocol("NewProtocol"));

		Command create = CreationUtils.getCreateRelationshipCommand(newProtocol.toUML(),
				UMLElementTypes.GENERALIZATION, protocol0.toUML(),
				true, fixture.getEditingDomain());
		fixture.execute(create);

		assertThat(newProtocol.getSuperProtocol(), is(protocol0));
	}

	@Test
	public void createSecondProtocolGeneralization() throws Exception {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTProtocol protocol0 = model.getProtocol("Protocol0");
		UMLRTProtocol newProtocol = fixture.execute(() -> model.createProtocol("NewProtocol"));

		fixture.execute(() -> newProtocol.setSuperProtocol(protocol0));

		CreationUtils.getCreateRelationshipCommand(newProtocol.toUML(),
				UMLElementTypes.GENERALIZATION, protocol0.toUML(),
				false, fixture.getEditingDomain());
	}

}
