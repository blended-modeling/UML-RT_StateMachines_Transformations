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

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EContentsEList.FeatureIterator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsAnything;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A JUnit test fixture that loads a test model from a resource, via annotation
 * or specified in the constructor, and provides a variety of utilities for
 * manipulation and interrogation of the model and test assertions.
 * 
 * @see TestModel
 */
public class ModelFixture extends TestWatcher {
	private static final Object TEST_DEFERRED = new Object();

	private String testModelPath;
	private ResourceSet resourceSet;
	private Package testModel;
	private AdapterFactory adapterFactory;
	private boolean crossReferenceCheck;

	private List<Matcher<Object>> deferred;

	private ChangeRecorder recorder;
	private AtomicBoolean applyingChange = new AtomicBoolean();

	/**
	 * Initializes me. I will get the test model from an annotation.
	 * 
	 * @see TestModel
	 */
	public ModelFixture() {
		this(null);
	}

	/**
	 * Initializes me with an explicit test model path.
	 * 
	 * @param the
	 *            test model path. If {@code null}, then I will look for an annotation
	 */
	public ModelFixture(String testModelPath) {
		super();

		this.testModelPath = testModelPath;
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	public Resource getResource() {
		return testModel.eResource();
	}

	public Package getModel() {
		return testModel;
	}

	public UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(getModel());
	}

	@SuppressWarnings("unchecked")
	public <T> T create(EClass eclass) {
		return (T) resourceSet.getPackageRegistry()
				.getEFactory(eclass.getEPackage().getNsURI())
				.create(eclass);
	}

	public <T extends NamedElement> T create(EClass eclass, String name) {
		@SuppressWarnings("unchecked")
		T result = (T) resourceSet.getPackageRegistry()
				.getEFactory(eclass.getEPackage().getNsURI())
				.create(eclass);
		result.setName(name);
		return result;
	}

	@SuppressWarnings("unchecked")
	public <N extends NamedElement> N getElement(String name) {
		NamedElement result = find(testModel, Arrays.asList(name.split(NamedElement.SEPARATOR)), null);

		assertThat("Element not found: " + name, result, notNullValue());

		return (N) result;
	}

	public <N extends NamedElement> N getElement(String name, Class<N> metaclass) {
		NamedElement result = find(testModel, Arrays.asList(name.split(NamedElement.SEPARATOR)),
				metaclass::isInstance);

		assertThat("Element not found: " + name, result, notNullValue());

		return metaclass.cast(result);
	}

	public <N extends NamedElement> N createElement(String name, Class<N> metaclass) {
		EClass eclass = (EClass) UMLPackage.eINSTANCE.getEClassifier(metaclass.getSimpleName());
		N result = metaclass.cast(getModel().createPackagedElement(name, eclass));

		// Find the best stereotype and apply it
		Stream.concat(UMLRealTimePackage.eINSTANCE.getEClassifiers().stream(), UMLRTStateMachinesPackage.eINSTANCE.getEClassifiers().stream())
				.filter(EClass.class::isInstance).map(EClass.class::cast)
				.filter(Predicate.isEqual(UMLRealTimePackage.Literals.RT_REDEFINED_ELEMENT).negate())
				.filter(canExtend(metaclass))
				.findAny()
				.ifPresent(stereotype -> StereotypeApplicationHelper.getInstance(result).applyStereotype(result, stereotype));

		return result;
	}

