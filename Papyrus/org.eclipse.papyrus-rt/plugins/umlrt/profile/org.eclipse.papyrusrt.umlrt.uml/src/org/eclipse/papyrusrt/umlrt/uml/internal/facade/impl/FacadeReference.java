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

package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.Element;

/**
 * Metadata object describing a reference between façade objects.
 */
class FacadeReference<T extends Element, S extends EObject, R extends FacadeObject, F extends R> {

	private final FacadeType<T, S, ? extends F> type;
	private final int facadeReferenceID;
	private final Class<R> referenceType;
	private final EReference feature;
	private final EReference extension;
	private final EClass metaclass;

	FacadeReference(FacadeType<T, S, ? extends F> type, int facadeReferenceID, Class<R> referenceType,
			EReference feature, EReference extension, EClass metaclass) {

		super();

		this.type = type;
		this.facadeReferenceID = facadeReferenceID;
		this.referenceType = referenceType;
		this.feature = feature;
		this.extension = extension;
		this.metaclass = metaclass;
	}

	FacadeType<T, S, ? extends F> getType() {
		return type;
	}

	EClass getEReferenceType() {
		return metaclass;
	}

	/**
	 * Obtains the subset of the reference values that are "real", stored
	 * in the UML model in the appropriate UML metaclass property.
	 * 
	 * @param element
	 *            the element from which to obtain the reference values
	 * 
	 * @return the real (locally-defined, redefined, or excluded) values
	 */
	@SuppressWarnings("unchecked")
	List<T> getUML(Element element) {
		return (List<T>) element.eGet(feature);
	}

	/**
	 * Obtains the subset of the reference values that are "virtual", stored
	 * in the extension of the UML model in the appropriate UML extension
	 * metaclass property.
	 * 
	 * @param element
	 *            the element from which to obtain the reference values
	 * 
	 * @return the virtual (purely inherited) values
	 */
	@SuppressWarnings("unchecked")
	List<T> getExtension(Element element) {
		return !(element instanceof InternalUMLRTElement)
				? ECollections.emptyEList()
				: (List<T>) element.eGet(extension);
	}

	/**
	 * Obtains the union of "real" and "virtual" values of the reference,
	 * including all local, inherited, and redefined definitions.
	 * 
	 * @param element
	 *            the element from which to obtain the reference values
	 * @return the union of {@linkplain #getUML(Element) real UML} values
	 *         and {@linkplain #getExtension(Element) virtual extension} values
	 */
	Stream<T> getAll(Element element) {
		return (extension == null)
				? getUML(element).stream().filter(metaclass::isInstance)
				: Stream.concat(
						getUML(element).stream(),
						getExtension(element).stream())
						.filter(metaclass::isInstance);
	}

	Stream<R> facades(Element element, boolean withExclusions) {
		Stream<R> result = getAll(element)
				.map(type::getFacade)
				.filter(referenceType::isInstance)
				.map(referenceType::cast);

		if (!withExclusions) {
			Predicate<R> isExcluded = FacadeObject::isExcluded;
			Predicate<R> notExcluded = isExcluded.negate();
			result = result.filter(notExcluded);
		}

		return result;
	}

	List<R> getFacades(FacadeObjectImpl owner, boolean withExclusions) {
		return facades(owner.toUML(), withExclusions).collect(
				collectingAndThen(toList(), list -> elist(owner, list)));
	}

	private EList<R> elist(FacadeObjectImpl owner, List<R> list) {
		return new FacadeObjectEList<>(owner, referenceType, facadeReferenceID, list);
	}

	List<R> getFacades(FacadeObjectImpl owner, boolean withExclusions, UnaryOperator<R> andThen) {
		return facades(owner.toUML(), withExclusions)
				.map(andThen)
				.collect(collectingAndThen(toList(), list -> elist(owner, list)));
	}

	List<R> getFacades(FacadeObjectImpl owner) {
		return getFacades(owner, false);
	}

	List<R> getFacades(FacadeObjectImpl owner, UnaryOperator<R> andThen) {
		return getFacades(owner, false, andThen);
	}

	@Override
	public String toString() {
		return String.format("FacadeReference(name=%s, type=%s)", feature.getName(), type); //$NON-NLS-1$
	}

	//
	// Nested types
	//

	static class Indirect<T extends Element, S extends EObject, R extends FacadeObject, F extends R> extends FacadeReference<T, S, R, F> {
		private final Function<? super Element, ? extends Element> indirection;

		Indirect(FacadeType<T, S, ? extends F> type, int facadeReferenceID, Class<R> referenceType, EReference feature, EReference extension, EClass metaclass,
				Function<? super Element, ? extends Element> indirection) {

			super(type, facadeReferenceID, referenceType, feature, extension, metaclass);

			this.indirection = indirection;
		}

		@Override
		List<T> getUML(Element element) {
			Element owner = indirection.apply(element);
			return (owner == null)
					? ECollections.emptyEList()
					: super.getUML(owner);
		}

		@Override
		List<T> getExtension(Element element) {
			Element owner = indirection.apply(element);
			return (owner == null)
					? ECollections.emptyEList()
					: super.getExtension(owner);
		}
	}
}
