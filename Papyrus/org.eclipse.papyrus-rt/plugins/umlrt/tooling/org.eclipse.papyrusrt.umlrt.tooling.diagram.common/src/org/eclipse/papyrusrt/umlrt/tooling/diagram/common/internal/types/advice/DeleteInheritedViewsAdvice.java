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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.types.advice;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils.getDiagram;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * Advice that handles the special case of notation dependents deletion for
 * inherited views: these are views that reference the inherited element
 * that is not being deleted but that actually need to be deleted because
 * a virtual element redefining it that the view actually visualizes was deleted.
 */
public class DeleteInheritedViewsAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public DeleteInheritedViewsAdvice() {
		super();
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result;

		if (request instanceof ExcludeDependentsRequest) {
			result = getBeforeExcludeDependentsCommand((ExcludeDependentsRequest) request);
		} else {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = null;

		EObject destructee = request.getElementToDestroy();
		if (destructee instanceof Element) {
			result = getBeforeDestroyDependentsCommand(request, (Element) destructee);
		}

		return result;
	}

	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request, Element destructee) {
		ICommand result;

		Optional<Element> inherited = Optional.of(destructee)
				.map(UMLRTExtensionUtil::getRootDefinition);

		// Look for views that visualize this element but which in the notation
		// model actually reference the root definition
		Stream<View> views = inherited.map(this::getViews)
				.orElseGet(Stream::empty)
				.filter(v -> {
					EObject semantic = EditPartInheritanceUtils.resolveSemanticElement(v);
					return (semantic != destructee)
							&& (semantic instanceof Element)
							&& UMLRTExtensionUtil.redefines((Element) semantic, destructee);
				});

		result = views.map(request::getDestroyDependentCommand)
				.filter(Objects::nonNull)
				.reduce(UMLRTCommandUtils::flatCompose)
				.orElse(null);

		return result;
	}

	Stream<View> getViews(Element element) {
		return EMFHelper.getUsages(element).stream()
				.filter(s -> s.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
				.map(EStructuralFeature.Setting::getEObject)
				.map(View.class::cast)
				.filter(v -> v.eResource() != null); // Only views not already deleted
	}

	/**
	 * Advice to destroy the views of elements that are going to be lost because of
	 * the re-inheritance of an element. More precisely, views of local definitions
	 * contained (recursively) in the redefinition that is being re-inherited (children
	 * that are also redefinitions of inherited elements will still be visualizable on
	 * the diagram).
	 */
	protected ICommand getBeforeExcludeDependentsCommand(ExcludeDependentsRequest request) {
		ICommand result = null;

		if (!request.isExclude()) {
			// Only with a re-inherit might we lose some elements that are visualized.
			// Find any local definitions and delete their views
			Stream<Element> locals = request.getElementToExclude().allOwnedElements().stream()
					.filter(((Predicate<Element>) UMLRTExtensionUtil::isInherited).negate());

			Stream<View> views = locals.flatMap(this::getViews);

			result = views.map(v -> getDestroyViewCommand(request, v))
					.filter(Objects::nonNull)
					.reduce(UMLRTCommandUtils::flatCompose)
					.orElse(null);

			// If the element being re-inherited has any diagram, it must be
			// deleted because owning a diagram requires redefinition
			Diagram diagram = getDiagram(request.getElementToExclude(), null);
			if (diagram != null) {
				result = UMLRTCommandUtils.flatCompose(result, getDestroyViewCommand(request, diagram));
			}
		}

		return result;
	}

	protected ICommand getDestroyViewCommand(ExcludeDependentsRequest request, View view) {
		ICommand result = null;

		IElementType type = ElementTypeRegistry.getInstance().getElementType(view, request.getClientContext());
		if (type != null) {
			DestroyElementRequest destroy = new DestroyElementRequest(request.getEditingDomain(), view, false);
			destroy.setClientContext(request.getClientContext());
			result = type.getEditCommand(destroy);
		}

		return result;
	}
}
