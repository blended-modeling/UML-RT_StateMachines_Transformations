/*****************************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, Zeligsoft (2009),  and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 476984, 495823, 510315, 507282, 512955
 *  Young-Soo Roh - Refactored to common multi-reference table property editor. 
 *                  bugs 494291
 *  
 ****************************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.properties.widget;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.RowSelectionProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.ICompositeAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrusrt.umlrt.tooling.properties.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.properties.editors.MultiReferenceControlEditor;
import org.eclipse.papyrusrt.umlrt.tooling.properties.providers.IRTTableSelectionProvider;
import org.eclipse.papyrusrt.umlrt.tooling.properties.providers.RTNattableSelectionService;
import org.eclipse.papyrusrt.umlrt.tooling.properties.providers.RTTableSelectionProvider;
import org.eclipse.papyrusrt.umlrt.tooling.ui.util.InheritanceUIComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Element;

/**
 * A PropertyEditor for editing multiple references in a {@link NatTable}.
 */
public class RTNatTableMultiReferencePropertyEditor extends RTNatTablePropertyEditor {

	/**
	 * Minimum Height of a row
	 */
	private static final int ROW_HEIGHT = 40;

	/**
	 * RT encapsulated selection provider.
	 */
	private IRTTableSelectionProvider selectionProvider;

	/**
	 * JFace-compatible selection provider for the up/down controls.
	 */
	private RowSelectionProvider<?> rowSelectionProvider;

	/**
	 * Control Button Editor
	 */
	private MultiReferenceControlEditor editor;

	/**
	 * Style of the Table
	 */
	protected int style;

	protected Class<? extends IControlManager> controlManagerClass;

	private IChangeListener modelChangeListener;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style of the composite.
	 */
	public RTNatTableMultiReferencePropertyEditor(final Composite parent, final int style) {
		super(parent, style);

		this.style = style;
	}

	protected void dispose() {
		disposeControlEditor();

		RTNattableSelectionService.getInstance().removeSelectionProvider(selectionProvider);

		if (rowSelectionProvider != null) {
			if (nattableManager.getBodyLayerStack() != null) {
				SelectionLayer selectionLayer = nattableManager.getBodyLayerStack().getSelectionLayer();
				selectionLayer.removeLayerListener(rowSelectionProvider);
			}

			rowSelectionProvider = null;
		}

		if (modelChangeListener != null) {
			IObservableList<?> list = getInputObservableList();
			if (list != null) {
				list.removeChangeListener(modelChangeListener);
			}
			modelChangeListener = null;
		}
	}

	@Override
	protected void doBinding() {
		super.doBinding();

		IObservableList<?> input = getInputObservableList();
		if (input != null) {
			input.addChangeListener(getModelChangeListener());
		}
	}

	@Override
	protected void createPreviousWidgets(EObject sourceElement, EStructuralFeature feature) {
		super.createPreviousWidgets(sourceElement, feature);

		// Create the Button Widget
		disposeControlEditor();
		editor = createControlEditor(sourceElement, feature);
		setEditor(editor);

	}

	@Override
	protected NatTable createNatTableWidget(INattableModelManager manager, Composite parent, int style, Collection<?> rows) {
		IAxisManager rowMgr = manager.getRowAxisManager();
		if ((rowMgr instanceof ICompositeAxisManager) && (manager.getTable().getContext() instanceof Element)) {
			// Sort by inheritance
			Comparator<Object> byInheritance = new InheritanceUIComparator();
			((ICompositeAxisManager) rowMgr).setAxisComparator(byInheritance);
		}

		NatTable result = super.createNatTableWidget(manager, parent, style, rows);

		// Hook the selection listener to the table for updating of buttons etc.
		setSelectionProvider();

		self.addDisposeListener(__ -> dispose());

		// Now that we have the NatTable, we can complete the controls
		editor.createContents();

		return result;
	}

	protected MultiReferenceControlEditor createControlEditor(EObject sourceElement, EStructuralFeature feature) {
		MultiReferenceControlEditor editor = new MultiReferenceControlEditor(self, style, sourceElement, feature, () -> nattableManager);

		editor.setModelObservable(getInputObservableList());
		editor.setFactory(input.getValueFactory(propertyPath));
		editor.setDirectCreation(input.getDirectCreation(propertyPath));
		editor.setReadOnly(!input.isEditable(propertyPath));

		if (getControlManagerClass() != null) {
			hookControlManager(editor);
		}

		if (getProperty() != null) {
			editor.setDialogSettingsKey(getProperty());
		}

		return editor;
	}

