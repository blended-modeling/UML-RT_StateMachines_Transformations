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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.PreferenceRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.Class;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Capsule-part inheritance test cases for the Capsule Structure Diagram.
 */
@PluginResource("resource/inheritance/parts.di")
@ActiveDiagram("Capsule1") // We usually start the test with some action in the superclass diagram
public class CapsuleStructureDiagramCapsulePartInheritanceTest extends AbstractInheritanceTest {

	@ClassRule
	public static final TestRule disableSnapToGrid = PreferenceRule.getSnapToGrid(false);

	public CapsuleStructureDiagramCapsulePartInheritanceTest() {
		super();
	}

	@Test
	public void followCapsulePart() {
		// Expand the part around its centre
		UMLRTCapsulePart part = getCapsulePart("capsule3");
		IGraphicalEditPart partEP = requireEditPart(part);
		resize(partEP, 20, 20);

		// Check that the part in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(part));
		assertFollowed(redefEP, partEP, NotationPackage.Literals.BOUNDS);

		undo();

		assertFollowed(redefEP, partEP, NotationPackage.Literals.BOUNDS);

		redo();

		assertFollowed(redefEP, partEP, NotationPackage.Literals.BOUNDS);
	}

	@Test
	public void unfollowPart() {
		UMLRTCapsulePart part = getCapsulePart("capsule3");
		IGraphicalEditPart partEP = requireEditPart(part);

		activateDiagram("Capsule2");
		UMLRTCapsulePart redef = getRedefinition(part);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(part);
		resize(partEP, 20, 20);

		// Check that the part in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(partEP, redefEP, NotationPackage.Literals.BOUNDS);
	}

	@Test
	public void autoUnfollowPart() {
		UMLRTCapsulePart part = getCapsulePart("capsule3");
		IGraphicalEditPart partEP = requireEditPart(part);

		activateDiagram("Capsule2");
		UMLRTCapsulePart redef = getRedefinition(part);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Resize the redefining part so that it will unfollow
		resize(redefEP, -10, -10);

		activateDiagram(part);
		resize(partEP, 20, 20);

		// Check that the part in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(partEP, redefEP, NotationPackage.Literals.BOUNDS);
	}

	@NeedsUIEvents
	@Test
	public void addNewPartToSuperclass() {
		UMLRTCapsulePart initialPart = getCapsulePart("capsule3");
		UMLRTCapsule owner = initialPart.getCapsule();

		UMLRTCapsulePart newPart = execute(() -> owner.createCapsulePart(owner.getPackage().createCapsule("NewCapsule")));

		// Place the new part
		IGraphicalEditPart partEP = requireEditPart(newPart);
		resize(partEP, 10, 10);

		// Check that the part in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newPart));
		assertFollowed(partEP, redefEP, NotationPackage.Literals.BOUNDS);
	}

	@Test
	@ActiveDiagram("Capsule2")
	public void partExclusion() {
		UMLRTCapsulePart part = getCapsule("Capsule2").getCapsulePart("capsule3");
		IGraphicalEditPart partEP = requireEditPart(part);

		exclude(part);

		partEP = getEditPart(part.toUML());
		assertThat(partEP, nullValue());
	}

	@Test
	public void partAppearance() {
		UMLRTCapsulePart part = getCapsulePart("capsule3");
		IGraphicalEditPart partEP = requireEditPart(part);

		IFigure figure = getCoreFigure(partEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the part in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTCapsulePart redefPart = getRedefinition(part);
		IGraphicalEditPart redefEP = requireEditPart(redefPart);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong capsule-part color", redefFig.getForegroundColor().getRGB(), not(fg));

		// Inherited capsule-part has normal label
		IGraphicalEditPart redefLabel = requireLabel(redefPart);
		IFigure redefLabelFig = getCoreFigure(redefLabel);
		FontData fontData = redefLabelFig.getFont().getFontData()[0];
		assertThat(fontData.getStyle() & SWT.BOLD, is(0));

		// But a redefined capsule-part that overrides the inherted type has a bold label
		execute(() -> redefPart.setType(redefPart.getType().getPackage().createCapsule("NewCapsule")));
		fontData = redefLabelFig.getFont().getFontData()[0];
		assertThat(fontData.getStyle() & SWT.BOLD, is(SWT.BOLD));
	}

	/**
	 * Verifies the initial layout of a new Capsule Structure Diagram's inherited parts.
	 * 
	 * @see <a href="http://eclip.se/514536">bug 514536</a>
	 */
	@Test
	@ActiveDiagram("Base1")
	public void initialLayoutOfInheritedParts() {
		UMLRTCapsule base1 = getCapsule("Base1");
		IGraphicalEditPart part1EP = requireEditPart(base1.getCapsulePart("part1"));
		IGraphicalEditPart part2EP = requireEditPart(base1.getCapsulePart("part2"));

		UMLRTPackage root = getRoot();
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root.toUML());
		assumeThat("No edit service for model root", edit, notNullValue());

		CreateElementRequest create = new CreateElementRequest(
				root.toUML(), UMLRTElementTypesEnumerator.CAPSULE);
		editor.execute(edit.getEditCommand(create));
		assumeThat("New element not available", create.getNewElement(), notNullValue());
		UMLRTCapsule newCapsule = UMLRTCapsule.getInstance((Class) create.getNewElement());

		edit = ElementEditServiceUtils.getCommandProvider(newCapsule.toUML());
		assumeThat("No edit service for new capsule", edit, notNullValue());
		CreateRelationshipRequest generalize = new CreateRelationshipRequest(
				newCapsule.toUML(), base1.toUML(), UMLElementTypes.GENERALIZATION);
		editor.execute(edit.getEditCommand(generalize));

		// Open the new capsule's diagram
		editor.openDiagram(newCapsule.getName());

		IGraphicalEditPart newPart1EP = requireEditPart(newCapsule.getCapsulePart("part1"));
		IGraphicalEditPart newPart2EP = requireEditPart(newCapsule.getCapsulePart("part2"));

		assertFollowed(newPart1EP, part1EP, NotationPackage.Literals.BOUNDS);
		assertFollowed(newPart2EP, part2EP, NotationPackage.Literals.BOUNDS);
	}

}
