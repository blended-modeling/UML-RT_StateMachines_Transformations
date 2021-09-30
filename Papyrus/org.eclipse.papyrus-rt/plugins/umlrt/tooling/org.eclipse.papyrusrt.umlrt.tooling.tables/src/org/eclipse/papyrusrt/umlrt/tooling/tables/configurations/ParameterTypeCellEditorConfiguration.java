/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bugs 476984, 495157, 514536
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.tables.configurations;

import static org.eclipse.papyrusrt.umlrt.tooling.ui.Messages.NoTypeForTypedElement_Label;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.celleditor.AbstractOpenDialogCellEditorButtonAction;
import org.eclipse.papyrus.infra.nattable.celleditor.AbstractPapyrusStyledTextCellEditor;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.painter.CustomizedCellPainter;
import org.eclipse.papyrus.infra.nattable.utils.CrossAxisWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.papyrus.uml.nattable.config.UMLSingleReferenceTextualCellEditorWithButtonConfiguration;
import org.eclipse.papyrus.uml.nattable.manager.cell.editor.UMLReferenceTextWithCompletionCellEditor;
import org.eclipse.papyrus.uml.nattable.messages.Messages;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.papyrusrt.umlrt.tooling.tables.editors.cell.ParameterTypeCellEditor;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.TypeNameResolutionHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * @author Céline JANSSENS
 *
 *         Cell Editor Configuration for the Parameter Type in Papyrus RT. Parameter of RTMessage.
 */
public class ParameterTypeCellEditorConfiguration extends UMLSingleReferenceTextualCellEditorWithButtonConfiguration implements ICellAxisConfiguration {

	protected static final INattableModelManager getModelManager(IConfigRegistry fromRegistry) {
		return fromRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
	}

	@Override
	public String getConfigurationDescription() {
		return getEditorDescription();
	}

	@Override
	public String getConfigurationId() {
		return getEditorConfigId();
	}

	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		final INattableModelManager modelManager = getModelManager(configRegistry);
		final Table table = modelManager.getTable();

		final String displayMode = getDisplayMode(table, axis);

		final ICellPainter painter = getCellPainter(table, axis);
		final ICellEditor editor = getICellEditor(table, axis, modelManager.getTableAxisElementProvider());
		final IDisplayConverter converter = getDisplayConvert(table, axis, new EMFLabelProvider());// TODO : label provider
		final IDataValidator validator = getDataValidator(table, axis);

