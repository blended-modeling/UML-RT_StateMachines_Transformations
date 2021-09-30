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
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.editors;

import static org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper.wrap;
import static org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils.canRedefine;
import static org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.widgets.editors.AbstractMultipleReferenceEditor;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.IFilteredObservableList;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Element;

/**
 * Partial multiple-reference editor for references that support inheritance.
 */
public abstract class AbstractInheritableMultipleReferenceEditor extends AbstractMultipleReferenceEditor {

	private static final String SHOW_EXCLUDED_SETTING = "showExcluded"; //$NON-NLS-1$

	private String dialogSettingsKey;

	protected Button toggleExcluded;
	protected Button reinherit;

	protected Predicate<Object> canAddElement = Objects::nonNull;

	protected Predicate<Object> canMoveElement = Objects::nonNull;

	protected Predicate<Object> canRemoveElement = Objects::nonNull;

	private Predicate<Object> inheritanceFilter;

	private ISelectionChangedListener upDownEnablementListener;
	private ISelectionChangedListener editEnablementListener;

	public AbstractInheritableMultipleReferenceEditor(Composite parent) {
		this(parent, false, false, null);
	}

	public AbstractInheritableMultipleReferenceEditor(Composite parent, String label) {
		this(parent, false, false, label);
	}

	public AbstractInheritableMultipleReferenceEditor(Composite parent, boolean ordered, boolean unique, String label) {
		super(parent, ordered, unique, label);
	}

	@Override
	protected void createContents() {
		super.createContents();

		// Tweak the layout for our custom buttons
		GridLayout layout = new GridLayout(7, false);
		layout.horizontalSpacing = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		controlsSection.setLayout(layout);
		updateControls();
	}

	public final void setDialogSettingsKey(String key) {
		this.dialogSettingsKey = key;

		loadDialogSettings();
	}

	public final String getDialogSettingsKey() {
		return dialogSettingsKey;
	}

	protected final String getDialogSettingsKey(String subkey) {
		String result = getDialogSettingsKey();
		return (result == null) ? null : String.format("%s.%s", result, subkey);
	}

	@Override
	protected Object getContextElement() {
		Object result = super.getContextElement();

		if (result instanceof FacadeObject) {
			result = ((FacadeObject) result).toUML();
		}

		return result;
	}

	@Override
	protected void createListControls() {
		ImageDescriptor toggleInheritedImage = imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"$nl$/icons/full/etool16/show_excluded.png"); //$NON-NLS-1$

		toggleExcluded = createToggleButton(ExtendedImageRegistry.INSTANCE.getImage(toggleInheritedImage),
				false, "Show excluded elements");

		super.createListControls();

