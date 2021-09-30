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

package org.eclipse.papyrusrt.umlrt.core.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

import com.google.common.collect.ImmutableList;

/**
 * A command to set the exclusion state of inherited model elements.
 * The result of execution contains the {@link #getElement() elements}
 * for which the exclusion state was successfully changed. Otherwise, the
 * result is empty (but still OK), usually because the elements were already
 * in the desired state.
 */
public class ExclusionCommand extends AbstractTransactionalCommand {

	private List<Element> elements;
	private boolean exclude;

	/**
	 * Initializes me with the {@code element} to exclude or reinherit and whether it
	 * is to be excluded or reinherited.
	 *
	 * @param element
	 *            an element to exclude or reinherit
	 * @param exclude
	 *            {@code true} to exclude the element; {@code false} to reinherit it
	 */
	public ExclusionCommand(Element element, boolean exclude) {
		this(TransactionUtil.getEditingDomain(element), element, exclude);
	}

	/**
	 * Initializes me with the {@code element} to exclude or reinherit and whether it
	 * is to be excluded or reinherited, in an explicit editing-domain context.
	 *
	 * @param domain
	 *            the editing domain context
	 * @param element
	 *            an element to exclude or reinherit
	 * @param exclude
	 *            {@code true} to exclude the element; {@code false} to reinherit it
	 */
	public ExclusionCommand(TransactionalEditingDomain domain, Element element, boolean exclude) {
		this(domain, Collections.singletonList(element), exclude);
	}

	/**
	 * Initializes me with the {@code elements} to exclude or reinherit and whether it
	 * is to be excluded or reinherited, in an explicit editing-domain context.
	 *
	 * @param domain
	 *            the editing domain context
	 * @param elements
	 *            a non-empty collection of elements to exclude or reinherit
	 * @param exclude
	 *            {@code true} to exclude the elements; {@code false} to reinherit them
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code elements} collection is empty
	 */
	public ExclusionCommand(TransactionalEditingDomain domain, Collection<? extends Element> elements, boolean exclude) {
		this(domain, ImmutableList.copyOf(elements), exclude);
	}

	private ExclusionCommand(TransactionalEditingDomain domain, List<Element> elements, boolean exclude) {
		super(domain, exclude ? "Exclude Element" : "Re-inherit Element", getWorkspaceFiles(elements));

		if (elements.isEmpty()) {
			throw new IllegalArgumentException("no elements"); //$NON-NLS-1$
		}

		this.elements = elements;
		this.exclude = exclude;
	}

	/**
	 * Obtains the model elements that I {@linkplain #isExclude() exclude or reinherit}.
	 * 
	 * @return my elements
	 * 
	 * @see #isExclude()
	 */
	public List<? extends Element> getElements() {
		return elements;
	}

	/**
	 * Queries whether I exclude my {@link #getElements() elements}.
	 * 
	 * @return {@code true} if I exclude my elements; {@code false} if I reinherit them
	 * 
	 * @see #getElements()
	 */
	public boolean isExclude() {
		return exclude;
	}

	/**
	 * I can execute if the basic conditions of my superclass are met and
	 * my element {@linkplain #isElementInherited() is inherited}.
	 */
	@Override
	public boolean canExecute() {
		return super.canExecute() && elements.stream().allMatch(this::isInherited);
	}

	/**
	 * Obtains the UML-RT façade for an element.
	 * 
	 * @param element
	 *            an element
	 * @return the {@code element}'s UML-RT façade, or {@code null} if it has none
	 */
	protected UMLRTNamedElement getUMLRTNamedElement(Element element) {
		return (element instanceof NamedElement)
				? UMLRTFactory.create((NamedElement) element)
				: null;
	}

