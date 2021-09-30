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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import static org.eclipse.papyrusrt.umlrt.uml.internal.operations.RedefinableElementRTOperations.getNearestDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.FacadeAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.RedefinableElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * Common interface of UML model elements that implement specialized
 * semantics for UML-RT.
 */
public interface InternalUMLRTElement extends InternalEObject {

	/** Exposes the corresponding API of BasicEObjectImpl. */
	int eDerivedStructuralFeatureID(EStructuralFeature eStructuralFeature);

	/**
	 * Queries whether the given {@code feature} has a value set according to
	 * UML-RT semantics. It may be set even if UML-ishly it is
	 * {@linkplain EObject#eIsSet(EStructuralFeature) unset} but its owner is,
	 * itself, UML-RT-ishly set (recursively).
	 * 
	 * @param feature
	 *            a feature
	 * @return whether it is explicitly set, according to UML-RT
	 * 
	 * @see EObject#eIsSet(EStructuralFeature)
	 */
	default boolean rtIsSet(EStructuralFeature feature) {
		// Most elements are not concerned with being owned by a redefinition
		return eIsSet(feature);
	}

	/**
	 * Obtains the value of the given {@code feature} according to
	 * core UML semantics.
	 * 
	 * @param feature
	 *            a feature
	 * @return its basic UML value
	 */
	@SuppressWarnings("unchecked")
	default <T> T umlGet(EStructuralFeature feature) {
		int featureID = eDerivedStructuralFeatureID(feature);
		return (featureID >= 0) ? umlGet(featureID) : (T) eGet(feature);
	}

	/**
	 * Obtains the value of the feature identified by the given
	 * {@link EClass}-local identifier according to core UML semantics.
	 * 
	 * @param featureID
	 *            a feature identifier
	 * @return the feature's basic UML value
	 */
	<T> T umlGet(int featureID);

	/**
	 * Ensures that my extension exists.
	 * 
	 * @throws UnsupportedOperationException
	 *             if I cannot be extended
	 */
	default void rtCreateExtension() {
		throw new UnsupportedOperationException("createExtension");
	}

	/**
	 * Ensures that my extension does not exist.
	 * 
	 * @throws UnsupportedOperationException
	 *             if I cannot be extended
	 */
	default void rtDestroyExtension() {
		throw new UnsupportedOperationException("destroyExtension");
	}

	default boolean rtHasExtension() {
		return false;
	}

	default <T extends EObject> T rtExtension(Class<T> extensionClass) {
		return null;
	}

	/**
	 * Suppresses the forwarding of notifications from my extension (if I have one)
	 * for the duration of the given {@code action} that I shall run.
	 * 
	 * @param action
	 *            an action to run without forwarding of extension notifications
	 */
	default void rtSuppressForwardingWhile(Runnable action) {
		action.run();
	}

	/**
	 * Obtains the possibly inherited value of the given {@code feature} according
	 * to UML-RT semantics.
	 * 
	 * @param feature
	 *            a feature to access
	 * @return its value, which may be explicitly set or inherited from a redefined element
	 */
	default <T> T inheritFeature(EStructuralFeature feature) {
		T result;

		if (feature instanceof EReference) {
			EReference reference = (EReference) feature;
			Function<? super EObject, ? extends EObject> resolver = null;

			if (!reference.isContainment() && !reference.isContainer()) {
				resolver = rtGetInheritanceResolver(reference);
			}

			result = (resolver == null)
					? RedefinableElementRTOperations.inheritFeature(this, feature, CacheAdapter.getInstance())
					: RedefinableElementRTOperations.inheritReference(this, reference, CacheAdapter.getInstance(), resolver);
		} else {
			result = RedefinableElementRTOperations.inheritFeature(this, feature, CacheAdapter.getInstance());
		}

		return result;
	}

	/**
	 * Obtains an inheritance resolving function for the given cross-{@code reference} that
	 * resolves objects inherited in the {@code reference} to (possibly virtual) redefinitions
	 * in the owner's redefinition context. This is not applied to containment or container references.
	 * 
	 * @param reference
	 *            a cross-reference for which to resolve inherited objects
	 * @return a transformation function that resolves inherited reference values into
	 *         the owner's context, or {@code null} if no such resolution is needed (usually because
	 *         the referenced objects are not inheritable/redefinable in the UML-RT semantics)
	 */
	default Function<? super EObject, ? extends EObject> rtGetInheritanceResolver(EReference reference) {
		return null;
	}

