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

package org.eclipse.papyrusrt.umlrt.core.tests.inheritance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Named;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.uml2.uml.Collaboration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Regression test cases for deletion of protocols that inherit messages.
 * 
 * @see <a href="http://eclip.se/513078">bug 513078</a>
 */
@PluginResource("resource/inheritance/delete_subprotocol.di")
public class SubProtocolDeletionTest {

	@Rule
	public final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("model");

	@Named("Protocol2::Protocol2")
	Collaboration protocol2;

	public SubProtocolDeletionTest() {
		super();
	}

	@Test
	public void deleteSubtypeProtocol() {
		ICommand destroy = DeletionUtils.requireDestroyCommand(protocol2);
		model.execute(destroy);
		assertThat(protocol2.eResource(), nullValue());
	}

	@Test
	public void undoDeleteSubtypeProtocol() {
		model.repeat(3, () -> {
			ICommand destroy = DeletionUtils.requireDestroyCommand(protocol2);
			model.execute(destroy);

			assertThat(protocol2.eResource(), nullValue());

			model.undo();

			assertThat(protocol2.eResource(), is(model.getModelResource()));

			// And the details are all restored, too
			UMLRTProtocol facade = UMLRTProtocol.getInstance(protocol2);
			requireMessage(facade, "greet", UMLRTInheritanceKind.INHERITED);
			requireMessage(facade, "asyncGreet", UMLRTInheritanceKind.NONE);
			requireMessage(facade, "reply", UMLRTInheritanceKind.INHERITED);
			requireMessage(facade, "asyncReply", UMLRTInheritanceKind.NONE);
			requireMessage(facade, "ack", UMLRTInheritanceKind.INHERITED);
		});
	}

	//
	// Test framework
	//

	UMLRTProtocolMessage requireMessage(UMLRTProtocol protocol, String name, UMLRTInheritanceKind kind) {
		UMLRTProtocolMessage result = protocol.getMessage(name);
		assertThat("No such message: " + name, result, notNullValue());
		assertInheritance(result, kind);
		return result;
	}

	void assertInheritance(UMLRTNamedElement element, UMLRTInheritanceKind kind) {
		assertThat("Wrong inheritance", element.getInheritanceKind(), is(kind));

		Resource resource = ((InternalEObject) element.toUML()).eInternalResource();
		assertThat("Not attached", resource, notNullValue());

		if (kind == UMLRTInheritanceKind.INHERITED) {
			assertThat("Not an extension", resource, not(model.getModelResource()));
		} else {
			assertThat("Not persisted", resource, is(model.getModelResource()));
		}
	}
}
