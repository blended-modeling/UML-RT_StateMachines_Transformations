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

package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Regression test cases for automatic redefinition of model elements by edits.
 */
@PluginResource("resource/inheritance/connectors.di")
@RunWith(ScenarioRunner.class)
public class AutoRedefinitionTest {

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	public AutoRedefinitionTest() {
		super();
	}

	@Scenario({ "edit", "undo", "redo" })
	public void autoRedefine() {
		UMLRTCapsule capsule = getCapsule("Subsubcapsule");
		UMLRTPort port = capsule.getPort("protocol1");

		assumeThat("Port is not inherited", port.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));

		modelSet.execute(() -> port.setReplicationFactor(2));

		if (verificationPoint()) {
			assertThat("Port is not redefined", port.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
			assertThat("Port is not in the model", resourceOf(port), is(resourceOf(getRoot())));
			assertThat("RTPort stereotype application is not in the model",
					rtStereotype(port).eInternalResource(), is(resourceOf(getRoot())));
		}

		modelSet.undo();

		if (verificationPoint()) {
			assertThat("Port is not inherited", port.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
			assertThat("Port is in the model", resourceOf(port), not(resourceOf(getRoot())));
			assertThat("RTPort stereotype application is in the model",
					rtStereotype(port).eInternalResource(), not(resourceOf(getRoot())));
		}

		modelSet.redo();

		if (verificationPoint()) {
			assertThat("Port is not redefined", port.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
			assertThat("Port is not in the model", resourceOf(port), is(resourceOf(getRoot())));
			assertThat("RTPort stereotype application is not in the model",
					rtStereotype(port).eInternalResource(), is(resourceOf(getRoot())));
		}
	}

	//
	// Test framework
	//

	UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(modelSet.getModel());
	}

	UMLRTCapsule getCapsule(String name) {
		return getRoot().getCapsule(name);
	}

	/** Gets the actual, as EMF sees it, resource containing an object. */
	Resource resourceOf(UMLRTNamedElement element) {
		return ((InternalEObject) element.toUML()).eInternalResource();
	}

	InternalEObject rtStereotype(UMLRTNamedElement element) {
		UMLRTSwitch<Class<? extends EObject>> stereotypeSwitch = new UMLRTSwitch<Class<? extends EObject>>() {
			@Override
			public Class<? extends EObject> caseCapsule(UMLRTCapsule object) {
				return Capsule.class;
			}

			@Override
			public Class<? extends EObject> casePort(UMLRTPort object) {
				return RTPort.class;
			}

			@Override
			public Class<? extends EObject> caseCapsulePart(UMLRTCapsulePart object) {
				return CapsulePart.class;
			}

			@Override
			public Class<? extends EObject> caseProtocol(UMLRTProtocol object) {
				return Protocol.class;
			}
		};

		return (InternalEObject) UMLUtil.getStereotypeApplication(element.toUML(), stereotypeSwitch.doSwitch(element));
	}

}
