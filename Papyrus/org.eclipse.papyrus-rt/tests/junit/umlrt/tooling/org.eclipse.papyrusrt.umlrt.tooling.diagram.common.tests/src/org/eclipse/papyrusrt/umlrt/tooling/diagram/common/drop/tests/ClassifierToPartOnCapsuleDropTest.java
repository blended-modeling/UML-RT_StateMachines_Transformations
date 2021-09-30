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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test primarily that classifiers for which we don't want to create parts
 * in a capsule cannot be dropped on a capsule.
 */
@ActiveDiagram("Capsule1")
@PluginResource("/resource/capsules/model.di")
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class ClassifierToPartOnCapsuleDropTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	private Point insideTheStructureCompartment = new Point(100, 100);

	/**
	 * Initializes me.
	 */
	public ClassifierToPartOnCapsuleDropTest() {
		super();
	}

	/**
	 * Control test: verify that we can drop a capsule onto a capsule.
	 * (would create a part)
	 */
	@Test
	public void dropCapsuleOnCapsule() {
		Command drop = getDropClassifierCommand(insideTheStructureCompartment,
				"test::Capsule2");
		assertThat("Cannot drop capsule", drop, isExecutable());
	}

	/**
	 * Control test: verify that we can drop a protocol onto a capsule.
	 * (would create an internal port)
	 */
	@Test
	public void dropProtocolOnCapsule() {
		Command drop = getDropProtocolCommand(insideTheStructureCompartment,
				"test::Greeter");
		assertThat("Cannot drop protocol", drop, isExecutable());
	}

	/**
	 * Verify that we cannot drop some other kind of classifier onto a capsule
	 * to create an attribute.
	 * 
	 * @see <a href="http://eclip.se/497755">bug 497755</a>
	 */
	@Test
	public void noDropClassOnCapsule() {
		Command drop = getDropClassifierCommand(insideTheStructureCompartment,
				"test::Thing");
		assertThat("Can drop class", drop, not(isExecutable()));
	}

	//
	// Test framework
	//

	/**
	 * Get a command to drop a classifier into a capsule structure to create a part.
	 */
	Command getDropClassifierCommand(Point mouse, String qualifiedName) {
		// Get the named classifier
		Collection<NamedElement> elements = UMLUtil.findNamedElements(editor.getModelResource(), qualifiedName);
		Classifier classifier = elements.stream()
				.filter(Classifier.class::isInstance).map(Classifier.class::cast)
				.findAny().get(); // This will fail the test if not found

		return getDropCommand(mouse, classifier);
	}

	private Command getDropCommand(Point mouse, EObject objectToDrop) {
		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;

		EditPart underMouse = classFrame.getViewer().findObjectAt(mouse);


		DropObjectsRequest request = new DropObjectsRequest();
		request.setLocation(mouse);
		request.setObjects(Collections.singletonList(objectToDrop));

		EditPart target = getTargetEditPart(underMouse, request);
		return target.getCommand(request);
	}

	private EditPart getTargetEditPart(EditPart editPart, Request request) {
		EditPart result = editPart;

		// Drill through text compartments
		while (result instanceof ITextAwareEditPart) {
			result = result.getParent();
		}

		result = result.getTargetEditPart(request);

		return result;
	}

	/**
	 * Get a command to drop a protocol, specifically. Finding protocols by name
	 * is different to finding other kinds of classifier.
	 */
	Command getDropProtocolCommand(Point mouse, String protocolName) {
		// Compute the actual protocol name
		protocolName = protocolName.replaceFirst("(::[^:]+)$", "$1$1");

		return getDropClassifierCommand(mouse, protocolName);
	}

}
