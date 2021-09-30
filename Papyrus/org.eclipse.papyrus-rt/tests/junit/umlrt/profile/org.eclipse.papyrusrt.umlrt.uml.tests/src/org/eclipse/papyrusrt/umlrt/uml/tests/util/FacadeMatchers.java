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

package org.eclipse.papyrusrt.umlrt.uml.tests.util;

import java.util.Objects;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * Hamcrest matchers for assertions on UML-RT Façade model elements.
 */
public class FacadeMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private FacadeMatchers() {
		super();
	}

	public static <N extends UMLRTNamedElement> Matcher<N> named(String name) {
		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("façade named \"").appendText(name).appendText("\"");
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof UMLRTNamedElement)
						&& Objects.equals(((UMLRTNamedElement) item).getName(), name);
			}
		};
	}

	public static <M extends UMLRTReplicatedElement> Matcher<M> replicated(int replication) {
		return replicated(String.valueOf(replication));
	}

	public static <M extends UMLRTReplicatedElement> Matcher<M> replicated(String replication) {
		return new BaseMatcher<M>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(String.format("has [%s] replication", replication));
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				if (item instanceof UMLRTReplicatedElement) {
					UMLRTReplicatedElement repl = (UMLRTReplicatedElement) item;
					String desc = repl.getReplicationFactorAsString();
					description.appendText("was ").appendText(desc);
				} else {
					super.describeMismatch(item, description);
				}
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof UMLRTReplicatedElement)
						&& replication.equals(((UMLRTReplicatedElement) item).getReplicationFactorAsString());
			}
		};
	}

	public static <T extends UMLRTNamedElement> Matcher<T> redefines(T element) {
		return new BaseMatcher<T>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("redefines ").appendText(element.toUML().getQualifiedName());
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof UMLRTNamedElement) && (element instanceof UMLRTNamedElement)
						&& redefines((UMLRTNamedElement) item, (UMLRTNamedElement) element);
			}

			boolean redefines(UMLRTNamedElement element, UMLRTNamedElement redefined) {
				boolean result = element == redefined;

				if (!result) {
					result = element.redefines(redefined);
				}

				return result;
			}
		};
	}

	public static <T extends UMLRTNamedElement> Matcher<T> hasInheritance(UMLRTInheritanceKind kind) {
		return new BaseMatcher<T>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is ").appendText(kind.toString());
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof UMLRTNamedElement)
						&& (((UMLRTNamedElement) item).getInheritanceKind() == kind);
			}
		};
	}

	public static <T extends UMLRTNamedElement> Matcher<T> isInherited() {
		return hasInheritance(UMLRTInheritanceKind.INHERITED);
	}

	public static <T extends UMLRTNamedElement> Matcher<T> isRedefined() {
		return hasInheritance(UMLRTInheritanceKind.REDEFINED);
	}

	public static <T extends UMLRTNamedElement> Matcher<T> isExcluded() {
		return hasInheritance(UMLRTInheritanceKind.EXCLUDED);
	}

	public static <T extends UMLRTNamedElement> Matcher<T> isRootDefinition() {
		return hasInheritance(UMLRTInheritanceKind.NONE);
	}

	public static Matcher<UMLRTTrigger> receives(Matcher<? super UMLRTProtocolMessage> messageMatcher) {
		return new CustomTypeSafeMatcher<UMLRTTrigger>("receives message " + StringDescription.asString(messageMatcher)) {
			@Override
			protected boolean matchesSafely(UMLRTTrigger item) {
				return messageMatcher.matches(item.getProtocolMessage());
			}
		};
	}
}
