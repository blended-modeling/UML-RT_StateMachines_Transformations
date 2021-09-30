/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 513065
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedToolTipLabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.services.labelprovider.service.ExtensibleLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;

/**
 * Specific RT implementation to handle the label displayed thanks to label provider.
 */
public class RTTransitionNameEditPart extends TransitionNameEditPart implements IStateMachineInheritableEditPart {

	/**
	 * Listener filter ID for the UML-RT root definition of our semantic element.
	 */
	private static final String UMLRT_ROOT_FILTER = "umlrtRoot"; //$NON-NLS-1$

	/** Label that is displayed as the tooltip. */
	private Label toolTipLabel = new Label();

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public RTTransitionNameEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Image getLabelIcon() {
		EObject parserElement = getParserElement();
		if (parserElement == null || getViewer() == null && parserElement.eResource() != null) {
			return null;
		}
		List<View> views = DiagramEditPartsUtil.findViews(parserElement, getViewer());
		for (View view : views) {
			if (AppearanceHelper.showElementIcon(view)) {
				try {
					// specific implementation relying on the label provider service from papyrus
					return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, parserElement).getLabelProvider().getImage(parserElement);
				} catch (ServiceException e) {
					Activator.log.error(e);
				}
			}
		}
		return null;
	}

	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();

		EObject semantic = resolveSemanticElement();
		if (semantic instanceof Element) {
			Element element = (Element) semantic;

			// Also listen for changes in the ordering of triggers in the root
			// definition to update our label
			Element root = UMLRTExtensionUtil.getRootDefinition(element);
			if ((root != null) && (root != element)) {
				addListenerFilter(UMLRT_ROOT_FILTER, this, root);
			}
		}
	}

	@Override
	protected void removeSemanticListeners() {
		// In case we added a UML-RT root definition listener
		removeListenerFilter(UMLRT_ROOT_FILTER);

		super.removeSemanticListeners();
	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);

		// manage the parser specificities
		if (getParser() != null && getParser().isAffectingEvent(event, getParserOptions().intValue())) {
			refreshLabel();
		}
		if (getParser() instanceof ISemanticParser) {
			ISemanticParser modelParser = (ISemanticParser) getParser();
			if (modelParser.areSemanticElementsAffected(null, event)) {
				removeSemanticListeners();
				if (resolveSemanticElement() != null) {
					addSemanticListeners();
				}
				refreshLabel();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void refreshLabel() {
		View view = (View) getModel();
		if (view.isVisible() && view.getElement() != null && view.getElement().eResource() != null) {
			super.refreshLabel();
			// refreshes the label tool tip
			getFigure().setToolTip(getLabelToolTip());
		}
	}

	/**
	 * Returns the figure that corresponds to the tooltip
	 * 
	 * @return the tooltip figure or <code>null</code> if no tooltip is supposed to be displayed
	 */
	protected IFigure getLabelToolTip() {
		String text = getToolTipText();
		if (text != null && text.length() > 0) {
			toolTipLabel.setText(text);
			return toolTipLabel;
		}
		return null;
	}

	/**
	 * This method can be overridden in the subclass to return
	 * text for the tooltip.
	 * <p>
	 * Basically, it uses the label provider to compute the tooltip
	 * </p>
	 * 
	 * @return String the tooltip
	 */
	protected String getToolTipText() {
		EObject parserElement = getParserElement();
		if (parserElement != null && parserElement.eResource() != null) {
			try {
				ILabelProvider labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, parserElement).getLabelProvider();
				if (labelProvider instanceof ExtensibleLabelProvider) {
					Method getProvider = ExtensibleLabelProvider.class.getDeclaredMethod("getProvider", Object.class);
					getProvider.setAccessible(true);
					Object result = getProvider.invoke(labelProvider, parserElement);
					if (result instanceof ICustomizedToolTipLabelProvider) {
						return ((ICustomizedToolTipLabelProvider) result).getToolTipText(parserElement, null);
					}
				}
			} catch (ServiceException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

}
