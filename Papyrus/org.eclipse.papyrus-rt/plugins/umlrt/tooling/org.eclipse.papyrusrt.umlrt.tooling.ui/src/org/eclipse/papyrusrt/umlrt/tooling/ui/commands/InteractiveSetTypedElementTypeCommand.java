/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.commands;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.iterate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Messages;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice.ConfiguratorDialog;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.types.UMLRTUIElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.menus.PopupMenuWithSeparators;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTExtModelElementFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.ParameterTypeContentProvider;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.PortTypeContentProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.collect.ImmutableSet;

/**
 * A command that interactively prompts the user for the type to assign to a
 * {@link TypedElement} and sets it.
 */
public class InteractiveSetTypedElementTypeCommand extends AbstractTransactionalCommand {

	private final TypedElement element;

	private final IElementType elementType;

	private final IElementType typeElementType;

	private final Function<? super IElementType, String> elementTypeLabelFunction;

	private Set<View> newTypeViews = Collections.emptySet();

	private boolean createNew;

	/**
	 * Initializes me with a default element-type labelling function.
	 * 
	 * @param element
	 *            the element for which I am to set a type
	 * @param elementType
	 *            the metatype of the {@code element}
	 * @param typeElementType
	 *            the metatype of the types that we may assign to the {@code element}
	 */
	public InteractiveSetTypedElementTypeCommand(TypedElement element, IElementType elementType, IElementType typeElementType) {
		this(element, elementType, typeElementType, IElementType::getDisplayName);
	}

	/**
	 * Initializes me.
	 * 
	 * @param element
	 *            the element for which I am to set a type
	 * @param elementType
	 *            the metatype of the {@code element}
	 * @param typeElementType
	 *            the metatype of the types that we may assign to the {@code element}
	 * @param elementTypeLabelFunction
	 *            a function for customization of element-type labels to be presented to
	 *            the user
	 */
	public InteractiveSetTypedElementTypeCommand(TypedElement element, IElementType elementType, IElementType typeElementType, Function<? super IElementType, String> elementTypeLabelFunction) {
		super(TransactionUtil.getEditingDomain(element), "Set Type", getWorkspaceFiles(element));

		this.element = element;
		this.elementType = elementType;
		this.typeElementType = typeElementType;
		this.elementTypeLabelFunction = elementTypeLabelFunction;
	}

	/**
	 * Obtains the element for I interactively set a type.
	 * 
	 * @return the typed element
	 */
	public TypedElement getElement() {
		return element;
	}

	/**
	 * Obtains the metatype of the element for which we are setting a type.
	 * 
	 * @return the typed element's metatype
	 */
	public IElementType getElementType() {
		return elementType;
	}

	/**
	 * Obtains the metatype classifying the types that are permitted for the element.
	 * 
	 * @return the permitted type metatype
	 */
	public IElementType getTypeElementType() {
		return typeElementType;
	}

	/**
	 * Obtains the best user presentable label for an element-type.
	 * 
	 * @param elementType
	 *            an element-type
	 * @return its user-friendly label
	 */
	public String getDisplayName(IElementType elementType) {
		return elementTypeLabelFunction.apply(elementType);
	}

	/**
	 * Assigns the views to be presented in a dialog for configuration of a new type.
	 * If empty, then no dialog is shown.
	 * 
	 * @param newTypeViews
	 *            the new type dialog views, or an empty set if no dialog is needed
	 */
	public void setNewTypeViews(Collection<? extends View> newTypeViews) {
		this.newTypeViews = ImmutableSet.copyOf(newTypeViews);
	}

	/**
	 * Obtains the views to be presented in a dialog for configuration of a new type.
	 * If empty, then no dialog is shown.
	 * 
	 * @return the new type dialog views
	 */
	public Set<View> getNewTypeViews() {
		return newTypeViews;
	}

	/**
	 * Sets whether the {@link #getElement() element} that we are configuring
	 * is newly created, which implies different labelling of menu items.
	 * 
	 * @param createNew
	 *            whether we are in a new element creation workflow
	 */
	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	/**
	 * Queries whether the {@link #getElement() element} that we are configuring
	 * is newly created, which implies different labelling of menu items.
	 * 
	 * @return whether we are in a new element creation workflow
	 */
	public boolean isCreateNew() {
		return createNew;
	}

