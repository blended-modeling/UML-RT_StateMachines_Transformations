/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.EditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.uml2.uml.Element;

/**
 * An edit request for exclusion or re-inheritance of dependent elements that must
 * be excluded/re-inherited together with the originally selected element in order
 * to maintain model integrity. For example, exclusion of a port of a capsule requires
 * exclusion of the inherited/redefined connectors that connect it in the context
 * of the inheriting capsule and, conversely, re-inheritance of a connector requires
 * re-inheritance of any excluded ports that it connects.
 */
public final class ExcludeDependentsRequest extends AbstractEditCommandRequest implements IExcludeElementRequest {

	private final Set<Element> dependentElementsToExclude = new LinkedHashSet<>();
	private final Set<Element> immutableViewOfDependents = Collections.unmodifiableSet(dependentElementsToExclude);

	private ExcludeRequest initialExcludeRequest;
	private Element elementToExclude;

	/**
	 * Initializes me with the initial exclude request for which we are finding dependents.
	 *
	 * @param excludeRequest
	 *            the initial exclude request
	 * 
	 * @throws NullPointerException
	 *             if the request is {@code null}
	 */
	public ExcludeDependentsRequest(ExcludeRequest excludeRequest) {
		super(requireNonNull(excludeRequest, "excludeRequest").getEditingDomain()); //$NON-NLS-1$

		this.initialExcludeRequest = excludeRequest;

		setElementToExclude(excludeRequest.getElementToExclude());
	}

	@Override
	public final Element getElementToExclude() {
		return elementToExclude;
	}

	@Override
	public final void setElementToExclude(Element element) {
		this.elementToExclude = requireNonNull(element, "element"); //$NON-NLS-1$

		dependentElementsToExclude.add(element);
	}

