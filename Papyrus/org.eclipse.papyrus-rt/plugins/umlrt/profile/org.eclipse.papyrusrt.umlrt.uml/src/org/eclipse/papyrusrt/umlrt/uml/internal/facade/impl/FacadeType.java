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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.util.Pair;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * Metadata object describing the type of a façade object.
 */
class FacadeType<T extends Element, S extends EObject, F extends FacadeObject> {
	static final EClass NO_STEREOTYPE = null;

	private final Class<F> instanceClass;
	private final EClass umlMetaclass;
	private final EClass stereotype;
	private final Function<T, F> facadeFunction;
	private final UnaryOperator<F> redefinitionOperator;
	private final Function<T, F> facadeFactory;

	FacadeType(Class<F> instanceClass,
			EClass umlMetaclass, EClass stereotype,
			Function<T, F> facadeFunction,
			UnaryOperator<F> redefinitionOperator,
			Supplier<F> facadeFactory) {

		this(instanceClass, umlMetaclass, stereotype, facadeFunction, redefinitionOperator,
				__ -> facadeFactory.get());
	}

	FacadeType(Class<F> instanceClass,
			EClass umlMetaclass, EClass stereotype,
			Function<T, F> facadeFunction,
			UnaryOperator<F> redefinitionOperator,
			Function<T, F> facadeFactory) {

		super();

		assert FacadeObjectImpl.class.isAssignableFrom(instanceClass);
		this.instanceClass = instanceClass;
		this.umlMetaclass = umlMetaclass;
		this.stereotype = stereotype;
		this.facadeFunction = facadeFunction;
		this.redefinitionOperator = redefinitionOperator;
		this.facadeFactory = facadeFactory;
	}

	F getFacade(T element) {
		return umlMetaclass.isInstance(element) ? facadeFunction.apply(element) : null;
	}

	/**
	 * Obtains my redefinition of the given {@code facade}, even if it
	 * is an {@linkplain FacadeObject#isExcluded() exclusion}.
	 * 
	 * @param facade
	 *            an inherited facade element
	 * @return the element that the {@code facade} redefines, implicit, excluded, or otherwise
	 * 
	 * @see FacadeObject#isExcluded()
	 */
	F getRedefinition(F facade) {
		return redefinitionOperator.apply(facade);
	}

	boolean redefines(FacadeObject self, FacadeObject other) {
		boolean result = false;

		for (; !result && (self != null); self = getRedefinition(cast(self))) {
			result = self == other;
		}

		return result;
	}

	F cast(Object object) {
		return instanceClass.cast(object);
	}

	@SuppressWarnings("unchecked")
	FacadeObjectImpl.BasicFacadeAdapter<F> getAdapter(T element) {
		return (FacadeObjectImpl.BasicFacadeAdapter<F>) EcoreUtil.getExistingAdapter(element, instanceClass);
	}

	FacadeObjectImpl.BasicFacadeAdapter<? extends FacadeObjectImpl> createAdapter(T element) {
		FacadeObjectImpl.BasicFacadeAdapter<? extends FacadeObjectImpl> result = null;
		FacadeObjectImpl facade = null;

		if (stereotype == NO_STEREOTYPE) {
			// There is no stereotype expected
			facade = (FacadeObjectImpl) (facadeFactory.apply(element));
			if (facade != null) {
				result = facade.createFacadeAdapter();
				result.addAdapter(element);
				facade.init(element, null);
			}
		} else {
			S application = getStereotypeApplication(element);
			if (application != null) {
				facade = (FacadeObjectImpl) facadeFactory.apply(element);
				if (facade != null) {
					result = facade.createFacadeAdapter();
					result.addAdapter(element);
					facade.init(element, application);
				}
			}
		}

		return result;
	}

	F getFacade(FacadeObjectImpl.BasicFacadeAdapter<? extends FacadeObjectImpl> adapter) {
		return instanceClass.cast(adapter.get());
	}

	@SuppressWarnings("unchecked")
	S applyStereotype(T element) {
		return (stereotype == null)
				? null
				: (S) StereotypeApplicationHelper.getInstance(element)
						.applyStereotype(element, stereotype);
	}

