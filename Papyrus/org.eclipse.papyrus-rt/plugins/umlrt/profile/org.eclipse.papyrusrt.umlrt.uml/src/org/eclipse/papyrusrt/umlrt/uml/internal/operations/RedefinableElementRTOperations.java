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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTRedefinedElement;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTNamespace;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTRedefinitionContext;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link RedefinableElement}s
 */
public class RedefinableElementRTOperations extends UMLUtil {

	protected RedefinableElementRTOperations() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static <R extends InternalUMLRTElement> R getRedefinedElement(R element) {
		R result = null;

		if (element instanceof RedefinableElement) {
			result = null;
			List<RedefinableElement> redefined = ((RedefinableElement) element).getRedefinedElements();

			// UML-RT recognizes only single redefinition, but being more general
			// here is practical
			EClass metaclass = element.eClass();
			for (RedefinableElement next : redefined) {
				if (next.eClass() == metaclass) {
					result = (R) next;
					break;
				}
			}
		}

		return result;
	}

	public static <R extends InternalUMLRTElement & RedefinableElement> R resolveRedefinedElement(InternalUMLRTElement element, R redefinedElement) {
		R result = redefinedElement;

		Element owner = element.rtOwner();
		if (owner instanceof InternalUMLRTRedefinitionContext<?>) {
			@SuppressWarnings("unchecked")
			InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>> context = (InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>>) owner;
			InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>> ancestor = context.rtGetAncestor();
			if ((ancestor != null) && (result.rtOwner() != ancestor)) {
				R candidate = ancestor.rtGetRedefinitionOf(redefinedElement);
				if (candidate != null) {
					result = candidate;
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <R extends InternalUMLRTElement, T> T inheritFeature(R element, EStructuralFeature feature) {
		T result = null;

		if (element != null) {
			if (element.rtIsSet(feature)) {
				result = (T) element.umlGet(feature);
			} else {
				// If the owner of the feature is an UML-RT redefinition context, then
				// it inherits from its ancestor context. In many cases this is a
				// redefined element, but for classifiers that aren't redefined (e.g., capsule classes)
				// it is the general classifier
				R redefined = (element instanceof InternalUMLRTRedefinitionContext<?>)
						? (R) ((InternalUMLRTRedefinitionContext<?>) element).rtGetAncestor()
						: element.rtGetRedefinedElement();
				if (redefined != null) {
					// Inherit this, whatever it is
					result = (T) redefined.eGet(feature);
				}
			}
		}

		if (result == null) {
			result = (T) feature.getDefaultValue();
		}

		return result;
	}

	public static <R extends InternalUMLRTElement, T> T inheritFeature(R element, EStructuralFeature feature, CacheAdapter cache) {
		T result;

		if (cache == null) {
			result = inheritFeature(element, feature);
		} else {
			@SuppressWarnings("unchecked")
			T cached = (T) cache.get(element, feature);
			if (cached == null) {
				cached = inheritFeature(element, feature);
				cache.put(element, feature, cached);
			}
			result = cached;
		}

		return result;
	}

	public static <R extends InternalUMLRTElement, T> T inheritReference(R element, EReference reference,
			CacheAdapter cache, Function<? super EObject, ? extends EObject> inheritanceResolver) {

		T result;

		if (cache == null) {
			@SuppressWarnings("unchecked")
			T resolved = (T) resolveInheritedReference(inheritFeature(element, reference), inheritanceResolver);
			result = resolved;
		} else {
			@SuppressWarnings("unchecked")
			T cached = (T) cache.get(element, reference);
			if (cached == null) {
				@SuppressWarnings("unchecked")
				T resolved = (T) resolveInheritedReference(inheritFeature(element, reference), inheritanceResolver);
				cached = resolved;
				cache.put(element, reference, cached);
			}
			result = cached;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static Object resolveInheritedReference(Object value, Function<? super EObject, ? extends EObject> inheritanceResolver) {
		return (value instanceof EObject)
				? resolveOne((EObject) value, inheritanceResolver)
				: (value instanceof List<?>)
						? resolveMany((List<? extends EObject>) value, inheritanceResolver)
						: value;
	}

	private static EObject resolveOne(EObject eObject, Function<? super EObject, ? extends EObject> inheritanceResolver) {
		return inheritanceResolver.apply(eObject);
	}

	private static List<EObject> resolveMany(List<? extends EObject> eObjects, Function<? super EObject, ? extends EObject> inheritanceResolver) {
		EcoreEList<? extends EObject> delegate = (EcoreEList<? extends EObject>) eObjects;
		return new DelegatingEcoreEList<EObject>((InternalEObject) delegate.getEObject()) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected List<EObject> delegateList() {
				return (List<EObject>) delegate;
			}

			@Override
			public int getFeatureID() {
				return delegate.getFeatureID();
			}

			@Override
			public Object getFeature() {
				return delegate.getFeature();
			}

			@Override
			protected EObject delegateGet(int index) {
				return inheritanceResolver.apply(super.delegateGet(index));
			}

		};
	}

	public static boolean isRedefinition(InternalUMLRTElement element) {
		boolean result = element.rtIsVirtual();

		// Virtual elements are, by definition, redefinitions (that's why they exist)
		if (!result) {
			// UML-RT recognizes only single redefinition
			result = element.rtGetRedefinedElement() != null;
		}

		return result;
	}

	public static boolean isExcluded(InternalUMLRTElement element) {
		boolean result = false;

		Element uml = (element instanceof Element)
				? (Element) element
				: UMLUtil.getBaseElement(element);

		if (uml instanceof RedefinableElement) {
			// RedefinableElement case (others use the false constraint convention)
			RTRedefinedElement rtRedef = UMLUtil.getStereotypeApplication(uml, RTRedefinedElement.class);
			result = (rtRedef != null) && (rtRedef.getRootFragment() == null);
		} else {
			// Is its root definition excluded by a constraint?
			InternalUMLRTElement root = element.rtGetRootDefinition();
			if ((root instanceof Element) && (root != element)) { // Can't exclude a root definition
				// Find the nearest redefinable owner
				InternalUMLRTElement context = redefinableOwner(element);
				if (context instanceof Namespace) {
					// Find the contstraint
					for (Constraint next : ((Namespace) context).getOwnedRules()) {
						if ((next.getSpecification() instanceof LiteralBoolean)
								&& !((LiteralBoolean) next.getSpecification()).isValue()
								&& next.getConstrainedElements().contains(root)) {

							// That's the exclusion constraint
							result = true;
							break;
						}
					}
				}
			}

			return result;

		}

		return result;
	}

	public static boolean exclude(InternalUMLRTElement element) {
		boolean result = false;

		if (!element.rtIsExcluded()) {
			if (element instanceof RedefinableElement) {
				// RedefinableElement case
				result = excludeRedefinable(element);
			} else {
				// Exceptional exclusion, using the false constraint convention
				result = excludeExceptional(element);
			}

			// And, if successful, eliminate all further redefinitions
			if (result) {
				element.rtGetRedefinitions()
						.skip(1) // Not the excluded element, of course!
						.collect(Collectors.toList()) // Iteration-safe copy
						.forEach(InternalUMLRTElement::rtDelete);
			}
		}

		return result;
	}

	private static boolean excludeRedefinable(InternalUMLRTElement element) {
		boolean result = false;

		Element uml = element.rtGetElement();
		RTRedefinedElement rtRedef = UMLUtil.getStereotypeApplication(uml, RTRedefinedElement.class);
		if (rtRedef == null) {
			rtRedef = (RTRedefinedElement) StereotypeApplicationHelper.getInstance(uml).applyStereotype(
					uml, UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT);
		}
		if (rtRedef != null) {
			if (rtRedef.getRootFragment() != null) {
				rtRedef.setRootFragment(null);
			}

			result = true;

			// In case it wasn't already or indirectly by the above, ensure
			// that the containment chain is all reified (redefined)
			for (InternalUMLRTElement next = element; (next != null) && next.rtIsVirtual();) {
				next.rtReify();

				InternalEObject container = next.eInternalContainer();
				if (container instanceof InternalUMLRTElement) {
					next = (InternalUMLRTElement) container;
				} else {
					next = null;
				}
			}
		}

		return result;
	}

	private static boolean excludeExceptional(InternalUMLRTElement element) {
		boolean result = false;

		// We add a constraint to the nearest owning redefinable element to
		// exclude the root definition
		InternalUMLRTElement root = element.rtGetRootDefinition();
		if ((root instanceof Element) && (root != element)) { // Can't exclude a root definition
			Element rootDefinition = (Element) root;

			// Find the nearest redefinable owner
			InternalUMLRTElement context = redefinableOwner(element);
			if (context instanceof InternalUMLRTNamespace) {
				((InternalUMLRTNamespace) context).rtCreateExclusionConstraint(rootDefinition);

				result = true;

				// In case it wasn't already or indirectly by the above, ensure
				// that the redefinable containment chain is all reified (redefined)
				for (InternalUMLRTElement next = context; (next != null) && next.rtIsVirtual();) {
					next.rtReify();

					InternalEObject container = next.eInternalContainer();
					if (container instanceof InternalUMLRTElement) {
						next = (InternalUMLRTElement) container;
					} else {
						next = null;
					}
				}
			}
		}

		return result;
	}

	private static InternalUMLRTElement redefinableOwner(InternalUMLRTElement element) {
		InternalUMLRTElement result = null;

		for (Element owner = element.rtOwner(); (result == null);) {
			if (owner instanceof InternalUMLRTElement) {
				if (owner instanceof RedefinableElement) {
					// That's our answer
					result = (InternalUMLRTElement) owner;
				} else {
					// Step up to the next owner
					owner = ((InternalUMLRTElement) owner).rtOwner();
				}
			} else {
				// Ran out of RT elements
				owner = null;
			}
		}

		return result;
	}

	public static boolean reinherit(InternalUMLRTElement element) {
		boolean result = false;

		if (element.rtIsRedefinition()) {
			if (element instanceof RedefinableElement) {
				// RedefinableElement case
				result = reinheritRedefinable(element);
			} else {
				// Exceptional exclusion, using the false constraint convention
				result = reinheritExceptional(element);
			}

			// And, if successful, re-inherit all further redefinitions
			if (result) {
				Element owner = element.rtOwner();
				if (owner instanceof InternalUMLRTRedefinitionContext<?>) {
					// How could it not be, if the element was re-inherited?
					@SuppressWarnings("unchecked")
					InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>> context = (InternalUMLRTRedefinitionContext<? extends InternalUMLRTRedefinitionContext<?>>) owner;
					context.rtDescendants().forEach(descendant -> descendant.rtInherit(descendant.rtGetAncestor()));
				}
			}
		}

		return result;
	}

	private static boolean reinheritRedefinable(InternalUMLRTElement element) {
		// Recursively reinherit contained objects. We are deemed to have
		// completed a reinherit if any of our contained objects did a
		// reinherit or if we did, ourselves
		boolean result = element.eContents().stream()
				.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast)
				.reduce(false, (r, e) -> e.rtReinherit() || r, Boolean::logicalOr);

		Element uml = element.rtGetElement();

		InternalUMLRTElement root = element.rtGetRootDefinition();
		RTRedefinedElement rtRedef = UMLUtil.getStereotypeApplication(uml, RTRedefinedElement.class);
		if (rtRedef != null) {
			// First ensure notification of the re-inherit
			rtRedef.setRootFragment((RedefinableElement) root);

			// But don't actually need the stereotype on a virtual redefinition
			ElementRTOperations.delete(rtRedef);

			result = true;
		}

		// Make it purely inherited, again
		element.rtVirtualize();

		return result;
	}

	private static boolean reinheritExceptional(InternalUMLRTElement element) {
		boolean result = false;

		// Is its root definition excluded by a constraint?
		InternalUMLRTElement root = element.rtGetRootDefinition();
		if ((root instanceof Element) && (root != element)) { // Can't exclude a root definition
			// Find the nearest redefinable owner
			InternalUMLRTElement context = redefinableOwner(element);
			if (context instanceof Namespace) {
				// Find the constraint
				for (Constraint next : ((Namespace) context).getOwnedRules()) {
					if ((next.getSpecification() instanceof LiteralBoolean)
							&& !((LiteralBoolean) next.getSpecification()).isValue()
							&& next.getConstrainedElements().contains(root)) {

						// That's the exclusion constraint. Delete it
						ElementRTOperations.delete(next);
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}

	public static <R extends RedefinableElement & InternalUMLRTElement> void redefine(R element, R redefined) {
		UMLRTExtensionUtil.run(element, () -> {
			// First, set the UML semantics of redefinition
			element.umlSetRedefinedElement(redefined);

			// Only apply this stereotype if the redefined element has it
			RTRedefinedElement inherited = UMLUtil.getStereotypeApplication(redefined, RTRedefinedElement.class);
			if (inherited != null) {
				RTRedefinedElement stereo = UMLUtil.getStereotypeApplication(element, RTRedefinedElement.class);
				if (stereo == null) {
					stereo = (RTRedefinedElement) StereotypeApplicationHelper.getInstance(element).applyStereotype(
							element, UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT);
				}

				// This is the redefinition root
				stereo.setRootFragment(inherited.getRootFragment());
			}

			// Make sure that the stereotypes end up in the right place
			element.rtAdjustStereotypes();
		});
	}

	public static <R extends RedefinableElement & InternalUMLRTElement> void ensureStereotype(R element) {
		UMLRTExtensionUtil.run(element, () -> {
			RTRedefinedElement stereo = UMLUtil.getStereotypeApplication(element, RTRedefinedElement.class);
			if (stereo == null) {
				stereo = (RTRedefinedElement) StereotypeApplicationHelper.getInstance(element).applyStereotype(
						element, UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT);

				// If the stereotype wasn't applied, then the element wasn't
				// excluded, so we have to set the root fragment
				R root = element.rtGetRootDefinition();
				stereo.setRootFragment(root);
			}
		});
	}

	public static Element getOwner(InternalEObject element) {
		EObject result = element.eInternalContainer();
		if (result instanceof ExtElement) {
			result = ((ExtElement) result).getExtendedElement();
		}
		return (result instanceof Element) ? (Element) result : null;
	}

	/**
	 * Obtains the first (re-)definition of a possibly redefining element
	 * that matches some criterion, transitively, according to UML-RT semantics.
	 * 
	 * @param element
	 *            a possible redefinition
	 * @param matching
	 *            the stopping condition (matching the redefinition to obtain)
	 * 
	 * @return the matching UML-RT definition, or the original {@code element} if it
	 *         is not a {@linkplain InternalUMLRTElement#rtIsRedefinition() redefinition}
	 */
	public static <R extends InternalUMLRTElement> R getNearestDefinition(R element, Predicate<? super R> matching) {
		R result = element;

		if (!matching.test(result)) {
			int count = 0;
			for (R redefined = result.rtGetRedefinedElement(); (redefined != null); redefined = result.rtGetRedefinedElement()) {
				// Since the cycle is detected by checking if we hit 'element' again, after many iterations
				// we'll call this method recursively in case we started with something that wasn't part of
				// a cycle but later traversed up to a cycle. Note that this technique is susceptible to
				// missing cycles of depth greater than a hundred thousand elements, but that seems an
				// acceptable risk
				if (++count > 100000) {
					return getNearestDefinition(redefined, matching);
				} else if (redefined == element) {
					throw new IllegalStateException("Redefinition cycle including " + element); //$NON-NLS-1$
				}

				result = redefined;

				if (matching.test(result)) {
					// Found the goal
					break;
				}
			}
		}

		return result;
	}

}