	/**
	 * Simulates a GMF-style destruction of an {@code element}, including complete
	 * disaggregation of its subtree.
	 * 
	 * @param element
	 *            an element to "destroy"
	 */
	public void destroy(Element element) {
		class Destructor extends UML2Util {
			void destroyRecursively(EObject eObject) {
				if (eObject.eResource() == null) {
					// GMF only destroys attached elements
					return;
				}

				if (!eObject.eContents().isEmpty()) {
					// First, the contents. GMF only works on the
					// real UML containment, so we do likewise
					List<EObject> contents = new ArrayList<>(EContentsEList.createEContentsEList(eObject));
					contents.forEach(this::destroyRecursively);
				}

				removeReferences(eObject);

				EcoreUtil.remove(eObject);
			}

			void removeReferences(EObject eObject) {
				// Remove incoming references
				for (EStructuralFeature.Setting next : new ArrayList<>(getNonNavigableInverseReferences(eObject))) {
					EReference ref = (EReference) next.getEStructuralFeature();

					if (ref.isChangeable() && !ref.isContainment() && !ref.isContainer() && !ref.isDerived()) {
						EcoreUtil.remove(next, eObject);
					}
				}

				// Remove outgoing references
				for (EReference xref : eObject.eClass().getEAllReferences()) {
					if (!xref.isContainment() && !xref.isContainer() && xref.isChangeable() && !xref.isDerived()
							&& eObject.eIsSet(xref)) {

						eObject.eUnset(xref);
					}
				}
			}
		}

		new Destructor().destroyRecursively(element);
	}

	Predicate<EClass> canExtend(Class<?> umlMetaclass) {
		return eClass -> eClass.getEAllReferences().stream()
				.filter(r -> r.getName().startsWith("base_"))
				.anyMatch(r -> r.getEReferenceType().getInstanceClass().isAssignableFrom(umlMetaclass));
	}

	Stream<NamedElement> members(Namespace namespace, String name) {
		return namespace.getMembers().stream()
				.filter(m -> name.equals(m.getName()));
	}

	NamedElement find(NamedElement element, List<String> qualifiedName, Predicate<? super NamedElement> filter) {
		NamedElement result;

		if (qualifiedName.isEmpty()) {
			result = element;
		} else if (element instanceof Namespace) {
			Stream<NamedElement> members = members((Namespace) element, qualifiedName.get(0));
			List<String> subQualifiedName = qualifiedName.subList(1, qualifiedName.size());
			result = members.map(m -> find(m, subQualifiedName, filter))
					.filter(Objects::nonNull)
					.filter(safeFilter(filter))
					.findFirst()
					.orElse(null);
		} else {
			result = null;
		}

		return result;
	}

	private static <T> Predicate<T> safeFilter(Predicate<T> filter) {
		return (filter == null) ? __ -> true : filter;
	}

	@Override
	protected void starting(Description description) {
		Optional<String> testModelPath = getTestModelPath(description);
		testModelPath.ifPresent(path_ -> {
			Optional<Class<?>> context = getAnyTest(description).map(Description::getTestClass);
			context.ifPresent(testClass -> {
				String path = path_;
				if (!path.startsWith("resources/")) {
					path = "resources/" + path;
				}
				URI modelURI = URI.createURI(getResource(testClass, path).toExternalForm(), true);
				loadTestModel(modelURI);
			});
		});

		crossReferenceCheck = isCrossReferenceCheck(description);

		ComposedAdapterFactory composed = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		composed.addAdapterFactory(new StereotypeApplicationItemProviderAdapterFactory());
		composed.addAdapterFactory(new UMLItemProviderAdapterFactory());
		composed.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		composed.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		adapterFactory = composed;
	}

	private boolean isCrossReferenceCheck(Description description) {
		return getCrossReferenceCheck(description)
				.map(CrossReferenceCheck::value)
				// Default of the cross-reference check value is true
				.orElse(true);
	}

	private Optional<String> getTestModelPath(Description description) {
		Optional<String> result = Optional.ofNullable(testModelPath);
		return result.isPresent()
				? result
				: getTestModel(description).map(TestModel::value);
	}

	private URL getResource(Class<?> context, String path) {
		URL result = context.getClassLoader().getResource(path);

		if (result == null) {
			// Maybe we aren't deployed in a JAR but are in the development environment
			String base = context.getResource(context.getSimpleName() + ".class").toExternalForm();
			base = base.split("bin/")[0];

			try {
				result = new URL(base + path);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail("Malformed test resource URL: " + e.getMessage());
			}
		}

		return result;
	}

