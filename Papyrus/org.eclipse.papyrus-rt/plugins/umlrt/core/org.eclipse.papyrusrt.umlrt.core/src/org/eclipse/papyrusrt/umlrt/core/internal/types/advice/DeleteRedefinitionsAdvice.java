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

package org.eclipse.papyrusrt.umlrt.core.internal.types.advice;

import static org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest.DESTROY_DEPENDENTS_REQUEST_PARAMETER;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;

/**
 * Advice that handles the deletion of redefinitions of elements being deleted,
 * according to the semantics of UML-RT redefinition.
 */
public class DeleteRedefinitionsAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public DeleteRedefinitionsAdvice() {
		super();
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = null;

		EObject destructee = request.getElementToDestroy();

		if (destructee instanceof Generalization) {
			result = getBeforeDestroyDependentsCommand(request, (Generalization) destructee);
		} else if (destructee instanceof NamedElement) {
			UMLRTNamedElement rt = UMLRTFactory.create((NamedElement) destructee);

			if (rt != null) {
				if (rt.isInherited()) {
					// We cannot destroy a redefinition unless we are destroying the root definition,
					// or the generalization by which it is inherited, or perhaps the namespace that
					// contains it
					if (!canDestroy(request, rt)) {
						// Block the deletion operation
						result = UnexecutableCommand.INSTANCE;
					}
				} else {
					// Destroying the root element. Its redefinitions are dependents
					result = getBeforeDestroyDependentsCommand(request, rt);
				}
			}
		}