	@SuppressWarnings("unchecked")
	S getStereotypeApplication(T element) {
		return (stereotype == null)
				? null
				: UMLUtil.getStereotypeApplication(element, (Class<S>) stereotype.getInstanceClass());
	}

	@Override
	public String toString() {
		return String.format("FacadeType(name=%s)", instanceClass.getSimpleName()); //$NON-NLS-1$
	}

	/**
	 * An union of facade types wrapping elements of a common metaclass.
	 */
	static final class Union<T extends Element, F extends FacadeObjectImpl> extends FacadeType<T, EObject, F> {
		private final Supplier<? extends List<FacadeType<? extends T, ? extends EObject, ? extends F>>> types;

		@SafeVarargs
		Union(Class<F> instanceClass,
				EClass umlMetaclass,
				FacadeType<? extends T, ? extends EObject, ? extends F> type1,
				FacadeType<? extends T, ? extends EObject, ? extends F> type2,
				FacadeType<? extends T, ? extends EObject, ? extends F>... rest) {

			this(instanceClass, umlMetaclass, asList(type1, type2, rest));
		}

		Union(Class<F> instanceClass,
				EClass umlMetaclass,
				List<FacadeType<? extends T, ? extends EObject, ? extends F>> types) {

			this(instanceClass, umlMetaclass, () -> types);
		}

		Union(Class<F> instanceClass,
				EClass umlMetaclass,
				Supplier<? extends List<FacadeType<? extends T, ? extends EObject, ? extends F>>> types) {

			super(instanceClass, umlMetaclass, null,
					unionFacadeFunction(types),
					unionRedefinitionOperator(types),
					(Supplier<F>) null);

			this.types = types;
		}

		@SafeVarargs
		private static <E> List<E> asList(E e1, E e2, E... rest) {
			if (rest.length == 0) {
				return new Pair<E>(e1, e2);
			} else {
				List<E> result = new ArrayList<>(2 + rest.length);
				result.add(e1);
				result.add(e2);
				for (int i = 0; i < rest.length; i++) {
					result.add(rest[i]);
				}
				return result;
			}
		}

		@SuppressWarnings("unchecked")
		final <V> V evaluate(BiFunction<FacadeType<T, ?, F>, T, V> operation, T element) {
			return types.get().stream()
					.filter(type -> type.umlMetaclass.isInstance(element))
					.map(type -> operation.apply((FacadeType<T, ?, F>) type, element))
					.filter(Objects::nonNull)
					.findFirst()
					.orElse(null);
		}

		@Override
		FacadeObjectImpl.BasicFacadeAdapter<F> getAdapter(T element) {
			return evaluate(FacadeType::getAdapter, element);
		}

		@Override
		FacadeObjectImpl.BasicFacadeAdapter<? extends FacadeObjectImpl> createAdapter(T element) {
			return evaluate(FacadeType::createAdapter, element);
		}

		@Override
		EObject applyStereotype(T element) {
			return evaluate(FacadeType::applyStereotype, element);
		}

		@Override
		EObject getStereotypeApplication(T element) {
			return evaluate(FacadeType::getStereotypeApplication, element);
		}

		@SuppressWarnings("unchecked")
		private static <T extends Element, F extends FacadeObjectImpl> //
		Function<T, F> unionFacadeFunction(Supplier<? extends List<FacadeType<? extends T, ? extends EObject, ? extends F>>> types) {
			return element -> types.get().stream()
					.filter(type -> type.umlMetaclass.isInstance(element))
					.map(type -> ((FacadeType<T, EObject, F>) type).facadeFunction)
					.map(f -> f.apply(element))
					.filter(Objects::nonNull)
					.findFirst().orElse(null);
		}

		@SuppressWarnings("unchecked")
		private static <T extends Element, F extends FacadeObjectImpl> //
		UnaryOperator<F> unionRedefinitionOperator(Supplier<? extends List<FacadeType<? extends T, ? extends EObject, ? extends F>>> types) {
			return facade -> types.get().stream()
					.filter(type -> type.instanceClass.isInstance(facade))
					.map(type -> ((FacadeType<T, EObject, F>) type).redefinitionOperator)
					.map(f -> f.apply(facade))
					.filter(Objects::nonNull)
					.findFirst().orElse(null);
		}
	}
}
