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

package org.eclipse.papyrusrt.junit.rules;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * A JUnit test rule that initializes fields of {@link EObject} type
 * annotated with {@link Named @Named} from the test's
 * {@linkplain AbstractModelFixture model fixture}. The value
 * of the {@link Named} annotation on a field is the qualified name
 * of the model element to look up in the fixture's resource set.
 */
public class FixtureElementRule implements MethodRule {
	private final String namespace;

	public FixtureElementRule() {
		this(null);
	}

	/**
	 * Initializes me with a namespace in which to search.
	 * All names are relative to this namespace.
	 *
	 * @param namespace
	 *            the base namespace to search, or {@code null}
	 *            if all names should be taken as fully qualified
	 */
	public FixtureElementRule(String namespace) {
		super();

		this.namespace = namespace;
	}

	@Override
	public Statement apply(final Statement base, FrameworkMethod method, final Object target) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Collection<Field> toClear = loadFixtures(target);

				try {
					base.evaluate();
				} finally {
					safeClear(target, toClear);
				}
			}
		};
	}

	protected Collection<Field> loadFixtures(Object testInstance) throws Exception {
		Collection<Field> result = new ArrayList<>();

		ResourceSet rset = getModelFixture(testInstance);

		for (Field field : getAnnotatedFields(testInstance, Named.class)) {
			String qname = getQualifiedElementName(field);

			Collection<?> elements = Collections.emptyList();

			if (qname != null) {
				if (ENamedElement.class.isAssignableFrom(field.getType())) {
					elements = UML2Util.findENamedElements(rset, qname, NamedElement.SEPARATOR, false, EcorePackage.Literals.ENAMED_ELEMENT);
				} else if (NamedElement.class.isAssignableFrom(field.getType())) {
					elements = UMLUtil.findNamedElements(rset, qname);
				} else if (UMLRTNamedElement.class.isAssignableFrom(field.getType())) {
					elements = findFacades(rset, qname);
				}
			}

			if (!elements.isEmpty()) {
				field.setAccessible(true);

				// Multiple elements of the same name can be a namespace if they are of
				// different types.
				// Intentionally get the optional to throw if the test is misconfigured
				Object found = elements.stream()
						.filter(field.getType()::isInstance)
						.findAny().get();
				field.set(testInstance, found);
				result.add(field);
			}
		}

		return result;
	}

	Collection<? extends UMLRTNamedElement> findFacades(ResourceSet rset, String qname) {
		Collection<? extends UMLRTNamedElement> result = Collections.emptyList();

		String[] segments = qname.split(NamedElement.SEPARATOR);
		List<?> searchScope = new ArrayList<>(rset.getResources());
		for (int i = 0; i < segments.length; i++) {
			String name = segments[i];
			List<UMLRTNamedElement> searchResults = new ArrayList<>();
			for (Object next : searchScope) {
				for (UMLRTNamedElement child : getChildren(next)) {
					if (name.equals(child.getName())) {
						searchResults.add(child);
					}
				}
			}

			if ((i + 1) >= segments.length) {
				// Done with the search
				result = searchResults;
			} else {
				searchScope = searchResults;
			}
		}

		return result;
	}

	Iterable<? extends UMLRTNamedElement> getChildren(Object parent) {
		List<? extends UMLRTNamedElement> result = new ArrayList<>();

		if (parent instanceof UMLRTNamedElement) {
			result = new UMLRTSwitch<List<? extends UMLRTNamedElement>>() {
				@Override
				public List<? extends UMLRTNamedElement> caseModel(UMLRTModel object) {
					UMLRTPackage result = object.getRoot();
					return (result == null) ? emptyList() : singletonList(result);
				}

				@Override
				public List<? extends UMLRTNamedElement> casePackage(UMLRTPackage object) {
					List<UMLRTNamedElement> result = new ArrayList<>();
					result.addAll(object.getCapsules());
					result.addAll(object.getProtocols());
					result.addAll(object.getNestedPackages());
					return result;
				}

				@Override
				public List<? extends UMLRTNamedElement> caseNamedElement(UMLRTNamedElement object) {
					return object.getRedefinableElements();
				}
			}.doSwitch(parent);
		} else if (parent instanceof Resource) {
			result = ((Resource) parent).getContents().stream()
					.filter(Package.class::isInstance).map(Package.class::cast)
					.map(UMLRTPackage::getInstance)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		}

		return result;
	}

	/**
	 * Safely clears a bunch of {@code fields} of an object.
	 * 
	 * @param owner
	 *            the owner of the fields to clear
	 * @param fields
	 *            the fields to clear, which may include static fields
	 */
	protected void safeClear(Object owner, Collection<? extends Field> fields) {
		for (Field next : fields) {
			try {
				if (Modifier.isStatic(next.getModifiers())) {
					next.set(null, null);
				} else {
					next.set(owner, null);
				}
			} catch (Exception e) {
				// It should be accessible, so there oughtn't to be any
				// other problem
				e.printStackTrace();
			}
		}
	}

	private ResourceSet getModelFixture(Object testInstance) throws Exception {
		ResourceSet result = null;

		// Use getFields() because the model fixture, being a rule, must be public
		for (Field next : testInstance.getClass().getFields()) {
			if (AbstractModelFixture.class.isAssignableFrom(next.getType())) {
				if (next.isAnnotationPresent(Rule.class)) {
					// Instance field
					result = ((AbstractModelFixture<?>) next.get(testInstance)).getResourceSet();
					break;
				} else if (next.isAnnotationPresent(ClassRule.class)) {
					// Static field
					result = ((AbstractModelFixture<?>) next.get(null)).getResourceSet();
					break;
				}
			}
		}

		return result;
	}

	private String getQualifiedElementName(AnnotatedElement meta) {
		String result = null;

		Named named = meta.getAnnotation(Named.class);
		if (named != null) {
			result = named.value();

			if (namespace != null) {
				result = String.format("%s%s%s", namespace, NamedElement.SEPARATOR, result);
			}
		}

		return result;
	}

	private Iterable<Field> getAnnotatedFields(Object testInstance, Class<? extends Annotation> annotationType) {
		List<Field> result = new ArrayList<>();

		for (Class<?> class_ = testInstance.getClass(); (class_ != null) && (class_ != Object.class); class_ = class_.getSuperclass()) {
			for (Field next : class_.getDeclaredFields()) {
				if ((EObject.class.isAssignableFrom(next.getType()) || FacadeObject.class.isAssignableFrom(next.getType()))
						&& next.isAnnotationPresent(annotationType)) {

					result.add(next);
				}
			}
		}

		return result;
	}
}