	/**
	 * Queries the features that I inherited from elements that I
	 * {@linkplain #rtGetRedefinedElement() redefine}.
	 * 
	 * @return my inherited features
	 * 
	 * @see #rtGetRedefinedElement()
	 */
	default Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return Collections.emptySet();
	}

	/**
	 * Queries whether I am a redefinition of an inherited element according
	 * to UML-RT semantics.
	 * 
	 * @return whether I am an UML-RT redefinition
	 */
	default boolean rtIsRedefinition() {
		// Virtual elements are, by definition, redefinitions (that's why they exist)
		return rtIsVirtual() || RedefinableElementRTOperations.isRedefinition(this);
	}

	/**
	 * Obtains the element that I redefine, according to UML-RT semantics.
	 * 
	 * @return my UML-RT redefined element, or {@code null} if I am not a
	 *         {@linkplain #rtIsRedefinition() redefinition} or I am
	 *         {@linkplain #rtIsExcluded() excluded}
	 */
	@SuppressWarnings("unchecked")
	default <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		return (R) RedefinableElementRTOperations.getRedefinedElement(this);
	}

	/**
	 * Obtains the original definition of the element that I ultimately redefine,
	 * transitively, according to UML-RT semantics.
	 * 
	 * @return my UML-RT root definition, or {@code this} if I am not a
	 *         {@linkplain #rtIsRedefinition() redefinition}
	 */
	@SuppressWarnings("unchecked")
	default <R extends InternalUMLRTElement> R rtGetRootDefinition() {
		// False predicate to always keep looking up the chain
		return getNearestDefinition((R) this, __ -> false);
	}

	/**
	 * Obtains the first real (re-)definition of the element that I redefine,
	 * transitively, according to UML-RT semantics. This may be the
	 * {@linkplain #rtGetRootDefinition()} or some real redefinition thereof.
	 * 
	 * @return my UML-RT real definition, or {@code this} if I am not a
	 *         {@linkplain #rtIsRedefinition() redefinition}
	 */
	@SuppressWarnings("unchecked")
	default <R extends InternalUMLRTElement> R rtGetNearestRealDefinition() {
		Predicate<R> isVirtual = InternalUMLRTElement::rtIsVirtual;
		return getNearestDefinition((R) this, isVirtual.negate());
	}

	/**
	 * Queries whether I am a redefinition of the given {@code element},
	 * transitively, according to UML-RT semantics. Formally, whether
	 * I and the {@code element} have the same
	 * {@linkplain #rtGetRootDefinition() root definition}.
	 * 
	 * @param element
	 *            an element that I may possibly redefine
	 * @return whether I am a redefinition (directly or transitively) ofthe {@code element}.
	 *         As a special case, I redefine myself, because I and myself trivially
	 *         have the same root definition, even if I am a root definition
	 * 
	 * @see #rtGetRootDefinition()
	 */
	default boolean rtRedefines(InternalUMLRTElement element) {
		boolean result = element == this;

		if (!result) {
			result = getNearestDefinition(this, e -> e == element) == element;
		}

		return result;
	}

	default <R extends InternalUMLRTElement> Stream<R> rtGetRedefinitions() {
		@SuppressWarnings("unchecked")
		R self = (R) this;

		Stream<R> result = Stream.of(self);

		// If I am not in an UML-RT redefinition context, then I can have
		// no redefinitions
		if (rtOwner() instanceof InternalUMLRTRedefinitionContext<?>) {
			@SuppressWarnings("unchecked")
			InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>> context = (InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>>) rtOwner();
			Stream<R> redefineMe = context.rtDescendants()
					.map(descendant -> descendant.rtGetRedefinitionOf(self))
					.filter(Objects::nonNull);

			result = Stream.concat(result, redefineMe);
		}

		return result;
	}

	default void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		throw new UnsupportedOperationException("redefinition not supported"); //$NON-NLS-1$
	}

	/**
	 * Queries whether I am a redefinition that excludes the inherited
	 * element according to UML-RT semantics.
	 * 
	 * @return whether I am an UML-RT exclusion
	 */
	default boolean rtIsExcluded() {
		return RedefinableElementRTOperations.isExcluded(this);
	}

	default Element rtGetElement() {
		return (this instanceof Element) ? (Element) this : UMLUtil.getBaseElement(this);
	}

	/**
	 * Destroys me in a GMF-compatible way: unlike the UML2 destruction
	 * algorithm, GMF does not remove adapters because destruction is not a
	 * permanent operation as it is in UML2. Rather, destroyed objects are
	 * retained in a transaction's change description for potential restoration.
	 * In this sense, it is more like {@link EcoreUtil#delete(EObject)}.
	 * 
	 * @see EcoreUtil#delete(EObject)
	 */
	default void rtDelete() {
		ElementRTOperations.delete(this);
	}

	default boolean rtExclude() {
		return run(() -> {
			boolean result = RedefinableElementRTOperations.exclude(this);

			if (result) {
				// Strip me of all inheritable features
				rtUnsetAll();

				// Is there a façade that needs to be notified?
				EObject owner = rtOwner();
				if ((owner != null) && (this instanceof Element)) {
					FacadeAdapter.getInstance(owner).ifPresent(a -> a.excluded((Element) this));
				}
			}

			return result;
		});
	}

	default boolean rtReinherit() {
		return run(() -> {
			boolean result = RedefinableElementRTOperations.reinherit(this);

			if (result) {
				// Strip me of all inheritable features
				rtUnsetAll();

				// Is there a façade that needs to be notified?
				EObject owner = rtOwner();
				if ((owner != null) && (this instanceof Element)) {
					FacadeAdapter.getInstance(owner).ifPresent(a -> a.reinherited((Element) this));
				}
			}

			return result;
		});
	}

	/**
	 * Unsets all of my features that are inheritable.
	 */
	void rtUnsetAll();

	default void rtRedefine(InternalUMLRTElement element) {
		if (element.eClass() != this.eClass()) {
			throw new IllegalArgumentException(String.format("a %s cannot redefine a %s",
					this.eClass().getName(), element.eClass().getName()));
		}

		RedefinableElementRTOperations.redefine((RedefinableElement & InternalUMLRTElement) this,
				(RedefinableElement & InternalUMLRTElement) element);
	}

	default Element rtOwner() {
		return RedefinableElementRTOperations.getOwner(this);
	}

	default Resource rtResource() {
		return eInternalResource();
	}

	default void rtReify() {
		throw new UnsupportedOperationException("rtReify"); //$NON-NLS-1$
	}

	default void rtVirtualize() {
		throw new UnsupportedOperationException("rtVirtualize"); //$NON-NLS-1$
	}

	/**
	 * Runs an {@code action} on me without allowing anything that
	 * it does to cause me to be {@linkplain #rtReify() reified},
	 * unless that is explicitly requested.
	 * 
	 * @param action
	 *            an action
	 * 
	 * @see #rtReify()
	 */
	default void run(Runnable action) {
		ReificationAdapter adapter = ReificationAdapter.getInstance(this);
		if (adapter == null) {
			// Just run the action
			action.run();
		} else {
			adapter.run(action);
		}
	}

	/**
	 * Runs an {@code action} on me without allowing anything that
	 * it does to cause me to be {@linkplain #rtReify() reified},
	 * unless that is explicitly requested.
	 * 
	 * @param action
	 *            an action
	 * @return the {@code action}'s result
	 * 
	 * @see #rtReify()
	 */
	default <V> V run(Callable<V> action) {
		V result;

		ReificationAdapter adapter = ReificationAdapter.getInstance(this);
		if (adapter == null) {
			// Just run the action
			try {
				result = action.call();
			} catch (Exception e) {
				throw new WrappedException(e);
			}
		} else {
			result = adapter.run(action);
		}

		return result;
	}

	default boolean rtApplyStereotypes(InternalUMLRTElement prototype) {
		boolean result = false;

		if ((this instanceof Element) && (prototype.eClass() == eClass())) {
			result = ElementRTOperations.rtApplyStereotype((InternalUMLRTElement & Element) this,
					(InternalUMLRTElement & Element) prototype);
		}

		return result;
	}

	default void rtAdjustStereotypes() {
		if (this instanceof Element) {
			Element element = (Element) this;

			TreeIterator<EObject> contents = EcoreUtil.getAllContents(Collections.singleton(element));
			while (contents.hasNext()) {
				EObject next = contents.next();
				if ((next instanceof Element) && (next instanceof InternalUMLRTElement)) {
					Element nextElement = (Element) next;
					InternalUMLRTElement nextInternal = (InternalUMLRTElement) nextElement;

					List<EObject> stereotypeApplications = new ArrayList<>(nextElement.getStereotypeApplications());
					if (!stereotypeApplications.isEmpty()) {
						if (nextInternal.rtIsVirtual()) {
							// They must also be stored in the Extension Extent
							nextInternal.rtResource().getContents().addAll(stereotypeApplications);
						} else {
							// Store my stereotypes in the appropriate model resource
							StereotypeApplicationHelper helper = StereotypeApplicationHelper.getInstance(this);
							stereotypeApplications.forEach(appl -> helper.addToContainmentList(nextElement, appl));
						}
					}

					// Also, if I need it, apply the RTRedefinedElement stereotype
					if (!nextInternal.rtIsVirtual() && nextInternal.rtIsRedefinition()
							&& (next instanceof RedefinableElement)) {

						RedefinableElementRTOperations.ensureStereotype(
								(RedefinableElement & InternalUMLRTElement) next);
					}
				} else {
					// There won't be any UML content inside of this non-UML content
					// (which is probably an annotation)
					contents.prune();
				}
			}
		}
	}

	default boolean rtIsVirtual() {
		boolean result = rtResource() instanceof ExtensionResource;

		if (!result) {
			InternalEObject container = eInternalContainer();
			if (container != null) {
				// I am virtual if I am contained in an unset reference
				EReference containment = eContainmentFeature();
				result = !container.eIsSet(containment);
			}
		}

		return result;
	}

	EObject create(EClass eClass);
}
