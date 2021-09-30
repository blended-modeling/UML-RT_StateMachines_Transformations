/*******************************************************************************
 * Copyright (c) 2014 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.protocoleditor.listeners;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.uml.tools.listeners.PapyrusStereotypeListener;
import org.eclipse.papyrus.umlrt.UMLRealTime.ProtocolContainer;
import org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil;
import org.eclipse.papyrusrt.protocoleditor.internal.UMLRTProtocolUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * UMLRT resource set listener
 * 
 * @author ysroh
 *
 */
public class UMLRTProtocolListener extends ResourceSetListenerImpl {

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {
		for (Notification n : event.getNotifications()) {
			n.getNotifier();
		}
		super.resourceSetChanged(event);
	}

	@Override
	public Command transactionAboutToCommit(ResourceSetChangeEvent event)
			throws RollbackException {

		for (Notification notification : event.getNotifications()) {

			if (!(notification.getNotifier() instanceof Element)) {
				continue;
			}

			Element notifier = (Element) notification.getNotifier();

			// create protocol container contents
			if (notification instanceof PapyrusStereotypeListener.StereotypeCustomNotification
					&& PapyrusStereotypeListener.APPLIED_STEREOTYPE == notification
							.getEventType()) {
				Object newValue = notification.getNewValue();
				if (newValue instanceof ProtocolContainer) {
					Package protocolContainer = (Package) notifier;
					return getCommandForNameChange(event.getEditingDomain(),
							protocolContainer, protocolContainer.getName(),
							protocolContainer.getName());
				}
			}

			// handle protocol name change
			if (notification.getEventType() == Notification.SET
					&& (UMLRealTimeProfileUtil.isProtocol(notifier) || UMLRealTimeProfileUtil
							.isProtocolContainer(notifier))
					&& notification.getFeature().equals(
							UMLPackage.Literals.NAMED_ELEMENT__NAME)) {
				Package protocolContainer = UMLRTProtocolUtil
						.getProtocolContainer(notifier);
				if (protocolContainer != null
						&& !UML2Util.isEmpty(notification.getOldStringValue())) {
					return getCommandForNameChange(event.getEditingDomain(),
							protocolContainer,
							notification.getOldStringValue(),
							notification.getNewStringValue());
				}
			}

			// handle operation name change
			if (notification.getEventType() == Notification.SET
					&& notification.getFeature().equals(
							UMLPackage.Literals.NAMED_ELEMENT__NAME)
					&& notifier instanceof Operation
					&& notifier.eContainer() instanceof Element
					&& UMLRealTimeProfileUtil.isRTMessageSet((Element) notifier
							.eContainer())) {
				return getCommandForOperationNameChange(
						event.getEditingDomain(), (Operation) notifier,
						notification.getOldStringValue(),
						notification.getNewStringValue());
			}

			// handle delete operation
			if (notification.getEventType() == Notification.SET
					&& notification.getFeature().equals(
							UMLPackage.Literals.OPERATION__INTERFACE)
					&& notification.getOldValue() instanceof Element
					&& notification.getNewValue() == null
					&& UMLRealTimeProfileUtil
							.isRTMessageSet((Element) notification
									.getOldValue())) {
				boolean isMove = false;
				for (Notification notification2 : event.getNotifications()) {
					// handle delete operation
					if (!(notification2.getNotifier() instanceof Element)) {
						continue;
					}
					Element notifier2 = (Element) notification2.getNotifier();

					if (!notifier.equals(notifier2)) {
						continue;
					}

					if (notification2.getEventType() == Notification.SET
							&& notification2.getFeature().equals(
									UMLPackage.Literals.OPERATION__INTERFACE)
							&& notification2.getNewValue() instanceof Element
							&& notification2.getOldValue() == null
							&& UMLRealTimeProfileUtil
									.isRTMessageSet((Element) notification2
											.getNewValue())) {
						isMove = true;
						break;
					}
				}
				if (!isMove) {
					return getCommandForRemoveOperation(
							event.getEditingDomain(),
							(Element) notification.getOldValue(),
							(Operation) notifier);
				}
				return null;
			}
			// handle add operation
			if (notification.getEventType() == Notification.ADD
					&& notification.getFeature().equals(
							UMLPackage.Literals.INTERFACE__OWNED_OPERATION)
					&& !UML2Util.isEmpty(((Operation) notification
							.getNewValue()).getName())
					&& UMLRealTimeProfileUtil.isRTMessageSet(notifier)) {
				// see if this is a move
				return getCommandForOperationNameChange(
						event.getEditingDomain(),
						(Operation) notification.getNewValue(),
						UMLUtil.EMPTY_STRING,
						((Operation) notification.getNewValue()).getName());
			}
			// handle new parameter
			if (notification.getFeature().equals(
					UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER)
					&& notifier instanceof Operation) {
				Operation op = (Operation) notifier;
				Interface intf = op.getInterface();
				if (intf != null && UMLRealTimeProfileUtil.isRTMessageSet(intf)) {
					return getCommandForOperationNameChange(
							event.getEditingDomain(), op, op.getName(),
							op.getName());
				}
			}

			// handle parameter change in order to change call event name
			if (notification.getEventType() == Notification.SET
					&& notification.getFeature().equals(
							UMLPackage.Literals.TYPED_ELEMENT__TYPE)
					&& notifier instanceof Parameter) {
				Operation op = ((Parameter) notifier).getOperation();
				if (op == null) {
					continue;
				}
				Interface intf = op.getInterface();
				if (intf != null && UMLRealTimeProfileUtil.isRTMessageSet(intf)) {
					return getCommandForOperationNameChange(
							event.getEditingDomain(), op, op.getName(),
							op.getName());
				}
			}
		}

		return null;
	}

