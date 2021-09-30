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

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.NamedElement;

/**
 * A property-editor factory for multi-valued properties of façade elements.
 */
public abstract class FacadeListPropertyEditorFactory<E extends UMLRTNamedElement> extends RTPropertyEditorFactory<E> {

	private static final URI CREATION_DIALOGS_CONTEXT_URI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/dialogs/CreationDialogs.ctx", true);
	private static final ResourceSet viewModelResourceSet = new ResourceSetImpl();

	public FacadeListPropertyEditorFactory(EReference referenceIn, IObservableList<E> modelProperty) {
		super(referenceIn, modelProperty);
	}

	protected abstract Set<View> getCreationDialogViews();

	protected Set<View> getEditDialogViews(Object source) {
		return PropertiesRuntime.getConstraintEngine().getDisplayUnits(new StructuredSelection(source));
	}

	protected static Set<View> loadDialogViews(URI contextURI, Predicate<? super View> viewSelector) {
		Set<View> result = Collections.emptySet();

		Resource contextResource = viewModelResourceSet.getResource(contextURI, true);
		if (contextResource != null) {
			result = contextResource.getContents().stream().sequential()
					.filter(Context.class::isInstance).map(Context.class::cast)
					.flatMap(ctx -> ctx.getViews().stream().sequential())
					.filter(viewSelector)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		}

		return result;
	}

	protected static Set<View> loadDialogViews(Predicate<? super View> viewSelector) {
		return loadDialogViews(CREATION_DIALOGS_CONTEXT_URI, viewSelector);
	}

	protected static Set<View> loadDialogViews(String viewName) {
		return loadDialogViews(CREATION_DIALOGS_CONTEXT_URI, v -> viewName.equals(v.getName()));
	}

	@Override
	public Object edit(Control widget, Object source) {
		Set<View> views = getEditDialogViews(source);
		if (views.isEmpty() && (source instanceof FacadeObject)) {
			return asUMLRT(edit(widget, ((FacadeObject) source).toUML()));
		}

		if (!views.isEmpty()) {
			return doEdit(widget, source, views, getEditionDialogTitle(source));
		}

		return asUMLRT(source);
	}

	@Override
	protected Function<EObject, Predicate<Object>> filterFunction() {
		return v -> (o -> asUML(o) != v);
	}

	protected Object asUMLRT(Object object) {
		Object result = object;

		if (object instanceof NamedElement) {
			UMLRTNamedElement facade = UMLRTFactory.create((NamedElement) object);
			if (facade != null) {
				result = facade;
			}
		}

		return result;
	}

	protected Object asUML(Object object) {
		Object result = object;

		if (object instanceof UMLRTNamedElement) {
			result = ((UMLRTNamedElement) object).toUML();
		}

		return result;
	}

	@Override
	protected Object doCreateObject(Control widget, Object context) {
		Object result;

		// Prefer the same workflow as, for example, the port creation tool
		// in the diagram and the new port menu action in Model Explorer
		EObject container = PlatformHelper.getAdapter(context, EObject.class);
		IEditCommandRequest customCreationRequest = getCustomCreationRequest(container);
		if (customCreationRequest != null) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(container);
			ICommand create = edit.getEditCommand(customCreationRequest);
			Predicate<E> filter = new HashSet<>(getModelProperty())::contains;
			try {
				// Don't let the new element show up while it is being added
				getFilteredObservableList().addFilter(filter);
				TransactionalEditingDomain domain = customCreationRequest.getEditingDomain();
				Command emfCreate = ICommandWrapper.wrap(create, Command.class);
				domain.getCommandStack().execute(emfCreate);
				result = asUMLRT(emfCreate.getResult().iterator().next());
			} catch (OperationCanceledException e) {
				// Normal
				result = null;
			} catch (Exception e) {
				Activator.log.error("Failed to create new element", e); //$NON-NLS-1$
				result = null;
			} finally {
				// This refreshes the list to reveal the new element, if any
				getFilteredObservableList().removeFilter(filter);
			}
		} else {
			result = super.doCreateObject(widget, context);
		}

		return result;
	}

	/**
	 * To by-pass the standard dialog-based creation workflow, subclasses may
	 * override this to return an edit request to create the new element
	 * (presumably with suitable UI interactions).
	 * 
	 * @param container
	 *            the editing context (proposed container)
	 * 
	 * @return an edit-request to create or {@code null} for the standard
	 *         dialog interaction
	 */
	protected IEditCommandRequest getCustomCreationRequest(EObject container) {
		return null;
	}

	@Override
	protected Object createObject(Control widget, Object context, Object source) {
		if (source == null) {
			return null;
		}

		Set<View> views = getCreationDialogViews();
		if (!views.isEmpty()) {
			CreationContext creationContext = getCreationContext(context);
			creationContext.pushCreatedElement(source);
			try {
				return asUMLRT(doEdit(widget, source, views, getCreationDialogTitle()));
			} finally {
				creationContext.popCreatedElement(source);
			}
		}

		return asUMLRT(source);
	}

	@Override
	protected CreationContext basicGetCreationContext(Object element) {
		if (element instanceof UMLRTNamedElement) {
			element = ((UMLRTNamedElement) element).toUML();
		}

		return super.basicGetCreationContext(element);
	}
}
