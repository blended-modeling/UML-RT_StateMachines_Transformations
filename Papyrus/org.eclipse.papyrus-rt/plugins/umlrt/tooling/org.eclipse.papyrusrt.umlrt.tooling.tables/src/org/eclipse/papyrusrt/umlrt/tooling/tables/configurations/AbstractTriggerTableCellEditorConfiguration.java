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

package org.eclipse.papyrusrt.umlrt.tooling.tables.configurations;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CrossAxisWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Partial implementation of a cell editor configuration for the Trigger Table.
 */
public abstract class AbstractTriggerTableCellEditorConfiguration implements ICellAxisConfiguration {
	static final String TABLE_CONFIGURATION_TYPE = "TriggerTable"; //$NON-NLS-1$

	private final String id;
	private final String description;
	private final EStructuralFeature feature;

	protected AbstractTriggerTableCellEditorConfiguration(String id, String description, EStructuralFeature feature) {
		super();

		this.id = id;
		this.description = description;
		this.feature = feature;
	}

	@Override
	public String getConfigurationId() {
		return id;
	}

	@Override
	public String getConfigurationDescription() {
		return description;
	}

	/**
	 * Queries the feature of a trigger that my cells present in the table.
	 * 
	 * @return my feature
	 */
	protected EStructuralFeature getFeature() {
		return feature;
	}

	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		final INattableModelManager nattableManager = configRegistry.getConfigAttribute(
				NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE,
				DisplayMode.NORMAL,
				NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		configureCellEditor(nattableManager, configRegistry, axis, configLabel);
	}

	protected void configureCellEditor(INattableModelManager manager, IConfigRegistry configRegistry, Object axis, String configLabel) {
		final Table table = manager.getTable();

		// Note that the width of the affected column will not be changed
		ICellPainter cellPainter = getCellPainter(table, manager, axis);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, cellPainter, DisplayMode.NORMAL, configLabel);
	}

	protected Image getImage(INattableModelManager manager, ILayerCell cell, IConfigRegistry configRegistry) {
		Image result = null;

		CrossAxisWrapper<EObject, EStructuralFeature> wrapper = UMLTableUtils.getRealEditedObject(cell, manager.getTableAxisElementProvider());
		if (wrapper.getFirstAxis() instanceof Trigger) {
			Trigger trigger = (Trigger) wrapper.getFirstAxis();
			UMLRTTrigger facade = UMLRTTrigger.getInstance(trigger);
			if (facade != null) {
				try {
					result = getImage(facade);
				} catch (ServiceException __) {
					// Pass
				}
			}
		}

		return result;
	}

	/**
	 * Obtains the appropriate image for my {@link #getFeature() feature} of a {@code trigger}.
	 * 
	 * @param trigger
	 *            a trigger
	 * @return the image
	 * @throws ServiceException
	 *             in case the image needs to be obtained from the label-provider
	 *             service but that service is not available
	 */
	protected abstract Image getImage(UMLRTTrigger trigger) throws ServiceException;

	protected ICellPainter getCellPainter(Table table, INattableModelManager manager, Object axis) {
		ICellPainter image = getImageCellPainter(table, manager, axis);

		return new CellPainterDecorator(new TextPainter(), CellEdgeEnum.LEFT, 5, image);
	}

	protected ICellPainter getImageCellPainter(Table table, INattableModelManager manager, Object axis) {
		ImagePainter result = new ImagePainter() {
			@Override
			protected Image getImage(ILayerCell cell, IConfigRegistry configRegistry) {
				Image result = AbstractTriggerTableCellEditorConfiguration.this
						.getImage(manager, cell, configRegistry);

				if (result == null) {
					result = super.getImage(cell, configRegistry);
				}

				return result;
			}
		};

		result.setCalculateByWidth(true);
		return result;
	}

	@Override
	public boolean handles(Table table, Object axisElement) {
		Object object = AxisUtils.getRepresentedElement(axisElement);
		return (table.getContext() instanceof Transition)
				&& TABLE_CONFIGURATION_TYPE.equals(table.getTableConfiguration().getType())
				&& (object == feature);
	}
}