	@Override
	public boolean isExclude() {
		return initialExcludeRequest.isExclude();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getElementsToEdit() {
		return Collections.singletonList(getElementToExclude());
	}

	@Override
	public IClientContext getClientContext() {
		IClientContext result = super.getClientContext();

		if (result == null) {
			result = initialExcludeRequest.getClientContext();
		}

		return result;
	}

	/**
	 * My edit-helper context is my {@link #getElementToExclude() element}, itself, because it best
	 * knows its dependents.
	 * 
	 * @return my element or an edit-helper context encapsulating it with my client-context
	 * 
	 * @see #getElementToExclude()
	 */
	@Override
	public Object getEditHelperContext() {
		Object result;
		IClientContext context = getClientContext();

		Element excludee = getElementToExclude();
		if (context == null) {
			result = excludee;
		} else {
			result = new EditHelperContext(excludee, context);
		}

		return result;
	}

	/**
	 * Obtains an immutable view of the set of dependent elements to exclude/re-inherit.
	 * 
	 * @return the immutable set of dependent elements
	 */
	public final Set<Element> getDependentElementsToDestroy() {
		return immutableViewOfDependents;
	}

	/**
	 * Obtains a command that excludes/reinherits the specified {@code dependent} of
	 * the {@linkplain #getElementToExclude() element to be excluded/reinherited}, if it
	 * is not already being excluded/reinherited by the processing of this
	 * {@code ExcludeDependentsRequest}. This command can then be composed with
	 * others by the edit helper processing the request.
	 * 
	 * @param dependent
	 *            an object dependent on the element being excluded/reinherited,
	 *            which must also be excluded/reinherited
	 * 
	 * @return a command to exclude/reinherit the {@code dependent}, or
	 *         {@code null} if the element is already being excluded/reinherited
	 * 
	 * @throws IllegalArgumentException
	 *             on an attempt to exclude/reinherit the
	 *             {@linkplain #getElementToExclude() element to exclude/reinherit} (as a
	 *             dependent of itself)
	 * @throws NullPointerException
	 *             on attempt to exclude/reinherit a {@code null}
	 *             dependent
	 * 
	 * @see #getExcludeDependentsCommand(Collection)
	 */
	public ICommand getExcludeDependentCommand(Element dependent) {
		ICommand result = null;

		if (addDependentElementToExclude(dependent)) {
			// Record the element that we are excluding, for later restoration
			Element elementBeingExcluded = getElementToExclude();

			try {
				ExcludeRequest exclude = new ExcludeRequest(getEditingDomain(), dependent, isExclude());
				exclude.setClientContext(getClientContext());

				// Propagate my parameters
				exclude.addParameters(getParameters());

				// Propagate the dependents information to detect cycles
				exclude.setParameter(ExcludeRequest.EXCLUDE_DEPENDENTS_REQUEST_PARAMETER, this);
				setElementToExclude(dependent);

				Object eHelperContext = exclude.getEditHelperContext();
				IElementType context = ElementTypeRegistry.getInstance().getElementType(eHelperContext);

				if (context != null) {
					result = context.getEditCommand(exclude);
				}
			} finally {
				// Restore the element that we are excluding
				setElementToExclude(elementBeingExcluded);
			}
		}

		return result;
	}

	/**
	 * Obtains a command that excludes/reinherits the specified {@code dependents} of
	 * the {@linkplain #getElementToExclude() element to be excluded/reinherited}, if they
	 * are not already being excluded/reinherited by the processing of this
	 * request. This command can then be composed with
	 * others by the edit helper processing the request.
	 * 
	 * @param dependents
	 *            dependents of the element being excluded/reinherited
	 * 
	 * @return a command to exclude/reinherit all of the {@code dependents},
	 *         or {@code null} if they are all already being excluded/reinherited
	 * 
	 * @see #getExcludeDependentCommand(Element)
	 */
	public ICommand getExcludeDependentsCommand(Collection<? extends Element> dependents) {
		return dependents.stream()
				.map(this::getExcludeDependentCommand)
				.filter(Objects::nonNull)
				.reduce(CompositeCommand::compose)
				.orElse(null);
	}

	/**
	 * <p>
	 * Indicates that the command that fulfils this request will also exclude
	 * or re-inherit the specified dependent of the
	 * {@link #getElementToExclude() element to be excluded/re-inherited}.
	 * </p>
	 * <p>
	 * Advice that provides a command to exclude/re-inherit a dependent element
	 * <em>must</em> indicate that fact by calling this method (only after
	 * checking whether it isn't already
	 * {@link #isElementToBeExcluded(Element) being excluded/re-inherited}, anyway).
	 * </p>
	 * 
	 * @param dependent
	 *            another object to exclude/re-inherit, which is dependent on the
	 *            element for which we are requesting exclusion/re-inheritance
	 * 
	 * @return {@code true} if the <code>dependent</code> was not already
	 *         in the set of elements being excluded/re-inherited; {@code false}, otherwise
	 * 
	 * @throws IllegalArgumentException
	 *             on an attempt to add the
	 *             {@link #getElementToExclude() element to be excluded/re-inherited} as a dependent
	 *             of itself
	 * @throws NullPointerException
	 *             on attempt to add a {@code null} element
	 * 
	 * @see #isElementToBeExcluded(Element)
	 * @see #getElementToExclude()
	 * @see #getExcludeDependentCommand(Element)
	 */
	protected boolean addDependentElementToExclude(Element dependent) {
		if (requireNonNull(dependent, "dependent") == getElementToExclude()) {
			throw new IllegalArgumentException("dependent is the element being excluded"); //$NON-NLS-1$
		}

		return !isElementToBeExcluded(dependent) && dependentElementsToExclude.add(dependent);
	}

	/**
	 * Queries whether the specified element will be excluded/re-inherited as a result of
	 * the fulfillment of this request..
	 * 
	 * @param element
	 *            an element
	 * 
	 * @return {@code true} if the command that fulfils this request
	 *         would exclude/re-inherit the {@code element}; {@code false} if a new
	 *         command would have to be composed with it to exclude/re-inherit the {@code element}
	 */
	protected boolean isElementToBeExcluded(Element element) {
		boolean result = false;

		EObject excludee = getElementToExclude();

		for (EObject toCheck = element; !result && (toCheck != null); toCheck = toCheck.eContainer()) {
			result = (toCheck == excludee) || dependentElementsToExclude.contains(toCheck);
		}

		return result;
	}
}