	/**
	 * Queries whether an element is inherited, either as is or redefining an
	 * inherited element.
	 * 
	 * @param element
	 *            an element
	 * @return whether the {@code element} is inherited (or a redefinition)
	 */
	protected boolean isInherited(Element element) {
		return UMLRTInheritanceKind.of(element) != UMLRTInheritanceKind.NONE;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		CommandResult result;

		if (!canExecute()) {
			throw new ExecutionException("Not executable"); //$NON-NLS-1$
		} else {
			Predicate<UMLRTNamedElement> action = exclude
					? UMLRTNamedElement::exclude
					: UMLRTNamedElement::reinherit;

			result = CommandResult.newOKCommandResult(getElements().stream()
					.map(this::getUMLRTNamedElement)
					.filter(Objects::nonNull)
					.filter(action) // This 'cheat' actually does our command
					.map(UMLRTNamedElement::toUML)
					.collect(Collectors.toList()));
		}

		return result;
	}

	/**
	 * Obtains an exclusion command for the given {@code elements}.
	 * 
	 * @param domain
	 *            the editing-domain context
	 * @param elements
	 *            a collection of elemenst to exclude or re-inherit
	 * @param exclude
	 *            {@code true} to exclude the {@code elements}; {@code false} to re-inherit them
	 * 
	 * @return the exclusion command
	 */
	public static ICommand getExclusionCommand(TransactionalEditingDomain domain, Collection<? extends Element> elements, boolean exclude) {
		return elements.stream()
				.map(ExcludeRequest.serialRequest(domain, exclude))
				.map(ExclusionCommand::getExclusionCommand)
				.filter(Objects::nonNull)
				.reduce(ICommand::compose)
				.orElse(UnexecutableCommand.INSTANCE);
	}

	/**
	 * Obtains an exclusion command for the given {@code element}.
	 * 
	 * @param domain
	 *            the editing-domain context
	 * @param element
	 *            an element to exclude or re-inherit
	 * @param exclude
	 *            {@code true} to exclude the {@code element}; {@code false} to re-inherit it
	 * 
	 * @return the exclusion command
	 */
	public static ICommand getExclusionCommand(TransactionalEditingDomain domain, Element element, boolean exclude) {
		return getExclusionCommand(new ExcludeRequest(domain, element, exclude));
	}

	private static ICommand getExclusionCommand(ExcludeRequest request) {
		ICommand result = null;

		if (request.getClientContext() == null) {
			try {
				request.setClientContext(TypeContext.getContext(request.getElementToExclude()));
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		}

		IElementType typeToEdit = ElementTypeRegistry.getInstance().getElementType(request.getEditHelperContext());
		if (typeToEdit != null) {
			result = typeToEdit.getEditCommand(request);
		}

		return result;
	}

	/**
	 * Obtains an exclusion command for the given {@code elements}. The editing-domain
	 * context is inferred from the {@code elements}.
	 * 
	 * @param domain
	 *            the editing-domain context
	 * @param elements
	 *            a collection of elemenst to exclude or re-inherit
	 * @param exclude
	 *            {@code true} to exclude the {@code elements}; {@code false} to re-inherit them
	 * 
	 * @return the exclusion command
	 */
	public static ICommand getExclusionCommand(Collection<? extends Element> elements, boolean exclude) {
		return elements.stream()
				.map(ExcludeRequest.serialRequest(exclude))
				.map(ExclusionCommand::getExclusionCommand)
				.filter(Objects::nonNull)
				.reduce(ICommand::compose)
				.orElse(null);
	}

	/**
	 * Obtains an exclusion command for the given {@code element}. The editing-domain
	 * context is inferred from the {@code element}.
	 * 
	 * @param element
	 *            an element to exclude or re-inherit
	 * @param exclude
	 *            {@code true} to exclude the {@code element}; {@code false} to re-inherit it
	 * 
	 * @return the exclusion command
	 */
	public static ICommand getExclusionCommand(Element element, boolean exclude) {
		return getExclusionCommand(new ExcludeRequest(element, exclude));
	}
}
