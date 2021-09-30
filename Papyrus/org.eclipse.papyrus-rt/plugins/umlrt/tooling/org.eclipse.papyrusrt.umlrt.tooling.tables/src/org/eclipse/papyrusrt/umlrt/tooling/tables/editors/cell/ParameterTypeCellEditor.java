/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bugs 476984, 495626
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.tables.editors.cell;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.tables.configurations.IUMLRTTableConfigurationConstants;
import org.eclipse.papyrusrt.umlrt.tooling.ui.commands.InteractiveSetTypedElementTypeCommand;
import org.eclipse.uml2.uml.Parameter;

/**
 * @author Céline JANSSENS
 *
 *         Cell Editor for the Parameter Type Table in Papyrus RT
 */
public class ParameterTypeCellEditor extends AbstractDialogCellEditor {

	/**
	 * The axis element provider, which offers access to the table manager.
	 */
	private INattableModelManager manager;

	/**
	 * The axis element.
	 */
	private Object axisElement;

	/**
	 * Constructor.
	 *
	 * @param axisElement
	 * @param elementProvider
	 */
	public ParameterTypeCellEditor(Object axisElement, ITableAxisElementProvider elementProvider) {
		super();

		this.manager = (INattableModelManager) elementProvider;
		this.axisElement = axisElement;
	}

	/**
	 * Instead of creating an actual dialog, we create a command which is "opened"
	 * by executing it, which then does interaction with the user.
	 */
	@Override
	public Object createDialogInstance() {
		int columnIndex = this.layerCell.getColumnIndex();
		int rowIndex = this.layerCell.getRowIndex();
		Object row = manager.getRowElement(rowIndex);
		Object column = manager.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);

		Parameter parameter = ((row instanceof Parameter) && (column == axisElement))
				? (Parameter) row
				: (Parameter) column;

		this.dialog = new InteractiveSetTypedElementTypeCommand(
				parameter,
				UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER,
				UMLElementTypes.CLASSIFIER,
				type -> (type == UMLElementTypes.CLASSIFIER)
						? "Type"
						: type.getEClass().getName());
		((InteractiveSetTypedElementTypeCommand) this.dialog).setNewTypeViews(getNewTypeDialogViews());
		return this.dialog;
	}

	private Collection<? extends View> getNewTypeDialogViews() {
		Optional<EStructuralFeatureAxis> axis = AxisUtils.getAxisProviderUsedForColumns(manager).getAxis().stream()
				.filter(EStructuralFeatureAxis.class::isInstance).map(EStructuralFeatureAxis.class::cast)
				.filter(a -> a.getElement() == this.axisElement)
				.findFirst();

		Optional<EAnnotation> annotation = axis.map(a -> a.getEAnnotation(IUMLRTTableConfigurationConstants.CREATION_DIALOG_VIEWS_ANNOTATION_SOURCE));
		return annotation.map(a -> a.getReferences().stream()
				.filter(View.class::isInstance).map(View.class::cast)
				.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	public Object getDialogInstance() {
		return this.dialog;
	}

	@Override
	public void setEditorValue(Object value) {
		// Pass
	}

	@Override
	public int open() {
		if (dialog instanceof ICommand) {
			try {
				EditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
				domain.getCommandStack().execute(ICommandWrapper.wrap(dialog, Command.class));
			} finally {
				// "Close" the "dialog"
				dialog = null;
			}
		}

		// We didn't actually open a dialog, so don't let clients think
		// they can get a dialog value and set it into us
		return Window.CANCEL;
	}

	@Override
	public void close() {
		if (dialog instanceof ICommand) {
			// It was never executed
			((ICommand) dialog).dispose();
			dialog = null;
		}
	}

	@Override
	public boolean isClosed() {
		return !(dialog instanceof ICommand);
	}

	@Override
	public Object getEditorValue() {
		return null;
	}

}
