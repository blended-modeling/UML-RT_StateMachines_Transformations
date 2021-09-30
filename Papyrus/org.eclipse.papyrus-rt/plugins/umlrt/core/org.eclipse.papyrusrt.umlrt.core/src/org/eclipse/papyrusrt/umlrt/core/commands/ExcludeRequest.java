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

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.EditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.uml2.uml.Element;

/**
 * An edit request for exclusion (analogous to destroy except that it merely
 * excludes an inherited element) or re-inheritance, which is a quasi-opposite
 * of exclusion.
 */
public final class ExcludeRequest extends AbstractEditCommandRequest implements IExcludeElementRequest {

	/**
	 * Key for the request parameter bearing the request to exclude/re-inherit dependents.
	 * The value of the parameter is of type {@link ExcludeDependentsParameter}.
	 */
	public static final String EXCLUDE_DEPENDENTS_REQUEST_PARAMETER = "ExcludeRequest.excludeDependentsRequest"; //$NON-NLS-1$

	/**
	 * Key for the request parameter indicating the initial element selected for exclusion/reinheritance.
	 * The value of the parameter is of type {@link Element}.
	 */
	public static final String INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER = "ExcludeRequest.initialElementToExclude"; //$NON-NLS-1$

	private final boolean exclude;

	private Element element;

	/**
	 * Initializes me with my context editing domain and the {@code element} to be excluded or reinherited.
	 *
	 * @param editingDomain
	 *            the context editing domain
	 * @param element
	 *            the element to be excluded or reinherited
	 * @param exclude
	 *            {@code true} to exclude the {@code element}, {@code false} to reinherit it
	 * 
	 * @throws NullPointerException
	 *             if either the {@code element} or its {@link Element#getOwner() owner} is {@code null},
	 *             because it doesn't make sense to exclude an unowned element because it cannot in that
	 *             case be inherited (besides that only packages can be unowned)
	 */
	public ExcludeRequest(TransactionalEditingDomain editingDomain, Element element, boolean exclude) {
		super(requireNonNull(editingDomain, "editingDomain")); //$NON-NLS-1$

		this.exclude = exclude;
		setElementToExclude(element);
	}

	/**
	 * Initializes me with the {@code element} to be excluded or reinherited. The editing domain context is
	 * inferred from the {@code element}.
	 *
	 * @param element
	 *            the element to be excluded
	 * @param exclude
	 *            {@code true} to exclude the {@code element}, {@code false} to reinherit it
	 * 
	 * @throws NullPointerException
	 *             if either the {@code element} or its {@link Element#getOwner() owner} is {@code null},
	 *             because it doesn't make sense to exclude an unowned element because it cannot in that
	 *             case be inherited (besides that only packages can be unowned)
	 */
	public ExcludeRequest(Element element, boolean exclude) {
		this(TransactionUtil.getEditingDomain(element), element, exclude);
	}

	@Override
	public final Element getElementToExclude() {
		return element;
	}

	@Override
	public final void setElementToExclude(Element element) {
		this.element = requireNonNull(element, "element"); //$NON-NLS-1$

		requireNonNull(element.getOwner(), "element.owner"); //$NON-NLS-1$
	}

	@Override
	public boolean isExclude() {
		return exclude;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getElementsToEdit() {
		return Collections.singletonList(getElementToExclude());
	}

	/**
	 * My edit-helper context is my {@link #getElementToExclude() element}'s owner.
	 * Roots of the model tree cannot be excluded because obviously they could not
	 * be inherited, having no owner.
	 * 
	 * @return my element's owner or an edit-helper context encapsulating it with my client-context
	 * 
	 * @throws NullPointerException
	 *             if now the {@linkplain #getElementToExclude() element} has no owner
	 * 
	 * @see #getElementToExclude()
	 * @see Element#getOwner()
	 */
	@Override
	public Object getEditHelperContext() {
		Object result;
		IClientContext context = getClientContext();

		Element owner = requireNonNull(getElementToExclude().getOwner(), "element.owner"); //$NON-NLS-1$

		if (context == null) {
			result = owner;
		} else {
			result = new EditHelperContext(owner, context);
		}

		return result;
	}

	/**
	 * Creates the default edit-command performing this request.
	 * 
	 * @return the default exclusion/reinheritance edit-command
	 */
	public ICommand getDefaultCommand() {
		return new ExclusionCommand(getEditingDomain(), getElementToExclude(), isExclude());
	}

	/**
	 * Obtains a supplier of exclusion requests for serial processing of
	 * multiple elements that optimally reuses request instances.
	 * 
	 * @param domain
	 *            the editing-domain context to use explicitly in requests
	 * @param exclude
	 *            {@code true} if elements are to be excluded or
	 *            {@code false} if they are to be re-inherited
	 * @return the serial exclusion-request function
	 */
	public static Function<Element, ExcludeRequest> serialRequest(TransactionalEditingDomain domain, boolean exclude) {
		return serialRequest(element -> new ExcludeRequest(domain, element, exclude));
	}

	/**
	 * Obtains a supplier of exclusion requests for serial processing of
	 * multiple elements that optimally reuses request instances.
	 * 
	 * @param exclude
	 *            {@code true} if elements are to be excluded or
	 *            {@code false} if they are to be re-inherited
	 * @return the serial exclusion-request function
	 */
	public static Function<Element, ExcludeRequest> serialRequest(boolean exclude) {
		return serialRequest(element -> new ExcludeRequest(element, exclude));
	}

	private static Function<Element, ExcludeRequest> serialRequest(Function<Element, ExcludeRequest> initializer) {
		return new Function<Element, ExcludeRequest>() {
			private ExcludeRequest request;

			@Override
			public ExcludeRequest apply(Element element) {
				if (request == null) {
					request = initializer.apply(element);
				} else {
					// This element must be the new initial, so forget any previous
					request.setParameter(INITIAL_ELEMENT_TO_EXCLUDE_PARAMETER, null);
					request.setElementToExclude(element);
				}

				return request;
			}
		};
	}
}
