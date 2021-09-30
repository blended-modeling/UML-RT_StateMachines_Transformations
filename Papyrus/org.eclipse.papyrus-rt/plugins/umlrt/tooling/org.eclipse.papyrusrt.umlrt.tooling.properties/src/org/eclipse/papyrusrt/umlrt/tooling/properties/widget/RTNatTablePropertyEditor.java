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
 *   Young-Soo Roh - bug495146
 *   Christian W. Damus - bug 512955
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.widget;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.AbstractResourceUndoContextPolicy;
import org.eclipse.emf.workspace.IResourceUndoContextPolicy;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.AggregateConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.selection.RowSelectionModel;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultRowSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.style.VerticalAlignmentEnum;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusGridLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.CellTextAlignment;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.CellTextStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.properties.widgets.NattablePropertyEditor;
import org.eclipse.papyrusrt.umlrt.tooling.properties.providers.RTNattableRowDataProvider;
import org.eclipse.papyrusrt.umlrt.tooling.tables.internal.handlers.RTTransactionalEditCellCommandHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * Customization of the standard Papyrus table property editor for UML-RT.
 */
public class RTNatTablePropertyEditor extends NattablePropertyEditor {

	private static final Map<CellTextAlignment, HorizontalAlignmentEnum> HALIGN;
	private static final Map<CellTextAlignment, VerticalAlignmentEnum> VALIGN;

	protected IRowDataProvider<Object> rowDataProvider;

	static {
		HALIGN = new EnumMap<>(CellTextAlignment.class);
		HALIGN.put(CellTextAlignment.TOP_LEFT, HorizontalAlignmentEnum.LEFT);
		HALIGN.put(CellTextAlignment.MIDDLE_LEFT, HorizontalAlignmentEnum.LEFT);
		HALIGN.put(CellTextAlignment.BOTTOM_LEFT, HorizontalAlignmentEnum.LEFT);
		HALIGN.put(CellTextAlignment.TOP_CENTER, HorizontalAlignmentEnum.CENTER);
		HALIGN.put(CellTextAlignment.MIDDLE_CENTER, HorizontalAlignmentEnum.CENTER);
		HALIGN.put(CellTextAlignment.BOTTOM_CENTER, HorizontalAlignmentEnum.CENTER);
		HALIGN.put(CellTextAlignment.TOP_RIGHT, HorizontalAlignmentEnum.RIGHT);
		HALIGN.put(CellTextAlignment.MIDDLE_RIGHT, HorizontalAlignmentEnum.RIGHT);
		HALIGN.put(CellTextAlignment.BOTTOM_RIGHT, HorizontalAlignmentEnum.RIGHT);

		VALIGN = new EnumMap<>(CellTextAlignment.class);
		VALIGN.put(CellTextAlignment.TOP_LEFT, VerticalAlignmentEnum.TOP);
		VALIGN.put(CellTextAlignment.TOP_CENTER, VerticalAlignmentEnum.TOP);
		VALIGN.put(CellTextAlignment.TOP_RIGHT, VerticalAlignmentEnum.TOP);
		VALIGN.put(CellTextAlignment.MIDDLE_LEFT, VerticalAlignmentEnum.MIDDLE);
		VALIGN.put(CellTextAlignment.MIDDLE_CENTER, VerticalAlignmentEnum.MIDDLE);
		VALIGN.put(CellTextAlignment.MIDDLE_RIGHT, VerticalAlignmentEnum.MIDDLE);
		VALIGN.put(CellTextAlignment.BOTTOM_LEFT, VerticalAlignmentEnum.BOTTOM);
		VALIGN.put(CellTextAlignment.BOTTOM_CENTER, VerticalAlignmentEnum.BOTTOM);
		VALIGN.put(CellTextAlignment.BOTTOM_RIGHT, VerticalAlignmentEnum.BOTTOM);
	}