	private String createTypeItemLabel(IElementType typeToCreate) {
		return NLS.bind(
				isCreateNew() ? "{0} with existing {1}..." : "Select existing {1}...",
				getDisplayName(getElementType()),
				getDisplayName(typeToCreate));
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		ICommand selectExistingTypeCommand = new AbstractTransactionalCommand(getEditingDomain(), getLabel(), getAffectedFiles()) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				UMLRTExtModelElementFactory meFactory = new UMLRTExtModelElementFactory();
				Type selectedType = null;

				// For UML-RT, the DataContextElement is not required, so just provide null
				ModelElement modelElement = meFactory.createFromSource(getElement(), null);
				try {
					IStaticContentProvider contentProvider = modelElement.getContentProvider("type"); //$NON-NLS-1$
					ILabelProvider labelProvider = modelElement.getLabelProvider("type"); //$NON-NLS-1$
					if (contentProvider != null) {
						String dialogTitle = NLS.bind("Select {0}", getDisplayName(getTypeElementType()));
						selectedType = browseType(getElement(), dialogTitle, contentProvider, labelProvider);
					}
				} finally {
					modelElement.dispose();
				}

				// User must select a type
				if (selectedType == null) {
					return CommandResult.newCancelledCommandResult();
				} else {
					return setType(getEditingDomain(), getElement(), selectedType);
				}
			}
		};

		Shell parentShell = Display.getCurrent().getActiveShell();

		Map<ICommand, String> createTypeCommands = getCreateTypeCommands();
		Map<ICommand, String> wrappedCreateTypeCommands = new LinkedHashMap<>();
		for (Map.Entry<ICommand, String> next : createTypeCommands.entrySet()) {
			ICommand createType = next.getKey();
			String label = next.getValue();

			ICommand createNewTypeCommand = new AbstractTransactionalCommand(getEditingDomain(), getLabel(), getAffectedFiles()) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					Type createdType = null;

					createType.execute(monitor, info);
					CommandResult typeResult = createType.getCommandResult();
					if (!typeResult.getStatus().isOK()) {
						return typeResult;
					}
					createdType = (typeResult.getReturnValue() instanceof List<?>)
							? (Type) ((List<?>) typeResult.getReturnValue()).get(0)
							: (Type) typeResult.getReturnValue();

					Set<View> dialogViews = getNewTypeViews();
					if (!dialogViews.isEmpty()) {
						// Let the user configure the new type
						ConfiguratorDialog dlg = new ConfiguratorDialog(parentShell, true);
						dlg.setTitle(NLS.bind("Create New {0}", getDisplayName(getTypeElementType())));
						dlg.setViews(getNewTypeViews());
						dlg.setInput(createdType);
						if (dlg.open() != Window.OK) {
							return CommandResult.newCancelledCommandResult();
						}
					}

					return setType(getEditingDomain(), getElement(), createdType);
				}
			};
			wrappedCreateTypeCommands.put(createNewTypeCommand, label);
		}

		List<ICommand> languageTypeCommands = getSetLanguageTypeCommands();

		ILabelProvider popupLabels = new LabelProvider() {
			@Override
			public String getText(Object element) {
				String result;

				if (element == selectExistingTypeCommand) {
					result = NLS.bind(
							isCreateNew() ? "{0} with existing {1}..." : "Select existing {1}...",
							getDisplayName(getElementType()),
							getDisplayName(getTypeElementType()));
				} else if (wrappedCreateTypeCommands.containsKey(element)) {
					result = wrappedCreateTypeCommands.get(element);
				} else if (element instanceof SetLanguageTypeCommand) {
					result = ((SetLanguageTypeCommand) element).getTypeLabel();
				} else {
					result = super.getText(element);
				}

				return result;
			}
		};

		// Count one for the separator even if we don't need it
		List<Object> menuItems = new ArrayList<>(languageTypeCommands.size() + 2 + wrappedCreateTypeCommands.size());
		menuItems.addAll(languageTypeCommands);
		if (!languageTypeCommands.isEmpty()) {
			menuItems.add(new PopupMenuWithSeparators.Separator());
		}
		menuItems.add(selectExistingTypeCommand);
		menuItems.addAll(wrappedCreateTypeCommands.keySet());
		PopupMenuWithSeparators popupMenu = new PopupMenuWithSeparators(menuItems, popupLabels);

		ICommand interactive = getPopupMenuCommand(parentShell, popupMenu);
		interactive.execute(monitor, info);
		return interactive.getCommandResult();
	}

	private ICommand getPopupMenuCommand(Shell parentShell, PopupMenu popupMenu) {
		return new PopupMenuCommand("Set Type", parentShell, popupMenu) {
			private ICommand delegate;

			@Override
			protected CommandResult doExecuteWithResult(
					IProgressMonitor progressMonitor, IAdaptable info)
					throws ExecutionException {

				if (!getPopupMenu().show(getParentShell())) {
					// User cancelled the menu
					delegate = UnexecutableCommand.INSTANCE;
					progressMonitor.setCanceled(true);
					return CommandResult.newCancelledCommandResult();
				}

				delegate = (ICommand) getPopupMenu().getResult();
				delegate.execute(progressMonitor, info);

				// Inherit contexts
				for (IUndoContext next : delegate.getContexts()) {
					if (!hasContext(next)) {
						addContext(next);
					}
				}

				return delegate.getCommandResult();
			}

			@Override
			public boolean canUndo() {
				return (delegate != null) && delegate.canUndo();
			}

			@Override
			protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				delegate.undo(progressMonitor, info);
				return delegate.getCommandResult();
			}

			@Override
			public boolean canRedo() {
				return (delegate != null) && delegate.canRedo();
			}

			@Override
			protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				delegate.redo(progressMonitor, info);
				return delegate.getCommandResult();
			}
		};
	}

	/**
	 * Set the {@code type} of a typed {@code element}, using the edit-service if available
	 * to involve relevant particles of advice. If the edit-service cannot do it, then just
	 * directly set the type.
	 * 
	 * @param domain
	 *            the editing-domain context
	 * @param element
	 *            the type element to edit
	 * @param type
	 *            the type to set for the {@code element}
	 * 
	 * @return the result of setting the type
	 * 
	 * @throws ExecutionException
	 *             on failure of edit-service command execution
	 */
	static CommandResult setType(TransactionalEditingDomain domain, TypedElement element, Type type) throws ExecutionException {
		// Use the edit-helper to set the type because there is advice
		// for things like capsule-parts that reacts to editing the type
		ICommand edit = null;
		IElementEditService editService = ElementEditServiceUtils.getCommandProvider(element);
		if (editService != null) {
			edit = editService.getEditCommand(new SetRequest(domain, element, UMLPackage.Literals.TYPED_ELEMENT__TYPE, type));
		}
		if ((edit == null) || !edit.canExecute()) {
			element.setType(type);
		} else {
			edit.execute(new NullProgressMonitor(), null);
			if (edit.getCommandResult().getStatus().getSeverity() >= IStatus.ERROR) {
				return edit.getCommandResult();
			}
		}
		return CommandResult.newOKCommandResult(type);
	}

	/**
	 * Clear the {@code type} of a typed {@code element}, using the edit-service if available
	 * to involve relevant particles of advice. If the edit-service cannot do it, then just
	 * directly unset the type.
	 * 
	 * @param domain
	 *            the editing-domain context
	 * @param element
	 *            the type element to edit
	 * 
	 * @return the result of clearing the type
	 * 
	 * @throws ExecutionException
	 *             on failure of edit-service command execution
	 */
	static CommandResult unsetType(TransactionalEditingDomain domain, TypedElement element) throws ExecutionException {
		// Use the edit-helper to set the type because there is advice
		// for things like capsule-parts that reacts to editing the type
		ICommand edit = null;
		IElementEditService editService = ElementEditServiceUtils.getCommandProvider(element);
		if (editService != null) {
			edit = editService.getEditCommand(new UnsetRequest(domain, element, UMLPackage.Literals.TYPED_ELEMENT__TYPE));
		}
		if ((edit == null) || !edit.canExecute()) {
			element.eUnset(UMLPackage.Literals.TYPED_ELEMENT__TYPE);
		} else {
			edit.execute(new NullProgressMonitor(), null);
			if (edit.getCommandResult().getStatus().getSeverity() >= IStatus.ERROR) {
				return edit.getCommandResult();
			}
		}
		return CommandResult.newOKCommandResult();
	}

	/**
	 * Obtains a mapping of commands to menu-item labels for pop-up menu actions to create
	 * a new element of the given type.
	 * 
	 * @param elementType
	 *            the element-type to create and configure
	 * @param typedElement
	 *            the element created (we are creating commands to configure it)
	 * @param typeToCreate
	 *            the kind of type to create for the element
	 * 
	 * @return the mapping of commands, possibly empty, to menu-item labels
	 */
	private Map<ICommand, String> getCreateTypeCommands() {
		Map<ICommand, String> result;

		org.eclipse.uml2.uml.Package container = getNearestPackage(getElement());

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(container);
		if (service == null) {
			Activator.log.error("Edit service not available for " + container, null);
			result = Collections.emptyMap();
		} else {
			// Function for item label according to whether we create new or not
			Function<IElementType, String> itemLabel = typeToCreate -> NLS.bind(
					isCreateNew() ? "{0} with new {1}..." : "Create new {1}...",
					getDisplayName(getElementType()),
					getDisplayName(typeToCreate));

			result = new UMLSwitch<Map<ICommand, String>>() {
				@Override
				public Map<ICommand, String> defaultCase(EObject object) {
					return Collections.emptyMap();
				}

				@Override
				public Map<ICommand, String> caseTypedElement(TypedElement object) {
					return Collections.singletonMap(
							service.getEditCommand(new CreateElementRequest(container, getTypeElementType())),
							itemLabel.apply(getTypeElementType()));
				}

				@Override
				public Map<ICommand, String> caseParameter(Parameter object) {
					// There are only a few kinds of types that we allow to create
					Map<ICommand, String> result = new LinkedHashMap<>();
					for (IElementType next : Arrays.asList(UMLElementTypes.CLASS, UMLElementTypes.ENUMERATION)) {
						ICommand command = service.getEditCommand(new CreateElementRequest(container, next));
						if ((command != null) && command.canExecute()) {
							result.put(command, itemLabel.apply(next));
						}
					}

					return result;
				}
			}.doSwitch(getElement());
		}

		return result;
	}

	/**
	 * Obtains the nearest package that is or contains an {@code element}
	 * that is not a protocol container.
	 * 
	 * @param element
	 *            an element
	 * @return the nearest package that is not a protocol container
	 */
	private org.eclipse.uml2.uml.Package getNearestPackage(Element element) {
		org.eclipse.uml2.uml.Package result = element.getNearestPackage();

		// Don't create these types in a protocol container
		while (ProtocolContainerUtils.isProtocolContainer(result)) {
			result = result.getOwner().getNearestPackage();
		}

		return result;
	}

	protected Type browseType(TypedElement typedElement, String title, IStaticContentProvider contentProvider, ILabelProvider labelProvider) {
		Type result = null;

		TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getCurrent().getActiveShell());
		dialog.setContentProvider(getTypesContentProvider(typedElement, contentProvider));
		dialog.setLabelProvider(labelProvider);
		dialog.setTitle(title);

		if (dialog.open() == Window.OK) {
			Object oneSelected;
			Object[] selected = dialog.getResult();
			if ((selected != null) && (selected.length > 0)) {
				oneSelected = selected[0];
				// Adapt, if applicable
				if (contentProvider instanceof IAdaptableContentProvider) {
					oneSelected = ((IAdaptableContentProvider) contentProvider).getAdaptedValue(oneSelected);
				}
				if (oneSelected instanceof Type) {
					result = (Type) oneSelected;
				}
			}
		}

		return result;
	}

	private ITreeContentProvider getTypesContentProvider(TypedElement typedElement, IStaticContentProvider contentProvider) {
		return new UMLSwitch<ITreeContentProvider>() {
			@Override
			public ITreeContentProvider defaultCase(EObject object) {
				return new EncapsulatedContentProvider(EmptyContentProvider.instance);
			}

			@Override
			public ITreeContentProvider caseTypedElement(TypedElement object) {
				return new EncapsulatedContentProvider(contentProvider);
			}

			@Override
			public ITreeContentProvider casePort(Port object) {
				// filter system protocols if we create the port from the palette as an external port
				return getPortTypeContentProvider(contentProvider);

			}

			@Override
			public ITreeContentProvider caseParameter(Parameter object) {
				return new ParameterTypeContentProvider(contentProvider);
			}
		}.doSwitch(typedElement);
	}

	private List<ICommand> getSetLanguageTypeCommands() {
		return new UMLSwitch<List<ICommand>>() {
			@Override
			public List<ICommand> defaultCase(EObject object) {
				return Collections.emptyList();
			}

			@Override
			public List<ICommand> caseTypedElement(TypedElement object) {
				return getPrimitiveSetLanguageTypeCommands(getElementType(), object);
			}

			@Override
			public List<ICommand> casePort(org.eclipse.uml2.uml.Port object) {

				// do not return default language system protocols if we create the port from the palette as an external port
				if (elementType.equals(UMLRTUIElementTypesEnumerator.EXTERNAL_PORT_CREATION_WITH_UI)) {
					return Collections.emptyList();
				} else {
					return getSystemProtocolsSetLanguageTypeCommands(getElementType(), object);
				}

			}


			@Override
			public List<ICommand> caseProperty(Property object) {
				return Collections.emptyList();
			}
		}.doSwitch(getElement());
	}

	private ITreeContentProvider getPortTypeContentProvider(IStaticContentProvider contentProvider) {

		// filter out system protocols if we drop the port on the capsule border
		if (elementType.equals(UMLRTUIElementTypesEnumerator.EXTERNAL_PORT_CREATION_WITH_UI)) {
			return new PortTypeContentProvider(contentProvider) {

				@Override
				public boolean isValidValue(Object element) {
					boolean result = super.isValidValue(element);

					if (result) {
						EObject eObject = EMFHelper.getEObject(element);
						result = (ProtocolUtils.isProtocol(eObject) &&
								!SystemElementsUtils.isSystemProtocol((Collaboration) eObject));
					}

					return result;
				}
			};
		} else {
			// only base protocols are filtred
			return new PortTypeContentProvider(contentProvider);
		}
	}

	protected List<ICommand> getSystemProtocolsSetLanguageTypeCommands(IElementType elementType, Port element) {
		List<ICommand> result;

		IDefaultLanguage language = null;

		try {
			IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, element);
			Element root = iterate(element, Element::getOwner).filter(e -> e.getOwner() == null).findFirst().get();
			language = service.getActiveDefaultLanguage(root);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		if (language == null) {
			result = Collections.emptyList();
		} else {
			// We know there's an editing domain if we had a service registry
			// to get the default language service
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
			Set<Collaboration> types = language.getSystemProtocols(domain.getResourceSet());
			result = types.stream()
					.map(SetLanguageTypeCommand.factory(domain, elementType, element))
					.sorted(comparing(SetLanguageTypeCommand::getTypeLabel))
					.collect(Collectors.toList());

		}

		return result;
	}

	private List<ICommand> getPrimitiveSetLanguageTypeCommands(IElementType elementType, TypedElement element) {
		List<ICommand> result;

		IDefaultLanguage language = null;

		try {
			IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, element);
			Element root = iterate(element, Element::getOwner).filter(e -> e.getOwner() == null).findFirst().get();
			language = service.getActiveDefaultLanguage(root);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		if (language == null) {
			result = Collections.emptyList();
		} else {
			// We know there's an editing domain if we had a service registry
			// to get the default language service
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
			Set<Type> types = language.getSpecificPrimitiveTypes(domain.getResourceSet());
			result = types.stream()
					.map(SetLanguageTypeCommand.factory(domain, elementType, element))
					.sorted(comparing(SetLanguageTypeCommand::getTypeLabel))
					.collect(Collectors.toList());

			// And add the untyped option, too
			result.add(0, new SetLanguageTypeCommand(domain, elementType, element, null) {
				@Override
				String getTypeLabel() {
					return Messages.NoTypeForTypedElement_Label;
				}

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					if (element.getType() != null) {
						unsetType(getEditingDomain(), element);
					}
					return CommandResult.newOKCommandResult();
				}
			});
		}

		return result;
	}

	//
	// Nested types
	//

	private static class SetLanguageTypeCommand extends AbstractTransactionalCommand {
		private final IElementType elementType;
		private final TypedElement element;
		private final Type type;

		SetLanguageTypeCommand(TransactionalEditingDomain domain, IElementType elementType, TypedElement element, Type type) {
			super(domain, "Set Type", getWorkspaceFiles(element));

			this.elementType = elementType;
			this.element = element;
			this.type = type;
		}

		String getTypeLabel() {
			return NLS.bind("{0}", type.getLabel(), elementType.getDisplayName());
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			return setType(getEditingDomain(), element, type);
		}

		static Function<Type, SetLanguageTypeCommand> factory(TransactionalEditingDomain domain, IElementType elementType, TypedElement element) {
			return type -> new SetLanguageTypeCommand(domain, elementType, element, type);
		}
	}

}
