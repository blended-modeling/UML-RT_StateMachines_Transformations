/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 514904
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateEditBasedElementCommand;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSourceChangedEvent;
import org.eclipse.papyrus.infra.properties.ui.modelelement.IDataSourceListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.infra.widgets.creation.IAtomicOperationExecutor;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Messages;
import org.eclipse.papyrusrt.umlrt.tooling.ui.editors.CreateProtocolMsgDialog;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.PortContentProvider;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.ProtocolMsgContentProvider;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.ProtocolMsgLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * A dialog for UML/RT port selection within a trigger. References the specific
 * port selection editor. More important. It registers a content provider that
 * will only present the behavior ports of the current capsule.
 */
public class PortAndMsgReference extends AbstractPropertyEditor {

	/**
	 * The property path for events (the XWT file configures the attribute propertyPath to ports)
	 */
	public static final String UML_TRIGGER_EVENT = "UML:Trigger:event"; //$NON-NLS-1$

	public static final String PROTOCOL_MESSAGES = " Protocol messages "; //$NON-NLS-1$

	public static final String PORTS = " Ports "; //$NON-NLS-1$

	protected CheckboxTableViewer fPorts;

	protected CheckboxTreeViewer fMessages;

	protected Package m_model;

	protected Class capsule;

	protected Button createProtocolMsgButton;

	protected IDataSourceListener dsListener;

	/**
	 * Whether we are in a dialog for "add" mode, editing a newly created
	 * trigger. In this case, multiple messages are allowed, which results
	 * in creation of additional triggers as necessary for additional messages.
	 */
	protected boolean addMode;

	protected boolean inSave;

	protected IChangeListener portChangeListener;

	protected IChangeListener msgChangeListener;

	protected Button okButton;

	/**
	 * store reference to transition that is owning the trigger
	 */
	protected Transition transition;

	enum SaveMode {
		PORTS, MESSAGES, ADDITIONAL_TRIGGER,
	};