	/**
	 * Handle operation change
	 * 
	 * @param editingDomain
	 * @param messageSet
	 * @param operation
	 * @return
	 */
	private Command getCommandForRemoveOperation(
			TransactionalEditingDomain editingDomain, final Element messageSet,
			final Operation operation) {
		return new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				Package protocolContainer = UMLRTProtocolUtil
						.getProtocolContainer(messageSet);
				if (protocolContainer == null) {
					return;
				}
				CallEvent callEvent = (CallEvent) protocolContainer
						.getPackagedElement(operation.getName(), false,
								UMLPackage.Literals.CALL_EVENT, false);
				if (callEvent != null) {
					EcoreUtil.delete(callEvent);
				}
			}
		};
	}

	/**
	 * Handle operation name change
	 * 
	 * @param editingDomain
	 * @param operation
	 * @param oldName
	 * @param newName
	 * @return
	 */
	private Command getCommandForOperationNameChange(
			TransactionalEditingDomain editingDomain,
			final Operation operation, final String oldName,
			final String newName) {

		return new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				Package protocolContainer = UMLRTProtocolUtil
						.getProtocolContainer(operation);
				if (protocolContainer == null) {
					return;
				}
				StringBuilder postfix = new StringBuilder();
				for (Parameter p : operation.getOwnedParameters()) {
					if (ParameterDirectionKind.IN_LITERAL.equals(p
							.getDirection()) && p.getType() != null) {
						postfix.append("_");
						postfix.append(p.getType().getName());
					}
				}
				String postfixString = postfix.toString();
				CallEvent callEvent = null;
				for (PackageableElement e : protocolContainer
						.getPackagedElements()) {
					if (e instanceof CallEvent
							&& operation.equals(((CallEvent) e).getOperation())) {
						e.setName(newName + postfixString);
						return;
					}
				}

				String oldEventName = oldName + postfixString;
				String newEventName = newName + postfixString;
				if (!UML2Util.isEmpty(oldEventName)) {
					callEvent = (CallEvent) protocolContainer
							.getPackagedElement(oldEventName, false,
									UMLPackage.Literals.CALL_EVENT, false);
				}
				if (callEvent == null) {
					callEvent = (CallEvent) protocolContainer
							.getPackagedElement(newEventName, false,
									UMLPackage.Literals.CALL_EVENT, true);
				}
				if (callEvent != null) {
					callEvent.setOperation(operation);
					callEvent.setName(newEventName);
				}
			}
		};
	}

	/**
	 * Handle protocol name change
	 * 
	 * @param editingDomain
	 * @param protocolContainer
	 * @param oldStringValue
	 * @param newStringValue
	 * @return
	 */
	private Command getCommandForNameChange(
			TransactionalEditingDomain editingDomain,
			final Package protocolContainer, final String oldStringValue,
			final String newStringValue) {

		return new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				UMLRTProtocolUtil.createProtocolContainerContent(
						protocolContainer, oldStringValue, newStringValue);

			}
		};
	}

}