		return result;
	}

	boolean canDestroy(DestroyDependentsRequest request, UMLRTNamedElement element) {
		boolean result;

		Namespace namespace = element.toUML().getNamespace();
		if ((namespace != null) && isBeingDestroyed(request, namespace)) {
			result = true;
		} else {
			// The easy case is when the element that I redefine is being destroyed
			UMLRTNamedElement redefined = element.getRedefinedElement();
			if ((redefined != null) && isBeingDestroyed(request, redefined.toUML())) {
				result = true;
			} else {
				// Assume that the redefinition is not valid until otherwise determined
				result = true;

				UMLRTNamedElement root = element.getRootDefinition();
				if (root != null) {
					UMLRTNamedElement redefCtx = element.getRedefinitionContext();
					UMLRTNamedElement rootCtx = root.getRedefinitionContext();

					if ((redefCtx instanceof UMLRTClassifier)
							&& (rootCtx instanceof UMLRTClassifier)) {

						UMLRTClassifier subtype = (UMLRTClassifier) redefCtx;
						UMLRTClassifier supertype = (UMLRTClassifier) rootCtx;

						if (supertype.isSuperTypeOf(subtype)) {
							// Valid redefinition. Look for a generalization by which the
							// definition is inherited being destroyed
							result = generalizations(subtype, supertype)
									.anyMatch(g -> isBeingDestroyed(request, g));
						}
					} else if ((redefCtx != null) && redefCtx.redefines(rootCtx)) {
						// Case of, for example, state machine redefinition. Look for any
						// of the redefinition contexts being destroyed
						result = redefCtx.getRedefinitionChain().stream()
								.anyMatch(ctx -> isBeingDestroyed(request, ctx.toUML()));
					}
				}
			}
		}

		return result;
	}

	boolean isBeingDestroyed(DestroyDependentsRequest request, EObject object) {
		boolean result = false;

		for (EObject test = object; !result && (test != null); test = test.eContainer()) {
			result = (request.getElementToDestroy() == test)
					|| (request.getParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER) == test)
					|| request.getDependentElementsToDestroy().contains(test);
		}

		return result;
	}

	/**
	 * Obtains the sequence of generalization relationships from one classifier to another.
	 * 
	 * @param fromClassifier
	 *            an inheriting classifier
	 * @param toClassifier
	 *            an inherited classifier
	 * 
	 * @return the sequence of generalizations, which is undefined if the {@code fromClassifier}
	 *         does not actually inherit the {@code toClassifier}
	 */
	private Stream<Generalization> generalizations(UMLRTClassifier fromClassifier, UMLRTClassifier toClassifier) {
		Stream.Builder<Generalization> result = Stream.builder();

		UMLRTClassifier supertype;

		for (UMLRTClassifier classifier = fromClassifier; (classifier != null) && (classifier != toClassifier); classifier = supertype) {
			supertype = getSupertype(classifier);
			if (supertype != null) {
				result.add(classifier.toUML().getGeneralization(supertype.toUML()));
			}
		}

		return result.build();
	}

	private UMLRTClassifier getSupertype(UMLRTClassifier classifier) {
		UMLRTClassifier result = null;

		if (classifier instanceof UMLRTCapsule) {
			result = ((UMLRTCapsule) classifier).getSuperclass();
		} else if (classifier instanceof UMLRTProtocol) {
			result = ((UMLRTProtocol) classifier).getSuperProtocol();
		}

		return result;
	}

	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request, Generalization destructee) {
		ICommand result;

		Optional<UMLRTClassifier> general = Optional.ofNullable(UMLRTFactory.create(destructee.getGeneral()))
				.filter(UMLRTClassifier.class::isInstance).map(UMLRTClassifier.class::cast);

		if (!general.isPresent()) {
			result = null;
		} else {
			Optional<UMLRTClassifier> specific = Optional.ofNullable(UMLRTFactory.create(destructee.getSpecific()))
					.filter(UMLRTClassifier.class::isInstance).map(UMLRTClassifier.class::cast);

			// Seek and destroy all redefinitions in the hierarchy. This should be
			// more efficient than letting destroy-dependents traverse the redefinition
			// hierarchy recursively
			Stream<? extends UMLRTClassifier> hierarchy = specific.map(UMLRTClassifier::getHierarchy).orElseGet(Stream::empty);
			Stream<NamedElement> elementsToDestroy = hierarchy
					.flatMap(this::inheritableMembers)
					.filter(p -> inheritsFrom(p, general.get()))
					.map(UMLRTNamedElement::toUML);

			result = elementsToDestroy
					.map(request::getDestroyDependentCommand)
					.reduce(UMLRTCommandUtils::flatCompose)
					.orElse(null);
		}

		return result;
	}

	boolean inheritsFrom(UMLRTNamedElement element, UMLRTClassifier classifier) {
		return Optional.ofNullable(element.getRootDefinition())
				.map(UMLRTNamedElement::getRedefinitionContext)
				.filter(UMLRTClassifier.class::isInstance).map(UMLRTClassifier.class::cast)
				.map(c -> c.isSuperTypeOf(classifier))
				.orElse(false);
	}

	Stream<? extends UMLRTNamedElement> inheritableMembers(UMLRTNamedElement redefinitionContext) {
		@SuppressWarnings({ "unchecked", "restriction" })
		List<? extends UMLRTNamedElement> result = (List<? extends UMLRTNamedElement>) ((org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject) redefinitionContext)
				.facadeGet(org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT, true);
		return result.stream();
	}

	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request, UMLRTNamedElement destructee) {
		ICommand result;

		UMLRTNamedElement context = destructee.getRedefinitionContext();

		Stream<? extends UMLRTNamedElement> hierarchy = (context instanceof UMLRTClassifier)
				? ((UMLRTClassifier) context).getHierarchy()
				: (context instanceof UMLRTTransition) // Special case for triggers and guards
						? context.allRedefinitions()
						: Stream.empty();

		// Seek and destroy all redefinitions in the hierarchy. This should be
		// more efficient than letting destroy-dependents traverse the redefinition
		// hierarchy recursively
		Stream<NamedElement> elementsToDestroy = hierarchy.skip(1) // Don't look for redefinitions in the root type
				.flatMap(this::inheritableMembers)
				.filter(p -> p.redefines(destructee))
				.map(UMLRTNamedElement::toUML);

		result = elementsToDestroy
				.map(e -> getDestroyDependentCommand(request, e))
				.reduce(UMLRTCommandUtils::flatCompose)
				.orElse(null);

		return result;
	}

	protected ICommand getDestroyDependentCommand(DestroyDependentsRequest request, EObject object) {
		// Work around a bug in GMF: the dependents request does not propagate its client context
		// to the new destroy request that it creates
		ICommand result = request.getDestroyDependentCommand(object);

		if (result == null) {
			// The dependent is now registered as having been processed, so we can be sure
			// that we won't try to destroy it with another (redundant) command. Also,
			// we checked before getting here that this object wasn't already bing destroyed.
			// So, it is safe to create a new command, here
			DestroyElementRequest destroy = new DestroyElementRequest(
					request.getEditingDomain(), object, request.isConfirmationRequired());
			destroy.setClientContext(request.getClientContext());
			destroy.addParameters(request.getParameters());
			destroy.setParameter(DESTROY_DEPENDENTS_REQUEST_PARAMETER, request);

			Object eHelperContext = destroy.getEditHelperContext();
			IElementType context = ElementTypeRegistry.getInstance().getElementType(eHelperContext);

			if (context != null) {
				result = context.getEditCommand(destroy);
			}
		}

		return result;
	}
}
