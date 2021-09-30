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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngine;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.uml.properties.creation.UMLPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.IFilteredObservableList;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <p>
 * A specialized property-editor factory that ensures that new elements
 * are (temporarily) attached to the model while being edited in the creation
 * dialog, so that
 * </p>
 * <ul>
 * <li>the RT profile context is available to support RT editing
 * semantics</li>
 * <li>the transactional editing domain is available to support
 * tables and the edit service</li>
 * </ul>
 */
public class RTPropertyEditorFactory<E> extends UMLPropertyEditorFactory {

	private final IObservableList<E> modelProperty;

	private final Map<EObject, Predicate<Object>> modelPropertyFilters = new HashMap<>();

	/**
	 * Initializes me with the reference that I edit.
	 * 
	 * @param referenceIn
	 *            my reference
	 */
	public RTPropertyEditorFactory(EReference referenceIn) {
		this(referenceIn, null);
	}

	/**
	 * Initializes me with the reference that I edit and the observable list that presents
	 * it in the UI. This is useful with a {@linkplain IFilteredObservableList filtered list},
	 * for example, to filter out of the UI presentation an object that is in the process of
	 * being created, while it is being edited in a dialog.
	 *
	 * @param referenceIn
	 *            my reference
	 * @param modelProperty
	 *            the model property that I am editing. May be {@code null}
	 */
	public RTPropertyEditorFactory(EReference referenceIn, IObservableList<E> modelProperty) {
		super(referenceIn);

		this.modelProperty = modelProperty;
	}

	/**
	 * Provides a customized creation context that sneakily (without EMF notifications)
	 * attaches created model elements to the model for the duration of the dialog.
	 */
	@Override
	protected final CreationContext getCreationContext(Object element) {
		CreationContext result = basicGetCreationContext(element);

		return (result == null)
				? result
				: wrapCreationContext(result);
	}

	protected CreationContext basicGetCreationContext(Object element) {
		return super.getCreationContext(element);
	}

	private CreationContext wrapCreationContext(CreationContext context) {
		return new AttachedCreationContext(context);
	}

	@Override
	protected Object createObject(Control widget, Object context, Object source) {
		if (source == null) {
			return null;
		}

		IStructuredSelection selection = new StructuredSelection(source);

		// We have to sneak the object into its containing context in the model
		// before calculating its applicable views because constraints can rely
		// on that containing context
		CreationContext creationContext = getCreationContext(context);
		creationContext.pushCreatedElement(source);
		try {
			ConstraintEngine<View> constraintEngine = PropertiesRuntime.getConstraintEngine();
			Set<View> views = constraintEngine.getDisplayUnits(selection);
			if (!views.isEmpty()) {
				return doEdit(widget, source, views, getCreationDialogTitle());
			}
		} finally {
			creationContext.popCreatedElement(source);
		}

		return source;
	}

	@Override
	protected Object doCreateObject(Control widget, Object context) {
		Object instance;

		if (isContainment(referenceIn)) {
			instance = simpleCreateObject(widget);
		} else {
			instance = createObjectInDifferentContainer(widget);
		}

		if (instance instanceof EObject) {
			// Let the edit dialog know that it's editing a new element
			NewElementUtil.elementCreated((EObject) instance,
					() -> Optional.of(NestedEditingDialogContext.getInstance())
							.map(NestedEditingDialogContext::getResourceSet)
							.map(TransactionUtil::getEditingDomain));
		}

		return createObject(widget, context, instance);
	}

	CreateIn getCreateIn(CreationContext context, EObject newElement) {
		CreateIn result = null;

		if (isContainment(referenceIn)) {
			// Implicit create-in case
			result = new CreateIn() {
				// Pass
			};
			result.createInReference = referenceIn;
			result.createInObject = (EObject) context.getCreationContextElement();
		} else {
			result = createIn.get(newElement);
		}

		return result;
	}

	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		if (!isContainment(referenceIn)) {
			for (Object objectToValidate : objectsToValidate) {
				// We add the object to the containment reference
				// They will be automatically added to the edited reference
				// (referenceIn) after this method returns
				CreateIn creationInformation = this.createIn.get(objectToValidate);
				if (creationInformation != null) {
					set(creationInformation.createInObject, creationInformation.createInReference, objectToValidate);
				} else {
					Activator.log.warn("Unknown object : " + objectToValidate); //$NON-NLS-1$
				}
			}
		}