	private void loadTestModel(URI modelURI) {
		resourceSet = new ResourceSetImpl();
		UMLRTResourcesUtil.init(resourceSet);
		UMLRTResourcesUtil.setUndoRedoQuery(resourceSet, applyingChange::get);

		// GMF-ish environment
		GMFishXrefAdapter.demandInstance(resourceSet);

		testModel = UML2Util.load(resourceSet, modelURI, UMLPackage.Literals.PACKAGE);
	}

	@Override
	protected void finished(Description description) {
		if (resourceSet != null) {
			try {
				// Check deferred assertions
				if (deferred != null) {
					deferred.forEach(m -> assertThat(TEST_DEFERRED, m));
				}

				// Check that nothing accidentally created the wrong kind of model element
				assertModelImplementationOverrides();

				if (crossReferenceCheck) {
					// And that no stereotypes applications for virtual elements are
					// persisted in the user resource and vice versa and that cross-references
					// in the model that are persisted only reference real elements
					assertPersistableCrossReferences();
				}

				// And that no façades were instantiated, if applicable
				getAnnotation(description, NoFacade.class)
						.map(NoFacade::value)
						.filter(Boolean.TRUE::equals)
						.ifPresent(__ -> assertNoFacades());
			} finally {
				if (recorder != null) {
					recorder.dispose();
				}

				((IDisposable) adapterFactory).dispose();

				resourceSet.getResources().forEach(Resource::unload);
				resourceSet.getResources().clear();
				resourceSet.eAdapters().clear();
			}
		}

		testModel = null;
		resourceSet = null;
		adapterFactory = null;
	}

	public String label(EObject object) {
		IItemLabelProvider labels = (IItemLabelProvider) adapterFactory.adapt(object, IItemLabelProvider.class);
		return (labels != null) ? labels.getText(object) : object.toString();
	}

	List<Resource> modelResources() {
		return resourceSet.getResources().stream()
				.filter(r -> !"pathmap".equals(r.getURI().scheme()))
				.collect(Collectors.toList());
	}

	void assertModelImplementationOverrides() {
		Set<EClass> overridden = new HashSet<>(Arrays.asList(
				UMLRealTimePackage.Literals.RT_PORT,
				UMLPackage.Literals.CLASS,
				UMLPackage.Literals.PORT,
				UMLPackage.Literals.PROPERTY,
				UMLPackage.Literals.COLLABORATION,
				UMLPackage.Literals.CONNECTOR,
				UMLPackage.Literals.CONNECTOR_END,
				UMLPackage.Literals.CONSTRAINT,
				UMLPackage.Literals.INTERFACE,
				UMLPackage.Literals.LITERAL_INTEGER,
				UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL,
				UMLPackage.Literals.LITERAL_STRING,
				UMLPackage.Literals.OPAQUE_BEHAVIOR,
				UMLPackage.Literals.OPAQUE_EXPRESSION,
				UMLPackage.Literals.OPERATION,
				UMLPackage.Literals.PACKAGE,
				UMLPackage.Literals.PARAMETER,
				UMLPackage.Literals.STATE_MACHINE,
				UMLPackage.Literals.REGION,
				UMLPackage.Literals.STATE,
				UMLPackage.Literals.PSEUDOSTATE,
				UMLPackage.Literals.FINAL_STATE,
				UMLPackage.Literals.TRANSITION,
				UMLPackage.Literals.TRIGGER,
				UMLPackage.Literals.MODEL));

		EcoreUtil.getAllContents(modelResources()).forEachRemaining(next -> {
			if (next instanceof EObject) {
				EObject eObject = (EObject) next;
				if (overridden.contains(eObject.eClass()) != (eObject instanceof InternalUMLRTElement)) {
					fail("Incorrect impl class override for " + label(eObject));
				}
			}
		});
	}