	/**
	 * Initializes me with my {@code parent} component and {@code style} bits.
	 *
	 * @param parent
	 *            my parent composite
	 * @param style
	 *            my styling bits
	 */
	public RTNatTablePropertyEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Overridden to ensure that the editing domain doesn't consider the referenced UML
	 * model resource as being affected when the table context is set to an element.
	 */
	@Override
	protected ServicesRegistry createServiceRegistry(EObject sourceElement) throws Exception {
		ServicesRegistry result = super.createServiceRegistry(sourceElement);

		TransactionalEditingDomain domain = result.getService(TransactionalEditingDomain.class);
		CommandStack stack = domain.getCommandStack();

		if (stack instanceof IWorkspaceCommandStack) {
			// In Papyrus, there are several different implementations of the
			// IWorkspaceCommandStack protocol
			MethodHandle policySetter = MethodHandles.publicLookup().findVirtual(
					stack.getClass(),
					"setResourceUndoContextPolicy", //$NON-NLS-1$
					MethodType.methodType(void.class, IResourceUndoContextPolicy.class));

			IResourceUndoContextPolicy policy = new AbstractResourceUndoContextPolicy() {
				@Override
				protected boolean pessimisticCrossReferences() {
					return false;
				}
			};

			try {
				policySetter.invoke(stack, policy);
			} catch (Error e) {
				throw e;
			} catch (Exception e) {
				throw e;
			} catch (Throwable t) {
				throw new UndeclaredThrowableException(t);
			}
		}

		return result;
	}

	/**
	 * Overridden to replace the edit-cell command handler in the table's grid
	 * layer with an RT-specific implementation.
	 */
	@Override
	protected NatTable createNatTableWidget(INattableModelManager manager, Composite parent, int style, Collection<?> rows) {
		NatTable result = super.createNatTableWidget(manager, parent, style, rows);

		GridLayer grid = TypeUtils.as((result == null) ? null : result.getLayer(), GridLayer.class);
		if (grid != null) {
			replaceEditCellCommandHandler(manager, grid);
		}

		// enable whole row selection for properties table
		if (manager instanceof NattableModelManager) {
			SelectionLayer selectionLayer = manager.getBodyLayerStack().getSelectionLayer();
			rowDataProvider = new RTNattableRowDataProvider((NattableModelManager) manager);
			IRowIdAccessor<Object> rowIdAccessor = new IRowIdAccessor<Object>() {

				@Override
				public Serializable getRowId(Object rowObject) {
					return rowObject.hashCode();
				}
			};
			selectionLayer.setSelectionModel(new RowSelectionModel<>(selectionLayer, rowDataProvider, rowIdAccessor, false));
			selectionLayer.addConfiguration(new DefaultRowSelectionLayerConfiguration());
		}

		configureCellStyle(result, manager);

		return result;
	}

	/**
	 * Configures the styling of body cells in the given {@code table}.
	 * 
	 * @param table
	 *            the table to configure
	 * @param manager
	 *            the table model manager
	 */
	protected void configureCellStyle(NatTable table, INattableModelManager manager) {
		// Default cell style for body cells
		AbstractAxisProvider columnProvider = manager.getTable().getCurrentColumnAxisProvider();
		CellTextStyle textStyle = (CellTextStyle) columnProvider.getStyle(NattablestylePackage.Literals.CELL_TEXT_STYLE);
		configureCellStyle(table, manager, -1, textStyle, GridRegion.BODY);

		// Cell style overrides for columns that have them
		DataLayer dataLayer = manager.getBodyLayerStack().getBodyDataLayer();
		ColumnOverrideLabelAccumulator acc = null;
		List<IAxis> columns = columnProvider.getAxis();
		for (int i = 0; i < columns.size(); i++) {
			textStyle = (CellTextStyle) columns.get(i).getStyle(NattablestylePackage.Literals.CELL_TEXT_STYLE);
			if (textStyle != null) {
				if (acc == null) {
					// We'll have to add one and repeat every time that Papyrus replaces
					// it on refresh of the table's column header layer
					acc = new ColumnOverrideLabelAccumulator(dataLayer);
					ensureLabels((AbstractNattableWidgetManager) manager, acc);
				}

				String columnStyleLabel = "CELL_STYLE_" + i;
				acc.registerColumnOverrides(i, columnStyleLabel);
				configureCellStyle(table, manager, i, textStyle, columnStyleLabel);
			}
		}
	}

