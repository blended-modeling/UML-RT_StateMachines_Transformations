/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   CEA LIST - bug 489624
 *   Christian W. Damus - bugs 496464, 467545, 510188
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests;

import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.types.advice.RTPortEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/capsules/model.di")
@ActiveDiagram("Capsule2")
public class CapsuleStructureDiagramTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();


	private Class capsule1;
	private Port capsule1_port1;
	private Port capsule1_port2;
	private Class capsule2;
	private Property capsule2_capsule1Part;

	/**
	 * Verify that on initial opening of a capsule structure diagram, only service ports
	 * are shown on capsule parts.
	 */
	@Test
	public void onlyServicePortsOnCapsulePartsInitially() {
		View partView = requireView(capsule2_capsule1Part, editor.getActiveDiagram().getNotationView());
		requireView(capsule1_port1, partView);
		assertNoView(capsule1_port2, partView);
	}

	/**
	 * Verify that when a port is changed to a service port, it pops into the
	 * capsule parts typed by its featuring capsules.
	 */
	@NeedsUIEvents
	@Test
	public void portBecomeServiceIsShown() {
		View partView = requireView(capsule2_capsule1Part, editor.getActiveDiagram().getNotationView());
		assumeNoView(capsule1_port2, partView); // Initial condition

		// Make it a kind of service port
		execute(new RTPortEditAccess().getSetKindCommand(capsule1_port2, UMLRTPortKind.SPP));

		requireView(capsule1_port2, partView); // And there it is

		// Make it a kind of legacy SPP port with isSerive= false : should be inside --> not shown in the part
		ICommand setisService = new SetValueCommand(new SetRequest(capsule1_port2, UMLPackage.eINSTANCE.getPort_IsService(), false));

		assertThat("Command is not executable", setisService != null && setisService.canExecute());
		execute(setisService);
		assumeNoView(capsule1_port2, partView); // And there it disappear

		ICommand setisServicetrue = new SetValueCommand(new SetRequest(capsule1_port2, UMLPackage.eINSTANCE.getPort_IsService(), true));

		assertThat("Command is not executable", setisService != null && setisServicetrue.canExecute());
		execute(setisServicetrue);
		requireView(capsule1_port2, partView); // And there it is
	}

	/**
	 * Verify that when a port is changed to a non-service port, it disappears
	 * from the capsule parts typed by its featuring capsules.
	 */
	@NeedsUIEvents
	@Test
	public void portBecomeNonServiceIsHidden() {
		View partView = requireView(capsule2_capsule1Part, editor.getActiveDiagram().getNotationView());
		assumeView(capsule1_port1, partView); // Initial condition

		// Make it a kind of non-service port
		execute(new RTPortEditAccess().getSetKindCommand(capsule1_port1, UMLRTPortKind.INTERNAL_BEHAVIOR));

		assertNoView(capsule1_port1, partView); // And now it is gone
	}

	/**
	 * Verify that when a simple attribute is added to a capsule, it is not
	 * visualized as a part.
	 */
	@Test
	public void newSimpleAttributeNotShown() {
		// Find the structure compartment
		View structure = (View) assumeView(capsule2_capsule1Part).eContainer();

		Property attribute = execute(() ->
		// It's not a proper capsule-part
		capsule2.createOwnedAttribute("attr1", capsule1));

		assertNoView(attribute, structure); // It isn't shown
	}

	/**
	 * Verify that when a capsule-part is added to a capsule, it is visualized
	 * as a part.
	 */
	@Test
	public void newCapsulePartIsShown() {
		// Find the structure compartment
		View structure = (View) assumeView(capsule2_capsule1Part).eContainer();

		Property newPart = execute(() -> {
			// Do this the hard way because we mustn't pop up a capsule
			// selection dialog
			Property result = capsule2.createOwnedAttribute("part2", capsule1);
			result.applyStereotype(result.getApplicableStereotype("UMLRealTime::CapsulePart"));
			result.setAggregation(AggregationKind.COMPOSITE_LITERAL);
			result.setVisibility(VisibilityKind.PROTECTED_LITERAL);
			((LiteralInteger) result.createLowerValue(null, null, UMLPackage.Literals.LITERAL_INTEGER)).setValue(1);
			((LiteralUnlimitedNatural) result.createUpperValue(null, null,
					UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL)).setValue(1);

			return result;
		});

		requireView(newPart, structure); // The new capsule-part is shown
	}


	/**
	 * Verify that when a capsule-part is removed (from the diagram), it is no longer visualized.
	 */
	@Test
	public void deletedCapsulePartIsNotShown() {

		View structure = (View) assumeView(capsule2_capsule1Part).eContainer(); // Find the structure compartment

		View partView = requireView(capsule2_capsule1Part, editor.getActiveDiagram().getNotationView()); // the part view

		IGraphicalEditPart propertyEditPart = getEditPart(partView.getElement()); // get the editPart to delete the view

		delete(propertyEditPart); // delete the view

		assertNoView(capsule2_capsule1Part, structure); // And now it is gone

	}


	//
	// Test framework
	//

	@Before
	public void getFixtures() {
		capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		capsule1_port1 = capsule1.getOwnedPort("port1", null);
		capsule1_port2 = capsule1.getOwnedPort("port2", null);
		capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");
		capsule2_capsule1Part = capsule2.getOwnedAttribute(null, capsule1);
	}

	private static class RTPortEditAccess extends RTPortEditHelperAdvice {
		ICommand getSetKindCommand(Port port, UMLRTPortKind kind) {
			SetRequest request = new SetRequest(port, null, null);
			request.setParameter(RTPortUtils.RTPORT_KIND_REQUEST_PARAMETER, kind);
			return getSetKindCommand(request);
		}

		@Override
		protected ICommand getSetKindCommand(SetRequest request) {
			return super.getSetKindCommand(request);
		}
	}
}
