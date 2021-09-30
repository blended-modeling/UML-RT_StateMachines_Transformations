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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Internal protocol for redefinition contexts.
 */
public interface InternalUMLRTRedefinitionContext<T extends InternalUMLRTRedefinitionContext<T>> extends InternalUMLRTNamespace {
	/**
	 * Inherits elements from my (new or changed) {@code ancestor}, supporting
	 * only single inheritance per the UML-RT semantics.
	 * 
	 * @param ancestor
	 *            my new or changed ancestor
	 */
	void rtInherit(T ancestor);

	/**
	 * Disinherits features from my (former) {@code ancestor}, supporting
	 * only single inheritnce per the UML-RT semantics.
	 * 
	 * @param ancestor
	 *            my former ancestor
	 */
	void rtDisinherit(T ancestor);

	/**
	 * Disinherits me from my immediate ancestor, if I have one.
	 * 
	 * @see #rtGetAncestor()
	 * @see #rtDisinherit(InternalUMLRTRedefinitionContext)
	 */
	default void rtAutoDisinherit() {
		T ancestor = rtGetAncestor();
		if (ancestor != null) {
			rtDisinherit(ancestor);
		}
	}

	/**
	 * Reviews all of my descendants to prune out any virtual redefinitions that
	 * they may have of elements that were removed from me.
	 * 
	 * @param deletedElements
	 *            the elements that were deleted from me
	 */
	default void rtPruneInheritance(Collection<?> deletedElements) {
		rtDescendants().forEach(d -> d.rtPrune(deletedElements));
	}

	/**
	 * Prunes out any virtual redefinitions that I may have of elements
	 * that were removed from my ancestor as indicated by the given.
	 * 
	 * @param deletedElements
	 *            the elements that were deleted from my ancestor
	 */
	default void rtPrune(Collection<?> deletedElements) {
		if (rtHasExtension()) {
			List<InternalUMLRTElement> toPrune = rtExtension(ExtElement.class).eContents().stream()
					.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast)
					.filter(rt -> {
						InternalUMLRTElement redef = rt.rtGetRedefinedElement();
						return (redef == null) || deletedElements.contains(redef);
					})
					.collect(Collectors.toList());

			if (!toPrune.isEmpty()) {
				// This will cascade to my descendants
				toPrune.forEach(InternalUMLRTElement::rtDelete);
			}
		}
	}

	default T rtGetAncestor() {
		return rtGetRedefinedElement();
	}

	default void rtSetAncestor(T ancestor) {
		umlSetRedefinedElement(ancestor);
	}

	Stream<? extends T> rtDescendants();

	default <E extends Element> void rtInherit(T ancestor, EReference reference, EReference extension,
			Class<E> type, Class<? extends EObject> stereotype) {

		Predicate<InternalUMLRTElement> typeFilter = (stereotype == null)
				? type::isInstance
				: elem -> type.isInstance(elem) && UMLUtil.getStereotypeApplication((Element) elem, stereotype) != null;

		Predicate<InternalUMLRTElement> isExcluded = InternalUMLRTElement::rtIsExcluded;

		List<InternalUMLRTElement> redefinitions = rtGet(reference, extension).collect(Collectors.toList());
		ancestor.rtGet(reference, extension)
				.filter(typeFilter)
				.filter(isExcluded.negate())
				.filter(inh -> !rtHasRedefinition(inh, redefinitions))
				.forEach(inh -> {
					// First, look for the case where we actually have a redefinition but the redefinition
					// reference skips one or more levels in the hierarchy because it is persisted
					InternalUMLRTElement resolved = rtGet(reference, null)
							.filter(r -> r.rtIsRedefinition()
									&& inh.rtRedefines(r.rtGetRedefinedElement().rtGetNearestRealDefinition()))
							.findFirst().orElse(null);

					if (resolved == null) {
						// Need to create a new virtual element
						rtAddImplicitRedefinition(inh, extension);
					} else {
						// Reconnect this one
						resolved.umlSetRedefinedElement(inh);
					}
				});
	}

	default Stream<InternalUMLRTElement> rtGet(EReference reference, EReference extension) {
		Stream<InternalUMLRTElement> result = ((List<?>) this.eGet(reference)).stream()
				.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast);

		if (extension != null) {
			result = Stream.concat(result, ((List<?>) this.eGet(extension)).stream()
					.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast));
		}

		return result;
	}

	default boolean rtHasRedefinition(InternalUMLRTElement inherited, Collection<? extends InternalUMLRTElement> redefinitions) {
		return redefinitions.stream() // Includes exclusions
				.map(InternalUMLRTElement::rtGetRedefinedElement)
				.anyMatch(inherited::equals);
	}

	@SuppressWarnings("unchecked")
	default <R extends InternalUMLRTElement> R rtGetRedefinitionOf(R inherited) {
		return (R) getOwnedElements().stream()
				.filter(inherited.eClass()::isInstance)
				.filter(r -> ((R) r).rtRedefines(inherited))
				.findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	default InternalUMLRTElement rtAddImplicitRedefinition(InternalUMLRTElement inherited, EReference extension) {
		InternalUMLRTElement result = (InternalUMLRTElement) create(inherited.eClass());

		List<? super InternalUMLRTElement> implicitElements = (List<? super InternalUMLRTElement>) eGet(extension);

		// Set the redefinition chain before adding the element, because the
		// inheritance adapter might try to propagate it further before it
		// know the whole redefinition story
		result.rtRedefine(inherited);

		implicitElements.add(result);

		result.rtApplyStereotypes(inherited);

		return result;
	}

	default void rtDisinherit(T general, EReference reference, EReference extension) {
		List<InternalUMLRTElement> redefinitions = rtGet(reference, extension).collect(Collectors.toList());

		// If the element is, itself, a redefinition context, then
		// auto-disinherit it before destroying it
		Consumer<InternalUMLRTElement> autoDisinherit = element -> {
			if (element instanceof InternalUMLRTRedefinitionContext<?>) {
				((InternalUMLRTRedefinitionContext<?>) element).rtAutoDisinherit();
			}
		};

		redefinitions.stream()
				.filter(redef -> Optional.ofNullable(redef.rtGetRedefinedElement())
						.filter(inh -> ((Element) inh).getOwner() == general)
						.isPresent())
				.peek(autoDisinherit)
				.forEach(InternalUMLRTElement::rtDelete);
	}
}