	void assertPersistableCrossReferences() {
		List<EStructuralFeature.Setting> badXrefs = new ArrayList<>();

		for (TreeIterator<EObject> iter = getResource().getAllContents(); iter.hasNext();) {
			InternalEObject next = (InternalEObject) iter.next();

			if ((next instanceof Element) && UMLRTExtensionUtil.isVirtualElement((Element) next)) {
				// Skip this: of course it has references to other virtual elements
				iter.prune();
			} else {
				// Check all persistent cross-references

				// Non-resolving iterator to get what is actually persisted (resolving includes
				// not only proxy resolution but also inheritance resolution)
				FeatureIterator<InternalEObject> persistentXrefs = new EContentsEList.FeatureIteratorImpl<InternalEObject>(next, next.eClass().getEAllReferences()) {
					@Override
					protected boolean isIncluded(EStructuralFeature eStructuralFeature) {
						EReference ref = (EReference) eStructuralFeature;
						return !ref.isContainment() && !ref.isTransient() && next.eIsSet(ref);
					}
				};

				while (persistentXrefs.hasNext()) {
					InternalEObject xref = persistentXrefs.next();
					Resource res = xref.eInternalResource();

					if (((res == null) && !xref.eIsProxy()) || (res instanceof ExtensionResource)) {
						// Dangling reference
						badXrefs.add(next.eSetting(persistentXrefs.feature()));
					}
				}
			}
		}

		if (!badXrefs.isEmpty()) {
			Function<EStructuralFeature.Setting, String> toString = s -> String.format("%s of %s",
					s.getEStructuralFeature().getName(),
					label(s.getEObject()));

			fail("Dangling references in the model\n" +
					badXrefs.stream().map(toString).collect(joining(", ")));
		}

		//
		// Stuff in the Extension Extent
		//

		List<EObject> badStereotypes = new ArrayList<>();

		Resource resource = ExtensionResource.getExtensionExtent(getModel());
		if (resource != null) {
			for (EObject next : resource.getContents()) {
				if (next instanceof Element) {
					fail("Found an UML element in the extension extent: " + next);
				} else if (!(next instanceof ExtElement)) {
					// It should be a stereotype application
					Element baseElement = UMLUtil.getBaseElement(next);
					if (baseElement != null) {
						if (!UMLRTExtensionUtil.isVirtualElement(baseElement)) {
							badStereotypes.add(next);
						}
					}
				}
			}

			assertThat("Stereotypes of real elements are in the extension extent",
					badStereotypes, not(hasItem(anything())));
		}
	}

