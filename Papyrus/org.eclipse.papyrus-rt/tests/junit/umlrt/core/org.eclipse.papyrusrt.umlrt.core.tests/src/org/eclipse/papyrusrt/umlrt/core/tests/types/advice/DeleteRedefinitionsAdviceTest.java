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

package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.utils.RequestCacheEntries;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the advice that handles deletion (destruction) of redefinitions.
 */
@PluginResource("resource/inheritance/connectors.di")
@RunWith(Parameterized.class)
public class DeleteRedefinitionsAdviceTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	private final TestElementKind elementKind;
	private final UMLRTInheritanceKind inheritanceKind;

	public DeleteRedefinitionsAdviceTest(TestElementKind elementKind, UMLRTInheritanceKind inheritanceKind) {
		super();

		this.elementKind = elementKind;
		this.inheritanceKind = inheritanceKind;
	}

	@Test
	public void destroyGeneralization() {
		UMLRTClassifier classifier = classifier();
		UMLRTClassifier intermediate = getSupertype(classifier);

		Generalization generalization = intermediate.toUML().getGeneralizations().get(0);

		UMLRTNamedElement member = element();

		destroy(generalization);

		assertDestroyed(member);
	}

	@Test
	public void destroyRootDefinition() {
		UMLRTNamedElement member = element();

		destroy(member.getRootDefinition());

		assertDestroyed(member);
	}

	@Test
	public void destroySpecificNamespace() {
		UMLRTNamedElement member = element();

		destroy(member.getRedefinitionContext());

		assertDestroyed(member);
	}

	@Test
	public void tryToDestroyRedefinition() {
		UMLRTNamedElement member = element();

		assertThat("Can destroy non-root definition", getDestroyCommand(member), not(isExecutable()));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}, {1}")
	public static Iterable<Object[]> parameters() {
		List<UMLRTInheritanceKind> inheritances = Arrays.asList(
				UMLRTInheritanceKind.INHERITED, UMLRTInheritanceKind.REDEFINED, UMLRTInheritanceKind.EXCLUDED);

		// Cross product of inheritable element kind and inheritance kind
		return Stream.of(TestElementKind.values())
				.flatMap(v -> inheritances.stream().map(i -> new Object[] { v, i }))
				.collect(Collectors.toList());
	}

	<T extends UMLRTClassifier> T getSupertype(UMLRTClassifier classifier) {
		return new UMLRTSwitch<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T caseCapsule(UMLRTCapsule object) {
				return (T) object.getSuperclass();
			}

			@SuppressWarnings("unchecked")
			@Override
			public T caseProtocol(UMLRTProtocol object) {
				return (T) object.getSuperProtocol();
			}
		}.doSwitch(classifier);
	}

	UMLRTClassifier classifier() {
		return elementKind.getTestClassifier(UMLRTPackage.getInstance(model.getModel()));
	}

	UMLRTNamedElement element() {
		return elementKind.getTestElement(model, classifier(), inheritanceKind);
	}

	void destroy(UMLRTNamedElement element) {
		destroy(element.toUML());
	}

	void destroy(Element element) {
		ICommand destroy = getDestroyCommand(element);
		assertThat(destroy, isExecutable());
		model.execute(destroy);
	}

	ICommand getDestroyCommand(UMLRTNamedElement element) {
		return getDestroyCommand(element.toUML());
	}

	ICommand getDestroyCommand(Element element) {
		DestroyElementRequest request = new DestroyElementRequest(element, false);

		// Prime the cache maps as the Delete action in the UI does
		Map<String, Object> cacheMaps = new HashMap<>();
		try {
			RequestCacheEntries.initializeEObjCache(element, cacheMaps);
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Failed to initialize destroy command: " + e.getMessage());
		}
		request.setParameter(RequestCacheEntries.Cache_Maps, cacheMaps);
		return ElementEditServiceUtils.getCommandProvider(element).getEditCommand(request);
	}

	void assertDestroyed(UMLRTNamedElement element) {
		assertDestroyed(element.toUML());
	}

	void assertDestroyed(Element element) {
		assertThat("Not destroyed", element.eContainer(), nullValue());
		assertThat("Not detached", element.eResource(), nullValue());
	}

	enum TestElementKind {
		PORT, CAPSULE_PART, CONNECTOR, PROTOCOL_MESSAGE;

		/** Get a subtype of more than first order. */
		UMLRTClassifier getTestClassifier(UMLRTPackage model) {
			switch (this) {
			case PORT:
			case CAPSULE_PART:
			case CONNECTOR:
				return model.getCapsule("Subsubcapsule");
			case PROTOCOL_MESSAGE:
				return model.getProtocol("Subsubprotocol");
			default:
				throw new AssertionError("Invalid test element kind: " + this.name());
			}
		}

		/** Get an element to verify its destruction. */
		UMLRTNamedElement getTestElement(UMLRTModelSetFixture fixture, UMLRTClassifier classifier, UMLRTInheritanceKind inheritance) {
			UMLRTNamedElement result;

			switch (this) {
			case PORT:
				result = ((UMLRTCapsule) classifier).getPort("protocol1");
				break;
			case CAPSULE_PART:
				result = ((UMLRTCapsule) classifier).getCapsulePart("nestedCapsule");
				break;
			case CONNECTOR:
				result = ((UMLRTCapsule) classifier).getConnector("connector1");
				break;
			case PROTOCOL_MESSAGE:
				result = ((UMLRTProtocol) classifier).getMessage("greet");
				break;
			default:
				throw new AssertionError("Invalid test element kind: " + this.name());
			}

			switch (inheritance) {
			case REDEFINED:
				// Just change the name to redefine it
				fixture.execute(() -> result.setName("$redefined$"));
				break;
			case EXCLUDED:
				fixture.execute(() -> result.exclude());
				break;
			default:
				// Nothing to do for the inherited (or local) element
				break;
			}

			return result;
		}

		@Override
		public String toString() {
			switch (this) {
			case PORT:
				return "port";
			case CAPSULE_PART:
				return "capsule-part";
			case CONNECTOR:
				return "connector";
			case PROTOCOL_MESSAGE:
				return "protocol-message";
			default:
				throw new AssertionError("Invalid test element kind: " + this.name());
			}
		}
	}
}