		return objectsToValidate;
	}

	protected boolean isContainment(EReference reference) {
		boolean result = reference.isContainment();

		if (!result) {
			// It is effectively a containment if it subsets a containment
			EAnnotation annotation = reference.getEAnnotation("subsets"); //$NON-NLS-1$
			if (annotation != null) {
				for (EObject next : annotation.getReferences()) {
					if (next instanceof EReference) {
						EReference superset = (EReference) next;
						if (isContainment(superset)) {
							result = true;
							break;
						}
					}
				}
			}
		}

		return result;
	}

	void filter(EObject createdObject) {
		IFilteredObservableList<E> list = getFilteredObservableList();
		if (list != null) {
			list.addFilter(getFilter(createdObject));
		}
	}

	protected final IObservableList<E> getModelProperty() {
		return modelProperty;
	}

	protected final IFilteredObservableList<E> getFilteredObservableList() {
		return IFilteredObservableList.getFilteredList(getModelProperty());
	}

	private Predicate<Object> getFilter(EObject createdObject) {
		return modelPropertyFilters.computeIfAbsent(createdObject, filterFunction());
	}

	protected Function<EObject, Predicate<Object>> filterFunction() {
		return v -> (o -> o != v);
	}

	void unfilter(EObject createdObject) {
		IFilteredObservableList<E> list = getFilteredObservableList();
		if (list != null) {
			list.removeFilter(modelPropertyFilters.remove(createdObject));
		}
	}

	@Override
	protected EObject simpleCreateObject(Control widget) {
		EClass toCreate = chooseEClass(widget);
		if (toCreate == null) {
			toCreate = this.type;
		}
		return create(toCreate);
	}

	protected EObject create(EClass eClass) {
		ResourceSet rset = NestedEditingDialogContext.getInstance().getResourceSet();
		EFactory factory = rset.getPackageRegistry().getEFactory(UMLPackage.eNS_URI);
		if (factory == null) {
			factory = UMLFactory.eINSTANCE;
		}
		return factory.create(eClass);
	}

	/**
	 * Applies stereotypes to the newly created (temporarily in place) element
	 * after its temporary attachment to the model is established, so that it
	 * may be properly edited in the dialog.
	 * 
	 * @param newElement
	 *            the new element to which to apply any needed stereotypes
	 */
	protected void applyStereotypes(Element newElement) {
		// Pass
	}

	//
	// Nested types
	//

	/**
	 * A decorating {@link CreationContext} that attaches the newly created
	 * element temporarily while it is being edited in the dialog.
	 */
	class AttachedCreationContext implements CreationContext {
		private final CreationContext delegate;

		AttachedCreationContext(CreationContext delegate) {
			super();

			this.delegate = delegate;
		}

		/**
		 * Attaches the {@code newElement} to its intended container in
		 * the model so that it may be correctly configured by the dialog.
		 */
		@Override
		public void pushCreatedElement(Object newElement) {
			if (newElement instanceof EObject) {
				EObject created = (EObject) newElement;
				CreateIn createIn = getCreateIn(this, created);
				if (createIn != null) {
					// Don't let the model property see this new element. We don't
					// want it to show in the UI temporarily
					filter(created);

					withoutReification(createIn, () -> eAdd(createIn.createInObject, createIn.createInReference, created));
				}
			}

			delegate.pushCreatedElement(newElement);
		}

		void withoutReification(CreateIn creationContext, Runnable action) {
			if (creationContext.createInObject instanceof Element) {
				UMLRTExtensionUtil.run((Element) creationContext.createInObject, action);
			} else {
				action.run();
			}
		}

		@SuppressWarnings("unchecked")
		private void eAdd(EObject owner, EStructuralFeature feature, Object value) {
			if (feature.isMany()) {
				((EList<Object>) owner.eGet(feature)).add(value);
			} else {
				owner.eSet(feature, value);
			}

			if (value instanceof Element) {
				applyStereotypes((Element) value);
			}
		}

		/**
		 * Detaches the {@code newElement} from its interim container so
		 * that it may later be added if the user completes the dialog normally.
		 */
		@Override
		public void popCreatedElement(Object newElement) {
			delegate.popCreatedElement(newElement);

			if (newElement instanceof EObject) {
				EObject created = (EObject) newElement;
				CreateIn createIn = getCreateIn(this, created);
				if (createIn != null) {
					withoutReification(createIn, () -> eRemove(createIn.createInObject, createIn.createInReference, created));

					// Remove the UI filter from the model property
					unfilter(created);
				}

				// While it was attached, we may have calculated derived
				// properties that are invalidated by the object's
				// removal and the cache won't have been notified of this
				CacheAdapter cache = CacheAdapter.getCacheAdapter(created);
				if (cache != null) {
					cache.clear();
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void eRemove(EObject owner, EStructuralFeature feature, Object value) {
			if (feature.isMany()) {
				((EList<Object>) owner.eGet(feature)).remove(value);
			} else if (owner.eGet(feature) == value) {
				owner.eUnset(feature);
			}
		}

		@Override
		public EObject getCreationContextElement() {
			return (EObject) delegate.getCreationContextElement();
		}
	}

}