	void assertNoFacades() {
		try {
			Class<? extends Adapter> facadeAdapterType = Class.forName(
					"org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.FacadeObjectImpl$BasicFacadeAdapter") //$NON-NLS-1$
					.asSubclass(Adapter.class);
			EcoreUtil.getAllContents(modelResources()).forEachRemaining(next -> {
				if (next instanceof EObject) {
					EObject eObject = (EObject) next;
					eObject.eAdapters().stream()
							.filter(facadeAdapterType::isInstance)
							.map(Adapter.class::cast)
							.forEach(adapter -> fail("Found a facade adapter: " + label((EObject) adapter.getTarget())));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not get facade adapter type: " + e.getMessage());
		}
	}

	Optional<TestModel> getTestModel(Description description) {
		return getAnnotation(description, TestModel.class);
	}

	Optional<CrossReferenceCheck> getCrossReferenceCheck(Description description) {
		return getAnnotation(description, CrossReferenceCheck.class);
	}

	<A extends Annotation> Optional<A> getAnnotation(Description description, Class<A> annotationType) {
		A result = description.getAnnotation(annotationType);

		if ((result == null) && (description.getTestClass() != null)) {
			result = description.getTestClass().getAnnotation(annotationType);
		}

		return Optional.ofNullable(result);
	}

	Optional<Description> getAnyTest(Description description) {
		Optional<Description> result;

		if (description.getTestClass() != null) {
			result = Optional.of(description);
		} else {
			result = description.getChildren().stream()
					.map(this::getAnyTest)
					.filter(Optional::isPresent)
					.findAny()
					.flatMap(Function.identity());
		}

		return result;
	}

	public <T> void expectNotification(Notifier notifier, Object feature, int eventType,
			Matcher<T> oldValueMatcher, Matcher<T> newValueMatcher, Runnable script) {

		expectNotification(notifier, feature, eventType, oldValueMatcher, newValueMatcher, script, true);
	}

	public <T> void expectNotification(FacadeObject notifier, Object feature, int eventType,
			Matcher<T> oldValueMatcher, Matcher<T> newValueMatcher, Runnable script) {

		expectNotification((EObject) notifier, feature, eventType, oldValueMatcher, newValueMatcher, script);
	}

	public <T> void expectNoNotification(Notifier notifier, Object feature, int eventType,
			Matcher<T> oldValueMatcher, Matcher<T> newValueMatcher, Runnable script) {

		expectNotification(notifier, feature, eventType, oldValueMatcher, newValueMatcher, script, false);
	}

	public <T> void expectNoNotification(FacadeObject notifier, Object feature, int eventType,
			Matcher<T> oldValueMatcher, Matcher<T> newValueMatcher, Runnable script) {

		expectNoNotification((EObject) notifier, feature, eventType, oldValueMatcher, newValueMatcher, script);
	}

	private <T> void expectNotification(Notifier notifier, Object feature, int eventType,
			Matcher<T> oldValueMatcher, Matcher<T> newValueMatcher, Runnable script,
			boolean expected) {

		AtomicBoolean found = new AtomicBoolean();
		AtomicBoolean everFoundOldValue = new AtomicBoolean();
		AtomicBoolean everFoundNewValue = new AtomicBoolean();

		Adapter adapter = new EContentAdapter() {
			{
				// It may be "contained" in an unset feature (e.g., Connector::end)
				addAdapter(notifier);
			}

			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);

				if (!notification.isTouch()) {
					if ((notification.getNotifier() == notifier)
							&& (notification.getFeature() == feature)
							&& (notification.getEventType() == eventType)) {

						boolean oldValueMatches = (oldValueMatcher == null)
								|| oldValueMatcher.matches(notification.getOldValue());
						boolean newValueMatches = (newValueMatcher == null)
								|| newValueMatcher.matches(notification.getNewValue());

						found.compareAndSet(false, oldValueMatches && newValueMatches);
						everFoundOldValue.compareAndSet(false, oldValueMatches);
						everFoundNewValue.compareAndSet(false, newValueMatches);
					}
				}
			}
		};

		if (notifier instanceof FacadeObject) {
			// Façade objects are free-floating, so we can only attach
			// adapters directly to them
			notifier.eAdapters().add(adapter);
		} else {
			resourceSet.eAdapters().add(adapter);
		}
		try {
			script.run();
		} finally {
			if (notifier instanceof FacadeObject) {
				notifier.eAdapters().remove(adapter);
			} else {
				resourceSet.eAdapters().remove(adapter);
			}
		}

		if (found.get() != expected) {
			org.hamcrest.Description desc = new StringDescription();

			String notifierType = (notifier instanceof EObject)
					? ((EObject) notifier).eClass().getName()
					: String.valueOf(notifier);
			String featureName = (feature instanceof EStructuralFeature)
					? ((EStructuralFeature) feature).getName()
					: String.valueOf(feature);

			if (expected) {
				desc.appendText("no notification on ");
			} else {
				desc.appendText("notification on ");
			}
			desc.appendText(notifierType);
			desc.appendText("::").appendText(featureName);

			if (!everFoundNewValue.get() && !isTrivial(newValueMatcher)) {
				desc.appendText(" where newValue ");
				newValueMatcher.describeTo(desc);
			} else if (!everFoundOldValue.get() && !isTrivial(oldValueMatcher)) {
				desc.appendText(" where oldValue ");
				oldValueMatcher.describeTo(desc);
			} else if (newValueMatcher != null) {
				// At least there's a new-value IsAnything
				desc.appendText(" where newValue ");
				newValueMatcher.describeTo(desc);
			}

			fail(desc.toString());
		}
	}

	private static boolean isTrivial(Matcher<?> matcher) {
		return (matcher == null) || (matcher instanceof IsAnything<?>);
	}

	@SuppressWarnings("unchecked")
	public <T> Matcher<T> defer(Supplier<Matcher<T>> matcherSupplier) {
		final List<Object> deferred = new ArrayList<>();

		return new BaseMatcher<T>() {
			{
				if (ModelFixture.this.deferred == null) {
					ModelFixture.this.deferred = new ArrayList<>();
				}
				ModelFixture.this.deferred.add((Matcher<Object>) this);
			}

			@Override
			public void describeMismatch(Object item, org.hamcrest.Description description) {
				if (deferred.isEmpty()) {
					description.appendText("Deferred matcher was never asserted: ");
					describeTo(description);
				} else {
					matcherSupplier.get().describeMismatch(deferred.get(0), description);
				}
			}

			@Override
			public void describeTo(org.hamcrest.Description description) {
				matcherSupplier.get().describeTo(description);
			}

			@Override
			public boolean matches(Object item) {
				if (item == TEST_DEFERRED) {
					return deferred.stream().anyMatch(matcherSupplier.get()::matches);
				} else {
					deferred.add(item);
					return true;
				}
			}
		};
	}

	public <T> Matcher<T> defer(Function<T, Matcher<T>> matcher, T expected) {
		return defer(() -> matcher.apply(expected));
	}

	public ChangeDescription record(Runnable action) {
		ChangeDescription result;

		if (recorder == null) {
			recorder = new ChangeRecorder(getResourceSet());
		} else {
			recorder.beginRecording(Collections.singleton(getResourceSet()));
		}

		try {
			action.run();
		} finally {
			result = recorder.endRecording();
		}

		return result;
	}

	/**
	 * Performs an undo (or redo, depending on the context) of a {@code change}
	 * by applying and reversing it.
	 * 
	 * @param change
	 *            a change to undo
	 * @return the undone change, ready to redo
	 */
	public ChangeDescription undo(ChangeDescription change) {
		boolean restore = applyingChange.compareAndSet(false, true);

		try {
			UMLRTExtensionUtil.run(testModel, change::applyAndReverse);
		} finally {
			applyingChange.compareAndSet(restore, false);
		}

		return change;
	}

	public void repeat(int count, Runnable action) {
		repeat(count, __ -> action.run());
	}

	public void repeat(int count, IntConsumer action) {
		IntStream.range(0, count).forEach(i -> {
			try {
				action.accept(i);
			} catch (AssertionError | RuntimeException e) {
				int iteration = i + 1;
				String ordinal;
				switch (iteration % 10) {
				case 1:
					ordinal = "st";
					break;
				case 2:
					ordinal = "nd";
					break;
				case 3:
					ordinal = "rd";
					break;
				default:
					ordinal = "th";
					break;
				}

				String message = String.format("[%d%s iteration] %s", iteration, ordinal, e.getMessage());
				if (e instanceof AssumptionViolatedException) {
					throw new AssumptionViolatedException(message, e);
				} else if (e instanceof AssertionError) {
					throw new AssertionError(message, e);
				} else {
					try {
						throw (RuntimeException) e.getClass()
								.getConstructor(String.class, Throwable.class)
								.newInstance(message, e);
					} catch (Exception __) {
						throw new WrappedException(message, (RuntimeException) e);
					}
				}
			}
		});
	}
}