	@Override
	public void setProperty(String path) {
		super.setProperty(path);

		if (editor != null) {
			editor.setDialogSettingsKey(path);
		}
	}

	/**
	 * Configure layout of the different Composite
	 * 
	 * @param sourceElement
	 *            the
	 */
	@Override
	protected void configureLayout(EObject sourceElement) {
		// parent composite
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalIndent = 0;
		setLayoutData(data);

		// table group layout
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		self.setLayout(layout);

		// button editor layout data
		GridData editorData = new GridData(SWT.RIGHT, SWT.TOP, false, false);
		editorData.horizontalIndent = 0;
		editor.setLayoutData(editorData);

		// Table Composite
		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableData.minimumHeight = getInputObservableList().size() * ROW_HEIGHT;
		tableData.horizontalIndent = 0;
		natTableWidget.setLayoutData(tableData);

		// Layout the different Widget
		self.layout();
		editor.layout();
		super.configureLayout(sourceElement);
	}


	/**
	 * Set the Selection Provider
	 */
	protected void setSelectionProvider() {
		SelectionLayer selectionLayer = nattableManager.getBodyLayerStack().getSelectionLayer();

		selectionProvider = new RTTableSelectionProvider(nattableManager, selectionLayer);
		RTNattableSelectionService.getInstance().addSelectionProvider(selectionProvider);

		rowSelectionProvider = new RowSelectionProvider<>(selectionLayer, rowDataProvider, false);
		editor.setSelectionProvider(rowSelectionProvider);
	}

	/**
	 * Dispose the existing button
	 */
	protected void disposeControlEditor() {
		if (null != editor) {
			editor.dispose();
			editor = null;
		}
	}

	/**
	 * Obtains my control manager class.
	 * 
	 * @return my controlManager class
	 */
	public Class<? extends IControlManager> getControlManagerClass() {
		return controlManagerClass;
	}

	/**
	 * Sets my control manager class.
	 * 
	 * @param controlManager
	 *            my control manager class
	 */
	public void setControlManagerClass(Class<? extends IControlManager> controlManager) {
		this.controlManagerClass = controlManager;

		if ((controlManager != null) && (editor != null)) {
			hookControlManager(editor);
		}
	}

	protected void hookControlManager(MultiReferenceControlEditor editor) {
		if ((getControlManagerClass() != null) && (editor != null)) {
			IControlManager controlManager;

			try {
				controlManager = getControlManagerClass().newInstance();
			} catch (Exception e) {
				Activator.log.error(e);
				return;
			}

			editor.enableAddElement(controlManager::canAddElement);
			editor.enableMoveElement(object -> controlManager.canMoveElement(
					nattableManager.getTable().getContext(), object));
			editor.enableRemoveElement(object -> controlManager.canRemoveElement(
					nattableManager.getTable().getContext(), object));

		}
	}

	private IChangeListener getModelChangeListener() {
		if (modelChangeListener == null) {
			modelChangeListener = event -> {
				if ((editor != null) && !editor.isDisposed()) {
					IAxisManager rowMgr = nattableManager.getRowAxisManager();
					if (rowMgr instanceof ICompositeAxisManager) {
						((ICompositeAxisManager) rowMgr).updateAxisContents();
					}
				}
			};
		}

		return modelChangeListener;
	}

	//
	// Nested types
	//

	/**
	 * Protocol for management of the enablement of the row control buttons atop the table.
	 */
	public interface IControlManager {
		/**
		 * Queries whether the object being edited supports the addition of any (more)
		 * elements in the table.
		 * 
		 * @param editedParent
		 *            the parent element being edit, which owns the elements in the table
		 * 
		 * @return whether another element may be added
		 */
		default boolean canAddElement(Object editedParent) {
			return true;
		}

		/**
		 * Queries whether an {@code object} may be moved within the table for the given parent object.
		 * 
		 * @param editedParent
		 *            the parent element being edit, which owns the elements in the table
		 * @param object
		 *            an object to be moved
		 * 
		 * @return whether the {@code object} may be moved
		 */
		default boolean canMoveElement(Object editedParent, Object object) {
			return true;
		}

		/**
		 * Queries whether an {@code object} may be removed from the table for the given parent object.
		 * There is no distinction between exclusion and deletion.
		 * 
		 * @param editedParent
		 *            the parent element being edit, which owns the elements in the table
		 * @param object
		 *            an object to be removed
		 * 
		 * @return whether the {@code object} may be removed
		 */
		default boolean canRemoveElement(Object editedParent, Object object) {
			return true;
		}
	}
}
