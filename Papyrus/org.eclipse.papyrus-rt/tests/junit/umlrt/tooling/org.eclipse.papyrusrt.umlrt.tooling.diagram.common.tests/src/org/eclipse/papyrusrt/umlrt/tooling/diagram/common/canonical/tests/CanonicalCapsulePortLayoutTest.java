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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests;

import static org.eclipse.papyrusrt.junit.matchers.GeometryMatchers.near;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.uml2.uml.Port;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/capsules/canonical-ports.di")
@ActiveDiagram("Capsule1")
public class CanonicalCapsulePortLayoutTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("test::Capsule1");

	private final Rectangle capsuleFrame = new Rectangle(40, 40, 500, 250);

	@Named("port1")
	private Port port1;
	@Named("port2")
	private Port port2;
	@Named("port3")
	private Port port3;
	@Named("port4")
	private Port port4;
	@Named("port5")
	private Port port5;
	@Named("port6")
	private Port port6;
	@Named("port7")
	private Port port7;
	@Named("port8")
	private Port port8;
	@Named("port9")
	private Port port9;
	@Named("porta")
	private Port porta;
	@Named("portb")
	private Port portb;
	@Named("portc")
	private Port portc;
	@Named("portd")
	private Port portd;
	@Named("porte")
	private Port porte;
	@Named("portf")
	private Port portf;

	/**
	 * Verify the automatic layout of canonically presented ports on a capsule.
	 */
	@Test
	public void canonicalPortLayout_bug496649() {
		Set<Point> allCenters = allPorts().map(this::requireCenter).collect(Collectors.toSet());

		double stride = 16.0 * 7.0 / 5.0;
		List<Point> expected = new ArrayList<>(allCenters.size());
		for (double off = 0.0; off < 125.0; off += stride) {
			// Down the left side
			expected.add(capsuleFrame.getLeft().translate(0.0, off));
		}
		for (double off = 0.0; off < 125.0; off += stride) {
			// Down the right side
			expected.add(capsuleFrame.getRight().translate(0.0, off));
		}
		for (double off = -stride; off > 45; off -= stride) {
			// A few more up the left side
			expected.add(capsuleFrame.getLeft().translate(0.0, off));
		}

		expected.forEach(pt -> assertThat(allCenters, hasItem(near(pt, 3))));
	}

	//
	// Test framework
	//

	Point requireCenter(EObject object) {
		IGraphicalEditPart editPart = requireEditPart(object);
		return editPart.getFigure().getBounds().getCenter();
	}

	Stream<Port> allPorts() {
		return Stream.of(
				port1, port2, port3, port4, port5,
				port6, port7, port8, port9, porta,
				portb, portc, portd, porte, portf);
	}
}
