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
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.junit.Test;

import com.google.common.collect.Iterators;

/**
 * Test cases for layout of capsule structure diagrams with inheritance.
 */
@ActiveDiagram("Capsule1")
public class CapsuleStructureLayoutTest extends AbstractInheritanceTest {

	public CapsuleStructureLayoutTest() {
		super();
	}

	/**
	 * Verify that the initial laying out of a Capsule Structure Diagram
	 * doesn't cause all of the ports to unfollow their parent diagram views.
	 */
	@Test
	@NeedsUIEvents // Flush UI events to let the deferred automatic layout run
	@PluginResource("resource/inheritance/initial-port-layout.di")
	public void initialLayout() {
		// Open the subclass's diagram for the first time
		editor.openDiagram("Capsule2");

		Iterators.filter(DiagramEditPartsUtil.getAllContents(getDiagramEditPart(), true), IInheritableEditPart.class)
				.forEachRemaining(ep -> assertThat("Edit part not following superclass", ep.isInherited(), is(true)));
	}
}
