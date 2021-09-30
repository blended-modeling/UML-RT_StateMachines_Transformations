/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, Zeligsoft (2009), and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 476984, 495908, 510315, 507282, 512955
 *  Young-Soo Roh - Refactored to generic multi-reference control editor
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.editors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.ICompositeAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.infra.widgets.creation.IAtomicOperationExecutor;
import org.eclipse.papyrusrt.umlrt.core.utils.EMFHacks;
import org.eclipse.papyrusrt.umlrt.tooling.properties.widget.RTNatTableMultiReferencePropertyEditor;
import org.eclipse.papyrusrt.umlrt.tooling.tables.manager.axis.RTSynchronizedOnFeatureAxisManager;
import org.eclipse.papyrusrt.umlrt.tooling.ui.editors.AbstractInheritableMultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


/**
 * Button Control Editor for the multi reference property editor.
 * 
 * @see RTNatTableMultiReferencePropertyEditor
 * 
 * @author Céline JANSSENS
 *
 */
public class MultiReferenceControlEditor extends AbstractInheritableMultipleReferenceEditor {

	private boolean adding;

	private Supplier<? extends INattableModelManager> tableManager;

	private RTSynchronizedOnFeatureAxisManager dataAxisManager;

	private ISelectionProvider selectionProvider;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            The Parent Composite
	 * @param style
	 *            The Style
	 * @param context
	 *            The Related Operation on which the element is added
	 * @param nattableManager
	 *            The Table Manager of the element Table
	 */
	public MultiReferenceControlEditor(final Composite parent, final int style, final EObject context, final EStructuralFeature containment, final Supplier<? extends INattableModelManager> nattableManager) {
		super(parent, true, true, null); // Ecore containments are implicitly ordered and definitely unique

		this.tableManager = nattableManager;
	}

	@Override
	public void createContents() {
		super.createContents();
	}

	@Override
	protected Control createContents(Composite parent) {
		return getNatTable();
	}

	protected NatTable getNatTable() {
		INattableModelManager mgr = tableManager.get();
		return (mgr == null) ? null : mgr.getAdapter(NatTable.class);
	}

	protected RTSynchronizedOnFeatureAxisManager getDataAxisManager() {
		if (dataAxisManager == null) {
			INattableModelManager manager = tableManager.get();
			IAxisManager rowMgr = manager.getRowAxisManager();
			if (rowMgr instanceof ICompositeAxisManager) {
				// Find the row axis manager via a fake axis, because the table configuration won't have one
				ICompositeAxisManager composite = (ICompositeAxisManager) rowMgr;
				IAxis axis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
				axis.setManager(manager.getTable().getTableConfiguration().getRowHeaderAxisConfiguration()
						.getAxisManagers().get(0));

				IAxisManager nested = composite.getSubAxisManagerFor(axis);
				if (nested instanceof RTSynchronizedOnFeatureAxisManager) {
					rowMgr = nested;
				}
			}

			dataAxisManager = TypeUtils.as(rowMgr, RTSynchronizedOnFeatureAxisManager.class);
		}

		return dataAxisManager;
	}

	@Override
	protected void setInput(IObservableList modelProperty) {
		// Pass
	}

	@Override
	public void refreshValue() {
		NatTable table = getNatTable();

		if (table != null) {
			table.refresh();
		}
	}

	@Override
	protected ISelectionProvider getSelectionProvider(Control contentControl) {
		return getSelectionProvider();
	}

	public void setSelectionProvider(ISelectionProvider selectionProvider) {
		this.selectionProvider = selectionProvider;
	}

	public ISelectionProvider getSelectionProvider() {
		return this.selectionProvider;
	}

	@Override
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		// Pass
	}

	@Override
	public IBaseLabelProvider getLabelProvider() {
		return null;
	}

	//
	// NatTable-specific adaptations for control actions
	//

	@Override
	protected void upAction() {
		IStructuredSelection selection = getSelection();

		super.upAction();

		adjustTableSelection(selection);
	}

	@Override
	protected void downAction() {
		IStructuredSelection selection = getSelection();

		super.downAction();

		adjustTableSelection(selection);
	}

	protected void adjustTableSelection(IStructuredSelection selection) {
		getDisplay().asyncExec(() -> {
			if (!isDisposed()) {
				getSelectionProvider().setSelection(selection);
			}
		});
	}

	@Override
	protected void updateShowExcluded() {
		super.updateShowExcluded();

		RTSynchronizedOnFeatureAxisManager axisMgr = getDataAxisManager();
		if (axisMgr != null) {
			axisMgr.setShowExclusions(this.toggleExcluded.getSelection());
		}
	}

	@Override
	protected void removeAction() {
		// What is being removed?
		List<?> toRemove = getSelection().toList();

		super.removeAction();

		// Process removals (if successful)
		toRemove.forEach(this::handleExclusion);
	}

	/**
	 * Ensures appropriate handling of exclusion of an element.
	 * 
	 * @param removedElement
	 *            an element that was deleted or excluded
	 */
	protected void handleExclusion(Object removedElement) {
		RTSynchronizedOnFeatureAxisManager axisMgr = getDataAxisManager();
		if (axisMgr != null) {
			axisMgr.handleExclusion(removedElement);
		}
	}

	/**
	 * Add Action
	 */
	@Override
	protected void addAction() {
		final boolean wasAdding = this.adding;
		this.adding = true;

		try {
			super.addAction();
		} finally {
			this.adding = wasAdding;
		}
	}

	@Override
	public IAtomicOperationExecutor getOperationExecutor(Object context) {
		IAtomicOperationExecutor result = super.getOperationExecutor(context);

		if (adding && (context instanceof EObject)) {
			EObject eObject = (EObject) context;

			boolean nested = EMFHacks.isReadWriteTransactionActive(eObject);
			if (nested) {
				// We don't want the element to show up in the table while it is being
				// edited, but only afterwards
				result = new QuietOperationExecutor(eObject, result)
						.postProcess(o -> EMFHacks.notifyAdded((EObject) o));
			}
		}

		return result;
	}

	//
	// Nested types
	//

	// Temporarily disable notification of addition of the new parameter
	// so that the UI doesn't update prematurely, then notify the addition
	// explicitly on its behalf later.
	// XXX: This is potentially dangerous because if the command adds
	// another parameter also to the same operation, then the transaction
	// will record changes out of order and undo will be broken.
	private static class QuietOperationExecutor implements IAtomicOperationExecutor {
		private final EObject context;
		private final IAtomicOperationExecutor delegate;

		private Consumer<Object> postProcess;

		QuietOperationExecutor(EObject context, IAtomicOperationExecutor delegate) {
			super();

			this.context = context;
			this.delegate = delegate;
		}

		@Override
		public void execute(Runnable operation, String label) {
			EMFHacks.silently(context, __ -> delegate.execute(operation, label));
		}

		@Override
		public <V> V execute(Callable<V> operation, String label) {
			class ResultCapture implements Callable<V> {
				V result;

				@Override
				public V call() throws Exception {
					result = operation.call();
					return result;
				}
			}

			ResultCapture result = new ResultCapture();
			EMFHacks.silently(context, __ -> delegate.execute(result, label));

			if (postProcess != null) {
				if (result.result instanceof Object[]) {
					Stream.of((Object[]) result.result).forEach(postProcess);
				} else if (result.result != null) {
					postProcess.accept(result.result);
				}
			}

			return result.result;
		}

		QuietOperationExecutor postProcess(Consumer<Object> action) {
			if (postProcess == null) {
				postProcess = action;
			} else {
				postProcess = postProcess.andThen(action);
			}
			return this;
		}
	}

}
