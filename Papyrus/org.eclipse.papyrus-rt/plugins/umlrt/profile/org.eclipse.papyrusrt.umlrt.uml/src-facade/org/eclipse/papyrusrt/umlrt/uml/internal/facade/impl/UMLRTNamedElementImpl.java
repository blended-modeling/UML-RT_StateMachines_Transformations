/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import static java.util.stream.Collectors.collectingAndThen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getModel <em>Model</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getInheritanceKind <em>Inheritance Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#isInherited <em>Is Inherited</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#isVirtualRedefinition <em>Is Virtual Redefinition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#isRedefinition <em>Is Redefinition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#isExcluded <em>Is Excluded</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getInheritedElement <em>Inherited Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getRootDefinition <em>Root Definition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTNamedElementImpl#getExcludedElements <em>Excluded Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UMLRTNamedElementImpl extends FacadeObjectImpl implements UMLRTNamedElement {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected int fFlags = 0;

	/**
	 * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected static final UMLRTModel MODEL_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getInheritanceKind() <em>Inheritance Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInheritanceKind()
	 * @generated
	 * @ordered
	 */
	protected static final UMLRTInheritanceKind INHERITANCE_KIND_EDEFAULT = UMLRTInheritanceKind.INHERITED;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isInherited() <em>Is Inherited</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isInherited()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INHERITED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isVirtualRedefinition() <em>Is Virtual Redefinition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isVirtualRedefinition()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_VIRTUAL_REDEFINITION_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isRedefinition() <em>Is Redefinition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isRedefinition()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REDEFINITION_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isExcluded() <em>Is Excluded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isExcluded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_EXCLUDED_EDEFAULT = false;


	protected static class NamedElementAdapter<F extends UMLRTNamedElementImpl> extends Reactor<F> {

		NamedElementAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, oldValue, newValue));
				}
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}

		protected static abstract class WithCode<F extends UMLRTNamedElementImpl> extends NamedElementAdapter<F> {
			WithCode(F facade) {
				super(facade);
			}

			protected abstract InternalFacadeEMap<String, String> getCode();

			@Override
			protected void handleValueAdded(Notification msg, int position, Object value) {
				Object feature = msg.getFeature();

				if ((feature == UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE)
						|| (feature == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
						|| (feature == UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE)
						|| (feature == UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY)) {

					if (getCode() != null) {
						List<String> languages = Code.getLanguages(msg.getNotifier());
						List<String> bodies = Code.getBodies(msg.getNotifier());

						if (languages.size() == bodies.size()) {
							// At this point, both the language and the body are added,
							// so pick them up
							getCode().facadePut(languages.get(position), bodies.get(position));
						}
					}
				}
			}

			@Override
			protected void handleValueRemoved(Notification msg, int position, Object value) {
				Object feature = msg.getFeature();

				if ((feature == UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE)
						|| (feature == UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE)) {

					if (getCode() != null) {
						// The languages are the keys, so that is our cue
						getCode().facadeRemoveKey(value);
					}
				} else if ((feature == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
						|| (feature == UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY)) {

					if (getCode() != null) {
						List<String> languages = Code.getLanguages(msg.getNotifier());
						List<String> bodies = Code.getBodies(msg.getNotifier());

						if (languages.size() == bodies.size()) {
							// At this point, both the language and the body should
							// correspond, so update the language that used to have
							// this body, if any
							getCode().entrySet().stream()
									.filter(entry -> Objects.equals(value, entry.getValue()))
									.findFirst()
									.ifPresent(entry -> {
										int languageIndex = languages.indexOf(entry.getKey());
										if (languageIndex >= 0) {
											entry.setValue(bodies.get(languageIndex));
										}
									});
						}
					}
				}
			}

			@Override
			protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
				if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE)
						|| (msg.getFeature() == UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE)) {
					// Re-map the body under the new language
					if (getCode() != null) {
						String body = getCode().facadeRemoveKey(oldValue);
						getCode().facadePut((String) newValue, body);
					}
				} else if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
						|| (msg.getFeature() == UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY)) {
					// Update the body under the current language
					if (getCode() != null) {
						List<String> languages = Code.getLanguages(msg.getNotifier());
						if (languages.size() > position) {
							String language = languages.get(position);
							getCode().facadePut(language, (String) newValue);
						}
					}
				}
			}
		}
	}

	protected static class Code extends FacadeEMap<String, String> {
		private static final long serialVersionUID = 1L;

		private boolean initializing;

		protected Code(UMLRTNamedElementImpl owner, int featureID) {
			super(owner, EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
					BasicEMap.Entry.class, featureID);

			List<String> languages = getLanguages(getCodeOwner());
			List<String> bodies = getBodies(getCodeOwner());
			int count = Math.min(languages.size(), bodies.size());
			for (int i = 0; i < count; i++) {
				@SuppressWarnings("unchecked")
				BasicEMap.Entry<String, String> entry = (BasicEMap.Entry<String, String>) EcoreUtil.create(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY);
				entry.setKey(languages.get(i));
				entry.setValue(bodies.get(i));
				facadeAdd(entry);
			}
		}

		protected EObject getCodeOwner() {
			UMLRTNamedElement owner = (UMLRTNamedElement) getEObject();
			return owner.toUML();
		}

		@Override
		protected void ensureEntryDataExists() {
			initializing = true;
			try {
				super.ensureEntryDataExists();
			} finally {
				initializing = false;
			}
		}

		@Override
		protected void didAdd(BasicEMap.Entry<String, String> entry) {
			if (initializing) {
				return;
			}

			int index = getLanguages(getCodeOwner()).indexOf(entry.getKey());
			if (index >= 0) {
				getBodies(getCodeOwner()).set(index, entry.getValue());
			} else {
				getLanguages(getCodeOwner()).add(entry.getKey());
				getBodies(getCodeOwner()).add(entry.getValue());
			}
		}

		@Override
		protected void didRemove(BasicEMap.Entry<String, String> entry) {
			List<String> languages = getLanguages(getCodeOwner());
			List<String> bodies = getBodies(getCodeOwner());

			int count = Math.min(languages.size(), bodies.size());

			for (int i = 0; i < count; i++) {
				if (Objects.equals(languages.get(i), entry.getKey())
						&& Objects.equals(bodies.get(i), entry.getValue())) {

					languages.remove(i);
					bodies.remove(i);
					break;
				}
			}
		}

		@Override
		protected void didModify(BasicEMap.Entry<String, String> entry, String oldValue) {
			int index = getLanguages(getCodeOwner()).indexOf(entry.getKey());
			if (index >= 0) {
				getBodies(getCodeOwner()).set(index, entry.getValue());
			} else {
				throw new IllegalArgumentException("No such language: " + entry.getKey());
			}
		}

		protected static List<String> getLanguages(Object owner) {
			List<String> result;

			if (owner instanceof OpaqueExpression) {
				result = ((OpaqueExpression) owner).getLanguages();
			} else if (owner instanceof OpaqueBehavior) {
				result = ((OpaqueBehavior) owner).getLanguages();
			} else {
				result = ECollections.emptyEList();
			}

			return result;
		}

		protected static List<String> getBodies(Object owner) {
			List<String> result;

			if (owner instanceof OpaqueExpression) {
				result = ((OpaqueExpression) owner).getBodies();
			} else if (owner instanceof OpaqueBehavior) {
				result = ((OpaqueBehavior) owner).getBodies();
			} else {
				result = ECollections.emptyEList();
			}

			return result;
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTNamedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.NAMED_ELEMENT;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTNamedElementImpl> createFacadeAdapter() {
		return new NamedElementAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<UMLRTNamedElement> getRedefinableElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTNamedElement> redefinableElements = (List<UMLRTNamedElement>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			if (redefinableElements == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT,
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINABLE_ELEMENT, null));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINABLE_ELEMENT, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinableElement(String name) {
		return getRedefinableElement(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinableElement(String name, boolean ignoreCase, EClass eClass) {
		redefinableElementLoop: for (UMLRTNamedElement redefinableElement : getRedefinableElements()) {
			if (eClass != null && !eClass.isInstance(redefinableElement)) {
				continue redefinableElementLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(redefinableElement.getName()) : name.equals(redefinableElement.getName()))) {
				continue redefinableElementLoop;
			}
			return redefinableElement;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTModel getModel() {
		return UMLRTModel.getInstance(toUML().eResource());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTInheritanceKind getInheritanceKind() {
		return UMLRTInheritanceKind.of(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getName() {
		return toUML().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setName(String newName) {
		toUML().setName(newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getQualifiedName() {
		return toUML().getQualifiedName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isInherited() {
		return getInheritanceKind() != UMLRTInheritanceKind.NONE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isVirtualRedefinition() {
		return getInheritanceKind() == UMLRTInheritanceKind.INHERITED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isRedefinition() {
		return getInheritanceKind() == UMLRTInheritanceKind.REDEFINED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isExcluded() {
		return getInheritanceKind() == UMLRTInheritanceKind.EXCLUDED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTNamedElement getInheritedElement() {
		UMLRTNamedElement result = getRedefinedElement();

		if ((result != null) && result.isVirtualRedefinition()) {
			result = result.getInheritedElement();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTNamedElement getRootDefinition() {
		// Start with the assumption that I am the root
		UMLRTNamedElement result = this;

		for (UMLRTNamedElement inh = result.getInheritedElement(); (inh != null); inh = result.getInheritedElement()) {
			result = inh;
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTNamedElement> getExcludedElements() {
		return excludedElements().collect(
				collectingAndThen(Collectors.<UMLRTNamedElement> toList(),
						list -> elist(UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__EXCLUDED_ELEMENT, list)));
	}

	protected Stream<? extends UMLRTNamedElement> excludedElements() {
		return exclusions()
				.map(UMLRTFactory::create)
				.filter(Objects::nonNull);
	}

	protected Stream<NamedElement> exclusions() {
		NamedElement uml = toUML();
		return (uml instanceof Namespace) ? exclusions((Namespace) uml) : Stream.empty();
	}

	protected static Stream<NamedElement> exclusions(Namespace namespace) {
		return (namespace instanceof InternalUMLRTElement)
				? ((List<?>) namespace.eGet(ExtUMLExtPackage.Literals.NAMESPACE__EXCLUDED_MEMBER)).stream()
						.filter(NamedElement.class::isInstance)
						.map(NamedElement.class::cast)
				: Stream.empty();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getExcludedElement(String name) {
		return getExcludedElement(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getExcludedElement(String name, boolean ignoreCase, EClass eClass) {
		excludedElementLoop: for (UMLRTNamedElement excludedElement : getExcludedElements()) {
			if (eClass != null && !eClass.isInstance(excludedElement)) {
				continue excludedElementLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(excludedElement.getName()) : name.equals(excludedElement.getName()))) {
				continue excludedElementLoop;
			}
			return excludedElement;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public NamedElement toUML() {
		return (NamedElement) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean redefines(UMLRTNamedElement element) {
		boolean result = element == this;

		if (!result) {
			int count = 0;
			for (UMLRTNamedElement redefined = getRedefinedElement(); !result && (redefined != null); redefined = redefined.getRedefinedElement()) {
				// Since the cycle is detected by checking if we hit 'this' again, after many iterations
				// we'll call this method recursively in case we started with something that wasn't part of
				// a cycle but later traversed up to a cycle. Note that this technique is susceptible to
				// missing cycles of depth greater than a hundred thousand elements, but that seems an
				// acceptable risk
				if (++count > 100000) {
					return redefined.redefines(element);
				} else if (redefined == this) {
					throw new IllegalStateException("Redefinition cycle including " + this); //$NON-NLS-1$
				}

				result = redefined == element;
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends UMLRTNamedElement> T getRedefinitionOf(T element) {
		return (T) Stream.concat(getRedefinableElements().stream(), getExcludedElements().stream())
				.filter(e -> e.redefines(element))
				.findAny().orElse(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTNamedElement> getRedefinitionChain() {
		List<UMLRTNamedElement> result;

		UMLRTNamedElement redef = getRedefinedElement();

		if (redef == null) {
			result = ECollections.singletonEList(this);
		} else {
			result = new ArrayList<>(3);
			result.add(this);
			for (; redef != null; redef = redef.getRedefinedElement()) {
				result.add(redef);
			}
			result = ECollections.unmodifiableEList(result);
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public <T extends UMLRTNamedElement> T getExcludedElement(String name, Class<T> type) {
		return excludedElements()
				.filter(type::isInstance)
				.filter(e -> Objects.equals(e.getName(), name))
				.map(type::cast)
				.findAny().orElse(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTNamedElement> allRedefinitions() {
		return Stream.of(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean exclude() {
		boolean result = false;

		NamedElement uml = toUML();
		if (uml instanceof InternalUMLRTElement) {
			result = ((InternalUMLRTElement) uml).rtExclude();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean reinherit() {
		boolean result = false;

		NamedElement uml = toUML();
		if (uml instanceof InternalUMLRTElement) {
			result = ((InternalUMLRTElement) uml).rtReinherit();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void reify() {
		NamedElement uml = toUML();
		if (uml instanceof InternalUMLRTElement) {
			InternalUMLRTElement internal = (InternalUMLRTElement) uml;
			if (internal.rtIsVirtual()) {
				internal.rtReify();
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void destroy() {
		super.destroy();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINED_ELEMENT:
			return getRedefinedElement();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINITION_CONTEXT:
			return getRedefinitionContext();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINABLE_ELEMENT:
			return getRedefinableElements();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__MODEL:
			return getModel();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__INHERITANCE_KIND:
			return getInheritanceKind();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__NAME:
			return getName();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__QUALIFIED_NAME:
			return getQualifiedName();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_INHERITED:
			return isInherited();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION:
			return isVirtualRedefinition();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_REDEFINITION:
			return isRedefinition();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_EXCLUDED:
			return isExcluded();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__INHERITED_ELEMENT:
			return getInheritedElement();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__ROOT_DEFINITION:
			return getRootDefinition();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__EXCLUDED_ELEMENT:
			return getExcludedElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		default:
			return eGet(referenceID, true, true);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.NAMED_ELEMENT__NAME:
			setName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.NAMED_ELEMENT__NAME:
			setName(NAME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.NAMED_ELEMENT__MODEL:
			return MODEL_EDEFAULT == null ? getModel() != null : !MODEL_EDEFAULT.equals(getModel());
		case UMLRTUMLRTPackage.NAMED_ELEMENT__INHERITANCE_KIND:
			return getInheritanceKind() != INHERITANCE_KIND_EDEFAULT;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__NAME:
			return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
		case UMLRTUMLRTPackage.NAMED_ELEMENT__QUALIFIED_NAME:
			return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_INHERITED:
			return isInherited() != IS_INHERITED_EDEFAULT;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_VIRTUAL_REDEFINITION:
			return isVirtualRedefinition() != IS_VIRTUAL_REDEFINITION_EDEFAULT;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_REDEFINITION:
			return isRedefinition() != IS_REDEFINITION_EDEFAULT;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__IS_EXCLUDED:
			return isExcluded() != IS_EXCLUDED_EDEFAULT;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__INHERITED_ELEMENT:
			return getInheritedElement() != null;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__ROOT_DEFINITION:
			return getRootDefinition() != null;
		case UMLRTUMLRTPackage.NAMED_ELEMENT__EXCLUDED_ELEMENT:
			return !getExcludedElements().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param eClass
	 *            The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

	protected <T extends EObject> T applyStereotype(Element base, Class<T> stereotype) {
		EClass eClass = (EClass) UMLRealTimePackage.eINSTANCE.getEClassifier(stereotype.getSimpleName());
		if (eClass == null) {
			eClass = (EClass) UMLRTStateMachinesPackage.eINSTANCE.getEClassifier(stereotype.getSimpleName());
		}

		return stereotype.cast(StereotypeApplicationHelper.getInstance(toUML()).applyStereotype(base, eClass));
	}

	/**
	 * Retrieves the cache adapter for this '<em><b>Named Element</b></em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return The cache adapter for this '<em><b>Named Element</b></em>'.
	 * @generated NOT
	 */
	protected CacheAdapter getCacheAdapter() {
		return CacheAdapter.getCacheAdapter(toUML());
	}

	protected static String initialLower(String name) {
		String result;

		if (UML2Util.isEmpty(name)) {
			result = name;
		} else {
			StringBuilder buf = new StringBuilder(name.length());
			buf.append(Character.toLowerCase(name.charAt(0)));
			buf.append(name, 1, name.length());
			result = buf.toString();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetRedefinedElement() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetRedefinitionContext() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetRedefinableElements() {
		return false;
	}

	@Override
	public String toString() {
		String pattern = isExcluded() ? "%s[X](name=%s)" : "%s(name=%s)"; //$NON-NLS-1$//$NON-NLS-2$
		return String.format(pattern, eClass().getName(), getName());
	}

} // UMLRTNamedElementImpl
