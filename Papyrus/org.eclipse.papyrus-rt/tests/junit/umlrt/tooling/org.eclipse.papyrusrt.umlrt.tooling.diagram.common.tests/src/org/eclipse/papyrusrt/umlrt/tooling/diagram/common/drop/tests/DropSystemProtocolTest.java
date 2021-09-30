/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 467545, 510188
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop.tests;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.junit.rules.ElementTypesResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests.PortEditingFixture;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Port;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * JUnit test for Class {@link DropSystemProtocolTest}
 */
@ElementTypesResource("/resource/types/umlrt-simple.elementtypesconfigurations")
@ActiveDiagram("Capsule1")
@PluginResource("resource/systemprotocols/model.di")
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class DropSystemProtocolTest extends AbstractPapyrusTest {



	private static final String UMLRT_CPP_ID = "umlrt-cpp";

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();
	private final PortEditingFixture ports = new PortEditingFixture(editor, "Capsule1");

	/**
	 * Constructor.
	 *
	 */
	public DropSystemProtocolTest() {

	}

	/*
	 * Check both drop inside and on the border of the capsule
	 */
	@Test
	public void checkSystemProtocolDropOnCapsule() throws Exception {


		EditPart part = editor.requireEditPart(editor.getActiveDiagram().getChildBySemanticHint("Class_Shape"), editor.getModel().getPackagedElement("Capsule1"));
		Rectangle bounds = ((ClassCompositeEditPart) part).getFigure().getBounds();
		Point topLeft = bounds.getTopLeft();

		// get the service
		IDefaultLanguageService service = null;

		service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, editor.getModel());


		assertNotNull("DefaultLanguage service should not be null", service);

		// get the default language
		IDefaultLanguage language = service.getActiveDefaultLanguage(editor.getModel());

		assertEquals("DefaultLanguage should be cpp", UMLRT_CPP_ID, language.getId());

		assertThat("Their is at least one System Protocol in the cpp library", !(language.getSystemProtocols(editor.getModel().eResource().getResourceSet()).isEmpty()));

		// get System Protocol
		Collaboration systemProtocol = language.getSystemProtocols(editor.getModel().eResource().getResourceSet()).iterator().next();
		assertThat(systemProtocol, notNullValue());
		// drop systemProtocol inside the capsule : drop command should be executable and create an SAP Port
		Port port = ports.dropSystemProtocolOnCapsule(topLeft.getTranslated(40, 40),
				systemProtocol);
		assertEquals("Created Port typed by SystemProtocol should be an SAP port", UMLRTPortKind.SAP, RTPortUtils.getPortKind(port));

		// drop systemProtocol on the border of the capsule : drop command should be not executable
		ports.assertCannotDropSystemProtocolOnBorder(topLeft, systemProtocol);

		editor.undo();
	}
}
