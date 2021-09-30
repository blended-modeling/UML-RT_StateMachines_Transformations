/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

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
import org.eclipse.papyrus.uml.diagram.common.editparts.EditableLabelForNodeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Specific Edit Part to override the label icon.
 */
public class LabelInListCompartmentEditPart extends EditableLabelForNodeEditPart {

	/** Label that is displayed as the tooltip. */
	private Label toolTipLabel = new Label();

	/**
	 * Constructor.
	 *
	 * @param view
	 *            the view managed by this edit part.
	 */
	public LabelInListCompartmentEditPart(View view) {
		super(view);
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


	/**
	 * {@inheritDoc}
	 */
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
