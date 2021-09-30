/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.not;

import java.util.Objects;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Stereotype;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest matchers for assertions on UML model elements.
 */
public class UMLMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private UMLMatchers() {
		super();
	}

	public static <N extends NamedElement> Matcher<N> named(String name) {
		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("element named \"").appendText(name).appendText("\"");
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof NamedElement)
						&& Objects.equals(((NamedElement) item).getName(), name);
			}
		};
	}

	public static <M extends MultiplicityElement> Matcher<M> replicated(int replication) {
		return replicated(replication, replication);
	}

	public static <M extends MultiplicityElement> Matcher<M> replicated(int lower, int upper) {
		return new BaseMatcher<M>() {
			@Override
			public void describeTo(Description description) {
				String desc = toString(lower, upper);
				description.appendText(desc);
			}

			private String toString(int lower, int upper) {
				String result;

				if (upper == lower) {
					result = String.format("has [%d] replication", upper);
				} else if (upper < 0) {
					if (lower <= 0) {
						result = String.format("has [*] replication", lower);
					} else {
						result = String.format("has [%d..*] replication", lower);
					}
				} else {
					result = String.format("has [%d..%d] replication", lower, upper);
				}

				return result;
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				if (item instanceof MultiplicityElement) {
					MultiplicityElement mult = (MultiplicityElement) item;
					String desc = toString(mult.getLower(), mult.getUpper());
					description.appendText("was ").appendText(desc);
				} else {
					super.describeMismatch(item, description);
				}
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof MultiplicityElement)
						&& (((MultiplicityElement) item).getLower() == lower)
						&& (((MultiplicityElement) item).getUpper() == upper);
			}
		};
	}

	public static Matcher<Element> stereotypedAs(String qualifiedName) {
		return new BaseMatcher<Element>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("element stereotyped as ").appendText(qualifiedName);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Element)
						&& (((Element) item).getAppliedStereotype(qualifiedName) != null);
			}
		};
	}

	public static Matcher<Element> hasStereotypeValue(String stereotypeQName, String propertyName, Matcher<?> valueMatcher) {
		return new BaseMatcher<Element>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("element has <<").appendText(stereotypeQName)
						.appendText(">>::").appendText(propertyName).appendText(" ");
				description.appendDescriptionOf(valueMatcher);
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof Element) {
					Element element = (Element) item;
					Stereotype stereo = element.getAppliedStereotype(stereotypeQName);
					if (stereo != null) {
						Object value = element.getValue(stereo, propertyName);
						result = valueMatcher.matches(value);
					}
				}

				return result;
			}
		};
	}

	public static <R extends RedefinableElement> Matcher<R> redefines(R element) {
		return new BaseMatcher<R>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("redefines ").appendText(element.getQualifiedName());
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof RedefinableElement)
						&& redefines((RedefinableElement) item, element);
			}

			boolean redefines(RedefinableElement element, RedefinableElement redefined) {
				boolean result = element == redefined;

				if (!result) {
					result = element.getRedefinedElements().stream()
							.anyMatch(r -> redefines(r, redefined));
				}

				return result;
			}
		};
	}

	public static <T extends NamedElement> Matcher<T> redefines(T element) {
		return new BaseMatcher<T>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("redefines ").appendText(element.getQualifiedName());
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof InternalUMLRTElement) && (element instanceof InternalUMLRTElement)
						&& redefines((InternalUMLRTElement) item, (InternalUMLRTElement) element);
			}

			boolean redefines(InternalUMLRTElement element, InternalUMLRTElement redefined) {
				boolean result = element == redefined;

				if (!result) {
					result = element.rtGetRedefinedElement() == redefined;
				}

				return result;
			}
		};
	}

	public static <T extends Element> Matcher<T> hasInheritance(UMLRTInheritanceKind kind) {
		return new BaseMatcher<T>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is ").appendText(kind.toString());
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof Element)
						&& (UMLRTInheritanceKind.of((Element) item) == kind);
			}
		};
	}

	public static <T extends Element> Matcher<T> isInherited() {
		return hasInheritance(UMLRTInheritanceKind.INHERITED);
	}

	public static <T extends Element> Matcher<T> isRedefined() {
		Matcher<T> result = hasInheritance(UMLRTInheritanceKind.REDEFINED);

		result = both(result).and(
				either(not(CoreMatchers.<Element> instanceOf(RedefinableElement.class))).or(
						stereotypedAs("UMLRealTime::RTRedefinedElement")));

		return result;
	}

	public static <T extends Element> Matcher<T> isExcluded() {
		return hasInheritance(UMLRTInheritanceKind.EXCLUDED);
	}

	public static <T extends Element> Matcher<T> isRootDefinition() {
		return hasInheritance(UMLRTInheritanceKind.NONE);
	}

	public static <N extends Number & Comparable<N>> Matcher<N> atLeast(final N minimum) {
		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("at least ").appendValue(minimum);
			}

			@Override
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				return ((N) item).compareTo(minimum) >= 0;
			}
		};
	}

	public static <N extends Number & Comparable<N>> Matcher<N> atMost(final N maximum) {
		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("at most ").appendValue(maximum);
			}

			@Override
			@SuppressWarnings("unchecked")
			public boolean matches(Object item) {
				return ((N) item).compareTo(maximum) <= 0;
			}
		};
	}

}
