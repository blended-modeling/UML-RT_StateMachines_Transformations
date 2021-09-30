/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.test;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.IStackedFigure;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Class to test the use of the Stack pattern according to the replication factor of Capsule Part and Port
 *
 */

@PluginResource("resource/capsule-parts/model.di")
@ActiveDiagram("Capsule1")
public class RTPropertyStackPatternTest extends AbstractCanonicalTest {


	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	private Class capsule1;
	private Class capsule2;
	private Property capsule2Part;
	private Port greeterPort;

	//
	// Test framework
	//
	@Before
	public void getFixtures() {
		capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");
		capsule2Part = capsule1.getOwnedAttribute(null, capsule2);
		greeterPort = capsule1.getOwnedPort("greeter", null);
	}

	//
	// Tests
	//

	/**
	 * Verify the use of the stack pattern
	 */
	@Test
	@NeedsUIEvents
	public void testStackPattern() {


		testStackPattern(capsule2Part);
		testStackPattern(greeterPort);


	}

	private void testStackPattern(Property property) {

		IFigure propertyFigure = null;
		EditPart propertyEditPart = editor.requireEditPart(editor.getActiveDiagram(), property);
		assertThat(propertyEditPart, instanceOf(IGraphicalEditPart.class));
		if (propertyEditPart instanceof RTPortEditPart) {
			propertyFigure = ((RTPortEditPart) propertyEditPart).getPrimaryShape();
		} else if (propertyEditPart instanceof RTPropertyPartEditPart) {
			propertyFigure = ((RTPropertyPartEditPart) propertyEditPart).getPrimaryShape();
		}

		if (propertyFigure != null && propertyFigure instanceof IStackedFigure) {

			IStackedFigure stackedPropertyFigure = (IStackedFigure) propertyFigure;

			// no stack pattern is shown on figure: default multiplicity = 1 [1..1]
			Assert.assertFalse("property figure should not be stacked", stackedPropertyFigure.isStack());

			// change the multiplicity to [0..1]
			setLowerValue(property, 0);
			// no stack pattern is shown on figure
			Assert.assertFalse("property figure should not be stacked", stackedPropertyFigure.isStack());


			// change the multiplicity to [0..*]
			setUpperValue(property, -1);
			// now a stack pattern is shown
			Assert.assertTrue("property figure should be stacked", stackedPropertyFigure.isStack());


			// change the multiplicity to [0..2]
			setUpperValue(property, 2);
			// now a stack pattern is shown
			Assert.assertTrue("property figure should be stacked", stackedPropertyFigure.isStack());


			// change the multiplicity to [2..2]
			setLowerValue(property, 2);
			// now a stack pattern is shown
			Assert.assertTrue("property figure should be stacked", stackedPropertyFigure.isStack());


			// change the multiplicity to [0..MAX]
			setLowerValue(property, 0);
			setUpperValue(property, "MAX");
			// now a stack pattern is shown
			Assert.assertTrue("property figure should be stacked", stackedPropertyFigure.isStack());


			// change the multiplicity to [MAX..MAX]
			setLowerValue(property, "MAX");
			// now a stack pattern is shown
			Assert.assertTrue("property figure should be stacked", stackedPropertyFigure.isStack());
		}
	}

	/**
	 * @param replication
	 */
	private void setUpperValue(Property property, Object replication) {
		ValueSpecification upperValue = null;
		if (replication instanceof Integer) {
			if ((int) replication == -1) {
				upperValue = (LiteralUnlimitedNatural) ModelUtils.create(property, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL);
				((LiteralUnlimitedNatural) upperValue).setValue((int) replication);
			} else {
				upperValue = (LiteralInteger) ModelUtils.create(property, UMLPackage.Literals.LITERAL_INTEGER);
				((LiteralInteger) upperValue).setValue((int) replication);
			}

		} else if (replication instanceof String) {
			upperValue = (OpaqueExpression) ModelUtils.create(property, UMLPackage.Literals.OPAQUE_EXPRESSION);
			((OpaqueExpression) upperValue).getBodies().add("MAX");
			((OpaqueExpression) upperValue).getLanguages().add("C++");
		}


		ICommand command = getSetCommand(property, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), upperValue);
		assertThat("No executable command ", command, isExecutable());
		editor.execute(command);

	}

	/**
	 * @param replication
	 */
	private void setLowerValue(Property property, Object replication) {

		ValueSpecification lowerValue = null;
		if (replication instanceof Integer) {
			lowerValue = (LiteralInteger) ModelUtils.create(property, UMLPackage.Literals.LITERAL_INTEGER);
			((LiteralInteger) lowerValue).setValue((int) replication);
		} else if (replication instanceof String) {
			lowerValue = (OpaqueExpression) ModelUtils.create(property, UMLPackage.Literals.OPAQUE_EXPRESSION);
			((OpaqueExpression) lowerValue).getBodies().add("MAX");
			((OpaqueExpression) lowerValue).getLanguages().add("C++");
		}
		ICommand command = getSetCommand(property, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), lowerValue);
		assertThat("No executable command ", command, isExecutable());
		editor.execute(command);
	}

	private ICommand getSetCommand(EObject objectToTest, EStructuralFeature featureToModify, Object newValue) {
		SetRequest setRequest = new SetRequest(editor.getEditingDomain(), objectToTest, featureToModify, newValue);
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(objectToTest);

		if (provider != null) {
			return provider.getEditCommand(setRequest);
		}

		fail("impossible to find command provider");
		return null;
	}

}