	protected void configureCellStyle(NatTable table, INattableModelManager manager, int column, CellTextStyle textStyle, String configLabel) {
		// configure cell alignment and possibly other styles
		HorizontalAlignmentEnum halign = HorizontalAlignmentEnum.LEFT;
		VerticalAlignmentEnum valign = VerticalAlignmentEnum.TOP;
		IStyle cellStyle = new org.eclipse.nebula.widgets.nattable.style.Style();
		if (textStyle != null) {
			CellTextAlignment align = textStyle.getAlignment();
			halign = HALIGN.get(align);
			valign = VALIGN.get(align);
		}
		cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, halign);
		cellStyle.setAttributeValue(CellStyleAttributes.VERTICAL_ALIGNMENT, valign);
		table.getConfigRegistry().registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL, configLabel);

		// padding
		int hpad = (halign != HorizontalAlignmentEnum.CENTER) ? 3 : 0;
		int vpad = (valign != VerticalAlignmentEnum.MIDDLE) ? 3 : 0;
		if ((hpad != 0) || (vpad != 0)) {
			ICellPainter painter;

			if (column < 0) {
				painter = table.getConfigRegistry().getConfigAttribute(CellConfigAttributes.CELL_PAINTER, DisplayMode.NORMAL, GridRegion.BODY);
			} else {
				// Our config label accumulator doesn't consider the row
				LabelStack labels = manager.getBodyLayerStack().getBodyDataLayer().getConfigLabelsByPosition(column, 0);
				painter = table.getConfigRegistry().getConfigAttribute(CellConfigAttributes.CELL_PAINTER, DisplayMode.NORMAL, labels.getLabels());
			}

			PaddingDecorator padding = new PaddingDecorator(painter, vpad, hpad, vpad, hpad);
			table.getConfigRegistry().registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, padding, DisplayMode.NORMAL, configLabel);
		}
	}

	private void ensureLabels(AbstractNattableWidgetManager manager, IConfigLabelAccumulator accumulator) {
		DataLayer dataLayer = manager.getBodyLayerStack().getBodyDataLayer();
		IConfigLabelAccumulator existing = dataLayer.getConfigLabelAccumulator();

		Runnable injectLabels = new Runnable() {
			private IConfigLabelAccumulator lastKnown = existing;

			@Override
			public void run() {
				IConfigLabelAccumulator current = dataLayer.getConfigLabelAccumulator();
				if (current != lastKnown) {
					// Papyrus has replaced the accumulator
					if (current == null) {
						dataLayer.setConfigLabelAccumulator(accumulator);
					} else {
						AggregateConfigLabelAccumulator aggLabels = new AggregateConfigLabelAccumulator();
						aggLabels.add(accumulator); // Our labels first
						aggLabels.add(current);
						dataLayer.setConfigLabelAccumulator(aggLabels);
					}

					lastKnown = dataLayer.getConfigLabelAccumulator();
				}
			}
		};

		// Initialize
		injectLabels.run();

		// Repeat on every refresh of column headers
		manager.getColumnHeaderLayerStack().addLayerListener(event -> injectLabels.run());
	}

	private void replaceEditCellCommandHandler(INattableModelManager manager, GridLayer grid) {
		// Only do this for the layer that already installs a transactional handler
		if (grid instanceof PapyrusGridLayer) {
			Map<Class<? extends ILayerCommand>, ILayerCommandHandler<? extends ILayerCommand>> commandHandlers = getField(grid, "commandHandlers");
			if (commandHandlers != null) {
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(manager.getTable().getContext());
				RTTransactionalEditCellCommandHandler handler = new RTTransactionalEditCellCommandHandler(domain);
				commandHandlers.put(handler.getCommandClass(), handler);
			}
		}
	}

	/**
	 * Obtains the value of the named field of an {@code object}.
	 * 
	 * @param object
	 *            an object
	 * @param fieldName
	 *            the name of a field
	 * 
	 * @return the field's value, or {@code null} if the fields does not exist
	 *         or is not accessible (or simply has no value)
	 */
	@SuppressWarnings("unchecked")
	static <T> T getField(Object object, String fieldName) {
		T result = null;

		for (Class<?> search = object.getClass(); search != null; search = search.getSuperclass()) {
			try {
				Field field = search.getDeclaredField(fieldName);
				field.setAccessible(true);
				result = (T) field.get(object);
				break;
			} catch (Exception e) {
				// Try superclass
			}
		}

		return result;
	}
}
