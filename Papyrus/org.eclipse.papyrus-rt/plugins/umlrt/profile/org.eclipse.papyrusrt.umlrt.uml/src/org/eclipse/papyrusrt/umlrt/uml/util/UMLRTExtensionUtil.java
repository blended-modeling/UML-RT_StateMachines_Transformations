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

package org.eclipse.papyrusrt.umlrt.uml.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utilities for working with the UML-RT extension of the UML metamodel.
 */
public class UMLRTExtensionUtil {

	static private Map<EStructuralFeature, EStructuralFeature> EXTENSIONS;

	static {
		Map<EStructuralFeature, EStructuralFeature> ext = new HashMap<>();

		ext.put(UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT, ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		ext.put(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		ext.put(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR, ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);
		ext.put(UMLPackage.Literals.CLASS__OWNED_OPERATION, ExtUMLExtPackage.Literals.CLASS__IMPLICIT_OPERATION);
		ext.put(UMLPackage.Literals.INTERFACE__OWNED_OPERATION, ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION);
		ext.put(UMLPackage.Literals.STATE_MACHINE__REGION, ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION);
		ext.put(UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT, ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_CONNECTION_POINT);
		ext.put(UMLPackage.Literals.STATE__REGION, ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION);
		ext.put(UMLPackage.Literals.STATE__CONNECTION_POINT, ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT);
		ext.put(UMLPackage.Literals.REGION__SUBVERTEX, ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX);
		ext.put(UMLPackage.Literals.REGION__TRANSITION, ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION);
		ext.put(UMLPackage.Literals.NAMESPACE__OWNED_RULE, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE);
		ext.put(UMLPackage.Literals.TRANSITION__TRIGGER, ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER);
		ext.put(UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR, ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);

		EXTENSIONS = ext;
	}

	/**
	 * Not instantiable by clients.
	 */
	private UMLRTExtensionUtil() {
		super();
	}

	/**
	 * Queries whether an UML {@code element} supports the extension
	 * facility for UML-RT semantics.
	 * 
	 * @param element
	 *            a model element
	 * 
	 * @return whether it implements the UML-RT extensions
	 */
	public static boolean hasUMLRTExtension(Element element) {
		return element instanceof InternalUMLRTElement;
	}

	/**
	 * Obtains the extension object of an UML {@code element} if it
	 * {@linkplain #hasUMLRTExtension(Element) supports} the extension
	 * facility for UML-RT semantics.
	 * 
	 * @param element
	 *            a model element
	 * 
	 * @return whether it implements the UML-RT extensions
	 */
	public static EObject getUMLRTExtension(Element element) {
		return hasUMLRTExtension(element)
				? ((InternalUMLRTElement) element).rtExtension(ExtElement.class)
				: null;
	}

	/**
	 * From an element or its extension, gets the element.
	 * 
	 * @param object
	 *            an object that may be an UML element, its extension, or neither
	 * 
	 * @return the UML element, or {@code null} if the {@code object} is something else
	 */
	public static Element getUMLElement(EObject object) {
		return (object instanceof Element)
				? (Element) object
				: (object instanceof ExtElement)
						? ((ExtElement) object).getExtendedElement()
						: null;
	}

	/**
	 * Queries whether an {@code element} is a virtual model element,
	 * not actually present in the model but implied via the UML-RT
	 * semantics of inheritance and incremental redefinition.
	 * 
	 * @param element
	 *            an element
	 * @return whether it is a virtual element
	 * 
	 * @see #isRedefinition(Element)
	 * @see #isExcluded(Element)
	 */
	public static boolean isVirtualElement(Element element) {
		return (element instanceof InternalUMLRTElement)
				&& ((InternalUMLRTElement) element).rtIsVirtual();
	}

	/**
	 * Queries whether an {@code element} is a redefinition of an
	 * inherited element and is not redefined in order to exclude
	 * the element.
	 * 
	 * @param element
	 *            an element
	 * @return whether it is a redefinition
	 * 
	 * @see #isVirtualElement(Element)
	 * @see #isExcluded(Element)
	 */
	public static boolean isRedefinition(Element element) {
		boolean result = false;

		if (element instanceof InternalUMLRTElement) {
			InternalUMLRTElement rt = (InternalUMLRTElement) element;

			result = rt.rtIsRedefinition() && !rt.rtIsExcluded() && !rt.rtIsVirtual();
		}

		return result;
	}

	/**
	 * Queries whether an {@code element} is an exclusion of an
	 * inherited element.
	 * 
	 * @param element
	 *            an element
	 * @return whether it is an exclusion
	 * 
	 * @see #isVirtualElement(Element)
	 * @see #isRedefinition(Element)
	 */
	public static boolean isExcluded(Element element) {
		return (element instanceof InternalUMLRTElement)
				&& ((InternalUMLRTElement) element).rtIsExcluded();
	}

	/**
	 * Queries whether an {@code element} is some kind of inherited
	 * element, whether {@link #isVirtualElement(Element) virtual},
	 * {@link #isRedefinition(Element) redefined}, or
	 * {@link #isExcluded(Element) excluded}.
	 * 
	 * @param element
	 *            an element
	 * @return whether it is an inherited element
	 * 
	 * @see #isRedefinition(Element)
	 * @see #isExcluded(Element)
	 */
	public static boolean isInherited(Element element) {
		return (element instanceof InternalUMLRTElement)
				&& ((InternalUMLRTElement) element).rtIsRedefinition();
	}

	/**
	 * Obtains the element redefined by an {@code element}, if any.
	 * 
	 * @param element
	 *            an element
	 * @return the element that it redefines, or {@code null} if the {@code element}
	 *         is not a {@linkplain #isInherited(Element) redefinition}/exclusion/inherited
	 * 
	 * @see #isInherited(Element)
	 */
	public static <T extends Element> T getRedefinedElement(T element) {
		return isInherited(element)
				? ((InternalUMLRTElement) element).rtGetRedefinedElement()
				: null;
	}

	/**
	 * If an {@code element} is {@linkplain #isVirtualElement(Element) virtual},
	 * touches it so that it becomes a real {@linkplain #isRedefinition(Element) redefinition}
	 * in the UML model. Otherwise, this operation has no effect.
	 * 
	 * @param element
	 *            an element to touch
	 * 
	 * @see #isVirtualElement(Element)
	 * @see #isRedefinition(Element)
	 */
	public static void touch(Element element) {
		if (isVirtualElement(element)) {
			((InternalUMLRTElement) element).rtReify();
		}
	}

	/**
	 * Queries whether an {@code element} is a virtual model element,
	 * not actually present in the model but implied via the UML-RT
	 * semantics of inheritance and incremental redefinition.
	 * 
	 * @param element
	 *            an element of the façade
	 * @return whether it is a virtual element
	 */
	public static boolean isVirtualElement(FacadeObject element) {
		return isVirtualElement(element.toUML());
	}

	/**
	 * Obtains the root definition of an {@code element} that may be
	 * inherited, a redefinition, or an exclusion.
	 * 
	 * @param element
	 *            an element
	 * @return its root definition, which may be itself if it is not
	 *         an inherited, redefining, or excluded element
	 * 
	 * @see #isInherited(Element)
	 */
	public static <T extends Element> T getRootDefinition(T element) {
		T result = element;

		if (element instanceof InternalUMLRTElement) {
			InternalUMLRTElement rt = (InternalUMLRTElement) element;
			result = rt.rtGetRootDefinition();
		}

		return result;
	}

	/**
	 * Queries whether an {@code element} redefines a purported
	 * {@code redefined} element of the same kind, either directly
	 * or indirectly. As a special case, an element is considered
	 * to redefine itself.
	 * 
	 * @param element
	 *            an element
	 * @param redefined
	 *            a purportedly redefined element
	 * @return whether the {@code element} is a direct or indirect
	 *         redefinition of the {@code redefined}
	 * 
	 * @see #getRootDefinition(Element)
	 */
	public static <T extends Element> boolean redefines(T element, T redefined) {
		boolean result = element == redefined;

		if (!result && (element instanceof InternalUMLRTElement) && (redefined instanceof InternalUMLRTElement)) {
			InternalUMLRTElement rt = (InternalUMLRTElement) element;
			result = rt.rtRedefines((InternalUMLRTElement) redefined);
		}

		return result;
	}

	/**
	 * Runs an {@code action} that modifies some {@code element} without
	 * affecting its {@linkplain #isVirtualElement(Element) real/virtual state}.
	 * 
	 * @param element
	 *            a model element
	 * @param action
	 *            an action that modifies it in some way
	 */
	public static void run(Element element, Runnable action) {
		InternalUMLRTElement context = null;

		if (element instanceof InternalUMLRTElement) {
			context = (InternalUMLRTElement) element;
		}

		if (context == null) {
			// Just run the action
			action.run();
		} else {
			context.run(action);
		}
	}

	/**
	 * Runs an {@code action} that modifies some façade {@code element} without
	 * affecting its {@linkplain #isVirtualElement(FacadeObject) real/virtual state}.
	 * 
	 * @param element
	 *            a model element
	 * @param action
	 *            an action that modifies it in some way
	 */
	public static void run(FacadeObject element, Runnable action) {
		run(element.toUML(), action);
	}

	/**
	 * For a given UML metamodel feature, obtains the corresponding extension feature, if any,
	 * that stores "shadows" defined locally in an element for inherited elements or values.
	 * 
	 * @param umlFeature
	 *            a feature in the UML metamodel
	 * 
	 * @return the corresponding extension feature, or {@code null} if there is none
	 * 
	 * @see #getUMLRTSupersetOf(EStructuralFeature)
	 */
	public static EStructuralFeature getExtensionFeature(EStructuralFeature umlFeature) {
		return EXTENSIONS.get(umlFeature);
	}

	/**
	 * For a given UML metamodel feature, obtains all of the features that logically
	 * implement it in the extended UML-RT semantics, including extension features, if any.
	 * 
	 * @param umlFeature
	 *            a feature in the UML metamodel
	 * 
	 * @return the collection of features that the façade API presents as the logical
	 *         extension of the UML feature, including at least the original {@code umlFeature}
	 *         in the first position
	 * 
	 * @see #getExtensionFeature(EStructuralFeature)
	 */
	public static List<? extends EStructuralFeature> getUMLRTSupersetOf(EStructuralFeature umlFeature) {
		EStructuralFeature extension = getExtensionFeature(umlFeature);

		return (extension == null) ? Collections.singletonList(umlFeature) : new Pair<>(umlFeature, extension);
	}

	/**
	 * For a given list of UML metamodel features, obtains a contents-list covering all of the features that
	 * logically implement them in the extended UML-RT semantics, including extension features, if any.
	 * 
	 * @param owner
	 *            the list owner
	 * @param umlFeatures
	 *            features in the UML metamodel
	 * 
	 * @return the contents list over all of the UML and extension features logically implementing
	 *         the given UML features
	 * 
	 * @see #getExtensionFeature(EStructuralFeature)
	 */
	public static <E> EContentsEList<E> getUMLRTContents(EObject owner, Collection<? extends EStructuralFeature> umlFeatures) {
		EContentsEList<E> result;

		// Do we even have extensions to be concerned with?
		if (owner instanceof InternalUMLRTElement) {
			List<EStructuralFeature> allFeatures = umlFeatures.stream()
					.map(UMLRTExtensionUtil::getUMLRTSupersetOf)
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
			result = new InheritanceContentsList<>(owner, allFeatures);
		} else {
			// Don't need to consider extension features because this object
			// is not extensible
			result = new EContentsEList<>(owner, umlFeatures.toArray(new EStructuralFeature[umlFeatures.size()]));
		}

		return result;
	}

	/**
	 * For a given list of UML metamodel features, obtains a contents-list covering all of the features that
	 * logically implement them in the extended UML-RT semantics, including extension features, if any.
	 * 
	 * @param owner
	 *            the list owner
	 * @param umlFeature
	 *            a feature in the UML metamodel
	 * @param rest
	 *            optional additional features
	 * 
	 * @return the contents list over all of the UML and extension features logically implementing
	 *         the given UML features
	 * 
	 * @see #getExtensionFeature(EStructuralFeature)
	 */
	public static <E> EContentsEList<E> getUMLRTContents(EObject owner, EStructuralFeature umlFeature, EStructuralFeature... rest) {
		EContentsEList<E> result;

		// Do we even have extensions to be concerned with?
		if (owner instanceof InternalUMLRTElement) {
			if (rest.length == 0) {
				result = new InheritanceContentsList<>(owner, getUMLRTSupersetOf(umlFeature));
			} else {
				List<EStructuralFeature> allFeatures = new ArrayList<>();
				allFeatures.addAll(getUMLRTSupersetOf(umlFeature));
				Stream.of(rest).map(UMLRTExtensionUtil::getUMLRTSupersetOf).forEach(allFeatures::addAll);
				result = new InheritanceContentsList<>(owner, allFeatures);
			}
		} else {
			// Don't need to consider extension features because this object
			// is not extensible
			if (rest.length == 0) {
				result = new EContentsEList<>(owner, new EStructuralFeature[] { umlFeature });
			} else {
				List<EStructuralFeature> allFeatures = new ArrayList<>(1 + rest.length);
				allFeatures.add(umlFeature);
				allFeatures.addAll(Arrays.asList(rest));
				result = new EContentsEList<>(owner, allFeatures);
			}
		}

		return result;
	}

	/**
	 * Removes a {@code value} from the list (including extension features) logically represented by
	 * the given feature.
	 * 
	 * @param owner
	 *            the list owner
	 * @param umlFeature
	 *            feature in the UML metamodel
	 * @param value
	 *            the value to remove from the feature of the {@code owner}
	 * 
	 * @return whether the element was removed, or {@code null} if it was not present in the feature
	 * 
	 * @see #getExtensionFeature(EStructuralFeature)
	 */
	public static boolean remove(EObject owner, EStructuralFeature umlFeature, Object value) {
		boolean result = doRemove(owner, umlFeature, value);

		// Do we even have extensions to be concerned with?
		if (!result && (owner instanceof InternalUMLRTElement)) {
			EStructuralFeature ext = getExtensionFeature(umlFeature);
			if (ext != null) {
				result = doRemove(owner, ext, value);
			}
		}

		return result;
	}

	private static boolean doRemove(EObject owner, EStructuralFeature feature, Object value) {
		boolean result = false;

		Object existing = owner.eGet(feature);
		if (existing == value) {
			owner.eSet(feature, null);
			result = true;
		} else if (existing instanceof Collection<?>) {
			result = ((Collection<?>) existing).remove(value);
		}

		return result;
	}

	//
	// Nested types
	//

	protected static class InheritanceContentsList<E> extends EContentsEList<E> {

		public InheritanceContentsList(EObject eObject, List<? extends EStructuralFeature> eStructuralFeatures) {
			super(eObject, eStructuralFeatures);
		}

		@Override
		protected boolean useIsSet() {
			// Don't cut off inheritable features like Operation::ownedParameter
			return false;
		}

		//
		// Copied from the superclass to replace the eGet(...) call with something
		// more appropriate for inheritable features. Otherwise identical to super.
		//
		@Override
		public int size() {
			int result = 0;
			if (eStructuralFeatures != null) {
				for (int i = 0; i < eStructuralFeatures.length; ++i) {
					EStructuralFeature feature = eStructuralFeatures[i];
					if (isIncluded(feature) && (!useIsSet() || eObject.eIsSet(feature))) {
						// The superclass has eGet(feature, false) hard-coded for
						// efficiency because proxies are irrelevant to size
						Object value = eObject.eGet(feature, resolve());
						if (FeatureMapUtil.isFeatureMap(feature)) {
							FeatureMap featureMap = (FeatureMap) value;
							for (int j = 0, size = featureMap.size(); j < size; ++j) {
								if (isIncludedEntry(featureMap.getEStructuralFeature(j)) && featureMap.getValue(j) != null) {
									++result;
								}
							}
						} else if (feature.isMany()) {
							result += ((Collection<?>) value).size();
						} else if (value != null) {
							++result;
						}
					}
				}
			}
			return result;
		}

		//
		// Copied from the superclass to replace the eGet(...) call with something
		// more appropriate for inheritable features. Otherwise identical to super.
		//
		@Override
		public boolean isEmpty() {
			if (eStructuralFeatures != null) {
				for (int i = 0; i < eStructuralFeatures.length; ++i) {
					EStructuralFeature feature = eStructuralFeatures[i];
					if (isIncluded(feature) && (!useIsSet() || eObject.eIsSet(feature))) {
						// The superclass has eGet(feature, false) hard-coded for
						// efficiency because proxies are irrelevant to emptiness
						Object value = eObject.eGet(feature, resolve());
						if (FeatureMapUtil.isFeatureMap(feature)) {
							FeatureMap featureMap = (FeatureMap) value;
							for (int j = 0, size = featureMap.size(); j < size; ++j) {
								if (isIncludedEntry(featureMap.getEStructuralFeature(j)) && featureMap.getValue(j) != null) {
									return false;
								}
							}
						} else if (feature.isMany()) {
							if (!((Collection<?>) value).isEmpty()) {
								return false;
							}
						} else if (value != null) {
							return false;
						}
					}
				}
			}
			return true;
		}

		/**
		 * References must be resolved in order to properly account for
		 * inheritance.
		 *
		 * @return {@code true}, always
		 */
		@Override
		protected final boolean resolve() {
			return true;
		}

		@Override
		protected ListIterator<E> newResolvingListIterator() {
			return new ResolvingFeatureIteratorImpl<E>(eObject, eStructuralFeatures) {
				@Override
				protected boolean useIsSet() {
					return InheritanceContentsList.this.useIsSet();
				}
			};
		}

		@Override
		protected ListIterator<E> newNonResolvingListIterator() {
			return new FeatureIteratorImpl<E>(eObject, eStructuralFeatures) {
				@Override
				protected boolean useIsSet() {
					return InheritanceContentsList.this.useIsSet();
				}
			};
		}
	}

}