		remove.setToolTipText("Remove or exclude selected element");
		ImageDescriptor reinheritImage = imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"$nl$/icons/full/etool16/reinherit.png"); //$NON-NLS-1$
		reinherit = createButton(ExtendedImageRegistry.INSTANCE.getImage(reinheritImage),
				"Re-inherit selected element");

		loadDialogSettings();
	}

	protected Button createToggleButton(Image image, boolean selected, String tooltip) {
		Button result = new Button(controlsSection, SWT.TOGGLE);
		result.setImage(image);
		result.setSelection(selected);
		result.setToolTipText(tooltip);
		result.addSelectionListener(this);
		return result;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == toggleExcluded) {
			toggleExcludedAction();
		} else if (e.widget == reinherit) {
			reinheritAction();
		} else {
			super.widgetSelected(e);
		}
	}

	@Override
	protected void updateControls() {
		super.updateControls();

		if (controlsSection.getLayout() instanceof GridLayout) {
			setExclusion(up, !ordered);
			setExclusion(down, !ordered);
		}

		add.setEnabled(canAdd());
		if (!canRemove()) {
			remove.setEnabled(false);
		}

		up.setEnabled(false);
		down.setEnabled(false);

		if (ordered) {
			if (upDownEnablementListener == null) {
				addSelectionChangedListener(getUpDownEnablementListener());
			}
			updateUpDownEnablement(getSelection());
		}

		toggleExcluded.setEnabled(!readOnly && (modelProperty instanceof IFilteredObservableList<?>));
		reinherit.setEnabled(!readOnly && canReinherit());

		if (editEnablementListener == null) {
			addSelectionChangedListener(getEditEnablementListener());
		}
		edit.setEnabled(canEdit(getSelection().getFirstElement()));
	}

	private ISelectionChangedListener getUpDownEnablementListener() {
		if (upDownEnablementListener == null) {
			upDownEnablementListener = event -> {
				updateUpDownEnablement(event.getStructuredSelection());
			};
		}

		return upDownEnablementListener;
	}

	void updateUpDownEnablement(IStructuredSelection selection) {
		if ((up != null) && (down != null)) {
			List<?> elements = selection.toList();
			boolean makesSense = ordered && !elements.isEmpty()
					&& isHomogeneous(elements, AbstractInheritableMultipleReferenceEditor.this::getInheritanceKindOf);
			up.setEnabled(makesSense && canMoveUp(elements));
			down.setEnabled(makesSense && canMoveDown(elements));
		}
	}

	private ISelectionChangedListener getEditEnablementListener() {
		if (editEnablementListener == null) {
			editEnablementListener = event -> {
				updateEditEnablement(event.getStructuredSelection());
			};
		}

		return editEnablementListener;
	}

	void updateEditEnablement(IStructuredSelection selection) {
		if (edit != null) {
			Object element = selection.getFirstElement();
			edit.setEnabled((element != null) && canEdit(element));
		}
	}

	/**
	 * Queries whether a collection of {@code elements} is homogeneous in
	 * some property expressed by the given {@code mapping}.
	 * 
	 * @param elements
	 *            a collection of elements
	 * @param mapping
	 *            a mapping function to extract the property
	 * @return {@code false} if the {@code mapping} yields different values for
	 *         any two {@code element}s; {@code true}, otherwise
	 */
	protected final <E> boolean isHomogeneous(Collection<? extends E> elements, Function<? super E, ?> mapping) {
		return elements.stream().map(mapping).distinct().count() < 2L;
	}

	protected Predicate<Object> getInheritanceFilter(boolean demandCreate) {
		if ((inheritanceFilter == null) && demandCreate) {
			Predicate<Object> isExcluded = o -> {
				EObject eObject = EMFHelper.getEObject(o);
				return (eObject instanceof FacadeObject)
						? ((FacadeObject) eObject).isExcluded()
						: (eObject instanceof Element) && UMLRTExtensionUtil.isExcluded((Element) eObject);
			};

			inheritanceFilter = isExcluded.negate();
		}
		return inheritanceFilter;
	}

	protected void toggleExcludedAction() {
		updateShowExcluded();

		commit();

		storeDialogSettings();
	}

	protected void updateShowExcluded() {
		if (toggleExcluded == null) {
			return;
		}

		boolean showExcluded = toggleExcluded.getSelection();

		if (modelProperty instanceof IFilteredObservableList<?>) {
			IFilteredObservableList<?> list = (IFilteredObservableList<?>) modelProperty;
			Predicate<Object> filter = getInheritanceFilter(!showExcluded);

			boolean filterChanged = false;
			if (showExcluded) {
				if (filter != null) {
					filterChanged = list.removeFilter(filter);
				}
			} else {
				filterChanged = list.addFilter(filter);
			}

			if (filterChanged && !isDisposed()) {
				refreshValue();
			}
		}
	}

	protected void reinheritAction() {
		IStructuredSelection selection = (IStructuredSelection) getSelectionProvider(reinherit).getSelection();
		List<Element> toReinherit = ((List<?>) selection.toList()).stream()
				.filter(this::isInherited)
				.map(this::toUML)
				.filter(Element.class::isInstance)
				.map(Element.class::cast)
				.collect(Collectors.toList());

		if (!toReinherit.isEmpty()) {
			Element any = toReinherit.get(0);
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(any);
			ICommand command = ExclusionCommand.getExclusionCommand(domain, toReinherit, false);
			domain.getCommandStack().execute(wrap(command));
		}

		commit();
	}

	@Override
	public void setModelObservable(@SuppressWarnings("rawtypes") IObservableList modelProperty) {
		super.setModelObservable(modelProperty);

		loadDialogSettings();
	}

	@Override
	public void handleChange(ChangeEvent event) {
		if (!isDisposed()) {
			if (event.getObservable() == modelProperty) {
				updateShowExcluded();
			}
		}

		super.handleChange(event);
	}

	protected void loadDialogSettings() {
		if (modelProperty == null) {
			return;
		}

		if (toggleExcluded != null) {
			boolean showExcluded = false;
			String setting = getDialogSettingsKey(SHOW_EXCLUDED_SETTING);
			if (setting != null) {
				showExcluded = getBoolean(setting, false);
				toggleExcluded.setSelection(showExcluded);
				updateShowExcluded();
			}
		}
	}

	protected void storeDialogSettings() {
		if (toggleExcluded != null) {
			String setting = getDialogSettingsKey(SHOW_EXCLUDED_SETTING);
			if (setting != null) {
				setBoolean(setting, toggleExcluded.getSelection());
			}
		}
	}

	protected boolean getBoolean(String key, boolean defaultValue) {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = DialogSettings.getOrCreateSection(settings, "InheritableMultipleReferenceEditor"); //$NON-NLS-1$
		return (section.get(key) == null) ? defaultValue : section.getBoolean(key);
	}

	protected void setBoolean(String key, boolean value) {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = DialogSettings.getOrCreateSection(settings, "InheritableMultipleReferenceEditor"); //$NON-NLS-1$
		section.put(key, value);
	}

	protected boolean canMoveUp(Collection<?> selection) {
		boolean result = true;

		for (Object next : selection) {
			if (!canMoveElement.test(next)) {
				result = false;
				break;
			}

			int index = modelProperty.indexOf(next);
			UMLRTInheritanceKind kind = getInheritanceKindOf(next);
			if (kind == UMLRTInheritanceKind.INHERITED) {
				// Cannot re-order these because there is nowhere to persist the ordering
				result = false;
				break;
			}

			UMLRTInheritanceKind before = (index > 0)
					? getInheritanceKindOf(modelProperty.get(index - 1))
					: null;
			if (before != kind) {
				result = false;
				break;
			}
		}

		return result;
	}

	protected UMLRTInheritanceKind getInheritanceKindOf(Object object) {
		return (object instanceof UMLRTNamedElement)
				? ((UMLRTNamedElement) object).getInheritanceKind()
				: (object instanceof Element)
						? UMLRTInheritanceKind.of((Element) object)
						: UMLRTInheritanceKind.NONE;
	}

	protected EObject toUML(Object source) {
		return (source instanceof FacadeObject)
				? ((FacadeObject) source).toUML()
				: EMFHelper.getEObject(source);
	}

	protected boolean isInherited(Object source) {
		return getInheritanceKindOf(source) != UMLRTInheritanceKind.NONE;
	}

	protected boolean canMoveDown(Collection<?> selection) {
		boolean result = true;

		int last = modelProperty.size() - 1;
		for (Object next : selection) {
			int index = modelProperty.indexOf(next);
			UMLRTInheritanceKind kind = getInheritanceKindOf(next);
			if (kind == UMLRTInheritanceKind.INHERITED) {
				// Cannot re-order these because there is nowhere to persist the ordering
				result = false;
				break;
			}

			UMLRTInheritanceKind after = (index < last)
					? getInheritanceKindOf(modelProperty.get(index + 1))
					: null;
			if (after != kind) {
				result = false;
				break;
			}
		}

		return result;
	}

	protected boolean canEdit(Object element) {
		boolean result = false;
		EObject eObject = toUML(element);

		if ((eObject != null) && !EMFHelper.isReadOnly(eObject)) {
			// Is it an inherited element that does not support redefinition
			// in this context?
			result = Optional.of(eObject)
					.filter(Element.class::isInstance)
					.map(Element.class::cast)
					.map(e -> !isInherited(e) || canRedefine(e))
					.orElse(result);
		}

		return result;
	}

	/**
	 * Registers a predicate determining whether an element may be added to the element
	 * being edited.
	 * 
	 * @param canAddElement
	 *            a predicate on the element being edited (which would be the
	 *            container of any element that would be added)
	 */
	public void enableAddElement(Predicate<Object> canAddElement) {
		this.canAddElement = this.canAddElement.and(canAddElement);

		if ((modelProperty != null) && (add != null)) {
			// This does not depend on the selection
			updateControls();
		}
	}

	protected boolean canAdd() {
		return canAddElement.test(getContextElement());
	}

	/**
	 * Registers a predicate determining whether an element may be moved.
	 * It is not necessary here to account for whether the element is
	 * at the beginning or end of the list.
	 * 
	 * @param canMoveElement
	 *            a predicate on the element being moved
	 */
	public void enableMoveElement(Predicate<Object> canMoveElement) {
		this.canMoveElement = this.canMoveElement.and(canMoveElement);
	}

	/**
	 * Registers a predicate determining whether an element may be removed.
	 * This applies equally to exclusion as to deletion.
	 * 
	 * @param canRemoveElement
	 *            a predicate on the element being removed
	 */
	public void enableRemoveElement(Predicate<Object> canRemoveElement) {
		this.canRemoveElement = this.canRemoveElement.and(canRemoveElement);
	}

	protected boolean canRemove() {
		return ((List<?>) getSelection().toList()).stream().allMatch(canRemoveElement);
	}

	protected boolean canReinherit() {
		List<?> selection = getSelection().toList();

		return !selection.isEmpty() && selection.stream()
				.allMatch(e -> {
					EObject eObject = toUML(e);
					return (eObject instanceof Element)
							&& UMLRTProfileUtils.canReinherit((Element) eObject);
				});
	}
}
