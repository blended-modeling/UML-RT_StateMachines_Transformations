/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList;
import org.eclipse.papyrus.uml.properties.expression.ExpressionList.Expression;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTExtModelElementFactory;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.editors.ILanguageEditor;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.editors.LanguageEditorRegistry;
import org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.internal.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;

/**
 * <p>
 * Abstract code snippet tab. Code snippet tabs should extend this class to
 * utilize basic functionalities provided by this abstract class.
 * </p>
 * 
 * @author ysroh
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractCodeSnippetTab implements ICodeSnippetTab, IChangeListener {

	/**
	 * Bold font.
	 */
	private static Font bold = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

	/**
	 * Italic font.
	 */
	private static Font italic = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);

	/**
	 * Current input.
	 */
	protected EObject input;

	/**
	 * Main composite.
	 */
	protected Composite tabComposite;

	/**
	 * Tab item.
	 */
	protected CTabItem tabItem;

	/**
	 * Label.
	 */
	protected CLabel label;

	/**
	 * Language label.
	 */
	protected CLabel languageLabel;

	/**
	 * Indicator for creation of composite.
	 */
	protected boolean controlCreated = false;

	/**
	 * Default language.
	 */
	protected String defaultLanguage = UML2Util.EMPTY_STRING;

	/**
	 * Model element for feature observable.
	 */
	protected ModelElement meForFeatureObservable;

	/**
	 * Model element for specification observable.
	 */
	protected ModelElement meForSpecificationObservable;

	/**
	 * Observable list for source and body pairs.
	 */
	protected ExpressionList expressionObservableList;

	/**
	 * Model element for expression list observable.
	 */
	protected ModelElement meForExpressionObservable;

	/**
	 * Selected language.
	 */
	protected String selectedLanguage;

	/**
	 * Editor for selected language.
	 */
	protected ILanguageEditor languageEditor;

	/**
	 * UMLRT model element factory.
	 */
	protected UMLRTExtModelElementFactory rtModelFactory = new UMLRTExtModelElementFactory();

	/** Flag for default tab selection. **/
	protected boolean defaultSelection = false;

	/**
	 * Element label provider.
	 */
	private ILabelProvider labelProvider;

	/**
	 * Flag for ignoring change listener event.
	 */
	private boolean ignoreChanges = false;

	/**
	 * Set input. Subclass may override this function to set different input.
	 * 
	 * @param input
	 *            input
	 */
	protected void doSetInput(EObject input) {
		this.input = input;
	}

	@Override
	public void setInput(EObject input) {
		// initialize default selection flag
		defaultSelection = false;

		doSetInput(input);
		if (this.input == null) {
			throw new IllegalArgumentException();
		}

		handleNewInput();
	}

	/**
	 * Handle new input.
	 */
	protected void handleNewInput() {
		// update default language
		defaultLanguage = ModelUtils.getDefaultLanguage((Element) input).getName();
		selectedLanguage = defaultLanguage;

		// update observable
		updateFeatureObservable();
		updateExpressionObservableList();

		// update label
		updateLabel();

		updateLanguageEditor();

		// refresh contents
		refresh();

		if (defaultSelection) {
			CTabFolder folder = tabItem.getParent();
			folder.setSelection(tabItem);
		}
	}

	/**
	 * Update language editor.
	 */
	protected void updateLanguageEditor() {
		if (languageEditor != null) {
			languageEditor.dispose();
		}
		languageEditor = LanguageEditorRegistry.getInstance().getNewLanguageEditorInstance(selectedLanguage);
		languageEditor.createControl(tabComposite);
		languageEditor.addValueChangeListener(new IValueChangeListener<Expression>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends Expression> event) {
				commit();
			}
		});
		tabComposite.layout();
	}

	/**
	 * Dispose context observable.
	 */
	private void disposeFeatureObservable() {

		// dispose model element for feature observable
		if (meForFeatureObservable != null) {
			meForFeatureObservable.dispose();
			meForFeatureObservable = null;
		}
	}

	/**
	 * Dispose observable list for language and body pairs.
	 */
	private void disposeExpressionObservableList() {
		// dispose model element for specification observable
		if (meForSpecificationObservable != null) {
			meForSpecificationObservable.dispose();
			meForSpecificationObservable = null;
		}

		// dispose expression observable
		if (expressionObservableList != null) {
			expressionObservableList.dispose();
			expressionObservableList = null;
		}

		// dispose model element for expression observable
		if (meForExpressionObservable != null) {
			meForExpressionObservable.dispose();
			meForExpressionObservable = null;
		}
	}

	/**
	 * Update feature observable.
	 */
	protected void updateFeatureObservable() {

		disposeFeatureObservable();
		IObservable featureObservable = getFeatureObservable();

		if (featureObservable != null) {
			featureObservable.addChangeListener(this);
		}
	}

	/**
	 * Update expression observable list.
	 */
	protected void updateExpressionObservableList() {

		disposeExpressionObservableList();
		IObservable specificationObservable = getSpecificationObservable();
		if (specificationObservable != null) {
			specificationObservable.addChangeListener(this);
		}

		expressionObservableList = getExpressionObservableList();
		if (expressionObservableList != null) {
			expressionObservableList.addChangeListener(this);
		}
	}

	@Override
	public CTabItem createControl(CTabFolder parent) {
		controlCreated = true;

		// create tab composite
		tabComposite = new Composite(parent, SWT.NONE);
		tabComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tabComposite.setLayout(new GridLayout());

		// create labels
		Composite labelComposite = new Composite(tabComposite, SWT.NONE);
		GridData data = new GridData(SWT.LEFT, SWT.TOP, false, false);
		labelComposite.setLayoutData(data);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		labelComposite.setLayout(layout);
		label = new CLabel(labelComposite, SWT.NONE);
		languageLabel = new CLabel(labelComposite, SWT.NONE);

		// ToDo: support CDT integration in the future.
		languageEditor = LanguageEditorRegistry.getInstance()
				.getNewLanguageEditorInstance(NoDefautLanguage.INSTANCE.getName());
		languageEditor.createControl(tabComposite);

		tabItem = new CTabItem(parent, SWT.NONE);
		tabItem.setControl(tabComposite);

		return tabItem;
	}

	@Override
	public CTabItem getControl() {
		return tabItem;
	}

	@Override
	public void handleChange(ChangeEvent event) {
		if (!ignoreChanges) {
			if (tabItem != null && !tabItem.isDisposed()) {

				IObservable observable = event.getObservable();
				if (!(observable instanceof Expression) && !(observable instanceof ExpressionList)) {
					// owner of expression has update so update observable
					updateExpressionObservableList();
				}
				// refresh contents
				refresh();
			}
		}
	}

	/**
	 * Update label.
	 */
	protected void updateLabel() {
		if (controlCreated && input != null) {

			// update the label for the object
			label.setImage(getLabelProvider().getImage(input));
			label.setText(getLabelProvider().getText(input));
			languageLabel.setText("(" + selectedLanguage + ")");

			tabComposite.layout();
		}
	}

	/**
	 * Get label provider.
	 * 
	 * @return label provider
	 */
	protected ILabelProvider getLabelProvider() {
		try {
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, input)
					.getLabelProvider();
		} catch (ServiceException e) {
			Activator.getDefault().error(e.getMessage(), e);
			// If this ever happens then return default provider so no NPE
			// is thrown
			labelProvider = new UMLLabelProvider();
		}
		return labelProvider;
	}

	@Override
	public boolean controlCreated() {
		return controlCreated;
	}

	@Override
	public void dispose() {
		disposeFeatureObservable();
		disposeExpressionObservableList();
		if (tabItem != null && !tabItem.isDisposed()) {
			// dispose tabItem
			tabItem.dispose();
		}
		if (tabComposite != null && !tabComposite.isDisposed()) {
			tabComposite.dispose();
		}
		tabComposite = null;
		controlCreated = false;
		input = null;
	}

	/**
	 * Subclass to override to refresh contents.
	 */
	protected void refresh() {

		// set tab item labels
		tabItem.setText(getTitle());
		tabItem.setImage(getImage());
		tabItem.setFont(italic);

		final Expression ex = new Expression();
		ex.setLanguage(selectedLanguage);
		ex.setBody(UML2Util.EMPTY_STRING);
		final UMLRTNamedElement facade = getFacade();
		if ((facade != null && !facade.isExcluded())
				|| (expressionObservableList != null && input instanceof Operation)) {
			// display contents if not excluded.
			int index = expressionObservableList.indexOf(ex);
			if (index != -1) {
				ex.setBody(((Expression) expressionObservableList.get(index)).getBody());
				tabItem.setFont(bold);
			}
		}

		SafeRunnable runnable = new SafeRunnable() {

			@Override
			public void run() throws Exception {
				languageEditor.setValue(ex);
			}
		};
		SafeRunnable.run(runnable);
	}

	/**
	 * Get facade for either opaque behaviour or constraint.
	 * 
	 * @return facade
	 */
	protected UMLRTNamedElement getFacade() {
		UMLRTNamedElement facade = null;
		if (expressionObservableList != null) {
			EObject observed = (EObject) expressionObservableList.getObserved();
			if (observed instanceof OpaqueExpression) {
				observed = observed.eContainer();
			}
			// It is either opaque behaviour or guard
			facade = (UMLRTNamedElement) UMLRTFactory.create(observed);
		}
		return facade;
	}

	/**
	 * Commit.
	 */
	protected void commit() {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(input);
		ICommand cmd = null;
		Expression expr = languageEditor.getValue();

		String commandLabel = "Save " + getTitle() + " Code";

		UMLRTNamedElement facade = getFacade();
		if (UML2Util.isEmpty(expr.getBody()) && facade != null && !facade.isInherited()) {
			// 1) When emptying an opaque behavior based code snippet, i.e.
			// effect of a transition or entry/exit for a state, for the
			// non-inheritance case, the opaque behavior shall be removed when
			// the code snippet is made empty.
			// 2) When emptying an opaque expression based code snippet, i.e. a
			// transition guard or a trigger guard, for the non-inheritance
			// case, the corresponding constraint, and thus its opaque
			// expression specification, shall be removed
			final Element elementToDelete = facade.toUML();
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(input);
			DestroyElementRequest request = new DestroyElementRequest(domain, elementToDelete, false);
			cmd = provider.getEditCommand(request);
		} else if (UML2Util.isEmpty(expr.getBody()) && facade != null && facade instanceof UMLRTOpaqueBehavior) {
			// When emptying an inherited opaque behavior based code snippet,
			// the opaque behavior shall be excluded
			if (facade.isInherited() && !facade.isExcluded()) {
				// exclude
				cmd = ExclusionCommand.getExclusionCommand(facade.toUML(), true);
			}
		} else {
			CompositeCommand compositeCmd = new CompositeCommand(commandLabel);

			if (facade != null && facade.isExcluded()) {
				// revert exclusion
				compositeCmd.compose(ExclusionCommand.getExclusionCommand(facade.toUML(), false));
			}
			ICommand saveCmd = new AbstractTransactionalCommand(domain, commandLabel, Collections.EMPTY_LIST) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
						throws ExecutionException {

					commit(expr);

					return CommandResult.newOKCommandResult();
				}
			};
			compositeCmd.compose(saveCmd);
			cmd = compositeCmd.reduce();
		}

		if (cmd != null && cmd.canExecute()) {
			// Do not handle changes during the commit to prevent accessing
			// disposed observables
			ignoreChanges = true;
			domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));
			ignoreChanges = false;
			setInput(input);
		}
	}

	/**
	 * Get expression list from element.
	 * 
	 * @param element
	 *            element
	 * @return Expression list
	 */
	@SuppressWarnings("rawtypes")
	protected ExpressionList getExpressionList(EObject element) {
		if (element instanceof OpaqueBehavior || element instanceof OpaqueExpression) {
			// UMLRT model factory does not require data context
			if (meForExpressionObservable == null) {
				meForExpressionObservable = rtModelFactory.createFromSource(element, null);
			}
			IObservableList languages = (IObservableList) meForExpressionObservable.getObservable("language");
			IObservableList bodies = (IObservableList) meForExpressionObservable.getObservable("body");
			ExpressionList el = new ExpressionList(languages, bodies, this);
			return el;
		}
		return null;
	}

	/**
	 * Get observable for specification.
	 * 
	 * @param element
	 *            context
	 * @return observable
	 */
	protected IObservable getSpecificationObservable(EObject element) {
		IObservable result = null;
		if (element != null) {
			if (meForSpecificationObservable == null) {
				meForSpecificationObservable = rtModelFactory.createFromSource(element, null);
			}
			result = meForSpecificationObservable.getObservable("specification");
		}
		return result;
	}

	/**
	 * Get tab title.
	 * 
	 * @return title
	 */
	protected String getTitle() {
		return "";
	}

	/**
	 * Get tab image.
	 * 
	 * @return image
	 */
	protected Image getImage() {
		return null;
	}

	@Override
	public boolean hasCode() {
		if (languageEditor != null && !UML2Util.isEmpty(languageEditor.getValue().getBody())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isDefaultSelected() {
		return defaultSelection;
	}

	/**
	 * Get feature observable.
	 * 
	 * @return feature observable.
	 */
	protected abstract IObservable getFeatureObservable();

	/**
	 * Get specification observable.
	 * 
	 * @return feature observable.
	 */
	protected abstract IObservable getSpecificationObservable();

	/**
	 * Queries expression observable list for this feature.
	 * 
	 * @return Expression list
	 */
	protected abstract ExpressionList getExpressionObservableList();

	/**
	 * Save user code for the first time. May need to create feature containing
	 * source code first.
	 * 
	 * @param expression
	 *            expression
	 */
	protected abstract void commit(Expression expression);

}