		if (painter != null) {
			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, painter, DisplayMode.NORMAL, configLabel);
		}

		if (editor != null) {
			configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, displayMode, configLabel);
		}

		if (converter != null) {
			configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, converter, displayMode, configLabel);
		}

		if (validator != null) {
			configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, validator, displayMode, configLabel);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.config.UMLSingleReferenceTextualCellEditorWithButtonConfiguration#getICellEditor(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider)
	 * 
	 */
	@Override
	public ICellEditor getICellEditor(Table table, Object axisElement, ITableAxisElementProvider elementProvider) {
		AbstractPapyrusStyledTextCellEditor result = new UMLReferenceTextWithCompletionCellEditor(table, axisElement, elementProvider) {
			@Override
			protected INameResolutionHelper createNameResolutionHelper() {
				return ParameterTypeCellEditorConfiguration.this.createNameResolutionHelper(layerCell, elementProvider);
			}
		};

		AbstractOpenDialogCellEditorButtonAction openDialog = getCellEditorWithDialogToOpen(axisElement, elementProvider);
		result.setOpenDialogCellEditorButtonAction(openDialog);
		openDialog.setText("..."); //$NON-NLS-1$
		openDialog.setTooltipText(Messages.UMLReferenceCellEditorConfiguration_OpenDialogToChooseTheValue);

		return result;
	}

	protected INameResolutionHelper createNameResolutionHelper(ILayerCell layerCell, ITableAxisElementProvider elementProvider) {
		CrossAxisWrapper<EObject, EStructuralFeature> editedElement = UMLTableUtils.getRealEditedObject(layerCell, elementProvider);
		EObject element = editedElement.getFirstAxis();

		Element scope;
		if (element instanceof Element) {
			scope = (Element) element;
		} else {
			// it could be a stereotype application
			scope = UMLUtil.getBaseElement(element);
		}

		INameResolutionHelper result = null;

		EStructuralFeature feature = editedElement.getSecondAxis();
		EClassifier eType = feature.getEType();

		if (feature == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
			result = new TypeNameResolutionHelper((TypedElement) element);
		} else if (eType instanceof EClass) {
			result = new NameResolutionHelper(scope, (EClass) eType);
		}

		return result;
	}


	/** Cell Editor with dialogToOpen */
	private AbstractOpenDialogCellEditorButtonAction getCellEditorWithDialogToOpen(Object axisElement, ITableAxisElementProvider elementProvider) {
		AbstractOpenDialogCellEditorButtonAction openDialogConfiguration = new AbstractOpenDialogCellEditorButtonAction() {
			/**
			 * @see org.eclipse.papyrus.infra.nattable.celleditor.AbstractOpenDialogCellEditorButtonAction#createDialogCellEditor()
			 */
			@Override
			public AbstractDialogCellEditor createDialogCellEditor() {
				AbstractDialogCellEditor cellEditor = new ParameterTypeCellEditor(axisElement, elementProvider);


				return cellEditor;
			}

		};
		return openDialogConfiguration;
	}

	@Override
	public IDisplayConverter getDisplayConvert(Table table, Object axisElement, ILabelProvider provider) {
		IDisplayConverter delegate = super.getDisplayConvert(table, axisElement, provider);

		// Special conversion of null type <--> '*'
		class TypeConverter implements IDisplayConverter, ISetPapyrusConverter {

			@Override
			public Object displayToCanonicalValue(ILayerCell cell, IConfigRegistry configRegistry, Object displayValue) {
				return delegate.displayToCanonicalValue(cell, configRegistry, displayToNull(displayValue));
			}

			@Override
			public Object displayToCanonicalValue(Object displayValue) {
				return delegate.displayToCanonicalValue(displayToNull(displayValue));
			}

			@Override
			public Object canonicalToDisplayValue(ILayerCell cell, IConfigRegistry configRegistry, Object canonicalValue) {
				return nullToDisplay(delegate.canonicalToDisplayValue(cell, configRegistry, canonicalValue));
			}

			@Override
			public Object canonicalToDisplayValue(Object canonicalValue) {
				return nullToDisplay(delegate.canonicalToDisplayValue(canonicalValue));
			}

			private Object displayToNull(Object displayValue) {
				Object result = displayValue;

				if ((result instanceof String) && ((String) result).trim().equals(NoTypeForTypedElement_Label)) {
					result = ""; //$NON-NLS-1$
				}

				return result;
			}

			private Object nullToDisplay(Object displayValue) {
				Object result = displayValue;

				if ((result == null) || ((result instanceof String) && ((String) result).trim().isEmpty())) {
					result = NoTypeForTypedElement_Label;
				}

				return result;
			}

			@Override
			public void setPapyrusConverter(IPapyrusConverter converter) {
				if (delegate instanceof ISetPapyrusConverter) {
					((ISetPapyrusConverter) delegate).setPapyrusConverter(converter);
				}
			}
		}

		return new TypeConverter();
	}

	@Override
	public ICellPainter getCellPainter(Table table, Object axisElement) {
		ICellPainter base = new CustomizedCellPainter() {
			@Override
			protected String convertDataType(ILayerCell cell, IConfigRegistry configRegistry) {
				String result;

				Object parameterType = cell.getDataValue();
				if (parameterType == null) {
					result = NoTypeForTypedElement_Label;
				} else {
					result = super.convertDataType(cell, configRegistry);
				}

				return result;
			}
		};

		return new PaddingDecorator(base, 3);
	}
}
