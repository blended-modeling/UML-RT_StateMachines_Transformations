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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.decorator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * Partial implementation of a decorator for edit-parts in UML-RT diagrams.
 */
public abstract class AbstractUMLRTDecorator<T extends IDecoratorTarget> implements IDecorator, NotificationListener {

	/** Decorations handled by this decorator. */
	private List<Decoration> decorations = new ArrayList<>(3); // Usually there's not many

	/** Adapter for the object to be decorated. */
	private T decoratorTarget;

	private IDisposable listenerRegistration;

	/**
	 * Initializes me with my target.
	 *
	 * @param decoratorType
	 *            the expected decorator type
	 * @param decoratorTarget
	 *            adapter for the object to be decorated
	 */
	public AbstractUMLRTDecorator(Class<? extends T> decoratorType, IDecoratorTarget decoratorTarget) {
		Assert.isLegal(decoratorType.isInstance(decoratorTarget));
		this.decoratorTarget = decoratorType.cast(decoratorTarget);
	}

	/**
	 * Initializes me with my target.
	 *
	 * @param decoratorTarget
	 *            adapter for the object to be decorated
	 */
	protected AbstractUMLRTDecorator(T decoratorTarget) {
		this.decoratorTarget = decoratorTarget;
	}

	/**
	 * Gets the adapter for object to be decorated.
	 *
	 * @return adapter for the object to be decorated
	 */
	protected T getDecoratorTarget() {
		return decoratorTarget;
	}

	@Override
	public void activate() {
		final EditPart editPart = getDecoratorTarget().getAdapter(EditPart.class);
		if (editPart == null) {
			return;
		}

		EObject semantic = EMFHelper.getEObject(editPart);
		if (matchesTarget(semantic)) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(semantic);
			if (domain != null) {
				DiagramEventBroker broker = DiagramEventBroker.getInstance(domain);
				broker.addNotificationListener(semantic, this);

				// Tokenize an undo of this listener registration because when
				// we become deactivated, at that point we may no longer have
				// access to the semantic element to remove the listener from it
				// and we would then end up reacting to notifications and trying
				// to update obsolete edit-parts
				listenerRegistration = () -> broker.removeNotificationListener(semantic, this);
			}
		}

		// initial calculation of the decorators
		refresh();
	}

	/**
	 * Queries whether I should compute decorations on the given semantic element.
	 * 
	 * @param semanticElement
	 *            a semantic element which edit-part is to be decorated
	 * 
	 * @return whether I should compute decorations for it
	 */
	protected boolean matchesTarget(EObject semanticElement) {
		return true;
	}

	@Override
	public void deactivate() {
		// remove listeners
		if (listenerRegistration != null) {
			listenerRegistration.dispose();
			listenerRegistration = null;
		}

		removeDecorations();
	}

	/**
	 * Adds a decoration for me to track.
	 * 
	 * @param decoration
	 *            the decoration to add
	 */
	public void addDecoration(IDecoration decoration) {
		Assert.isTrue(decoration instanceof Decoration);
		decorations.add((Decoration) decoration);
	}

	/**
	 * Removes all extant decorations.
	 */
	protected void removeDecorations() {
		decorations.stream().forEach(dec -> getDecoratorTarget().removeDecoration(dec));
		decorations.clear();
	}

	@Override
	public void notifyChanged(Notification notification) {
		// May need some registration on some sub elements?
		refresh();
	}

	@Override
	public void refresh() {
		removeDecorations();

		EditPart editPart = getDecoratorTarget().getAdapter(EditPart.class);
		EObject semanticElement = getDecoratorTarget().getAdapter(EObject.class);
		if ((editPart instanceof IGraphicalEditPart)
				&& (semanticElement != null) && matchesTarget(semanticElement)) {

			addDecorations((IGraphicalEditPart) editPart, semanticElement);
		}
	}

	/**
	 * Computes and adds the decorations applicable to the edit-part.
	 * 
	 * @param editPart
	 *            the edit-part to decorate
	 * @param semanticElement
	 *            the semantic element
	 * 
	 * @see #addDecoration(IDecoration)
	 */
	protected abstract void addDecorations(IGraphicalEditPart editPart, EObject semanticElement);
}