	public PortAndMsgReference(Composite parent, int style) {

		GridLayout grid = new GridLayout(2, true);
		Composite selections = new Composite(parent, SWT.NONE);

		selections.setLayout(grid);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;

		selections.setLayoutData(gridData);
		createPortSelectionGroup(selections);
		createMessageSelectionGroup(selections);

		okButton = DialogUtils.findOkButton(
				DialogUtils.getDialogShell(parent));

		addMode = false;
		inSave = false;

		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent dp) {
				save(SaveMode.ADDITIONAL_TRIGGER);
				removeChangeListeners();
			}
		});

		dsListener = new IDataSourceListener() {

			@Override
			public void dataSourceChanged(DataSourceChangedEvent event) {

				setInput(event.getDataSource());
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						removeChangeListeners();
						doBinding();
					}
				});
			}
		};

		portChangeListener = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent ce) {
				if (!inSave) {
					setPortChecks();
					// refresh available messages
					fMessages.setInput(this);
					setMessageChecks();
				}
			}
		};

		msgChangeListener = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent ce) {
				if (!inSave) {
					setMessageChecks();
				}
			}
		};
	}


	/**
	 * Install change listeners in input source
	 */
	protected void installChangeListeners() {
		if (input != null) {
			IObservable observablePort = input.getObservable(propertyPath);
			IObservable observableEvent = input.getObservable(UML_TRIGGER_EVENT);
			observablePort.addChangeListener(portChangeListener);
			observableEvent.addChangeListener(msgChangeListener);
		}
	}

	/**
	 * remove change listeners on input source
	 */
	protected void removeChangeListeners() {
		if (input != null) {
			IObservable observablePort = input.getObservable(propertyPath);
			IObservable observableEvent = input.getObservable(UML_TRIGGER_EVENT);
			observablePort.removeChangeListener(portChangeListener);
			observableEvent.removeChangeListener(msgChangeListener);
		}
	}

	/**
	 * Save changes, wraps doSave function and executes it on the setCommand stack
	 * 
	 * @param createAddTriggers
	 *            if true, create additional triggers corresponding to multiple selections (save should be called only once with this option)
	 */
	protected void save(SaveMode saveMode) {
		IOperationHistory history = OperationHistoryFactory.getOperationHistory();
		try {
			history.execute(new AbstractTransactionalCommand(domain, "trigger changes", Collections.EMPTY_LIST) {

				@Override
				public CommandResult doExecuteWithResult(IProgressMonitor dummy, IAdaptable info) {
					doSave(saveMode);
					return CommandResult.newOKCommandResult();
				}
			}, null, null);
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Save changes
	 * 
	 * @param createAddTriggers
	 *            if true, create additional triggers corresponding to multiple selections (save should be called only once with this option)
	 */
	protected void doSave(SaveMode saveMode) {
		// ignore change events during save
		inSave = true;

		Object checkedElements[] = fMessages.getCheckedElements();
		if (saveMode == SaveMode.PORTS) {
			trigger.getPorts().clear();
			for (Object port : fPorts.getCheckedElements()) {
				trigger.getPorts().add((Port) port);
			}
		} else if (saveMode == SaveMode.MESSAGES) {
			trigger.setEvent(checkedElements.length > 0 ? (Event) checkedElements[0] : null);
		} else if (saveMode == SaveMode.ADDITIONAL_TRIGGER) {
			if (checkedElements.length > 1 && addMode) {
				if (fMessages.getCheckedElements()[0] instanceof AnyReceiveEvent) {
					// multiple elements are visually checked, but there is a single UML representation, nothing else to do
				} else if (transition != null) {
					// create additional triggers in transition
					for (int i = 1; i < checkedElements.length; i++) {
						Trigger additionalTrigger = transition.createTrigger(null);
						additionalTrigger.getPorts().addAll(trigger.getPorts());
						additionalTrigger.setEvent((Event) checkedElements[i]);
					}
				}
			}
		}
		inSave = false;
	}

	protected void createPortSelectionGroup(Composite parent) {
		//
		// --------------- available ports -------------------
		//
		Composite gPortSelGroup = new Composite(parent, SWT.NONE);
		Label label = new Label(gPortSelGroup, SWT.NONE);
		label.setText(PORTS);
		gPortSelGroup.setLayout(new GridLayout(1, false));
		gPortSelGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		fPorts = CheckboxTableViewer.newCheckList(gPortSelGroup, SWT.NONE);

		GridData portSelGridData = new GridData(GridData.FILL_BOTH);
		portSelGridData.heightHint = 150;
		portSelGridData.widthHint = 250;
		fPorts.getTable().setLayoutData(portSelGridData);

		ICheckStateListener checkListener = new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object obj = event.getElement();
				if (obj instanceof Port) {
					save(SaveMode.PORTS);
					fMessages.setInput(this);

					updateCreateProtocolMsgState();
					List<Object> availableEvents = new ArrayList<>();
					availableEvents.addAll(Arrays.asList(messageContentProvider.getElements()));
					availableEvents.addAll(Arrays.asList(((ITreeContentProvider) messageContentProvider).getChildren(null)));

					Event umlEvent = trigger.getEvent();
					if (umlEvent != null && !availableEvents.contains(umlEvent)) {
						save(SaveMode.MESSAGES);
					} else {
						setMessageChecks();
					}
					updateOkButtonState();
				}
			}
		};
		fPorts.addCheckStateListener(checkListener);

		if (false) {
			Button addPortButton = new Button(gPortSelGroup, SWT.NONE);
			addPortButton.setText(Messages.PortAndMsgReference_CreatePortTitle);
			addPortButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					try {
						CreateElementCommand command = new CreateEditBasedElementCommand(
								new CreateElementRequest(capsule, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrusrt.umlrt.tooling.ui.rtportcreationwithui")));
						IStatus status;
						status = command.execute(null, null);
						if (status.isOK()) {
							fPorts.refresh();
							Object result = command.getCommandResult().getReturnValue();
							if (result instanceof Port) {
								Port port = (Port) result;
								fPorts.setChecked(port, true);
								trigger.getPorts().add(port);
								fMessages.setInput(this);
								// make new element visible
								fPorts.reveal(port);
								updateCreateProtocolMsgState();
							}
						}
					} catch (ExecutionException ee) {
						Activator.log.error(ee);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}

	/**
	 * Update whether protocol messages can be created
	 * (if a single port is selected and the protocol is not read-only)
	 */
	protected void updateCreateProtocolMsgState() {
		Object portObjs[] = fPorts.getCheckedElements();
		boolean enabled = false;
		if ((portObjs.length == 1) && (portObjs[0] instanceof Port)) {
			Port port = (Port) portObjs[0];
			// check whether in same resource as trigger => editable (not read-only)
			if (port.getType() != null && port.getType().eResource() == trigger.eResource()) {
				enabled = true;
			}
		}
		// createProtocolMsgButton.setEnabled(enabled);
	}

	/**
	 * enable the ok button, if and only if a protocol message has been selected
	 */
	protected void updateOkButtonState() {
		if (okButton != null) {
			okButton.setEnabled(trigger.getEvent() != null);
		}
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	protected void createMessageSelectionGroup(Composite parent) {

		Composite messageSelectionGroup = new Composite(parent, SWT.NONE);
		Label label = new Label(messageSelectionGroup, SWT.NONE);
		label.setText(PROTOCOL_MESSAGES);

		messageSelectionGroup.setLayout(new GridLayout(1, true));
		messageSelectionGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		fMessages = new CheckboxTreeViewer(messageSelectionGroup, SWT.NONE);

		GridData msgGridData = new GridData(GridData.FILL_BOTH);
		msgGridData.heightHint = 150;
		msgGridData.widthHint = 250;
		fMessages.getTree().setLayoutData(msgGridData);

		messageSelectionGroup.pack();

		ICheckStateListener checkListener = new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent changeEvent) {
				Object obj = changeEvent.getElement();

				if (addMode) {
					if (obj instanceof AnyReceiveEvent) {
						if (changeEvent.getChecked()) {
							// check all elements
							Object children[] = ((ITreeContentProvider) messageContentProvider).getChildren(null);
							fMessages.setCheckedElements(children);
							fMessages.setChecked(obj, true);
						} else {
							// un-check all
							fMessages.setCheckedElements(new Object[0]);
						}
					} else if (!changeEvent.getChecked()) {
						// handle case that a child is unselected. If any was checked, it needs to be removed
						Object checkedElems[] = fMessages.getCheckedElements();
						if (checkedElems.length > 0 && checkedElems[0] instanceof AnyReceiveEvent) {
							fMessages.setChecked(checkedElems[0], false);
						}
					}
				} else {
					if (changeEvent.getChecked()) {
						if (obj instanceof AnyReceiveEvent) {
							// check all elements
							Object children[] = ((ITreeContentProvider) messageContentProvider).getChildren(obj);
							fMessages.setCheckedElements(children);
						} else {
							fMessages.setCheckedElements(new Object[0]);
						}
						fMessages.setChecked(obj, true);
					} else {
						// un-check all
						fMessages.setCheckedElements(new Object[0]);
					}
				}
				// no full save, since we don't want to add additional triggers right now
				save(SaveMode.MESSAGES);
				updateOkButtonState();
			}
		};
		fMessages.addCheckStateListener(checkListener);

		// Disable following code temporarily, see comments in bug 477811
		if (false) {
			createProtocolMsgButton = new Button(messageSelectionGroup, SWT.NONE);
			createProtocolMsgButton.setText(CreateProtocolMsgDialog.CREATE_PROTOCOL_MSG_TITLE);
			updateCreateProtocolMsgState();
			createProtocolMsgButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					final Object portObjs[] = fPorts.getCheckedElements();
					if ((portObjs.length == 1) && (portObjs[0] instanceof Port)) {
						try {
							getOperationExecutor(trigger).execute(new Runnable() {

								@Override
								public void run() {
									Port port = (Port) portObjs[0];
									if ((port != null) && (port.getType() != null)) {
										Package protocol = port.getType().getNearestPackage();
										boolean isConjugated = ((Port) fPorts.getCheckedElements()[0]).isConjugated();
										CreateProtocolMsgDialog editProtocolDialog = new CreateProtocolMsgDialog(Display.getDefault().getActiveShell(), protocol, isConjugated);
										if (editProtocolDialog.open() == Window.CANCEL) {
											throw new OperationCanceledException();
										}
										fMessages.refresh();
										Object checkedElements[] = fMessages.getCheckedElements();
										if ((checkedElements.length > 0) && (checkedElements[0] instanceof AnyReceiveEvent)) {
											// assure that new element is marked, if any receive event is set
											Object any = checkedElements[0];
											Object children[] = ((ITreeContentProvider) messageContentProvider).getChildren(any);
											fMessages.setCheckedElements(children);
											fMessages.setChecked(any, true);
										}
									}
								}
							}, CreateProtocolMsgDialog.CREATE_PROTOCOL_MSG_TITLE);
						} catch (OperationCanceledException cancelException) {
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}

	/**
	 * Obtains the most appropriate operation executor for the object being edited.
	 *
	 * @param context
	 *            the object being edited
	 * @return the executor to use to run operations (never {@code null})
	 */
	public IAtomicOperationExecutor getOperationExecutor(Object context) {
		IAtomicOperationExecutor result;
		if (context instanceof IAdaptable) {
			result = ((IAdaptable) context).getAdapter(IAtomicOperationExecutor.class);
		} else if (context != null) {
			result = Platform.getAdapterManager().getAdapter(context, IAtomicOperationExecutor.class);
		} else {
			// We can't adapt null, of course, so we will have to settle for the default executor
			result = null;
		}

		if (result == null) {
			result = IAtomicOperationExecutor.DEFAULT;
		}

		return result;
	}

	IStaticContentProvider portContentProvider;

	ILabelProvider portLabelProvider;

	IStaticContentProvider messageContentProvider;

	ILabelProvider messageLabelProvider;

	TransactionalEditingDomain domain;

	protected Trigger trigger;

	public void initializeProviders(ILabelProvider labelProvider) {
		portContentProvider = new PortContentProvider(capsule);
		portLabelProvider = labelProvider;
		messageContentProvider = new ProtocolMsgContentProvider(trigger);
		messageLabelProvider = new ProtocolMsgLabelProvider(labelProvider);
	}

	@Override
	protected void unhookDataSourceListener(DataSource oldInput) {
		oldInput.removeDataSourceListener(dsListener);
		super.unhookDataSourceListener(oldInput);
	}

	@Override
	protected void hookDataSourceListener(DataSource newInput) {
		newInput.addDataSourceListener(dsListener);
		super.hookDataSourceListener(newInput);
	}

	/**
	 * Set the check-status of ports according to model contents
	 */
	protected void setPortChecks() {
		fPorts.setAllChecked(false);
		for (Port existingPort : trigger.getPorts()) {
			fPorts.setChecked(existingPort, true);
		}
	}

	/**
	 * Set the check-status of protocol-messages according to model contents
	 */
	protected void setMessageChecks() {
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				Event event = trigger.getEvent();
				// uncheck all first
				fMessages.setCheckedElements(new Object[0]);
				if (event != null) {
					fMessages.expandAll();
					if (event instanceof AnyReceiveEvent) {
						// mark all children
						Object children[] = ((ITreeContentProvider) messageContentProvider).getChildren(event);
						fMessages.setCheckedElements(children);
					}
					fMessages.setChecked(event, true);
				}
			}
		});
	}

	@Override
	protected void doBinding() {
		ModelElement triggerElement = input.getModelElement(propertyPath);
		ILabelProvider labelProvider = input.getLabelProvider(propertyPath);

		EObject triggerEObj = ((UMLModelElement) triggerElement).getSource();
		installChangeListeners();

		if (triggerEObj instanceof Trigger) {
			trigger = (Trigger) triggerEObj;

			// obtain transition from trigger
			transition = (Transition) trigger.getOwner();
			addMode = NewElementUtil.isCreatedElement(triggerEObj);
			if (transition == null) {
				// trigger has been created, but is not yet part of the model, get owner from context
				CreationContext creationContext = (CreationContext) EcoreUtil.getExistingAdapter(trigger, CreationContext.class);
				if (creationContext != null) {
					transition = (Transition) creationContext.getCreationContextElement();
				}
				// Even if the trigger wasn't marked as new, it must be if it's not yet
				// attached to the model, so treat it as new in 'add mode' for selection
				// of multiple messages
				addMode = true;
			}
			// navigate from transition to capsule (owning class)
			Element owner = transition;
			while (owner != null) {
				if ((owner instanceof Class) && !(owner instanceof Behavior)) {
					capsule = (Class) owner;
					break;
				}
				owner = owner.getOwner();
			}
			if (capsule != null) {
				// get domain from capsule instead of trigger, since trigger might not have been attached
				// to the model
				domain = TransactionUtil.getEditingDomain(capsule);

				initializeProviders(labelProvider);
				fPorts.setContentProvider(portContentProvider);
				fPorts.setLabelProvider(portLabelProvider);
				fPorts.setInput(this);
				setPortChecks();

				fMessages.setContentProvider(messageContentProvider);
				fMessages.setLabelProvider(messageLabelProvider);
				fMessages.setInput(this);

				setMessageChecks();
			}
		}
		super.doBinding();
		updateOkButtonState();
	}
}
